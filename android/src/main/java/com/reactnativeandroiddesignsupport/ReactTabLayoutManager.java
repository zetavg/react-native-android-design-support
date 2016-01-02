package com.reactnativeandroiddesignsupport;

import java.util.Map;
import javax.annotation.Nullable;

import android.view.View;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ReactProp;
import com.facebook.react.uimanager.ThemedReactContext;

import android.util.Log;


public class ReactTabLayoutManager extends ViewGroupManager<TabLayout> {
  public ReadableArray mTabs = null;

  @Override
  public String getName() {
    return "RCTTabLayoutAndroid";
  }

  @Override
  public TabLayout createViewInstance(ThemedReactContext context) {
    return new TabLayout(context);
  }

  public boolean needsCustomLayoutForChildren() {
    return true;
  }

  @Override
  public void addView(TabLayout view, View child, int index) {
    view.addTab(view.newTab().setCustomView(child));
  }

  @Override
  public int getChildCount(TabLayout view) {
    return view.getTabCount();
  }

  @Override
  public View getChildAt(TabLayout view, int index) {
    TabLayout.Tab t = view.getTabAt(index);
    return t.getCustomView();
  }

  @Override
  public void removeViewAt(TabLayout view, int index) {
    view.removeTabAt(index);
  }

  // @Override
  // public void removeAllViews(TabLayout view) {
  //   view.removeAllTabs();
  // }

  public static final int COMMAND_SET_VIEW_PAGER = 1;

  @Override
  public Map<String,Integer> getCommandsMap() {
    return MapBuilder.of(
        "setViewPager",
        COMMAND_SET_VIEW_PAGER);
  }

  @Override
  public void receiveCommand(TabLayout view, int commandType, @Nullable ReadableArray args) {
    Assertions.assertNotNull(view);
    Assertions.assertNotNull(args);

    switch (commandType) {
      case COMMAND_SET_VIEW_PAGER: {
        int viewPagerId = args.getInt(0);
        ViewPager viewPager = (ViewPager) view.getRootView().findViewById(viewPagerId);
        view.setupWithViewPager(viewPager);

        ReadableArray tabs = args.getArray(1);
        if (tabs != null) {
          view.removeAllTabs();
          this.populateTablayoutWithTabs(view, tabs);
        }

        return;
      }

      default:
        throw new JSApplicationIllegalArgumentException(String.format(
          "Unsupported command %d received by %s.",
          commandType,
          getClass().getSimpleName()));
    }
  }

  @ReactProp(name = "tabs")
  public void setTabs(TabLayout view, ReadableArray tabs) {
    view.removeAllTabs();
    this.mTabs = tabs;
    this.populateTablayoutWithTabs(view, tabs);
  }

  @ReactProp(name = "normalColor", customType = "Color")
  public void setNormalColor(TabLayout view, int color) {
    int selectedColor = view.getTabTextColors().getColorForState(ReactTabLayout.getSelectedStateSet(), color);
    view.setTabTextColors(color, selectedColor);
  }

  @ReactProp(name = "selectedColor", customType = "Color")
  public void setSelectedColor(TabLayout view, int color) {
    int normalColor = view.getTabTextColors().getColorForState(ReactTabLayout.getEmptyStateSet(), color);
    view.setTabTextColors(normalColor, color);
  }

  @ReactProp(name = "selectedTabIndicatorColor", customType = "Color")
  public void setSelectedTabIndicatorColor(TabLayout view, int color) {
    view.setSelectedTabIndicatorColor(color);
  }

  @ReactProp(name = "tabMode")
  public void setTabMode(TabLayout view, String mode) {
    if ("fixed".equals(mode)) {
      view.setTabMode(TabLayout.MODE_FIXED);
    } else if ("scrollable".equals(mode)) {
      view.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
  }

  private void populateTablayoutWithTabs(TabLayout view, ReadableArray tabs) {
    try {
      int tabSize = tabs.size();
      for (int i=0; i<tabSize; i++) {

        ReadableMap tabMap = tabs.getMap(i);

        TabLayout.Tab tab = view.newTab();

        if (tabMap.hasKey("text")) tab.setText(tabMap.getString("text"));
        // TODO: Deal with icons, etc.

        view.addTab(tab);
      }
    } catch (Exception e) {
      // TODO: Handle Exception
    }
  }
}
