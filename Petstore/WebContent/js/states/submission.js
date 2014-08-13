define(
    ["views/home/homeController"],

    function (homeController) {
        return {
            name: "mainState.submission",
            definition: {
                url: "/submission",
                resolve: {
                	// this need to change to actual end point to get all possible species
                	species: function($http){
                        return $http({
                            method: 'GET',
                            url: 'api/animals/species' ,
                        }).success(function (data, status, headers, config) {
                        	return data;

                        }).error(function (data, status, headers, config) {
                            console.log('failed to resolve breeds');

                        });
               		
                	},                 	
                	getUserId: function() {
                		return window.sessionStorage.getItem('userId');
                	}

                	
                },
                views: {
                    "sidebar@mainState": {
                        templateUrl: "js/views/sidebar/sidebar.html",
                        controller: function ($scope) {
                        }
                    },
                    "content@mainState": {
                        templateUrl: "js/views/submission/submission.html",
                        controller: function ($scope, getUserId, species, $http, $state) {
                        	                        	
                        	$scope.species = species.data;
                        	$scope.sexes = ['M', 'F'];
                        	
                        	$scope.resolveSpeciesAndVaccines = function(specie) {
                        		if(specie.name){
                        			$scope.submission.animal.specie = specie;
                        			$scope.submission.animal.breed.name = "";
                                    $http({
                                        method: 'GET',
                                        url: 'api/animals/' + specie.name + '/breeds' ,
                                    }).success(function (data, status, headers, config) {
                                    	$scope.breeds = data;

                                    }).error(function (data, status, headers, config) {
                                        console.log('failed to resolve breeds');

                                    });
                                    
                                    $http({
                                        method: 'GET',
                                        url: 'api/animals/' + specie.name + '/vaccines' ,
                                    }).success(function (data, status, headers, config) {
                                    	$scope.vaccines = data;

                                    }).error(function (data, status, headers, config) {
                                        console.log('failed to resolve breeds');

                                    });
                        			
                        		}
                        		
                        	};
                        	
                        	
                        	$scope.selectedVaccines = [];
                        	
                        	$scope.submission = {
                        			submitDate: null,
                        		    submissionContraints: "",
                        		    person: {
                        		    	id: ""
                        		    },
                        		    animal: {
                        		    	name: "",
                        		    	age: "",
                        		    	color: "",
                        		    	sex: "",
                        		    	specie: {
                        		    		name: ""
                        		    	},
                        		    	breed: {
                        		    		name: ""
                        		    	},
                            		    medicalRecord: {
                            		    	vetName: "",
                            		    	lastVisit: new Date(),
                            		    	animalCondition: "",
                            		    	vaccines: []
                            		    }
                        		    	
                        		    }
                        			
                        	};
                        	
                        	$scope.submiSubmission = function(){
                        		var userId = window.sessionStorage.getItem('userId');
                        		if(! userId){
                        			// user is not logged in yet force login
                        			$state.go('mainState.submission');
                        			return;
                        		}
                        		$scope.submission.person.id = userId;
                        		$scope.submission.submitDate = new Date();
                        		for(var i = 0 ; i < $scope.selectedVaccines.length ; i ++){
                        			var vaccine = $scope.selectedVaccines[0];
                        			if(vaccine.name && vaccine.id){
                        				$scope.submission.animal.medicalRecord.vaccines.push({vaccine: {name: vaccine.name, id: vaccine.id}});
                        			}
                        		}
                        		
                        		$scope.submission.status="Submitted";
                        		$http({
                        			 method: 'POST',
                                     url: 'api/submissions',
                                     headers: {
                                         'Accept': 'application/json',
                                         'Content-Type' : 'application/json'
                                     },
                                     data: $scope.submission
                                }).success(function (data, status, headers, config) {
                                	// we successfully created the submission, go to home
                                	$state.go('mainState.home');

                                }).error(function (data, status, headers, config) {
                                    alert('failed to create submission');
                                    console.log('object is: ', JSON.stringify($scope.submission));

                                });
                        		
                        		
                        	};
                        	
                                  $scope.maxDate = new Date();
                        		  $scope.showWeeks = true;
                        		  $scope.toggleWeeks = function () {
                        		    $scope.showWeeks = ! $scope.showWeeks;
                        		  };

                        		  // Disable future date pick
                        		  $scope.disabled = function(date, mode) {
                        			var today = new Date();
                        		    return ( mode === 'day' && (date > today) );
                        		  };

                        		  $scope.toggleMin = function() {
                        		    $scope.minDate = ( $scope.minDate ) ? null : new Date();
                        		  };
                        		  $scope.toggleMin();

                        		  $scope.open = function($event) {
                        		    $event.preventDefault();
                        		    $event.stopPropagation();

                        		    $scope.opened = true;
                        		  };

                        		  $scope.dateOptions = {
                        		    'year-format': "'yy'",
                        		    'starting-day': 1
                        		  };

                        		  $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'shortDate'];
                        		  $scope.format = $scope.formats[0];
                        }
                    }
                }


            }
        };
    }
);