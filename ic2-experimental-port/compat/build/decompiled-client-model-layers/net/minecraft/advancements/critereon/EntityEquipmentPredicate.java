/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Items;

public class EntityEquipmentPredicate {
    public static final EntityEquipmentPredicate f_32176_ = new EntityEquipmentPredicate(ItemPredicate.f_45028_, ItemPredicate.f_45028_, ItemPredicate.f_45028_, ItemPredicate.f_45028_, ItemPredicate.f_45028_, ItemPredicate.f_45028_);
    public static final EntityEquipmentPredicate f_32177_ = new EntityEquipmentPredicate(ItemPredicate.Builder.m_45068_().m_151445_(Items.f_42660_).m_45075_(Raid.m_37779_().m_41783_()).m_45077_(), ItemPredicate.f_45028_, ItemPredicate.f_45028_, ItemPredicate.f_45028_, ItemPredicate.f_45028_, ItemPredicate.f_45028_);
    private final ItemPredicate f_32178_;
    private final ItemPredicate f_32179_;
    private final ItemPredicate f_32180_;
    private final ItemPredicate f_32181_;
    private final ItemPredicate f_32182_;
    private final ItemPredicate f_32183_;

    public EntityEquipmentPredicate(ItemPredicate p_32186_, ItemPredicate p_32187_, ItemPredicate p_32188_, ItemPredicate p_32189_, ItemPredicate p_32190_, ItemPredicate p_32191_) {
        this.f_32178_ = p_32186_;
        this.f_32179_ = p_32187_;
        this.f_32180_ = p_32188_;
        this.f_32181_ = p_32189_;
        this.f_32182_ = p_32190_;
        this.f_32183_ = p_32191_;
    }

    public boolean m_32193_(@Nullable Entity p_32194_) {
        if (this == f_32176_) {
            return true;
        }
        if (!(p_32194_ instanceof LivingEntity)) {
            return false;
        }
        LivingEntity $$1 = (LivingEntity)p_32194_;
        if (!this.f_32178_.m_45049_($$1.m_6844_(EquipmentSlot.HEAD))) {
            return false;
        }
        if (!this.f_32179_.m_45049_($$1.m_6844_(EquipmentSlot.CHEST))) {
            return false;
        }
        if (!this.f_32180_.m_45049_($$1.m_6844_(EquipmentSlot.LEGS))) {
            return false;
        }
        if (!this.f_32181_.m_45049_($$1.m_6844_(EquipmentSlot.FEET))) {
            return false;
        }
        if (!this.f_32182_.m_45049_($$1.m_6844_(EquipmentSlot.MAINHAND))) {
            return false;
        }
        return this.f_32183_.m_45049_($$1.m_6844_(EquipmentSlot.OFFHAND));
    }

    public static EntityEquipmentPredicate m_32195_(@Nullable JsonElement p_32196_) {
        if (p_32196_ == null || p_32196_.isJsonNull()) {
            return f_32176_;
        }
        JsonObject $$1 = GsonHelper.m_13918_(p_32196_, "equipment");
        ItemPredicate $$2 = ItemPredicate.m_45051_($$1.get("head"));
        ItemPredicate $$3 = ItemPredicate.m_45051_($$1.get("chest"));
        ItemPredicate $$4 = ItemPredicate.m_45051_($$1.get("legs"));
        ItemPredicate $$5 = ItemPredicate.m_45051_($$1.get("feet"));
        ItemPredicate $$6 = ItemPredicate.m_45051_($$1.get("mainhand"));
        ItemPredicate $$7 = ItemPredicate.m_45051_($$1.get("offhand"));
        return new EntityEquipmentPredicate($$2, $$3, $$4, $$5, $$6, $$7);
    }

    public JsonElement m_32192_() {
        if (this == f_32176_) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$0 = new JsonObject();
        $$0.add("head", this.f_32178_.m_45048_());
        $$0.add("chest", this.f_32179_.m_45048_());
        $$0.add("legs", this.f_32180_.m_45048_());
        $$0.add("feet", this.f_32181_.m_45048_());
        $$0.add("mainhand", this.f_32182_.m_45048_());
        $$0.add("offhand", this.f_32183_.m_45048_());
        return $$0;
    }

    public static class Builder {
        private ItemPredicate f_32197_ = ItemPredicate.f_45028_;
        private ItemPredicate f_32198_ = ItemPredicate.f_45028_;
        private ItemPredicate f_32199_ = ItemPredicate.f_45028_;
        private ItemPredicate f_32200_ = ItemPredicate.f_45028_;
        private ItemPredicate f_32201_ = ItemPredicate.f_45028_;
        private ItemPredicate f_32202_ = ItemPredicate.f_45028_;

        public static Builder m_32204_() {
            return new Builder();
        }

        public Builder m_32205_(ItemPredicate p_32206_) {
            this.f_32197_ = p_32206_;
            return this;
        }

        public Builder m_32208_(ItemPredicate p_32209_) {
            this.f_32198_ = p_32209_;
            return this;
        }

        public Builder m_32210_(ItemPredicate p_32211_) {
            this.f_32199_ = p_32211_;
            return this;
        }

        public Builder m_32212_(ItemPredicate p_32213_) {
            this.f_32200_ = p_32213_;
            return this;
        }

        public Builder m_149928_(ItemPredicate p_149929_) {
            this.f_32201_ = p_149929_;
            return this;
        }

        public Builder m_149930_(ItemPredicate p_149931_) {
            this.f_32202_ = p_149931_;
            return this;
        }

        public EntityEquipmentPredicate m_32207_() {
            return new EntityEquipmentPredicate(this.f_32197_, this.f_32198_, this.f_32199_, this.f_32200_, this.f_32201_, this.f_32202_);
        }
    }
}

