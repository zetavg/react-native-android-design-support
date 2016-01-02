package com.reactnativeandroiddesignsupport;

import java.util.Map;
import javax.annotation.Nullable;

import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.NestedScrollView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.AppBarLayout;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ReactProp;
import com.facebook.react.uimanager.ThemedReactContext;

public class ReactCoordinatorLayoutManager extends ViewGroupManager<CoordinatorLayout> {

  @Override
  public String getName() {
    return "RCTCoordinatorLayoutAndroid";
  }

  @Override
  public CoordinatorLayout createViewInstance(ThemedReactContext context) {
    return new CoordinatorLayout(context);
  }

  public boolean needsCustomLayoutForChildren() {
    return true;
  }

  public static final int COMMAND_SET_VIEW_APPBAR_SCROLLING_VIEW_BEHAVIOR = 1;
  public static final int COMMAND_SET_CHILDREN_LAYOUT = 2;

  @Override
  public Map<String,Integer> getCommandsMap() {
    return MapBuilder.of(
      "setAppBarScrollingViewBehavior",
      COMMAND_SET_VIEW_APPBAR_SCROLLING_VIEW_BEHAVIOR,
      "setChildrenLayout",
      COMMAND_SET_CHILDREN_LAYOUT
    );
  }

  @Override
  public void receiveCommand(CoordinatorLayout view, int commandType, @Nullable ReadableArray args) {
    Assertions.assertNotNull(view);
    Assertions.assertNotNull(args);

    switch (commandType) {
      case COMMAND_SET_VIEW_APPBAR_SCROLLING_VIEW_BEHAVIOR: {
        setViewAppBarScrollingViewBehavior(view, args.getInt(0));
        return;
      }

      case COMMAND_SET_CHILDREN_LAYOUT: {
        ReadableArray options = args.getArray(0);
        setChildrenLayout(view, options);
        return;
      }

      default:
        throw new JSApplicationIllegalArgumentException(String.format(
          "Unsupported command %d received by %s.",
          commandType,
          getClass().getSimpleName()));
    }
  }

  private void setViewAppBarScrollingViewBehavior(CoordinatorLayout parent, int viewId) {
    try {
      View view = parent.getRootView().findViewById(viewId);

      CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
      params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
      view.requestLayout();

    } catch (Exception e) {
      // TODO: Handle Exception
    }
  }

  private void setChildrenLayout(CoordinatorLayout view, ReadableArray options) {
    try {
      int optionSize = options.size();
      for (int i=0; i<optionSize; i++) {

        ReadableMap optionMap = options.getMap(i);

        View childView = view.getChildAt(optionMap.getInt("index"));

        int width = 0, height = 0;

        if ("matchParent".equals(optionMap.getString("layoutWidth"))) {
          width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else if ("wrapContent".equals(optionMap.getString("layoutWidth"))) {
          width = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
          width = optionMap.getInt("layoutWidth");
        }

        if ("matchParent".equals(optionMap.getString("layoutHeight"))) {
          height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else if ("wrapContent".equals(optionMap.getString("layoutHeight"))) {
          height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
          height = optionMap.getInt("layoutHeight");
        }

        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(width, height);

        childView.setLayoutParams(params);
      }
    } catch (Exception e) {
      // TODO: Handle Exception
    }
  }
}
