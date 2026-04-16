import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    protected ArrayList<Card> hand;
    
    public Player() {
        this.hand = new ArrayList<>(7);
    }

    @Override
    public String toString() { return "PLAYER"; }

    /**
     * Draws seven cards from the deck and pushes them to this player's hand
     * @param deck
     */
    public void DrawHand(Deck deck) {
        for (int i = 0; i <= 6; i++) {
            this.DrawCard(deck, false);
        }
    }

    /**
     * Draws a card from the deck and pushes it to this player's hand
     * @param deck
     * @param log if true, will print a message stating this player drew a card
     */
    public void DrawCard(Deck deck, boolean log) {
        this.hand.add(deck.Draw());
        if (log) System.out.println(this.toString() + " drew a card.");
    }

    /**
     * Checks if the last card played was a Draw2 or Draw4 card. If it was, draws the corresponding number of cards and pushes them to this player's hand
     * @param deck
     */
    private void DrawCheck(Deck deck) {
        if (deck.Last().GetType() == Card.Type.DRAW2) {
            this.DrawCard(deck, true);
            this.DrawCard(deck, true);
        }

        if (deck.Last().GetType() == Card.Type.DRAW4) {
            for (int i = 0; i <= 3; i++) {
                this.DrawCard(deck, true);
            }
        }
    }

    /**
     * @param deck
     * @return an ArrayList containing the playable cards in this player's hand
     */
    private ArrayList<Card> GetPlayables(Deck deck) {
        ArrayList<Card> playables = new ArrayList<>();
        for (Card card : this.hand) {
            if (card.CanPlayCard(deck.Last())) {
                playables.add(card);
            }
        }

        System.out.println(this.hand.size() + " cards in " + this.toString() + " hand, " + playables.size() + " playable cards.");
        return playables;
    }

    /**
     * Draws cards until a playable card is found and then plays it
     * @param deck
     * @param in
     */
    private void NoPlayablesFound(Deck deck, Scanner in) {
        boolean playableCardFound = false;
        while (!playableCardFound) {
            Card newCard = deck.Draw();
            System.out.println(this.toString() + " drew a card.");
            if (newCard.CanPlayCard(deck.Last())) {
                this.PlayCard(newCard, deck, in);
                System.out.println();
                playableCardFound = true;
            } else {
                this.hand.add(newCard);
            }
        }
    }

    /**
     * @return true if this player's hand is empty, otherwise false
     */
    private boolean EndGameCheck() {
        if (this.hand.size() == 0) {
            System.out.println(this.toString() + " WINS!");
            return true;
        } else {
            System.out.println();
            return false;
        }
    }

    /**
     * @param deck
     * @param in
     * @return true if this player won the game, otherwise false
     */
    public boolean TakeTurn(Deck deck, Scanner in) {
        deck.PrintSize();
        this.DrawCheck(deck);

        ArrayList<Card> playables = this.GetPlayables(deck);
        
        if (playables.size() == 0) {
            this.NoPlayablesFound(deck, in);
            return false;
        } else {
            ChooseCard(deck, in, playables);
        }

        return this.EndGameCheck();
    }

    /**
     * Prompts the player to choose a playable card from their hand and plays is
     * @param deck
     * @param in
     * @param playables
     */
    protected void ChooseCard(Deck deck, Scanner in, ArrayList<Card> playables) {
        int i = 1;
        for (Card card : playables) {
            System.out.println("[" + i + "] " + card.toString());
            i++;
        }
        
        Card card = this.RequestCard(playables, in);
        this.PlayCard(card, deck, in);
    }

    public void PlayCard(Card card, Deck deck, Scanner in) {
        if (card.GetColor() == Card.Color.WILD) {
            String cardString = card.toString();

            Card.Color newColor = ChooseColor(in);
            card.SetColor(newColor);
            
            this.hand.remove(card);
            System.out.println(this.toString() + " played [" + cardString + "] as " + newColor.toString());
            deck.Discard(card);
            return;
        }

        this.hand.remove(card);
        System.out.println(this.toString() + " played [" + card.toString() + "]");
        deck.Discard(card);
    }

    protected Card.Color ChooseColor(Scanner in) {
        Card.Color[] cols = Card.Color.values();
        int idx;
        for (int i = 0; i < cols.length - 1; i++) {
            System.out.println(i + 1 + ". " + cols[i].toString());
        }

        do {
            System.out.print("Choose a color for the wild card: ");
            if (in.hasNextInt()) {
                idx = in.nextInt();
            } else {
                idx = -1;
                in.nextLine();
            }

            idx = idx - 1;
        } while (idx < 0 || idx > cols.length - 1);

        return cols[idx];
    }

    /**
     * Prompts the player to choose a card from their hand to play
     * @param playables
     * @param in
     * @return chosen card
     */
    private Card RequestCard(ArrayList<Card> playables, Scanner in) {
        Card card = playables.get(0);
        int idx;
        do {
            System.out.print("Choose a card to play: ");

            if (in.hasNextInt()) {
                idx = in.nextInt();
            } else {
                idx = -1;
                in.nextLine();
            }

            idx = idx - 1;
        } while (idx < 0 || idx >= playables.size());

        card = playables.get(idx);
        return card;
    }
}