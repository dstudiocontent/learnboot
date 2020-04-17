package com.extack.learnboot.model;

import org.springframework.lang.NonNull;

public class SingleResource<T> extends Resource{
    T data;

    public Resource success(@NonNull T data) {
        super.status = Resource.ResourceStatus.SUCCESS;
        this.data = data;
        return this;
    }

    public T getData() {
        return data;
    }
}
