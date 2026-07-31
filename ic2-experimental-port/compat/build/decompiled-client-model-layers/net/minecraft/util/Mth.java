/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.math.NumberUtils
 */
package net.minecraft.util;

import java.util.Locale;
import java.util.UUID;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import net.minecraft.Util;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.math.NumberUtils;

public class Mth {
    private static final int f_144836_ = 1024;
    private static final float f_144837_ = 1024.0f;
    private static final long f_144838_ = 61440L;
    private static final long f_144839_ = 16384L;
    private static final long f_144840_ = -4611686018427387904L;
    private static final long f_144841_ = Long.MIN_VALUE;
    public static final float f_144830_ = (float)Math.PI;
    public static final float f_144831_ = 1.5707964f;
    public static final float f_144832_ = (float)Math.PI * 2;
    public static final float f_144833_ = (float)Math.PI / 180;
    public static final float f_144834_ = 57.295776f;
    public static final float f_144835_ = 1.0E-5f;
    public static final float f_13994_ = Mth.m_14116_(2.0f);
    private static final float f_144842_ = 10430.378f;
    private static final float[] f_13995_ = Util.m_137469_(new float[65536], p_14077_ -> {
        for (int $$1 = 0; $$1 < ((float[])p_14077_).length; ++$$1) {
            p_14077_[$$1] = (float)Math.sin((double)$$1 * Math.PI * 2.0 / 65536.0);
        }
    });
    private static final RandomSource f_13996_ = RandomSource.m_216337_();
    private static final int[] f_13997_ = new int[]{0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
    private static final double f_144843_ = 0.16666666666666666;
    private static final int f_144844_ = 8;
    private static final int f_144845_ = 257;
    private static final double f_13998_ = Double.longBitsToDouble(4805340802404319232L);
    private static final double[] f_13999_ = new double[257];
    private static final double[] f_14000_ = new double[257];

    public static float m_14031_(float p_14032_) {
        return f_13995_[(int)(p_14032_ * 10430.378f) & 0xFFFF];
    }

    public static float m_14089_(float p_14090_) {
        return f_13995_[(int)(p_14090_ * 10430.378f + 16384.0f) & 0xFFFF];
    }

    public static float m_14116_(float p_14117_) {
        return (float)Math.sqrt(p_14117_);
    }

    public static int m_14143_(float p_14144_) {
        int $$1 = (int)p_14144_;
        return p_14144_ < (float)$$1 ? $$1 - 1 : $$1;
    }

    public static int m_14080_(double p_14081_) {
        return (int)(p_14081_ + 1024.0) - 1024;
    }

    public static int m_14107_(double p_14108_) {
        int $$1 = (int)p_14108_;
        return p_14108_ < (double)$$1 ? $$1 - 1 : $$1;
    }

    public static long m_14134_(double p_14135_) {
        long $$1 = (long)p_14135_;
        return p_14135_ < (double)$$1 ? $$1 - 1L : $$1;
    }

    public static int m_144939_(double p_144940_) {
        return (int)(p_144940_ >= 0.0 ? p_144940_ : -p_144940_ + 1.0);
    }

    public static float m_14154_(float p_14155_) {
        return Math.abs(p_14155_);
    }

    public static int m_14040_(int p_14041_) {
        return Math.abs(p_14041_);
    }

    public static int m_14167_(float p_14168_) {
        int $$1 = (int)p_14168_;
        return p_14168_ > (float)$$1 ? $$1 + 1 : $$1;
    }

    public static int m_14165_(double p_14166_) {
        int $$1 = (int)p_14166_;
        return p_14166_ > (double)$$1 ? $$1 + 1 : $$1;
    }

    public static byte m_144847_(byte p_144848_, byte p_144849_, byte p_144850_) {
        if (p_144848_ < p_144849_) {
            return p_144849_;
        }
        if (p_144848_ > p_144850_) {
            return p_144850_;
        }
        return p_144848_;
    }

    public static int m_14045_(int p_14046_, int p_14047_, int p_14048_) {
        if (p_14046_ < p_14047_) {
            return p_14047_;
        }
        if (p_14046_ > p_14048_) {
            return p_14048_;
        }
        return p_14046_;
    }

    public static long m_14053_(long p_14054_, long p_14055_, long p_14056_) {
        if (p_14054_ < p_14055_) {
            return p_14055_;
        }
        if (p_14054_ > p_14056_) {
            return p_14056_;
        }
        return p_14054_;
    }

    public static float m_14036_(float p_14037_, float p_14038_, float p_14039_) {
        if (p_14037_ < p_14038_) {
            return p_14038_;
        }
        if (p_14037_ > p_14039_) {
            return p_14039_;
        }
        return p_14037_;
    }

    public static double m_14008_(double p_14009_, double p_14010_, double p_14011_) {
        if (p_14009_ < p_14010_) {
            return p_14010_;
        }
        if (p_14009_ > p_14011_) {
            return p_14011_;
        }
        return p_14009_;
    }

    public static double m_14085_(double p_14086_, double p_14087_, double p_14088_) {
        if (p_14088_ < 0.0) {
            return p_14086_;
        }
        if (p_14088_ > 1.0) {
            return p_14087_;
        }
        return Mth.m_14139_(p_14088_, p_14086_, p_14087_);
    }

    public static float m_144920_(float p_144921_, float p_144922_, float p_144923_) {
        if (p_144923_ < 0.0f) {
            return p_144921_;
        }
        if (p_144923_ > 1.0f) {
            return p_144922_;
        }
        return Mth.m_14179_(p_144923_, p_144921_, p_144922_);
    }

    public static double m_14005_(double p_14006_, double p_14007_) {
        if (p_14006_ < 0.0) {
            p_14006_ = -p_14006_;
        }
        if (p_14007_ < 0.0) {
            p_14007_ = -p_14007_;
        }
        return p_14006_ > p_14007_ ? p_14006_ : p_14007_;
    }

    public static int m_14042_(int p_14043_, int p_14044_) {
        return Math.floorDiv(p_14043_, p_14044_);
    }

    public static int m_216271_(RandomSource p_216272_, int p_216273_, int p_216274_) {
        if (p_216273_ >= p_216274_) {
            return p_216273_;
        }
        return p_216272_.m_188503_(p_216274_ - p_216273_ + 1) + p_216273_;
    }

    public static float m_216267_(RandomSource p_216268_, float p_216269_, float p_216270_) {
        if (p_216269_ >= p_216270_) {
            return p_216269_;
        }
        return p_216268_.m_188501_() * (p_216270_ - p_216269_) + p_216269_;
    }

    public static double m_216263_(RandomSource p_216264_, double p_216265_, double p_216266_) {
        if (p_216265_ >= p_216266_) {
            return p_216265_;
        }
        return p_216264_.m_188500_() * (p_216266_ - p_216265_) + p_216265_;
    }

    public static double m_14078_(long[] p_14079_) {
        long $$1 = 0L;
        for (long $$2 : p_14079_) {
            $$1 += $$2;
        }
        return (double)$$1 / (double)p_14079_.length;
    }

    public static boolean m_14033_(float p_14034_, float p_14035_) {
        return Math.abs(p_14035_ - p_14034_) < 1.0E-5f;
    }

    public static boolean m_14082_(double p_14083_, double p_14084_) {
        return Math.abs(p_14084_ - p_14083_) < (double)1.0E-5f;
    }

    public static int m_14100_(int p_14101_, int p_14102_) {
        return Math.floorMod(p_14101_, p_14102_);
    }

    public static float m_14091_(float p_14092_, float p_14093_) {
        return (p_14092_ % p_14093_ + p_14093_) % p_14093_;
    }

    public static double m_14109_(double p_14110_, double p_14111_) {
        return (p_14110_ % p_14111_ + p_14111_) % p_14111_;
    }

    public static int m_14098_(int p_14099_) {
        int $$1 = p_14099_ % 360;
        if ($$1 >= 180) {
            $$1 -= 360;
        }
        if ($$1 < -180) {
            $$1 += 360;
        }
        return $$1;
    }

    public static float m_14177_(float p_14178_) {
        float $$1 = p_14178_ % 360.0f;
        if ($$1 >= 180.0f) {
            $$1 -= 360.0f;
        }
        if ($$1 < -180.0f) {
            $$1 += 360.0f;
        }
        return $$1;
    }

    public static double m_14175_(double p_14176_) {
        double $$1 = p_14176_ % 360.0;
        if ($$1 >= 180.0) {
            $$1 -= 360.0;
        }
        if ($$1 < -180.0) {
            $$1 += 360.0;
        }
        return $$1;
    }

    public static float m_14118_(float p_14119_, float p_14120_) {
        return Mth.m_14177_(p_14120_ - p_14119_);
    }

    public static float m_14145_(float p_14146_, float p_14147_) {
        return Mth.m_14154_(Mth.m_14118_(p_14146_, p_14147_));
    }

    public static float m_14094_(float p_14095_, float p_14096_, float p_14097_) {
        float $$3 = Mth.m_14118_(p_14095_, p_14096_);
        float $$4 = Mth.m_14036_($$3, -p_14097_, p_14097_);
        return p_14096_ - $$4;
    }

    public static float m_14121_(float p_14122_, float p_14123_, float p_14124_) {
        p_14124_ = Mth.m_14154_(p_14124_);
        if (p_14122_ < p_14123_) {
            return Mth.m_14036_(p_14122_ + p_14124_, p_14122_, p_14123_);
        }
        return Mth.m_14036_(p_14122_ - p_14124_, p_14123_, p_14122_);
    }

    public static float m_14148_(float p_14149_, float p_14150_, float p_14151_) {
        float $$3 = Mth.m_14118_(p_14149_, p_14150_);
        return Mth.m_14121_(p_14149_, p_14149_ + $$3, p_14151_);
    }

    public static int m_14059_(String p_14060_, int p_14061_) {
        return NumberUtils.toInt((String)p_14060_, (int)p_14061_);
    }

    public static int m_144905_(String p_144906_, int p_144907_, int p_144908_) {
        return Math.max(p_144908_, Mth.m_14059_(p_144906_, p_144907_));
    }

    public static double m_144898_(String p_144899_, double p_144900_) {
        try {
            return Double.parseDouble(p_144899_);
        }
        catch (Throwable $$2) {
            return p_144900_;
        }
    }

    public static double m_144901_(String p_144902_, double p_144903_, double p_144904_) {
        return Math.max(p_144904_, Mth.m_144898_(p_144902_, p_144903_));
    }

    public static int m_14125_(int p_14126_) {
        int $$1 = p_14126_ - 1;
        $$1 |= $$1 >> 1;
        $$1 |= $$1 >> 2;
        $$1 |= $$1 >> 4;
        $$1 |= $$1 >> 8;
        $$1 |= $$1 >> 16;
        return $$1 + 1;
    }

    public static boolean m_14152_(int p_14153_) {
        return p_14153_ != 0 && (p_14153_ & p_14153_ - 1) == 0;
    }

    public static int m_14163_(int p_14164_) {
        p_14164_ = Mth.m_14152_(p_14164_) ? p_14164_ : Mth.m_14125_(p_14164_);
        return f_13997_[(int)((long)p_14164_ * 125613361L >> 27) & 0x1F];
    }

    public static int m_14173_(int p_14174_) {
        return Mth.m_14163_(p_14174_) - (Mth.m_14152_(p_14174_) ? 0 : 1);
    }

    public static int m_14159_(float p_14160_, float p_14161_, float p_14162_) {
        return Mth.m_14103_(Mth.m_14143_(p_14160_ * 255.0f), Mth.m_14143_(p_14161_ * 255.0f), Mth.m_14143_(p_14162_ * 255.0f));
    }

    public static int m_14103_(int p_14104_, int p_14105_, int p_14106_) {
        int $$3 = p_14104_;
        $$3 = ($$3 << 8) + p_14105_;
        $$3 = ($$3 << 8) + p_14106_;
        return $$3;
    }

    public static int m_144932_(int p_144933_, int p_144934_) {
        int $$2 = (p_144933_ & 0xFF0000) >> 16;
        int $$3 = (p_144934_ & 0xFF0000) >> 16;
        int $$4 = (p_144933_ & 0xFF00) >> 8;
        int $$5 = (p_144934_ & 0xFF00) >> 8;
        int $$6 = (p_144933_ & 0xFF) >> 0;
        int $$7 = (p_144934_ & 0xFF) >> 0;
        int $$8 = (int)((float)$$2 * (float)$$3 / 255.0f);
        int $$9 = (int)((float)$$4 * (float)$$5 / 255.0f);
        int $$10 = (int)((float)$$6 * (float)$$7 / 255.0f);
        return p_144933_ & 0xFF000000 | $$8 << 16 | $$9 << 8 | $$10;
    }

    public static int m_144881_(int p_144882_, float p_144883_, float p_144884_, float p_144885_) {
        int $$4 = (p_144882_ & 0xFF0000) >> 16;
        int $$5 = (p_144882_ & 0xFF00) >> 8;
        int $$6 = (p_144882_ & 0xFF) >> 0;
        int $$7 = (int)((float)$$4 * p_144883_);
        int $$8 = (int)((float)$$5 * p_144884_);
        int $$9 = (int)((float)$$6 * p_144885_);
        return p_144882_ & 0xFF000000 | $$7 << 16 | $$8 << 8 | $$9;
    }

    public static float m_14187_(float p_14188_) {
        return p_14188_ - (float)Mth.m_14143_(p_14188_);
    }

    public static double m_14185_(double p_14186_) {
        return p_14186_ - (double)Mth.m_14134_(p_14186_);
    }

    public static Vec3 m_144892_(Vec3 p_144893_, Vec3 p_144894_, Vec3 p_144895_, Vec3 p_144896_, double p_144897_) {
        double $$5 = ((-p_144897_ + 2.0) * p_144897_ - 1.0) * p_144897_ * 0.5;
        double $$6 = ((3.0 * p_144897_ - 5.0) * p_144897_ * p_144897_ + 2.0) * 0.5;
        double $$7 = ((-3.0 * p_144897_ + 4.0) * p_144897_ + 1.0) * p_144897_ * 0.5;
        double $$8 = (p_144897_ - 1.0) * p_144897_ * p_144897_ * 0.5;
        return new Vec3(p_144893_.f_82479_ * $$5 + p_144894_.f_82479_ * $$6 + p_144895_.f_82479_ * $$7 + p_144896_.f_82479_ * $$8, p_144893_.f_82480_ * $$5 + p_144894_.f_82480_ * $$6 + p_144895_.f_82480_ * $$7 + p_144896_.f_82480_ * $$8, p_144893_.f_82481_ * $$5 + p_144894_.f_82481_ * $$6 + p_144895_.f_82481_ * $$7 + p_144896_.f_82481_ * $$8);
    }

    public static long m_14057_(Vec3i p_14058_) {
        return Mth.m_14130_(p_14058_.m_123341_(), p_14058_.m_123342_(), p_14058_.m_123343_());
    }

    public static long m_14130_(int p_14131_, int p_14132_, int p_14133_) {
        long $$3 = (long)(p_14131_ * 3129871) ^ (long)p_14133_ * 116129781L ^ (long)p_14132_;
        $$3 = $$3 * $$3 * 42317861L + $$3 * 11L;
        return $$3 >> 16;
    }

    public static UUID m_216261_(RandomSource p_216262_) {
        long $$1 = p_216262_.m_188505_() & 0xFFFFFFFFFFFF0FFFL | 0x4000L;
        long $$2 = p_216262_.m_188505_() & 0x3FFFFFFFFFFFFFFFL | Long.MIN_VALUE;
        return new UUID($$1, $$2);
    }

    public static UUID m_14002_() {
        return Mth.m_216261_(f_13996_);
    }

    public static double m_14112_(double p_14113_, double p_14114_, double p_14115_) {
        return (p_14113_ - p_14114_) / (p_14115_ - p_14114_);
    }

    public static float m_184655_(float p_184656_, float p_184657_, float p_184658_) {
        return (p_184656_ - p_184657_) / (p_184658_ - p_184657_);
    }

    public static boolean m_144888_(Vec3 p_144889_, Vec3 p_144890_, AABB p_144891_) {
        double $$3 = (p_144891_.f_82288_ + p_144891_.f_82291_) * 0.5;
        double $$4 = (p_144891_.f_82291_ - p_144891_.f_82288_) * 0.5;
        double $$5 = p_144889_.f_82479_ - $$3;
        if (Math.abs($$5) > $$4 && $$5 * p_144890_.f_82479_ >= 0.0) {
            return false;
        }
        double $$6 = (p_144891_.f_82289_ + p_144891_.f_82292_) * 0.5;
        double $$7 = (p_144891_.f_82292_ - p_144891_.f_82289_) * 0.5;
        double $$8 = p_144889_.f_82480_ - $$6;
        if (Math.abs($$8) > $$7 && $$8 * p_144890_.f_82480_ >= 0.0) {
            return false;
        }
        double $$9 = (p_144891_.f_82290_ + p_144891_.f_82293_) * 0.5;
        double $$10 = (p_144891_.f_82293_ - p_144891_.f_82290_) * 0.5;
        double $$11 = p_144889_.f_82481_ - $$9;
        if (Math.abs($$11) > $$10 && $$11 * p_144890_.f_82481_ >= 0.0) {
            return false;
        }
        double $$12 = Math.abs(p_144890_.f_82479_);
        double $$13 = Math.abs(p_144890_.f_82480_);
        double $$14 = Math.abs(p_144890_.f_82481_);
        double $$15 = p_144890_.f_82480_ * $$11 - p_144890_.f_82481_ * $$8;
        if (Math.abs($$15) > $$7 * $$14 + $$10 * $$13) {
            return false;
        }
        $$15 = p_144890_.f_82481_ * $$5 - p_144890_.f_82479_ * $$11;
        if (Math.abs($$15) > $$4 * $$14 + $$10 * $$12) {
            return false;
        }
        $$15 = p_144890_.f_82479_ * $$8 - p_144890_.f_82480_ * $$5;
        return Math.abs($$15) < $$4 * $$13 + $$7 * $$12;
    }

    public static double m_14136_(double p_14137_, double p_14138_) {
        boolean $$5;
        boolean $$4;
        boolean $$3;
        double $$2 = p_14138_ * p_14138_ + p_14137_ * p_14137_;
        if (Double.isNaN($$2)) {
            return Double.NaN;
        }
        boolean bl = $$3 = p_14137_ < 0.0;
        if ($$3) {
            p_14137_ = -p_14137_;
        }
        boolean bl2 = $$4 = p_14138_ < 0.0;
        if ($$4) {
            p_14138_ = -p_14138_;
        }
        boolean bl3 = $$5 = p_14137_ > p_14138_;
        if ($$5) {
            double $$6 = p_14138_;
            p_14138_ = p_14137_;
            p_14137_ = $$6;
        }
        double $$7 = Mth.m_14193_($$2);
        p_14138_ *= $$7;
        double $$8 = f_13998_ + (p_14137_ *= $$7);
        int $$9 = (int)Double.doubleToRawLongBits($$8);
        double $$10 = f_13999_[$$9];
        double $$11 = f_14000_[$$9];
        double $$12 = $$8 - f_13998_;
        double $$13 = p_14137_ * $$11 - p_14138_ * $$12;
        double $$14 = (6.0 + $$13 * $$13) * $$13 * 0.16666666666666666;
        double $$15 = $$10 + $$14;
        if ($$5) {
            $$15 = 1.5707963267948966 - $$15;
        }
        if ($$4) {
            $$15 = Math.PI - $$15;
        }
        if ($$3) {
            $$15 = -$$15;
        }
        return $$15;
    }

    public static float m_14195_(float p_14196_) {
        float $$1 = 0.5f * p_14196_;
        int $$2 = Float.floatToIntBits(p_14196_);
        $$2 = 1597463007 - ($$2 >> 1);
        p_14196_ = Float.intBitsToFloat($$2);
        p_14196_ *= 1.5f - $$1 * p_14196_ * p_14196_;
        return p_14196_;
    }

    public static double m_14193_(double p_14194_) {
        double $$1 = 0.5 * p_14194_;
        long $$2 = Double.doubleToRawLongBits(p_14194_);
        $$2 = 6910469410427058090L - ($$2 >> 1);
        p_14194_ = Double.longBitsToDouble($$2);
        p_14194_ *= 1.5 - $$1 * p_14194_ * p_14194_;
        return p_14194_;
    }

    public static float m_14199_(float p_14200_) {
        int $$1 = Float.floatToIntBits(p_14200_);
        $$1 = 1419967116 - $$1 / 3;
        float $$2 = Float.intBitsToFloat($$1);
        $$2 = 0.6666667f * $$2 + 1.0f / (3.0f * $$2 * $$2 * p_14200_);
        $$2 = 0.6666667f * $$2 + 1.0f / (3.0f * $$2 * $$2 * p_14200_);
        return $$2;
    }

    /*
     * WARNING - void declaration
     */
    public static int m_14169_(float p_14170_, float p_14171_, float p_14172_) {
        void $$28;
        void $$27;
        void $$26;
        int $$3 = (int)(p_14170_ * 6.0f) % 6;
        float $$4 = p_14170_ * 6.0f - (float)$$3;
        float $$5 = p_14172_ * (1.0f - p_14171_);
        float $$6 = p_14172_ * (1.0f - $$4 * p_14171_);
        float $$7 = p_14172_ * (1.0f - (1.0f - $$4) * p_14171_);
        switch ($$3) {
            case 0: {
                float $$8 = p_14172_;
                float $$9 = $$7;
                float $$10 = $$5;
                break;
            }
            case 1: {
                float $$11 = $$6;
                float $$12 = p_14172_;
                float $$13 = $$5;
                break;
            }
            case 2: {
                float $$14 = $$5;
                float $$15 = p_14172_;
                float $$16 = $$7;
                break;
            }
            case 3: {
                float $$17 = $$5;
                float $$18 = $$6;
                float $$19 = p_14172_;
                break;
            }
            case 4: {
                float $$20 = $$7;
                float $$21 = $$5;
                float $$22 = p_14172_;
                break;
            }
            case 5: {
                float $$23 = p_14172_;
                float $$24 = $$5;
                float $$25 = $$6;
                break;
            }
            default: {
                throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + p_14170_ + ", " + p_14171_ + ", " + p_14172_);
            }
        }
        int $$29 = Mth.m_14045_((int)($$26 * 255.0f), 0, 255);
        int $$30 = Mth.m_14045_((int)($$27 * 255.0f), 0, 255);
        int $$31 = Mth.m_14045_((int)($$28 * 255.0f), 0, 255);
        return $$29 << 16 | $$30 << 8 | $$31;
    }

