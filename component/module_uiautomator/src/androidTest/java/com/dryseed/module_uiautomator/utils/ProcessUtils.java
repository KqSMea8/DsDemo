package com.dryseed.module_uiautomator.utils;

import java.io.*;

public class ProcessUtils {

    public static void waitForOrKill(Process command, long numberOfMillis) {
        ProcessUtils.ProcessRunner runnable = new ProcessUtils.ProcessRunner(command);
        Thread thread = new Thread(runnable);
        thread.start();
        runnable.waitForOrKill(numberOfMillis);
    }

    public static void closeStreams(Process command) {
        try {
            command.getErrorStream().close();
        } catch (IOException e) {
            // ignore
        }

        try {
            command.getInputStream().close();
        } catch (IOException e) {
            // ignore
        }

        try {
            command.getOutputStream().close();
        } catch (IOException e) {
            // ignore
        }

    }

    public static void consumeProcessOutput(Process command) {
        consumeProcessOutput(command, (OutputStream) null, (OutputStream) null);
    }

    public static void consumeProcessOutput(Process command, Appendable output, Appendable error) {
        consumeProcessOutputStream(command, output);
        consumeProcessErrorStream(command, error);
    }

    public static void consumeProcessOutput(Process command, OutputStream output, OutputStream error) {
        consumeProcessOutputStream(command, output);
        consumeProcessErrorStream(command, error);
    }

    public static void waitForProcessOutput(Process command) {
        waitForProcessOutput(command, (OutputStream) null, (OutputStream) null);
    }

    public static void waitForProcessOutput(Process command, Appendable output, Appendable error) {
        Thread tout = consumeProcessOutputStream(command, output);
        Thread terr = consumeProcessErrorStream(command, error);

        try {
            tout.join();
        } catch (InterruptedException e) {
            // ignore
        }

        try {
            terr.join();
        } catch (InterruptedException e) {
            // ignore
        }

        try {
            command.waitFor();
        } catch (InterruptedException e) {
            // ignore
        }

        closeStreams(command);
    }

    public static void waitForProcessOutput(Process command, OutputStream output, OutputStream error) {
        Thread tout = consumeProcessOutputStream(command, output);
        Thread terr = consumeProcessErrorStream(command, error);

        try {
            tout.join();
        } catch (InterruptedException e) {
            // ignore
        }

        try {
            terr.join();
        } catch (InterruptedException e) {
            // ignore
        }

        try {
            command.waitFor();
        } catch (InterruptedException e) {
            // ignore
        }

        closeStreams(command);
    }

    public static Thread consumeProcessErrorStream(Process command, OutputStream err) {
        Thread thread = new Thread(new ProcessUtils.ByteDumper(command.getErrorStream(), err));
        thread.start();
        return thread;
    }

    public static Thread consumeProcessErrorStream(Process command, Appendable error) {
        Thread thread = new Thread(new ProcessUtils.TextDumper(command.getErrorStream(), error));
        thread.start();
        return thread;
    }

    public static Thread consumeProcessOutputStream(Process command, Appendable output) {
        Thread thread = new Thread(new ProcessUtils.TextDumper(command.getInputStream(), output));
        thread.start();
        return thread;
    }

    public static Thread consumeProcessOutputStream(Process command, OutputStream output) {
        Thread thread = new Thread(new ProcessUtils.ByteDumper(command.getInputStream(), output));
        thread.start();
        return thread;
    }

    public static Process execute(String command) throws IOException {
        return Runtime.getRuntime().exec(command);
    }

    public static Process execute(String command, String[] envp, File dir) throws IOException {
        return Runtime.getRuntime().exec(command, envp, dir);
    }

    public static Process execute(String[] commandArray) throws IOException {
        return Runtime.getRuntime().exec(commandArray);
    }

    public static Process execute(String[] commandArray, String[] envp, File dir) throws IOException {
        return Runtime.getRuntime().exec(commandArray, envp, dir);
    }

    private static class ByteDumper implements Runnable {
        InputStream in;
        OutputStream out;

        public ByteDumper(InputStream in, OutputStream out) {
            this.in = new BufferedInputStream(in);
            this.out = out;
        }

        public void run() {
            byte[] buf = new byte[8192];

            try {
                int next;
                while ((next = this.in.read(buf)) != -1) {
                    if (this.out != null) {
                        this.out.write(buf, 0, next);
                    }
                }
                this.out.flush();
            } catch (IOException e) {
                throw new RuntimeException("exception while dumping process stream", e);
            }
        }
    }

    private static class TextDumper implements Runnable {
        InputStream in;
        Appendable app;

        public TextDumper(InputStream in, Appendable app) {
            this.in = in;
            this.app = app;
        }

        public void run() {
            InputStreamReader isr = new InputStreamReader(this.in);
            BufferedReader br = new BufferedReader(isr);

            try {
                String next;
                while ((next = br.readLine()) != null) {
                    if (this.app != null) {
                        this.app.append(next);
                        this.app.append("\n");
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException("exception while reading process stream", e);
            }
        }
    }

    protected static class ProcessRunner implements Runnable {
        Process process;
        private boolean finished;

        public ProcessRunner(Process process) {
            this.process = process;
        }

        private void doProcessWait() {
            try {
                this.process.waitFor();
            } catch (InterruptedException e) {
                // ignore
            }

        }

        public void run() {
            this.doProcessWait();
            synchronized (this) {
                this.notifyAll();
                this.finished = true;
            }
        }

        public synchronized void waitForOrKill(long millis) {
            if (!this.finished) {
                try {
                    this.wait(millis);
                } catch (InterruptedException e) {
                    // ignore
                }

                if (!this.finished) {
                    this.process.destroy();
                    this.doProcessWait();
                }
            }

        }
    }

}
