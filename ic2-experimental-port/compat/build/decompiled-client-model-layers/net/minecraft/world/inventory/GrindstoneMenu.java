/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class GrindstoneMenu
extends AbstractContainerMenu {
    public static final int f_150565_ = 35;
    public static final int f_150566_ = 0;
    public static final int f_150567_ = 1;
    public static final int f_150568_ = 2;
    private static final int f_150569_ = 3;
    private static final int f_150570_ = 30;
    private static final int f_150571_ = 30;
    private static final int f_150572_ = 39;
    private final Container f_39559_ = new ResultContainer();
    final Container f_39560_ = new SimpleContainer(2){

        @Override
        public void m_6596_() {
            super.m_6596_();
            GrindstoneMenu.this.m_6199_(this);
        }
    };
    private final ContainerLevelAccess f_39561_;

    public GrindstoneMenu(int p_39563_, Inventory p_39564_) {
        this(p_39563_, p_39564_, ContainerLevelAccess.f_39287_);
    }

    public GrindstoneMenu(int p_39566_, Inventory p_39567_, final ContainerLevelAccess p_39568_) {
        super(MenuType.f_39971_, p_39566_);
        this.f_39561_ = p_39568_;
        this.m_38897_(new Slot(this.f_39560_, 0, 49, 19){

            @Override
            public boolean m_5857_(ItemStack p_39607_) {
                return p_39607_.m_41763_() || p_39607_.m_150930_(Items.f_42690_) || p_39607_.m_41793_();
            }
        });
        this.m_38897_(new Slot(this.f_39560_, 1, 49, 40){

            @Override
            public boolean m_5857_(ItemStack p_39616_) {
                return p_39616_.m_41763_() || p_39616_.m_150930_(Items.f_42690_) || p_39616_.m_41793_();
            }
        });
        this.m_38897_(new Slot(this.f_39559_, 2, 129, 34){

            @Override
            public boolean m_5857_(ItemStack p_39630_) {
                return false;
            }

            @Override
            public void m_142406_(Player p_150574_, ItemStack p_150575_) {
                p_39568_.m_39292_((p_39634_, p_39635_) -> {
                    if (p_39634_ instanceof ServerLevel) {
                        ExperienceOrb.m_147082_((ServerLevel)p_39634_, Vec3.m_82512_(p_39635_), this.m_39631_((Level)p_39634_));
                    }
                    p_39634_.m_46796_(1042, (BlockPos)p_39635_, 0);
                });
                GrindstoneMenu.this.f_39560_.m_6836_(0, ItemStack.f_41583_);
                GrindstoneMenu.this.f_39560_.m_6836_(1, ItemStack.f_41583_);
            }

            private int m_39631_(Level p_39632_) {
                int $$1 = 0;
                $$1 += this.m_39636_(GrindstoneMenu.this.f_39560_.m_8020_(0));
                if (($$1 += this.m_39636_(GrindstoneMenu.this.f_39560_.m_8020_(1))) > 0) {
                    int $$2 = (int)Math.ceil((double)$$1 / 2.0);
                    return $$2 + p_39632_.f_46441_.m_188503_($$2);
                }
                return 0;
            }

            private int m_39636_(ItemStack p_39637_) {
                int $$1 = 0;
                Map<Enchantment, Integer> $$2 = EnchantmentHelper.m_44831_(p_39637_);
                for (Map.Entry<Enchantment, Integer> $$3 : $$2.entrySet()) {
                    Enchantment $$4 = $$3.getKey();
                    Integer $$5 = $$3.getValue();
                    if ($$4.m_6589_()) continue;
                    $$1 += $$4.m_6183_($$5);
                }
                return $$1;
            }
        });
        for (int $$3 = 0; $$3 < 3; ++$$3) {
            for (int $$4 = 0; $$4 < 9; ++$$4) {
                this.m_38897_(new Slot(p_39567_, $$4 + $$3 * 9 + 9, 8 + $$4 * 18, 84 + $$3 * 18));
            }
        }
        for (int $$5 = 0; $$5 < 9; ++$$5) {
            this.m_38897_(new Slot(p_39567_, $$5, 8 + $$5 * 18, 142));
        }
    }

    @Override
    public void m_6199_(Container p_39570_) {
        super.m_6199_(p_39570_);
        if (p_39570_ == this.f_39560_) {
            this.m_39593_();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void m_39593_() {
        boolean $$3;
        ItemStack $$0 = this.f_39560_.m_8020_(0);
        ItemStack $$1 = this.f_39560_.m_8020_(1);
        boolean $$2 = !$$0.m_41619_() || !$$1.m_41619_();
        boolean bl = $$3 = !$$0.m_41619_() && !$$1.m_41619_();
        if ($$2) {
            ItemStack $$14;
            int $$13;
            boolean $$4;
            boolean bl2 = $$4 = !$$0.m_41619_() && !$$0.m_150930_(Items.f_42690_) && !$$0.m_41793_() || !$$1.m_41619_() && !$$1.m_150930_(Items.f_42690_) && !$$1.m_41793_();
            if ($$0.m_41613_() > 1 || $$1.m_41613_() > 1 || !$$3 && $$4) {
                this.f_39559_.m_6836_(0, ItemStack.f_41583_);
                this.m_38946_();
                return;
            }
            int $$5 = 1;
            if ($$3) {
                if (!$$0.m_150930_($$1.m_41720_())) {
                    this.f_39559_.m_6836_(0, ItemStack.f_41583_);
                    this.m_38946_();
                    return;
                }
                Item $$6 = $$0.m_41720_();
                int $$7 = $$6.m_41462_() - $$0.m_41773_();
                int $$8 = $$6.m_41462_() - $$1.m_41773_();
                int $$9 = $$7 + $$8 + $$6.m_41462_() * 5 / 100;
                int $$10 = Math.max($$6.m_41462_() - $$9, 0);
                ItemStack $$11 = this.m_39590_($$0, $$1);
                if (!$$11.m_41763_()) {
                    if (!ItemStack.m_41728_($$0, $$1)) {
                        this.f_39559_.m_6836_(0, ItemStack.f_41583_);
                        this.m_38946_();
                        return;
                    }
                    $$5 = 2;
                }
            } else {
                boolean $$12 = !$$0.m_41619_();
                $$13 = $$12 ? $$0.m_41773_() : $$1.m_41773_();
                $$14 = $$12 ? $$0 : $$1;
            }
            this.f_39559_.m_6836_(0, this.m_39579_($$14, $$13, $$5));
        } else {
            this.f_39559_.m_6836_(0, ItemStack.f_41583_);
        }
        this.m_38946_();
    }

    private ItemStack m_39590_(ItemStack p_39591_, ItemStack p_39592_) {
        ItemStack $$2 = p_39591_.m_41777_();
        Map<Enchantment, Integer> $$3 = EnchantmentHelper.m_44831_(p_39592_);
        for (Map.Entry<Enchantment, Integer> $$4 : $$3.entrySet()) {
            Enchantment $$5 = $$4.getKey();
            if ($$5.m_6589_() && EnchantmentHelper.m_44843_($$5, $$2) != 0) continue;
            $$2.m_41663_($$5, $$4.getValue());
        }
        return $$2;
    }

    private ItemStack m_39579_(ItemStack p_39580_, int p_39581_, int p_39582_) {
        ItemStack $$3 = p_39580_.m_41777_();
        $$3.m_41749_("Enchantments");
        $$3.m_41749_("StoredEnchantments");
        if (p_39581_ > 0) {
            $$3.m_41721_(p_39581_);
        } else {
            $$3.m_41749_("Damage");
        }
        $$3.m_41764_(p_39582_);
        Map<Enchantment, Integer> $$4 = EnchantmentHelper.m_44831_(p_39580_).entrySet().stream().filter(p_39584_ -> ((Enchantment)p_39584_.getKey()).m_6589_()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        EnchantmentHelper.m_44865_($$4, $$3);
        $$3.m_41742_(0);
        if ($$3.m_150930_(Items.f_42690_) && $$4.size() == 0) {
            $$3 = new ItemStack(Items.f_42517_);
            if (p_39580_.m_41788_()) {
                $$3.m_41714_(p_39580_.m_41786_());
            }
        }
        for (int $$5 = 0; $$5 < $$4.size(); ++$$5) {
            $$3.m_41742_(AnvilMenu.m_39025_($$3.m_41610_()));
        }
        return $$3;
    }

    @Override
    public void m_6877_(Player p_39586_) {
        super.m_6877_(p_39586_);
        this.f_39561_.m_39292_((p_39575_, p_39576_) -> this.m_150411_(p_39586_, this.f_39560_));
    }

    @Override
    public boolean m_6875_(Player p_39572_) {
        return GrindstoneMenu.m_38889_(this.f_39561_, p_39572_, Blocks.f_50623_);
    }

    @Override
    public ItemStack m_7648_(Player p_39588_, int p_39589_) {
        ItemStack $$2 = ItemStack.f_41583_;
        Slot $$3 = (Slot)this.f_38839_.get(p_39589_);
        if ($$3 != null && $$3.m_6657_()) {
            ItemStack $$4 = $$3.m_7993_();
            $$2 = $$4.m_41777_();
            ItemStack $$5 = this.f_39560_.m_8020_(0);
            ItemStack $$6 = this.f_39560_.m_8020_(1);
            if (p_39589_ == 2) {
                if (!this.m_38903_($$4, 3, 39, true)) {
                    return ItemStack.f_41583_;
                }
                $$3.m_40234_($$4, $$2);
            } else if (p_39589_ == 0 || p_39589_ == 1 ? !this.m_38903_($$4, 3, 39, false) : ($$5.m_41619_() || $$6.m_41619_() ? !this.m_38903_($$4, 0, 2, false) : (p_39589_ >= 3 && p_39589_ < 30 ? !this.m_38903_($$4, 30, 39, false) : p_39589_ >= 30 && p_39589_ < 39 && !this.m_38903_($$4, 3, 30, false)))) {
                return ItemStack.f_41583_;
            }
            if ($$4.m_41619_()) {
                $$3.m_5852_(ItemStack.f_41583_);
            } else {
                $$3.m_6654_();
            }
            if ($$4.m_41613_() == $$2.m_41613_()) {
                return ItemStack.f_41583_;
            }
            $$3.m_142406_(p_39588_, $$4);
        }
        return $$2;
    }
}

