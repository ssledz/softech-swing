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
package pl.softech.swing.chart.axis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class AxisState {

    private final Graphics2D g2;

    private Font labelFont;

    private Color axisColor;

    private Line2D axis;

    private List<Tick> ticks;

    private List<Point2D> tickLabelCursors;

    private List<Line2D> tickLines;

    private double label2AxisDist;

    public AxisState(Graphics2D g2) {
        this.g2 = g2;
        tickLines = new LinkedList<Line2D>();
        tickLabelCursors = new LinkedList<Point2D>();
    }

    public void setLabelFontSize(float size) {
        
        labelFont = labelFont.deriveFont(size);
        
    }
    
    public Graphics2D getG2() {
        return g2;
    }

    public double getLabel2AxisDist() {
        return label2AxisDist;
    }

    public void setLabel2AxisDist(double label2AxisDist) {
        this.label2AxisDist = label2AxisDist;
    }

    public void addTickLine(Line2D line) {
        tickLines.add(line);
    }

    public void addTickLabelCursor(double x, double y) {
        tickLabelCursors.add(new Point2D.Double(x, y));
    }

    public List<Point2D> getTickLabelCursors() {
        return tickLabelCursors;
    }

    public Font getLabelFont() {
        return labelFont;
    }

    public Color getAxisColor() {
        return axisColor;
    }

    public void setAxisColor(Color axisColor) {
        this.axisColor = axisColor;
    }

    public void setLabelFont(Font labelFont) {
        this.labelFont = labelFont;
    }

    public Line2D getAxis() {
        return axis;
    }

    public void setAxis(Line2D axis) {
        this.axis = axis;
    }

    public List<Tick> getTicks() {
        return ticks;
    }

    public void setTicks(List<Tick> ticks) {
        this.ticks = ticks;
    }

    public List<Line2D> getTickLines() {
        return tickLines;
    }

}
