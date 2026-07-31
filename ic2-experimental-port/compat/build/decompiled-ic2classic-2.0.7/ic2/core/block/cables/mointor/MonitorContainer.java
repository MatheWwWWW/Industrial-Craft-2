/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.block.cables.mointor;

import ic2.api.items.IDisplayProvider;
import ic2.core.block.cables.mointor.MonitorTileEntity;
import ic2.core.block.machines.components.misc.MonitorComponent;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.components.simple.FilterComponent;
import ic2.core.inventory.slot.FilterSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class MonitorContainer
extends ContainerComponent<MonitorTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/misc/gui_monitor.png");

    public MonitorContainer(MonitorTileEntity key, Player player, int id) {
        super(key, player, id);
        for (int i = 0; i < 8; ++i) {
            this.m_38897_(new FilterSlot(key, i, 26 + i % 4 * 18, 19 + i / 4 * 18, T -> T.m_41720_() instanceof IDisplayProvider));
        }
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new MonitorComponent(key));
        this.addComponent(new FilterComponent(this.getInventoryOffset()));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

