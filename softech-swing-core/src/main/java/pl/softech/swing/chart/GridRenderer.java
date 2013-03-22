package pl.softech.swing.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import pl.softech.swing.chart.axis.AxisState;

public class GridRenderer {

    private float dash1[] = { 10.0f };
    private BasicStroke dashed;
    private Color color;

    public GridRenderer() {

        dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
        color = Color.WHITE;
    }
    
    
    public void draw(Graphics2D g2, Rectangle2D plotArea, AxisState xAxisState, AxisState yAxisState) {
        
        double x1 = plotArea.getX();
        double x2 = plotArea.getMaxX();
        
        double y1 = plotArea.getY();
        double y2 = plotArea.getMaxY();
        
        g2.setStroke(dashed);
        g2.setColor(color);
        
        for(Line2D tickLine : yAxisState.getTickLines()) {
            double y = tickLine.getY1();
            
            if(y <= y1) {
                continue;
            }
            
            g2.draw(new Line2D.Double(x1, y, x2 , y));
            
        }
        
        
        
        for(Line2D tickLine : xAxisState.getTickLines()) {
            double x = tickLine.getX1();
            
            if(x >= x2) {
                continue;
            }
            
            g2.draw(new Line2D.Double(x, y1, x , y2));
            
        }
        
    }

}

