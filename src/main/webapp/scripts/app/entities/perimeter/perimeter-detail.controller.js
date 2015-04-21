'use strict';

angular.module('selecteurApp')
    .controller('PerimeterDetailController', function ($scope, $stateParams, Perimeter, Domain, Condition) {
        $scope.perimeter = {};
        $scope.load = function (id) {
            Perimeter.get({id: id}, function(result) {
              $scope.perimeter = result;
            });
        };
        $scope.load($stateParams.id);
    });
