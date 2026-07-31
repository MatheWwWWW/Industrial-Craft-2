/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.raid;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;

public class Raids
extends SavedData {
    private static final String f_150234_ = "raids";
    private final Map<Integer, Raid> f_37951_ = Maps.newHashMap();
    private final ServerLevel f_37952_;
    private int f_37953_;
    private int f_37954_;

    public Raids(ServerLevel p_37956_) {
        this.f_37952_ = p_37956_;
        this.f_37953_ = 1;
        this.m_77762_();
    }

    public Raid m_37958_(int p_37959_) {
        return this.f_37951_.get(p_37959_);
    }

    public void m_37957_() {
        ++this.f_37954_;
        Iterator<Raid> $$0 = this.f_37951_.values().iterator();
        while ($$0.hasNext()) {
            Raid $$1 = $$0.next();
            if (this.f_37952_.m_46469_().m_46207_(GameRules.f_46154_)) {
                $$1.m_37774_();
            }
            if ($$1.m_37762_()) {
                $$0.remove();
                this.m_77762_();
                continue;
            }
            $$1.m_37775_();
        }
        if (this.f_37954_ % 200 == 0) {
            this.m_77762_();
        }
        DebugPackets.m_133688_(this.f_37952_, this.f_37951_.values());
    }

    public static boolean m_37965_(Raider p_37966_, Raid p_37967_) {
        if (p_37966_ != null && p_37967_ != null && p_37967_.m_37769_() != null) {
            return p_37966_.m_6084_() && p_37966_.m_37882_() && p_37966_.m_21216_() <= 2400 && p_37966_.f_19853_.m_6042_() == p_37967_.m_37769_().m_6042_();
        }
        return false;
    }

    @Nullable
    public Raid m_37963_(ServerPlayer p_37964_) {
        BlockPos $$9;
        if (p_37964_.m_5833_()) {
            return null;
        }
        if (this.f_37952_.m_46469_().m_46207_(GameRules.f_46154_)) {
            return null;
        }
        DimensionType $$1 = p_37964_.f_19853_.m_6042_();
        if (!$$1.m_63963_()) {
            return null;
        }
        BlockPos $$2 = p_37964_.m_20183_();
        List<PoiRecord> $$3 = this.f_37952_.m_8904_().m_27181_(p_219845_ -> p_219845_.m_203656_(PoiTypeTags.f_215876_), $$2, 64, PoiManager.Occupancy.IS_OCCUPIED).toList();
        int $$4 = 0;
        Vec3 $$5 = Vec3.f_82478_;
        for (PoiRecord $$6 : $$3) {
            BlockPos $$7 = $$6.m_27257_();
            $$5 = $$5.m_82520_($$7.m_123341_(), $$7.m_123342_(), $$7.m_123343_());
            ++$$4;
        }
        if ($$4 > 0) {
            $$5 = $$5.m_82490_(1.0 / (double)$$4);
            BlockPos $$8 = new BlockPos($$5);
        } else {
            $$9 = $$2;
        }
        Raid $$10 = this.m_37960_(p_37964_.m_9236_(), $$9);
        boolean $$11 = false;
        if (!$$10.m_37770_()) {
            if (!this.f_37951_.containsKey($$10.m_37781_())) {
                this.f_37951_.put($$10.m_37781_(), $$10);
            }
            $$11 = true;
        } else if ($$10.m_37773_() < $$10.m_37772_()) {
            $$11 = true;
        } else {
            p_37964_.m_21195_(MobEffects.f_19594_);
            p_37964_.f_8906_.m_9829_(new ClientboundEntityEventPacket(p_37964_, 43));
        }
        if ($$11) {
            $$10.m_37728_(p_37964_);
            p_37964_.f_8906_.m_9829_(new ClientboundEntityEventPacket(p_37964_, 43));
            if (!$$10.m_37757_()) {
                p_37964_.m_36220_(Stats.f_12980_);
                CriteriaTriggers.f_10558_.m_222618_(p_37964_);
            }
        }
        this.m_77762_();
        return $$10;
    }

    private Raid m_37960_(ServerLevel p_37961_, BlockPos p_37962_) {
        Raid $$2 = p_37961_.m_8832_(p_37962_);
        return $$2 != null ? $$2 : new Raid(this.m_37977_(), p_37961_, p_37962_);
    }

    public static Raids m_150235_(ServerLevel p_150236_, CompoundTag p_150237_) {
        Raids $$2 = new Raids(p_150236_);
        $$2.f_37953_ = p_150237_.m_128451_("NextAvailableID");
        $$2.f_37954_ = p_150237_.m_128451_("Tick");
        ListTag $$3 = p_150237_.m_128437_("Raids", 10);
        for (int $$4 = 0; $$4 < $$3.size(); ++$$4) {
            CompoundTag $$5 = $$3.m_128728_($$4);
            Raid $$6 = new Raid(p_150236_, $$5);
            $$2.f_37951_.put($$6.m_37781_(), $$6);
        }
        return $$2;
    }

    @Override
    public CompoundTag m_7176_(CompoundTag p_37976_) {
        p_37976_.m_128405_("NextAvailableID", this.f_37953_);
        p_37976_.m_128405_("Tick", this.f_37954_);
        ListTag $$1 = new ListTag();
        for (Raid $$2 : this.f_37951_.values()) {
            CompoundTag $$3 = new CompoundTag();
            $$2.m_37747_($$3);
            $$1.add($$3);
        }
        p_37976_.m_128365_("Raids", $$1);
        return p_37976_;
    }

    public static String m_211596_(Holder<DimensionType> p_211597_) {
        if (p_211597_.m_203565_(BuiltinDimensionTypes.f_223540_)) {
            return "raids_end";
        }
        return f_150234_;
    }

    private int m_37977_() {
        return ++this.f_37953_;
    }

    @Nullable
    public Raid m_37970_(BlockPos p_37971_, int p_37972_) {
        Raid $$2 = null;
        double $$3 = p_37972_;
        for (Raid $$4 : this.f_37951_.values()) {
            double $$5 = $$4.m_37780_().m_123331_(p_37971_);
            if (!$$4.m_37782_() || !($$5 < $$3)) continue;
            $$2 = $$4;
            $$3 = $$5;
        }
        return $$2;
    }
}

