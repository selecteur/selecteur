'use strict';

angular.module('selecteurApp')
    .controller('CommercialOperationDetailController', function ($scope, $stateParams, CommercialOperation, Domain) {
        $scope.commercialOperation = {};
        $scope.load = function (id) {
            CommercialOperation.get({id: id}, function(result) {
              $scope.commercialOperation = result;
            });
        };
        $scope.load($stateParams.id);
    });
