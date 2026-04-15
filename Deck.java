import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private ArrayDeque<Card> deck;
    private ArrayDeque<Card> discard;
    private final int DECK_SIZE = 108;

    public Deck() {
        this.CreateCards();
        this.Shuffle();
        this.discard = new ArrayDeque<>(this.DECK_SIZE);
    }

    public Card Draw() {
        this.EmptyDeckCheck();
        return this.deck.pop();
    }

    public void Discard(Card card) { this.discard.push(card); }
    public Card Last() { return this.discard.peek(); }

    private void CreateCards() {
        this.deck = new ArrayDeque<>(DECK_SIZE);
        Card.Color[] cols = Card.Color.values();

        for (int i = 0; i < cols.length; i++) {
            Card.Color col = cols[i];

            for (int j = 1; j <= 2; j++) {
                for (int k = 1; k <= 9; k++) {
                    Card card = new Card(col, Card.Type.NUMBER, k);
                    this.deck.push(card);
                }

                Card skip = new Card(col, Card.Type.SKIP, -1);
                this.deck.push(skip);
                Card reverse = new Card(col, Card.Type.REVERSE, -1);
                this.deck.push(reverse);
                Card draw2 = new Card(col, Card.Type.DRAW2, -1);
                this.deck.push(draw2);
            }

            Card zero = new Card(col, Card.Type.NUMBER, 0);
            this.deck.push(zero);
            Card draw4 = new Card(Card.Color.WILD, Card.Type.DRAW4, -1);
            this.deck.push(draw4);
            Card wild = new Card(Card.Color.WILD, Card.Type.WILD, -1);
            this.deck.push(wild);
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

    public void PrintSize() {
        System.out.println(this.deck.size() + " card(s) in deck, " + this.discard.size() + " card(s) in discard pile.");
    }
}
