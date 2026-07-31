/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongConsumer
 */
package net.minecraft.core;

import it.unimi.dsi.fastutil.longs.LongConsumer;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Cursor3D;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.entity.EntityAccess;

public class SectionPos
extends Vec3i {
    public static final int f_175535_ = 4;
    public static final int f_175536_ = 16;
    public static final int f_175539_ = 15;
    public static final int f_175537_ = 8;
    public static final int f_175538_ = 15;
    private static final int f_175540_ = 22;
    private static final int f_175541_ = 20;
    private static final int f_175542_ = 22;
    private static final long f_175543_ = 0x3FFFFFL;
    private static final long f_175544_ = 1048575L;
    private static final long f_175545_ = 0x3FFFFFL;
    private static final int f_175546_ = 0;
    private static final int f_175547_ = 20;
    private static final int f_175548_ = 42;
    private static final int f_175549_ = 8;
    private static final int f_175550_ = 0;
    private static final int f_175551_ = 4;

    SectionPos(int p_123162_, int p_123163_, int p_123164_) {
        super(p_123162_, p_123163_, p_123164_);
    }

    public static SectionPos m_123173_(int p_123174_, int p_123175_, int p_123176_) {
        return new SectionPos(p_123174_, p_123175_, p_123176_);
    }

    public static SectionPos m_123199_(BlockPos p_123200_) {
        return new SectionPos(SectionPos.m_123171_(p_123200_.m_123341_()), SectionPos.m_123171_(p_123200_.m_123342_()), SectionPos.m_123171_(p_123200_.m_123343_()));
    }

    public static SectionPos m_123196_(ChunkPos p_123197_, int p_123198_) {
        return new SectionPos(p_123197_.f_45578_, p_123198_, p_123197_.f_45579_);
    }

    public static SectionPos m_235861_(EntityAccess p_235862_) {
        return SectionPos.m_123199_(p_235862_.m_20183_());
    }

    public static SectionPos m_235863_(Position p_235864_) {
        return new SectionPos(SectionPos.m_235865_(p_235864_.m_7096_()), SectionPos.m_235865_(p_235864_.m_7098_()), SectionPos.m_235865_(p_235864_.m_7094_()));
    }

    public static SectionPos m_123184_(long p_123185_) {
        return new SectionPos(SectionPos.m_123213_(p_123185_), SectionPos.m_123225_(p_123185_), SectionPos.m_123230_(p_123185_));
    }

    public static SectionPos m_175562_(ChunkAccess p_175563_) {
        return SectionPos.m_123196_(p_175563_.m_7697_(), p_175563_.m_151560_());
    }

    public static long m_123191_(long p_123192_, Direction p_123193_) {
        return SectionPos.m_123186_(p_123192_, p_123193_.m_122429_(), p_123193_.m_122430_(), p_123193_.m_122431_());
    }

    public static long m_123186_(long p_123187_, int p_123188_, int p_123189_, int p_123190_) {
        return SectionPos.m_123209_(SectionPos.m_123213_(p_123187_) + p_123188_, SectionPos.m_123225_(p_123187_) + p_123189_, SectionPos.m_123230_(p_123187_) + p_123190_);
    }

    public static int m_175552_(double p_175553_) {
        return SectionPos.m_123171_(Mth.m_14107_(p_175553_));
    }

    public static int m_123171_(int p_123172_) {
        return p_123172_ >> 4;
    }

    public static int m_235865_(double p_235866_) {
        return Mth.m_14107_(p_235866_) >> 4;
    }

    public static int m_123207_(int p_123208_) {
        return p_123208_ & 0xF;
    }

    public static short m_123218_(BlockPos p_123219_) {
        int $$1 = SectionPos.m_123207_(p_123219_.m_123341_());
        int $$2 = SectionPos.m_123207_(p_123219_.m_123342_());
        int $$3 = SectionPos.m_123207_(p_123219_.m_123343_());
        return (short)($$1 << 8 | $$3 << 4 | $$2 << 0);
    }

    public static int m_123204_(short p_123205_) {
        return p_123205_ >>> 8 & 0xF;
    }

    public static int m_123220_(short p_123221_) {
        return p_123221_ >>> 0 & 0xF;
    }

    public static int m_123227_(short p_123228_) {
        return p_123228_ >>> 4 & 0xF;
    }

    public int m_123232_(short p_123233_) {
        return this.m_123229_() + SectionPos.m_123204_(p_123233_);
    }

    public int m_123237_(short p_123238_) {
        return this.m_123234_() + SectionPos.m_123220_(p_123238_);
    }

    public int m_123242_(short p_123243_) {
        return this.m_123239_() + SectionPos.m_123227_(p_123243_);
    }

    public BlockPos m_123245_(short p_123246_) {
        return new BlockPos(this.m_123232_(p_123246_), this.m_123237_(p_123246_), this.m_123242_(p_123246_));
    }

    public static int m_123223_(int p_123224_) {
        return p_123224_ << 4;
    }

    public static int m_175554_(int p_175555_, int p_175556_) {
        return SectionPos.m_123223_(p_175555_) + p_175556_;
    }

    public static int m_123213_(long p_123214_) {
        return (int)(p_123214_ << 0 >> 42);
    }

    public static int m_123225_(long p_123226_) {
        return (int)(p_123226_ << 44 >> 44);
    }

    public static int m_123230_(long p_123231_) {
        return (int)(p_123231_ << 22 >> 42);
    }

    public int m_123170_() {
        return this.m_123341_();
    }

    public int m_123206_() {
        return this.m_123342_();
    }

    public int m_123222_() {
        return this.m_123343_();
    }

    public int m_123229_() {
        return SectionPos.m_123223_(this.m_123170_());
    }

    public int m_123234_() {
        return SectionPos.m_123223_(this.m_123206_());
    }

    public int m_123239_() {
        return SectionPos.m_123223_(this.m_123222_());
    }

    public int m_123244_() {
        return SectionPos.m_175554_(this.m_123170_(), 15);
    }

    public int m_123247_() {
        return SectionPos.m_175554_(this.m_123206_(), 15);
    }

    public int m_123248_() {
        return SectionPos.m_175554_(this.m_123222_(), 15);
    }

    public static long m_123235_(long p_123236_) {
        return SectionPos.m_123209_(SectionPos.m_123171_(BlockPos.m_121983_(p_123236_)), SectionPos.m_123171_(BlockPos.m_122008_(p_123236_)), SectionPos.m_123171_(BlockPos.m_122015_(p_123236_)));
    }

    public static long m_123240_(long p_123241_) {
        return p_123241_ & 0xFFFFFFFFFFF00000L;
    }

    public BlockPos m_123249_() {
        return new BlockPos(SectionPos.m_123223_(this.m_123170_()), SectionPos.m_123223_(this.m_123206_()), SectionPos.m_123223_(this.m_123222_()));
    }

    public BlockPos m_123250_() {
        int $$0 = 8;
        return this.m_123249_().m_7918_(8, 8, 8);
    }

    public ChunkPos m_123251_() {
        return new ChunkPos(this.m_123170_(), this.m_123222_());
    }

    public static long m_175568_(BlockPos p_175569_) {
        return SectionPos.m_123209_(SectionPos.m_123171_(p_175569_.m_123341_()), SectionPos.m_123171_(p_175569_.m_123342_()), SectionPos.m_123171_(p_175569_.m_123343_()));
    }

    public static long m_123209_(int p_123210_, int p_123211_, int p_123212_) {
        long $$3 = 0L;
        $$3 |= ((long)p_123210_ & 0x3FFFFFL) << 42;
        $$3 |= ((long)p_123211_ & 0xFFFFFL) << 0;
        return $$3 |= ((long)p_123212_ & 0x3FFFFFL) << 20;
    }

    public long m_123252_() {
        return SectionPos.m_123209_(this.m_123170_(), this.m_123206_(), this.m_123222_());
    }

    @Override
    public SectionPos m_7918_(int p_175571_, int p_175572_, int p_175573_) {
        if (p_175571_ == 0 && p_175572_ == 0 && p_175573_ == 0) {
            return this;
        }
        return new SectionPos(this.m_123170_() + p_175571_, this.m_123206_() + p_175572_, this.m_123222_() + p_175573_);
    }

    public Stream<BlockPos> m_123253_() {
        return BlockPos.m_121886_(this.m_123229_(), this.m_123234_(), this.m_123239_(), this.m_123244_(), this.m_123247_(), this.m_123248_());
    }

    public static Stream<SectionPos> m_123201_(SectionPos p_123202_, int p_123203_) {
        int $$2 = p_123202_.m_123170_();
        int $$3 = p_123202_.m_123206_();
        int $$4 = p_123202_.m_123222_();
        return SectionPos.m_123177_($$2 - p_123203_, $$3 - p_123203_, $$4 - p_123203_, $$2 + p_123203_, $$3 + p_123203_, $$4 + p_123203_);
    }

    public static Stream<SectionPos> m_175557_(ChunkPos p_175558_, int p_175559_, int p_175560_, int p_175561_) {
        int $$4 = p_175558_.f_45578_;
        int $$5 = p_175558_.f_45579_;
        return SectionPos.m_123177_($$4 - p_175559_, p_175560_, $$5 - p_175559_, $$4 + p_175559_, p_175561_ - 1, $$5 + p_175559_);
    }

    public static Stream<SectionPos> m_123177_(final int p_123178_, final int p_123179_, final int p_123180_, final int p_123181_, final int p_123182_, final int p_123183_) {
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<SectionPos>((long)((p_123181_ - p_123178_ + 1) * (p_123182_ - p_123179_ + 1) * (p_123183_ - p_123180_ + 1)), 64){
            final Cursor3D f_123254_;
            {
                super(p_123262_, p_123263_);
                this.f_123254_ = new Cursor3D(p_123178_, p_123179_, p_123180_, p_123181_, p_123182_, p_123183_);
            }

            @Override
            public boolean tryAdvance(Consumer<? super SectionPos> p_123271_) {
                if (this.f_123254_.m_122304_()) {
                    p_123271_.accept(new SectionPos(this.f_123254_.m_122305_(), this.f_123254_.m_122306_(), this.f_123254_.m_122307_()));
                    return true;
                }
                return false;
            }
        }, false);
    }

    public static void m_194642_(BlockPos p_194643_, LongConsumer p_194644_) {
        SectionPos.m_194634_(p_194643_.m_123341_(), p_194643_.m_123342_(), p_194643_.m_123343_(), p_194644_);
    }

    public static void m_194639_(long p_194640_, LongConsumer p_194641_) {
        SectionPos.m_194634_(BlockPos.m_121983_(p_194640_), BlockPos.m_122008_(p_194640_), BlockPos.m_122015_(p_194640_), p_194641_);
    }

    public static void m_194634_(int p_194635_, int p_194636_, int p_194637_, LongConsumer p_194638_) {
        int $$4 = SectionPos.m_123171_(p_194635_ - 1);
        int $$5 = SectionPos.m_123171_(p_194635_ + 1);
        int $$6 = SectionPos.m_123171_(p_194636_ - 1);
        int $$7 = SectionPos.m_123171_(p_194636_ + 1);
        int $$8 = SectionPos.m_123171_(p_194637_ - 1);
        int $$9 = SectionPos.m_123171_(p_194637_ + 1);
        if ($$4 == $$5 && $$6 == $$7 && $$8 == $$9) {
            p_194638_.accept(SectionPos.m_123209_($$4, $$6, $$8));
        } else {
            for (int $$10 = $$4; $$10 <= $$5; ++$$10) {
                for (int $$11 = $$6; $$11 <= $$7; ++$$11) {
                    for (int $$12 = $$8; $$12 <= $$9; ++$$12) {
                        p_194638_.accept(SectionPos.m_123209_($$10, $$11, $$12));
                    }
                }
            }
        }
    }

    @Override
    public /* synthetic */ Vec3i m_7918_(int n, int n2, int n3) {
        return this.m_7918_(n, n2, n3);
    }
}

