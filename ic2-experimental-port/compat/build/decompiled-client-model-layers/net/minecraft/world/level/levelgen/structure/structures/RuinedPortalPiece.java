/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlackstoneReplaceProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockAgeProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.LavaSubmergedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.slf4j.Logger;

public class RuinedPortalPiece
extends TemplateStructurePiece {
    private static final Logger f_229097_ = LogUtils.getLogger();
    private static final float f_229098_ = 0.3f;
    private static final float f_229099_ = 0.07f;
    private static final float f_229100_ = 0.2f;
    private final VerticalPlacement f_229101_;
    private final Properties f_229102_;

    public RuinedPortalPiece(StructureTemplateManager p_229105_, BlockPos p_229106_, VerticalPlacement p_229107_, Properties p_229108_, ResourceLocation p_229109_, StructureTemplate p_229110_, Rotation p_229111_, Mirror p_229112_, BlockPos p_229113_) {
        super(StructurePieceType.f_210104_, 0, p_229105_, p_229109_, p_229109_.toString(), RuinedPortalPiece.m_229151_(p_229112_, p_229111_, p_229107_, p_229113_, p_229108_), p_229106_);
        this.f_229101_ = p_229107_;
        this.f_229102_ = p_229108_;
    }

    public RuinedPortalPiece(StructureTemplateManager p_229115_, CompoundTag p_229116_) {
        super(StructurePieceType.f_210104_, p_229116_, p_229115_, p_229188_ -> RuinedPortalPiece.m_229165_(p_229115_, p_229116_, p_229188_));
        this.f_229101_ = VerticalPlacement.m_229242_(p_229116_.m_128461_("VerticalPlacement"));
        this.f_229102_ = (Properties)Properties.f_229197_.parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_229116_.m_128423_("Properties"))).getOrThrow(true, arg_0 -> ((Logger)f_229097_).error(arg_0));
    }

    @Override
    protected void m_183620_(StructurePieceSerializationContext p_229158_, CompoundTag p_229159_) {
        super.m_183620_(p_229158_, p_229159_);
        p_229159_.m_128359_("Rotation", this.f_73657_.m_74404_().name());
        p_229159_.m_128359_("Mirror", this.f_73657_.m_74401_().name());
        p_229159_.m_128359_("VerticalPlacement", this.f_229101_.m_229241_());
        Properties.f_229197_.encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.f_229102_).resultOrPartial(arg_0 -> ((Logger)f_229097_).error(arg_0)).ifPresent(p_229177_ -> p_229159_.m_128365_("Properties", (Tag)p_229177_));
    }

    private static StructurePlaceSettings m_229165_(StructureTemplateManager p_229166_, CompoundTag p_229167_, ResourceLocation p_229168_) {
        StructureTemplate $$3 = p_229166_.m_230359_(p_229168_);
        BlockPos $$4 = new BlockPos($$3.m_163801_().m_123341_() / 2, 0, $$3.m_163801_().m_123343_() / 2);
        return RuinedPortalPiece.m_229151_(Mirror.valueOf(p_229167_.m_128461_("Mirror")), Rotation.valueOf(p_229167_.m_128461_("Rotation")), VerticalPlacement.m_229242_(p_229167_.m_128461_("VerticalPlacement")), $$4, (Properties)Properties.f_229197_.parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_229167_.m_128423_("Properties"))).getOrThrow(true, arg_0 -> ((Logger)f_229097_).error(arg_0)));
    }

    private static StructurePlaceSettings m_229151_(Mirror p_229152_, Rotation p_229153_, VerticalPlacement p_229154_, BlockPos p_229155_, Properties p_229156_) {
        BlockIgnoreProcessor $$5 = p_229156_.f_229200_ ? BlockIgnoreProcessor.f_74046_ : BlockIgnoreProcessor.f_74048_;
        ArrayList $$6 = Lists.newArrayList();
        $$6.add(RuinedPortalPiece.m_229144_(Blocks.f_50074_, 0.3f, Blocks.f_50016_));
        $$6.add(RuinedPortalPiece.m_229162_(p_229154_, p_229156_));
        if (!p_229156_.f_229198_) {
            $$6.add(RuinedPortalPiece.m_229144_(Blocks.f_50134_, 0.07f, Blocks.f_50450_));
        }
        StructurePlaceSettings $$7 = new StructurePlaceSettings().m_74379_(p_229153_).m_74377_(p_229152_).m_74385_(p_229155_).m_74383_($$5).m_74383_(new RuleProcessor($$6)).m_74383_(new BlockAgeProcessor(p_229156_.f_229199_)).m_74383_(new ProtectedBlockProcessor(BlockTags.f_144287_)).m_74383_(new LavaSubmergedBlockProcessor());
        if (p_229156_.f_229203_) {
            $$7.m_74383_(BlackstoneReplaceProcessor.f_73994_);
        }
        return $$7;
    }

    private static ProcessorRule m_229162_(VerticalPlacement p_229163_, Properties p_229164_) {
        if (p_229163_ == VerticalPlacement.ON_OCEAN_FLOOR) {
            return RuinedPortalPiece.m_229148_(Blocks.f_49991_, Blocks.f_50450_);
        }
        if (p_229164_.f_229198_) {
            return RuinedPortalPiece.m_229148_(Blocks.f_49991_, Blocks.f_50134_);
        }
        return RuinedPortalPiece.m_229144_(Blocks.f_49991_, 0.2f, Blocks.f_50450_);
    }

    @Override
    public void m_213694_(WorldGenLevel p_229137_, StructureManager p_229138_, ChunkGenerator p_229139_, RandomSource p_229140_, BoundingBox p_229141_, ChunkPos p_229142_, BlockPos p_229143_) {
        BoundingBox $$7 = this.f_73656_.m_74633_(this.f_73657_, this.f_73658_);
        if (!p_229141_.m_71051_($$7.m_162394_())) {
            return;
        }
        p_229141_.m_162386_($$7);
        super.m_213694_(p_229137_, p_229138_, p_229139_, p_229140_, p_229141_, p_229142_, p_229143_);
        this.m_229178_(p_229140_, p_229137_);
        this.m_229117_(p_229140_, p_229137_);
        if (this.f_229102_.f_229202_ || this.f_229102_.f_229201_) {
            BlockPos.m_121919_(this.m_73547_()).forEach(p_229127_ -> {
                if (this.f_229102_.f_229202_) {
                    this.m_229120_(p_229140_, p_229137_, (BlockPos)p_229127_);
                }
                if (this.f_229102_.f_229201_) {
                    this.m_229181_(p_229140_, p_229137_, (BlockPos)p_229127_);
                }
            });
        }
    }

    @Override
    protected void m_213704_(String p_229170_, BlockPos p_229171_, ServerLevelAccessor p_229172_, RandomSource p_229173_, BoundingBox p_229174_) {
    }

    private void m_229120_(RandomSource p_229121_, LevelAccessor p_229122_, BlockPos p_229123_) {
        BlockState $$3 = p_229122_.m_8055_(p_229123_);
        if ($$3.m_60795_() || $$3.m_60713_(Blocks.f_50191_)) {
            return;
        }
        Direction $$4 = RuinedPortalPiece.m_226760_(p_229121_);
        BlockPos $$5 = p_229123_.m_121945_($$4);
        BlockState $$6 = p_229122_.m_8055_($$5);
        if (!$$6.m_60795_()) {
            return;
        }
        if (!Block.m_49918_($$3.m_60812_(p_229122_, p_229123_), $$4)) {
            return;
        }
        BooleanProperty $$7 = VineBlock.m_57883_($$4.m_122424_());
        p_229122_.m_7731_($$5, (BlockState)Blocks.f_50191_.m_49966_().m_61124_($$7, true), 3);
    }

    private void m_229181_(RandomSource p_229182_, LevelAccessor p_229183_, BlockPos p_229184_) {
        if (p_229182_.m_188501_() < 0.5f && p_229183_.m_8055_(p_229184_).m_60713_(Blocks.f_50134_) && p_229183_.m_8055_(p_229184_.m_7494_()).m_60795_()) {
            p_229183_.m_7731_(p_229184_.m_7494_(), (BlockState)Blocks.f_50053_.m_49966_().m_61124_(LeavesBlock.f_54419_, true), 3);
        }
    }

    private void m_229117_(RandomSource p_229118_, LevelAccessor p_229119_) {
        for (int $$2 = this.f_73383_.m_162395_() + 1; $$2 < this.f_73383_.m_162399_(); ++$$2) {
            for (int $$3 = this.f_73383_.m_162398_() + 1; $$3 < this.f_73383_.m_162401_(); ++$$3) {
                BlockPos $$4 = new BlockPos($$2, this.f_73383_.m_162396_(), $$3);
                if (!p_229119_.m_8055_($$4).m_60713_(Blocks.f_50134_)) continue;
                this.m_229189_(p_229118_, p_229119_, $$4.m_7495_());
            }
        }
    }

    private void m_229189_(RandomSource p_229190_, LevelAccessor p_229191_, BlockPos p_229192_) {
        BlockPos.MutableBlockPos $$3 = p_229192_.m_122032_();
        this.m_229193_(p_229190_, p_229191_, $$3);
        for (int $$4 = 8; $$4 > 0 && p_229190_.m_188501_() < 0.5f; --$$4) {
            $$3.m_122173_(Direction.DOWN);
            this.m_229193_(p_229190_, p_229191_, $$3);
        }
    }

    private void m_229178_(RandomSource p_229179_, LevelAccessor p_229180_) {
        boolean $$2 = this.f_229101_ == VerticalPlacement.ON_LAND_SURFACE || this.f_229101_ == VerticalPlacement.ON_OCEAN_FLOOR;
        BlockPos $$3 = this.f_73383_.m_162394_();
        int $$4 = $$3.m_123341_();
        int $$5 = $$3.m_123343_();
        float[] $$6 = new float[]{1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.9f, 0.9f, 0.8f, 0.7f, 0.6f, 0.4f, 0.2f};
        int $$7 = $$6.length;
        int $$8 = (this.f_73383_.m_71056_() + this.f_73383_.m_71058_()) / 2;
        int $$9 = p_229179_.m_188503_(Math.max(1, 8 - $$8 / 2));
        int $$10 = 3;
        BlockPos.MutableBlockPos $$11 = BlockPos.f_121853_.m_122032_();
        for (int $$12 = $$4 - $$7; $$12 <= $$4 + $$7; ++$$12) {
            for (int $$13 = $$5 - $$7; $$13 <= $$5 + $$7; ++$$13) {
                int $$14 = Math.abs($$12 - $$4) + Math.abs($$13 - $$5);
                int $$15 = Math.max(0, $$14 + $$9);
                if ($$15 >= $$7) continue;
                float $$16 = $$6[$$15];
                if (!(p_229179_.m_188500_() < (double)$$16)) continue;
                int $$17 = RuinedPortalPiece.m_229128_(p_229180_, $$12, $$13, this.f_229101_);
                int $$18 = $$2 ? $$17 : Math.min(this.f_73383_.m_162396_(), $$17);
                $$11.m_122178_($$12, $$18, $$13);
                if (Math.abs($$18 - this.f_73383_.m_162396_()) > 3 || !this.m_229133_(p_229180_, $$11)) continue;
                this.m_229193_(p_229179_, p_229180_, $$11);
                if (this.f_229102_.f_229201_) {
                    this.m_229181_(p_229179_, p_229180_, $$11);
                }
                this.m_229189_(p_229179_, p_229180_, (BlockPos)$$11.m_7495_());
            }
        }
    }

    private boolean m_229133_(LevelAccessor p_229134_, BlockPos p_229135_) {
        BlockState $$2 = p_229134_.m_8055_(p_229135_);
        return !$$2.m_60713_(Blocks.f_50016_) && !$$2.m_60713_(Blocks.f_50080_) && !$$2.m_204336_(BlockTags.f_144287_) && (this.f_229101_ == VerticalPlacement.IN_NETHER || !$$2.m_60713_(Blocks.f_49991_));
    }

    private void m_229193_(RandomSource p_229194_, LevelAccessor p_229195_, BlockPos p_229196_) {
        if (!this.f_229102_.f_229198_ && p_229194_.m_188501_() < 0.07f) {
            p_229195_.m_7731_(p_229196_, Blocks.f_50450_.m_49966_(), 3);
        } else {
            p_229195_.m_7731_(p_229196_, Blocks.f_50134_.m_49966_(), 3);
        }
    }

    private static int m_229128_(LevelAccessor p_229129_, int p_229130_, int p_229131_, VerticalPlacement p_229132_) {
        return p_229129_.m_6924_(RuinedPortalPiece.m_229160_(p_229132_), p_229130_, p_229131_) - 1;
    }

    public static Heightmap.Types m_229160_(VerticalPlacement p_229161_) {
        return p_229161_ == VerticalPlacement.ON_OCEAN_FLOOR ? Heightmap.Types.OCEAN_FLOOR_WG : Heightmap.Types.WORLD_SURFACE_WG;
    }

    private static ProcessorRule m_229144_(Block p_229145_, float p_229146_, Block p_229147_) {
        return new ProcessorRule(new RandomBlockMatchTest(p_229145_, p_229146_), AlwaysTrueTest.f_73954_, p_229147_.m_49966_());
    }

    private static ProcessorRule m_229148_(Block p_229149_, Block p_229150_) {
        return new ProcessorRule(new BlockMatchTest(p_229149_), AlwaysTrueTest.f_73954_, p_229150_.m_49966_());
    }

    public static final class VerticalPlacement
    extends Enum<VerticalPlacement>
    implements StringRepresentable {
        public static final /* enum */ VerticalPlacement ON_LAND_SURFACE = new VerticalPlacement("on_land_surface");
        public static final /* enum */ VerticalPlacement PARTLY_BURIED = new VerticalPlacement("partly_buried");
        public static final /* enum */ VerticalPlacement ON_OCEAN_FLOOR = new VerticalPlacement("on_ocean_floor");
        public static final /* enum */ VerticalPlacement IN_MOUNTAIN = new VerticalPlacement("in_mountain");
        public static final /* enum */ VerticalPlacement UNDERGROUND = new VerticalPlacement("underground");
        public static final /* enum */ VerticalPlacement IN_NETHER = new VerticalPlacement("in_nether");
        public static final StringRepresentable.EnumCodec<VerticalPlacement> f_229233_;
        private final String f_229234_;
        private static final /* synthetic */ VerticalPlacement[] $VALUES;

        public static VerticalPlacement[] values() {
            return (VerticalPlacement[])$VALUES.clone();
        }

        public static VerticalPlacement valueOf(String p_229247_) {
            return Enum.valueOf(VerticalPlacement.class, p_229247_);
        }

        private VerticalPlacement(String p_229240_) {
            this.f_229234_ = p_229240_;
        }

        public String m_229241_() {
            return this.f_229234_;
        }

        public static VerticalPlacement m_229242_(String p_229243_) {
            return f_229233_.m_216455_(p_229243_);
        }

        @Override
        public String m_7912_() {
            return this.f_229234_;
        }

        private static /* synthetic */ VerticalPlacement[] m_229244_() {
            return new VerticalPlacement[]{ON_LAND_SURFACE, PARTLY_BURIED, ON_OCEAN_FLOOR, IN_MOUNTAIN, UNDERGROUND, IN_NETHER};
        }

        static {
            $VALUES = VerticalPlacement.m_229244_();
            f_229233_ = StringRepresentable.m_216439_(VerticalPlacement::values);
        }
    }

    public static class Properties {
        public static final Codec<Properties> f_229197_ = RecordCodecBuilder.create(p_229214_ -> p_229214_.group((App)Codec.BOOL.fieldOf("cold").forGetter(p_229226_ -> p_229226_.f_229198_), (App)Codec.FLOAT.fieldOf("mossiness").forGetter(p_229224_ -> Float.valueOf(p_229224_.f_229199_)), (App)Codec.BOOL.fieldOf("air_pocket").forGetter(p_229222_ -> p_229222_.f_229200_), (App)Codec.BOOL.fieldOf("overgrown").forGetter(p_229220_ -> p_229220_.f_229201_), (App)Codec.BOOL.fieldOf("vines").forGetter(p_229218_ -> p_229218_.f_229202_), (App)Codec.BOOL.fieldOf("replace_with_blackstone").forGetter(p_229216_ -> p_229216_.f_229203_)).apply((Applicative)p_229214_, Properties::new));
        public boolean f_229198_;
        public float f_229199_;
        public boolean f_229200_;
        public boolean f_229201_;
        public boolean f_229202_;
        public boolean f_229203_;

        public Properties() {
        }

        public Properties(boolean p_229207_, float p_229208_, boolean p_229209_, boolean p_229210_, boolean p_229211_, boolean p_229212_) {
            this.f_229198_ = p_229207_;
            this.f_229199_ = p_229208_;
            this.f_229200_ = p_229209_;
            this.f_229201_ = p_229210_;
            this.f_229202_ = p_229211_;
            this.f_229203_ = p_229212_;
        }
    }
}

