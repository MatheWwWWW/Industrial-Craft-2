/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.machine.container.ContainerFluidDistributor;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.LiquidUtil;
import ic2.core.util.Util;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityFluidDistributor
extends TileEntityInventory
implements IHasGui,
INetworkClientTileEntityEventListener {
    public final InvSlotConsumableLiquidByTank inputSlot;
    public final InvSlotOutput OutputSlot;
    @GuiSynced
    public final Fluids.InternalFluidTank fluidTank;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntityFluidDistributor(BlockPos blockPos, BlockState blockState) {
        this((BlockEntityType<? extends TileEntityFluidDistributor>)Ic2BlockEntities.FLUID_DISTRIBUTOR, blockPos, blockState);
    }

    protected TileEntityFluidDistributor(BlockEntityType<? extends TileEntityFluidDistributor> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)blockEntityType, blockPos, blockState);
        this.fluidTank = this.fluids.addTank("fluidTank", 1000);
        this.inputSlot = new InvSlotConsumableLiquidByTank(this, "inputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.BOTTOM, InvSlotConsumableLiquid.OpType.Fill, this.fluidTank);
        this.OutputSlot = new InvSlotOutput(this, "OutputSlot", 1);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.updateConnectivity();
    }

    @Override
    public void setActive(boolean bl) {
        super.setActive(bl);
        this.updateConnectivity();
    }

    @Override
    protected void setFacing(Level level, Direction direction) {
        super.setFacing(level, direction);
        this.updateConnectivity();
    }

    protected void updateConnectivity() {
        EnumSet<Direction> enumSet = EnumSet.of(this.getFacing());
        if (this.getActive()) {
            enumSet = EnumSet.complementOf(enumSet);
        }
        this.fluids.changeConnectivity(this.fluidTank, enumSet, Collections.emptySet());
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.inputSlot.processFromTank(this.fluidTank, this.OutputSlot);
        if (this.fluidTank.getFluidAmount() > 0) {
            this.moveFluid();
        }
    }

    protected void moveFluid() {
        block6: {
            int n;
            int n2;
            Object object;
            BlockEntity blockEntity;
            Level level;
            block5: {
                int n3;
                Direction direction;
                level = this.m_58904_();
                if (!this.getActive()) break block5;
                BlockEntity blockEntity2 = level.m_7702_(this.f_58858_.m_121945_(this.getFacing()));
                if (!LiquidUtil.isFluidTile(blockEntity2, direction = this.getFacing().m_122424_()) || (n3 = LiquidUtil.fillTile(blockEntity2, direction, this.fluidTank.getFluidStack(), false)) <= 0) break block6;
                this.fluidTank.drainMbUnchecked(n3, false);
                break block6;
            }
            EnumMap<Direction, BlockEntity> enumMap = new EnumMap<Direction, BlockEntity>(Direction.class);
            int n4 = 0;
            for (Direction direction : Util.ALL_DIRS) {
                if (direction == this.getFacing() || !LiquidUtil.isFluidTile(blockEntity = level.m_7702_(this.f_58858_.m_121945_(direction)), (Direction)(object = direction.m_122424_())) || (n2 = LiquidUtil.fillTile(blockEntity, (Direction)object, this.fluidTank.getFluidStack(), true)) <= 0) continue;
                enumMap.put(direction, blockEntity);
                n4 += n2;
            }
            block1: while (!enumMap.isEmpty() && (n = Math.min(n4, this.fluidTank.getFluidAmount())) > 0) {
                Direction direction;
                if ((n /= enumMap.size()) > 0) {
                    Iterator iterator = enumMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry entry = iterator.next();
                        direction = (BlockEntity)entry.getValue();
                        blockEntity = ((Direction)entry.getKey()).m_122424_();
                        object = this.fluidTank.getFluidStack();
                        if (object == null || (object = object.copy()).isEmpty()) continue block1;
                        object.setAmountMb(Math.min(n, object.getAmountMb()));
                        n2 = LiquidUtil.fillTile((BlockEntity)direction, (Direction)blockEntity, (Ic2FluidStack)object, false);
                        this.fluidTank.drainMbUnchecked(n2, false);
                        n4 -= n2;
                        if (n2 >= object.getAmountMb()) continue;
                        iterator.remove();
                    }
                    continue;
                }
                for (Map.Entry entry : enumMap.entrySet()) {
                    direction = (BlockEntity)entry.getValue();
                    blockEntity = ((Direction)entry.getKey()).m_122424_();
                    object = this.fluidTank.getFluidStack();
                    if (object == null) break block1;
                    object = object.copy();
                    object.setAmountMb(Math.min(n4, object.getAmountMb()));
                    if (object.isEmpty()) break block1;
                    n2 = LiquidUtil.fillTile((BlockEntity)direction, (Direction)blockEntity, (Ic2FluidStack)object, false);
                    this.fluidTank.drainMbUnchecked(n2, false);
                    n4 -= n2;
                }
                break;
            }
        }
    }

    @Override
    public void onNetworkEvent(Player player, int n) {
        this.setActive(!this.getActive());
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerFluidDistributor(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerFluidDistributor(n, inventory, this);
    }
}

