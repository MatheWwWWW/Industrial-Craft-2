/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.Object2FloatMap
 *  it.unimi.dsi.fastutil.objects.Object2FloatMaps
 *  it.unimi.dsi.fastutil.objects.Object2FloatOpenCustomHashMap
 *  org.slf4j.Logger
 */
package net.minecraft.util.worldupdate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenCustomHashMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.storage.ChunkStorage;
import net.minecraft.world.level.chunk.storage.RegionFile;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.slf4j.Logger;

public class WorldUpgrader {
    private static final Logger f_18797_ = LogUtils.getLogger();
    private static final ThreadFactory f_18798_ = new ThreadFactoryBuilder().setDaemon(true).build();
    private final WorldGenSettings f_185925_;
    private final boolean f_18800_;
    private final LevelStorageSource.LevelStorageAccess f_18801_;
    private final Thread f_18802_;
    private final DataFixer f_18803_;
    private volatile boolean f_18804_ = true;
    private volatile boolean f_18805_;
    private volatile float f_18806_;
    private volatile int f_18807_;
    private volatile int f_18808_;
    private volatile int f_18809_;
    private final Object2FloatMap<ResourceKey<Level>> f_18810_ = Object2FloatMaps.synchronize((Object2FloatMap)new Object2FloatOpenCustomHashMap(Util.m_137583_()));
    private volatile Component f_18811_ = Component.m_237115_("optimizeWorld.stage.counting");
    private static final Pattern f_18812_ = Pattern.compile("^r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca$");
    private final DimensionDataStorage f_18813_;

    public WorldUpgrader(LevelStorageSource.LevelStorageAccess p_185927_, DataFixer p_185928_, WorldGenSettings p_185929_, boolean p_185930_) {
        this.f_185925_ = p_185929_;
        this.f_18800_ = p_185930_;
        this.f_18803_ = p_185928_;
        this.f_18801_ = p_185927_;
        this.f_18813_ = new DimensionDataStorage(this.f_18801_.m_197394_(Level.f_46428_).resolve("data").toFile(), p_185928_);
        this.f_18802_ = f_18798_.newThread(this::m_18838_);
        this.f_18802_.setUncaughtExceptionHandler((p_18825_, p_18826_) -> {
            f_18797_.error("Error upgrading world", p_18826_);
            this.f_18811_ = Component.m_237115_("optimizeWorld.stage.failed");
            this.f_18805_ = true;
        });
        this.f_18802_.start();
    }

