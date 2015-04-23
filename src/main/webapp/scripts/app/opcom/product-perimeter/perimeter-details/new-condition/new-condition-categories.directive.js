/**
 * Created by fabrice on 28/04/15.
 */
'use strict';
angular.module('selecteurApp')
    .controller('newConditionCategoriesController', function(){

    })
    .directive('newConditionCategories', function(){
        return {
            restrict: 'E',
            replace: true,
            templateUrl: 'scripts/app/opcom/product-perimeter/perimeter-details/new-condition/new-condition-categories.html',
            controller: 'newConditionCategoriesController',
            scope: {

            }
        }
    });
