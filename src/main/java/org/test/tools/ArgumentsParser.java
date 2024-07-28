package org.test.tools;

import org.apache.commons.cli.*;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class ArgumentsParser {

    private static ArgumentsParser instance;

    private ArgumentsParser() {
    }

    public static ArgumentsParser getInstance() {
        if (instance == null) {
            instance = new ArgumentsParser();
        }
        return instance;
    }

    private String prefix = "";
    private boolean isAppendEnabled = false;
    private boolean isSimpleStatisticsEnabled = false;
    private boolean isFullStatisticsEnabled = false;
    private String[] inputFilePaths = null;

    private String outputFilePath = "";
    private String outputIntegerPath = null;
    private String outputDoublePath = null;
    private String outputStringPath = null;

    private String formatPath(String InPath) {
        if (!InPath.isEmpty()) {
            String result = InPath;

            if (result.endsWith("/") || result.endsWith("\\")) {
                result = result.substring(0, result.length() - 1);
            }

            return result;
        }
        return null;
    }

    public String getOutputPath(String typeName) {
        String result;

        if (outputFilePath.isEmpty())
            result = prefix;
        else
            result = outputFilePath + "/" + prefix;

        result = result + typeName + ".txt";

        return result;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }

    public String getOutputIntegerPath() {
        return outputIntegerPath;
    }

    public String getOutputDoublePath() {
        return outputDoublePath;
    }

    public String getOutputStringPath() {
        return outputStringPath;
    }

    private static Options getOptions() {
        Options options = new Options();

        /* Init program argument list */

        Option appendArgument = new Option("a", "append", false, "If file not empty, append new data");
        options.addOption(appendArgument);

        Option simpleStatisticArgument = new Option("s", "simple-statistic", false, "Simple statistic");
        options.addOption(simpleStatisticArgument);

        Option fullStatisticArgument = new Option("f", "full-statistic", false, "Full statistic");
        options.addOption(fullStatisticArgument);

        Option filenamePrefixArgument = new Option("p", "prefix", true, "Output filename prefix");
        options.addOption(filenamePrefixArgument);

        Option filenameArgument = new Option("o", "output", true, "Output path");
        options.addOption(filenameArgument);
        return options;
    }

    public void parse(String[] args) {
        Options options = getOptions();

        CommandLine cmd;
        CommandLineParser parser = new DefaultParser();

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("a")) {
                isAppendEnabled = true;
            }

            if (cmd.hasOption("s")) {
                isSimpleStatisticsEnabled = true;
            }

            if (cmd.hasOption("f")) {
                isFullStatisticsEnabled = true;
            }

            if (cmd.hasOption("p")) {
                prefix = cmd.getOptionValue("p");
                if (!isValidPath(prefix)) {
                    throw new ParseException("prefix \""+prefix+"\" does not comply with file naming rules");
                }
            }

            if (cmd.hasOption("o")) {
                outputFilePath = formatPath(cmd.getOptionValue("o"));
                if (!isValidPath(outputFilePath)) {
                    throw new ParseException("output path \""+outputFilePath+"\" does not comply with file naming rules");
                }

                if (!Files.exists(Paths.get(outputFilePath))) {
                    throw new ParseException("bad output path \"" + outputFilePath + "\"");
                }
            }

            if (cmd.getArgs().length != 0) {
                inputFilePaths = cmd.getArgs();
                for (String inputFilePath : inputFilePaths) {
                    if (!Files.exists(Paths.get(inputFilePath))) {
                        throw new ParseException("bad input filename \"" + inputFilePath+ "\"");
                    }
                }
            } else {
                throw new ParseException("you must specify at least one input file");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        outputIntegerPath = getOutputPath("integers");
        outputDoublePath = getOutputPath("floats");
        outputStringPath = getOutputPath("strings");

    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isAppendEnabled() {
        return isAppendEnabled;
    }

    public boolean isSimpleStatisticsEnabled() {
        return isSimpleStatisticsEnabled;
    }

    public boolean isFullStatisticsEnabled() {
        return isFullStatisticsEnabled;
    }

    public String[] getInputFilePaths() {
        return inputFilePaths;
    }
}