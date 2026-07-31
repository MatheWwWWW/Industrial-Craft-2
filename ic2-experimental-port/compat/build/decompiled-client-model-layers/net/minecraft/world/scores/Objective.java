/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.scores;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class Objective {
    private final Scoreboard f_83301_;
    private final String f_83302_;
    private final ObjectiveCriteria f_83303_;
    private Component f_83304_;
    private Component f_83305_;
    private ObjectiveCriteria.RenderType f_83306_;

    public Objective(Scoreboard p_83308_, String p_83309_, ObjectiveCriteria p_83310_, Component p_83311_, ObjectiveCriteria.RenderType p_83312_) {
        this.f_83301_ = p_83308_;
        this.f_83302_ = p_83309_;
        this.f_83303_ = p_83310_;
        this.f_83304_ = p_83311_;
        this.f_83305_ = this.m_83325_();
        this.f_83306_ = p_83312_;
    }

    public Scoreboard m_83313_() {
        return this.f_83301_;
    }

    public String m_83320_() {
        return this.f_83302_;
    }

    public ObjectiveCriteria m_83321_() {
        return this.f_83303_;
    }

    public Component m_83322_() {
        return this.f_83304_;
    }

    private Component m_83325_() {
        return ComponentUtils.m_130748_(this.f_83304_.m_6881_().m_130938_(p_83319_ -> p_83319_.m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, Component.m_237113_(this.f_83302_)))));
    }

    public Component m_83323_() {
        return this.f_83305_;
    }

    public void m_83316_(Component p_83317_) {
        this.f_83304_ = p_83317_;
        this.f_83305_ = this.m_83325_();
        this.f_83301_.m_7091_(this);
    }

    public ObjectiveCriteria.RenderType m_83324_() {
        return this.f_83306_;
    }

    public void m_83314_(ObjectiveCriteria.RenderType p_83315_) {
        this.f_83306_ = p_83315_;
        this.f_83301_.m_7091_(this);
    }
}

