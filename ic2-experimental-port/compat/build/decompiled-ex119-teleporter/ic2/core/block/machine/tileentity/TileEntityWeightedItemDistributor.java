/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.ClientModifiable;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.machine.container.ContainerWeightedItemDistributor;
import ic2.core.block.machine.tileentity.IWeightedDistributor;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.item.EnvItemHandler;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityWeightedItemDistributor
extends TileEntityInventory
implements IHasGui,
IWeightedDistributor {
    @ClientModifiable
    protected List<Direction> priority = new ArrayList<Direction>(5);
    public final InvSlot buffer = new InvSlot(this, "buffer", InvSlot.Access.I, 9);

    public TileEntityWeightedItemDistributor(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.WEIGHTED_ITEM_DISTRIBUTOR, blockPos, blockState);
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
    protected void onLoaded() {
        super.onLoaded();
        this.updateConnectivity();
    }

    @Override
    protected void setFacing(Level level, Direction direction) {
        super.setFacing(level, direction);
        this.updateConnectivity();
    }

    protected void updateConnectivity() {
        if (!this.m_58904_().f_46443_ && !this.priority.isEmpty() && this.priority.remove(this.getFacing())) {
            this.updatePriority(true);
        }
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (!this.priority.isEmpty() && !this.buffer.isEmpty()) {
            Level level = this.m_58904_();
            boolean bl = false;
            for (Direction direction : this.priority) {
                EnvItemHandler.AdjacentInventory adjacentInventory = StackUtil.ENV.getAdjacentInventory(this, direction);
                if (adjacentInventory == null) continue;
                boolean bl2 = true;
                for (int i = 0; i < this.buffer.size(); ++i) {
                    ItemStack itemStack;
                    ItemStack itemStack2;
                    int n;
                    if (this.buffer.isEmpty(i) || (n = StackUtil.ENV.deposit(adjacentInventory, itemStack2 = StackUtil.copy(itemStack = this.buffer.get(i)), true)) <= 0) continue;
                    n = StackUtil.ENV.deposit(adjacentInventory, itemStack2, false);
                    itemStack = StackUtil.decSize(itemStack, n);
                    this.buffer.put(i, itemStack);
                    bl = true;
                    bl2 &= StackUtil.isEmpty(itemStack);
                }
                if (!bl || !bl2) continue;
                break;
            }
            if (bl) {
                this.m_6596_();
            }
        }
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerWeightedItemDistributor(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerWeightedItemDistributor(n, inventory, this);
    }

    @Override
    public List<Direction> getPriority() {
        return this.priority;
    }

    @Override
    public void updatePriority(boolean bl) {
        IC2.network.get(bl).updateTileEntityField(this, "priority");
    }
}

