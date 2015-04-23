/**
 * Created by fabrice on 11/05/15.
 */
"use strict";

angular.module('selecteurApp').factory('attributesService', function ($http) {

    return {
        getAttributeCode: function (code) {
            return $http.get('api/attributes/code/' + code, {cache: true}).then(function (response) {
                return response.data;
            })

        },
        getAttributeValue: function (value) {
            return $http.get('api/attributes/value/' + value, {cache: true}).then(function (response) {
                return response.data;
            })

        }
    }

});