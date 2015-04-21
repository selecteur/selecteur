'use strict';

angular.module('selecteurApp')
    .factory('Domain', function ($resource) {
        return $resource('api/domains/:id', {}, {
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
