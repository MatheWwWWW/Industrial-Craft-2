/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.entity;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class CampfireBlockEntity
extends BlockEntity
implements Clearable {
    private static final int f_155298_ = 2;
    private static final int f_155299_ = 4;
    private final NonNullList<ItemStack> f_59042_ = NonNullList.m_122780_(4, ItemStack.f_41583_);
    private final int[] f_59043_ = new int[4];
    private final int[] f_59044_ = new int[4];
    private final RecipeManager.CachedCheck<Container, CampfireCookingRecipe> f_222760_ = RecipeManager.m_220267_(RecipeType.f_44111_);

    public CampfireBlockEntity(BlockPos p_155301_, BlockState p_155302_) {
        super(BlockEntityType.f_58911_, p_155301_, p_155302_);
    }

    public static void m_155306_(Level p_155307_, BlockPos p_155308_, BlockState p_155309_, CampfireBlockEntity p_155310_) {
        boolean $$4 = false;
        for (int $$5 = 0; $$5 < p_155310_.f_59042_.size(); ++$$5) {
            ItemStack $$6 = p_155310_.f_59042_.get($$5);
            if ($$6.m_41619_()) continue;
            $$4 = true;
            int n = $$5;
            p_155310_.f_59043_[n] = p_155310_.f_59043_[n] + 1;
            if (p_155310_.f_59043_[$$5] < p_155310_.f_59044_[$$5]) continue;
            SimpleContainer $$7 = new SimpleContainer($$6);
            ItemStack $$8 = p_155310_.f_222760_.m_213657_($$7, p_155307_).map(p_155305_ -> p_155305_.m_5874_($$7)).orElse($$6);
            Containers.m_18992_(p_155307_, p_155308_.m_123341_(), p_155308_.m_123342_(), p_155308_.m_123343_(), $$8);
            p_155310_.f_59042_.set($$5, ItemStack.f_41583_);
            p_155307_.m_7260_(p_155308_, p_155309_, p_155309_, 3);
            p_155307_.m_220407_(GameEvent.f_157792_, p_155308_, GameEvent.Context.m_223722_(p_155309_));
        }
        if ($$4) {
            CampfireBlockEntity.m_155232_(p_155307_, p_155308_, p_155309_);
        }
    }

    public static void m_155313_(Level p_155314_, BlockPos p_155315_, BlockState p_155316_, CampfireBlockEntity p_155317_) {
        boolean $$4 = false;
        for (int $$5 = 0; $$5 < p_155317_.f_59042_.size(); ++$$5) {
            if (p_155317_.f_59043_[$$5] <= 0) continue;
            $$4 = true;
            p_155317_.f_59043_[$$5] = Mth.m_14045_(p_155317_.f_59043_[$$5] - 2, 0, p_155317_.f_59044_[$$5]);
        }
        if ($$4) {
            CampfireBlockEntity.m_155232_(p_155314_, p_155315_, p_155316_);
        }
    }

    public static void m_155318_(Level p_155319_, BlockPos p_155320_, BlockState p_155321_, CampfireBlockEntity p_155322_) {
        RandomSource $$4 = p_155319_.f_46441_;
        if ($$4.m_188501_() < 0.11f) {
            for (int $$5 = 0; $$5 < $$4.m_188503_(2) + 2; ++$$5) {
                CampfireBlock.m_51251_(p_155319_, p_155320_, p_155321_.m_61143_(CampfireBlock.f_51228_), false);
            }
        }
        int $$6 = p_155321_.m_61143_(CampfireBlock.f_51230_).m_122416_();
        for (int $$7 = 0; $$7 < p_155322_.f_59042_.size(); ++$$7) {
            if (p_155322_.f_59042_.get($$7).m_41619_() || !($$4.m_188501_() < 0.2f)) continue;
            Direction $$8 = Direction.m_122407_(Math.floorMod($$7 + $$6, 4));
            float $$9 = 0.3125f;
            double $$10 = (double)p_155320_.m_123341_() + 0.5 - (double)((float)$$8.m_122429_() * 0.3125f) + (double)((float)$$8.m_122427_().m_122429_() * 0.3125f);
            double $$11 = (double)p_155320_.m_123342_() + 0.5;
            double $$12 = (double)p_155320_.m_123343_() + 0.5 - (double)((float)$$8.m_122431_() * 0.3125f) + (double)((float)$$8.m_122427_().m_122431_() * 0.3125f);
            for (int $$13 = 0; $$13 < 4; ++$$13) {
                p_155319_.m_7106_(ParticleTypes.f_123762_, $$10, $$11, $$12, 0.0, 5.0E-4, 0.0);
            }
        }
    }

    public NonNullList<ItemStack> m_59065_() {
        return this.f_59042_;
    }

    @Override
    public void m_142466_(CompoundTag p_155312_) {
        super.m_142466_(p_155312_);
        this.f_59042_.clear();
        ContainerHelper.m_18980_(p_155312_, this.f_59042_);
        if (p_155312_.m_128425_("CookingTimes", 11)) {
            int[] $$1 = p_155312_.m_128465_("CookingTimes");
            System.arraycopy($$1, 0, this.f_59043_, 0, Math.min(this.f_59044_.length, $$1.length));
        }
        if (p_155312_.m_128425_("CookingTotalTimes", 11)) {
            int[] $$2 = p_155312_.m_128465_("CookingTotalTimes");
            System.arraycopy($$2, 0, this.f_59044_, 0, Math.min(this.f_59044_.length, $$2.length));
        }
    }

    @Override
    protected void m_183515_(CompoundTag p_187486_) {
        super.m_183515_(p_187486_);
        ContainerHelper.m_18976_(p_187486_, this.f_59042_, true);
        p_187486_.m_128385_("CookingTimes", this.f_59043_);
        p_187486_.m_128385_("CookingTotalTimes", this.f_59044_);
    }

    public ClientboundBlockEntityDataPacket m_58483_() {
        return ClientboundBlockEntityDataPacket.m_195640_(this);
    }

    @Override
    public CompoundTag m_5995_() {
        CompoundTag $$0 = new CompoundTag();
        ContainerHelper.m_18976_($$0, this.f_59042_, true);
        return $$0;
    }

    public Optional<CampfireCookingRecipe> m_59051_(ItemStack p_59052_) {
        if (this.f_59042_.stream().noneMatch(ItemStack::m_41619_)) {
            return Optional.empty();
        }
        return this.f_222760_.m_213657_(new SimpleContainer(p_59052_), this.f_58857_);
    }

    public boolean m_238284_(@Nullable Entity p_238285_, ItemStack p_238286_, int p_238287_) {
        for (int $$3 = 0; $$3 < this.f_59042_.size(); ++$$3) {
            ItemStack $$4 = this.f_59042_.get($$3);
            if (!$$4.m_41619_()) continue;
            this.f_59044_[$$3] = p_238287_;
            this.f_59043_[$$3] = 0;
            this.f_59042_.set($$3, p_238286_.m_41620_(1));
            this.f_58857_.m_220407_(GameEvent.f_157792_, this.m_58899_(), GameEvent.Context.m_223719_(p_238285_, this.m_58900_()));
            this.m_59069_();
            return true;
        }
        return false;
    }

    private void m_59069_() {
        this.m_6596_();
        this.m_58904_().m_7260_(this.m_58899_(), this.m_58900_(), this.m_58900_(), 3);
    }

    @Override
    public void m_6211_() {
        this.f_59042_.clear();
    }

    public void m_59066_() {
        if (this.f_58857_ != null) {
            this.m_59069_();
        }
    }

    public /* synthetic */ Packet m_58483_() {
        return this.m_58483_();
    }
}

