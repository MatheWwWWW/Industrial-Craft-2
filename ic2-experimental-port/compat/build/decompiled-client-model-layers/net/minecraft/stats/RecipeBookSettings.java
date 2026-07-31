/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.stats;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.RecipeBookType;

public final class RecipeBookSettings {
    private static final Map<RecipeBookType, Pair<String, String>> f_12725_ = ImmutableMap.of((Object)((Object)RecipeBookType.CRAFTING), (Object)Pair.of((Object)"isGuiOpen", (Object)"isFilteringCraftable"), (Object)((Object)RecipeBookType.FURNACE), (Object)Pair.of((Object)"isFurnaceGuiOpen", (Object)"isFurnaceFilteringCraftable"), (Object)((Object)RecipeBookType.BLAST_FURNACE), (Object)Pair.of((Object)"isBlastingFurnaceGuiOpen", (Object)"isBlastingFurnaceFilteringCraftable"), (Object)((Object)RecipeBookType.SMOKER), (Object)Pair.of((Object)"isSmokerGuiOpen", (Object)"isSmokerFilteringCraftable"));
    private final Map<RecipeBookType, TypeSettings> f_12726_;

    private RecipeBookSettings(Map<RecipeBookType, TypeSettings> p_12730_) {
        this.f_12726_ = p_12730_;
    }

    public RecipeBookSettings() {
        this(Util.m_137469_(Maps.newEnumMap(RecipeBookType.class), p_12740_ -> {
            for (RecipeBookType $$1 : RecipeBookType.values()) {
                p_12740_.put($$1, new TypeSettings(false, false));
            }
        }));
    }

    public boolean m_12734_(RecipeBookType p_12735_) {
        return this.f_12726_.get((Object)((Object)p_12735_)).f_12766_;
    }

    public void m_12736_(RecipeBookType p_12737_, boolean p_12738_) {
        this.f_12726_.get((Object)((Object)p_12737_)).f_12766_ = p_12738_;
    }

    public boolean m_12754_(RecipeBookType p_12755_) {
        return this.f_12726_.get((Object)((Object)p_12755_)).f_12767_;
    }

    public void m_12756_(RecipeBookType p_12757_, boolean p_12758_) {
        this.f_12726_.get((Object)((Object)p_12757_)).f_12767_ = p_12758_;
    }

    public static RecipeBookSettings m_12752_(FriendlyByteBuf p_12753_) {
        EnumMap $$1 = Maps.newEnumMap(RecipeBookType.class);
        for (RecipeBookType $$2 : RecipeBookType.values()) {
            boolean $$3 = p_12753_.readBoolean();
            boolean $$4 = p_12753_.readBoolean();
            $$1.put($$2, new TypeSettings($$3, $$4));
        }
        return new RecipeBookSettings($$1);
    }

    public void m_12761_(FriendlyByteBuf p_12762_) {
        for (RecipeBookType $$1 : RecipeBookType.values()) {
            TypeSettings $$2 = this.f_12726_.get((Object)$$1);
            if ($$2 == null) {
                p_12762_.writeBoolean(false);
                p_12762_.writeBoolean(false);
                continue;
            }
            p_12762_.writeBoolean($$2.f_12766_);
            p_12762_.writeBoolean($$2.f_12767_);
        }
    }

    public static RecipeBookSettings m_12741_(CompoundTag p_12742_) {
        EnumMap $$1 = Maps.newEnumMap(RecipeBookType.class);
        f_12725_.forEach((p_12750_, p_12751_) -> {
            boolean $$4 = p_12742_.m_128471_((String)p_12751_.getFirst());
            boolean $$5 = p_12742_.m_128471_((String)p_12751_.getSecond());
            $$1.put(p_12750_, new TypeSettings($$4, $$5));
        });
        return new RecipeBookSettings($$1);
    }

    public void m_12759_(CompoundTag p_12760_) {
        f_12725_.forEach((p_12745_, p_12746_) -> {
            TypeSettings $$3 = this.f_12726_.get(p_12745_);
            p_12760_.m_128379_((String)p_12746_.getFirst(), $$3.f_12766_);
            p_12760_.m_128379_((String)p_12746_.getSecond(), $$3.f_12767_);
        });
    }

    public RecipeBookSettings m_12731_() {
        EnumMap $$0 = Maps.newEnumMap(RecipeBookType.class);
        for (RecipeBookType $$1 : RecipeBookType.values()) {
            TypeSettings $$2 = this.f_12726_.get((Object)$$1);
            $$0.put($$1, $$2.m_12771_());
        }
        return new RecipeBookSettings($$0);
    }

    public void m_12732_(RecipeBookSettings p_12733_) {
        this.f_12726_.clear();
        for (RecipeBookType $$1 : RecipeBookType.values()) {
            TypeSettings $$2 = p_12733_.f_12726_.get((Object)$$1);
            this.f_12726_.put($$1, $$2.m_12771_());
        }
    }

    public boolean equals(Object p_12764_) {
        return this == p_12764_ || p_12764_ instanceof RecipeBookSettings && this.f_12726_.equals(((RecipeBookSettings)p_12764_).f_12726_);
    }

    public int hashCode() {
        return this.f_12726_.hashCode();
    }

    static final class TypeSettings {
        boolean f_12766_;
        boolean f_12767_;

        public TypeSettings(boolean p_12769_, boolean p_12770_) {
            this.f_12766_ = p_12769_;
            this.f_12767_ = p_12770_;
        }

        public TypeSettings m_12771_() {
            return new TypeSettings(this.f_12766_, this.f_12767_);
        }

        public boolean equals(Object p_12783_) {
            if (this == p_12783_) {
                return true;
            }
            if (p_12783_ instanceof TypeSettings) {
                TypeSettings $$1 = (TypeSettings)p_12783_;
                return this.f_12766_ == $$1.f_12766_ && this.f_12767_ == $$1.f_12767_;
            }
            return false;
        }

        public int hashCode() {
            int $$0 = this.f_12766_ ? 1 : 0;
            $$0 = 31 * $$0 + (this.f_12767_ ? 1 : 0);
            return $$0;
        }

        public String toString() {
            return "[open=" + this.f_12766_ + ", filtering=" + this.f_12767_ + "]";
        }
    }
}

