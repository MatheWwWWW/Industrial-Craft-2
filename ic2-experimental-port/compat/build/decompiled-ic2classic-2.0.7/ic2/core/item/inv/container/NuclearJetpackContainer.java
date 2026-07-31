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
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.item.inv.inventory.NuclearJetpackInventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NuclearJetpackContainer
extends ItemContainer<NuclearJetpackInventory> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/items/gui_nuclear_jetpack.png");

    public NuclearJetpackContainer(NuclearJetpackInventory key, Player player, int id, int windowID) {
        super(key, player, id, windowID);
        IFilter filter = key::isUsefulItem;
        for (int y = 0; y < 5; ++y) {
            for (int x = 0; x < 5; ++x) {
                this.m_38897_(new FilterSlot(key, x + y * 5, 44 + x * 18, 18 + y * 18, filter));
            }
        }
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 56);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(176, 222);
    }
}

