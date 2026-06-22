package com.quitqecom.exceptions;

public class FileNotFoundException
        extends RuntimeException {

    public FileNotFoundException(
            String message) {

        super(message);
    }
}