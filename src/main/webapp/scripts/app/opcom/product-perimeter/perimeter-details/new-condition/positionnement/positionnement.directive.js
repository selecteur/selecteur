/**
 * Created by fabrice on 06/05/15.
 */
'use strict';

angular.module('selecteurApp').directive('positionnement', function(){
    return {
        restrict: 'E',
        scope: {
            data: '='
        },
        templateUrl: 'scripts/app/opcom/product-perimeter/perimeter-details/new-condition/positionnement/positionnement.html'
    }
});