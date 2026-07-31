/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.behavior;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;

public class RunSometimes<E extends LivingEntity>
extends Behavior<E> {
    private boolean f_23836_;
    private boolean f_23837_;
    private final UniformInt f_23838_;
    private final Behavior<? super E> f_23839_;
    private int f_23840_;

    public RunSometimes(Behavior<? super E> p_147874_, UniformInt p_147875_) {
        this(p_147874_, false, p_147875_);
    }

    public RunSometimes(Behavior<? super E> p_147877_, boolean p_147878_, UniformInt p_147879_) {
        super(p_147877_.f_22522_);
        this.f_23839_ = p_147877_;
        this.f_23836_ = !p_147878_;
        this.f_23838_ = p_147879_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23853_, E p_23854_) {
        if (!this.f_23839_.m_6114_(p_23853_, p_23854_)) {
            return false;
        }
        if (this.f_23836_) {
            this.m_23850_(p_23853_);
            this.f_23836_ = false;
        }
        if (this.f_23840_ > 0) {
            --this.f_23840_;
        }
        return !this.f_23837_ && this.f_23840_ == 0;
    }

    @Override
    protected void m_6735_(ServerLevel p_23856_, E p_23857_, long p_23858_) {
        this.f_23839_.m_6735_(p_23856_, p_23857_, p_23858_);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_23860_, E p_23861_, long p_23862_) {
        return this.f_23839_.m_6737_(p_23860_, p_23861_, p_23862_);
    }

    @Override
    protected void m_6725_(ServerLevel p_23868_, E p_23869_, long p_23870_) {
        this.f_23839_.m_6725_(p_23868_, p_23869_, p_23870_);
        this.f_23837_ = this.f_23839_.m_22536_() == Behavior.Status.RUNNING;
    }

    @Override
    protected void m_6732_(ServerLevel p_23864_, E p_23865_, long p_23866_) {
        this.m_23850_(p_23864_);
        this.f_23839_.m_6732_(p_23864_, p_23865_, p_23866_);
    }

    private void m_23850_(ServerLevel p_23851_) {
        this.f_23840_ = this.f_23838_.m_214085_(p_23851_.f_46441_);
    }

    @Override
    protected boolean m_7773_(long p_23849_) {
        return false;
    }

    @Override
    public String toString() {
        return "RunSometimes: " + this.f_23839_;
    }
}

