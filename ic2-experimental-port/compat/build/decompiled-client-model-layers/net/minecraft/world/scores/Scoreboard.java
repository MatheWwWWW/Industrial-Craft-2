/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.scores;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.slf4j.Logger;

public class Scoreboard {
    private static final Logger f_199933_ = LogUtils.getLogger();
    public static final int f_166087_ = 0;
    public static final int f_166088_ = 1;
    public static final int f_166089_ = 2;
    public static final int f_166090_ = 3;
    public static final int f_166091_ = 18;
    public static final int f_166092_ = 19;
    private final Map<String, Objective> f_83408_ = Maps.newHashMap();
    private final Map<ObjectiveCriteria, List<Objective>> f_83409_ = Maps.newHashMap();
    private final Map<String, Map<Objective, Score>> f_83410_ = Maps.newHashMap();
    private final Objective[] f_83411_ = new Objective[19];
    private final Map<String, PlayerTeam> f_83412_ = Maps.newHashMap();
    private final Map<String, PlayerTeam> f_83413_ = Maps.newHashMap();
    @Nullable
    private static String[] f_83414_;

    public boolean m_83459_(String p_83460_) {
        return this.f_83408_.containsKey(p_83460_);
    }

    public Objective m_83469_(String p_83470_) {
        return this.f_83408_.get(p_83470_);
    }

    @Nullable
    public Objective m_83477_(@Nullable String p_83478_) {
        return this.f_83408_.get(p_83478_);
    }

    public Objective m_83436_(String p_83437_, ObjectiveCriteria p_83438_, Component p_83439_, ObjectiveCriteria.RenderType p_83440_) {
        if (this.f_83408_.containsKey(p_83437_)) {
            throw new IllegalArgumentException("An objective with the name '" + p_83437_ + "' already exists!");
        }
        Objective $$4 = new Objective(this, p_83437_, p_83438_, p_83439_, p_83440_);
        this.f_83409_.computeIfAbsent(p_83438_, p_83426_ -> Lists.newArrayList()).add($$4);
        this.f_83408_.put(p_83437_, $$4);
        this.m_7092_($$4);
        return $$4;
    }

    public final void m_83427_(ObjectiveCriteria p_83428_, String p_83429_, Consumer<Score> p_83430_) {
        this.f_83409_.getOrDefault(p_83428_, Collections.emptyList()).forEach(p_83444_ -> p_83430_.accept(this.m_83471_(p_83429_, (Objective)p_83444_)));
    }

    public boolean m_83461_(String p_83462_, Objective p_83463_) {
        Map<Objective, Score> $$2 = this.f_83410_.get(p_83462_);
        if ($$2 == null) {
            return false;
        }
        Score $$3 = $$2.get(p_83463_);
        return $$3 != null;
    }

    public Score m_83471_(String p_83472_, Objective p_83473_) {
        Map $$2 = this.f_83410_.computeIfAbsent(p_83472_, p_83507_ -> Maps.newHashMap());
        return $$2.computeIfAbsent(p_83473_, p_83487_ -> {
            Score $$2 = new Score(this, (Objective)p_83487_, p_83472_);
            $$2.m_83402_(0);
            return $$2;
        });
    }

    public Collection<Score> m_83498_(Objective p_83499_) {
        ArrayList $$1 = Lists.newArrayList();
        for (Map<Objective, Score> $$2 : this.f_83410_.values()) {
            Score $$3 = $$2.get(p_83499_);
            if ($$3 == null) continue;
            $$1.add($$3);
        }
        $$1.sort(Score.f_83380_);
        return $$1;
    }

    public Collection<Objective> m_83466_() {
        return this.f_83408_.values();
    }

    public Collection<String> m_83474_() {
        return this.f_83408_.keySet();
    }

    public Collection<String> m_83482_() {
        return Lists.newArrayList(this.f_83410_.keySet());
    }

    public void m_83479_(String p_83480_, @Nullable Objective p_83481_) {
        if (p_83481_ == null) {
            Map<Objective, Score> $$2 = this.f_83410_.remove(p_83480_);
            if ($$2 != null) {
                this.m_7182_(p_83480_);
            }
        } else {
            Map<Objective, Score> $$3 = this.f_83410_.get(p_83480_);
            if ($$3 != null) {
                Score $$4 = $$3.remove(p_83481_);
                if ($$3.size() < 1) {
                    Map<Objective, Score> $$5 = this.f_83410_.remove(p_83480_);
                    if ($$5 != null) {
                        this.m_7182_(p_83480_);
                    }
                } else if ($$4 != null) {
                    this.m_5973_(p_83480_, p_83481_);
                }
            }
        }
    }

    public Map<Objective, Score> m_83483_(String p_83484_) {
        HashMap $$1 = this.f_83410_.get(p_83484_);
        if ($$1 == null) {
            $$1 = Maps.newHashMap();
        }
        return $$1;
    }

    public void m_83502_(Objective p_83503_) {
        this.f_83408_.remove(p_83503_.m_83320_());
        for (int $$1 = 0; $$1 < 19; ++$$1) {
            if (this.m_83416_($$1) != p_83503_) continue;
            this.m_7136_($$1, null);
        }
        List<Objective> $$2 = this.f_83409_.get(p_83503_.m_83321_());
        if ($$2 != null) {
            $$2.remove(p_83503_);
        }
        for (Map<Objective, Score> $$3 : this.f_83410_.values()) {
            $$3.remove(p_83503_);
        }
        this.m_7093_(p_83503_);
    }

    public void m_7136_(int p_83418_, @Nullable Objective p_83419_) {
        this.f_83411_[p_83418_] = p_83419_;
    }

    @Nullable
    public Objective m_83416_(int p_83417_) {
        return this.f_83411_[p_83417_];
    }

