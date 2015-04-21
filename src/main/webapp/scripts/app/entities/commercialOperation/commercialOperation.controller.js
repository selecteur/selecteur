'use strict';

angular.module('selecteurApp')
    .controller('CommercialOperationController', function ($scope, CommercialOperation, Domain, ParseLinks) {
        $scope.commercialOperations = [];
        $scope.domains = Domain.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            CommercialOperation.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.commercialOperations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            CommercialOperation.update($scope.commercialOperation,
                function () {
                    $scope.loadAll();
                    $('#saveCommercialOperationModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            CommercialOperation.get({id: id}, function(result) {
                $scope.commercialOperation = result;
                $('#saveCommercialOperationModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            CommercialOperation.get({id: id}, function(result) {
                $scope.commercialOperation = result;
                $('#deleteCommercialOperationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            CommercialOperation.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCommercialOperationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.commercialOperation = {providerCode: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
