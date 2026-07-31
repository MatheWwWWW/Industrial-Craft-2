/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  com.google.common.base.Suppliers
 *  com.google.common.collect.HashBasedTable
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.inventory;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.ContainerSynchronizer;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.slf4j.Logger;

public abstract class AbstractContainerMenu {
    private static final Logger f_207773_ = LogUtils.getLogger();
    public static final int f_150385_ = -999;
    public static final int f_150386_ = 0;
    public static final int f_150387_ = 1;
    public static final int f_150388_ = 2;
    public static final int f_150389_ = 0;
    public static final int f_150390_ = 1;
    public static final int f_150391_ = 2;
    public static final int f_150392_ = Integer.MAX_VALUE;
    private final NonNullList<ItemStack> f_38841_ = NonNullList.m_122779_();
    public final NonNullList<Slot> f_38839_ = NonNullList.m_122779_();
    private final List<DataSlot> f_38842_ = Lists.newArrayList();
    private ItemStack f_150393_ = ItemStack.f_41583_;
    private final NonNullList<ItemStack> f_150394_ = NonNullList.m_122779_();
    private final IntList f_150395_ = new IntArrayList();
    private ItemStack f_150396_ = ItemStack.f_41583_;
    private int f_182405_;
    @Nullable
    private final MenuType<?> f_38843_;
    public final int f_38840_;
    private int f_38845_ = -1;
    private int f_38846_;
    private final Set<Slot> f_38847_ = Sets.newHashSet();
    private final List<ContainerListener> f_38848_ = Lists.newArrayList();
    @Nullable
    private ContainerSynchronizer f_150397_;
    private boolean f_150398_;

    protected AbstractContainerMenu(@Nullable MenuType<?> p_38851_, int p_38852_) {
        this.f_38843_ = p_38851_;
        this.f_38840_ = p_38852_;
    }

    protected static boolean m_38889_(ContainerLevelAccess p_38890_, Player p_38891_, Block p_38892_) {
        return p_38890_.m_39299_((p_38916_, p_38917_) -> {
            if (!p_38916_.m_8055_((BlockPos)p_38917_).m_60713_(p_38892_)) {
                return false;
            }
            return p_38891_.m_20275_((double)p_38917_.m_123341_() + 0.5, (double)p_38917_.m_123342_() + 0.5, (double)p_38917_.m_123343_() + 0.5) <= 64.0;
        }, true);
    }

    public MenuType<?> m_6772_() {
        if (this.f_38843_ == null) {
            throw new UnsupportedOperationException("Unable to construct this menu by type");
        }
        return this.f_38843_;
    }

    protected static void m_38869_(Container p_38870_, int p_38871_) {
        int $$2 = p_38870_.m_6643_();
        if ($$2 < p_38871_) {
            throw new IllegalArgumentException("Container size " + $$2 + " is smaller than expected " + p_38871_);
        }
    }

    protected static void m_38886_(ContainerData p_38887_, int p_38888_) {
        int $$2 = p_38887_.m_6499_();
        if ($$2 < p_38888_) {
            throw new IllegalArgumentException("Container data count " + $$2 + " is smaller than expected " + p_38888_);
        }
    }

    public boolean m_207775_(int p_207776_) {
        return p_207776_ == -1 || p_207776_ == -999 || p_207776_ < this.f_38839_.size();
    }

    protected Slot m_38897_(Slot p_38898_) {
        p_38898_.f_40219_ = this.f_38839_.size();
        this.f_38839_.add(p_38898_);
        this.f_38841_.add(ItemStack.f_41583_);
        this.f_150394_.add(ItemStack.f_41583_);
        return p_38898_;
    }

    protected DataSlot m_38895_(DataSlot p_38896_) {
        this.f_38842_.add(p_38896_);
        this.f_150395_.add(0);
        return p_38896_;
    }

    protected void m_38884_(ContainerData p_38885_) {
        for (int $$1 = 0; $$1 < p_38885_.m_6499_(); ++$$1) {
            this.m_38895_(DataSlot.m_39403_(p_38885_, $$1));
        }
    }

