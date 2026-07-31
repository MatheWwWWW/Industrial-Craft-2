/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction8;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.ticks.SavedTick;
import org.slf4j.Logger;

public class UpgradeData {
    private static final Logger f_63321_ = LogUtils.getLogger();
    public static final UpgradeData f_63320_ = new UpgradeData(EmptyBlockGetter.INSTANCE);
    private static final String f_156504_ = "Indices";
    private static final Direction8[] f_63322_ = Direction8.values();
    private final EnumSet<Direction8> f_63323_ = EnumSet.noneOf(Direction8.class);
    private final List<SavedTick<Block>> f_208118_ = Lists.newArrayList();
    private final List<SavedTick<Fluid>> f_208119_ = Lists.newArrayList();
    private final int[][] f_63324_;
    static final Map<Block, BlockFixer> f_63325_ = new IdentityHashMap<Block, BlockFixer>();
    static final Set<BlockFixer> f_63326_ = Sets.newHashSet();

    private UpgradeData(LevelHeightAccessor p_156506_) {
        this.f_63324_ = new int[p_156506_.m_151559_()][];
    }

    public UpgradeData(CompoundTag p_156508_, LevelHeightAccessor p_156509_) {
        this(p_156509_);
        if (p_156508_.m_128425_(f_156504_, 10)) {
            CompoundTag $$2 = p_156508_.m_128469_(f_156504_);
            for (int $$3 = 0; $$3 < this.f_63324_.length; ++$$3) {
                String $$4 = String.valueOf($$3);
                if (!$$2.m_128425_($$4, 11)) continue;
                this.f_63324_[$$3] = $$2.m_128465_($$4);
            }
        }
        int $$5 = p_156508_.m_128451_("Sides");
        for (Direction8 $$6 : Direction8.values()) {
            if (($$5 & 1 << $$6.ordinal()) == 0) continue;
            this.f_63323_.add($$6);
        }
        UpgradeData.m_208132_(p_156508_, "neighbor_block_ticks", p_208144_ -> Registry.f_122824_.m_6612_(ResourceLocation.m_135820_(p_208144_)).or(() -> Optional.of(Blocks.f_50016_)), this.f_208118_);
        UpgradeData.m_208132_(p_156508_, "neighbor_fluid_ticks", p_208131_ -> Registry.f_122822_.m_6612_(ResourceLocation.m_135820_(p_208131_)).or(() -> Optional.of(Fluids.f_76191_)), this.f_208119_);
    }

    private static <T> void m_208132_(CompoundTag p_208133_, String p_208134_, Function<String, Optional<T>> p_208135_, List<SavedTick<T>> p_208136_) {
        if (p_208133_.m_128425_(p_208134_, 9)) {
            ListTag $$4 = p_208133_.m_128437_(p_208134_, 10);
            for (Tag $$5 : $$4) {
                SavedTick.m_210669_((CompoundTag)$$5, p_208135_).ifPresent(p_208136_::add);
            }
        }
    }

    public void m_63341_(LevelChunk p_63342_) {
        this.m_63347_(p_63342_);
        for (Direction8 $$1 : f_63322_) {
            UpgradeData.m_63343_(p_63342_, $$1);
        }
        Level $$2 = p_63342_.m_62953_();
        this.f_208118_.forEach(p_208142_ -> {
            Block $$2 = p_208142_.f_193311_() == Blocks.f_50016_ ? $$2.m_8055_(p_208142_.f_193312_()).m_60734_() : (Block)p_208142_.f_193311_();
            $$2.m_186464_(p_208142_.f_193312_(), $$2, p_208142_.f_193313_(), p_208142_.f_193314_());
        });
        this.f_208119_.forEach(p_208125_ -> {
            Fluid $$2 = p_208125_.f_193311_() == Fluids.f_76191_ ? $$2.m_6425_(p_208125_.f_193312_()).m_76152_() : (Fluid)p_208125_.f_193311_();
            $$2.m_186473_(p_208125_.f_193312_(), $$2, p_208125_.f_193313_(), p_208125_.f_193314_());
        });
        f_63326_.forEach(p_208122_ -> p_208122_.m_5870_($$2));
    }

