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

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class BarChartExample {

    public static class SimpleData1 extends AbstractDataSetModel {

        private List<Point2D> list;

        public SimpleData1() {
            list = new LinkedList<Point2D>();
            double a = 1;
            double b = 1;
            double c = -10;
            double y;
            for (double x = -5; x < 5; x += 0.5) {
                y = a * x * x + b * x + c;
                list.add(new Point2D.Double(x, y));
            }
        }

        @Override
        public Collection<Point2D> getData() {
            return list;
        }

    }
    
    public static void main(String[] args) {

        Chart chart = new Chart();
        
        final Plot plot = chart.getPlot();
        plot.setPlotRenderer(new PlotBarChartRenderer());
        plot.addDataSeries(new SimpleData1());

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chart);
        frame.setSize(new Dimension(1024, 768));
        frame.setVisible(true);

    }
    
}
