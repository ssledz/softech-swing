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
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Collection;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class PlotDotedLineChartRenderer implements IPlotRenderer {

    @Override
    public void drawChart(Graphics2D g2, AbstractDataSetModel model, Plot plot) {
        double pointRadius = 6;
        Color graphColor = model.getGraphColor();

        Collection<Point2D> points = model.getData();
        
        Point2D[] tab = PlotRendererUtilities.sortByX(points);

        Point2D last = null;

        PlotModel plotModel = plot.getPlotModel();

        for (int i = 0; i < tab.length; i++) {

            Color color = g2.getColor();
            g2.setColor(graphColor);

            Point2D p = plotModel.coordFromModelToView(tab[i]);

            Ellipse2D ellipse = new Ellipse2D.Double(p.getX() - pointRadius / 2, p.getY() - pointRadius / 2,
                    pointRadius, pointRadius);

            g2.fill(ellipse);

            if (last != null) {
                Line2D line = new Line2D.Double(last, p);
                g2.draw(line);
            }
            last = p;
            g2.setColor(color);

        }

    }

}
