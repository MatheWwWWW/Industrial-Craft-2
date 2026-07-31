/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.block.generators.containers;

import ic2.core.block.generators.components.SolarTurbineComponent;
import ic2.core.block.generators.tiles.SolarTurbineTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.components.simple.FlagBarComponent;
import ic2.core.inventory.gui.components.simple.PumpComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class SolarTurbineContainer
extends ContainerComponent<SolarTurbineTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/gui_solar_turbine.png");
    public static final Box2i FLAG_BOX = new Box2i(64, 37, 14, 14);
    public static final Vec2i FLAG_POS = new Vec2i(176, 0);
    public static final Box2i PROGRESS_BOX = new Box2i(99, 26, 12, 41);
    public static final Vec2i PROGRESS_POS = new Vec2i(176, 55);
    public static final Vec2i PROGRESS_FLUID = new Vec2i(176, 14);
    public static final Vec2i PROGRESS_GLASS = new Vec2i(188, 14);

    public SolarTurbineContainer(SolarTurbineTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createChargeSlot(key, key.tier, 0, 62, 17));
        this.m_38897_(FilterSlot.createFluidDrainSlot((IHasInventory)key, 1, 122, 17, new Fluid[]{Fluids.f_76193_}));
        this.m_38897_(FilterSlot.createFluidOutputSlot(key, 2, 122, 53));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(FlagBarComponent.createActiveBar(FLAG_BOX, key, FLAG_POS));
        this.addComponent(new PumpComponent(PROGRESS_BOX, key, PROGRESS_FLUID, PROGRESS_POS, PROGRESS_GLASS, "gui.ic2.water_tank"));
        this.addComponent(new SolarTurbineComponent(key));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

