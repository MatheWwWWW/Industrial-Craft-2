/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.items;

import ic2.api.items.IDrinkableFluid;
import net.minecraft.world.item.ItemStack;

public interface IDrinkContainer {
    public int getCapacity();

    public IDrinkableFluid getContent(ItemStack var1);

    public boolean hasContent(ItemStack var1);

    public ItemStack fillWith(IDrinkableFluid var1, int var2);

    public ItemStack getEmptyContainer();

    public static class PotionQuality {
        public static final int RAW = 0;
        public static final int UNREFINED = 1;
        public static final int IMPURE = 2;
        public static final int REDUCED = 3;
        public static final int PURE = 4;
        public static final int CONCENTRATED = 5;
        public static final int BAD = 6;
    }

    public static class WhiskyQuality {
        public static final int RAW = 0;
        public static final int TEN_YEARS = 1;
        public static final int TWELVE_YEARS = 2;
        public static final int FIFTEEN_YEARS = 3;
        public static final int TWENTY_FIVE_YEARS = 4;
        public static final int FIFTY_YEARS = 5;
    }

    public static class BeerSolidRatio {
        public static final int WATERY = 0;
        public static final int CLEAR = 1;
        public static final int LITE = 2;
        public static final int NORMAL = 3;
        public static final int STRONG = 4;
        public static final int THICK = 5;
        public static final int STODGE = 6;
    }

    public static class BeerAlcoholLevel {
        public static final int SOUP = 0;
        public static final int ALCFREE = 1;
        public static final int WHITE = 2;
        public static final int NORMAL = 3;
        public static final int DARK = 4;
        public static final int FULL = 5;
        public static final int BLACK = 6;
    }

    public static class BeerQuality {
        public static final int BREW = 0;
        public static final int YOUNGSTER = 1;
        public static final int BEER = 2;
        public static final int ALE = 3;
        public static final int DRAGONBLOOD = 4;
        public static final int BAD = 5;
    }

    public static class BrewType {
        public static final int EMPTY = 0;
        public static final int BEER = 1;
        public static final int RUM = 2;
        public static final int WHISKY = 5;
        public static final int POTION = 10;
    }
}

