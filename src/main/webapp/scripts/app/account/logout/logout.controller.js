'use strict';

angular.module('selecteurApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
