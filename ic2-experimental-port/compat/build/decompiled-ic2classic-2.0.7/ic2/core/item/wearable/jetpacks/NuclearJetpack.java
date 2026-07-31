/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.wearable.jetpacks;

import ic2.api.items.armor.IArmorModule;
import ic2.core.IC2;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.inv.inventory.NuclearJetpackInventory;
import ic2.core.item.logic.NuclearJetpackLogic;
import ic2.core.item.logic.TickableItemLogic;
import ic2.core.item.wearable.base.IC2ElectricJetpackBase;
import ic2.core.item.wearable.base.IC2JetpackBase;
import ic2.core.item.wearable.base.IHasArmorInventory;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NuclearJetpack
extends IC2ElectricJetpackBase
implements IHasArmorInventory {
    public NuclearJetpack() {
        this("nuclear_jetpack");
    }

    protected NuclearJetpack(String name) {
        super(name, EquipmentSlot.CHEST, null);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack armor, ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        super.addToolTip(armor, stack, player, type, helper);
        CompoundTag nbt = this.getNBTData(armor, false);
        IC2.LOGGER.info("Test: " + nbt);
        helper.addDataTooltip("tooltip.item.ic2.jetpack.heat.reactor", nbt.m_128451_("heat"), nbt.m_128451_("max"));
        helper.addDataTooltip("tooltip.item.ic2.jetpack.heat.component", nbt.m_128451_("component_heat") + "%");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        super.addToolTip(stack, player, type, helper);
        CompoundTag nbt = this.getNBTData(stack, false);
        helper.addDataTooltip("tooltip.item.ic2.jetpack.heat.reactor", nbt.m_128451_("heat"), nbt.m_128451_("max"));
        helper.addDataTooltip("tooltip.item.ic2.jetpack.heat.component", nbt.m_128451_("component_heat") + "%");
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return 30000;
    }

    @Override
    public int getTier(ItemStack stack) {
        return 1;
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean canDoRocketMode(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canDoAdvHoverMode(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isElectricJetpack(ItemStack stack) {
        return true;
    }

    @Override
    public float getPower(ItemStack stack) {
        return 0.95f;
    }

    @Override
    public float getThruster(ItemStack stack, IC2JetpackBase.HoverMode mode) {
        switch (mode) {
            case ADV: {
                return 1.5f;
            }
            case BASIC: {
                return 1.0f;
            }
            case NONE: {
                return 0.5f;
            }
        }
        return 0.5f;
    }

    @Override
    public float getDropPercentage(ItemStack stack) {
        return 0.05f;
    }

    @Override
    public int getMaxHeight(ItemStack stack, int worldHeight) {
        return (int)((float)worldHeight / 1.1f);
    }

    @Override
    public int getMaxRocketCharge(ItemStack stack) {
        return 25000;
    }

    @Override
    public int getFuelCost(ItemStack stack, IC2JetpackBase.HoverMode mode) {
        return mode == IC2JetpackBase.HoverMode.BASIC ? 9 : 12;
    }

    @Override
    public String getTextureFolder() {
        return "armor/jetpack";
    }

    @Override
    public String getTextureName() {
        return "nuclear";
    }

    @Override
    public String getArmorTexture() {
        return "ic2:textures/models/armor/jetpack_nuclear";
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        super.onArmorTick(stack, world, player);
        TickableItemLogic logic = this.getLogic(stack, player);
        if (logic == null) {
            return;
        }
        logic.onTick(stack);
        logic.save(stack);
    }

    public TickableItemLogic getLogic(ItemStack stack, Player player) {
        return new NuclearJetpackLogic(stack, player, this.f_40377_).load(stack);
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (IC2.KEYBOARD.isSideInventoryKeyDown(playerIn)) {
            ItemStack stack = playerIn.m_21120_(handIn);
            if (IC2.PLATFORM.isSimulating()) {
                IC2.PLATFORM.launchGui(playerIn, handIn, null, this.getInventory(playerIn, handIn, stack));
            }
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(worldIn, playerIn, handIn);
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return new NuclearJetpackInventory(player, this, stack, null).load(stack);
    }

    @Override
    public IPortableInventory getInventory(Player player, ItemStack stack, Slot slot) {
        return new NuclearJetpackInventory(player, this, stack, slot).load(stack);
    }

    @Override
    public boolean hasArmorInventory(ItemStack armor, ItemStack stack) {
        return true;
    }

    @Override
    public void onInstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        super.onInstall(stack, armor, holder);
        holder.addMulModifier(armor, IArmorModule.ArmorMod.ENERGY_TRANSFER, -1.0f);
        holder.addAddModifier(armor, IArmorModule.ArmorMod.ENERGY_PROVIDER, -1000);
    }

    @Override
    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        super.onUninstall(stack, armor, holder);
        holder.removeMulModifier(armor, IArmorModule.ArmorMod.ENERGY_TRANSFER, -1.0f);
        holder.removeAddModifier(armor, IArmorModule.ArmorMod.ENERGY_PROVIDER, -1000);
    }
}

