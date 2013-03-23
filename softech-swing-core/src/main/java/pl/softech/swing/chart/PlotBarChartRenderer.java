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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class PlotBarChartRenderer implements IPlotRenderer {

    @Override
    public void drawChart(Graphics2D g2, AbstractDataSetModel model, Plot plot) {

        double pointRadius = 5;

        PlotModel plotModel = plot.getPlotModel();

        Collection<Point2D> points = model.getData();
        Color graphColor = model.getGraphColor();
        Point2D[] tab = new Point2D[0];
        tab = points.toArray(tab);
        Arrays.sort(tab, new Comparator<Point2D>() {

            @Override
            public int compare(Point2D o1, Point2D o2) {

                double diff = o1.getX() - o2.getX();
                if (diff == 0)
                    return 0;

                return ((diff > 0) ? 1 : -1);
            }

        });

        Color color = g2.getColor();
        Composite composite = g2.getComposite();
        float alpha = 0.1f;

        AlphaComposite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);

        g2.setColor(graphColor);

        Point2D last = null;

        Point2D startCoord = plotModel.getStartViewCoordinate();
        for (int i = 0; i < tab.length; i++) {

            Point2D p = plotModel.coordFromModelToView(tab[i]);

            Ellipse2D ellipse = new Ellipse2D.Double(p.getX() - pointRadius / 2, p.getY() - pointRadius / 2,
                    pointRadius, pointRadius);

            g2.fill(ellipse);

            if (last == null) {
                last = p;
                continue;
            }

            Rectangle2D rect;
            if (tab[i - 1].getY() < 0) {

                rect = new Rectangle2D.Double(last.getX(), startCoord.getY(), p.getX() - last.getX(), last.getY()
                        - startCoord.getY());

            } else {

                rect = new Rectangle2D.Double(last.getX(), last.getY(), p.getX() - last.getX(), startCoord.getY()
                        - last.getY());

            }
            g2.draw(rect);
            g2.setComposite(alphaComp);
            g2.fill(rect);
            g2.setComposite(composite);
            last = p;

        }

        Rectangle2D rect;
        Point2D max = plotModel.coordFromModelToView(new Point2D.Double(model.getMaxX(), model.getMaxY()));
        if (tab[tab.length - 1].getY() < 0) {

            rect = new Rectangle2D.Double(last.getX(), startCoord.getY(), max.getX() - last.getX(), last.getY()
                    - startCoord.getY());

        } else {

            rect = new Rectangle2D.Double(last.getX(), last.getY(), max.getX() - last.getX(), startCoord.getY()
                    - last.getY());

        }
        g2.draw(rect);
        g2.setComposite(alphaComp);
        g2.fill(rect);
        g2.setComposite(composite);

        g2.setColor(color);

    }

}
