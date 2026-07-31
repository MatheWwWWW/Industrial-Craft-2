/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  com.google.gson.TypeAdapterFactory
 *  com.google.gson.stream.JsonReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.stream.JsonReader;
import com.mojang.brigadier.Message;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.BlockDataSource;
import net.minecraft.network.chat.contents.DataSource;
import net.minecraft.network.chat.contents.EntityDataSource;
import net.minecraft.network.chat.contents.KeybindContents;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.chat.contents.NbtContents;
import net.minecraft.network.chat.contents.ScoreContents;
import net.minecraft.network.chat.contents.SelectorContents;
import net.minecraft.network.chat.contents.StorageDataSource;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.LowerCaseEnumTypeAdapterFactory;

public interface Component
extends Message,
FormattedText {
    public Style m_7383_();

    public ComponentContents m_214077_();

    @Override
    default public String getString() {
        return FormattedText.super.getString();
    }

    default public String m_130668_(int p_130669_) {
        StringBuilder $$1 = new StringBuilder();
        this.m_5651_(p_130673_ -> {
            int $$3 = p_130669_ - $$1.length();
            if ($$3 <= 0) {
                return f_130759_;
            }
            $$1.append(p_130673_.length() <= $$3 ? p_130673_ : p_130673_.substring(0, $$3));
            return Optional.empty();
        });
        return $$1.toString();
    }

    public List<Component> m_7360_();

    default public MutableComponent m_6879_() {
        return MutableComponent.m_237204_(this.m_214077_());
    }

    default public MutableComponent m_6881_() {
        return new MutableComponent(this.m_214077_(), new ArrayList<Component>(this.m_7360_()), this.m_7383_());
    }

    public FormattedCharSequence m_7532_();

    @Override
    default public <T> Optional<T> m_7451_(FormattedText.StyledContentConsumer<T> p_130679_, Style p_130680_) {
        Style $$2 = this.m_7383_().m_131146_(p_130680_);
        Optional<T> $$3 = this.m_214077_().m_213724_(p_130679_, $$2);
        if ($$3.isPresent()) {
            return $$3;
        }
        for (Component $$4 : this.m_7360_()) {
            Optional<T> $$5 = $$4.m_7451_(p_130679_, $$2);
            if (!$$5.isPresent()) continue;
            return $$5;
        }
        return Optional.empty();
    }

    @Override
    default public <T> Optional<T> m_5651_(FormattedText.ContentConsumer<T> p_130677_) {
        Optional<T> $$1 = this.m_214077_().m_213874_(p_130677_);
        if ($$1.isPresent()) {
            return $$1;
        }
        for (Component $$2 : this.m_7360_()) {
            Optional<T> $$3 = $$2.m_5651_(p_130677_);
            if (!$$3.isPresent()) continue;
            return $$3;
        }
        return Optional.empty();
    }

    default public List<Component> m_240407_() {
        return this.m_178405_(Style.f_131099_);
    }

    default public List<Component> m_178405_(Style p_178406_) {
        ArrayList $$1 = Lists.newArrayList();
        this.m_7451_((p_178403_, p_178404_) -> {
            if (!p_178404_.isEmpty()) {
                $$1.add(Component.m_237113_(p_178404_).m_130948_(p_178403_));
            }
            return Optional.empty();
        }, p_178406_);
        return $$1;
    }

    default public boolean m_240452_(Component p_240571_) {
        List<Component> $$2;
        if (this.equals(p_240571_)) {
            return true;
        }
        List<Component> $$1 = this.m_240407_();
        return Collections.indexOfSubList($$1, $$2 = p_240571_.m_178405_(this.m_7383_())) != -1;
    }

    public static Component m_130674_(@Nullable String p_130675_) {
        return p_130675_ != null ? Component.m_237113_(p_130675_) : CommonComponents.f_237098_;
    }

    public static MutableComponent m_237113_(String p_237114_) {
        return MutableComponent.m_237204_(new LiteralContents(p_237114_));
    }

    public static MutableComponent m_237115_(String p_237116_) {
        return MutableComponent.m_237204_(new TranslatableContents(p_237116_));
    }

    public static MutableComponent m_237110_(String p_237111_, Object ... p_237112_) {
        return MutableComponent.m_237204_(new TranslatableContents(p_237111_, p_237112_));
    }

    public static MutableComponent m_237119_() {
        return MutableComponent.m_237204_(ComponentContents.f_237124_);
    }

    public static MutableComponent m_237117_(String p_237118_) {
        return MutableComponent.m_237204_(new KeybindContents(p_237118_));
    }

    public static MutableComponent m_237105_(String p_237106_, boolean p_237107_, Optional<Component> p_237108_, DataSource p_237109_) {
        return MutableComponent.m_237204_(new NbtContents(p_237106_, p_237107_, p_237108_, p_237109_));
    }

    public static MutableComponent m_237099_(String p_237100_, String p_237101_) {
        return MutableComponent.m_237204_(new ScoreContents(p_237100_, p_237101_));
    }

    public static MutableComponent m_237102_(String p_237103_, Optional<Component> p_237104_) {
        return MutableComponent.m_237204_(new SelectorContents(p_237103_, p_237104_));
    }

    public static class Serializer
    implements JsonDeserializer<MutableComponent>,
    JsonSerializer<Component> {
        private static final Gson f_130685_ = Util.m_137537_(() -> {
            GsonBuilder $$0 = new GsonBuilder();
            $$0.disableHtmlEscaping();
            $$0.registerTypeHierarchyAdapter(Component.class, (Object)new Serializer());
            $$0.registerTypeHierarchyAdapter(Style.class, (Object)new Style.Serializer());
            $$0.registerTypeAdapterFactory((TypeAdapterFactory)new LowerCaseEnumTypeAdapterFactory());
            return $$0.create();
        });
        private static final Field f_130686_ = Util.m_137537_(() -> {
            try {
                new JsonReader((Reader)new StringReader(""));
                Field $$0 = JsonReader.class.getDeclaredField("pos");
                $$0.setAccessible(true);
                return $$0;
            }
            catch (NoSuchFieldException $$1) {
                throw new IllegalStateException("Couldn't get field 'pos' for JsonReader", $$1);
            }
        });
        private static final Field f_130687_ = Util.m_137537_(() -> {
            try {
                new JsonReader((Reader)new StringReader(""));
                Field $$0 = JsonReader.class.getDeclaredField("lineStart");
                $$0.setAccessible(true);
                return $$0;
            }
            catch (NoSuchFieldException $$1) {
                throw new IllegalStateException("Couldn't get field 'lineStart' for JsonReader", $$1);
            }
        });

        /*
         * WARNING - void declaration
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public MutableComponent deserialize(JsonElement p_130694_, Type p_130695_, JsonDeserializationContext p_130696_) throws JsonParseException {
            if (p_130694_.isJsonPrimitive()) {
                return Component.m_237113_(p_130694_.getAsString());
            }
            if (p_130694_.isJsonObject()) {
                void $$26;
                JsonObject $$3 = p_130694_.getAsJsonObject();
                if ($$3.has("text")) {
                    String $$4 = GsonHelper.m_13906_($$3, "text");
                    MutableComponent $$5 = $$4.isEmpty() ? Component.m_237119_() : Component.m_237113_($$4);
                } else if ($$3.has("translate")) {
                    String $$6 = GsonHelper.m_13906_($$3, "translate");
                    if ($$3.has("with")) {
                        JsonArray $$7 = GsonHelper.m_13933_($$3, "with");
                        Object[] $$8 = new Object[$$7.size()];
                        for (int $$9 = 0; $$9 < $$8.length; ++$$9) {
                            $$8[$$9] = Serializer.m_237120_(this.deserialize($$7.get($$9), p_130695_, p_130696_));
                        }
                        MutableComponent $$10 = Component.m_237110_($$6, $$8);
                    } else {
                        MutableComponent $$11 = Component.m_237115_($$6);
                    }
                } else if ($$3.has("score")) {
                    JsonObject $$12 = GsonHelper.m_13930_($$3, "score");
                    if (!$$12.has("name") || !$$12.has("objective")) throw new JsonParseException("A score component needs a least a name and an objective");
                    MutableComponent $$13 = Component.m_237099_(GsonHelper.m_13906_($$12, "name"), GsonHelper.m_13906_($$12, "objective"));
                } else if ($$3.has("selector")) {
                    Optional<Component> $$15 = this.m_178415_(p_130695_, p_130696_, $$3);
                    MutableComponent $$16 = Component.m_237102_(GsonHelper.m_13906_($$3, "selector"), $$15);
                } else if ($$3.has("keybind")) {
                    MutableComponent $$17 = Component.m_237117_(GsonHelper.m_13906_($$3, "keybind"));
                } else {
                    void $$24;
                    if (!$$3.has("nbt")) throw new JsonParseException("Don't know how to turn " + p_130694_ + " into a Component");
                    String $$18 = GsonHelper.m_13906_($$3, "nbt");
                    Optional<Component> $$19 = this.m_178415_(p_130695_, p_130696_, $$3);
                    boolean $$20 = GsonHelper.m_13855_($$3, "interpret", false);
                    if ($$3.has("block")) {
                        BlockDataSource $$21 = new BlockDataSource(GsonHelper.m_13906_($$3, "block"));
                    } else if ($$3.has("entity")) {
                        EntityDataSource $$22 = new EntityDataSource(GsonHelper.m_13906_($$3, "entity"));
                    } else {
                        if (!$$3.has("storage")) throw new JsonParseException("Don't know how to turn " + p_130694_ + " into a Component");
                        StorageDataSource $$23 = new StorageDataSource(new ResourceLocation(GsonHelper.m_13906_($$3, "storage")));
                    }
                    MutableComponent $$25 = Component.m_237105_($$18, $$20, $$19, (DataSource)$$24);
                }
                if ($$3.has("extra")) {
                    JsonArray $$27 = GsonHelper.m_13933_($$3, "extra");
                    if ($$27.size() <= 0) throw new JsonParseException("Unexpected empty array of components");
                    for (int $$28 = 0; $$28 < $$27.size(); ++$$28) {
                        $$26.m_7220_(this.deserialize($$27.get($$28), p_130695_, p_130696_));
                    }
                }
                $$26.m_6270_((Style)p_130696_.deserialize(p_130694_, Style.class));
                return $$26;
            }
            if (!p_130694_.isJsonArray()) throw new JsonParseException("Don't know how to turn " + p_130694_ + " into a Component");
            JsonArray $$29 = p_130694_.getAsJsonArray();
            MutableComponent $$30 = null;
            for (JsonElement $$31 : $$29) {
                MutableComponent $$32 = this.deserialize($$31, $$31.getClass(), p_130696_);
                if ($$30 == null) {
                    $$30 = $$32;
                    continue;
                }
                $$30.m_7220_($$32);
            }
            return $$30;
        }

        private static Object m_237120_(Object p_237121_) {
            ComponentContents $$2;
            Component $$1;
            if (p_237121_ instanceof Component && ($$1 = (Component)p_237121_).m_7383_().m_131179_() && $$1.m_7360_().isEmpty() && ($$2 = $$1.m_214077_()) instanceof LiteralContents) {
                LiteralContents $$3 = (LiteralContents)$$2;
                return $$3.f_237368_();
            }
            return p_237121_;
        }

        private Optional<Component> m_178415_(Type p_178416_, JsonDeserializationContext p_178417_, JsonObject p_178418_) {
            if (p_178418_.has("separator")) {
                return Optional.of(this.deserialize(p_178418_.get("separator"), p_178416_, p_178417_));
            }
            return Optional.empty();
        }

        private void m_130709_(Style p_130710_, JsonObject p_130711_, JsonSerializationContext p_130712_) {
            JsonElement $$3 = p_130712_.serialize((Object)p_130710_);
            if ($$3.isJsonObject()) {
                JsonObject $$4 = (JsonObject)$$3;
                for (Map.Entry $$5 : $$4.entrySet()) {
                    p_130711_.add((String)$$5.getKey(), (JsonElement)$$5.getValue());
                }
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public JsonElement serialize(Component p_130706_, Type p_130707_, JsonSerializationContext p_130708_) {
            ComponentContents $$6;
            JsonObject $$3 = new JsonObject();
            if (!p_130706_.m_7383_().m_131179_()) {
                this.m_130709_(p_130706_.m_7383_(), $$3, p_130708_);
            }
            if (!p_130706_.m_7360_().isEmpty()) {
                JsonArray $$4 = new JsonArray();
                for (Component $$5 : p_130706_.m_7360_()) {
                    $$4.add(this.serialize($$5, (Type)((Object)Component.class), p_130708_));
                }
                $$3.add("extra", (JsonElement)$$4);
            }
            if (($$6 = p_130706_.m_214077_()) == ComponentContents.f_237124_) {
                $$3.addProperty("text", "");
                return $$3;
            } else if ($$6 instanceof LiteralContents) {
                LiteralContents $$7 = (LiteralContents)$$6;
                $$3.addProperty("text", $$7.f_237368_());
                return $$3;
            } else if ($$6 instanceof TranslatableContents) {
                TranslatableContents $$8 = (TranslatableContents)$$6;
                $$3.addProperty("translate", $$8.m_237508_());
                if ($$8.m_237523_().length <= 0) return $$3;
                JsonArray $$9 = new JsonArray();
                for (Object $$10 : $$8.m_237523_()) {
                    if ($$10 instanceof Component) {
                        $$9.add(this.serialize((Component)$$10, $$10.getClass(), p_130708_));
                        continue;
                    }
                    $$9.add((JsonElement)new JsonPrimitive(String.valueOf($$10)));
                }
                $$3.add("with", (JsonElement)$$9);
                return $$3;
            } else if ($$6 instanceof ScoreContents) {
                ScoreContents $$11 = (ScoreContents)$$6;
                JsonObject $$12 = new JsonObject();
                $$12.addProperty("name", $$11.m_237440_());
                $$12.addProperty("objective", $$11.m_237453_());
                $$3.add("score", (JsonElement)$$12);
                return $$3;
            } else if ($$6 instanceof SelectorContents) {
                SelectorContents $$13 = (SelectorContents)$$6;
                $$3.addProperty("selector", $$13.m_237466_());
                this.m_178411_(p_130708_, $$3, $$13.m_237479_());
                return $$3;
            } else if ($$6 instanceof KeybindContents) {
                KeybindContents $$14 = (KeybindContents)$$6;
                $$3.addProperty("keybind", $$14.m_237348_());
                return $$3;
            } else {
                if (!($$6 instanceof NbtContents)) throw new IllegalArgumentException("Don't know how to serialize " + $$6 + " as a Component");
                NbtContents $$15 = (NbtContents)$$6;
                $$3.addProperty("nbt", $$15.m_237399_());
                $$3.addProperty("interpret", Boolean.valueOf($$15.m_237426_()));
                this.m_178411_(p_130708_, $$3, $$15.m_237427_());
                DataSource $$16 = $$15.m_237428_();
                if ($$16 instanceof BlockDataSource) {
                    BlockDataSource $$17 = (BlockDataSource)$$16;
                    $$3.addProperty("block", $$17.f_237309_());
                    return $$3;
                } else if ($$16 instanceof EntityDataSource) {
                    EntityDataSource $$18 = (EntityDataSource)$$16;
                    $$3.addProperty("entity", $$18.f_237327_());
                    return $$3;
                } else {
                    if (!($$16 instanceof StorageDataSource)) throw new IllegalArgumentException("Don't know how to serialize " + $$6 + " as a Component");
                    StorageDataSource $$19 = (StorageDataSource)$$16;
                    $$3.addProperty("storage", $$19.f_237484_().toString());
                }
            }
            return $$3;
        }

        private void m_178411_(JsonSerializationContext p_178412_, JsonObject p_178413_, Optional<Component> p_178414_) {
            p_178414_.ifPresent(p_178410_ -> p_178413_.add("separator", this.serialize((Component)p_178410_, (Type)p_178410_.getClass(), p_178412_)));
        }

        public static String m_130703_(Component p_130704_) {
            return f_130685_.toJson((Object)p_130704_);
        }

        public static String m_237122_(Component p_237123_) {
            return GsonHelper.m_216216_(Serializer.m_130716_(p_237123_));
        }

        public static JsonElement m_130716_(Component p_130717_) {
            return f_130685_.toJsonTree((Object)p_130717_);
        }

        @Nullable
        public static MutableComponent m_130701_(String p_130702_) {
            return GsonHelper.m_13798_(f_130685_, p_130702_, MutableComponent.class, false);
        }

        @Nullable
        public static MutableComponent m_130691_(JsonElement p_130692_) {
            return (MutableComponent)f_130685_.fromJson(p_130692_, MutableComponent.class);
        }

        @Nullable
        public static MutableComponent m_130714_(String p_130715_) {
            return GsonHelper.m_13798_(f_130685_, p_130715_, MutableComponent.class, true);
        }

        public static MutableComponent m_130699_(com.mojang.brigadier.StringReader p_130700_) {
            try {
                JsonReader $$1 = new JsonReader((Reader)new StringReader(p_130700_.getRemaining()));
                $$1.setLenient(false);
                MutableComponent $$2 = (MutableComponent)f_130685_.getAdapter(MutableComponent.class).read($$1);
                p_130700_.setCursor(p_130700_.getCursor() + Serializer.m_130697_($$1));
                return $$2;
            }
            catch (IOException | StackOverflowError $$3) {
                throw new JsonParseException($$3);
            }
        }

        private static int m_130697_(JsonReader p_130698_) {
            try {
                return f_130686_.getInt(p_130698_) - f_130687_.getInt(p_130698_) + 1;
            }
            catch (IllegalAccessException $$1) {
                throw new IllegalStateException("Couldn't read position of JsonReader", $$1);
            }
        }

        public /* synthetic */ JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((Component)object, type, jsonSerializationContext);
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

