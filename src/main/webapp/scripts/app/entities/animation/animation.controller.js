'use strict';

angular.module('selecteurApp')
    .controller('AnimationController', function ($scope, Animation, OffersSerie, ParseLinks) {
        $scope.animations = [];
        $scope.offersseries = OffersSerie.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Animation.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.animations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Animation.update($scope.animation,
                function () {
                    $scope.loadAll();
                    $('#saveAnimationModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Animation.get({id: id}, function(result) {
                $scope.animation = result;
                $('#saveAnimationModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Animation.get({id: id}, function(result) {
                $scope.animation = result;
                $('#deleteAnimationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Animation.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAnimationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.animation = {providerAnimationCode: null, providerListCode: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
