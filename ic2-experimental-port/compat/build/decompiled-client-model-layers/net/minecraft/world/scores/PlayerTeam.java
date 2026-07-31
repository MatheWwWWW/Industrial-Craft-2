/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.world.scores;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;

public class PlayerTeam
extends Team {
    private static final int f_166084_ = 0;
    private static final int f_166085_ = 1;
    private final Scoreboard f_83326_;
    private final String f_83327_;
    private final Set<String> f_83328_ = Sets.newHashSet();
    private Component f_83329_;
    private Component f_83330_ = CommonComponents.f_237098_;
    private Component f_83331_ = CommonComponents.f_237098_;
    private boolean f_83332_ = true;
    private boolean f_83333_ = true;
    private Team.Visibility f_83334_ = Team.Visibility.ALWAYS;
    private Team.Visibility f_83335_ = Team.Visibility.ALWAYS;
    private ChatFormatting f_83336_ = ChatFormatting.RESET;
    private Team.CollisionRule f_83337_ = Team.CollisionRule.ALWAYS;
    private final Style f_83338_;

    public PlayerTeam(Scoreboard p_83340_, String p_83341_) {
        this.f_83326_ = p_83340_;
        this.f_83327_ = p_83341_;
        this.f_83329_ = Component.m_237113_(p_83341_);
        this.f_83338_ = Style.f_131099_.m_131138_(p_83341_).m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, Component.m_237113_(p_83341_)));
    }

    public Scoreboard m_166086_() {
        return this.f_83326_;
    }

    @Override
    public String m_5758_() {
        return this.f_83327_;
    }

    public Component m_83364_() {
        return this.f_83329_;
    }

    public MutableComponent m_83367_() {
        MutableComponent $$0 = ComponentUtils.m_130748_(this.f_83329_.m_6881_().m_130948_(this.f_83338_));
        ChatFormatting $$1 = this.m_7414_();
        if ($$1 != ChatFormatting.RESET) {
            $$0.m_130940_($$1);
        }
        return $$0;
    }

    public void m_83353_(Component p_83354_) {
        if (p_83354_ == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.f_83329_ = p_83354_;
        this.f_83326_.m_7645_(this);
    }

    public void m_83360_(@Nullable Component p_83361_) {
        this.f_83330_ = p_83361_ == null ? CommonComponents.f_237098_ : p_83361_;
        this.f_83326_.m_7645_(this);
    }

    public Component m_83370_() {
        return this.f_83330_;
    }

    public void m_83365_(@Nullable Component p_83366_) {
        this.f_83331_ = p_83366_ == null ? CommonComponents.f_237098_ : p_83366_;
        this.f_83326_.m_7645_(this);
    }

    public Component m_83371_() {
        return this.f_83331_;
    }

    @Override
    public Collection<String> m_6809_() {
        return this.f_83328_;
    }

    @Override
    public MutableComponent m_6870_(Component p_83369_) {
        MutableComponent $$1 = Component.m_237119_().m_7220_(this.f_83330_).m_7220_(p_83369_).m_7220_(this.f_83331_);
        ChatFormatting $$2 = this.m_7414_();
        if ($$2 != ChatFormatting.RESET) {
            $$1.m_130940_($$2);
        }
        return $$1;
    }

    public static MutableComponent m_83348_(@Nullable Team p_83349_, Component p_83350_) {
        if (p_83349_ == null) {
            return p_83350_.m_6881_();
        }
        return p_83349_.m_6870_(p_83350_);
    }

    @Override
    public boolean m_6260_() {
        return this.f_83332_;
    }

    public void m_83355_(boolean p_83356_) {
        this.f_83332_ = p_83356_;
        this.f_83326_.m_7645_(this);
    }

    @Override
    public boolean m_6259_() {
        return this.f_83333_;
    }

    public void m_83362_(boolean p_83363_) {
        this.f_83333_ = p_83363_;
        this.f_83326_.m_7645_(this);
    }

    @Override
    public Team.Visibility m_7470_() {
        return this.f_83334_;
    }

    @Override
    public Team.Visibility m_7468_() {
        return this.f_83335_;
    }

    public void m_83346_(Team.Visibility p_83347_) {
        this.f_83334_ = p_83347_;
        this.f_83326_.m_7645_(this);
    }

    public void m_83358_(Team.Visibility p_83359_) {
        this.f_83335_ = p_83359_;
        this.f_83326_.m_7645_(this);
    }

    @Override
    public Team.CollisionRule m_7156_() {
        return this.f_83337_;
    }

    public void m_83344_(Team.CollisionRule p_83345_) {
        this.f_83337_ = p_83345_;
        this.f_83326_.m_7645_(this);
    }

    public int m_83378_() {
        int $$0 = 0;
        if (this.m_6260_()) {
            $$0 |= 1;
        }
        if (this.m_6259_()) {
            $$0 |= 2;
        }
        return $$0;
    }

    public void m_83342_(int p_83343_) {
        this.m_83355_((p_83343_ & 1) > 0);
        this.m_83362_((p_83343_ & 2) > 0);
    }

    public void m_83351_(ChatFormatting p_83352_) {
        this.f_83336_ = p_83352_;
        this.f_83326_.m_7645_(this);
    }

    @Override
    public ChatFormatting m_7414_() {
        return this.f_83336_;
    }
}

