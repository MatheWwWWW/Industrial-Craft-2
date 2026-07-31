/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public abstract class SimpleOptionsSubScreen
extends OptionsSubScreen {
    protected final OptionInstance<?>[] f_96666_;
    @Nullable
    private AbstractWidget f_96667_;
    private OptionsList f_96668_;

    public SimpleOptionsSubScreen(Screen p_232763_, Options p_232764_, Component p_232765_, OptionInstance<?>[] p_232766_) {
        super(p_232763_, p_232764_, p_232765_);
        this.f_96666_ = p_232766_;
    }

    @Override
    protected void m_7856_() {
        this.f_96668_ = new OptionsList(this.f_96541_, this.f_96543_, this.f_96544_, 32, this.f_96544_ - 32, 25);
        this.f_96668_.m_232533_(this.f_96666_);
        this.m_7787_(this.f_96668_);
        this.m_7853_();
        this.f_96667_ = this.f_96668_.m_232535_(this.f_96282_.m_231930_());
        if (this.f_96667_ != null) {
            this.f_96667_.f_93623_ = this.f_96541_.m_240477_().m_93316_();
        }
    }

    protected void m_7853_() {
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 27, 200, 20, CommonComponents.f_130655_, p_96680_ -> this.f_96541_.m_91152_(this.f_96281_)));
    }

    @Override
    public void m_6305_(PoseStack p_96675_, int p_96676_, int p_96677_, float p_96678_) {
        this.m_7333_(p_96675_);
        this.f_96668_.m_6305_(p_96675_, p_96676_, p_96677_, p_96678_);
        SimpleOptionsSubScreen.m_93215_(p_96675_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 20, 0xFFFFFF);
        super.m_6305_(p_96675_, p_96676_, p_96677_, p_96678_);
        List<FormattedCharSequence> $$4 = SimpleOptionsSubScreen.m_96287_(this.f_96668_, p_96676_, p_96677_);
        this.m_96617_(p_96675_, $$4, p_96676_, p_96677_);
    }

    public void m_96682_() {
        if (this.f_96667_ instanceof CycleButton) {
            ((CycleButton)this.f_96667_).m_168892_(this.f_96282_.m_231930_().m_231551_());
        }
    }
}

