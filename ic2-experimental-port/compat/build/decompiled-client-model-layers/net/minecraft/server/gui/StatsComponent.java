/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.Timer;
import net.minecraft.Util;
import net.minecraft.server.MinecraftServer;

public class StatsComponent
extends JComponent {
    private static final DecimalFormat f_139955_ = Util.m_137469_(new DecimalFormat("########0.000"), p_139968_ -> p_139968_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT)));
    private final int[] f_139956_ = new int[256];
    private int f_139957_;
    private final String[] f_139958_ = new String[11];
    private final MinecraftServer f_139959_;
    private final Timer f_139960_;

    public StatsComponent(MinecraftServer p_139963_) {
        this.f_139959_ = p_139963_;
        this.setPreferredSize(new Dimension(456, 246));
        this.setMinimumSize(new Dimension(456, 246));
        this.setMaximumSize(new Dimension(456, 246));
        this.f_139960_ = new Timer(500, p_139966_ -> this.m_139971_());
        this.f_139960_.start();
        this.setBackground(Color.BLACK);
    }

    private void m_139971_() {
        long $$0 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        this.f_139958_[0] = "Memory use: " + $$0 / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
        this.f_139958_[1] = "Avg tick: " + f_139955_.format(this.m_139969_(this.f_139959_.f_129748_) * 1.0E-6) + " ms";
        this.f_139956_[this.f_139957_++ & 0xFF] = (int)($$0 * 100L / Runtime.getRuntime().maxMemory());
        this.repaint();
    }

    private double m_139969_(long[] p_139970_) {
        long $$1 = 0L;
        for (long $$2 : p_139970_) {
            $$1 += $$2;
        }
        return (double)$$1 / (double)p_139970_.length;
    }

    @Override
    public void paint(Graphics p_139973_) {
        p_139973_.setColor(new Color(0xFFFFFF));
        p_139973_.fillRect(0, 0, 456, 246);
        for (int $$1 = 0; $$1 < 256; ++$$1) {
            int $$2 = this.f_139956_[$$1 + this.f_139957_ & 0xFF];
            p_139973_.setColor(new Color($$2 + 28 << 16));
            p_139973_.fillRect($$1, 100 - $$2, 1, $$2);
        }
        p_139973_.setColor(Color.BLACK);
        for (int $$3 = 0; $$3 < this.f_139958_.length; ++$$3) {
            String $$4 = this.f_139958_[$$3];
            if ($$4 == null) continue;
            p_139973_.drawString($$4, 32, 116 + $$3 * 16);
        }
    }

    public void m_139964_() {
        this.f_139960_.stop();
    }
}

