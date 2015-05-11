/**
 * Created by fabrice on 20/04/15.
 */
'use strict';

angular.module('selecteurApp').
    directive('productPerimeter', function(){
        return {
            restrict: 'E',
            templateUrl: 'scripts/app/opcom/product-perimeter/product-perimeter.html',
            //controller: 'opcomDetailsController',
            scope:{
                opcom: '=',
                productPerimeter:'='
            },
            link: function(scope, element, attrs, controllers){
                console.log('ici', scope.productPerimeter)

            }
        };
    });