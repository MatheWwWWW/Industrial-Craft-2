/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 */
package ic2.api.crops;

import ic2.api.crops.CropCard;
import ic2.api.info.ILocatable;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public interface ICropTile
extends ILocatable {
    public CropCard getCrop();

    public void setCrop(CropCard var1);

    public int getCurrentAge();

    public void setCurrentAge(int var1);

    public int getStatGrowth();

    public void setStatGrowth(int var1);

    public int getStatGain();

    public void setStatGain(int var1);

    public int getStatResistance();

    public void setStatResistance(int var1);

    public int getStorageNutrients();

    public void setStorageNutrients(int var1);

    public int getStorageWater();

    public void setStorageWater(int var1);

    public int getStorageWeedEX();

    public void setStorageWeedEX(int var1);

    public int getScanLevel();

    public void setScanLevel(int var1);

    public int getGrowthPoints();

    public void setGrowthPoints(int var1);

    public boolean isCrossingBase();

    public void setCrossingBase(boolean var1);

    public CompoundTag getCustomData();

    public int getTerrainHumidity();

    public int getTerrainNutrients();

    public int getTerrainAirQuality();

    @Deprecated
    public Level getWorld();

    @Deprecated
    public BlockPos getLocation();

    public int getLightLevel();

    public boolean pick();

    public boolean performManualHarvest();

    public List<ItemStack> performHarvest();

    public void reset();

    public void updateState();

    public boolean isBlockBelow(Block var1);

    public boolean isBlockBelow(TagKey<Block> var1);

    public ItemStack generateSeeds(CropCard var1, int var2, int var3, int var4, int var5);
}

