/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.inv.container;

import ic2.core.inventory.container.ItemContainer;
import ic2.core.inventory.filter.ClassFilter;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.item.food_and_drink.TinCanItem;
import ic2.core.item.inv.inventory.FoodStorageModuleInventory;
import ic2.core.platform.registries.IC2Items;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FoodStorageModuleContainer
extends ItemContainer<FoodStorageModuleInventory> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/items/gui_food_storage.png");

    public FoodStorageModuleContainer(FoodStorageModuleInventory key, Player player, int id, int windowID) {
        super(key, player, id, windowID);
        SimpleFilter empty = new SimpleFilter((ItemLike)IC2Items.TIN_CAN);
        ClassFilter full = new ClassFilter(TinCanItem.class);
        this.m_38897_(new FilterSlot(key, 0, 44, 24, full));
        this.m_38897_(new FilterSlot(key, 1, 62, 24, full));
        this.m_38897_(new FilterSlot(key, 2, 98, 24, empty));
        this.m_38897_(new FilterSlot(key, 3, 116, 24, empty));
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, -17);
    }

    @Override
    public Component getName() {
        return IC2Items.FOOD_STORAGE_MODULE.m_7626_(new ItemStack((ItemLike)IC2Items.FOOD_STORAGE_MODULE));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(176, 149);
    }
}

