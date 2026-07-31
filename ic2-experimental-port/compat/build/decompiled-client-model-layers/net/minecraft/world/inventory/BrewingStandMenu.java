/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;

public class BrewingStandMenu
extends AbstractContainerMenu {
    private static final int f_150488_ = 0;
    private static final int f_150489_ = 2;
    private static final int f_150490_ = 3;
    private static final int f_150491_ = 4;
    private static final int f_150492_ = 5;
    private static final int f_150493_ = 2;
    private static final int f_150494_ = 5;
    private static final int f_150495_ = 32;
    private static final int f_150496_ = 32;
    private static final int f_150497_ = 41;
    private final Container f_39086_;
    private final ContainerData f_39087_;
    private final Slot f_39088_;

    public BrewingStandMenu(int p_39090_, Inventory p_39091_) {
        this(p_39090_, p_39091_, new SimpleContainer(5), new SimpleContainerData(2));
    }

    public BrewingStandMenu(int p_39093_, Inventory p_39094_, Container p_39095_, ContainerData p_39096_) {
        super(MenuType.f_39967_, p_39093_);
        BrewingStandMenu.m_38869_(p_39095_, 5);
        BrewingStandMenu.m_38886_(p_39096_, 2);
        this.f_39086_ = p_39095_;
        this.f_39087_ = p_39096_;
        this.m_38897_(new PotionSlot(p_39095_, 0, 56, 51));
        this.m_38897_(new PotionSlot(p_39095_, 1, 79, 58));
        this.m_38897_(new PotionSlot(p_39095_, 2, 102, 51));
        this.f_39088_ = this.m_38897_(new IngredientsSlot(p_39095_, 3, 79, 17));
        this.m_38897_(new FuelSlot(p_39095_, 4, 17, 17));
        this.m_38884_(p_39096_);
        for (int $$4 = 0; $$4 < 3; ++$$4) {
            for (int $$5 = 0; $$5 < 9; ++$$5) {
                this.m_38897_(new Slot(p_39094_, $$5 + $$4 * 9 + 9, 8 + $$5 * 18, 84 + $$4 * 18));
            }
        }
        for (int $$6 = 0; $$6 < 9; ++$$6) {
            this.m_38897_(new Slot(p_39094_, $$6, 8 + $$6 * 18, 142));
        }
    }

    @Override
    public boolean m_6875_(Player p_39098_) {
        return this.f_39086_.m_6542_(p_39098_);
    }

    @Override
    public ItemStack m_7648_(Player p_39100_, int p_39101_) {
        ItemStack $$2 = ItemStack.f_41583_;
        Slot $$3 = (Slot)this.f_38839_.get(p_39101_);
        if ($$3 != null && $$3.m_6657_()) {
            ItemStack $$4 = $$3.m_7993_();
            $$2 = $$4.m_41777_();
            if (p_39101_ >= 0 && p_39101_ <= 2 || p_39101_ == 3 || p_39101_ == 4) {
                if (!this.m_38903_($$4, 5, 41, true)) {
                    return ItemStack.f_41583_;
                }
                $$3.m_40234_($$4, $$2);
            } else if (FuelSlot.m_39112_($$2) ? this.m_38903_($$4, 4, 5, false) || this.f_39088_.m_5857_($$4) && !this.m_38903_($$4, 3, 4, false) : (this.f_39088_.m_5857_($$4) ? !this.m_38903_($$4, 3, 4, false) : (PotionSlot.m_39133_($$2) && $$2.m_41613_() == 1 ? !this.m_38903_($$4, 0, 3, false) : (p_39101_ >= 5 && p_39101_ < 32 ? !this.m_38903_($$4, 32, 41, false) : (p_39101_ >= 32 && p_39101_ < 41 ? !this.m_38903_($$4, 5, 32, false) : !this.m_38903_($$4, 5, 41, false)))))) {
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
            $$3.m_142406_(p_39100_, $$4);
        }
        return $$2;
    }

    public int m_39102_() {
        return this.f_39087_.m_6413_(1);
    }

    public int m_39103_() {
        return this.f_39087_.m_6413_(0);
    }

    static class PotionSlot
    extends Slot {
        public PotionSlot(Container p_39123_, int p_39124_, int p_39125_, int p_39126_) {
            super(p_39123_, p_39124_, p_39125_, p_39126_);
        }

        @Override
        public boolean m_5857_(ItemStack p_39132_) {
            return PotionSlot.m_39133_(p_39132_);
        }

        @Override
        public int m_6641_() {
            return 1;
        }

        @Override
        public void m_142406_(Player p_150499_, ItemStack p_150500_) {
            Potion $$2 = PotionUtils.m_43579_(p_150500_);
            if (p_150499_ instanceof ServerPlayer) {
                CriteriaTriggers.f_10577_.m_19120_((ServerPlayer)p_150499_, $$2);
            }
            super.m_142406_(p_150499_, p_150500_);
        }

        public static boolean m_39133_(ItemStack p_39134_) {
            return p_39134_.m_150930_(Items.f_42589_) || p_39134_.m_150930_(Items.f_42736_) || p_39134_.m_150930_(Items.f_42739_) || p_39134_.m_150930_(Items.f_42590_);
        }
    }

    static class IngredientsSlot
    extends Slot {
        public IngredientsSlot(Container p_39115_, int p_39116_, int p_39117_, int p_39118_) {
            super(p_39115_, p_39116_, p_39117_, p_39118_);
        }

        @Override
        public boolean m_5857_(ItemStack p_39121_) {
            return PotionBrewing.m_43506_(p_39121_);
        }

        @Override
        public int m_6641_() {
            return 64;
        }
    }

    static class FuelSlot
    extends Slot {
        public FuelSlot(Container p_39105_, int p_39106_, int p_39107_, int p_39108_) {
            super(p_39105_, p_39106_, p_39107_, p_39108_);
        }

        @Override
        public boolean m_5857_(ItemStack p_39111_) {
            return FuelSlot.m_39112_(p_39111_);
        }

        public static boolean m_39112_(ItemStack p_39113_) {
            return p_39113_.m_150930_(Items.f_42593_);
        }

        @Override
        public int m_6641_() {
            return 64;
        }
    }
}

