'use strict';

angular.module('selecteurApp')
    .controller('ExpositionDetailController', function ($scope, $stateParams, Exposition, OffersSerie) {
        $scope.exposition = {};
        $scope.load = function (id) {
            Exposition.get({id: id}, function(result) {
              $scope.exposition = result;
            });
        };
        $scope.load($stateParams.id);
    });
