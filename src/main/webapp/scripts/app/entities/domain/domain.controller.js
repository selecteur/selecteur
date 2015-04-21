'use strict';

angular.module('selecteurApp')
    .controller('DomainController', function ($scope, Domain, CommercialOperation, Perimeter, OffersSerie, ParseLinks) {
        $scope.domains = [];
        $scope.commercialoperations = CommercialOperation.query();
        $scope.perimeters = Perimeter.query();
        $scope.offersseries = OffersSerie.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Domain.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.domains = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Domain.update($scope.domain,
                function () {
                    $scope.loadAll();
                    $('#saveDomainModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Domain.get({id: id}, function(result) {
                $scope.domain = result;
                $('#saveDomainModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Domain.get({id: id}, function(result) {
                $scope.domain = result;
                $('#deleteDomainConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Domain.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDomainConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.domain = {author: null, updateTime: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
