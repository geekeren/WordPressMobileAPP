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
    Dimensions
} from 'react-native';
var TimeUtil = require("./Utils/Time")
var { height, width } = Dimensions.get('window')



class ShuoShuo extends Component {
    constructor(props) {
        super(props);
        this.state = {
            dataSource: new ListView.DataSource({
                rowHasChanged: (row1, row2) => row1 !== row2,
            }),
            loaded: false,
            foot: 0,// 控制foot， 0：隐藏foot  1：已加载完成   2 ：显示加载中
            error: false,
            totalList: new Array(),
            pageNum: 1,
            pageTotalNum: -1
        };
    }

    componentDidMount() {
        this.fetchData();
    }

    fetchData() {
        this.setState({
            foot: 2
        });
        REQUEST_URL = 'http://wangbaiyuan.cn/wp-json/wp/v2/shuoshuo' + "?page=" + this.state.pageNum;
        fetch(REQUEST_URL)
            .then((response) => response.json())
            .then((responseData) => {
                console.log(REQUEST_URL);
                var j = responseData.length;

                if (j == 0) {
                    this.state.pageTotalNum = this.state.pageNum;
                }
                for (var i = 0; i < j; i++) {
                    this.state.totalList.push(responseData[i]);
                }
                this.setState({
                    dataSource: this.state.dataSource.cloneWithRows(this.state.totalList),
                    loaded: true,
                    foot: 1
                });
            }).catch(function (e) {
                console.log(e);
            }).done();
    }

    render() {
        // if (!this.state.loaded) {
        //     return this.renderLoadingView();
        // }

        return (

            <ListView
                refreshControl={
                    <RefreshControl
                        refreshing={false}
                        onRefresh={this.reloadWordData.bind(this)}
                    />}
                enableEmptySections={true}
                dataSource={this.state.dataSource}
                renderHeader={this.renderHeader.bind(this)}
                renderFooter={this.renderFooter.bind(this)}
                renderRow={this.renderPost.bind(this)}
                onEndReached={this.endReached.bind(this)}
                onEndReachedThreshold={height / 5}
                style={styles.listView}
            />


        );
    }

    reloadWordData() {
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve()
            }, 2000)
        });
    }


    renderLoadingView() {
        return (<View style={styles.container}>
            <Image style={{ width: 25, height: 25 }} source={{ uri: 'ajax_loader' }} /><Text style={{ color: '#999999', fontSize: 15 }}>文章加载中......</Text>
        </View>

        );
    }
    _onPressButton(post) {
        
        var postArray = new Object()
        
        postArray["content"] = post.content.raw
        NativeModules.ShuoShuoModule.share(postArray)
    }
    renderPost(post) {
        var thumb;
        if (post.thumb_url != "null_thumb.by.jpg") {
            thumb = (<Image
                source={{ uri: post.thumb_url }}
                style={styles.thumbnail}
            />)

        }
        var dateStr = new TimeUtil().getTimeString(post.date)
        return (

            <View style={styles.container}>

                <Text style={styles.title}>『{post.title.rendered}』</Text>
                <TouchableHighlight
                    accessible={true}

                    activeOpacity={0.7}
                    underlayColor={'#C9E2E7'}
                    onLongPress={() => this._onPressButton(post)}>
                    <Text style={styles.content}>{post.content.raw}</Text>
                </TouchableHighlight>
                {thumb}
                <Text style={styles.year}>{dateStr}</Text>
            </View>

        );


    }

    renderHeader() {

        return (
            <View style={{ flex: 1, height: 250, backgroundColor: "#FFF" }}>
                <Image style={{ height: 225, width: width, resizeMode: 'cover', }} source={{ uri: 'shuoshuo_header_img' }}>
                </Image>
                <View style={{ flexDirection: "row", position: "absolute", bottom: 0, right: 10 }}>

                    <Text style={{ marginTop: 20, fontSize: 18, fontWeight: "bold", color: "#FFFFFF" }}>王柏元</Text>
                    <Image style={{ height: 80, width: 80, marginLeft: 8, borderColor: "#FFF", borderWidth: 2 }} source={{ uri: 'shuoshuo_head' }} />


                </View>
            </View>)
    }

    renderFooter() {
        if (this.state.foot === 1) {//加载完毕
            return (
                <View style={{ height: 40, alignItems: 'center', justifyContent: 'flex-start', }}>
                    <Text style={{ color: '#999999', fontSize: 12, marginTop: 10 }}>
                        加载完毕
                    </Text>
                </View>);
        } else if (this.state.foot === 2) {//加载中
            return (
                <View style={{ height: 40, alignItems: 'center', justifyContent: 'center', flexDirection: "row" }}>
                    <Image style={{ width: 25, height: 25 }} source={{ uri: 'ajax_loader' }} />
                    <Text style={{ color: '#999999', fontSize: 15 }}>
                        加载中
                    </Text>
                </View>);
        }
    }

    endReached() {
        // if (this.state.foot != 0) {
        //     return;
        // }
        if (this.state.pageTotalNum == -1) {
            this.setState({
                foot: 2,
            });

            this.state.pageNum++;
            this.fetchData();
        } else {
            this.state.foot = 1
        }


    }

    componentWillUnmount() {
        // 如果存在this.timer，则使用clearTimeout清空。
        // 如果你使用多个timer，那么用多个变量，或者用个数组来保存引用，然后逐个clear
        // this.timer && clearTimeout(this.timer);
    }


}


var styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'flex-start',
        backgroundColor: '#FFFFFF',
        shadowColor: '#eeeeee',
        shadowOpacity: 0.6,
        shadowRadius: 3,
        padding: 8,
        margin: 3,
        borderBottomWidth: 1,
        borderColor: "#F0F0F0"


    },

    title: {
        fontSize: 14,
        textAlign: 'left',
        color: "#082642",



    },
    content: {
        fontFamily: 'artfont',
        fontSize: 18,
        alignItems: "center",
        marginTop: 12,
        marginBottom: 12,
        marginLeft: 5,
        marginRight: 5,
        textAlign: 'left',
        color: "#111",



    },
    year: {
        color: '#072642'
    },
    thumbnail: {
        width: 82,
        height: 82,
        padding: 5,
        margin: 6,
        borderRadius: 4,
        borderColor: '#f0f0f0',
        borderWidth: 1
    },
    listView: {
        backgroundColor: '#FFF',
    },
});

AppRegistry.registerComponent('ShuoShuo', () => ShuoShuo);
module.exports = ShuoShuo