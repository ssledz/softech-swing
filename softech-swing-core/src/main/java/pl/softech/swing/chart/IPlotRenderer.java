package pl.softech.swing.chart;

import java.awt.Graphics2D;

public interface IPlotRenderer {

    public void drawChart(Graphics2D g2, AbstractDataSetModel model, Plot plot);
    
}