    public void m_38893_(ContainerListener p_38894_) {
        if (this.f_38848_.contains(p_38894_)) {
            return;
        }
        this.f_38848_.add(p_38894_);
        this.m_38946_();
    }

    public void m_150416_(ContainerSynchronizer p_150417_) {
        this.f_150397_ = p_150417_;
        this.m_150429_();
    }

    public void m_150429_() {
        int $$1 = this.f_38839_.size();
        for (int $$0 = 0; $$0 < $$1; ++$$0) {
            this.f_150394_.set($$0, this.f_38839_.get($$0).m_7993_().m_41777_());
        }
        this.f_150396_ = this.m_142621_().m_41777_();
        int $$3 = this.f_38842_.size();
        for (int $$2 = 0; $$2 < $$3; ++$$2) {
            this.f_150395_.set($$2, this.f_38842_.get($$2).m_6501_());
        }
        if (this.f_150397_ != null) {
            this.f_150397_.m_142589_(this, this.f_150394_, this.f_150396_, this.f_150395_.toIntArray());
        }
    }

    public void m_38943_(ContainerListener p_38944_) {
        this.f_38848_.remove(p_38944_);
    }

    public NonNullList<ItemStack> m_38927_() {
        NonNullList<ItemStack> $$0 = NonNullList.m_122779_();
        for (Slot $$1 : this.f_38839_) {
            $$0.add($$1.m_7993_());
        }
        return $$0;
    }

    public void m_38946_() {
        for (int $$0 = 0; $$0 < this.f_38839_.size(); ++$$0) {
            ItemStack $$1 = this.f_38839_.get($$0).m_7993_();
            Supplier $$2 = Suppliers.memoize($$1::m_41777_);
            this.m_150407_($$0, $$1, (java.util.function.Supplier<ItemStack>)$$2);
            this.m_150435_($$0, $$1, (java.util.function.Supplier<ItemStack>)$$2);
        }
        this.m_150445_();
        for (int $$3 = 0; $$3 < this.f_38842_.size(); ++$$3) {
            DataSlot $$4 = this.f_38842_.get($$3);
            int $$5 = $$4.m_6501_();
            if ($$4.m_39409_()) {
                this.m_182420_($$3, $$5);
            }
            this.m_150440_($$3, $$5);
        }
    }

    public void m_182423_() {
        for (int $$0 = 0; $$0 < this.f_38839_.size(); ++$$0) {
            ItemStack $$1 = this.f_38839_.get($$0).m_7993_();
            this.m_150407_($$0, $$1, $$1::m_41777_);
        }
        for (int $$2 = 0; $$2 < this.f_38842_.size(); ++$$2) {
            DataSlot $$3 = this.f_38842_.get($$2);
            if (!$$3.m_39409_()) continue;
            this.m_182420_($$2, $$3.m_6501_());
        }
        this.m_150429_();
    }

    private void m_182420_(int p_182421_, int p_182422_) {
        for (ContainerListener $$2 : this.f_38848_) {
            $$2.m_142153_(this, p_182421_, p_182422_);
        }
    }

    private void m_150407_(int p_150408_, ItemStack p_150409_, java.util.function.Supplier<ItemStack> p_150410_) {
        ItemStack $$3 = this.f_38841_.get(p_150408_);
        if (!ItemStack.m_41728_($$3, p_150409_)) {
            ItemStack $$4 = p_150410_.get();
            this.f_38841_.set(p_150408_, $$4);
            for (ContainerListener $$5 : this.f_38848_) {
                $$5.m_7934_(this, p_150408_, $$4);
            }
        }
    }

    private void m_150435_(int p_150436_, ItemStack p_150437_, java.util.function.Supplier<ItemStack> p_150438_) {
        if (this.f_150398_) {
            return;
        }
        ItemStack $$3 = this.f_150394_.get(p_150436_);
        if (!ItemStack.m_41728_($$3, p_150437_)) {
            ItemStack $$4 = p_150438_.get();
            this.f_150394_.set(p_150436_, $$4);
            if (this.f_150397_ != null) {
                this.f_150397_.m_142074_(this, p_150436_, $$4);
            }
        }
    }

