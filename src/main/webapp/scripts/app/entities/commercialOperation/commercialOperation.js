'use strict';

angular.module('selecteurApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('commercialOperation', {
                parent: 'entity',
                url: '/commercialOperation',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'selecteurApp.commercialOperation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/commercialOperation/commercialOperations.html',
                        controller: 'CommercialOperationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('commercialOperation');
                        return $translate.refresh();
                    }]
                }
            })
            .state('commercialOperationDetail', {
                parent: 'entity',
                url: '/commercialOperation/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'selecteurApp.commercialOperation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/commercialOperation/commercialOperation-detail.html',
                        controller: 'CommercialOperationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('commercialOperation');
                        return $translate.refresh();
                    }]
                }
            });
    });
