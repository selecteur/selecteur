'use strict';

angular.module('selecteurApp')
    .controller('AnimationDetailController', function ($scope, $stateParams, Animation, OffersSerie) {
        $scope.animation = {};
        $scope.load = function (id) {
            Animation.get({id: id}, function(result) {
              $scope.animation = result;
            });
        };
        $scope.load($stateParams.id);
    });
