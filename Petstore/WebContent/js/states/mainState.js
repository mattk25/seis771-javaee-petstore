define(
    [
        "views/header/headerController",
        "views/sidebar/sidebarController"
    ],

    function (headerController, sidebarController) {
        return {
            name: "mainState",
            definition: {
                abstract: true,
                resolve: {
                    authenticate: function ($rootScope, $modal, $state, $urlRouter) {
                    	var toState, toStateParams;
                        $rootScope.user = {
                        };
                        
                        

                  		$rootScope.$on('$stateChangeError',function(event, toState, toParams, fromState, fromParams){
                  		  console.log('$stateChangeError - fired when an error occurs during transition.');
                  		  console.log(arguments);
                  		});
                  		$rootScope.$on('$stateChangeSuccess',function(event, toState, toParams, fromState, fromParams){
                  		  console.log('$stateChangeSuccess to '+toState.name+'- fired once the state transition is complete.');
                  		});
                  		// $rootScope.$on('$viewContentLoading',function(event, viewConfig){
                  		//   // runs on individual scopes, so putting it in "run" doesn't work.
                  		//   console.log('$viewContentLoading - view begins loading - dom not rendered',viewConfig);
                  		// });
                  		$rootScope.$on('$viewContentLoaded',function(event){
                  		  console.log('$viewContentLoaded - fired after dom rendered',event);
                  		});
                  		$rootScope.$on('$stateNotFound',function(event, unfoundState, fromState, fromParams){
                  		  console.log('$stateNotFound '+unfoundState.to+'  - fired when a state cannot be found by its name.');
                  		  console.log(unfoundState, fromState, fromParams);
                  		});
                        
                        
                        
                        $rootScope.$on('$stateChangeStart', function (ev, to, toParams, from, fromParams) {
                            toState = to;
                            toStateParams = toParams;
                            
                        });
                        //Handle login required event
                        $rootScope.$on('loginRequired', function (event, hash) {

                            $modal.open({
                            	backdrop: false,
                                templateUrl: 'js/views/partials/login-form.html',
                                controller: function($modalInstance, $q, $state, $http, $scope){
                                    $scope.loginFailed = false;
                                	$scope.user.userName = '';
                                	$scope.user.password = '';
                                    
                                    $scope.cancel = function() {
                                    	$modalInstance.close();
                                    }

                                    $scope.login = function(){
                                        var userName = $scope.user.userName;
                                        var password = $scope.user.password;
                                        var userPassword = btoa(userName+":"+password);
                                        $http({
                                            method: 'GET',
                                            url: 'api/people/person/' + userName ,
                                            headers: {'Authorization': 'Basic '+userPassword}
                                        }).success(function (data, status, headers, config) {
                                            window.sessionStorage.setItem('authorization', userPassword);
                                            window.sessionStorage.setItem('userName', userName);
                                            window.sessionStorage.setItem('userId', data.id);
                                            // we need to set isAdmin in both $rootScope and session so if user refreshes the page we still know
                                            // it is an admin via sessionStorage
                                            window.sessionStorage.setItem('isAdmin', false);
                                            $rootScope.userId = data.id;
                                            var group = data.group;
                                            if(group && group.name && group.name === 'admin'){
                                            	window.sessionStorage.setItem('isAdmin', true);
                                            	$rootScope.isAdmin = true;
                                            }
                                            $rootScope.$broadcast('loginSuccess', hash);
                                            $modalInstance.close();

                                        }).error(function (data, status, headers, config) {
                                            $scope.logFailed = true;

                                        });

                                    };

                                    $scope.showRegister = function(){
                                        $modalInstance.close();
                                        $rootScope.$broadcast('registrationRequired');
                                    }

                                }
                            });


                        });
                        
                        $rootScope.$on('loginSuccess', function (event, hash) {
                        	$rootScope.isLoggedIn = true;
                        	 if (toState && toState.name && toStateParams) {
                                 $state.go(toState.name, toStateParams);
                             } else {
                             	if(hash){
                             		$urlRouter.sync();
                            	} else {
                            		$state.go('mainState.home');
                            	}
                             	
                             }

                        	
                               

                        });

                        $rootScope.$on('registrationRequired', function(){
                            $modal.open({
                                templateUrl: 'js/views/partials/registration-form.html',
                                controller: function($modalInstance, $q, $state, $http, $scope){
                                	
                                	$scope.user.userName = '';
                                	$scope.user.password = '';
                                	
                                    $scope.register = function(){
                                        $scope.user.group = {
                                          description: "User Group",
                                          name: "user"
                                        };

                                        $http({
                                            method: 'POST',
                                            url: 'api/people',
                                            headers: {
                                                'Accept': 'application/json',
                                                'Content-Type' : 'application/json'
                                            },
                                            data: $scope.user
                                        }).success(function (data, status, headers, config) {
                                            var userName = $scope.user.userName;
                                            var password = $scope.user.password;
                                            var userPassword = btoa(userName+":"+password);
                                            window.sessionStorage.setItem('authorization', userPassword);
                                            window.sessionStorage.setItem('userName', userName);
                                            window.sessionStorage.setItem('userId', data.id);
                                            // we need to set isAdmin in both $rootScope and session so if user refreshes the page we still know
                                            // it is an admin via sessionStorage
                                            window.sessionStorage.setItem('isAdmin', false);
                                            $rootScope.userId = data.id;
                                            $rootScope.isAdmin = false;
                                            var group = data.group;
                                            if(group && group.name && group.name === 'admin'){
                                            	window.sessionStorage.setItem('isAdmin', true);
                                            	$rootScope.isAdmin = true;
                                            }
                                            $modalInstance.close();
                                            $rootScope.$broadcast('loginSuccess');


                                        }).error(function (data, status, headers, config) {
                                            $modalInstance.close();
                                            alert('failed to register');

                                        });

                                    };

                                }
                            });

                        });

                        return;

                    }

                },
                onEnter: function($rootScope) {
                	$rootScope.isAdmin = window.sessionStorage.getItem('isAdmin');
                },
                views: {
                    "header": {
                        templateUrl: "js/views/header/header.html",
                        controller: headerController
                    },
                    "mainEntry": {
                        templateUrl: "js/views/mainEntry.html",
                        controller: sidebarController
                    }
                }


            }
        };

    }
);