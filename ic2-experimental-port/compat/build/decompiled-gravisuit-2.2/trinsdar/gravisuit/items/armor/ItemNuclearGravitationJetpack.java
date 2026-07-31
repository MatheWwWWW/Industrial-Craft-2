/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.api.items.armor.IArmorModule$ArmorMod
 *  ic2.api.items.armor.IArmorModule$IArmorModuleHolder
 *  ic2.core.item.logic.TickableItemLogic
 *  ic2.core.item.wearable.base.IC2JetpackBase$HoverMode
 *  ic2.core.item.wearable.jetpacks.NuclearJetpack
 *  ic2.core.utils.tooltips.ToolTipHelper
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package trinsdar.gravisuit.items.armor;

import ic2.api.items.armor.IArmorModule;
import ic2.core.item.logic.TickableItemLogic;
import ic2.core.item.wearable.base.IC2JetpackBase;
import ic2.core.item.wearable.jetpacks.NuclearJetpack;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import trinsdar.gravisuit.items.armor.IGravitationJetpack;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.Registry;

public class ItemNuclearGravitationJetpack
extends NuclearJetpack
implements IGravitationJetpack {
    public ItemNuclearGravitationJetpack() {
        super("nuclear_gravitation_jetpack");
        Registry.REGISTRY.put(new ResourceLocation("gravisuit", "nuclear_gravitation_jetpack"), (Item)this);
    }

    public boolean canProvideEnergy(ItemStack itemStack) {
        return GravisuitConfig.MISC.GRAVITATION_N_JETPACK_PROVIDE_ENERGY;
    }

    public int getCapacity(ItemStack itemStack) {
        return GravisuitConfig.POWER_VALUES.NUCLEAR_GRAVITATION_JETPACK_STORAGE;
    }

    public int getTier(ItemStack itemStack) {
        return 3;
    }

    public int getTransferLimit(ItemStack itemStack) {
        return 0;
    }

    public float getPower(ItemStack itemStack) {
        return 1.4f;
    }

    public float getThruster(ItemStack itemStack, IC2JetpackBase.HoverMode hoverMode) {
        return switch (hoverMode) {
            default -> throw new IncompatibleClassChangeError();
            case IC2JetpackBase.HoverMode.ADV -> 1.8f;
            case IC2JetpackBase.HoverMode.BASIC -> 1.2f;
            case IC2JetpackBase.HoverMode.NONE -> 0.6f;
        };
    }

    public float getDropPercentage(ItemStack itemStack) {
        return 0.0f;
    }

    public int getMaxHeight(ItemStack itemStack, int worldHeight) {
        return worldHeight;
    }

    public int getMaxRocketCharge(ItemStack itemStack) {
        return 30000;
    }

    public int getFuelCost(ItemStack itemStack, IC2JetpackBase.HoverMode hoverMode) {
        return switch (hoverMode) {
            default -> throw new IncompatibleClassChangeError();
            case IC2JetpackBase.HoverMode.NONE -> 25;
            case IC2JetpackBase.HoverMode.BASIC -> 30;
            case IC2JetpackBase.HoverMode.ADV -> 40;
        };
    }

    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (this.armorTick(stack, world, player)) {
            super.onArmorTick(stack, world, player);
        } else {
            TickableItemLogic logic = this.getLogic(stack, player);
            if (logic != null) {
                logic.onTick(stack);
                logic.save(stack);
            }
        }
    }

    public void onInstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        super.onInstall(stack, armor, holder);
        if (this.canProvideEnergy(stack)) {
            holder.addAddModifier(armor, IArmorModule.ArmorMod.ENERGY_PROVIDER, 1001);
        }
    }

    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        super.onUninstall(stack, armor, holder);
        if (this.canProvideEnergy(stack)) {
            holder.removeAddModifier(armor, IArmorModule.ArmorMod.ENERGY_PROVIDER, 1001);
        }
    }

    public String getTextureFolder() {
        return "jetpack";
    }

    public String getTextureName() {
        return "nuclear_gravitation_jetpack";
    }

    public String getArmorTexture() {
        return "gravisuit:textures/models/nuclear_gravitation_jetpack";
    }

    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        super.addToolTip(stack, player, type, helper);
        this.toolTip(stack, player, type, helper);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack armor, ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        super.addToolTip(armor, stack, player, type, helper);
        this.toolTip(armor, player, type, helper);
    }

    @Override
    public CompoundTag nbtData(ItemStack stack, boolean create) {
        return this.getNBTData(stack, create);
    }
}

