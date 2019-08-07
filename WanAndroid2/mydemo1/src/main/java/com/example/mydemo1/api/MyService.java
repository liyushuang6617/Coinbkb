package com.example.mydemo1.api;

import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.ListData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface MyService {

    @GET
    Observable<BaseResponse<List<ListData>>> get(@Url String url);
}
