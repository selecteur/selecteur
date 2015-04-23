/**
 * Created by fabrice on 28/04/15.
 */
'use strict';
angular.module('selecteurApp')
    .controller('newConditionArticlesController', function ($scope, $filter) {

        function computeArticleToDisplayList() {
            $scope.articlesToDisplay = $filter('filter')($scope.articles, $scope.filtre);
            $scope.gridOptions.data = $scope.articlesToDisplay;
        };

        $scope.$watch('articles', function () {
            computeArticleToDisplayList();
        });

        $scope.$watch('filtre', function () {
            computeArticleToDisplayList();
        });



        $scope.gridOptions = {
            paginationPageSizes: [25, 50, 75],
            paginationPageSize: 25,
            enableSorting: true,
            columnDefs: [
                {name: 'Nom', field: 'item.product.title', enableColumnMenu: false, group: 'Product'},
                {name: 'Ean', field: 'item.ean', enableColumnMenu: false, group: 'Product'},
                {name: 'Coloris', field: '', enableColumnMenu: false},
                {name: 'Taille', field: '', enableColumnMenu: false},
                {name: 'Marque', field: 'item.product.brand', enableColumnMenu: false},
                {name: 'Famille', field: 'item.product.noc.family', enableColumnMenu: false},
                {name: 'Sous-famille', field: 'item.product.noc.subfamily', enableColumnMenu: false},
                {name: 'Positionnement', field: 'item.product.positionnement', cellTemplate: '<positionnement data="COL_FIELD"></positionnement>'}
            ],
            data: $scope.articlesToDisplay,
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
            }
        };

        $scope.toggle = function (groupName) {
            console.log(groupName)
            _($scope.gridOptions.columnDefs)
                .chain()
                .filter(function(column){
                    return column.group === groupName
                })
                .forEach(function(column){
                    if (column.visible === true) {
                        column.visible = false;
                    } else {
                        column.visible = true;
                    }
                });
            
            $scope.gridApi.core.refresh();
        }
    })
    .directive('newConditionArticles', function () {
        return {
            restrict: 'E',
            templateUrl: 'scripts/app/opcom/product-perimeter/perimeter-details/new-condition/new-condition-articles.html',
            controller: 'newConditionArticlesController',
            scope: {
                articles: '='
            }
        }
    });