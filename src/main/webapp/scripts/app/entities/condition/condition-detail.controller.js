'use strict';

angular.module('selecteurApp')
    .controller('ConditionDetailController', function ($scope, $stateParams, Condition, Perimeter) {
        $scope.condition = {};
        $scope.load = function (id) {
            Condition.get({id: id}, function(result) {
              $scope.condition = result;
            });
        };
        $scope.load($stateParams.id);
    });
