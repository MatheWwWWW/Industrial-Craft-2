/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.reactor.tileentity;

import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableItemStack;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import ic2.core.block.reactor.tileentity.TileEntityReactorChamberElectric;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.item.reactor.ItemReactorCondensator;
import ic2.core.network.GrowingBuffer;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TileEntityAbstractRCI
extends TileEntityElectricMachine
implements IUpgradableBlock,
IHasGui {
    private TileEntityNuclearReactorElectric reactor;
    private final ItemStack target;
    private final double energyPerOperation = 1000.0;
    public final InvSlotConsumableItemStack inputSlot;
    public final InvSlotUpgrade upgradeSlot;

    protected TileEntityAbstractRCI(BlockEntityType<? extends TileEntityAbstractRCI> blockEntityType, BlockPos blockPos, BlockState blockState, ItemStack itemStack, ItemStack itemStack2) {
        super(blockEntityType, blockPos, blockState, 48000, 2);
        this.target = itemStack;
        this.inputSlot = new InvSlotConsumableItemStack(this, "input", InvSlot.Access.I, 9, InvSlot.InvSide.ANY, itemStack2);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!this.m_58904_().f_46443_) {
            this.updateEnergyFacings();
        }
        this.updateReactor();
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        if (!this.inputSlot.isEmpty() && this.energy.getEnergy() >= 1000.0 && this.reactor != null) {
            this.setActive(true);
        } else {
            this.setActive(false);
        }
        if (this.getActive()) {
            for (ItemStack itemStack : this.reactor.reactorSlot) {
                ItemReactorCondensator itemReactorCondensator;
                if (itemStack == null || !StackUtil.checkItemEquality(itemStack, this.target) || !((itemReactorCondensator = (ItemReactorCondensator)itemStack.m_41720_()).getUseFraction(itemStack) > 0.85) || this.inputSlot.consume(1) == null || !this.energy.useEnergy(1000.0)) continue;
                itemReactorCondensator.setUse(itemStack, 0);
                bl = true;
            }
        }
        if (bl |= this.upgradeSlot.tickNoMark()) {
            super.m_6596_();
        }
    }

    @Override
    protected void onNeighborChange(Block block, BlockPos blockPos) {
        super.onNeighborChange(block, blockPos);
        this.updateEnergyFacings();
        this.updateReactor();
    }

    @Override
    protected void setFacing(Level level, Direction direction) {
        super.setFacing(level, direction);
        this.updateEnergyFacings();
        this.updateReactor();
    }

    public void updateEnergyFacings() {
        Level level = this.m_58904_();
        HashSet<Direction> hashSet = new HashSet<Direction>();
        for (Direction direction : Util.ALL_DIRS) {
            BlockEntity blockEntity = level.m_7702_(this.f_58858_.m_121945_(direction));
            if (blockEntity instanceof TileEntityNuclearReactorElectric || blockEntity instanceof TileEntityReactorChamberElectric) continue;
            hashSet.add(direction);
        }
        this.energy.setDirections(hashSet, Collections.emptySet());
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.ItemConsuming);
    }

    @Override
    public double getEnergy() {
        return 0.0;
    }

    @Override
    public boolean useEnergy(double d) {
        return false;
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return DynamicContainer.create(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return DynamicContainer.create(n, inventory, this);
    }

    private void updateReactor() {
        Level level = this.m_58904_();
        if (!Util.isAreaLoaded((LevelReader)level, this.f_58858_, 2)) {
            this.reactor = null;
            return;
        }
        BlockEntity blockEntity = level.m_7702_(this.f_58858_.m_121945_(this.getFacing().m_122424_()));
        if (blockEntity instanceof TileEntityNuclearReactorElectric) {
            this.reactor = (TileEntityNuclearReactorElectric)blockEntity;
            return;
        }
        if (blockEntity instanceof TileEntityReactorChamberElectric) {
            this.reactor = ((TileEntityReactorChamberElectric)blockEntity).getReactorInstance();
            return;
        }
        this.reactor = null;
    }
}

