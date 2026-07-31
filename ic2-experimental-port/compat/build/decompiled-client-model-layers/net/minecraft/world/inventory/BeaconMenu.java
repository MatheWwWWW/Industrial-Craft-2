/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.inventory;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class BeaconMenu
extends AbstractContainerMenu {
    private static final int f_150481_ = 0;
    private static final int f_150482_ = 1;
    private static final int f_150483_ = 3;
    private static final int f_150484_ = 1;
    private static final int f_150485_ = 28;
    private static final int f_150486_ = 28;
    private static final int f_150487_ = 37;
    private final Container f_39031_ = new SimpleContainer(1){

        @Override
        public boolean m_7013_(int p_39066_, ItemStack p_39067_) {
            return p_39067_.m_204117_(ItemTags.f_13164_);
        }

        @Override
        public int m_6893_() {
            return 1;
        }
    };
    private final PaymentSlot f_39032_;
    private final ContainerLevelAccess f_39033_;
    private final ContainerData f_39034_;

    public BeaconMenu(int p_39036_, Container p_39037_) {
        this(p_39036_, p_39037_, new SimpleContainerData(3), ContainerLevelAccess.f_39287_);
    }

    public BeaconMenu(int p_39039_, Container p_39040_, ContainerData p_39041_, ContainerLevelAccess p_39042_) {
        super(MenuType.f_39965_, p_39039_);
        BeaconMenu.m_38886_(p_39041_, 3);
        this.f_39034_ = p_39041_;
        this.f_39033_ = p_39042_;
        this.f_39032_ = new PaymentSlot(this.f_39031_, 0, 136, 110);
        this.m_38897_(this.f_39032_);
        this.m_38884_(p_39041_);
        int $$4 = 36;
        int $$5 = 137;
        for (int $$6 = 0; $$6 < 3; ++$$6) {
            for (int $$7 = 0; $$7 < 9; ++$$7) {
                this.m_38897_(new Slot(p_39040_, $$7 + $$6 * 9 + 9, 36 + $$7 * 18, 137 + $$6 * 18));
            }
        }
        for (int $$8 = 0; $$8 < 9; ++$$8) {
            this.m_38897_(new Slot(p_39040_, $$8, 36 + $$8 * 18, 195));
        }
    }

    @Override
    public void m_6877_(Player p_39049_) {
        super.m_6877_(p_39049_);
        if (p_39049_.f_19853_.f_46443_) {
            return;
        }
        ItemStack $$1 = this.f_39032_.m_6201_(this.f_39032_.m_6641_());
        if (!$$1.m_41619_()) {
            p_39049_.m_36176_($$1, false);
        }
    }

    @Override
    public boolean m_6875_(Player p_39047_) {
        return BeaconMenu.m_38889_(this.f_39033_, p_39047_, Blocks.f_50273_);
    }

    @Override
    public void m_7511_(int p_39044_, int p_39045_) {
        super.m_7511_(p_39044_, p_39045_);
        this.m_38946_();
    }

    @Override
    public ItemStack m_7648_(Player p_39051_, int p_39052_) {
        ItemStack $$2 = ItemStack.f_41583_;
        Slot $$3 = (Slot)this.f_38839_.get(p_39052_);
        if ($$3 != null && $$3.m_6657_()) {
            ItemStack $$4 = $$3.m_7993_();
            $$2 = $$4.m_41777_();
            if (p_39052_ == 0) {
                if (!this.m_38903_($$4, 1, 37, true)) {
                    return ItemStack.f_41583_;
                }
                $$3.m_40234_($$4, $$2);
            } else if (!this.f_39032_.m_6657_() && this.f_39032_.m_5857_($$4) && $$4.m_41613_() == 1 ? !this.m_38903_($$4, 0, 1, false) : (p_39052_ >= 1 && p_39052_ < 28 ? !this.m_38903_($$4, 28, 37, false) : (p_39052_ >= 28 && p_39052_ < 37 ? !this.m_38903_($$4, 1, 28, false) : !this.m_38903_($$4, 1, 37, false)))) {
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
            $$3.m_142406_(p_39051_, $$4);
        }
        return $$2;
    }

    public int m_39056_() {
        return this.f_39034_.m_6413_(0);
    }

    @Nullable
    public MobEffect m_39057_() {
        return MobEffect.m_19453_(this.f_39034_.m_6413_(1));
    }

    @Nullable
    public MobEffect m_39058_() {
        return MobEffect.m_19453_(this.f_39034_.m_6413_(2));
    }

    public void m_219972_(Optional<MobEffect> p_219973_, Optional<MobEffect> p_219974_) {
        if (this.f_39032_.m_6657_()) {
            this.f_39034_.m_8050_(1, p_219973_.map(MobEffect::m_19459_).orElse(-1));
            this.f_39034_.m_8050_(2, p_219974_.map(MobEffect::m_19459_).orElse(-1));
            this.f_39032_.m_6201_(1);
            this.f_39033_.m_39292_(Level::m_151543_);
        }
    }

    public boolean m_39059_() {
        return !this.f_39031_.m_8020_(0).m_41619_();
    }

    class PaymentSlot
    extends Slot {
        public PaymentSlot(Container p_39071_, int p_39072_, int p_39073_, int p_39074_) {
            super(p_39071_, p_39072_, p_39073_, p_39074_);
        }

        @Override
        public boolean m_5857_(ItemStack p_39077_) {
            return p_39077_.m_204117_(ItemTags.f_13164_);
        }

        @Override
        public int m_6641_() {
            return 1;
        }
    }
}

