package org.test.storages;

import org.test.tools.ArgumentsParser;
import org.test.interfaces.StorageInterface;

import java.io.*;
import java.math.BigInteger;

public class FileStorage implements StorageInterface {
    private static FileStorage instance;
    private static final ArgumentsParser argumentsParser = ArgumentsParser.getInstance();
    private static File doubles_file;
    private static File integers_file;
    private static File strings_file;

    private FileStorage() {
    }

    public static FileStorage getInstance() {
        if (instance == null) {
            instance = new FileStorage();
        }
        return instance;
    }

    @Override
    public void init() {
        if (doubles_file == null) {
            doubles_file = new File(argumentsParser.getOutputDoublePath());
            if (doubles_file.exists() && !argumentsParser.isAppendEnabled()) {
                cleanFile(doubles_file);
            }
        }

        if (integers_file == null) {
            integers_file = new File(argumentsParser.getOutputIntegerPath());
            if (integers_file.exists() && !argumentsParser.isAppendEnabled()) {
                cleanFile(integers_file);
            }
        }

        if (strings_file == null) {
            strings_file = new File(argumentsParser.getOutputStringPath());
            if (strings_file.exists() && !argumentsParser.isAppendEnabled()) {
                cleanFile(strings_file);
            }
        }
    }

    @Override
    public void storeString(String value) {
        appendStringToFile(value, strings_file);
    }

    @Override
    public void storeInteger(BigInteger value) {
        appendStringToFile(value.toString(), integers_file);
    }

    @Override
    public void storeDouble(Double value) {
        appendStringToFile(value.toString(), doubles_file);
    }

    private void appendStringToFile(String stringToAppend, File file) {
        try (FileWriter fileWriter = new FileWriter(file, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(stringToAppend + "\n");
        } catch (IOException e) {
            System.out.println("can't append string to file");
            System.out.println(e.getMessage());
        }
    }

    private void cleanFile(File file) {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.write("");
        } catch (FileNotFoundException e) {
            System.out.println("file \"" + file.getName() + "\" not found");
            System.out.println(e.getMessage());
        }
    }

}
