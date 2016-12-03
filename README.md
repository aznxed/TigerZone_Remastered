![](http://i68.tinypic.com/2yyz9u9.jpg)
#Welcome to the TigerZone_Remastered wiki!

##Team Members
__(FirstName LastName -> GitHub Username)__
* Edward Wu -> aznxed
* Spencer Hartwick -> SHartwick
* Ryan Burroughs -> rburrou
* Marisa Fernandez -> marisagfernandez
* Skyler Burgoyne -> smburgoyne
* Luc Olsthoorn -> Luc-Olsthoorn

##How to Build the client
__We recommend using Eclipse. Which can be found at https://eclipse.org/downloads/ This project can be opened in eclipse by clicking "Open Project from File System" You may have to remove __
![](http://i64.tinypic.com/25koqrk.png)

1. Download the ZIP from the repository and unzip it
2. Open up a command prompt or terminal and change directory into the folder
3. Execute __javac -cp "junit.jar" .\src\Game\*.java .\src\testing\*.java__

*Make sure that you have Java installed and environmental variables set up

##How to Run the Client
1. Navigate to the src folder using command prompt or terminal
2. Execute java -classpath . Game.tcpClient [IP ADDRESS] [PORT] [TOURNAMENT PASSWORD] [USERNAME] [PASSWORD]

__ex. java -classpath . Game.tcpClient 10.136.73.30 4444 TIGERZONE TEAMO IAMO__

##Unit and Acceptance Tests
__"You could test all your business rules, all your presenters...without the application server, the web running, anything running, at 30,000 feet, while sipping a gin and tonic" - Robert C. Martin__

###Unit Testing
For unit testing, we decided to use JUnit. JUnit is a unit testing framework for Java programming language. Our Junit test file can be found under src/Game/Junit.java. We have set up the tests so they run in alphabetical order. 
 
* TestA: tests that a tile is being placed on the board
* TestB: tests tiles that are placed on a board can be retrieved
* TestC: tests all tiles successfully placed on the board are being placed into the placedTiles list
* TestD: tests that the isValid method is returning true for a valid move and false for an invalid move
* TestE: tests that the getNeighbors method returns the correct number of neighbors for a tile
* TestF: tests that the rotateTile method is properly rotating tiles
* TestG: tests that the getValidOrients and getPossibleMoves methods are returning the correct number of valid orientations and possible moves for a tile respectively
 
These unit tests are testing the core game logic that our AI depends on to make valid moves.  

In Eclipse: 
 * Simply click on the Junit.java file
 * Click on the green run button in the top left corner. The tests will all run in the order they are shown. 
 * To run just one test, simply highlight its method name and hit the green run button. Keep in mind this is only okay for the first few tests. The later tests rely on the previous tests to pass. 

In command line/terminal:
 * Go to the downloaded project directory
 * To compile enter: javac Board.java Junit.java JunitRunner.java 
 * To run enter: java JunitRunner 
 * If all the tests pass you will get true

###Acceptance Testing
Testing was done using the a student's server at the University of Florida's CISE department and was tested against other AI's to ensure valid moves. 

##How to Run Tests
To run individual tests, simply highlight the method and run it as a Junit test.

##Difficulties and Challenges
__Shared Code__

One of the biggest difficulties is establishing a shared code. Even though GitHub and version control were used, not all members on the team were familiar with using it. This challenge was overcome by emailing code and integrating it in existing code. However, this solution is imperfect. With respect to GitHub's contributions analysis, it appears that some members added very little, if anything at all, which is usually not the case. For future improvement, we hope to further develop on our experience with Git. 

__Shared Understanding__

Establishing a shared understanding of code was another difficulties. Teams were established: game, AI, and TCPProtocol teams. When it came time to consolidate code, it became difficult to understand how to each other team's code worked. This was issue was addressed towards the end of the project by implementing pair programming. Each member, worked with a member of another team to establish a shared understanding of each others code. 

__Being Agile__

Agile methods work well with smaller groups. As the group become bigger, constant communication becomes increasingly difficult. We started the project by writing a product backlog. Each week, we were to pull an item from the product backlog and create a sprint backlog and work on each item in that log individually and put our work together at the end of the week. In theory, this sounded very probable. However, in addition to encountering the above problems, as a team we weren't able to meet efficiently due to schedule conflicts and other commitments. Additionally, we did not follow the logs well and were continually behind schedule.  

##Architecture and Design
__"...architecture is the decisions that you wish you could get right early in a project" - Martin Fowler__

###UML Activity Diagram
The following is a UML activity diagram that was developed prior to writing code.
![](http://i66.tinypic.com/v3dk3s.png) 

###UML Structural Diagram
The following is a UML structure diagram that was developed prior to writing code.
![](http://i63.tinypic.com/34qv11v.png)

The entire application is written in Java. 

The game was built using behavior-driven design principles. Functions and methods were written with ubiquitous language. For example, Deck.getTop() gets the top tile in the deck. Deck.isEmpty() checks if the deck is empty. Variables are assigned as they would be while playing an actual game. For example, while holding a tile, it does not have a coordinate or rotation, we can only see what's on the tile. Only when we place the tile on the board, does the coordinate and rotation become significant.

The UI was developed in it's own class and is independent of the game. According to Robert Martin, a good architecture hangs the UI off application like an appendix. So, that it can be removed if it doesn't work. 

The TCP client 

##Process
## User Stories
* As a player, I want to play against an AI 
* As a player, I want to place a tile with/without a rotation on the board so that I can make a move.  
* As a player, I want to check the score and number of meeples so that I can see who’s winning.
* As a player, I want to see the remaining tiles so that I can calculate my odds.
* As a player, I want to see the valid board placements for a tile so that I can make my move easier. 
* As a server, I want to send tiles to the players.
* As a server, I want to receive moves from the players. 
* As a server, I want to check valid placement of tiles.
* As a server, I want to score completed tile chains. 
* As a server, I want to declare a victor for the game. 

## Sprints
![](http://i66.tinypic.com/23pm5u.png)
![](http://i65.tinypic.com/33jmydd.png)
![](http://i64.tinypic.com/2uxwco8.png)
![](http://i67.tinypic.com/24v04xz.png)
![](http://i67.tinypic.com/33kdh5l.png)
