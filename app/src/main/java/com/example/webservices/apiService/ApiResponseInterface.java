package com.example.webservices.apiService;

public interface ApiResponseInterface<T> {
    void onResponse(T model);
    void onError(Exception e);
}
