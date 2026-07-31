/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.block.generators.containers;

import ic2.core.block.generators.tiles.SolarPanelTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.components.simple.FlagBarComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class SolarPanelContainer
extends ContainerComponent<SolarPanelTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/gui_solar.png");
    public static final Box2i MAIN_BOX = new Box2i(80, 45, 14, 14);
    public static final Vec2i MAIN_POS = new Vec2i(176, 0);

    public SolarPanelContainer(SolarPanelTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createChargeSlot(key, key.tier, 0, 80, 26));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(FlagBarComponent.createActiveBar(MAIN_BOX, key, MAIN_POS));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