    private void m_150440_(int p_150441_, int p_150442_) {
        if (this.f_150398_) {
            return;
        }
        int $$2 = this.f_150395_.getInt(p_150441_);
        if ($$2 != p_150442_) {
            this.f_150395_.set(p_150441_, p_150442_);
            if (this.f_150397_ != null) {
                this.f_150397_.m_142145_(this, p_150441_, p_150442_);
            }
        }
    }

    private void m_150445_() {
        if (this.f_150398_) {
            return;
        }
        if (!ItemStack.m_41728_(this.m_142621_(), this.f_150396_)) {
            this.f_150396_ = this.m_142621_().m_41777_();
            if (this.f_150397_ != null) {
                this.f_150397_.m_142529_(this, this.f_150396_);
            }
        }
    }

    public void m_150404_(int p_150405_, ItemStack p_150406_) {
        this.f_150394_.set(p_150405_, p_150406_.m_41777_());
    }

    public void m_182414_(int p_182415_, ItemStack p_182416_) {
        if (p_182415_ < 0 || p_182415_ >= this.f_150394_.size()) {
            f_207773_.debug("Incorrect slot index: {} available slots: {}", (Object)p_182415_, (Object)this.f_150394_.size());
            return;
        }
        this.f_150394_.set(p_182415_, p_182416_);
    }

    public void m_150422_(ItemStack p_150423_) {
        this.f_150396_ = p_150423_.m_41777_();
    }

    public boolean m_6366_(Player p_38875_, int p_38876_) {
        return false;
    }

    public Slot m_38853_(int p_38854_) {
        return this.f_38839_.get(p_38854_);
    }

    public abstract ItemStack m_7648_(Player var1, int var2);

    public void m_150399_(int p_150400_, int p_150401_, ClickType p_150402_, Player p_150403_) {
        try {
            this.m_150430_(p_150400_, p_150401_, p_150402_, p_150403_);
        }
        catch (Exception $$4) {
            CrashReport $$5 = CrashReport.m_127521_($$4, "Container click");
            CrashReportCategory $$6 = $$5.m_127514_("Click info");
            $$6.m_128165_("Menu Type", () -> this.f_38843_ != null ? Registry.f_122863_.m_7981_(this.f_38843_).toString() : "<no type>");
            $$6.m_128165_("Menu Class", () -> this.getClass().getCanonicalName());
            $$6.m_128159_("Slot Count", this.f_38839_.size());
            $$6.m_128159_("Slot", p_150400_);
            $$6.m_128159_("Button", p_150401_);
            $$6.m_128159_("Type", (Object)p_150402_);
            throw new ReportedException($$5);
        }
    }

