/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonElement
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.data.advancements;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.advancements.AdventureAdvancements;
import net.minecraft.data.advancements.HusbandryAdvancements;
import net.minecraft.data.advancements.NetherAdvancements;
import net.minecraft.data.advancements.StoryAdvancements;
import net.minecraft.data.advancements.TheEndAdvancements;
import org.slf4j.Logger;

public class AdvancementProvider
implements DataProvider {
    private static final Logger f_123960_ = LogUtils.getLogger();
    private final DataGenerator.PathProvider f_236156_;
    private final List<Consumer<Consumer<Advancement>>> f_123963_ = ImmutableList.of((Object)new TheEndAdvancements(), (Object)new HusbandryAdvancements(), (Object)new AdventureAdvancements(), (Object)new NetherAdvancements(), (Object)new StoryAdvancements());

    public AdvancementProvider(DataGenerator p_123966_) {
        this.f_236156_ = p_123966_.m_236036_(DataGenerator.Target.DATA_PACK, "advancements");
    }

    @Override
    public void m_213708_(CachedOutput p_236158_) {
        HashSet $$1 = Sets.newHashSet();
        Consumer<Advancement> $$2 = p_236162_ -> {
            if (!$$1.add(p_236162_.m_138327_())) {
                throw new IllegalStateException("Duplicate advancement " + p_236162_.m_138327_());
            }
            Path $$3 = this.f_236156_.m_236048_(p_236162_.m_138327_());
            try {
                DataProvider.m_236072_(p_236158_, (JsonElement)p_236162_.m_138313_().m_138400_(), $$3);
            }
            catch (IOException $$4) {
                f_123960_.error("Couldn't save advancement {}", (Object)$$3, (Object)$$4);
            }
        };
        for (Consumer<Consumer<Advancement>> $$3 : this.f_123963_) {
            $$3.accept($$2);
        }
    }

    @Override
    public String m_6055_() {
        return "Advancements";
    }
}

