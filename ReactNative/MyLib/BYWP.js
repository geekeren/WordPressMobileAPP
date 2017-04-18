
import React, {Component} from 'react';
import {
    AsyncStorage
} from 'react-native';
var WPAPI = require( 'wpapi' );
var singleWp = (function(){

    var BYWP;



    function getInstance(){

        if( BYWP === undefined ){

           var apiPromise = WPAPI.discover( 'http://my-site.com' ).then(function( site ) {
                return site.auth({
                    username: 'admin',
                    password: 'always use secure passwords'
                });
            });
            apiPromise.then(function( site ) {
                BYWP = site;
            })

        }

        return BYWP;

    }



    function Construct(){


    }



    return {

        getInstance : getInstance

    }

})();
var BYWP =singleWp.getInstance();
module.exports = BYWP;