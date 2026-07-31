/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.block.generators.containers;

import ic2.core.block.generators.components.WindTurbineComponent;
import ic2.core.block.generators.tiles.WindTurbineTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.components.simple.AreaOfEffectComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Box2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class WindTurbineContainer
extends ContainerComponent<WindTurbineTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/gui_wind_turbine.png");

    public WindTurbineContainer(WindTurbineTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createChargeSlot(key, key.tier, 0, 80, 26));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new WindTurbineComponent(key));
        this.addComponent(new AreaOfEffectComponent(key, new Box2i(119, 70, 50, 12)));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

