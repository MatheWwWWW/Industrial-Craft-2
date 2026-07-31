/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectListIterator
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.util.BitStorage;
import net.minecraft.util.Mth;
import net.minecraft.util.SimpleBitStorage;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.slf4j.Logger;

public class Heightmap {
    private static final Logger f_158363_ = LogUtils.getLogger();
    static final Predicate<BlockState> f_64230_ = p_64263_ -> !p_64263_.m_60795_();
    static final Predicate<BlockState> f_64231_ = p_64255_ -> p_64255_.m_60767_().m_76334_();
    private final BitStorage f_64232_;
    private final Predicate<BlockState> f_64233_;
    private final ChunkAccess f_64234_;

    public Heightmap(ChunkAccess p_64237_, Types p_64238_) {
        this.f_64233_ = p_64238_.m_64299_();
        this.f_64234_ = p_64237_;
        int $$2 = Mth.m_14163_(p_64237_.m_141928_() + 1);
        this.f_64232_ = new SimpleBitStorage($$2, 256);
    }

    public static void m_64256_(ChunkAccess p_64257_, Set<Types> p_64258_) {
        int $$2 = p_64258_.size();
        ObjectArrayList $$3 = new ObjectArrayList($$2);
        ObjectListIterator $$4 = $$3.iterator();
        int $$5 = p_64257_.m_62098_() + 16;
        BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
        for (int $$7 = 0; $$7 < 16; ++$$7) {
            block1: for (int $$8 = 0; $$8 < 16; ++$$8) {
                for (Types $$9 : p_64258_) {
                    $$3.add((Object)p_64257_.m_6005_($$9));
                }
                for (int $$10 = $$5 - 1; $$10 >= p_64257_.m_141937_(); --$$10) {
                    $$6.m_122178_($$7, $$10, $$8);
                    BlockState $$11 = p_64257_.m_8055_($$6);
                    if ($$11.m_60713_(Blocks.f_50016_)) continue;
                    while ($$4.hasNext()) {
                        Heightmap $$12 = (Heightmap)$$4.next();
                        if (!$$12.f_64233_.test($$11)) continue;
                        $$12.m_64245_($$7, $$8, $$10 + 1);
                        $$4.remove();
                    }
                    if ($$3.isEmpty()) continue block1;
                    $$4.back($$2);
                }
            }
        }
    }

    public boolean m_64249_(int p_64250_, int p_64251_, int p_64252_, BlockState p_64253_) {
        int $$4 = this.m_64242_(p_64250_, p_64252_);
        if (p_64251_ <= $$4 - 2) {
            return false;
        }
        if (this.f_64233_.test(p_64253_)) {
            if (p_64251_ >= $$4) {
                this.m_64245_(p_64250_, p_64252_, p_64251_ + 1);
                return true;
            }
        } else if ($$4 - 1 == p_64251_) {
            BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
            for (int $$6 = p_64251_ - 1; $$6 >= this.f_64234_.m_141937_(); --$$6) {
                $$5.m_122178_(p_64250_, $$6, p_64252_);
                if (!this.f_64233_.test(this.f_64234_.m_8055_($$5))) continue;
                this.m_64245_(p_64250_, p_64252_, $$6 + 1);
                return true;
            }
            this.m_64245_(p_64250_, p_64252_, this.f_64234_.m_141937_());
            return true;
        }
        return false;
    }

    public int m_64242_(int p_64243_, int p_64244_) {
        return this.m_64240_(Heightmap.m_64265_(p_64243_, p_64244_));
    }

    public int m_158368_(int p_158369_, int p_158370_) {
        return this.m_64240_(Heightmap.m_64265_(p_158369_, p_158370_)) - 1;
    }

    private int m_64240_(int p_64241_) {
        return this.f_64232_.m_13514_(p_64241_) + this.f_64234_.m_141937_();
    }

    private void m_64245_(int p_64246_, int p_64247_, int p_64248_) {
        this.f_64232_.m_13524_(Heightmap.m_64265_(p_64246_, p_64247_), p_64248_ - this.f_64234_.m_141937_());
    }

