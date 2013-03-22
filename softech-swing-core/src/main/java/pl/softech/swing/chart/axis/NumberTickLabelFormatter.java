package pl.softech.swing.chart.axis;

import java.text.NumberFormat;

public class NumberTickLabelFormatter implements ITickLabelFormatter {

    private final NumberFormat nbf = NumberFormat.getInstance();
    
    @Override
    public String format(double value) {
        return nbf.format(value);
    }

}
