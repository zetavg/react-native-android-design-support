# react-native-android-design-support [![npm version](https://img.shields.io/npm/v/react-native-android-design-support.svg?style=flat-square)](https://www.npmjs.com/package/react-native-android-design-support)

React Native wrapper for [Android Design Support Library](http://android-developers.blogspot.tw/2015/05/android-design-support-library.html), providing native Material Design to modern and also older Android devices.

- [x] Snackbar
- [ ] Navigation View
- [ ] Floating Action Button
- [x] TextInputLayout
- [x] TabLayout
- [x] CoordinatorLayout
- [x] AppBarLayout
- [ ] CollapsingToolbarLayout

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

### TextInputLayout

```js
<TextInputLayoutAndroid hint="This is the floating label">
  <TextInput/>
</TextInputLayoutAndroid>
```

#### Props of `TextInputLayoutAndroid`

- `hint`
- `hintAnimationEnabled`
- `errorEnabled`
- `error`
- `counterEnabled`
- `counterMaxLength`


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


### CoordinatorLayout

The following is an example of using `<CoordinatorLayoutAndroid>` with `<AppBarLayoutAndroid>` and `<NestedScrollViewAndroid>`, which shall be the most common usage.

```js
...

var {
  CoordinatorLayoutAndroid,
  AppBarLayoutAndroid,
  NestedScrollViewAndroid
} = require('react-native-android-design-support');

var CoordinatorLayoutBasicExample = React.createClass({

  componentDidMount: function() {
    // Set view behavior for the main content container. This will make
    // this.refs.contentContainer move with the <AppBarLayoutAndroid>
    // in this.refs.coordinatorLayout.
    this.refs.coordinatorLayout.setAppBarScrollingViewBehavior(this.refs.contentContainer);
  },

  render: function() {
    return (
      <View style={{ flex: 1 }}>
        <CoordinatorLayoutAndroid ref="coordinatorLayout">
          {/* Any elements that are directily in <CoordinatorLayoutAndroid> */}
          {/* will not use React Native's flexbox position. We need to      */}
          {/* specify `layoutWidthAndroid` and `layoutHeightAndroid`        */}
          {/* (= "matchParent" or "wrapContent") instead.                   */}
          <AppBarLayoutAndroid
            layoutWidthAndroid="matchParent"
            layoutHeightAndroid="wrapContent"
          >
            {/* Elements directily in <AppBarLayoutAndroid> can have an     */}
            {/* additional scrollFlagsAndroid prop. This must be an array   */}
            {/* of strings, specifying the scroll flags of this element.    */}
            {/* see https://goo.gl/RiAyDX for reference.                    */}
            <View scrollFlagsAndroid={['scroll']}>
              <Text style={{ fontSize: 18 }}>Hi, I will be collapsed when scrolling down, and show again when scrolled to top.</Text>
            </View>
            <View scrollFlagsAndroid={['scroll', 'enterAlways']}>
              <Text style={{ fontSize: 18 }}>Hi, I will be collapsed when scrolling down, and show again when scrolling up.</Text>
            </View>
            <View scrollFlagsAndroid={[]}>
              <Text style={{ fontSize: 18 }}>Hi, I will not be collapsed.</Text>
            </View>
          </AppBarLayoutAndroid>
          <View
            ref="contentContainer"
            // We're setting the background color to prevent a bug that mulls
            // the position of the container.
            style={{ backgroundColor: 'transparent' }}
          >
            {/* We'll need to use <NestedScrollViewAndroid> because the      */}
            {/* default <ScrollView> dosen't support CoordinatorLayout.      */}
            <NestedScrollViewAndroid
              // For now, we'll need to calculate <NestedScrollViewAndroid>'s
              // heignt by ourself. Normally this will be
              // React.Dimensions.get('window').height - <appBar's min height>.
              height={React.Dimensions.get('window').height - (18)}
            >
              <Text style={{ margin: 8 }}>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas feugiat tortor eget ligula luctus, eget porttitor purus imperdiet. Proin accumsan erat non accumsan convallis. Donec a cursus libero. Proin varius metus vitae nisl ornare tempor. Donec leo libero, scelerisque non lorem nec, dapibus pharetra elit. Maecenas ultrices dui nec euismod convallis. Quisque facilisis elementum luctus. Aenean tempus dui sed elit ultrices egestas. Phasellus consectetur ac ipsum nec eleifend. Donec et leo neque. In auctor, est eu placerat efficitur, tortor mauris congue urna, a rutrum dui ex id purus. Curabitur eget ultrices purus. Curabitur vulputate justo lacus, vitae varius nulla ullamcorper et. Donec consectetur molestie mollis.</Text>
              <Text style={{ margin: 8 }}>Quisque ac pretium nisi. Phasellus non diam vitae velit dictum ultricies. Praesent vel quam enim. Curabitur id turpis nec ante consequat dignissim. Pellentesque ac augue felis. Curabitur pulvinar viverra iaculis. Mauris vitae consequat mi. Fusce feugiat ac risus ac fringilla. Praesent sed diam nec sem porta scelerisque. Maecenas euismod sed tellus sodales iaculis. Vivamus sit amet felis vitae enim auctor sodales. Duis eleifend nec orci id laoreet. Nulla ullamcorper et est non aliquet. Etiam posuere urna eget ipsum malesuada dapibus. Aliquam ligula velit, venenatis id magna ac, luctus dignissim sem.</Text>
              <Text style={{ margin: 8 }}>Suspendisse a nulla imperdiet, blandit nunc nec, consequat massa. Proin fermentum sem sapien, sed fringilla tellus dapibus ut. Aliquam iaculis eu lorem non feugiat. Nulla dignissim erat et mi imperdiet, cursus ultrices velit eleifend. Vestibulum ac aliquam diam, et molestie felis. Vivamus facilisis ex et interdum suscipit. Phasellus dapibus rhoncus placerat. Nunc blandit leo eu volutpat pellentesque. Phasellus semper metus ut risus aliquam, vestibulum vestibulum lectus lacinia.</Text>
              <Text style={{ margin: 8 }}>Phasellus nec sapien sed ipsum consequat faucibus. Cras mollis leo elit, id pellentesque orci condimentum ut. Proin orci massa, feugiat id auctor quis, euismod sit amet purus. Sed et justo porta, volutpat massa vitae, auctor metus. Etiam tincidunt lacinia lectus sit amet iaculis. Proin tempor lectus at libero vestibulum, vitae auctor metus porttitor. Nunc egestas posuere elementum. Donec nec consequat velit. Quisque quis massa libero. Curabitur a sem a nisl sagittis dapibus consectetur eget nulla. Vivamus at ultricies elit, quis gravida ex.</Text>
              <Text style={{ margin: 8 }}>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas feugiat tortor eget ligula luctus, eget porttitor purus imperdiet. Proin accumsan erat non accumsan convallis. Donec a cursus libero. Proin varius metus vitae nisl ornare tempor. Donec leo libero, scelerisque non lorem nec, dapibus pharetra elit. Maecenas ultrices dui nec euismod convallis. Quisque facilisis elementum luctus. Aenean tempus dui sed elit ultrices egestas. Phasellus consectetur ac ipsum nec eleifend. Donec et leo neque. In auctor, est eu placerat efficitur, tortor mauris congue urna, a rutrum dui ex id purus. Curabitur eget ultrices purus. Curabitur vulputate justo lacus, vitae varius nulla ullamcorper et. Donec consectetur molestie mollis.</Text>
              <Text style={{ margin: 8 }}>Quisque ac pretium nisi. Phasellus non diam vitae velit dictum ultricies. Praesent vel quam enim. Curabitur id turpis nec ante consequat dignissim. Pellentesque ac augue felis. Curabitur pulvinar viverra iaculis. Mauris vitae consequat mi. Fusce feugiat ac risus ac fringilla. Praesent sed diam nec sem porta scelerisque. Maecenas euismod sed tellus sodales iaculis. Vivamus sit amet felis vitae enim auctor sodales. Duis eleifend nec orci id laoreet. Nulla ullamcorper et est non aliquet. Etiam posuere urna eget ipsum malesuada dapibus. Aliquam ligula velit, venenatis id magna ac, luctus dignissim sem.</Text>
              <Text style={{ margin: 8 }}>Suspendisse a nulla imperdiet, blandit nunc nec, consequat massa. Proin fermentum sem sapien, sed fringilla tellus dapibus ut. Aliquam iaculis eu lorem non feugiat. Nulla dignissim erat et mi imperdiet, cursus ultrices velit eleifend. Vestibulum ac aliquam diam, et molestie felis. Vivamus facilisis ex et interdum suscipit. Phasellus dapibus rhoncus placerat. Nunc blandit leo eu volutpat pellentesque. Phasellus semper metus ut risus aliquam, vestibulum vestibulum lectus lacinia.</Text>
              <Text style={{ margin: 8 }}>Phasellus nec sapien sed ipsum consequat faucibus. Cras mollis leo elit, id pellentesque orci condimentum ut. Proin orci massa, feugiat id auctor quis, euismod sit amet purus. Sed et justo porta, volutpat massa vitae, auctor metus. Etiam tincidunt lacinia lectus sit amet iaculis. Proin tempor lectus at libero vestibulum, vitae auctor metus porttitor. Nunc egestas posuere elementum. Donec nec consequat velit. Quisque quis massa libero. Curabitur a sem a nisl sagittis dapibus consectetur eget nulla. Vivamus at ultricies elit, quis gravida ex.</Text>
            </NestedScrollViewAndroid>
          </View>
        </CoordinatorLayoutAndroid>
      </View>
    );
  },
});

...
```

And this example includes the usage of `<ViewPagerAndroid>` and `<TabLayoutAndroid>`:

```js
...

var {
  CoordinatorLayoutAndroid,
  AppBarLayoutAndroid,
  TabLayoutAndroid,
  NestedScrollViewAndroid
} = require('react-native-android-design-support');

var { ViewPagerAndroid, ToolbarAndroid } = require('react-native');

var CoordinatorLayoutWithTabExample = React.createClass({

  componentDidMount: function() {
    this.refs.tab.setViewPager(this.refs.viewPager);
    this.refs.coordinatorLayout.setAppBarScrollingViewBehavior(this.refs.viewPager);
  },

  render: function() {
    return (
      <View style={{ flex: 1 }}>
        <CoordinatorLayoutAndroid ref="coordinatorLayout">
          <AppBarLayoutAndroid
            layoutWidthAndroid="matchParent"
            layoutHeightAndroid="wrapContent"
          >
            <ToolbarAndroid
              scrollFlagsAndroid={['scroll', 'enterAlways']}
              title="Hello World"
              style={{ height: 50 }}
            />
            <TabLayoutAndroid
              scrollFlagsAndroid={[]}
              ref="tab"
              tabs={[
                { text: 'First Tab' },
                { text: 'Second Tab' },
                { text: 'Third Tab' }
              ]}
              style={{ height: 45 }}
            />
          </AppBarLayoutAndroid>

          <ViewPagerAndroid
            ref="viewPager"
            // We're setting the background color to prevent a bug that mulls
            // the position of the container.
            style={{ backgroundColor: 'transparent' }}
          >
            <View>
              <NestedScrollViewAndroid
                height={React.Dimensions.get('window').height - (45)}
              >
                <Text style={{ margin: 8 }}>This is the first tab.</Text>
                <Text style={{ margin: 8 }}>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas feugiat tortor eget ligula luctus, eget porttitor purus imperdiet. Proin accumsan erat non accumsan convallis. Donec a cursus libero. Proin varius metus vitae nisl ornare tempor. Donec leo libero, scelerisque non lorem nec, dapibus pharetra elit. Maecenas ultrices dui nec euismod convallis. Quisque facilisis elementum luctus. Aenean tempus dui sed elit ultrices egestas. Phasellus consectetur ac ipsum nec eleifend. Donec et leo neque. In auctor, est eu placerat efficitur, tortor mauris congue urna, a rutrum dui ex id purus. Curabitur eget ultrices purus. Curabitur vulputate justo lacus, vitae varius nulla ullamcorper et. Donec consectetur molestie mollis.</Text>
                <Text style={{ margin: 8 }}>Quisque ac pretium nisi. Phasellus non diam vitae velit dictum ultricies. Praesent vel quam enim. Curabitur id turpis nec ante consequat dignissim. Pellentesque ac augue felis. Curabitur pulvinar viverra iaculis. Mauris vitae consequat mi. Fusce feugiat ac risus ac fringilla. Praesent sed diam nec sem porta scelerisque. Maecenas euismod sed tellus sodales iaculis. Vivamus sit amet felis vitae enim auctor sodales. Duis eleifend nec orci id laoreet. Nulla ullamcorper et est non aliquet. Etiam posuere urna eget ipsum malesuada dapibus. Aliquam ligula velit, venenatis id magna ac, luctus dignissim sem.</Text>
                <Text style={{ margin: 8 }}>Suspendisse a nulla imperdiet, blandit nunc nec, consequat massa. Proin fermentum sem sapien, sed fringilla tellus dapibus ut. Aliquam iaculis eu lorem non feugiat. Nulla dignissim erat et mi imperdiet, cursus ultrices velit eleifend. Vestibulum ac aliquam diam, et molestie felis. Vivamus facilisis ex et interdum suscipit. Phasellus dapibus rhoncus placerat. Nunc blandit leo eu volutpat pellentesque. Phasellus semper metus ut risus aliquam, vestibulum vestibulum lectus lacinia.</Text>
                <Text style={{ margin: 8 }}>Phasellus nec sapien sed ipsum consequat faucibus. Cras mollis leo elit, id pellentesque orci condimentum ut. Proin orci massa, feugiat id auctor quis, euismod sit amet purus. Sed et justo porta, volutpat massa vitae, auctor metus. Etiam tincidunt lacinia lectus sit amet iaculis. Proin tempor lectus at libero vestibulum, vitae auctor metus porttitor. Nunc egestas posuere elementum. Donec nec consequat velit. Quisque quis massa libero. Curabitur a sem a nisl sagittis dapibus consectetur eget nulla. Vivamus at ultricies elit, quis gravida ex.</Text>
                <Text style={{ margin: 8 }}>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas feugiat tortor eget ligula luctus, eget porttitor purus imperdiet. Proin accumsan erat non accumsan convallis. Donec a cursus libero. Proin varius metus vitae nisl ornare tempor. Donec leo libero, scelerisque non lorem nec, dapibus pharetra elit. Maecenas ultrices dui nec euismod convallis. Quisque facilisis elementum luctus. Aenean tempus dui sed elit ultrices egestas. Phasellus consectetur ac ipsum nec eleifend. Donec et leo neque. In auctor, est eu placerat efficitur, tortor mauris congue urna, a rutrum dui ex id purus. Curabitur eget ultrices purus. Curabitur vulputate justo lacus, vitae varius nulla ullamcorper et. Donec consectetur molestie mollis.</Text>
                <Text style={{ margin: 8 }}>Quisque ac pretium nisi. Phasellus non diam vitae velit dictum ultricies. Praesent vel quam enim. Curabitur id turpis nec ante consequat dignissim. Pellentesque ac augue felis. Curabitur pulvinar viverra iaculis. Mauris vitae consequat mi. Fusce feugiat ac risus ac fringilla. Praesent sed diam nec sem porta scelerisque. Maecenas euismod sed tellus sodales iaculis. Vivamus sit amet felis vitae enim auctor sodales. Duis eleifend nec orci id laoreet. Nulla ullamcorper et est non aliquet. Etiam posuere urna eget ipsum malesuada dapibus. Aliquam ligula velit, venenatis id magna ac, luctus dignissim sem.</Text>
                <Text style={{ margin: 8 }}>Suspendisse a nulla imperdiet, blandit nunc nec, consequat massa. Proin fermentum sem sapien, sed fringilla tellus dapibus ut. Aliquam iaculis eu lorem non feugiat. Nulla dignissim erat et mi imperdiet, cursus ultrices velit eleifend. Vestibulum ac aliquam diam, et molestie felis. Vivamus facilisis ex et interdum suscipit. Phasellus dapibus rhoncus placerat. Nunc blandit leo eu volutpat pellentesque. Phasellus semper metus ut risus aliquam, vestibulum vestibulum lectus lacinia.</Text>
                <Text style={{ margin: 8 }}>Phasellus nec sapien sed ipsum consequat faucibus. Cras mollis leo elit, id pellentesque orci condimentum ut. Proin orci massa, feugiat id auctor quis, euismod sit amet purus. Sed et justo porta, volutpat massa vitae, auctor metus. Etiam tincidunt lacinia lectus sit amet iaculis. Proin tempor lectus at libero vestibulum, vitae auctor metus porttitor. Nunc egestas posuere elementum. Donec nec consequat velit. Quisque quis massa libero. Curabitur a sem a nisl sagittis dapibus consectetur eget nulla. Vivamus at ultricies elit, quis gravida ex.</Text>
              </NestedScrollViewAndroid>
            </View>
            <View>
              <NestedScrollViewAndroid
                height={React.Dimensions.get('window').height - (45)}
              >
                <Text style={{ margin: 8 }}>This is the second tab.</Text>
                <Text style={{ margin: 8 }}>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas feugiat tortor eget ligula luctus, eget porttitor purus imperdiet. Proin accumsan erat non accumsan convallis. Donec a cursus libero. Proin varius metus vitae nisl ornare tempor. Donec leo libero, scelerisque non lorem nec, dapibus pharetra elit. Maecenas ultrices dui nec euismod convallis. Quisque facilisis elementum luctus. Aenean tempus dui sed elit ultrices egestas. Phasellus consectetur ac ipsum nec eleifend. Donec et leo neque. In auctor, est eu placerat efficitur, tortor mauris congue urna, a rutrum dui ex id purus. Curabitur eget ultrices purus. Curabitur vulputate justo lacus, vitae varius nulla ullamcorper et. Donec consectetur molestie mollis.</Text>
                <Text style={{ margin: 8 }}>Quisque ac pretium nisi. Phasellus non diam vitae velit dictum ultricies. Praesent vel quam enim. Curabitur id turpis nec ante consequat dignissim. Pellentesque ac augue felis. Curabitur pulvinar viverra iaculis. Mauris vitae consequat mi. Fusce feugiat ac risus ac fringilla. Praesent sed diam nec sem porta scelerisque. Maecenas euismod sed tellus sodales iaculis. Vivamus sit amet felis vitae enim auctor sodales. Duis eleifend nec orci id laoreet. Nulla ullamcorper et est non aliquet. Etiam posuere urna eget ipsum malesuada dapibus. Aliquam ligula velit, venenatis id magna ac, luctus dignissim sem.</Text>
                <Text style={{ margin: 8 }}>Suspendisse a nulla imperdiet, blandit nunc nec, consequat massa. Proin fermentum sem sapien, sed fringilla tellus dapibus ut. Aliquam iaculis eu lorem non feugiat. Nulla dignissim erat et mi imperdiet, cursus ultrices velit eleifend. Vestibulum ac aliquam diam, et molestie felis. Vivamus facilisis ex et interdum suscipit. Phasellus dapibus rhoncus placerat. Nunc blandit leo eu volutpat pellentesque. Phasellus semper metus ut risus aliquam, vestibulum vestibulum lectus lacinia.</Text>
                <Text style={{ margin: 8 }}>Phasellus nec sapien sed ipsum consequat faucibus. Cras mollis leo elit, id pellentesque orci condimentum ut. Proin orci massa, feugiat id auctor quis, euismod sit amet purus. Sed et justo porta, volutpat massa vitae, auctor metus. Etiam tincidunt lacinia lectus sit amet iaculis. Proin tempor lectus at libero vestibulum, vitae auctor metus porttitor. Nunc egestas posuere elementum. Donec nec consequat velit. Quisque quis massa libero. Curabitur a sem a nisl sagittis dapibus consectetur eget nulla. Vivamus at ultricies elit, quis gravida ex.</Text>
                <Text style={{ margin: 8 }}>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas feugiat tortor eget ligula luctus, eget porttitor purus imperdiet. Proin accumsan erat non accumsan convallis. Donec a cursus libero. Proin varius metus vitae nisl ornare tempor. Donec leo libero, scelerisque non lorem nec, dapibus pharetra elit. Maecenas ultrices dui nec euismod convallis. Quisque facilisis elementum luctus. Aenean tempus dui sed elit ultrices egestas. Phasellus consectetur ac ipsum nec eleifend. Donec et leo neque. In auctor, est eu placerat efficitur, tortor mauris congue urna, a rutrum dui ex id purus. Curabitur eget ultrices purus. Curabitur vulputate justo lacus, vitae varius nulla ullamcorper et. Donec consectetur molestie mollis.</Text>
                <Text style={{ margin: 8 }}>Quisque ac pretium nisi. Phasellus non diam vitae velit dictum ultricies. Praesent vel quam enim. Curabitur id turpis nec ante consequat dignissim. Pellentesque ac augue felis. Curabitur pulvinar viverra iaculis. Mauris vitae consequat mi. Fusce feugiat ac risus ac fringilla. Praesent sed diam nec sem porta scelerisque. Maecenas euismod sed tellus sodales iaculis. Vivamus sit amet felis vitae enim auctor sodales. Duis eleifend nec orci id laoreet. Nulla ullamcorper et est non aliquet. Etiam posuere urna eget ipsum malesuada dapibus. Aliquam ligula velit, venenatis id magna ac, luctus dignissim sem.</Text>
                <Text style={{ margin: 8 }}>Suspendisse a nulla imperdiet, blandit nunc nec, consequat massa. Proin fermentum sem sapien, sed fringilla tellus dapibus ut. Aliquam iaculis eu lorem non feugiat. Nulla dignissim erat et mi imperdiet, cursus ultrices velit eleifend. Vestibulum ac aliquam diam, et molestie felis. Vivamus facilisis ex et interdum suscipit. Phasellus dapibus rhoncus placerat. Nunc blandit leo eu volutpat pellentesque. Phasellus semper metus ut risus aliquam, vestibulum vestibulum lectus lacinia.</Text>
                <Text style={{ margin: 8 }}>Phasellus nec sapien sed ipsum consequat faucibus. Cras mollis leo elit, id pellentesque orci condimentum ut. Proin orci massa, feugiat id auctor quis, euismod sit amet purus. Sed et justo porta, volutpat massa vitae, auctor metus. Etiam tincidunt lacinia lectus sit amet iaculis. Proin tempor lectus at libero vestibulum, vitae auctor metus porttitor. Nunc egestas posuere elementum. Donec nec consequat velit. Quisque quis massa libero. Curabitur a sem a nisl sagittis dapibus consectetur eget nulla. Vivamus at ultricies elit, quis gravida ex.</Text>
              </NestedScrollViewAndroid>
            </View>
            <View>
              <Text style={{ margin: 8 }}>This is the third tab.</Text>
              <Text style={{ margin: 8 }}>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas feugiat tortor eget ligula luctus, eget porttitor purus imperdiet. Proin accumsan erat non accumsan convallis. Donec a cursus libero. Proin varius metus vitae nisl ornare tempor. Donec leo libero, scelerisque non lorem nec, dapibus pharetra elit. Maecenas ultrices dui nec euismod convallis. Quisque facilisis elementum luctus. Aenean tempus dui sed elit ultrices egestas. Phasellus consectetur ac ipsum nec eleifend. Donec et leo neque. In auctor, est eu placerat efficitur, tortor mauris congue urna, a rutrum dui ex id purus. Curabitur eget ultrices purus. Curabitur vulputate justo lacus, vitae varius nulla ullamcorper et. Donec consectetur molestie mollis.</Text>
            </View>
          </ViewPagerAndroid>
        </CoordinatorLayoutAndroid>
      </View>
    );
  },
});

...
```
