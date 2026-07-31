/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.transport.item.container;

import ic2.core.block.transport.item.components.FilteredExtractionTubeComponent;
import ic2.core.block.transport.item.tubes.FilteredExtractionTubeTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.FilterComponent;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.GhostSlot;
import ic2.core.inventory.slot.LockedSlot;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FilterExtractionTubeContainer
extends ContainerComponent<FilteredExtractionTubeTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/tubes/gui_filter_extraction_tube.png");

    public FilterExtractionTubeContainer(FilteredExtractionTubeTileEntity key, Player player, int id) {
        super(key, player, id);
        SimpleInventory inventory = new SimpleInventory(16);
        this.m_38897_(new GhostSlot(key.filterInventory, 0, 142, 17, SpecialFilters.ANY_ITEM));
        this.m_38897_(new FilterSlot(key.upgradeInventory, 0, 168, 17, new SimpleFilter((ItemLike)IC2Items.OVERCLOCKER_UPGRADE)));
        this.m_38897_(new FilterSlot(key.upgradeInventory, 1, 168, 35, new SimpleFilter((ItemLike)IC2Items.IMPORT_UPGRADE_SIMPLE)));
        for (int i = 0; i < 16; ++i) {
            int xPos = i % 4;
            int yPos = i / 4;
            this.m_38897_(new LockedSlot(inventory, i, 8 + xPos * 18, 17 + yPos * 18));
        }
        this.addPlayerInventoryWithOffset(player.m_150109_(), 8, 9);
        this.addComponent(new FilteredExtractionTubeComponent(key, inventory));
        this.addComponent(new FilterComponent(this.getPreviewOffset()));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(192, 173);
        screen.setPlayerInventoryOffset(119, 0);
        screen.setFlag(4);
        screen.clearFlag(1);
    }

    @Override
    public Vec2i getPreviewButtonOffset() {
        return new Vec2i(0, -11);
    }

    @Override
    public int getInventorySize() {
        return 19;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    public void m_150399_(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (slotId == 0) {
            ItemStack stack = this.m_142621_();
            ((FilteredExtractionTubeTileEntity)this.getHolder()).filterInventory.setStackInSlot(slotId, stack.m_41619_() ? ItemStack.f_41583_ : StackUtil.copyWithSize(stack, 1));
            return;
        }
        if (slotId >= 3 && slotId < 19) {
            return;
        }
        super.m_150399_(slotId, dragType, clickTypeIn, player);
    }
}

