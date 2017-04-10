var mainApp = angular.module('mainApp', ['ui.router']);
mainApp.controller('LoginController', ['$scope', function($scope) {
      $scope.enter = function () {
          $scope.user = {
              email: "",
              pass: ""
          };
        if (($scope.user.email.length) === 0
            || ($scope.user.pass.length === 0)) {
            var input = $("<input>")
                .attr("type", "submit")
                .attr("name", "mydata");
            $('#form1').append($(input));
            $('<input type="submit">').hide().appendTo.$(form[name='loginForm']).click().remove();
        }
    }
}]);
mainApp.config(['$stateProvider','$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
    $stateProvider.state("login", {
        url: '/login',
        views: {
            'body': {
                controller: 'LoginController',
                templateUrl: 'login.html'
            }
        }
    });
    $urlRouterProvider.otherwise("/");
}]);
$(document).ready(function () {
    $(".btn").click(function(event) {
        // Removes focus of the button.
        $(this).blur();
    });
});