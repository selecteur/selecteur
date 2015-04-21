'use strict';

angular.module('selecteurApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('exposition', {
                parent: 'entity',
                url: '/exposition',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'selecteurApp.exposition.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/exposition/expositions.html',
                        controller: 'ExpositionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('exposition');
                        return $translate.refresh();
                    }]
                }
            })
            .state('expositionDetail', {
                parent: 'entity',
                url: '/exposition/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'selecteurApp.exposition.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/exposition/exposition-detail.html',
                        controller: 'ExpositionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('exposition');
                        return $translate.refresh();
                    }]
                }
            });
    });