    @Nullable
    public PlayerTeam m_83489_(String p_83490_) {
        return this.f_83412_.get(p_83490_);
    }

    public PlayerTeam m_83492_(String p_83493_) {
        PlayerTeam $$1 = this.m_83489_(p_83493_);
        if ($$1 != null) {
            f_199933_.warn("Requested creation of existing team '{}'", (Object)p_83493_);
            return $$1;
        }
        $$1 = new PlayerTeam(this, p_83493_);
        this.f_83412_.put(p_83493_, $$1);
        this.m_7650_($$1);
        return $$1;
    }

    public void m_83475_(PlayerTeam p_83476_) {
        this.f_83412_.remove(p_83476_.m_5758_());
        for (String $$1 : p_83476_.m_6809_()) {
            this.f_83413_.remove($$1);
        }
        this.m_7644_(p_83476_);
    }

    public boolean m_6546_(String p_83434_, PlayerTeam p_83435_) {
        if (this.m_83500_(p_83434_) != null) {
            this.m_83495_(p_83434_);
        }
        this.f_83413_.put(p_83434_, p_83435_);
        return p_83435_.m_6809_().add(p_83434_);
    }

    public boolean m_83495_(String p_83496_) {
        PlayerTeam $$1 = this.m_83500_(p_83496_);
        if ($$1 != null) {
            this.m_6519_(p_83496_, $$1);
            return true;
        }
        return false;
    }

    public void m_6519_(String p_83464_, PlayerTeam p_83465_) {
        if (this.m_83500_(p_83464_) != p_83465_) {
            throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + p_83465_.m_5758_() + "'.");
        }
        this.f_83413_.remove(p_83464_);
        p_83465_.m_6809_().remove(p_83464_);
    }

    public Collection<String> m_83488_() {
        return this.f_83412_.keySet();
    }

    public Collection<PlayerTeam> m_83491_() {
        return this.f_83412_.values();
    }

    @Nullable
    public PlayerTeam m_83500_(String p_83501_) {
        return this.f_83413_.get(p_83501_);
    }

    public void m_7092_(Objective p_83422_) {
    }

    public void m_7091_(Objective p_83455_) {
    }

    public void m_7093_(Objective p_83467_) {
    }

    public void m_5734_(Score p_83424_) {
    }

    public void m_7182_(String p_83431_) {
    }

    public void m_5973_(String p_83432_, Objective p_83433_) {
    }

    public void m_7650_(PlayerTeam p_83423_) {
    }

    public void m_7645_(PlayerTeam p_83456_) {
    }

    public void m_7644_(PlayerTeam p_83468_) {
    }

    public static String m_83453_(int p_83454_) {
        ChatFormatting $$1;
        switch (p_83454_) {
            case 0: {
                return "list";
            }
            case 1: {
                return "sidebar";
            }
            case 2: {
                return "belowName";
            }
        }
        if (p_83454_ >= 3 && p_83454_ <= 18 && ($$1 = ChatFormatting.m_126647_(p_83454_ - 3)) != null && $$1 != ChatFormatting.RESET) {
            return "sidebar.team." + $$1.m_126666_();
        }
        return null;
    }

    public static int m_83504_(String p_83505_) {
        String $$1;
        ChatFormatting $$2;
        if ("list".equalsIgnoreCase(p_83505_)) {
            return 0;
        }
        if ("sidebar".equalsIgnoreCase(p_83505_)) {
            return 1;
        }
        if ("belowName".equalsIgnoreCase(p_83505_)) {
            return 2;
        }
        if (p_83505_.startsWith("sidebar.team.") && ($$2 = ChatFormatting.m_126657_($$1 = p_83505_.substring("sidebar.team.".length()))) != null && $$2.m_126656_() >= 0) {
            return $$2.m_126656_() + 3;
        }
        return -1;
    }

    public static String[] m_83494_() {
        if (f_83414_ == null) {
            f_83414_ = new String[19];
            for (int $$0 = 0; $$0 < 19; ++$$0) {
                Scoreboard.f_83414_[$$0] = Scoreboard.m_83453_($$0);
            }
        }
        return f_83414_;
    }

    public void m_83420_(Entity p_83421_) {
        if (p_83421_ == null || p_83421_ instanceof Player || p_83421_.m_6084_()) {
            return;
        }
        String $$1 = p_83421_.m_20149_();
        this.m_83479_($$1, null);
        this.m_83495_($$1);
    }

    protected ListTag m_83497_() {
        ListTag $$0 = new ListTag();
        this.f_83410_.values().stream().map(Map::values).forEach(p_83452_ -> p_83452_.stream().filter(p_166098_ -> p_166098_.m_83404_() != null).forEach(p_166096_ -> {
            CompoundTag $$2 = new CompoundTag();
            $$2.m_128359_("Name", p_166096_.m_83405_());
            $$2.m_128359_("Objective", p_166096_.m_83404_().m_83320_());
            $$2.m_128405_("Score", p_166096_.m_83400_());
            $$2.m_128379_("Locked", p_166096_.m_83407_());
            $$0.add($$2);
        }));
        return $$0;
    }

    protected void m_83445_(ListTag p_83446_) {
        for (int $$1 = 0; $$1 < p_83446_.size(); ++$$1) {
            CompoundTag $$2 = p_83446_.m_128728_($$1);
            Objective $$3 = this.m_83469_($$2.m_128461_("Objective"));
            String $$4 = $$2.m_128461_("Name");
            Score $$5 = this.m_83471_($$4, $$3);
            $$5.m_83402_($$2.m_128451_("Score"));
            if (!$$2.m_128441_("Locked")) continue;
            $$5.m_83398_($$2.m_128471_("Locked"));
        }
    }
}

