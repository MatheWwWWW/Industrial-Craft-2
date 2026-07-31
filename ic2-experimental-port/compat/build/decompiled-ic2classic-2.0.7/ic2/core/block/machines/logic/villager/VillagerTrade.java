/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.trading.MerchantOffer
 */
package ic2.core.block.machines.logic.villager;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;

public class VillagerTrade {
    UUID owner;
    int index;
    boolean outOfStock = false;
    boolean state = false;
    ItemStack input;
    ItemStack subInput;
    ItemStack output;
    int realCost;

    public VillagerTrade(UUID owner, int index, MerchantOffer offer) {
        this.owner = owner;
        this.index = index;
        this.syncItems(offer);
    }

    public VillagerTrade(UUID owner, CompoundTag tag) {
        this.owner = owner;
        this.index = tag.m_128451_("index");
        this.state = tag.m_128471_("state");
        this.outOfStock = tag.m_128471_("stock");
        this.input = NBTUtils.loadItem(tag, "main", ItemStack.f_41583_);
        this.subInput = NBTUtils.loadItem(tag, "sub", ItemStack.f_41583_);
        this.output = NBTUtils.loadItem(tag, "out", ItemStack.f_41583_);
        this.realCost = NBTUtils.getInt(tag, "realCost", this.input.m_41613_());
    }

    public VillagerTrade(UUID owner, IInputBuffer buffer) {
        this.owner = owner;
        this.index = buffer.readVarInt();
        this.state = buffer.readBoolean();
        this.outOfStock = buffer.readBoolean();
        this.input = buffer.readItemStack();
        this.subInput = buffer.readItemStack();
        this.output = buffer.readItemStack();
        this.realCost = buffer.readVarInt();
    }

    public boolean matches(VillagerTrade other) {
        return other.owner.equals(this.owner) && this.index == other.index;
    }

    public int getIndex() {
        return this.index;
    }

    public UUID getOwner() {
        return this.owner;
    }

    public void setState(boolean newState) {
        this.state = newState;
    }

    public boolean getState() {
        return this.state;
    }

    public ItemStack getMainItem() {
        return this.input;
    }

    public ItemStack getSubItem() {
        return this.subInput;
    }

    public ItemStack getOutputItem() {
        return this.output;
    }

    public int getRealPrice() {
        return this.realCost;
    }

    public boolean isDifferentPrice() {
        return this.input.m_41613_() != this.realCost;
    }

    public boolean isOutOfStock() {
        return this.outOfStock;
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.m_128405_("index", this.index);
        NBTUtils.putBoolean(tag, "state", this.state, false);
        NBTUtils.putBoolean(tag, "stock", this.outOfStock, false);
        NBTUtils.saveItem(tag, "main", this.input);
        NBTUtils.saveItem(tag, "sub", this.subInput);
        NBTUtils.saveItem(tag, "out", this.output);
        NBTUtils.putInt(tag, "realCost", this.realCost, this.input.m_41613_());
        return tag;
    }

    public void write(IOutputBuffer buffer) {
        buffer.writeVarInt(this.index);
        buffer.writeBoolean(this.state);
        buffer.writeBoolean(this.outOfStock);
        buffer.writeItemStack(this.input);
        buffer.writeItemStack(this.subInput);
        buffer.writeItemStack(this.output);
        buffer.writeVarInt(this.realCost);
    }

    public boolean updateIndex(int newIndex) {
        if (this.index == newIndex) {
            return false;
        }
        this.index = newIndex;
        return true;
    }

    public boolean matches(MerchantOffer offer) {
        return StackUtil.isStackEqual(this.input, offer.m_45352_()) && (this.subInput.m_41619_() || StackUtil.isStackEqual(this.subInput, offer.m_45364_())) && StackUtil.isStackEqual(this.output, offer.m_45368_());
    }

    public boolean syncItems(MerchantOffer offer) {
        boolean result = false;
        this.input = offer.m_45352_();
        this.subInput = offer.m_45364_();
        this.output = offer.m_45368_();
        if (this.realCost != offer.m_45358_().m_41613_()) {
            this.realCost = offer.m_45358_().m_41613_();
            result = true;
        }
        if (this.outOfStock != offer.m_45380_()) {
            this.outOfStock = offer.m_45380_();
            result = true;
        }
        return result;
    }
}

