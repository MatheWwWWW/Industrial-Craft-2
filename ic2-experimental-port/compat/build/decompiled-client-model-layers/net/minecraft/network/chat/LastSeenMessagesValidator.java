/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.network.chat.LastSeenMessages;

public class LastSeenMessagesValidator {
    private static final int f_241669_ = Integer.MIN_VALUE;
    private LastSeenMessages f_241607_ = LastSeenMessages.f_241634_;
    private final ObjectList<LastSeenMessages.Entry> f_241651_ = new ObjectArrayList();

    public void m_241796_(LastSeenMessages.Entry p_242384_) {
        this.f_241651_.add((Object)p_242384_);
    }

    public int m_241920_() {
        return this.f_241651_.size();
    }

    private boolean m_241852_(LastSeenMessages p_242422_) {
        HashSet<UUID> $$1 = new HashSet<UUID>(p_242422_.f_241630_().size());
        for (LastSeenMessages.Entry $$2 : p_242422_.f_241630_()) {
            if ($$1.add($$2.f_241648_())) continue;
            return true;
        }
        return false;
    }

    private int m_241897_(List<LastSeenMessages.Entry> p_242209_, int[] p_242285_, @Nullable LastSeenMessages.Entry p_242264_) {
        Arrays.fill(p_242285_, Integer.MIN_VALUE);
        List<LastSeenMessages.Entry> $$3 = this.f_241607_.f_241630_();
        int $$4 = $$3.size();
        for (int $$5 = $$4 - 1; $$5 >= 0; --$$5) {
            int $$6 = p_242209_.indexOf($$3.get($$5));
            if ($$6 == -1) continue;
            p_242285_[$$6] = -$$5 - 1;
        }
        int $$7 = Integer.MIN_VALUE;
        int $$8 = this.f_241651_.size();
        for (int $$9 = 0; $$9 < $$8; ++$$9) {
            LastSeenMessages.Entry $$10 = (LastSeenMessages.Entry)this.f_241651_.get($$9);
            int $$11 = p_242209_.indexOf($$10);
            if ($$11 != -1) {
                p_242285_[$$11] = $$9;
            }
            if (!$$10.equals(p_242264_)) continue;
            $$7 = $$9;
        }
        return $$7;
    }

    public Set<ErrorCondition> m_241869_(LastSeenMessages.Update p_242403_) {
        EnumSet<ErrorCondition> $$1 = EnumSet.noneOf(ErrorCondition.class);
        LastSeenMessages $$2 = p_242403_.f_241678_();
        LastSeenMessages.Entry $$3 = p_242403_.f_241661_().orElse(null);
        List<LastSeenMessages.Entry> $$4 = $$2.f_241630_();
        int $$5 = this.f_241607_.f_241630_().size();
        int $$6 = Integer.MIN_VALUE;
        int $$7 = $$4.size();
        if ($$7 < $$5) {
            $$1.add(ErrorCondition.REMOVED_MESSAGES);
        }
        int[] $$8 = new int[$$7];
        int $$9 = this.m_241897_($$4, $$8, $$3);
        for (int $$10 = $$7 - 1; $$10 >= 0; --$$10) {
            int $$11 = $$8[$$10];
            if ($$11 != Integer.MIN_VALUE) {
                if ($$11 < $$6) {
                    $$1.add(ErrorCondition.OUT_OF_ORDER);
                    continue;
                }
                $$6 = $$11;
                continue;
            }
            $$1.add(ErrorCondition.UNKNOWN_MESSAGES);
        }
        if ($$3 != null) {
            if ($$9 == Integer.MIN_VALUE || $$9 < $$6) {
                $$1.add(ErrorCondition.UNKNOWN_MESSAGES);
            } else {
                $$6 = $$9;
            }
        }
        if ($$6 >= 0) {
            this.f_241651_.removeElements(0, $$6 + 1);
        }
        if (this.m_241852_($$2)) {
            $$1.add(ErrorCondition.DUPLICATED_PROFILES);
        }
        this.f_241607_ = $$2;
        return $$1;
    }

    public static final class ErrorCondition
    extends Enum<ErrorCondition> {
        public static final /* enum */ ErrorCondition OUT_OF_ORDER = new ErrorCondition("messages received out of order");
        public static final /* enum */ ErrorCondition DUPLICATED_PROFILES = new ErrorCondition("multiple entries for single profile");
        public static final /* enum */ ErrorCondition UNKNOWN_MESSAGES = new ErrorCondition("unknown message");
        public static final /* enum */ ErrorCondition REMOVED_MESSAGES = new ErrorCondition("previously present messages removed from context");
        private final String f_241620_;
        private static final /* synthetic */ ErrorCondition[] $VALUES;

        public static ErrorCondition[] values() {
            return (ErrorCondition[])$VALUES.clone();
        }

        public static ErrorCondition valueOf(String p_242360_) {
            return Enum.valueOf(ErrorCondition.class, p_242360_);
        }

        private ErrorCondition(String p_242324_) {
            this.f_241620_ = p_242324_;
        }

        public String m_241951_() {
            return this.f_241620_;
        }

        private static /* synthetic */ ErrorCondition[] m_241886_() {
            return new ErrorCondition[]{OUT_OF_ORDER, DUPLICATED_PROFILES, UNKNOWN_MESSAGES, REMOVED_MESSAGES};
        }

        static {
            $VALUES = ErrorCondition.m_241886_();
        }
    }
}