    public static int m_14183_(int p_14184_) {
        p_14184_ ^= p_14184_ >>> 16;
        p_14184_ *= -2048144789;
        p_14184_ ^= p_14184_ >>> 13;
        p_14184_ *= -1028477387;
        p_14184_ ^= p_14184_ >>> 16;
        return p_14184_;
    }

    public static long m_144886_(long p_144887_) {
        p_144887_ ^= p_144887_ >>> 33;
        p_144887_ *= -49064778989728563L;
        p_144887_ ^= p_144887_ >>> 33;
        p_144887_ *= -4265267296055464877L;
        p_144887_ ^= p_144887_ >>> 33;
        return p_144887_;
    }

    public static double[] m_144912_(double ... p_144913_) {
        double $$1 = 0.0;
        for (double $$2 : p_144913_) {
            $$1 += $$2;
        }
        int $$3 = 0;
        while ($$3 < p_144913_.length) {
            int n = $$3++;
            p_144913_[n] = p_144913_[n] / $$1;
        }
        for (int $$4 = 0; $$4 < p_144913_.length; ++$$4) {
            p_144913_[$$4] = ($$4 == 0 ? 0.0 : p_144913_[$$4 - 1]) + p_144913_[$$4];
        }
        return p_144913_;
    }

    public static int m_216275_(RandomSource p_216276_, double[] p_216277_) {
        double $$2 = p_216276_.m_188500_();
        for (int $$3 = 0; $$3 < p_216277_.length; ++$$3) {
            if (!($$2 < p_216277_[$$3])) continue;
            return $$3;
        }
        return p_216277_.length;
    }

