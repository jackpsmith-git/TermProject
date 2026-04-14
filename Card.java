public class Card {
    private Color color;
    private Type type;
    private int value;

    public Color GetColor() { return this.color; }
    public void SetColor(Color color) { this.color = color; }
    public Type GetType() { return this.type; }

    public enum Color {
        RED,
        BLUE,
        GREEN,
        YELLOW,
        WILD,
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

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.color.toString().toUpperCase());
        if (this.type == Type.NUMBER) {
            sb.append(" " + this.value);
        } else {
            sb.append(" " + this.type.toString());
        }

        return sb.toString();
    }

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