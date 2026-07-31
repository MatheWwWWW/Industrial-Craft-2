/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.tiles.tubes;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.tiles.tubes.IItemCache;
import ic2.api.util.DirectionList;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public final class TransportedItem
implements INetworkDataBuffer {
    static int GLOBAL_COUNTER = 0;
    public static final int MIN_SPEED = 1;
    public static final int MAX_SPEED = 100;
    int id;
    UUID logistics = null;
    ItemStack serverStack = ItemStack.f_41583_;
    Supplier<ItemStack> clientStack;
    byte color = 0;
    byte speed = (byte)5;
    byte progress;
    byte lastProgress;
    boolean centering;
    byte currentDirection;
    byte triedDirections;
    protected byte timeOut = 0;
    protected boolean invalid = false;

    private TransportedItem() {
    }

    public TransportedItem(ItemStack stack) {
        this.serverStack = stack.m_41777_();
        this.id = GLOBAL_COUNTER++;
        if (GLOBAL_COUNTER < 0) {
            GLOBAL_COUNTER = 0;
        }
    }

    public static TransportedItem readFromNetwork(IInputBuffer buffer) {
        TransportedItem item = new TransportedItem();
        item.read(buffer);
        return item;
    }

    public static TransportedItem read(CompoundTag nbt) {
        TransportedItem item = new TransportedItem();
        item.readFromNBT(nbt);
        return item.isInvalid() ? null : item;
    }

    public void readFromNBT(CompoundTag nbt) {
        this.id = nbt.m_128451_("id");
        this.serverStack = ItemStack.m_41712_((CompoundTag)nbt.m_128469_("item"));
        this.speed = nbt.m_128445_("speed");
        this.progress = nbt.m_128445_("progress");
        this.centering = nbt.m_128471_("centering");
        this.color = nbt.m_128445_("color");
        this.currentDirection = nbt.m_128445_("dir");
        this.triedDirections = nbt.m_128445_("tried");
        if (nbt.m_128441_("logistics")) {
            this.logistics = nbt.m_128342_("logistics");
        }
    }

    public CompoundTag writeToNBT(CompoundTag nbt) {
        nbt.m_128405_("id", this.id);
        nbt.m_128365_("item", (Tag)this.serverStack.m_41739_(new CompoundTag()));
        nbt.m_128344_("speed", this.speed);
        nbt.m_128344_("progress", this.progress);
        nbt.m_128379_("centering", this.centering);
        nbt.m_128344_("color", this.color);
        nbt.m_128344_("dir", this.currentDirection);
        nbt.m_128344_("tried", this.triedDirections);
        if (this.logistics != null) {
            nbt.m_128362_("logistics", this.logistics);
        }
        return nbt;
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.id = buffer.readVarInt();
        this.serverStack = ItemStack.f_41583_;
        this.clientStack = IItemCache.getCache().getItem(buffer.readVarInt());
        this.speed = buffer.readByte();
        this.progress = buffer.readByte();
        this.deserializeEssentials(buffer.readShort());
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeVarInt(this.id);
        buffer.writeVarInt(IItemCache.getCache().registerItem(this.serverStack));
        buffer.writeByte(this.speed);
        buffer.writeByte(this.progress);
        buffer.writeShort(this.serializeEssentials());
    }

    public short serializeEssentials() {
        return (short)((this.centering ? 1 : 0) << 14 | (this.color & 0x1F) << 9 | (this.currentDirection & 7) << 6 | this.triedDirections & 0x3F);
    }

    public void deserializeEssentials(short data) {
        this.triedDirections = (byte)(data & 0x3F);
        this.currentDirection = (byte)(data >> 6 & 7);
        this.color = (byte)(data >> 9 & 0x1F);
        this.centering = data >> 14 > 0;
    }

    public int getId() {
        return this.id;
    }

    public UUID getRequestId() {
        return this.logistics;
    }

    public boolean isCentering() {
        return this.centering;
    }

    public ItemStack getServerStack() {
        return this.serverStack;
    }

    public ItemStack getStack() {
        return this.clientStack != null ? this.clientStack.get() : this.serverStack;
    }

    public byte getProgress() {
        return this.progress;
    }

    public boolean isInvalid() {
        return this.invalid || this.clientStack == null && this.serverStack.m_41619_();
    }

    public DyeColor getColor() {
        return this.color == 0 ? null : DyeColor.m_41053_((int)(this.color - 1));
    }

    public TransportedItem setColor(DyeColor color) {
        this.color = (byte)(color == null ? 0 : color.m_41060_() + 1);
        return this;
    }

    public void setAttemptedDirections(DirectionList list) {
        this.triedDirections = (byte)list.getCode();
    }

    public TransportedItem setRequestId(UUID id) {
        this.logistics = id;
        return this;
    }

    public void invalidate() {
        this.invalid = true;
    }

    public boolean hasReachedCenter() {
        return this.centering && this.progress >= 0;
    }

    public boolean hasReachedEnd() {
        return !this.centering && this.progress >= 100;
    }

    public Direction getTransferDirection() {
        return Direction.m_122376_((int)this.currentDirection);
    }

    public Direction getTravelingDirection() {
        return this.isCentering() ? this.getTransferDirection().m_122424_() : this.getTransferDirection();
    }

    public DirectionList getAttemptedDirections() {
        return DirectionList.ofNumber(this.triedDirections);
    }

    public void clearDirections() {
        this.triedDirections = 0;
    }

    public void setCentering() {
        this.centering = true;
    }

    public void setInsertionDirection(Direction dir) {
        if (dir == null) {
            this.progress = 0;
            this.centering = false;
            return;
        }
        this.centering = true;
        this.progress = (byte)-100;
        this.currentDirection = (byte)dir.m_122411_();
        this.triedDirections = (byte)(this.triedDirections | 1 << dir.m_122411_());
    }

    public void setExportDirection(Direction dir) {
        this.centering = false;
        this.currentDirection = (byte)dir.m_122411_();
    }

    public void update() {
        this.progress = (byte)Math.min(this.centering ? 0 : 100, this.progress + this.speed);
        if (this.progress == this.lastProgress) {
            this.timeOut = (byte)(this.timeOut + 1);
            if (this.timeOut > 4) {
                this.invalid = true;
                return;
            }
        } else {
            this.timeOut = 0;
        }
        this.lastProgress = this.progress;
    }

    public TransportedItem setStartSpeed(int speed) {
        this.speed = (byte)Mth.m_14045_((int)speed, (int)1, (int)100);
        return this;
    }

    public boolean updateSpeed(int newSpeed, int change) {
        if (newSpeed == this.speed || change == 0) {
            return false;
        }
        this.speed = (byte)Mth.m_14045_((int)(newSpeed < this.speed ? this.speed - change : this.speed + change), (int)1, (int)100);
        return true;
    }
}

