define(
    [],

    function () {
        return {
            name: "mainState.adoptions",
            definition: {
                url: "/adoptions",
                resolve: {
                	adoptions : function($http, $stateParams){
                		var urlPath = 'api/adoptionRequest';
                        return $http({
                            method: 'GET',
                            url: urlPath ,
                        }).success(function (data, status, headers, config) {
                        	console.log(data)
                        	return data;

                        }).error(function (data, status, headers, config) {
                            console.log('failed to resolve adoptions');

                        });
                		
                	}, 
                	
                	isUserAdoptions: function($stateParams) {
                		var result = false;
                		if($stateParams.userId){
                			result = true;
                		}
                		return result;
                	}
                	
                },
                views: {
                    "sidebar@mainState": {
                        templateUrl: "js/views/sidebar/sidebar.html",
                        controller: function ($scope) {
                        }
                    },
                    "content@mainState": {
                    	templateUrl: "js/views/adoption/adoptions.html",
                        controller: function ($scope, $state, adoptions, $filter, $stateParams, isUserAdoptions) {
                        	$scope.noAdoptionMessage = "There aren't any adoptions in the system";
                        	if(isUserAdoptions){
                        		$scope.noAdoptionMessage = "You haven't created any adoptions yet";
                        	}
                        	$scope.adoptions = adoptions.data;
                        	var statuses = [];
                        	var adoptions = $scope.adoptions;
                        	for(var i= 0; i < adoptions.length; i++){
                        		var adoption = adoptions[i];
                        		statuses.push({status: adoption.status});
                        	}
                        	if(statuses.length >= 1){
                            	$scope.filteredStatuses = $filter('unique')(statuses, 'status');
                            	$scope.filteredStatuses.unshift({status: ""});
                        	}

                        	$scope.goToAdoption = function(adoptionId){
                        		$state.go("mainState.adoptions.adoptionReview", {adoptionId: adoptionId});
                        	};
                        	
                        	
                        }
                    }
                }


            }
        };
    }
);