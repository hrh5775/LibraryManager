package team2.database_wrapper.helper;

public class TypeHelper {
    public static boolean toBoolean(byte value) {
        return value >= 1;
    }

    public static byte toByte(boolean value) {
        return (byte) (value ? 1 : 0);
    }
}
