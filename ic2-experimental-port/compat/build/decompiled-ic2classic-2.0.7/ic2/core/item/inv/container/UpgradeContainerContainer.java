/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.inv.container;

import ic2.core.inventory.container.ItemContainer;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.item.inv.inventory.UpgradeContainerInventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UpgradeContainerContainer
extends ItemContainer<UpgradeContainerInventory> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/items/gui_upgrade_container.png");

    public UpgradeContainerContainer(UpgradeContainerInventory key, Player player, int id, int windowID) {
        super(key, player, id, windowID);
        for (int i = 0; i < 3; ++i) {
            this.m_38897_(new FilterSlot(key, i, 62 + i * 18, 17, SpecialFilters.UPGRADE_CONTAINER));
        }
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, -32);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.modifySize(0, -32);
    }
}

