/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.Level
 */
package ic2.core;

import ic2.core.IC2;
import net.minecraft.world.level.Level;

public class WindSim {
    private int windStrength = 5 + IC2.random.m_188503_(20);
    private int windDirection = IC2.random.m_188503_(360);
    public int windTicker;
    private final Level world;
    private final double[] windHeightCoefficients;

    public WindSim(Level level) {
        this.world = level;
        this.windHeightCoefficients = WindSim.calculateCoefficients(IC2.getWorldMaxHeight(level), IC2.getSeaLevel(level));
    }

    private static double[] calculateCoefficients(int n, int n2) {
        double[] dArray;
        double d;
        n = Math.max(1, n);
        double d2 = (n2 = Math.max(0, n2)) < n ? (double)n2 : (double)n * 0.5;
        double d3 = d2 + ((double)n - d2) / 2.0;
        double[] dArray2 = new double[]{d3, d3 * d3, d3 * d3 * d3, d = (double)n * 1.125, d * d, d * d * d, 1.0, 2.0 * d3, 3.0 * d3 * d3};
        if (!WindSim.solve(dArray2, dArray = new double[]{1.0, 0.0, 0.0})) {
            throw new RuntimeException("matrix inversion failed (height=" + n + ", sealevel=" + n2 + ")");
        }
        return dArray;
    }

    private static boolean solve(double[] dArray, double[] dArray2) {
        double d = 1.0 / (dArray[0] * dArray[4] * dArray[8] + dArray[1] * dArray[5] * dArray[6] + dArray[2] * dArray[3] * dArray[7] - dArray[2] * dArray[4] * dArray[6] - dArray[0] * dArray[5] * dArray[7] - dArray[1] * dArray[3] * dArray[8]);
        if (Double.isInfinite(d)) {
            return false;
        }
        double d2 = (dArray[4] * dArray[8] - dArray[5] * dArray[7]) * d;
        double d3 = (dArray[2] * dArray[7] - dArray[1] * dArray[8]) * d;
        double d4 = (dArray[1] * dArray[5] - dArray[2] * dArray[4]) * d;
        double d5 = (dArray[5] * dArray[6] - dArray[3] * dArray[8]) * d;
        double d6 = (dArray[0] * dArray[8] - dArray[2] * dArray[6]) * d;
        double d7 = (dArray[2] * dArray[3] - dArray[0] * dArray[5]) * d;
        double d8 = (dArray[3] * dArray[7] - dArray[4] * dArray[6]) * d;
        double d9 = (dArray[1] * dArray[6] - dArray[0] * dArray[7]) * d;
        double d10 = (dArray[0] * dArray[4] - dArray[1] * dArray[3]) * d;
        double d11 = d2 * dArray2[0] + d3 * dArray2[1] + d4 * dArray2[2];
        double d12 = d5 * dArray2[0] + d6 * dArray2[1] + d7 * dArray2[2];
        double d13 = d8 * dArray2[0] + d9 * dArray2[1] + d10 * dArray2[2];
        dArray2[0] = d11;
        dArray2[1] = d12;
        dArray2[2] = d13;
        return true;
    }

    public void updateWind() {
        if (this.windTicker++ % 128 != 0) {
            return;
        }
        int n = 10;
        int n2 = 10;
        if (this.windStrength > 20) {
            n -= this.windStrength - 20;
        } else if (this.windStrength < 10) {
            n2 -= 10 - this.windStrength;
        }
        if (IC2.random.m_188503_(100) < n) {
            ++this.windStrength;
        } else if (IC2.random.m_188503_(100) < n2) {
            --this.windStrength;
        }
        switch (IC2.random.m_188503_(3)) {
            case 0: {
                this.windDirection = this.chancewindDirection(-18);
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                this.windDirection = this.chancewindDirection(18);
            }
        }
    }

    public double getWindAt(double d) {
        double d2 = this.windStrength;
        double d3 = this.windHeightCoefficients[0] * d + this.windHeightCoefficients[1] * d * d + this.windHeightCoefficients[2] * d * d * d;
        d3 = Math.max(0.0, d3);
        d2 *= d3;
        if (this.world.m_46470_()) {
            d2 *= 1.5;
        } else if (this.world.m_46471_()) {
            d2 *= 1.25;
        }
        return d2 *= 2.4;
    }

    public double getMaxWind() {
        return 108.0;
    }

    private int chancewindDirection(int n) {
        this.windDirection += n;
        if (this.windDirection < 0) {
            return 359 - this.windDirection;
        }
        if (this.windDirection > 359) {
            return 0 + (this.windDirection - 359);
        }
        return this.windDirection;
    }
}

