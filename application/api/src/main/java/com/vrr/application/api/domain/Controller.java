package com.vrr.application.api.domain;

import com.vrr.application.api.global.auth.annotation.CurrentUserSerial;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/api/2")
    public String sh2() {
        return "hello222222";
    }

    @GetMapping("/api/1")
    public String sh(@CurrentUserSerial String serial) {
        return serial;
    }

}
