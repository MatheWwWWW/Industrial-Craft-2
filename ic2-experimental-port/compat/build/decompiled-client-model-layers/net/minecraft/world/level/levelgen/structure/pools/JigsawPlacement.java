/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Queues
 *  com.mojang.logging.LogUtils
 *  org.apache.commons.lang3.mutable.MutableObject
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen.structure.pools;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.EmptyPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.JigsawJunction;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;

public class JigsawPlacement {
    static final Logger f_210265_ = LogUtils.getLogger();

    public static Optional<Structure.GenerationStub> m_227238_(Structure.GenerationContext p_227239_, Holder<StructureTemplatePool> p_227240_, Optional<ResourceLocation> p_227241_, int p_227242_, BlockPos p_227243_, boolean p_227244_, Optional<Heightmap.Types> p_227245_, int p_227246_) {
        int $$28;
        BlockPos $$20;
        RegistryAccess $$8 = p_227239_.f_226621_();
        ChunkGenerator $$9 = p_227239_.f_226622_();
        StructureTemplateManager $$10 = p_227239_.f_226625_();
        LevelHeightAccessor $$11 = p_227239_.f_226629_();
        WorldgenRandom $$12 = p_227239_.f_226626_();
        Registry<StructureTemplatePool> $$13 = $$8.m_175515_(Registry.f_122884_);
        Rotation $$14 = Rotation.m_221990_($$12);
        StructureTemplatePool $$15 = p_227240_.m_203334_();
        StructurePoolElement $$16 = $$15.m_227355_($$12);
        if ($$16 == EmptyPoolElement.f_210175_) {
            return Optional.empty();
        }
        if (p_227241_.isPresent()) {
            ResourceLocation $$17 = p_227241_.get();
            Optional<BlockPos> $$18 = JigsawPlacement.m_227247_($$16, $$17, p_227243_, $$14, $$10, $$12);
            if ($$18.isEmpty()) {
                f_210265_.error("No starting jigsaw {} found in start pool {}", (Object)$$17, (Object)p_227240_.m_203543_().get().m_135782_());
                return Optional.empty();
            }
            BlockPos $$19 = $$18.get();
        } else {
            $$20 = p_227243_;
        }
        BlockPos $$21 = $$20.m_121996_(p_227243_);
        BlockPos $$22 = p_227243_.m_121996_($$21);
        PoolElementStructurePiece $$23 = new PoolElementStructurePiece($$10, $$16, $$22, $$16.m_210540_(), $$14, $$16.m_214015_($$10, $$22, $$14));
        BoundingBox $$24 = $$23.m_73547_();
        int $$25 = ($$24.m_162399_() + $$24.m_162395_()) / 2;
        int $$26 = ($$24.m_162401_() + $$24.m_162398_()) / 2;
        if (p_227245_.isPresent()) {
            int $$27 = p_227243_.m_123342_() + $$9.m_223221_($$25, $$26, p_227245_.get(), $$11, p_227239_.f_226624_());
        } else {
            $$28 = $$22.m_123342_();
        }
        int $$29 = $$24.m_162396_() + $$23.m_72647_();
        $$23.m_6324_(0, $$28 - $$29, 0);
        int $$30 = $$28 + $$21.m_123342_();
        return Optional.of(new Structure.GenerationStub(new BlockPos($$25, $$30, $$26), p_227237_ -> {
            ArrayList $$15 = Lists.newArrayList();
            $$15.add($$23);
            if (p_227242_ <= 0) {
                return;
            }
            AABB $$16 = new AABB($$25 - p_227246_, $$30 - p_227246_, $$26 - p_227246_, $$25 + p_227246_ + 1, $$30 + p_227246_ + 1, $$26 + p_227246_ + 1);
            VoxelShape $$17 = Shapes.m_83113_(Shapes.m_83064_($$16), Shapes.m_83064_(AABB.m_82321_($$24)), BooleanOp.f_82685_);
            JigsawPlacement.m_227210_(p_227239_.f_226624_(), p_227242_, p_227244_, $$9, $$10, $$11, $$12, $$13, $$23, $$15, $$17);
            $$15.forEach(p_227237_::m_142679_);
        }));
    }

