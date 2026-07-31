/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;

public class SplashManager
extends SimplePreparableReloadListener<List<String>> {
    private static final ResourceLocation f_118860_ = new ResourceLocation("texts/splashes.txt");
    private static final RandomSource f_118861_ = RandomSource.m_216327_();
    private final List<String> f_118862_ = Lists.newArrayList();
    private final User f_118863_;

    public SplashManager(User p_118866_) {
        this.f_118863_ = p_118866_;
    }

    @Override
    protected List<String> m_5944_(ResourceManager p_118869_, ProfilerFiller p_118870_) {
        List<String> list;
        block8: {
            BufferedReader $$2 = Minecraft.m_91087_().m_91098_().m_215597_(f_118860_);
            try {
                list = $$2.lines().map(String::trim).filter(p_118876_ -> p_118876_.hashCode() != 125780783).collect(Collectors.toList());
                if ($$2 == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if ($$2 != null) {
                        try {
                            $$2.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (IOException $$3) {
                    return Collections.emptyList();
                }
            }
            $$2.close();
        }
        return list;
    }

    @Override
    protected void m_5787_(List<String> p_118878_, ResourceManager p_118879_, ProfilerFiller p_118880_) {
        this.f_118862_.clear();
        this.f_118862_.addAll(p_118878_);
    }

    @Nullable
    public String m_118867_() {
        Calendar $$0 = Calendar.getInstance();
        $$0.setTime(new Date());
        if ($$0.get(2) + 1 == 12 && $$0.get(5) == 24) {
            return "Merry X-mas!";
        }
        if ($$0.get(2) + 1 == 1 && $$0.get(5) == 1) {
            return "Happy new year!";
        }
        if ($$0.get(2) + 1 == 10 && $$0.get(5) == 31) {
            return "OOoooOOOoooo! Spooky!";
        }
        if (this.f_118862_.isEmpty()) {
            return null;
        }
        if (this.f_118863_ != null && f_118861_.m_188503_(this.f_118862_.size()) == 42) {
            return this.f_118863_.m_92546_().toUpperCase(Locale.ROOT) + " IS YOU";
        }
        return this.f_118862_.get(f_118861_.m_188503_(this.f_118862_.size()));
    }

    @Override
    protected /* synthetic */ Object m_5944_(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        return this.m_5944_(resourceManager, profilerFiller);
    }
}

