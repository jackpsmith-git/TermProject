# CLI UNO
CS122 Term Project - Single player UNO written in Java.

## Rules
This version of UNO is single-player against the CPU.

### Setup
* The UNO deck contains 108 cards:
    * 19 numbered cards per color (red, blue, green, yellow):
        * 1-9 (x2 per color)
        * 0 (x1 per color)
    * 6 special cards per color (red, blue, green, yellow):
        * skip (x2 per color)
        * reverse (x2 per color)
        * draw 2 (x2 per color)
    * 8 wild cards
        * draw 4 (x2)
        * wild (x2)
* The player and the CPU each start with 7 cards in their hand.
* The discard pile starts with one card.
* The CPU starts the game which actually gives the player a slight advantage. We don't check if it is the first turn or not, so if the card that starts in the discard pile happens to be a draw 4 or draw 2 card, the CPU must draw extra cards on the first turn.
* The player and CPU take turns playeing cards from their hand until one of them has no cards remaining.

### Playing cards

A card can be played if it meets any of the following criteria:
* It is a number card with the same number value as the previous card
* It is the same color as the previous card
* It is a special card (reverse, skip, draw 2) of the same type as the previous card
* It is a wild card

The player must choose a playable card from their hand. If they play a wild card, they must also choose a color to assign to the card before playing it. The CPU chooses playable cards from its hand at random and randomly assigns colors to wild cards.

You cannot play multiple cards of the same type at once (stacking). Playing a draw 2 or draw 4 card on top of another draw 2 or draw 4 card respectively does not pass the initial draw 2/4 back to the CPU.

Reverse cards do nothing since reversing the direction in a two player game does not change the order of turns.

If the player or CPU does not have a playable card in their hand, they must continue to draw cards until they draw a playable card, and then must play it.

### Game Over
The game ends when either the player or the CPU have no cards left in their hand, winning the game.

## Contributors
- Jack Smith
- Sasha Kulo
- Dan Ingunza

## TO DO:
* Implement skip cards