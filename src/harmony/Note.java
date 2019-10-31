package harmony;

import java.util.Objects;

public class Note {
    private PitchClass pitchClass;
    private int octave;

    public Note(Pitch pitch, Accidental accidental, int octave) {
        this.pitchClass = new PitchClass(pitch, accidental);
        this.octave = octave;
    }

    public Note(Pitch pitch, Accidental accidental) {
        this(pitch, accidental, 4);
    }

    public Note(Pitch pitch, int octave) {
        this(pitch, Accidental.NATURAL, octave);
    }

    public Note(Pitch pitch) {
        this(pitch, Accidental.NATURAL, 4);
    }

    public Note(String noteName) throws InvalidNameException {
        if (noteName.matches("^[ABCDEFG](b*|#*)$")) { // pitch and accidental
            this.pitchClass = new PitchClass(Pitch.getPitch(noteName.substring(0, 1)),
                                             new Accidental(noteName.substring(1)));
            this.octave = 4;
        } else if (noteName.matches("^[ABCDEFG]-?[0-9]*$")) { // pitch and octave
            this.pitchClass = new PitchClass(Pitch.getPitch(noteName.substring(0, 1)),
                                             Accidental.NATURAL);
            this.octave = Integer.parseInt(noteName.substring(1));
        } else if (noteName.matches("^[ABCDEFG](b*|#*)-?[0-9]*$")) { // pitch, accidental, and octave
            char accidentalChar = noteName.charAt(1);
            int accidental = 0;
            while (noteName.charAt(accidental + 1) == accidentalChar) {
                accidental++;
            }

            this.pitchClass = new PitchClass(Pitch.getPitch(noteName.substring(0, 1)),
                                             new Accidental(accidentalChar == '#' ? accidental : -accidental));
            this.octave = Integer.parseInt(noteName.substring(1 + accidental));
        } else {
            throw new InvalidNameException("Invalid note name: " + noteName);
        }
    }

    public PitchClass getPitchClass() {
        return pitchClass;
    }

    public Pitch getPitch() {
        return pitchClass.getPitch();
    }

    public Accidental getAccidental() {
        return pitchClass.getAccidental();
    }

    public int getOctave() {
        return octave;
    }

    // increments the Note by the given number of degrees and semitones
    public Note increment(int degrees, int semitones) {
        Pitch newPitch = pitchClass.getPitch();
        int newSemitones = semitones;
        int newOctave = octave;

        if (degrees > 0) { // positive increment
            for (int i = 0; i < degrees; i++) {
                if (newPitch == Pitch.B || newPitch == Pitch.E) {
                    newSemitones--;
                } else {
                    newSemitones -= 2;
                }
                newPitch = newPitch.next();
                if (newPitch == Pitch.C) {
                    newOctave++;
                }
            }
        } else if (degrees < 0) { // negative increment
            for (int i = 0; i < -degrees; i++) {
                if (newPitch == Pitch.C || newPitch == Pitch.F) {
                    newSemitones++;
                } else {
                    newSemitones += 2;
                }
                newPitch = newPitch.prev();
                if (newPitch == Pitch.B) {
                    newOctave--;
                }
            }
        }

        return new Note(newPitch, new Accidental(pitchClass.getAccidental().getSemitones() + newSemitones), newOctave);
    }

    // increments the given note by the given interval
    public Note increment(Interval interval) {
        return increment(interval.getDegrees(), interval.getSemitones());
    }

    public Note getEnharmonic(Pitch targetPitch) {
        Note flatterEnharmonic = new Note(pitchClass.getPitch(), pitchClass.getAccidental(), octave);
        Note sharperEnharmonic = new Note(pitchClass.getPitch(), pitchClass.getAccidental(), octave);

        while (flatterEnharmonic.getPitch() != targetPitch && sharperEnharmonic.getPitch() != targetPitch) {
            flatterEnharmonic = flatterEnharmonic.increment(1, 0);
            sharperEnharmonic = sharperEnharmonic.increment(-1, 0);
        }

        return flatterEnharmonic.getPitch() == targetPitch ? flatterEnharmonic : sharperEnharmonic;
    }

    public boolean isEnharmonic(Note other) {
        Note newOther = other;
        while (getPitch() != newOther.getPitch()) {
            newOther = newOther.increment(1, 0);
        }
        return newOther.getAccidental().getSemitones() - pitchClass.getAccidental().getSemitones()
               == Pitch.SEMITONES * (octave - newOther.getOctave());
    }

    public Note simplified(Accidental preferredAccidental) {
        Accidental pref = preferredAccidental.getSemitones() >= 0 ? Accidental.SHARP : Accidental.FLAT;
        Note simplified = this;

        while (!simplified.getAccidental().equals(Accidental.NATURAL) && !simplified.getAccidental().equals(pref)) {
            if (simplified.getAccidental().getSemitones() > 0) {
                simplified = simplified.increment(1, 0);
            } else {
                simplified = simplified.increment(-1, 0);
            }
        }

        if (simplified.getPitchClass().equals(new PitchClass(Pitch.C, Accidental.FLAT)) ||
            simplified.getPitchClass().equals(new PitchClass(Pitch.F, Accidental.FLAT))) {
            simplified = simplified.increment(-1, 0);
        } else if (simplified.getPitchClass().equals(new PitchClass(Pitch.B, Accidental.SHARP)) ||
                   simplified.getPitchClass().equals(new PitchClass(Pitch.E, Accidental.SHARP))) {
            simplified = simplified.increment(1, 0);
        }

        return simplified;
    }


    // Override methods


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return octave == note.octave &&
                Objects.equals(pitchClass, note.pitchClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pitchClass, octave);
    }

    @Override
    public String toString() {
        return pitchClass.toString() + octave;
    }
}
