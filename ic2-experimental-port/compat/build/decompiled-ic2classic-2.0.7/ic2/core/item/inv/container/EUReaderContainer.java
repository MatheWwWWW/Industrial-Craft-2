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
import ic2.core.item.inv.components.EUReaderComponent;
import ic2.core.item.inv.inventory.EUReaderInventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EUReaderContainer
extends ItemContainer<EUReaderInventory> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/items/gui_eu_reader.png");

    public EUReaderContainer(EUReaderInventory key, Player player, int id, int windowID) {
        super(key, player, id, windowID);
        this.addHiddenPlayerInventory(player.m_150109_());
        this.addComponent(new EUReaderComponent(key));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        super.onGuiLoaded(screen);
        screen.modifySize(70, 43);
        screen.clearFlag(1);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public int getInventorySize() {
        return 0;
    }
}

