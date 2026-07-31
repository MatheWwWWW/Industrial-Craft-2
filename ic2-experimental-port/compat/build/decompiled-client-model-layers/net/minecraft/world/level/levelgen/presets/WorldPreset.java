/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Lifecycle
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.presets;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldGenSettings;

public class WorldPreset {
    public static final Codec<WorldPreset> f_226414_ = RecordCodecBuilder.create(p_226426_ -> p_226426_.group((App)Codec.unboundedMap(ResourceKey.m_195966_(Registry.f_122820_), LevelStem.f_63970_).fieldOf("dimensions").forGetter(p_226430_ -> p_226430_.f_226416_)).apply((Applicative)p_226426_, WorldPreset::new)).flatXmap(WorldPreset::m_238378_, WorldPreset::m_238378_);
    public static final Codec<Holder<WorldPreset>> f_226415_ = RegistryFileCodec.m_135589_(Registry.f_235726_, f_226414_);
    private final Map<ResourceKey<LevelStem>, LevelStem> f_226416_;

    public WorldPreset(Map<ResourceKey<LevelStem>, LevelStem> p_226419_) {
        this.f_226416_ = p_226419_;
    }

    private Registry<LevelStem> m_226435_() {
        MappedRegistry<LevelStem> $$0 = new MappedRegistry<LevelStem>(Registry.f_122820_, Lifecycle.experimental(), null);
        LevelStem.m_223605_(this.f_226416_.keySet().stream()).forEach(p_226433_ -> {
            LevelStem $$2 = this.f_226416_.get(p_226433_);
            if ($$2 != null) {
                $$0.m_203505_((ResourceKey<LevelStem>)p_226433_, $$2, Lifecycle.stable());
            }
        });
        return ((Registry)$$0).m_203521_();
    }

    public WorldGenSettings m_226421_(long p_226422_, boolean p_226423_, boolean p_226424_) {
        return new WorldGenSettings(p_226422_, p_226423_, p_226424_, this.m_226435_());
    }

    public WorldGenSettings m_226427_(WorldGenSettings p_226428_) {
        return this.m_226421_(p_226428_.m_64619_(), p_226428_.m_224677_(), p_226428_.m_64660_());
    }

    public Optional<LevelStem> m_226420_() {
        return Optional.ofNullable(this.f_226416_.get(LevelStem.f_63971_));
    }

    public LevelStem m_226434_() {
        return this.m_226420_().orElseThrow(() -> new IllegalStateException("Can't find overworld in this preset"));
    }

    private static DataResult<WorldPreset> m_238378_(WorldPreset p_238379_) {
        if (p_238379_.m_226420_().isEmpty()) {
            return DataResult.error((String)"Missing overworld dimension");
        }
        return DataResult.success((Object)p_238379_, (Lifecycle)Lifecycle.stable());
    }
}

