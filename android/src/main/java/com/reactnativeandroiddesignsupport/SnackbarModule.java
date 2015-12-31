package com.reactnativeandroiddesignsupport;

import android.content.Context;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import java.util.HashMap;
import java.util.Map;

import android.support.design.widget.Snackbar;

public class SnackbarModule extends ReactContextBaseJavaModule {
  public Activity mActivity = null;
  public Context mContext = null;

  @Override
  public String getName() {
    return "SnackbarAndroid";
  }

  public SnackbarModule(ReactApplicationContext reactContext, Activity activity) {
    super(reactContext);
    this.mContext = reactContext;
    this.mActivity = activity;
  }

  @ReactMethod
  public void show(String text, int duration, String action, String payload, Callback callback) {
    final String fPayload = payload;
    final Callback fCallback = callback;

    if (action == "") {
      Snackbar
        .make(mActivity.findViewById(android.R.id.content), text, duration)
        .show();
    } else {
      Snackbar
        .make(mActivity.findViewById(android.R.id.content), text, duration)
        .setAction(action, new OnClickListener() {
          @Override
          public void onClick(View v) {
            fCallback.invoke(fPayload);
          }})
        .show();
    }
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put("SHORT", Snackbar.LENGTH_SHORT);
    constants.put("LONG", Snackbar.LENGTH_LONG);
    return constants;
  }
}
