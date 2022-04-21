package com.jingwei.utilities;

import java.io.IOException;
import java.io.InputStream;

public class CommandRunner extends Thread{
    private final String cmd;

    public CommandRunner(String cmd){
        this.cmd = cmd;
    }

    @Override
    public void run() {
        Runtime run = Runtime.getRuntime();
        try {
            System.out.println("Run cmd:" + cmd);
            Process process = run.exec(cmd);
            process.waitFor();
            System.out.println("******************done******************");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
