'use strict';

describe('Petstore App', function() {
	
	var login = function(user, password) {
		element(by.buttonText('Login')).click();
		element(by.model('user.userName')).sendKeys(user);
		element(by.model('user.password')).sendKeys(password);
		element(by.buttonText('Sign in')).click();
	};
	
	

	it('should redirect /Petstore to /Petstore/#/home', function() {
		browser.get('Petstore');
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('#')[1]).toBe('/home');
		});
	});

	it('should show 3 animals', function() {

		var animalList = element.all(by.repeater('animal in animals'));
		expect(animalList.count()).toBe(3);
	});

	it('should prompt user to login', function() {
		expect(element(by.buttonText('Cancel')).isPresent()).toBe(false)
		element(by.buttonText('Login')).click();
		var cancel = element(by.buttonText('Cancel'));
		expect(cancel.isPresent()).toBe(true);
		cancel.click();
	});

	it('should logout user and direct to home page when user logs out', function() {

				login('user1', 'user1');
				
				element(by.xpath('//a[@ui-sref="mainState.submission"]')).click();

				var logoutBtn;
				logoutBtn = element(by.buttonText('Logout'));
				expect(logoutBtn.isPresent()).toBe(true);

				logoutBtn.click().then(function() {
					browser.getLocationAbsUrl().then(function(url) {
						expect(url.split('#')[1]).toBe('/home');
					});
				});
	});
	
	it('should show admin show submissions & View Scheduled Drop Offs', function() {
		login('admin', 'admin');
		var showSubmissions = element(by.xpath('//a[@ui-sref="mainState.submissions"]'));
		var showDropOffs = element(by.xpath('//a[@ui-sref="mainState.dropOffs"]'));
		
		showSubmissions.click().then(function() {
					browser.getLocationAbsUrl().then(function(url) {
						expect(url.split('#')[1]).toBe('/submissions');
					});
		});
		
		showDropOffs.click().then(function() {
			browser.getLocationAbsUrl().then(function(url) {
				expect(url.split('#')[1]).toBe('/dropoffs');
			});
		});
		
		
		
		element(by.buttonText('Logout')).click();
		
	});
	
	
});
