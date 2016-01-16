package com.reactnativeandroiddesignsupport;

/**
 * This file is copied from https://goo.gl/wjDv5i, only with some additional
 * edits.
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
import com.facebook.react.views.scroll.OnScrollDispatchHelper;
import com.facebook.react.views.scroll.ReactHorizontalScrollView;
import com.facebook.react.views.scroll.ReactHorizontalScrollViewManager;
import com.facebook.react.views.scroll.ReactScrollView;
import com.facebook.react.views.scroll.ReactScrollViewCommandHelper;
import com.facebook.react.views.scroll.ReactScrollViewHelper;
import com.facebook.react.views.scroll.ReactScrollViewManager;
import com.facebook.react.views.scroll.ScrollEvent;
import android.view.ViewGroup;
import android.support.v4.widget.NestedScrollView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.AppBarLayout;

import javax.annotation.Nullable;
import java.util.Map;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ReactProp;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.views.view.ReactClippingViewGroupHelper;
/**
 * View manager for {@link ReactScrollView} components.
 *
 * <p>Note that {@link ReactScrollView} and {@link ReactHorizontalScrollView} are exposed to JS
 * as a single ScrollView component, configured via the {@code horizontal} boolean property.
 */

// EDITED: 2. Repalce all ReactScrollView in this file with
// ReactNestedScrollView
public class ReactNestedScrollViewManager
    extends ViewGroupManager<ReactNestedScrollView>
    implements ReactScrollViewCommandHelper.ScrollCommandHandler<ReactNestedScrollView> {

  // EDITED: 3. Change the class name
  private static final String REACT_CLASS = "RCTNestedScrollViewAndroid";
  @Override
  public String getName() {
    return REACT_CLASS;
  }

  // EDITED: 4. Stretch the view always
  @Override
  public ReactNestedScrollView createViewInstance(ThemedReactContext context) {
    ReactNestedScrollView sv = new ReactNestedScrollView(context);
    sv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    return sv;
  }
  @ReactProp(name = "showsVerticalScrollIndicator")
  public void setShowsVerticalScrollIndicator(ReactNestedScrollView view, boolean value) {
    view.setVerticalScrollBarEnabled(value);
  }
  @ReactProp(name = ReactClippingViewGroupHelper.PROP_REMOVE_CLIPPED_SUBVIEWS)
  public void setRemoveClippedSubviews(ReactNestedScrollView view, boolean removeClippedSubviews) {
    view.setRemoveClippedSubviews(removeClippedSubviews);
  }
  @Override
  public @Nullable Map<String, Integer> getCommandsMap() {
    return ReactScrollViewCommandHelper.getCommandsMap();
  }
  @Override
  public void receiveCommand(
      ReactNestedScrollView scrollView,
      int commandId,
      @Nullable ReadableArray args) {
    ReactScrollViewCommandHelper.receiveCommand(this, scrollView, commandId, args);
  }
  @Override
  public void scrollTo(
      ReactNestedScrollView scrollView,
      ReactScrollViewCommandHelper.ScrollToCommandData data) {
    scrollView.smoothScrollTo(data.mDestX, data.mDestY);
  }
  @Override
  public void scrollWithoutAnimationTo(
      ReactNestedScrollView scrollView,
      ReactScrollViewCommandHelper.ScrollToCommandData data) {
    scrollView.scrollTo(data.mDestX, data.mDestY);
  }
  @Override
  public @Nullable Map getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.builder()
        .put(ScrollEvent.EVENT_NAME, MapBuilder.of("registrationName", "onScroll"))
        .put("topScrollBeginDrag", MapBuilder.of("registrationName", "onScrollBeginDrag"))
        .put("topScrollEndDrag", MapBuilder.of("registrationName", "onScrollEndDrag"))
        .put("topScrollAnimationEnd", MapBuilder.of("registrationName", "onScrollAnimationEnd"))
        .put("topMomentumScrollBegin", MapBuilder.of("registrationName", "onMomentumScrollBegin"))
        .put("topMomentumScrollEnd", MapBuilder.of("registrationName", "onMomentumScrollEnd"))
        .build();
  }
}
