/**
 * Created by fabrice on 28/04/15.
 */
'use strict';
angular.module('selecteurApp').filter('startAt', function(){
    return function(array, startIndex){
        return _(array).rest(startIndex);
    }
});