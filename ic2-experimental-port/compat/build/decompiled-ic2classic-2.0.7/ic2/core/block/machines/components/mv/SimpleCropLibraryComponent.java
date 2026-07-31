/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.mv;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.base.tiles.impls.BaseCropLibraryTileEntity;
import ic2.core.block.machines.components.base.BaseCropLibraryComponent;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.math.geometry.Box2i;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SimpleCropLibraryComponent
extends BaseCropLibraryComponent {
    public static final int COLOR = ColorUtils.darker(ColorUtils.CYAN);
    static final Box2i TYPES = new Box2i(55, 106, 62, 9);
    static final Box2i STATS = new Box2i(55, 118, 62, 9);

    public SimpleCropLibraryComponent(BaseCropLibraryTileEntity tile) {
        super(tile);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        super.drawBackground(matrix, mouseX, mouseY, partialTicks);
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        float min = this.tile.syncer.getCropCount();
        float max = this.tile.storage.getTypeLimit();
        this.gui.drawColoredRegion(matrix, x + 55, y + 106, min / max * 63.0f, 9.0f, COLOR);
        min = this.tile.syncer.getStatCount();
        max = this.tile.storage.getTypeLimit() * this.tile.storage.getStatLimit();
        this.gui.drawColoredRegion(matrix, x + 55, y + 118, min / max * 63.0f, 9.0f, COLOR);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        super.drawForeground(matrix, mouseX, mouseY);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.simple_crop_library.types"), 22, 106, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.simple_crop_library.stats"), 26, 118, 0x404040);
    }

    @Override
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (TYPES.isInBox(mouseX, mouseY)) {
            tooltips.accept((Component)this.translate("gui.ic2.simple_crop_library.type_count", this.tile.syncer.getCropCount(), this.tile.storage.getTypeLimit()));
        }
        if (STATS.isInBox(mouseX, mouseY)) {
            tooltips.accept((Component)this.translate("gui.ic2.simple_crop_library.stat_count", this.tile.syncer.getStatCount(), this.tile.storage.getStatLimit() * this.tile.storage.getTypeLimit()));
        }
    }
}