    public static double[] m_144866_(double p_144867_, double p_144868_, double p_144869_, int p_144870_, int p_144871_) {
        double[] $$5 = new double[p_144871_ - p_144870_ + 1];
        int $$6 = 0;
        for (int $$7 = p_144870_; $$7 <= p_144871_; ++$$7) {
            $$5[$$6] = Math.max(0.0, p_144867_ * StrictMath.exp(-((double)$$7 - p_144869_) * ((double)$$7 - p_144869_) / (2.0 * p_144868_ * p_144868_)));
            ++$$6;
        }
        return $$5;
    }

    public static double[] m_144857_(double p_144858_, double p_144859_, double p_144860_, double p_144861_, double p_144862_, double p_144863_, int p_144864_, int p_144865_) {
        double[] $$8 = new double[p_144865_ - p_144864_ + 1];
        int $$9 = 0;
        for (int $$10 = p_144864_; $$10 <= p_144865_; ++$$10) {
            $$8[$$9] = Math.max(0.0, p_144858_ * StrictMath.exp(-((double)$$10 - p_144860_) * ((double)$$10 - p_144860_) / (2.0 * p_144859_ * p_144859_)) + p_144861_ * StrictMath.exp(-((double)$$10 - p_144863_) * ((double)$$10 - p_144863_) / (2.0 * p_144862_ * p_144862_)));
            ++$$9;
        }
        return $$8;
    }

