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
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;

public interface LevelEntityGetter<T extends EntityAccess> {
    @Nullable
    public T m_142597_(int var1);

    @Nullable
    public T m_142694_(UUID var1);

    public Iterable<T> m_142273_();

    public <U extends T> void m_142690_(EntityTypeTest<T, U> var1, Consumer<U> var2);

    public void m_142232_(AABB var1, Consumer<T> var2);

    public <U extends T> void m_142137_(EntityTypeTest<T, U> var1, AABB var2, Consumer<U> var3);
}

