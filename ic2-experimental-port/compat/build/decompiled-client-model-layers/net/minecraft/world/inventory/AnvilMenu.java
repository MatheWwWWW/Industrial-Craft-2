/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 */
package net.minecraft.world.inventory;

import com.mojang.logging.LogUtils;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

public class AnvilMenu
extends ItemCombinerMenu {
    private static final Logger f_38999_ = LogUtils.getLogger();
    private static final boolean f_150469_ = false;
    public static final int f_150468_ = 50;
    private int f_39000_;
    private String f_39001_;
    private final DataSlot f_39002_ = DataSlot.m_39401_();
    private static final int f_150470_ = 0;
    private static final int f_150471_ = 1;
    private static final int f_150472_ = 1;
    private static final int f_150464_ = 1;
    private static final int f_150465_ = 2;
    private static final int f_150466_ = 1;
    private static final int f_150467_ = 1;

    public AnvilMenu(int p_39005_, Inventory p_39006_) {
        this(p_39005_, p_39006_, ContainerLevelAccess.f_39287_);
    }

    public AnvilMenu(int p_39008_, Inventory p_39009_, ContainerLevelAccess p_39010_) {
        super(MenuType.f_39964_, p_39008_, p_39009_, p_39010_);
        this.m_38895_(this.f_39002_);
    }

    @Override
    protected boolean m_8039_(BlockState p_39019_) {
        return p_39019_.m_204336_(BlockTags.f_13033_);
    }

    @Override
    protected boolean m_6560_(Player p_39023_, boolean p_39024_) {
        return (p_39023_.m_150110_().f_35937_ || p_39023_.f_36078_ >= this.f_39002_.m_6501_()) && this.f_39002_.m_6501_() > 0;
    }

    @Override
    protected void m_142365_(Player p_150474_, ItemStack p_150475_) {
        if (!p_150474_.m_150110_().f_35937_) {
            p_150474_.m_6749_(-this.f_39002_.m_6501_());
        }
        this.f_39769_.m_6836_(0, ItemStack.f_41583_);
        if (this.f_39000_ > 0) {
            ItemStack $$2 = this.f_39769_.m_8020_(1);
            if (!$$2.m_41619_() && $$2.m_41613_() > this.f_39000_) {
                $$2.m_41774_(this.f_39000_);
                this.f_39769_.m_6836_(1, $$2);
            } else {
                this.f_39769_.m_6836_(1, ItemStack.f_41583_);
            }
        } else {
            this.f_39769_.m_6836_(1, ItemStack.f_41583_);
        }
        this.f_39002_.m_6422_(0);
        this.f_39770_.m_39292_((p_150479_, p_150480_) -> {
            BlockState $$3 = p_150479_.m_8055_((BlockPos)p_150480_);
            if (!p_182427_.m_150110_().f_35937_ && $$3.m_204336_(BlockTags.f_13033_) && p_150474_.m_217043_().m_188501_() < 0.12f) {
                BlockState $$4 = AnvilBlock.m_48824_($$3);
                if ($$4 == null) {
                    p_150479_.m_7471_((BlockPos)p_150480_, false);
                    p_150479_.m_46796_(1029, (BlockPos)p_150480_, 0);
                } else {
                    p_150479_.m_7731_((BlockPos)p_150480_, $$4, 2);
                    p_150479_.m_46796_(1030, (BlockPos)p_150480_, 0);
                }
            } else {
                p_150479_.m_46796_(1030, (BlockPos)p_150480_, 0);
            }
        });
    }

    @Override
    public void m_6640_() {
        ItemStack $$0 = this.f_39769_.m_8020_(0);
        this.f_39002_.m_6422_(1);
        int $$1 = 0;
        int $$2 = 0;
        int $$3 = 0;
        if ($$0.m_41619_()) {
            this.f_39768_.m_6836_(0, ItemStack.f_41583_);
            this.f_39002_.m_6422_(0);
            return;
        }
        ItemStack $$4 = $$0.m_41777_();
        ItemStack $$5 = this.f_39769_.m_8020_(1);
        Map<Enchantment, Integer> $$6 = EnchantmentHelper.m_44831_($$4);
        $$2 += $$0.m_41610_() + ($$5.m_41619_() ? 0 : $$5.m_41610_());
        this.f_39000_ = 0;
        if (!$$5.m_41619_()) {
            boolean $$7;
            boolean bl = $$7 = $$5.m_150930_(Items.f_42690_) && !EnchantedBookItem.m_41163_($$5).isEmpty();
            if ($$4.m_41763_() && $$4.m_41720_().m_6832_($$0, $$5)) {
                int $$9;
                int $$8 = Math.min($$4.m_41773_(), $$4.m_41776_() / 4);
                if ($$8 <= 0) {
                    this.f_39768_.m_6836_(0, ItemStack.f_41583_);
                    this.f_39002_.m_6422_(0);
                    return;
                }
                for ($$9 = 0; $$8 > 0 && $$9 < $$5.m_41613_(); ++$$9) {
                    int $$10 = $$4.m_41773_() - $$8;
                    $$4.m_41721_($$10);
                    ++$$1;
                    $$8 = Math.min($$4.m_41773_(), $$4.m_41776_() / 4);
                }
                this.f_39000_ = $$9;
            } else {
                if (!($$7 || $$4.m_150930_($$5.m_41720_()) && $$4.m_41763_())) {
                    this.f_39768_.m_6836_(0, ItemStack.f_41583_);
                    this.f_39002_.m_6422_(0);
                    return;
                }
                if ($$4.m_41763_() && !$$7) {
                    int $$11 = $$0.m_41776_() - $$0.m_41773_();
                    int $$12 = $$5.m_41776_() - $$5.m_41773_();
                    int $$13 = $$12 + $$4.m_41776_() * 12 / 100;
                    int $$14 = $$11 + $$13;
                    int $$15 = $$4.m_41776_() - $$14;
                    if ($$15 < 0) {
                        $$15 = 0;
                    }
                    if ($$15 < $$4.m_41773_()) {
                        $$4.m_41721_($$15);
                        $$1 += 2;
                    }
                }
                Map<Enchantment, Integer> $$16 = EnchantmentHelper.m_44831_($$5);
                boolean $$17 = false;
                boolean $$18 = false;
                for (Enchantment $$19 : $$16.keySet()) {
                    int $$21;
                    if ($$19 == null) continue;
                    int $$20 = $$6.getOrDefault($$19, 0);
                    $$21 = $$20 == ($$21 = $$16.get($$19).intValue()) ? $$21 + 1 : Math.max($$21, $$20);
                    boolean $$22 = $$19.m_6081_($$0);
                    if (this.f_39771_.m_150110_().f_35937_ || $$0.m_150930_(Items.f_42690_)) {
                        $$22 = true;
                    }
                    for (Enchantment $$23 : $$6.keySet()) {
                        if ($$23 == $$19 || $$19.m_44695_($$23)) continue;
                        $$22 = false;
                        ++$$1;
                    }
                    if (!$$22) {
                        $$18 = true;
                        continue;
                    }
                    $$17 = true;
                    if ($$21 > $$19.m_6586_()) {
                        $$21 = $$19.m_6586_();
                    }
                    $$6.put($$19, $$21);
                    int $$24 = 0;
                    switch ($$19.m_44699_()) {
                        case COMMON: {
                            $$24 = 1;
                            break;
                        }
                        case UNCOMMON: {
                            $$24 = 2;
                            break;
                        }
                        case RARE: {
                            $$24 = 4;
                            break;
                        }
                        case VERY_RARE: {
                            $$24 = 8;
                        }
                    }
                    if ($$7) {
                        $$24 = Math.max(1, $$24 / 2);
                    }
                    $$1 += $$24 * $$21;
                    if ($$0.m_41613_() <= 1) continue;
                    $$1 = 40;
                }
                if ($$18 && !$$17) {
                    this.f_39768_.m_6836_(0, ItemStack.f_41583_);
                    this.f_39002_.m_6422_(0);
                    return;
                }
            }
        }
        if (StringUtils.isBlank((CharSequence)this.f_39001_)) {
            if ($$0.m_41788_()) {
                $$3 = 1;
                $$1 += $$3;
                $$4.m_41787_();
            }
        } else if (!this.f_39001_.equals($$0.m_41786_().getString())) {
            $$3 = 1;
            $$1 += $$3;
            $$4.m_41714_(Component.m_237113_(this.f_39001_));
        }
        this.f_39002_.m_6422_($$2 + $$1);
        if ($$1 <= 0) {
            $$4 = ItemStack.f_41583_;
        }
        if ($$3 == $$1 && $$3 > 0 && this.f_39002_.m_6501_() >= 40) {
            this.f_39002_.m_6422_(39);
        }
        if (this.f_39002_.m_6501_() >= 40 && !this.f_39771_.m_150110_().f_35937_) {
            $$4 = ItemStack.f_41583_;
        }
        if (!$$4.m_41619_()) {
            int $$25 = $$4.m_41610_();
            if (!$$5.m_41619_() && $$25 < $$5.m_41610_()) {
                $$25 = $$5.m_41610_();
            }
            if ($$3 != $$1 || $$3 == 0) {
                $$25 = AnvilMenu.m_39025_($$25);
            }
            $$4.m_41742_($$25);
            EnchantmentHelper.m_44865_($$6, $$4);
        }
        this.f_39768_.m_6836_(0, $$4);
        this.m_38946_();
    }

    public static int m_39025_(int p_39026_) {
        return p_39026_ * 2 + 1;
    }

    public void m_39020_(String p_39021_) {
        this.f_39001_ = p_39021_;
        if (this.m_38853_(2).m_6657_()) {
            ItemStack $$1 = this.m_38853_(2).m_7993_();
            if (StringUtils.isBlank((CharSequence)p_39021_)) {
                $$1.m_41787_();
            } else {
                $$1.m_41714_(Component.m_237113_(this.f_39001_));
            }
        }
        this.m_6640_();
    }

    public int m_39028_() {
        return this.f_39002_.m_6501_();
    }
}

