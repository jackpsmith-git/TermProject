import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CPU extends Player {
    public CPU() {
        super();
    }

    @Override
    public String toString() { return "CPU"; }

    /**
     * Chooses a playable card at random from the CPU's hand and plays it
     */
    @Override
    protected void ChooseCard(Deck deck, Scanner in, ArrayList<Card> playables) {
        Random random = new Random();
        int rint = random.nextInt(playables.size());
        Card card = playables.get(rint);
        this.PlayCard(card, deck, in);
    }

    @Override
    protected Card.Color ChooseColor(Scanner in) {
        Random random = new Random();
        return Card.Color.values()[random.nextInt(4)];
    }
}