    public static double[] m_144872_(double p_144873_, double p_144874_, int p_144875_, int p_144876_) {
        double[] $$4 = new double[p_144876_ - p_144875_ + 1];
        int $$5 = 0;
        for (int $$6 = p_144875_; $$6 <= p_144876_; ++$$6) {
            $$4[$$5] = Math.max(p_144873_ * StrictMath.log($$6) + p_144874_, 0.0);
            ++$$5;
        }
        return $$4;
    }

    public static int m_14049_(int p_14050_, int p_14051_, IntPredicate p_14052_) {
        int $$3 = p_14051_ - p_14050_;
        while ($$3 > 0) {
            int $$4 = $$3 / 2;
            int $$5 = p_14050_ + $$4;
            if (p_14052_.test($$5)) {
                $$3 = $$4;
                continue;
            }
            p_14050_ = $$5 + 1;
            $$3 -= $$4 + 1;
        }
        return p_14050_;
    }

    public static float m_14179_(float p_14180_, float p_14181_, float p_14182_) {
        return p_14181_ + p_14180_ * (p_14182_ - p_14181_);
    }

    public static double m_14139_(double p_14140_, double p_14141_, double p_14142_) {
        return p_14141_ + p_14140_ * (p_14142_ - p_14141_);
    }

    public static double m_14012_(double p_14013_, double p_14014_, double p_14015_, double p_14016_, double p_14017_, double p_14018_) {
        return Mth.m_14139_(p_14014_, Mth.m_14139_(p_14013_, p_14015_, p_14016_), Mth.m_14139_(p_14013_, p_14017_, p_14018_));
    }