    private static Optional<BlockPos> m_227247_(StructurePoolElement p_227248_, ResourceLocation p_227249_, BlockPos p_227250_, Rotation p_227251_, StructureTemplateManager p_227252_, WorldgenRandom p_227253_) {
        List<StructureTemplate.StructureBlockInfo> $$6 = p_227248_.m_213638_(p_227252_, p_227250_, p_227251_, p_227253_);
        Optional<BlockPos> $$7 = Optional.empty();
        for (StructureTemplate.StructureBlockInfo $$8 : $$6) {
            ResourceLocation $$9 = ResourceLocation.m_135820_($$8.f_74677_.m_128461_("name"));
            if (!p_227249_.equals($$9)) continue;
            $$7 = Optional.of($$8.f_74675_);
            break;
        }
        return $$7;
    }

    private static void m_227210_(RandomState p_227211_, int p_227212_, boolean p_227213_, ChunkGenerator p_227214_, StructureTemplateManager p_227215_, LevelHeightAccessor p_227216_, RandomSource p_227217_, Registry<StructureTemplatePool> p_227218_, PoolElementStructurePiece p_227219_, List<PoolElementStructurePiece> p_227220_, VoxelShape p_227221_) {
        Placer $$11 = new Placer(p_227218_, p_227212_, p_227214_, p_227215_, p_227220_, p_227217_);
        $$11.f_210321_.addLast(new PieceState(p_227219_, (MutableObject<VoxelShape>)new MutableObject((Object)p_227221_), 0));
        while (!$$11.f_210321_.isEmpty()) {
            PieceState $$12 = $$11.f_210321_.removeFirst();
            $$11.m_227264_($$12.f_210307_, $$12.f_210308_, $$12.f_210309_, p_227213_, p_227216_, p_227211_);
        }
    }

    public static boolean m_227203_(ServerLevel p_227204_, Holder<StructureTemplatePool> p_227205_, ResourceLocation p_227206_, int p_227207_, BlockPos p_227208_, boolean p_227209_) {
        ChunkGenerator $$6 = p_227204_.m_7726_().m_8481_();
        StructureTemplateManager $$7 = p_227204_.m_215082_();
        StructureManager $$8 = p_227204_.m_215010_();
        RandomSource $$9 = p_227204_.m_213780_();
        Structure.GenerationContext $$10 = new Structure.GenerationContext(p_227204_.m_5962_(), $$6, $$6.m_62218_(), p_227204_.m_7726_().m_214994_(), $$7, p_227204_.m_7328_(), new ChunkPos(p_227208_), p_227204_, p_227255_ -> true);
        Optional<Structure.GenerationStub> $$11 = JigsawPlacement.m_227238_($$10, p_227205_, Optional.of(p_227206_), p_227207_, p_227208_, false, Optional.empty(), 128);
        if ($$11.isPresent()) {
            StructurePiecesBuilder $$12 = $$11.get().m_226677_();
            for (StructurePiece $$13 : $$12.m_192780_().f_192741_()) {
                if (!($$13 instanceof PoolElementStructurePiece)) continue;
                PoolElementStructurePiece $$14 = (PoolElementStructurePiece)$$13;
                $$14.m_226509_(p_227204_, $$8, $$6, $$9, BoundingBox.m_71044_(), p_227208_, p_227209_);
            }
            return true;
        }
        return false;
    }

    static final class Placer {
        private final Registry<StructureTemplatePool> f_210314_;
        private final int f_210315_;
        private final ChunkGenerator f_210317_;
        private final StructureTemplateManager f_227256_;
        private final List<? super PoolElementStructurePiece> f_210319_;
        private final RandomSource f_210320_;
        final Deque<PieceState> f_210321_ = Queues.newArrayDeque();

        Placer(Registry<StructureTemplatePool> p_227258_, int p_227259_, ChunkGenerator p_227260_, StructureTemplateManager p_227261_, List<? super PoolElementStructurePiece> p_227262_, RandomSource p_227263_) {
            this.f_210314_ = p_227258_;
            this.f_210315_ = p_227259_;
            this.f_210317_ = p_227260_;
            this.f_227256_ = p_227261_;
            this.f_210319_ = p_227262_;
            this.f_210320_ = p_227263_;
        }

