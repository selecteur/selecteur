/**
 * Created by fabrice on 28/04/15.
 */
'use strict';
angular.module('selecteurApp')
    .controller('newCondtionCritereController', function($scope){
        $scope.displayStep1 = function(){
            $scope.$parent.$parent.new = true;
        };

    })
    .directive('newConditionCritere', function(){
        return {
            restrict: 'E',
            replace: true,
            templateUrl : 'scripts/app/opcom/product-perimeter/perimeter-details/new-condition/new-condition-critere.html',
            controller: 'newCondtionCritereController',
            scope: {

            }
        }
    });