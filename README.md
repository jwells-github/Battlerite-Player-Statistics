# Battlerite-Player-Statistics
An android App that uses the API for the game battlerite to display user statistics

## Overview

The first view shown to the user prompts them to enter their Battlerite player name. When the user presses search a request is
sent to the API with the player's name. If the player does not exist then a toast message is shown notifying the user.

If the player does exist then they are taken to the UserOverview view.

![App user search](https://github.com/jwells-github/Battlerite-Player-Statistics/blob/master/userSearch.png)

In the UserOverview the user is shown some stats related to their account (Account name, Account Level, Total Wins & Total Losses)

The user is also able to view statistics for each individual Battlerite champion by pressing a button at the bottom of the view

![App user overview](https://github.com/jwells-github/Battlerite-Player-Statistics/blob/master/userOverview.png?raw=true)

The statistics for each indidual champion are held in a list and are shown to the user via a recyclerview. 

The user is able to see the wins, losses, win rate, champion level and time played for each champion

![App Champion overview](https://github.com/jwells-github/Battlerite-Player-Statistics/blob/master/ChampionStats.png?raw=true)

The user is also able to sort the list of champions by any of the following, Name, Wins, Losses and Time Played by accessing a 
drop down menu from the actionbar

![app sort](https://github.com/jwells-github/Battlerite-Player-Statistics/blob/master/sortList.png?raw=true)