    private void m_150430_(int p_150431_, int p_150432_, ClickType p_150433_, Player p_150434_) {
        block39: {
            block50: {
                block46: {
                    ItemStack $$29;
                    ItemStack $$28;
                    Slot $$27;
                    Inventory $$4;
                    block49: {
                        block48: {
                            block47: {
                                block44: {
                                    ClickAction $$16;
                                    block45: {
                                        block43: {
                                            block37: {
                                                block42: {
                                                    ItemStack $$7;
                                                    block41: {
                                                        block40: {
                                                            block38: {
                                                                $$4 = p_150434_.m_150109_();
                                                                if (p_150433_ != ClickType.QUICK_CRAFT) break block37;
                                                                int $$5 = this.f_38846_;
                                                                this.f_38846_ = AbstractContainerMenu.m_38947_(p_150432_);
                                                                if ($$5 == 1 && this.f_38846_ == 2 || $$5 == this.f_38846_) break block38;
                                                                this.m_38951_();
                                                                break block39;
                                                            }
                                                            if (!this.m_142621_().m_41619_()) break block40;
                                                            this.m_38951_();
                                                            break block39;
                                                        }
                                                        if (this.f_38846_ != 0) break block41;
                                                        this.f_38845_ = AbstractContainerMenu.m_38928_(p_150432_);
                                                        if (AbstractContainerMenu.m_38862_(this.f_38845_, p_150434_)) {
                                                            this.f_38846_ = 1;
                                                            this.f_38847_.clear();
                                                        } else {
                                                            this.m_38951_();
                                                        }
                                                        break block39;
                                                    }
                                                    if (this.f_38846_ != 1) break block42;
                                                    Slot $$6 = this.f_38839_.get(p_150431_);
                                                    if (!AbstractContainerMenu.m_38899_($$6, $$7 = this.m_142621_(), true) || !$$6.m_5857_($$7) || this.f_38845_ != 2 && $$7.m_41613_() <= this.f_38847_.size() || !this.m_5622_($$6)) break block39;
                                                    this.f_38847_.add($$6);
                                                    break block39;
                                                }
                                                if (this.f_38846_ == 2) {
                                                    if (!this.f_38847_.isEmpty()) {
                                                        if (this.f_38847_.size() == 1) {
                                                            int $$8 = this.f_38847_.iterator().next().f_40219_;
                                                            this.m_38951_();
                                                            this.m_150430_($$8, this.f_38845_, ClickType.PICKUP, p_150434_);
                                                            return;
                                                        }
                                                        ItemStack $$9 = this.m_142621_().m_41777_();
                                                        int $$10 = this.m_142621_().m_41613_();
                                                        for (Slot $$11 : this.f_38847_) {
                                                            ItemStack $$12 = this.m_142621_();
                                                            if ($$11 == null || !AbstractContainerMenu.m_38899_($$11, $$12, true) || !$$11.m_5857_($$12) || this.f_38845_ != 2 && $$12.m_41613_() < this.f_38847_.size() || !this.m_5622_($$11)) continue;
                                                            ItemStack $$13 = $$9.m_41777_();
                                                            int $$14 = $$11.m_6657_() ? $$11.m_7993_().m_41613_() : 0;
                                                            AbstractContainerMenu.m_38922_(this.f_38847_, this.f_38845_, $$13, $$14);
                                                            int $$15 = Math.min($$13.m_41741_(), $$11.m_5866_($$13));
                                                            if ($$13.m_41613_() > $$15) {
                                                                $$13.m_41764_($$15);
                                                            }
                                                            $$10 -= $$13.m_41613_() - $$14;
                                                            $$11.m_5852_($$13);
                                                        }
                                                        $$9.m_41764_($$10);
                                                        this.m_142503_($$9);
                                                    }
                                                    this.m_38951_();
                                                } else {
                                                    this.m_38951_();
                                                }
                                                break block39;
                                            }
                                            if (this.f_38846_ == 0) break block43;
                                            this.m_38951_();
                                            break block39;
                                        }
                                        if (p_150433_ != ClickType.PICKUP && p_150433_ != ClickType.QUICK_MOVE || p_150432_ != 0 && p_150432_ != 1) break block44;
                                        ClickAction clickAction = $$16 = p_150432_ == 0 ? ClickAction.PRIMARY : ClickAction.SECONDARY;
                                        if (p_150431_ != -999) break block45;
                                        if (this.m_142621_().m_41619_()) break block39;
                                        if ($$16 == ClickAction.PRIMARY) {
                                            p_150434_.m_36176_(this.m_142621_(), true);
                                            this.m_142503_(ItemStack.f_41583_);
                                        } else {
                                            p_150434_.m_36176_(this.m_142621_().m_41620_(1), true);
                                        }
                                        break block39;
                                    }
                                    if (p_150433_ == ClickType.QUICK_MOVE) {
                                        if (p_150431_ < 0) {
                                            return;
                                        }
                                        Slot $$17 = this.f_38839_.get(p_150431_);
                                        if (!$$17.m_8010_(p_150434_)) {
                                            return;
                                        }
                                        ItemStack $$18 = this.m_7648_(p_150434_, p_150431_);
                                        while (!$$18.m_41619_() && ItemStack.m_41746_($$17.m_7993_(), $$18)) {
                                            $$18 = this.m_7648_(p_150434_, p_150431_);
                                        }
                                    } else {
                                        if (p_150431_ < 0) {
                                            return;
                                        }
                                        Slot $$19 = this.f_38839_.get(p_150431_);
                                        ItemStack $$20 = $$19.m_7993_();
                                        ItemStack $$21 = this.m_142621_();
                                        p_150434_.m_141945_($$21, $$19.m_7993_(), $$16);
                                        if (!$$21.m_150926_($$19, $$16, p_150434_) && !$$20.m_150932_($$21, $$19, $$16, p_150434_, this.m_150446_())) {
                                            if ($$20.m_41619_()) {
                                                if (!$$21.m_41619_()) {
                                                    int $$22 = $$16 == ClickAction.PRIMARY ? $$21.m_41613_() : 1;
                                                    this.m_142503_($$19.m_150656_($$21, $$22));
                                                }
                                            } else if ($$19.m_8010_(p_150434_)) {
                                                if ($$21.m_41619_()) {
                                                    int $$23 = $$16 == ClickAction.PRIMARY ? $$20.m_41613_() : ($$20.m_41613_() + 1) / 2;
                                                    Optional<ItemStack> $$24 = $$19.m_150641_($$23, Integer.MAX_VALUE, p_150434_);
                                                    $$24.ifPresent(p_150421_ -> {
                                                        this.m_142503_((ItemStack)p_150421_);
                                                        $$19.m_142406_(p_150434_, (ItemStack)p_150421_);
                                                    });
                                                } else if ($$19.m_5857_($$21)) {
                                                    if (ItemStack.m_150942_($$20, $$21)) {
                                                        int $$25 = $$16 == ClickAction.PRIMARY ? $$21.m_41613_() : 1;
                                                        this.m_142503_($$19.m_150656_($$21, $$25));
                                                    } else if ($$21.m_41613_() <= $$19.m_5866_($$21)) {
                                                        this.m_142503_($$20);
                                                        $$19.m_5852_($$21);
                                                    }
                                                } else if (ItemStack.m_150942_($$20, $$21)) {
                                                    Optional<ItemStack> $$26 = $$19.m_150641_($$20.m_41613_(), $$21.m_41741_() - $$21.m_41613_(), p_150434_);
                                                    $$26.ifPresent(p_150428_ -> {
                                                        $$21.m_41769_(p_150428_.m_41613_());
                                                        $$19.m_142406_(p_150434_, (ItemStack)p_150428_);
                                                    });
                                                }
                                            }
                                        }
                                        $$19.m_6654_();
                                    }
                                    break block39;
                                }
                                if (p_150433_ != ClickType.SWAP) break block46;
                                $$27 = this.f_38839_.get(p_150431_);
                                $$28 = $$4.m_8020_(p_150432_);
                                $$29 = $$27.m_7993_();
                                if ($$28.m_41619_() && $$29.m_41619_()) break block39;
                                if (!$$28.m_41619_()) break block47;
                                if (!$$27.m_8010_(p_150434_)) break block39;
                                $$4.m_6836_(p_150432_, $$29);
                                $$27.m_6405_($$29.m_41613_());
                                $$27.m_5852_(ItemStack.f_41583_);
                                $$27.m_142406_(p_150434_, $$29);
                                break block39;
                            }
                            if (!$$29.m_41619_()) break block48;
                            if (!$$27.m_5857_($$28)) break block39;
                            int $$30 = $$27.m_5866_($$28);
                            if ($$28.m_41613_() > $$30) {
                                $$27.m_5852_($$28.m_41620_($$30));
                            } else {
                                $$4.m_6836_(p_150432_, ItemStack.f_41583_);
                                $$27.m_5852_($$28);
                            }
                            break block39;
                        }
                        if (!$$27.m_8010_(p_150434_) || !$$27.m_5857_($$28)) break block39;
                        int $$31 = $$27.m_5866_($$28);
                        if ($$28.m_41613_() <= $$31) break block49;
                        $$27.m_5852_($$28.m_41620_($$31));
                        $$27.m_142406_(p_150434_, $$29);
                        if ($$4.m_36054_($$29)) break block39;
                        p_150434_.m_36176_($$29, true);
                        break block39;
                    }
                    $$4.m_6836_(p_150432_, $$29);
                    $$27.m_5852_($$28);
                    $$27.m_142406_(p_150434_, $$29);
                    break block39;
                }
                if (p_150433_ != ClickType.CLONE || !p_150434_.m_150110_().f_35937_ || !this.m_142621_().m_41619_() || p_150431_ < 0) break block50;
                Slot $$32 = this.f_38839_.get(p_150431_);
                if (!$$32.m_6657_()) break block39;
                ItemStack $$33 = $$32.m_7993_().m_41777_();
                $$33.m_41764_($$33.m_41741_());
                this.m_142503_($$33);
                break block39;
            }
            if (p_150433_ == ClickType.THROW && this.m_142621_().m_41619_() && p_150431_ >= 0) {
                Slot $$34 = this.f_38839_.get(p_150431_);
                int $$35 = p_150432_ == 0 ? 1 : $$34.m_7993_().m_41613_();
                ItemStack $$36 = $$34.m_150647_($$35, Integer.MAX_VALUE, p_150434_);
                p_150434_.m_36176_($$36, true);
            } else if (p_150433_ == ClickType.PICKUP_ALL && p_150431_ >= 0) {
                Slot $$37 = this.f_38839_.get(p_150431_);
                ItemStack $$38 = this.m_142621_();
                if (!($$38.m_41619_() || $$37.m_6657_() && $$37.m_8010_(p_150434_))) {
                    int $$39 = p_150432_ == 0 ? 0 : this.f_38839_.size() - 1;
                    int $$40 = p_150432_ == 0 ? 1 : -1;
                    for (int $$41 = 0; $$41 < 2; ++$$41) {
                        for (int $$42 = $$39; $$42 >= 0 && $$42 < this.f_38839_.size() && $$38.m_41613_() < $$38.m_41741_(); $$42 += $$40) {
                            Slot $$43 = this.f_38839_.get($$42);
                            if (!$$43.m_6657_() || !AbstractContainerMenu.m_38899_($$43, $$38, true) || !$$43.m_8010_(p_150434_) || !this.m_5882_($$38, $$43)) continue;
                            ItemStack $$44 = $$43.m_7993_();
                            if ($$41 == 0 && $$44.m_41613_() == $$44.m_41741_()) continue;
                            ItemStack $$45 = $$43.m_150647_($$44.m_41613_(), $$38.m_41741_() - $$38.m_41613_(), p_150434_);
                            $$38.m_41769_($$45.m_41613_());
                        }
                    }
                }
            }
        }
    }