    public static double m_14019_(double p_14020_, double p_14021_, double p_14022_, double p_14023_, double p_14024_, double p_14025_, double p_14026_, double p_14027_, double p_14028_, double p_14029_, double p_14030_) {
        return Mth.m_14139_(p_14022_, Mth.m_14012_(p_14020_, p_14021_, p_14023_, p_14024_, p_14025_, p_14026_), Mth.m_14012_(p_14020_, p_14021_, p_14027_, p_14028_, p_14029_, p_14030_));
    }

    public static float m_216244_(float p_216245_, float p_216246_, float p_216247_, float p_216248_, float p_216249_) {
        return 0.5f * (2.0f * p_216247_ + (p_216248_ - p_216246_) * p_216245_ + (2.0f * p_216246_ - 5.0f * p_216247_ + 4.0f * p_216248_ - p_216249_) * p_216245_ * p_216245_ + (3.0f * p_216247_ - p_216246_ - 3.0f * p_216248_ + p_216249_) * p_216245_ * p_216245_ * p_216245_);
    }

    public static double m_14197_(double p_14198_) {
        return p_14198_ * p_14198_ * p_14198_ * (p_14198_ * (p_14198_ * 6.0 - 15.0) + 10.0);
    }

    public static double m_144946_(double p_144947_) {
        return 30.0 * p_144947_ * p_144947_ * (p_144947_ - 1.0) * (p_144947_ - 1.0);
    }

