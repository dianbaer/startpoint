package org.startpoint.action;

import org.startpoint.http.HOpCodeUCenter;
import org.startpoint.protobuf.http.UCErrorProto.UCError;
import org.startpoint.protobuf.http.UCErrorProto.UCErrorCode;

public class UCErrorPack {
	public static UCError create(UCErrorCode ucErrorCode, String errorHOpCode) {
		UCError.Builder errorBuilder = UCError.newBuilder();
		errorBuilder.setHOpCode(HOpCodeUCenter.UC_ERROR);
		errorBuilder.setErrorCode(ucErrorCode);
		errorBuilder.setErrorHOpCode(errorHOpCode);
		return errorBuilder.build();
	}
}
