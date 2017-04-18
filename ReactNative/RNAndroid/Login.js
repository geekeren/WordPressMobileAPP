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
    Touchable,
    TouchableOpacity,
    NativeModules,
    Button,
    TextInput,
    ToastAndroid,
    AsyncStorage
} from 'react-native';
var Buffer = require('buffer').Buffer
import WPAPI from 'wpapi'
var wp = new WPAPI({ endpoint: 'http://src.wordpress-develop.dev/wp-json' });
class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {

            userName: '',
            password: ''
        };
    }

    login() {
        var loginParams = {action: "appajaxlogin", userName: this.state.userName, password: this.state.password,}
        let formData = new FormData();
        formData.append("action", "appajaxlogin");
        formData.append("userName", this.state.userName);
        formData.append("password", this.state.password);
        formData.append("remember",true);

    }

    render() {
        return (<View style={styles.container}>
                <View style={{alignItems: "center"}}>
                    <Text style={styles.title}>登录王柏元的博客</Text></View>
                <View style={{borderColor: "#777777", borderWidth: 1, borderRadius: 4, marginBottom: 30}}>
                    <TextInput underlineColorAndroid={'transparent'} style={styles.textInput} autoFocus={true}
                               placeholder={"用户名"} onChangeText={(text)=> {
                        this.setState({userName: text})
                    }}/>
                    <TextInput underlineColorAndroid={'transparent'} style={styles.textInput} secureTextEntry={true}
                               placeholder={"密码"} onChangeText={(text)=> {
                        this.setState({password: text})
                    }}/>
                </View>

                <TouchableOpacity style={styles.button} onPress={()=>this.login()}>
                    <View style={{alignItems: "center", padding: 10}}>
                        <Text style={styles.textstyle}>
                            登录
                        </Text>
                    </View>
                </TouchableOpacity>
                <View style={{padding: 20, flexDirection: "row", alignItems: "center"}}>
                    <View style={{backgroundColor: "#aaaaaa", height: 1, width: 55, margin: 10}}/>
                    <Text style={{flex: 1, justifyContent: "center", alignSelf: "center"}}>社交帐号直接登录</Text>
                    <View style={{backgroundColor: "#aaaaaa", height: 1, width: 55, margin: 10}}/>
                </View>


            </View>
        )
    }


}


var styles = StyleSheet.create({
        textInput: {
            color: "#000000",
            borderColor: "#999999",
            borderRadius: 2,
            borderWidth: 0.5

        },
        title: {

            fontSize: 20,
            color: "#666666",

            marginBottom: 30,
            justifyContent: "center",

        },

        container: {
            padding: 20,
            flex: 1,
            flexDirection: "column",
        },
        button: {

            backgroundColor: '#3194d0',
            borderRadius: 6,
        },
        textstyle: {

            color: "#FFF",
            textAlign: 'center',
            fontSize: 20,
        }
    }
);

AppRegistry.registerComponent('Login', () => Login);
module.exports = Login