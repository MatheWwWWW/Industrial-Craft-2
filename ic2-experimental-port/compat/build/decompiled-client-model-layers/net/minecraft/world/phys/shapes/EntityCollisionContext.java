/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.phys.shapes;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EntityCollisionContext
implements CollisionContext {
    protected static final CollisionContext f_82865_ = new EntityCollisionContext(false, -1.7976931348623157E308, ItemStack.f_41583_, p_205118_ -> false, null){

        @Override
        public boolean m_6513_(VoxelShape p_82898_, BlockPos p_82899_, boolean p_82900_) {
            return p_82900_;
        }
    };
    private final boolean f_82866_;
    private final double f_82867_;
    private final ItemStack f_82868_;
    private final Predicate<FluidState> f_82869_;
    @Nullable
    private final Entity f_166002_;

    protected EntityCollisionContext(boolean p_198916_, double p_198917_, ItemStack p_198918_, Predicate<FluidState> p_198919_, @Nullable Entity p_198920_) {
        this.f_82866_ = p_198916_;
        this.f_82867_ = p_198917_;
        this.f_82868_ = p_198918_;
        this.f_82869_ = p_198919_;
        this.f_166002_ = p_198920_;
    }

    @Deprecated
    protected EntityCollisionContext(Entity p_82872_) {
        this(p_82872_.m_20164_(), p_82872_.m_20186_(), p_82872_ instanceof LivingEntity ? ((LivingEntity)p_82872_).m_21205_() : ItemStack.f_41583_, p_82872_ instanceof LivingEntity ? ((LivingEntity)p_82872_)::m_203441_ : p_205113_ -> false, p_82872_);
    }

    @Override
    public boolean m_7142_(Item p_82879_) {
        return this.f_82868_.m_150930_(p_82879_);
    }

    @Override
    public boolean m_203682_(FluidState p_205115_, FluidState p_205116_) {
        return this.f_82869_.test(p_205116_) && !p_205115_.m_76152_().m_6212_(p_205116_.m_76152_());
    }

    @Override
    public boolean m_6226_() {
        return this.f_82866_;
    }

    @Override
    public boolean m_6513_(VoxelShape p_82886_, BlockPos p_82887_, boolean p_82888_) {
        return this.f_82867_ > (double)p_82887_.m_123342_() + p_82886_.m_83297_(Direction.Axis.Y) - (double)1.0E-5f;
    }

    @Nullable
    public Entity m_193113_() {
        return this.f_166002_;
    }
}

