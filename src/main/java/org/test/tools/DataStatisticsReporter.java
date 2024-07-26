package org.test.tools;

import org.test.middlewares.FullStatisticMiddleware;
import org.test.middlewares.SimpleStatisticMiddleware;

public class DataStatisticsReporter {
    private static final ArgumentsParser argumentsParser = ArgumentsParser.getInstance();
    private static final SimpleStatisticMiddleware simpleStatisticMiddleware = SimpleStatisticMiddleware.getInstance();
    private static final FullStatisticMiddleware fullStatisticMiddleware = FullStatisticMiddleware.getInstance();

    public static void printStatistics() {
        if (argumentsParser.isSimpleStatisticsEnabled()) {
            simpleStatisticMiddleware.printStatistics();
        }
        if (argumentsParser.isFullStatisticsEnabled()) {
            fullStatisticMiddleware.printStatistics();
        }
    }
}
