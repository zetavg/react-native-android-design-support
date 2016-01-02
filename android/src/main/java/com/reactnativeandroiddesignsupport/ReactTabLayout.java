package com.reactnativeandroiddesignsupport;

import android.content.Context;
import android.support.design.widget.TabLayout;

public class ReactTabLayout extends TabLayout {

  public ReactTabLayout(Context context) {
    super(context);
  }

  public final static int[] getSelectedStateSet() {
    return SELECTED_STATE_SET;
  }

  public final static int[] getEmptyStateSet() {
    return EMPTY_STATE_SET;
  }
}
