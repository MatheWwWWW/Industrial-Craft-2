/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.BiMap
 *  com.google.common.collect.HashBiMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.storage.loot.parameters;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class LootContextParamSets {
    private static final BiMap<ResourceLocation, LootContextParamSet> f_81422_ = HashBiMap.create();
    public static final LootContextParamSet f_81410_ = LootContextParamSets.m_81428_("empty", p_81454_ -> {});
    public static final LootContextParamSet f_81411_ = LootContextParamSets.m_81428_("chest", p_81452_ -> p_81452_.m_81406_(LootContextParams.f_81460_).m_81408_(LootContextParams.f_81455_));
    public static final LootContextParamSet f_81412_ = LootContextParamSets.m_81428_("command", p_81450_ -> p_81450_.m_81406_(LootContextParams.f_81460_).m_81408_(LootContextParams.f_81455_));
    public static final LootContextParamSet f_81413_ = LootContextParamSets.m_81428_("selector", p_81448_ -> p_81448_.m_81406_(LootContextParams.f_81460_).m_81406_(LootContextParams.f_81455_));
    public static final LootContextParamSet f_81414_ = LootContextParamSets.m_81428_("fishing", p_81446_ -> p_81446_.m_81406_(LootContextParams.f_81460_).m_81406_(LootContextParams.f_81463_).m_81408_(LootContextParams.f_81455_));
    public static final LootContextParamSet f_81415_ = LootContextParamSets.m_81428_("entity", p_81444_ -> p_81444_.m_81406_(LootContextParams.f_81455_).m_81406_(LootContextParams.f_81460_).m_81406_(LootContextParams.f_81457_).m_81408_(LootContextParams.f_81458_).m_81408_(LootContextParams.f_81459_).m_81408_(LootContextParams.f_81456_));
    public static final LootContextParamSet f_81416_ = LootContextParamSets.m_81428_("gift", p_81442_ -> p_81442_.m_81406_(LootContextParams.f_81460_).m_81406_(LootContextParams.f_81455_));
    public static final LootContextParamSet f_81417_ = LootContextParamSets.m_81428_("barter", p_81440_ -> p_81440_.m_81406_(LootContextParams.f_81455_));
    public static final LootContextParamSet f_81418_ = LootContextParamSets.m_81428_("advancement_reward", p_81438_ -> p_81438_.m_81406_(LootContextParams.f_81455_).m_81406_(LootContextParams.f_81460_));
    public static final LootContextParamSet f_81419_ = LootContextParamSets.m_81428_("advancement_entity", p_81436_ -> p_81436_.m_81406_(LootContextParams.f_81455_).m_81406_(LootContextParams.f_81460_));
    public static final LootContextParamSet f_81420_ = LootContextParamSets.m_81428_("generic", p_81434_ -> p_81434_.m_81406_(LootContextParams.f_81455_).m_81406_(LootContextParams.f_81456_).m_81406_(LootContextParams.f_81457_).m_81406_(LootContextParams.f_81458_).m_81406_(LootContextParams.f_81459_).m_81406_(LootContextParams.f_81460_).m_81406_(LootContextParams.f_81461_).m_81406_(LootContextParams.f_81462_).m_81406_(LootContextParams.f_81463_).m_81406_(LootContextParams.f_81464_));
    public static final LootContextParamSet f_81421_ = LootContextParamSets.m_81428_("block", p_81425_ -> p_81425_.m_81406_(LootContextParams.f_81461_).m_81406_(LootContextParams.f_81460_).m_81406_(LootContextParams.f_81463_).m_81408_(LootContextParams.f_81455_).m_81408_(LootContextParams.f_81462_).m_81408_(LootContextParams.f_81464_));

    private static LootContextParamSet m_81428_(String p_81429_, Consumer<LootContextParamSet.Builder> p_81430_) {
        LootContextParamSet.Builder $$2 = new LootContextParamSet.Builder();
        p_81430_.accept($$2);
        LootContextParamSet $$3 = $$2.m_81405_();
        ResourceLocation $$4 = new ResourceLocation(p_81429_);
        LootContextParamSet $$5 = (LootContextParamSet)f_81422_.put((Object)$$4, (Object)$$3);
        if ($$5 != null) {
            throw new IllegalStateException("Loot table parameter set " + $$4 + " is already registered");
        }
        return $$3;
    }

    @Nullable
    public static LootContextParamSet m_81431_(ResourceLocation p_81432_) {
        return (LootContextParamSet)f_81422_.get((Object)p_81432_);
    }

    @Nullable
    public static ResourceLocation m_81426_(LootContextParamSet p_81427_) {
        return (ResourceLocation)f_81422_.inverse().get((Object)p_81427_);
    }
}