    public void m_18820_() {
        this.f_18804_ = false;
        try {
            this.f_18802_.join();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    private void m_18838_() {
        this.f_18807_ = 0;
        ImmutableMap.Builder $$0 = ImmutableMap.builder();
        ImmutableSet<ResourceKey<Level>> $$1 = this.f_185925_.m_64667_();
        for (ResourceKey $$2 : $$1) {
            List<ChunkPos> $$3 = this.m_18830_($$2);
            $$0.put((Object)$$2, $$3.listIterator());
            this.f_18807_ += $$3.size();
        }
        if (this.f_18807_ == 0) {
            this.f_18805_ = true;
            return;
        }
        float $$4 = this.f_18807_;
        ImmutableMap $$5 = $$0.build();
        ImmutableMap.Builder $$6 = ImmutableMap.builder();
        for (ResourceKey $$7 : $$1) {
            Path $$8 = this.f_18801_.m_197394_($$7);
            $$6.put((Object)$$7, (Object)new ChunkStorage($$8.resolve("region"), this.f_18803_, true));
        }
        ImmutableMap $$9 = $$6.build();
        long $$10 = Util.m_137550_();
        this.f_18811_ = Component.m_237115_("optimizeWorld.stage.upgrading");
        while (this.f_18804_) {
            boolean $$11 = false;
            float $$12 = 0.0f;
            for (ResourceKey $$13 : $$1) {
                ListIterator $$14 = (ListIterator)$$5.get((Object)$$13);
                ChunkStorage $$15 = (ChunkStorage)$$9.get((Object)$$13);
                if ($$14.hasNext()) {
                    ChunkPos $$16 = (ChunkPos)$$14.next();
                    boolean $$17 = false;
                    try {
                        CompoundTag $$18 = $$15.m_223454_($$16).join().orElse(null);
                        if ($$18 != null) {
                            boolean $$23;
                            int $$19 = ChunkStorage.m_63505_($$18);
                            ChunkGenerator $$20 = this.f_185925_.m_204655_().m_6246_(WorldGenSettings.m_190052_($$13)).m_63990_();
                            CompoundTag $$21 = $$15.m_188288_($$13, () -> this.f_18813_, $$18, $$20.m_187743_());
                            ChunkPos $$22 = new ChunkPos($$21.m_128451_("xPos"), $$21.m_128451_("zPos"));
                            if (!$$22.equals($$16)) {
                                f_18797_.warn("Chunk {} has invalid position {}", (Object)$$16, (Object)$$22);
                            }
                            boolean bl = $$23 = $$19 < SharedConstants.m_183709_().getWorldVersion();
                            if (this.f_18800_) {
                                $$23 = $$23 || $$21.m_128441_("Heightmaps");
                                $$21.m_128473_("Heightmaps");
                                $$23 = $$23 || $$21.m_128441_("isLightOn");
                                $$21.m_128473_("isLightOn");
                                ListTag $$24 = $$21.m_128437_("sections", 10);
                                for (int $$25 = 0; $$25 < $$24.size(); ++$$25) {
                                    CompoundTag $$26 = $$24.m_128728_($$25);
                                    $$23 = $$23 || $$26.m_128441_("BlockLight");
                                    $$26.m_128473_("BlockLight");
                                    $$23 = $$23 || $$26.m_128441_("SkyLight");
                                    $$26.m_128473_("SkyLight");
                                }
                            }
                            if ($$23) {
                                $$15.m_63502_($$16, $$21);
                                $$17 = true;
                            }
                        }
                    }
                    catch (CompletionException | ReportedException $$27) {
                        Throwable $$28 = $$27.getCause();
                        if ($$28 instanceof IOException) {
                            f_18797_.error("Error upgrading chunk {}", (Object)$$16, (Object)$$28);
                        }
                        throw $$27;
                    }
                    if ($$17) {
                        ++this.f_18808_;
                    } else {
                        ++this.f_18809_;
                    }
                    $$11 = true;
                }
                float $$29 = (float)$$14.nextIndex() / $$4;
                this.f_18810_.put((Object)$$13, $$29);
                $$12 += $$29;
            }
            this.f_18806_ = $$12;
            if ($$11) continue;
            this.f_18804_ = false;
        }
        this.f_18811_ = Component.m_237115_("optimizeWorld.stage.finished");
        for (ChunkStorage $$30 : $$9.values()) {
            try {
                $$30.close();
            }
            catch (IOException $$31) {
                f_18797_.error("Error upgrading chunk", (Throwable)$$31);
            }
        }
        this.f_18813_.m_78151_();
        $$10 = Util.m_137550_() - $$10;
        f_18797_.info("World optimizaton finished after {} ms", (Object)$$10);
        this.f_18805_ = true;
    }

    private List<ChunkPos> m_18830_(ResourceKey<Level> p_18831_) {
        File $$1 = this.f_18801_.m_197394_(p_18831_).toFile();
        File $$2 = new File($$1, "region");
        File[] $$3 = $$2.listFiles((p_18822_, p_18823_) -> p_18823_.endsWith(".mca"));
        if ($$3 == null) {
            return ImmutableList.of();
        }
        ArrayList $$4 = Lists.newArrayList();
        for (File $$5 : $$3) {
            Matcher $$6 = f_18812_.matcher($$5.getName());
            if (!$$6.matches()) continue;
            int $$7 = Integer.parseInt($$6.group(1)) << 5;
            int $$8 = Integer.parseInt($$6.group(2)) << 5;
            try (RegionFile $$9 = new RegionFile($$5.toPath(), $$2.toPath(), true);){
                for (int $$10 = 0; $$10 < 32; ++$$10) {
                    for (int $$11 = 0; $$11 < 32; ++$$11) {
                        ChunkPos $$12 = new ChunkPos($$10 + $$7, $$11 + $$8);
                        if (!$$9.m_63673_($$12)) continue;
                        $$4.add($$12);
                    }
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return $$4;
    }

    public boolean m_18829_() {
        return this.f_18805_;
    }

    public ImmutableSet<ResourceKey<Level>> m_18832_() {
        return this.f_185925_.m_64667_();
    }

    public float m_18827_(ResourceKey<Level> p_18828_) {
        return this.f_18810_.getFloat(p_18828_);
    }

    public float m_18833_() {
        return this.f_18806_;
    }

    public int m_18834_() {
        return this.f_18807_;
    }

    public int m_18835_() {
        return this.f_18808_;
    }

    public int m_18836_() {
        return this.f_18809_;
    }

    public Component m_18837_() {
        return this.f_18811_;
    }
}

