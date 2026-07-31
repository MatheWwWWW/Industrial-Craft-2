/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.block;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkVeinBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

public class SculkSpreader {
    public static final int f_222233_ = 24;
    public static final int f_222234_ = 1000;
    public static final float f_222235_ = 0.5f;
    private static final int f_222237_ = 32;
    public static final int f_222236_ = 11;
    final boolean f_222238_;
    private final TagKey<Block> f_222239_;
    private final int f_222240_;
    private final int f_222241_;
    private final int f_222242_;
    private final int f_222243_;
    private List<ChargeCursor> f_222244_ = new ArrayList<ChargeCursor>();
    private static final Logger f_222245_ = LogUtils.getLogger();

    public SculkSpreader(boolean p_222248_, TagKey<Block> p_222249_, int p_222250_, int p_222251_, int p_222252_, int p_222253_) {
        this.f_222238_ = p_222248_;
        this.f_222239_ = p_222249_;
        this.f_222240_ = p_222250_;
        this.f_222241_ = p_222251_;
        this.f_222242_ = p_222252_;
        this.f_222243_ = p_222253_;
    }

    public static SculkSpreader m_222254_() {
        return new SculkSpreader(false, BlockTags.f_215823_, 10, 4, 10, 5);
    }

    public static SculkSpreader m_222274_() {
        return new SculkSpreader(true, BlockTags.f_215824_, 50, 1, 5, 10);
    }

    public TagKey<Block> m_222277_() {
        return this.f_222239_;
    }

    public int m_222278_() {
        return this.f_222240_;
    }

    public int m_222279_() {
        return this.f_222241_;
    }

    public int m_222280_() {
        return this.f_222242_;
    }

    public int m_222281_() {
        return this.f_222243_;
    }

    public boolean m_222282_() {
        return this.f_222238_;
    }

    @VisibleForTesting
    public List<ChargeCursor> m_222283_() {
        return this.f_222244_;
    }

    public void m_222284_() {
        this.f_222244_.clear();
    }

