The Tweet Analyzer is a Java application that analyzes a set of tweets collected from Twitter based on the location of the tweet and any hashtags it contains. The program attempts to determine which state the tweet came from, and then gives the user the option of seeing which states had the most tweets, which hashtags are most popular in a given state, and how often a given phrase appears as a function of time. Sample files are provided to read in 300,000 tweets from either a txt or JSON file, while state latitudes and longitudes are provided in a .csv file.

This project focuses on implementing different design patterns (Factory Method, Singleton Pattern, Observer Pattern) to achieve modularity and functional independence. The Model-View-Controller architecture is implemented such that the Controller passes information between the model (DataProcessor) and the view (UserInterface). The program also logs all user input to both the screen and a log file.


To run from the command line:
- (1) cd into the root folder
- (2) javac -cp json-simple-1.1.1.jar *.java
- (3) java Main TEXT 300k_tweets.txt states.csv log.txt
