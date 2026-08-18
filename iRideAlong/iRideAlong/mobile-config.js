/**
 * Created by jroehl on 03.05.16.
 */
// This section sets up some basic app metadata,
// the entire section is optional.
App.info({
    id: 'de.dev.beuth.iridealong',
    name: 'iRideAlong',
    description: 'Find friends, share your ticket',
    author: 'RoBoJoPhi',
    email: '...',
    website: 'http://54.93.73.9/',
    version: "0.9.7"
});

// // Set up resources such as icons and launch screens.
App.icons({
  'iphone_2x': 'icons/res/icons/ios/icon-60-2x.png',  //(120x120)
  'iphone_3x': 'icons/res/icons/ios/icon-60-3x.png',  //(180x180)
  'ipad': 'icons/res/icons/ios/icon-76.png',          //(76x76)
  'ipad_2x': 'icons/res/icons/ios/icon-76-2x.png',    //(152x152)
  'ipad_pro': 'icons/res/icons/ios/icon-76-2x.png',   //(167x167)
  'ios_settings': 'icons/res/icons/ios/icon-small.png',  //(29x29)
  'ios_settings_2x': 'icons/res/icons/ios/icon-small-2x.png',  //(58x58)
  'ios_settings_3x': 'icons/res/icons/ios/icon-76.png',  //(87x87)
  'ios_spotlight': 'icons/res/icons/ios/icon-40.png',  //(40x40)
  'ios_spotlight_2x': 'icons/res/icons/ios/icon-40-2x.png', //(80x80)
  'android_mdpi': 'icons/res/icons/android/icon-48-mdpi.png', //(48x48)
  'android_hdpi': 'icons/res/icons/android/icon-72-hdpi.png', //(72x72)
  'android_xhdpi': 'icons/res/icons/android/icon-96-xhdpi.png',  //(96x96)
  'android_xxhdpi': 'icons/res/icons/android/icon-144-xxhdpi.png', //(144x144)
  'android_xxxhdpi': 'icons/res/icons/android/icon-192-xxxhdpi.png', //(192x192)
});

App.launchScreens({
  'iphone_2x': 'icons/res/screens/ios/screen-iphone-portrait-2x.png', //(640x960)
  'iphone5': 'icons/res/screens/ios/screen-iphone-portrait-568h-2x.png', //(640x1136)
  'iphone6': 'icons/res/screens/ios/screen-iphone-portrait-667h.png', //(750x1334)
  'iphone6p_portrait': 'icons/res/screens/ios/screen-iphone-portrait-736h.png', //(1242x2208)
  'ipad_portrait': 'icons/res/screens/ios/screen-ipad-portrait.png', //(768x1024)
  'ipad_portrait_2x': 'icons/res/screens/ios/screen-ipad-portrait-2x.png', //(1536x2048)
  'android_mdpi_portrait': 'icons/res/screens/android/screen-mdpi-portrait.png', //(new 320x480)
  'android_hdpi_portrait': 'icons/res/screens/android/screen-hdpi-portrait.png', //(new 480x800)
  'android_xhdpi_portrait': 'icons/res/screens/android/screen-xhdpi-portrait.png', //(new 720x1280)
  'android_xxhdpi_portrait': 'icons/res/screens/android/screen-xxhdpi-portrait-FHD.png', //(FHD: 1080x1920)
});

// Set PhoneGap/Cordova preferences
App.setPreference('Orientation', 'portrait');
App.setPreference('android-targetSdkVersion', '23');