    private static void m_63343_(LevelChunk p_63344_, Direction8 p_63345_) {
        Level $$2 = p_63344_.m_62953_();
        if (!p_63344_.m_7387_().f_63323_.remove((Object)p_63345_)) {
            return;
        }
        Set<Direction> $$3 = p_63345_.m_122593_();
        boolean $$4 = false;
        int $$5 = 15;
        boolean $$6 = $$3.contains(Direction.EAST);
        boolean $$7 = $$3.contains(Direction.WEST);
        boolean $$8 = $$3.contains(Direction.SOUTH);
        boolean $$9 = $$3.contains(Direction.NORTH);
        boolean $$10 = $$3.size() == 1;
        ChunkPos $$11 = p_63344_.m_7697_();
        int $$12 = $$11.m_45604_() + ($$10 && ($$9 || $$8) ? 1 : ($$7 ? 0 : 15));
        int $$13 = $$11.m_45604_() + ($$10 && ($$9 || $$8) ? 14 : ($$7 ? 0 : 15));
        int $$14 = $$11.m_45605_() + ($$10 && ($$6 || $$7) ? 1 : ($$9 ? 0 : 15));
        int $$15 = $$11.m_45605_() + ($$10 && ($$6 || $$7) ? 14 : ($$9 ? 0 : 15));
        Direction[] $$16 = Direction.values();
        BlockPos.MutableBlockPos $$17 = new BlockPos.MutableBlockPos();
        for (BlockPos $$18 : BlockPos.m_121976_($$12, $$2.m_141937_(), $$14, $$13, $$2.m_151558_() - 1, $$15)) {
            BlockState $$19;
            BlockState $$20 = $$19 = $$2.m_8055_($$18);
            for (Direction $$21 : $$16) {
                $$17.m_122159_($$18, $$21);
                $$20 = UpgradeData.m_63335_($$20, $$21, $$2, $$18, $$17);
            }
            Block.m_49902_($$19, $$20, $$2, $$18, 18);
        }
    }

    private static BlockState m_63335_(BlockState p_63336_, Direction p_63337_, LevelAccessor p_63338_, BlockPos p_63339_, BlockPos p_63340_) {
        return f_63325_.getOrDefault(p_63336_.m_60734_(), BlockFixers.DEFAULT).m_5731_(p_63336_, p_63337_, p_63338_.m_8055_(p_63340_), p_63338_, p_63339_, p_63340_);
    }

    private void m_63347_(LevelChunk p_63348_) {
        BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos $$2 = new BlockPos.MutableBlockPos();
        ChunkPos $$3 = p_63348_.m_7697_();
        Level $$4 = p_63348_.m_62953_();
        for (int $$5 = 0; $$5 < this.f_63324_.length; ++$$5) {
            LevelChunkSection $$6 = p_63348_.m_183278_($$5);
            int[] $$7 = this.f_63324_[$$5];
            this.f_63324_[$$5] = null;
            if ($$7 == null || $$7.length <= 0) continue;
            Direction[] $$8 = Direction.values();
            PalettedContainer<BlockState> $$9 = $$6.m_63019_();
            for (int $$10 : $$7) {
                BlockState $$14;
                int $$11 = $$10 & 0xF;
                int $$12 = $$10 >> 8 & 0xF;
                int $$13 = $$10 >> 4 & 0xF;
                $$1.m_122178_($$3.m_45604_() + $$11, $$6.m_63017_() + $$12, $$3.m_45605_() + $$13);
                BlockState $$15 = $$14 = $$9.m_63085_($$10);
                for (Direction $$16 : $$8) {
                    $$2.m_122159_($$1, $$16);
                    if (SectionPos.m_123171_($$1.m_123341_()) != $$3.f_45578_ || SectionPos.m_123171_($$1.m_123343_()) != $$3.f_45579_) continue;
                    $$15 = UpgradeData.m_63335_($$15, $$16, $$4, $$1, $$2);
                }
                Block.m_49902_($$14, $$15, $$4, $$1, 18);
            }
        }
        for (int $$17 = 0; $$17 < this.f_63324_.length; ++$$17) {
            if (this.f_63324_[$$17] != null) {
                f_63321_.warn("Discarding update data for section {} for chunk ({} {})", new Object[]{$$4.m_151568_($$17), $$3.f_45578_, $$3.f_45579_});
            }
            this.f_63324_[$$17] = null;
        }
    }

    public boolean m_63331_() {
        for (int[] $$0 : this.f_63324_) {
            if ($$0 == null) continue;
            return false;
        }
        return this.f_63323_.isEmpty();
    }

