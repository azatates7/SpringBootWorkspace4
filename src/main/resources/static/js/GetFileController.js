mainApp.controller('GetFilesController', function($scope, $http) {

    $scope.allFiles = [];
    $scope.getAllFiles = function() {
        var url = "/rest/getAllFiles";
        $http.get(url).then(
            function(response) { alert("OK");
                $scope.allFiles = response.data;
            },
            function(response) {
                alert("Error: " + response.data);
            }
        );
    };
});