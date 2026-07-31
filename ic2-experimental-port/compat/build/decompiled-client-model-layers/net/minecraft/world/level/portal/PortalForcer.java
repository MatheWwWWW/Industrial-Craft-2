/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.portal;

import java.util.Comparator;
import java.util.Optional;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;

public class PortalForcer {
    private static final int f_164734_ = 3;
    private static final int f_164735_ = 128;
    private static final int f_164736_ = 16;
    private static final int f_164737_ = 5;
    private static final int f_164738_ = 4;
    private static final int f_164739_ = 3;
    private static final int f_164740_ = -1;
    private static final int f_164741_ = 4;
    private static final int f_164742_ = -1;
    private static final int f_164743_ = 3;
    private static final int f_164744_ = -1;
    private static final int f_164745_ = 2;
    private static final int f_164746_ = -1;
    private final ServerLevel f_77648_;

    public PortalForcer(ServerLevel p_77650_) {
        this.f_77648_ = p_77650_;
    }

    public Optional<BlockUtil.FoundRectangle> m_192985_(BlockPos p_192986_, boolean p_192987_, WorldBorder p_192988_) {
        PoiManager $$3 = this.f_77648_.m_8904_();
        int $$4 = p_192987_ ? 16 : 128;
        $$3.m_27056_(this.f_77648_, p_192986_, $$4);
        Optional<PoiRecord> $$5 = $$3.m_27166_(p_230634_ -> p_230634_.m_203565_(PoiTypes.f_218064_), p_192986_, $$4, PoiManager.Occupancy.ANY).filter(p_192981_ -> p_192988_.m_61937_(p_192981_.m_27257_())).sorted(Comparator.comparingDouble(p_192984_ -> p_192984_.m_27257_().m_123331_(p_192986_)).thenComparingInt(p_192992_ -> p_192992_.m_27257_().m_123342_())).filter(p_192990_ -> this.f_77648_.m_8055_(p_192990_.m_27257_()).m_61138_(BlockStateProperties.f_61364_)).findFirst();
        return $$5.map(p_192975_ -> {
            BlockPos $$1 = p_192975_.m_27257_();
            this.f_77648_.m_7726_().m_8387_(TicketType.f_9447_, new ChunkPos($$1), 3, $$1);
            BlockState $$2 = this.f_77648_.m_8055_($$1);
            return BlockUtil.m_124334_($$1, $$2.m_61143_(BlockStateProperties.f_61364_), 21, Direction.Axis.Y, 21, p_192978_ -> this.f_77648_.m_8055_((BlockPos)p_192978_) == $$2);
        });
    }

