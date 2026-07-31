/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.transport.cover;

import ic2.core.IC2;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.block.transport.cover.ICoverHolder;
import ic2.core.block.transport.cover.ICoverItem;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class Covers
extends TileEntityComponent {
    protected ItemStack[] covers = new ItemStack[6];

    public Covers(Ic2TileEntity ic2TileEntity) {
        super(ic2TileEntity);
    }

    public void addCover(Direction direction, ItemStack itemStack) {
        if (StackUtil.isEmpty(this.covers[direction.ordinal()])) {
            ItemStack itemStack2 = itemStack.m_41777_();
            CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack2);
            compoundTag.m_128344_("side", (byte)direction.ordinal());
            this.covers[direction.ordinal()] = itemStack2;
        }
    }

    public ItemStack removeCover(Direction direction) {
        ItemStack itemStack = this.covers[direction.ordinal()];
        itemStack.m_41751_(null);
        this.covers[direction.ordinal()] = null;
        return itemStack;
    }

    public boolean hasCover(Direction direction) {
        return !StackUtil.isEmpty(this.covers[direction.ordinal()]);
    }

    public ICoverItem getCoverItem(Direction direction) {
        ItemStack itemStack = this.covers[direction.ordinal()];
        if (StackUtil.isEmpty(itemStack)) {
            return null;
        }
        return (ICoverItem)itemStack.m_41720_();
    }

    @Override
    public void readFromNbt(CompoundTag compoundTag) {
        ListTag listTag = compoundTag.m_128437_("covers", 10);
        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag2 = listTag.m_128728_(i);
            int n = compoundTag2.m_128445_("facing") & 0xFF;
            if (n >= this.covers.length) {
                IC2.log.error(LogCategory.Block, "Can't load cover for %s, index %d is out of bounds.", Util.toString(this.parent), n);
                continue;
            }
            ItemStack itemStack = ItemStack.m_41712_((CompoundTag)compoundTag2);
            if (StackUtil.isEmpty(itemStack)) {
                IC2.log.warn(LogCategory.Block, "Can't load cover %s for %s, index %d, no matching item for %d:%d.", StackUtil.toStringSafe(itemStack), Util.toString(this.parent), n, compoundTag2.m_128448_("id"), compoundTag2.m_128448_("Damage"));
                continue;
            }
            if (!StackUtil.isEmpty(this.covers[n])) {
                IC2.log.error(LogCategory.Block, "Loading cover to non-empty cover for %s, index %d, replacing %s with %s.", Util.toString(this.parent), n, this.covers[n], itemStack);
            }
            this.covers[n] = itemStack;
        }
    }

    @Override
    public CompoundTag writeToNbt() {
        CompoundTag compoundTag = new CompoundTag();
        ListTag listTag = new ListTag();
        for (Direction direction : Util.ALL_DIRS) {
            ItemStack itemStack = this.covers[direction.ordinal()];
            if (StackUtil.isEmpty(itemStack)) continue;
            CompoundTag compoundTag2 = new CompoundTag();
            compoundTag2.m_128344_("facing", (byte)direction.ordinal());
            itemStack.m_41739_(compoundTag2);
            listTag.add((Object)compoundTag2);
        }
        compoundTag.m_128365_("covers", (Tag)listTag);
        return compoundTag;
    }

    @Override
    public boolean enableWorldTick() {
        return !this.parent.m_58904_().f_46443_;
    }

    @Override
    public void onWorldTick() {
        for (Direction direction : Util.ALL_DIRS) {
            if (StackUtil.isEmpty(this.covers[direction.ordinal()])) continue;
            ((ICoverItem)this.covers[direction.ordinal()].m_41720_()).onTick(this.covers[direction.ordinal()], (ICoverHolder)((Object)this.parent));
        }
    }
}

