# JBlackJack
A comprehensive implementation of Blackjack, a desktop card game, developed in Java (Eclipse), for the course Metodologie di Porgrammazione (2023-2024), strictly adhering to the MVC (Model-View-Controller) architecture to ensure separation of concerns.

I implemented several Design Patterns to enhance modularity: Strategy to handle different playstyles for human players and bots, Observer to make sure the game interface updates automatically whenever the game state changes and Factory Method to manage how different cards are generated within the deck. 
I used Java Streams to simplify data tasks, like calculating hand scores and identifying the winner at the end of a round and I added a saving system using Serialization so players can create profiles and keep track of their game statistics.
There is a responsive GUI, created with Java Swing/AWT, featuring real-time updates via the Observer pattern, sound effects and animations. 
Bots use specific game strategies, like hitting until 17, managed through dedicated threads to simulate realistic gameplay.
