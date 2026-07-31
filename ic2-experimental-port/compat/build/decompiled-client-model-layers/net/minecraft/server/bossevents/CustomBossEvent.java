/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 */
package net.minecraft.server.bossevents;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;

public class CustomBossEvent
extends ServerBossEvent {
    private final ResourceLocation f_136256_;
    private final Set<UUID> f_136257_ = Sets.newHashSet();
    private int f_136258_;
    private int f_136259_ = 100;

    public CustomBossEvent(ResourceLocation p_136261_, Component p_136262_) {
        super(p_136262_, BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.PROGRESS);
        this.f_136256_ = p_136261_;
        this.m_142711_(0.0f);
    }

    public ResourceLocation m_136263_() {
        return this.f_136256_;
    }

    @Override
    public void m_6543_(ServerPlayer p_136267_) {
        super.m_6543_(p_136267_);
        this.f_136257_.add(p_136267_.m_20148_());
    }

    public void m_136270_(UUID p_136271_) {
        this.f_136257_.add(p_136271_);
    }

    @Override
    public void m_6539_(ServerPlayer p_136281_) {
        super.m_6539_(p_136281_);
        this.f_136257_.remove(p_136281_.m_20148_());
    }

    @Override
    public void m_7706_() {
        super.m_7706_();
        this.f_136257_.clear();
    }

    public int m_136282_() {
        return this.f_136258_;
    }

    public int m_136285_() {
        return this.f_136259_;
    }

    public void m_136264_(int p_136265_) {
        this.f_136258_ = p_136265_;
        this.m_142711_(Mth.m_14036_((float)p_136265_ / (float)this.f_136259_, 0.0f, 1.0f));
    }

    public void m_136278_(int p_136279_) {
        this.f_136259_ = p_136279_;
        this.m_142711_(Mth.m_14036_((float)this.f_136258_ / (float)p_136279_, 0.0f, 1.0f));
    }

    public final Component m_136288_() {
        return ComponentUtils.m_130748_(this.m_18861_()).m_130938_(p_136276_ -> p_136276_.m_131140_(this.m_18862_().m_18883_()).m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, Component.m_237113_(this.m_136263_().toString()))).m_131138_(this.m_136263_().toString()));
    }

    public boolean m_136268_(Collection<ServerPlayer> p_136269_) {
        HashSet $$1 = Sets.newHashSet();
        HashSet $$2 = Sets.newHashSet();
        for (UUID $$3 : this.f_136257_) {
            boolean $$4 = false;
            for (ServerPlayer $$5 : p_136269_) {
                if (!$$5.m_20148_().equals($$3)) continue;
                $$4 = true;
                break;
            }
            if ($$4) continue;
            $$1.add($$3);
        }
        for (ServerPlayer $$6 : p_136269_) {
            boolean $$7 = false;
            for (UUID $$8 : this.f_136257_) {
                if (!$$6.m_20148_().equals($$8)) continue;
                $$7 = true;
                break;
            }
            if ($$7) continue;
            $$2.add($$6);
        }
        for (UUID $$9 : $$1) {
            for (ServerPlayer $$10 : this.m_8324_()) {
                if (!$$10.m_20148_().equals($$9)) continue;
                this.m_6539_($$10);
                break;
            }
            this.f_136257_.remove($$9);
        }
        for (ServerPlayer $$11 : $$2) {
            this.m_6543_($$11);
        }
        return !$$1.isEmpty() || !$$2.isEmpty();
    }

    public CompoundTag m_136289_() {
        CompoundTag $$0 = new CompoundTag();
        $$0.m_128359_("Name", Component.Serializer.m_130703_(this.f_18840_));
        $$0.m_128379_("Visible", this.m_8323_());
        $$0.m_128405_("Value", this.f_136258_);
        $$0.m_128405_("Max", this.f_136259_);
        $$0.m_128359_("Color", this.m_18862_().m_18886_());
        $$0.m_128359_("Overlay", this.m_18863_().m_18902_());
        $$0.m_128379_("DarkenScreen", this.m_18864_());
        $$0.m_128379_("PlayBossMusic", this.m_18865_());
        $$0.m_128379_("CreateWorldFog", this.m_18866_());
        ListTag $$1 = new ListTag();
        for (UUID $$2 : this.f_136257_) {
            $$1.add(NbtUtils.m_129226_($$2));
        }
        $$0.m_128365_("Players", $$1);
        return $$0;
    }

    public static CustomBossEvent m_136272_(CompoundTag p_136273_, ResourceLocation p_136274_) {
        CustomBossEvent $$2 = new CustomBossEvent(p_136274_, Component.Serializer.m_130701_(p_136273_.m_128461_("Name")));
        $$2.m_8321_(p_136273_.m_128471_("Visible"));
        $$2.m_136264_(p_136273_.m_128451_("Value"));
        $$2.m_136278_(p_136273_.m_128451_("Max"));
        $$2.m_6451_(BossEvent.BossBarColor.m_18884_(p_136273_.m_128461_("Color")));
        $$2.m_5648_(BossEvent.BossBarOverlay.m_18903_(p_136273_.m_128461_("Overlay")));
        $$2.m_7003_(p_136273_.m_128471_("DarkenScreen"));
        $$2.m_7005_(p_136273_.m_128471_("PlayBossMusic"));
        $$2.m_7006_(p_136273_.m_128471_("CreateWorldFog"));
        ListTag $$3 = p_136273_.m_128437_("Players", 11);
        for (int $$4 = 0; $$4 < $$3.size(); ++$$4) {
            $$2.m_136270_(NbtUtils.m_129233_($$3.get($$4)));
        }
        return $$2;
    }

    public void m_136283_(ServerPlayer p_136284_) {
        if (this.f_136257_.contains(p_136284_.m_20148_())) {
            this.m_6543_(p_136284_);
        }
    }

    public void m_136286_(ServerPlayer p_136287_) {
        super.m_6539_(p_136287_);
    }
}

