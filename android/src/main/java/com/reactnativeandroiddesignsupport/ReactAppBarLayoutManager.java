package com.reactnativeandroiddesignsupport;

import java.util.Map;
import javax.annotation.Nullable;

import android.view.View;
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

public class ReactAppBarLayoutManager extends ViewGroupManager<AppBarLayout> {

  @Override
  public String getName() {
    return "RCTAppBarLayoutAndroid";
  }

  @Override
  public AppBarLayout createViewInstance(ThemedReactContext context) {
    return new AppBarLayout(context);
  }

  public boolean needsCustomLayoutForChildren() {
    return true;
  }

  public static final int COMMAND_SET_CHILDREN_SCROLL_FLAGS = 1;

  @Override
  public Map<String,Integer> getCommandsMap() {
    return MapBuilder.of(
        "setChildrenScrollFlags",
        COMMAND_SET_CHILDREN_SCROLL_FLAGS);
  }

  @Override
  public void receiveCommand(AppBarLayout view, int commandType, @Nullable ReadableArray args) {
    Assertions.assertNotNull(view);
    Assertions.assertNotNull(args);

    switch (commandType) {
      case COMMAND_SET_CHILDREN_SCROLL_FLAGS: {
        ReadableArray options = args.getArray(0);
        setChildrenScrollFlags(view, options);
        return;
      }

      default:
        throw new JSApplicationIllegalArgumentException(String.format(
          "Unsupported command %d received by %s.",
          commandType,
          getClass().getSimpleName()));
    }
  }

  @ReactProp(name = "childrenScrollFlags")
  public void setScrollFlags(AppBarLayout view, ReadableArray options) {
    this.setChildrenScrollFlags(view, options);
  }

  private void setChildrenScrollFlags(AppBarLayout view, ReadableArray options) {
    try {
      int optionSize = options.size();
      for (int i=0; i<optionSize; i++) {

        ReadableMap optionMap = options.getMap(i);
        ReadableArray scrollFlags = optionMap.getArray("scrollFlags");

        int scrollFlagsSize = scrollFlags.size();
        int scrollFlagsInteger = 0;

        for (int j=0; j<scrollFlagsSize; j++) {
          String scrollFlagString = scrollFlags.getString(j);

          if ("enterAlways".equals(scrollFlagString)) {
            scrollFlagsInteger = scrollFlagsInteger | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS;
          } else if ("enterAlwaysCollapsed".equals(scrollFlagString)) {
            scrollFlagsInteger = scrollFlagsInteger | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED;
          } else if ("exitUntilCollapsed".equals(scrollFlagString)) {
            scrollFlagsInteger = scrollFlagsInteger | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED;
          } else if ("scroll".equals(scrollFlagString)) {
            scrollFlagsInteger = scrollFlagsInteger | AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL;
          } else if ("snap".equals(scrollFlagString)) {
            scrollFlagsInteger = scrollFlagsInteger | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP;
          }
        }

        View childView = view.getChildAt(optionMap.getInt("index"));
        AppBarLayout.LayoutParams param = (AppBarLayout.LayoutParams) childView.getLayoutParams();

        param.setScrollFlags(scrollFlagsInteger);
      }
    } catch (Exception e) {
      // TODO: Handle Exception
    }
  }
}
