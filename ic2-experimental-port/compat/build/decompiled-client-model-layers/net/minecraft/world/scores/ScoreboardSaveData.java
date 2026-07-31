/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.scores;

import java.util.Collection;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class ScoreboardSaveData
extends SavedData {
    public static final String f_166099_ = "scoreboard";
    private final Scoreboard f_83509_;

    public ScoreboardSaveData(Scoreboard p_166101_) {
        this.f_83509_ = p_166101_;
    }

    public ScoreboardSaveData m_166102_(CompoundTag p_166103_) {
        this.m_83528_(p_166103_.m_128437_("Objectives", 10));
        this.f_83509_.m_83445_(p_166103_.m_128437_("PlayerScores", 10));
        if (p_166103_.m_128425_("DisplaySlots", 10)) {
            this.m_83530_(p_166103_.m_128469_("DisplaySlots"));
        }
        if (p_166103_.m_128425_("Teams", 9)) {
            this.m_83524_(p_166103_.m_128437_("Teams", 10));
        }
        return this;
    }

    private void m_83524_(ListTag p_83525_) {
        for (int $$1 = 0; $$1 < p_83525_.size(); ++$$1) {
            Team.CollisionRule $$10;
            Team.Visibility $$9;
            Team.Visibility $$8;
            MutableComponent $$7;
            MutableComponent $$6;
            CompoundTag $$2 = p_83525_.m_128728_($$1);
            String $$3 = $$2.m_128461_("Name");
            PlayerTeam $$4 = this.f_83509_.m_83492_($$3);
            MutableComponent $$5 = Component.Serializer.m_130701_($$2.m_128461_("DisplayName"));
            if ($$5 != null) {
                $$4.m_83353_($$5);
            }
            if ($$2.m_128425_("TeamColor", 8)) {
                $$4.m_83351_(ChatFormatting.m_126657_($$2.m_128461_("TeamColor")));
            }
            if ($$2.m_128425_("AllowFriendlyFire", 99)) {
                $$4.m_83355_($$2.m_128471_("AllowFriendlyFire"));
            }
            if ($$2.m_128425_("SeeFriendlyInvisibles", 99)) {
                $$4.m_83362_($$2.m_128471_("SeeFriendlyInvisibles"));
            }
            if ($$2.m_128425_("MemberNamePrefix", 8) && ($$6 = Component.Serializer.m_130701_($$2.m_128461_("MemberNamePrefix"))) != null) {
                $$4.m_83360_($$6);
            }
            if ($$2.m_128425_("MemberNameSuffix", 8) && ($$7 = Component.Serializer.m_130701_($$2.m_128461_("MemberNameSuffix"))) != null) {
                $$4.m_83365_($$7);
            }
            if ($$2.m_128425_("NameTagVisibility", 8) && ($$8 = Team.Visibility.m_83579_($$2.m_128461_("NameTagVisibility"))) != null) {
                $$4.m_83346_($$8);
            }
            if ($$2.m_128425_("DeathMessageVisibility", 8) && ($$9 = Team.Visibility.m_83579_($$2.m_128461_("DeathMessageVisibility"))) != null) {
                $$4.m_83358_($$9);
            }
            if ($$2.m_128425_("CollisionRule", 8) && ($$10 = Team.CollisionRule.m_83555_($$2.m_128461_("CollisionRule"))) != null) {
                $$4.m_83344_($$10);
            }
            this.m_83514_($$4, $$2.m_128437_("Players", 8));
        }
    }

    private void m_83514_(PlayerTeam p_83515_, ListTag p_83516_) {
        for (int $$2 = 0; $$2 < p_83516_.size(); ++$$2) {
            this.f_83509_.m_6546_(p_83516_.m_128778_($$2), p_83515_);
        }
    }

    private void m_83530_(CompoundTag p_83531_) {
        for (int $$1 = 0; $$1 < 19; ++$$1) {
            if (!p_83531_.m_128425_("slot_" + $$1, 8)) continue;
            String $$2 = p_83531_.m_128461_("slot_" + $$1);
            Objective $$3 = this.f_83509_.m_83477_($$2);
            this.f_83509_.m_7136_($$1, $$3);
        }
    }

    private void m_83528_(ListTag p_83529_) {
        for (int $$1 = 0; $$1 < p_83529_.size(); ++$$1) {
            CompoundTag $$2 = p_83529_.m_128728_($$1);
            ObjectiveCriteria.m_83614_($$2.m_128461_("CriteriaName")).ifPresent(p_83523_ -> {
                String $$2 = $$2.m_128461_("Name");
                MutableComponent $$3 = Component.Serializer.m_130701_($$2.m_128461_("DisplayName"));
                ObjectiveCriteria.RenderType $$4 = ObjectiveCriteria.RenderType.m_83634_($$2.m_128461_("RenderType"));
                this.f_83509_.m_83436_($$2, (ObjectiveCriteria)p_83523_, $$3, $$4);
            });
        }
    }

    @Override
    public CompoundTag m_7176_(CompoundTag p_83527_) {
        p_83527_.m_128365_("Objectives", this.m_83534_());
        p_83527_.m_128365_("PlayerScores", this.f_83509_.m_83497_());
        p_83527_.m_128365_("Teams", this.m_83513_());
        this.m_83532_(p_83527_);
        return p_83527_;
    }

    private ListTag m_83513_() {
        ListTag $$0 = new ListTag();
        Collection<PlayerTeam> $$1 = this.f_83509_.m_83491_();
        for (PlayerTeam $$2 : $$1) {
            CompoundTag $$3 = new CompoundTag();
            $$3.m_128359_("Name", $$2.m_5758_());
            $$3.m_128359_("DisplayName", Component.Serializer.m_130703_($$2.m_83364_()));
            if ($$2.m_7414_().m_126656_() >= 0) {
                $$3.m_128359_("TeamColor", $$2.m_7414_().m_126666_());
            }
            $$3.m_128379_("AllowFriendlyFire", $$2.m_6260_());
            $$3.m_128379_("SeeFriendlyInvisibles", $$2.m_6259_());
            $$3.m_128359_("MemberNamePrefix", Component.Serializer.m_130703_($$2.m_83370_()));
            $$3.m_128359_("MemberNameSuffix", Component.Serializer.m_130703_($$2.m_83371_()));
            $$3.m_128359_("NameTagVisibility", $$2.m_7470_().f_83567_);
            $$3.m_128359_("DeathMessageVisibility", $$2.m_7468_().f_83567_);
            $$3.m_128359_("CollisionRule", $$2.m_7156_().f_83543_);
            ListTag $$4 = new ListTag();
            for (String $$5 : $$2.m_6809_()) {
                $$4.add(StringTag.m_129297_($$5));
            }
            $$3.m_128365_("Players", $$4);
            $$0.add($$3);
        }
        return $$0;
    }

    private void m_83532_(CompoundTag p_83533_) {
        CompoundTag $$1 = new CompoundTag();
        boolean $$2 = false;
        for (int $$3 = 0; $$3 < 19; ++$$3) {
            Objective $$4 = this.f_83509_.m_83416_($$3);
            if ($$4 == null) continue;
            $$1.m_128359_("slot_" + $$3, $$4.m_83320_());
            $$2 = true;
        }
        if ($$2) {
            p_83533_.m_128365_("DisplaySlots", $$1);
        }
    }

    private ListTag m_83534_() {
        ListTag $$0 = new ListTag();
        Collection<Objective> $$1 = this.f_83509_.m_83466_();
        for (Objective $$2 : $$1) {
            if ($$2.m_83321_() == null) continue;
            CompoundTag $$3 = new CompoundTag();
            $$3.m_128359_("Name", $$2.m_83320_());
            $$3.m_128359_("CriteriaName", $$2.m_83321_().m_83620_());
            $$3.m_128359_("DisplayName", Component.Serializer.m_130703_($$2.m_83322_()));
            $$3.m_128359_("RenderType", $$2.m_83324_().m_83633_());
            $$0.add($$3);
        }
        return $$0;
    }
}

