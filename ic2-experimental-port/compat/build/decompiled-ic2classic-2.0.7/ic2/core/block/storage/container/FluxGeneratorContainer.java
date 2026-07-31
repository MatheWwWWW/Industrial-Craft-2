/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.block.storage.container;

import ic2.core.block.base.tiles.impls.BaseFluxGeneratorTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.special.FeItemFilter;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class FluxGeneratorContainer
extends ContainerComponent<BaseFluxGeneratorTileEntity> {
    public static final Vec2i CHARGE_POS = new Vec2i(176, 0);
    public static final Box2i CHARGE_BOX = new Box2i(80, 45, 14, 14);
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/storage/gui_flux_generator.png");

    public FluxGeneratorContainer(BaseFluxGeneratorTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(new FilterSlot(key, 0, 80, 26, FeItemFilter.CHARGE_FILTER).setBackground(new ResourceLocation("ic2", "misc/gui/charge_slot")));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new ChargebarComponent(CHARGE_BOX, key, CHARGE_POS, true));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

