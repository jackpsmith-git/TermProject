import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private ArrayDeque<Card> deck;
    private ArrayDeque<Card> discard;

    public Deck() {
        this.CreateCards();
        this.Shuffle();
        this.discard = new ArrayDeque<>();
    }

    /**
     * Pops a card off of the deck stack
     * @return the top card from the deck
     */
    public Card Draw() {
        this.EmptyDeckCheck();
        return this.deck.pop();
    }

    /**
     * Pushes a card to the discard stack
     * @param card
     */
    public void Discard(Card card) { this.discard.push(card); }
    
    /** Peeks the top card of the discard stack
     * @return the last card that was played
     */
    public Card Last() { return this.discard.peek(); }

    /**
     * Instantiates a new Card and pushes it to the deck stack
     * @param color
     * @param type
     * @param value card number (-1 if card has no number value)
     */
    private void CreateCard(Card.Color color, Card.Type type, int value) {
        Card card = new Card(color, type, value);
        this.deck.push(card);
        System.out.println(card.toString());
    }

    /**
     * Creates all cards and pushes them to the deck stack
     */
    private void CreateCards() {
        this.deck = new ArrayDeque<>();
        Card.Color[] cols = Card.Color.values();

        for (int i = 0; i < cols.length - 1; i++) {
            Card.Color col = cols[i];

            for (int j = 1; j <= 2; j++) {
                for (int k = 1; k <= 9; k++) {
                    this.CreateCard(col, Card.Type.NUMBER, k);
                }

                this.CreateCard(col, Card.Type.SKIP, -1);
                this.CreateCard(col, Card.Type.REVERSE, -1);
                this.CreateCard(col, Card.Type.DRAW2, -1);
            }

            this.CreateCard(col, Card.Type.NUMBER, 0);
            this.CreateCard(Card.Color.WILD, Card.Type.DRAW4, -1);
            this.CreateCard(Card.Color.WILD, Card.Type.WILD, -1);
        }
    }

    private void Shuffle() {
        System.out.println("Shuffling deck...");   
        List<Card> list = new ArrayList<>(this.deck);
        Collections.shuffle(list);
        this.deck.clear();
        this.deck.addAll(list);
        System.out.println("Deck shuffled.\n");
    }

    /**
     * Checks if the deck is empty. If it is, stores the top card of the discard pile, pushes the rest of the discard pile to the deck stack, shuffles the deck stack, and restores the previously played card to the discard stack
     */
    private void EmptyDeckCheck() {
        if (this.deck.size() == 0) {
            System.out.println("Deck empty. Reshuffling discard pile into deck.");
            Card last = this.discard.pop();
            this.deck = this.discard;
            this.discard.clear();
            this.Shuffle();
            this.Discard(last);
        }
    }

    /**
     * Prints the number of cards currently in the deck and the discard stacks
     */
    public void PrintSize() {
        System.out.println(this.deck.size() + 
        " card(s) in deck, " + this.discard.size() + 
        " card(s) in discard pile.");
    }
}
