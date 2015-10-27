Application is created using Eclipse STS and project can be simply imported into any Eclipse STS workspace as far as project files are present in the project.

To Build and Run.
1. App can be run using a Spring Boot "run configuration", where class "com.oddsring.BettingAppApplication" has to be specified as main.

2. Also app can be built and run from the console with regular Maven commands
* Build and run tests: mvn clean install
* Run: mvn spring-boot:run



Application starts on localhost port 8080. Integration tests are starting on localhost at random available port.

Main functionality for getting list of matches and placing a bet is available here:

GET http://localhost:8080/roster - get list of all the matches

POST http://localhost:8080/placeBet - place a bet to certain match

Reporting functionality can be accessed from here:

GET http://localhost:8080/reports/bets - list of all the bets

GET http://localhost:8080/reports/bets/ip/{ip} - all bets by ip (localhost is either 127.0.0.1 or 0:0:0:0:0:0:0:1)

GET http://localhost:8080/reports/bets/type/{betType} - all bets by type {'WIN', 'LOSE', 'DRAW'}

GET http://localhost:8080/reports/bets/{betType}ip/{ip} - all bets by type {'WIN', 'LOSE', 'DRAW'} and ip

GET http://localhost:8080/reports/bets/{betType}match/{matchName} - all bets by type {'WIN', 'LOSE', 'DRAW'} and match name

DELETE http://localhost:8080/reports/bets/{id} - delete bet by it's id(timestamp, when the bet was created)


Implementation.
Coefficients of the matches are updated every 2-5 seconds.
UI part is done in Angular JS and matches are requested by poll requests every 3 seconds.