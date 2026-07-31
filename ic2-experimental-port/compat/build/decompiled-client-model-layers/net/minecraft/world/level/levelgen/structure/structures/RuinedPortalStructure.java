/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class RuinedPortalStructure
extends Structure {
    private static final String[] f_229250_ = new String[]{"ruined_portal/portal_1", "ruined_portal/portal_2", "ruined_portal/portal_3", "ruined_portal/portal_4", "ruined_portal/portal_5", "ruined_portal/portal_6", "ruined_portal/portal_7", "ruined_portal/portal_8", "ruined_portal/portal_9", "ruined_portal/portal_10"};
    private static final String[] f_229251_ = new String[]{"ruined_portal/giant_portal_1", "ruined_portal/giant_portal_2", "ruined_portal/giant_portal_3"};
    private static final float f_229252_ = 0.05f;
    private static final int f_229253_ = 15;
    private final List<Setup> f_229254_;
    public static final Codec<RuinedPortalStructure> f_229249_ = RecordCodecBuilder.create(p_229304_ -> p_229304_.group(RuinedPortalStructure.m_226567_(p_229304_), (App)ExtraCodecs.m_144637_(Setup.f_229306_.listOf()).fieldOf("setups").forGetter(p_229299_ -> p_229299_.f_229254_)).apply((Applicative)p_229304_, RuinedPortalStructure::new));

    public RuinedPortalStructure(Structure.StructureSettings p_229260_, List<Setup> p_229261_) {
        super(p_229260_);
        this.f_229254_ = p_229261_;
    }

    public RuinedPortalStructure(Structure.StructureSettings p_229257_, Setup p_229258_) {
        this(p_229257_, List.of(p_229258_));
    }

    @Override
    public Optional<Structure.GenerationStub> m_214086_(Structure.GenerationContext p_229285_) {
        ResourceLocation $$10;
        RuinedPortalPiece.Properties $$1 = new RuinedPortalPiece.Properties();
        WorldgenRandom $$2 = p_229285_.f_226626_();
        Setup $$3 = null;
        if (this.f_229254_.size() > 1) {
            float $$4 = 0.0f;
            for (Setup setup : this.f_229254_) {
                $$4 += setup.f_229314_();
            }
            float $$6 = $$2.m_188501_();
            for (Setup $$7 : this.f_229254_) {
                if (!(($$6 -= $$7.f_229314_() / $$4) < 0.0f)) continue;
                $$3 = $$7;
                break;
            }
        } else {
            $$3 = this.f_229254_.get(0);
        }
        if ($$3 == null) {
            throw new IllegalStateException();
        }
        Setup $$8 = $$3;
        $$1.f_229200_ = RuinedPortalStructure.m_229281_($$2, $$8.f_229308_());
        $$1.f_229199_ = $$8.f_229309_();
        $$1.f_229201_ = $$8.f_229310_();
        $$1.f_229202_ = $$8.f_229311_();
        $$1.f_229203_ = $$8.f_229313_();
        if ($$2.m_188501_() < 0.05f) {
            ResourceLocation $$9 = new ResourceLocation(f_229251_[$$2.m_188503_(f_229251_.length)]);
        } else {
            $$10 = new ResourceLocation(f_229250_[$$2.m_188503_(f_229250_.length)]);
        }
        StructureTemplate structureTemplate = p_229285_.f_226625_().m_230359_($$10);
        Rotation $$12 = Util.m_214670_(Rotation.values(), $$2);
        Mirror $$13 = $$2.m_188501_() < 0.5f ? Mirror.NONE : Mirror.FRONT_BACK;
        BlockPos $$14 = new BlockPos(structureTemplate.m_163801_().m_123341_() / 2, 0, structureTemplate.m_163801_().m_123343_() / 2);
        ChunkGenerator $$15 = p_229285_.f_226622_();
        LevelHeightAccessor $$16 = p_229285_.f_226629_();
        RandomState $$17 = p_229285_.f_226624_();
        BlockPos $$18 = p_229285_.f_226628_().m_45615_();
        BoundingBox $$19 = structureTemplate.m_74598_($$18, $$12, $$14, $$13);
        BlockPos $$20 = $$19.m_162394_();
        int $$21 = $$15.m_214096_($$20.m_123341_(), $$20.m_123343_(), RuinedPortalPiece.m_229160_($$8.f_229307_()), $$16, $$17) - 1;
        int $$22 = RuinedPortalStructure.m_229266_($$2, $$15, $$8.f_229307_(), $$1.f_229200_, $$21, $$19.m_71057_(), $$19, $$16, $$17);
        BlockPos $$23 = new BlockPos($$18.m_123341_(), $$22, $$18.m_123343_());
        return Optional.of(new Structure.GenerationStub($$23, p_229297_ -> {
            if ($$8.f_229312_()) {
                p_229288_.f_229198_ = RuinedPortalStructure.m_229300_($$23, p_229285_.f_226622_().m_62218_().m_203407_(QuartPos.m_175400_($$23.m_123341_()), QuartPos.m_175400_($$23.m_123342_()), QuartPos.m_175400_($$23.m_123343_()), $$17.m_224579_()));
            }
            p_229297_.m_142679_(new RuinedPortalPiece(p_229285_.f_226625_(), $$23, $$8.f_229307_(), $$1, $$10, $$11, $$12, $$13, $$14));
        }));
    }

    private static boolean m_229281_(WorldgenRandom p_229282_, float p_229283_) {
        if (p_229283_ == 0.0f) {
            return false;
        }
        if (p_229283_ == 1.0f) {
            return true;
        }
        return p_229282_.m_188501_() < p_229283_;
    }

    private static boolean m_229300_(BlockPos p_229301_, Holder<Biome> p_229302_) {
        return p_229302_.m_203334_().m_198904_(p_229301_);
    }

    private static int m_229266_(RandomSource p_229267_, ChunkGenerator p_229268_, RuinedPortalPiece.VerticalPlacement p_229269_, boolean p_229270_, int p_229271_, int p_229272_, BoundingBox p_229273_, LevelHeightAccessor p_229274_, RandomState p_229275_) {
        int $$22;
        int $$9 = p_229274_.m_141937_() + 15;
        if (p_229269_ == RuinedPortalPiece.VerticalPlacement.IN_NETHER) {
            if (p_229270_) {
                int $$10 = Mth.m_216287_(p_229267_, 32, 100);
            } else if (p_229267_.m_188501_() < 0.5f) {
                int $$11 = Mth.m_216287_(p_229267_, 27, 29);
            } else {
                int $$12 = Mth.m_216287_(p_229267_, 29, 100);
            }
        } else if (p_229269_ == RuinedPortalPiece.VerticalPlacement.IN_MOUNTAIN) {
            int $$13 = p_229271_ - p_229272_;
            int $$14 = RuinedPortalStructure.m_229262_(p_229267_, 70, $$13);
        } else if (p_229269_ == RuinedPortalPiece.VerticalPlacement.UNDERGROUND) {
            int $$15 = p_229271_ - p_229272_;
            int $$16 = RuinedPortalStructure.m_229262_(p_229267_, $$9, $$15);
        } else if (p_229269_ == RuinedPortalPiece.VerticalPlacement.PARTLY_BURIED) {
            int $$17 = p_229271_ - p_229272_ + Mth.m_216287_(p_229267_, 2, 8);
        } else {
            int $$18 = p_229271_;
        }
        ImmutableList $$19 = ImmutableList.of((Object)new BlockPos(p_229273_.m_162395_(), 0, p_229273_.m_162398_()), (Object)new BlockPos(p_229273_.m_162399_(), 0, p_229273_.m_162398_()), (Object)new BlockPos(p_229273_.m_162395_(), 0, p_229273_.m_162401_()), (Object)new BlockPos(p_229273_.m_162399_(), 0, p_229273_.m_162401_()));
        List $$20 = $$19.stream().map(p_229280_ -> p_229268_.m_214184_(p_229280_.m_123341_(), p_229280_.m_123343_(), p_229274_, p_229275_)).collect(Collectors.toList());
        Heightmap.Types $$21 = p_229269_ == RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR ? Heightmap.Types.OCEAN_FLOOR_WG : Heightmap.Types.WORLD_SURFACE_WG;
        block0: for ($$22 = $$18; $$22 > $$9; --$$22) {
            int $$23 = 0;
            for (NoiseColumn $$24 : $$20) {
                BlockState $$25 = $$24.m_183556_($$22);
                if (!$$21.m_64299_().test($$25) || ++$$23 != 3) continue;
                break block0;
            }
        }
        return $$22;
    }

    private static int m_229262_(RandomSource p_229263_, int p_229264_, int p_229265_) {
        if (p_229264_ < p_229265_) {
            return Mth.m_216287_(p_229263_, p_229264_, p_229265_);
        }
        return p_229265_;
    }

    @Override
    public StructureType<?> m_213658_() {
        return StructureType.f_226873_;
    }

    public record Setup(RuinedPortalPiece.VerticalPlacement f_229307_, float f_229308_, float f_229309_, boolean f_229310_, boolean f_229311_, boolean f_229312_, boolean f_229313_, float f_229314_) {
        public static final Codec<Setup> f_229306_ = RecordCodecBuilder.create(p_229327_ -> p_229327_.group((App)RuinedPortalPiece.VerticalPlacement.f_229233_.fieldOf("placement").forGetter(Setup::f_229307_), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("air_pocket_probability").forGetter(Setup::f_229308_), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("mossiness").forGetter(Setup::f_229309_), (App)Codec.BOOL.fieldOf("overgrown").forGetter(Setup::f_229310_), (App)Codec.BOOL.fieldOf("vines").forGetter(Setup::f_229311_), (App)Codec.BOOL.fieldOf("can_be_cold").forGetter(Setup::f_229312_), (App)Codec.BOOL.fieldOf("replace_with_blackstone").forGetter(Setup::f_229313_), (App)ExtraCodecs.f_184349_.fieldOf("weight").forGetter(Setup::f_229314_)).apply((Applicative)p_229327_, Setup::new));

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Setup.class, "placement;airPocketProbability;mossiness;overgrown;vines;canBeCold;replaceWithBlackstone;weight", "f_229307_", "f_229308_", "f_229309_", "f_229310_", "f_229311_", "f_229312_", "f_229313_", "f_229314_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Setup.class, "placement;airPocketProbability;mossiness;overgrown;vines;canBeCold;replaceWithBlackstone;weight", "f_229307_", "f_229308_", "f_229309_", "f_229310_", "f_229311_", "f_229312_", "f_229313_", "f_229314_"}, this);
        }

        @Override
        public final boolean equals(Object p_229333_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Setup.class, "placement;airPocketProbability;mossiness;overgrown;vines;canBeCold;replaceWithBlackstone;weight", "f_229307_", "f_229308_", "f_229309_", "f_229310_", "f_229311_", "f_229312_", "f_229313_", "f_229314_"}, this, p_229333_);
        }
    }
}

