/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.storage.container;

import ic2.core.block.base.tiles.impls.BaseChargingBenchTileEntity;
import ic2.core.block.storage.components.ChargingBenchComponent;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.slot.ArmorSlot;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.SlotBase;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChargingBenchContainer
extends ContainerComponent<BaseChargingBenchTileEntity> {
    public static final EquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/storage/gui_charge_bench.png");
    public static final ResourceLocation TEXTURE_SORTED = new ResourceLocation("ic2", "textures/gui_sprites/blocks/storage/gui_charge_bench_sorted.png");
    public static final Box2i CHARGE_BOX = new Box2i(30, 48, 12, 66);
    public static final Vec2i CHARGE_POS = new Vec2i(176, 0);

    public ChargingBenchContainer(BaseChargingBenchTileEntity key, Player player, int id) {
        super(key, player, id);
        int i;
        for (i = 0; i < 16; ++i) {
            this.m_38897_(new FilterSlot(key, i, 49 + i % 4 * 18, 46 + i / 4 * 18, key.createFilter(i)));
        }
        this.m_38897_(FilterSlot.createDischargeSlot(key, key.tier, 16, 153, 100));
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 48);
        for (i = 0; i < 4; ++i) {
            this.m_38897_(new ArmorSlot(player, VALID_EQUIPMENT_SLOTS[i], 39 - i, 8, 46 + i * 18));
        }
        this.addCurioSlots(new Vec2i(0, 0));
        this.applySlots(null);
        this.addComponent(new ChargebarComponent(CHARGE_BOX, key, CHARGE_POS, true).setStepSize(5, 1));
        this.addComponent(new ChargingBenchComponent(key));
    }

    public void applySlots(Player player) {
        SlotBase slot;
        int i;
        for (i = 0; i < 16; ++i) {
            slot = (SlotBase)((Object)this.f_38839_.get(i));
            slot.setBackground(InventoryMenu.f_39692_, new ResourceLocation("ic2:misc/gui/charge_slot"));
            slot.setX(49 + i % 4 * 18);
        }
        if (((BaseChargingBenchTileEntity)this.getHolder()).sortedMode) {
            for (i = 0; i < 4; ++i) {
                slot = (Slot)this.f_38839_.get(i * 4);
                slot.setBackground(InventoryMenu.f_39692_, new ResourceLocation(ArmorSlot.ARMOR_SLOT_TEXTURES[3 - i]));
            }
            Slot slot2 = (Slot)this.f_38839_.get(5);
            slot2.setBackground(null, null);
            slot2 = (Slot)this.f_38839_.get(9);
            slot2.setBackground(InventoryMenu.f_39692_, new ResourceLocation("item/empty_armor_slot_shield"));
            if (player != null && ((BaseChargingBenchTileEntity)this.getHolder()).isSimulating()) {
                NonNullList toInsert = NonNullList.m_122779_();
                for (int i2 = 0; i2 < 4; ++i2) {
                    slot2 = (Slot)this.f_38839_.get(i2 * 4);
                    if (!slot2.m_6657_() || slot2.m_5857_(slot2.m_7993_())) continue;
                    toInsert.add((Object)slot2.m_7993_());
                    slot2.m_5852_(ItemStack.f_41583_);
                }
                if (!toInsert.isEmpty()) {
                    IItemTransporter transporter = TransporterManager.getTransporter(player);
                    int m = toInsert.size();
                    for (int i3 = 0; i3 < m; ++i3) {
                        ItemStack stack = (ItemStack)toInsert.get(i3);
                        for (int j = 0; j < 16; ++j) {
                            slot2 = (Slot)this.f_38839_.get(j);
                            if (slot2.m_6657_() || !slot2.m_5857_(stack)) continue;
                            slot2.m_5852_(stack);
                            stack = ItemStack.f_41583_;
                            break;
                        }
                        if (stack.m_41619_()) continue;
                        stack.m_41774_(transporter.addItem(stack, Direction.DOWN, false));
                        if (stack.m_41613_() <= 0) continue;
                        player.m_36176_(stack, false);
                    }
                }
            }
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(176, 213);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

