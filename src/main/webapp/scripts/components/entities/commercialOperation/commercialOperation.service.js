'use strict';

angular.module('selecteurApp')
    .factory('CommercialOperation', function ($resource) {
        return $resource('api/commercialOperations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
