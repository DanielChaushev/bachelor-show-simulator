# Bachelor Show Simulator

A Java simulation of a reality TV show built as a university assignment.
Implements OOP principles including interfaces, polymorphism, and array-based data structures.

## Structure

- `Ergenka` — interface representing a contestant
  - `RomanticErgenka` — rating based on romance level and favorite location
  - `HumorousErgenka` — rating based on humor level and date duration
- `EliminationRule` — interface for elimination logic
  - `LowestRatingEliminationRule` — eliminates all contestants with the lowest rating
  - `LowAttributeSumEliminationRule` — eliminates contestants below an attribute threshold
  - `PublicVoteEliminationRule` — eliminates the contestant with 50%+1 votes (Boyer-Moore algorithm)
- `ShowAPIImpl` — orchestrates rounds, dates, and eliminations

## How a round works

1. The bachelor goes on a date with every contestant using the same `DateEvent`
2. Each contestant updates her rating based on her type and the date parameters
3. Elimination rules are applied to remove contestants
