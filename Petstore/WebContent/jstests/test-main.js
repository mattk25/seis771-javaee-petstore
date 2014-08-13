var tests = [];
for (var file in window.__karma__.files) {
    if (window.__karma__.files.hasOwnProperty(file)) {
        if (/Test\.js$/.test(file)) {
            tests.push(file);
        }
    }
}

requirejs.config({
    // Karma serves files from '/base'
    baseUrl: '/base/js',

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
    },

    // ask Require.js to load these files (all our tests)
    deps: tests,

    // start test run, once Require.js is done
    callback: window.__karma__.start
});