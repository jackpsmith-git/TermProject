import java.util.ArrayDeque;

public class DiscardPile {
    private ArrayDeque<Card> discard;

    public DiscardPile() {
        this.discard = new ArrayDeque<>(Deck.DECK_SIZE);
    }

    public ArrayDeque<Card> Get() { return this.discard; }
    public Card GetLast() { return this.discard.peek(); }
    public void Discard(Card card) { this.discard.push(card); }
    public Card Pop() { return this.discard.pop(); }
    public void Clear() { this.discard.clear(); }
    public int Size() { return this.discard.size(); }
}
