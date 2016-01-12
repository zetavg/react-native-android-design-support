var React = require('react-native')
var { requireNativeComponent, PropTypes, View } = React;

var TextInputLayoutAndroid = React.createClass({
  propTypes: {
    ...View.propTypes,
    hint: PropTypes.string,
    hintAnimationEnabled: PropTypes.bool,
    errorEnabled: PropTypes.bool,
    error: PropTypes.string,
    counterEnabled: PropTypes.bool,
    counterMaxLength: PropTypes.number
  },

  getDefaultProps: function() {
    return {};
  },

  render: function() {
    return (
      <RCTTextInputLayoutAndroid
        {...this.props}
        style={this.props.style}
      >
        {this.props.children}
      </RCTTextInputLayoutAndroid>
    );
  }
});

var RCTTextInputLayoutAndroid = requireNativeComponent('RCTTextInputLayoutAndroid', TextInputLayoutAndroid, {
  nativeOnly: {}
});

module.exports = TextInputLayoutAndroid;
