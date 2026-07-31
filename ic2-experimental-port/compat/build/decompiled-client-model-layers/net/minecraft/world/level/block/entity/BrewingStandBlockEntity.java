/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.entity;

import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BrewingStandBlockEntity
extends BaseContainerBlockEntity
implements WorldlyContainer {
    private static final int f_155280_ = 3;
    private static final int f_155281_ = 4;
    private static final int[] f_58972_ = new int[]{3};
    private static final int[] f_58973_ = new int[]{0, 1, 2, 3};
    private static final int[] f_58974_ = new int[]{0, 1, 2, 4};
    public static final int f_155276_ = 20;
    public static final int f_155277_ = 0;
    public static final int f_155278_ = 1;
    public static final int f_155279_ = 2;
    private NonNullList<ItemStack> f_58975_ = NonNullList.m_122780_(5, ItemStack.f_41583_);
    int f_58976_;
    private boolean[] f_58977_;
    private Item f_58978_;
    int f_58979_;
    protected final ContainerData f_58971_ = new ContainerData(){

        @Override
        public int m_6413_(int p_59038_) {
            switch (p_59038_) {
                case 0: {
                    return BrewingStandBlockEntity.this.f_58976_;
                }
                case 1: {
                    return BrewingStandBlockEntity.this.f_58979_;
                }
            }
            return 0;
        }

        @Override
        public void m_8050_(int p_59040_, int p_59041_) {
            switch (p_59040_) {
                case 0: {
                    BrewingStandBlockEntity.this.f_58976_ = p_59041_;
                    break;
                }
                case 1: {
                    BrewingStandBlockEntity.this.f_58979_ = p_59041_;
                }
            }
        }

        @Override
        public int m_6499_() {
            return 2;
        }
    };

    public BrewingStandBlockEntity(BlockPos p_155283_, BlockState p_155284_) {
        super(BlockEntityType.f_58927_, p_155283_, p_155284_);
    }

    @Override
    protected Component m_6820_() {
        return Component.m_237115_("container.brewing");
    }

    @Override
    public int m_6643_() {
        return this.f_58975_.size();
    }

    @Override
    public boolean m_7983_() {
        for (ItemStack $$0 : this.f_58975_) {
            if ($$0.m_41619_()) continue;
            return false;
        }
        return true;
    }

    public static void m_155285_(Level p_155286_, BlockPos p_155287_, BlockState p_155288_, BrewingStandBlockEntity p_155289_) {
        ItemStack $$4 = p_155289_.f_58975_.get(4);
        if (p_155289_.f_58979_ <= 0 && $$4.m_150930_(Items.f_42593_)) {
            p_155289_.f_58979_ = 20;
            $$4.m_41774_(1);
            BrewingStandBlockEntity.m_155232_(p_155286_, p_155287_, p_155288_);
        }
        boolean $$5 = BrewingStandBlockEntity.m_155294_(p_155289_.f_58975_);
        boolean $$6 = p_155289_.f_58976_ > 0;
        ItemStack $$7 = p_155289_.f_58975_.get(3);
        if ($$6) {
            boolean $$8;
            --p_155289_.f_58976_;
            boolean bl = $$8 = p_155289_.f_58976_ == 0;
            if ($$8 && $$5) {
                BrewingStandBlockEntity.m_155290_(p_155286_, p_155287_, p_155289_.f_58975_);
                BrewingStandBlockEntity.m_155232_(p_155286_, p_155287_, p_155288_);
            } else if (!$$5 || !$$7.m_150930_(p_155289_.f_58978_)) {
                p_155289_.f_58976_ = 0;
                BrewingStandBlockEntity.m_155232_(p_155286_, p_155287_, p_155288_);
            }
        } else if ($$5 && p_155289_.f_58979_ > 0) {
            --p_155289_.f_58979_;
            p_155289_.f_58976_ = 400;
            p_155289_.f_58978_ = $$7.m_41720_();
            BrewingStandBlockEntity.m_155232_(p_155286_, p_155287_, p_155288_);
        }
        boolean[] $$9 = p_155289_.m_59029_();
        if (!Arrays.equals($$9, p_155289_.f_58977_)) {
            p_155289_.f_58977_ = $$9;
            BlockState $$10 = p_155288_;
            if (!($$10.m_60734_() instanceof BrewingStandBlock)) {
                return;
            }
            for (int $$11 = 0; $$11 < BrewingStandBlock.f_50905_.length; ++$$11) {
                $$10 = (BlockState)$$10.m_61124_(BrewingStandBlock.f_50905_[$$11], $$9[$$11]);
            }
            p_155286_.m_7731_(p_155287_, $$10, 2);
        }
    }

    private boolean[] m_59029_() {
        boolean[] $$0 = new boolean[3];
        for (int $$1 = 0; $$1 < 3; ++$$1) {
            if (this.f_58975_.get($$1).m_41619_()) continue;
            $$0[$$1] = true;
        }
        return $$0;
    }

    private static boolean m_155294_(NonNullList<ItemStack> p_155295_) {
        ItemStack $$1 = p_155295_.get(3);
        if ($$1.m_41619_()) {
            return false;
        }
        if (!PotionBrewing.m_43506_($$1)) {
            return false;
        }
        for (int $$2 = 0; $$2 < 3; ++$$2) {
            ItemStack $$3 = p_155295_.get($$2);
            if ($$3.m_41619_() || !PotionBrewing.m_43508_($$3, $$1)) continue;
            return true;
        }
        return false;
    }

    private static void m_155290_(Level p_155291_, BlockPos p_155292_, NonNullList<ItemStack> p_155293_) {
        ItemStack $$3 = p_155293_.get(3);
        for (int $$4 = 0; $$4 < 3; ++$$4) {
            p_155293_.set($$4, PotionBrewing.m_43529_($$3, p_155293_.get($$4)));
        }
        $$3.m_41774_(1);
        if ($$3.m_41720_().m_41470_()) {
            ItemStack $$5 = new ItemStack($$3.m_41720_().m_41469_());
            if ($$3.m_41619_()) {
                $$3 = $$5;
            } else {
                Containers.m_18992_(p_155291_, p_155292_.m_123341_(), p_155292_.m_123342_(), p_155292_.m_123343_(), $$5);
            }
        }
        p_155293_.set(3, $$3);
        p_155291_.m_46796_(1035, p_155292_, 0);
    }

    @Override
    public void m_142466_(CompoundTag p_155297_) {
        super.m_142466_(p_155297_);
        this.f_58975_ = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
        ContainerHelper.m_18980_(p_155297_, this.f_58975_);
        this.f_58976_ = p_155297_.m_128448_("BrewTime");
        this.f_58979_ = p_155297_.m_128445_("Fuel");
    }

    @Override
    protected void m_183515_(CompoundTag p_187484_) {
        super.m_183515_(p_187484_);
        p_187484_.m_128376_("BrewTime", (short)this.f_58976_);
        ContainerHelper.m_18973_(p_187484_, this.f_58975_);
        p_187484_.m_128344_("Fuel", (byte)this.f_58979_);
    }

    @Override
    public ItemStack m_8020_(int p_58985_) {
        if (p_58985_ >= 0 && p_58985_ < this.f_58975_.size()) {
            return this.f_58975_.get(p_58985_);
        }
        return ItemStack.f_41583_;
    }

    @Override
    public ItemStack m_7407_(int p_58987_, int p_58988_) {
        return ContainerHelper.m_18969_(this.f_58975_, p_58987_, p_58988_);
    }

    @Override
    public ItemStack m_8016_(int p_59015_) {
        return ContainerHelper.m_18966_(this.f_58975_, p_59015_);
    }

    @Override
    public void m_6836_(int p_58993_, ItemStack p_58994_) {
        if (p_58993_ >= 0 && p_58993_ < this.f_58975_.size()) {
            this.f_58975_.set(p_58993_, p_58994_);
        }
    }

    @Override
    public boolean m_6542_(Player p_59000_) {
        if (this.f_58857_.m_7702_(this.f_58858_) != this) {
            return false;
        }
        return !(p_59000_.m_20275_((double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_() + 0.5, (double)this.f_58858_.m_123343_() + 0.5) > 64.0);
    }

    @Override
    public boolean m_7013_(int p_59017_, ItemStack p_59018_) {
        if (p_59017_ == 3) {
            return PotionBrewing.m_43506_(p_59018_);
        }
        if (p_59017_ == 4) {
            return p_59018_.m_150930_(Items.f_42593_);
        }
        return (p_59018_.m_150930_(Items.f_42589_) || p_59018_.m_150930_(Items.f_42736_) || p_59018_.m_150930_(Items.f_42739_) || p_59018_.m_150930_(Items.f_42590_)) && this.m_8020_(p_59017_).m_41619_();
    }

    @Override
    public int[] m_7071_(Direction p_59010_) {
        if (p_59010_ == Direction.UP) {
            return f_58972_;
        }
        if (p_59010_ == Direction.DOWN) {
            return f_58973_;
        }
        return f_58974_;
    }

    @Override
    public boolean m_7155_(int p_58996_, ItemStack p_58997_, @Nullable Direction p_58998_) {
        return this.m_7013_(p_58996_, p_58997_);
    }

    @Override
    public boolean m_7157_(int p_59020_, ItemStack p_59021_, Direction p_59022_) {
        if (p_59020_ == 3) {
            return p_59021_.m_150930_(Items.f_42590_);
        }
        return true;
    }

    @Override
    public void m_6211_() {
        this.f_58975_.clear();
    }

    @Override
    protected AbstractContainerMenu m_6555_(int p_58990_, Inventory p_58991_) {
        return new BrewingStandMenu(p_58990_, p_58991_, this, this.f_58971_);
    }
}

