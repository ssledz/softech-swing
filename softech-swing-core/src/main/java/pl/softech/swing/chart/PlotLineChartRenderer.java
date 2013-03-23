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
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class PlotLineChartRenderer implements IPlotRenderer {

    private boolean drawWithGradient = false;

    public PlotLineChartRenderer(boolean drawWithGradient) {
        this.drawWithGradient = drawWithGradient;
    }

    @Override
    public void drawChart(Graphics2D g2, AbstractDataSetModel model, Plot plot) {

        PlotModel plotModel = plot.getPlotModel();

        Color graphColor = model.getGraphColor();

        Point2D[] points = PlotRendererUtilities.sortByX(model.getData());

        Path2D path = new Path2D.Double();

        Point2D point = plotModel.coordFromModelToView(new Point2D.Double(points[0].getX(), model.getMinY()));

        path.moveTo(point.getX(), point.getY());

        for (int i = 0; i < points.length; i++) {

            point = plotModel.coordFromModelToView(points[i]);

            path.lineTo(point.getX(), point.getY());

        }

        point = plotModel.coordFromModelToView(new Point2D.Double(points[points.length - 1].getX(), model.getMinY()));

        path.lineTo(point.getX(), point.getY());
        path.closePath();

        if (drawWithGradient) {
            Point2D p1 = plotModel.coordFromModelToView(new Point2D.Double(model.getMinX(), model.getMaxY()));
            Point2D p2 = plotModel.coordFromModelToView(new Point2D.Double(model.getMaxX(), model.getMinY()));

            g2.setPaint(new GradientPaint((float) p1.getX(), (float) p1.getY(), graphColor, (float) p2.getX(),
                    (float) p2.getY(), Color.BLACK));
        } else {
            g2.setColor(graphColor);
        }

        g2.fill(path);
    }

}