    public void m_222269_(CompoundTag p_222270_) {
        if (p_222270_.m_128425_("cursors", 9)) {
            this.f_222244_.clear();
            List $$1 = ChargeCursor.f_222286_.listOf().parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_222270_.m_128437_("cursors", 10))).resultOrPartial(arg_0 -> ((Logger)f_222245_).error(arg_0)).orElseGet(ArrayList::new);
            int $$2 = Math.min($$1.size(), 32);
            for (int $$3 = 0; $$3 < $$2; ++$$3) {
                this.m_222260_((ChargeCursor)$$1.get($$3));
            }
        }
    }

    public void m_222275_(CompoundTag p_222276_) {
        ChargeCursor.f_222286_.listOf().encodeStart((DynamicOps)NbtOps.f_128958_, this.f_222244_).resultOrPartial(arg_0 -> ((Logger)f_222245_).error(arg_0)).ifPresent(p_222273_ -> p_222276_.m_128365_("cursors", (Tag)p_222273_));
    }

    public void m_222266_(BlockPos p_222267_, int p_222268_) {
        while (p_222268_ > 0) {
            int $$2 = Math.min(p_222268_, 1000);
            this.m_222260_(new ChargeCursor(p_222267_, $$2));
            p_222268_ -= $$2;
        }
    }

    private void m_222260_(ChargeCursor p_222261_) {
        if (this.f_222244_.size() >= 32) {
            return;
        }
        this.f_222244_.add(p_222261_);
    }

    public void m_222255_(LevelAccessor p_222256_, BlockPos p_222257_, RandomSource p_222258_, boolean p_222259_) {
        if (this.f_222244_.isEmpty()) {
            return;
        }
        ArrayList<ChargeCursor> $$4 = new ArrayList<ChargeCursor>();
        HashMap<BlockPos, ChargeCursor> $$5 = new HashMap<BlockPos, ChargeCursor>();
        Object2IntOpenHashMap $$6 = new Object2IntOpenHashMap();
        for (ChargeCursor $$7 : this.f_222244_) {
            $$7.m_222311_(p_222256_, p_222257_, p_222258_, this, p_222259_);
            if ($$7.f_222289_ <= 0) {
                p_222256_.m_46796_(3006, $$7.m_222304_(), 0);
                continue;
            }
            BlockPos $$8 = $$7.m_222304_();
            $$6.computeInt((Object)$$8, (p_222264_, p_222265_) -> (p_222265_ == null ? 0 : p_222265_) + p_222263_.f_222289_);
            ChargeCursor $$9 = (ChargeCursor)$$5.get($$8);
            if ($$9 == null) {
                $$5.put($$8, $$7);
                $$4.add($$7);
                continue;
            }
            if (!this.m_222282_() && $$7.f_222289_ + $$9.f_222289_ <= 1000) {
                $$9.m_222331_($$7);
                continue;
            }
            $$4.add($$7);
            if ($$7.f_222289_ >= $$9.f_222289_) continue;
            $$5.put($$8, $$7);
        }
        for (Object2IntMap.Entry $$10 : $$6.object2IntEntrySet()) {
            Set<Direction> $$14;
            BlockPos $$11 = (BlockPos)$$10.getKey();
            int $$12 = $$10.getIntValue();
            ChargeCursor $$13 = (ChargeCursor)$$5.get($$11);
            Set<Direction> set = $$14 = $$13 == null ? null : $$13.m_222347_();
            if ($$12 <= 0 || $$14 == null) continue;
            int $$15 = (int)(Math.log1p($$12) / (double)2.3f) + 1;
            int $$16 = ($$15 << 6) + MultifaceBlock.m_221576_($$14);
            p_222256_.m_46796_(3006, $$11, $$16);
        }
        this.f_222244_ = $$4;
    }

    public static class ChargeCursor {
        private static final ObjectArrayList<Vec3i> f_222287_ = Util.m_137469_(new ObjectArrayList(18), p_222338_ -> BlockPos.m_121990_(new BlockPos(-1, -1, -1), new BlockPos(1, 1, 1)).filter(p_222336_ -> (p_222336_.m_123341_() == 0 || p_222336_.m_123342_() == 0 || p_222336_.m_123343_() == 0) && !p_222336_.equals(BlockPos.f_121853_)).map(BlockPos::m_7949_).forEach(arg_0 -> ((ObjectArrayList)p_222338_).add(arg_0)));
        public static final int f_222285_ = 1;
        private BlockPos f_222288_;
        int f_222289_;
        private int f_222290_;
        private int f_222291_;
        @Nullable
        private Set<Direction> f_222292_;
        private static final Codec<Set<Direction>> f_222293_ = Direction.f_175356_.listOf().xmap(p_222340_ -> Sets.newEnumSet((Iterable)p_222340_, Direction.class), Lists::newArrayList);
        public static final Codec<ChargeCursor> f_222286_ = RecordCodecBuilder.create(p_222330_ -> p_222330_.group((App)BlockPos.f_121852_.fieldOf("pos").forGetter(ChargeCursor::m_222304_), (App)Codec.intRange((int)0, (int)1000).fieldOf("charge").orElse((Object)0).forGetter(ChargeCursor::m_222341_), (App)Codec.intRange((int)0, (int)1).fieldOf("decay_delay").orElse((Object)1).forGetter(ChargeCursor::m_222344_), (App)Codec.intRange((int)0, (int)Integer.MAX_VALUE).fieldOf("update_delay").orElse((Object)0).forGetter(p_222346_ -> p_222346_.f_222290_), (App)f_222293_.optionalFieldOf("facings").forGetter(p_222343_ -> Optional.ofNullable(p_222343_.m_222347_()))).apply((Applicative)p_222330_, ChargeCursor::new));

        private ChargeCursor(BlockPos p_222299_, int p_222300_, int p_222301_, int p_222302_, Optional<Set<Direction>> p_222303_) {
            this.f_222288_ = p_222299_;
            this.f_222289_ = p_222300_;
            this.f_222291_ = p_222301_;
            this.f_222290_ = p_222302_;
            this.f_222292_ = p_222303_.orElse(null);
        }

        public ChargeCursor(BlockPos p_222296_, int p_222297_) {
            this(p_222296_, p_222297_, 1, 0, Optional.empty());
        }

        public BlockPos m_222304_() {
            return this.f_222288_;
        }

        public int m_222341_() {
            return this.f_222289_;
        }

        public int m_222344_() {
            return this.f_222291_;
        }

        @Nullable
        public Set<Direction> m_222347_() {
            return this.f_222292_;
        }

        private boolean m_222325_(LevelAccessor p_222326_, BlockPos p_222327_, boolean p_222328_) {
            if (this.f_222289_ <= 0) {
                return false;
            }
            if (p_222328_) {
                return true;
            }
            if (p_222326_ instanceof ServerLevel) {
                ServerLevel $$3 = (ServerLevel)p_222326_;
                return $$3.m_220393_(p_222327_);
            }
            return false;
        }

        public void m_222311_(LevelAccessor p_222312_, BlockPos p_222313_, RandomSource p_222314_, SculkSpreader p_222315_, boolean p_222316_) {
            if (!this.m_222325_(p_222312_, p_222313_, p_222315_.f_222238_)) {
                return;
            }
            if (this.f_222290_ > 0) {
                --this.f_222290_;
                return;
            }
            BlockState $$5 = p_222312_.m_8055_(this.f_222288_);
            SculkBehaviour $$6 = ChargeCursor.m_222333_($$5);
            if (p_222316_ && $$6.m_214094_(p_222312_, this.f_222288_, $$5, this.f_222292_, p_222315_.m_222282_())) {
                if ($$6.m_213999_()) {
                    $$5 = p_222312_.m_8055_(this.f_222288_);
                    $$6 = ChargeCursor.m_222333_($$5);
                }
                p_222312_.m_5594_(null, this.f_222288_, SoundEvents.f_215753_, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            this.f_222289_ = $$6.m_213628_(this, p_222312_, p_222313_, p_222314_, p_222315_, p_222316_);
            if (this.f_222289_ <= 0) {
                $$6.m_213805_(p_222312_, $$5, this.f_222288_, p_222314_);
                return;
            }
            BlockPos $$7 = ChargeCursor.m_222307_(p_222312_, this.f_222288_, p_222314_);
            if ($$7 != null) {
                $$6.m_213805_(p_222312_, $$5, this.f_222288_, p_222314_);
                this.f_222288_ = $$7.m_7949_();
                if (p_222315_.m_222282_() && !this.f_222288_.m_123314_(new Vec3i(p_222313_.m_123341_(), this.f_222288_.m_123342_(), p_222313_.m_123343_()), 15.0)) {
                    this.f_222289_ = 0;
                    return;
                }
                $$5 = p_222312_.m_8055_($$7);
            }
            if ($$5.m_60734_() instanceof SculkBehaviour) {
                this.f_222292_ = MultifaceBlock.m_221584_($$5);
            }
            this.f_222291_ = $$6.m_213670_(this.f_222291_);
            this.f_222290_ = $$6.m_222025_();
        }

        void m_222331_(ChargeCursor p_222332_) {
            this.f_222289_ += p_222332_.f_222289_;
            p_222332_.f_222289_ = 0;
            this.f_222290_ = Math.min(this.f_222290_, p_222332_.f_222290_);
        }

        private static SculkBehaviour m_222333_(BlockState p_222334_) {
            SculkBehaviour $$1;
            Block block = p_222334_.m_60734_();
            return block instanceof SculkBehaviour ? ($$1 = (SculkBehaviour)((Object)block)) : SculkBehaviour.f_222023_;
        }

        private static List<Vec3i> m_222305_(RandomSource p_222306_) {
            return Util.m_214611_(f_222287_, p_222306_);
        }

        @Nullable
        private static BlockPos m_222307_(LevelAccessor p_222308_, BlockPos p_222309_, RandomSource p_222310_) {
            BlockPos.MutableBlockPos $$3 = p_222309_.m_122032_();
            BlockPos.MutableBlockPos $$4 = p_222309_.m_122032_();
            for (Vec3i $$5 : ChargeCursor.m_222305_(p_222310_)) {
                $$4.m_175306_(p_222309_, $$5);
                BlockState $$6 = p_222308_.m_8055_($$4);
                if (!($$6.m_60734_() instanceof SculkBehaviour) || !ChargeCursor.m_222317_(p_222308_, p_222309_, $$4)) continue;
                $$3.m_122190_($$4);
                if (!SculkVeinBlock.m_222354_(p_222308_, $$6, $$4)) continue;
                break;
            }
            return $$3.equals(p_222309_) ? null : $$3;
        }

        private static boolean m_222317_(LevelAccessor p_222318_, BlockPos p_222319_, BlockPos p_222320_) {
            if (p_222319_.m_123333_(p_222320_) == 1) {
                return true;
            }
            BlockPos $$3 = p_222320_.m_121996_(p_222319_);
            Direction $$4 = Direction.m_122387_(Direction.Axis.X, $$3.m_123341_() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
            Direction $$5 = Direction.m_122387_(Direction.Axis.Y, $$3.m_123342_() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
            Direction $$6 = Direction.m_122387_(Direction.Axis.Z, $$3.m_123343_() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
            if ($$3.m_123341_() == 0) {
                return ChargeCursor.m_222321_(p_222318_, p_222319_, $$5) || ChargeCursor.m_222321_(p_222318_, p_222319_, $$6);
            }
            if ($$3.m_123342_() == 0) {
                return ChargeCursor.m_222321_(p_222318_, p_222319_, $$4) || ChargeCursor.m_222321_(p_222318_, p_222319_, $$6);
            }
            return ChargeCursor.m_222321_(p_222318_, p_222319_, $$4) || ChargeCursor.m_222321_(p_222318_, p_222319_, $$5);
        }

        private static boolean m_222321_(LevelAccessor p_222322_, BlockPos p_222323_, Direction p_222324_) {
            BlockPos $$3 = p_222323_.m_121945_(p_222324_);
            return !p_222322_.m_8055_($$3).m_60783_(p_222322_, $$3, p_222324_.m_122424_());
        }
    }
}

