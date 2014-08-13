define(
    function () {
        return {
            name: "mainState.examples",
            definition: {
                url: "/examples",
                views: {
                    "sidebar@mainState": {
                        templateUrl: "js/views/examples/sidebar.html",
                        controller: function ($scope) {
                            $scope.sidebar = 'Examples!';
                        }
                    },
                    "content@mainState": {
                        templateUrl: "js/views/examples/content.html",
                        controller: function ($scope, $interval, $http) {

                            $canStop = false;
                            $scope.myprogress = 0;
                            var intervalPromise;
                            $scope.handleProgress = function () {
                                $scope.canStop = true;
                                if ($scope.myprogress > 0) {
                                    $scope.myprogress = 0;
                                }
                                intervalPromise = $interval(function () {
                                    if ($scope.myprogress >= 100) {
                                        $interval.cancel(intervalPromise);
                                        return;
                                    } else {
                                        $scope.myprogress += 4;
                                    }
                                }, 200);


                            };

                            $scope.stopProgress = function () {
                                if (intervalPromise) {
                                    $interval.cancel(intervalPromise);
                                }
                                $scope.myprogress = 0;
                                $scope.canStop = false;

                            };

                            $scope.visit = function () {
                                // make this disappear
                                $scope.mydata = null;
                                $http({
                                    method: 'GET',
                                    url: 'https://api.github.com/users/mralexgray/repos'
                                }).success(function (data, status, headers, config) {
                                    $scope.mydata = data;
                                }).error(function (data, status, headers, config) {

                                });

                            };

                            $scope.myData = [
                                {name: "Moroni", age: 50},
                                {name: "Tiancum", age: 43},
                                {name: "Jacob", age: 27},
                                {name: "Nephi", age: 29},
                                {name: "Enos", age: 34}
                            ];
                            $scope.gridOptions = {
                                data: 'myData',
                                enableCellSelection: true,
                                enableRowSelection: false,
                                enableCellEditOnFocus: true,
                                columnDefs: [
                                    {field: 'name', displayName: 'Name', enableCellEdit: true},
                                    {field: 'age', displayName: 'Age', enableCellEdit: true}
                                ]
                            };
                        }
                    }
                }


            }
        };
    }
);