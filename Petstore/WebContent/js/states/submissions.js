define(
    [],

    function () {
        return {
            name: "mainState.submissions",
            definition: {
                url: "/submissions/:userId",
                resolve: {
                	submissions : function($http, $stateParams){
                		var urlPath = 'api/submissions';
                		if($stateParams.userId){
                			urlPath = urlPath + '/user/' + $stateParams.userId
                		}
                        return $http({
                            method: 'GET',
                            url: urlPath ,
                        }).success(function (data, status, headers, config) {
                        	return data;

                        }).error(function (data, status, headers, config) {
                            console.log('failed to resolve animal submissions');

                        });
                		
                	},
                	
                	adoptionSubmissions : function($http, $stateParams){
                		if(!$stateParams.userId){
                			return;
                		}
                		var urlPath = 'api/adoptionRequest/user/' + $stateParams.userId;
                        return $http({
                            method: 'GET',
                            url: urlPath ,
                        }).success(function (data, status, headers, config) {
                        	return data;

                        }).error(function (data, status, headers, config) {
                            console.log('failed to resolve adoption requests for user:' + $stateParams.userId);

                        });
                		
                	},
                	
                	isUserSubmissions: function($stateParams) {
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
                    	templateUrl: "js/views/submissions/submissions.html",
                        controller: function ($scope, $state, submissions, $filter, $stateParams, isUserSubmissions, adoptionSubmissions) {
                        	$scope.noSubmissionMessage = "There aren't any submissions in the system";
                        	if(isUserSubmissions){
                        		$scope.noSubmissionMessage = "You haven't created any submissions yet";
                        	}
                        	if(angular.isDefined(adoptionSubmissions)){
                        		$scope.adoptions = adoptionSubmissions.data;
                        	}
                        	
                        	$scope.submissions = submissions.data;
                        	var statuses = [];
                        	var submissions = $scope.submissions;
                        	for(var i= 0; i < submissions.length; i++){
                        		var submission = submissions[i];
                        		statuses.push({status: submission.status});
                        	}
                        	if(statuses.length >= 1){
                            	$scope.filteredStatuses = $filter('unique')(statuses, 'status');
                            	$scope.filteredStatuses.unshift({status: ""});
                        	}

                        	$scope.goToSubmission = function(submissionId){
                        		$state.go("mainState.submissions.submissionReview", {submissionId: submissionId});
                        	};
                        	
                        	
                        }
                    }
                }


            }
        };
    }
);