package com.vrr.domain.utils;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @LocalServerPort
    public int port;

    @Autowired
    private DatabaseCleanup dataBaseCleanUp;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        dataBaseCleanUp.execute();
    }
}
