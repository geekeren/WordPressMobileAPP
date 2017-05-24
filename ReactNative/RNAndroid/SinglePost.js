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
    WebView,
    Alert
} from 'react-native';

var PostRender = require('./Utils/PostUtil')
var WPAPI = require('wpapi');
const wp = new WPAPI({endpoint: 'http://wangbaiyuan.cn/wp-json'});

class SinglePost extends Component {

    constructor(props) {
        super(props);
        this.state = {
            loaded: 0,
            error: false,
            postData:null,
        };

    }


    componentWillMount() {
        this.fetch_data();

    }

    fetch_data() {

        var thiz = this;
        wp.posts().id(this.props.id)
            .then((post) => {
                thiz.setState({
                    loaded: 1,
                    error: false,
                    postData: post
            }) }).catch((e) => {
                thiz.setState({
                    error: true
                });
            }
        );

    }

    render() {
        const { loaded, error, postData } = this.state;
        // Diagnostics: are we rendering?
        console.log('render is called');
        // Diagnostics: is the state object populated?
        console.log(loaded, error, postData);

        // Simple case: postData is available
        if (postData) {
            return (<WebView
                style={{height: 500,}}
                source={{html: PostRender(this.state.postData)}}
            />);
        }

        // Error state
        if (error) {
            return (<View style={styles.loading}>
                <Image style={{width: 40, height: 40}} source={{uri: 'reload'}}/>
                <Text style={{color: '#666666', fontSize: 15}}>错误</Text>
            </View>);
        }

        // If no error and no post data, then we're loading
        return (<View style={styles.loading}>
            <Image style={{width: 40, height: 40}} source={{uri: 'ajax_loader'}}/>
            <Text style={{color: '#666666', fontSize: 15}}>加载中 </Text>
        </View>);
    }


}


var styles = StyleSheet.create({

    loading: {
        flex: 1,
        flexDirection: "column",
        justifyContent: 'center',
        backgroundColor: '#FFFFFF',
        alignItems: 'center',
    },
});

AppRegistry.registerComponent('SinglePost', () => SinglePost);
module.exports = SinglePost