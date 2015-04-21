'use strict';

angular.module('selecteurApp')
    .factory('Condition', function ($resource) {
        return $resource('api/conditions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.updateDate = new Date(data.updateDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
