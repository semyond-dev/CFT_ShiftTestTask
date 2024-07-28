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

    private Double minDouble = null;
    private Double maxDouble = null;
    private Double sumDouble = 0.0;
    private Double avgDouble = 0.0;
    private int countDouble = 0;

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

                case DOUBLE_TYPE:
                    if (minDouble == null) minDouble = Double.parseDouble(text);
                    else minDouble = Math.min(minDouble, Double.parseDouble(text));

                    if (maxDouble == null) maxDouble = Double.parseDouble(text);
                    else maxDouble = Math.max(maxDouble, Double.parseDouble(text));

                    sumDouble = sumDouble + Double.parseDouble(text);
                    countDouble++;
                    avgDouble = sumDouble / countDouble;
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

        if (countDouble > 0) {
            System.out.println();
            System.out.println("Doubles type statistic:");
            System.out.println();

            System.out.println("Minimal double value " + minDouble);
            System.out.println("Maximal double value " + maxDouble);
            System.out.println("Sum double value " + sumDouble);
            System.out.println("Avg double value " + avgDouble);
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
