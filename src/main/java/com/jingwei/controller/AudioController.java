package com.jingwei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@Controller
public class AudioController {
    @GetMapping("/audios")
    public void getAudio(@RequestParam("meetingIndex")String meetingIndex, @RequestParam("audioType")int audioType, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tgtPath = "D:\\_code\\project_java_meetingServer\\ideaProj\\processedRecords\\" + meetingIndex + "\\";

        if(audioType == 0) tgtPath += "0.wav";
        else if(audioType == 1) tgtPath += "1.wav";
        else tgtPath += "2.wav";

        File file = ResourceUtils.getFile(tgtPath);
        FileInputStream fileInputStream = new FileInputStream(file);
        response.addHeader("Content-Type", "audio/mpeg;charset=UTF-8");
        response.addHeader("Content-Length", String.valueOf(file.length()));

        ServletOutputStream servletOutputStream= response.getOutputStream();
        int len;
        byte b[] = new byte[2048];
        while((len = fileInputStream.read(b)) != -1){
            servletOutputStream.write(b, 0, len);
        }

        fileInputStream.close();
        servletOutputStream.close();
    }
}
