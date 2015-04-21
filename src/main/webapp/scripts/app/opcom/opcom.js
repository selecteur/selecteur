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
                        templateUrl: 'scripts/app/opcom/opcom.html',
                        controller: 'OpcomController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('opcom');
                        return $translate.refresh();
                    }],
                    opcom: function(){
                        return {
                            title: 'Informations sur l\'opération commerciale',
                            productPerimeter: [{
                                title: 'PAP Homme RED'
                            },{
                                title: 'Robe courte'
                            },{
                                title: 'Polo été'
                            }]
                        }
                    }
                }
            });
    });