    private SlotAccess m_150446_() {
        return new SlotAccess(){

            @Override
            public ItemStack m_142196_() {
                return AbstractContainerMenu.this.m_142621_();
            }

            @Override
            public boolean m_142104_(ItemStack p_150452_) {
                AbstractContainerMenu.this.m_142503_(p_150452_);
                return true;
            }
        };
    }

    public boolean m_5882_(ItemStack p_38908_, Slot p_38909_) {
        return true;
    }

    public void m_6877_(Player p_38940_) {
        ItemStack $$1;
        if (p_38940_ instanceof ServerPlayer && !($$1 = this.m_142621_()).m_41619_()) {
            if (!p_38940_.m_6084_() || ((ServerPlayer)p_38940_).m_9232_()) {
                p_38940_.m_36176_($$1, false);
            } else {
                p_38940_.m_150109_().m_150079_($$1);
            }
            this.m_142503_(ItemStack.f_41583_);
        }
    }

    protected void m_150411_(Player p_150412_, Container p_150413_) {
        if (!p_150412_.m_6084_() || p_150412_ instanceof ServerPlayer && ((ServerPlayer)p_150412_).m_9232_()) {
            for (int $$2 = 0; $$2 < p_150413_.m_6643_(); ++$$2) {
                p_150412_.m_36176_(p_150413_.m_8016_($$2), false);
            }
            return;
        }
        for (int $$3 = 0; $$3 < p_150413_.m_6643_(); ++$$3) {
            Inventory $$4 = p_150412_.m_150109_();
            if (!($$4.f_35978_ instanceof ServerPlayer)) continue;
            $$4.m_150079_(p_150413_.m_8016_($$3));
        }
    }

