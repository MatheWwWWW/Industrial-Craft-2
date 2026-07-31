/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.shorts.ShortList
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.chunk.storage;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.levelgen.BelowZeroRetrogen;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.ticks.LevelChunkTicks;
import net.minecraft.world.ticks.ProtoChunkTicks;
import org.slf4j.Logger;

public class ChunkSerializer {
    private static final Codec<PalettedContainer<BlockState>> f_188227_ = PalettedContainer.m_238371_(Block.f_49791_, BlockState.f_61039_, PalettedContainer.Strategy.f_188137_, Blocks.f_50016_.m_49966_());
    private static final Logger f_63449_ = LogUtils.getLogger();
    private static final String f_156511_ = "UpgradeData";
    private static final String f_188228_ = "block_ticks";
    private static final String f_188229_ = "fluid_ticks";
    public static final String f_223441_ = "xPos";
    public static final String f_223442_ = "zPos";
    public static final String f_223443_ = "Heightmaps";
    public static final String f_223444_ = "isLightOn";
    public static final String f_223445_ = "sections";
    public static final String f_223446_ = "BlockLight";
    public static final String f_223447_ = "SkyLight";

    public static ProtoChunk m_188230_(ServerLevel p_188231_, PoiManager p_188232_, ChunkPos p_188233_, CompoundTag p_188234_) {
        ProtoChunk $$37;
        BlendingData $$30;
        ChunkPos $$4 = new ChunkPos(p_188234_.m_128451_(f_223441_), p_188234_.m_128451_(f_223442_));
        if (!Objects.equals(p_188233_, $$4)) {
            f_63449_.error("Chunk file at {} is in the wrong location; relocating. (Expected {}, got {})", new Object[]{p_188233_, p_188233_, $$4});
        }
        UpgradeData $$5 = p_188234_.m_128425_(f_156511_, 10) ? new UpgradeData(p_188234_.m_128469_(f_156511_), p_188231_) : UpgradeData.f_63320_;
        boolean $$6 = p_188234_.m_128471_(f_223444_);
        ListTag $$7 = p_188234_.m_128437_(f_223445_, 10);
        int $$8 = p_188231_.m_151559_();
        LevelChunkSection[] $$9 = new LevelChunkSection[$$8];
        boolean $$10 = p_188231_.m_6042_().f_223549_();
        ServerChunkCache $$11 = p_188231_.m_7726_();
        LevelLightEngine $$12 = ((ChunkSource)$$11).m_7827_();
        Registry<Biome> $$13 = p_188231_.m_5962_().m_175515_(Registry.f_122885_);
        Codec<PalettedContainerRO<Holder<Biome>>> $$14 = ChunkSerializer.m_188260_($$13);
        boolean $$15 = false;
        for (int $$16 = 0; $$16 < $$7.size(); ++$$16) {
            boolean $$26;
            CompoundTag $$17 = $$7.m_128728_($$16);
            byte $$18 = $$17.m_128445_("Y");
            int $$19 = p_188231_.m_151566_($$18);
            if ($$19 >= 0 && $$19 < $$9.length) {
                LevelChunkSection $$24;
                PalettedContainer<Holder<Biome>> $$23;
                PalettedContainer<BlockState> $$21;
                if ($$17.m_128425_("block_states", 10)) {
                    PalettedContainer $$20 = (PalettedContainer)f_188227_.parse((DynamicOps)NbtOps.f_128958_, (Object)$$17.m_128469_("block_states")).promotePartial(p_188283_ -> ChunkSerializer.m_188239_(p_188233_, $$18, p_188283_)).getOrThrow(false, arg_0 -> ((Logger)f_63449_).error(arg_0));
                } else {
                    $$21 = new PalettedContainer<BlockState>(Block.f_49791_, Blocks.f_50016_.m_49966_(), PalettedContainer.Strategy.f_188137_);
                }
                if ($$17.m_128425_("biomes", 10)) {
                    PalettedContainerRO $$22 = (PalettedContainerRO)$$14.parse((DynamicOps)NbtOps.f_128958_, (Object)$$17.m_128469_("biomes")).promotePartial(p_188274_ -> ChunkSerializer.m_188239_(p_188233_, $$18, p_188274_)).getOrThrow(false, arg_0 -> ((Logger)f_63449_).error(arg_0));
                } else {
                    $$23 = new PalettedContainer<Holder<Biome>>($$13.m_206115_(), $$13.m_206081_(Biomes.f_48202_), PalettedContainer.Strategy.f_188138_);
                }
                $$9[$$19] = $$24 = new LevelChunkSection($$18, $$21, $$23);
                p_188232_.m_27047_(p_188233_, $$24);
            }
            boolean $$25 = $$17.m_128425_(f_223446_, 7);
            boolean bl = $$26 = $$10 && $$17.m_128425_(f_223447_, 7);
            if (!$$25 && !$$26) continue;
            if (!$$15) {
                $$12.m_6462_(p_188233_, true);
                $$15 = true;
            }
            if ($$25) {
                $$12.m_5687_(LightLayer.BLOCK, SectionPos.m_123196_(p_188233_, $$18), new DataLayer($$17.m_128463_(f_223446_)), true);
            }
            if (!$$26) continue;
            $$12.m_5687_(LightLayer.SKY, SectionPos.m_123196_(p_188233_, $$18), new DataLayer($$17.m_128463_(f_223447_)), true);
        }
        long $$27 = p_188234_.m_128454_("InhabitedTime");
        ChunkStatus.ChunkType $$28 = ChunkSerializer.m_63485_(p_188234_);
        if (p_188234_.m_128425_("blending_data", 10)) {
            BlendingData $$29 = BlendingData.f_190254_.parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_188234_.m_128469_("blending_data"))).resultOrPartial(arg_0 -> ((Logger)f_63449_).error(arg_0)).orElse(null);
        } else {
            $$30 = null;
        }
        if ($$28 == ChunkStatus.ChunkType.LEVELCHUNK) {
            LevelChunkTicks<Block> $$31 = LevelChunkTicks.m_193185_(p_188234_.m_128437_(f_188228_, 10), p_188287_ -> Registry.f_122824_.m_6612_(ResourceLocation.m_135820_(p_188287_)), p_188233_);
            LevelChunkTicks<Fluid> $$32 = LevelChunkTicks.m_193185_(p_188234_.m_128437_(f_188229_, 10), p_188285_ -> Registry.f_122822_.m_6612_(ResourceLocation.m_135820_(p_188285_)), p_188233_);
            LevelChunk $$33 = new LevelChunk(p_188231_.m_6018_(), p_188233_, $$5, $$31, $$32, $$27, $$9, ChunkSerializer.m_196890_(p_188231_, p_188234_), $$30);
        } else {
            boolean $$40;
            ProtoChunkTicks<Block> $$34 = ProtoChunkTicks.m_193302_(p_188234_.m_128437_(f_188228_, 10), p_196906_ -> Registry.f_122824_.m_6612_(ResourceLocation.m_135820_(p_196906_)), p_188233_);
            ProtoChunkTicks<Fluid> $$35 = ProtoChunkTicks.m_193302_(p_188234_.m_128437_(f_188229_, 10), p_188276_ -> Registry.f_122822_.m_6612_(ResourceLocation.m_135820_(p_188276_)), p_188233_);
            ProtoChunk $$36 = new ProtoChunk(p_188233_, $$5, $$9, $$34, $$35, p_188231_, $$13, $$30);
            $$37 = $$36;
            $$37.m_6141_($$27);
            if (p_188234_.m_128425_("below_zero_retrogen", 10)) {
                BelowZeroRetrogen.f_188455_.parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_188234_.m_128469_("below_zero_retrogen"))).resultOrPartial(arg_0 -> ((Logger)f_63449_).error(arg_0)).ifPresent($$36::m_188183_);
            }
            ChunkStatus $$38 = ChunkStatus.m_62397_(p_188234_.m_128461_("Status"));
            $$36.m_7150_($$38);
            if ($$38.m_62427_(ChunkStatus.f_62322_)) {
                $$36.m_63209_($$12);
            }
            BelowZeroRetrogen $$39 = $$36.m_183376_();
            boolean bl = $$40 = $$38.m_62427_(ChunkStatus.f_62323_) || $$39 != null && $$39.m_188466_().m_62427_(ChunkStatus.f_62323_);
            if (!$$6 && $$40) {
                for (BlockPos $$41 : BlockPos.m_121976_(p_188233_.m_45604_(), p_188231_.m_141937_(), p_188233_.m_45605_(), p_188233_.m_45608_(), p_188231_.m_151558_() - 1, p_188233_.m_45609_())) {
                    if ($$37.m_8055_($$41).m_60791_() == 0) continue;
                    $$36.m_63277_($$41);
                }
            }
        }
        $$37.m_8094_($$6);
        CompoundTag $$42 = p_188234_.m_128469_(f_223443_);
        EnumSet<Heightmap.Types> $$43 = EnumSet.noneOf(Heightmap.Types.class);
        for (Heightmap.Types $$44 : ((ChunkAccess)$$37).m_6415_().m_62500_()) {
            String $$45 = $$44.m_64294_();
            if ($$42.m_128425_($$45, 12)) {
                $$37.m_6511_($$44, $$42.m_128467_($$45));
                continue;
            }
            $$43.add($$44);
        }
        Heightmap.m_64256_($$37, $$43);
        CompoundTag $$46 = p_188234_.m_128469_("structures");
        $$37.m_8040_(ChunkSerializer.m_188254_(StructurePieceSerializationContext.m_192770_(p_188231_), $$46, p_188231_.m_7328_()));
        $$37.m_62737_(ChunkSerializer.m_208154_(p_188231_.m_5962_(), p_188233_, $$46));
        if (p_188234_.m_128471_("shouldSave")) {
            $$37.m_8092_(true);
        }
        ListTag $$47 = p_188234_.m_128437_("PostProcessing", 9);
        for (int $$48 = 0; $$48 < $$47.size(); ++$$48) {
            ListTag $$49 = $$47.m_128744_($$48);
            for (int $$50 = 0; $$50 < $$49.size(); ++$$50) {
                ((ChunkAccess)$$37).m_6561_($$49.m_128757_($$50), $$48);
            }
        }
        if ($$28 == ChunkStatus.ChunkType.LEVELCHUNK) {
            return new ImposterProtoChunk((LevelChunk)((Object)$$37), false);
        }
        ProtoChunk $$51 = $$37;
        ListTag $$52 = p_188234_.m_128437_("entities", 10);
        for (int $$53 = 0; $$53 < $$52.size(); ++$$53) {
            $$51.m_63242_($$52.m_128728_($$53));
        }
        ListTag $$54 = p_188234_.m_128437_("block_entities", 10);
        for (int $$55 = 0; $$55 < $$54.size(); ++$$55) {
            CompoundTag $$56 = $$54.m_128728_($$55);
            $$37.m_5604_($$56);
        }
        ListTag $$57 = p_188234_.m_128437_("Lights", 9);
        for (int $$58 = 0; $$58 < $$57.size(); ++$$58) {
            ListTag $$59 = $$57.m_128744_($$58);
            for (int $$60 = 0; $$60 < $$59.size(); ++$$60) {
                $$51.m_63244_($$59.m_128757_($$60), $$58);
            }
        }
        CompoundTag $$61 = p_188234_.m_128469_("CarvingMasks");
        for (String $$62 : $$61.m_128431_()) {
            GenerationStep.Carving $$63 = GenerationStep.Carving.valueOf($$62);
            $$51.m_188186_($$63, new CarvingMask($$61.m_128467_($$62), $$37.m_141937_()));
        }
        return $$51;
    }

    private static void m_188239_(ChunkPos p_188240_, int p_188241_, String p_188242_) {
        f_63449_.error("Recoverable errors when loading section [" + p_188240_.f_45578_ + ", " + p_188241_ + ", " + p_188240_.f_45579_ + "]: " + p_188242_);
    }

    private static Codec<PalettedContainerRO<Holder<Biome>>> m_188260_(Registry<Biome> p_188261_) {
        return PalettedContainer.m_238418_(p_188261_.m_206115_(), p_188261_.m_206110_(), PalettedContainer.Strategy.f_188138_, p_188261_.m_206081_(Biomes.f_48202_));
    }

    public static CompoundTag m_63454_(ServerLevel p_63455_, ChunkAccess p_63456_) {
        UpgradeData $$6;
        BelowZeroRetrogen $$5;
        ChunkPos $$2 = p_63456_.m_7697_();
        CompoundTag $$3 = new CompoundTag();
        $$3.m_128405_("DataVersion", SharedConstants.m_183709_().getWorldVersion());
        $$3.m_128405_(f_223441_, $$2.f_45578_);
        $$3.m_128405_("yPos", p_63456_.m_151560_());
        $$3.m_128405_(f_223442_, $$2.f_45579_);
        $$3.m_128356_("LastUpdate", p_63455_.m_46467_());
        $$3.m_128356_("InhabitedTime", p_63456_.m_6319_());
        $$3.m_128359_("Status", p_63456_.m_6415_().m_62467_());
        BlendingData $$4 = p_63456_.m_183407_();
        if ($$4 != null) {
            BlendingData.f_190254_.encodeStart((DynamicOps)NbtOps.f_128958_, (Object)$$4).resultOrPartial(arg_0 -> ((Logger)f_63449_).error(arg_0)).ifPresent(p_196909_ -> $$3.m_128365_("blending_data", (Tag)p_196909_));
        }
        if (($$5 = p_63456_.m_183376_()) != null) {
            BelowZeroRetrogen.f_188455_.encodeStart((DynamicOps)NbtOps.f_128958_, (Object)$$5).resultOrPartial(arg_0 -> ((Logger)f_63449_).error(arg_0)).ifPresent(p_188279_ -> $$3.m_128365_("below_zero_retrogen", (Tag)p_188279_));
        }
        if (!($$6 = p_63456_.m_7387_()).m_63331_()) {
            $$3.m_128365_(f_156511_, $$6.m_63346_());
        }
        LevelChunkSection[] $$7 = p_63456_.m_7103_();
        ListTag $$8 = new ListTag();
        ThreadedLevelLightEngine $$9 = p_63455_.m_7726_().m_7827_();
        Registry<Biome> $$10 = p_63455_.m_5962_().m_175515_(Registry.f_122885_);
        Codec<PalettedContainerRO<Holder<Biome>>> $$11 = ChunkSerializer.m_188260_($$10);
        boolean $$12 = p_63456_.m_6332_();
        for (int $$13 = $$9.m_164447_(); $$13 < $$9.m_164448_(); ++$$13) {
            int $$14 = p_63456_.m_151566_($$13);
            boolean $$15 = $$14 >= 0 && $$14 < $$7.length;
            DataLayer $$16 = $$9.m_75814_(LightLayer.BLOCK).m_8079_(SectionPos.m_123196_($$2, $$13));
            DataLayer $$17 = $$9.m_75814_(LightLayer.SKY).m_8079_(SectionPos.m_123196_($$2, $$13));
            if (!$$15 && $$16 == null && $$17 == null) continue;
            CompoundTag $$18 = new CompoundTag();
            if ($$15) {
                LevelChunkSection $$19 = $$7[$$14];
                $$18.m_128365_("block_states", (Tag)f_188227_.encodeStart((DynamicOps)NbtOps.f_128958_, $$19.m_63019_()).getOrThrow(false, arg_0 -> ((Logger)f_63449_).error(arg_0)));
                $$18.m_128365_("biomes", (Tag)$$11.encodeStart((DynamicOps)NbtOps.f_128958_, $$19.m_187996_()).getOrThrow(false, arg_0 -> ((Logger)f_63449_).error(arg_0)));
            }
            if ($$16 != null && !$$16.m_62575_()) {
                $$18.m_128382_(f_223446_, $$16.m_7877_());
            }
            if ($$17 != null && !$$17.m_62575_()) {
                $$18.m_128382_(f_223447_, $$17.m_7877_());
            }
            if ($$18.m_128456_()) continue;
            $$18.m_128344_("Y", (byte)$$13);
            $$8.add($$18);
        }
        $$3.m_128365_(f_223445_, $$8);
        if ($$12) {
            $$3.m_128379_(f_223444_, true);
        }
        ListTag $$20 = new ListTag();
        for (BlockPos $$21 : p_63456_.m_5928_()) {
            CompoundTag $$22 = p_63456_.m_8051_($$21);
            if ($$22 == null) continue;
            $$20.add($$22);
        }
        $$3.m_128365_("block_entities", $$20);
        if (p_63456_.m_6415_().m_62494_() == ChunkStatus.ChunkType.PROTOCHUNK) {
            ProtoChunk $$23 = (ProtoChunk)p_63456_;
            ListTag $$24 = new ListTag();
            $$24.addAll($$23.m_63293_());
            $$3.m_128365_("entities", $$24);
            $$3.m_128365_("Lights", ChunkSerializer.m_63490_($$23.m_63291_()));
            CompoundTag $$25 = new CompoundTag();
            for (GenerationStep.Carving $$26 : GenerationStep.Carving.values()) {
                CarvingMask $$27 = $$23.m_183612_($$26);
                if ($$27 == null) continue;
                $$25.m_128388_($$26.toString(), $$27.m_187584_());
            }
            $$3.m_128365_("CarvingMasks", $$25);
        }
        ChunkSerializer.m_188235_(p_63455_, $$3, p_63456_.m_183568_());
        $$3.m_128365_("PostProcessing", ChunkSerializer.m_63490_(p_63456_.m_6720_()));
        CompoundTag $$28 = new CompoundTag();
        for (Map.Entry<Heightmap.Types, Heightmap> $$29 : p_63456_.m_6890_()) {
            if (!p_63456_.m_6415_().m_62500_().contains($$29.getKey())) continue;
            $$28.m_128365_($$29.getKey().m_64294_(), new LongArrayTag($$29.getValue().m_64239_()));
        }
        $$3.m_128365_(f_223443_, $$28);
        $$3.m_128365_("structures", ChunkSerializer.m_188249_(StructurePieceSerializationContext.m_192770_(p_63455_), $$2, p_63456_.m_6633_(), p_63456_.m_62769_()));
        return $$3;
    }

    private static void m_188235_(ServerLevel p_188236_, CompoundTag p_188237_, ChunkAccess.TicksToSave p_188238_) {
        long $$3 = p_188236_.m_6106_().m_6793_();
        p_188237_.m_128365_(f_188228_, p_188238_.f_187680_().m_183237_($$3, p_196894_ -> Registry.f_122824_.m_7981_((Block)p_196894_).toString()));
        p_188237_.m_128365_(f_188229_, p_188238_.f_187681_().m_183237_($$3, p_196896_ -> Registry.f_122822_.m_7981_((Fluid)p_196896_).toString()));
    }

    public static ChunkStatus.ChunkType m_63485_(@Nullable CompoundTag p_63486_) {
        if (p_63486_ != null) {
            return ChunkStatus.m_62397_(p_63486_.m_128461_("Status")).m_62494_();
        }
        return ChunkStatus.ChunkType.PROTOCHUNK;
    }

    @Nullable
    private static LevelChunk.PostLoadProcessor m_196890_(ServerLevel p_196891_, CompoundTag p_196892_) {
        ListTag $$2 = ChunkSerializer.m_196897_(p_196892_, "entities");
        ListTag $$3 = ChunkSerializer.m_196897_(p_196892_, "block_entities");
        if ($$2 == null && $$3 == null) {
            return null;
        }
        return p_196904_ -> {
            if ($$2 != null) {
                p_196891_.m_143311_(EntityType.m_147045_($$2, p_196891_));
            }
            if ($$3 != null) {
                for (int $$4 = 0; $$4 < $$3.size(); ++$$4) {
                    CompoundTag $$5 = $$3.m_128728_($$4);
                    boolean $$6 = $$5.m_128471_("keepPacked");
                    if ($$6) {
                        p_196904_.m_5604_($$5);
                        continue;
                    }
                    BlockPos $$7 = BlockEntity.m_187472_($$5);
                    BlockEntity $$8 = BlockEntity.m_155241_($$7, p_196904_.m_8055_($$7), $$5);
                    if ($$8 == null) continue;
                    p_196904_.m_142169_($$8);
                }
            }
        };
    }

    @Nullable
    private static ListTag m_196897_(CompoundTag p_196898_, String p_196899_) {
        ListTag $$2 = p_196898_.m_128437_(p_196899_, 10);
        return $$2.isEmpty() ? null : $$2;
    }

    private static CompoundTag m_188249_(StructurePieceSerializationContext p_188250_, ChunkPos p_188251_, Map<Structure, StructureStart> p_188252_, Map<Structure, LongSet> p_188253_) {
        CompoundTag $$4 = new CompoundTag();
        CompoundTag $$5 = new CompoundTag();
        Registry<Structure> $$6 = p_188250_.f_192763_().m_175515_(Registry.f_235725_);
        for (Map.Entry<Structure, StructureStart> $$7 : p_188252_.entrySet()) {
            ResourceLocation $$8 = $$6.m_7981_($$7.getKey());
            $$5.m_128365_($$8.toString(), $$7.getValue().m_192660_(p_188250_, p_188251_));
        }
        $$4.m_128365_("starts", $$5);
        CompoundTag $$9 = new CompoundTag();
        for (Map.Entry<Structure, LongSet> $$10 : p_188253_.entrySet()) {
            if ($$10.getValue().isEmpty()) continue;
            ResourceLocation $$11 = $$6.m_7981_($$10.getKey());
            $$9.m_128365_($$11.toString(), new LongArrayTag($$10.getValue()));
        }
        $$4.m_128365_("References", $$9);
        return $$4;
    }

    private static Map<Structure, StructureStart> m_188254_(StructurePieceSerializationContext p_188255_, CompoundTag p_188256_, long p_188257_) {
        HashMap $$3 = Maps.newHashMap();
        Registry<Structure> $$4 = p_188255_.f_192763_().m_175515_(Registry.f_235725_);
        CompoundTag $$5 = p_188256_.m_128469_("starts");
        for (String $$6 : $$5.m_128431_()) {
            ResourceLocation $$7 = ResourceLocation.m_135820_($$6);
            Structure $$8 = $$4.m_7745_($$7);
            if ($$8 == null) {
                f_63449_.error("Unknown structure start: {}", (Object)$$7);
                continue;
            }
            StructureStart $$9 = StructureStart.m_226857_(p_188255_, $$5.m_128469_($$6), p_188257_);
            if ($$9 == null) continue;
            $$3.put($$8, $$9);
        }
        return $$3;
    }

    private static Map<Structure, LongSet> m_208154_(RegistryAccess p_208155_, ChunkPos p_208156_, CompoundTag p_208157_) {
        HashMap $$3 = Maps.newHashMap();
        Registry<Structure> $$4 = p_208155_.m_175515_(Registry.f_235725_);
        CompoundTag $$5 = p_208157_.m_128469_("References");
        for (String $$6 : $$5.m_128431_()) {
            ResourceLocation $$7 = ResourceLocation.m_135820_($$6);
            Structure $$8 = $$4.m_7745_($$7);
            if ($$8 == null) {
                f_63449_.warn("Found reference to unknown structure '{}' in chunk {}, discarding", (Object)$$7, (Object)p_208156_);
                continue;
            }
            long[] $$9 = $$5.m_128467_($$6);
            if ($$9.length == 0) continue;
            $$3.put($$8, new LongOpenHashSet(Arrays.stream($$9).filter(p_208153_ -> {
                ChunkPos $$3 = new ChunkPos(p_208153_);
                if ($$3.m_45594_(p_208156_) > 8) {
                    f_63449_.warn("Found invalid structure reference [ {} @ {} ] for chunk {}.", new Object[]{$$7, $$3, p_208156_});
                    return false;
                }
                return true;
            }).toArray()));
        }
        return $$3;
    }

    public static ListTag m_63490_(ShortList[] p_63491_) {
        ListTag $$1 = new ListTag();
        for (ShortList $$2 : p_63491_) {
            ListTag $$3 = new ListTag();
            if ($$2 != null) {
                for (Short $$4 : $$2) {
                    $$3.add(ShortTag.m_129258_($$4));
                }
            }
            $$1.add($$3);
        }
        return $$1;
    }
}

