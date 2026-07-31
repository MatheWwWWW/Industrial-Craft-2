/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SimpleContainer
implements Container,
StackedContentsCompatible {
    private final int f_19146_;
    private final NonNullList<ItemStack> f_19147_;
    @Nullable
    private List<ContainerListener> f_19148_;

    public SimpleContainer(int p_19150_) {
        this.f_19146_ = p_19150_;
        this.f_19147_ = NonNullList.m_122780_(p_19150_, ItemStack.f_41583_);
    }

    public SimpleContainer(ItemStack ... p_19152_) {
        this.f_19146_ = p_19152_.length;
        this.f_19147_ = NonNullList.m_122783_(ItemStack.f_41583_, p_19152_);
    }

    public void m_19164_(ContainerListener p_19165_) {
        if (this.f_19148_ == null) {
            this.f_19148_ = Lists.newArrayList();
        }
        this.f_19148_.add(p_19165_);
    }

    public void m_19181_(ContainerListener p_19182_) {
        if (this.f_19148_ != null) {
            this.f_19148_.remove(p_19182_);
        }
    }

    @Override
    public ItemStack m_8020_(int p_19157_) {
        if (p_19157_ < 0 || p_19157_ >= this.f_19147_.size()) {
            return ItemStack.f_41583_;
        }
        return this.f_19147_.get(p_19157_);
    }

    public List<ItemStack> m_19195_() {
        List<ItemStack> $$0 = this.f_19147_.stream().filter(p_19197_ -> !p_19197_.m_41619_()).collect(Collectors.toList());
        this.m_6211_();
        return $$0;
    }

    @Override
    public ItemStack m_7407_(int p_19159_, int p_19160_) {
        ItemStack $$2 = ContainerHelper.m_18969_(this.f_19147_, p_19159_, p_19160_);
        if (!$$2.m_41619_()) {
            this.m_6596_();
        }
        return $$2;
    }

    public ItemStack m_19170_(Item p_19171_, int p_19172_) {
        ItemStack $$2 = new ItemStack(p_19171_, 0);
        for (int $$3 = this.f_19146_ - 1; $$3 >= 0; --$$3) {
            ItemStack $$4 = this.m_8020_($$3);
            if (!$$4.m_41720_().equals(p_19171_)) continue;
            int $$5 = p_19172_ - $$2.m_41613_();
            ItemStack $$6 = $$4.m_41620_($$5);
            $$2.m_41769_($$6.m_41613_());
            if ($$2.m_41613_() == p_19172_) break;
        }
        if (!$$2.m_41619_()) {
            this.m_6596_();
        }
        return $$2;
    }

    public ItemStack m_19173_(ItemStack p_19174_) {
        ItemStack $$1 = p_19174_.m_41777_();
        this.m_19191_($$1);
        if ($$1.m_41619_()) {
            return ItemStack.f_41583_;
        }
        this.m_19189_($$1);
        if ($$1.m_41619_()) {
            return ItemStack.f_41583_;
        }
        return $$1;
    }

    public boolean m_19183_(ItemStack p_19184_) {
        boolean $$1 = false;
        for (ItemStack $$2 : this.f_19147_) {
            if (!$$2.m_41619_() && (!ItemStack.m_150942_($$2, p_19184_) || $$2.m_41613_() >= $$2.m_41741_())) continue;
            $$1 = true;
            break;
        }
        return $$1;
    }

    @Override
    public ItemStack m_8016_(int p_19180_) {
        ItemStack $$1 = this.f_19147_.get(p_19180_);
        if ($$1.m_41619_()) {
            return ItemStack.f_41583_;
        }
        this.f_19147_.set(p_19180_, ItemStack.f_41583_);
        return $$1;
    }

    @Override
    public void m_6836_(int p_19162_, ItemStack p_19163_) {
        this.f_19147_.set(p_19162_, p_19163_);
        if (!p_19163_.m_41619_() && p_19163_.m_41613_() > this.m_6893_()) {
            p_19163_.m_41764_(this.m_6893_());
        }
        this.m_6596_();
    }

    @Override
    public int m_6643_() {
        return this.f_19146_;
    }

    @Override
    public boolean m_7983_() {
        for (ItemStack $$0 : this.f_19147_) {
            if ($$0.m_41619_()) continue;
            return false;
        }
        return true;
    }

    @Override
    public void m_6596_() {
        if (this.f_19148_ != null) {
            for (ContainerListener $$0 : this.f_19148_) {
                $$0.m_5757_(this);
            }
        }
    }

    @Override
    public boolean m_6542_(Player p_19167_) {
        return true;
    }

    @Override
    public void m_6211_() {
        this.f_19147_.clear();
        this.m_6596_();
    }

    @Override
    public void m_5809_(StackedContents p_19169_) {
        for (ItemStack $$1 : this.f_19147_) {
            p_19169_.m_36491_($$1);
        }
    }

    public String toString() {
        return this.f_19147_.stream().filter(p_19194_ -> !p_19194_.m_41619_()).collect(Collectors.toList()).toString();
    }

    private void m_19189_(ItemStack p_19190_) {
        for (int $$1 = 0; $$1 < this.f_19146_; ++$$1) {
            ItemStack $$2 = this.m_8020_($$1);
            if (!$$2.m_41619_()) continue;
            this.m_6836_($$1, p_19190_.m_41777_());
            p_19190_.m_41764_(0);
            return;
        }
    }

    private void m_19191_(ItemStack p_19192_) {
        for (int $$1 = 0; $$1 < this.f_19146_; ++$$1) {
            ItemStack $$2 = this.m_8020_($$1);
            if (!ItemStack.m_150942_($$2, p_19192_)) continue;
            this.m_19185_(p_19192_, $$2);
            if (!p_19192_.m_41619_()) continue;
            return;
        }
    }

    private void m_19185_(ItemStack p_19186_, ItemStack p_19187_) {
        int $$2 = Math.min(this.m_6893_(), p_19187_.m_41741_());
        int $$3 = Math.min(p_19186_.m_41613_(), $$2 - p_19187_.m_41613_());
        if ($$3 > 0) {
            p_19187_.m_41769_($$3);
            p_19186_.m_41774_($$3);
            this.m_6596_();
        }
    }

    public void m_7797_(ListTag p_19178_) {
        for (int $$1 = 0; $$1 < p_19178_.size(); ++$$1) {
            ItemStack $$2 = ItemStack.m_41712_(p_19178_.m_128728_($$1));
            if ($$2.m_41619_()) continue;
            this.m_19173_($$2);
        }
    }

    public ListTag m_7927_() {
        ListTag $$0 = new ListTag();
        for (int $$1 = 0; $$1 < this.m_6643_(); ++$$1) {
            ItemStack $$2 = this.m_8020_($$1);
            if ($$2.m_41619_()) continue;
            $$0.add($$2.m_41739_(new CompoundTag()));
        }
        return $$0;
    }
}

