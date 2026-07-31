/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.projectile;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownPotion
extends ThrowableItemProjectile
implements ItemSupplier {
    public static final double f_150190_ = 4.0;
    private static final double f_150191_ = 16.0;
    public static final Predicate<LivingEntity> f_37524_ = LivingEntity::m_6126_;

    public ThrownPotion(EntityType<? extends ThrownPotion> p_37527_, Level p_37528_) {
        super((EntityType<? extends ThrowableItemProjectile>)p_37527_, p_37528_);
    }

    public ThrownPotion(Level p_37535_, LivingEntity p_37536_) {
        super((EntityType<? extends ThrowableItemProjectile>)EntityType.f_20486_, p_37536_, p_37535_);
    }

    public ThrownPotion(Level p_37530_, double p_37531_, double p_37532_, double p_37533_) {
        super((EntityType<? extends ThrowableItemProjectile>)EntityType.f_20486_, p_37531_, p_37532_, p_37533_, p_37530_);
    }

    @Override
    protected Item m_7881_() {
        return Items.f_42736_;
    }

    @Override
    protected float m_7139_() {
        return 0.05f;
    }

    @Override
    protected void m_8060_(BlockHitResult p_37541_) {
        super.m_8060_(p_37541_);
        if (this.f_19853_.f_46443_) {
            return;
        }
        ItemStack $$1 = this.m_7846_();
        Potion $$2 = PotionUtils.m_43579_($$1);
        List<MobEffectInstance> $$3 = PotionUtils.m_43547_($$1);
        boolean $$4 = $$2 == Potions.f_43599_ && $$3.isEmpty();
        Direction $$5 = p_37541_.m_82434_();
        BlockPos $$6 = p_37541_.m_82425_();
        BlockPos $$7 = $$6.m_121945_($$5);
        if ($$4) {
            this.m_150192_($$7);
            this.m_150192_($$7.m_121945_($$5.m_122424_()));
            for (Direction $$8 : Direction.Plane.HORIZONTAL) {
                this.m_150192_($$7.m_121945_($$8));
            }
        }
    }

    @Override
    protected void m_6532_(HitResult p_37543_) {
        boolean $$4;
        super.m_6532_(p_37543_);
        if (this.f_19853_.f_46443_) {
            return;
        }
        ItemStack $$1 = this.m_7846_();
        Potion $$2 = PotionUtils.m_43579_($$1);
        List<MobEffectInstance> $$3 = PotionUtils.m_43547_($$1);
        boolean bl = $$4 = $$2 == Potions.f_43599_ && $$3.isEmpty();
        if ($$4) {
            this.m_37552_();
        } else if (!$$3.isEmpty()) {
            if (this.m_37553_()) {
                this.m_37537_($$1, $$2);
            } else {
                this.m_37547_($$3, p_37543_.m_6662_() == HitResult.Type.ENTITY ? ((EntityHitResult)p_37543_).m_82443_() : null);
            }
        }
        int $$5 = $$2.m_43491_() ? 2007 : 2002;
        this.f_19853_.m_46796_($$5, this.m_20183_(), PotionUtils.m_43575_($$1));
        this.m_146870_();
    }

    private void m_37552_() {
        AABB $$0 = this.m_20191_().m_82377_(4.0, 2.0, 4.0);
        List<LivingEntity> $$1 = this.f_19853_.m_6443_(LivingEntity.class, $$0, f_37524_);
        if (!$$1.isEmpty()) {
            for (LivingEntity $$2 : $$1) {
                double $$3 = this.m_20280_($$2);
                if (!($$3 < 16.0) || !$$2.m_6126_()) continue;
                $$2.m_6469_(DamageSource.m_19367_(this, this.m_37282_()), 1.0f);
            }
        }
        List<Axolotl> $$4 = this.f_19853_.m_45976_(Axolotl.class, $$0);
        for (Axolotl $$5 : $$4) {
            $$5.m_149177_();
        }
    }

    private void m_37547_(List<MobEffectInstance> p_37548_, @Nullable Entity p_37549_) {
        AABB $$2 = this.m_20191_().m_82377_(4.0, 2.0, 4.0);
        List<LivingEntity> $$3 = this.f_19853_.m_45976_(LivingEntity.class, $$2);
        if (!$$3.isEmpty()) {
            Entity $$4 = this.m_150173_();
            for (LivingEntity $$5 : $$3) {
                double $$6;
                if (!$$5.m_5801_() || !(($$6 = this.m_20280_($$5)) < 16.0)) continue;
                double $$7 = 1.0 - Math.sqrt($$6) / 4.0;
                if ($$5 == p_37549_) {
                    $$7 = 1.0;
                }
                for (MobEffectInstance $$8 : p_37548_) {
                    MobEffect $$9 = $$8.m_19544_();
                    if ($$9.m_8093_()) {
                        $$9.m_19461_(this, this.m_37282_(), $$5, $$8.m_19564_(), $$7);
                        continue;
                    }
                    int $$10 = (int)($$7 * (double)$$8.m_19557_() + 0.5);
                    if ($$10 <= 20) continue;
                    $$5.m_147207_(new MobEffectInstance($$9, $$10, $$8.m_19564_(), $$8.m_19571_(), $$8.m_19572_()), $$4);
                }
            }
        }
    }

    private void m_37537_(ItemStack p_37538_, Potion p_37539_) {
        AreaEffectCloud $$2 = new AreaEffectCloud(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_());
        Entity $$3 = this.m_37282_();
        if ($$3 instanceof LivingEntity) {
            $$2.m_19718_((LivingEntity)$$3);
        }
        $$2.m_19712_(3.0f);
        $$2.m_19732_(-0.5f);
        $$2.m_19740_(10);
        $$2.m_19738_(-$$2.m_19743_() / (float)$$2.m_19748_());
        $$2.m_19722_(p_37539_);
        for (MobEffectInstance $$4 : PotionUtils.m_43571_(p_37538_)) {
            $$2.m_19716_(new MobEffectInstance($$4));
        }
        CompoundTag $$5 = p_37538_.m_41783_();
        if ($$5 != null && $$5.m_128425_("CustomPotionColor", 99)) {
            $$2.m_19714_($$5.m_128451_("CustomPotionColor"));
        }
        this.f_19853_.m_7967_($$2);
    }

    private boolean m_37553_() {
        return this.m_7846_().m_150930_(Items.f_42739_);
    }

    private void m_150192_(BlockPos p_150193_) {
        BlockState $$1 = this.f_19853_.m_8055_(p_150193_);
        if ($$1.m_204336_(BlockTags.f_13076_)) {
            this.f_19853_.m_7471_(p_150193_, false);
        } else if (AbstractCandleBlock.m_151933_($$1)) {
            AbstractCandleBlock.m_151899_(null, $$1, this.f_19853_, p_150193_);
        } else if (CampfireBlock.m_51319_($$1)) {
            this.f_19853_.m_5898_(null, 1009, p_150193_, 0);
            CampfireBlock.m_152749_(this.m_37282_(), this.f_19853_, p_150193_, $$1);
            this.f_19853_.m_46597_(p_150193_, (BlockState)$$1.m_61124_(CampfireBlock.f_51227_, false));
        }
    }
}