        void m_227264_(PoolElementStructurePiece p_227265_, MutableObject<VoxelShape> p_227266_, int p_227267_, boolean p_227268_, LevelHeightAccessor p_227269_, RandomState p_227270_) {
            StructurePoolElement $$6 = p_227265_.m_209918_();
            BlockPos $$7 = p_227265_.m_72646_();
            Rotation $$8 = p_227265_.m_6830_();
            StructureTemplatePool.Projection $$9 = $$6.m_210539_();
            boolean $$10 = $$9 == StructureTemplatePool.Projection.RIGID;
            MutableObject $$11 = new MutableObject();
            BoundingBox $$12 = p_227265_.m_73547_();
            int $$13 = $$12.m_162396_();
            block0: for (StructureTemplate.StructureBlockInfo $$14 : $$6.m_213638_(this.f_227256_, $$7, $$8, this.f_210320_)) {
                StructurePoolElement $$28;
                MutableObject<VoxelShape> $$26;
                Direction $$15 = JigsawBlock.m_54250_($$14.f_74676_);
                BlockPos $$16 = $$14.f_74675_;
                BlockPos $$17 = $$16.m_121945_($$15);
                int $$18 = $$16.m_123342_() - $$13;
                int $$19 = -1;
                ResourceLocation $$20 = new ResourceLocation($$14.f_74677_.m_128461_("pool"));
                Optional<StructureTemplatePool> $$21 = this.f_210314_.m_6612_($$20);
                if (!$$21.isPresent() || $$21.get().m_210590_() == 0 && !Objects.equals($$20, Pools.f_127186_.m_135782_())) {
                    f_210265_.warn("Empty or non-existent pool: {}", (Object)$$20);
                    continue;
                }
                ResourceLocation $$22 = $$21.get().m_210573_();
                Optional<StructureTemplatePool> $$23 = this.f_210314_.m_6612_($$22);
                if (!$$23.isPresent() || $$23.get().m_210590_() == 0 && !Objects.equals($$22, Pools.f_127186_.m_135782_())) {
                    f_210265_.warn("Empty or non-existent fallback pool: {}", (Object)$$22);
                    continue;
                }
                boolean $$24 = $$12.m_71051_($$17);
                if ($$24) {
                    MutableObject $$25 = $$11;
                    if ($$11.getValue() == null) {
                        $$11.setValue((Object)Shapes.m_83064_(AABB.m_82321_($$12)));
                    }
                } else {
                    $$26 = p_227266_;
                }
                ArrayList $$27 = Lists.newArrayList();
                if (p_227267_ != this.f_210315_) {
                    $$27.addAll($$21.get().m_227362_(this.f_210320_));
                }
                $$27.addAll($$23.get().m_227362_(this.f_210320_));
                Iterator iterator = $$27.iterator();
                while (iterator.hasNext() && ($$28 = (StructurePoolElement)iterator.next()) != EmptyPoolElement.f_210175_) {
                    for (Rotation $$29 : Rotation.m_221992_(this.f_210320_)) {
                        int $$33;
                        List<StructureTemplate.StructureBlockInfo> $$30 = $$28.m_213638_(this.f_227256_, BlockPos.f_121853_, $$29, this.f_210320_);
                        BoundingBox $$31 = $$28.m_214015_(this.f_227256_, BlockPos.f_121853_, $$29);
                        if (!p_227268_ || $$31.m_71057_() > 16) {
                            boolean $$32 = false;
                        } else {
                            $$33 = $$30.stream().mapToInt(p_210332_ -> {
                                if (!$$31.m_71051_(p_210332_.f_74675_.m_121945_(JigsawBlock.m_54250_(p_210332_.f_74676_)))) {
                                    return 0;
                                }
                                ResourceLocation $$2 = new ResourceLocation(p_210332_.f_74677_.m_128461_("pool"));
                                Optional<StructureTemplatePool> $$3 = this.f_210314_.m_6612_($$2);
                                Optional<Integer> $$4 = $$3.flatMap(p_210344_ -> this.f_210314_.m_6612_(p_210344_.m_210573_()));
                                int $$5 = $$3.map(p_210342_ -> p_210342_.m_227357_(this.f_227256_)).orElse(0);
                                int $$6 = $$4.map(p_210340_ -> p_210340_.m_227357_(this.f_227256_)).orElse(0);
                                return Math.max($$5, $$6);
                            }).max().orElse(0);
                        }
                        for (StructureTemplate.StructureBlockInfo $$34 : $$30) {
                            int $$55;
                            int $$51;
                            int $$44;
                            if (!JigsawBlock.m_54245_($$14, $$34)) continue;
                            BlockPos $$35 = $$34.f_74675_;
                            BlockPos $$36 = $$17.m_121996_($$35);
                            BoundingBox $$37 = $$28.m_214015_(this.f_227256_, $$36, $$29);
                            int $$38 = $$37.m_162396_();
                            StructureTemplatePool.Projection $$39 = $$28.m_210539_();
                            boolean $$40 = $$39 == StructureTemplatePool.Projection.RIGID;
                            int $$41 = $$35.m_123342_();
                            int $$42 = $$18 - $$41 + JigsawBlock.m_54250_($$14.f_74676_).m_122430_();
                            if ($$10 && $$40) {
                                int $$43 = $$13 + $$42;
                            } else {
                                if ($$19 == -1) {
                                    $$19 = this.f_210317_.m_223221_($$16.m_123341_(), $$16.m_123343_(), Heightmap.Types.WORLD_SURFACE_WG, p_227269_, p_227270_);
                                }
                                $$44 = $$19 - $$41;
                            }
                            int $$45 = $$44 - $$38;
                            BoundingBox $$46 = $$37.m_71045_(0, $$45, 0);
                            BlockPos $$47 = $$36.m_7918_(0, $$45, 0);
                            if ($$33 > 0) {
                                int $$48 = Math.max($$33 + 1, $$46.m_162400_() - $$46.m_162396_());
                                $$46.m_162371_(new BlockPos($$46.m_162395_(), $$46.m_162396_() + $$48, $$46.m_162398_()));
                            }
                            if (Shapes.m_83157_((VoxelShape)$$26.getValue(), Shapes.m_83064_(AABB.m_82321_($$46).m_82406_(0.25)), BooleanOp.f_82683_)) continue;
                            $$26.setValue((Object)Shapes.m_83148_((VoxelShape)$$26.getValue(), Shapes.m_83064_(AABB.m_82321_($$46)), BooleanOp.f_82685_));
                            int $$49 = p_227265_.m_72647_();
                            if ($$40) {
                                int $$50 = $$49 - $$42;
                            } else {
                                $$51 = $$28.m_210540_();
                            }
                            PoolElementStructurePiece $$52 = new PoolElementStructurePiece(this.f_227256_, $$28, $$47, $$51, $$29, $$46);
                            if ($$10) {
                                int $$53 = $$13 + $$18;
                            } else if ($$40) {
                                int $$54 = $$44 + $$41;
                            } else {
                                if ($$19 == -1) {
                                    $$19 = this.f_210317_.m_223221_($$16.m_123341_(), $$16.m_123343_(), Heightmap.Types.WORLD_SURFACE_WG, p_227269_, p_227270_);
                                }
                                $$55 = $$19 + $$42 / 2;
                            }
                            p_227265_.m_209916_(new JigsawJunction($$17.m_123341_(), (int)($$55 - $$18 + $$49), $$17.m_123343_(), $$42, $$39));
                            $$52.m_209916_(new JigsawJunction($$16.m_123341_(), $$55 - $$41 + $$51, $$16.m_123343_(), -$$42, $$9));
                            this.f_210319_.add($$52);
                            if (p_227267_ + 1 > this.f_210315_) continue block0;
                            this.f_210321_.addLast(new PieceState($$52, $$26, p_227267_ + 1));
                            continue block0;
                        }
                    }
                }
            }
        }
    }

    static final class PieceState {
        final PoolElementStructurePiece f_210307_;
        final MutableObject<VoxelShape> f_210308_;
        final int f_210309_;

        PieceState(PoolElementStructurePiece p_210311_, MutableObject<VoxelShape> p_210312_, int p_210313_) {
            this.f_210307_ = p_210311_;
            this.f_210308_ = p_210312_;
            this.f_210309_ = p_210313_;
        }
    }
}

