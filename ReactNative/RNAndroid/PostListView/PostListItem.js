 'use strict'
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

var styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#FFFFFF',
        padding: 2,
        margin: 2
    },
    rightContainer: {
        flex: 1,
    },
    title: {
        fontSize: 16,
        marginBottom: 8,
        textAlign: 'left',
        color: "#444"
    },
    year: {
        textAlign: 'center',
    },
    thumbnail: {
        width: 100,
        height: 80,
        padding: 5,
        margin: 6,
    },
    listView: {
        backgroundColor: '#F1F1F1',
    },
});

class PostListView extends Component{
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
            REQUEST_URL:""
        };
    }

    componentDidMount() {
        this.fetchData();
    }

    fetchData() {
        this.setState({
            foot: 2
        });
        this.setState({
            REQUEST_URL : 'http://wangbaiyuan.cn/wp-json/wp/v2/posts?context=embed' + "&page=" + this.state.pageNum
        })
        fetch(this.state.REQUEST_URL)
            .then((response) => response.json())
            .then((responseData) => {
                console.log(REQUEST_URL);
                var j = responseData.length;

                for (var i = 0; i < j; i++) {
                    this.state.totalList.push(responseData[i]);
                }
                this.setState({
                    dataSource: this.state.dataSource.cloneWithRows(this.state.totalList),
                    loaded: true,
                    foot: 1
                });
            }).catch(function (e) {

        }).done();
    }

    render() {
        if (!this.state.loaded) {
            return this.renderLoadingView();
        }

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
                onEndReachedThreshold={200}
                style={styles.listView}
            />

        );
    }

    reloadWordData() {
        return new Promise((resolve) => {
            setTimeout(()=> {
                resolve()
            }, 2000)
        });
    }


    renderLoadingView() {
        return (<View style={styles.container}>
                <Text>文章加载中......</Text>
            </View>

        );
    }
    _onPressButton() {
        // ToastAndroid.show('点击了', ToastAndroid.SHORT);
        // alert('点击了按钮');
        NativeModules.NativeModule.startActivityByClassName("cn.wangbaiyuan.blog.module.post.SinglePostActivity")
        console.log("pressed")
    }
    renderPost(post) {
        var thumb;
        if (post.thumb_url != "null_thumb.by.jpg") {
            thumb = (<Image
                source={{uri: post.thumb_url}}
                style={styles.thumbnail}
            />)

        }
        return (
            <TouchableHighlight
                accessible={true}

                activeOpacity={0.7}
                underlayColor={'#C9E2E7'}
                onPress={this._onPressButton}>
            <View style={styles.container}>
                {thumb}
                <View style={styles.rightContainer}>

                        <Text style={styles.title}>{post.title.rendered}</Text>

                    <Text style={styles.year}>{post.date}</Text>
                </View>
            </View></TouchableHighlight>
        );


    }

    renderFooter() {
        if (this.state.foot === 1) {//加载完毕
            return (
                <View style={{height: 40, alignItems: 'center', justifyContent: 'flex-start',}}>
                    <Text style={{color: '#999999', fontSize: 12, marginTop: 10}}>
                        加载完毕
                    </Text>
                </View>);
        } else if (this.state.foot === 2) {//加载中
            return (
                <View style={{height: 40, alignItems: 'center', justifyContent: 'center',}}>
                    <Text style={{color: '#999999', fontSize: 12, marginTop: 10}}>
                        加载中
                    </Text>
                </View>);
        }
    }

    endReached() {
        // if (this.state.foot != 0) {
        //     return;
        // }
        this.setState({
            foot: 2,
        });
        this.timer = setTimeout(
            () => {
                this.state.pageNum++;
                this.fetchData();
            }, 500);
    }

    componentWillUnmount() {
// 如果存在this.timer，则使用clearTimeout清空。
// 如果你使用多个timer，那么用多个变量，或者用个数组来保存引用，然后逐个clear
        this.timer && clearTimeout(this.timer);
    }
}
AppRegistry.registerComponent('Home',() =>PostListView);
module.exports = PostListView
