package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
// Only loads beans of the web layer // Don't star a real servlet container

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Loads all beans and starts a real servlet container in a random port

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
// Loads all beans and starts a real servlet container in a defined port on aplication.properties

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    //properties = {"server.port=8881", "hostname=192.168.8.2"})

// Loads all beans and starts a real servlet container in a specified port that substitutes the port on aplication.properties
// @TestPropertySource(locations = "/application-test.properties") // Load properties from a file
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Highter priority than others configurations. Uses any random disponible port.
public class DefiningPortNumberAndPropertiesTest {

    @Value("${server.port}")
    private int serverPort;

    @LocalServerPort
    private int localServerPort;

    @Test
    public void test() {
        System.out.println("server.port=" + serverPort);
        System.out.println("server.port=" + localServerPort);
    }

}
