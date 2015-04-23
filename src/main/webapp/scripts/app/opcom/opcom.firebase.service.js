/**
 * Created by fabrice on 23/04/15.
 */
'use strict';

angular.module('selecteurApp')
    .factory('opcomServiceFirbase', function ($http, $q, FIREBASE_URL) {
        return {
            listOpcom: function () {
                var opcomRef = new Firebase(FIREBASE_URL + '/opcom');
                var defer = $q.defer();

                opcomRef.once('value', function (snapshot) {
                    defer.resolve(_(snapshot.val()).pairs());
                }, function (error) {
                    defer.reject(error);
                });
                return defer.promise;
            },
            getById: function (opcomId) {
                var opcomRef = new Firebase(FIREBASE_URL + '/opcom/' + opcomId);
                var defer = $q.defer();
                var self = this;
                opcomRef.once('value', function (snapshot) {
                    var opCom = snapshot.val();
                    opCom.opcom = opcomId;
                    self.getDomainByOpcomId(opcomId).then(function (domain) {
                        opCom.domain = _(domain).filter({'opcom': opcomId});
                        defer.resolve(opCom);
                    }).catch(function (error) {
                        defer.reject(error);
                    });
                }, function (error) {
                    defer.reject(error);
                });
                return defer.promise;
            },
            getDomainByOpcomId: function (opcomId) {
                var domainRef = new Firebase(FIREBASE_URL + '/domain');
                var defer = $q.defer();

                domainRef.once('value', function (snapshot) {
                    var domains = snapshot.val();
                    domains.forEach(function (domain, index) {
                        domain.domain = index;
                    });
                    defer.resolve(domains);
                }, function (error) {
                    defer.reject(error);
                });
                return defer.promise;
            }
        }
    });