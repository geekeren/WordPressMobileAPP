/**
 * Created by BrainWang on 2017-02-27.
 */

import React, {Component} from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    Image,
    ListView,
    RefreshControl,
    ToastAndroid,
    Touchable,
    TouchableHighlight,
    NativeModules,
} from 'react-native';

import Home from './RNAndroid/Home'
import SinglePost from './RNAndroid/SinglePost'
import ShuoShuo from './RNAndroid/ShuoShuo'
import Login from './RNAndroid/Login'
AppRegistry.registerComponent('Category', () => Home);
AppRegistry.registerComponent('Me', () => Home);
AppRegistry.registerComponent('Message', () => Home);


// import Category from './RNAndroid/Category'
// import Me from './RNAndroid/Me'