package com.jingwei.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class testCtl {
    @PostMapping("/testPost")
    public void post(@RequestBody String str) throws UnsupportedEncodingException {
        System.out.println(str);
        String result = java.net.URLDecoder.decode(str, "utf-8");
        System.out.println(result);
        return;
    }

}
