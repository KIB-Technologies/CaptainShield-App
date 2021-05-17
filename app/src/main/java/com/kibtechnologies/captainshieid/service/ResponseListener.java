package com.kibtechnologies.captainshieid.service;

import com.kibtechnologies.captainshieid.UserDataResponse;

import java.io.Reader;

/**
 * Created by Khushboo Jha on 5/16/21.
 */

public interface ResponseListener {

  void startProgress();

  void onResponse(UserDataResponse respReader, int requestCode);

  void stopProgress();

  void onError(Throwable t, Object... args);
}