    public void m_6199_(Container p_38868_) {
        this.m_38946_();
    }

    public void m_182406_(int p_182407_, int p_182408_, ItemStack p_182409_) {
        this.m_38853_(p_182407_).m_5852_(p_182409_);
        this.f_182405_ = p_182408_;
    }

    public void m_182410_(int p_182411_, List<ItemStack> p_182412_, ItemStack p_182413_) {
        for (int $$3 = 0; $$3 < p_182412_.size(); ++$$3) {
            this.m_38853_($$3).m_219996_(p_182412_.get($$3));
        }
        this.f_150393_ = p_182413_;
        this.f_182405_ = p_182411_;
    }

    public void m_7511_(int p_38855_, int p_38856_) {
        this.f_38842_.get(p_38855_).m_6422_(p_38856_);
    }

    public abstract boolean m_6875_(Player var1);

    protected boolean m_38903_(ItemStack p_38904_, int p_38905_, int p_38906_, boolean p_38907_) {
        boolean $$4 = false;
        int $$5 = p_38905_;
        if (p_38907_) {
            $$5 = p_38906_ - 1;
        }
        if (p_38904_.m_41753_()) {
            while (!p_38904_.m_41619_() && (p_38907_ ? $$5 >= p_38905_ : $$5 < p_38906_)) {
                Slot $$6 = this.f_38839_.get($$5);
                ItemStack $$7 = $$6.m_7993_();
                if (!$$7.m_41619_() && ItemStack.m_150942_(p_38904_, $$7)) {
                    int $$8 = $$7.m_41613_() + p_38904_.m_41613_();
                    if ($$8 <= p_38904_.m_41741_()) {
                        p_38904_.m_41764_(0);
                        $$7.m_41764_($$8);
                        $$6.m_6654_();
                        $$4 = true;
                    } else if ($$7.m_41613_() < p_38904_.m_41741_()) {
                        p_38904_.m_41774_(p_38904_.m_41741_() - $$7.m_41613_());
                        $$7.m_41764_(p_38904_.m_41741_());
                        $$6.m_6654_();
                        $$4 = true;
                    }
                }
                if (p_38907_) {
                    --$$5;
                    continue;
                }
                ++$$5;
            }
        }
        if (!p_38904_.m_41619_()) {
            $$5 = p_38907_ ? p_38906_ - 1 : p_38905_;
            while (p_38907_ ? $$5 >= p_38905_ : $$5 < p_38906_) {
                Slot $$9 = this.f_38839_.get($$5);
                ItemStack $$10 = $$9.m_7993_();
                if ($$10.m_41619_() && $$9.m_5857_(p_38904_)) {
                    if (p_38904_.m_41613_() > $$9.m_6641_()) {
                        $$9.m_5852_(p_38904_.m_41620_($$9.m_6641_()));
                    } else {
                        $$9.m_5852_(p_38904_.m_41620_(p_38904_.m_41613_()));
                    }
                    $$9.m_6654_();
                    $$4 = true;
                    break;
                }
                if (p_38907_) {
                    --$$5;
                    continue;
                }
                ++$$5;
            }
        }
        return $$4;
    }

