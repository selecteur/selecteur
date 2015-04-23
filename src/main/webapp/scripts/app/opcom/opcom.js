/**
 * Created by fabrice on 20/04/15.
 */
'use strict';

angular.module('selecteurApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('opcom', {
                parent: 'site',
                url: '/opcom',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'opcom.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/opcom/opcom-list/opcom-list.html',
                        controller: 'OpcomListController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('opcom');
                        return $translate.refresh();
                    }],
                    opcomList: function (opcomService) {
                        return opcomService.listOpcom();
                    }
                }
            })
            .state('opcom-details', {
                parent: 'site',
                url: '/opcom/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'opcom.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/opcom/opcom.html',
                        controller: 'OpcomController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('opcom');
                        return $translate.refresh();
                    }],
                    opcom: function ($stateParams, opcomService) {
                        return opcomService.getById($stateParams.id);
                    },
                    perimeters: function ($stateParams, opcomService) {
                        return opcomService.getPerimetersByOpcomId($stateParams.id);
                    }
                }
            });
    });

