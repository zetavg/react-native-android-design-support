var RCTSnackbarAndroid = require('react-native').NativeModules.SnackbarAndroid;

var SnackbarAndroid = {
  INDEFINITE: RCTSnackbarAndroid.INDEFINITE,
  LONG: RCTSnackbarAndroid.LONG,
  SHORT: RCTSnackbarAndroid.SHORT,

  show: function(text, duration, action, payload, callback) {
    if (duration === undefined || duration === null) duration = RCTSnackbarAndroid.SHORT;

    if (!action) action = '';
    if (!payload) payload = {};
    var nPayload = JSON.stringify(payload);
    var nCallback = function(p) {
      callback(JSON.parse(p));
    }

    RCTSnackbarAndroid.show(text, duration, action, nPayload, nCallback);
  }
};

module.exports = SnackbarAndroid;
