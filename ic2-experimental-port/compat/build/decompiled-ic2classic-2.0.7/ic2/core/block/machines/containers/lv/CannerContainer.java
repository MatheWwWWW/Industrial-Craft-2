/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.block.machines.containers.lv;

import ic2.core.block.machines.tiles.lv.CannerTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.gui.components.simple.ProgressComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.UpgradeSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class CannerContainer
extends ContainerComponent<CannerTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/lv/gui_canner.png");
    public static final Box2i PROGRESS_BOX = new Box2i(74, 35, 34, 16);
    public static final Vec2i PROGRESS_POS = new Vec2i(176, 14);
    public static final Box2i ENERGY_BOX = new Box2i(31, 27, 14, 14);
    public static final Vec2i ENERGY_POS = new Vec2i(176, 0);

    public CannerContainer(CannerTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createDischargeSlot(key, key.tier, 0, 30, 45));
        this.m_38897_(new FilterSlot(key, 1, 69, 53, T -> key.getValidContainer(T, true) > 0));
        this.m_38897_(new FilterSlot(key, 2, 69, 17, key::canInsertItem));
        this.m_38897_(FilterSlot.createOutputSlot(key, 3, 119, 35));
        this.m_38897_(FilterSlot.createOutputSlot(key, 4, 119, 59));
        for (int i = 0; i < 4; ++i) {
            this.m_38897_(new UpgradeSlot(key, 5 + i, 152, 8 + i * 18));
        }
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new ProgressComponent(PROGRESS_BOX, key, PROGRESS_POS, false));
        this.addComponent(new ChargebarComponent(new Box2i(31, 27, 14, 14), key, ENERGY_POS, true));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

