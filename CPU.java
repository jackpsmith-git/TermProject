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
    public boolean TakeTurn(Deck deck, Scanner in) {
        deck.PrintSize();
        if (deck.Last().GetType() == Card.Type.DRAW2) {
            this.DrawCard(deck, true);
            this.DrawCard(deck, true);
        }

        if (deck.Last().GetType() == Card.Type.DRAW4) {
            for (int i = 0; i <= 3; i++) {
                this.DrawCard(deck, true);
            }
        }

        ArrayList<Card> playables = new ArrayList<>();
        
        for (Card card : this.hand) {
            if (card.CanPlayCard(deck.Last())) {
                playables.add(card);
            }
        }

        System.out.println(this.hand.size() + " cards in " + this.toString() + " hand, " + playables.size() + " playable cards.");
    
        if (playables.size() == 0) {
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

            return false;
        } else {
            Random random = new Random();
            int rint = random.nextInt(playables.size());
            Card card = playables.get(rint);
            this.PlayCard(card, deck, in);
        }

        if (this.hand.size() == 0) {
            System.out.println(this.toString() + " WINS!");
            return true;
        } else {
            System.out.println();
            return false;
        }
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
