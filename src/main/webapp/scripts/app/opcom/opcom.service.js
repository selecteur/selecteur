/**
 * Created by fabrice on 22/04/15.
 */
'use strict';

angular.module('selecteurApp')
    .factory('opcomService', function ($http, $q, $rootScope, FIREBASE_URL) {
        return {
            listOpcom: function () {
                return $http.get('/api/commercialOperations').then(function(response){
                    return response.data;
                });
            },
            getById: function(opcomId) {
                return $http.get('/api/commercialOperations/' + opcomId).then(function(response){
                    return response.data;
                })
            },
            getPerimetersByOpcomId: function(opcomId) {
                return $http.get('/api/commercialOperations/' + opcomId + '/perimeters').then(function(response){
                    return response.data;
                });
            },
            getConditionForPerimeter: function(opcomId,perimeterId) {
                // uncomment when API ok
                //return $http.get('/api/commercialOperations/' + opcomId + '/perimeters/' + perimeterId + '/conditions').then(function(response){
                //    return response.data;
                //});
                // use firebase mock instead
                var conditionsRef = new Firebase(FIREBASE_URL + '/condition');
                var defer = $q.defer();
                var conditions = [];

                conditionsRef.orderByChild("perimeter").startAt(perimeterId).endAt(perimeterId).on('child_added', function(snapshot){
                    $rootScope.$apply(function(){
                        conditions.push(snapshot.val());
                    });

                });

                defer.resolve(conditions);
                return defer.promise;
            }
        }
    });

