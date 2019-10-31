package harmony;

public class ScaleType extends NoteSetType {
    public ScaleType(Interval[] intervals) {
        super(intervals);
    }

    public ScaleType(Interval[] intervals, String name) {
        super(intervals, name);
    }

    public ScaleType getMode(int degree) {
        int numIntervals = getIntervals().length;
        Interval[] modeIntervals = new Interval[numIntervals];

        for (int i = 0; i < modeIntervals.length; i++) {
            modeIntervals[i] = getIntervals()[(((i + degree) % numIntervals) + numIntervals) % numIntervals];
        }

        return new ScaleType(modeIntervals);
    }

    public ScaleType getMode(int degree, String name) {
        return new ScaleType(getMode(degree).getIntervals(), name);
    }
}
