/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.packs.repository;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import org.slf4j.Logger;

public class Pack {
    private static final Logger f_10399_ = LogUtils.getLogger();
    private final String f_10401_;
    private final Supplier<PackResources> f_10402_;
    private final Component f_10403_;
    private final Component f_10404_;
    private final PackCompatibility f_10405_;
    private final Position f_10406_;
    private final boolean f_10407_;
    private final boolean f_10408_;
    private final PackSource f_10409_;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    public static Pack m_10430_(String p_10431_, boolean p_10432_, Supplier<PackResources> p_10433_, PackConstructor p_10434_, Position p_10435_, PackSource p_10436_) {
        try (PackResources $$6 = p_10433_.get();){
            PackMetadataSection $$7 = $$6.m_5550_(PackMetadataSection.f_10366_);
            if ($$7 != null) {
                Pack pack = p_10434_.m_143873_(p_10431_, Component.m_237113_($$6.m_8017_()), p_10432_, p_10433_, $$7, p_10435_, p_10436_);
                return pack;
            }
            f_10399_.warn("Couldn't find pack meta for pack {}", (Object)p_10431_);
            return null;
        }
        catch (IOException $$8) {
            f_10399_.warn("Couldn't get pack info for: {}", (Object)$$8.toString());
        }
        return null;
    }

    public Pack(String p_10420_, boolean p_10421_, Supplier<PackResources> p_10422_, Component p_10423_, Component p_10424_, PackCompatibility p_10425_, Position p_10426_, boolean p_10427_, PackSource p_10428_) {
        this.f_10401_ = p_10420_;
        this.f_10402_ = p_10422_;
        this.f_10403_ = p_10423_;
        this.f_10404_ = p_10424_;
        this.f_10405_ = p_10425_;
        this.f_10407_ = p_10421_;
        this.f_10406_ = p_10426_;
        this.f_10408_ = p_10427_;
        this.f_10409_ = p_10428_;
    }

    public Pack(String p_143865_, Component p_143866_, boolean p_143867_, Supplier<PackResources> p_143868_, PackMetadataSection p_143869_, PackType p_143870_, Position p_143871_, PackSource p_143872_) {
        this(p_143865_, p_143867_, p_143868_, p_143866_, p_143869_.m_10373_(), PackCompatibility.m_143885_(p_143869_, p_143870_), p_143871_, false, p_143872_);
    }

    public Component m_10429_() {
        return this.f_10403_;
    }

    public Component m_10442_() {
        return this.f_10404_;
    }

    public Component m_10437_(boolean p_10438_) {
        return ComponentUtils.m_130748_(this.f_10409_.m_10540_(Component.m_237113_(this.f_10401_))).m_130938_(p_10441_ -> p_10441_.m_131140_(p_10438_ ? ChatFormatting.GREEN : ChatFormatting.RED).m_131138_(StringArgumentType.escapeIfRequired((String)this.f_10401_)).m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, Component.m_237119_().m_7220_(this.f_10403_).m_130946_("\n").m_7220_(this.f_10404_))));
    }

    public PackCompatibility m_10443_() {
        return this.f_10405_;
    }

    public PackResources m_10445_() {
        return this.f_10402_.get();
    }

    public String m_10446_() {
        return this.f_10401_;
    }

    public boolean m_10449_() {
        return this.f_10407_;
    }

    public boolean m_10450_() {
        return this.f_10408_;
    }

    public Position m_10451_() {
        return this.f_10406_;
    }

    public PackSource m_10453_() {
        return this.f_10409_;
    }

    public boolean equals(Object p_10448_) {
        if (this == p_10448_) {
            return true;
        }
        if (!(p_10448_ instanceof Pack)) {
            return false;
        }
        Pack $$1 = (Pack)p_10448_;
        return this.f_10401_.equals($$1.f_10401_);
    }

    public int hashCode() {
        return this.f_10401_.hashCode();
    }

    @FunctionalInterface
    public static interface PackConstructor {
        @Nullable
        public Pack m_143873_(String var1, Component var2, boolean var3, Supplier<PackResources> var4, PackMetadataSection var5, Position var6, PackSource var7);
    }

    public static final class Position
    extends Enum<Position> {
        public static final /* enum */ Position TOP = new Position();
        public static final /* enum */ Position BOTTOM = new Position();
        private static final /* synthetic */ Position[] $VALUES;

        public static Position[] values() {
            return (Position[])$VALUES.clone();
        }

        public static Position valueOf(String p_10476_) {
            return Enum.valueOf(Position.class, p_10476_);
        }

        public <T> int m_10470_(List<T> p_10471_, T p_10472_, Function<T, Pack> p_10473_, boolean p_10474_) {
            Pack $$8;
            int $$7;
            Position $$4;
            Position position = $$4 = p_10474_ ? this.m_10469_() : this;
            if ($$4 == BOTTOM) {
                Pack $$6;
                int $$5;
                for ($$5 = 0; $$5 < p_10471_.size() && ($$6 = p_10473_.apply(p_10471_.get($$5))).m_10450_() && $$6.m_10451_() == this; ++$$5) {
                }
                p_10471_.add($$5, p_10472_);
                return $$5;
            }
            for ($$7 = p_10471_.size() - 1; $$7 >= 0 && ($$8 = p_10473_.apply(p_10471_.get($$7))).m_10450_() && $$8.m_10451_() == this; --$$7) {
            }
            p_10471_.add($$7 + 1, p_10472_);
            return $$7 + 1;
        }

        public Position m_10469_() {
            return this == TOP ? BOTTOM : TOP;
        }

        private static /* synthetic */ Position[] m_143881_() {
            return new Position[]{TOP, BOTTOM};
        }

        static {
            $VALUES = Position.m_143881_();
        }
    }
}

