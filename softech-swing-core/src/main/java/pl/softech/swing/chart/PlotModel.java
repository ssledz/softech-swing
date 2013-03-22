package pl.softech.swing.chart;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import pl.softech.swing.chart.PlotModelEvent.Type;

public class PlotModel {

    private Point2D maxModelCoordinate;
    private Point2D minModelCoordinate;

    private double xRatio;
    private double yRatio;
    private Point2D startViewCoordinate;
    private Point2D startModelCoordinate;

    private Collection<AbstractDataSetModel> dataSeries;

    private final List<IPlotModelListener> listeners;

    public PlotModel(Collection<AbstractDataSetModel> dataSeries) {
        this.dataSeries = dataSeries;
        listeners = new LinkedList<IPlotModelListener>();
    }

    public Point2D getStartViewCoordinate() {
        return startViewCoordinate;
    }

    public void removeAllDataSeries() {
        dataSeries.clear();
        minModelCoordinate = null;
        maxModelCoordinate = null;
        fireDataChangeEvent();
    }

    public void removeDataSeries(AbstractDataSetModel series) {
        dataSeries.remove(series);

        if (dataSeries.size() == 0) {
            minModelCoordinate = null;
            maxModelCoordinate = null;
        }

        fireDataChangeEvent();
    }

    public void addDataSeries(AbstractDataSetModel series) {
        dataSeries.add(series);
        fireDataChangeEvent();
    }

    public Collection<AbstractDataSetModel> getDataSeries() {
        return dataSeries;
    }

    public void addListener(IPlotModelListener l) {
        listeners.add(l);
    }

    private void fireDataChangeEvent() {
        fireEvent(new PlotModelEvent(Type.DATA_CHANGE));
    }

    private void fireEvent(PlotModelEvent event) {
        for (IPlotModelListener l : listeners) {
            l.actionPerformed(event);
        }
    }

    private void computeMaxModelCoordinate() {

        double xMax = Double.MIN_VALUE;
        double yMax = Double.MIN_VALUE;

        for (AbstractDataSetModel model : dataSeries) {
            xMax = Math.max(xMax, model.getMaxX());
            yMax = Math.max(yMax, model.getMaxY());
        }
        maxModelCoordinate = new Point2D.Double(xMax, yMax);
    }

    private void computeMinModelCoordinate() {

        double xMin = Double.MAX_VALUE;
        double yMin = Double.MAX_VALUE;

        for (AbstractDataSetModel model : dataSeries) {
            xMin = Math.min(xMin, model.getMinX());
            yMin = Math.min(yMin, model.getMinY());
        }
        minModelCoordinate = new Point2D.Double(xMin, yMin);

    }

    public Range getXRange() {

        if (minModelCoordinate == null || maxModelCoordinate == null) {
            return new Range(-1, 1);
        }

        return new Range(minModelCoordinate.getX(), maxModelCoordinate.getX());

    }

    public Range getYRange() {

        if (minModelCoordinate == null || maxModelCoordinate == null) {
            return new Range(-1, 1);
        }

        return new Range(minModelCoordinate.getY(), maxModelCoordinate.getY());

    }

    public boolean computeModel(Rectangle2D rect) {

        if (dataSeries.size() == 0 || rect == null) {
            return true;
        }

        computeMaxModelCoordinate();
        computeMinModelCoordinate();

        double maxX = maxModelCoordinate.getX();
        double minX = minModelCoordinate.getX();
        double absX = maxX - minX;
        double xNegRatio = (minX < 0) ? -minX / absX : 0;

        double maxY = maxModelCoordinate.getY();
        double minY = minModelCoordinate.getY();
        double absY = maxY - minY;
        double yNegRatio = (minY < 0) ? -minY / absY : 0;

        startViewCoordinate = new Point2D.Double(xNegRatio * rect.getWidth() + rect.getX(), rect.getHeight()
                - yNegRatio * rect.getHeight() + rect.getY());

        startModelCoordinate = new Point2D.Double((minX < 0) ? 0 : minX, (minY < 0) ? 0 : minY);

        xRatio = rect.getWidth() / absX;
        yRatio = rect.getHeight() / absY;

        return true;

    }

    public Point2D coordFromModelToView(Point2D point) {

        if (startViewCoordinate == null || startModelCoordinate == null) {
            return null;
        }

        return new Point2D.Double(startViewCoordinate.getX() + (point.getX() - startModelCoordinate.getX()) * xRatio,
                startViewCoordinate.getY() - (point.getY() - startModelCoordinate.getY()) * yRatio);
    }

}
