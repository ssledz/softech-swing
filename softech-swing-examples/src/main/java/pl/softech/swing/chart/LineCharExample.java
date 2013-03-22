package pl.softech.swing.chart;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;

public class LineCharExample {

    public static class SimpleData1 extends AbstractDataSetModel {

        private List<Point2D> list;

        public SimpleData1() {
            list = new LinkedList<Point2D>();
            double a = 1;
            double b = 1;
            double y;
            for (double x = -1; x < 20; x += 1) {
                y = a * x + b;
                list.add(new Point2D.Double(x, y));
            }
        }

        @Override
        public Collection<Point2D> getData() {
            return list;
        }

    }

    public static class SimpleData2 extends AbstractDataSetModel {

        private List<Point2D> list;

        public SimpleData2() {
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
        plot.addDataSeries(new SimpleData2());
        new Thread() {
            public void run() {
              
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                plot.addDataSeries(new SimpleData1());

            }
        }.start();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chart);
        frame.setSize(new Dimension(1024, 768));
        frame.setVisible(true);

    }

}
