package com.kibtechnologies.captainshieid.service;

import android.util.Log;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class APIInterceptor implements Interceptor {
  private String token;

  public APIInterceptor(String token) {
    this.token = token;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
      Request request = chain.request();
      //Log.i("Authorization", token);
      if (token != null) {
        request = request.newBuilder()
            .addHeader("Authorization", token)
            .build();
      }
      Log.e("Response", request.toString());
      Response response = null;
      try {
        response = chain.proceed(request);
        if (response.code() != 200) {
          response = response.newBuilder().code(200).build();
        }
      }
      catch (SocketTimeoutException exception) {
        exception.printStackTrace();
      }
    return response;

  }
}
