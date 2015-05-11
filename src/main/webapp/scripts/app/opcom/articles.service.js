/**
 * Created by fabrice on 28/04/15.
 */
'use strict';
angular.module('selecteurApp').factory('articlesService', function(FIREBASE_URL, $q){
    return {
        getAll : function(){
            var articlesRef = new Firebase(FIREBASE_URL + '/articles');
            var defer = $q.defer();

            articlesRef.once('value', function(snapshot){
                var articles = snapshot.val();
                articles = _(articles)
                    .chain()
                    .pairs()
                    .map(function(article){
                        return article[1];
                    })
                    .value();
                defer.resolve(articles);
            });

            return defer.promise;
        }
    }
});