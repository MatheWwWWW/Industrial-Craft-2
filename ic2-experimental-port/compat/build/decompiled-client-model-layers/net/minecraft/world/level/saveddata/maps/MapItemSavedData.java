/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.saveddata.maps;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.maps.MapBanner;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapFrame;
import org.slf4j.Logger;

public class MapItemSavedData
extends SavedData {
    private static final Logger f_77895_ = LogUtils.getLogger();
    private static final int f_164765_ = 128;
    private static final int f_164766_ = 64;
    public static final int f_164764_ = 4;
    public static final int f_181307_ = 256;
    public final int f_77885_;
    public final int f_77886_;
    public final ResourceKey<Level> f_77887_;
    private final boolean f_77888_;
    private final boolean f_77889_;
    public final byte f_77890_;
    public byte[] f_77891_ = new byte[16384];
    public final boolean f_77892_;
    private final List<HoldingPlayer> f_77893_ = Lists.newArrayList();
    private final Map<Player, HoldingPlayer> f_77896_ = Maps.newHashMap();
    private final Map<String, MapBanner> f_77897_ = Maps.newHashMap();
    final Map<String, MapDecoration> f_77894_ = Maps.newLinkedHashMap();
    private final Map<String, MapFrame> f_77898_ = Maps.newHashMap();
    private int f_181308_;

    private MapItemSavedData(int p_164768_, int p_164769_, byte p_164770_, boolean p_164771_, boolean p_164772_, boolean p_164773_, ResourceKey<Level> p_164774_) {
        this.f_77890_ = p_164770_;
        this.f_77885_ = p_164768_;
        this.f_77886_ = p_164769_;
        this.f_77887_ = p_164774_;
        this.f_77888_ = p_164771_;
        this.f_77889_ = p_164772_;
        this.f_77892_ = p_164773_;
        this.m_77762_();
    }

    public static MapItemSavedData m_164780_(double p_164781_, double p_164782_, byte p_164783_, boolean p_164784_, boolean p_164785_, ResourceKey<Level> p_164786_) {
        int $$6 = 128 * (1 << p_164783_);
        int $$7 = Mth.m_14107_((p_164781_ + 64.0) / (double)$$6);
        int $$8 = Mth.m_14107_((p_164782_ + 64.0) / (double)$$6);
        int $$9 = $$7 * $$6 + $$6 / 2 - 64;
        int $$10 = $$8 * $$6 + $$6 / 2 - 64;
        return new MapItemSavedData($$9, $$10, p_164783_, p_164784_, p_164785_, false, p_164786_);
    }

    public static MapItemSavedData m_164776_(byte p_164777_, boolean p_164778_, ResourceKey<Level> p_164779_) {
        return new MapItemSavedData(0, 0, p_164777_, false, false, p_164778_, p_164779_);
    }

    public static MapItemSavedData m_164807_(CompoundTag p_164808_) {
        ResourceKey $$1 = (ResourceKey)DimensionType.m_63911_(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_164808_.m_128423_("dimension"))).resultOrPartial(arg_0 -> ((Logger)f_77895_).error(arg_0)).orElseThrow(() -> new IllegalArgumentException("Invalid map dimension: " + p_164808_.m_128423_("dimension")));
        int $$2 = p_164808_.m_128451_("xCenter");
        int $$3 = p_164808_.m_128451_("zCenter");
        byte $$4 = (byte)Mth.m_14045_(p_164808_.m_128445_("scale"), 0, 4);
        boolean $$5 = !p_164808_.m_128425_("trackingPosition", 1) || p_164808_.m_128471_("trackingPosition");
        boolean $$6 = p_164808_.m_128471_("unlimitedTracking");
        boolean $$7 = p_164808_.m_128471_("locked");
        MapItemSavedData $$8 = new MapItemSavedData($$2, $$3, $$4, $$5, $$6, $$7, $$1);
        byte[] $$9 = p_164808_.m_128463_("colors");
        if ($$9.length == 16384) {
            $$8.f_77891_ = $$9;
        }
        ListTag $$10 = p_164808_.m_128437_("banners", 10);
        for (int $$11 = 0; $$11 < $$10.size(); ++$$11) {
            MapBanner $$12 = MapBanner.m_77777_($$10.m_128728_($$11));
            $$8.f_77897_.put($$12.m_77787_(), $$12);
            $$8.m_77937_($$12.m_77782_(), null, $$12.m_77787_(), $$12.m_77773_().m_123341_(), $$12.m_77773_().m_123343_(), 180.0, $$12.m_77783_());
        }
        ListTag $$13 = p_164808_.m_128437_("frames", 10);
        for (int $$14 = 0; $$14 < $$13.size(); ++$$14) {
            MapFrame $$15 = MapFrame.m_77872_($$13.m_128728_($$14));
            $$8.f_77898_.put($$15.m_77877_(), $$15);
            $$8.m_77937_(MapDecoration.Type.FRAME, null, "frame-" + $$15.m_77876_(), $$15.m_77874_().m_123341_(), $$15.m_77874_().m_123343_(), $$15.m_77875_(), null);
        }
        return $$8;
    }

    @Override
    public CompoundTag m_7176_(CompoundTag p_77956_) {
        ResourceLocation.f_135803_.encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.f_77887_.m_135782_()).resultOrPartial(arg_0 -> ((Logger)f_77895_).error(arg_0)).ifPresent(p_77954_ -> p_77956_.m_128365_("dimension", (Tag)p_77954_));
        p_77956_.m_128405_("xCenter", this.f_77885_);
        p_77956_.m_128405_("zCenter", this.f_77886_);
        p_77956_.m_128344_("scale", this.f_77890_);
        p_77956_.m_128382_("colors", this.f_77891_);
        p_77956_.m_128379_("trackingPosition", this.f_77888_);
        p_77956_.m_128379_("unlimitedTracking", this.f_77889_);
        p_77956_.m_128379_("locked", this.f_77892_);
        ListTag $$1 = new ListTag();
        for (MapBanner $$2 : this.f_77897_.values()) {
            $$1.add($$2.m_77784_());
        }
        p_77956_.m_128365_("banners", $$1);
        ListTag $$3 = new ListTag();
        for (MapFrame $$4 : this.f_77898_.values()) {
            $$3.add($$4.m_77869_());
        }
        p_77956_.m_128365_("frames", $$3);
        return p_77956_;
    }

    public MapItemSavedData m_164775_() {
        MapItemSavedData $$0 = new MapItemSavedData(this.f_77885_, this.f_77886_, this.f_77890_, this.f_77888_, this.f_77889_, true, this.f_77887_);
        $$0.f_77897_.putAll(this.f_77897_);
        $$0.f_77894_.putAll(this.f_77894_);
        $$0.f_181308_ = this.f_181308_;
        System.arraycopy(this.f_77891_, 0, $$0.f_77891_, 0, this.f_77891_.length);
        $$0.m_77762_();
        return $$0;
    }

    public MapItemSavedData m_164787_(int p_164788_) {
        return MapItemSavedData.m_164780_(this.f_77885_, this.f_77886_, (byte)Mth.m_14045_(this.f_77890_ + p_164788_, 0, 4), this.f_77888_, this.f_77889_, this.f_77887_);
    }

    public void m_77918_(Player p_77919_, ItemStack p_77920_) {
        CompoundTag $$10;
        if (!this.f_77896_.containsKey(p_77919_)) {
            HoldingPlayer $$2 = new HoldingPlayer(p_77919_);
            this.f_77896_.put(p_77919_, $$2);
            this.f_77893_.add($$2);
        }
        if (!p_77919_.m_150109_().m_36063_(p_77920_)) {
            this.m_164799_(p_77919_.m_7755_().getString());
        }
        for (int $$3 = 0; $$3 < this.f_77893_.size(); ++$$3) {
            HoldingPlayer $$4 = this.f_77893_.get($$3);
            String $$5 = $$4.f_77959_.m_7755_().getString();
            if ($$4.f_77959_.m_213877_() || !$$4.f_77959_.m_150109_().m_36063_(p_77920_) && !p_77920_.m_41794_()) {
                this.f_77896_.remove($$4.f_77959_);
                this.f_77893_.remove($$4);
                this.m_164799_($$5);
                continue;
            }
            if (p_77920_.m_41794_() || $$4.f_77959_.f_19853_.m_46472_() != this.f_77887_ || !this.f_77888_) continue;
            this.m_77937_(MapDecoration.Type.PLAYER, $$4.f_77959_.f_19853_, $$5, $$4.f_77959_.m_20185_(), $$4.f_77959_.m_20189_(), $$4.f_77959_.m_146908_(), null);
        }
        if (p_77920_.m_41794_() && this.f_77888_) {
            ItemFrame $$6 = p_77920_.m_41795_();
            BlockPos $$7 = $$6.m_31748_();
            MapFrame $$8 = this.f_77898_.get(MapFrame.m_77870_($$7));
            if ($$8 != null && $$6.m_19879_() != $$8.m_77876_() && this.f_77898_.containsKey($$8.m_77877_())) {
                this.m_164799_("frame-" + $$8.m_77876_());
            }
            MapFrame $$9 = new MapFrame($$7, $$6.m_6350_().m_122416_() * 90, $$6.m_19879_());
            this.m_77937_(MapDecoration.Type.FRAME, p_77919_.f_19853_, "frame-" + $$6.m_19879_(), $$7.m_123341_(), $$7.m_123343_(), $$6.m_6350_().m_122416_() * 90, null);
            this.f_77898_.put($$9.m_77877_(), $$9);
        }
        if (($$10 = p_77920_.m_41783_()) != null && $$10.m_128425_("Decorations", 9)) {
            ListTag $$11 = $$10.m_128437_("Decorations", 10);
            for (int $$12 = 0; $$12 < $$11.size(); ++$$12) {
                CompoundTag $$13 = $$11.m_128728_($$12);
                if (this.f_77894_.containsKey($$13.m_128461_("id"))) continue;
                this.m_77937_(MapDecoration.Type.m_77854_($$13.m_128445_("type")), p_77919_.f_19853_, $$13.m_128461_("id"), $$13.m_128459_("x"), $$13.m_128459_("z"), $$13.m_128459_("rot"), null);
            }
        }
    }

    private void m_164799_(String p_164800_) {
        MapDecoration $$1 = this.f_77894_.remove(p_164800_);
        if ($$1 != null && $$1.m_77803_().m_181306_()) {
            --this.f_181308_;
        }
        this.m_164812_();
    }

    public static void m_77925_(ItemStack p_77926_, BlockPos p_77927_, String p_77928_, MapDecoration.Type p_77929_) {
        ListTag $$5;
        if (p_77926_.m_41782_() && p_77926_.m_41783_().m_128425_("Decorations", 9)) {
            ListTag $$4 = p_77926_.m_41783_().m_128437_("Decorations", 10);
        } else {
            $$5 = new ListTag();
            p_77926_.m_41700_("Decorations", $$5);
        }
        CompoundTag $$6 = new CompoundTag();
        $$6.m_128344_("type", p_77929_.m_77853_());
        $$6.m_128359_("id", p_77928_);
        $$6.m_128347_("x", p_77927_.m_123341_());
        $$6.m_128347_("z", p_77927_.m_123343_());
        $$6.m_128347_("rot", 180.0);
        $$5.add($$6);
        if (p_77929_.m_77857_()) {
            CompoundTag $$7 = p_77926_.m_41698_("display");
            $$7.m_128405_("MapColor", p_77929_.m_77858_());
        }
    }

    /*
     * WARNING - void declaration
     */
    private void m_77937_(MapDecoration.Type p_77938_, @Nullable LevelAccessor p_77939_, String p_77940_, double p_77941_, double p_77942_, double p_77943_, @Nullable Component p_77944_) {
        MapDecoration $$19;
        void $$17;
        MapDecoration $$18;
        int $$7 = 1 << this.f_77890_;
        float $$8 = (float)(p_77941_ - (double)this.f_77885_) / (float)$$7;
        float $$9 = (float)(p_77942_ - (double)this.f_77886_) / (float)$$7;
        byte $$10 = (byte)((double)($$8 * 2.0f) + 0.5);
        byte $$11 = (byte)((double)($$9 * 2.0f) + 0.5);
        int $$12 = 63;
        if ($$8 >= -63.0f && $$9 >= -63.0f && $$8 <= 63.0f && $$9 <= 63.0f) {
            byte $$13 = (byte)((p_77943_ += p_77943_ < 0.0 ? -8.0 : 8.0) * 16.0 / 360.0);
            if (this.f_77887_ == Level.f_46429_ && p_77939_ != null) {
                int $$14 = (int)(p_77939_.m_6106_().m_6792_() / 10L);
                $$13 = (byte)($$14 * $$14 * 34187121 + $$14 * 121 >> 15 & 0xF);
            }
        } else if (p_77938_ == MapDecoration.Type.PLAYER) {
            int $$15 = 320;
            if (Math.abs($$8) < 320.0f && Math.abs($$9) < 320.0f) {
                p_77938_ = MapDecoration.Type.PLAYER_OFF_MAP;
            } else if (this.f_77889_) {
                p_77938_ = MapDecoration.Type.PLAYER_OFF_LIMITS;
            } else {
                this.m_164799_(p_77940_);
                return;
            }
            boolean $$16 = false;
            if ($$8 <= -63.0f) {
                $$10 = -128;
            }
            if ($$9 <= -63.0f) {
                $$11 = -128;
            }
            if ($$8 >= 63.0f) {
                $$10 = 127;
            }
            if ($$9 >= 63.0f) {
                $$11 = 127;
            }
        } else {
            this.m_164799_(p_77940_);
            return;
        }
        if (!($$18 = new MapDecoration(p_77938_, $$10, $$11, (byte)$$17, p_77944_)).equals($$19 = this.f_77894_.put(p_77940_, $$18))) {
            if ($$19 != null && $$19.m_77803_().m_181306_()) {
                --this.f_181308_;
            }
            if (p_77938_.m_181306_()) {
                ++this.f_181308_;
            }
            this.m_164812_();
        }
    }

    @Nullable
    public Packet<?> m_164796_(int p_164797_, Player p_164798_) {
        HoldingPlayer $$2 = this.f_77896_.get(p_164798_);
        if ($$2 == null) {
            return null;
        }
        return $$2.m_164815_(p_164797_);
    }

    private void m_164789_(int p_164790_, int p_164791_) {
        this.m_77762_();
        for (HoldingPlayer $$2 : this.f_77893_) {
            $$2.m_164817_(p_164790_, p_164791_);
        }
    }

    private void m_164812_() {
        this.m_77762_();
        this.f_77893_.forEach(HoldingPlayer::m_164820_);
    }

    public HoldingPlayer m_77916_(Player p_77917_) {
        HoldingPlayer $$1 = this.f_77896_.get(p_77917_);
        if ($$1 == null) {
            $$1 = new HoldingPlayer(p_77917_);
            this.f_77896_.put(p_77917_, $$1);
            this.f_77893_.add($$1);
        }
        return $$1;
    }

    public boolean m_77934_(LevelAccessor p_77935_, BlockPos p_77936_) {
        double $$2 = (double)p_77936_.m_123341_() + 0.5;
        double $$3 = (double)p_77936_.m_123343_() + 0.5;
        int $$4 = 1 << this.f_77890_;
        double $$5 = ($$2 - (double)this.f_77885_) / (double)$$4;
        double $$6 = ($$3 - (double)this.f_77886_) / (double)$$4;
        int $$7 = 63;
        if ($$5 >= -63.0 && $$6 >= -63.0 && $$5 <= 63.0 && $$6 <= 63.0) {
            MapBanner $$8 = MapBanner.m_77774_(p_77935_, p_77936_);
            if ($$8 == null) {
                return false;
            }
            if (this.f_77897_.remove($$8.m_77787_(), $$8)) {
                this.m_164799_($$8.m_77787_());
                return true;
            }
            if (!this.m_181312_(256)) {
                this.f_77897_.put($$8.m_77787_(), $$8);
                this.m_77937_($$8.m_77782_(), p_77935_, $$8.m_77787_(), $$2, $$3, 180.0, $$8.m_77783_());
                return true;
            }
        }
        return false;
    }

    public void m_77930_(BlockGetter p_77931_, int p_77932_, int p_77933_) {
        Iterator<MapBanner> $$3 = this.f_77897_.values().iterator();
        while ($$3.hasNext()) {
            MapBanner $$5;
            MapBanner $$4 = $$3.next();
            if ($$4.m_77773_().m_123341_() != p_77932_ || $$4.m_77773_().m_123343_() != p_77933_ || $$4.equals($$5 = MapBanner.m_77774_(p_77931_, $$4.m_77773_()))) continue;
            $$3.remove();
            this.m_164799_($$4.m_77787_());
        }
    }

    public Collection<MapBanner> m_164809_() {
        return this.f_77897_.values();
    }

    public void m_77947_(BlockPos p_77948_, int p_77949_) {
        this.m_164799_("frame-" + p_77949_);
        this.f_77898_.remove(MapFrame.m_77870_(p_77948_));
    }

    public boolean m_164792_(int p_164793_, int p_164794_, byte p_164795_) {
        byte $$3 = this.f_77891_[p_164793_ + p_164794_ * 128];
        if ($$3 != p_164795_) {
            this.m_164803_(p_164793_, p_164794_, p_164795_);
            return true;
        }
        return false;
    }

    public void m_164803_(int p_164804_, int p_164805_, byte p_164806_) {
        this.f_77891_[p_164804_ + p_164805_ * 128] = p_164806_;
        this.m_164789_(p_164804_, p_164805_);
    }

    public boolean m_164810_() {
        for (MapDecoration $$0 : this.f_77894_.values()) {
            if ($$0.m_77803_() != MapDecoration.Type.MANSION && $$0.m_77803_() != MapDecoration.Type.MONUMENT) continue;
            return true;
        }
        return false;
    }

    public void m_164801_(List<MapDecoration> p_164802_) {
        this.f_77894_.clear();
        this.f_181308_ = 0;
        for (int $$1 = 0; $$1 < p_164802_.size(); ++$$1) {
            MapDecoration $$2 = p_164802_.get($$1);
            this.f_77894_.put("icon-" + $$1, $$2);
            if (!$$2.m_77803_().m_181306_()) continue;
            ++this.f_181308_;
        }
    }

    public Iterable<MapDecoration> m_164811_() {
        return this.f_77894_.values();
    }

    public boolean m_181312_(int p_181313_) {
        return this.f_181308_ >= p_181313_;
    }

    public class HoldingPlayer {
        public final Player f_77959_;
        private boolean f_77962_ = true;
        private int f_77963_;
        private int f_77964_;
        private int f_77965_ = 127;
        private int f_77966_ = 127;
        private boolean f_164813_ = true;
        private int f_77967_;
        public int f_77960_;

        HoldingPlayer(Player p_77970_) {
            this.f_77959_ = p_77970_;
        }

        private MapPatch m_164814_() {
            int $$0 = this.f_77963_;
            int $$1 = this.f_77964_;
            int $$2 = this.f_77965_ + 1 - this.f_77963_;
            int $$3 = this.f_77966_ + 1 - this.f_77964_;
            byte[] $$4 = new byte[$$2 * $$3];
            for (int $$5 = 0; $$5 < $$2; ++$$5) {
                for (int $$6 = 0; $$6 < $$3; ++$$6) {
                    $$4[$$5 + $$6 * $$2] = MapItemSavedData.this.f_77891_[$$0 + $$5 + ($$1 + $$6) * 128];
                }
            }
            return new MapPatch($$0, $$1, $$2, $$3, $$4);
        }

        @Nullable
        Packet<?> m_164815_(int p_164816_) {
            Collection<MapDecoration> $$4;
            MapPatch $$2;
            if (this.f_77962_) {
                this.f_77962_ = false;
                MapPatch $$1 = this.m_164814_();
            } else {
                $$2 = null;
            }
            if (this.f_164813_ && this.f_77967_++ % 5 == 0) {
                this.f_164813_ = false;
                Collection<MapDecoration> $$3 = MapItemSavedData.this.f_77894_.values();
            } else {
                $$4 = null;
            }
            if ($$4 != null || $$2 != null) {
                return new ClientboundMapItemDataPacket(p_164816_, MapItemSavedData.this.f_77890_, MapItemSavedData.this.f_77892_, $$4, $$2);
            }
            return null;
        }

        void m_164817_(int p_164818_, int p_164819_) {
            if (this.f_77962_) {
                this.f_77963_ = Math.min(this.f_77963_, p_164818_);
                this.f_77964_ = Math.min(this.f_77964_, p_164819_);
                this.f_77965_ = Math.max(this.f_77965_, p_164818_);
                this.f_77966_ = Math.max(this.f_77966_, p_164819_);
            } else {
                this.f_77962_ = true;
                this.f_77963_ = p_164818_;
                this.f_77964_ = p_164819_;
                this.f_77965_ = p_164818_;
                this.f_77966_ = p_164819_;
            }
        }

        private void m_164820_() {
            this.f_164813_ = true;
        }
    }

    public static class MapPatch {
        public final int f_164821_;
        public final int f_164822_;
        public final int f_164823_;
        public final int f_164824_;
        public final byte[] f_164825_;

        public MapPatch(int p_164827_, int p_164828_, int p_164829_, int p_164830_, byte[] p_164831_) {
            this.f_164821_ = p_164827_;
            this.f_164822_ = p_164828_;
            this.f_164823_ = p_164829_;
            this.f_164824_ = p_164830_;
            this.f_164825_ = p_164831_;
        }

        public void m_164832_(MapItemSavedData p_164833_) {
            for (int $$1 = 0; $$1 < this.f_164823_; ++$$1) {
                for (int $$2 = 0; $$2 < this.f_164824_; ++$$2) {
                    p_164833_.m_164803_(this.f_164821_ + $$1, this.f_164822_ + $$2, this.f_164825_[$$1 + $$2 * this.f_164823_]);
                }
            }
        }
    }
}

