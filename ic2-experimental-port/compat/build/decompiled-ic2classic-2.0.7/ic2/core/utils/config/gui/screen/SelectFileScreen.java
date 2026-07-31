/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.NativeImage
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.locale.Language
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.world.level.storage.LevelSummary
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.utils.config.gui.screen;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.config.gui.api.BackgroundTexture;
import ic2.core.utils.config.gui.api.IModConfig;
import ic2.core.utils.config.gui.config.Element;
import ic2.core.utils.config.gui.config.ListScreen;
import ic2.core.utils.config.gui.screen.ConfigScreen;
import ic2.core.utils.config.gui.widgets.GuiUtils;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class SelectFileScreen
extends ListScreen {
    private static final Component TEXT = Component.m_237115_((String)"gui.ic2.select_world");
    IModConfig config;
    Screen parent;

    public SelectFileScreen(Component name, BackgroundTexture customTexture, Screen parent, IModConfig config) {
        super(name, customTexture);
        this.config = config;
        this.parent = parent;
    }

    @Override
    protected void collectElements(Consumer<Element> elements) {
        for (IModConfig.IConfigTarget target : this.config.getPotentialFiles()) {
            elements.accept(new WorldElement(target, this.config, this.parent, this.f_96539_));
        }
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        int x = this.f_96543_ / 2;
        int y = this.f_96544_;
        this.m_142416_((GuiEventListener)new ExtendedButton(x - 80, y - 27, 160, 20, (Component)Component.m_237115_((String)"gui.ic2.back"), T -> this.m_7379_()));
    }

    @Override
    protected int getElementHeight() {
        return 28;
    }

    public void m_7379_() {
        this.f_96541_.m_91152_(this.parent);
    }

    @Override
    public void m_6305_(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.m_6305_(stack, mouseX, mouseY, partialTicks);
        this.f_96547_.m_92889_(stack, TEXT, (float)(this.f_96543_ / 2 - this.f_96547_.m_92852_((FormattedText)TEXT) / 2), 8.0f, -1);
    }

    @Override
    public void m_7861_() {
        this.allEntries.forEach(this::cleanup);
        super.m_7861_();
    }

    private void cleanup(Element element) {
        if (element instanceof WorldElement) {
            ((WorldElement)element).cleanup();
        }
    }

    private static class WorldElement
    extends Element {
        IModConfig config;
        IModConfig.IConfigTarget target;
        Screen parent;
        Button button;
        Component title;
        Component path;
        DynamicTexture texture;
        Component prevName;

        public WorldElement(IModConfig.IConfigTarget target, IModConfig config, Screen parent, Component prevName) {
            super((Component)Component.m_237113_((String)target.getName()));
            this.target = target;
            this.config = config;
            this.parent = parent;
            this.prevName = prevName;
        }

        @Override
        public void init() {
            this.button = new ExtendedButton(0, 0, 62, 20, (Component)Component.m_237115_((String)"gui.ic2.pick"), this::onPick);
            if (this.target instanceof IModConfig.WorldConfigTarget) {
                IModConfig.WorldConfigTarget world = (IModConfig.WorldConfigTarget)this.target;
                LevelSummary sum = world.getSummary();
                this.loadIcon(sum.m_230875_());
                this.title = Component.m_237113_((String)sum.m_78361_());
                this.path = Component.m_237113_((String)sum.m_78358_()).m_130940_(ChatFormatting.GRAY);
            } else {
                this.title = Component.m_237113_((String)this.target.getName());
                Path folder = this.target.getFolder();
                int index = folder.getNameCount();
                this.path = Component.m_237113_((String)folder.subpath(index - 3, index).toString()).m_130940_(ChatFormatting.GRAY);
            }
        }

        @Override
        public void m_6311_(PoseStack poseStack, int x, int top, int left, int width, int height, int mouseX, int mouseY, boolean selected, float partialTicks) {
            this.button.f_93620_ = left + width - 62;
            this.button.f_93621_ = top + 2;
            this.button.m_6305_(poseStack, mouseX, mouseY, partialTicks);
            this.font.m_92877_(poseStack, Language.m_128107_().m_5536_(GuiUtils.ellipsize((FormattedText)this.title, 140, this.font)), (float)(left + 5), (float)(top + 4), -1);
            this.font.m_92877_(poseStack, Language.m_128107_().m_5536_(GuiUtils.ellipsize((FormattedText)this.path, 140, this.font)), (float)(left + 5), (float)(top + 13), -1);
            if (this.texture != null) {
                RenderSystem.m_157429_((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                RenderSystem.m_157453_((int)0, (int)this.texture.m_117963_());
                GuiUtils.drawTextureRegion(poseStack, left - 24, top, 0.0f, 0.0f, 24.0f, 24.0f, 64.0f, 64.0f, 64.0f, 64.0f);
            }
        }

        private void loadIcon(Path iconFile) {
            try (InputStream stream = Files.newInputStream(iconFile, new OpenOption[0]);){
                NativeImage image = NativeImage.m_85058_((InputStream)stream);
                if (image == null || image.m_84982_() != 64 || image.m_85084_() != 64) {
                    return;
                }
                this.texture = new DynamicTexture(image);
                this.texture.m_117985_();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public List<? extends GuiEventListener> m_6702_() {
            return ObjectLists.singleton((Object)this.button);
        }

        private void onPick(Button button) {
            IModConfig config = this.config.loadFromFile(this.target.getConfigFile());
            if (config == null) {
                this.mc.m_91152_(this.parent);
                return;
            }
            this.mc.m_91152_((Screen)new ConfigScreen((Component)this.prevName.m_6881_().m_7220_((Component)Component.m_237113_((String)" > ").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})).m_7220_((Component)this.path.m_6881_().m_130940_(ChatFormatting.WHITE)), config, this.parent, this.owner.getCustomTexture()));
        }

        private void cleanup() {
            if (this.texture == null) {
                return;
            }
            this.texture.close();
            this.texture = null;
        }
    }
}

