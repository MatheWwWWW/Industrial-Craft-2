/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.google.common.collect.ComparisonChain
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.mojang.logging.LogUtils
 *  org.apache.commons.lang3.builder.EqualsBuilder
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.dto;

import com.google.common.base.Joiner;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.dto.PlayerInfo;
import com.mojang.realmsclient.dto.RealmsServerPing;
import com.mojang.realmsclient.dto.RealmsServerPlayerList;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.util.JsonUtils;
import com.mojang.realmsclient.util.RealmsUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.slf4j.Logger;

public class RealmsServer
extends ValueObject {
    private static final Logger f_87491_ = LogUtils.getLogger();
    public long f_87473_;
    public String f_87474_;
    public String f_87475_;
    public String f_87476_;
    public State f_87477_;
    public String f_87478_;
    public String f_87479_;
    public List<PlayerInfo> f_87480_;
    public Map<Integer, RealmsWorldOptions> f_87481_;
    public boolean f_87482_;
    public boolean f_87483_;
    public int f_87484_;
    public WorldType f_87485_;
    public int f_87486_;
    public String f_87487_;
    public int f_87488_;
    public String f_87489_;
    public RealmsServerPing f_87490_ = new RealmsServerPing();

    public String m_87494_() {
        return this.f_87476_;
    }

    public String m_87512_() {
        return this.f_87475_;
    }

    public String m_87517_() {
        return this.f_87487_;
    }

    public void m_87508_(String p_87509_) {
        this.f_87475_ = p_87509_;
    }

    public void m_87515_(String p_87516_) {
        this.f_87476_ = p_87516_;
    }

    public void m_87506_(RealmsServerPlayerList p_87507_) {
        ArrayList $$1 = Lists.newArrayList();
        int $$2 = 0;
        for (String $$3 : p_87507_.f_87583_) {
            if ($$3.equals(Minecraft.m_91087_().m_91094_().m_92545_())) continue;
            String $$4 = "";
            try {
                $$4 = RealmsUtil.m_90221_($$3);
            }
            catch (Exception $$5) {
                f_87491_.error("Could not get name for {}", (Object)$$3, (Object)$$5);
                continue;
            }
            $$1.add($$4);
            ++$$2;
        }
        this.f_87490_.f_87579_ = String.valueOf($$2);
        this.f_87490_.f_87580_ = Joiner.on((char)'\n').join((Iterable)$$1);
    }

    public static RealmsServer m_87499_(JsonObject p_87500_) {
        RealmsServer $$1 = new RealmsServer();
        try {
            $$1.f_87473_ = JsonUtils.m_90157_("id", p_87500_, -1L);
            $$1.f_87474_ = JsonUtils.m_90161_("remoteSubscriptionId", p_87500_, null);
            $$1.f_87475_ = JsonUtils.m_90161_("name", p_87500_, null);
            $$1.f_87476_ = JsonUtils.m_90161_("motd", p_87500_, null);
            $$1.f_87477_ = RealmsServer.m_87525_(JsonUtils.m_90161_("state", p_87500_, State.CLOSED.name()));
            $$1.f_87478_ = JsonUtils.m_90161_("owner", p_87500_, null);
            if (p_87500_.get("players") != null && p_87500_.get("players").isJsonArray()) {
                $$1.f_87480_ = RealmsServer.m_87497_(p_87500_.get("players").getAsJsonArray());
                RealmsServer.m_87504_($$1);
            } else {
                $$1.f_87480_ = Lists.newArrayList();
            }
            $$1.f_87484_ = JsonUtils.m_90153_("daysLeft", p_87500_, 0);
            $$1.f_87482_ = JsonUtils.m_90165_("expired", p_87500_, false);
            $$1.f_87483_ = JsonUtils.m_90165_("expiredTrial", p_87500_, false);
            $$1.f_87485_ = RealmsServer.m_87529_(JsonUtils.m_90161_("worldType", p_87500_, WorldType.NORMAL.name()));
            $$1.f_87479_ = JsonUtils.m_90161_("ownerUUID", p_87500_, "");
            $$1.f_87481_ = p_87500_.get("slots") != null && p_87500_.get("slots").isJsonArray() ? RealmsServer.m_87513_(p_87500_.get("slots").getAsJsonArray()) : RealmsServer.m_87524_();
            $$1.f_87487_ = JsonUtils.m_90161_("minigameName", p_87500_, null);
            $$1.f_87486_ = JsonUtils.m_90153_("activeSlot", p_87500_, -1);
            $$1.f_87488_ = JsonUtils.m_90153_("minigameId", p_87500_, -1);
            $$1.f_87489_ = JsonUtils.m_90161_("minigameImage", p_87500_, null);
        }
        catch (Exception $$2) {
            f_87491_.error("Could not parse McoServer: {}", (Object)$$2.getMessage());
        }
        return $$1;
    }

    private static void m_87504_(RealmsServer p_87505_) {
        p_87505_.f_87480_.sort((p_87502_, p_87503_) -> ComparisonChain.start().compareFalseFirst(p_87503_.m_87460_(), p_87502_.m_87460_()).compare((Comparable)((Object)p_87502_.m_87447_().toLowerCase(Locale.ROOT)), (Comparable)((Object)p_87503_.m_87447_().toLowerCase(Locale.ROOT))).result());
    }

    private static List<PlayerInfo> m_87497_(JsonArray p_87498_) {
        ArrayList $$1 = Lists.newArrayList();
        for (JsonElement $$2 : p_87498_) {
            try {
                JsonObject $$3 = $$2.getAsJsonObject();
                PlayerInfo $$4 = new PlayerInfo();
                $$4.m_87448_(JsonUtils.m_90161_("name", $$3, null));
                $$4.m_87453_(JsonUtils.m_90161_("uuid", $$3, null));
                $$4.m_87450_(JsonUtils.m_90165_("operator", $$3, false));
                $$4.m_87455_(JsonUtils.m_90165_("accepted", $$3, false));
                $$4.m_87458_(JsonUtils.m_90165_("online", $$3, false));
                $$1.add($$4);
            }
            catch (Exception exception) {}
        }
        return $$1;
    }

    private static Map<Integer, RealmsWorldOptions> m_87513_(JsonArray p_87514_) {
        HashMap $$1 = Maps.newHashMap();
        for (JsonElement $$2 : p_87514_) {
            try {
                RealmsWorldOptions $$7;
                JsonObject $$3 = $$2.getAsJsonObject();
                JsonParser $$4 = new JsonParser();
                JsonElement $$5 = $$4.parse($$3.get("options").getAsString());
                if ($$5 == null) {
                    RealmsWorldOptions $$6 = RealmsWorldOptions.m_87625_();
                } else {
                    $$7 = RealmsWorldOptions.m_87628_($$5.getAsJsonObject());
                }
                int $$8 = JsonUtils.m_90153_("slotId", $$3, -1);
                $$1.put($$8, $$7);
            }
            catch (Exception exception) {}
        }
        for (int $$9 = 1; $$9 <= 3; ++$$9) {
            if ($$1.containsKey($$9)) continue;
            $$1.put($$9, RealmsWorldOptions.m_87632_());
        }
        return $$1;
    }

    private static Map<Integer, RealmsWorldOptions> m_87524_() {
        HashMap $$0 = Maps.newHashMap();
        $$0.put(1, RealmsWorldOptions.m_87632_());
        $$0.put(2, RealmsWorldOptions.m_87632_());
        $$0.put(3, RealmsWorldOptions.m_87632_());
        return $$0;
    }

    public static RealmsServer m_87518_(String p_87519_) {
        try {
            return RealmsServer.m_87499_(new JsonParser().parse(p_87519_).getAsJsonObject());
        }
        catch (Exception $$1) {
            f_87491_.error("Could not parse McoServer: {}", (Object)$$1.getMessage());
            return new RealmsServer();
        }
    }

    private static State m_87525_(String p_87526_) {
        try {
            return State.valueOf(p_87526_);
        }
        catch (Exception $$1) {
            return State.CLOSED;
        }
    }

    private static WorldType m_87529_(String p_87530_) {
        try {
            return WorldType.valueOf(p_87530_);
        }
        catch (Exception $$1) {
            return WorldType.NORMAL;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.f_87473_, this.f_87475_, this.f_87476_, this.f_87477_, this.f_87478_, this.f_87482_});
    }

    public boolean equals(Object p_87528_) {
        if (p_87528_ == null) {
            return false;
        }
        if (p_87528_ == this) {
            return true;
        }
        if (p_87528_.getClass() != this.getClass()) {
            return false;
        }
        RealmsServer $$1 = (RealmsServer)p_87528_;
        return new EqualsBuilder().append(this.f_87473_, $$1.f_87473_).append((Object)this.f_87475_, (Object)$$1.f_87475_).append((Object)this.f_87476_, (Object)$$1.f_87476_).append((Object)this.f_87477_, (Object)$$1.f_87477_).append((Object)this.f_87478_, (Object)$$1.f_87478_).append(this.f_87482_, $$1.f_87482_).append((Object)this.f_87485_, (Object)this.f_87485_).isEquals();
    }

    public RealmsServer clone() {
        RealmsServer $$0 = new RealmsServer();
        $$0.f_87473_ = this.f_87473_;
        $$0.f_87474_ = this.f_87474_;
        $$0.f_87475_ = this.f_87475_;
        $$0.f_87476_ = this.f_87476_;
        $$0.f_87477_ = this.f_87477_;
        $$0.f_87478_ = this.f_87478_;
        $$0.f_87480_ = this.f_87480_;
        $$0.f_87481_ = this.m_87510_(this.f_87481_);
        $$0.f_87482_ = this.f_87482_;
        $$0.f_87483_ = this.f_87483_;
        $$0.f_87484_ = this.f_87484_;
        $$0.f_87490_ = new RealmsServerPing();
        $$0.f_87490_.f_87579_ = this.f_87490_.f_87579_;
        $$0.f_87490_.f_87580_ = this.f_87490_.f_87580_;
        $$0.f_87485_ = this.f_87485_;
        $$0.f_87479_ = this.f_87479_;
        $$0.f_87487_ = this.f_87487_;
        $$0.f_87486_ = this.f_87486_;
        $$0.f_87488_ = this.f_87488_;
        $$0.f_87489_ = this.f_87489_;
        return $$0;
    }

    public Map<Integer, RealmsWorldOptions> m_87510_(Map<Integer, RealmsWorldOptions> p_87511_) {
        HashMap $$1 = Maps.newHashMap();
        for (Map.Entry<Integer, RealmsWorldOptions> $$2 : p_87511_.entrySet()) {
            $$1.put($$2.getKey(), $$2.getValue().clone());
        }
        return $$1;
    }

    public String m_87495_(int p_87496_) {
        return this.f_87475_ + " (" + this.f_87481_.get(p_87496_).m_87626_(p_87496_) + ")";
    }

    public ServerData m_87522_(String p_87523_) {
        return new ServerData(this.f_87475_, p_87523_, false);
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    public static final class State
    extends Enum<State> {
        public static final /* enum */ State CLOSED = new State();
        public static final /* enum */ State OPEN = new State();
        public static final /* enum */ State UNINITIALIZED = new State();
        private static final /* synthetic */ State[] $VALUES;

        public static State[] values() {
            return (State[])$VALUES.clone();
        }

        public static State valueOf(String p_87550_) {
            return Enum.valueOf(State.class, p_87550_);
        }

        private static /* synthetic */ State[] m_167288_() {
            return new State[]{CLOSED, OPEN, UNINITIALIZED};
        }

        static {
            $VALUES = State.m_167288_();
        }
    }

    public static final class WorldType
    extends Enum<WorldType> {
        public static final /* enum */ WorldType NORMAL = new WorldType();
        public static final /* enum */ WorldType MINIGAME = new WorldType();
        public static final /* enum */ WorldType ADVENTUREMAP = new WorldType();
        public static final /* enum */ WorldType EXPERIENCE = new WorldType();
        public static final /* enum */ WorldType INSPIRATION = new WorldType();
        private static final /* synthetic */ WorldType[] $VALUES;

        public static WorldType[] values() {
            return (WorldType[])$VALUES.clone();
        }

        public static WorldType valueOf(String p_87563_) {
            return Enum.valueOf(WorldType.class, p_87563_);
        }

        private static /* synthetic */ WorldType[] m_167289_() {
            return new WorldType[]{NORMAL, MINIGAME, ADVENTUREMAP, EXPERIENCE, INSPIRATION};
        }

        static {
            $VALUES = WorldType.m_167289_();
        }
    }

    public static class McoServerComparator
    implements Comparator<RealmsServer> {
        private final String f_87532_;

        public McoServerComparator(String p_87534_) {
            this.f_87532_ = p_87534_;
        }

        @Override
        public int compare(RealmsServer p_87536_, RealmsServer p_87537_) {
            return ComparisonChain.start().compareTrueFirst(p_87536_.f_87477_ == State.UNINITIALIZED, p_87537_.f_87477_ == State.UNINITIALIZED).compareTrueFirst(p_87536_.f_87483_, p_87537_.f_87483_).compareTrueFirst(p_87536_.f_87478_.equals(this.f_87532_), p_87537_.f_87478_.equals(this.f_87532_)).compareFalseFirst(p_87536_.f_87482_, p_87537_.f_87482_).compareTrueFirst(p_87536_.f_87477_ == State.OPEN, p_87537_.f_87477_ == State.OPEN).compare(p_87536_.f_87473_, p_87537_.f_87473_).result();
        }

        @Override
        public /* synthetic */ int compare(Object object, Object object2) {
            return this.compare((RealmsServer)object, (RealmsServer)object2);
        }
    }
}

