package com.extack.learnboot.model;

import org.springframework.lang.NonNull;

public class ErrorResource<T> extends Resource{
    String errorMessage;

    public Resource error(@NonNull String errorMessage) {
        this.status = ResourceStatus.ERROR;
        this.errorMessage = errorMessage;
        return this;
    }

    public String getError() {
        return errorMessage;
    }
}