    public static int m_14205_(double p_14206_) {
        if (p_14206_ == 0.0) {
            return 0;
        }
        return p_14206_ > 0.0 ? 1 : -1;
    }

    public static float m_14189_(float p_14190_, float p_14191_, float p_14192_) {
        return p_14191_ + p_14190_ * Mth.m_14177_(p_14192_ - p_14191_);
    }

    public static float m_144948_(float p_144949_, float p_144950_, float p_144951_) {
        return Math.min(p_144949_ * p_144949_ * 0.6f + p_144950_ * p_144950_ * ((3.0f + p_144950_) / 4.0f) + p_144951_ * p_144951_ * 0.8f, 1.0f);
    }

    @Deprecated
    public static float m_14201_(float p_14202_, float p_14203_, float p_14204_) {
        float $$3;
        for ($$3 = p_14203_ - p_14202_; $$3 < -180.0f; $$3 += 360.0f) {
        }
        while ($$3 >= 180.0f) {
            $$3 -= 360.0f;
        }
        return p_14202_ + p_14204_ * $$3;
    }

    @Deprecated
    public static float m_14209_(double p_14210_) {
        while (p_14210_ >= 180.0) {
            p_14210_ -= 360.0;
        }
        while (p_14210_ < -180.0) {
            p_14210_ += 360.0;
        }
        return (float)p_14210_;
    }

