/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.containers.nv;

import ic2.api.items.armor.IArmorModule;
import ic2.core.block.machines.components.nv.ArmorComponent;
import ic2.core.block.machines.tiles.nv.ArmorConfiguratorTileEntity;
import ic2.core.block.storage.container.ContainerEnergyStorage;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.IQuickMoveBlocker;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Vec2i;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArmorConfiguratorContainer
extends ContainerComponent<ArmorConfiguratorTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/stone/gui_armor_configurator.png");
    boolean disableChanges = false;

    public ArmorConfiguratorContainer(ArmorConfiguratorTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(new ArmorSlot(key, 0, 80, 117).setBackground(InventoryMenu.f_39692_, new ResourceLocation("item/empty_armor_slot_chestplate")));
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.m_38897_(new ModuleSlot(key, y, x, 8 + 18 * x, 29 + y * 30));
            }
        }
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 56);
        for (int i = 0; i < 4; ++i) {
            this.m_38897_(new ic2.core.inventory.slot.ArmorSlot(player, ContainerEnergyStorage.VALID_EQUIPMENT_SLOTS[i], 39 - i, 116 + i * 18, 117));
        }
        this.addComponent(new ArmorComponent(key));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(194, 222);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public int getInventorySize() {
        return 32;
    }

    @Override
    public Vec2i getInvButtonOffset() {
        return new Vec2i(0, -11);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void m_182410_(int state, List<ItemStack> items, ItemStack carried) {
        this.disableChanges = true;
        super.m_182410_(state, items, carried);
        this.disableChanges = false;
    }

    public void m_182406_(int slotID, int state, ItemStack stack) {
        super.m_182406_(slotID, state, stack);
    }

    public static class ArmorSlot
    extends FilterSlot {
        ArmorConfiguratorTileEntity inv;

        public ArmorSlot(ArmorConfiguratorTileEntity inv, int index, int xPosition, int yPosition) {
            super(inv.armory.getArmor(), index, xPosition, yPosition, IArmorModule.IArmorModuleHolder::isValidModularArmor);
            this.inv = inv;
        }

        @Override
        public void m_5852_(ItemStack stack) {
            super.m_5852_(stack);
            this.inv.armory.onSlotSet();
        }

        public void m_142406_(Player thePlayer, ItemStack stack) {
            super.m_142406_(thePlayer, stack);
            this.inv.armory.reset();
        }
    }

    public class ModuleSlot
    extends FilterSlot
    implements IQuickMoveBlocker {
        final ArmorConfiguratorTileEntity f_40217_;
        final int offset;

        public ModuleSlot(ArmorConfiguratorTileEntity inv, int offset, int index, int xPosition, int yPosition) {
            super(inv, index, xPosition, yPosition, null);
            this.f_40217_ = inv;
            this.offset = offset;
        }

        public boolean m_6659_() {
            return this.f_40217_.armory.getSize(this.offset) > this.getSlotIndex();
        }

        @Override
        public boolean m_5857_(ItemStack stack) {
            return this.f_40217_.armory.canInsert(stack, this.offset);
        }

        @Override
        public IHasInventory getInventory() {
            return this.f_40217_.armory.getSlotInventory(this.offset);
        }

        @Override
        public void m_5852_(ItemStack stack) {
            ItemStack prev = this.m_7993_();
            super.m_5852_(stack);
            if (!stack.m_41619_() && !StackUtil.isStackEqual(stack, prev)) {
                if (prev != null) {
                    this.f_40217_.armory.onExtracted(prev);
                }
                this.f_40217_.armory.onInserted(stack);
            }
        }

        public void m_142406_(Player thePlayer, ItemStack stack) {
            this.f_40217_.armory.onExtracted(stack);
            super.m_142406_(thePlayer, stack);
        }
    }
}

