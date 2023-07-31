package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.LongStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Value(value = "${server.port}")
    private int port;

    public InfoController() {
    }

    @GetMapping("/getPort")
    int getPort(){
        return port;
    }

    @GetMapping("/calculation-time-test")
    public String getTimeTest1(){
        long startTime = System.currentTimeMillis();

        long sum = LongStream
                .iterate(1, a -> a + 1)
                .limit(1_000_000_0)
                .filter(i -> i % 2 == 0)
                .map(e -> (long) (e * 1.23))
                .filter(i -> i % 3 == 0)
                .sum();

        long finishTime = System.currentTimeMillis();

        return "Time " + (finishTime-startTime) + " mc, sum = " + sum;
    }
    @GetMapping("/calculation-time-test-parallel-streams")
    public String getTimeTest2(){
        long startTime = System.currentTimeMillis();

        long sum = LongStream
                .iterate(1, a -> a + 1)
                .limit(1_000_000_0)
                .parallel()
                .filter(i -> i % 2 == 0)
                .map(e -> (long) (e * 1.23))
                .filter(i -> i % 3 == 0)
                .sum();

        long finishTime = System.currentTimeMillis();

        return "Time " + (finishTime-startTime) + " mc, sum = " + sum;
    }

}
