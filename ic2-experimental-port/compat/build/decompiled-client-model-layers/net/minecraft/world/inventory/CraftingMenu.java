/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import java.util.Optional;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class CraftingMenu
extends RecipeBookMenu<CraftingContainer> {
    public static final int f_150539_ = 0;
    private static final int f_150540_ = 1;
    private static final int f_150541_ = 10;
    private static final int f_150542_ = 10;
    private static final int f_150543_ = 37;
    private static final int f_150544_ = 37;
    private static final int f_150545_ = 46;
    private final CraftingContainer f_39348_ = new CraftingContainer(this, 3, 3);
    private final ResultContainer f_39349_ = new ResultContainer();
    private final ContainerLevelAccess f_39350_;
    private final Player f_39351_;

    public CraftingMenu(int p_39353_, Inventory p_39354_) {
        this(p_39353_, p_39354_, ContainerLevelAccess.f_39287_);
    }

    public CraftingMenu(int p_39356_, Inventory p_39357_, ContainerLevelAccess p_39358_) {
        super(MenuType.f_39968_, p_39356_);
        this.f_39350_ = p_39358_;
        this.f_39351_ = p_39357_.f_35978_;
        this.m_38897_(new ResultSlot(p_39357_.f_35978_, this.f_39348_, this.f_39349_, 0, 124, 35));
        for (int $$3 = 0; $$3 < 3; ++$$3) {
            for (int $$4 = 0; $$4 < 3; ++$$4) {
                this.m_38897_(new Slot(this.f_39348_, $$4 + $$3 * 3, 30 + $$4 * 18, 17 + $$3 * 18));
            }
        }
        for (int $$5 = 0; $$5 < 3; ++$$5) {
            for (int $$6 = 0; $$6 < 9; ++$$6) {
                this.m_38897_(new Slot(p_39357_, $$6 + $$5 * 9 + 9, 8 + $$6 * 18, 84 + $$5 * 18));
            }
        }
        for (int $$7 = 0; $$7 < 9; ++$$7) {
            this.m_38897_(new Slot(p_39357_, $$7, 8 + $$7 * 18, 142));
        }
    }

    protected static void m_150546_(AbstractContainerMenu p_150547_, Level p_150548_, Player p_150549_, CraftingContainer p_150550_, ResultContainer p_150551_) {
        CraftingRecipe $$8;
        if (p_150548_.f_46443_) {
            return;
        }
        ServerPlayer $$5 = (ServerPlayer)p_150549_;
        ItemStack $$6 = ItemStack.f_41583_;
        Optional<CraftingRecipe> $$7 = p_150548_.m_7654_().m_129894_().m_44015_(RecipeType.f_44107_, p_150550_, p_150548_);
        if ($$7.isPresent() && p_150551_.m_40135_(p_150548_, $$5, $$8 = $$7.get())) {
            $$6 = $$8.m_5874_(p_150550_);
        }
        p_150551_.m_6836_(0, $$6);
        p_150547_.m_150404_(0, $$6);
        $$5.f_8906_.m_9829_(new ClientboundContainerSetSlotPacket(p_150547_.f_38840_, p_150547_.m_182425_(), 0, $$6));
    }

    @Override
    public void m_6199_(Container p_39366_) {
        this.f_39350_.m_39292_((p_39386_, p_39387_) -> CraftingMenu.m_150546_(this, p_39386_, this.f_39351_, this.f_39348_, this.f_39349_));
    }

    @Override
    public void m_5816_(StackedContents p_39374_) {
        this.f_39348_.m_5809_(p_39374_);
    }

    @Override
    public void m_6650_() {
        this.f_39348_.m_6211_();
        this.f_39349_.m_6211_();
    }

    @Override
    public boolean m_6032_(Recipe<? super CraftingContainer> p_39384_) {
        return p_39384_.m_5818_(this.f_39348_, this.f_39351_.f_19853_);
    }

    @Override
    public void m_6877_(Player p_39389_) {
        super.m_6877_(p_39389_);
        this.f_39350_.m_39292_((p_39371_, p_39372_) -> this.m_150411_(p_39389_, this.f_39348_));
    }

    @Override
    public boolean m_6875_(Player p_39368_) {
        return CraftingMenu.m_38889_(this.f_39350_, p_39368_, Blocks.f_50091_);
    }

    @Override
    public ItemStack m_7648_(Player p_39391_, int p_39392_) {
        ItemStack $$2 = ItemStack.f_41583_;
        Slot $$3 = (Slot)this.f_38839_.get(p_39392_);
        if ($$3 != null && $$3.m_6657_()) {
            ItemStack $$4 = $$3.m_7993_();
            $$2 = $$4.m_41777_();
            if (p_39392_ == 0) {
                this.f_39350_.m_39292_((p_39378_, p_39379_) -> $$4.m_41720_().m_7836_($$4, (Level)p_39378_, p_39391_));
                if (!this.m_38903_($$4, 10, 46, true)) {
                    return ItemStack.f_41583_;
                }
                $$3.m_40234_($$4, $$2);
            } else if (p_39392_ >= 10 && p_39392_ < 46 ? !this.m_38903_($$4, 1, 10, false) && (p_39392_ < 37 ? !this.m_38903_($$4, 37, 46, false) : !this.m_38903_($$4, 10, 37, false)) : !this.m_38903_($$4, 10, 46, false)) {
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
            $$3.m_142406_(p_39391_, $$4);
            if (p_39392_ == 0) {
                p_39391_.m_36176_($$4, false);
            }
        }
        return $$2;
    }

    @Override
    public boolean m_5882_(ItemStack p_39381_, Slot p_39382_) {
        return p_39382_.f_40218_ != this.f_39349_ && super.m_5882_(p_39381_, p_39382_);
    }

    @Override
    public int m_6636_() {
        return 0;
    }

    @Override
    public int m_6635_() {
        return this.f_39348_.m_39347_();
    }

    @Override
    public int m_6656_() {
        return this.f_39348_.m_39346_();
    }

    @Override
    public int m_6653_() {
        return 10;
    }

    @Override
    public RecipeBookType m_5867_() {
        return RecipeBookType.CRAFTING;
    }

    @Override
    public boolean m_142157_(int p_150553_) {
        return p_150553_ != this.m_6636_();
    }
}

