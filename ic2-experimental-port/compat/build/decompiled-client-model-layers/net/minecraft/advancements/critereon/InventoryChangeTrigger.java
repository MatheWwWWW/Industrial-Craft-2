/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 */
package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Set;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class InventoryChangeTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_43145_ = new ResourceLocation("inventory_changed");

    @Override
    public ResourceLocation m_7295_() {
        return f_43145_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_43168_, EntityPredicate.Composite p_43169_, DeserializationContext p_43170_) {
        JsonObject $$3 = GsonHelper.m_13841_(p_43168_, "slots", new JsonObject());
        MinMaxBounds.Ints $$4 = MinMaxBounds.Ints.m_55373_($$3.get("occupied"));
        MinMaxBounds.Ints $$5 = MinMaxBounds.Ints.m_55373_($$3.get("full"));
        MinMaxBounds.Ints $$6 = MinMaxBounds.Ints.m_55373_($$3.get("empty"));
        ItemPredicate[] $$7 = ItemPredicate.m_45055_(p_43168_.get("items"));
        return new TriggerInstance(p_43169_, $$4, $$5, $$6, $$7);
    }

    public void m_43149_(ServerPlayer p_43150_, Inventory p_43151_, ItemStack p_43152_) {
        int $$3 = 0;
        int $$4 = 0;
        int $$5 = 0;
        for (int $$6 = 0; $$6 < p_43151_.m_6643_(); ++$$6) {
            ItemStack $$7 = p_43151_.m_8020_($$6);
            if ($$7.m_41619_()) {
                ++$$4;
                continue;
            }
            ++$$5;
            if ($$7.m_41613_() < $$7.m_41741_()) continue;
            ++$$3;
        }
        this.m_43153_(p_43150_, p_43151_, p_43152_, $$3, $$4, $$5);
    }

    private void m_43153_(ServerPlayer p_43154_, Inventory p_43155_, ItemStack p_43156_, int p_43157_, int p_43158_, int p_43159_) {
        this.m_66234_(p_43154_, p_43166_ -> p_43166_.m_43186_(p_43155_, p_43156_, p_43157_, p_43158_, p_43159_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final MinMaxBounds.Ints f_43176_;
        private final MinMaxBounds.Ints f_43177_;
        private final MinMaxBounds.Ints f_43178_;
        private final ItemPredicate[] f_43179_;

        public TriggerInstance(EntityPredicate.Composite p_43181_, MinMaxBounds.Ints p_43182_, MinMaxBounds.Ints p_43183_, MinMaxBounds.Ints p_43184_, ItemPredicate[] p_43185_) {
            super(f_43145_, p_43181_);
            this.f_43176_ = p_43182_;
            this.f_43177_ = p_43183_;
            this.f_43178_ = p_43184_;
            this.f_43179_ = p_43185_;
        }

        public static TriggerInstance m_43197_(ItemPredicate ... p_43198_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, p_43198_);
        }

        public static TriggerInstance m_43199_(ItemLike ... p_43200_) {
            ItemPredicate[] $$1 = new ItemPredicate[p_43200_.length];
            for (int $$2 = 0; $$2 < p_43200_.length; ++$$2) {
                $$1[$$2] = new ItemPredicate(null, (Set<Item>)ImmutableSet.of((Object)p_43200_[$$2].m_5456_()), MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, EnchantmentPredicate.f_30465_, EnchantmentPredicate.f_30465_, null, NbtPredicate.f_57471_);
            }
            return TriggerInstance.m_43197_($$1);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_43196_) {
            JsonObject $$1 = super.m_7683_(p_43196_);
            if (!(this.f_43176_.m_55327_() && this.f_43177_.m_55327_() && this.f_43178_.m_55327_())) {
                JsonObject $$2 = new JsonObject();
                $$2.add("occupied", this.f_43176_.m_55328_());
                $$2.add("full", this.f_43177_.m_55328_());
                $$2.add("empty", this.f_43178_.m_55328_());
                $$1.add("slots", (JsonElement)$$2);
            }
            if (this.f_43179_.length > 0) {
                JsonArray $$3 = new JsonArray();
                for (ItemPredicate $$4 : this.f_43179_) {
                    $$3.add($$4.m_45048_());
                }
                $$1.add("items", (JsonElement)$$3);
            }
            return $$1;
        }

        public boolean m_43186_(Inventory p_43187_, ItemStack p_43188_, int p_43189_, int p_43190_, int p_43191_) {
            if (!this.f_43177_.m_55390_(p_43189_)) {
                return false;
            }
            if (!this.f_43178_.m_55390_(p_43190_)) {
                return false;
            }
            if (!this.f_43176_.m_55390_(p_43191_)) {
                return false;
            }
            int $$5 = this.f_43179_.length;
            if ($$5 == 0) {
                return true;
            }
            if ($$5 == 1) {
                return !p_43188_.m_41619_() && this.f_43179_[0].m_45049_(p_43188_);
            }
            ObjectArrayList $$6 = new ObjectArrayList((Object[])this.f_43179_);
            int $$7 = p_43187_.m_6643_();
            for (int $$8 = 0; $$8 < $$7; ++$$8) {
                if ($$6.isEmpty()) {
                    return true;
                }
                ItemStack $$9 = p_43187_.m_8020_($$8);
                if ($$9.m_41619_()) continue;
                $$6.removeIf(p_43194_ -> p_43194_.m_45049_($$9));
            }
            return $$6.isEmpty();
        }
    }
}

