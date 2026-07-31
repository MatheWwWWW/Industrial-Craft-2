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

import ic2.core.block.transport.item.components.TeleportTubeComponent;
import ic2.core.block.transport.item.tubes.TeleportTubeTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TeleportTubeContainer
extends ContainerComponent<TeleportTubeTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/tubes/gui_teleport_tube.png");

    public TeleportTubeContainer(TeleportTubeTileEntity key, Player player, int id) {
        super(key, player, id);
        this.addHiddenPlayerInventory(player.m_150109_());
        this.addComponent(new TeleportTubeComponent(key));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(100, 94);
        screen.clearFlag(1);
    }

    @Override
    public int getInventorySize() {
        return 0;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

