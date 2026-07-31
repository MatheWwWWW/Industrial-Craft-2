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
package ic2.core.block.machines.containers.lv;

import ic2.core.block.machines.tiles.lv.WoodGassifierTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.TagFilter;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
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

public class ElectricWoodGassifierContainer
extends ContainerComponent<WoodGassifierTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/lv/gui_electric_wood_gassifier.png");
    public static Box2i CHARGE_BOX = new Box2i(46, 36, 14, 14);
    public static Vec2i CHARGE_POS = new Vec2i(176, 0);
    public static Box2i PROGRESS_BOX = new Box2i(69, 34, 24, 16);
    public static Vec2i PROGRESS_POS = new Vec2i(176, 14);
    public static Box2i TANK_BOX = new Box2i(132, 26, 12, 41);
    public static Vec2i TANK_BAR_POS = new Vec2i(176, 72);
    public static Vec2i TANK_FLUID_POS = new Vec2i(176, 31);
    public static Vec2i TANK_GLASS_POS = new Vec2i(188, 31);

    public ElectricWoodGassifierContainer(WoodGassifierTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createDischargeSlot(key, key.tier, 0, 46, 53));
        this.m_38897_(new FilterSlot(key, 1, 46, 17, new TagFilter((TagKey<Item>)ItemTags.f_13182_)));
        this.m_38897_(FilterSlot.createOutputSlot(key, 2, 106, 35));
        this.m_38897_(FilterSlot.createFluidFillSlot((IHasInventory)key, 3, 151, 24, IC2Fluids.WOOD_GAS));
        this.m_38897_(FilterSlot.createFluidOutputSlot(key, 4, 151, 45));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new ChargebarComponent(CHARGE_BOX, key, CHARGE_POS, true));
        this.addComponent(new ProgressComponent(PROGRESS_BOX, key, PROGRESS_POS, false));
        this.addComponent(new PumpComponent(TANK_BOX, key, TANK_FLUID_POS, TANK_BAR_POS, TANK_GLASS_POS, "gui.ic2.gas_tank"));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

