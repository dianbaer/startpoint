package action;

import http.HOpCodeUCenter;
import protobuf.http.UCErrorProto.UCError;
import protobuf.http.UCErrorProto.UCErrorCode;

public class UCErrorPack {
	public static UCError create(UCErrorCode ucErrorCode, String errorHOpCode) {
		UCError.Builder errorBuilder = UCError.newBuilder();
		errorBuilder.setHOpCode(HOpCodeUCenter.UC_ERROR);
		errorBuilder.setErrorCode(ucErrorCode);
		errorBuilder.setErrorHOpCode(errorHOpCode);
		return errorBuilder.build();
	}
}
