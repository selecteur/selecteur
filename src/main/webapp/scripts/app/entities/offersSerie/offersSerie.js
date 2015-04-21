'use strict';

angular.module('selecteurApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('offersSerie', {
                parent: 'entity',
                url: '/offersSerie',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'selecteurApp.offersSerie.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/offersSerie/offersSeries.html',
                        controller: 'OffersSerieController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('offersSerie');
                        return $translate.refresh();
                    }]
                }
            })
            .state('offersSerieDetail', {
                parent: 'entity',
                url: '/offersSerie/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'selecteurApp.offersSerie.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/offersSerie/offersSerie-detail.html',
                        controller: 'OffersSerieDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('offersSerie');
                        return $translate.refresh();
                    }]
                }
            });
    });
