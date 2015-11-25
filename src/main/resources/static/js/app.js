var libApp = angular.module("libApp", [])

libApp.controller("routeController", 
	function ($scope)	{
		$scope.inc = {
		    "url":"login.html"
		};
	}
);

libApp.controller("bookController", 
	function ($scope, $rootScope, $http)	{
		$scope.addBook = function()	{
			var request = $http( {
				method  : "POST",
				url		: "/library/add",
				data	: {
					"name" 	: $scope.book.name,
					"author": $scope.book.author,
					"isbn"	: $scope.isbn,
					"category": $scope.category
				},
				headers	: {
					"LIB_AUTH_TOKEN" : $rootScope.authtoken,
					"Accept" : "application/json"
				}
			});
			request.success(
				function (response)	{
					angular.element(document.querySelector("#divstatus")).css("visibility", "visible");
					if (response.status == "success")	{
						angular.element(document.querySelector("#divstatus")).addClass("alert-success");
						$scope.statusmsg="Book has been added successfully!";
						$scope.book = null; 
					}
					else	{
						angular.element(document.querySelector("#divstatus")).addClass("alert-danger");
						$scope.statusmsg="Error:" + response.detail;
					}
				});
			
			request.error(
				function (response)	{
					angular.element(document.querySelector("#divstatus")).css("visibility", "visible");
					angular.element(document.querySelector("#divstatus")).addClass("alert-danger");
					$scope.statusmsg="Error:" + response.detail;
				});
				
		}
	
	});
libApp.controller("loginController", 
		function ($scope, $rootScope, $http)	{
			$scope.login = function()	{
				
				if ($scope.loginkey == "" || $scope.loginkey == null)	{
					$scope.loginkey = "public";
				}
			
				
				var request = $http( {
					method  : "GET",
					url		: "/library/role",
					headers	: {
						"LIB_AUTH_TOKEN" : $scope.loginkey,
						"Accept" : "application/json"
					}
				});
				request.success(
					function (response)	{
						if (response.role == "ROLE_LIBRARIAN")
							$scope.inc.url= "searchbook.html"; //"addbook.html";
						else
							$scope.inc.url= "addbook.html"; //"searchbook.html";
						$rootScope.authtoken = $scope.loginkey;
						
					});
				
				request.error(
					function (response)	{
						angular.element(document.querySelector("#divstatus")).css("visibility", "visible");
						angular.element(document.querySelector("#divstatus")).addClass("alert-danger");
						$scope.statusmsg="Error: " + response.message;
					});
			}
		});

libApp.controller("searchController", 
		function ($scope, $rootScope, $http)	{
			$scope.search = function()	{
				var request = $http( {
					method  : "POST",
					url		: "/library/search",
					headers	: {
						"LIB_AUTH_TOKEN" : $rootScope.authtoken,
						"Accept" : "application/json"
					},
					data : {
						"bookname" : $scope.searchname
					}
				});
				request.success(
					function (response)	{
						angular.element(document.querySelector("#divstatus")).css("visibility", "visible");
						angular.element(document.querySelector("#divstatus")).addClass("alert-success");
						
						if (response.results == null)	{
							$scope.statusmsg = "No books found!";
						}
						else	{	
							$scope.statusmsg = "Books found!";
						}
					});
				
				request.error(
					function (response)	{
						angular.element(document.querySelector("#divstatus")).css("visibility", "visible");
						angular.element(document.querySelector("#divstatus")).addClass("alert-danger");
						$scope.statusmsg="Error: " + response.message;
					});
			}
		});
