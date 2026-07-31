/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.base.features;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

public interface ISonarProvider {
    public BlockPos getStartPosition(ItemStack var1);

    public int getViewRadius(ItemStack var1);

    public boolean isDone(ItemStack var1);

    public int getSonarColor(ItemStack var1);

    public static ISonarProvider simple(int color, BlockPos startPos, int radius) {
        return new SimpleSonar(color, startPos, radius);
    }

    public static class SimpleSonar
    implements ISonarProvider {
        int color;
        boolean done = false;
        BlockPos startPos;
        int radius;

        public SimpleSonar(int color, BlockPos startPos, int radius) {
            this.color = color;
            this.startPos = startPos;
            this.radius = radius;
        }

        @Override
        public BlockPos getStartPosition(ItemStack stack) {
            return this.startPos;
        }

        @Override
        public int getViewRadius(ItemStack stack) {
            return this.radius;
        }

        @Override
        public boolean isDone(ItemStack stack) {
            if (this.done) {
                return true;
            }
            this.done = true;
            return false;
        }

        @Override
        public int getSonarColor(ItemStack stack) {
            return this.color;
        }
    }
}

