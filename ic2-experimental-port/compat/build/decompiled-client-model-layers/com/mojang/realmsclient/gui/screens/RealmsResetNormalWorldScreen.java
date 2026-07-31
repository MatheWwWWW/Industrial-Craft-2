/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.util.LevelType;
import com.mojang.realmsclient.util.WorldGenerationInfo;
import java.util.function.Consumer;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.realms.RealmsScreen;

public class RealmsResetNormalWorldScreen
extends RealmsScreen {
    private static final Component f_89266_ = Component.m_237115_("mco.reset.world.seed");
    private final Consumer<WorldGenerationInfo> f_167436_;
    private EditBox f_89270_;
    private LevelType f_167435_ = LevelType.DEFAULT;
    private boolean f_89271_ = true;
    private final Component f_89273_;

    public RealmsResetNormalWorldScreen(Consumer<WorldGenerationInfo> p_167438_, Component p_167439_) {
        super(Component.m_237115_("mco.reset.world.generate"));
        this.f_167436_ = p_167438_;
        this.f_89273_ = p_167439_;
    }

    @Override
    public void m_86600_() {
        this.f_89270_.m_94120_();
        super.m_86600_();
    }

    @Override
    public void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_89270_ = new EditBox(this.f_96541_.f_91062_, this.f_96543_ / 2 - 100, RealmsResetNormalWorldScreen.m_120774_(2), 200, 20, null, Component.m_237115_("mco.reset.world.seed"));
        this.f_89270_.m_94199_(32);
        this.m_7787_(this.f_89270_);
        this.m_94718_(this.f_89270_);
        this.m_142416_(CycleButton.m_168894_(LevelType::m_167607_).m_168961_((LevelType[])LevelType.values()).m_168948_(this.f_167435_).m_168936_(this.f_96543_ / 2 - 102, RealmsResetNormalWorldScreen.m_120774_(4), 205, 20, Component.m_237115_("selectWorld.mapType"), (p_167441_, p_167442_) -> {
            this.f_167435_ = p_167442_;
        }));
        this.m_142416_(CycleButton.m_168916_(this.f_89271_).m_168936_(this.f_96543_ / 2 - 102, RealmsResetNormalWorldScreen.m_120774_(6) - 2, 205, 20, Component.m_237115_("selectWorld.mapFeatures"), (p_167444_, p_167445_) -> {
            this.f_89271_ = p_167445_;
        }));
        this.m_142416_(new Button(this.f_96543_ / 2 - 102, RealmsResetNormalWorldScreen.m_120774_(12), 97, 20, this.f_89273_, p_89291_ -> this.f_167436_.accept(new WorldGenerationInfo(this.f_89270_.m_94155_(), this.f_167435_, this.f_89271_))));
        this.m_142416_(new Button(this.f_96543_ / 2 + 8, RealmsResetNormalWorldScreen.m_120774_(12), 97, 20, CommonComponents.f_130660_, p_89288_ -> this.m_7379_()));
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    @Override
    public void m_7379_() {
        this.f_167436_.accept(null);
    }

    @Override
    public void m_6305_(PoseStack p_89283_, int p_89284_, int p_89285_, float p_89286_) {
        this.m_7333_(p_89283_);
        RealmsResetNormalWorldScreen.m_93215_(p_89283_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 17, 0xFFFFFF);
        this.f_96547_.m_92889_(p_89283_, f_89266_, this.f_96543_ / 2 - 100, RealmsResetNormalWorldScreen.m_120774_(1), 0xA0A0A0);
        this.f_89270_.m_6305_(p_89283_, p_89284_, p_89285_, p_89286_);
        super.m_6305_(p_89283_, p_89284_, p_89285_, p_89286_);
    }
}