    public static int m_38928_(int p_38929_) {
        return p_38929_ >> 2 & 3;
    }

    public static int m_38947_(int p_38948_) {
        return p_38948_ & 3;
    }

    public static int m_38930_(int p_38931_, int p_38932_) {
        return p_38931_ & 3 | (p_38932_ & 3) << 2;
    }

    public static boolean m_38862_(int p_38863_, Player p_38864_) {
        if (p_38863_ == 0) {
            return true;
        }
        if (p_38863_ == 1) {
            return true;
        }
        return p_38863_ == 2 && p_38864_.m_150110_().f_35937_;
    }

    protected void m_38951_() {
        this.f_38846_ = 0;
        this.f_38847_.clear();
    }

    public static boolean m_38899_(@Nullable Slot p_38900_, ItemStack p_38901_, boolean p_38902_) {
        boolean $$3;
        boolean bl = $$3 = p_38900_ == null || !p_38900_.m_6657_();
        if (!$$3 && ItemStack.m_150942_(p_38901_, p_38900_.m_7993_())) {
            return p_38900_.m_7993_().m_41613_() + (p_38902_ ? 0 : p_38901_.m_41613_()) <= p_38901_.m_41741_();
        }
        return $$3;
    }

    public static void m_38922_(Set<Slot> p_38923_, int p_38924_, ItemStack p_38925_, int p_38926_) {
        switch (p_38924_) {
            case 0: {
                p_38925_.m_41764_(Mth.m_14143_((float)p_38925_.m_41613_() / (float)p_38923_.size()));
                break;
            }
            case 1: {
                p_38925_.m_41764_(1);
                break;
            }
            case 2: {
                p_38925_.m_41764_(p_38925_.m_41720_().m_41459_());
            }
        }
        p_38925_.m_41769_(p_38926_);
    }

