/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.internal.Streams
 *  com.google.gson.stream.JsonReader
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  org.apache.commons.io.FileUtils
 *  org.slf4j.Logger
 */
package net.minecraft.stats;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.stats.StatsCounter;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

public class ServerStatsCounter
extends StatsCounter {
    private static final Logger f_12809_ = LogUtils.getLogger();
    private final MinecraftServer f_12810_;
    private final File f_12811_;
    private final Set<Stat<?>> f_12812_ = Sets.newHashSet();

    public ServerStatsCounter(MinecraftServer p_12816_, File p_12817_) {
        this.f_12810_ = p_12816_;
        this.f_12811_ = p_12817_;
        if (p_12817_.isFile()) {
            try {
                this.m_12832_(p_12816_.m_129933_(), FileUtils.readFileToString((File)p_12817_));
            }
            catch (IOException $$2) {
                f_12809_.error("Couldn't read statistics file {}", (Object)p_12817_, (Object)$$2);
            }
            catch (JsonParseException $$3) {
                f_12809_.error("Couldn't parse statistics file {}", (Object)p_12817_, (Object)$$3);
            }
        }
    }

    public void m_12818_() {
        try {
            FileUtils.writeStringToFile((File)this.f_12811_, (String)this.m_12845_());
        }
        catch (IOException $$0) {
            f_12809_.error("Couldn't save stats", (Throwable)$$0);
        }
    }

    @Override
    public void m_6085_(Player p_12827_, Stat<?> p_12828_, int p_12829_) {
        super.m_6085_(p_12827_, p_12828_, p_12829_);
        this.f_12812_.add(p_12828_);
    }

    private Set<Stat<?>> m_12851_() {
        HashSet $$0 = Sets.newHashSet(this.f_12812_);
        this.f_12812_.clear();
        return $$0;
    }

    public void m_12832_(DataFixer p_12833_, String p_12834_) {
        try (JsonReader $$2 = new JsonReader((Reader)new StringReader(p_12834_));){
            $$2.setLenient(false);
            JsonElement $$3 = Streams.parse((JsonReader)$$2);
            if ($$3.isJsonNull()) {
                f_12809_.error("Unable to parse Stat data from {}", (Object)this.f_12811_);
                return;
            }
            CompoundTag $$4 = ServerStatsCounter.m_12830_($$3.getAsJsonObject());
            if (!$$4.m_128425_("DataVersion", 99)) {
                $$4.m_128405_("DataVersion", 1343);
            }
            if (($$4 = NbtUtils.m_129213_(p_12833_, DataFixTypes.STATS, $$4, $$4.m_128451_("DataVersion"))).m_128425_("stats", 10)) {
                CompoundTag $$5 = $$4.m_128469_("stats");
                for (String $$6 : $$5.m_128431_()) {
                    if (!$$5.m_128425_($$6, 10)) continue;
                    Util.m_137521_(Registry.f_122867_.m_6612_(new ResourceLocation($$6)), p_12844_ -> {
                        CompoundTag $$3 = $$5.m_128469_($$6);
                        for (String $$4 : $$3.m_128431_()) {
                            if ($$3.m_128425_($$4, 99)) {
                                Util.m_137521_(this.m_12823_((StatType)p_12844_, $$4), p_144252_ -> this.f_13013_.put(p_144252_, $$3.m_128451_($$4)), () -> f_12809_.warn("Invalid statistic in {}: Don't know what {} is", (Object)this.f_12811_, (Object)$$4));
                                continue;
                            }
                            f_12809_.warn("Invalid statistic value in {}: Don't know what {} is for key {}", new Object[]{this.f_12811_, $$3.m_128423_($$4), $$4});
                        }
                    }, () -> f_12809_.warn("Invalid statistic type in {}: Don't know what {} is", (Object)this.f_12811_, (Object)$$6));
                }
            }
        }
        catch (JsonParseException | IOException $$7) {
            f_12809_.error("Unable to parse Stat data from {}", (Object)this.f_12811_, (Object)$$7);
        }
    }

    private <T> Optional<Stat<T>> m_12823_(StatType<T> p_12824_, String p_12825_) {
        return Optional.ofNullable(ResourceLocation.m_135820_(p_12825_)).flatMap(p_12824_.m_12893_()::m_6612_).map(p_12824_::m_12902_);
    }

    private static CompoundTag m_12830_(JsonObject p_12831_) {
        CompoundTag $$1 = new CompoundTag();
        for (Map.Entry $$2 : p_12831_.entrySet()) {
            JsonPrimitive $$4;
            JsonElement $$3 = (JsonElement)$$2.getValue();
            if ($$3.isJsonObject()) {
                $$1.m_128365_((String)$$2.getKey(), ServerStatsCounter.m_12830_($$3.getAsJsonObject()));
                continue;
            }
            if (!$$3.isJsonPrimitive() || !($$4 = $$3.getAsJsonPrimitive()).isNumber()) continue;
            $$1.m_128405_((String)$$2.getKey(), $$4.getAsInt());
        }
        return $$1;
    }

    protected String m_12845_() {
        HashMap $$0 = Maps.newHashMap();
        for (Object $$1 : this.f_13013_.object2IntEntrySet()) {
            Stat stat = (Stat)$$1.getKey();
            $$0.computeIfAbsent(stat.m_12859_(), p_12822_ -> new JsonObject()).addProperty(ServerStatsCounter.m_12846_(stat).toString(), (Number)$$1.getIntValue());
        }
        JsonObject $$3 = new JsonObject();
        for (Map.Entry entry : $$0.entrySet()) {
            $$3.add(Registry.f_122867_.m_7981_((StatType)entry.getKey()).toString(), (JsonElement)entry.getValue());
        }
        JsonObject $$5 = new JsonObject();
        $$5.add("stats", (JsonElement)$$3);
        $$5.addProperty("DataVersion", (Number)SharedConstants.m_183709_().getWorldVersion());
        return $$5.toString();
    }

    private static <T> ResourceLocation m_12846_(Stat<T> p_12847_) {
        return p_12847_.m_12859_().m_12893_().m_7981_(p_12847_.m_12867_());
    }

    public void m_12850_() {
        this.f_12812_.addAll((Collection<Stat<?>>)this.f_13013_.keySet());
    }

    public void m_12819_(ServerPlayer p_12820_) {
        Object2IntOpenHashMap $$1 = new Object2IntOpenHashMap();
        for (Stat<?> $$2 : this.m_12851_()) {
            $$1.put($$2, this.m_13015_($$2));
        }
        p_12820_.f_8906_.m_9829_(new ClientboundAwardStatsPacket((Object2IntMap<Stat<?>>)$$1));
    }
}

