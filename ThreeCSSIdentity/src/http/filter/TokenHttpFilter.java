package http.filter;

import java.util.Date;

import action.TokenAction;
import action.UCErrorPack;
import action.UserAction;
import dao.model.base.Token;
import dao.model.base.User;
import http.HOpCodeUCenter;
import http.HSession;
import http.exception.HttpErrorException;
import protobuf.http.UCErrorProto.UCError;
import protobuf.http.UCErrorProto.UCErrorCode;

public class TokenHttpFilter implements IHttpFilter {

	@Override
	public boolean httpFilter(HSession hSession) throws HttpErrorException {
		if (hSession.headParam.token == null) {
			if (HOpCodeUCenter.GET_TOKEN == hSession.headParam.hOpCode || HOpCodeUCenter.CHECK_USER_BY_USER_NAME == hSession.headParam.hOpCode) {
				// 可以通过
				return true;
			} else {
				// 不可以通过
				UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_2, hSession.headParam.hOpCode);
				throw new HttpErrorException(HOpCodeUCenter.UC_ERROR, errorPack);
			}
		}
		Token token = TokenAction.getTokenById(hSession.headParam.token);
		if (token == null) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_2, hSession.headParam.hOpCode);
			throw new HttpErrorException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		Date date = new Date();
		// token过期
		if (date.getTime() > token.getTokenExpireTime().getTime()) {
			TokenAction.deleteToken(token.getTokenId());
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_1, hSession.headParam.hOpCode);
			throw new HttpErrorException(HOpCodeUCenter.UC_ERROR, errorPack);
		} else {
			TokenAction.updateToken(token.getTokenId());
		}
		User user = UserAction.getUserById(token.getUserId());
		if (user == null) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_2, hSession.headParam.hOpCode);
			throw new HttpErrorException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		hSession.otherData = user;
		return true;
	}

}
