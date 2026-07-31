/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.schedule;

import net.minecraft.core.Registry;

public class Activity {
    public static final Activity f_37978_ = Activity.m_37999_("core");
    public static final Activity f_37979_ = Activity.m_37999_("idle");
    public static final Activity f_37980_ = Activity.m_37999_("work");
    public static final Activity f_37981_ = Activity.m_37999_("play");
    public static final Activity f_37982_ = Activity.m_37999_("rest");
    public static final Activity f_37983_ = Activity.m_37999_("meet");
    public static final Activity f_37984_ = Activity.m_37999_("panic");
    public static final Activity f_37985_ = Activity.m_37999_("raid");
    public static final Activity f_37986_ = Activity.m_37999_("pre_raid");
    public static final Activity f_37987_ = Activity.m_37999_("hide");
    public static final Activity f_37988_ = Activity.m_37999_("fight");
    public static final Activity f_37989_ = Activity.m_37999_("celebrate");
    public static final Activity f_37990_ = Activity.m_37999_("admire_item");
    public static final Activity f_37991_ = Activity.m_37999_("avoid");
    public static final Activity f_37992_ = Activity.m_37999_("ride");
    public static final Activity f_150238_ = Activity.m_37999_("play_dead");
    public static final Activity f_150239_ = Activity.m_37999_("long_jump");
    public static final Activity f_150240_ = Activity.m_37999_("ram");
    public static final Activity f_219846_ = Activity.m_37999_("tongue");
    public static final Activity f_219847_ = Activity.m_37999_("swim");
    public static final Activity f_219848_ = Activity.m_37999_("lay_spawn");
    public static final Activity f_219849_ = Activity.m_37999_("sniff");
    public static final Activity f_219850_ = Activity.m_37999_("investigate");
    public static final Activity f_219851_ = Activity.m_37999_("roar");
    public static final Activity f_219852_ = Activity.m_37999_("emerge");
    public static final Activity f_219853_ = Activity.m_37999_("dig");
    private final String f_37993_;
    private final int f_37994_;

    private Activity(String p_37997_) {
        this.f_37993_ = p_37997_;
        this.f_37994_ = p_37997_.hashCode();
    }

    public String m_37998_() {
        return this.f_37993_;
    }

    private static Activity m_37999_(String p_38000_) {
        return Registry.m_122961_(Registry.f_122874_, p_38000_, new Activity(p_38000_));
    }

    public boolean equals(Object p_38002_) {
        if (this == p_38002_) {
            return true;
        }
        if (p_38002_ == null || this.getClass() != p_38002_.getClass()) {
            return false;
        }
        Activity $$1 = (Activity)p_38002_;
        return this.f_37993_.equals($$1.f_37993_);
    }

    public int hashCode() {
        return this.f_37994_;
    }

    public String toString() {
        return this.m_37998_();
    }
}

