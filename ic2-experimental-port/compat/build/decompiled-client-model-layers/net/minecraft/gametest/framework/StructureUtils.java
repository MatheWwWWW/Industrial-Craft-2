/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.io.IOUtils
 *  org.slf4j.Logger
 */
package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.structures.NbtToSnbt;
import net.minecraft.data.structures.StructureUpdater;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.LevelTicks;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class StructureUtils {
    private static final Logger f_177742_ = LogUtils.getLogger();
    public static final String f_177741_ = "gameteststructures";
    public static String f_127833_ = "gameteststructures";
    private static final int f_177743_ = 4;

    public static Rotation m_127835_(int p_127836_) {
        switch (p_127836_) {
            case 0: {
                return Rotation.NONE;
            }
            case 1: {
                return Rotation.CLOCKWISE_90;
            }
            case 2: {
                return Rotation.CLOCKWISE_180;
            }
            case 3: {
                return Rotation.COUNTERCLOCKWISE_90;
            }
        }
        throw new IllegalArgumentException("rotationSteps must be a value from 0-3. Got value " + p_127836_);
    }

    public static int m_177751_(Rotation p_177752_) {
        switch (p_177752_) {
            case NONE: {
                return 0;
            }
            case CLOCKWISE_90: {
                return 1;
            }
            case CLOCKWISE_180: {
                return 2;
            }
            case COUNTERCLOCKWISE_90: {
                return 3;
            }
        }
        throw new IllegalArgumentException("Unknown rotation value, don't know how many steps it represents: " + p_177752_);
    }

    public static void main(String[] p_177771_) throws IOException {
        Bootstrap.m_135870_();
        Files.walk(Paths.get(f_127833_, new String[0]), new FileVisitOption[0]).filter(p_177775_ -> p_177775_.toString().endsWith(".snbt")).forEach(p_177773_ -> {
            try {
                String $$1 = Files.readString(p_177773_);
                CompoundTag $$2 = NbtUtils.m_178024_($$1);
                CompoundTag $$3 = StructureUpdater.m_176822_(p_177773_.toString(), $$2);
                NbtToSnbt.m_236377_(CachedOutput.f_236016_, p_177773_, NbtUtils.m_178063_($$3));
            }
            catch (CommandSyntaxException | IOException $$4) {
                f_177742_.error("Something went wrong upgrading: {}", p_177773_, (Object)$$4);
            }
        });
    }

    public static AABB m_127847_(StructureBlockEntity p_127848_) {
        BlockPos $$1 = p_127848_.m_58899_();
        BlockPos $$2 = $$1.m_121955_(p_127848_.m_155805_().m_7918_(-1, -1, -1));
        BlockPos $$3 = StructureTemplate.m_74593_($$2, Mirror.NONE, p_127848_.m_59906_(), $$1);
        return new AABB($$1, $$3);
    }

    public static BoundingBox m_127904_(StructureBlockEntity p_127905_) {
        BlockPos $$1 = p_127905_.m_58899_();
        BlockPos $$2 = $$1.m_121955_(p_127905_.m_155805_().m_7918_(-1, -1, -1));
        BlockPos $$3 = StructureTemplate.m_74593_($$2, Mirror.NONE, p_127905_.m_59906_(), $$1);
        return BoundingBox.m_162375_($$1, $$3);
    }

    public static void m_127875_(BlockPos p_127876_, BlockPos p_127877_, Rotation p_127878_, ServerLevel p_127879_) {
        BlockPos $$4 = StructureTemplate.m_74593_(p_127876_.m_121955_(p_127877_), Mirror.NONE, p_127878_, p_127876_);
        p_127879_.m_46597_($$4, Blocks.f_50272_.m_49966_());
        CommandBlockEntity $$5 = (CommandBlockEntity)p_127879_.m_7702_($$4);
        $$5.m_59141_().m_6590_("test runthis");
        BlockPos $$6 = StructureTemplate.m_74593_($$4.m_7918_(0, 0, -1), Mirror.NONE, p_127878_, $$4);
        p_127879_.m_46597_($$6, Blocks.f_50124_.m_49966_().m_60717_(p_127878_));
    }

    public static void m_177764_(String p_177765_, BlockPos p_177766_, Vec3i p_177767_, Rotation p_177768_, ServerLevel p_177769_) {
        BoundingBox $$5 = StructureUtils.m_177760_(p_177766_, p_177767_, p_177768_);
        StructureUtils.m_127849_($$5, p_177766_.m_123342_(), p_177769_);
        p_177769_.m_46597_(p_177766_, Blocks.f_50677_.m_49966_());
        StructureBlockEntity $$6 = (StructureBlockEntity)p_177769_.m_7702_(p_177766_);
        $$6.m_59876_(false);
        $$6.m_59874_(new ResourceLocation(p_177765_));
        $$6.m_155797_(p_177767_);
        $$6.m_59860_(StructureMode.SAVE);
        $$6.m_59898_(true);
    }

    /*
     * WARNING - void declaration
     */
    public static StructureBlockEntity m_127883_(String p_127884_, BlockPos p_127885_, Rotation p_127886_, int p_127887_, ServerLevel p_127888_, boolean p_127889_) {
        void $$12;
        Vec3i $$6 = StructureUtils.m_127880_(p_127884_, p_127888_).m_163801_();
        BoundingBox $$7 = StructureUtils.m_177760_(p_127885_, $$6, p_127886_);
        if (p_127886_ == Rotation.NONE) {
            BlockPos $$8 = p_127885_;
        } else if (p_127886_ == Rotation.CLOCKWISE_90) {
            BlockPos $$9 = p_127885_.m_7918_($$6.m_123343_() - 1, 0, 0);
        } else if (p_127886_ == Rotation.CLOCKWISE_180) {
            BlockPos $$10 = p_127885_.m_7918_($$6.m_123341_() - 1, 0, $$6.m_123343_() - 1);
        } else if (p_127886_ == Rotation.COUNTERCLOCKWISE_90) {
            BlockPos $$11 = p_127885_.m_7918_(0, 0, $$6.m_123341_() - 1);
        } else {
            throw new IllegalArgumentException("Invalid rotation: " + p_127886_);
        }
        StructureUtils.m_127857_(p_127885_, p_127888_);
        StructureUtils.m_127849_($$7, p_127885_.m_123342_(), p_127888_);
        StructureBlockEntity $$13 = StructureUtils.m_127890_(p_127884_, (BlockPos)$$12, p_127886_, p_127888_, p_127889_);
        ((LevelTicks)p_127888_.m_183326_()).m_193234_($$7);
        p_127888_.m_8722_($$7);
        return $$13;
    }

    private static void m_127857_(BlockPos p_127858_, ServerLevel p_127859_) {
        ChunkPos $$2 = new ChunkPos(p_127858_);
        for (int $$3 = -1; $$3 < 4; ++$$3) {
            for (int $$4 = -1; $$4 < 4; ++$$4) {
                int $$5 = $$2.f_45578_ + $$3;
                int $$6 = $$2.f_45579_ + $$4;
                p_127859_.m_8602_($$5, $$6, true);
            }
        }
    }

    public static void m_127849_(BoundingBox p_127850_, int p_127851_, ServerLevel p_127852_) {
        BoundingBox $$3 = new BoundingBox(p_127850_.m_162395_() - 2, p_127850_.m_162396_() - 3, p_127850_.m_162398_() - 3, p_127850_.m_162399_() + 3, p_127850_.m_162400_() + 20, p_127850_.m_162401_() + 3);
        BlockPos.m_121919_($$3).forEach(p_177748_ -> StructureUtils.m_127841_(p_127851_, p_177748_, p_127852_));
        ((LevelTicks)p_127852_.m_183326_()).m_193234_($$3);
        p_127852_.m_8722_($$3);
        AABB $$4 = new AABB($$3.m_162395_(), $$3.m_162396_(), $$3.m_162398_(), $$3.m_162399_(), $$3.m_162400_(), $$3.m_162401_());
        List<Entity> $$5 = p_127852_.m_6443_(Entity.class, $$4, p_177750_ -> !(p_177750_ instanceof Player));
        $$5.forEach(Entity::m_146870_);
    }

    public static BoundingBox m_177760_(BlockPos p_177761_, Vec3i p_177762_, Rotation p_177763_) {
        BlockPos $$3 = p_177761_.m_121955_(p_177762_).m_7918_(-1, -1, -1);
        BlockPos $$4 = StructureTemplate.m_74593_($$3, Mirror.NONE, p_177763_, p_177761_);
        BoundingBox $$5 = BoundingBox.m_162375_(p_177761_, $$4);
        int $$6 = Math.min($$5.m_162395_(), $$5.m_162399_());
        int $$7 = Math.min($$5.m_162398_(), $$5.m_162401_());
        return $$5.m_162367_(p_177761_.m_123341_() - $$6, 0, p_177761_.m_123343_() - $$7);
    }

    public static Optional<BlockPos> m_127853_(BlockPos p_127854_, int p_127855_, ServerLevel p_127856_) {
        return StructureUtils.m_127910_(p_127854_, p_127855_, p_127856_).stream().filter(p_177756_ -> StructureUtils.m_127867_(p_177756_, p_127854_, p_127856_)).findFirst();
    }

    @Nullable
    public static BlockPos m_127906_(BlockPos p_127907_, int p_127908_, ServerLevel p_127909_) {
        Comparator<BlockPos> $$3 = Comparator.comparingInt(p_177759_ -> p_177759_.m_123333_(p_127907_));
        Collection<BlockPos> $$4 = StructureUtils.m_127910_(p_127907_, p_127908_, p_127909_);
        Optional<BlockPos> $$5 = $$4.stream().min($$3);
        return $$5.orElse(null);
    }

    public static Collection<BlockPos> m_127910_(BlockPos p_127911_, int p_127912_, ServerLevel p_127913_) {
        ArrayList $$3 = Lists.newArrayList();
        AABB $$4 = new AABB(p_127911_);
        $$4 = $$4.m_82400_(p_127912_);
        for (int $$5 = (int)$$4.f_82288_; $$5 <= (int)$$4.f_82291_; ++$$5) {
            for (int $$6 = (int)$$4.f_82289_; $$6 <= (int)$$4.f_82292_; ++$$6) {
                for (int $$7 = (int)$$4.f_82290_; $$7 <= (int)$$4.f_82293_; ++$$7) {
                    BlockPos $$8 = new BlockPos($$5, $$6, $$7);
                    BlockState $$9 = p_127913_.m_8055_($$8);
                    if (!$$9.m_60713_(Blocks.f_50677_)) continue;
                    $$3.add($$8);
                }
            }
        }
        return $$3;
    }

    private static StructureTemplate m_127880_(String p_127881_, ServerLevel p_127882_) {
        StructureTemplateManager $$2 = p_127882_.m_215082_();
        Optional<StructureTemplate> $$3 = $$2.m_230407_(new ResourceLocation(p_127881_));
        if ($$3.isPresent()) {
            return $$3.get();
        }
        String $$4 = p_127881_ + ".snbt";
        Path $$5 = Paths.get(f_127833_, $$4);
        CompoundTag $$6 = StructureUtils.m_127902_($$5);
        if ($$6 == null) {
            throw new RuntimeException("Could not find structure file " + $$5 + ", and the structure is not available in the world structures either.");
        }
        return $$2.m_230404_($$6);
    }

    private static StructureBlockEntity m_127890_(String p_127891_, BlockPos p_127892_, Rotation p_127893_, ServerLevel p_127894_, boolean p_127895_) {
        p_127894_.m_46597_(p_127892_, Blocks.f_50677_.m_49966_());
        StructureBlockEntity $$5 = (StructureBlockEntity)p_127894_.m_7702_(p_127892_);
        $$5.m_59860_(StructureMode.LOAD);
        $$5.m_59883_(p_127893_);
        $$5.m_59876_(false);
        $$5.m_59874_(new ResourceLocation(p_127891_));
        $$5.m_59844_(p_127894_, p_127895_);
        if ($$5.m_155805_() != Vec3i.f_123288_) {
            return $$5;
        }
        StructureTemplate $$6 = StructureUtils.m_127880_(p_127891_, p_127894_);
        $$5.m_59847_(p_127894_, p_127895_, $$6);
        if ($$5.m_155805_() == Vec3i.f_123288_) {
            throw new RuntimeException("Failed to load structure " + p_127891_);
        }
        return $$5;
    }

    @Nullable
    private static CompoundTag m_127902_(Path p_127903_) {
        try {
            BufferedReader $$1 = Files.newBufferedReader(p_127903_);
            String $$2 = IOUtils.toString((Reader)$$1);
            return NbtUtils.m_178024_($$2);
        }
        catch (IOException $$3) {
            return null;
        }
        catch (CommandSyntaxException $$4) {
            throw new RuntimeException("Error while trying to load structure " + p_127903_, $$4);
        }
    }

    private static void m_127841_(int p_127842_, BlockPos p_127843_, ServerLevel p_127844_) {
        BlockState $$3 = null;
        FlatLevelGeneratorSettings $$4 = FlatLevelGeneratorSettings.m_211734_(p_127844_.m_5962_().m_175515_(Registry.f_122885_), p_127844_.m_5962_().m_175515_(Registry.f_211073_));
        List<BlockState> $$5 = $$4.m_161917_();
        int $$6 = p_127843_.m_123342_() - p_127844_.m_141937_();
        if (p_127843_.m_123342_() < p_127842_ && $$6 > 0 && $$6 <= $$5.size()) {
            $$3 = $$5.get($$6 - 1);
        }
        if ($$3 == null) {
            $$3 = Blocks.f_50016_.m_49966_();
        }
        BlockInput $$7 = new BlockInput($$3, Collections.emptySet(), null);
        $$7.m_114670_(p_127844_, p_127843_, 2);
        p_127844_.m_6289_(p_127843_, $$3.m_60734_());
    }

    private static boolean m_127867_(BlockPos p_127868_, BlockPos p_127869_, ServerLevel p_127870_) {
        StructureBlockEntity $$3 = (StructureBlockEntity)p_127870_.m_7702_(p_127868_);
        AABB $$4 = StructureUtils.m_127847_($$3).m_82400_(1.0);
        return $$4.m_82390_(Vec3.m_82512_(p_127869_));
    }
}

