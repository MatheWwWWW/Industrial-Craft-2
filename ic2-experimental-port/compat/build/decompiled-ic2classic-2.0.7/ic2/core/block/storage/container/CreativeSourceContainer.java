/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.block.storage.container;

import ic2.core.block.generators.components.CreativeSourceComponent;
import ic2.core.block.storage.tiles.CreativeSourceTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class CreativeSourceContainer
extends ContainerComponent<CreativeSourceTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/storage/gui_adjustable_transformer.png");

    public CreativeSourceContainer(CreativeSourceTileEntity key, Player player, int id) {
        super(key, player, id);
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new CreativeSourceComponent(key));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

