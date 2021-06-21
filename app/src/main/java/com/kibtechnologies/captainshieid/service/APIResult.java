package com.kibtechnologies.captainshieid.service;

import androidx.annotation.Nullable;

public class APIResult<T> {
  @Nullable
  private T request_code;
  @Nullable
  private Throwable error;

  public APIResult(@Nullable T request_code) {
    this.request_code = request_code;
  }

  public APIResult(@Nullable Throwable error) {
    this.error = error;
  }

  @Nullable
  public Throwable getError() {
    return error;
  }

  @Nullable
  public T getSuccess() {
    return request_code;
  }
}
