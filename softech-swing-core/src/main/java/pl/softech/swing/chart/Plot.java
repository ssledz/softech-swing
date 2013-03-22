package pl.softech.swing.chart;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

public class Plot {

    private Rectangle2D plotArea;
    private Insets insets;
    private Color backgrounColor = new Color(192, 192, 192);

    private final PlotModel plotModel;
    private final Chart chart;

    private IPlotRenderer plotRenderer;

    public Plot(Chart chart) {
        this.chart = chart;
        this.plotModel = new PlotModel(new LinkedList<AbstractDataSetModel>());
        this.plotRenderer = new PlotLineChartRenderer(true);
        init();
    }
    
    private void init() {
        
        insets = new Insets(10, 10, 10, 10);
        
        plotModel.addListener(new IPlotModelListener() {
            
            @Override
            public void actionPerformed(PlotModelEvent event) {
                
                if(plotModel.computeModel(plotArea)) {
                    
                    chart.repaint();
                    
                }
                
            }
        });
    }

    public void setPlotRenderer(IPlotRenderer plotRenderer) {
        this.plotRenderer = plotRenderer;
    }

    public PlotModel getPlotModel() {
        return plotModel;
    }
    
    public void removeDataSeries(AbstractDataSetModel series) {
        plotModel.removeDataSeries(series);
    }
    
    public void removeAllDataSeries() {
        plotModel.removeAllDataSeries();
    }
    
    public void addDataSeries(AbstractDataSetModel series) {
        plotModel.addDataSeries(series);
    }

    public void draw(Graphics2D g2, Point2D cursor, double width, double height) {
        
        g2.setColor(backgrounColor);
        g2.fill(new Rectangle2D.Double(cursor.getX(), cursor.getY(), width, height));
        
        Rectangle2D bounds = computeBounds(insets, width, height, cursor);
        if(!bounds.equals(this.plotArea)) {
            this.plotArea = bounds;
            plotModel.computeModel(bounds);
        }
        
        for(AbstractDataSetModel model : plotModel.getDataSeries()) {
            plotRenderer.drawChart(g2, model, this);
        }
        
    }

    public Rectangle2D getPlotArea() {
        return plotArea;
    }

    private Rectangle2D computeBounds(Insets insets, double width, double height, Point2D cursor) {
        
        return new Rectangle2D.Double(
                cursor.getX() + insets.left, 
                cursor.getY() + insets.top, width - (insets.left + insets.right), 
                height - (insets.top + insets.bottom)
        );
        
    }
    
}
