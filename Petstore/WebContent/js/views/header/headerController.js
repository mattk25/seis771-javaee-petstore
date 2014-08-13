define(
    [
    ],
    function () {
        return function ($rootScope, $scope, $state) {
        	$scope.loginUser = function () {
        		$scope.$emit('loginRequired');
        	};
        	
        	$scope.logoutUser = function () {
        		window.sessionStorage.removeItem('authorization');
        		window.sessionStorage.removeItem('userName');
                window.sessionStorage.removeItem('userId');
                window.sessionStorage.removeItem('isAdmin');
                // 
                $rootScope.isLoggedIn = false;
                $rootScope.isAdmin = false;
                $rootScope.userId = null;
                $state.go('mainState.home');
        		
        	};
        	
        	
        };
    }
);