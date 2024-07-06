package org.example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

public class Main {
    static CLIManager cliManager = new CLIManager();
    public static void main(String[] args) throws ParseException {


        CliRequest cliRequest = new CliRequest();
        CommandLine config = null;

        try {
            config = cliManager.parse(args);
            cliRequest.commandLine = config;
        } catch(ParseException e) {
            System.err.println("Unable to parse command line options: " + e.getMessage());
            cliManager.displayHelp(System.out);
            throw e;
        }

        try {
            informativeCommands(cliRequest);
        }
        catch (ExitException e) {
            System.out.println("Process Completed Normally.");
            return;
        }

        if (cliRequest.commandLine.hasOption(CLIManager.ALTERNATE_POM_FILE)) {
            System.out.println("Alternate Pom File :  " + cliRequest.commandLine.getOptionValue(CLIManager.ALTERNATE_POM_FILE));

        }

        System.out.println("Hello world!");
    }

    private static void informativeCommands(CliRequest cliRequest) throws ExitException {
        if (cliRequest.commandLine.hasOption(CLIManager.HELP)) {
            cliManager.displayHelp(System.out);
            throw new ExitException(0);
        }

        if (cliRequest.commandLine.hasOption(CLIManager.VERSION)) {
            if (cliRequest.commandLine.hasOption(CLIManager.QUIET)) {
                System.out.println("1.0");
            } else {
                System.out.println("1.0.0");
            }
            throw new ExitException(0);
        }


    }



    static class ExitException extends Exception {
        int exitCode;

        ExitException(int exitCode) {
            this.exitCode = exitCode;
        }
    }


}