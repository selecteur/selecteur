'use strict';

angular.module('selecteurApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('perimeter', {
                parent: 'entity',
                url: '/perimeter',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'selecteurApp.perimeter.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/perimeter/perimeters.html',
                        controller: 'PerimeterController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('perimeter');
                        return $translate.refresh();
                    }]
                }
            })
            .state('perimeterDetail', {
                parent: 'entity',
                url: '/perimeter/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'selecteurApp.perimeter.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/perimeter/perimeter-detail.html',
                        controller: 'PerimeterDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('perimeter');
                        return $translate.refresh();
                    }]
                }
            });
    });
