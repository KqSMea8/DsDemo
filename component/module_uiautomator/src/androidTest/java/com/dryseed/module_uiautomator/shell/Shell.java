package com.dryseed.module_uiautomator.shell;


import com.dryseed.module_uiautomator.utils.CommandUtils;
import com.dryseed.module_uiautomator.utils.ProcessUtils;
import com.dryseed.module_uiautomator.utils.StreamUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class Shell {

    private final File mBaseDir;

    public Shell(Builder builder) {
        this.mBaseDir = builder.baseDir;
    }

    public String execCommand(String command) throws IOException {
        Process process = getProcess();
        DataOutputStream stream = getShellStream(process);
        stream.writeBytes(command + '\n');
        stream.writeBytes(getExitProcessCommand() + '\n');
        StreamUtils.closeSilently(stream);
        return CommandUtils.executeCommand(process);
    }

    public String execCommands(List<String> commands) throws IOException {
        Process process = getProcess();
        DataOutputStream stream = getShellStream(process);
        for (String command : commands) {
            stream.writeBytes(command + '\n');
        }
        stream.writeBytes(getExitProcessCommand() + '\n');
        StreamUtils.closeSilently(stream);
        return CommandUtils.executeCommand(process);
    }

    protected DataOutputStream getShellStream(Process process) {
        return new DataOutputStream(process.getOutputStream());
    }

    protected Process getProcess() throws IOException {
        return ProcessUtils.execute(getCreateProcessCommand(), null, mBaseDir);
    }

    protected abstract String getExitProcessCommand();

    protected abstract String getCreateProcessCommand();

    public static class Builder {

        private boolean root;
        private File baseDir;

        public Builder setRoot(boolean root) {
            this.root = root;
            return this;
        }

        public Builder setBaseDir(File baseDir) {
            this.baseDir = baseDir;
            return this;
        }

        public Shell build() {
            if (root) {
                return new RootShell(this);
            }
            return new SimpleShell(this);
        }
    }

    static class RootShell extends Shell {

        private RootShell(Builder builder) {
            super(builder);
        }

        @Override
        protected String getExitProcessCommand() {
            return "exit";
        }

        @Override
        protected String getCreateProcessCommand() {
            return "su";
        }
    }

    static class SimpleShell extends Shell {

        private SimpleShell(Builder builder) {
            super(builder);
        }

        @Override
        protected String getExitProcessCommand() {
            return "exit";
        }

        @Override
        protected String getCreateProcessCommand() {
            return "sh";
        }
    }

}
