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
import java.awt.geom.Point2D;
import java.util.Collection;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public abstract class AbstractDataSetModel {

    private static final Color DEFAULT_GRAPH_COLOR = Color.RED;

    private static int instancesQuantity = 0;
    
    private Color graphColor = DEFAULT_GRAPH_COLOR;
    private Point2D maxPointCoordinate;
    private Point2D minPointCoordinate;
    private String name;

    public AbstractDataSetModel() {
        ++instancesQuantity;
        name = "series" + instancesQuantity;
    }
    
    private void computeMinMaxCoord() {

        Collection<Point2D> points = getData();

        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;

        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;

        for (Point2D p : points) {
            maxX = Math.max(maxX, p.getX());
            maxY = Math.max(maxY, p.getY());

            minX = Math.min(minX, p.getX());
            minY = Math.min(minY, p.getY());
        }

        maxPointCoordinate = new Point2D.Double(maxX, maxY);
        minPointCoordinate = new Point2D.Double(minX, minY);

    }

    public abstract Collection<Point2D> getData();

    public String getName() {
        return name;
    }

    public Color getGraphColor() {
        return graphColor;
    }

    public double getMaxX() {
        if (maxPointCoordinate == null)
            computeMinMaxCoord();
        return maxPointCoordinate.getX();
    }

    public double getMaxY() {
        if (maxPointCoordinate == null)
            computeMinMaxCoord();
        return maxPointCoordinate.getY();
    }

    public double getMinX() {
        if (minPointCoordinate == null)
            computeMinMaxCoord();
        return minPointCoordinate.getX();
    }

    public double getMinY() {
        if (minPointCoordinate == null)
            computeMinMaxCoord();
        return minPointCoordinate.getY();
    }
}
