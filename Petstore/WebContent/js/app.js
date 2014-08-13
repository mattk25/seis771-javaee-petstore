define(
    [
        "angular",
        "states/mainState",
        "states/home",
        "states/examples",
        "states/submission",
        "states/submissions",
        "states/submissionReview",
        "states/contract",
        "states/animalCare",
        "states/dropOffs",
        "states/adoptions",
        "states/adoptionReview",
        "states/animalDetails",
        "states/facility",
        "ui-bootstrap",
        "angular-ui-router",
        "ng-grid",
        "jquery",
        "checklist-model"
    ],
    function (angular, 
    		  mainAbstract, 
    		  home, 
    		  examples, 
    		  submission, 
    		  submissions, 
    		  submissionReview, 
    		  contract,
    		  animalCare,
    		  dropOffs,
    		  adoptions,
    		  adoptionReview,
		  animalDetails,
		  facility) {

        var petStore = angular.module('petStore', ['ui.router', 'ui.bootstrap', 'ngGrid', 'checklist-model']);


        /** states registration **/
        petStore.config(function ($stateProvider, $urlRouterProvider) {
        	
            $urlRouterProvider.otherwise("/home");
            $stateProvider.state(mainAbstract.name, mainAbstract.definition);
            $stateProvider.state(home.name, home.definition);
            $stateProvider.state(examples.name, examples.definition);
            $stateProvider.state(submission.name, submission.definition);
            $stateProvider.state(submissions.name, submissions.definition);
            $stateProvider.state(submissionReview.name, submissionReview.definition);
            $stateProvider.state(contract.name, contract.definition);
            $stateProvider.state(animalCare.name, animalCare.definition);
            $stateProvider.state(dropOffs.name, dropOffs.definition);
            $stateProvider.state(adoptions.name, adoptions.definition);
            $stateProvider.state(adoptionReview.name, adoptionReview.definition);
            $stateProvider.state(animalDetails.name, animalDetails.definition);
            $stateProvider.state(facility.name, facility.definition);
        });
        
        /** filters **/
        
        /** copied from https://github.com/angular-ui/ui-utils/blob/master/modules/unique/unique.js **/
        petStore.filter('unique', ['$parse', function ($parse) {

        	  return function (items, filterOn) {

        	    if (filterOn === false) {
        	      return items;
        	    }

        	    if ((filterOn || angular.isUndefined(filterOn)) && angular.isArray(items)) {
        	      var newItems = [],
        	        get = angular.isString(filterOn) ? $parse(filterOn) : function (item) { return item; };

        	      var extractValueToCompare = function (item) {
        	        return angular.isObject(item) ? get(item) : item;
        	      };

        	      angular.forEach(items, function (item) {
        	        var isDuplicate = false;

        	        for (var i = 0; i < newItems.length; i++) {
        	          if (angular.equals(extractValueToCompare(newItems[i]), extractValueToCompare(item))) {
        	            isDuplicate = true;
        	            break;
        	          }
        	        }
        	        if (!isDuplicate) {
        	          newItems.push(item);
        	        }

        	      });
        	      items = newItems;
        	    }
        	    return items;
        	  };
        	}]);


        /** directives registrations **/


        /** filters registrastion **/

        /** interceptor to handle login */
        petStore.config(function($httpProvider) {
	
           $httpProvider.interceptors.push(function($rootScope, $q, $injector){
               var loginInProgress = false;
               var savedRequests = [];

               $rootScope.$on('loginSuccess', function(){
                   loginInProgress = false;
                   $injector.invoke(function ($http) {
                       for (var i = 0, l = savedRequests.length; i < l; i++) {
                           var req = savedRequests[i];

                           $http(req.config).then(function (response) {
                               req.defer.resolve(response);
                           });
                       }
                   });

               });
               return {
                   'request': function(config){
                       var authorization = window.sessionStorage.getItem('authorization');
                       if(authorization){
                           config.headers['Authorization'] = 'Basic '+authorization;
                       }
                       return config;

                   },
                   'responseError' : function(rejection){
                       // authorization required. at this point we would need to broadcast authentication required
                       if(rejection.status && rejection.status === 401){
                           if(loginInProgress === false) {
                               loginInProgress = true;
                               $rootScope.$broadcast("loginRequired", window.location.hash);
                           }
                           // user is already prompted to login, queue requests to be processed after login is successful
                           var defer = $q.defer();
                           savedRequests.push({config: rejection.config, defer: defer});
                           return defer.promise;

                       // another type of rejection just pass it along
                       } else {
                           return $q.reject(rejection);
                       }
                   }
               }
           }) ;
        });



        return petStore;

    }
);