/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.ops;

import ic2.shades.org.ejml.data.D1Matrix64F;
import ic2.shades.org.ejml.ops.CommonOps;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class MatrixComponent
extends JPanel {
    BufferedImage image;

    public MatrixComponent(int width, int height) {
        this.image = new BufferedImage(width, height, 1);
        this.setPreferredSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));
    }

    public synchronized void setMatrix(D1Matrix64F A) {
        double maxValue = CommonOps.elementMaxAbs(A);
        MatrixComponent.renderMatrix(A, this.image, maxValue);
        this.repaint();
    }

    public static void renderMatrix(D1Matrix64F M, BufferedImage image, double maxValue) {
        int w = image.getWidth();
        int h = image.getHeight();
        double widthStep = (double)M.numCols / (double)image.getWidth();
        double heightStep = (double)M.numRows / (double)image.getHeight();
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                int rgb;
                int p;
                double value = M.get((int)((double)i * heightStep), (int)((double)j * widthStep));
                if (value == 0.0) {
                    image.setRGB(j, i, -16777216);
                    continue;
                }
                if (value > 0.0) {
                    p = 255 - (int)(255.0 * (value / maxValue));
                    rgb = 0xFFFF0000 | p << 8 | p;
                    image.setRGB(j, i, rgb);
                    continue;
                }
                p = 255 + (int)(255.0 * (value / maxValue));
                rgb = 0xFF000000 | p << 16 | p << 8 | 0xFF;
                image.setRGB(j, i, rgb);
            }
        }
    }

    @Override
    public synchronized void paint(Graphics g) {
        g.drawImage(this.image, 0, 0, this);
    }
}

