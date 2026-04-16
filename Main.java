import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to UNO!\n");

        Scanner in = new Scanner(System.in);
        boolean newGame;

        do {
            Game game = new Game(in);
            game.Start();

            String response;
            do {
                System.out.print("Play again? [Y/N]: ");
                response = in.nextLine().strip();
            } while (!response.equalsIgnoreCase("Y") && !response.equalsIgnoreCase("N"));

            newGame = response.equalsIgnoreCase("Y");
            System.out.println();
        } while (newGame);

        System.out.println("Thank you for playing!");
        in.close();
    }
}