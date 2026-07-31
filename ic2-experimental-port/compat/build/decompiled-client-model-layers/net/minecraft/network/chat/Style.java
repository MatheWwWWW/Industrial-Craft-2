/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  com.google.gson.JsonSyntaxException
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.ResourceLocationException;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class Style {
    public static final Style f_131099_ = new Style(null, null, null, null, null, null, null, null, null, null);
    public static final Codec<Style> f_237254_ = RecordCodecBuilder.create(p_237256_ -> p_237256_.group((App)TextColor.f_237295_.optionalFieldOf("color").forGetter(p_237281_ -> Optional.ofNullable(p_237281_.f_131101_)), (App)Codec.BOOL.optionalFieldOf("bold").forGetter(p_237279_ -> Optional.ofNullable(p_237279_.f_131102_)), (App)Codec.BOOL.optionalFieldOf("italic").forGetter(p_237277_ -> Optional.ofNullable(p_237277_.f_131103_)), (App)Codec.BOOL.optionalFieldOf("underlined").forGetter(p_237275_ -> Optional.ofNullable(p_237275_.f_131104_)), (App)Codec.BOOL.optionalFieldOf("strikethrough").forGetter(p_237273_ -> Optional.ofNullable(p_237273_.f_131105_)), (App)Codec.BOOL.optionalFieldOf("obfuscated").forGetter(p_237271_ -> Optional.ofNullable(p_237271_.f_131106_)), (App)Codec.STRING.optionalFieldOf("insertion").forGetter(p_237269_ -> Optional.ofNullable(p_237269_.f_131109_)), (App)ResourceLocation.f_135803_.optionalFieldOf("font").forGetter(p_237267_ -> Optional.ofNullable(p_237267_.f_131110_))).apply((Applicative)p_237256_, Style::m_237257_));
    public static final ResourceLocation f_131100_ = new ResourceLocation("minecraft", "default");
    @Nullable
    final TextColor f_131101_;
    @Nullable
    final Boolean f_131102_;
    @Nullable
    final Boolean f_131103_;
    @Nullable
    final Boolean f_131104_;
    @Nullable
    final Boolean f_131105_;
    @Nullable
    final Boolean f_131106_;
    @Nullable
    final ClickEvent f_131107_;
    @Nullable
    final HoverEvent f_131108_;
    @Nullable
    final String f_131109_;
    @Nullable
    final ResourceLocation f_131110_;

    private static Style m_237257_(Optional<TextColor> p_237258_, Optional<Boolean> p_237259_, Optional<Boolean> p_237260_, Optional<Boolean> p_237261_, Optional<Boolean> p_237262_, Optional<Boolean> p_237263_, Optional<String> p_237264_, Optional<ResourceLocation> p_237265_) {
        return new Style(p_237258_.orElse(null), p_237259_.orElse(null), p_237260_.orElse(null), p_237261_.orElse(null), p_237262_.orElse(null), p_237263_.orElse(null), null, null, p_237264_.orElse(null), p_237265_.orElse(null));
    }

    Style(@Nullable TextColor p_131113_, @Nullable Boolean p_131114_, @Nullable Boolean p_131115_, @Nullable Boolean p_131116_, @Nullable Boolean p_131117_, @Nullable Boolean p_131118_, @Nullable ClickEvent p_131119_, @Nullable HoverEvent p_131120_, @Nullable String p_131121_, @Nullable ResourceLocation p_131122_) {
        this.f_131101_ = p_131113_;
        this.f_131102_ = p_131114_;
        this.f_131103_ = p_131115_;
        this.f_131104_ = p_131116_;
        this.f_131105_ = p_131117_;
        this.f_131106_ = p_131118_;
        this.f_131107_ = p_131119_;
        this.f_131108_ = p_131120_;
        this.f_131109_ = p_131121_;
        this.f_131110_ = p_131122_;
    }

    @Nullable
    public TextColor m_131135_() {
        return this.f_131101_;
    }

    public boolean m_131154_() {
        return this.f_131102_ == Boolean.TRUE;
    }

    public boolean m_131161_() {
        return this.f_131103_ == Boolean.TRUE;
    }

    public boolean m_131168_() {
        return this.f_131105_ == Boolean.TRUE;
    }

    public boolean m_131171_() {
        return this.f_131104_ == Boolean.TRUE;
    }

    public boolean m_131176_() {
        return this.f_131106_ == Boolean.TRUE;
    }

    public boolean m_131179_() {
        return this == f_131099_;
    }

    @Nullable
    public ClickEvent m_131182_() {
        return this.f_131107_;
    }

    @Nullable
    public HoverEvent m_131186_() {
        return this.f_131108_;
    }

    @Nullable
    public String m_131189_() {
        return this.f_131109_;
    }

    public ResourceLocation m_131192_() {
        return this.f_131110_ != null ? this.f_131110_ : f_131100_;
    }

    public Style m_131148_(@Nullable TextColor p_131149_) {
        return new Style(p_131149_, this.f_131102_, this.f_131103_, this.f_131104_, this.f_131105_, this.f_131106_, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
    }

    public Style m_131140_(@Nullable ChatFormatting p_131141_) {
        return this.m_131148_(p_131141_ != null ? TextColor.m_131270_(p_131141_) : null);
    }

    public Style m_178520_(int p_178521_) {
        return this.m_131148_(TextColor.m_131266_(p_178521_));
    }

    public Style m_131136_(@Nullable Boolean p_131137_) {
        return new Style(this.f_131101_, p_131137_, this.f_131103_, this.f_131104_, this.f_131105_, this.f_131106_, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
    }

    public Style m_131155_(@Nullable Boolean p_131156_) {
        return new Style(this.f_131101_, this.f_131102_, p_131156_, this.f_131104_, this.f_131105_, this.f_131106_, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
    }

    public Style m_131162_(@Nullable Boolean p_131163_) {
        return new Style(this.f_131101_, this.f_131102_, this.f_131103_, p_131163_, this.f_131105_, this.f_131106_, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
    }

    public Style m_178522_(@Nullable Boolean p_178523_) {
        return new Style(this.f_131101_, this.f_131102_, this.f_131103_, this.f_131104_, p_178523_, this.f_131106_, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
    }

    public Style m_178524_(@Nullable Boolean p_178525_) {
        return new Style(this.f_131101_, this.f_131102_, this.f_131103_, this.f_131104_, this.f_131105_, p_178525_, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
    }

    public Style m_131142_(@Nullable ClickEvent p_131143_) {
        return new Style(this.f_131101_, this.f_131102_, this.f_131103_, this.f_131104_, this.f_131105_, this.f_131106_, p_131143_, this.f_131108_, this.f_131109_, this.f_131110_);
    }

    public Style m_131144_(@Nullable HoverEvent p_131145_) {
        return new Style(this.f_131101_, this.f_131102_, this.f_131103_, this.f_131104_, this.f_131105_, this.f_131106_, this.f_131107_, p_131145_, this.f_131109_, this.f_131110_);
    }

    public Style m_131138_(@Nullable String p_131139_) {
        return new Style(this.f_131101_, this.f_131102_, this.f_131103_, this.f_131104_, this.f_131105_, this.f_131106_, this.f_131107_, this.f_131108_, p_131139_, this.f_131110_);
    }

    public Style m_131150_(@Nullable ResourceLocation p_131151_) {
        return new Style(this.f_131101_, this.f_131102_, this.f_131103_, this.f_131104_, this.f_131105_, this.f_131106_, this.f_131107_, this.f_131108_, this.f_131109_, p_131151_);
    }

    public Style m_131157_(ChatFormatting p_131158_) {
        TextColor $$1 = this.f_131101_;
        Boolean $$2 = this.f_131102_;
        Boolean $$3 = this.f_131103_;
        Boolean $$4 = this.f_131105_;
        Boolean $$5 = this.f_131104_;
        Boolean $$6 = this.f_131106_;
        switch (p_131158_) {
            case OBFUSCATED: {
                $$6 = true;
                break;
            }
            case BOLD: {
                $$2 = true;
                break;
            }
            case STRIKETHROUGH: {
                $$4 = true;
                break;
            }
            case UNDERLINE: {
                $$5 = true;
                break;
            }
            case ITALIC: {
                $$3 = true;
                break;
            }
            case RESET: {
                return f_131099_;
            }
            default: {
                $$1 = TextColor.m_131270_(p_131158_);
            }
        }
        return new Style($$1, $$2, $$3, $$5, $$4, $$6, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
    }

    public Style m_131164_(ChatFormatting p_131165_) {
        TextColor $$1 = this.f_131101_;
        Boolean $$2 = this.f_131102_;
        Boolean $$3 = this.f_131103_;
        Boolean $$4 = this.f_131105_;
        Boolean $$5 = this.f_131104_;
        Boolean $$6 = this.f_131106_;
        switch (p_131165_) {
            case OBFUSCATED: {
                $$6 = true;
                break;
            }
            case BOLD: {
                $$2 = true;
                break;
            }
            case STRIKETHROUGH: {
                $$4 = true;
                break;
            }
            case UNDERLINE: {
                $$5 = true;
                break;
            }
            case ITALIC: {
                $$3 = true;
                break;
            }
            case RESET: {
                return f_131099_;
            }
            default: {
                $$6 = false;
                $$2 = false;
                $$4 = false;
                $$5 = false;
                $$3 = false;
                $$1 = TextColor.m_131270_(p_131165_);
            }
        }
        return new Style($$1, $$2, $$3, $$5, $$4, $$6, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
    }

    public Style m_131152_(ChatFormatting ... p_131153_) {
        TextColor $$1 = this.f_131101_;
        Boolean $$2 = this.f_131102_;
        Boolean $$3 = this.f_131103_;
        Boolean $$4 = this.f_131105_;
        Boolean $$5 = this.f_131104_;
        Boolean $$6 = this.f_131106_;
        block8: for (ChatFormatting $$7 : p_131153_) {
            switch ($$7) {
                case OBFUSCATED: {
                    $$6 = true;
                    continue block8;
                }
                case BOLD: {
                    $$2 = true;
                    continue block8;
                }
                case STRIKETHROUGH: {
                    $$4 = true;
                    continue block8;
                }
                case UNDERLINE: {
                    $$5 = true;
                    continue block8;
                }
                case ITALIC: {
                    $$3 = true;
                    continue block8;
                }
                case RESET: {
                    return f_131099_;
                }
                default: {
                    $$1 = TextColor.m_131270_($$7);
                }
            }
        }
        return new Style($$1, $$2, $$3, $$5, $$4, $$6, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
    }

    public Style m_131146_(Style p_131147_) {
        if (this == f_131099_) {
            return p_131147_;
        }
        if (p_131147_ == f_131099_) {
            return this;
        }
        return new Style(this.f_131101_ != null ? this.f_131101_ : p_131147_.f_131101_, this.f_131102_ != null ? this.f_131102_ : p_131147_.f_131102_, this.f_131103_ != null ? this.f_131103_ : p_131147_.f_131103_, this.f_131104_ != null ? this.f_131104_ : p_131147_.f_131104_, this.f_131105_ != null ? this.f_131105_ : p_131147_.f_131105_, this.f_131106_ != null ? this.f_131106_ : p_131147_.f_131106_, this.f_131107_ != null ? this.f_131107_ : p_131147_.f_131107_, this.f_131108_ != null ? this.f_131108_ : p_131147_.f_131108_, this.f_131109_ != null ? this.f_131109_ : p_131147_.f_131109_, this.f_131110_ != null ? this.f_131110_ : p_131147_.f_131110_);
    }

    public String toString() {
        final StringBuilder $$0 = new StringBuilder("{");
        class Collector {
            private boolean f_237284_;

            Collector() {
            }

            private void m_237288_() {
                if (this.f_237284_) {
                    $$0.append(',');
                }
                this.f_237284_ = true;
            }

            void m_237289_(String p_237290_, @Nullable Boolean p_237291_) {
                if (p_237291_ != null) {
                    this.m_237288_();
                    if (!p_237291_.booleanValue()) {
                        $$0.append('!');
                    }
                    $$0.append(p_237290_);
                }
            }

            void m_237292_(String p_237293_, @Nullable Object p_237294_) {
                if (p_237294_ != null) {
                    this.m_237288_();
                    $$0.append(p_237293_);
                    $$0.append('=');
                    $$0.append(p_237294_);
                }
            }
        }
        Collector $$1 = new Collector();
        $$1.m_237292_("color", this.f_131101_);
        $$1.m_237289_("bold", this.f_131102_);
        $$1.m_237289_("italic", this.f_131103_);
        $$1.m_237289_("underlined", this.f_131104_);
        $$1.m_237289_("strikethrough", this.f_131105_);
        $$1.m_237289_("obfuscated", this.f_131106_);
        $$1.m_237292_("clickEvent", this.f_131107_);
        $$1.m_237292_("hoverEvent", this.f_131108_);
        $$1.m_237292_("insertion", this.f_131109_);
        $$1.m_237292_("font", this.f_131110_);
        $$0.append("}");
        return $$0.toString();
    }

    public boolean equals(Object p_131175_) {
        if (this == p_131175_) {
            return true;
        }
        if (p_131175_ instanceof Style) {
            Style $$1 = (Style)p_131175_;
            return this.m_131154_() == $$1.m_131154_() && Objects.equals(this.m_131135_(), $$1.m_131135_()) && this.m_131161_() == $$1.m_131161_() && this.m_131176_() == $$1.m_131176_() && this.m_131168_() == $$1.m_131168_() && this.m_131171_() == $$1.m_131171_() && Objects.equals(this.m_131182_(), $$1.m_131182_()) && Objects.equals(this.m_131186_(), $$1.m_131186_()) && Objects.equals(this.m_131189_(), $$1.m_131189_()) && Objects.equals(this.m_131192_(), $$1.m_131192_());
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.f_131101_, this.f_131102_, this.f_131103_, this.f_131104_, this.f_131105_, this.f_131106_, this.f_131107_, this.f_131108_, this.f_131109_);
    }

    public static class Serializer
    implements JsonDeserializer<Style>,
    JsonSerializer<Style> {
        @Nullable
        public Style deserialize(JsonElement p_131200_, Type p_131201_, JsonDeserializationContext p_131202_) throws JsonParseException {
            if (p_131200_.isJsonObject()) {
                JsonObject $$3 = p_131200_.getAsJsonObject();
                if ($$3 == null) {
                    return null;
                }
                Boolean $$4 = Serializer.m_131205_($$3, "bold");
                Boolean $$5 = Serializer.m_131205_($$3, "italic");
                Boolean $$6 = Serializer.m_131205_($$3, "underlined");
                Boolean $$7 = Serializer.m_131205_($$3, "strikethrough");
                Boolean $$8 = Serializer.m_131205_($$3, "obfuscated");
                TextColor $$9 = Serializer.m_131222_($$3);
                String $$10 = Serializer.m_131216_($$3);
                ClickEvent $$11 = Serializer.m_131214_($$3);
                HoverEvent $$12 = Serializer.m_131212_($$3);
                ResourceLocation $$13 = Serializer.m_131203_($$3);
                return new Style($$9, $$4, $$5, $$6, $$7, $$8, $$11, $$12, $$10, $$13);
            }
            return null;
        }

        @Nullable
        private static ResourceLocation m_131203_(JsonObject p_131204_) {
            if (p_131204_.has("font")) {
                String $$1 = GsonHelper.m_13906_(p_131204_, "font");
                try {
                    return new ResourceLocation($$1);
                }
                catch (ResourceLocationException $$2) {
                    throw new JsonSyntaxException("Invalid font name: " + $$1);
                }
            }
            return null;
        }

        @Nullable
        private static HoverEvent m_131212_(JsonObject p_131213_) {
            JsonObject $$1;
            HoverEvent $$2;
            if (p_131213_.has("hoverEvent") && ($$2 = HoverEvent.m_130821_($$1 = GsonHelper.m_13930_(p_131213_, "hoverEvent"))) != null && $$2.m_130820_().m_130847_()) {
                return $$2;
            }
            return null;
        }

        @Nullable
        private static ClickEvent m_131214_(JsonObject p_131215_) {
            if (p_131215_.has("clickEvent")) {
                JsonObject $$1 = GsonHelper.m_13930_(p_131215_, "clickEvent");
                String $$2 = GsonHelper.m_13851_($$1, "action", null);
                ClickEvent.Action $$3 = $$2 == null ? null : ClickEvent.Action.m_130645_($$2);
                String $$4 = GsonHelper.m_13851_($$1, "value", null);
                if ($$3 != null && $$4 != null && $$3.m_130644_()) {
                    return new ClickEvent($$3, $$4);
                }
            }
            return null;
        }

        @Nullable
        private static String m_131216_(JsonObject p_131217_) {
            return GsonHelper.m_13851_(p_131217_, "insertion", null);
        }

        @Nullable
        private static TextColor m_131222_(JsonObject p_131223_) {
            if (p_131223_.has("color")) {
                String $$1 = GsonHelper.m_13906_(p_131223_, "color");
                return TextColor.m_131268_($$1);
            }
            return null;
        }

        @Nullable
        private static Boolean m_131205_(JsonObject p_131206_, String p_131207_) {
            if (p_131206_.has(p_131207_)) {
                return p_131206_.get(p_131207_).getAsBoolean();
            }
            return null;
        }

        @Nullable
        public JsonElement serialize(Style p_131209_, Type p_131210_, JsonSerializationContext p_131211_) {
            if (p_131209_.m_131179_()) {
                return null;
            }
            JsonObject $$3 = new JsonObject();
            if (p_131209_.f_131102_ != null) {
                $$3.addProperty("bold", p_131209_.f_131102_);
            }
            if (p_131209_.f_131103_ != null) {
                $$3.addProperty("italic", p_131209_.f_131103_);
            }
            if (p_131209_.f_131104_ != null) {
                $$3.addProperty("underlined", p_131209_.f_131104_);
            }
            if (p_131209_.f_131105_ != null) {
                $$3.addProperty("strikethrough", p_131209_.f_131105_);
            }
            if (p_131209_.f_131106_ != null) {
                $$3.addProperty("obfuscated", p_131209_.f_131106_);
            }
            if (p_131209_.f_131101_ != null) {
                $$3.addProperty("color", p_131209_.f_131101_.m_131274_());
            }
            if (p_131209_.f_131109_ != null) {
                $$3.add("insertion", p_131211_.serialize((Object)p_131209_.f_131109_));
            }
            if (p_131209_.f_131107_ != null) {
                JsonObject $$4 = new JsonObject();
                $$4.addProperty("action", p_131209_.f_131107_.m_130622_().m_130649_());
                $$4.addProperty("value", p_131209_.f_131107_.m_130623_());
                $$3.add("clickEvent", (JsonElement)$$4);
            }
            if (p_131209_.f_131108_ != null) {
                $$3.add("hoverEvent", (JsonElement)p_131209_.f_131108_.m_130825_());
            }
            if (p_131209_.f_131110_ != null) {
                $$3.addProperty("font", p_131209_.f_131110_.toString());
            }
            return $$3;
        }

        @Nullable
        public /* synthetic */ JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((Style)object, type, jsonSerializationContext);
        }

        @Nullable
        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

