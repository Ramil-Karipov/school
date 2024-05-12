package ru.hogwarts.school.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/info")
public class InfoController {

    Logger logger = LoggerFactory.getLogger(InfoController.class);


    @Value("${server.port:-1}")
    private int port;


    @GetMapping("port")
    public int getPort() {
        return port;
    }


    @GetMapping("/calculate")
    public int calculate() {
        int sum;
        long t = System.currentTimeMillis();
        sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        logger.info("before optimization: " + (System.currentTimeMillis() - t));

        t = System.currentTimeMillis();
        sum = IntStream.range(1, 10_000_000)
                .parallel()
                .sum();
        logger.info("after optimization: " + (System.currentTimeMillis() - t));

        return sum;

    }
}
