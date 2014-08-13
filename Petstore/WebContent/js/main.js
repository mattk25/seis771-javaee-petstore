requirejs.config({
    baseUrl: 'js/',
    paths: {
        'angular': '../lib/angular',
        'angular-ui-router': '../lib/angular-ui-router',
        'ui-bootstrap': '../lib/ui-bootstrap-tpls-0.10.0',
        'ng-grid': '../lib/ng-grid-2.0.7.debug',
        'jquery': '../lib/jquery-1.11.0',
        'checklist-model': '../lib/checklist-model'


    },
    shim: {
        'angular': {
            exports: 'angular'

        },

        'angular-ui-router': {
            deps: [
                'angular'
            ]
        },

        'ui-bootstrap': {
            deps: [
                'angular'
            ]
        },

        'ng-grid': {
            deps: [
                'angular',
                'jquery'
            ]
        },
        
        'checklist-model': {
        	deps: [
        	       'angular'
        	]
        }
    }
});

define(
    [
        "angular",
        "app"

    ],
    function (angular, petStore) {

        petStore.run();
        angular.bootstrap(document, [petStore.name]);

    }
);