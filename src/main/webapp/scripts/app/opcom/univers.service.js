/**
 * Created by fabrice on 28/04/15.
 */
'use strict';
angular.module('selecteurApp').factory('universService', function(FIREBASE_URL, $q){
    return {
        getAll: function() {
            var universRef = new Firebase(FIREBASE_URL + '/univers');
            var defer = $q.defer();

            universRef.once('value', function(snapshot){
                var univers = snapshot.val();
                univers = _(univers)
                    .chain()
                    .pairs()
                    .map(function(univers){
                        return univers[1];
                    })
                    .value();
                defer.resolve(univers);
            });

            return defer.promise;
        }
    }
});