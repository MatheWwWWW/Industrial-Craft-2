/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public abstract class RandomizableContainerBlockEntity
extends BaseContainerBlockEntity {
    public static final String f_155626_ = "LootTable";
    public static final String f_155627_ = "LootTableSeed";
    @Nullable
    protected ResourceLocation f_59605_;
    protected long f_59606_;

    protected RandomizableContainerBlockEntity(BlockEntityType<?> p_155629_, BlockPos p_155630_, BlockState p_155631_) {
        super(p_155629_, p_155630_, p_155631_);
    }

    public static void m_222766_(BlockGetter p_222767_, RandomSource p_222768_, BlockPos p_222769_, ResourceLocation p_222770_) {
        BlockEntity $$4 = p_222767_.m_7702_(p_222769_);
        if ($$4 instanceof RandomizableContainerBlockEntity) {
            ((RandomizableContainerBlockEntity)$$4).m_59626_(p_222770_, p_222768_.m_188505_());
        }
    }

    protected boolean m_59631_(CompoundTag p_59632_) {
        if (p_59632_.m_128425_(f_155626_, 8)) {
            this.f_59605_ = new ResourceLocation(p_59632_.m_128461_(f_155626_));
            this.f_59606_ = p_59632_.m_128454_(f_155627_);
            return true;
        }
        return false;
    }

    protected boolean m_59634_(CompoundTag p_59635_) {
        if (this.f_59605_ == null) {
            return false;
        }
        p_59635_.m_128359_(f_155626_, this.f_59605_.toString());
        if (this.f_59606_ != 0L) {
            p_59635_.m_128356_(f_155627_, this.f_59606_);
        }
        return true;
    }

    public void m_59640_(@Nullable Player p_59641_) {
        if (this.f_59605_ != null && this.f_58857_.m_7654_() != null) {
            LootTable $$1 = this.f_58857_.m_7654_().m_129898_().m_79217_(this.f_59605_);
            if (p_59641_ instanceof ServerPlayer) {
                CriteriaTriggers.f_10563_.m_54597_((ServerPlayer)p_59641_, this.f_59605_);
            }
            this.f_59605_ = null;
            LootContext.Builder $$2 = new LootContext.Builder((ServerLevel)this.f_58857_).m_78972_(LootContextParams.f_81460_, Vec3.m_82512_(this.f_58858_)).m_78965_(this.f_59606_);
            if (p_59641_ != null) {
                $$2.m_78963_(p_59641_.m_36336_()).m_78972_(LootContextParams.f_81455_, p_59641_);
            }
            $$1.m_79123_(this, $$2.m_78975_(LootContextParamSets.f_81411_));
        }
    }

    public void m_59626_(ResourceLocation p_59627_, long p_59628_) {
        this.f_59605_ = p_59627_;
        this.f_59606_ = p_59628_;
    }

    @Override
    public boolean m_7983_() {
        this.m_59640_(null);
        return this.m_7086_().stream().allMatch(ItemStack::m_41619_);
    }

    @Override
    public ItemStack m_8020_(int p_59611_) {
        this.m_59640_(null);
        return this.m_7086_().get(p_59611_);
    }

    @Override
    public ItemStack m_7407_(int p_59613_, int p_59614_) {
        this.m_59640_(null);
        ItemStack $$2 = ContainerHelper.m_18969_(this.m_7086_(), p_59613_, p_59614_);
        if (!$$2.m_41619_()) {
            this.m_6596_();
        }
        return $$2;
    }

    @Override
    public ItemStack m_8016_(int p_59630_) {
        this.m_59640_(null);
        return ContainerHelper.m_18966_(this.m_7086_(), p_59630_);
    }

    @Override
    public void m_6836_(int p_59616_, ItemStack p_59617_) {
        this.m_59640_(null);
        this.m_7086_().set(p_59616_, p_59617_);
        if (p_59617_.m_41613_() > this.m_6893_()) {
            p_59617_.m_41764_(this.m_6893_());
        }
        this.m_6596_();
    }

    @Override
    public boolean m_6542_(Player p_59619_) {
        if (this.f_58857_.m_7702_(this.f_58858_) != this) {
            return false;
        }
        return !(p_59619_.m_20275_((double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_() + 0.5, (double)this.f_58858_.m_123343_() + 0.5) > 64.0);
    }

    @Override
    public void m_6211_() {
        this.m_7086_().clear();
    }

    protected abstract NonNullList<ItemStack> m_7086_();

    protected abstract void m_6520_(NonNullList<ItemStack> var1);

    @Override
    public boolean m_7525_(Player p_59643_) {
        return super.m_7525_(p_59643_) && (this.f_59605_ == null || !p_59643_.m_5833_());
    }

    @Override
    @Nullable
    public AbstractContainerMenu m_7208_(int p_59637_, Inventory p_59638_, Player p_59639_) {
        if (this.m_7525_(p_59639_)) {
            this.m_59640_(p_59638_.f_35978_);
            return this.m_6555_(p_59637_, p_59638_);
        }
        return null;
    }
}

