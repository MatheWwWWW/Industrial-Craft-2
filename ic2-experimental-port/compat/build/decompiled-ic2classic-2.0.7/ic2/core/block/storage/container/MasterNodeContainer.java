/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.storage.container;

import ic2.core.block.storage.components.MasterComponent;
import ic2.core.block.storage.tiles.RedirectorMasterTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MasterNodeContainer
extends ContainerComponent<RedirectorMasterTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/mv/gui_master_node.png");

    public MasterNodeContainer(RedirectorMasterTileEntity key, Player player, int id) {
        super(key, player, id);
        this.addHiddenPlayerInventory(player.m_150109_());
        this.addComponent(new MasterComponent(key));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(120, 133);
        screen.clearFlag(1);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

