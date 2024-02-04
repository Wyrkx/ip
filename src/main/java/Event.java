public class Event extends Task {
    private String from, to;

    public Event(String name, String from, String to) {
        this(name, from, to, false);
    }

    public Event(String name, String from, String to, boolean mark) {
        super(name, mark);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toSaveString() {
        return String.format("E\t%s\t%s\t%s", super.toSaveString(), from, to);
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), this.from, this.to);
    }
}
