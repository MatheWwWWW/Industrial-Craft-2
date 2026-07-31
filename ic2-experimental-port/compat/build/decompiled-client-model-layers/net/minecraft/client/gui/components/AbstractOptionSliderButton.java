/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.components;

import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.CommonComponents;

public abstract class AbstractOptionSliderButton
extends AbstractSliderButton {
    protected final Options f_93377_;

    protected AbstractOptionSliderButton(Options p_93379_, int p_93380_, int p_93381_, int p_93382_, int p_93383_, double p_93384_) {
        super(p_93380_, p_93381_, p_93382_, p_93383_, CommonComponents.f_237098_, p_93384_);
        this.f_93377_ = p_93379_;
    }
}

