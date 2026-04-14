import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayDeque<Card> deck;
    public final static int DECK_SIZE = 108;

    public Deck() {
        this.CreateCards();
        this.Shuffle();
    }

    public void SetDeck(ArrayDeque<Card> deck) { this.deck = deck; }

    private void CreateCards() {
        deck = new ArrayDeque<>(DECK_SIZE);
        Card.Color[] cols = Card.Color.values();

        for (int i = 0; i < cols.length; i++) {
            Card.Color col = cols[i];

            for (int j = 1; j <= 2; j++) {
                for (int k = 1; k <= 9; k++) {
                    Card card = new Card(col, Card.Type.NUMBER, k);
                    deck.push(card);
                }

                Card skip = new Card(col, Card.Type.SKIP, -1);
                deck.push(skip);
                Card reverse = new Card(col, Card.Type.REVERSE, -1);
                deck.push(reverse);
                Card draw2 = new Card(col, Card.Type.DRAW2, -1);
                deck.push(draw2);
            }

            Card zero = new Card(col, Card.Type.NUMBER, 0);
            deck.push(zero);
            Card draw4 = new Card(Card.Color.WILD, Card.Type.DRAW4, -1);
            deck.push(draw4);
            Card wild = new Card(Card.Color.WILD, Card.Type.WILD, -1);
            deck.push(wild);
        }
    }

    public void Shuffle() {
        ArrayList<Card> list = new ArrayList<>(this.deck);
        Collections.shuffle(list);
        this.deck.clear();
        this.deck.addAll(list);
    }

    public ArrayList<Card> DrawHand(DiscardPile discardPile) {
        ArrayList<Card> hand = new ArrayList<>(7);
        for (int i = 0; i <= 6; i++) {
            hand.add(Draw(discardPile));
        }
        return hand;
    }

    public Card Draw(DiscardPile discardPile) { 
        EmptyDeckCheck(discardPile);
        return this.deck.pop(); 
    }

    public Card Draw(ArrayList<Card> hand, DiscardPile discardPile) {
        Card card = this.Draw(discardPile);
        hand.add(card);
        return card;
    }

    public void EmptyDeckCheck(DiscardPile discardPile) {
        if (this.Size() == 0) {
            Card last = discardPile.Pop();
            this.SetDeck(discardPile.Get());
            discardPile.Clear();
            this.Shuffle();
            discardPile.Discard(last);
        }
    }

    public int Size() { return this.deck.size(); }
}