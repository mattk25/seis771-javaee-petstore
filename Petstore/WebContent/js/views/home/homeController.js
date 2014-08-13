define(
    [
    ],
    function () {

        return function ($scope, $state, getAnimals) {

            // load animals
            $scope.animals = getAnimals.data;

            
            $scope.goToAnimal = function (animalId) {
            	$state.go("mainState.home.animalDetails", {animalId: animalId});
            }
        };
    }
);