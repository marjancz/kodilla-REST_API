package com.crud.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestClass {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestClass.class);

    public static void main(String[] args) {
        LOGGER.info("Starting my test application!!!");
        LOGGER.warn("There is missing field in my configuration!!!");

        try {
            throw new Exception("Configuration is not provided!!!");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}