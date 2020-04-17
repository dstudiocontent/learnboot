package com.extack.learnboot.model;

import org.springframework.lang.NonNull;

import java.util.List;

public class ListResource<T> extends Resource{
    List<T> data;

    public Resource success(@NonNull List<T> data) {
        super.status = ResourceStatus.SUCCESS;
        this.data = data;
        return this;
    }

    public List<T> getData() {
        return data;
    }
}