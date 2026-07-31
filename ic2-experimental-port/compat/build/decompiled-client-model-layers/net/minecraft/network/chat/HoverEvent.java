/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.network.chat;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

public class HoverEvent {
    static final Logger f_130813_ = LogUtils.getLogger();
    private final Action<?> f_130814_;
    private final Object f_130815_;

    public <T> HoverEvent(Action<T> p_130818_, T p_130819_) {
        this.f_130814_ = p_130818_;
        this.f_130815_ = p_130819_;
    }

    public Action<?> m_130820_() {
        return this.f_130814_;
    }

    @Nullable
    public <T> T m_130823_(Action<T> p_130824_) {
        if (this.f_130814_ == p_130824_) {
            return p_130824_.m_130864_(this.f_130815_);
        }
        return null;
    }

    public boolean equals(Object p_130828_) {
        if (this == p_130828_) {
            return true;
        }
        if (p_130828_ == null || this.getClass() != p_130828_.getClass()) {
            return false;
        }
        HoverEvent $$1 = (HoverEvent)p_130828_;
        return this.f_130814_ == $$1.f_130814_ && Objects.equals(this.f_130815_, $$1.f_130815_);
    }

    public String toString() {
        return "HoverEvent{action=" + this.f_130814_ + ", value='" + this.f_130815_ + "'}";
    }

    public int hashCode() {
        int $$0 = this.f_130814_.hashCode();
        $$0 = 31 * $$0 + (this.f_130815_ != null ? this.f_130815_.hashCode() : 0);
        return $$0;
    }

    @Nullable
    public static HoverEvent m_130821_(JsonObject p_130822_) {
        String $$1 = GsonHelper.m_13851_(p_130822_, "action", null);
        if ($$1 == null) {
            return null;
        }
        Action<?> $$2 = Action.m_130852_($$1);
        if ($$2 == null) {
            return null;
        }
        JsonElement $$3 = p_130822_.get("contents");
        if ($$3 != null) {
            return $$2.m_130848_($$3);
        }
        MutableComponent $$4 = Component.Serializer.m_130691_(p_130822_.get("value"));
        if ($$4 != null) {
            return $$2.m_130854_($$4);
        }
        return null;
    }

    public JsonObject m_130825_() {
        JsonObject $$0 = new JsonObject();
        $$0.addProperty("action", this.f_130814_.m_130861_());
        $$0.add("contents", this.f_130814_.m_130850_(this.f_130815_));
        return $$0;
    }

    public static class Action<T> {
        public static final Action<Component> f_130831_ = new Action<Component>("show_text", true, Component.Serializer::m_130691_, Component.Serializer::m_130716_, Function.identity());
        public static final Action<ItemStackInfo> f_130832_ = new Action<ItemStackInfo>("show_item", true, ItemStackInfo::m_130906_, ItemStackInfo::m_130905_, ItemStackInfo::m_130908_);
        public static final Action<EntityTooltipInfo> f_130833_ = new Action<EntityTooltipInfo>("show_entity", true, EntityTooltipInfo::m_130880_, EntityTooltipInfo::m_130879_, EntityTooltipInfo::m_130882_);
        private static final Map<String, Action<?>> f_130834_ = (Map)Stream.of(f_130831_, f_130832_, f_130833_).collect(ImmutableMap.toImmutableMap(Action::m_130861_, p_178444_ -> p_178444_));
        private final String f_130835_;
        private final boolean f_130836_;
        private final Function<JsonElement, T> f_130837_;
        private final Function<T, JsonElement> f_130838_;
        private final Function<Component, T> f_130839_;

        public Action(String p_130842_, boolean p_130843_, Function<JsonElement, T> p_130844_, Function<T, JsonElement> p_130845_, Function<Component, T> p_130846_) {
            this.f_130835_ = p_130842_;
            this.f_130836_ = p_130843_;
            this.f_130837_ = p_130844_;
            this.f_130838_ = p_130845_;
            this.f_130839_ = p_130846_;
        }

        public boolean m_130847_() {
            return this.f_130836_;
        }

