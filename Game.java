import java.util.Scanner;

public class Game {
    private Deck deck;
    private Player player;
    private CPU cpu;

    boolean isPlayerTurn = false;

    public Game() {
        PrintRules();
        this.deck = new Deck();
        this.player = new Player();
        this.cpu = new CPU();
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
        Scanner in = new Scanner(System.in);
        while (!gameOver) {
            gameOver = turn % 2 == 0 ? cpu.TakeTurn(deck, in) : player.TakeTurn(deck, in);
            if (deck.Last().GetType() != Card.Type.SKIP) turn++;
        }
        in.close();
    }

    private void PrintRules() {
        StringBuilder sb = new StringBuilder("Welcome to UNO!\n");
        System.out.println(sb.toString());
        // Add rules
    }
}