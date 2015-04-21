'use strict';

angular.module('selecteurApp')
    .controller('ConditionController', function ($scope, Condition, Perimeter, ParseLinks) {
        $scope.conditions = [];
        $scope.perimeters = Perimeter.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Condition.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.conditions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Condition.update($scope.condition,
                function () {
                    $scope.loadAll();
                    $('#saveConditionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Condition.get({id: id}, function(result) {
                $scope.condition = result;
                $('#saveConditionModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Condition.get({id: id}, function(result) {
                $scope.condition = result;
                $('#deleteConditionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Condition.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteConditionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.condition = {author: null, updateDate: null, state: null, query: null, nbOffer: null, nbItem: null, nbProduct: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
