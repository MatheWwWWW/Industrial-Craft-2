/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.world.entity.player;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Nameable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class Inventory
implements Container,
Nameable {
    public static final int f_150064_ = 5;
    public static final int f_150065_ = 36;
    private static final int f_150070_ = 9;
    public static final int f_150066_ = 40;
    public static final int f_150067_ = -1;
    public static final int[] f_150068_ = new int[]{0, 1, 2, 3};
    public static final int[] f_150069_ = new int[]{3};
    public final NonNullList<ItemStack> f_35974_ = NonNullList.m_122780_(36, ItemStack.f_41583_);
    public final NonNullList<ItemStack> f_35975_ = NonNullList.m_122780_(4, ItemStack.f_41583_);
    public final NonNullList<ItemStack> f_35976_ = NonNullList.m_122780_(1, ItemStack.f_41583_);
    private final List<NonNullList<ItemStack>> f_35979_ = ImmutableList.of(this.f_35974_, this.f_35975_, this.f_35976_);
    public int f_35977_;
    public final Player f_35978_;
    private int f_35981_;

    public Inventory(Player p_35983_) {
        this.f_35978_ = p_35983_;
    }

    public ItemStack m_36056_() {
        if (Inventory.m_36045_(this.f_35977_)) {
            return this.f_35974_.get(this.f_35977_);
        }
        return ItemStack.f_41583_;
    }

    public static int m_36059_() {
        return 9;
    }

    private boolean m_36014_(ItemStack p_36015_, ItemStack p_36016_) {
        return !p_36015_.m_41619_() && ItemStack.m_150942_(p_36015_, p_36016_) && p_36015_.m_41753_() && p_36015_.m_41613_() < p_36015_.m_41741_() && p_36015_.m_41613_() < this.m_6893_();
    }

    public int m_36062_() {
        for (int $$0 = 0; $$0 < this.f_35974_.size(); ++$$0) {
            if (!this.f_35974_.get($$0).m_41619_()) continue;
            return $$0;
        }
        return -1;
    }

    public void m_36012_(ItemStack p_36013_) {
        int $$1 = this.m_36030_(p_36013_);
        if (Inventory.m_36045_($$1)) {
            this.f_35977_ = $$1;
            return;
        }
        if ($$1 == -1) {
            int $$2;
            this.f_35977_ = this.m_36065_();
            if (!this.f_35974_.get(this.f_35977_).m_41619_() && ($$2 = this.m_36062_()) != -1) {
                this.f_35974_.set($$2, this.f_35974_.get(this.f_35977_));
            }
            this.f_35974_.set(this.f_35977_, p_36013_);
        } else {
            this.m_36038_($$1);
        }
    }

    public void m_36038_(int p_36039_) {
        this.f_35977_ = this.m_36065_();
        ItemStack $$1 = this.f_35974_.get(this.f_35977_);
        this.f_35974_.set(this.f_35977_, this.f_35974_.get(p_36039_));
        this.f_35974_.set(p_36039_, $$1);
    }

    public static boolean m_36045_(int p_36046_) {
        return p_36046_ >= 0 && p_36046_ < 9;
    }

    public int m_36030_(ItemStack p_36031_) {
        for (int $$1 = 0; $$1 < this.f_35974_.size(); ++$$1) {
            if (this.f_35974_.get($$1).m_41619_() || !ItemStack.m_150942_(p_36031_, this.f_35974_.get($$1))) continue;
            return $$1;
        }
        return -1;
    }

    public int m_36043_(ItemStack p_36044_) {
        for (int $$1 = 0; $$1 < this.f_35974_.size(); ++$$1) {
            ItemStack $$2 = this.f_35974_.get($$1);
            if (this.f_35974_.get($$1).m_41619_() || !ItemStack.m_150942_(p_36044_, this.f_35974_.get($$1)) || this.f_35974_.get($$1).m_41768_() || $$2.m_41793_() || $$2.m_41788_()) continue;
            return $$1;
        }
        return -1;
    }

    public int m_36065_() {
        for (int $$0 = 0; $$0 < 9; ++$$0) {
            int $$1 = (this.f_35977_ + $$0) % 9;
            if (!this.f_35974_.get($$1).m_41619_()) continue;
            return $$1;
        }
        for (int $$2 = 0; $$2 < 9; ++$$2) {
            int $$3 = (this.f_35977_ + $$2) % 9;
            if (this.f_35974_.get($$3).m_41793_()) continue;
            return $$3;
        }
        return this.f_35977_;
    }

    public void m_35988_(double p_35989_) {
        int $$1 = (int)Math.signum(p_35989_);
        this.f_35977_ -= $$1;
        while (this.f_35977_ < 0) {
            this.f_35977_ += 9;
        }
        while (this.f_35977_ >= 9) {
            this.f_35977_ -= 9;
        }
    }

    public int m_36022_(Predicate<ItemStack> p_36023_, int p_36024_, Container p_36025_) {
        int $$3 = 0;
        boolean $$4 = p_36024_ == 0;
        $$3 += ContainerHelper.m_18956_(this, p_36023_, p_36024_ - $$3, $$4);
        $$3 += ContainerHelper.m_18956_(p_36025_, p_36023_, p_36024_ - $$3, $$4);
        ItemStack $$5 = this.f_35978_.f_36096_.m_142621_();
        $$3 += ContainerHelper.m_18961_($$5, p_36023_, p_36024_ - $$3, $$4);
        if ($$5.m_41619_()) {
            this.f_35978_.f_36096_.m_142503_(ItemStack.f_41583_);
        }
        return $$3;
    }

    private int m_36066_(ItemStack p_36067_) {
        int $$1 = this.m_36050_(p_36067_);
        if ($$1 == -1) {
            $$1 = this.m_36062_();
        }
        if ($$1 == -1) {
            return p_36067_.m_41613_();
        }
        return this.m_36047_($$1, p_36067_);
    }

    private int m_36047_(int p_36048_, ItemStack p_36049_) {
        int $$5;
        Item $$2 = p_36049_.m_41720_();
        int $$3 = p_36049_.m_41613_();
        ItemStack $$4 = this.m_8020_(p_36048_);
        if ($$4.m_41619_()) {
            $$4 = new ItemStack($$2, 0);
            if (p_36049_.m_41782_()) {
                $$4.m_41751_(p_36049_.m_41783_().m_6426_());
            }
            this.m_6836_(p_36048_, $$4);
        }
        if (($$5 = $$3) > $$4.m_41741_() - $$4.m_41613_()) {
            $$5 = $$4.m_41741_() - $$4.m_41613_();
        }
        if ($$5 > this.m_6893_() - $$4.m_41613_()) {
            $$5 = this.m_6893_() - $$4.m_41613_();
        }
        if ($$5 == 0) {
            return $$3;
        }
        $$4.m_41769_($$5);
        $$4.m_41754_(5);
        return $$3 -= $$5;
    }

    public int m_36050_(ItemStack p_36051_) {
        if (this.m_36014_(this.m_8020_(this.f_35977_), p_36051_)) {
            return this.f_35977_;
        }
        if (this.m_36014_(this.m_8020_(40), p_36051_)) {
            return 40;
        }
        for (int $$1 = 0; $$1 < this.f_35974_.size(); ++$$1) {
            if (!this.m_36014_(this.f_35974_.get($$1), p_36051_)) continue;
            return $$1;
        }
        return -1;
    }

    public void m_36068_() {
        for (NonNullList<ItemStack> $$0 : this.f_35979_) {
            for (int $$1 = 0; $$1 < $$0.size(); ++$$1) {
                if ($$0.get($$1).m_41619_()) continue;
                $$0.get($$1).m_41666_(this.f_35978_.f_19853_, this.f_35978_, $$1, this.f_35977_ == $$1);
            }
        }
    }

    public boolean m_36054_(ItemStack p_36055_) {
        return this.m_36040_(-1, p_36055_);
    }

    public boolean m_36040_(int p_36041_, ItemStack p_36042_) {
        if (p_36042_.m_41619_()) {
            return false;
        }
        try {
            if (!p_36042_.m_41768_()) {
                int $$2;
                do {
                    $$2 = p_36042_.m_41613_();
                    if (p_36041_ == -1) {
                        p_36042_.m_41764_(this.m_36066_(p_36042_));
                        continue;
                    }
                    p_36042_.m_41764_(this.m_36047_(p_36041_, p_36042_));
                } while (!p_36042_.m_41619_() && p_36042_.m_41613_() < $$2);
                if (p_36042_.m_41613_() == $$2 && this.f_35978_.m_150110_().f_35937_) {
                    p_36042_.m_41764_(0);
                    return true;
                }
                return p_36042_.m_41613_() < $$2;
            }
            if (p_36041_ == -1) {
                p_36041_ = this.m_36062_();
            }
            if (p_36041_ >= 0) {
                this.f_35974_.set(p_36041_, p_36042_.m_41777_());
                this.f_35974_.get(p_36041_).m_41754_(5);
                p_36042_.m_41764_(0);
                return true;
            }
            if (this.f_35978_.m_150110_().f_35937_) {
                p_36042_.m_41764_(0);
                return true;
            }
            return false;
        }
        catch (Throwable $$3) {
            CrashReport $$4 = CrashReport.m_127521_($$3, "Adding item to inventory");
            CrashReportCategory $$5 = $$4.m_127514_("Item being added");
            $$5.m_128159_("Item ID", Item.m_41393_(p_36042_.m_41720_()));
            $$5.m_128159_("Item data", p_36042_.m_41773_());
            $$5.m_128165_("Item name", () -> p_36042_.m_41786_().getString());
            throw new ReportedException($$4);
        }
    }

    public void m_150079_(ItemStack p_150080_) {
        this.m_150076_(p_150080_, true);
    }

    public void m_150076_(ItemStack p_150077_, boolean p_150078_) {
        while (!p_150077_.m_41619_()) {
            int $$2 = this.m_36050_(p_150077_);
            if ($$2 == -1) {
                $$2 = this.m_36062_();
            }
            if ($$2 == -1) {
                this.f_35978_.m_36176_(p_150077_, false);
                break;
            }
            int $$3 = p_150077_.m_41741_() - this.m_8020_($$2).m_41613_();
            if (!this.m_36040_($$2, p_150077_.m_41620_($$3)) || !p_150078_ || !(this.f_35978_ instanceof ServerPlayer)) continue;
            ((ServerPlayer)this.f_35978_).f_8906_.m_9829_(new ClientboundContainerSetSlotPacket(-2, 0, $$2, this.m_8020_($$2)));
        }
    }

    @Override
    public ItemStack m_7407_(int p_35993_, int p_35994_) {
        NonNullList<ItemStack> $$2 = null;
        for (NonNullList<ItemStack> $$3 : this.f_35979_) {
            if (p_35993_ < $$3.size()) {
                $$2 = $$3;
                break;
            }
            p_35993_ -= $$3.size();
        }
        if ($$2 != null && !((ItemStack)$$2.get(p_35993_)).m_41619_()) {
            return ContainerHelper.m_18969_($$2, p_35993_, p_35994_);
        }
        return ItemStack.f_41583_;
    }

    public void m_36057_(ItemStack p_36058_) {
        block0: for (NonNullList<ItemStack> $$1 : this.f_35979_) {
            for (int $$2 = 0; $$2 < $$1.size(); ++$$2) {
                if ($$1.get($$2) != p_36058_) continue;
                $$1.set($$2, ItemStack.f_41583_);
                continue block0;
            }
        }
    }

    @Override
    public ItemStack m_8016_(int p_36029_) {
        NonNullList<ItemStack> $$1 = null;
        for (NonNullList<ItemStack> $$2 : this.f_35979_) {
            if (p_36029_ < $$2.size()) {
                $$1 = $$2;
                break;
            }
            p_36029_ -= $$2.size();
        }
        if ($$1 != null && !((ItemStack)$$1.get(p_36029_)).m_41619_()) {
            ItemStack $$3 = $$1.get(p_36029_);
            $$1.set(p_36029_, ItemStack.f_41583_);
            return $$3;
        }
        return ItemStack.f_41583_;
    }

    @Override
    public void m_6836_(int p_35999_, ItemStack p_36000_) {
        NonNullList<ItemStack> $$2 = null;
        for (NonNullList<ItemStack> $$3 : this.f_35979_) {
            if (p_35999_ < $$3.size()) {
                $$2 = $$3;
                break;
            }
            p_35999_ -= $$3.size();
        }
        if ($$2 != null) {
            $$2.set(p_35999_, p_36000_);
        }
    }

    public float m_36020_(BlockState p_36021_) {
        return this.f_35974_.get(this.f_35977_).m_41691_(p_36021_);
    }

    public ListTag m_36026_(ListTag p_36027_) {
        for (int $$1 = 0; $$1 < this.f_35974_.size(); ++$$1) {
            if (this.f_35974_.get($$1).m_41619_()) continue;
            CompoundTag $$2 = new CompoundTag();
            $$2.m_128344_("Slot", (byte)$$1);
            this.f_35974_.get($$1).m_41739_($$2);
            p_36027_.add($$2);
        }
        for (int $$3 = 0; $$3 < this.f_35975_.size(); ++$$3) {
            if (this.f_35975_.get($$3).m_41619_()) continue;
            CompoundTag $$4 = new CompoundTag();
            $$4.m_128344_("Slot", (byte)($$3 + 100));
            this.f_35975_.get($$3).m_41739_($$4);
            p_36027_.add($$4);
        }
        for (int $$5 = 0; $$5 < this.f_35976_.size(); ++$$5) {
            if (this.f_35976_.get($$5).m_41619_()) continue;
            CompoundTag $$6 = new CompoundTag();
            $$6.m_128344_("Slot", (byte)($$5 + 150));
            this.f_35976_.get($$5).m_41739_($$6);
            p_36027_.add($$6);
        }
        return p_36027_;
    }

    public void m_36035_(ListTag p_36036_) {
        this.f_35974_.clear();
        this.f_35975_.clear();
        this.f_35976_.clear();
        for (int $$1 = 0; $$1 < p_36036_.size(); ++$$1) {
            CompoundTag $$2 = p_36036_.m_128728_($$1);
            int $$3 = $$2.m_128445_("Slot") & 0xFF;
            ItemStack $$4 = ItemStack.m_41712_($$2);
            if ($$4.m_41619_()) continue;
            if ($$3 >= 0 && $$3 < this.f_35974_.size()) {
                this.f_35974_.set($$3, $$4);
                continue;
            }
            if ($$3 >= 100 && $$3 < this.f_35975_.size() + 100) {
                this.f_35975_.set($$3 - 100, $$4);
                continue;
            }
            if ($$3 < 150 || $$3 >= this.f_35976_.size() + 150) continue;
            this.f_35976_.set($$3 - 150, $$4);
        }
    }

    @Override
    public int m_6643_() {
        return this.f_35974_.size() + this.f_35975_.size() + this.f_35976_.size();
    }

    @Override
    public boolean m_7983_() {
        for (ItemStack $$0 : this.f_35974_) {
            if ($$0.m_41619_()) continue;
            return false;
        }
        for (ItemStack $$1 : this.f_35975_) {
            if ($$1.m_41619_()) continue;
            return false;
        }
        for (ItemStack $$2 : this.f_35976_) {
            if ($$2.m_41619_()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack m_8020_(int p_35991_) {
        NonNullList<ItemStack> $$1 = null;
        for (NonNullList<ItemStack> $$2 : this.f_35979_) {
            if (p_35991_ < $$2.size()) {
                $$1 = $$2;
                break;
            }
            p_35991_ -= $$2.size();
        }
        return $$1 == null ? ItemStack.f_41583_ : (ItemStack)$$1.get(p_35991_);
    }

    @Override
    public Component m_7755_() {
        return Component.m_237115_("container.inventory");
    }

    public ItemStack m_36052_(int p_36053_) {
        return this.f_35975_.get(p_36053_);
    }

    public void m_150072_(DamageSource p_150073_, float p_150074_, int[] p_150075_) {
        if (p_150074_ <= 0.0f) {
            return;
        }
        if ((p_150074_ /= 4.0f) < 1.0f) {
            p_150074_ = 1.0f;
        }
        for (int $$3 : p_150075_) {
            ItemStack $$4 = this.f_35975_.get($$3);
            if (p_150073_.m_19384_() && $$4.m_41720_().m_41475_() || !($$4.m_41720_() instanceof ArmorItem)) continue;
            $$4.m_41622_((int)p_150074_, this.f_35978_, p_35997_ -> p_35997_.m_21166_(EquipmentSlot.m_20744_(EquipmentSlot.Type.ARMOR, $$3)));
        }
    }

    public void m_36071_() {
        for (List list : this.f_35979_) {
            for (int $$1 = 0; $$1 < list.size(); ++$$1) {
                ItemStack $$2 = (ItemStack)list.get($$1);
                if ($$2.m_41619_()) continue;
                this.f_35978_.m_7197_($$2, true, false);
                list.set($$1, ItemStack.f_41583_);
            }
        }
    }

    @Override
    public void m_6596_() {
        ++this.f_35981_;
    }

    public int m_36072_() {
        return this.f_35981_;
    }

    @Override
    public boolean m_6542_(Player p_36009_) {
        if (this.f_35978_.m_213877_()) {
            return false;
        }
        return !(p_36009_.m_20280_(this.f_35978_) > 64.0);
    }

    public boolean m_36063_(ItemStack p_36064_) {
        for (List list : this.f_35979_) {
            for (ItemStack $$2 : list) {
                if ($$2.m_41619_() || !$$2.m_41656_(p_36064_)) continue;
                return true;
            }
        }
        return false;
    }

    public boolean m_204075_(TagKey<Item> p_204076_) {
        for (List list : this.f_35979_) {
            for (ItemStack $$2 : list) {
                if ($$2.m_41619_() || !$$2.m_204117_(p_204076_)) continue;
                return true;
            }
        }
        return false;
    }

    public void m_36006_(Inventory p_36007_) {
        for (int $$1 = 0; $$1 < this.m_6643_(); ++$$1) {
            this.m_6836_($$1, p_36007_.m_8020_($$1));
        }
        this.f_35977_ = p_36007_.f_35977_;
    }

    @Override
    public void m_6211_() {
        for (List list : this.f_35979_) {
            list.clear();
        }
    }

    public void m_36010_(StackedContents p_36011_) {
        for (ItemStack $$1 : this.f_35974_) {
            p_36011_.m_36466_($$1);
        }
    }

    public ItemStack m_182403_(boolean p_182404_) {
        ItemStack $$1 = this.m_36056_();
        if ($$1.m_41619_()) {
            return ItemStack.f_41583_;
        }
        return this.m_7407_(this.f_35977_, p_182404_ ? $$1.m_41613_() : 1);
    }
}

