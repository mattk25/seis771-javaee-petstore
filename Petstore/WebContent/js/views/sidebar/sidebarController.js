define(
    [
    ],
    function () {
        var changeCount = 0;
        return function ($scope, $state) {
            $scope.links = ['link1', 'link2', 'link3'];
        };
    }
);