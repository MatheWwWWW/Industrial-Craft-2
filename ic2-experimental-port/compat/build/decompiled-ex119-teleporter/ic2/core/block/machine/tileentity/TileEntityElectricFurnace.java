/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.block.invslot.InvSlotProcessableSmelting;
import ic2.core.block.machine.tileentity.TileEntityIronFurnace;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2SoundEvents;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityElectricFurnace
extends TileEntityStandardMachine<ItemStack, ItemStack, ItemStack>
implements INetworkClientTileEntityEventListener {
    protected double xp = 0.0;

    public TileEntityElectricFurnace(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.ELECTRIC_FURNACE, blockPos, blockState, 3, 100, 1);
        this.inputSlot = new InvSlotProcessableSmelting(this, "input", 1);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.xp = compoundTag.m_128459_("xp");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128347_("xp", this.xp);
    }

    @Override
    protected Collection<ItemStack> getOutput(ItemStack itemStack) {
        return Collections.singletonList(itemStack);
    }

    @Override
    public void operateOnce(MachineRecipeResult<ItemStack, ItemStack, ItemStack> machineRecipeResult, Collection<ItemStack> collection) {
        super.operateOnce(machineRecipeResult, collection);
        this.xp += (double)machineRecipeResult.getRecipe().getMetaData().m_128457_("experience");
    }

    @Override
    public void onNetworkEvent(Player player, int n) {
        if (n == 0) {
            assert (!this.m_58904_().f_46443_);
            this.xp = TileEntityIronFurnace.spawnXP(player, this.xp);
        }
    }

    @Override
    public SoundEvent getStartSoundEvent() {
        return Ic2SoundEvents.MACHINE_FURNACE_ELECTRIC_START;
    }

    @Override
    public SoundEvent getLoopingSoundEvent() {
        return Ic2SoundEvents.MACHINE_FURNACE_ELECTRIC_LOOP;
    }

    @Override
    public SoundEvent getInterruptSoundEvent() {
        return Ic2SoundEvents.MACHINE_INTERRUPT1;
    }

    @Override
    public SoundEvent getStopSoundEvent() {
        return Ic2SoundEvents.MACHINE_FURNACE_ELECTRIC_STOP;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    }
}

