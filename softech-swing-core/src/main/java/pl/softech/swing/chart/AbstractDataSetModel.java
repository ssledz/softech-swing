package pl.softech.swing.chart;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Collection;

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
