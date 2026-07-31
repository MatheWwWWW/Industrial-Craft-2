/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonObject
 */
package net.minecraft.advancements.critereon;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public class KilledByCrossbowTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_46867_ = new ResourceLocation("killed_by_crossbow");

    @Override
    public ResourceLocation m_7295_() {
        return f_46867_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_46875_, EntityPredicate.Composite p_46876_, DeserializationContext p_46877_) {
        EntityPredicate.Composite[] $$3 = EntityPredicate.Composite.m_36692_(p_46875_, "victims", p_46877_);
        MinMaxBounds.Ints $$4 = MinMaxBounds.Ints.m_55373_(p_46875_.get("unique_entity_types"));
        return new TriggerInstance(p_46876_, $$3, $$4);
    }

    public void m_46871_(ServerPlayer p_46872_, Collection<Entity> p_46873_) {
        ArrayList $$2 = Lists.newArrayList();
        HashSet $$3 = Sets.newHashSet();
        for (Entity $$4 : p_46873_) {
            $$3.add($$4.m_6095_());
            $$2.add(EntityPredicate.m_36616_(p_46872_, $$4));
        }
        this.m_66234_(p_46872_, p_46881_ -> p_46881_.m_46897_($$2, $$3.size()));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final EntityPredicate.Composite[] f_46887_;
        private final MinMaxBounds.Ints f_46888_;

        public TriggerInstance(EntityPredicate.Composite p_46890_, EntityPredicate.Composite[] p_46891_, MinMaxBounds.Ints p_46892_) {
            super(f_46867_, p_46890_);
            this.f_46887_ = p_46891_;
            this.f_46888_ = p_46892_;
        }

        public static TriggerInstance m_46900_(EntityPredicate.Builder ... p_46901_) {
            EntityPredicate.Composite[] $$1 = new EntityPredicate.Composite[p_46901_.length];
            for (int $$2 = 0; $$2 < p_46901_.length; ++$$2) {
                EntityPredicate.Builder $$3 = p_46901_[$$2];
                $$1[$$2] = EntityPredicate.Composite.m_36673_($$3.m_36662_());
            }
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, $$1, MinMaxBounds.Ints.f_55364_);
        }

        public static TriggerInstance m_46893_(MinMaxBounds.Ints p_46894_) {
            EntityPredicate.Composite[] $$1 = new EntityPredicate.Composite[]{};
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, $$1, p_46894_);
        }

        public boolean m_46897_(Collection<LootContext> p_46898_, int p_46899_) {
            if (this.f_46887_.length > 0) {
                ArrayList $$2 = Lists.newArrayList(p_46898_);
                for (EntityPredicate.Composite $$3 : this.f_46887_) {
                    boolean $$4 = false;
                    Iterator $$5 = $$2.iterator();
                    while ($$5.hasNext()) {
                        LootContext $$6 = (LootContext)$$5.next();
                        if (!$$3.m_36681_($$6)) continue;
                        $$5.remove();
                        $$4 = true;
                        break;
                    }
                    if ($$4) continue;
                    return false;
                }
            }
            return this.f_46888_.m_55390_(p_46899_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_46896_) {
            JsonObject $$1 = super.m_7683_(p_46896_);
            $$1.add("victims", EntityPredicate.Composite.m_36687_(this.f_46887_, p_46896_));
            $$1.add("unique_entity_types", this.f_46888_.m_55328_());
            return $$1;
        }
    }
}

