/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.booleans.BooleanConsumer
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class EditServerScreen
extends Screen {
    private static final Component f_96005_ = Component.m_237115_("addServer.enterName");
    private static final Component f_96006_ = Component.m_237115_("addServer.enterIp");
    private Button f_96007_;
    private final BooleanConsumer f_96008_;
    private final ServerData f_96009_;
    private EditBox f_96010_;
    private EditBox f_96011_;
    private final Screen f_96013_;

    public EditServerScreen(Screen p_96017_, BooleanConsumer p_96018_, ServerData p_96019_) {
        super(Component.m_237115_("addServer.title"));
        this.f_96013_ = p_96017_;
        this.f_96008_ = p_96018_;
        this.f_96009_ = p_96019_;
    }

    @Override
    public void m_86600_() {
        this.f_96011_.m_94120_();
        this.f_96010_.m_94120_();
    }

    @Override
    protected void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_96011_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 100, 66, 200, 20, Component.m_237115_("addServer.enterName"));
        this.f_96011_.m_94178_(true);
        this.f_96011_.m_94144_(this.f_96009_.f_105362_);
        this.f_96011_.m_94151_(p_169304_ -> this.m_169305_());
        this.m_7787_(this.f_96011_);
        this.f_96010_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 100, 106, 200, 20, Component.m_237115_("addServer.enterIp"));
        this.f_96010_.m_94199_(128);
        this.f_96010_.m_94144_(this.f_96009_.f_105363_);
        this.f_96010_.m_94151_(p_169302_ -> this.m_169305_());
        this.m_7787_(this.f_96010_);
        this.m_142416_(CycleButton.m_168894_(ServerData.ServerPackStatus::m_105400_).m_168961_((ServerData.ServerPackStatus[])ServerData.ServerPackStatus.values()).m_168948_(this.f_96009_.m_105387_()).m_168936_(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 72, 200, 20, Component.m_237115_("addServer.resourcePack"), (p_169299_, p_169300_) -> this.f_96009_.m_105379_((ServerData.ServerPackStatus)((Object)p_169300_))));
        this.f_96007_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 96 + 18, 200, 20, Component.m_237115_("addServer.add"), p_96030_ -> this.m_96045_()));
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 120 + 18, 200, 20, CommonComponents.f_130656_, p_169297_ -> this.f_96008_.accept(false)));
        this.m_169305_();
    }

    @Override
    public void m_6574_(Minecraft p_96026_, int p_96027_, int p_96028_) {
        String $$3 = this.f_96010_.m_94155_();
        String $$4 = this.f_96011_.m_94155_();
        this.m_6575_(p_96026_, p_96027_, p_96028_);
        this.f_96010_.m_94144_($$3);
        this.f_96011_.m_94144_($$4);
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    private void m_96045_() {
        this.f_96009_.f_105362_ = this.f_96011_.m_94155_();
        this.f_96009_.f_105363_ = this.f_96010_.m_94155_();
        this.f_96008_.accept(true);
    }

    @Override
    public void m_7379_() {
        this.f_96541_.m_91152_(this.f_96013_);
    }

    private void m_169305_() {
        this.f_96007_.f_93623_ = ServerAddress.m_171867_(this.f_96010_.m_94155_()) && !this.f_96011_.m_94155_().isEmpty();
    }

    @Override
    public void m_6305_(PoseStack p_96021_, int p_96022_, int p_96023_, float p_96024_) {
        this.m_7333_(p_96021_);
        EditServerScreen.m_93215_(p_96021_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 17, 0xFFFFFF);
        EditServerScreen.m_93243_(p_96021_, this.f_96547_, f_96005_, this.f_96543_ / 2 - 100, 53, 0xA0A0A0);
        EditServerScreen.m_93243_(p_96021_, this.f_96547_, f_96006_, this.f_96543_ / 2 - 100, 94, 0xA0A0A0);
        this.f_96011_.m_6305_(p_96021_, p_96022_, p_96023_, p_96024_);
        this.f_96010_.m_6305_(p_96021_, p_96022_, p_96023_, p_96024_);
        super.m_6305_(p_96021_, p_96022_, p_96023_, p_96024_);
    }
}

