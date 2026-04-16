import java.util.Scanner;

public class Game {
    private Deck deck;
    private Player player;
    private CPU cpu;

    boolean isPlayerTurn = false;
    Scanner in;

    public Game(Scanner in) {
        this.deck = new Deck();
        this.player = new Player();
        this.cpu = new CPU();
        this.in = in;
    }

    public void Start() {
        player.DrawHand(this.deck);
        cpu.DrawHand(this.deck);

        Card firstCard = this.deck.Draw();
        this.deck.Discard(firstCard);
        while (firstCard.GetType() == Card.Type.DRAW2 || firstCard.GetType() == Card.Type.DRAW4) {
            firstCard = this.deck.Draw();
            this.deck.Discard(firstCard);
        }
        System.out.println("First card: [" + firstCard + "]\n");

        int turn = 0;
        boolean gameOver = false;
        while (!gameOver) {
            gameOver = turn % 2 == 0 ? cpu.TakeTurn(deck, this.in) : player.TakeTurn(deck, this.in);
            if (deck.Last().GetType() != Card.Type.SKIP) turn++;
        }
    }
}