        public String m_130861_() {
            return this.f_130835_;
        }

        @Nullable
        public static Action<?> m_130852_(String p_130853_) {
            return f_130834_.get(p_130853_);
        }

        T m_130864_(Object p_130865_) {
            return (T)p_130865_;
        }

        @Nullable
        public HoverEvent m_130848_(JsonElement p_130849_) {
            T $$1 = this.f_130837_.apply(p_130849_);
            if ($$1 == null) {
                return null;
            }
            return new HoverEvent(this, $$1);
        }

        @Nullable
        public HoverEvent m_130854_(Component p_130855_) {
            T $$1 = this.f_130839_.apply(p_130855_);
            if ($$1 == null) {
                return null;
            }
            return new HoverEvent(this, $$1);
        }

        public JsonElement m_130850_(Object p_130851_) {
            return this.f_130838_.apply(this.m_130864_(p_130851_));
        }

        public String toString() {
            return "<action " + this.f_130835_ + ">";
        }
    }

    public static class ItemStackInfo {
        private final Item f_130888_;
        private final int f_130889_;
        @Nullable
        private final CompoundTag f_130890_;
        @Nullable
        private ItemStack f_130891_;

        ItemStackInfo(Item p_130893_, int p_130894_, @Nullable CompoundTag p_130895_) {
            this.f_130888_ = p_130893_;
            this.f_130889_ = p_130894_;
            this.f_130890_ = p_130895_;
        }

        public ItemStackInfo(ItemStack p_130897_) {
            this(p_130897_.m_41720_(), p_130897_.m_41613_(), p_130897_.m_41783_() != null ? p_130897_.m_41783_().m_6426_() : null);
        }

        public boolean equals(Object p_130911_) {
            if (this == p_130911_) {
                return true;
            }
            if (p_130911_ == null || this.getClass() != p_130911_.getClass()) {
                return false;
            }
            ItemStackInfo $$1 = (ItemStackInfo)p_130911_;
            return this.f_130889_ == $$1.f_130889_ && this.f_130888_.equals($$1.f_130888_) && Objects.equals(this.f_130890_, $$1.f_130890_);
        }

        public int hashCode() {
            int $$0 = this.f_130888_.hashCode();
            $$0 = 31 * $$0 + this.f_130889_;
            $$0 = 31 * $$0 + (this.f_130890_ != null ? this.f_130890_.hashCode() : 0);
            return $$0;
        }

        public ItemStack m_130898_() {
            if (this.f_130891_ == null) {
                this.f_130891_ = new ItemStack(this.f_130888_, this.f_130889_);
                if (this.f_130890_ != null) {
                    this.f_130891_.m_41751_(this.f_130890_);
                }
            }
            return this.f_130891_;
        }

        private static ItemStackInfo m_130906_(JsonElement p_130907_) {
            if (p_130907_.isJsonPrimitive()) {
                return new ItemStackInfo(Registry.f_122827_.m_7745_(new ResourceLocation(p_130907_.getAsString())), 1, null);
            }
            JsonObject $$1 = GsonHelper.m_13918_(p_130907_, "item");
            Item $$2 = Registry.f_122827_.m_7745_(new ResourceLocation(GsonHelper.m_13906_($$1, "id")));
            int $$3 = GsonHelper.m_13824_($$1, "count", 1);
            if ($$1.has("tag")) {
                String $$4 = GsonHelper.m_13906_($$1, "tag");
                try {
                    CompoundTag $$5 = TagParser.m_129359_($$4);
                    return new ItemStackInfo($$2, $$3, $$5);
                }
                catch (CommandSyntaxException $$6) {
                    f_130813_.warn("Failed to parse tag: {}", (Object)$$4, (Object)$$6);
                }
            }
            return new ItemStackInfo($$2, $$3, null);
        }

        @Nullable
        private static ItemStackInfo m_130908_(Component p_130909_) {
            try {
                CompoundTag $$1 = TagParser.m_129359_(p_130909_.getString());
                return new ItemStackInfo(ItemStack.m_41712_($$1));
            }
            catch (CommandSyntaxException $$2) {
                f_130813_.warn("Failed to parse item tag: {}", (Object)p_130909_, (Object)$$2);
                return null;
            }
        }

