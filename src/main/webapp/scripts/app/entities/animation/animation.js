'use strict';

angular.module('selecteurApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('animation', {
                parent: 'entity',
                url: '/animation',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'selecteurApp.animation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/animation/animations.html',
                        controller: 'AnimationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('animation');
                        return $translate.refresh();
                    }]
                }
            })
            .state('animationDetail', {
                parent: 'entity',
                url: '/animation/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'selecteurApp.animation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/animation/animation-detail.html',
                        controller: 'AnimationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('animation');
                        return $translate.refresh();
                    }]
                }
            });
    });
