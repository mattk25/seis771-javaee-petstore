define(
    [],

    function () {
        return {
            name: "mainState.facility",
            definition: {
                url: "/facilities",
                resolve: {
                	facilities : function($http){
                        return $http({
                            method: 'GET',
                            url: 'api/facilities'
                        }).success(function (data, status, headers, config) {
                        	return data;

                        }).error(function (data, status, headers, config) {
                            console.log('failed to obtain contracts. response: ' + data);

                        });
                	},
                	submission: function($http) {
                		return $http({
                			method:'GET',
                			url: 'api/submissions/' + 20
                		}).success(function (data, status, headers, config) {
                			console.log(JSON.stringify(data))
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
                        templateUrl: "js/views/facility/facility.html",
                        controller: function ($scope, facilities, $http, $state, submission) {
                            
                            $scope.setSelected = function() {
                                console.log("show", arguments, this);
                                if ($scope.lastSelected) {
                                  $scope.lastSelected.selected = '';
                                }
                                this.selected = 'selected';
                                $scope.lastSelected = this;
                                
                                
                                this.$parent.assignFacility(this.facility.id)
                             }
                                                	
                        	
                        	$scope.facilities = facilities.data;
                        	$scope.submission = submission.data;
                        	
                        	$scope.assignFacility = function(facilityID) {
                        		$scope.submission.facility = {
                        				"id":""
                        		}
                        		
                        		$scope.submission.facility.id = facilityID;
                        		$scope.submission.isAvailable = false;
                        		console.log(facilityID);
                        	}
                        	
                        	$scope.updateFacility = function(subId, facId) {
                        		
                        		$http({
                        			method: 'POST',
                                    url: 'api/submissions/submission/' + $scope.submission.id + '/facility/' +$scope.submission.facility.id ,
                                    headers: {
                                        'Accept': 'application/json',
                                        'Content-Type' : 'application/json'
                                    },
                                    data: $scope.submission
                        		}).success(function (data, status, headers, config) {
                        			$state.go('mainState.home');
                        			return data;

                                }).error(function (data, status, headers, config) {
                                    console.log('failed to obtain submission. response: ' + data);
                                    
                                });
                        	}
                        	
                        	
                        	
                        }
                    }
                }


            }
        };
    }
);