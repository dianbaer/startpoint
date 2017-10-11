package http.filter;

import java.util.Date;

import org.grain.httpserver.HttpException;
import org.grain.httpserver.HttpPacket;
import org.grain.httpserver.IHttpFilter;

import action.TokenAction;
import action.UCErrorPack;
import action.UserAction;
import dao.model.base.Token;
import dao.model.base.User;
import http.HOpCodeUCenter;
import protobuf.http.UCErrorProto.UCError;
import protobuf.http.UCErrorProto.UCErrorCode;

public class TokenHttpFilter implements IHttpFilter {

	@Override
	public boolean httpFilter(HttpPacket httpPacket) throws HttpException {
		if (httpPacket.hSession.headParam.token == null) {
			if (HOpCodeUCenter.GET_TOKEN.equals(httpPacket.hSession.headParam.hOpCode) || HOpCodeUCenter.CHECK_USER_BY_USER_NAME.equals(httpPacket.hSession.headParam.hOpCode) || HOpCodeUCenter.CHECK_USER_PHONE.equals(httpPacket.hSession.headParam.hOpCode)) {
				// 可以通过
				return true;
			} else {
				// 不可以通过
				UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_2, httpPacket.hSession.headParam.hOpCode);
				throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
			}
		}
		Token token = TokenAction.getTokenById(httpPacket.hSession.headParam.token);
		if (token == null) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_1, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		Date date = new Date();
		// token过期
		if (date.getTime() > token.getTokenExpireTime().getTime()) {
			TokenAction.deleteToken(token.getTokenId());
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_1, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		} else {
			TokenAction.updateToken(token.getTokenId());
		}
		User user = UserAction.getUserById(token.getUserId());
		if (user == null) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_4, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		httpPacket.hSession.otherData = user;
		return true;
	}

}
