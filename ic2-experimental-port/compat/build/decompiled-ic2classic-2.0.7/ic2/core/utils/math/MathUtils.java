/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.utils.math;

import ic2.core.utils.helpers.Formatters;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.List;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class MathUtils {
    static final float DIVIDER = 0.003921569f;
    public static final BiFunction<Integer, Integer, Integer> REMOVAL = MathUtils::merge;

    static Integer merge(Integer key, Integer value) {
        int result = key - value;
        return result <= 0 ? null : Integer.valueOf(result);
    }

    public static int[] fromTo(int from, int to) {
        int[] array = new int[to - from];
        for (int i = 0; i < array.length; ++i) {
            array[i] = i + from;
        }
        return array;
    }

    public static IntList fromToList(int from, int to) {
        IntArrayList list = new IntArrayList(to - from);
        int m = to - from;
        for (int i = 0; i < m; ++i) {
            list.add(from + i);
        }
        return list;
    }

    public static int sumUp(int base) {
        int sum = 0;
        for (int i = 1; i <= base; ++i) {
            sum += i;
        }
        return sum;
    }

    public static <T> void shuffle(List<T> p_214674_, RandomSource p_214675_) {
        int i;
        for (int j = i = p_214674_.size(); j > 1; --j) {
            int k = p_214675_.m_188503_(j);
            p_214674_.set(j - 1, p_214674_.set(k, p_214674_.get(j - 1)));
        }
    }

    public static Vec3 divide(Vec3 source, double divider) {
        return new Vec3(source.f_82479_ / divider, source.f_82480_ / divider, source.f_82481_ / divider);
    }

    public static BlockPos divide(BlockPos source, double divider) {
        return new BlockPos((double)source.m_123341_() / divider, (double)source.m_123342_() / divider, (double)source.m_123343_() / divider);
    }

    public static Vec3 clamp(Vec3 source, double min, double max) {
        return new Vec3(Mth.m_14008_((double)source.f_82479_, (double)min, (double)max), Mth.m_14008_((double)source.f_82480_, (double)min, (double)max), Mth.m_14008_((double)source.f_82481_, (double)min, (double)max));
    }

    public static long putIntoLong(long key, long value) {
        return key << 32 | value & Integer.MAX_VALUE;
    }

    public static int getIntKey(long input) {
        return (int)(input >> 32 & Integer.MAX_VALUE);
    }

    public static int getIntValue(long input) {
        return (int)(input & Integer.MAX_VALUE);
    }

    public static int putInt(int key, int value) {
        return key << 16 | value & Short.MAX_VALUE;
    }

    public static int getShortKey(int input) {
        return input >> 16 & Short.MAX_VALUE;
    }

    public static int getShortValue(int input) {
        return input & Short.MAX_VALUE;
    }

    public static int sign(int value) {
        return Integer.signum(value);
    }

    public static int sign(long value) {
        return Long.signum(value);
    }

    public static double easeInBack(double input) {
        double c1 = 1.70158;
        double c3 = c1 + 1.0;
        return c3 * input * input * input - c1 * input * input;
    }

    public static boolean isHexNumber(String s) {
        if (s.isEmpty()) {
            return true;
        }
        int m = s.length();
        for (int i = 0; i < m; ++i) {
            char charIn = s.charAt(i);
            if (charIn >= '0' && charIn <= '9' || charIn >= 'a' && charIn <= 'f' || charIn >= 'A' && charIn <= 'F') continue;
            return false;
        }
        return true;
    }

    public static String toLagStringProbe(String prefix, long lag) {
        return prefix + ": [ms=" + Formatters.EU_READER_FORMAT.format(lag / 1000000L) + ", qs=" + Formatters.EU_READER_FORMAT.format(lag / 1000L) + ", ns=" + Formatters.EU_READER_FORMAT.format(lag) + "]";
    }

    public static String toLagString(String prefix, long lag) {
        return prefix + ": " + lag / 1000000L + "ms, " + lag / 1000L + "qs, " + lag + "ns";
    }
}

