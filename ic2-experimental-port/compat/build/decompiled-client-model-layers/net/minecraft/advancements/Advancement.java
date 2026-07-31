/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.ArrayUtils
 */
package net.minecraft.advancements;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.ArrayUtils;

public class Advancement {
    @Nullable
    private final Advancement f_138298_;
    @Nullable
    private final DisplayInfo f_138299_;
    private final AdvancementRewards f_138300_;
    private final ResourceLocation f_138301_;
    private final Map<String, Criterion> f_138302_;
    private final String[][] f_138303_;
    private final Set<Advancement> f_138304_ = Sets.newLinkedHashSet();
    private final Component f_138305_;

    public Advancement(ResourceLocation p_138307_, @Nullable Advancement p_138308_, @Nullable DisplayInfo p_138309_, AdvancementRewards p_138310_, Map<String, Criterion> p_138311_, String[][] p_138312_) {
        this.f_138301_ = p_138307_;
        this.f_138299_ = p_138309_;
        this.f_138302_ = ImmutableMap.copyOf(p_138311_);
        this.f_138298_ = p_138308_;
        this.f_138300_ = p_138310_;
        this.f_138303_ = p_138312_;
        if (p_138308_ != null) {
            p_138308_.m_138317_(this);
        }
        if (p_138309_ == null) {
            this.f_138305_ = Component.m_237113_(p_138307_.toString());
        } else {
            Component $$6 = p_138309_.m_14977_();
            ChatFormatting $$7 = p_138309_.m_14992_().m_15552_();
            MutableComponent $$8 = ComponentUtils.m_130750_($$6.m_6881_(), Style.f_131099_.m_131140_($$7)).m_130946_("\n").m_7220_(p_138309_.m_14985_());
            MutableComponent $$9 = $$6.m_6881_().m_130938_(p_138316_ -> p_138316_.m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, $$8)));
            this.f_138305_ = ComponentUtils.m_130748_($$9).m_130940_($$7);
        }
    }

    public Builder m_138313_() {
        return new Builder(this.f_138298_ == null ? null : this.f_138298_.m_138327_(), this.f_138299_, this.f_138300_, this.f_138302_, this.f_138303_);
    }

    @Nullable
    public Advancement m_138319_() {
        return this.f_138298_;
    }

    @Nullable
    public DisplayInfo m_138320_() {
        return this.f_138299_;
    }

    public AdvancementRewards m_138321_() {
        return this.f_138300_;
    }

    public String toString() {
        return "SimpleAdvancement{id=" + this.m_138327_() + ", parent=" + (Comparable)(this.f_138298_ == null ? "null" : this.f_138298_.m_138327_()) + ", display=" + this.f_138299_ + ", rewards=" + this.f_138300_ + ", criteria=" + this.f_138302_ + ", requirements=" + Arrays.deepToString((Object[])this.f_138303_) + "}";
    }

    public Iterable<Advancement> m_138322_() {
        return this.f_138304_;
    }

    public Map<String, Criterion> m_138325_() {
        return this.f_138302_;
    }

    public int m_138326_() {
        return this.f_138303_.length;
    }

    public void m_138317_(Advancement p_138318_) {
        this.f_138304_.add(p_138318_);
    }

    public ResourceLocation m_138327_() {
        return this.f_138301_;
    }

    public boolean equals(Object p_138324_) {
        if (this == p_138324_) {
            return true;
        }
        if (!(p_138324_ instanceof Advancement)) {
            return false;
        }
        Advancement $$1 = (Advancement)p_138324_;
        return this.f_138301_.equals($$1.f_138301_);
    }

    public int hashCode() {
        return this.f_138301_.hashCode();
    }

    public String[][] m_138329_() {
        return this.f_138303_;
    }

    public Component m_138330_() {
        return this.f_138305_;
    }

    public static class Builder {
        @Nullable
        private ResourceLocation f_138332_;
        @Nullable
        private Advancement f_138333_;
        @Nullable
        private DisplayInfo f_138334_;
        private AdvancementRewards f_138335_ = AdvancementRewards.f_9978_;
        private Map<String, Criterion> f_138336_ = Maps.newLinkedHashMap();
        @Nullable
        private String[][] f_138337_;
        private RequirementsStrategy f_138338_ = RequirementsStrategy.f_15978_;

        Builder(@Nullable ResourceLocation p_138341_, @Nullable DisplayInfo p_138342_, AdvancementRewards p_138343_, Map<String, Criterion> p_138344_, String[][] p_138345_) {
            this.f_138332_ = p_138341_;
            this.f_138334_ = p_138342_;
            this.f_138335_ = p_138343_;
            this.f_138336_ = p_138344_;
            this.f_138337_ = p_138345_;
        }

        private Builder() {
        }

        public static Builder m_138353_() {
            return new Builder();
        }

        public Builder m_138398_(Advancement p_138399_) {
            this.f_138333_ = p_138399_;
            return this;
        }

        public Builder m_138396_(ResourceLocation p_138397_) {
            this.f_138332_ = p_138397_;
            return this;
        }

        public Builder m_138362_(ItemStack p_138363_, Component p_138364_, Component p_138365_, @Nullable ResourceLocation p_138366_, FrameType p_138367_, boolean p_138368_, boolean p_138369_, boolean p_138370_) {
            return this.m_138358_(new DisplayInfo(p_138363_, p_138364_, p_138365_, p_138366_, p_138367_, p_138368_, p_138369_, p_138370_));
        }

        public Builder m_138371_(ItemLike p_138372_, Component p_138373_, Component p_138374_, @Nullable ResourceLocation p_138375_, FrameType p_138376_, boolean p_138377_, boolean p_138378_, boolean p_138379_) {
            return this.m_138358_(new DisplayInfo(new ItemStack(p_138372_.m_5456_()), p_138373_, p_138374_, p_138375_, p_138376_, p_138377_, p_138378_, p_138379_));
        }

        public Builder m_138358_(DisplayInfo p_138359_) {
            this.f_138334_ = p_138359_;
            return this;
        }

        public Builder m_138354_(AdvancementRewards.Builder p_138355_) {
            return this.m_138356_(p_138355_.m_10004_());
        }

        public Builder m_138356_(AdvancementRewards p_138357_) {
            this.f_138335_ = p_138357_;
            return this;
        }

        public Builder m_138386_(String p_138387_, CriterionTriggerInstance p_138388_) {
            return this.m_138383_(p_138387_, new Criterion(p_138388_));
        }

        public Builder m_138383_(String p_138384_, Criterion p_138385_) {
            if (this.f_138336_.containsKey(p_138384_)) {
                throw new IllegalArgumentException("Duplicate criterion " + p_138384_);
            }
            this.f_138336_.put(p_138384_, p_138385_);
            return this;
        }

        public Builder m_138360_(RequirementsStrategy p_138361_) {
            this.f_138338_ = p_138361_;
            return this;
        }

        public Builder m_143951_(String[][] p_143952_) {
            this.f_138337_ = p_143952_;
            return this;
        }

        public boolean m_138392_(Function<ResourceLocation, Advancement> p_138393_) {
            if (this.f_138332_ == null) {
                return true;
            }
            if (this.f_138333_ == null) {
                this.f_138333_ = p_138393_.apply(this.f_138332_);
            }
            return this.f_138333_ != null;
        }

        public Advancement m_138403_(ResourceLocation p_138404_) {
            if (!this.m_138392_(p_138407_ -> null)) {
                throw new IllegalStateException("Tried to build incomplete advancement!");
            }
            if (this.f_138337_ == null) {
                this.f_138337_ = this.f_138338_.m_15985_(this.f_138336_.keySet());
            }
            return new Advancement(p_138404_, this.f_138333_, this.f_138334_, this.f_138335_, this.f_138336_, this.f_138337_);
        }

        public Advancement m_138389_(Consumer<Advancement> p_138390_, String p_138391_) {
            Advancement $$2 = this.m_138403_(new ResourceLocation(p_138391_));
            p_138390_.accept($$2);
            return $$2;
        }

        public JsonObject m_138400_() {
            if (this.f_138337_ == null) {
                this.f_138337_ = this.f_138338_.m_15985_(this.f_138336_.keySet());
            }
            JsonObject $$0 = new JsonObject();
            if (this.f_138333_ != null) {
                $$0.addProperty("parent", this.f_138333_.m_138327_().toString());
            } else if (this.f_138332_ != null) {
                $$0.addProperty("parent", this.f_138332_.toString());
            }
            if (this.f_138334_ != null) {
                $$0.add("display", this.f_138334_.m_14998_());
            }
            $$0.add("rewards", this.f_138335_.m_9997_());
            JsonObject $$1 = new JsonObject();
            for (Map.Entry<String, Criterion> $$2 : this.f_138336_.entrySet()) {
                $$1.add($$2.getKey(), $$2.getValue().m_11425_());
            }
            $$0.add("criteria", (JsonElement)$$1);
            JsonArray $$3 = new JsonArray();
            for (String[] $$4 : this.f_138337_) {
                JsonArray $$5 = new JsonArray();
                for (String $$6 : $$4) {
                    $$5.add($$6);
                }
                $$3.add((JsonElement)$$5);
            }
            $$0.add("requirements", (JsonElement)$$3);
            return $$0;
        }

        public void m_138394_(FriendlyByteBuf p_138395_) {
            if (this.f_138337_ == null) {
                this.f_138337_ = this.f_138338_.m_15985_(this.f_138336_.keySet());
            }
            p_138395_.m_236821_(this.f_138332_, FriendlyByteBuf::m_130085_);
            p_138395_.m_236821_(this.f_138334_, (p_214831_, p_214832_) -> p_214832_.m_14983_((FriendlyByteBuf)((Object)p_214831_)));
            Criterion.m_11420_(this.f_138336_, p_138395_);
            p_138395_.m_130130_(this.f_138337_.length);
            for (String[] $$1 : this.f_138337_) {
                p_138395_.m_130130_($$1.length);
                for (String $$2 : $$1) {
                    p_138395_.m_130070_($$2);
                }
            }
        }

        public String toString() {
            return "Task Advancement{parentId=" + this.f_138332_ + ", display=" + this.f_138334_ + ", rewards=" + this.f_138335_ + ", criteria=" + this.f_138336_ + ", requirements=" + Arrays.deepToString((Object[])this.f_138337_) + "}";
        }

        public static Builder m_138380_(JsonObject p_138381_, DeserializationContext p_138382_) {
            ResourceLocation $$2 = p_138381_.has("parent") ? new ResourceLocation(GsonHelper.m_13906_(p_138381_, "parent")) : null;
            DisplayInfo $$3 = p_138381_.has("display") ? DisplayInfo.m_14981_(GsonHelper.m_13930_(p_138381_, "display")) : null;
            AdvancementRewards $$4 = p_138381_.has("rewards") ? AdvancementRewards.m_9991_(GsonHelper.m_13930_(p_138381_, "rewards")) : AdvancementRewards.f_9978_;
            Map<String, Criterion> $$5 = Criterion.m_11426_(GsonHelper.m_13930_(p_138381_, "criteria"), p_138382_);
            if ($$5.isEmpty()) {
                throw new JsonSyntaxException("Advancement criteria cannot be empty");
            }
            JsonArray $$6 = GsonHelper.m_13832_(p_138381_, "requirements", new JsonArray());
            String[][] $$7 = new String[$$6.size()][];
            for (int $$8 = 0; $$8 < $$6.size(); ++$$8) {
                JsonArray $$9 = GsonHelper.m_13924_($$6.get($$8), "requirements[" + $$8 + "]");
                $$7[$$8] = new String[$$9.size()];
                for (int $$10 = 0; $$10 < $$9.size(); ++$$10) {
                    $$7[$$8][$$10] = GsonHelper.m_13805_($$9.get($$10), "requirements[" + $$8 + "][" + $$10 + "]");
                }
            }
            if ($$7.length == 0) {
                $$7 = new String[$$5.size()][];
                int $$11 = 0;
                for (String $$12 : $$5.keySet()) {
                    $$7[$$11++] = new String[]{$$12};
                }
            }
            for (String[] $$13 : $$7) {
                if ($$13.length == 0 && $$5.isEmpty()) {
                    throw new JsonSyntaxException("Requirement entry cannot be empty");
                }
                String[] stringArray = $$13;
                int n = stringArray.length;
                for (int i = 0; i < n; ++i) {
                    String $$14 = stringArray[i];
                    if ($$5.containsKey($$14)) continue;
                    throw new JsonSyntaxException("Unknown required criterion '" + $$14 + "'");
                }
            }
            for (String $$15 : $$5.keySet()) {
                boolean $$16 = false;
                for (Object[] objectArray : $$7) {
                    if (!ArrayUtils.contains((Object[])objectArray, (Object)$$15)) continue;
                    $$16 = true;
                    break;
                }
                if ($$16) continue;
                throw new JsonSyntaxException("Criterion '" + $$15 + "' isn't a requirement for completion. This isn't supported behaviour, all criteria must be required.");
            }
            return new Builder($$2, $$3, $$4, $$5, $$7);
        }

        public static Builder m_138401_(FriendlyByteBuf p_138402_) {
            ResourceLocation $$1 = (ResourceLocation)p_138402_.m_236868_(FriendlyByteBuf::m_130281_);
            DisplayInfo $$2 = (DisplayInfo)p_138402_.m_236868_(DisplayInfo::m_14988_);
            Map<String, Criterion> $$3 = Criterion.m_11431_(p_138402_);
            String[][] $$4 = new String[p_138402_.m_130242_()][];
            for (int $$5 = 0; $$5 < $$4.length; ++$$5) {
                $$4[$$5] = new String[p_138402_.m_130242_()];
                for (int $$6 = 0; $$6 < $$4[$$5].length; ++$$6) {
                    $$4[$$5][$$6] = p_138402_.m_130277_();
                }
            }
            return new Builder($$1, $$2, AdvancementRewards.f_9978_, $$3, $$4);
        }

        public Map<String, Criterion> m_138405_() {
            return this.f_138336_;
        }
    }
}

