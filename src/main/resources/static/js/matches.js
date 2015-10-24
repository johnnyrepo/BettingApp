angular.module('matchesMod', [])
.controller('matchesCtrl',
function($scope, $http, $interval, $rootScope, $filter, $window) {

	$scope.pollAllMatches = function() {
		$http.get('/roster/').success(function(data) {
			// update list of matches
			$scope.matches = data;
			
			// notify bet slip form about the change
			if ($scope.selectedMatchId != undefined) {
				match = $filter('filter')($scope.matches, function (m) {return m.id === $scope.selectedMatchId;})[0];
				odd = undefined;
				if ($scope.selectedMatchType == 'WIN') {
					odd = match.win;
				} else if (($scope.selectedMatchType == 'DRAW')) {
					odd = match.draw;
				} else if ($scope.selectedMatchType == 'LOSE') {
					odd = match.lose;
				}

				$rootScope.$broadcast('matchesUpdatedEvent', odd);
			}
		}).error(function(data, status, headers, config) {
			console.log(status);
		});
	}
	
	$scope.selectMatch = function(matchId, name, type, odd) {
		$scope.selectedMatchId = matchId;
		$scope.selectedMatchType = type;
		$rootScope.$broadcast('matchSelectedEvent', matchId, name, type, odd);
	}

	$scope.$on('$destroy', function() {
		$interval.cancel(stopTime);
	});

	$interval(function() {
		$scope.pollAllMatches();
	}, 3000);

})
.controller('betCtrl',
function($scope, $http, $window) {

	$scope.betSlip = {};
	
	$scope.$on('matchesUpdatedEvent', function(event, odd) {
		$scope.betSlip.odd = odd;
	});
	
	$scope.$on('matchSelectedEvent', function(event, matchId, name, type, odd) {
		$scope.betSlip.matchId = matchId;
		$scope.betSlip.name = name;
		$scope.betSlip.type = type;
		$scope.betSlip.odd = odd;
		});
	
	$scope.placeBet = function() {
		$http.post('/placeBet', $scope.betSlip).success(function(data) {
			$window.alert('Bet placed');
		}).error(function(data, status, headers, config) {
			$window.alert('Failed bet placing.\n' + data.message);
			console.log(status);
		});
	};
	
	$scope.cancelBet = function() {
		$scope.betSlip = {}
	}

});
