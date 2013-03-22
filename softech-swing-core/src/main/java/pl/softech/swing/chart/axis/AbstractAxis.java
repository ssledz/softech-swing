package pl.softech.swing.chart.axis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.font.TextLayout;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import pl.softech.swing.chart.Range;

public abstract class AbstractAxis {

    protected static final double DEFAULT_AXE_TO_PLOT_DISTANCE = 0.2;

    protected Color color = Color.BLUE;
    protected Insets insets;

    protected Font baseLabelFont;

    private Range range;

    protected AxisState state;

    private ITickLabelFormatter tickLabelFormatter;

    protected int tickLabelMinQuantity;

    public AbstractAxis() {
        this.range = new Range(-1, 1);
        baseLabelFont = new Font("Dialog", Font.PLAIN, 12);
        tickLabelFormatter = new NumberTickLabelFormatter();
        tickLabelMinQuantity = 10;
    }

    public void setTickLabelMinQuantity(int tickLabelMinQuantity) {
        this.tickLabelMinQuantity = tickLabelMinQuantity;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public Range getRange() {
        return range;
    }

    public void setTickLabelFormatter(ITickLabelFormatter tickLabelFormatter) {
        this.tickLabelFormatter = tickLabelFormatter;
    }

    protected Rectangle2D computeBounds(Insets insets, double width, double height, Point2D cursor) {

        return new Rectangle2D.Double(cursor.getX() + insets.left, cursor.getY() + insets.top, width
                - (insets.left + insets.right), height - (insets.top + insets.bottom));
    }

    public abstract AxisState build(Graphics2D g2, Point2D cursor, double width, double height, Rectangle2D plotArea,
            Range range);

    public void draw() {
        Graphics2D g2 = state.getG2();
        g2.setColor(state.getAxisColor());
        g2.draw(state.getAxis());

        Iterator<Tick> tickIt = state.getTicks().iterator();
        Iterator<Line2D> tickLineIt = state.getTickLines().iterator();
        Iterator<Point2D> tickLabelCursorIt = state.getTickLabelCursors().iterator();

        while (tickIt.hasNext()) {

            Tick tick = tickIt.next();
            Line2D tickLine = tickLineIt.next();
            Point2D tickLabelCursor = tickLabelCursorIt.next();
            g2.draw(tickLine);
            drawTickLabel(g2, tickLabelCursor, state.getLabelFont(), tick, state.getLabel2AxisDist());
        }

    }

    protected abstract void drawTickLabel(Graphics2D g2, Point2D cursor, Font font, Tick tick, double label2AxisDist);

    protected float computeMaxFontSize(Graphics2D g2, Font font, double width, double height, List<Tick> ticks) {

        float fontSize = 30;

        for (Tick tick : ticks) {

            for (float size = 30; size > 0; size--) {
                TextLayout txtLayout = new TextLayout(tick.getText(), font.deriveFont(size), g2.getFontRenderContext());
                if (txtLayout.getBounds().getWidth() < width && txtLayout.getBounds().getHeight() < height) {
                    if (fontSize > size) {
                        fontSize = size;
                    }
                    break;
                }
            }
        }

        return fontSize;

    }

    protected List<Tick> createTicks(Range range, double plotAreaLength,double plotInset, double edgeLength, int minCount) {
        List<Tick> ret = new ArrayList<Tick>(minCount);

        double num = range.abs() / minCount;

        double fract = plotAreaLength / minCount;

        double cursor = edgeLength - plotAreaLength - plotInset;

        for (double it = range.getLower(); it < range.getUpper(); it += num, cursor += fract) {

            ret.add(new Tick(tickLabelFormatter.format(it), cursor));

        }

        return ret;

    }
}
