'use strict';

angular.module('selecteurApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


