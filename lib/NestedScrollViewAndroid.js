var React = require('react-native')
var { requireNativeComponent, PropTypes, StyleSheet, View, ScrollView } = React;
var ScrollResponder = require('react-native/Libraries/Components/ScrollResponder');

var SCROLLVIEW = 'scrollView';
var INNERVIEW = 'InnerScrollView';

var NestedScrollViewAndroid = React.createClass({
  mixins: [ScrollResponder.Mixin],

  propTypes: {
    ...ScrollView.propTypes,
  },

  getInitialState: function() {
    return this.scrollResponderMixinGetInitialState();
  },

  setNativeProps: function(props: Object) {
    this.refs[SCROLLVIEW].setNativeProps(props);
  },

  getScrollResponder: function(): ReactComponent {
    return this;
  },

  getInnerViewNode: function(): any {
    return React.findNodeHandle(this.refs[INNERVIEW]);
  },

  scrollTo: function(destY?: number, destX?: number) {
    this.getScrollResponder().scrollResponderScrollTo(destX || 0, destY || 0);
  },

  scrollWithoutAnimationTo: function(destY?: number, destX?: number) {
    this.getScrollResponder().scrollResponderScrollWithouthAnimationTo(
      destX || 0,
      destY || 0,
    );
  },

  handleScroll: function(e: Event) {
    if (__DEV__) {
      if (this.props.onScroll && !this.props.scrollEventThrottle) {
        console.log(
          'You specified `onScroll` on a <ScrollView> but not ' +
          '`scrollEventThrottle`. You will only receive one event. ' +
          'Using `16` you get all the events but be aware that it may ' +
          'cause frame drops, use a bigger number if you don\'t need as ' +
          'much precision.'
        );
      }
    }

    if (this.props.keyboardDismissMode === 'on-drag') {
      dismissKeyboard();
    }

    this.scrollResponderHandleScroll(e);
  },

  _handleContentOnLayout: function(e: Object) {
    var { width, height } = e.nativeEvent.layout;
    this.props.onContentSizeChange && this.props.onContentSizeChange(width, height);
  },

  render: function() {
    var contentContainerStyle = [
      this.props.horizontal && styles.contentContainerHorizontal,
      this.props.contentContainerStyle,
    ];

    // if (__DEV__ && this.props.style) {
    //   var style = flattenStyle(this.props.style);
    //   var childLayoutProps = ['alignItems', 'justifyContent']
    //     .filter((prop) => style && style[prop] !== undefined);
    //   invariant(
    //     childLayoutProps.length === 0,
    //     'ScrollView child layout (' + JSON.stringify(childLayoutProps) +
    //       ') must by applied through the contentContainerStyle prop.'
    //   );
    // }

    var contentSizeChangeProps = {};

    if (this.props.onContentSizeChange) {
      contentSizeChangeProps = {
        onLayout: this._handleContentOnLayout,
      };
    }

    var contentContainer =
      <View
        {...contentSizeChangeProps}
        ref={INNERVIEW}
        style={contentContainerStyle}
        removeClippedSubviews={this.props.removeClippedSubviews}
        collapsable={false}>
        {this.props.children}
      </View>;


    var props = {
      ...this.props,
      style: ([styles.base, this.props.style, this.props.height && { height: this.props.height }]: ?Array<any>),
      onTouchStart: this.scrollResponderHandleTouchStart,
      onTouchMove: this.scrollResponderHandleTouchMove,
      onTouchEnd: this.scrollResponderHandleTouchEnd,
      onScrollBeginDrag: this.scrollResponderHandleScrollBeginDrag,
      onScrollEndDrag: this.scrollResponderHandleScrollEndDrag,
      onMomentumScrollBegin: this.scrollResponderHandleMomentumScrollBegin,
      onMomentumScrollEnd: this.scrollResponderHandleMomentumScrollEnd,
      onStartShouldSetResponder: this.scrollResponderHandleStartShouldSetResponder,
      onStartShouldSetResponderCapture: this.scrollResponderHandleStartShouldSetResponderCapture,
      onScrollShouldSetResponder: this.scrollResponderHandleScrollShouldSetResponder,
      onScroll: this.handleScroll,
      onResponderGrant: this.scrollResponderHandleResponderGrant,
      onResponderTerminationRequest: this.scrollResponderHandleTerminationRequest,
      onResponderTerminate: this.scrollResponderHandleTerminate,
      onResponderRelease: this.scrollResponderHandleResponderRelease,
      onResponderReject: this.scrollResponderHandleResponderReject,
    };

    var onRefreshStart = this.props.onRefreshStart;

    props.onRefreshStart = onRefreshStart
      ? function() { onRefreshStart && onRefreshStart(this.endRefreshing); }.bind(this)
      : null;

    return (
      <RCTNestedScrollViewAndroid
        ref={SCROLLVIEW}
        {...props}
      >
        {contentContainer}
      </RCTNestedScrollViewAndroid>
    );
  }
});

var RCTNestedScrollViewAndroid = requireNativeComponent('RCTNestedScrollViewAndroid', NestedScrollViewAndroid, {
  nativeOnly: {}
});

var styles = StyleSheet.create({
  base: {
    // flex: 1
  },
  contentContainerHorizontal: {
    alignSelf: 'flex-start',
    flexDirection: 'row',
  },
});

module.exports = NestedScrollViewAndroid;
