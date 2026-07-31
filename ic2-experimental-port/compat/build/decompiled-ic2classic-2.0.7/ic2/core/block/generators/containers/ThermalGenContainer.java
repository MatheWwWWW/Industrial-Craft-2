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

import ic2.core.block.generators.components.ThermalGeneratorComponent;
import ic2.core.block.generators.tiles.ThermalGeneratorTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.components.simple.AreaOfEffectComponent;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.gui.components.simple.FlagBarComponent;
import ic2.core.inventory.gui.components.simple.FuelComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.platform.registries.IC2Fluids;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class ThermalGenContainer
extends ContainerComponent<ThermalGeneratorTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/gui_thermal_generator.png");
    public static final Box2i FUEL_BOX = new Box2i(66, 36, 14, 14);
    public static final Vec2i FUEL_POS = new Vec2i(176, 0);
    public static final Box2i ENERGY_BOX = new Box2i(94, 35, 24, 17);
    public static final Vec2i ENERGY_POS = new Vec2i(176, 14);
    public static final Vec2i PASSIVE_FUEL = new Vec2i(176, 31);

    public ThermalGenContainer(ThermalGeneratorTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createChargeSlot(key, key.tier, 0, 131, 36));
        this.m_38897_(FilterSlot.createFluidDrainSlot((IHasInventory)key, 1, 65, 17, new Fluid[]{Fluids.f_76195_, IC2Fluids.BLAZING_LAVA}));
        this.m_38897_(FilterSlot.createFluidOutputSlot(key, 2, 65, 53));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new ThermalGeneratorComponent(key));
        this.addComponent(new FuelComponent(FUEL_BOX, key, FUEL_POS, true));
        this.addComponent(FlagBarComponent.createWorkBar(FUEL_BOX, key, PASSIVE_FUEL));
        this.addComponent(new ChargebarComponent(ENERGY_BOX, key, ENERGY_POS, false));
        this.addComponent(new AreaOfEffectComponent(key, new Box2i(119, 70, 50, 12)));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

