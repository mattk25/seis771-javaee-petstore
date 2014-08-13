define(
    [],

    function () {
        return {
            name: "mainState.home.animalDetails",
            definition: {
                url: "/animal/:animalId",
                views: {
                    "sidebar@mainState": {
                        templateUrl: "js/views/sidebar/sidebar.html",
                        controller: function ($scope) {
                        }
                    },
                    "content@mainState": {
                        templateUrl: "js/views/animals/animalDetails.html",
                        // getAnimals is automatically inherited from parent state "mainState.home" so no need to fetch the animal from server again
                        controller: function ($scope, $state, $stateParams, getAnimals, $http) {
                        	$scope.adoptionSubmitted = false;
                        	var animals = getAnimals.data;
                        	var animalId = $stateParams.animalId;
                        	if(animals && animalId){
                        		for(var i = 0; i < animals.length; i++){
                        			var current = animals[i];
                        			if(current.id === parseInt(animalId) ){
                        				$scope.animal = current;
                        				break;
                        			}
                        			
                        		}
                        	}
                        	
                        	if(!$scope.animal){
                        		// couldn't find animal so pull it from server
                        		console.log('failed to obtain animal from state');
                        		$http({
                                    method: 'GET',
                                    url: 'api/animals/animal/' + animalId
                                }).success(function (data, status, headers, config) {
                                	$scope.animal = data;
                                }).error(function (data, status, headers, config) {
                                	console.log('failed to obtain animal from server', data, status, headers, config);
                                });
                        		
                        	}
                
                        	
                        	$scope.adopt = function () {
                        		var postBody = {
                        				requestedAnimal: {
                        					id: $scope.animal.id
                        				},
                        				requester: {
                        					id: $scope.userId
                        				},
                        				submitDate: new Date(),
                        				status: 'submitted'
                        		}
                        		
                        		$http({
                         			 method: 'POST',
                                      url: 'api/adoptionRequest' ,
                                      headers: {
                                          'Accept': 'application/json',
                                          'Content-Type' : 'application/json'
                                      },
                                      data: postBody
                                 }).success(function (data, status, headers, config) {
                                	 $scope.adoptionSubmitted = true;

                                 }).error(function (data, status, headers, config) {
                                	 $scope.failure = 'You have already requested to adopt this animal!';

                                 });
                        	}
                        	
                        }
                    }
                }


            }
        };
    }
);