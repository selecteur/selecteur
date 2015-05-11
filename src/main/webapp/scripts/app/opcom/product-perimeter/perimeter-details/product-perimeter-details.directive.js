/**
 * Created by fabrice on 22/04/15.
 */
'use strict';

angular.module('selecteurApp')
    .controller('productPerimeterDetailsController', function($scope, opcomService){
        opcomService.getConditionForPerimeter($scope.opcom.id,$scope.productPerimeter.id).then(function(conditions){
            $scope.conditions = conditions;
        });

    })
    .directive('productPerimeterDetails', function () {
        return {
            restrict: 'E',
            templateUrl: 'scripts/app/opcom/product-perimeter/perimeter-details/product-perimeter-details.html',
            controller: 'productPerimeterDetailsController',
            scope: {
                opcom: '=',
                productPerimeter: '='
            },
            link: function (scope, element, attrs, controllers) {
                console.log('ici', scope.productPerimeter)

            }
        };
    });