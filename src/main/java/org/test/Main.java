package org.test;

import org.test.factories.DataFactory;
import org.test.middlewares.FullStatisticMiddleware;
import org.test.middlewares.ResultStorageMiddleware;
import org.test.middlewares.SimpleStatisticMiddleware;
import org.test.middlewares.StringMiddleware;
import org.test.tools.ArgumentsParser;
import org.test.tools.DataStatisticsReporter;

import java.util.Iterator;

public class Main {
    static final ArgumentsParser argumentsParser = ArgumentsParser.getInstance();

    public static void main(String[] args) {
        argumentsParser.parse(args);

        DataFactory factory = new DataFactory();
        Iterator<String> data = factory.getData();

        StringMiddleware middleware = StringMiddleware.link(
                SimpleStatisticMiddleware.getInstance(),
                FullStatisticMiddleware.getInstance(),
                new ResultStorageMiddleware()
        );

        while (data.hasNext()) {
            String text = data.next();
            middleware.check(text);
        }

        DataStatisticsReporter.printStatistics();
    }
}