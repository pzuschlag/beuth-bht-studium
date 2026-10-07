/**
 * Created by jroehl on 03.05.16.
 */
// This section sets up some basic app metadata,
// the entire section is optional.
App.info({
    id: 'de.dev.beuth.wanderwonder',
    name: 'WanderWonder',
    description: '...'
        // author: '?',
        // email: '?',
        // website: '?'
});
//
// Set up resources such as icons and launch screens.
// https://makeappicon.com/
App.icons({
    'iphone_2x': 'icons/res/icons/ios/icon-60-2x.png',
    'iphone_3x': 'icons/res/icons/ios/icon-60-3x.png',
    'ipad': 'icons/res/icons/ios/icon-60.png',
    'ipad_2x': 'icons/res/icons/ios/icon-72-2x.png',
    'ipad_pro': 'icons/res/icons/ios/icon-76-2x.png',
    'ios_settings': 'icons/res/icons/ios/icon-40.png',
    'ios_settings_2x': 'icons/res/icons/ios/icon-40-2x.png',
    'ios_settings_3x': 'icons/res/icons/ios/icon-50.png',
    'ios_spotlight': 'icons/res/icons/ios/icon-small.png',
    'ios_spotlight_2x': 'icons/res/icons/ios/icon-small-2x.png',
    'android_mdpi': 'icons/res/icons/android/icon-48-mdpi.png',
    'android_hdpi': 'icons/res/icons/android/icon-72-hdpi.png',
    'android_xhdpi': 'icons/res/icons/android/icon-96-xhdpi.png',
    'android_xxhdpi': 'icons/res/icons/android/icon-144-xxhdpi.png',
    'android_xxxhdpi': 'icons/res/icons/android/icon-192-xxxhdpi.png',
});

App.launchScreens({
    'iphone_2x': 'icons/res/screens/ios/screen-iphone-portrait-2x.png',
    'iphone5': 'icons/res/screens/ios/screen-iphone-portrait-568h-2x.png',
    'iphone6': 'icons/res/screens/ios/screen-iphone-portrait-667h.png',
    'iphone6p_portrait': 'icons/res/screens/ios/screen-iphone-portrait-736h.png',
    // 'iphone6p_landscape': 'splash/Default~iphone.png',
    'ipad_portrait': 'icons/res/screens/ios/screen-ipad-portrait.png',
    'ipad_portrait_2x': 'icons/res/screens/ios/screen-ipad-portrait-2x.png',
    // 'ipad_landscape': 'splash/Default~iphone.png',
    // 'ipad_landscape_2x': 'splash/Default~iphone.png',
    'android_mdpi_portrait': 'icons/res/screens/android/screen-mdpi-portrait.png',
    // 'android_mdpi_landscape': 'splash/Default~iphone.png',
    'android_hdpi_portrait': 'icons/res/screens/android/screen-ldpi-portrait.png',
    // 'android_hdpi_landscape': 'splash/Default~iphone.png',
    'android_xhdpi_portrait': 'icons/res/screens/android/screen-hdpi-portrait.png',
    // 'android_xhdpi_landscape': 'splash/Default~iphone.png',
    'android_xxhdpi_portrait': 'icons/res/screens/android/screen-xhdpi-portrait.png',
    // 'android_xxhdpi_landscape': 'splash/Default~iphone.png',
});

//
// Set PhoneGap/Cordova preferences
App.setPreference('Orientation', 'portrait');
App.setPreference('android-targetSdkVersion', '23');
App.accessRule("*"); //should be a little more tightly controlled
//
// // Pass preferences for a particular PhoneGap/Cordova plugin
// App.configurePlugin('com.phonegap.plugins.facebookconnect', {
//     APP_ID: '1234567890',
//     API_KEY: 'supersecretapikey'
// });
