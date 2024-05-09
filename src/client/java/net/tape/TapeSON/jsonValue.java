package net.tape.TapeSON;

public class jsonValue {
    private final String type;
    private final String source;

    jsonValue(String type, String source) {
        this.type = type;
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public String getSource() {
        return source;
    }
}
