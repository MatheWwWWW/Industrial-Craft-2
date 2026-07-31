/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.network.buffer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface NetworkInfo {
    public BitLevel value() default BitLevel.BIT_0;

    public static enum BitLevel {
        BIT_0(0),
        BIT_8(1),
        BIT_16(2),
        BIT_24(3),
        BIT_32(4),
        BIT_40(5),
        BIT_48(6),
        BIT_56(7),
        BIT_64(8);

        final int index;
        final long maxNumber;
        public static final BitLevel[] LEVELS;

        private BitLevel(int lvl) {
            this.index = lvl;
            this.maxNumber = 1L << lvl * 8;
        }

        public int getIndex() {
            return this.index;
        }

        public boolean isSkip() {
            return this.index == 0;
        }

        public boolean isValid(BitLevel limit) {
            if (this.index == 0) {
                return false;
            }
            return this.index < limit.index;
        }

        public long limitNumber(long input) {
            if (this.index == 0) {
                return 0L;
            }
            if (input >= this.maxNumber) {
                return this.maxNumber - 1L;
            }
            return input;
        }

        public static BitLevel getLevel(int index) {
            if (index < 0 || index > 8) {
                return BIT_32;
            }
            return LEVELS[index];
        }

        static {
            LEVELS = new BitLevel[BitLevel.values().length];
            BitLevel[] bitLevelArray = BitLevel.values();
            int n = bitLevelArray.length;
            for (int i = 0; i < n; ++i) {
                BitLevel level;
                BitLevel.LEVELS[level.getIndex()] = level = bitLevelArray[i];
            }
        }
    }
}

