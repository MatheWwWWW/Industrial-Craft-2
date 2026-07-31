/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IClickHandler;
import ic2.core.gui.MouseButton;
import ic2.core.init.Localization;
import ic2.core.proxy.SideProxyClient;
import java.util.function.Supplier;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;

public abstract class Button<T extends Button<T>>
extends GuiElement<T> {
    private static final int iconSize = 16;
    private final IClickHandler handler;
    private Supplier<String> textProvider;
    private Supplier<ItemStack> iconProvider;

    protected Button(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, IClickHandler iClickHandler) {
        super(ic2Gui, n, n2, n3, n4);
        this.handler = iClickHandler;
    }

    public T withText(final String string) {
        return this.withText(new Supplier<String>(){

            @Override
            public String get() {
                return string;
            }
        });
    }

    public T withText(Supplier<String> supplier) {
        this.textProvider = supplier;
        return (T)this;
    }

    public T withIcon(Supplier<ItemStack> supplier) {
        this.iconProvider = supplier;
        return (T)this;
    }

    protected int getTextColor(int n, int n2) {
        return 0xDDDDDD;
    }

    @Override
    public void drawBackground(PoseStack poseStack, int n, int n2) {
        ItemStack itemStack;
        if (this.textProvider != null) {
            String string = this.textProvider.get();
            if (string != null && !string.isEmpty()) {
                string = Localization.translate(string);
                this.gui.drawXYCenteredString(poseStack, this.x + this.width / 2, this.y + this.height / 2, string, this.getTextColor(n, n2), true);
            }
        } else if (this.iconProvider != null && (itemStack = this.iconProvider.get()) != null && itemStack.m_41720_() != null) {
            this.gui.drawItem(this.x + (this.width - 16) / 2, this.y + (this.height - 16) / 2, itemStack);
        }
    }

    @Override
    protected boolean onMouseClick(int n, int n2, MouseButton mouseButton) {
        SideProxyClient.mc.m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)SoundEvents.f_12490_, (float)1.0f));
        this.handler.onClick(mouseButton);
        return false;
    }
}

