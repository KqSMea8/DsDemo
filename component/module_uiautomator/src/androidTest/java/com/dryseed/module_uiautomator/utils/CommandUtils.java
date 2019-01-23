package com.dryseed.module_uiautomator.utils;

import android.util.Log;

import javax.annotation.Nullable;
import java.io.*;

public class CommandUtils {

    private static final String TAG = "CommandUtils";

    public static String executeCommand(String command) throws IOException {
        return executeCommand(ProcessUtils.execute(command));
    }

    public static String executeCommand(String command, @Nullable File rootDir) throws IOException {
        return executeCommand(ProcessUtils.execute(command, null, rootDir));
    }

    public static void executeCommand(String command, @Nullable File rootDir, File outputFile) throws IOException {
        executeCommand(ProcessUtils.execute(command, null, rootDir),
                new BufferedOutputStream(new FileOutputStream(outputFile)), System.err);
    }

    public static void executeCommand(String command, @Nullable File rootDir, OutputStream outputStream) throws IOException {
        executeCommand(ProcessUtils.execute(command, null, rootDir),
                new BufferedOutputStream(outputStream), System.err);
    }

    public static void executeCommand(String command, @Nullable File rootDir,
                                      File outputFile, File errorFile) throws IOException {
        executeCommand(ProcessUtils.execute(command, null, rootDir),
                new BufferedOutputStream(new FileOutputStream(outputFile)),
                new BufferedOutputStream(new FileOutputStream(errorFile)));
    }

    public static void executeCommand(String command, @Nullable File rootDir,
                                      OutputStream outputStream, OutputStream errorStream) throws IOException {
        executeCommand(ProcessUtils.execute(command, null, rootDir),
                new BufferedOutputStream(outputStream), new BufferedOutputStream(errorStream));
    }

    public static String executeCommand(String[] command) throws IOException {
        return executeCommand(ProcessUtils.execute(command));
    }

    public static String executeCommand(String[] command, @Nullable File rootDir) throws IOException {
        return executeCommand(ProcessUtils.execute(command, null, rootDir));
    }

    public static void executeCommand(String[] command, @Nullable File rootDir, File outputFile) throws IOException {
        executeCommand(ProcessUtils.execute(command, null, rootDir),
                new BufferedOutputStream(new FileOutputStream(outputFile)), System.err);
    }

    public static void executeCommand(String[] command, @Nullable File rootDir, OutputStream outputStream) throws IOException {
        executeCommand(ProcessUtils.execute(command, null, rootDir),
                new BufferedOutputStream(outputStream), System.err);
    }

    public static void executeCommand(String[] command, @Nullable File rootDir,
                                      File outputFile, File errorFile) throws IOException {
        executeCommand(ProcessUtils.execute(command, null, rootDir),
                new BufferedOutputStream(new FileOutputStream(outputFile)),
                new BufferedOutputStream(new FileOutputStream(errorFile)));
    }

    public static void executeCommand(String[] command, @Nullable File rootDir,
                                      OutputStream outputStream, OutputStream errorStream) throws IOException {
        executeCommand(ProcessUtils.execute(command, null, rootDir),
                new BufferedOutputStream(outputStream), new BufferedOutputStream(errorStream));
    }

    public static void executeCommand(Process process, OutputStream output, OutputStream error) {
        ProcessUtils.waitForProcessOutput(process, output, error);
        StreamUtils.closeSilently(output);
        StreamUtils.closeSilently(error);
        int code = process.exitValue();
        if (code != 0) {
            throw new RuntimeException("ExitValue:" + code + " Error: " + error.toString());
        }
    }

    public static String executeCommand(Process process) {
        StringBuffer result = new StringBuffer();
        StringBuffer error = new StringBuffer();
        ProcessUtils.waitForProcessOutput(process, result, error);
        if (!CommonUtils.isEmpty(error)) {
            Log.e(TAG, error.toString());
        }
        int code = process.exitValue();
        if (code != 0) {
            throw new RuntimeException("ExitValue:" + code + " Error: " + error.toString());
        }
        return result.toString().trim();
    }

}
