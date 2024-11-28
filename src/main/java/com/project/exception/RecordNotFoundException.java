package com.project.exception;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(Long id) {
        super("Record not found: [" + id + "]");
    } // TODO: etotic

    public RecordNotFoundException(String code) {
        super("Record not found: [" + code + "]");
    }

}

