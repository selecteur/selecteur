/**
 * Created by fabrice on 20/04/15.
 */
'use strict';

angular.module('selecteurApp').
    directive('opComDetails', function(){
        return {
            restrict: 'E',
            templateUrl: 'scripts/app/opcom/opcom-details/opcom-details.html',
            //controller: 'opcomDetailsController',
            scope:{
                opcom:'='
            },
            link: function(scope, element, attrs, controllers){
                console.log('ici', scope.opcom)

            }
        };
    });
