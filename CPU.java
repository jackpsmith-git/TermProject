import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CPU extends Player {
        public CPU() {
        super();
    }

    @Override
    public String toString() {
        return "CPU";
    }

    @Override
    protected void ChooseCard(Deck deck, Scanner in, ArrayList<Card> playables) {
        Random random = new Random();
        int rint = random.nextInt(playables.size());
        Card card = playables.get(rint);
        this.PlayCard(card, deck, in);
    }

    @Override
    public void PlayCard(Card card, Deck deck, Scanner in) {
        if (card.GetColor() == Card.Color.WILD) {
            String cardString = card.toString();
            Random random = new Random();
            Card.Color newColor = Card.Color.values()[random.nextInt(4)];
            card.SetColor(newColor);
            this.hand.remove(card);
            System.out.println("CPU played [" + cardString + "] as " + newColor.toString());
            
            deck.Discard(card);
            return;
        }

        this.hand.remove(card);
        System.out.println(this.toString() + " played [" + card.toString() + "]");
        deck.Discard(card);
    }
}
