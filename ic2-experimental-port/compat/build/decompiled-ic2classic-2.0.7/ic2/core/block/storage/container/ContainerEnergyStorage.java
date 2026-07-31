/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.storage.container;

import ic2.core.block.base.tiles.impls.BaseEnergyStorageTileEntity;
import ic2.core.block.storage.components.EnergyStorageComponent;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.slot.ArmorSlot;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ContainerEnergyStorage
extends ContainerComponent<BaseEnergyStorageTileEntity> {
    public static final EquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/storage/gui_electric_block.png");
    public static final Vec2i BUTTON_OFFSET_INV = new Vec2i(143, 67);
    public static final Vec2i BUTTON_OFFSET_PREVIEW = new Vec2i(154, 56);
    public static final Vec2i BUTTON_OFFSET_COMPARATOR = new Vec2i(121, 67);

    public ContainerEnergyStorage(BaseEnergyStorageTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createChargeSlot(key, key.tier, 0, 56 + key.getGuiOffset(), 17));
        this.m_38897_(FilterSlot.createDischargeSlot(key, key.tier, 1, 56 + key.getGuiOffset(), 53));
        this.addPlayerInventory(player.m_150109_());
        for (int i = 0; i < 4; ++i) {
            this.m_38897_(new ArmorSlot(player, VALID_EQUIPMENT_SLOTS[i], 39 - i, 8, 8 + i * 18));
        }
        this.addCurioSlots(new Vec2i(110, 56));
        this.addComponent(new EnergyStorageComponent(key));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        super.onGuiLoaded(screen);
        screen.setPlayerInventoryOffset(20, 0);
    }

    @Override
    public Vec2i getInvButtonOffset() {
        return BUTTON_OFFSET_INV;
    }

    @Override
    public Vec2i getPreviewButtonOffset() {
        return BUTTON_OFFSET_PREVIEW;
    }

    @Override
    public Vec2i getComparatorButtonOffset() {
        return BUTTON_OFFSET_COMPARATOR;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

