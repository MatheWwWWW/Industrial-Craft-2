/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.machines.logic.planner;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.reactor.IReactorPlannerComponent;
import ic2.core.block.machines.logic.planner.PlannerRegistry;
import ic2.core.block.machines.tiles.mv.ReactorPlannerTileEntity;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class PlannerInventory
extends SimpleInventory
implements INetworkDataBuffer {
    ReactorPlannerTileEntity planner;
    ItemStack[] currentSet;
    byte currentIndex = (byte)-1;
    int offset = 0;

    public PlannerInventory(ReactorPlannerTileEntity planner) {
        super(18);
        this.planner = planner;
    }

    public void init() {
        this.currentSet = PlannerRegistry.getByType(this.currentIndex == -1 ? null : IReactorPlannerComponent.ComponentType.byID(this.currentIndex), this.planner.isSteamReactor());
        this.offset = 0;
        this.update();
    }

    public void update() {
        int start = this.offset * 3;
        for (int i = 0; i < 18; ++i) {
            int slot = start + i;
            if (slot >= this.currentSet.length) {
                this.setStackInSlot(i, ItemStack.f_41583_);
                continue;
            }
            ItemStack stack = this.currentSet[slot];
            if (stack == null) {
                this.setStackInSlot(i, ItemStack.f_41583_);
                continue;
            }
            this.setStackInSlot(i, ((IReactorPlannerComponent)stack.m_41720_()).applyStackSize(stack.m_41777_(), this.planner.stackSize));
        }
    }

    public void setFilter(IReactorPlannerComponent.ComponentType type) {
        this.currentIndex = (byte)(type == null ? -1 : type.getIndex());
        this.init();
    }

    public void setPrevFilter() {
        this.currentIndex = (byte)(this.currentIndex == -1 ? IReactorPlannerComponent.ComponentType.size() - 1 : this.currentIndex - 1);
        this.init();
    }

    public void setNextFilter() {
        this.currentIndex = (byte)(this.currentIndex + 1 >= IReactorPlannerComponent.ComponentType.size() ? -1 : this.currentIndex + 1);
        this.init();
    }

    public int findItem(ItemStack stack) {
        if (stack.m_41619_()) {
            return -1;
        }
        this.setFilter(null);
        int index = -1;
        int m = this.currentSet.length;
        for (int i = 0; i < m; ++i) {
            if (!StackUtil.isStackEqual(stack, this.currentSet[i])) continue;
            index = i;
            break;
        }
        if (index != -1) {
            this.setScroll(index / 3);
        }
        return index;
    }

    public void scroll(int scrollOffset) {
        this.offset = Mth.m_14045_((int)(this.offset + scrollOffset), (int)0, (int)Math.max(0, (this.currentSet.length - 15) / 3));
        this.update();
    }

    public void setScroll(int scroll) {
        this.offset = Mth.m_14045_((int)scroll, (int)0, (int)Math.max(0, (this.currentSet.length - 15) / 3));
        this.update();
    }

    public int getOffset() {
        return this.offset;
    }

    public byte getCurrentIndex() {
        return this.currentIndex;
    }

    public int getTotalItems() {
        return this.currentSet.length;
    }

    public ItemStack getCurrentItem(int index) {
        return PlannerRegistry.setStackSize(this.currentSet[index].m_41777_(), this.planner.stackSize);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.currentIndex = nbt.m_128445_("index");
        this.offset = nbt.m_128451_("offset");
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        super.save(nbt);
        nbt.m_128344_("index", this.currentIndex);
        nbt.m_128405_("offset", this.offset);
        return nbt;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeByte(this.currentIndex);
        buffer.writeVarInt(this.offset);
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.currentIndex = buffer.readByte();
        this.offset = buffer.readVarInt();
        this.currentSet = PlannerRegistry.getByType(this.currentIndex == -1 ? null : IReactorPlannerComponent.ComponentType.byID(this.currentIndex), this.planner.isSteamReactor());
    }
}

