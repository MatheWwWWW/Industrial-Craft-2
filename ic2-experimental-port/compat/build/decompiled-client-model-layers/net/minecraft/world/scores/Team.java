/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.scores;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public abstract class Team {
    public boolean m_83536_(@Nullable Team p_83537_) {
        if (p_83537_ == null) {
            return false;
        }
        return this == p_83537_;
    }

    public abstract String m_5758_();

    public abstract MutableComponent m_6870_(Component var1);

    public abstract boolean m_6259_();

    public abstract boolean m_6260_();

    public abstract Visibility m_7470_();

    public abstract ChatFormatting m_7414_();

    public abstract Collection<String> m_6809_();

    public abstract Visibility m_7468_();

    public abstract CollisionRule m_7156_();

    public static final class CollisionRule
    extends Enum<CollisionRule> {
        public static final /* enum */ CollisionRule ALWAYS = new CollisionRule("always", 0);
        public static final /* enum */ CollisionRule NEVER = new CollisionRule("never", 1);
        public static final /* enum */ CollisionRule PUSH_OTHER_TEAMS = new CollisionRule("pushOtherTeams", 2);
        public static final /* enum */ CollisionRule PUSH_OWN_TEAM = new CollisionRule("pushOwnTeam", 3);
        private static final Map<String, CollisionRule> f_83545_;
        public final String f_83543_;
        public final int f_83544_;
        private static final /* synthetic */ CollisionRule[] $VALUES;

        public static CollisionRule[] values() {
            return (CollisionRule[])$VALUES.clone();
        }

        public static CollisionRule valueOf(String p_83561_) {
            return Enum.valueOf(CollisionRule.class, p_83561_);
        }

        @Nullable
        public static CollisionRule m_83555_(String p_83556_) {
            return f_83545_.get(p_83556_);
        }

        private CollisionRule(String p_83551_, int p_83552_) {
            this.f_83543_ = p_83551_;
            this.f_83544_ = p_83552_;
        }

        public Component m_83557_() {
            return Component.m_237115_("team.collision." + this.f_83543_);
        }

        private static /* synthetic */ CollisionRule[] m_166104_() {
            return new CollisionRule[]{ALWAYS, NEVER, PUSH_OTHER_TEAMS, PUSH_OWN_TEAM};
        }

        static {
            $VALUES = CollisionRule.m_166104_();
            f_83545_ = Arrays.stream(CollisionRule.values()).collect(Collectors.toMap(p_83559_ -> p_83559_.f_83543_, p_83554_ -> p_83554_));
        }
    }

    public static final class Visibility
    extends Enum<Visibility> {
        public static final /* enum */ Visibility ALWAYS = new Visibility("always", 0);
        public static final /* enum */ Visibility NEVER = new Visibility("never", 1);
        public static final /* enum */ Visibility HIDE_FOR_OTHER_TEAMS = new Visibility("hideForOtherTeams", 2);
        public static final /* enum */ Visibility HIDE_FOR_OWN_TEAM = new Visibility("hideForOwnTeam", 3);
        private static final Map<String, Visibility> f_83569_;
        public final String f_83567_;
        public final int f_83568_;
        private static final /* synthetic */ Visibility[] $VALUES;

        public static Visibility[] values() {
            return (Visibility[])$VALUES.clone();
        }

        public static Visibility valueOf(String p_83585_) {
            return Enum.valueOf(Visibility.class, p_83585_);
        }

        public static String[] m_166105_() {
            return f_83569_.keySet().toArray(new String[0]);
        }

        @Nullable
        public static Visibility m_83579_(String p_83580_) {
            return f_83569_.get(p_83580_);
        }

        private Visibility(String p_83575_, int p_83576_) {
            this.f_83567_ = p_83575_;
            this.f_83568_ = p_83576_;
        }

        public Component m_83581_() {
            return Component.m_237115_("team.visibility." + this.f_83567_);
        }

        private static /* synthetic */ Visibility[] m_166106_() {
            return new Visibility[]{ALWAYS, NEVER, HIDE_FOR_OTHER_TEAMS, HIDE_FOR_OWN_TEAM};
        }

        static {
            $VALUES = Visibility.m_166106_();
            f_83569_ = Arrays.stream(Visibility.values()).collect(Collectors.toMap(p_83583_ -> p_83583_.f_83567_, p_83578_ -> p_83578_));
        }
    }
}

