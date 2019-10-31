package harmony;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.*;

public abstract class NoteSet {
    private Note root;
    private NoteSetType type;

    public NoteSet(Note root, NoteSetType type) {
        this.root = root;
        this.type = type;
    }

    public Note getRoot() {
        return root;
    }

    public NoteSetType getType() {
        return type;
    }

    public Interval[] getIntervals() {
        return type.getIntervals();
    }

    public Note[] getNotes() {
        Note[] notes = new Note[type.getIntervals().length + 1];
        notes[0] = root;
        for (int i = 0; i < type.getIntervals().length; i++) {
            notes[i + 1] = notes[i].increment(type.getIntervals()[i]);
        }
        return notes;
    }

    public PitchClass[] getPitchClasses() {
        return Stream.of(getNotes())
                .map(Note::getPitchClass)
                .toArray(PitchClass[]::new);
    }

    public String getName() {
        return root.getPitchClass().toString() + " " + type.getName();
    }

    public abstract NoteSet simplified(Accidental preferredAccidental);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteSet noteSet = (NoteSet) o;
        return Objects.equals(root, noteSet.root) &&
                Objects.equals(type, noteSet.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root, type);
    }

    @Override
    public String toString() {
        return Stream.of(getNotes())
                .map(Note::toString)
                .collect(Collectors.joining("-"));
    }

    public String toPitchClassString() {
        return Stream.of(getPitchClasses())
                .map(PitchClass::toString)
                .collect(Collectors.joining("-"));
    }
}
