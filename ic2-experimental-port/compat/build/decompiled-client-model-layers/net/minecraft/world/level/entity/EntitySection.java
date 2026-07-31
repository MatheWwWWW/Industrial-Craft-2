/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.entity;

import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraft.util.ClassInstanceMultiMap;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.entity.Visibility;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;

public class EntitySection<T extends EntityAccess> {
    private static final Logger f_156826_ = LogUtils.getLogger();
    private final ClassInstanceMultiMap<T> f_156827_;
    private Visibility f_156828_;

    public EntitySection(Class<T> p_156831_, Visibility p_156832_) {
        this.f_156828_ = p_156832_;
        this.f_156827_ = new ClassInstanceMultiMap<T>(p_156831_);
    }

    public void m_188346_(T p_188347_) {
        this.f_156827_.add(p_188347_);
    }

    public boolean m_188355_(T p_188356_) {
        return this.f_156827_.remove(p_188356_);
    }

    public void m_188352_(AABB p_188353_, Consumer<T> p_188354_) {
        for (EntityAccess $$2 : this.f_156827_) {
            if (!$$2.m_20191_().m_82381_(p_188353_)) continue;
            p_188354_.accept($$2);
        }
    }

    public <U extends T> void m_188348_(EntityTypeTest<T, U> p_188349_, AABB p_188350_, Consumer<? super U> p_188351_) {
        Collection<T> $$3 = this.f_156827_.m_13533_(p_188349_.m_142225_());
        if ($$3.isEmpty()) {
            return;
        }
        for (EntityAccess $$4 : $$3) {
            EntityAccess $$5 = (EntityAccess)p_188349_.m_141992_($$4);
            if ($$5 == null || !$$4.m_20191_().m_82381_(p_188350_)) continue;
            p_188351_.accept($$5);
        }
    }

    public boolean m_156833_() {
        return this.f_156827_.isEmpty();
    }

    public Stream<T> m_156845_() {
        return this.f_156827_.stream();
    }

    public Visibility m_156848_() {
        return this.f_156828_;
    }

    public Visibility m_156838_(Visibility p_156839_) {
        Visibility $$1 = this.f_156828_;
        this.f_156828_ = p_156839_;
        return $$1;
    }

    @VisibleForDebug
    public int m_156849_() {
        return this.f_156827_.size();
    }
}

