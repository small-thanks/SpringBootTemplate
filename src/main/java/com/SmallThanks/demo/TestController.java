package com.SmallThanks.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class TestController {

    @GetMapping("/t1")
    public HashMap t1(){
        HashMap<String, Integer> jsonObject = new HashMap<>();
        jsonObject.put("1",2);
        jsonObject.put("3",3);
        jsonObject.put("4",4);
        return jsonObject;
    }
}
