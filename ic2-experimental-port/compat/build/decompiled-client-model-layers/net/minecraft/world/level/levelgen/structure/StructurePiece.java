/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.ImmutableSet;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicOps;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.material.FluidState;
import org.slf4j.Logger;

public abstract class StructurePiece {
    private static final Logger f_163536_ = LogUtils.getLogger();
    protected static final BlockState f_73382_ = Blocks.f_50627_.m_49966_();
    protected BoundingBox f_73383_;
    @Nullable
    private Direction f_73377_;
    private Mirror f_73378_;
    private Rotation f_73379_;
    protected int f_73384_;
    private final StructurePieceType f_73380_;
    private static final Set<Block> f_73381_ = ImmutableSet.builder().add((Object)Blocks.f_50198_).add((Object)Blocks.f_50081_).add((Object)Blocks.f_50082_).add((Object)Blocks.f_50132_).add((Object)Blocks.f_50479_).add((Object)Blocks.f_50483_).add((Object)Blocks.f_50482_).add((Object)Blocks.f_50480_).add((Object)Blocks.f_50481_).add((Object)Blocks.f_50155_).add((Object)Blocks.f_50183_).build();

    protected StructurePiece(StructurePieceType p_209994_, int p_209995_, BoundingBox p_209996_) {
        this.f_73380_ = p_209994_;
        this.f_73384_ = p_209995_;
        this.f_73383_ = p_209996_;
    }

    public StructurePiece(StructurePieceType p_209998_, CompoundTag p_209999_) {
        this(p_209998_, p_209999_.m_128451_("GD"), (BoundingBox)BoundingBox.f_162354_.parse((DynamicOps)NbtOps.f_128958_, (Object)p_209999_.m_128423_("BB")).resultOrPartial(arg_0 -> ((Logger)f_163536_).error(arg_0)).orElseThrow(() -> new IllegalArgumentException("Invalid boundingbox")));
        int $$2 = p_209999_.m_128451_("O");
        this.m_73519_($$2 == -1 ? null : Direction.m_122407_($$2));
    }

    protected static BoundingBox m_163541_(int p_163542_, int p_163543_, int p_163544_, Direction p_163545_, int p_163546_, int p_163547_, int p_163548_) {
        if (p_163545_.m_122434_() == Direction.Axis.Z) {
            return new BoundingBox(p_163542_, p_163543_, p_163544_, p_163542_ + p_163546_ - 1, p_163543_ + p_163547_ - 1, p_163544_ + p_163548_ - 1);
        }
        return new BoundingBox(p_163542_, p_163543_, p_163544_, p_163542_ + p_163548_ - 1, p_163543_ + p_163547_ - 1, p_163544_ + p_163546_ - 1);
    }

    protected static Direction m_226760_(RandomSource p_226761_) {
        return Direction.Plane.HORIZONTAL.m_235690_(p_226761_);
    }

