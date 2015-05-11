/**
 * Created by fabrice on 24/04/15.
 */
'use strict';
angular.module('selecteurApp')
    .controller('newConditionController', function($scope, $http, universService, articlesService, attributesService){

        $scope.new = true;

        $scope.toggle = function(){
            if($scope.new) {
                $scope.new = false;
            } else {
                $scope.new = true;
            }
        };

        universService.getAll().then(function(univers){
            $scope.univers = univers;
            console.log($scope.univers);
        });

        articlesService.getAll().then(function(articles){
            var promises = []
            _(articles)
                .forEach(function(article){

                    //attributesService.get
                    //promises.push()
                    //promises.push()
                });

            $q.all(promises).then(function(articles){
                $scope.articles = articles;
            });

        });


    })
    .directive('newCondition', function(){
        return {
            restrict: 'E',
            templateUrl:'scripts/app/opcom/product-perimeter/perimeter-details/new-condition/new-condition.html',
            controller: 'newConditionController',
            scope:{},
            replace: false,
            link: function(scope){
                console.log('new condition');
                scope.attributes = ['COLORIS'];
                scope.groupBys = ['SKU'];
            }
        }
    });