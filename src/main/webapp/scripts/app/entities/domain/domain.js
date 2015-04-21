'use strict';

angular.module('selecteurApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('domain', {
                parent: 'entity',
                url: '/domain',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'selecteurApp.domain.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/domain/domains.html',
                        controller: 'DomainController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('domain');
                        return $translate.refresh();
                    }]
                }
            })
            .state('domainDetail', {
                parent: 'entity',
                url: '/domain/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'selecteurApp.domain.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/domain/domain-detail.html',
                        controller: 'DomainDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('domain');
                        return $translate.refresh();
                    }]
                }
            });
    });
