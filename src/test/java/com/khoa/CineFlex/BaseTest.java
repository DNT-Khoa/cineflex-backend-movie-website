package com.khoa.CineFlex;

import org.testcontainers.containers.MySQLContainer;

public class BaseTest {
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest")
            .withDatabaseName("cineflex-test")
            .withUsername("khoa")
            .withPassword("12345");

    static {
        mySQLContainer.start();
    }
}