    public boolean m_5622_(Slot p_38945_) {
        return true;
    }

    public static int m_38918_(@Nullable BlockEntity p_38919_) {
        if (p_38919_ instanceof Container) {
            return AbstractContainerMenu.m_38938_((Container)((Object)p_38919_));
        }
        return 0;
    }

    public static int m_38938_(@Nullable Container p_38939_) {
        if (p_38939_ == null) {
            return 0;
        }
        int $$1 = 0;
        float $$2 = 0.0f;
        for (int $$3 = 0; $$3 < p_38939_.m_6643_(); ++$$3) {
            ItemStack $$4 = p_38939_.m_8020_($$3);
            if ($$4.m_41619_()) continue;
            $$2 += (float)$$4.m_41613_() / (float)Math.min(p_38939_.m_6893_(), $$4.m_41741_());
            ++$$1;
        }
        return Mth.m_14143_(($$2 /= (float)p_38939_.m_6643_()) * 14.0f) + ($$1 > 0 ? 1 : 0);
    }

    public void m_142503_(ItemStack p_150439_) {
        this.f_150393_ = p_150439_;
    }

    public ItemStack m_142621_() {
        return this.f_150393_;
    }

    public void m_150443_() {
        this.f_150398_ = true;
    }

    public void m_150444_() {
        this.f_150398_ = false;
    }

    public void m_150414_(AbstractContainerMenu p_150415_) {
        HashBasedTable $$1 = HashBasedTable.create();
        for (int $$2 = 0; $$2 < p_150415_.f_38839_.size(); ++$$2) {
            Slot $$3 = p_150415_.f_38839_.get($$2);
            $$1.put((Object)$$3.f_40218_, (Object)$$3.m_150661_(), (Object)$$2);
        }
        for (int $$4 = 0; $$4 < this.f_38839_.size(); ++$$4) {
            Slot $$5 = this.f_38839_.get($$4);
            Integer $$6 = (Integer)$$1.get((Object)$$5.f_40218_, (Object)$$5.m_150661_());
            if ($$6 == null) continue;
            this.f_38841_.set($$4, p_150415_.f_38841_.get($$6));
            this.f_150394_.set($$4, p_150415_.f_150394_.get($$6));
        }
    }

    public OptionalInt m_182417_(Container p_182418_, int p_182419_) {
        for (int $$2 = 0; $$2 < this.f_38839_.size(); ++$$2) {
            Slot $$3 = this.f_38839_.get($$2);
            if ($$3.f_40218_ != p_182418_ || p_182419_ != $$3.m_150661_()) continue;
            return OptionalInt.of($$2);
        }
        return OptionalInt.empty();
    }

    public int m_182424_() {
        return this.f_182405_;
    }

    public int m_182425_() {
        this.f_182405_ = this.f_182405_ + 1 & Short.MAX_VALUE;
        return this.f_182405_;
    }
}