    public Optional<BlockUtil.FoundRectangle> m_77666_(BlockPos p_77667_, Direction.Axis p_77668_) {
        Direction $$2 = Direction.m_122390_(Direction.AxisDirection.POSITIVE, p_77668_);
        double $$3 = -1.0;
        BlockPos $$4 = null;
        double $$5 = -1.0;
        BlockPos $$6 = null;
        WorldBorder $$7 = this.f_77648_.m_6857_();
        int $$8 = Math.min(this.f_77648_.m_151558_(), this.f_77648_.m_141937_() + this.f_77648_.m_143344_()) - 1;
        BlockPos.MutableBlockPos $$9 = p_77667_.m_122032_();
        for (BlockPos.MutableBlockPos $$10 : BlockPos.m_121935_(p_77667_, 16, Direction.EAST, Direction.SOUTH)) {
            int $$11 = Math.min($$8, this.f_77648_.m_6924_(Heightmap.Types.MOTION_BLOCKING, $$10.m_123341_(), $$10.m_123343_()));
            boolean $$12 = true;
            if (!$$7.m_61937_($$10) || !$$7.m_61937_($$10.m_122175_($$2, 1))) continue;
            $$10.m_122175_($$2.m_122424_(), 1);
            for (int $$13 = $$11; $$13 >= this.f_77648_.m_141937_(); --$$13) {
                int $$15;
                $$10.m_142448_($$13);
                if (!this.f_77648_.m_46859_($$10)) continue;
                int $$14 = $$13;
                while ($$13 > this.f_77648_.m_141937_() && this.f_77648_.m_46859_($$10.m_122173_(Direction.DOWN))) {
                    --$$13;
                }
                if ($$13 + 4 > $$8 || ($$15 = $$14 - $$13) > 0 && $$15 < 3) continue;
                $$10.m_142448_($$13);
                if (!this.m_77661_($$10, $$9, $$2, 0)) continue;
                double $$16 = p_77667_.m_123331_($$10);
                if (this.m_77661_($$10, $$9, $$2, -1) && this.m_77661_($$10, $$9, $$2, 1) && ($$3 == -1.0 || $$3 > $$16)) {
                    $$3 = $$16;
                    $$4 = $$10.m_7949_();
                }
                if ($$3 != -1.0 || $$5 != -1.0 && !($$5 > $$16)) continue;
                $$5 = $$16;
                $$6 = $$10.m_7949_();
            }
        }
        if ($$3 == -1.0 && $$5 != -1.0) {
            $$4 = $$6;
            $$3 = $$5;
        }
        if ($$3 == -1.0) {
            int $$18 = $$8 - 9;
            int $$17 = Math.max(this.f_77648_.m_141937_() - -1, 70);
            if ($$18 < $$17) {
                return Optional.empty();
            }
            $$4 = new BlockPos(p_77667_.m_123341_(), Mth.m_14045_(p_77667_.m_123342_(), $$17, $$18), p_77667_.m_123343_()).m_7949_();
            Direction $$19 = $$2.m_122427_();
            if (!$$7.m_61937_($$4)) {
                return Optional.empty();
            }
            for (int $$20 = -1; $$20 < 2; ++$$20) {
                for (int $$21 = 0; $$21 < 2; ++$$21) {
                    for (int $$22 = -1; $$22 < 3; ++$$22) {
                        BlockState $$23 = $$22 < 0 ? Blocks.f_50080_.m_49966_() : Blocks.f_50016_.m_49966_();
                        $$9.m_122154_($$4, $$21 * $$2.m_122429_() + $$20 * $$19.m_122429_(), $$22, $$21 * $$2.m_122431_() + $$20 * $$19.m_122431_());
                        this.f_77648_.m_46597_($$9, $$23);
                    }
                }
            }
        }
        for (int $$24 = -1; $$24 < 3; ++$$24) {
            for (int $$25 = -1; $$25 < 4; ++$$25) {
                if ($$24 != -1 && $$24 != 2 && $$25 != -1 && $$25 != 3) continue;
                $$9.m_122154_($$4, $$24 * $$2.m_122429_(), $$25, $$24 * $$2.m_122431_());
                this.f_77648_.m_7731_($$9, Blocks.f_50080_.m_49966_(), 3);
            }
        }
        BlockState $$26 = (BlockState)Blocks.f_50142_.m_49966_().m_61124_(NetherPortalBlock.f_54904_, p_77668_);
        for (int $$27 = 0; $$27 < 2; ++$$27) {
            for (int $$28 = 0; $$28 < 3; ++$$28) {
                $$9.m_122154_($$4, $$27 * $$2.m_122429_(), $$28, $$27 * $$2.m_122431_());
                this.f_77648_.m_7731_($$9, $$26, 18);
            }
        }
        return Optional.of(new BlockUtil.FoundRectangle($$4.m_7949_(), 2, 3));
    }

    private boolean m_77661_(BlockPos p_77662_, BlockPos.MutableBlockPos p_77663_, Direction p_77664_, int p_77665_) {
        Direction $$4 = p_77664_.m_122427_();
        for (int $$5 = -1; $$5 < 3; ++$$5) {
            for (int $$6 = -1; $$6 < 4; ++$$6) {
                p_77663_.m_122154_(p_77662_, p_77664_.m_122429_() * $$5 + $$4.m_122429_() * p_77665_, $$6, p_77664_.m_122431_() * $$5 + $$4.m_122431_() * p_77665_);
                if ($$6 < 0 && !this.f_77648_.m_8055_(p_77663_).m_60767_().m_76333_()) {
                    return false;
                }
                if ($$6 < 0 || this.f_77648_.m_46859_(p_77663_)) continue;
                return false;
            }
        }
        return true;
    }
}

