package org.test.middlewares;

import org.test.interfaces.StatisticsInterface;
import org.test.tools.ArgumentsParser;
import org.test.tools.Type;

import java.math.BigInteger;


public class FullStatisticMiddleware extends StringMiddleware implements StatisticsInterface {
    private final static ArgumentsParser argumentsParser = ArgumentsParser.getInstance();
    private static FullStatisticMiddleware instance;

    private FullStatisticMiddleware() {
    }

    public static FullStatisticMiddleware getInstance() {
        if (instance == null) {
            instance = new FullStatisticMiddleware();
        }
        return instance;
    }

    private BigInteger minInt = null;
    private BigInteger maxInt = null;
    private BigInteger sumInt = new BigInteger("0");
    private BigInteger avgInt = new BigInteger("0");
    private int countInt = 0;

    private Float minFloat = null;
    private Float maxFloat = null;
    private Float sumFloat = 0.0F;
    private Float avgFloat = 0.0F;
    private int countFloat = 0;

    private Integer minLength = null;
    private Integer maxLength = null;
    private int countStrings = 0;

    @Override
    public boolean check(String text) {
        Type.Types type = Type.getType(text);
        if (argumentsParser.isFullStatisticsEnabled()) {
            switch (type) {
                case INT_TYPE:
                    BigInteger converted = new BigInteger(text);

                    if (minInt == null) minInt = converted;
                    else minInt = minInt.min(converted);

                    if (maxInt == null) maxInt = converted;
                    else maxInt = maxInt.max(converted);

                    sumInt = sumInt.add(converted);
                    countInt++;
                    avgInt = sumInt.divide(BigInteger.valueOf(countInt));
                    break;

                case FLOAT_TYPE:
                    if (minFloat == null) minFloat = Float.parseFloat(text);
                    else minFloat = Math.min(minFloat, Float.parseFloat(text));

                    if (maxFloat == null) maxFloat = Float.parseFloat(text);
                    else maxFloat = Math.max(maxFloat, Float.parseFloat(text));

                    sumFloat = sumFloat + Float.parseFloat(text);
                    countFloat++;
                    avgFloat = sumFloat / countFloat;
                    break;

                case STRING_TYPE:
                    int length = text.length();
                    countStrings++;

                    if (minLength == null)
                        minLength = length;
                    else
                        minLength = Math.min(minLength, length);

                    if (maxLength == null)
                        maxLength = length;
                    else
                        maxLength = Math.max(maxLength, length);
                    break;

            }
        }
        return checkNext(text);
    }

    @Override
    public void printStatistics() {
        if (countInt > 0) {
            System.out.println();
            System.out.println("Integers type statistic:");
            System.out.println();

            System.out.println("Minimal int value " + minInt);
            System.out.println("Maximal int value " + maxInt);
            System.out.println("Sum int value " + sumInt);
            System.out.println("Avg int value " + avgInt);
            System.out.println();
        }

        if (countFloat > 0) {
            System.out.println();
            System.out.println("Floats type statistic:");
            System.out.println();

            System.out.println("Minimal float value " + minFloat);
            System.out.println("Maximal float value " + maxFloat);
            System.out.println("Sum float value " + sumFloat);
            System.out.println("Avg float value " + avgFloat);
            System.out.println();
        }

        if (countStrings > 0) {
            System.out.println();
            System.out.println("Strings type statistic:");
            System.out.println();

            System.out.println("Minimal string length " + minLength);
            System.out.println("Maximal string length " + maxLength);
            System.out.println("Strings count " + countStrings);
            System.out.println();
        }

    }
}
