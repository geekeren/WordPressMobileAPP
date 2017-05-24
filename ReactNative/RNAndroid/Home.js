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

var { height, width } = Dimensions.get('window')
var TimeUtil = require("./Utils/Time")
var WPAPI = require( 'wpapi' );
var wp = new WPAPI({ endpoint: 'http://wangbaiyuan.cn/wp-json' });
class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            dataSource: new ListView.DataSource({
                rowHasChanged: (row1, row2) => (row1 !== row2 && row1.content != row2.content),
            }),
            loaded: false,
            foot: 0,// 控制foot， 0：隐藏foot  1：已加载完成   2 ：显示加载中
            error: false,
            totalList: new Array(),
            pageNum: 1,
            pageTotalNum: -1,
        };
    }

    componentWillMount() {
        this.fetchData();
    }

    handlerErr(e) {

    }

    fetchData() {

        this.setState({
            foot: 2
        });

        //var thiz = this;
        wp.posts().embed().perPage(10).page(this.state.pageNum)
            .then(function( responseData ) {
                //console.log(responseData);
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
                    foot: 1,
                    error: false
                });
        }.bind(this)).catch(function( err ) {
            this.setState({
                error: true
            })
        }.bind(this));

        // REQUEST_URL = 'http://wangbaiyuan.cn/wp-json/wp/v2/posts?context=embed' + "&page=" + this.state.pageNum;
        // var err = false;
        // fetch(REQUEST_URL)
        //     .then((response) => response.json())
        //     .then((responseData) => {
        //         console.log(REQUEST_URL);
        //         var j = responseData.length;
        //         if (j == 0) {
        //             this.state.pageTotalNum = this.state.pageNum;
        //         }
        //         for (var i = 0; i < j; i++) {
        //             this.state.totalList.push(responseData[i]);
        //         }
        //         this.setState({
        //             dataSource: this.state.dataSource.cloneWithRows(this.state.totalList),
        //             loaded: true,
        //             foot: 1,
        //             error: false
        //         });
        //     }).catch((e) => {
        //         this.setState({
        //             error: true
        //         })
        //     })
        //     .done();
        // this.state.error=err;


    }

    render() {
        if (this.state.loaded === 0 && !(this.state.error)) {
            return this.renderLoadingView();
        } else if (this.state.error)
            return (<View style={styles.loading}>
                <Image style={{ width: 40, height: 40 }} source={{ uri: 'reload' }} /><Text style={{ color: '#666666', fontSize: 15 }}>请检查网络！</Text>
            </View>)
        else
            return (
                <ListView
                    refreshControl={
                        <RefreshControl
                            refreshing={false}
                            onRefresh={this.reloadWordData.bind(this)}
                        />}
                    enableEmptySections={true}
                    dataSource={this.state.dataSource}
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
        return (<View style={styles.loading}>
            <Image style={{ width: 40, height: 40 }} source={{ uri: 'ajax_loader' }} /><Text style={{ color: '#666666', fontSize: 15 }}>文章加载中</Text>
        </View>

        );
    }
    _onPressButton(post) {
        // ToastAndroid.show('点击了', ToastAndroid.SHORT);
        // alert('点击了按钮'+post.title);

        var postArray = new Object()
        postArray["title"] = post.title.rendered;
        postArray["id"] = post.id+""
        postArray["date"] = post.date
        postArray["category"]=post.category
        postArray["modified"] = post.modified
        postArray["thumb_url"] = post.thumb_url
        postArray["link"] = post.link
        postArray["excerpt"] = post.excerpt.rendered
        NativeModules.NativeModule.openUriWithExtras(post._links.self[0].href, postArray)
    }
    renderPost(post) {
        var thumb;
        if (post.thumb_url != "null_thumb.by.jpg") {
            thumb = (<Image
                source={{ uri: post.thumb_url }}
                style={styles.thumbnail}
            />)

        }

        //  NativeModules.NativeModule.timeToString(post.date,"yyyy-MM-dd'T'HH:mm:ss",
        //  (timeStr)=>{
        //     post.date=timeStr
        //      this.setState({
        //     loaded: true
        //     });
        //  })
        var dateStr = new TimeUtil().getTimeString(post.date)
        return (
            <TouchableHighlight
                accessible={true}

                activeOpacity={0.7}
                underlayColor={'#C9E2E7'}
                onPress={() => this._onPressButton(post)}>
                <View style={styles.container}>
                    <View style={styles.topContainer}>
                        <View style={styles.rightContainer}>
                            <View style={{ flexDirection: 'row' }} ><Text style={styles.category}>{post.category}</Text>
                                <Text style={styles.year}>{dateStr}</Text>
                            </View>
                            <Text style={styles.title}
                            >{post.title.rendered}</Text>

                        </View>
                        {thumb}
                    </View>
                    <Text style={{ color: "#444", fontSize: 15, height: 60, lineHeight: 20, overflow: "hidden", paddingLeft: 8, paddingRight: 8 }}
                        numberOfLines={3}
                        ellipsizeMode="middle">{post.excerpt.rendered}</Text>
                </View></TouchableHighlight>
        );


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
        this.timer && clearTimeout(this.timer);
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
    container: {
        flex: 1,
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#FFFFFF',
        shadowColor: '#eeeeee',
        shadowOpacity: 0.6,
        shadowRadius: 3,
        paddingTop: 18,
        paddingBottom: 18,
        paddingLeft: 6,
        paddingRight: 6,
        margin: 2,


    },

    topContainer: {
        flex: 1,
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#FFFFFF',
        shadowColor: '#eeeeee',
        shadowOpacity: 0.6,
        shadowRadius: 3,

        paddingLeft: 6,
        paddingRight: 6,
        margin: 2,


    },
    rightContainer: {
        flex: 1,
    },
    title: {
        fontSize: 18,
        marginBottom: 12,
        marginLeft: 5,
        textAlign: 'left',
        color: "#333",
        fontWeight: "bold",


    },
    year: {
        marginLeft: 8,
        color: '#202020'
    },
    category: {
        color: "#0aa284",

        borderColor: "#E66386",
        borderWidth: 1,
        borderRadius: 4,
        paddingTop: 1,
        fontWeight: "bold",
        marginBottom: 5,
        paddingBottom: 1,
        paddingLeft: 4,
        paddingRight: 4,
        justifyContent: 'center',
        alignItems: "center"
    },
    thumbnail: {
        width: 85,
        height: 85,
        padding: 5,
        margin: 6,
        borderRadius: 4,
        borderColor: '#f0f0f0',
        borderWidth: 1,
    },
    listView: {
        backgroundColor: '#F1F1F1',
    },
});

AppRegistry.registerComponent('Home', () => Home);
module.exports = Home