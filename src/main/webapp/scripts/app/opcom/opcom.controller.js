/**
 * Created by fabrice on 20/04/15.
 */
'use strict';

angular.module('selecteurApp')
    .controller('OpcomController', function ($scope,translatePartialLoader, opcom, perimeters) {
        $scope.opcom = opcom;
        $scope.perimeters = perimeters;

    });
