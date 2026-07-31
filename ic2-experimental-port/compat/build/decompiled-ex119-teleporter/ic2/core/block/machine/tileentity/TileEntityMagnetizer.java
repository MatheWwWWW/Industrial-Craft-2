/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Redstone;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerMagnetizer;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.network.GrowingBuffer;
import ic2.core.ref.Ic2BlockEntities;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityMagnetizer
extends TileEntityElectricMachine
implements IHasGui,
IUpgradableBlock {
    public InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
    public static final int defaultMaxEnergy = 100;
    public static final int defaultTier = 1;
    private static final double boostEnergy = 2.0;
    protected final Redstone redstone = this.addComponent(new Redstone(this));

    public TileEntityMagnetizer(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.MAGNETIZER, blockPos, blockState, 100, 1);
    }

    @Override
    public void m_6596_() {
        super.m_6596_();
        Level level = this.m_58904_();
        if (level != null && !level.f_46443_) {
            this.setOverclockRates();
        }
    }

    public void setOverclockRates() {
        this.upgradeSlot.onChanged();
        int n = this.upgradeSlot.getTier(1);
        this.energy.setSinkTier(n);
        this.dischargeSlot.setTier(n);
        this.energy.setCapacity(this.upgradeSlot.getEnergyStorage(100, 0, 0));
    }

    private int distance() {
        return 20 + this.upgradeSlot.augmentation;
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerMagnetizer(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerMagnetizer(n, inventory, this);
    }

    @Override
    public double getEnergy() {
        return this.energy.getEnergy();
    }

    @Override
    public boolean useEnergy(double d) {
        return this.energy.useEnergy(d);
    }

    public boolean canBoost() {
        return this.energy.getEnergy() >= 2.0;
    }

    public void boost(double d) {
        this.energy.useEnergy(2.0 * d);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Augmentable, UpgradableProperty.RedstoneSensitive, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage);
    }
}

