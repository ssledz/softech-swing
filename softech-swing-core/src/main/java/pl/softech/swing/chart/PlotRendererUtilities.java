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

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
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
