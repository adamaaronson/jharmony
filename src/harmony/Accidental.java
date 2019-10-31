package harmony;

import java.util.Objects;

public class Accidental {
    public static final Accidental DOUBLE_FLAT = new Accidental(-2);
    public static final Accidental FLAT = new Accidental(-1);
    public static final Accidental NATURAL = new Accidental(0);
    public static final Accidental SHARP = new Accidental(1);
    public static final Accidental DOUBLE_SHARP = new Accidental(2);

    private int semitones;

    public Accidental(int semitones) {
        this.semitones = semitones;
    }

    public Accidental(String accidentalName) throws InvalidNameException {
        if (!accidentalName.matches("^(b*|#*)$")) {
            throw new InvalidNameException("Invalid accidental name: " + accidentalName);
        }
        if (accidentalName.length() == 0) {
            semitones = 0;
        } else {
            semitones = accidentalName.length() * (accidentalName.charAt(0) == '#' ? 1 : -1);
        }
    }

    public int getSemitones() {
        return semitones;
    }


    // Override methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accidental that = (Accidental) o;
        return semitones == that.semitones;
    }

    @Override
    public int hashCode() {
        return Objects.hash(semitones);
    }

    @Override
    public String toString() {
        if (semitones > 0) {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < semitones; i++) {
                str.append("#");
            }
            return str.toString();
        } else if (semitones < 0) {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < -semitones; i++) {
                str.append("b");
            }
            return str.toString();
        } else {
            return "";
        }
    }
}
