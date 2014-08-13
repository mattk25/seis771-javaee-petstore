define(
    ['angular'],

    function (angular) {

        describe('just proof test', function () {

            it('confirms angular is loaded and available to test', function () {
                expect(angular).not.toBe(null);
            });

            it('checks angular version', function () {
                expect(angular.version.codeName).toBe("feisty-cryokinesis");
            });

        });

    });