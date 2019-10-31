package harmony;

import java.util.Objects;

public class Interval {
    // 2 basic intervals
    public static final Interval UNISON = new Interval(0, 0);
    public static final Interval OCTAVE = new Interval(Pitch.PITCHES, Pitch.SEMITONES);

    // 12 major, minor, and perfect intervals
    public static final Interval PERFECT_UNISON = UNISON;
    public static final Interval MINOR_SECOND = new Interval(1, 1);
    public static final Interval MAJOR_SECOND = new Interval(1, 2);
    public static final Interval MINOR_THIRD = new Interval(2, 3);
    public static final Interval MAJOR_THIRD = new Interval(2, 4);
    public static final Interval PERFECT_FOURTH = new Interval(3, 5);
    public static final Interval PERFECT_FIFTH = new Interval(4, 7);
    public static final Interval MINOR_SIXTH = new Interval(5, 8);
    public static final Interval MAJOR_SIXTH = new Interval(5, 9);
    public static final Interval MINOR_SEVENTH = new Interval(6, 10);
    public static final Interval MAJOR_SEVENTH = new Interval(6, 11);
    public static final Interval PERFECT_OCTAVE = OCTAVE;

    // 14 augmented and diminished intervals
    public static final Interval DIMINISHED_SECOND = new Interval(1, 0);
    public static final Interval AUGMENTED_UNISON = new Interval(0, 1);
    public static final Interval DIMINISHED_THIRD = new Interval(2, 2);
    public static final Interval AUGMENTED_SECOND = new Interval(1, 3);
    public static final Interval DIMINISHED_FOURTH = new Interval(3, 4);
    public static final Interval AUGMENTED_THIRD = new Interval(2, 5);
    public static final Interval DIMINISHED_FIFTH = new Interval(4, 6);
    public static final Interval AUGMENTED_FOURTH = new Interval(3, 6);
    public static final Interval DIMINISHED_SIXTH = new Interval(5, 7);
    public static final Interval AUGMENTED_FIFTH = new Interval(4, 8);
    public static final Interval DIMINISHED_SEVENTH = new Interval(6, 9);
    public static final Interval AUGMENTED_SIXTH = new Interval(5, 10);
    public static final Interval DIMINISHED_OCTAVE = new Interval(7, 11);
    public static final Interval AUGMENTED_SEVENTH = new Interval(6, 12);


    private int degrees;
    private int semitones;

    public Interval(int degrees, int semitones) {
        this.degrees = degrees;
        this.semitones = semitones;
    }

    public int getDegrees() {
        return degrees;
    }

    public int getSemitones() {
        return semitones;
    }

    public Interval inverted() {
        return new Interval(-degrees, -semitones);
    }

    public static Interval getInterval(Note note1, Note note2) {
        int pitchChange = (note2.getOctave() * Pitch.PITCHES + note2.getPitch().ordinal())
                        - (note1.getOctave() * Pitch.PITCHES + note1.getPitch().ordinal());


        Note newNote2 = note2.getEnharmonic(note1.getPitch());
        int semitoneChange = (newNote2.getOctave() * Pitch.SEMITONES + newNote2.getAccidental().getSemitones())
                           - (note1.getOctave() * Pitch.SEMITONES + note1.getAccidental().getSemitones());

        return new Interval(pitchChange, semitoneChange);
    }

    // Override methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return degrees == interval.degrees &&
                semitones == interval.semitones;
    }

    @Override
    public int hashCode() {
        return Objects.hash(degrees, semitones);
    }

    @Override
    public String toString() {
        return "Interval{" +
                "degrees=" + degrees +
                ", semitones=" + semitones +
                '}';
    }
}
