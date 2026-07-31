/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.entity;

import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntityLookup;
import net.minecraft.world.level.entity.EntitySectionStorage;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.phys.AABB;

public class LevelEntityGetterAdapter<T extends EntityAccess>
implements LevelEntityGetter<T> {
    private final EntityLookup<T> f_156940_;
    private final EntitySectionStorage<T> f_156941_;

    public LevelEntityGetterAdapter(EntityLookup<T> p_156943_, EntitySectionStorage<T> p_156944_) {
        this.f_156940_ = p_156943_;
        this.f_156941_ = p_156944_;
    }

    @Override
    @Nullable
    public T m_142597_(int p_156947_) {
        return this.f_156940_.m_156812_(p_156947_);
    }

    @Override
    @Nullable
    public T m_142694_(UUID p_156959_) {
        return this.f_156940_.m_156819_(p_156959_);
    }

    @Override
    public Iterable<T> m_142273_() {
        return this.f_156940_.m_156811_();
    }

    @Override
    public <U extends T> void m_142690_(EntityTypeTest<T, U> p_156953_, Consumer<U> p_156954_) {
        this.f_156940_.m_156816_(p_156953_, p_156954_);
    }

    @Override
    public void m_142232_(AABB p_156956_, Consumer<T> p_156957_) {
        this.f_156941_.m_156890_(p_156956_, p_156957_);
    }

    @Override
    public <U extends T> void m_142137_(EntityTypeTest<T, U> p_156949_, AABB p_156950_, Consumer<U> p_156951_) {
        this.f_156941_.m_156863_(p_156949_, p_156950_, p_156951_);
    }
}

