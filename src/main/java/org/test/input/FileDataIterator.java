package org.test.input;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.io.File;
import java.util.Scanner;

public class FileDataIterator implements Iterator<String> {
    private final Scanner scanner;

    public FileDataIterator(String filepath) {
        if (filepath == null) {
            throw new NullPointerException("filepath is null");
        }
        try {
            File file = new File(filepath);
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasNext() {
        return scanner.hasNextLine();
    }

    @Override
    public String next() {
        return scanner.nextLine();
    }
}
