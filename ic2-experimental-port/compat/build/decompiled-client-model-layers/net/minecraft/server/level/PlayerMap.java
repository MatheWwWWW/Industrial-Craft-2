/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap
 */
package net.minecraft.server.level;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.Set;
import net.minecraft.server.level.ServerPlayer;

public final class PlayerMap {
    private final Object2BooleanMap<ServerPlayer> f_8241_ = new Object2BooleanOpenHashMap();

    public Set<ServerPlayer> m_183926_(long p_183927_) {
        return this.f_8241_.keySet();
    }

    public void m_8252_(long p_8253_, ServerPlayer p_8254_, boolean p_8255_) {
        this.f_8241_.put((Object)p_8254_, p_8255_);
    }

    public void m_8249_(long p_8250_, ServerPlayer p_8251_) {
        this.f_8241_.removeBoolean((Object)p_8251_);
    }

    public void m_8256_(ServerPlayer p_8257_) {
        this.f_8241_.replace((Object)p_8257_, true);
    }

    public void m_8258_(ServerPlayer p_8259_) {
        this.f_8241_.replace((Object)p_8259_, false);
    }

    public boolean m_8260_(ServerPlayer p_8261_) {
        return this.f_8241_.getOrDefault((Object)p_8261_, true);
    }

    public boolean m_8262_(ServerPlayer p_8263_) {
        return this.f_8241_.getBoolean((Object)p_8263_);
    }

    public void m_8245_(long p_8246_, long p_8247_, ServerPlayer p_8248_) {
    }
}

