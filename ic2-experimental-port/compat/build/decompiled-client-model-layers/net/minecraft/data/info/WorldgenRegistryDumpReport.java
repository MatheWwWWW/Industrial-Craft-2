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
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import org.slf4j.Logger;

public class WorldgenRegistryDumpReport
implements DataProvider {
    private static final Logger f_194674_ = LogUtils.getLogger();
    private final DataGenerator f_194676_;

    public WorldgenRegistryDumpReport(DataGenerator p_194679_) {
        this.f_194676_ = p_194679_;
    }

    @Override
    public void m_213708_(CachedOutput p_236203_) {
        RegistryAccess $$1 = RegistryAccess.f_123049_.get();
        RegistryOps $$2 = RegistryOps.m_206821_(JsonOps.INSTANCE, $$1);
        RegistryAccess.m_194613_().forEach(p_236219_ -> this.m_236204_(p_236203_, $$1, $$2, (RegistryAccess.RegistryData)p_236219_));
    }

    private <T> void m_236204_(CachedOutput p_236205_, RegistryAccess p_236206_, DynamicOps<JsonElement> p_236207_, RegistryAccess.RegistryData<T> p_236208_) {
        ResourceKey<Registry<T>> $$4 = p_236208_.f_123101_();
        Registry<T> $$5 = p_236206_.m_206191_($$4);
        DataGenerator.PathProvider $$6 = this.f_194676_.m_236036_(DataGenerator.Target.REPORTS, $$4.m_135782_().m_135815_());
        for (Map.Entry<ResourceKey<T>, T> $$7 : $$5.m_6579_()) {
            WorldgenRegistryDumpReport.m_236209_($$6.m_236048_($$7.getKey().m_135782_()), p_236205_, p_236207_, p_236208_.f_123102_(), $$7.getValue());
        }
    }

    private static <E> void m_236209_(Path p_236210_, CachedOutput p_236211_, DynamicOps<JsonElement> p_236212_, Encoder<E> p_236213_, E p_236214_) {
        try {
            Optional $$5 = p_236213_.encodeStart(p_236212_, p_236214_).resultOrPartial(p_206405_ -> f_194674_.error("Couldn't serialize element {}: {}", (Object)p_236210_, p_206405_));
            if ($$5.isPresent()) {
                DataProvider.m_236072_(p_236211_, (JsonElement)$$5.get(), p_236210_);
            }
        }
        catch (IOException $$6) {
            f_194674_.error("Couldn't save element {}", (Object)p_236210_, (Object)$$6);
        }
    }

    @Override
    public String m_6055_() {
        return "Worldgen";
    }
}

