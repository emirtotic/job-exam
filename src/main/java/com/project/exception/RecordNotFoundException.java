package com.project.exception;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(String code) {
        super("Record not found: [" + code + "]");
    }

}

