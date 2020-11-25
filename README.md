# Specification Summary

## Game Setup
- Multiple boards
- A number of fixed tiles
    - Indicated in some way
- Board populated randomly
- Player profiles selected to play
- Correct number of player pieces placed

## On Your Turn
- Win if reach goal
- Player turn moves left (clockwise)

### Draw a Tile
- Draw a random tile from silk bag

**If Action**
- Keep it but **cannot** play this round

**If Floor**
- Slide from an available edge space
    - Must be indicated in some way
- Tiles pushed over one space
    - Opposite tile ejected into silk bag
    - Player piece moved to other side

### Play Action Tile
- Play action from hand
    - Don't have to
- After using, it is removed from game

**Ice**
- Choose tile
- 3 * 3 area on board
- Tiles act as fixed tiles
- Lasts 1 turns (start of user's turn)

**Fire**
- Choose tile
- 3 * 3 area on board
    - Cannot have player tokens in
- Players cannot move onto tiles
- Lasts 2 turns (start of user's turn)

**Double Move**
- User may move twice

**Backtrack**
- Choose opponent
- Player Piece moves back 2 turns
    - Unless previous tile is on fire
- Can only affect each player once per game

### Moving
- Must move if can
- In any of the 4 cardinal directions
- Up to 4 tiles
- Paths must link
- Cannot pass through players or fire

## Graphics
- Good looking graphics
- At least jumpy animation

## Game Boards
- Must be simple ASCII based files
- Must include:
    > • Size of the board (width and height) The game board need not be square.
    > 
    > • Details about each fixed tile: which tile it is (Straight, Corner. T-shaped, Goal),
    > its orientation and its location on the board (x, y).
    > 
    > • The set of tiles (both Action Tiles and Floor Tiles) that go into the Silk Bag (these
    > do not include the fixed tiles specified in the previous point).
    > 
    > • The starting location of the 4 player pieces (all 4 must be specified in the file,
    > but fewer may be used in any particular game that uses this game board).

## Player Profile
- Has player name
- Tracks no games played, wins, looses

## Leaderboard
- Per board
- Player profiles listed in order of wins

## Data
- Data must persist across runs
- Able to save/load other games

## Message of the Day
- Must be displayed

## Extra Features
> ... extend the implementation in novel ways. All extensions that do not violate the
> specification will be considered. Substantial extensions to the software, extra reading
> and learning, will be required to achieve a high First Class mark (in A2).
