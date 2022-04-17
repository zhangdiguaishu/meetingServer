package com.jingwei.utilities;

import java.io.IOException;
import java.io.InputStream;

public class CommandRunner extends Thread{
    private final String meetingIndex;

    public CommandRunner(String idx){
        this.meetingIndex = idx;
    }

    @Override
    public void run() {
        String cmd = "D:\\_environment\\PYTHON\\python.exe D:\\_code\\project_java_meetingServer\\ideaProj\\utility_beamforming\\process_test.py " + meetingIndex;
        System.out.println(cmd);
        Runtime run = Runtime.getRuntime();
        try {
            Process process = run.exec(cmd);
            InputStream inputStream = process.getInputStream();
            while(inputStream.available() > 0){
                System.out.print(inputStream.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
