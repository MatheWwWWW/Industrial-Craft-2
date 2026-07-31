/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.TooltipFlag$Default
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.wiki.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.crops.ICrop;
import ic2.core.block.crops.CropRegistry;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.item.misc.CropSeedItem;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.wiki.components.BaseWikiComponent;
import ic2.core.wiki.components.IWikiComponent;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CropComponent
extends BaseWikiComponent {
    ICrop crop;
    ItemStack baseSeed;
    ItemStack cropItem;
    ItemStack display;

    public CropComponent(ICrop crop) {
        super(36);
        this.crop = crop;
        this.baseSeed = CropRegistry.INSTANCE.getSeedForCrop(crop);
        this.cropItem = CropSeedItem.createStack(crop, 1, 1, 1, 4);
        this.display = CropRegistry.INSTANCE.getDisplayItem(crop.id());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addSearchString(Consumer<Component> listener) {
        listener.accept(this.crop.getName());
        listener.accept(this.crop.discoveredBy());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderBackground(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        screen.bindDefaultTexture();
        screen.drawTextureRegion(stack, x, y, 0.0f, 248.0f, 116.0f, 4.0f);
        screen.drawTextureRegion(stack, x, y + 30, 0.0f, 252.0f, 116.0f, 4.0f);
        screen.bindTexture(IWikiComponent.TEXTURE);
        screen.drawTextureRegion(stack, x + 10, y + 7, 158.0f, 0.0f, 20.0f, 20.0f);
        screen.drawTextureRegion(stack, x + 43, y + 8, 124.0f, 0.0f, 18.0f, 18.0f, 18.0f, 18.0f);
        screen.drawTextureRegion(stack, x + 67, y + 11, 124.0f, 18.0f, 16.0f, 12.0f, 16.0f, 12.0f);
        screen.drawTextureRegion(stack, x + 88, y + 5, 124.0f, 0.0f, 24.0f, 24.0f, 18.0f, 18.0f);
        screen.drawItemStack(stack, x + 11, y + 8, this.baseSeed, 18.0f, 18.0f, true, null);
        screen.drawItemStack(stack, x + 43, y + 8, this.cropItem, 18.0f, 18.0f);
        screen.drawItemStack(stack, x + 91, y + 8, this.display, 18.0f, 18.0f);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTips(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, Consumer<Component> tooltips) {
        Box2i box = new Box2i(11, 7, 18, 18);
        if (box.isInBox(mouseX - x, mouseY - y)) {
            this.baseSeed.m_41651_((Player)screen.getPlayer(), (TooltipFlag)TooltipFlag.Default.NORMAL).forEach(tooltips);
            tooltips.accept((Component)this.translate(this.baseSeed.m_41720_() == IC2Items.CROP_STICKS ? "tooltip.ic2.crops.breed_only.name" : "tooltip.ic2.crops.plant_only.name"));
        }
        if ((box = new Box2i(43, 8, 18, 18)).isInBox(mouseX - x, mouseY - y)) {
            this.cropItem.m_41651_((Player)screen.getPlayer(), (TooltipFlag)TooltipFlag.Default.NORMAL).forEach(tooltips);
        }
        if ((box = new Box2i(91, 8, 18, 18)).isInBox(mouseX - x, mouseY - y)) {
            this.display.m_41651_((Player)screen.getPlayer(), (TooltipFlag)TooltipFlag.Default.NORMAL).forEach(tooltips);
        }
    }
}

