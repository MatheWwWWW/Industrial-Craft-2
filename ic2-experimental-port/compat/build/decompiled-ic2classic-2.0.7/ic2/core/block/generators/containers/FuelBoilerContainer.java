/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.fluids.IFluidTank
 */
package ic2.core.block.generators.containers;

import ic2.core.block.generators.components.FuelBoilerComponent;
import ic2.core.block.generators.tiles.FuelBoilerTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.gui.components.simple.FuelComponent;
import ic2.core.inventory.gui.components.simple.TankComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.IFluidTank;

public class FuelBoilerContainer
extends ContainerComponent<FuelBoilerTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/gui_fuel_boiler.png");
    public static final Vec2i OFFSET = new Vec2i(156, 0);
    public static final Box2i WATER_TANK = new Box2i(30, 15, 16, 58);
    public static final Box2i STEAM_TANK = new Box2i(152, 15, 16, 58);
    public static final Vec2i TANK_POS = new Vec2i(176, 31);

    public FuelBoilerContainer(FuelBoilerTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createFluidDrainSlot((IHasInventory)key, 0, 8, 19, new Fluid[]{Fluids.f_76193_}));
        this.m_38897_(FilterSlot.createFluidOutputSlot(key, 1, 8, 53));
        this.m_38897_(new FilterSlot(key, 2, 80, 53, SpecialFilters.FUEL_CAN).setBackground(new ResourceLocation("ic2", "misc/gui/fuel_can")));
        this.m_38897_(FilterSlot.createOutputSlot(key, 3, 102, 53));
        this.m_38897_(new FilterSlot(key, 4, 58, 53, SpecialFilters.FUEL_CAN).setBackground(new ResourceLocation("ic2", "misc/gui/fuel_can")));
        this.m_38897_(new FilterSlot(key, 5, 58, 35, SpecialFilters.FUEL_CAN).setBackground(new ResourceLocation("ic2", "misc/gui/fuel_can")));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new TankComponent(WATER_TANK, TANK_POS, (IFluidTank)key.waterTank));
        this.addComponent(new TankComponent(STEAM_TANK, TANK_POS, (IFluidTank)key.steamTank));
        this.addComponent(new FuelBoilerComponent(key));
        this.addComponent(new FuelComponent(new Box2i(80, 34, 14, 14), key, new Vec2i(176, 0), true));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public Vec2i getPreviewButtonOffset() {
        return new Vec2i(22, -11);
    }
}

