/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 */
package ic2.api.crops;

import ic2.api.crops.ICropTile;
import java.util.List;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class CropDifficulty {
    int level;

    public CropDifficulty(int level) {
        this.level = level;
    }

    public int getWeedChance() {
        switch (this.level) {
            case 0: {
                return 25;
            }
            case 1: {
                return 50;
            }
            case 3: {
                return 200;
            }
        }
        return 100;
    }

    public int getCropGrowth(int value) {
        switch (this.level) {
            case 0: {
                return value * 2;
            }
            case 1: {
                return (int)((double)value * 1.3);
            }
            case 3: {
                if (value == 0) {
                    return 0;
                }
                return (int)Math.max(1.0, (double)value * 0.75);
            }
        }
        return value;
    }

    public int getBreedingDifficulty() {
        switch (this.level) {
            case 0: {
                return 10;
            }
            case 1: {
                return 6;
            }
            case 3: {
                return 2;
            }
        }
        return 4;
    }

    public int[] getCropStats(List<ICropTile> crops, RandomSource rand) {
        int[] result = new int[3];
        int size = crops.size();
        for (int k = 0; k < size; ++k) {
            ICropTile tile = crops.get(k);
            result[0] = result[0] + tile.getGrowthStat();
            result[1] = result[1] + tile.getResistanceStat();
            result[2] = result[2] + tile.getGainStat();
        }
        for (int i = 0; i < result.length; ++i) {
            int value = result[i];
            value /= size;
            result[i] = (byte)Mth.m_14045_((int)(value += this.getRandomBoost(rand, size)), (int)0, (int)31);
        }
        return result;
    }

    private int getRandomBoost(RandomSource rand, int count) {
        switch (this.level) {
            case 0: {
                return rand.m_188503_(1 + 2 * count);
            }
            case 1: {
                return rand.m_188503_(1 + 2 * count) - count / 2;
            }
            case 3: {
                int value = rand.m_188503_(1 + 2 * count) - count;
                if (rand.m_188499_()) {
                    return 0;
                }
                return value;
            }
        }
        return rand.m_188503_(1 + 2 * count) - count;
    }
}

