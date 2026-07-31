/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.Encoder
 *  com.mojang.serialization.JsonOps
 *  org.slf4j.Logger
 */
package net.minecraft.data.info;

import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import org.slf4j.Logger;

public class BiomeParametersDumpReport
implements DataProvider {
    private static final Logger f_236172_ = LogUtils.getLogger();
    private final Path f_236173_;

    public BiomeParametersDumpReport(DataGenerator p_236176_) {
        this.f_236173_ = p_236176_.m_236034_(DataGenerator.Target.REPORTS).resolve("biome_parameters");
    }

    @Override
    public void m_213708_(CachedOutput p_236186_) {
        RegistryAccess.Frozen $$1 = RegistryAccess.f_123049_.get();
        RegistryOps $$2 = RegistryOps.m_206821_(JsonOps.INSTANCE, $$1);
        Registry<Biome> $$3 = $$1.m_175515_(Registry.f_122885_);
        MultiNoiseBiomeSource.Preset.m_220657_().forEach(p_236184_ -> {
            MultiNoiseBiomeSource $$4 = ((MultiNoiseBiomeSource.Preset)p_236184_.getSecond()).m_187104_($$3, false);
            BiomeParametersDumpReport.m_236187_(this.m_236178_((ResourceLocation)p_236184_.getFirst()), p_236186_, $$2, MultiNoiseBiomeSource.f_48425_, $$4);
        });
    }

    private static <E> void m_236187_(Path p_236188_, CachedOutput p_236189_, DynamicOps<JsonElement> p_236190_, Encoder<E> p_236191_, E p_236192_) {
        try {
            Optional $$5 = p_236191_.encodeStart(p_236190_, p_236192_).resultOrPartial(p_236195_ -> f_236172_.error("Couldn't serialize element {}: {}", (Object)p_236188_, p_236195_));
            if ($$5.isPresent()) {
                DataProvider.m_236072_(p_236189_, (JsonElement)$$5.get(), p_236188_);
            }
        }
        catch (IOException $$6) {
            f_236172_.error("Couldn't save element {}", (Object)p_236188_, (Object)$$6);
        }
    }

    private Path m_236178_(ResourceLocation p_236179_) {
        return this.f_236173_.resolve(p_236179_.m_135827_()).resolve(p_236179_.m_135815_() + ".json");
    }

    @Override
    public String m_6055_() {
        return "Biome Parameters";
    }
}

