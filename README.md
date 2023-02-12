# Stone-Paper-Scissors
The **Stone Paper Scissors** game presented as a simple single-page Web app

## Description
The game is a single page Web app. it can be played in two modes:

[1] **Unlimited mode**: Enter player name and select Unlimited rounds to keep playing Indefinitely.

[2] **Limited mode**: Enter player name and Number of rounds to keep playing until you finish. After this however, one can extend the rounds and (also, deselect 'New Game' here) keep playing the same game.

Also, once you've configured the Number of rounds, one can not select 'Unlimited Rounds' but can keep extending using the 'Number of Rounds' option. If one wishes then to play an infinite game, he needs to enter a new name to begin a fresh game. This is by design.

## Installation / Project Setup
The Application consists of two Web applications containing a Spring Boot based Backend and an Angular single-page Frontend. 

The Backend application can be run by selecting `GameApplication->Run as -> Spring Boot App`. The application runs at port `8080`.
The Frontend application can be run in two ways:

[1] From command-line, run the command `npm install` and then `ng build` to generate folder `dist`. Go to `dist` and do a `ng-serve` OR

[2] By simply running commands `npm install` and `ng serve` through command line at the folder containing `package.json`. 

[3] Finally, navigate to `http://localhost:4200/`.
