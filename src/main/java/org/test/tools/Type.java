package org.test.tools;

import java.math.BigInteger;

public class Type {
    public enum Types {
        INT_TYPE,
        FLOAT_TYPE,
        STRING_TYPE,
    }

    public static Types getType(String text) {
        if (checkIsFloat(text)) {
            return Types.FLOAT_TYPE;
        } else if (checkIsInteger(text)) {
            return Types.INT_TYPE;
        } else {
            return Types.STRING_TYPE;
        }
    }

    private static boolean checkIsInteger(String str) {
        try {
            new BigInteger(str);
            return true;
        } catch (NumberFormatException ignored) {
        }
        return false;
    }

    private static boolean checkIsFloat(String str) {
        try {
            Float.parseFloat(str);
            if (str.contains("."))
                return true;
        } catch (NumberFormatException ignored) {
        }
        return false;
    }
}
