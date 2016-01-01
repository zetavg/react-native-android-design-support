# react-native-android-design-support [![npm version](https://img.shields.io/npm/v/react-native-android-design-support.svg?style=flat-square)](https://www.npmjs.com/package/react-native-android-design-support)

React Native wrapper for [Android Design Support Library](http://android-developers.blogspot.tw/2015/05/android-design-support-library.html), providing native Material Design to modern and also older Android devices.

- [ ] Navigation View
- [ ] Floating labels for editing text
- [ ] Floating Action Button
- [x] Snackbar
- [x] TabLayout
- [ ] Collapsing Toolbars

## Installation

- Run `npm install react-native-android-design-support --save` to install using npm.

- Add the following two lines to `android/settings.gradle`:

```gradle
include ':react-native-android-design-support'
project(':react-native-android-design-support').projectDir = new File(settingsDir, '../node_modules/react-native-android-design-support/android')
```

- Edit `android/app/build.gradle` and add the annoated lines as below:

```gradle
...

dependencies {
    compile fileTree(dir: "libs", include: ["*.jar"])
    compile "com.android.support:appcompat-v7:23.0.1"
    compile "com.facebook.react:react-native:0.15.+"
    compile project(':react-native-android-design-support')  // <- Add this line
}
```

- Edit `MainActivity.java` (usually at `android/app/src/main/java/com/<project-name>/MainActivity.java`) and add the annoated lines as below:

```java
import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

import com.reactnativeandroiddesignsupport.DesignSupportPackage;        // <- Add this line

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {

...

        mReactRootView = new ReactRootView(this);

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .addPackage(new DesignSupportPackage(this))             // <- Add this line
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();

...
```

> Make sure to update the Android Support Repository to the latest in the Android SDK Manager.
> If it is not updated, the following error might occur while compiling the Android apk:
>
> ```
> A problem occurred configuring project ':react-native-android-design-support'.
> Could not resolve all dependencies for configuration ':react-native-android-design-support'.
> ```


## Usage

### Snackbar

```js
var SnackbarAndroid = require('react-native-android-design-support').SnackbarAndroid;

// Show an simple Snackbar with text:
SnackbarAndroid.show('Hello World!');

// Set the Snackbar duration:
SnackbarAndroid.show('Hello World!', SnackbarAndroid.LONG);

// Set an action and callback on the Snackbar:
SnackbarAndroid.show('Hi!', SnackbarAndroid.LONG, 'Show another "Hi!"', { yo: 'ya' }, (p) => { SnackbarAndroid.show(`Hi, ${JSON.stringify(p)}`) });
```

### TabLayout

The most common usage of `TabLayoutAndroid` is with `ViewPagerAndroid`:

```js
var ViewPagerWithTabExample = React.createClass({

  componentDidMount: function() {
    this.refs.tab.setViewPager(this.refs.viewPager);
  },

  render: function() {
    return (
      <View style={{ flex: 1 }}>
        <TabLayoutAndroid ref="tab"
          tabs={[
            { text: 'First Page' },
            { text: 'Second Page' },
            { text: 'Third Page' }
          ]}
        />
        <ViewPagerAndroid ref="viewPager" style={{ flex: 1 }}>
          <View>
            <Text>This is the first page.</Text>
          </View>
          <View>
            <Text>This is the second page.</Text>
          </View>
          <View>
            <Text>This is the third page.</Text>
          </View>
        </ViewPagerAndroid>
      </View>
    );
  }
});
```

> Note that the `tabs` attribute must be provided, so that the `TabLayout` can know whether the tab titles or icons to be displayed.

Also, you can use it directly by just specifying the `tabs`, or giving childrens as it tabs:

```js
var TabLayoutAndroid = require('react-native-android-design-support').TabLayoutAndroid;

...

render: function() {
  return (
    <TabLayoutAndroid
      style={styles.tabLayout}
    >
      <View style={styles.tab}>
        <Text>First Tab</Text>
      </View>
      <View style={styles.tab}>
        <Text>Second Tab</Text>
      </View>
    </TabLayoutAndroid>
  );
}

...
```

> TODO: Handle custom events while using it independently.

#### Props of `TabLayoutAndroid`

- `style`
- `tabs`: `Array: { text: String }`: The data of the tabs, required if no chirdren is provided.
- `tabMode`: `String`: The tab mode, `fixed` or `scrollable`.
- `normalColor`: The text color for normal tab.
- `selectedColor`: The text color for selected tab.
- `selectedTabIndicatorColor`: The color for selected tab indicator.
