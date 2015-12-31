# react-native-android-design-support [![npm version](https://img.shields.io/npm/v/react-native-android-design-support.svg?style=flat-square)](https://www.npmjs.com/package/react-native-android-design-support)

React Native wrapper for Android Design Support Library, providing Material Design to modern and also older Android devices.


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
