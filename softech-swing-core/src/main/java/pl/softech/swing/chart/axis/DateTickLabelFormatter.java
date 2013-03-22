package pl.softech.swing.chart.axis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTickLabelFormatter implements ITickLabelFormatter {

    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    public String format(double value) {
        return df.format(new Date((long) value));
    }

}
