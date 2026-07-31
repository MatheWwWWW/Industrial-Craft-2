/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.OptionalDynamic
 */
package net.minecraft.world.level.storage;

import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import net.minecraft.SharedConstants;
import net.minecraft.world.level.storage.DataVersion;

public class LevelVersion {
    private final int f_78378_;
    private final long f_78379_;
    private final String f_78380_;
    private final DataVersion f_78381_;
    private final boolean f_78382_;

    private LevelVersion(int p_193023_, long p_193024_, String p_193025_, int p_193026_, String p_193027_, boolean p_193028_) {
        this.f_78378_ = p_193023_;
        this.f_78379_ = p_193024_;
        this.f_78380_ = p_193025_;
        this.f_78381_ = new DataVersion(p_193026_, p_193027_);
        this.f_78382_ = p_193028_;
    }

    public static LevelVersion m_78390_(Dynamic<?> p_78391_) {
        int $$1 = p_78391_.get("version").asInt(0);
        long $$2 = p_78391_.get("LastPlayed").asLong(0L);
        OptionalDynamic $$3 = p_78391_.get("Version");
        if ($$3.result().isPresent()) {
            return new LevelVersion($$1, $$2, $$3.get("Name").asString(SharedConstants.m_183709_().getName()), $$3.get("Id").asInt(SharedConstants.m_183709_().m_183476_().m_193006_()), $$3.get("Series").asString(DataVersion.f_192993_), $$3.get("Snapshot").asBoolean(!SharedConstants.m_183709_().isStable()));
        }
        return new LevelVersion($$1, $$2, "", 0, DataVersion.f_192993_, false);
    }

    public int m_78389_() {
        return this.f_78378_;
    }

    public long m_78392_() {
        return this.f_78379_;
    }

    public String m_78393_() {
        return this.f_78380_;
    }

    public DataVersion m_193029_() {
        return this.f_78381_;
    }

    public boolean m_78395_() {
        return this.f_78382_;
    }
}

