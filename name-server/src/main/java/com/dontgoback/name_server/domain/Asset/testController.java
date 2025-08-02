package com.dontgoback.name_server.domain.Asset;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/test")
@RestController
public class testController {
    @GetMapping("/{testId}")
    public String testRestApi(@PathVariable("testId") Long testId){
        return "Hello World" + testId;
    }

}
