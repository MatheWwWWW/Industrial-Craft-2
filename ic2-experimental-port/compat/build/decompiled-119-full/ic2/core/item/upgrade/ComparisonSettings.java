/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.item.upgrade;

import java.util.Locale;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public enum ComparisonSettings {
    LESS_OR_EQUAL("<="){

        @Override
        public boolean compare(int n, int n2) {
            return n <= n2;
        }
    }
    ,
    LESS("<"){

        @Override
        public boolean compare(int n, int n2) {
            return n < n2;
        }
    }
    ,
    GREATER(">"){

        @Override
        public boolean compare(int n, int n2) {
            return n > n2;
        }
    }
    ,
    GREATER_OR_EQUAL(">="){

        @Override
        public boolean compare(int n, int n2) {
            return n >= n2;
        }
    };

    final String symbol;
    final String name = "ic2.upgrade.advancedGUI." + this.name().toLowerCase(Locale.ENGLISH);
    public static final ComparisonSettings DEFAULT;
    public static final ComparisonSettings[] VALUES;

    private ComparisonSettings(String string2) {
        this.symbol = string2;
    }

    public abstract boolean compare(int var1, int var2);

    public byte getForNBT() {
        return (byte)this.ordinal();
    }

    public static ComparisonSettings getFromNBT(byte by) {
        return VALUES[by];
    }

    static {
        DEFAULT = LESS;
        VALUES = ComparisonSettings.values();
    }
}

