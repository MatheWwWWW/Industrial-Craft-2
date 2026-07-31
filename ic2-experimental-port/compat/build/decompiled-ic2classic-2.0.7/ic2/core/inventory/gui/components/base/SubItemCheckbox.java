/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.ScreenUtils
 */
package ic2.core.inventory.gui.components.base;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.feature.ITooltipProvider;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.RenderUtils;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ScreenUtils;

@OnlyIn(value=Dist.CLIENT)
public class SubItemCheckbox
extends Button
implements ITooltipProvider {
    Button.OnPress listener;
    ItemStack display;
    ItemStack subDisplay;
    boolean drawBackground = true;
    boolean checked;
    TextureAtlasSprite[] sprites;
    Component tooltip;

    public SubItemCheckbox(int x, int y, int width, int height, Button.OnPress onPress, ItemStack display, ItemStack subDisplay, boolean checked) {
        this(x, y, width, height, onPress, display, subDisplay);
        this.setChecked(checked);
    }

    public SubItemCheckbox(int x, int y, int width, int height, Button.OnPress onPress, ItemStack display, ItemStack subDisplay) {
        super(x, y, width, height, (Component)Component.m_237119_(), null);
        this.listener = onPress;
        this.display = display;
        this.subDisplay = subDisplay;
        Map<String, TextureAtlasSprite> sprites = IC2Textures.getMappedEntriesIC2("misc/gui");
        this.sprites = new TextureAtlasSprite[]{sprites.get("yes"), sprites.get("no")};
    }

    public boolean isChecked() {
        return this.checked;
    }

    public SubItemCheckbox disableBackground() {
        this.drawBackground = false;
        return this;
    }

    public SubItemCheckbox setChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

    public SubItemCheckbox setIcons(ItemStack main, ItemStack sub) {
        this.display = main;
        this.subDisplay = sub;
        return this;
    }

    public SubItemCheckbox setToolTip(Component toolTip) {
        this.tooltip = toolTip;
        return this;
    }

    public SubItemCheckbox setToolTip(String s, Object ... args) {
        return this.setToolTip((Component)Component.m_237110_((String)s, (Object[])args));
    }

    public SubItemCheckbox setToolTip(String s) {
        return this.setToolTip((Component)Component.m_237115_((String)s));
    }

    public void m_5691_() {
        if (this.listener != null) {
            this.listener.m_93750_((Button)this);
        } else {
            this.checked = !this.checked;
        }
    }

    public void m_6303_(PoseStack mStack, int mouseX, int mouseY, float p_230431_4_) {
        if (this.f_93624_) {
            Minecraft mc = Minecraft.m_91087_();
            this.f_93622_ = mouseX >= this.f_93620_ && mouseY >= this.f_93621_ && mouseX < this.f_93620_ + this.f_93618_ && mouseY < this.f_93621_ + this.f_93619_;
            int k = this.m_7202_(this.m_198029_());
            ScreenUtils.blitWithBorder((PoseStack)mStack, (ResourceLocation)f_93617_, (int)this.f_93620_, (int)this.f_93621_, (int)0, (int)(46 + k * 20), (int)this.f_93618_, (int)this.f_93619_, (int)200, (int)20, (int)2, (int)3, (int)2, (int)2, (float)this.m_93252_());
            this.m_7906_(mStack, mc, mouseX, mouseY);
            Component buttonText = this.m_6035_();
            int strWidth = mc.f_91062_.m_92852_((FormattedText)buttonText);
            int ellipsisWidth = mc.f_91062_.m_92895_("...");
            if (strWidth > this.f_93618_ - 6 && strWidth > ellipsisWidth) {
                buttonText = Component.m_237113_((String)(mc.f_91062_.m_92854_((FormattedText)buttonText, this.f_93618_ - 6 - ellipsisWidth).getString() + "..."));
            }
            SubItemCheckbox.m_93215_((PoseStack)mStack, (Font)mc.f_91062_, (Component)buttonText, (int)(this.f_93620_ + this.f_93618_ / 2), (int)(this.f_93621_ + (this.f_93619_ - 8) / 2), (int)this.getFGColor());
            RenderSystem.m_157456_((int)0, (ResourceLocation)InventoryMenu.f_39692_);
            int xOffset = this.f_93618_ - this.f_93618_ / 2;
            int yOffset = this.f_93619_ - this.f_93619_ / 2;
            this.renderItem(mStack, this.display, this.f_93620_, this.f_93621_, this.f_93618_, this.f_93619_, 0.0f, mc);
            RenderSystem.m_157456_((int)0, (ResourceLocation)InventoryMenu.f_39692_);
            IC2Screen.drawTexture(mStack, this.f_93620_ + xOffset + 1, (float)(this.f_93621_ + yOffset) + 1.5f, this.m_93252_(), this.sprites[this.checked ? 0 : 1], (float)this.f_93618_ / 2.4f, (float)this.f_93619_ / 2.4f);
            this.renderItem(mStack, this.subDisplay, this.f_93620_, this.f_93621_ + yOffset, this.f_93618_ / 2, this.f_93619_ / 2, 15.0f, mc);
        }
    }

    public void renderItem(PoseStack matrix, ItemStack stack, int x, int y, int width, int height, float zOffset, Minecraft mc) {
        matrix.m_85836_();
        matrix.m_85837_((double)x, (double)y, (double)zOffset);
        float xScale = 20.0f / (float)width;
        float yScale = 20.0f / (float)height;
        matrix.m_85841_(1.0f / xScale, 1.0f / yScale, 1.0f);
        Lighting.m_84931_();
        RenderSystem.m_69482_();
        RenderUtils.renderGuiItem(mc.m_91291_(), matrix, stack, 2.0f, 2.0f);
        RenderSystem.m_69465_();
        matrix.m_85849_();
    }

    @Override
    public void addToolTip(IC2Screen gui, int x, int y, Consumer<Component> tooltips) {
        if (this.m_198029_() && this.tooltip != null) {
            tooltips.accept(this.tooltip);
        }
    }
}

