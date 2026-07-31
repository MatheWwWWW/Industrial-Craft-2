/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.block.machines.containers.mv;

import ic2.core.block.machines.tiles.lv.CropMatronTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.components.simple.AreaOfEffectComponent;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class CropmatronContainer
extends ContainerComponent<CropMatronTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/mv/gui_crop_matron.png");
    public static final Box2i ENERGY_BOX = new Box2i(26, 38, 14, 14);
    public static final Vec2i ENERGY_POS = new Vec2i(176, 0);

    public CropmatronContainer(CropMatronTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(new FilterSlot(key, 0, 62, 20, key.filters[0]));
        this.m_38897_(new FilterSlot(key, 1, 62, 38, key.filters[0]));
        this.m_38897_(new FilterSlot(key, 2, 62, 56, key.filters[0]));
        this.m_38897_(new FilterSlot(key, 3, 98, 20, key.filters[1]));
        this.m_38897_(new FilterSlot(key, 4, 98, 38, key.filters[1]));
        this.m_38897_(new FilterSlot(key, 5, 98, 56, key.filters[1]));
        this.m_38897_(new FilterSlot(key, 6, 134, 20, key.filters[2]));
        this.m_38897_(new FilterSlot(key, 7, 134, 38, key.filters[2]));
        this.m_38897_(new FilterSlot(key, 8, 134, 56, key.filters[2]));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new ChargebarComponent(ENERGY_BOX, key, ENERGY_POS, true));
        this.addComponent(new AreaOfEffectComponent(key, new Box2i(7, 61, 50, 12)));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

