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
package pl.softech.swing.chart.axis;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.font.TextLayout;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import pl.softech.swing.chart.Range;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class XAxis extends AbstractAxis {

    public XAxis() {
        this.insets = new Insets(0, 0, 0, 0);
    }

    public AxisState build(Graphics2D g2, Point2D cursor, double width, double height, Rectangle2D plotArea, Range range) {

        state = new AxisState(g2);

        Rectangle2D bounds = computeBounds(insets, width, height, cursor);

        state.setAxisColor(color);

        double axeYCoord = bounds.getY() + bounds.getHeight() * DEFAULT_AXE_TO_PLOT_DISTANCE;
        double tickHeight = bounds.getHeight() * 0.1;
        double label2AxisDist = 2 * tickHeight;

        state.setLabel2AxisDist(label2AxisDist);

        double y = axeYCoord + tickHeight;
        double labelHeight = bounds.getMaxY() - y - label2AxisDist;

        state.setAxis(new Line2D.Double(cursor.getX(), axeYCoord, bounds.getMaxX(), axeYCoord));

        double plotInset = plotArea.getX() - cursor.getX();
        
        List<Tick> ticks = createTicks(range, plotArea.getWidth(), plotInset, width, tickLabelMinQuantity);

        state.setTicks(ticks);

        double labelWidth = (width / ticks.size()) * 0.8;
        
        float fontSize = computeMaxFontSize(g2, baseLabelFont, labelWidth, labelHeight, ticks);

        Font labelFont = this.baseLabelFont.deriveFont(fontSize);

        state.setLabelFont(labelFont);

        for (Tick tick : ticks) {

            double x = cursor.getX() + tick.getCursor();

            state.addTickLine(new Line2D.Double(x, axeYCoord, x, y));

            state.addTickLabelCursor(x, y);

        }

        return state;
    }

    @Override
    protected void drawTickLabel(Graphics2D g2, Point2D cursor, Font font, Tick tick, double label2AxisDist) {

        TextLayout txtLayout = new TextLayout(tick.getText(), font, g2.getFontRenderContext());
        double xDrawCoord = cursor.getX() - txtLayout.getBounds().getCenterX();
        double yDrawCoord = cursor.getY() + label2AxisDist + txtLayout.getBounds().getHeight();
        txtLayout.draw(g2, (float) xDrawCoord, (float) yDrawCoord);

    }
}
