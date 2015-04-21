'use strict';

angular.module('selecteurApp')
    .factory('Perimeter', function ($resource) {
        return $resource('api/perimeters/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.updateTime = new Date(data.updateTime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
