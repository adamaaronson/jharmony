package harmony;

import java.util.Objects;

public class PitchClass {
    private Pitch pitch;
    private Accidental accidental;

    public PitchClass(Pitch pitch, Accidental accidental) {
        this.pitch = pitch;
        this.accidental = accidental;
    }

    public Pitch getPitch() {
        return pitch;
    }

    public Accidental getAccidental() {
        return accidental;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PitchClass that = (PitchClass) o;
        return pitch == that.pitch &&
                Objects.equals(accidental, that.accidental);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pitch, accidental);
    }

    @Override
    public String toString() {
        return pitch.toString() + accidental.toString();
    }
}
