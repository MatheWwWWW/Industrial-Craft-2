/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.block.machines.containers.ev;

import ic2.core.block.machines.tiles.ev.PlasmafierTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.gui.components.simple.PumpComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;

public class PlasmafierContainer
extends ContainerComponent<PlasmafierTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/ev/gui_plasmafier.png");
    public static final Box2i PROGRESS_BOX = new Box2i(82, 24, 12, 41);
    public static final Vec2i PROGRESS_POS = new Vec2i(176, 41);
    public static final Vec2i PROGRESS_FLUID = new Vec2i(176, 0);
    public static final Vec2i PROGRESS_GLASS = new Vec2i(188, 0);

    public PlasmafierContainer(PlasmafierTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(new FilterSlot(key, 0, 44, 35, new SimpleFilter((ItemLike)IC2Items.UUMATTER)));
        this.m_38897_(new FilterSlot(key, 1, 116, 23, new SimpleFilter((ItemLike)IC2Items.CELL_EMPTY)));
        this.m_38897_(FilterSlot.createOutputSlot(key, 2, 116, 45));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new PumpComponent(PROGRESS_BOX, key, PROGRESS_FLUID, PROGRESS_POS, PROGRESS_GLASS, "gui.ic2.plasma"));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

