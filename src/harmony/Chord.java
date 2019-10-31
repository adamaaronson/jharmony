package harmony;

import java.util.HashMap;
import java.util.Map;

public class Chord extends NoteSet {
    // Triads
    public static final ChordType MAJOR_TRIAD = new ChordType(new Interval[] {
            Interval.MAJOR_THIRD,
            Interval.MINOR_THIRD
    }, "major", "M");

    public static final ChordType MINOR_TRIAD = new ChordType(new Interval[] {
            Interval.MINOR_THIRD,
            Interval.MAJOR_THIRD
    }, "minor", "m");

    public static final ChordType AUGMENTED_TRIAD = new ChordType(new Interval[] {
            Interval.MAJOR_THIRD,
            Interval.MAJOR_THIRD
    }, "augmented", "+");

    public static final ChordType DIMINISHED_TRIAD = new ChordType(new Interval[] {
            Interval.MINOR_THIRD,
            Interval.MINOR_THIRD
    }, "diminished", "o");

    // Sevenths and Sixths
    public static final ChordType MAJOR_SEVENTH = MAJOR_TRIAD.getExtension(Interval.MAJOR_THIRD, "major seventh", "M7");
    public static final ChordType DOMINANT_SEVENTH = MAJOR_TRIAD.getExtension(Interval.MINOR_THIRD, "dominant seventh", "7");

    public static final ChordType MINOR_SEVENTH = MINOR_TRIAD.getExtension(Interval.MINOR_THIRD, "minor seventh", "m7");
    public static final ChordType MINOR_MAJOR_SEVENTH = MINOR_TRIAD.getExtension(Interval.MAJOR_THIRD, "minor major seventh", "mM7");

    public static final ChordType DIMINISHED_SEVENTH = DIMINISHED_TRIAD.getExtension(Interval.MINOR_THIRD, "diminished seventh", "o7");
    public static final ChordType HALF_DIMINISHED_SEVENTH = DIMINISHED_TRIAD.getExtension(Interval.MAJOR_THIRD, "half-diminished seventh", "m7b5");

    public static final ChordType AUGMENTED_SEVENTH = AUGMENTED_TRIAD.getExtension(Interval.DIMINISHED_THIRD, "augmented seventh", "+7");
    public static final ChordType AUGMENTED_MAJOR_SEVENTH = AUGMENTED_TRIAD.getExtension(Interval.MINOR_THIRD, "augmented major seventh", "+M7");

    public static final ChordType MAJOR_SIXTH = MAJOR_TRIAD.getExtension(Interval.MAJOR_SECOND, "major sixth", "6");
    public static final ChordType MINOR_SIXTH = MINOR_TRIAD.getExtension(Interval.MAJOR_SECOND, "minor sixth", "-6");

    private static final ChordType[] CHORD_TYPES = new ChordType[] {
            MAJOR_TRIAD,
            MINOR_TRIAD,
            AUGMENTED_TRIAD,
            DIMINISHED_TRIAD,
            MAJOR_SEVENTH,
            DOMINANT_SEVENTH,
            MINOR_SEVENTH,
            MINOR_MAJOR_SEVENTH,
            DIMINISHED_SEVENTH,
            HALF_DIMINISHED_SEVENTH,
            AUGMENTED_SEVENTH,
            AUGMENTED_MAJOR_SEVENTH,
            MAJOR_SIXTH,
            MINOR_SIXTH
    };
    private static final Map<String, ChordType> CHORD_NAMES = new HashMap<>();
    private static final Map<String, ChordType> CHORD_SYMBOLS = new HashMap<>();

    static {
        for (ChordType c : CHORD_TYPES) {
            CHORD_NAMES.put(c.getName(), c);
            CHORD_SYMBOLS.put(c.getSymbol(), c);
        }
    }

    public static ChordType getByName(String chordName) {
        return CHORD_NAMES.get(chordName);
    }

    public static ChordType getBySymbol(String chordSymbol) {
        return CHORD_SYMBOLS.get(chordSymbol);
    }

    public Chord(Note root, ChordType type) {
        super(root, type);
    }

    public Chord makeChord(Note[] notes) {
        Note root = notes[0];
        Interval[] intervals = new Interval[notes.length - 1];
        for (int i = 1; i < notes.length; i++) {
            intervals[i - 1] = Interval.getInterval(notes[i - 1], notes[i]);
        }
        ChordType type = new ChordType(intervals);
        return new Chord(root, type);
    }

    @Override
    public NoteSet simplified(Accidental preferredAccidental) {
        Note[] notes = getNotes();
        for (int i = 0; i < notes.length; i++) {
            notes[i] = notes[i].simplified(preferredAccidental);
        }
        Interval[] simplifiedIntervals = new Interval[getIntervals().length];
        for (int i = 0; i < simplifiedIntervals.length; i++) {
            simplifiedIntervals[i] = Interval.getInterval(notes[i], notes[i + 1]);
        }
        return new Chord(notes[0], new ChordType(simplifiedIntervals));
    }

    public Chord getExtension(Interval interval) {
        return new Chord(getRoot(), ((ChordType) getType()).getExtension(interval));
    }

    public Chord getExtension(Interval interval, String name) {
        return new Chord(getRoot(), ((ChordType) getType()).getExtension(interval, name));
    }

    public Chord getExtension(Interval interval, String name, String symbol) {
        return new Chord(getRoot(), ((ChordType) getType()).getExtension(interval, name, symbol));
    }

    public Chord getInversion(int position) throws IllegalArgumentException {
        if (position < 0) {
            throw new IllegalArgumentException("Inversion position must be non-negative");
        }

        int numNotes = getNotes().length;
        position %= numNotes;
        Note[] newNotes = new Note[numNotes];

        for (int i = 0; i < numNotes; i++) {
            newNotes[i] = getNotes()[(i + position) % numNotes];
            if (i >= numNotes - position) {
                newNotes[i] = newNotes[i].increment(Interval.OCTAVE);
            }
        }
        return makeChord(newNotes);
    }
}
