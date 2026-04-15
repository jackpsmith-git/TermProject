import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CPU extends Player {
    public CPU() {
        super();
    }

    @Override
    public String toString() { return "CPU"; }

    @Override
    protected void ChooseCard(Deck deck, Scanner in, ArrayList<Card> playables) {
        Random random = new Random();
        int rint = random.nextInt(playables.size());
        Card card = playables.get(rint);
        this.PlayCard(card, deck, in);
    }

    @Override
    protected Card.Color GetColor(Scanner in) {
        Random random = new Random();
        return Card.Color.values()[random.nextInt(4)];
    }
}
