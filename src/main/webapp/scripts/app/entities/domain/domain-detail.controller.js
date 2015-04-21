'use strict';

angular.module('selecteurApp')
    .controller('DomainDetailController', function ($scope, $stateParams, Domain, CommercialOperation, Perimeter, OffersSerie) {
        $scope.domain = {};
        $scope.load = function (id) {
            Domain.get({id: id}, function(result) {
              $scope.domain = result;
            });
        };
        $scope.load($stateParams.id);
    });
