'use strict';

angular.module('selecteurApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('condition', {
                parent: 'entity',
                url: '/condition',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'selecteurApp.condition.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/condition/conditions.html',
                        controller: 'ConditionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('condition');
                        return $translate.refresh();
                    }]
                }
            })
            .state('conditionDetail', {
                parent: 'entity',
                url: '/condition/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'selecteurApp.condition.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/condition/condition-detail.html',
                        controller: 'ConditionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('condition');
                        return $translate.refresh();
                    }]
                }
            });
    });
