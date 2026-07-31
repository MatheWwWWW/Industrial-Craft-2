/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.TooltipFlag$Default
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.wiki.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.platform.rendering.RenderUtils;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.wiki.base.managers.IWikiProvider;
import ic2.core.wiki.components.BaseWikiComponent;
import ic2.core.wiki.recipes.providers.IItemReference;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemListComponent
extends BaseWikiComponent {
    public static final Box2i UP_ARROW = new Box2i(20, 20, 8, 10);
    public static final Box2i DOWN_ARROW = new Box2i(88, 20, 8, 10);
    int rows;
    IItemReference items;
    int offset;

    public ItemListComponent(int rows, IItemReference items) {
        super(rows * 17 + 15);
        this.rows = rows;
        this.items = items;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderBackground(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        for (int column = 0; column < this.rows; ++column) {
            for (int row = 0; row < 6; ++row) {
                screen.drawTextureRegion(stack, x + 7 + row * 17, y + column * 17, 124.0f, 0.0f, 18.0f, 18.0f);
            }
        }
        screen.bindDefaultTexture();
        List<ItemStack> stacks = this.items.getItemList();
        int limit = this.items.getItemList().size() / 6 - Math.max(1, this.rows - 1);
        screen.drawTextureRegion(stack, x + 20, y + 20 + (this.rows - 1) * 17, 21.0f, 208.0f, 8.0f, 10.0f, ColorUtils.darker(-1, this.offset <= 0 || UP_ARROW.isInBox((mouseX += screen.getGuiLeft()) - x, (mouseY += screen.getGuiTop()) - y - (this.rows - 1) * 17) ? 0.75f : 1.0f));
        screen.drawTextureRegion(stack, x + 88, y + 20 + (this.rows - 1) * 17, 31.0f, 208.0f, 8.0f, 10.0f, ColorUtils.darker(-1, this.offset >= limit || DOWN_ARROW.isInBox(mouseX - x, mouseY - y - (this.rows - 1) * 17) ? 0.75f : 1.0f));
        ItemRenderer render = screen.getRenderItem();
        if (stacks.isEmpty()) {
            return;
        }
        for (int column = 0; column < this.rows; ++column) {
            for (int row = 0; row < 6; ++row) {
                int index = row + (this.offset + column) * 6;
                if (index >= stacks.size()) continue;
                RenderUtils.renderGuiItem(render, stack, stacks.get(index), x + 8 + row * 17, y + 1 + column * 17);
            }
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderForeground(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        List<ItemStack> stacks = this.items.getItemList();
        screen.drawCenterString(stack, (Component)this.string(this.offset + " / " + (stacks.size() / 6 - Math.max(1, this.rows - 1))), x + 60, y + 21 + (this.rows - 1) * 17, -12829907);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTips(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if ((mouseX -= (x += 8)) < 0 || (mouseY -= y) < 0) {
            return;
        }
        int xIndex = mouseX / 17;
        int yIndex = mouseY / 17;
        if (xIndex >= 6 || yIndex >= this.rows) {
            return;
        }
        int index = (yIndex + this.offset) * 6 + xIndex;
        List<ItemStack> list = this.items.getItemList();
        if (index >= list.size()) {
            return;
        }
        list.get(index).m_41651_((Player)screen.getPlayer(), (TooltipFlag)TooltipFlag.Default.NORMAL).forEach(tooltips);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseScroll(IC2Screen screen, int x, int y, int mouseX, int mouseY, int scroll, IWikiProvider provider) {
        int limit;
        int newAmount;
        if ((mouseX -= (x += 9)) >= 7 && mouseX < 109 && (mouseY -= (y += 9)) >= 0 && mouseY < this.rows * 17 && scroll != 0 && this.offset != (newAmount = Mth.m_14045_((int)(this.offset - scroll), (int)0, (int)(limit = this.items.getItemList().size() / 6 - Math.max(1, this.rows - 1))))) {
            this.offset = newAmount;
            IC2.AUDIO.playSound(Minecraft.m_91087_().f_91074_, SoundEvents.f_11713_.m_11660_(), AudioManager.SoundType.STATIC, 1.0f, 2.0f);
            return true;
        }
        return false;
    }

    @Override
    public boolean onMouseClick(IC2Screen screen, int x, int y, int mouseX, int mouseY, IWikiProvider provider) {
        if (UP_ARROW.isInBox(mouseX - (x += 9), mouseY - (y += 9) - (this.rows - 1) * 17)) {
            if (this.offset > 0) {
                --this.offset;
                IC2.AUDIO.playSound(Minecraft.m_91087_().f_91074_, SoundEvents.f_11713_.m_11660_(), AudioManager.SoundType.STATIC, 1.0f, 2.0f);
            }
            return true;
        }
        if (DOWN_ARROW.isInBox(mouseX - x, mouseY - y - (this.rows - 1) * 17)) {
            int limit = this.items.getItemList().size() / 6 - Math.max(1, this.rows - 1);
            if (this.offset < limit) {
                ++this.offset;
                IC2.AUDIO.playSound(Minecraft.m_91087_().f_91074_, SoundEvents.f_11713_.m_11660_(), AudioManager.SoundType.STATIC, 1.0f, 2.0f);
            }
            return true;
        }
        return false;
    }
}

