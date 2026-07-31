/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 */
package ic2.core.block.machines.containers.nv;

import ic2.core.block.machines.tiles.nv.StoneWoodGassifierTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.TagFilter;
import ic2.core.inventory.gui.components.simple.FuelComponent;
import ic2.core.inventory.gui.components.simple.ProgressComponent;
import ic2.core.inventory.gui.components.simple.PumpComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.platform.registries.IC2Fluids;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class WoodGassifierContainer
extends ContainerComponent<StoneWoodGassifierTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/stone/gui_wood_gasser.png");
    public static final Box2i TANK_BOX = new Box2i(131, 26, 12, 41);
    public static final Vec2i TANK_BAR = new Vec2i(176, 72);
    public static final Vec2i TANK_FLUID = new Vec2i(176, 31);
    public static final Vec2i TANK_GLASS = new Vec2i(188, 31);
    public static final Box2i PROGRESS_BOX = new Box2i(59, 34, 24, 16);
    public static final Vec2i PROGRESS_POS = new Vec2i(176, 14);
    public static final Box2i FUEL_BOX = new Box2i(36, 36, 14, 14);
    public static final Vec2i FUEL_POS = new Vec2i(176, 0);

    public WoodGassifierContainer(StoneWoodGassifierTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createFuelSlot(key, 0, 36, 53, true));
        this.m_38897_(new FilterSlot(key, 1, 36, 17, new TagFilter((TagKey<Item>)ItemTags.f_13182_)));
        this.m_38897_(FilterSlot.createOutputSlot(key, 2, 95, 35));
        this.m_38897_(FilterSlot.createFluidFillSlot((IHasInventory)key, 3, 152, 17, IC2Fluids.WOOD_GAS));
        this.m_38897_(FilterSlot.createFluidOutputSlot(key, 4, 152, 53));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new FuelComponent(FUEL_BOX, key, FUEL_POS, true));
        this.addComponent(new ProgressComponent(PROGRESS_BOX, key, PROGRESS_POS, false));
        this.addComponent(new PumpComponent(TANK_BOX, key, TANK_FLUID, TANK_BAR, TANK_GLASS, "gui.ic2.gas_tank"));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

