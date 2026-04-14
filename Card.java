public class Card {
    private Color color;
    public Color GetColor() { return this.color; }
    public void SetColor(Color color) { this.color = color; }

    private Type type;
    public Type GetType() { return this.type; }

    private int value;
    public int GetValue() { return this.value; }

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
            sb.append(" " + this.GetValue());
        } else {
            sb.append(" " + this.type.toString());
        }

        return sb.toString();
    }

    public boolean CanPlayCard(Card previous) {
        if ((this.GetColor() == previous.GetColor() || this.color == Color.WILD) ||
            (this.GetType() == Type.NUMBER && previous.GetType() == Type.NUMBER && this.GetValue() == previous.GetValue()) ||
            (this.GetType() != Type.NUMBER && this.GetType() == previous.GetType()) ||
            (this.GetType() == Type.WILD)) {
            return true;
        }

        return false;
    }
}