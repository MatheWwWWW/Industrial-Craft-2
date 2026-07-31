/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.generator.tileentity;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergySource;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TileEntityConversionGenerator
extends TileEntityInventory
implements IHasGui,
IEnergySource {
    private static final NumberFormat FORMAT = new DecimalFormat("#.#");
    @GuiSynced
    private double lastProduction;
    @GuiSynced
    private double maxProduction;
    private double production;
    private boolean registeredToEnet;

    public TileEntityConversionGenerator(BlockEntityType<? extends TileEntityConversionGenerator> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)blockEntityType, blockPos, blockState);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.lastProduction = this.production;
        this.production = 0.0;
        this.setActive(this.maxProduction > 0.0);
    }

    @Override
    protected void onUnloaded() {
        super.onUnloaded();
        if (this.registeredToEnet && !this.f_58857_.f_46443_) {
            EnergyNet.instance.removeTile(this);
            this.registeredToEnet = false;
        }
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!this.registeredToEnet && !this.f_58857_.f_46443_) {
            EnergyNet.instance.addBlockEntityTile(this);
            this.registeredToEnet = true;
        }
    }

    public String getProduction() {
        return FORMAT.format(this.lastProduction);
    }

    public String getMaxProduction() {
        return FORMAT.format(this.maxProduction);
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return DynamicContainer.create(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return DynamicContainer.create(n, inventory, this);
    }

    protected abstract int getEnergyAvailable();

    protected abstract void drawEnergyAvailable(int var1);

    protected abstract double getMultiplier();

    @Override
    public double getOfferedEnergy() {
        this.maxProduction = (double)this.getEnergyAvailable() * this.getMultiplier();
        return this.maxProduction;
    }

    @Override
    public void drawEnergy(double d) {
        this.production += d;
        this.drawEnergyAvailable((int)Math.ceil(d / this.getMultiplier()));
    }

    @Override
    public int getSourceTier() {
        return Math.max(EnergyNet.instance.getTierFromPower(this.maxProduction), 2);
    }

    @Override
    public boolean emitsEnergyTo(IEnergyAcceptor iEnergyAcceptor, Direction direction) {
        return direction != this.getFacing();
    }
}

