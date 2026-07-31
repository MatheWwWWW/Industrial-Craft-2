/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.block.machines.containers.mv;

import ic2.core.block.machines.components.mv.BaseTeleporterComponent;
import ic2.core.block.machines.tiles.mv.BaseTeleporterTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class BaseTeleporterContainer
extends ContainerComponent<BaseTeleporterTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/mv/gui_base_teleporter.png");

    public BaseTeleporterContainer(BaseTeleporterTileEntity key, Player player, int id) {
        super(key, player, id);
        this.addHiddenPlayerInventory(player.m_150109_());
        this.addComponent(new BaseTeleporterComponent(key));
        this.addComponent(new ChargebarComponent(new Box2i(152, 16, 14, 14), key, new Vec2i(176, 0), true));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

