define(
    [],

    function () {
        return {
            name: "mainState.contract",
            definition: {
                url: "/contracts/{type}/:contractId",
                resolve: {
                	
                	contract : function($http, $stateParams){
   		
                		return $http({
                            method: 'GET',
                            url: 'api/contracts/' + $stateParams.type + '/' + $stateParams.contractId ,
                        }).success(function (data, status, headers, config) {
                        	return data;

                        }).error(function (data, status, headers, config) {
                            console.log('failed to resolve contract');

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
                    	templateUrl: "js/views/contracts/contract.html",
                        controller: function ($scope, $state, $http, contract, $stateParams) {
                        	
                        	$scope.type = $stateParams.type
                        	$scope.agree=false;
                        	$scope.contract = contract.data;                      	
                        	$scope.contract.pickUpDate = new Date();
                        	$scope.contract.dropOffDate = new Date();
                        	$scope.minDate = new Date();
                  		  $scope.open = function($event) {
                  		    $event.preventDefault();
                  		    $event.stopPropagation();

                  		    $scope.opened = true;
                  		  };
                  		  
                  		  $scope.isAdoption = function() {
                  			  console.log($stateParams.type)
                  			  return $stateParams.type == 'adoption'
                  		  }
                  		  
                  		  $scope.submitContract = function () {
                  			$scope.contract.signature = 'yes';
                      		$http({
                      			 method: 'POST',
                                   url: 'api/contracts',
                                   headers: {
                                       'Accept': 'application/json',
                                       'Content-Type' : 'application/json'
                                   },
                                   data: $scope.contract
                              }).success(function (data, status, headers, config) {
                              	// we successfully created the contract
                              	$state.go('mainState.home');

                              }).error(function (data, status, headers, config) {
                                  alert('failed to create contract');
                                  console.log('object is: ', JSON.stringify($scope.contract));

                              });
                  			  
                  			  
                  		  }
                        }
                    }
                }


            }
        };
    }
);