define(
    [],

    function () {
        return {
            name: "mainState.submissions.submissionReview",
            definition: {
                url: "/submission/:submissionId",
                resolve: {
                	submission : function($http, $stateParams){
                        return $http({
                            method: 'GET',
                            url: 'api/submissions/' + $stateParams.submissionId ,
                        }).success(function (data, status, headers, config) {
                        	return data;

                        }).error(function (data, status, headers, config) {
                            console.log('failed to resolve submission');

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
                    	templateUrl: "js/views/submissions/submissionReview.html",
                        controller: function ($scope, $state, $http, submission) {
                        	var sendReviewDescision = function(decision, submissionId){
                        		// at this point a staff member has made a decision record his id as STAFF_ID stored on submission in database
                        		$scope.submission.staff = {"id": $scope.userId};
                        		$http({
                          			 method: 'POST',
                                       url: 'api/submissions/' + $scope.submission.id + "/" + decision ,
                                       headers: {
                                           'Accept': 'application/json',
                                           'Content-Type' : 'application/json'
                                       },
                                       data: $scope.submission
                                  }).success(function (data, status, headers, config) {
                                	// go to parent state (submissions)
                                  	$state.go('^', null, {reload:true});

                                  }).error(function (data, status, headers, config) {
                                      alert('Failed to ' + decision + ' submission');
                                      console.log('object is: ', JSON.stringify($scope.submission));

                                  });
                        		
                        	};
                        	
                        	$scope.submission = submission.data;
                        	$scope.submissionStates = ['Rejected', 'Approved'];
                        	$scope.approveSubbmission = function(){
                        		$scope.submission.status="Approved";
                        		sendReviewDescision('approve');
                        	};
                        	
                        	$scope.rejectSubmission = function(){
                        		$scope.submission.status="Rejected";
                        		sendReviewDescision('reject');
                        		
                        	};
                        }
                    }
                }


            }
        };
    }
);