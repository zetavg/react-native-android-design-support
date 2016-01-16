package com.reactnativeandroiddesignsupport;
/**
 * This file is copied from https://goo.gl/cP1YU3, only replacing ScrollView to
 * NestedScrollView.
 */

/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

// EDITED: 1. Import additional classes
import com.facebook.react.views.scroll.ScrollEvent;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;

/**
 * Helper class that deals with emitting Scroll Events.
 */
// EDITED: 1. Rename the class
public class ReactNestedScrollViewHelper {
  /**
   * Shared by {@link ReactScrollView} and {@link ReactHorizontalScrollView}.
   */
  public static void emitScrollEvent(ViewGroup scrollView, int scrollX, int scrollY) {
    View contentView = scrollView.getChildAt(0);
    ReactContext reactContext = (ReactContext) scrollView.getContext();
    reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher().dispatchEvent(
        ScrollEvent.obtain(
            scrollView.getId(),
            SystemClock.uptimeMillis(),
            scrollX,
            scrollY,
            contentView.getWidth(),
            contentView.getHeight(),
            scrollView.getWidth(),
            scrollView.getHeight()));
  }
}
