package pl.softech.swing.chart.axis;


public class Tick {

    private final String text;
    private final double cursor;
    
    public Tick(String text, double cursor) {
        this.text = text;
        this.cursor = cursor;
    }

    public String getText() {
        return text;
    }

    public double getCursor() {
        return cursor;
    }
    
    
    
}
