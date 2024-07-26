package org.test.factories;

import org.test.tools.ArgumentsParser;
import org.test.input.FileDataIterator;
import org.test.tools.StringIteratorMerger;
import org.test.interfaces.DataFactoryInterface;

import java.util.Iterator;

public class DataFactory implements DataFactoryInterface {
    static final ArgumentsParser argumentsParser = ArgumentsParser.getInstance();

    @Override
    public Iterator<String> getData() {
        StringIteratorMerger merger = new StringIteratorMerger();

        for (String filepath : argumentsParser.getInputFilePaths()) {
            FileDataIterator iter = new FileDataIterator(filepath);
            merger.addIterator(iter);
        }

        return merger;
    }
}
