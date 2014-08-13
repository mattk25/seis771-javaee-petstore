define(
    [],

    function () {
        return {
            name: "mainState.animalCare",
            definition: {
            	url:"/animalCare",
                views: {
                    "sidebar@mainState": {
                        templateUrl: "js/views/sidebar/sidebar.html",
                        controller: function ($scope) {
                        }
                    },
                    "content@mainState": {
                    	templateUrl: "js/views/animalCare/animalCare.html",
                             
                        	controller: function ($scope, $state, $http) {                        	
                        		
                        		$scope.careRecord = {
                        				"animal": {
                        					"id": null 
                        				},
                        				"comment": "",
                        				"gaveWater": false,
                        				"gaveFood": false,
                        				"recordTimeStamp": null
                        		};
                        		
                        		$scope.updateTable = function() {
                        			console.log("BUtton clicked")
                        			
                        			$scope.getRecords();
                        		};
                        			
                        		$scope.records = null
                        		$scope.getRecords = function() {
                        			$http({
                           			 method: 'GET',
                                        url: 'api/animalCare/animal/' + $scope.careRecord.animal.id,
                                        headers: {
                                            'Accept': 'application/json',
                                            'Content-Type' : 'application/json'
                                        },
                                        data: $scope.existingAnimal
                                   }).success(function (data, status, headers, config) {
                                   	// we successfully created the submission, go to home
                                   	//$state.go('mainState.home');
                                	   $scope.records = data;	
                                	   console.log($scope.existingAnimal)
                                   }).error(function (data, status, headers, config) {
                                       $scope.records=null;
                                       alert('failed to create submission');
                                       console.log('object is: ', JSON.stringify($scope.submission));
                                   });
                        		};
                        		
                        		$scope.createRecord = function() {
                        			$scope.careRecord.recordTimeStamp = new Date();
                        			
                        			$http({
                            			 method: 'POST',
                                         url: 'api/animalCare/animal/' + $scope.careRecord.animal.id,
                                         headers: {
                                             'Accept': 'application/json',
                                             'Content-Type' : 'application/json'
                                         },
                                         data: $scope.careRecord
                                    }).success(function (data, status, headers, config) {
                                    	// we successfully created the submission, go to home
                                    	$scope.records = data;
                                    	//$state.go('mainState.home');

                                    }).error(function (data, status, headers, config) {
                                        alert('failed to create submission');
                                        console.log('object is: ', JSON.stringify($scope.submission));

                                    });
                        		}
                        }
                    }

            }
        }
        };
    }
);