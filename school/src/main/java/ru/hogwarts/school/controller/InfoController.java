package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
public class InfoController {

    @GetMapping("/sum")
    public long getSum() {
        return Stream.iterate(1L, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0L, Long::sum);
    }
}