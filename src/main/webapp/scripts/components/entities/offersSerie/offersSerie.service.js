'use strict';

angular.module('selecteurApp')
    .factory('OffersSerie', function ($resource) {
        return $resource('api/offersSeries/:id', {}, {
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
