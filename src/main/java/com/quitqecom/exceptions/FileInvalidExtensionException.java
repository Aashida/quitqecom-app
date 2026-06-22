package com.quitqecom.exceptions;

public class FileInvalidExtensionException
        extends RuntimeException {

    public FileInvalidExtensionException(
            String message) {

        super(message);
    }
}