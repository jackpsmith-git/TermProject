import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Game {
    private ArrayDeque<Card> deck;
    private ArrayDeque<Card> discard;

    private ArrayList<Card> playerHand;
    private ArrayList<Card> cpuHand;
    private Scanner in;

    private final int DECK_SIZE = 108;

    private enum Player {
        Player,
        CPU
    }

    public Game() {
        PrintRules();
        this.CreateCards();
        this.Shuffle();
        this.discard = new ArrayDeque<>(DECK_SIZE);
        this.DealCards();
    }

    public void Start() {
        Card firstCard = this.Draw();
        System.out.println("First card: [" + firstCard + "]\n");
        this.Discard(firstCard);

        in = new Scanner(System.in);
        this.Turn(Player.CPU);
        in.close();
    }

    private Card LastCard() { return this.discard.peek(); }
    private Card Draw() { 
        EmptyDeckCheck();
        return this.deck.pop();
    }
    private void Discard(Card card) { this.discard.push(card); }

    private Card Draw(Player player, boolean log) {
        EmptyDeckCheck();
        Card card = this.deck.pop();
        if (player == Player.Player) {
            playerHand.add(card);
            if (log) System.out.println("PLAYER drew a card.");
        } else {
            cpuHand.add(card);
            if (log) System.out.println("CPU drew a card.");
        }
        return card;
    }

    private void CreateCards() {
        Card[] deckArray = new Card[DECK_SIZE];
        Card.Color[] cols = Card.Color.values();

        int idx = 0;
        for (int i = 0; i < cols.length - 1; i++) {
            Card.Color col = cols[i];

            for (int j = 1; j <= 2; j++) {
                for (int k = 1; k <= 9; k++) {
                    Card card = new Card(col, Card.Type.NUMBER, k);
                    deckArray[idx] = card;
                    idx++;
                }

                Card sc = new Card(col, Card.Type.SKIP, -1);
                deckArray[idx] = sc;
                idx++;
                
                Card rc = new Card(col, Card.Type.REVERSE, -1);
                deckArray[idx] = rc;
                idx++;

                Card dtc = new Card(col, Card.Type.DRAW2, -1);
                deckArray[idx] = dtc;
                idx++;
            }

            Card nc = new Card(col, Card.Type.NUMBER, 0);
            deckArray[idx] = nc;
            idx++;

            Card dfc = new Card(Card.Color.WILD, Card.Type.DRAW4, 0);
            deckArray[idx] = dfc;
            idx++;

            Card wc = new Card(Card.Color.WILD, Card.Type.WILD, -1);
            deckArray[idx] = wc;
            idx++;
        }

        this.deck = new ArrayDeque<>(Arrays.asList(deckArray));
    }

    private void Shuffle() {
        System.out.println("Shuffling deck...");   
        List<Card> list = new ArrayList<>(this.deck);
        Collections.shuffle(list);
        this.deck.clear();
        this.deck.addAll(list);
        System.out.println("Deck shuffled.\n");
    }

    private void DealCards() {
        this.playerHand = new ArrayList<>();
        this.cpuHand = new ArrayList<>();

        for (int i = 0; i <= 13; i++) {
            Player p = Player.values()[i % 2];
            Draw(p, false);
        }
    }

    private void Turn(Player player) {
        System.out.println(this.deck.size() + " card(s) in deck, " + this.discard.size() + " card(s) in discard pile.");
        boolean isPlayerTurn = player == Player.Player;

        if (LastCard().GetType() == Card.Type.DRAW2) {
            Draw(player, true);
            Draw(player, true);
        }

        if (LastCard().GetType() == Card.Type.DRAW4) {
            for (int i = 0; i <= 3; i++) {
                Draw(player, true);
            }
        }

        ArrayList<Card> playables = new ArrayList<>();
        ArrayList<Card> hand = isPlayerTurn ? playerHand : cpuHand;

        for (Card card : hand) {
            if (card.CanPlayCard(LastCard())) {
                playables.add(card);
            }
        }

        System.out.println(hand.size() + " cards in " + player.toString() + " hand, " + playables.size() + " playable cards.");

        if (playables.size() == 0) {
            boolean playableCardFound = false;
            while (!playableCardFound) {
                Card newCard = Draw();
                System.out.println(player.toString() + " drew a card.");
                if (newCard.CanPlayCard(this.LastCard())) {
                    PlayCard(player, newCard);
                    System.out.println();
                    playableCardFound = true;
                } else {
                    hand.add(newCard);
                }
            }

            if (isPlayerTurn) {
                Turn(Player.CPU);
            } else {
                Turn(Player.Player);
            }
            return;
        } else {
            if (isPlayerTurn) {
                int i = 1;
                for (Card card : playables) {
                    System.out.println("[" + i + "] " + card.toString());
                    i++;
                }
                
                Card card = RequestPlayerCard(playables, in);
                PlayCard(Player.Player, card);
            } else {
                Random random = new Random();
                int rint = random.nextInt(playables.size());
                Card card = playables.get(rint);
                PlayCard(Player.CPU, card);
            }
        }

        if (hand.size() == 0) {
            System.out.println(player.toString() + " WINS!");
        } else {
            EmptyDeckCheck();
            System.out.println();
            if (isPlayerTurn) {
                Turn(Player.CPU);
            } else {
                Turn(Player.Player);
            }
        }
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

    private Card.Color RequestColor(Scanner in) {
        Card.Color[] cols = Card.Color.values();
        int idx;
        for (int i = 0; i < cols.length - 1; i++) {
            System.out.println(i + 1 + ". " + cols[i].toString());
        }

        do {
            System.out.print("Choose a color for the wild card: ");
            if (in.hasNextInt()) {
                idx = in.nextInt();
            } else {
                idx = -1;
                in.nextLine();
            }

            idx = idx - 1;
        } while (idx < 0 || idx > cols.length - 1);

        return cols[idx];
    }

    private Card RequestPlayerCard(ArrayList<Card> playables, Scanner in) {
        Card card = playables.get(0);
        int idx;
        do {
            System.out.print("Choose a card to play: ");

            if (in.hasNextInt()) {
                idx = in.nextInt();
            } else {
                idx = -1;
                in.nextLine();
            }

            idx = idx - 1;
        } while (idx < 0 || idx >= playables.size());

        card = playables.get(idx);
        return card;
    }

    private void PlayCard(Player player, Card card) {
        if (card.GetColor() == Card.Color.WILD) {
            String cardString = card.toString();
            if (player == Player.Player) {
                Card.Color newColor = RequestColor(in);
                card.SetColor(newColor);
                playerHand.remove(card);
                System.out.println("Player played [" + cardString + "] as " + newColor.toString());
            } else {
                Random random = new Random();
                Card.Color newColor = Card.Color.values()[random.nextInt(4)];
                card.SetColor(newColor);
                cpuHand.remove(card);
                System.out.println("CPU played [" + cardString + "] as " + newColor.toString());
            }

            this.discard.push(card);
            return;
        }

        if (player == Player.Player) {
            playerHand.remove(card);
            System.out.println("Player played [" + card.toString() + "]");
        } else {
            cpuHand.remove(card);
            System.out.println("CPU played [" + card.toString() + "]");
        }

        this.discard.push(card);
    }

    private void PrintRules() {
        StringBuilder sb = new StringBuilder("Welcome to UNO!\n");
        System.out.println(sb.toString());
        // Add rules
    }
}