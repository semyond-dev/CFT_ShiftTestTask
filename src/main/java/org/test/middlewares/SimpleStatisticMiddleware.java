package org.test.middlewares;

import org.test.interfaces.StatisticsInterface;
import org.test.tools.ArgumentsParser;
import org.test.tools.Type;

import java.util.HashMap;


public class SimpleStatisticMiddleware extends StringMiddleware implements StatisticsInterface {
    private static final ArgumentsParser argumentsParser = ArgumentsParser.getInstance();
    private static final HashMap<Type.Types, Integer> typesCount = new HashMap<>();

    public static SimpleStatisticMiddleware instance = null;

    private SimpleStatisticMiddleware() {}

    public static SimpleStatisticMiddleware getInstance() {
        if (instance == null) {
            instance = new SimpleStatisticMiddleware();
        }
        return instance;
    }

    @Override
    public boolean check(String text)  {
        Type.Types type = Type.getType(text);
        if (argumentsParser.isSimpleStatisticsEnabled()) {
            if (!typesCount.containsKey(type)) {
                typesCount.put(type, 1);
                return checkNext(text);
            }
            typesCount.put(type, typesCount.get(type) + 1);
        }
        return checkNext(text);
    }

    public static int getIntegersCount() {
        if (typesCount.containsKey(Type.Types.INT_TYPE)) {
            return typesCount.get(Type.Types.INT_TYPE);
        }
        return 0;
    }

    public static int getDoublesCount() {
        if (typesCount.containsKey(Type.Types.DOUBLE_TYPE)) {
            return typesCount.get(Type.Types.DOUBLE_TYPE);
        }
        return 0;
    }

    public static int getStringsCount() {
        if (typesCount.containsKey(Type.Types.STRING_TYPE)) {
            return typesCount.get(Type.Types.STRING_TYPE);
        }
        return 0;
    }

    @Override
    public void printStatistics() {
        System.out.println();
        System.out.println("Total strings sorted: " + getStringsCount());
        System.out.println("Total integers sorted: " + getIntegersCount());
        System.out.println("Total doubles sorted: " + getDoublesCount());
        System.out.println();
    }
}
