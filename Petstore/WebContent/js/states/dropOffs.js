define(
    ["views/home/homeController"],

    function (homeController) {
        return {
            name: "mainState.dropOffs",
            definition: {
                url: "/dropoffs",
                resolve: {
                	contracts: function($http){
                        return $http({
                            method: 'GET',
                            url: 'api/contracts' ,
                        }).success(function (data, status, headers, config) {
                        	return data;

                        }).error(function (data, status, headers, config) {
                            console.log('failed to obtain contracts. response: ' + data);

                        });
                	}
                	
                },
                views: {
                    "sidebar@mainState": {
                        templateUrl: "js/views/sidebar/sidebar.html",
                        controller: function ($scope) {
                        }
                    },
                    "content@mainState": {
                        templateUrl: "js/views/dropoffs/dropoffs.html",
                        controller: function ($scope, contracts, $http, $state) {
                        	

                        	
                        	$scope.contracts = contracts.data;
                        	    
                        	
                        	
                        	$scope.goToFacilityAssignment = function(submissionId){
                        		console.log("Where are here");
                        		console.log(submissionId)
                        		$state.go("mainState.facility");
                        	};
                        }
                    }
                }


            }
        };
    }
);