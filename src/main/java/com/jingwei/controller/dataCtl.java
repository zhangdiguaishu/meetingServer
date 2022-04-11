package com.jingwei.controller;

import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
public class dataCtl {
    //java类来表示
    //读取配置文件之中的值，可以使用value或者env来
    //或者使用configuration配置，但是需要提供get&set

    //@RequestMapping(value = "/testGet", method = RequestMethod.GET)
    //根据get的id不同来获取不同的数据
    //PathVariable，路径上的变量
    //RequestHeader，请求头的内容
    //RequestBody，请求体的内容（只有post才有请求体）
    @GetMapping("/info")
    public String meetingBreif(){
        return "none";
    }

    //test
    @PostMapping("/submit")
    public void post(@RequestBody String str) throws UnsupportedEncodingException {
    }

    public static String readJsonFile(String fileName) {
        return "none";
    }

    public static void updateJsonFile(String filePath, String updateStr){

    }
}
