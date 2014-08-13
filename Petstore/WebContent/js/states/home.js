define(
    ["views/home/homeController"],

    function (homeController) {
        return {
            name: "mainState.home",
            definition: {
                url: "/home",
                resolve: {
                	getAnimals : function($http) {
                        return $http({
                            method: 'GET',
                            url: 'api/animals'
                        }).success(function (data, status, headers, config) {
                            return data;
                        }).error(function (data, status, headers, config) {

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
                        templateUrl: "js/views/home/home.html",
                        controller: homeController
                    }
                }


            }
        };
    }
);