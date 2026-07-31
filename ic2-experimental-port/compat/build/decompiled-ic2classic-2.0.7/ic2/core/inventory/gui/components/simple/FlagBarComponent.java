/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 */
package ic2.core.inventory.gui.components.simple;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.tiles.readers.IWorkProvider;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import java.util.Set;
import java.util.function.BooleanSupplier;

public class FlagBarComponent
extends GuiWidget {
    BooleanSupplier base;
    Vec2i posTexture;

    public FlagBarComponent(Box2i box, BooleanSupplier base, Vec2i posTexture) {
        super(box);
        this.base = base;
        this.posTexture = posTexture;
    }

    public static FlagBarComponent createActiveBar(Box2i box, BaseTileEntity tile, Vec2i posTexture) {
        return new FlagBarComponent(box, tile::isActive, posTexture);
    }

    public static FlagBarComponent createWorkBar(Box2i box, IWorkProvider tile, Vec2i posTexture) {
        return new FlagBarComponent(box, tile::isWorking, posTexture);
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
    }

    @Override
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        if (this.base.getAsBoolean()) {
            this.gui.drawTextureRegion(matrix, this.box.getX() + this.gui.getGuiLeft(), this.box.getY() + this.gui.getGuiTop(), this.posTexture.getX(), this.posTexture.getY(), this.box.getWidth(), this.box.getHeight());
        }
    }
}

