# Anotation

The project *hardestgame-ea* is made for demonstrating the strength of an evolution algorithm by beating the first level of so called "World's Hardest Game".

# Task

Create a playable version of the first level of "World's Hardest Game" and implement an incremental evolution algorithm which solves the level.

# Description

## World's Hardest Game

The "World's Hardest Game" is a two-dimensional fast-paced game where the point is to lead a red square from one green zone of the map to another green zone. The square's movement direction is controlled by the user. What makes the game difficult are moving blue circles in between the two green zones. Upon colliding with these circles is the red square returned to its starting location, forcing the user to try again.

## Evolution Algorithm

The game is solved by an evolution algorithm, which is implemented as follows. A set (called *generation*) of computer-controlled red squares is first initialized to contain large number of the red squares. The movement of each computer-controlled red square is randomly initialized to some small number of random initial movements. We will run the game for this set and based on the performance of each red square is a new generation created out of best performing red squares. After every few generations we add a few random moves to its red squares. We repeat this process until there is a generation containing a red square which reaches the final green zone.

# User Interface

## Before Start

The game can be run in two modes. The default mode is the evolution algorithm mode and the second one is the player mode. One can start the player mode by using the flag "-p".

## On Start

### Player Mode

The user will see one red square and a level with moving blue circles. The rest is as described in the *Game* section.

### Evolution Algorithm Mode

Upon starting the application, the user can observe the evolving generations of computer-controlled red squares. The current number of a generation is displayed in the top-left corner of the level.

This mode has no frame cap, so it runs as fast as the platform allows it. 