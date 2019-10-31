package harmony;

public class ChordType extends NoteSetType {
    private String symbol;

    public ChordType(Interval[] intervals) {
        super(intervals);
        symbol = "";
    }

    public ChordType(Interval[] intervals, String name) {
        super(intervals, name);
        symbol = "";
    }

    public ChordType(Interval[] intervals, String name, String symbol) {
        super(intervals, name);
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public ChordType getExtension(Interval interval) {
        Interval[] newIntervals = new Interval[getIntervals().length + 1];
        for (int i = 0; i < getIntervals().length; i++) {
            newIntervals[i] = getIntervals()[i];
        }
        newIntervals[getIntervals().length] = interval;
        return new ChordType(newIntervals);
    }

    public ChordType getExtension(Interval interval, String name) {
        return new ChordType(getExtension(interval).getIntervals(), name, this.symbol);
    }

    public ChordType getExtension(Interval interval, String name, String symbol) {
        return new ChordType(getExtension(interval).getIntervals(), name, symbol);
    }
}
