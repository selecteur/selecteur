'use strict';

angular.module('selecteurApp')
    .controller('ExpositionController', function ($scope, Exposition, OffersSerie, ParseLinks) {
        $scope.expositions = [];
        $scope.offersseries = OffersSerie.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Exposition.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.expositions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Exposition.update($scope.exposition,
                function () {
                    $scope.loadAll();
                    $('#saveExpositionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Exposition.get({id: id}, function(result) {
                $scope.exposition = result;
                $('#saveExpositionModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Exposition.get({id: id}, function(result) {
                $scope.exposition = result;
                $('#deleteExpositionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Exposition.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteExpositionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.exposition = {id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
