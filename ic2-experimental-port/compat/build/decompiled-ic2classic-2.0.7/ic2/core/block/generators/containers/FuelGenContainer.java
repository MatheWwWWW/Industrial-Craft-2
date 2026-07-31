/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.block.generators.containers;

import ic2.core.block.generators.tiles.FuelGenTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.gui.components.simple.FuelComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class FuelGenContainer
extends ContainerComponent<FuelGenTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/gui_generator.png");
    public static Box2i FUEL_BOX = new Box2i(66, 36, 14, 14);
    public static Vec2i FUEL_POS = new Vec2i(176, 0);
    public static Box2i ENERGY_BOX = new Box2i(94, 35, 24, 17);
    public static Vec2i ENERGY_POS = new Vec2i(176, 14);

    public FuelGenContainer(FuelGenTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createChargeSlot(key, key.tier, 0, 65, 17));
        this.m_38897_(FilterSlot.createFuelSlot(key, 1, 65, 53, false));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new FuelComponent(FUEL_BOX, key, FUEL_POS, true));
        this.addComponent(new ChargebarComponent(ENERGY_BOX, key, ENERGY_POS, false));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

