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

import ic2.core.block.generators.tiles.WaterMillTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.components.simple.AreaOfEffectComponent;
import ic2.core.inventory.gui.components.simple.FuelComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class WaterGenContainer
extends ContainerComponent<WaterMillTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/gui_water_generator.png");
    public static Box2i FUEL_BOX = new Box2i(80, 36, 14, 14);
    public static Vec2i FUEL_POS = new Vec2i(176, 0);

    public WaterGenContainer(WaterMillTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createChargeSlot(key, key.tier, 0, 80, 17));
        this.m_38897_(FilterSlot.createFluidDrainSlot((IHasInventory)key, 1, 80, 53, new Fluid[]{Fluids.f_76193_}));
        this.m_38897_(FilterSlot.createFluidOutputSlot(key, 2, 103, 53));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new FuelComponent(FUEL_BOX, key, FUEL_POS, true));
        this.addComponent(new AreaOfEffectComponent(key, new Box2i(119, 70, 50, 12)));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

