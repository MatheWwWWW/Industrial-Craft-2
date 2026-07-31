/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.controls;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.KeyBindsList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class KeyBindsScreen
extends OptionsSubScreen {
    @Nullable
    public KeyMapping f_193975_;
    public long f_193976_;
    private KeyBindsList f_193977_;
    private Button f_193978_;

    public KeyBindsScreen(Screen p_193980_, Options p_193981_) {
        super(p_193980_, p_193981_, Component.m_237115_("controls.keybinds.title"));
    }

    @Override
    protected void m_7856_() {
        this.f_193977_ = new KeyBindsList(this, this.f_96541_);
        this.m_7787_(this.f_193977_);
        this.f_193978_ = this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ - 29, 150, 20, Component.m_237115_("controls.resetAll"), p_193999_ -> {
            for (KeyMapping $$1 : this.f_96282_.f_92059_) {
                $$1.m_90848_($$1.m_90861_());
            }
            KeyMapping.m_90854_();
        }));
        this.m_142416_(new Button(this.f_96543_ / 2 - 155 + 160, this.f_96544_ - 29, 150, 20, CommonComponents.f_130655_, p_193996_ -> this.f_96541_.m_91152_(this.f_96281_)));
    }

    @Override
    public boolean m_6375_(double p_193983_, double p_193984_, int p_193985_) {
        if (this.f_193975_ != null) {
            this.f_96282_.m_92159_(this.f_193975_, InputConstants.Type.MOUSE.m_84895_(p_193985_));
            this.f_193975_ = null;
            KeyMapping.m_90854_();
            return true;
        }
        return super.m_6375_(p_193983_, p_193984_, p_193985_);
    }

    @Override
    public boolean m_7933_(int p_193987_, int p_193988_, int p_193989_) {
        if (this.f_193975_ != null) {
            if (p_193987_ == 256) {
                this.f_96282_.m_92159_(this.f_193975_, InputConstants.f_84822_);
            } else {
                this.f_96282_.m_92159_(this.f_193975_, InputConstants.m_84827_(p_193987_, p_193988_));
            }
            this.f_193975_ = null;
            this.f_193976_ = Util.m_137550_();
            KeyMapping.m_90854_();
            return true;
        }
        return super.m_7933_(p_193987_, p_193988_, p_193989_);
    }

    @Override
    public void m_6305_(PoseStack p_193991_, int p_193992_, int p_193993_, float p_193994_) {
        this.m_7333_(p_193991_);
        this.f_193977_.m_6305_(p_193991_, p_193992_, p_193993_, p_193994_);
        KeyBindsScreen.m_93215_(p_193991_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 8, 0xFFFFFF);
        boolean $$4 = false;
        for (KeyMapping $$5 : this.f_96282_.f_92059_) {
            if ($$5.m_90864_()) continue;
            $$4 = true;
            break;
        }
        this.f_193978_.f_93623_ = $$4;
        super.m_6305_(p_193991_, p_193992_, p_193993_, p_193994_);
    }
}

