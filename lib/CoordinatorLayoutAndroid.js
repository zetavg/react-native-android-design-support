var React = require('react-native')
var { requireNativeComponent, PropTypes, View, UIManager, findNodeHandle } = React;

var CoordinatorLayoutAndroid = React.createClass({
  propTypes: {
    ...View.propTypes
  },

  getDefaultProps: function() {
    return {};
  },

  componentDidMount: function() {
    this._setChildrenLayout();
  },

  componentDidUpdate: function() {
    this._setChildrenLayout();
  },

  setAppBarScrollingViewBehavior: function(element) {
    var viewID = findNodeHandle(element);

    UIManager.dispatchViewManagerCommand(
      React.findNodeHandle(this),
      UIManager.RCTCoordinatorLayoutAndroid.Commands.setAppBarScrollingViewBehavior,
      [viewID],
    );
  },

  _setChildrenLayout: function() {
    var self = this;
    var layout = [];

    React.Children.map(this.props.children, function(child, index) {
      layout.push({
        index: index,
        layoutWidth: child.props.layoutWidthAndroid || 'matchParent',
        layoutHeight: child.props.layoutHeightAndroid || 'matchParent'
      });
    });

    UIManager.dispatchViewManagerCommand(
      React.findNodeHandle(this),
      UIManager.RCTCoordinatorLayoutAndroid.Commands.setChildrenLayout,
      [layout],
    );
  },

  render: function() {
    return (
      <RCTCoordinatorLayoutAndroid
        {...this.props}
        style={[{
          flex: 1
        }, this.props.style]}
      >
        {this.props.children}
      </RCTCoordinatorLayoutAndroid>
    );
  }
});

var RCTCoordinatorLayoutAndroid = requireNativeComponent('RCTCoordinatorLayoutAndroid', CoordinatorLayoutAndroid, {
  nativeOnly: {}
});

module.exports = CoordinatorLayoutAndroid;
