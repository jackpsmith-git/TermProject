public class Card {
    private Color color;
    private Type type;
    private int value;

    public Color GetColor() { return this.color; }
    public void SetColor(Color color) { this.color = color; }
    public Type GetType() { return this.type; }

    public final static  String ANSI_GREEN = "\u001B[32m";
    public final static String ANSI_RED = "\u001B[31m";
    public final static String ANSI_BLUE = "\u001B[34m";
    public final static String ANSI_YELLOW = "\u001B[33m";
    public final static String ANSI_RESET = "\u001B[0m";

    public enum Color {
        RED,
        BLUE,
        GREEN,
        YELLOW,
        WILD,
    }

    public static String GetAnsiCode(Color col) {
        String colorCode = "";
        switch (col) {
            case Card.Color.RED:
                colorCode = Card.ANSI_RED;
                break;
            case Card.Color.BLUE:
                colorCode = Card.ANSI_BLUE;
                break;
            case Card.Color.GREEN:
                colorCode = Card.ANSI_GREEN;
                break;
            case Card.Color.YELLOW:
                colorCode = Card.ANSI_YELLOW;
                break;
            case Card.Color.WILD:
                break;
        }
        return colorCode;
    }

    public enum Type {
        NUMBER,
        DRAW4,
        DRAW2,
        REVERSE,
        SKIP,
        WILD
    }

    public Card(Color color, Type type, int value) {
        this.color = color;
        this.type = type;
        this.value = value;
    }

    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder(GetAnsiCode(this.color));
        if (this.type == Type.NUMBER) {
            sb.append(this.value);
        } else {
            sb.append(this.type.toString());
        }
        sb.append(ANSI_RESET);
        return sb.toString();
    }

    /**
     * @param previous the previous card that was played
     * @return true if this card can be played, otherwise false
     */
    public boolean CanPlayCard(Card previous) {
        if ((this.color == previous.color || this.color == Color.WILD) ||
            (this.type == Type.NUMBER && previous.type == Type.NUMBER && this.value == previous.value) ||
            (this.type != Type.NUMBER && this.type == previous.type) ||
            (this.type == Type.WILD)) {
            return true;
        }

        return false;
    }
}