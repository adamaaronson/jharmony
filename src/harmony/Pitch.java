package harmony;

public enum Pitch {
    C,
    D,
    E,
    F,
    G,
    A,
    B;

    public final static int PITCHES = 7;
    public final static int SEMITONES = 12;

    public Pitch increment(int pitches) {
        int nextIndex = (((this.ordinal() + pitches) % PITCHES) + PITCHES) % PITCHES;
        return Pitch.values()[nextIndex];
    }

    public Pitch next() {
        return this.increment(1);
    }

    public Pitch prev() {
        return this.increment(-1);
    }

    public static Pitch getPitch(String name) {
        switch (name) {
            case "C":
                return C;
            case "D":
                return D;
            case "E":
                return E;
            case "F":
                return F;
            case "G":
                return G;
            case "A":
                return A;
            case "B":
                return B;
            default:
                return null;
        }
    }
}