    public static float m_14156_(float p_14157_, float p_14158_) {
        return (Math.abs(p_14157_ % p_14158_ - p_14158_ * 0.5f) - p_14158_ * 0.25f) / (p_14158_ * 0.25f);
    }

    public static float m_14207_(float p_14208_) {
        return p_14208_ * p_14208_;
    }

    public static double m_144952_(double p_144953_) {
        return p_144953_ * p_144953_;
    }

    public static int m_144944_(int p_144945_) {
        return p_144945_ * p_144945_;
    }

    public static long m_184643_(long p_184644_) {
        return p_184644_ * p_184644_;
    }

    public static float m_216299_(float p_216300_) {
        return p_216300_ * p_216300_ * p_216300_;
    }

    public static double m_144851_(double p_144852_, double p_144853_, double p_144854_, double p_144855_, double p_144856_) {
        return Mth.m_14085_(p_144855_, p_144856_, Mth.m_14112_(p_144852_, p_144853_, p_144854_));
    }

    public static float m_184631_(float p_184632_, float p_184633_, float p_184634_, float p_184635_, float p_184636_) {
        return Mth.m_144920_(p_184635_, p_184636_, Mth.m_184655_(p_184632_, p_184633_, p_184634_));
    }

    public static double m_144914_(double p_144915_, double p_144916_, double p_144917_, double p_144918_, double p_144919_) {
        return Mth.m_14139_(Mth.m_14112_(p_144915_, p_144916_, p_144917_), p_144918_, p_144919_);
    }

