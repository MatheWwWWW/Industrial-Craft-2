/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.ClientModifiable;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.block.machine.container.ContainerWeightedFluidDistributor;
import ic2.core.block.machine.tileentity.IWeightedDistributor;
import ic2.core.block.machine.tileentity.TileEntityFluidDistributor;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.LiquidUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityWeightedFluidDistributor
extends TileEntityFluidDistributor
implements IWeightedDistributor {
    @ClientModifiable
    protected List<Direction> priority = new ArrayList<Direction>(5);

    public TileEntityWeightedFluidDistributor(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityFluidDistributor>)Ic2BlockEntities.WEIGHTED_FLUID_DISTRIBUTOR, blockPos, blockState);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        int[] nArray = compoundTag.m_128465_("priority");
        if (nArray.length > 0) {
            for (int n : nArray) {
                this.priority.add(Direction.m_122376_((int)n));
            }
        }
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        if (!this.priority.isEmpty()) {
            int[] nArray = new int[this.priority.size()];
            for (int i = 0; i < nArray.length; ++i) {
                nArray[i] = this.priority.get(i).m_122411_();
            }
            compoundTag.m_128385_("priority", nArray);
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("priority");
        return list;
    }

    @Override
    protected void updateConnectivity() {
        if (!this.m_58904_().f_46443_ && !this.priority.isEmpty() && this.priority.remove(this.getFacing())) {
            this.updatePriority(true);
        }
        this.fluids.changeConnectivity(this.fluidTank, Collections.singleton(this.getFacing()), Collections.emptySet());
    }

    @Override
    protected void moveFluid() {
        if (!this.priority.isEmpty()) {
            int n = this.fluidTank.getFluidAmount();
            for (Direction direction : this.priority) {
                int n2;
                Direction direction2;
                assert (direction != this.getFacing());
                BlockEntity blockEntity = this.f_58857_.m_7702_(this.f_58858_.m_121945_(direction));
                if (!LiquidUtil.isFluidTile(blockEntity, direction2 = direction.m_122424_()) || (n2 = LiquidUtil.fillTile(blockEntity, direction2, this.fluidTank.getFluidStack(), false)) <= 0) continue;
                this.fluidTank.drainMbUnchecked(n2, false);
                if ((n -= n2) > 0) continue;
                break;
            }
        }
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerWeightedFluidDistributor(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerWeightedFluidDistributor(n, inventory, this);
    }

    @Override
    public List<Direction> getPriority() {
        return this.priority;
    }

    @Override
    public void updatePriority(boolean bl) {
        IC2.network.get(bl).updateTileEntityField(this, "priority");
    }

    @Override
    public void onNetworkEvent(Player player, int n) {
        int n2 = n / 10;
        Direction direction = Direction.m_122376_((int)(n % 10 & 6));
        assert (n2 >= 0 && n2 <= this.priority.size()) : "Position was " + n2;
        assert (direction != this.getFacing());
        if (n2 == this.priority.size()) {
            this.priority.add(direction);
        } else {
            this.priority.set(n2, direction);
        }
    }
}

