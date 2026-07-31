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
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.item.inv.components.HeatPumpComponent;
import ic2.core.item.inv.inventory.HeatPumpInventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HeatPumpContainer
extends ItemContainer<HeatPumpInventory> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/items/gui_heat_pump.png");

    public HeatPumpContainer(HeatPumpInventory key, Player player, int id, int windowID) {
        super(key, player, id, windowID);
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, -4);
        this.addComponent(new HeatPumpComponent(key));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(176, 162);
        screen.setPlayerInventoryOffset(110, 0);
    }

    @Override
    public int getInventorySize() {
        return 0;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

