/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.block.machines.containers.nv;

import ic2.core.block.machines.tiles.nv.StoneCannerTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.gui.components.simple.FuelComponent;
import ic2.core.inventory.gui.components.simple.ProgressComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;

public class StoneCannerContainer
extends ContainerComponent<StoneCannerTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/stone/gui_canner.png");
    public static final Box2i PROGRESS_BOX = new Box2i(74, 36, 34, 16);
    public static final Box2i FUEL_BOX = new Box2i(31, 28, 13, 13);
    public static final Vec2i PROGRESS_POS = new Vec2i(176, 14);
    public static final Vec2i FUEL_POS = new Vec2i(176, 0);

    public StoneCannerContainer(StoneCannerTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createFuelSlot(key, 0, 30, 45, false));
        this.m_38897_(new FilterSlot(key, 1, 69, 17, T -> key.getFoodAmount(T) > 0));
        this.m_38897_(new FilterSlot(key, 2, 69, 53, new SimpleFilter((ItemLike)IC2Items.TIN_CAN)));
        this.m_38897_(FilterSlot.createOutputSlot(key, 3, 119, 35));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new FuelComponent(FUEL_BOX, key, FUEL_POS, true));
        this.addComponent(new ProgressComponent(PROGRESS_BOX, key, PROGRESS_POS, false));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

