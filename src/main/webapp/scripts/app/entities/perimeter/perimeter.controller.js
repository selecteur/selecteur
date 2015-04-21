'use strict';

angular.module('selecteurApp')
    .controller('PerimeterController', function ($scope, Perimeter, Domain, Condition, ParseLinks) {
        $scope.perimeters = [];
        $scope.domains = Domain.query();
        $scope.conditions = Condition.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Perimeter.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.perimeters = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Perimeter.update($scope.perimeter,
                function () {
                    $scope.loadAll();
                    $('#savePerimeterModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Perimeter.get({id: id}, function(result) {
                $scope.perimeter = result;
                $('#savePerimeterModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Perimeter.get({id: id}, function(result) {
                $scope.perimeter = result;
                $('#deletePerimeterConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Perimeter.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePerimeterConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.perimeter = {author: null, updateTime: null, name: null, state: null, isTemplate: null, domainIndex: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
