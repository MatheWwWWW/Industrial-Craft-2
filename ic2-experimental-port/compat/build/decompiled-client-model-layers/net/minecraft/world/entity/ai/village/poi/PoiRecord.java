/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.entity.ai.village.poi;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class PoiRecord {
    private final BlockPos f_27227_;
    private final Holder<PoiType> f_27228_;
    private int f_27229_;
    private final Runnable f_27230_;

    public static Codec<PoiRecord> m_27242_(Runnable p_27243_) {
        return RecordCodecBuilder.create(p_27246_ -> p_27246_.group((App)BlockPos.f_121852_.fieldOf("pos").forGetter(p_148673_ -> p_148673_.f_27227_), (App)RegistryFixedCodec.m_206740_(Registry.f_122810_).fieldOf("type").forGetter(p_218017_ -> p_218017_.f_27228_), (App)Codec.INT.fieldOf("free_tickets").orElse((Object)0).forGetter(p_148669_ -> p_148669_.f_27229_), (App)RecordCodecBuilder.point((Object)p_27243_)).apply((Applicative)p_27246_, PoiRecord::new));
    }

    private PoiRecord(BlockPos p_218008_, Holder<PoiType> p_218009_, int p_218010_, Runnable p_218011_) {
        this.f_27227_ = p_218008_.m_7949_();
        this.f_27228_ = p_218009_;
        this.f_27229_ = p_218010_;
        this.f_27230_ = p_218011_;
    }

    public PoiRecord(BlockPos p_218013_, Holder<PoiType> p_218014_, Runnable p_218015_) {
        this(p_218013_, p_218014_, p_218014_.m_203334_().f_27326_(), p_218015_);
    }

    @Deprecated
    @VisibleForDebug
    public int m_148667_() {
        return this.f_27229_;
    }

    protected boolean m_27247_() {
        if (this.f_27229_ <= 0) {
            return false;
        }
        --this.f_27229_;
        this.f_27230_.run();
        return true;
    }

    protected boolean m_27250_() {
        if (this.f_27229_ >= this.f_27228_.m_203334_().f_27326_()) {
            return false;
        }
        ++this.f_27229_;
        this.f_27230_.run();
        return true;
    }

    public boolean m_27253_() {
        return this.f_27229_ > 0;
    }

    public boolean m_27254_() {
        return this.f_27229_ != this.f_27228_.m_203334_().f_27326_();
    }

    public BlockPos m_27257_() {
        return this.f_27227_;
    }

    public Holder<PoiType> m_218018_() {
        return this.f_27228_;
    }

    public boolean equals(Object p_27256_) {
        if (this == p_27256_) {
            return true;
        }
        if (p_27256_ == null || this.getClass() != p_27256_.getClass()) {
            return false;
        }
        return Objects.equals(this.f_27227_, ((PoiRecord)p_27256_).f_27227_);
    }

    public int hashCode() {
        return this.f_27227_.hashCode();
    }
}

