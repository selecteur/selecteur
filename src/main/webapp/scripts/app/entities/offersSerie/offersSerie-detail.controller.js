'use strict';

angular.module('selecteurApp')
    .controller('OffersSerieDetailController', function ($scope, $stateParams, OffersSerie, Domain, Animation, Exposition) {
        $scope.offersSerie = {};
        $scope.load = function (id) {
            OffersSerie.get({id: id}, function(result) {
              $scope.offersSerie = result;
            });
        };
        $scope.load($stateParams.id);
    });
