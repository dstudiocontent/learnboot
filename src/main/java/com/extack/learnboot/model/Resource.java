package com.extack.learnboot.model;

import org.springframework.lang.NonNull;

import java.util.List;

abstract public class Resource {

    @NonNull
    ResourceStatus status;

    public Resource() {
        status = ResourceStatus.CREATED;
    }

    @NonNull
    public ResourceStatus getStatus() {
        return status;
    }

    public enum ResourceStatus {
        CREATED,
        SUCCESS,
        ERROR
    }
}


