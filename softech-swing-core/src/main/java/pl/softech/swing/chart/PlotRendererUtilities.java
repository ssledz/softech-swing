package pl.softech.swing.chart;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class PlotRendererUtilities {

    public static Point2D[] sortByX(Collection<Point2D> points) {
        
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
        
        return tab;
        
    }
    
}
