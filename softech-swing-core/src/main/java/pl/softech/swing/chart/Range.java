package pl.softech.swing.chart;

public class Range {

    private double lower;
    private double upper;
    
    public Range(double lower, double upper) {
        super();
        this.lower = lower;
        this.upper = upper;
    }
    public double getLower() {
        return lower;
    }
    public double getUpper() {
        return upper;
    }
    
    public double abs() {
        return upper - lower;
    }
    
}
