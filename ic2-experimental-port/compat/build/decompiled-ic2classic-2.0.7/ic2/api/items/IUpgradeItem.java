/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.items;

import ic2.api.recipes.ingridients.queue.IStackOutput;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.tiles.IMachine;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IUpgradeItem {
    public UpgradeType getType(ItemStack var1);

    public EnumSet<Functions> getFunctions(ItemStack var1);

    public void onInstall(ItemStack var1, IMachine var2);

    public double getProcessingSpeedMultiplier(ItemStack var1, IMachine var2);

    public int getExtraProcessingSpeed(ItemStack var1, IMachine var2);

    public double getProcessingTimeMultiplier(ItemStack var1, IMachine var2);

    public int getExtraProcessingTime(ItemStack var1, IMachine var2);

    public double getEnergyDemandMultiplier(ItemStack var1, IMachine var2);

    public int getExtraEnergyDemand(ItemStack var1, IMachine var2);

    public double getEnergyStorageMultiplier(ItemStack var1, IMachine var2);

    public int getExtraEnergyStorage(ItemStack var1, IMachine var2);

    public int getExtraTier(ItemStack var1, IMachine var2);

    public float getSoundMultiplier(ItemStack var1, IMachine var2);

    public boolean useRedstoneInvertion(ItemStack var1, IMachine var2);

    public void onTick(ItemStack var1, IMachine var2);

    public void onMachineFinishedRecipePre(ItemStack var1, IMachine var2, IRecipeOutput var3, CompoundTag var4);

    public void onMachineFinishedRecipePost(ItemStack var1, IMachine var2, @Nullable IMachineRecipeList.RecipeEntry var3, List<IStackOutput> var4);

    public void onMachineProcessed(ItemStack var1, IMachine var2);

    public static enum Functions {
        TICK,
        RECIPE;

    }

    public static enum UpgradeType {
        RECIPE_MOD("ProcessingTime", "EnergyDemand", "ProcessingSpeed"),
        MACHINE_MOD("EnergyStorage", "ExtraTier"),
        REDSTONE_MOD("RedstoneInvertion"),
        PROCESSING_MOD(new String[0]),
        TRANSPORT_MOD(new String[0]),
        AUDIO_MOD(new String[0]),
        CUSTOM_MOD(new String[0]);

        String[] names;

        private UpgradeType(String ... names) {
            this.names = names;
        }
    }
}