    public final CompoundTag m_192644_(StructurePieceSerializationContext p_192645_) {
        CompoundTag $$1 = new CompoundTag();
        $$1.m_128359_("id", Registry.f_122843_.m_7981_(this.m_210000_()).toString());
        BoundingBox.f_162354_.encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.f_73383_).resultOrPartial(arg_0 -> ((Logger)f_163536_).error(arg_0)).ifPresent(p_163579_ -> $$1.m_128365_("BB", (Tag)p_163579_));
        Direction $$2 = this.m_73549_();
        $$1.m_128405_("O", $$2 == null ? -1 : $$2.m_122416_());
        $$1.m_128405_("GD", this.f_73384_);
        this.m_183620_(p_192645_, $$1);
        return $$1;
    }

    protected abstract void m_183620_(StructurePieceSerializationContext var1, CompoundTag var2);

    public void m_214092_(StructurePiece p_226835_, StructurePieceAccessor p_226836_, RandomSource p_226837_) {
    }

    public abstract void m_213694_(WorldGenLevel var1, StructureManager var2, ChunkGenerator var3, RandomSource var4, BoundingBox var5, ChunkPos var6, BlockPos var7);

    public BoundingBox m_73547_() {
        return this.f_73383_;
    }

    public int m_73548_() {
        return this.f_73384_;
    }

    public void m_226758_(int p_226759_) {
        this.f_73384_ = p_226759_;
    }

    public boolean m_73411_(ChunkPos p_73412_, int p_73413_) {
        int $$2 = p_73412_.m_45604_();
        int $$3 = p_73412_.m_45605_();
        return this.f_73383_.m_71019_($$2 - p_73413_, $$3 - p_73413_, $$2 + 15 + p_73413_, $$3 + 15 + p_73413_);
    }

    public BlockPos m_142171_() {
        return new BlockPos(this.f_73383_.m_162394_());
    }

    protected BlockPos.MutableBlockPos m_163582_(int p_163583_, int p_163584_, int p_163585_) {
        return new BlockPos.MutableBlockPos(this.m_73392_(p_163583_, p_163585_), this.m_73544_(p_163584_), this.m_73525_(p_163583_, p_163585_));
    }

    protected int m_73392_(int p_73393_, int p_73394_) {
        Direction $$2 = this.m_73549_();
        if ($$2 == null) {
            return p_73393_;
        }
        switch ($$2) {
            case NORTH: 
            case SOUTH: {
                return this.f_73383_.m_162395_() + p_73393_;
            }
            case WEST: {
                return this.f_73383_.m_162399_() - p_73394_;
            }
            case EAST: {
                return this.f_73383_.m_162395_() + p_73394_;
            }
        }
        return p_73393_;
    }

    protected int m_73544_(int p_73545_) {
        if (this.m_73549_() == null) {
            return p_73545_;
        }
        return p_73545_ + this.f_73383_.m_162396_();
    }

    protected int m_73525_(int p_73526_, int p_73527_) {
        Direction $$2 = this.m_73549_();
        if ($$2 == null) {
            return p_73527_;
        }
        switch ($$2) {
            case NORTH: {
                return this.f_73383_.m_162401_() - p_73527_;
            }
            case SOUTH: {
                return this.f_73383_.m_162398_() + p_73527_;
            }
            case WEST: 
            case EAST: {
                return this.f_73383_.m_162398_() + p_73526_;
            }
        }
        return p_73527_;
    }

    protected void m_73434_(WorldGenLevel p_73435_, BlockState p_73436_, int p_73437_, int p_73438_, int p_73439_, BoundingBox p_73440_) {
        BlockPos.MutableBlockPos $$6 = this.m_163582_(p_73437_, p_73438_, p_73439_);
        if (!p_73440_.m_71051_($$6)) {
            return;
        }
        if (!this.m_142085_(p_73435_, p_73437_, p_73438_, p_73439_, p_73440_)) {
            return;
        }
        if (this.f_73378_ != Mirror.NONE) {
            p_73436_ = p_73436_.m_60715_(this.f_73378_);
        }
        if (this.f_73379_ != Rotation.NONE) {
            p_73436_ = p_73436_.m_60717_(this.f_73379_);
        }
        p_73435_.m_7731_($$6, p_73436_, 2);
        FluidState $$7 = p_73435_.m_6425_($$6);
        if (!$$7.m_76178_()) {
            p_73435_.m_186469_($$6, $$7.m_76152_(), 0);
        }
        if (f_73381_.contains(p_73436_.m_60734_())) {
            p_73435_.m_46865_($$6).m_8113_($$6);
        }
    }

    protected boolean m_142085_(LevelReader p_163553_, int p_163554_, int p_163555_, int p_163556_, BoundingBox p_163557_) {
        return true;
    }

    protected BlockState m_73398_(BlockGetter p_73399_, int p_73400_, int p_73401_, int p_73402_, BoundingBox p_73403_) {
        BlockPos.MutableBlockPos $$5 = this.m_163582_(p_73400_, p_73401_, p_73402_);
        if (!p_73403_.m_71051_($$5)) {
            return Blocks.f_50016_.m_49966_();
        }
        return p_73399_.m_8055_($$5);
    }

    protected boolean m_73414_(LevelReader p_73415_, int p_73416_, int p_73417_, int p_73418_, BoundingBox p_73419_) {
        BlockPos.MutableBlockPos $$5 = this.m_163582_(p_73416_, p_73417_ + 1, p_73418_);
        if (!p_73419_.m_71051_($$5)) {
            return false;
        }
        return $$5.m_123342_() < p_73415_.m_6924_(Heightmap.Types.OCEAN_FLOOR_WG, $$5.m_123341_(), $$5.m_123343_());
    }

    protected void m_73535_(WorldGenLevel p_73536_, BoundingBox p_73537_, int p_73538_, int p_73539_, int p_73540_, int p_73541_, int p_73542_, int p_73543_) {
        for (int $$8 = p_73539_; $$8 <= p_73542_; ++$$8) {
            for (int $$9 = p_73538_; $$9 <= p_73541_; ++$$9) {
                for (int $$10 = p_73540_; $$10 <= p_73543_; ++$$10) {
                    this.m_73434_(p_73536_, Blocks.f_50016_.m_49966_(), $$9, $$8, $$10, p_73537_);
                }
            }
        }
    }

    protected void m_73441_(WorldGenLevel p_73442_, BoundingBox p_73443_, int p_73444_, int p_73445_, int p_73446_, int p_73447_, int p_73448_, int p_73449_, BlockState p_73450_, BlockState p_73451_, boolean p_73452_) {
        for (int $$11 = p_73445_; $$11 <= p_73448_; ++$$11) {
            for (int $$12 = p_73444_; $$12 <= p_73447_; ++$$12) {
                for (int $$13 = p_73446_; $$13 <= p_73449_; ++$$13) {
                    if (p_73452_ && this.m_73398_(p_73442_, $$12, $$11, $$13, p_73443_).m_60795_()) continue;
                    if ($$11 == p_73445_ || $$11 == p_73448_ || $$12 == p_73444_ || $$12 == p_73447_ || $$13 == p_73446_ || $$13 == p_73449_) {
                        this.m_73434_(p_73442_, p_73450_, $$12, $$11, $$13, p_73443_);
                        continue;
                    }
                    this.m_73434_(p_73442_, p_73451_, $$12, $$11, $$13, p_73443_);
                }
            }
        }
    }

    protected void m_163558_(WorldGenLevel p_163559_, BoundingBox p_163560_, BoundingBox p_163561_, BlockState p_163562_, BlockState p_163563_, boolean p_163564_) {
        this.m_73441_(p_163559_, p_163560_, p_163561_.m_162395_(), p_163561_.m_162396_(), p_163561_.m_162398_(), p_163561_.m_162399_(), p_163561_.m_162400_(), p_163561_.m_162401_(), p_163562_, p_163563_, p_163564_);
    }

    protected void m_226776_(WorldGenLevel p_226777_, BoundingBox p_226778_, int p_226779_, int p_226780_, int p_226781_, int p_226782_, int p_226783_, int p_226784_, boolean p_226785_, RandomSource p_226786_, BlockSelector p_226787_) {
        for (int $$11 = p_226780_; $$11 <= p_226783_; ++$$11) {
            for (int $$12 = p_226779_; $$12 <= p_226782_; ++$$12) {
                for (int $$13 = p_226781_; $$13 <= p_226784_; ++$$13) {
                    if (p_226785_ && this.m_73398_(p_226777_, $$12, $$11, $$13, p_226778_).m_60795_()) continue;
                    p_226787_.m_213766_(p_226786_, $$12, $$11, $$13, $$11 == p_226780_ || $$11 == p_226783_ || $$12 == p_226779_ || $$12 == p_226782_ || $$13 == p_226781_ || $$13 == p_226784_);
                    this.m_73434_(p_226777_, p_226787_.m_73555_(), $$12, $$11, $$13, p_226778_);
                }
            }
        }
    }

    protected void m_226828_(WorldGenLevel p_226829_, BoundingBox p_226830_, BoundingBox p_226831_, boolean p_226832_, RandomSource p_226833_, BlockSelector p_226834_) {
        this.m_226776_(p_226829_, p_226830_, p_226831_.m_162395_(), p_226831_.m_162396_(), p_226831_.m_162398_(), p_226831_.m_162399_(), p_226831_.m_162400_(), p_226831_.m_162401_(), p_226832_, p_226833_, p_226834_);
    }

    protected void m_226788_(WorldGenLevel p_226789_, BoundingBox p_226790_, RandomSource p_226791_, float p_226792_, int p_226793_, int p_226794_, int p_226795_, int p_226796_, int p_226797_, int p_226798_, BlockState p_226799_, BlockState p_226800_, boolean p_226801_, boolean p_226802_) {
        for (int $$14 = p_226794_; $$14 <= p_226797_; ++$$14) {
            for (int $$15 = p_226793_; $$15 <= p_226796_; ++$$15) {
                for (int $$16 = p_226795_; $$16 <= p_226798_; ++$$16) {
                    if (p_226791_.m_188501_() > p_226792_ || p_226801_ && this.m_73398_(p_226789_, $$15, $$14, $$16, p_226790_).m_60795_() || p_226802_ && !this.m_73414_(p_226789_, $$15, $$14, $$16, p_226790_)) continue;
                    if ($$14 == p_226794_ || $$14 == p_226797_ || $$15 == p_226793_ || $$15 == p_226796_ || $$16 == p_226795_ || $$16 == p_226798_) {
                        this.m_73434_(p_226789_, p_226799_, $$15, $$14, $$16, p_226790_);
                        continue;
                    }
                    this.m_73434_(p_226789_, p_226800_, $$15, $$14, $$16, p_226790_);
                }
            }
        }
    }

    protected void m_226803_(WorldGenLevel p_226804_, BoundingBox p_226805_, RandomSource p_226806_, float p_226807_, int p_226808_, int p_226809_, int p_226810_, BlockState p_226811_) {
        if (p_226806_.m_188501_() < p_226807_) {
            this.m_73434_(p_226804_, p_226811_, p_226808_, p_226809_, p_226810_, p_226805_);
        }
    }

    protected void m_73453_(WorldGenLevel p_73454_, BoundingBox p_73455_, int p_73456_, int p_73457_, int p_73458_, int p_73459_, int p_73460_, int p_73461_, BlockState p_73462_, boolean p_73463_) {
        float $$10 = p_73459_ - p_73456_ + 1;
        float $$11 = p_73460_ - p_73457_ + 1;
        float $$12 = p_73461_ - p_73458_ + 1;
        float $$13 = (float)p_73456_ + $$10 / 2.0f;
        float $$14 = (float)p_73458_ + $$12 / 2.0f;
        for (int $$15 = p_73457_; $$15 <= p_73460_; ++$$15) {
            float $$16 = (float)($$15 - p_73457_) / $$11;
            for (int $$17 = p_73456_; $$17 <= p_73459_; ++$$17) {
                float $$18 = ((float)$$17 - $$13) / ($$10 * 0.5f);
                for (int $$19 = p_73458_; $$19 <= p_73461_; ++$$19) {
                    float $$21;
                    float $$20 = ((float)$$19 - $$14) / ($$12 * 0.5f);
                    if (p_73463_ && this.m_73398_(p_73454_, $$17, $$15, $$19, p_73455_).m_60795_() || !(($$21 = $$18 * $$18 + $$16 * $$16 + $$20 * $$20) <= 1.05f)) continue;
                    this.m_73434_(p_73454_, p_73462_, $$17, $$15, $$19, p_73455_);
                }
            }
        }
    }

    protected void m_73528_(WorldGenLevel p_73529_, BlockState p_73530_, int p_73531_, int p_73532_, int p_73533_, BoundingBox p_73534_) {
        BlockPos.MutableBlockPos $$6 = this.m_163582_(p_73531_, p_73532_, p_73533_);
        if (!p_73534_.m_71051_($$6)) {
            return;
        }
        while (this.m_163572_(p_73529_.m_8055_($$6)) && $$6.m_123342_() > p_73529_.m_141937_() + 1) {
            p_73529_.m_7731_($$6, p_73530_, 2);
            $$6.m_122173_(Direction.DOWN);
        }
    }

    protected boolean m_163572_(BlockState p_163573_) {
        return p_163573_.m_60795_() || p_163573_.m_60767_().m_76332_() || p_163573_.m_60713_(Blocks.f_152475_) || p_163573_.m_60713_(Blocks.f_50037_) || p_163573_.m_60713_(Blocks.f_50038_);
    }

    protected boolean m_213787_(WorldGenLevel p_226812_, BoundingBox p_226813_, RandomSource p_226814_, int p_226815_, int p_226816_, int p_226817_, ResourceLocation p_226818_) {
        return this.m_226762_(p_226812_, p_226813_, p_226814_, this.m_163582_(p_226815_, p_226816_, p_226817_), p_226818_, null);
    }

    public static BlockState m_73407_(BlockGetter p_73408_, BlockPos p_73409_, BlockState p_73410_) {
        Direction $$3 = null;
        for (Direction $$4 : Direction.Plane.HORIZONTAL) {
            BlockPos $$5 = p_73409_.m_121945_($$4);
            BlockState $$6 = p_73408_.m_8055_($$5);
            if ($$6.m_60713_(Blocks.f_50087_)) {
                return p_73410_;
            }
            if (!$$6.m_60804_(p_73408_, $$5)) continue;
            if ($$3 == null) {
                $$3 = $$4;
                continue;
            }
            $$3 = null;
            break;
        }
        if ($$3 != null) {
            return (BlockState)p_73410_.m_61124_(HorizontalDirectionalBlock.f_54117_, $$3.m_122424_());
        }
        Direction $$7 = p_73410_.m_61143_(HorizontalDirectionalBlock.f_54117_);
        BlockPos $$8 = p_73409_.m_121945_($$7);
        if (p_73408_.m_8055_($$8).m_60804_(p_73408_, $$8)) {
            $$7 = $$7.m_122424_();
            $$8 = p_73409_.m_121945_($$7);
        }
        if (p_73408_.m_8055_($$8).m_60804_(p_73408_, $$8)) {
            $$7 = $$7.m_122427_();
            $$8 = p_73409_.m_121945_($$7);
        }
        if (p_73408_.m_8055_($$8).m_60804_(p_73408_, $$8)) {
            $$7 = $$7.m_122424_();
            $$8 = p_73409_.m_121945_($$7);
        }
        return (BlockState)p_73410_.m_61124_(HorizontalDirectionalBlock.f_54117_, $$7);
    }

    protected boolean m_226762_(ServerLevelAccessor p_226763_, BoundingBox p_226764_, RandomSource p_226765_, BlockPos p_226766_, ResourceLocation p_226767_, @Nullable BlockState p_226768_) {
        if (!p_226764_.m_71051_(p_226766_) || p_226763_.m_8055_(p_226766_).m_60713_(Blocks.f_50087_)) {
            return false;
        }
        if (p_226768_ == null) {
            p_226768_ = StructurePiece.m_73407_(p_226763_, p_226766_, Blocks.f_50087_.m_49966_());
        }
        p_226763_.m_7731_(p_226766_, p_226768_, 2);
        BlockEntity $$6 = p_226763_.m_7702_(p_226766_);
        if ($$6 instanceof ChestBlockEntity) {
            ((ChestBlockEntity)$$6).m_59626_(p_226767_, p_226765_.m_188505_());
        }
        return true;
    }

    protected boolean m_226819_(WorldGenLevel p_226820_, BoundingBox p_226821_, RandomSource p_226822_, int p_226823_, int p_226824_, int p_226825_, Direction p_226826_, ResourceLocation p_226827_) {
        BlockPos.MutableBlockPos $$8 = this.m_163582_(p_226823_, p_226824_, p_226825_);
        if (p_226821_.m_71051_($$8) && !p_226820_.m_8055_($$8).m_60713_(Blocks.f_50061_)) {
            this.m_73434_(p_226820_, (BlockState)Blocks.f_50061_.m_49966_().m_61124_(DispenserBlock.f_52659_, p_226826_), p_226823_, p_226824_, p_226825_, p_226821_);
            BlockEntity $$9 = p_226820_.m_7702_($$8);
            if ($$9 instanceof DispenserBlockEntity) {
                ((DispenserBlockEntity)$$9).m_59626_(p_226827_, p_226822_.m_188505_());
            }
            return true;
        }
        return false;
    }

    public void m_6324_(int p_73395_, int p_73396_, int p_73397_) {
        this.f_73383_.m_162367_(p_73395_, p_73396_, p_73397_);
    }

    public static BoundingBox m_192651_(Stream<StructurePiece> p_192652_) {
        return BoundingBox.m_162388_(p_192652_.map(StructurePiece::m_73547_)::iterator).orElseThrow(() -> new IllegalStateException("Unable to calculate boundingbox without pieces"));
    }

    @Nullable
    public static StructurePiece m_192648_(List<StructurePiece> p_192649_, BoundingBox p_192650_) {
        for (StructurePiece $$2 : p_192649_) {
            if (!$$2.m_73547_().m_71049_(p_192650_)) continue;
            return $$2;
        }
        return null;
    }

    @Nullable
    public Direction m_73549_() {
        return this.f_73377_;
    }

    public void m_73519_(@Nullable Direction p_73520_) {
        this.f_73377_ = p_73520_;
        if (p_73520_ == null) {
            this.f_73379_ = Rotation.NONE;
            this.f_73378_ = Mirror.NONE;
        } else {
            switch (p_73520_) {
                case SOUTH: {
                    this.f_73378_ = Mirror.LEFT_RIGHT;
                    this.f_73379_ = Rotation.NONE;
                    break;
                }
                case WEST: {
                    this.f_73378_ = Mirror.LEFT_RIGHT;
                    this.f_73379_ = Rotation.CLOCKWISE_90;
                    break;
                }
                case EAST: {
                    this.f_73378_ = Mirror.NONE;
                    this.f_73379_ = Rotation.CLOCKWISE_90;
                    break;
                }
                default: {
                    this.f_73378_ = Mirror.NONE;
                    this.f_73379_ = Rotation.NONE;
                }
            }
        }
    }

    public Rotation m_6830_() {
        return this.f_73379_;
    }

    public Mirror m_163587_() {
        return this.f_73378_;
    }

    public StructurePieceType m_210000_() {
        return this.f_73380_;
    }

    public static abstract class BlockSelector {
        protected BlockState f_73553_ = Blocks.f_50016_.m_49966_();

        public abstract void m_213766_(RandomSource var1, int var2, int var3, int var4, boolean var5);

        public BlockState m_73555_() {
            return this.f_73553_;
        }
    }
}

