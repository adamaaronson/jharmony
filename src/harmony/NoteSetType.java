package harmony;

import java.util.Arrays;
import java.util.Objects;

public abstract class NoteSetType {
    private Interval[] intervals;
    private String name;

    NoteSetType(Interval[] intervals) {
        this.intervals = intervals;
        this.name = "";
    }

    NoteSetType(Interval[] intervals, String name) {
        this.intervals = intervals;
        this.name = name;
    }

    Interval[] getIntervals() {
        return intervals;
    }

    String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteSetType that = (NoteSetType) o;
        return Arrays.equals(intervals, that.intervals) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(intervals);
        return result;
    }

    @Override
    public String toString() {
        return Arrays.toString(intervals);
    }
}