    public void m_158364_(ChunkAccess p_158365_, Types p_158366_, long[] p_158367_) {
        long[] $$3 = this.f_64232_.m_13513_();
        if ($$3.length == p_158367_.length) {
            System.arraycopy(p_158367_, 0, $$3, 0, p_158367_.length);
            return;
        }
        f_158363_.warn("Ignoring heightmap data for chunk " + p_158365_.m_7697_() + ", size does not match; expected: " + $$3.length + ", got: " + p_158367_.length);
        Heightmap.m_64256_(p_158365_, EnumSet.of(p_158366_));
    }

    public long[] m_64239_() {
        return this.f_64232_.m_13513_();
    }

    private static int m_64265_(int p_64266_, int p_64267_) {
        return p_64266_ + p_64267_ * 16;
    }

    public static final class Types
    extends Enum<Types>
    implements StringRepresentable {
        public static final /* enum */ Types WORLD_SURFACE_WG = new Types("WORLD_SURFACE_WG", Usage.WORLDGEN, f_64230_);
        public static final /* enum */ Types WORLD_SURFACE = new Types("WORLD_SURFACE", Usage.CLIENT, f_64230_);
        public static final /* enum */ Types OCEAN_FLOOR_WG = new Types("OCEAN_FLOOR_WG", Usage.WORLDGEN, f_64231_);
        public static final /* enum */ Types OCEAN_FLOOR = new Types("OCEAN_FLOOR", Usage.LIVE_WORLD, f_64231_);
        public static final /* enum */ Types MOTION_BLOCKING = new Types("MOTION_BLOCKING", Usage.CLIENT, p_64296_ -> p_64296_.m_60767_().m_76334_() || !p_64296_.m_60819_().m_76178_());
        public static final /* enum */ Types MOTION_BLOCKING_NO_LEAVES = new Types("MOTION_BLOCKING_NO_LEAVES", Usage.LIVE_WORLD, p_64289_ -> (p_64289_.m_60767_().m_76334_() || !p_64289_.m_60819_().m_76178_()) && !(p_64289_.m_60734_() instanceof LeavesBlock));
        public static final Codec<Types> f_64274_;
        private final String f_64275_;
        private final Usage f_64276_;
        private final Predicate<BlockState> f_64277_;
        private static final /* synthetic */ Types[] $VALUES;

        public static Types[] values() {
            return (Types[])$VALUES.clone();
        }

        public static Types valueOf(String p_64301_) {
            return Enum.valueOf(Types.class, p_64301_);
        }

        private Types(String p_64284_, Usage p_64285_, Predicate<BlockState> p_64286_) {
            this.f_64275_ = p_64284_;
            this.f_64276_ = p_64285_;
            this.f_64277_ = p_64286_;
        }

        public String m_64294_() {
            return this.f_64275_;
        }

        public boolean m_64297_() {
            return this.f_64276_ == Usage.CLIENT;
        }

        public boolean m_64298_() {
            return this.f_64276_ != Usage.WORLDGEN;
        }

        public Predicate<BlockState> m_64299_() {
            return this.f_64277_;
        }

        @Override
        public String m_7912_() {
            return this.f_64275_;
        }

        private static /* synthetic */ Types[] m_158371_() {
            return new Types[]{WORLD_SURFACE_WG, WORLD_SURFACE, OCEAN_FLOOR_WG, OCEAN_FLOOR, MOTION_BLOCKING, MOTION_BLOCKING_NO_LEAVES};
        }

        static {
            $VALUES = Types.m_158371_();
            f_64274_ = StringRepresentable.m_216439_(Types::values);
        }
    }

    public static final class Usage
    extends Enum<Usage> {
        public static final /* enum */ Usage WORLDGEN = new Usage();
        public static final /* enum */ Usage LIVE_WORLD = new Usage();
        public static final /* enum */ Usage CLIENT = new Usage();
        private static final /* synthetic */ Usage[] $VALUES;

        public static Usage[] values() {
            return (Usage[])$VALUES.clone();
        }

        public static Usage valueOf(String p_64312_) {
            return Enum.valueOf(Usage.class, p_64312_);
        }

        private static /* synthetic */ Usage[] m_158372_() {
            return new Usage[]{WORLDGEN, LIVE_WORLD, CLIENT};
        }

        static {
            $VALUES = Usage.m_158372_();
        }
    }
}

