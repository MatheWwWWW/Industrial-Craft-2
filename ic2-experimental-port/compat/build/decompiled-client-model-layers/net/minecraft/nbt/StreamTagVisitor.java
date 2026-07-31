/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.nbt;

import net.minecraft.nbt.TagType;

public interface StreamTagVisitor {
    public ValueResult m_196525_();

    public ValueResult m_196458_(String var1);

    public ValueResult m_196209_(byte var1);

    public ValueResult m_196553_(short var1);

    public ValueResult m_196353_(int var1);

    public ValueResult m_196295_(long var1);

    public ValueResult m_196532_(float var1);

    public ValueResult m_196455_(double var1);

    public ValueResult m_196152_(byte[] var1);

    public ValueResult m_196376_(int[] var1);

    public ValueResult m_196280_(long[] var1);

    public ValueResult m_196339_(TagType<?> var1, int var2);

    public EntryResult m_196214_(TagType<?> var1);

    public EntryResult m_196425_(TagType<?> var1, String var2);

    public EntryResult m_196338_(TagType<?> var1, int var2);

    public ValueResult m_196527_();

    public ValueResult m_196213_(TagType<?> var1);

    public static final class EntryResult
    extends Enum<EntryResult> {
        public static final /* enum */ EntryResult ENTER = new EntryResult();
        public static final /* enum */ EntryResult SKIP = new EntryResult();
        public static final /* enum */ EntryResult BREAK = new EntryResult();
        public static final /* enum */ EntryResult HALT = new EntryResult();
        private static final /* synthetic */ EntryResult[] $VALUES;

        public static EntryResult[] values() {
            return (EntryResult[])$VALUES.clone();
        }

        public static EntryResult valueOf(String p_197549_) {
            return Enum.valueOf(EntryResult.class, p_197549_);
        }

        private static /* synthetic */ EntryResult[] m_197547_() {
            return new EntryResult[]{ENTER, SKIP, BREAK, HALT};
        }

        static {
            $VALUES = EntryResult.m_197547_();
        }
    }

    public static final class ValueResult
    extends Enum<ValueResult> {
        public static final /* enum */ ValueResult CONTINUE = new ValueResult();
        public static final /* enum */ ValueResult BREAK = new ValueResult();
        public static final /* enum */ ValueResult HALT = new ValueResult();
        private static final /* synthetic */ ValueResult[] $VALUES;

        public static ValueResult[] values() {
            return (ValueResult[])$VALUES.clone();
        }

        public static ValueResult valueOf(String p_197561_) {
            return Enum.valueOf(ValueResult.class, p_197561_);
        }

        private static /* synthetic */ ValueResult[] m_197559_() {
            return new ValueResult[]{CONTINUE, BREAK, HALT};
        }

        static {
            $VALUES = ValueResult.m_197559_();
        }
    }
}

