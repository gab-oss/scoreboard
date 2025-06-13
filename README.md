# Football World Cup Score Board

## Description
This is a simple Java library for managing a live scoreboard of matches. Data about matches and scores is kept in-memory.
The library allows for the following operations:
- start a match - it will appear in the summary until finished and its score can be updated,
- update a match by providing team names and a pair of scores - will update if match is currently ongoing,
- finish a match - delete info about match - the board is live only,
- get a summary - provides a summary of all matches as a list ordered by total score first and being most recently started second.

## Assumptions
- a team is uniquely identified by its name
- there can be many games running (started and unfinished) at the same time
- the summary will be a list of objects of a library-provided class, with the class having a dedicated toString() - providing an already formatted summary as string seems restrictive for the user
- matches can be updated with any score not lower than previous
- a running match is uniquely identified by home and away team names
- a team name can't be blank
- finished games are dropped and won't appear in summary