/**
 * Created by fabrice on 22/04/15.
 */
'use strict';
angular.module('selecteurApp')
    .directive('condition',function(){
        return {
            restrict: 'E',
            templateUrl: 'scripts/app/opcom/product-perimeter/perimeter-details/condition/condition.html',
            //controller: 'opcomDetailsController',
            scope: {
                condition: '='
            },
            link: function (scope, element, attrs, controllers) {
                console.log('ici', scope.condition);

            }
        };
    });
