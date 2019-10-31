package harmony;

import java.util.HashMap;
import java.util.Map;

public class Scale extends NoteSet {
    public static final ScaleType MAJOR = new ScaleType(new Interval[] {
            Interval.MAJOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.MINOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.MINOR_SECOND
    }, "major");

    public static final ScaleType MINOR = MAJOR.getMode(5, "minor");

    public static final ScaleType MELODIC_MINOR = new ScaleType(new Interval[] {
            Interval.MAJOR_SECOND,
            Interval.MINOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.MINOR_SECOND
    }, "melodic minor");

    public static final ScaleType HARMONIC_MINOR = new ScaleType(new Interval[] {
            Interval.MAJOR_SECOND,
            Interval.MINOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.MINOR_SECOND,
            Interval.AUGMENTED_SECOND,
            Interval.MINOR_SECOND
    }, "harmonic minor");

    public static final ScaleType WHOLE_TONE = new ScaleType(new Interval[] {
            Interval.MAJOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.DIMINISHED_THIRD
    }, "whole tone");

    public static final ScaleType PENTATONIC = new ScaleType(new Interval[] {
            Interval.MAJOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.MINOR_THIRD,
            Interval.MAJOR_SECOND,
            Interval.MINOR_THIRD
    }, "pentatonic");

    public static final ScaleType BLUES = new ScaleType(new Interval[] {
            Interval.MINOR_THIRD,
            Interval.MAJOR_SECOND,
            Interval.MINOR_SECOND,
            Interval.AUGMENTED_UNISON,
            Interval.MINOR_THIRD,
            Interval.MAJOR_SECOND
    }, "blues");

    public static final ScaleType BEBOP = new ScaleType(new Interval[] {
            Interval.MAJOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.MINOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.MAJOR_SECOND,
            Interval.MINOR_SECOND,
            Interval.AUGMENTED_UNISON,
            Interval.MINOR_SECOND
    }, "bebop");

    public static final ScaleType IONIAN = MAJOR.getMode(0, "ionian");
    public static final ScaleType DORIAN = MAJOR.getMode(1, "dorian");
    public static final ScaleType PHRYGIAN = MAJOR.getMode(2, "phrygian");
    public static final ScaleType LYDIAN = MAJOR.getMode(3, "lydian");
    public static final ScaleType MIXOLYDIAN = MAJOR.getMode(4, "mixolydian");
    public static final ScaleType AEOLIAN = MAJOR.getMode(5, "aeolian");
    public static final ScaleType LOCRIAN = MAJOR.getMode(6, "locrian");

    public static final ScaleType LYDIAN_DOMINANT = MELODIC_MINOR.getMode(3, "lydian dominant");
    public static final ScaleType ALTERED = MELODIC_MINOR.getMode(6, "altered");

    private static final ScaleType[] SCALE_TYPES = new ScaleType[] {
            MAJOR,
            MINOR,
            MELODIC_MINOR,
            HARMONIC_MINOR,
            WHOLE_TONE,
            PENTATONIC,
            BLUES,
            BEBOP,
            IONIAN,
            DORIAN,
            PHRYGIAN,
            LYDIAN,
            MIXOLYDIAN,
            AEOLIAN,
            LOCRIAN,
            LYDIAN_DOMINANT,
            ALTERED
    };
    private static final Map<String, ScaleType> SCALE_NAMES = new HashMap<>();

    static {
        for (ScaleType s : SCALE_TYPES) {
            SCALE_NAMES.put(s.getName(), s);
        }
    }

    public static ScaleType getByName(String scaleName) {
        return SCALE_NAMES.get(scaleName);
    }

    public Scale(Note root, ScaleType type) {
        super(root, type);
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
        return new Scale(notes[0], new ScaleType(simplifiedIntervals));
    }

    public static void main(String[] args) {
        System.out.println(new Scale(new Note(Pitch.E, new Accidental(-3)), LOCRIAN));
    }
}
