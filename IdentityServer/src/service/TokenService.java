package service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.grain.httpserver.HttpException;
import org.grain.httpserver.HttpPacket;
import org.grain.httpserver.IHttpListener;

import action.TokenAction;
import action.UCErrorPack;
import action.UserAction;
import config.UserConfig;
import dao.model.base.Token;
import dao.model.base.User;
import http.HOpCodeUCenter;
import protobuf.http.UCErrorProto.UCError;
import protobuf.http.UCErrorProto.UCErrorCode;
import protobuf.http.UserGroupProto.DeleteTokenS;
import protobuf.http.UserGroupProto.GetTokenC;
import protobuf.http.UserGroupProto.GetTokenS;
import protobuf.http.UserGroupProto.UpdateTokenS;
import tool.TimeUtils;

public class TokenService implements IHttpListener {

	@Override
	public Map<String, String> getHttps() {
		HashMap<String, String> map = new HashMap<>();
		map.put(HOpCodeUCenter.GET_TOKEN, "getTokenHandle");
		map.put(HOpCodeUCenter.UPDATE_TOKEN, "updateTokenHandle");
		map.put(HOpCodeUCenter.DELETE_TOKEN, "deleteTokenHandle");
		return map;
	}

	public HttpPacket getTokenHandle(HttpPacket httpPacket) throws HttpException {
		GetTokenC message = (GetTokenC) httpPacket.getData();
		User user = UserAction.getUserByName(message.getUserName());
		if (user == null) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_4, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		if (user.getUserState().intValue() != UserConfig.STATE_USABLE) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_5, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		// 判断密码
		if (!user.getUserPassword().equals(message.getUserPassword())) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_6, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		Token token = TokenAction.getTokenByUserId(user.getUserId());
		if (token == null) {
			token = TokenAction.createToken(user.getUserId());
			if (token == null) {
				token = TokenAction.getTokenByUserId(user.getUserId());
			}
		} else {
			Date date = new Date();
			// 判断是否过期
			if (date.getTime() > token.getTokenExpireTime().getTime()) {
				TokenAction.deleteToken(token.getTokenId());
				token = TokenAction.createToken(user.getUserId());
				if (token == null) {
					token = TokenAction.getTokenByUserId(user.getUserId());
				}
			} else {
				TokenAction.updateToken(token.getTokenId());
			}
		}
		if (token == null) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_7, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		GetTokenS.Builder builder = GetTokenS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		builder.setTokenId(token.getTokenId());
		builder.setTokenExpireTime(TimeUtils.dateToString(token.getTokenExpireTime()));
		builder.setUser(UserAction.getUserDataBuilder(user, token.getTokenId()));
		HttpPacket packet = new HttpPacket(httpPacket.hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket updateTokenHandle(HttpPacket httpPacket) throws HttpException {

		Token token = TokenAction.updateToken(httpPacket.hSession.headParam.token);
		if (token == null) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_8, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		User user = (User) httpPacket.hSession.otherData;
		UpdateTokenS.Builder builder = UpdateTokenS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		builder.setTokenId(token.getTokenId());
		builder.setTokenExpireTime(TimeUtils.dateToString(token.getTokenExpireTime()));
		builder.setUser(UserAction.getUserDataBuilder(user, token.getTokenId()));
		HttpPacket packet = new HttpPacket(httpPacket.hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket deleteTokenHandle(HttpPacket httpPacket) throws HttpException {

		boolean result = TokenAction.deleteToken(httpPacket.hSession.headParam.token);
		if (!result) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_9, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		DeleteTokenS.Builder builder = DeleteTokenS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		HttpPacket packet = new HttpPacket(httpPacket.hSession.headParam.hOpCode, builder.build());
		return packet;
	}

}
