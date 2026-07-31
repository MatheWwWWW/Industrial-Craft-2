/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public abstract class AbstractFurnaceMenu
extends RecipeBookMenu<Container> {
    public static final int f_150453_ = 0;
    public static final int f_150454_ = 1;
    public static final int f_150455_ = 2;
    public static final int f_150456_ = 3;
    public static final int f_150457_ = 4;
    private static final int f_150458_ = 3;
    private static final int f_150459_ = 30;
    private static final int f_150460_ = 30;
    private static final int f_150461_ = 39;
    private final Container f_38955_;
    private final ContainerData f_38956_;
    protected final Level f_38954_;
    private final RecipeType<? extends AbstractCookingRecipe> f_38957_;
    private final RecipeBookType f_38958_;

    protected AbstractFurnaceMenu(MenuType<?> p_38960_, RecipeType<? extends AbstractCookingRecipe> p_38961_, RecipeBookType p_38962_, int p_38963_, Inventory p_38964_) {
        this(p_38960_, p_38961_, p_38962_, p_38963_, p_38964_, new SimpleContainer(3), new SimpleContainerData(4));
    }

    protected AbstractFurnaceMenu(MenuType<?> p_38966_, RecipeType<? extends AbstractCookingRecipe> p_38967_, RecipeBookType p_38968_, int p_38969_, Inventory p_38970_, Container p_38971_, ContainerData p_38972_) {
        super(p_38966_, p_38969_);
        this.f_38957_ = p_38967_;
        this.f_38958_ = p_38968_;
        AbstractFurnaceMenu.m_38869_(p_38971_, 3);
        AbstractFurnaceMenu.m_38886_(p_38972_, 4);
        this.f_38955_ = p_38971_;
        this.f_38956_ = p_38972_;
        this.f_38954_ = p_38970_.f_35978_.f_19853_;
        this.m_38897_(new Slot(p_38971_, 0, 56, 17));
        this.m_38897_(new FurnaceFuelSlot(this, p_38971_, 1, 56, 53));
        this.m_38897_(new FurnaceResultSlot(p_38970_.f_35978_, p_38971_, 2, 116, 35));
        for (int $$7 = 0; $$7 < 3; ++$$7) {
            for (int $$8 = 0; $$8 < 9; ++$$8) {
                this.m_38897_(new Slot(p_38970_, $$8 + $$7 * 9 + 9, 8 + $$8 * 18, 84 + $$7 * 18));
            }
        }
        for (int $$9 = 0; $$9 < 9; ++$$9) {
            this.m_38897_(new Slot(p_38970_, $$9, 8 + $$9 * 18, 142));
        }
        this.m_38884_(p_38972_);
    }

    @Override
    public void m_5816_(StackedContents p_38976_) {
        if (this.f_38955_ instanceof StackedContentsCompatible) {
            ((StackedContentsCompatible)((Object)this.f_38955_)).m_5809_(p_38976_);
        }
    }

    @Override
    public void m_6650_() {
        this.m_38853_(0).m_5852_(ItemStack.f_41583_);
        this.m_38853_(2).m_5852_(ItemStack.f_41583_);
    }

    @Override
    public boolean m_6032_(Recipe<? super Container> p_38980_) {
        return p_38980_.m_5818_(this.f_38955_, this.f_38954_);
    }

    @Override
    public int m_6636_() {
        return 2;
    }

    @Override
    public int m_6635_() {
        return 1;
    }

    @Override
    public int m_6656_() {
        return 1;
    }

    @Override
    public int m_6653_() {
        return 3;
    }

    @Override
    public boolean m_6875_(Player p_38974_) {
        return this.f_38955_.m_6542_(p_38974_);
    }

    @Override
    public ItemStack m_7648_(Player p_38986_, int p_38987_) {
        ItemStack $$2 = ItemStack.f_41583_;
        Slot $$3 = (Slot)this.f_38839_.get(p_38987_);
        if ($$3 != null && $$3.m_6657_()) {
            ItemStack $$4 = $$3.m_7993_();
            $$2 = $$4.m_41777_();
            if (p_38987_ == 2) {
                if (!this.m_38903_($$4, 3, 39, true)) {
                    return ItemStack.f_41583_;
                }
                $$3.m_40234_($$4, $$2);
            } else if (p_38987_ == 1 || p_38987_ == 0 ? !this.m_38903_($$4, 3, 39, false) : (this.m_38977_($$4) ? !this.m_38903_($$4, 0, 1, false) : (this.m_38988_($$4) ? !this.m_38903_($$4, 1, 2, false) : (p_38987_ >= 3 && p_38987_ < 30 ? !this.m_38903_($$4, 30, 39, false) : p_38987_ >= 30 && p_38987_ < 39 && !this.m_38903_($$4, 3, 30, false))))) {
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
            $$3.m_142406_(p_38986_, $$4);
        }
        return $$2;
    }

    protected boolean m_38977_(ItemStack p_38978_) {
        return this.f_38954_.m_7465_().m_44015_(this.f_38957_, new SimpleContainer(p_38978_), this.f_38954_).isPresent();
    }

    protected boolean m_38988_(ItemStack p_38989_) {
        return AbstractFurnaceBlockEntity.m_58399_(p_38989_);
    }

    public int m_38995_() {
        int $$0 = this.f_38956_.m_6413_(2);
        int $$1 = this.f_38956_.m_6413_(3);
        if ($$1 == 0 || $$0 == 0) {
            return 0;
        }
        return $$0 * 24 / $$1;
    }

    public int m_38996_() {
        int $$0 = this.f_38956_.m_6413_(1);
        if ($$0 == 0) {
            $$0 = 200;
        }
        return this.f_38956_.m_6413_(0) * 13 / $$0;
    }

    public boolean m_38997_() {
        return this.f_38956_.m_6413_(0) > 0;
    }

    @Override
    public RecipeBookType m_5867_() {
        return this.f_38958_;
    }

    @Override
    public boolean m_142157_(int p_150463_) {
        return p_150463_ != 1;
    }
}

