/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.block.machines.containers.hv;

import ic2.core.block.machines.components.hv.MassFabricatorComponent;
import ic2.core.block.machines.tiles.hv.MassFabricatorTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.slot.FilterSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class MassFabricatorContainer
extends ContainerComponent<MassFabricatorTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/hv/gui_matter.png");

    public MassFabricatorContainer(MassFabricatorTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(new FilterSlot(key, 0, 80, 56, T -> key.getRecipes().getRecipe(T, false) != null));
        this.m_38897_(FilterSlot.createOutputSlot(key, 1, 80, 19));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new MassFabricatorComponent(key));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

