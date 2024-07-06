package org.example;


import java.io.File;
import java.nio.file.Path;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;


/**
 * CliRequest
 */
public class CliRequest {
    String[] args;

    CommandLine commandLine;



    String workingDirectory;

    File multiModuleProjectDirectory;

    Path rootDirectory;

    Path topDirectory;

    boolean verbose;

    boolean quiet;

    boolean showErrors = true;

    Properties userProperties = new Properties();

    Properties systemProperties = new Properties();



    public String[] getArgs() {
        return args;
    }

    public CommandLine getCommandLine() {
        return commandLine;
    }


    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public File getMultiModuleProjectDirectory() {
        return multiModuleProjectDirectory;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public boolean isQuiet() {
        return quiet;
    }

    public boolean isShowErrors() {
        return showErrors;
    }

    public Properties getUserProperties() {
        return userProperties;
    }

    public Properties getSystemProperties() {
        return systemProperties;
    }


    public void setUserProperties(Properties properties) {
        this.userProperties.putAll(properties);
    }

    public Path getRootDirectory() {
        return rootDirectory;
    }

    public Path getTopDirectory() {
        return topDirectory;
    }
}