    public CompoundTag m_63346_() {
        CompoundTag $$0 = new CompoundTag();
        CompoundTag $$1 = new CompoundTag();
        for (int $$2 = 0; $$2 < this.f_63324_.length; ++$$2) {
            String $$3 = String.valueOf($$2);
            if (this.f_63324_[$$2] == null || this.f_63324_[$$2].length == 0) continue;
            $$1.m_128385_($$3, this.f_63324_[$$2]);
        }
        if (!$$1.m_128456_()) {
            $$0.m_128365_(f_156504_, $$1);
        }
        int $$4 = 0;
        for (Direction8 $$5 : this.f_63323_) {
            $$4 |= 1 << $$5.ordinal();
        }
        $$0.m_128344_("Sides", (byte)$$4);
        if (!this.f_208118_.isEmpty()) {
            ListTag $$6 = new ListTag();
            this.f_208118_.forEach(p_208147_ -> $$6.add(p_208147_.m_193343_(p_208127_ -> Registry.f_122824_.m_7981_((Block)p_208127_).toString())));
            $$0.m_128365_("neighbor_block_ticks", $$6);
        }
        if (!this.f_208119_.isEmpty()) {
            ListTag $$7 = new ListTag();
            this.f_208119_.forEach(p_208139_ -> $$7.add(p_208139_.m_193343_(p_208129_ -> Registry.f_122822_.m_7981_((Fluid)p_208129_).toString())));
            $$0.m_128365_("neighbor_fluid_ticks", $$7);
        }
        return $$0;
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    static abstract class BlockFixers
    extends Enum<BlockFixers>
    implements BlockFixer {
        public static final /* enum */ BlockFixers BLACKLIST = new BlockFixers(new Block[]{Blocks.f_50455_, Blocks.f_50142_, Blocks.f_50506_, Blocks.f_50507_, Blocks.f_50508_, Blocks.f_50509_, Blocks.f_50510_, Blocks.f_50511_, Blocks.f_50512_, Blocks.f_50513_, Blocks.f_50514_, Blocks.f_50515_, Blocks.f_50516_, Blocks.f_50517_, Blocks.f_50518_, Blocks.f_50519_, Blocks.f_50573_, Blocks.f_50574_, Blocks.f_50322_, Blocks.f_50323_, Blocks.f_50324_, Blocks.f_50260_, Blocks.f_49994_, Blocks.f_49992_, Blocks.f_49993_, Blocks.f_50095_, Blocks.f_50149_, Blocks.f_50150_, Blocks.f_50151_, Blocks.f_50152_, Blocks.f_50153_, Blocks.f_50158_, Blocks.f_50159_, Blocks.f_50160_, Blocks.f_50161_, Blocks.f_50162_, Blocks.f_50163_}){

            @Override
            public BlockState m_5731_(BlockState p_63394_, Direction p_63395_, BlockState p_63396_, LevelAccessor p_63397_, BlockPos p_63398_, BlockPos p_63399_) {
                return p_63394_;
            }
        };
        public static final /* enum */ BlockFixers DEFAULT = new BlockFixers(new Block[0]){

            @Override
            public BlockState m_5731_(BlockState p_63405_, Direction p_63406_, BlockState p_63407_, LevelAccessor p_63408_, BlockPos p_63409_, BlockPos p_63410_) {
                return p_63405_.m_60728_(p_63406_, p_63408_.m_8055_(p_63410_), p_63408_, p_63409_, p_63410_);
            }
        };
        public static final /* enum */ BlockFixers CHEST = new BlockFixers(new Block[]{Blocks.f_50087_, Blocks.f_50325_}){

            @Override
            public BlockState m_5731_(BlockState p_63416_, Direction p_63417_, BlockState p_63418_, LevelAccessor p_63419_, BlockPos p_63420_, BlockPos p_63421_) {
                if (p_63418_.m_60713_(p_63416_.m_60734_()) && p_63417_.m_122434_().m_122479_() && p_63416_.m_61143_(ChestBlock.f_51479_) == ChestType.SINGLE && p_63418_.m_61143_(ChestBlock.f_51479_) == ChestType.SINGLE) {
                    Direction $$6 = p_63416_.m_61143_(ChestBlock.f_51478_);
                    if (p_63417_.m_122434_() != $$6.m_122434_() && $$6 == p_63418_.m_61143_(ChestBlock.f_51478_)) {
                        ChestType $$7 = p_63417_ == $$6.m_122427_() ? ChestType.LEFT : ChestType.RIGHT;
                        p_63419_.m_7731_(p_63421_, (BlockState)p_63418_.m_61124_(ChestBlock.f_51479_, $$7.m_61486_()), 18);
                        if ($$6 == Direction.NORTH || $$6 == Direction.EAST) {
                            BlockEntity $$8 = p_63419_.m_7702_(p_63420_);
                            BlockEntity $$9 = p_63419_.m_7702_(p_63421_);
                            if ($$8 instanceof ChestBlockEntity && $$9 instanceof ChestBlockEntity) {
                                ChestBlockEntity.m_59103_((ChestBlockEntity)$$8, (ChestBlockEntity)$$9);
                            }
                        }
                        return (BlockState)p_63416_.m_61124_(ChestBlock.f_51479_, $$7);
                    }
                }
                return p_63416_;
            }
        };
        public static final /* enum */ BlockFixers LEAVES = new BlockFixers(true, new Block[]{Blocks.f_50054_, Blocks.f_50052_, Blocks.f_50055_, Blocks.f_50053_, Blocks.f_50050_, Blocks.f_50051_}){
            private final ThreadLocal<List<ObjectSet<BlockPos>>> f_63422_ = ThreadLocal.withInitial(() -> Lists.newArrayListWithCapacity((int)7));

            @Override
            public BlockState m_5731_(BlockState p_63432_, Direction p_63433_, BlockState p_63434_, LevelAccessor p_63435_, BlockPos p_63436_, BlockPos p_63437_) {
                BlockState $$6 = p_63432_.m_60728_(p_63433_, p_63435_.m_8055_(p_63437_), p_63435_, p_63436_, p_63437_);
                if (p_63432_ != $$6) {
                    int $$7 = $$6.m_61143_(BlockStateProperties.f_61414_);
                    List<ObjectSet<BlockPos>> $$8 = this.f_63422_.get();
                    if ($$8.isEmpty()) {
                        for (int $$9 = 0; $$9 < 7; ++$$9) {
                            $$8.add((ObjectSet<BlockPos>)new ObjectOpenHashSet());
                        }
                    }
                    $$8.get($$7).add((Object)p_63436_.m_7949_());
                }
                return p_63432_;
            }

            @Override
            public void m_5870_(LevelAccessor p_63430_) {
                BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
                List<ObjectSet<BlockPos>> $$2 = this.f_63422_.get();
                for (int $$3 = 2; $$3 < $$2.size(); ++$$3) {
                    int $$4 = $$3 - 1;
                    ObjectSet<BlockPos> $$5 = $$2.get($$4);
                    ObjectSet<BlockPos> $$6 = $$2.get($$3);
                    for (BlockPos $$7 : $$5) {
                        BlockState $$8 = p_63430_.m_8055_($$7);
                        if ($$8.m_61143_(BlockStateProperties.f_61414_) < $$4) continue;
                        p_63430_.m_7731_($$7, (BlockState)$$8.m_61124_(BlockStateProperties.f_61414_, $$4), 18);
                        if ($$3 == 7) continue;
                        for (Direction $$9 : f_63363_) {
                            $$1.m_122159_($$7, $$9);
                            BlockState $$10 = p_63430_.m_8055_($$1);
                            if (!$$10.m_61138_(BlockStateProperties.f_61414_) || $$8.m_61143_(BlockStateProperties.f_61414_) <= $$3) continue;
                            $$6.add((Object)$$1.m_7949_());
                        }
                    }
                }
                $$2.clear();
            }
        };
        public static final /* enum */ BlockFixers STEM_BLOCK = new BlockFixers(new Block[]{Blocks.f_50190_, Blocks.f_50189_}){

            @Override
            public BlockState m_5731_(BlockState p_63443_, Direction p_63444_, BlockState p_63445_, LevelAccessor p_63446_, BlockPos p_63447_, BlockPos p_63448_) {
                StemGrownBlock $$6;
                if (p_63443_.m_61143_(StemBlock.f_57013_) == 7 && p_63445_.m_60713_($$6 = ((StemBlock)p_63443_.m_60734_()).m_57056_())) {
                    return (BlockState)$$6.m_7810_().m_49966_().m_61124_(HorizontalDirectionalBlock.f_54117_, p_63444_);
                }
                return p_63443_;
            }
        };
        public static final Direction[] f_63363_;
        private static final /* synthetic */ BlockFixers[] $VALUES;

        public static BlockFixers[] values() {
            return (BlockFixers[])$VALUES.clone();
        }

        public static BlockFixers valueOf(String p_63387_) {
            return Enum.valueOf(BlockFixers.class, p_63387_);
        }

        BlockFixers(Block ... p_63380_) {
            this(false, p_63380_);
        }

        BlockFixers(boolean p_63369_, Block ... p_63370_) {
            for (Block $$2 : p_63370_) {
                f_63325_.put($$2, this);
            }
            if (p_63369_) {
                f_63326_.add(this);
            }
        }

        private static /* synthetic */ BlockFixers[] m_156510_() {
            return new BlockFixers[]{BLACKLIST, DEFAULT, CHEST, LEAVES, STEM_BLOCK};
        }

        static {
            $VALUES = BlockFixers.m_156510_();
            f_63363_ = Direction.values();
        }
    }

    public static interface BlockFixer {
        public BlockState m_5731_(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6);

        default public void m_5870_(LevelAccessor p_63351_) {
        }
    }
}