    public static float m_184637_(float p_184638_, float p_184639_, float p_184640_, float p_184641_, float p_184642_) {
        return Mth.m_14179_(Mth.m_184655_(p_184638_, p_184639_, p_184640_), p_184641_, p_184642_);
    }

    public static double m_144954_(double p_144955_) {
        return p_144955_ + (2.0 * RandomSource.m_216335_(Mth.m_14107_(p_144955_ * 3000.0)).m_188500_() - 1.0) * 1.0E-7 / 2.0;
    }

    public static int m_144941_(int p_144942_, int p_144943_) {
        return Mth.m_184652_(p_144942_, p_144943_) * p_144943_;
    }

    public static int m_184652_(int p_184653_, int p_184654_) {
        return -Math.floorDiv(-p_184653_, p_184654_);
    }

    public static int m_216287_(RandomSource p_216288_, int p_216289_, int p_216290_) {
        return p_216288_.m_188503_(p_216290_ - p_216289_ + 1) + p_216289_;
    }

    public static float m_216283_(RandomSource p_216284_, float p_216285_, float p_216286_) {
        return p_216284_.m_188501_() * (p_216286_ - p_216285_) + p_216285_;
    }

    public static float m_216291_(RandomSource p_216292_, float p_216293_, float p_216294_) {
        return p_216293_ + (float)p_216292_.m_188583_() * p_216294_;
    }

    public static double m_211589_(double p_211590_, double p_211591_) {
        return p_211590_ * p_211590_ + p_211591_ * p_211591_;
    }

    public static double m_184645_(double p_184646_, double p_184647_) {
        return Math.sqrt(Mth.m_211589_(p_184646_, p_184647_));
    }

    public static double m_211592_(double p_211593_, double p_211594_, double p_211595_) {
        return p_211593_ * p_211593_ + p_211594_ * p_211594_ + p_211595_ * p_211595_;
    }

    public static double m_184648_(double p_184649_, double p_184650_, double p_184651_) {
        return Math.sqrt(Mth.m_211592_(p_184649_, p_184650_, p_184651_));
    }

    public static int m_184628_(double p_184629_, int p_184630_) {
        return Mth.m_14107_(p_184629_ / (double)p_184630_) * p_184630_;
    }

    public static IntStream m_216295_(int p_216296_, int p_216297_, int p_216298_) {
        return Mth.m_216250_(p_216296_, p_216297_, p_216298_, 1);
    }

    public static IntStream m_216250_(int p_216251_, int p_216252_, int p_216253_, int p_216254_) {
        if (p_216252_ > p_216253_) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "upperbound %d expected to be > lowerBound %d", p_216253_, p_216252_));
        }
        if (p_216254_ < 1) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "steps expected to be >= 1, was %d", p_216254_));
        }
        if (p_216251_ < p_216252_ || p_216251_ > p_216253_) {
            return IntStream.empty();
        }
        return IntStream.iterate(p_216251_, p_216282_ -> {
            int $$4 = Math.abs(p_216251_ - p_216282_);
            return p_216251_ - $$4 >= p_216252_ || p_216251_ + $$4 <= p_216253_;
        }, p_216260_ -> {
            int $$8;
            boolean $$7;
            boolean $$5 = p_216260_ <= p_216251_;
            int $$6 = Math.abs(p_216251_ - p_216260_);
            boolean bl = $$7 = p_216251_ + $$6 + p_216254_ <= p_216253_;
            if (!($$5 && $$7 || ($$8 = p_216251_ - $$6 - ($$5 ? p_216254_ : 0)) < p_216252_)) {
                return $$8;
            }
            return p_216251_ + $$6 + p_216254_;
        });
    }

    static {
        for (int $$0 = 0; $$0 < 257; ++$$0) {
            double $$1 = (double)$$0 / 256.0;
            double $$2 = Math.asin($$1);
            Mth.f_14000_[$$0] = Math.cos($$2);
            Mth.f_13999_[$$0] = $$2;
        }
    }
}

