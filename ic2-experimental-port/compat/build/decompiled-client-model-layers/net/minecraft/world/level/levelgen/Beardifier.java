/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectListIterator
 */
package net.minecraft.world.level.levelgen;

import com.google.common.annotations.VisibleForTesting;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.JigsawJunction;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class Beardifier
implements DensityFunctions.BeardifierOrMarker {
    public static final int f_158060_ = 12;
    private static final int f_158061_ = 24;
    private static final float[] f_158062_ = Util.m_137469_(new float[13824], p_158082_ -> {
        for (int $$1 = 0; $$1 < 24; ++$$1) {
            for (int $$2 = 0; $$2 < 24; ++$$2) {
                for (int $$3 = 0; $$3 < 24; ++$$3) {
                    p_158082_[$$1 * 24 * 24 + $$2 * 24 + $$3] = (float)Beardifier.m_158091_($$2 - 12, $$3 - 12, $$1 - 12);
                }
            }
        }
    });
    private final ObjectListIterator<Rigid> f_158065_;
    private final ObjectListIterator<JigsawJunction> f_158066_;

    public static Beardifier m_223937_(StructureManager p_223938_, ChunkPos p_223939_) {
        int $$2 = p_223939_.m_45604_();
        int $$3 = p_223939_.m_45605_();
        ObjectArrayList $$4 = new ObjectArrayList(10);
        ObjectArrayList $$5 = new ObjectArrayList(32);
        p_223938_.m_220477_(p_223939_, p_223941_ -> p_223941_.m_226620_() != TerrainAdjustment.NONE).forEach(arg_0 -> Beardifier.m_223930_(p_223939_, (ObjectList)$$4, $$2, $$3, (ObjectList)$$5, arg_0));
        return new Beardifier((ObjectListIterator<Rigid>)$$4.iterator(), (ObjectListIterator<JigsawJunction>)$$5.iterator());
    }

    @VisibleForTesting
    public Beardifier(ObjectListIterator<Rigid> p_223917_, ObjectListIterator<JigsawJunction> p_223918_) {
        this.f_158065_ = p_223917_;
        this.f_158066_ = p_223918_;
    }

    @Override
    public double m_207386_(DensityFunction.FunctionContext p_208200_) {
        int $$1 = p_208200_.m_207115_();
        int $$2 = p_208200_.m_207114_();
        int $$3 = p_208200_.m_207113_();
        double $$4 = 0.0;
        while (this.f_158065_.hasNext()) {
            Rigid $$5 = (Rigid)this.f_158065_.next();
            BoundingBox $$6 = $$5.f_223944_();
            int $$7 = $$5.f_223946_();
            int $$8 = Math.max(0, Math.max($$6.m_162395_() - $$1, $$1 - $$6.m_162399_()));
            int $$9 = Math.max(0, Math.max($$6.m_162398_() - $$3, $$3 - $$6.m_162401_()));
            int $$10 = $$6.m_162396_() + $$7;
            int $$11 = $$2 - $$10;
            int $$12 = switch ($$5.f_223945_()) {
                default -> throw new IncompatibleClassChangeError();
                case TerrainAdjustment.NONE -> 0;
                case TerrainAdjustment.BURY, TerrainAdjustment.BEARD_THIN -> $$11;
                case TerrainAdjustment.BEARD_BOX -> Math.max(0, Math.max($$10 - $$2, $$2 - $$6.m_162400_()));
            };
            $$4 += (switch ($$5.f_223945_()) {
                default -> throw new IncompatibleClassChangeError();
                case TerrainAdjustment.NONE -> 0.0;
                case TerrainAdjustment.BURY -> Beardifier.m_158083_($$8, $$12, $$9);
                case TerrainAdjustment.BEARD_THIN, TerrainAdjustment.BEARD_BOX -> Beardifier.m_223925_($$8, $$12, $$9, $$11) * 0.8;
            });
        }
        this.f_158065_.back(Integer.MAX_VALUE);
        while (this.f_158066_.hasNext()) {
            JigsawJunction $$13 = (JigsawJunction)this.f_158066_.next();
            int $$14 = $$1 - $$13.m_210252_();
            int $$15 = $$2 - $$13.m_210257_();
            int $$16 = $$3 - $$13.m_210258_();
            $$4 += Beardifier.m_223925_($$14, $$15, $$16, $$15) * 0.4;
        }
        this.f_158066_.back(Integer.MAX_VALUE);
        return $$4;
    }

    @Override
    public double m_207402_() {
        return Double.NEGATIVE_INFINITY;
    }

    @Override
    public double m_207401_() {
        return Double.POSITIVE_INFINITY;
    }

    private static double m_158083_(int p_158084_, int p_158085_, int p_158086_) {
        double $$3 = Mth.m_184648_(p_158084_, (double)p_158085_ / 2.0, p_158086_);
        return Mth.m_144851_($$3, 0.0, 6.0, 1.0, 0.0);
    }

    private static double m_223925_(int p_223926_, int p_223927_, int p_223928_, int p_223929_) {
        int $$4 = p_223926_ + 12;
        int $$5 = p_223927_ + 12;
        int $$6 = p_223928_ + 12;
        if (!(Beardifier.m_223919_($$4) && Beardifier.m_223919_($$5) && Beardifier.m_223919_($$6))) {
            return 0.0;
        }
        double $$7 = (double)p_223929_ + 0.5;
        double $$8 = Mth.m_211592_(p_223926_, $$7, p_223928_);
        double $$9 = -$$7 * Mth.m_14193_($$8 / 2.0) / 2.0;
        return $$9 * (double)f_158062_[$$6 * 24 * 24 + $$4 * 24 + $$5];
    }

    private static boolean m_223919_(int p_223920_) {
        return p_223920_ >= 0 && p_223920_ < 24;
    }

    private static double m_158091_(int p_158092_, int p_158093_, int p_158094_) {
        return Beardifier.m_223921_(p_158092_, (double)p_158093_ + 0.5, p_158094_);
    }

    private static double m_223921_(int p_223922_, double p_223923_, int p_223924_) {
        double $$3 = Mth.m_211592_(p_223922_, p_223923_, p_223924_);
        double $$4 = Math.pow(Math.E, -$$3 / 16.0);
        return $$4;
    }

    private static /* synthetic */ void m_223930_(ChunkPos p_223931_, ObjectList p_223932_, int p_223933_, int p_223934_, ObjectList p_223935_, StructureStart p_223936_) {
        TerrainAdjustment $$6 = p_223936_.m_226861_().m_226620_();
        for (StructurePiece $$7 : p_223936_.m_73602_()) {
            if (!$$7.m_73411_(p_223931_, 12)) continue;
            if ($$7 instanceof PoolElementStructurePiece) {
                PoolElementStructurePiece $$8 = (PoolElementStructurePiece)$$7;
                StructureTemplatePool.Projection $$9 = $$8.m_209918_().m_210539_();
                if ($$9 == StructureTemplatePool.Projection.RIGID) {
                    p_223932_.add((Object)new Rigid($$8.m_73547_(), $$6, $$8.m_72647_()));
                }
                for (JigsawJunction $$10 : $$8.m_72648_()) {
                    int $$11 = $$10.m_210252_();
                    int $$12 = $$10.m_210258_();
                    if ($$11 <= p_223933_ - 12 || $$12 <= p_223934_ - 12 || $$11 >= p_223933_ + 15 + 12 || $$12 >= p_223934_ + 15 + 12) continue;
                    p_223935_.add((Object)$$10);
                }
                continue;
            }
            p_223932_.add((Object)new Rigid($$7.m_73547_(), $$6, 0));
        }
    }

    @VisibleForTesting
    public record Rigid(BoundingBox f_223944_, TerrainAdjustment f_223945_, int f_223946_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Rigid.class, "box;terrainAdjustment;groundLevelDelta", "f_223944_", "f_223945_", "f_223946_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Rigid.class, "box;terrainAdjustment;groundLevelDelta", "f_223944_", "f_223945_", "f_223946_"}, this);
        }

        @Override
        public final boolean equals(Object p_223955_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Rigid.class, "box;terrainAdjustment;groundLevelDelta", "f_223944_", "f_223945_", "f_223946_"}, this, p_223955_);
        }
    }
}

