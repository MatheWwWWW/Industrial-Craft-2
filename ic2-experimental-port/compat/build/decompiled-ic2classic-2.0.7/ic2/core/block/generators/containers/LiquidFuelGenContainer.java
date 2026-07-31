/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.fluids.IFluidTank
 */
package ic2.core.block.generators.containers;

import ic2.core.block.generators.tiles.LiquidFuelGenTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.gui.components.simple.FuelComponent;
import ic2.core.inventory.gui.components.simple.TankComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.IFluidTank;

public class LiquidFuelGenContainer
extends ContainerComponent<LiquidFuelGenTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/gui_liquid_generator.png");
    public static final Box2i ENERGY_BOX = new Box2i(92, 34, 24, 17);
    public static final Vec2i ENERGY_POS = new Vec2i(176, 14);
    public static final Box2i FUEL_BOX = new Box2i(70, 35, 14, 14);
    public static final Vec2i FUEL_POS = new Vec2i(176, 0);
    public static final Box2i TANK_BOX = new Box2i(48, 15, 16, 58);
    public static final Vec2i TANK_POS = new Vec2i(176, 31);

    public LiquidFuelGenContainer(LiquidFuelGenTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createChargeSlot(key, key.tier, 0, 134, 35));
        this.m_38897_(new FilterSlot(key, 1, 26, 17, key::canInsertFuel).setBackground(new ResourceLocation("ic2", "misc/gui/fluid_drain")));
        this.m_38897_(FilterSlot.createFluidOutputSlot(key, 2, 26, 53));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new FuelComponent(FUEL_BOX, key, FUEL_POS, true));
        this.addComponent(new ChargebarComponent(ENERGY_BOX, key, ENERGY_POS, false));
        this.addComponent(new TankComponent(TANK_BOX, TANK_POS, (IFluidTank)key.tank));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

