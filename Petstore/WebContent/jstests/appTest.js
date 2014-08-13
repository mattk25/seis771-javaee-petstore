define(
    ['app'],

    function (app) {
        describe("Test our angular app", function () {
            it("Should have module name 'petStore", function () {
                expect(app.name).toBe('petStore');
            });
        });
    }
);