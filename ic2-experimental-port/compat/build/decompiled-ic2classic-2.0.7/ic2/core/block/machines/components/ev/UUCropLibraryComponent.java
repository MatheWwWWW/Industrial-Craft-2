/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.ev;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.components.base.BaseCropLibraryComponent;
import ic2.core.block.machines.tiles.ev.UUCropLibraryTileEntity;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.math.geometry.Box2i;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UUCropLibraryComponent
extends BaseCropLibraryComponent {
    public static final Box2i UU_BOX = new Box2i(141, 112, 7, 16);
    public static final int COLOR = ColorUtils.rgb(219, 0, 205);

    public UUCropLibraryComponent(UUCropLibraryTileEntity tile) {
        super(tile);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        super.drawBackground(matrix, mouseX, mouseY, partialTicks);
        float pixels = (float)UU_BOX.getHeight() * ((float)((UUCropLibraryTileEntity)this.tile).uu_matter / 512.0f);
        if (pixels > 0.0f) {
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + UU_BOX.getX(), (float)(this.gui.getGuiTop() + UU_BOX.getY()) + ((float)UU_BOX.getHeight() - pixels), UU_BOX.getWidth(), pixels, COLOR);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        super.addTooltips(matrix, mouseX, mouseY, tooltips);
        if (UU_BOX.isInBox(mouseX, mouseY)) {
            tooltips.accept((Component)this.translate("gui.ic2.uu_crop_library.stored", ((UUCropLibraryTileEntity)this.tile).uu_matter));
        }
    }
}

