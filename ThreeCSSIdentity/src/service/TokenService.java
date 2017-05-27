package service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import action.TokenAction;
import action.UserAction;
import dao.model.base.Token;
import dao.model.base.User;
import http.HOpCodeUCenter;
import http.HSession;
import http.HttpPacket;
import http.IHttpListener;
import protobuf.http.UserGroupProto.DeleteTokenS;
import protobuf.http.UserGroupProto.GetTokenC;
import protobuf.http.UserGroupProto.GetTokenS;
import protobuf.http.UserGroupProto.UpdateTokenS;
import tool.TimeUtils;

public class TokenService implements IHttpListener {

	@Override
	public Map<Integer, String> getHttps() throws Exception {
		HashMap<Integer, String> map = new HashMap<>();
		map.put(HOpCodeUCenter.GET_TOKEN, "getTokenHandle");
		map.put(HOpCodeUCenter.UPDATE_TOKEN, "updateTokenHandle");
		map.put(HOpCodeUCenter.DELETE_TOKEN, "deleteTokenHandle");
		return map;
	}

	@Override
	public Object getInstance() {
		return this;
	}

	public HttpPacket getTokenHandle(HSession hSession) {
		GetTokenC message = (GetTokenC) hSession.httpPacket.getData();
		User user = UserAction.getUserByName(message.getUserName());
		if (user == null) {
			return null;
		}
		// 判断密码
		if (!user.getUserPassword().equals(message.getUserPassword())) {
			return null;
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
			return null;
		}
		GetTokenS.Builder builder = GetTokenS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		builder.setTokenId(token.getTokenId());
		builder.setTokenExpireTime(TimeUtils.dateToString(token.getTokenExpireTime()));
		builder.setUser(UserAction.getUserDataBuilder(user));
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket updateTokenHandle(HSession hSession) {

		Token token = TokenAction.updateToken(hSession.headParam.token);
		if (token == null) {
			return null;
		}
		User user = (User) hSession.otherData;
		if (user == null) {
			return null;
		}
		UpdateTokenS.Builder builder = UpdateTokenS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		builder.setTokenId(token.getTokenId());
		builder.setTokenExpireTime(TimeUtils.dateToString(token.getTokenExpireTime()));
		builder.setUser(UserAction.getUserDataBuilder(user));
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket deleteTokenHandle(HSession hSession) {

		boolean result = TokenAction.deleteToken(hSession.headParam.token);
		if (!result) {
			return null;
		}
		DeleteTokenS.Builder builder = DeleteTokenS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

}
