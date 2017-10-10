package keylock;

public class UCenterKeyLockType implements IKeyLockType {
	public static String USER_GROUP = "USER_GROUP";

	@Override
	public String[] getkeyLockType() {
		return new String[] { USER_GROUP };
	}

}
