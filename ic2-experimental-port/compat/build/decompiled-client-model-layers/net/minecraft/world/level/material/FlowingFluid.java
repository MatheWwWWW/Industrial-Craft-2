/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.shorts.Short2BooleanMap
 *  it.unimi.dsi.fastutil.shorts.Short2BooleanOpenHashMap
 *  it.unimi.dsi.fastutil.shorts.Short2ObjectMap
 *  it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap
 */
package net.minecraft.world.level.material;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class FlowingFluid
extends Fluid {
    public static final BooleanProperty f_75947_ = BlockStateProperties.f_61434_;
    public static final IntegerProperty f_75948_ = BlockStateProperties.f_61420_;
    private static final int f_164507_ = 200;
    private static final ThreadLocal<Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey>> f_75949_ = ThreadLocal.withInitial(() -> {
        Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> $$0 = new Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey>(200){

            protected void rehash(int p_76102_) {
            }
        };
        $$0.defaultReturnValue((byte)127);
        return $$0;
    });
    private final Map<FluidState, VoxelShape> f_75950_ = Maps.newIdentityHashMap();

    @Override
    protected void m_7180_(StateDefinition.Builder<Fluid, FluidState> p_76046_) {
        p_76046_.m_61104_(f_75947_);
    }

    @Override
    public Vec3 m_7000_(BlockGetter p_75987_, BlockPos p_75988_, FluidState p_75989_) {
        double $$3 = 0.0;
        double $$4 = 0.0;
        BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
        for (Direction $$6 : Direction.Plane.HORIZONTAL) {
            $$5.m_122159_(p_75988_, $$6);
            FluidState $$7 = p_75987_.m_6425_($$5);
            if (!this.m_76094_($$7)) continue;
            float $$8 = $$7.m_76182_();
            float $$9 = 0.0f;
            if ($$8 == 0.0f) {
                Vec3i $$10;
                FluidState $$11;
                if (!p_75987_.m_8055_($$5).m_60767_().m_76334_() && this.m_76094_($$11 = p_75987_.m_6425_((BlockPos)($$10 = $$5.m_7495_()))) && ($$8 = $$11.m_76182_()) > 0.0f) {
                    $$9 = p_75989_.m_76182_() - ($$8 - 0.8888889f);
                }
            } else if ($$8 > 0.0f) {
                $$9 = p_75989_.m_76182_() - $$8;
            }
            if ($$9 == 0.0f) continue;
            $$3 += (double)((float)$$6.m_122429_() * $$9);
            $$4 += (double)((float)$$6.m_122431_() * $$9);
        }
        Vec3 $$12 = new Vec3($$3, 0.0, $$4);
        if (p_75989_.m_61143_(f_75947_).booleanValue()) {
            for (Direction $$13 : Direction.Plane.HORIZONTAL) {
                $$5.m_122159_(p_75988_, $$13);
                if (!this.m_75990_(p_75987_, $$5, $$13) && !this.m_75990_(p_75987_, (BlockPos)$$5.m_7494_(), $$13)) continue;
                $$12 = $$12.m_82541_().m_82520_(0.0, -6.0, 0.0);
                break;
            }
        }
        return $$12.m_82541_();
    }

    private boolean m_76094_(FluidState p_76095_) {
        return p_76095_.m_76178_() || p_76095_.m_76152_().m_6212_(this);
    }

    protected boolean m_75990_(BlockGetter p_75991_, BlockPos p_75992_, Direction p_75993_) {
        BlockState $$3 = p_75991_.m_8055_(p_75992_);
        FluidState $$4 = p_75991_.m_6425_(p_75992_);
        if ($$4.m_76152_().m_6212_(this)) {
            return false;
        }
        if (p_75993_ == Direction.UP) {
            return true;
        }
        if ($$3.m_60767_() == Material.f_76276_) {
            return false;
        }
        return $$3.m_60783_(p_75991_, p_75992_, p_75993_);
    }

    protected void m_76010_(LevelAccessor p_76011_, BlockPos p_76012_, FluidState p_76013_) {
        if (p_76013_.m_76178_()) {
            return;
        }
        BlockState $$3 = p_76011_.m_8055_(p_76012_);
        BlockPos $$4 = p_76012_.m_7495_();
        BlockState $$5 = p_76011_.m_8055_($$4);
        FluidState $$6 = this.m_76035_(p_76011_, $$4, $$5);
        if (this.m_75977_(p_76011_, p_76012_, $$3, Direction.DOWN, $$4, $$5, p_76011_.m_6425_($$4), $$6.m_76152_())) {
            this.m_6364_(p_76011_, $$4, $$5, Direction.DOWN, $$6);
            if (this.m_76019_(p_76011_, p_76012_) >= 3) {
                this.m_76014_(p_76011_, p_76012_, p_76013_, $$3);
            }
        } else if (p_76013_.m_76170_() || !this.m_75956_(p_76011_, $$6.m_76152_(), p_76012_, $$3, $$4, $$5)) {
            this.m_76014_(p_76011_, p_76012_, p_76013_, $$3);
        }
    }

    private void m_76014_(LevelAccessor p_76015_, BlockPos p_76016_, FluidState p_76017_, BlockState p_76018_) {
        int $$4 = p_76017_.m_76186_() - this.m_6713_(p_76015_);
        if (p_76017_.m_61143_(f_75947_).booleanValue()) {
            $$4 = 7;
        }
        if ($$4 <= 0) {
            return;
        }
        Map<Direction, FluidState> $$5 = this.m_76079_(p_76015_, p_76016_, p_76018_);
        for (Map.Entry<Direction, FluidState> $$6 : $$5.entrySet()) {
            BlockState $$10;
            Direction $$7 = $$6.getKey();
            FluidState $$8 = $$6.getValue();
            BlockPos $$9 = p_76016_.m_121945_($$7);
            if (!this.m_75977_(p_76015_, p_76016_, p_76018_, $$7, $$9, $$10 = p_76015_.m_8055_($$9), p_76015_.m_6425_($$9), $$8.m_76152_())) continue;
            this.m_6364_(p_76015_, $$9, $$10, $$7, $$8);
        }
    }

    protected FluidState m_76035_(LevelReader p_76036_, BlockPos p_76037_, BlockState p_76038_) {
        BlockPos $$11;
        BlockState $$12;
        FluidState $$13;
        int $$3 = 0;
        int $$4 = 0;
        for (Direction $$5 : Direction.Plane.HORIZONTAL) {
            BlockPos $$6 = p_76037_.m_121945_($$5);
            BlockState $$7 = p_76036_.m_8055_($$6);
            FluidState $$8 = $$7.m_60819_();
            if (!$$8.m_76152_().m_6212_(this) || !this.m_76061_($$5, p_76036_, p_76037_, p_76038_, $$6, $$7)) continue;
            if ($$8.m_76170_()) {
                ++$$4;
            }
            $$3 = Math.max($$3, $$8.m_76186_());
        }
        if (this.m_6760_() && $$4 >= 2) {
            BlockState $$9 = p_76036_.m_8055_(p_76037_.m_7495_());
            FluidState $$10 = $$9.m_60819_();
            if ($$9.m_60767_().m_76333_() || this.m_76096_($$10)) {
                return this.m_76068_(false);
            }
        }
        if (!($$13 = ($$12 = p_76036_.m_8055_($$11 = p_76037_.m_7494_())).m_60819_()).m_76178_() && $$13.m_76152_().m_6212_(this) && this.m_76061_(Direction.UP, p_76036_, p_76037_, p_76038_, $$11, $$12)) {
            return this.m_75953_(8, true);
        }
        int $$14 = $$3 - this.m_6713_(p_76036_);
        if ($$14 <= 0) {
            return Fluids.f_76191_.m_76145_();
        }
        return this.m_75953_($$14, false);
    }

    private boolean m_76061_(Direction p_76062_, BlockGetter p_76063_, BlockPos p_76064_, BlockState p_76065_, BlockPos p_76066_, BlockState p_76067_) {
        VoxelShape $$12;
        VoxelShape $$11;
        boolean $$13;
        Object $$10;
        Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> $$7;
        if (p_76065_.m_60734_().m_49967_() || p_76067_.m_60734_().m_49967_()) {
            Object $$6 = null;
        } else {
            $$7 = f_75949_.get();
        }
        if ($$7 != null) {
            Block.BlockStatePairKey $$8 = new Block.BlockStatePairKey(p_76065_, p_76067_, p_76062_);
            byte $$9 = $$7.getAndMoveToFirst((Object)$$8);
            if ($$9 != 127) {
                return $$9 != 0;
            }
        } else {
            $$10 = null;
        }
        boolean bl = $$13 = !Shapes.m_83152_($$11 = p_76065_.m_60812_(p_76063_, p_76064_), $$12 = p_76067_.m_60812_(p_76063_, p_76066_), p_76062_);
        if ($$7 != null) {
            if ($$7.size() == 200) {
                $$7.removeLastByte();
            }
            $$7.putAndMoveToFirst($$10, (byte)($$13 ? 1 : 0));
        }
        return $$13;
    }

    public abstract Fluid m_5615_();

    public FluidState m_75953_(int p_75954_, boolean p_75955_) {
        return (FluidState)((FluidState)this.m_5615_().m_76145_().m_61124_(f_75948_, p_75954_)).m_61124_(f_75947_, p_75955_);
    }

    public abstract Fluid m_5613_();

    public FluidState m_76068_(boolean p_76069_) {
        return (FluidState)this.m_5613_().m_76145_().m_61124_(f_75947_, p_76069_);
    }

    protected abstract boolean m_6760_();

    protected void m_6364_(LevelAccessor p_76005_, BlockPos p_76006_, BlockState p_76007_, Direction p_76008_, FluidState p_76009_) {
        if (p_76007_.m_60734_() instanceof LiquidBlockContainer) {
            ((LiquidBlockContainer)((Object)p_76007_.m_60734_())).m_7361_(p_76005_, p_76006_, p_76007_, p_76009_);
        } else {
            if (!p_76007_.m_60795_()) {
                this.m_7456_(p_76005_, p_76006_, p_76007_);
            }
            p_76005_.m_7731_(p_76006_, p_76009_.m_76188_(), 3);
        }
    }

    protected abstract void m_7456_(LevelAccessor var1, BlockPos var2, BlockState var3);

    private static short m_76058_(BlockPos p_76059_, BlockPos p_76060_) {
        int $$2 = p_76060_.m_123341_() - p_76059_.m_123341_();
        int $$3 = p_76060_.m_123343_() - p_76059_.m_123343_();
        return (short)(($$2 + 128 & 0xFF) << 8 | $$3 + 128 & 0xFF);
    }

    protected int m_76026_(LevelReader p_76027_, BlockPos p_76028_, int p_76029_, Direction p_76030_, BlockState p_76031_, BlockPos p_76032_, Short2ObjectMap<Pair<BlockState, FluidState>> p_76033_, Short2BooleanMap p_76034_) {
        int $$8 = 1000;
        for (Direction $$9 : Direction.Plane.HORIZONTAL) {
            int $$16;
            if ($$9 == p_76030_) continue;
            BlockPos $$10 = p_76028_.m_121945_($$9);
            short $$11 = FlowingFluid.m_76058_(p_76032_, $$10);
            Pair $$12 = (Pair)p_76033_.computeIfAbsent($$11, p_192916_ -> {
                BlockState $$3 = p_76027_.m_8055_($$10);
                return Pair.of((Object)$$3, (Object)$$3.m_60819_());
            });
            BlockState $$13 = (BlockState)$$12.getFirst();
            FluidState $$14 = (FluidState)$$12.getSecond();
            if (!this.m_75963_(p_76027_, this.m_5615_(), p_76028_, p_76031_, $$9, $$10, $$13, $$14)) continue;
            boolean $$15 = p_76034_.computeIfAbsent($$11, p_192912_ -> {
                BlockPos $$4 = $$10.m_7495_();
                BlockState $$5 = p_76027_.m_8055_($$4);
                return this.m_75956_(p_76027_, this.m_5615_(), $$10, $$13, $$4, $$5);
            });
            if ($$15) {
                return p_76029_;
            }
            if (p_76029_ >= this.m_6719_(p_76027_) || ($$16 = this.m_76026_(p_76027_, $$10, p_76029_ + 1, $$9.m_122424_(), $$13, p_76032_, p_76033_, p_76034_)) >= $$8) continue;
            $$8 = $$16;
        }
        return $$8;
    }

    private boolean m_75956_(BlockGetter p_75957_, Fluid p_75958_, BlockPos p_75959_, BlockState p_75960_, BlockPos p_75961_, BlockState p_75962_) {
        if (!this.m_76061_(Direction.DOWN, p_75957_, p_75959_, p_75960_, p_75961_, p_75962_)) {
            return false;
        }
        if (p_75962_.m_60819_().m_76152_().m_6212_(this)) {
            return true;
        }
        return this.m_75972_(p_75957_, p_75961_, p_75962_, p_75958_);
    }

    private boolean m_75963_(BlockGetter p_75964_, Fluid p_75965_, BlockPos p_75966_, BlockState p_75967_, Direction p_75968_, BlockPos p_75969_, BlockState p_75970_, FluidState p_75971_) {
        return !this.m_76096_(p_75971_) && this.m_76061_(p_75968_, p_75964_, p_75966_, p_75967_, p_75969_, p_75970_) && this.m_75972_(p_75964_, p_75969_, p_75970_, p_75965_);
    }

    private boolean m_76096_(FluidState p_76097_) {
        return p_76097_.m_76152_().m_6212_(this) && p_76097_.m_76170_();
    }

    protected abstract int m_6719_(LevelReader var1);

    private int m_76019_(LevelReader p_76020_, BlockPos p_76021_) {
        int $$2 = 0;
        for (Direction $$3 : Direction.Plane.HORIZONTAL) {
            BlockPos $$4 = p_76021_.m_121945_($$3);
            FluidState $$5 = p_76020_.m_6425_($$4);
            if (!this.m_76096_($$5)) continue;
            ++$$2;
        }
        return $$2;
    }

    protected Map<Direction, FluidState> m_76079_(LevelReader p_76080_, BlockPos p_76081_, BlockState p_76082_) {
        int $$3 = 1000;
        EnumMap $$4 = Maps.newEnumMap(Direction.class);
        Short2ObjectOpenHashMap $$5 = new Short2ObjectOpenHashMap();
        Short2BooleanOpenHashMap $$6 = new Short2BooleanOpenHashMap();
        for (Direction $$7 : Direction.Plane.HORIZONTAL) {
            int $$17;
            BlockPos $$8 = p_76081_.m_121945_($$7);
            short $$9 = FlowingFluid.m_76058_(p_76081_, $$8);
            Pair $$10 = (Pair)$$5.computeIfAbsent($$9, p_192907_ -> {
                BlockState $$3 = p_76080_.m_8055_($$8);
                return Pair.of((Object)$$3, (Object)$$3.m_60819_());
            });
            BlockState $$11 = (BlockState)$$10.getFirst();
            FluidState $$12 = (FluidState)$$10.getSecond();
            FluidState $$13 = this.m_76035_(p_76080_, $$8, $$11);
            if (!this.m_75963_(p_76080_, $$13.m_76152_(), p_76081_, p_76082_, $$7, $$8, $$11, $$12)) continue;
            BlockPos $$14 = $$8.m_7495_();
            boolean $$15 = $$6.computeIfAbsent($$9, p_192903_ -> {
                BlockState $$5 = p_76080_.m_8055_($$14);
                return this.m_75956_(p_76080_, this.m_5615_(), $$8, $$11, $$14, $$5);
            });
            if ($$15) {
                boolean $$16 = false;
            } else {
                $$17 = this.m_76026_(p_76080_, $$8, 1, $$7.m_122424_(), $$11, p_76081_, (Short2ObjectMap<Pair<BlockState, FluidState>>)$$5, (Short2BooleanMap)$$6);
            }
            if ($$17 < $$3) {
                $$4.clear();
            }
            if ($$17 > $$3) continue;
            $$4.put($$7, $$13);
            $$3 = $$17;
        }
        return $$4;
    }

    private boolean m_75972_(BlockGetter p_75973_, BlockPos p_75974_, BlockState p_75975_, Fluid p_75976_) {
        Block $$4 = p_75975_.m_60734_();
        if ($$4 instanceof LiquidBlockContainer) {
            return ((LiquidBlockContainer)((Object)$$4)).m_6044_(p_75973_, p_75974_, p_75975_, p_75976_);
        }
        if ($$4 instanceof DoorBlock || p_75975_.m_204336_(BlockTags.f_13068_) || p_75975_.m_60713_(Blocks.f_50155_) || p_75975_.m_60713_(Blocks.f_50130_) || p_75975_.m_60713_(Blocks.f_50628_)) {
            return false;
        }
        Material $$5 = p_75975_.m_60767_();
        if ($$5 == Material.f_76298_ || $$5 == Material.f_76297_ || $$5 == Material.f_76301_ || $$5 == Material.f_76304_) {
            return false;
        }
        return !$$5.m_76334_();
    }

    protected boolean m_75977_(BlockGetter p_75978_, BlockPos p_75979_, BlockState p_75980_, Direction p_75981_, BlockPos p_75982_, BlockState p_75983_, FluidState p_75984_, Fluid p_75985_) {
        return p_75984_.m_76158_(p_75978_, p_75982_, p_75985_, p_75981_) && this.m_76061_(p_75981_, p_75978_, p_75979_, p_75980_, p_75982_, p_75983_) && this.m_75972_(p_75978_, p_75982_, p_75983_, p_75985_);
    }

    protected abstract int m_6713_(LevelReader var1);

    protected int m_6886_(Level p_75998_, BlockPos p_75999_, FluidState p_76000_, FluidState p_76001_) {
        return this.m_6718_(p_75998_);
    }

    @Override
    public void m_6292_(Level p_75995_, BlockPos p_75996_, FluidState p_75997_) {
        if (!p_75997_.m_76170_()) {
            FluidState $$3 = this.m_76035_(p_75995_, p_75996_, p_75995_.m_8055_(p_75996_));
            int $$4 = this.m_6886_(p_75995_, p_75996_, p_75997_, $$3);
            if ($$3.m_76178_()) {
                p_75997_ = $$3;
                p_75995_.m_7731_(p_75996_, Blocks.f_50016_.m_49966_(), 3);
            } else if (!$$3.equals(p_75997_)) {
                p_75997_ = $$3;
                BlockState $$5 = p_75997_.m_76188_();
                p_75995_.m_7731_(p_75996_, $$5, 2);
                p_75995_.m_186469_(p_75996_, p_75997_.m_76152_(), $$4);
                p_75995_.m_46672_(p_75996_, $$5.m_60734_());
            }
        }
        this.m_76010_(p_75995_, p_75996_, p_75997_);
    }

    protected static int m_76092_(FluidState p_76093_) {
        if (p_76093_.m_76170_()) {
            return 0;
        }
        return 8 - Math.min(p_76093_.m_76186_(), 8) + (p_76093_.m_61143_(f_75947_) != false ? 8 : 0);
    }

    private static boolean m_76088_(FluidState p_76089_, BlockGetter p_76090_, BlockPos p_76091_) {
        return p_76089_.m_76152_().m_6212_(p_76090_.m_6425_(p_76091_.m_7494_()).m_76152_());
    }

    @Override
    public float m_6098_(FluidState p_76050_, BlockGetter p_76051_, BlockPos p_76052_) {
        if (FlowingFluid.m_76088_(p_76050_, p_76051_, p_76052_)) {
            return 1.0f;
        }
        return p_76050_.m_76182_();
    }

    @Override
    public float m_7427_(FluidState p_76048_) {
        return (float)p_76048_.m_76186_() / 9.0f;
    }

    @Override
    public abstract int m_7430_(FluidState var1);

    @Override
    public VoxelShape m_7999_(FluidState p_76084_, BlockGetter p_76085_, BlockPos p_76086_) {
        if (p_76084_.m_76186_() == 9 && FlowingFluid.m_76088_(p_76084_, p_76085_, p_76086_)) {
            return Shapes.m_83144_();
        }
        return this.f_75950_.computeIfAbsent(p_76084_, p_76073_ -> Shapes.m_83048_(0.0, 0.0, 0.0, 1.0, p_76073_.m_76155_(p_76085_, p_76086_), 1.0));
    }
}

