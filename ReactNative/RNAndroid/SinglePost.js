/**
 * Created by BrainWang on 2017-02-27.
 */

import React, { Component } from 'react';
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
    WebView
} from 'react-native';

var PostRender = require('./Utils/PostUtil')

class SinglePost extends Component {

    constructor(props) {
        super(props);
        this.state = {
            loaded: 0,
            error: false,
            postData: new Object(),
        };

    }
    componentDidMount() {
        this.fetch_data();

    }

    fetch_data() {
        fetch(this.props.restLink)
            .then((Response) => (Response.json()))
            .then((post) => {
                this.setState({
                    postData: post,
                    error: false,
                    loaded: 1
                });
                console.log(this.state.title)
                console.log(post.title.rendered)

            }).catch((e) => {
                this.setState({
                    error: true
                })
            }).done();

    }
    render() {
        if (this.state.loaded === 0 && !(this.state.error)) {

            return (<View style={styles.loading}>
                <Image style={{ width: 40, height: 40 }} source={{ uri: 'ajax_loader' }} /><Text style={{ color: '#666666', fontSize: 15 }}>文章加载中</Text>
            </View>)
        } else if (this.state.error) {
            return (<View style={styles.loading}>
                <Image style={{ width: 40, height: 40 }} source={{ uri: 'reload' }} /><Text style={{ color: '#666666', fontSize: 15 }}>错误</Text>
            </View>)
        }
        else {
            return (

                <WebView style={{
                    height: 500,
                }}
                    source={{ html: PostRender(this.state.postData) }} />
            )
        }

        // var HTML =
        // console.log(HTML)

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