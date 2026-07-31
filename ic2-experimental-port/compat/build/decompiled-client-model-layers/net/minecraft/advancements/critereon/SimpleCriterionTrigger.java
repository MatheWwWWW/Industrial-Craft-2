/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonObject
 */
package net.minecraft.advancements.critereon;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.LootContext;

public abstract class SimpleCriterionTrigger<T extends AbstractCriterionTriggerInstance>
implements CriterionTrigger<T> {
    private final Map<PlayerAdvancements, Set<CriterionTrigger.Listener<T>>> f_66232_ = Maps.newIdentityHashMap();

    @Override
    public final void m_6467_(PlayerAdvancements p_66243_, CriterionTrigger.Listener<T> p_66244_) {
        this.f_66232_.computeIfAbsent(p_66243_, p_66252_ -> Sets.newHashSet()).add(p_66244_);
    }

    @Override
    public final void m_6468_(PlayerAdvancements p_66254_, CriterionTrigger.Listener<T> p_66255_) {
        Set<CriterionTrigger.Listener<T>> $$2 = this.f_66232_.get(p_66254_);
        if ($$2 != null) {
            $$2.remove(p_66255_);
            if ($$2.isEmpty()) {
                this.f_66232_.remove(p_66254_);
            }
        }
    }

    @Override
    public final void m_5656_(PlayerAdvancements p_66241_) {
        this.f_66232_.remove(p_66241_);
    }

    protected abstract T m_7214_(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3);

    @Override
    public final T m_5868_(JsonObject p_66246_, DeserializationContext p_66247_) {
        EntityPredicate.Composite $$2 = EntityPredicate.Composite.m_36677_(p_66246_, "player", p_66247_);
        return this.m_7214_(p_66246_, $$2, p_66247_);
    }

    protected void m_66234_(ServerPlayer p_66235_, Predicate<T> p_66236_) {
        PlayerAdvancements $$2 = p_66235_.m_8960_();
        Set<CriterionTrigger.Listener<T>> $$3 = this.f_66232_.get($$2);
        if ($$3 == null || $$3.isEmpty()) {
            return;
        }
        LootContext $$4 = EntityPredicate.m_36616_(p_66235_, p_66235_);
        List $$5 = null;
        for (CriterionTrigger.Listener<T> $$6 : $$3) {
            AbstractCriterionTriggerInstance $$7 = (AbstractCriterionTriggerInstance)$$6.m_13685_();
            if (!p_66236_.test($$7) || !$$7.m_16980_().m_36681_($$4)) continue;
            if ($$5 == null) {
                $$5 = Lists.newArrayList();
            }
            $$5.add($$6);
        }
        if ($$5 != null) {
            for (CriterionTrigger.Listener<Object> $$8 : $$5) {
                $$8.m_13686_($$2);
            }
        }
    }

    @Override
    public /* synthetic */ CriterionTriggerInstance m_5868_(JsonObject jsonObject, DeserializationContext deserializationContext) {
        return this.m_5868_(jsonObject, deserializationContext);
    }
}

