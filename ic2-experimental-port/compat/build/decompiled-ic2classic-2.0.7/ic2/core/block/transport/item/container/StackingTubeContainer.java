/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.transport.item.container;

import ic2.core.block.transport.item.components.StackingTubeComponent;
import ic2.core.block.transport.item.tubes.StackingTubeTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StackingTubeContainer
extends ContainerComponent<StackingTubeTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/tubes/gui_stacking_tube.png");

    public StackingTubeContainer(StackingTubeTileEntity key, Player player, int id) {
        super(key, player, id);
        this.addComponent(new StackingTubeComponent(key));
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 56);
    }

    @Override
    public int getInventorySize() {
        return 0;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setYSize(222);
    }
}

