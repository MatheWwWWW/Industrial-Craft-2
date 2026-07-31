/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.api.crops;

import ic2.api.crops.CropCard;
import ic2.api.info.ILocatable;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ICropTile
extends ILocatable {
    public CropCard getCrop();

    public void setCrop(CropCard var1);

    public int getCurrentSize();

    public void setCurrentSize(int var1);

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

    public NBTTagCompound getCustomData();

    public int getTerrainHumidity();

    public int getTerrainNutrients();

    public int getTerrainAirQuality();

    @Deprecated
    public World getWorld();

    @Deprecated
    public BlockPos getLocation();

    public int getLightLevel();

    public boolean pick();

    public boolean performManualHarvest();

    public List<ItemStack> performHarvest();

    public void reset();

    public void updateState();

    public boolean isBlockBelow(Block var1);

    public boolean isBlockBelow(String var1);

    public ItemStack generateSeeds(CropCard var1, int var2, int var3, int var4, int var5);
}

