/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.inventory;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SmithingMenu
extends ItemCombinerMenu {
    private final Level f_40241_;
    @Nullable
    private UpgradeRecipe f_40242_;
    private final List<UpgradeRecipe> f_40243_;

    public SmithingMenu(int p_40245_, Inventory p_40246_) {
        this(p_40245_, p_40246_, ContainerLevelAccess.f_39287_);
    }

    public SmithingMenu(int p_40248_, Inventory p_40249_, ContainerLevelAccess p_40250_) {
        super(MenuType.f_39977_, p_40248_, p_40249_, p_40250_);
        this.f_40241_ = p_40249_.f_35978_.f_19853_;
        this.f_40243_ = this.f_40241_.m_7465_().m_44013_(RecipeType.f_44113_);
    }

    @Override
    protected boolean m_8039_(BlockState p_40266_) {
        return p_40266_.m_60713_(Blocks.f_50625_);
    }

    @Override
    protected boolean m_6560_(Player p_40268_, boolean p_40269_) {
        return this.f_40242_ != null && this.f_40242_.m_5818_(this.f_39769_, this.f_40241_);
    }

    @Override
    protected void m_142365_(Player p_150663_, ItemStack p_150664_) {
        p_150664_.m_41678_(p_150663_.f_19853_, p_150663_, p_150664_.m_41613_());
        this.f_39768_.m_8015_(p_150663_);
        this.m_40270_(0);
        this.m_40270_(1);
        this.f_39770_.m_39292_((p_40263_, p_40264_) -> p_40263_.m_46796_(1044, (BlockPos)p_40264_, 0));
    }

    private void m_40270_(int p_40271_) {
        ItemStack $$1 = this.f_39769_.m_8020_(p_40271_);
        $$1.m_41774_(1);
        this.f_39769_.m_6836_(p_40271_, $$1);
    }

    @Override
    public void m_6640_() {
        List<UpgradeRecipe> $$0 = this.f_40241_.m_7465_().m_44056_(RecipeType.f_44113_, this.f_39769_, this.f_40241_);
        if ($$0.isEmpty()) {
            this.f_39768_.m_6836_(0, ItemStack.f_41583_);
        } else {
            this.f_40242_ = $$0.get(0);
            ItemStack $$1 = this.f_40242_.m_5874_(this.f_39769_);
            this.f_39768_.m_6029_(this.f_40242_);
            this.f_39768_.m_6836_(0, $$1);
        }
    }

    @Override
    protected boolean m_5861_(ItemStack p_40255_) {
        return this.f_40243_.stream().anyMatch(p_40261_ -> p_40261_.m_44535_(p_40255_));
    }

    @Override
    public boolean m_5882_(ItemStack p_40257_, Slot p_40258_) {
        return p_40258_.f_40218_ != this.f_39768_ && super.m_5882_(p_40257_, p_40258_);
    }
}

