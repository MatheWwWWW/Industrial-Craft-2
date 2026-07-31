/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.common.io.Files
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParseException
 *  com.google.gson.internal.Streams
 *  com.google.gson.reflect.TypeToken
 *  com.google.gson.stream.JsonReader
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.GameRules;
import org.slf4j.Logger;

public class PlayerAdvancements {
    private static final Logger f_135958_ = LogUtils.getLogger();
    private static final int f_179926_ = 2;
    private static final Gson f_135959_ = new GsonBuilder().registerTypeAdapter(AdvancementProgress.class, (Object)new AdvancementProgress.Serializer()).registerTypeAdapter(ResourceLocation.class, (Object)new ResourceLocation.Serializer()).setPrettyPrinting().create();
    private static final TypeToken<Map<ResourceLocation, AdvancementProgress>> f_135960_ = new TypeToken<Map<ResourceLocation, AdvancementProgress>>(){};
    private final DataFixer f_135961_;
    private final PlayerList f_135962_;
    private final File f_135963_;
    private final Map<Advancement, AdvancementProgress> f_135964_ = Maps.newLinkedHashMap();
    private final Set<Advancement> f_135965_ = Sets.newLinkedHashSet();
    private final Set<Advancement> f_135966_ = Sets.newLinkedHashSet();
    private final Set<Advancement> f_135967_ = Sets.newLinkedHashSet();
    private ServerPlayer f_135968_;
    @Nullable
    private Advancement f_135969_;
    private boolean f_135970_ = true;

    public PlayerAdvancements(DataFixer p_135973_, PlayerList p_135974_, ServerAdvancementManager p_135975_, File p_135976_, ServerPlayer p_135977_) {
        this.f_135961_ = p_135973_;
        this.f_135962_ = p_135974_;
        this.f_135963_ = p_135976_;
        this.f_135968_ = p_135977_;
        this.m_136006_(p_135975_);
    }

    public void m_135979_(ServerPlayer p_135980_) {
        this.f_135968_ = p_135980_;
    }

    public void m_135978_() {
        for (CriterionTrigger<?> $$0 : CriteriaTriggers.m_10594_()) {
            $$0.m_5656_(this);
        }
    }

    public void m_135981_(ServerAdvancementManager p_135982_) {
        this.m_135978_();
        this.f_135964_.clear();
        this.f_135965_.clear();
        this.f_135966_.clear();
        this.f_135967_.clear();
        this.f_135970_ = true;
        this.f_135969_ = null;
        this.m_136006_(p_135982_);
    }

    private void m_135994_(ServerAdvancementManager p_135995_) {
        for (Advancement $$1 : p_135995_.m_136028_()) {
            this.m_136004_($$1);
        }
    }

    private void m_136001_() {
        ArrayList $$0 = Lists.newArrayList();
        for (Map.Entry<Advancement, AdvancementProgress> $$1 : this.f_135964_.entrySet()) {
            if (!$$1.getValue().m_8193_()) continue;
            $$0.add($$1.getKey());
            this.f_135967_.add($$1.getKey());
        }
        for (Advancement $$2 : $$0) {
            this.m_136010_($$2);
        }
    }

    private void m_136002_(ServerAdvancementManager p_136003_) {
        for (Advancement $$1 : p_136003_.m_136028_()) {
            if (!$$1.m_138325_().isEmpty()) continue;
            this.m_135988_($$1, "");
            $$1.m_138321_().m_9989_(this.f_135968_);
        }
    }

