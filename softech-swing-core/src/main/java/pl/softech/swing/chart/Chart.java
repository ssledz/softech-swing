/*
 * Copyright 2013 Sławomir Śledź <slawomir.sledz@sof-tech.pl>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.softech.swing.chart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import pl.softech.swing.chart.axis.AxisState;
import pl.softech.swing.chart.axis.XAxis;
import pl.softech.swing.chart.axis.YAxis;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class Chart extends JPanel {

    private static final long serialVersionUID = 1L;
    private int width;
    private int height;
    
    private BufferedImage imgBuffer;
    
    private final Plot plot;
    private final XAxis xAxis;
    private final YAxis yAxis;
    private final GridRenderer gridRenderer; 
    private Color backgrounColor = Color.WHITE;
    
    private Insets insets;
    
    public Chart() {
        this.insets = new Insets(10, 20, 10, 20);
        this.plot = new Plot(this);
        this.xAxis = new XAxis();
        this.yAxis = new YAxis();
        this.gridRenderer = new GridRenderer();
    }
    
    public Plot getPlot() {
        return plot;
    }
    
    public XAxis getxAxis() {
        return xAxis;
    }

    public YAxis getyAxis() {
        return yAxis;
    }

    private void draw(Graphics2D g2, Point2D cursor, double width, double height) {
        
        double widthFactor = 0.95f;
        double heightFactor = 0.95f;
        
        double yAxeWidth = width - (width * widthFactor);
        double xAxeHeight = height - height * heightFactor;
        
        Point2D.Double localCursor = new Point2D.Double(cursor.getX(), cursor.getY());
        localCursor.x += yAxeWidth;
        
        plot.draw(g2, localCursor, width * widthFactor, height * heightFactor);
        
        Range plotYRange = plot.getPlotModel().getYRange();
        
        AxisState yAxisState = yAxis.build(g2, cursor, yAxeWidth, height * heightFactor, plot.getPlotArea(), plotYRange);
        
        localCursor.y += height * heightFactor;
        
        Range plotXRange = plot.getPlotModel().getXRange();
        
        AxisState xAxisState = xAxis.build(g2, localCursor, width * widthFactor, xAxeHeight, plot.getPlotArea(), plotXRange);
        
        synFontSize(xAxisState, yAxisState);
        
        xAxis.draw();
        yAxis.draw();
        
        
        gridRenderer.draw(g2, plot.getPlotArea(), xAxisState, yAxisState);
        
    }
    
    private void synFontSize(AxisState xAxisState, AxisState yAxisState) {
        float xAxisfontSize = xAxisState.getLabelFont().getSize2D();
        float yAxisfontSize = yAxisState.getLabelFont().getSize2D();
        
        if(xAxisfontSize < yAxisfontSize) {
            yAxisState.setLabelFontSize(xAxisfontSize);
        } else {
            xAxisState.setLabelFontSize(yAxisfontSize);
        }
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        int w = getWidth();
        int h = getHeight();
        if (width != w || height != h || imgBuffer == null) {
            width = w;
            height = h;
            imgBuffer = getGraphicsConfiguration().createCompatibleImage(width, height);
        }

        Graphics2D g2 = imgBuffer.createGraphics();
        initRenderingHints(g2);

        g2.setColor(backgrounColor);
        g2.fillRect(0, 0, w, h);
        
        draw(g2, new Point2D.Double(insets.left, insets.top), 
                width - (insets.left + insets.right), 
                height - (insets.top + insets.bottom));
        
        g2.dispose();

        ((Graphics2D) g).drawImage(imgBuffer, null, 0, 0);
    }
    
    private void initRenderingHints(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    }
}
