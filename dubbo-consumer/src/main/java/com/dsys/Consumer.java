package com.dsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.dsys")
public class Consumer {
    public static void  main(String args[]){
        SpringApplication.run(Consumer.class,args);
    }
}