    private void m_136006_(ServerAdvancementManager p_136007_) {
        if (this.f_135963_.isFile()) {
            try (JsonReader $$1 = new JsonReader((Reader)new StringReader(Files.toString((File)this.f_135963_, (Charset)StandardCharsets.UTF_8)));){
                $$1.setLenient(false);
                Dynamic $$2 = new Dynamic((DynamicOps)JsonOps.INSTANCE, (Object)Streams.parse((JsonReader)$$1));
                if (!$$2.get("DataVersion").asNumber().result().isPresent()) {
                    $$2 = $$2.set("DataVersion", $$2.createInt(1343));
                }
                $$2 = this.f_135961_.update(DataFixTypes.ADVANCEMENTS.m_14504_(), $$2, $$2.get("DataVersion").asInt(0), SharedConstants.m_183709_().getWorldVersion());
                $$2 = $$2.remove("DataVersion");
                Map $$3 = (Map)f_135959_.getAdapter(f_135960_).fromJsonTree((JsonElement)$$2.getValue());
                if ($$3 == null) {
                    throw new JsonParseException("Found null for advancements");
                }
                Stream<Map.Entry> $$4 = $$3.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue));
                for (Map.Entry $$5 : $$4.collect(Collectors.toList())) {
                    Advancement $$6 = p_136007_.m_136041_((ResourceLocation)$$5.getKey());
                    if ($$6 == null) {
                        f_135958_.warn("Ignored advancement '{}' in progress file {} - it doesn't exist anymore?", $$5.getKey(), (Object)this.f_135963_);
                        continue;
                    }
                    this.m_135985_($$6, (AdvancementProgress)$$5.getValue());
                }
            }
            catch (JsonParseException $$7) {
                f_135958_.error("Couldn't parse player advancements in {}", (Object)this.f_135963_, (Object)$$7);
            }
            catch (IOException $$8) {
                f_135958_.error("Couldn't access player advancements in {}", (Object)this.f_135963_, (Object)$$8);
            }
        }
        this.m_136002_(p_136007_);
        this.m_136001_();
        this.m_135994_(p_136007_);
    }

    public void m_135991_() {
        HashMap $$0 = Maps.newHashMap();
        for (Map.Entry<Advancement, AdvancementProgress> $$1 : this.f_135964_.entrySet()) {
            AdvancementProgress $$2 = $$1.getValue();
            if (!$$2.m_8206_()) continue;
            $$0.put($$1.getKey().m_138327_(), $$2);
        }
        if (this.f_135963_.getParentFile() != null) {
            this.f_135963_.getParentFile().mkdirs();
        }
        JsonElement $$3 = f_135959_.toJsonTree((Object)$$0);
        $$3.getAsJsonObject().addProperty("DataVersion", (Number)SharedConstants.m_183709_().getWorldVersion());
        try (FileOutputStream $$4 = new FileOutputStream(this.f_135963_);
             OutputStreamWriter $$5 = new OutputStreamWriter((OutputStream)$$4, Charsets.UTF_8.newEncoder());){
            f_135959_.toJson($$3, (Appendable)$$5);
        }
        catch (IOException $$6) {
            f_135958_.error("Couldn't save player advancements to {}", (Object)this.f_135963_, (Object)$$6);
        }
    }

    public boolean m_135988_(Advancement p_135989_, String p_135990_) {
        boolean $$2 = false;
        AdvancementProgress $$3 = this.m_135996_(p_135989_);
        boolean $$4 = $$3.m_8193_();
        if ($$3.m_8196_(p_135990_)) {
            this.m_136008_(p_135989_);
            this.f_135967_.add(p_135989_);
            $$2 = true;
            if (!$$4 && $$3.m_8193_()) {
                p_135989_.m_138321_().m_9989_(this.f_135968_);
                if (p_135989_.m_138320_() != null && p_135989_.m_138320_().m_14996_() && this.f_135968_.f_19853_.m_46469_().m_46207_(GameRules.f_46153_)) {
                    this.f_135962_.m_240416_(Component.m_237110_("chat.type.advancement." + p_135989_.m_138320_().m_14992_().m_15548_(), this.f_135968_.m_5446_(), p_135989_.m_138330_()), false);
                }
            }
        }
        if ($$3.m_8193_()) {
            this.m_136010_(p_135989_);
        }
        return $$2;
    }

    public boolean m_135998_(Advancement p_135999_, String p_136000_) {
        boolean $$2 = false;
        AdvancementProgress $$3 = this.m_135996_(p_135999_);
        if ($$3.m_8209_(p_136000_)) {
            this.m_136004_(p_135999_);
            this.f_135967_.add(p_135999_);
            $$2 = true;
        }
        if (!$$3.m_8206_()) {
            this.m_136010_(p_135999_);
        }
        return $$2;
    }

    private void m_136004_(Advancement p_136005_) {
        AdvancementProgress $$1 = this.m_135996_(p_136005_);
        if ($$1.m_8193_()) {
            return;
        }
        for (Map.Entry<String, Criterion> $$2 : p_136005_.m_138325_().entrySet()) {
            CriterionTrigger<CriterionTriggerInstance> $$5;
            CriterionTriggerInstance $$4;
            CriterionProgress $$3 = $$1.m_8214_($$2.getKey());
            if ($$3 == null || $$3.m_12911_() || ($$4 = $$2.getValue().m_11416_()) == null || ($$5 = CriteriaTriggers.m_10597_($$4.m_7294_())) == null) continue;
            $$5.m_6467_(this, new CriterionTrigger.Listener<CriterionTriggerInstance>($$4, p_136005_, $$2.getKey()));
        }
    }

    private void m_136008_(Advancement p_136009_) {
        AdvancementProgress $$1 = this.m_135996_(p_136009_);
        for (Map.Entry<String, Criterion> $$2 : p_136009_.m_138325_().entrySet()) {
            CriterionTrigger<CriterionTriggerInstance> $$5;
            CriterionTriggerInstance $$4;
            CriterionProgress $$3 = $$1.m_8214_($$2.getKey());
            if ($$3 == null || !$$3.m_12911_() && !$$1.m_8193_() || ($$4 = $$2.getValue().m_11416_()) == null || ($$5 = CriteriaTriggers.m_10597_($$4.m_7294_())) == null) continue;
            $$5.m_6468_(this, new CriterionTrigger.Listener<CriterionTriggerInstance>($$4, p_136009_, $$2.getKey()));
        }
    }

    public void m_135992_(ServerPlayer p_135993_) {
        if (this.f_135970_ || !this.f_135966_.isEmpty() || !this.f_135967_.isEmpty()) {
            HashMap $$1 = Maps.newHashMap();
            LinkedHashSet $$2 = Sets.newLinkedHashSet();
            LinkedHashSet $$3 = Sets.newLinkedHashSet();
            for (Advancement $$4 : this.f_135967_) {
                if (!this.f_135965_.contains($$4)) continue;
                $$1.put($$4.m_138327_(), this.f_135964_.get($$4));
            }
            for (Advancement $$5 : this.f_135966_) {
                if (this.f_135965_.contains($$5)) {
                    $$2.add($$5);
                    continue;
                }
                $$3.add($$5.m_138327_());
            }
            if (this.f_135970_ || !$$1.isEmpty() || !$$2.isEmpty() || !$$3.isEmpty()) {
                p_135993_.f_8906_.m_9829_(new ClientboundUpdateAdvancementsPacket(this.f_135970_, $$2, $$3, $$1));
                this.f_135966_.clear();
                this.f_135967_.clear();
            }
        }
        this.f_135970_ = false;
    }

    public void m_135983_(@Nullable Advancement p_135984_) {
        Advancement $$1 = this.f_135969_;
        this.f_135969_ = p_135984_ != null && p_135984_.m_138319_() == null && p_135984_.m_138320_() != null ? p_135984_ : null;
        if ($$1 != this.f_135969_) {
            this.f_135968_.f_8906_.m_9829_(new ClientboundSelectAdvancementsTabPacket(this.f_135969_ == null ? null : this.f_135969_.m_138327_()));
        }
    }

    public AdvancementProgress m_135996_(Advancement p_135997_) {
        AdvancementProgress $$1 = this.f_135964_.get(p_135997_);
        if ($$1 == null) {
            $$1 = new AdvancementProgress();
            this.m_135985_(p_135997_, $$1);
        }
        return $$1;
    }

    private void m_135985_(Advancement p_135986_, AdvancementProgress p_135987_) {
        p_135987_.m_8198_(p_135986_.m_138325_(), p_135986_.m_138329_());
        this.f_135964_.put(p_135986_, p_135987_);
    }

    private void m_136010_(Advancement p_136011_) {
        boolean $$1 = this.m_136012_(p_136011_);
        boolean $$2 = this.f_135965_.contains(p_136011_);
        if ($$1 && !$$2) {
            this.f_135965_.add(p_136011_);
            this.f_135966_.add(p_136011_);
            if (this.f_135964_.containsKey(p_136011_)) {
                this.f_135967_.add(p_136011_);
            }
        } else if (!$$1 && $$2) {
            this.f_135965_.remove(p_136011_);
            this.f_135966_.add(p_136011_);
        }
        if ($$1 != $$2 && p_136011_.m_138319_() != null) {
            this.m_136010_(p_136011_.m_138319_());
        }
        for (Advancement $$3 : p_136011_.m_138322_()) {
            this.m_136010_($$3);
        }
    }

    private boolean m_136012_(Advancement p_136013_) {
        for (int $$1 = 0; p_136013_ != null && $$1 <= 2; p_136013_ = p_136013_.m_138319_(), ++$$1) {
            if ($$1 == 0 && this.m_136014_(p_136013_)) {
                return true;
            }
            if (p_136013_.m_138320_() == null) {
                return false;
            }
            AdvancementProgress $$2 = this.m_135996_(p_136013_);
            if ($$2.m_8193_()) {
                return true;
            }
            if (!p_136013_.m_138320_().m_14997_()) continue;
            return false;
        }
        return false;
    }

    private boolean m_136014_(Advancement p_136015_) {
        AdvancementProgress $$1 = this.m_135996_(p_136015_);
        if ($$1.m_8193_()) {
            return true;
        }
        for (Advancement $$2 : p_136015_.m_138322_()) {
            if (!this.m_136014_($$2)) continue;
            return true;
        }
        return false;
    }
}

