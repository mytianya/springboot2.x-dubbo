package com.dsys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dsys.service.TestService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Reference(
            version = "${test.service.version}",
            application = "${dubbo.application.id}",
            registry = "${dubbo.registry.id}"
    )
    private TestService testService;
    @GetMapping("/test/{id}")
    public String test(@PathVariable("id")String id){
        return testService.test(id);
    }
}
