/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Window
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Matrix4f
 *  com.mojang.math.Vector3f
 *  com.mojang.math.Vector4f
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.wiki.components;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import ic2.core.block.rendering.world.impl.MultiBlock;
import ic2.core.block.rendering.world.impl.MultiBlockOverlay;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.inventory.gui.components.base.TexturedButton;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.item.inv.container.WikiBoxContainer;
import ic2.core.utils.math.SmootherFloat;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.wiki.base.managers.IWikiProvider;
import ic2.core.wiki.components.BaseWikiComponent;
import ic2.core.wiki.gui.BookMarkComponent;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class MultiBlockComponent
extends BaseWikiComponent {
    SmootherFloat xRotation = new SmootherFloat(30.0f, 0.1f);
    SmootherFloat yRotation = new SmootherFloat(45.0f, 0.1f);
    SmootherFloat scale = new SmootherFloat(1.0f, 0.1f);
    float lastMouseX = 0.0f;
    float lastMouseY = 0.0f;
    float initialScale;
    int yLimit = 0;
    boolean dragging = false;
    boolean disablePlacement;
    MultiBlock block;
    Button button;
    ToolTipButton down = new TexturedButton(0, 0, 16, 16, WikiBoxContainer.TEXTURE, new Box2i(50, 192, 12, 16), T -> ++this.yLimit).setToolTip("Remove Layer");
    ToolTipButton up = new TexturedButton(0, 0, 16, 16, WikiBoxContainer.TEXTURE, new Box2i(34, 192, 12, 16), T -> --this.yLimit).setToolTip("Add Layer");
    ToolTipButton reset = new TexturedButton(0, 0, 16, 16, WikiBoxContainer.TEXTURE, new Box2i(117, 219, 16, 16), T -> {
        this.yLimit = 0;
        this.scale.setTarget(this.initialScale);
        this.yRotation.setTarget(45.0f);
        this.xRotation.setTarget(30.0f);
    }).setFull().setToolTip("Reset");
    ItemCheckBox rotate;
    ItemCheckBox formed;

    public MultiBlockComponent(MultiBlock block, boolean disablePlacement) {
        super(disablePlacement ? 109 : 119);
        this.block = block;
        this.disablePlacement = disablePlacement;
        AABB axis = block.getBounds().m_82400_(0.5);
        int sizeX = Mth.m_14107_((double)axis.m_82362_());
        int sizeZ = Mth.m_14107_((double)axis.m_82385_());
        float maxX = 85.0f;
        float maxY = 85.0f;
        double baseScale = Math.sqrt(sizeX * sizeX + sizeZ * sizeZ);
        this.scale.setTarget((float)(-Math.min((double)maxX / baseScale, (double)(maxY / (float)Mth.m_14107_((double)axis.m_82376_())))));
        this.scale.forceFinish();
        this.initialScale = this.scale.getTarget();
    }

    @OnlyIn(value=Dist.CLIENT)
    private void render(IC2Screen screen, PoseStack stack, Button button, int mouseX, int mouseY, int x, int y, float partialTicks) {
        button.f_93620_ = x;
        button.f_93621_ = y;
        button.m_6305_(stack, mouseX + screen.getGuiLeft(), mouseY + screen.getGuiTop(), partialTicks);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderBackground(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        if (this.button == null) {
            this.button = new ExtendedButton(0, 0, 100, 14, (Component)this.translate("tooltip.ic2.multiblock.show"), T -> {
                MultiBlockOverlay.INSTANCE.setSelectedMultiBlock(this.block.copy());
                Minecraft.m_91087_().f_91080_.m_7379_();
            });
            this.button.f_93624_ = !this.disablePlacement;
            this.rotate = new ItemCheckBox(0, 0, 16, 16, null, new ItemStack((ItemLike)Items.f_42524_), false).setToolTip("Rotate");
            this.formed = new ItemCheckBox(0, 0, 16, 16, null, new ItemStack((ItemLike)Items.f_41966_), true).setToolTip("Formed");
        }
        this.button.f_93623_ = !MultiBlockOverlay.INSTANCE.hasSelectedMultiBlock();
        this.down.f_93623_ = (double)this.yLimit < this.block.getBounds().m_82376_() - 1.0;
        this.up.f_93623_ = this.yLimit > 0;
        this.render(screen, stack, this.button, mouseX, mouseY, x + 8, y + 105, partialTicks);
        this.render(screen, stack, (Button)this.down, mouseX, mouseY, x + 8, y + 88, partialTicks);
        this.render(screen, stack, (Button)this.up, mouseX, mouseY, x + 25, y + 88, partialTicks);
        this.render(screen, stack, (Button)this.reset, mouseX, mouseY, x + 42, y + 88, partialTicks);
        this.render(screen, stack, this.rotate, mouseX, mouseY, x + 59, y + 88, partialTicks);
        this.render(screen, stack, this.formed, mouseX, mouseY, x + 76, y + 88, partialTicks);
        screen.bindTexture(BookMarkComponent.TEXTURE);
        screen.drawTextureRegion(stack, x, y, 0.0f, 94.0f, 118.0f, 85.0f, 74.0f, 1.0f);
        stack.m_85836_();
        if (this.rotate.isChecked()) {
            this.yRotation.addTarget(1.0f);
        }
        if (this.dragging) {
            this.xRotation.addTarget((float)mouseY - this.lastMouseY);
            this.yRotation.addTarget((float)mouseX - this.lastMouseX);
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
        }
        this.yRotation.update(partialTicks);
        this.xRotation.update(partialTicks);
        this.scale.update(partialTicks);
        Matrix4f rotMat = new Matrix4f();
        rotMat.m_27624_();
        this.setupMatrix(stack, rotMat, this.xRotation.getValue(), this.yRotation.getValue(), x, y - 8);
        Vector4f eye = new Vector4f(0.0f, 0.0f, -100.0f, 1.0f);
        eye.m_123607_(rotMat);
        eye.m_123618_();
        MultiBufferSource.BufferSource buffers = Minecraft.m_91087_().m_91269_().m_110104_();
        RenderSystem.m_69472_();
        RenderSystem.m_69465_();
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        this.enableScissors(x, y, 118, 85);
        this.block.setYLimit(this.yLimit);
        stack.m_85836_();
        this.block.renderMultiBlock(stack, buffers.m_6299_(RenderType.m_110463_()), (Level)screen.getMinecraft().f_91073_, BlockPos.f_121853_.m_7918_(0, 512, 0), Rotation.NONE, this.formed.isChecked());
        this.block.setYLimit(0);
        stack.m_85849_();
        RenderSystem.m_69482_();
        buffers.m_109911_();
        RenderSystem.m_69471_();
        RenderSystem.m_69493_();
        stack.m_85849_();
    }

    protected final void enableScissors(int x, int y, int width, int height) {
        Window window = Minecraft.m_91087_().m_91268_();
        double scaledWidth = (double)window.m_85441_() / (double)window.m_85445_();
        double scaledHeight = (double)window.m_85442_() / (double)window.m_85446_();
        int bottom = y + height;
        RenderSystem.m_69488_((int)((int)((double)x * scaledWidth)), (int)((int)((double)window.m_85442_() - (double)bottom * scaledHeight)), (int)((int)((double)width * scaledWidth)), (int)((int)((double)height * scaledHeight)));
    }

    @OnlyIn(value=Dist.CLIENT)
    public void setupMatrix(PoseStack stack, Matrix4f matrix, float xRotation, float yRotation, int x, int y) {
        AABB axis = this.block.getBounds();
        Vec3 offset = this.block.getOffset();
        int sizeX = Mth.m_14107_((double)axis.m_82362_());
        int sizeY = Mth.m_14107_((double)axis.m_82376_());
        boolean odd = (sizeX & 1) == 1;
        float scale = Math.min(-1.0f, this.scale.getValue());
        stack.m_85837_(47.0, (double)(sizeY * 20), 100.0);
        stack.m_85837_((double)x + offset.m_7096_() + (double)(45.0f - (float)Math.min(sizeX, 3) * 15.0f), (double)y + offset.m_7098_() + (60.0 - axis.m_82376_() * 20.0), offset.m_7094_());
        stack.m_85841_(scale, scale, scale);
        stack.m_85837_((double)(-sizeX / 2), (double)(-sizeY / 2), 0.0);
        float offsetX = odd ? 0.5f : 0.0f;
        float offsetZ = odd ? 0.5f : 1.0f;
        stack.m_85837_(0.0, (double)((float)sizeY * 0.5f - 0.5f), 0.0);
        stack.m_85845_(Vector3f.f_122223_.m_122240_(-xRotation));
        matrix.m_27646_(Vector3f.f_122223_.m_122240_(xRotation));
        stack.m_85837_((double)offsetX, (double)((float)sizeY * -0.5f + 0.5f), (double)offsetZ);
        stack.m_85845_(Vector3f.f_122225_.m_122240_(yRotation));
        matrix.m_27646_(Vector3f.f_122225_.m_122240_(-yRotation));
        stack.m_85837_((double)(-offsetX), 0.0, (double)(-offsetZ));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(IC2Screen screen, int x, int y, int mouseX, int mouseY, IWikiProvider provider) {
        int xOff = screen.getGuiLeft();
        int yOff = screen.getGuiTop();
        if (this.button != null && this.button.m_6375_((double)(mouseX + xOff), (double)(mouseY + yOff), 0)) {
            return true;
        }
        if (this.down.m_6375_(mouseX + xOff, mouseY + yOff, 0) || this.up.m_6375_(mouseX + xOff, mouseY + yOff, 0) || this.reset.m_6375_(mouseX + xOff, mouseY + yOff, 0) || this.rotate.m_6375_(mouseX + xOff, mouseY + yOff, 0) || this.formed.m_6375_(mouseX + xOff, mouseY + yOff, 0)) {
            return true;
        }
        if (mouseX >= x && mouseX < x + 118 && mouseY >= y && mouseY <= y + 85) {
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
            this.dragging = true;
            return true;
        }
        return false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTips(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, Consumer<Component> tooltips) {
        this.down.addToolTip(screen, mouseX, mouseY, tooltips);
        this.up.addToolTip(screen, mouseX, mouseY, tooltips);
        this.reset.addToolTip(screen, mouseX, mouseY, tooltips);
        this.rotate.addToolTip(screen, mouseX, mouseY, tooltips);
        this.formed.addToolTip(screen, mouseX, mouseY, tooltips);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseScroll(IC2Screen screen, int x, int y, int mouseX, int mouseY, int scroll, IWikiProvider provider) {
        if (mouseX >= x && mouseX < x + 118 && mouseY >= y && mouseY <= y + 85) {
            this.scale.setTarget(Math.min(-1.0f, this.scale.getTarget() + (float)scroll * -1.0f));
            return true;
        }
        return false;
    }

    @Override
    public boolean onMouseRelease(IC2Screen screen, int x, int y, int mouseX, int mouseY) {
        if (this.dragging) {
            this.dragging = false;
            return true;
        }
        return false;
    }
}