        private JsonElement m_130905_() {
            JsonObject $$0 = new JsonObject();
            $$0.addProperty("id", Registry.f_122827_.m_7981_(this.f_130888_).toString());
            if (this.f_130889_ != 1) {
                $$0.addProperty("count", (Number)this.f_130889_);
            }
            if (this.f_130890_ != null) {
                $$0.addProperty("tag", this.f_130890_.toString());
            }
            return $$0;
        }
    }

    public static class EntityTooltipInfo {
        public final EntityType<?> f_130871_;
        public final UUID f_130872_;
        @Nullable
        public final Component f_130873_;
        @Nullable
        private List<Component> f_130874_;

        public EntityTooltipInfo(EntityType<?> p_130876_, UUID p_130877_, @Nullable Component p_130878_) {
            this.f_130871_ = p_130876_;
            this.f_130872_ = p_130877_;
            this.f_130873_ = p_130878_;
        }

        @Nullable
        public static EntityTooltipInfo m_130880_(JsonElement p_130881_) {
            if (!p_130881_.isJsonObject()) {
                return null;
            }
            JsonObject $$1 = p_130881_.getAsJsonObject();
            EntityType<?> $$2 = Registry.f_122826_.m_7745_(new ResourceLocation(GsonHelper.m_13906_($$1, "type")));
            UUID $$3 = UUID.fromString(GsonHelper.m_13906_($$1, "id"));
            MutableComponent $$4 = Component.Serializer.m_130691_($$1.get("name"));
            return new EntityTooltipInfo($$2, $$3, $$4);
        }

        @Nullable
        public static EntityTooltipInfo m_130882_(Component p_130883_) {
            try {
                CompoundTag $$1 = TagParser.m_129359_(p_130883_.getString());
                MutableComponent $$2 = Component.Serializer.m_130701_($$1.m_128461_("name"));
                EntityType<?> $$3 = Registry.f_122826_.m_7745_(new ResourceLocation($$1.m_128461_("type")));
                UUID $$4 = UUID.fromString($$1.m_128461_("id"));
                return new EntityTooltipInfo($$3, $$4, $$2);
            }
            catch (Exception $$5) {
                return null;
            }
        }

        public JsonElement m_130879_() {
            JsonObject $$0 = new JsonObject();
            $$0.addProperty("type", Registry.f_122826_.m_7981_(this.f_130871_).toString());
            $$0.addProperty("id", this.f_130872_.toString());
            if (this.f_130873_ != null) {
                $$0.add("name", Component.Serializer.m_130716_(this.f_130873_));
            }
            return $$0;
        }

        public List<Component> m_130884_() {
            if (this.f_130874_ == null) {
                this.f_130874_ = Lists.newArrayList();
                if (this.f_130873_ != null) {
                    this.f_130874_.add(this.f_130873_);
                }
                this.f_130874_.add(Component.m_237110_("gui.entity_tooltip.type", this.f_130871_.m_20676_()));
                this.f_130874_.add(Component.m_237113_(this.f_130872_.toString()));
            }
            return this.f_130874_;
        }

        public boolean equals(Object p_130886_) {
            if (this == p_130886_) {
                return true;
            }
            if (p_130886_ == null || this.getClass() != p_130886_.getClass()) {
                return false;
            }
            EntityTooltipInfo $$1 = (EntityTooltipInfo)p_130886_;
            return this.f_130871_.equals($$1.f_130871_) && this.f_130872_.equals($$1.f_130872_) && Objects.equals(this.f_130873_, $$1.f_130873_);
        }

        public int hashCode() {
            int $$0 = this.f_130871_.hashCode();
            $$0 = 31 * $$0 + this.f_130872_.hashCode();
            $$0 = 31 * $$0 + (this.f_130873_ != null ? this.f_130873_.hashCode() : 0);
            return $$0;
        }
    }
}

