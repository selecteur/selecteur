'use strict';

angular.module('selecteurApp')
    .controller('OffersSerieController', function ($scope, OffersSerie, Domain, Animation, Exposition, ParseLinks) {
        $scope.offersSeries = [];
        $scope.domains = Domain.query();
        $scope.animations = Animation.query();
        $scope.expositions = Exposition.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            OffersSerie.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.offersSeries = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            OffersSerie.update($scope.offersSerie,
                function () {
                    $scope.loadAll();
                    $('#saveOffersSerieModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            OffersSerie.get({id: id}, function(result) {
                $scope.offersSerie = result;
                $('#saveOffersSerieModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            OffersSerie.get({id: id}, function(result) {
                $scope.offersSerie = result;
                $('#deleteOffersSerieConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OffersSerie.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOffersSerieConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.offersSerie = {author: null, updateDate: null, query: null, name: null, offersIds: null, inputAction: null, outputAction: null, state: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
