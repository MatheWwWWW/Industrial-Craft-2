/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.world.entity.npc;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public final class VillagerType {
    public static final VillagerType f_35819_ = VillagerType.m_35831_("desert");
    public static final VillagerType f_35820_ = VillagerType.m_35831_("jungle");
    public static final VillagerType f_35821_ = VillagerType.m_35831_("plains");
    public static final VillagerType f_35822_ = VillagerType.m_35831_("savanna");
    public static final VillagerType f_35823_ = VillagerType.m_35831_("snow");
    public static final VillagerType f_35824_ = VillagerType.m_35831_("swamp");
    public static final VillagerType f_35825_ = VillagerType.m_35831_("taiga");
    private final String f_35826_;
    private static final Map<ResourceKey<Biome>, VillagerType> f_35827_ = Util.m_137469_(Maps.newHashMap(), p_35834_ -> {
        p_35834_.put(Biomes.f_48159_, f_35819_);
        p_35834_.put(Biomes.f_48203_, f_35819_);
        p_35834_.put(Biomes.f_48194_, f_35819_);
        p_35834_.put(Biomes.f_186753_, f_35819_);
        p_35834_.put(Biomes.f_48197_, f_35820_);
        p_35834_.put(Biomes.f_48222_, f_35820_);
        p_35834_.put(Biomes.f_186769_, f_35820_);
        p_35834_.put(Biomes.f_48158_, f_35822_);
        p_35834_.put(Biomes.f_48157_, f_35822_);
        p_35834_.put(Biomes.f_186768_, f_35822_);
        p_35834_.put(Biomes.f_48172_, f_35823_);
        p_35834_.put(Biomes.f_48211_, f_35823_);
        p_35834_.put(Biomes.f_48212_, f_35823_);
        p_35834_.put(Biomes.f_48182_, f_35823_);
        p_35834_.put(Biomes.f_48148_, f_35823_);
        p_35834_.put(Biomes.f_48152_, f_35823_);
        p_35834_.put(Biomes.f_186761_, f_35823_);
        p_35834_.put(Biomes.f_186755_, f_35823_);
        p_35834_.put(Biomes.f_186756_, f_35823_);
        p_35834_.put(Biomes.f_186757_, f_35823_);
        p_35834_.put(Biomes.f_186758_, f_35823_);
        p_35834_.put(Biomes.f_48207_, f_35824_);
        p_35834_.put(Biomes.f_220595_, f_35824_);
        p_35834_.put(Biomes.f_186764_, f_35825_);
        p_35834_.put(Biomes.f_186763_, f_35825_);
        p_35834_.put(Biomes.f_186766_, f_35825_);
        p_35834_.put(Biomes.f_186765_, f_35825_);
        p_35834_.put(Biomes.f_48206_, f_35825_);
        p_35834_.put(Biomes.f_186767_, f_35825_);
    });

    private VillagerType(String p_35830_) {
        this.f_35826_ = p_35830_;
    }

    public String toString() {
        return this.f_35826_;
    }

    private static VillagerType m_35831_(String p_35832_) {
        return Registry.m_122965_(Registry.f_122868_, new ResourceLocation(p_35832_), new VillagerType(p_35832_));
    }

    public static VillagerType m_204073_(Holder<Biome> p_204074_) {
        return p_204074_.m_203543_().map(f_35827_::get).orElse(f_35821_);
    }
}

