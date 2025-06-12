# Football World Cup Score Board

## Assumptions
- "added to our system" means "started", so the summary should show both finished and not finished games
- a team is uniquely identified by its name
- there can be many games running (started and unfinished) at the same time
- a finished game can't be updated
- the summary will be a list of objects of a library-provided class, with the class having a nice toString() - providing an already formatted summary-string seems restrictive for the user
- matches can be updated with any score higher than previous
- a *running* match is uniquely identified by home and away team names
- a team name can't be blank