import java.util.Scanner;

class Game {
    private Deck deck;
    private Player player;
    private CPU cpu;

    boolean isPlayerTurn = false;

    public Game() {
        PrintRules();
        this.deck = new Deck();

        this.player = new Player();
        player.DrawHand(this.deck);

        this.cpu = new CPU();
        cpu.DrawHand(this.deck);
    }

    public void Start() {
        Card firstCard = this.deck.Draw();
        System.out.println("First card: [" + firstCard + "]\n");
        this.deck.Discard(firstCard);

        int turn = 0;
        boolean gameOver = false;
        Scanner in = new Scanner(System.in);
        while (!gameOver) {
            gameOver = turn % 2 == 0 ? cpu.TakeTurn(deck, in) : player.TakeTurn(deck, in);
            turn++;
        }
        in.close();
    }

    private void PrintRules() {
        StringBuilder sb = new StringBuilder("Welcome to UNO!\n");
        System.out.println(sb.toString());
        // Add rules
    }
}