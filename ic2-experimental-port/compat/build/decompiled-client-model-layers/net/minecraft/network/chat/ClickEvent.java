/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.chat;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ClickEvent {
    private final Action f_130617_;
    private final String f_130618_;

    public ClickEvent(Action p_130620_, String p_130621_) {
        this.f_130617_ = p_130620_;
        this.f_130618_ = p_130621_;
    }

    public Action m_130622_() {
        return this.f_130617_;
    }

    public String m_130623_() {
        return this.f_130618_;
    }

    public boolean equals(Object p_130625_) {
        if (this == p_130625_) {
            return true;
        }
        if (p_130625_ == null || this.getClass() != p_130625_.getClass()) {
            return false;
        }
        ClickEvent $$1 = (ClickEvent)p_130625_;
        if (this.f_130617_ != $$1.f_130617_) {
            return false;
        }
        return !(this.f_130618_ != null ? !this.f_130618_.equals($$1.f_130618_) : $$1.f_130618_ != null);
    }

    public String toString() {
        return "ClickEvent{action=" + this.f_130617_ + ", value='" + this.f_130618_ + "'}";
    }

    public int hashCode() {
        int $$0 = this.f_130617_.hashCode();
        $$0 = 31 * $$0 + (this.f_130618_ != null ? this.f_130618_.hashCode() : 0);
        return $$0;
    }

    public static final class Action
    extends Enum<Action> {
        public static final /* enum */ Action OPEN_URL = new Action("open_url", true);
        public static final /* enum */ Action OPEN_FILE = new Action("open_file", false);
        public static final /* enum */ Action RUN_COMMAND = new Action("run_command", true);
        public static final /* enum */ Action SUGGEST_COMMAND = new Action("suggest_command", true);
        public static final /* enum */ Action CHANGE_PAGE = new Action("change_page", true);
        public static final /* enum */ Action COPY_TO_CLIPBOARD = new Action("copy_to_clipboard", true);
        private static final Map<String, Action> f_130634_;
        private final boolean f_130635_;
        private final String f_130636_;
        private static final /* synthetic */ Action[] $VALUES;

        public static Action[] values() {
            return (Action[])$VALUES.clone();
        }

        public static Action valueOf(String p_130651_) {
            return Enum.valueOf(Action.class, p_130651_);
        }

        private Action(String p_130642_, boolean p_130643_) {
            this.f_130636_ = p_130642_;
            this.f_130635_ = p_130643_;
        }

        public boolean m_130644_() {
            return this.f_130635_;
        }

        public String m_130649_() {
            return this.f_130636_;
        }

        public static Action m_130645_(String p_130646_) {
            return f_130634_.get(p_130646_);
        }

        private static /* synthetic */ Action[] m_178387_() {
            return new Action[]{OPEN_URL, OPEN_FILE, RUN_COMMAND, SUGGEST_COMMAND, CHANGE_PAGE, COPY_TO_CLIPBOARD};
        }

        static {
            $VALUES = Action.m_178387_();
            f_130634_ = Arrays.stream(Action.values()).collect(Collectors.toMap(Action::m_130649_, p_130648_ -> p_130648_));
        }
    }
}

