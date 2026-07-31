/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.inventory.Slot
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.containers.hv;

import ic2.api.items.electric.IScanner;
import ic2.core.block.machines.components.hv.ScannerComponent;
import ic2.core.block.machines.tiles.hv.OreScannerTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.ClassFilter;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.LockedSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class OreScannerContainer
extends ContainerComponent<OreScannerTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/hv/gui_ore_scanner.png");
    public static final Vec2i OFFSET = new Vec2i(20, 0);
    public static final Vec2i OFFSET_COMPARATOR = new Vec2i(9, 0);
    public static final Box2i CHARGE_BOX = new Box2i(135, 9, 14, 14);
    public static final Vec2i CHARGE_POS = new Vec2i(176, 0);

    public OreScannerContainer(OreScannerTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createDischargeSlot(key, key.tier, 0, 151, 9));
        this.m_38897_(new FilterSlot(key, 1, 7, 9, new ClassFilter(IScanner.class)));
        this.m_38897_(new LockedSlot(key.results, 0, 10, 32));
        this.m_38897_(new LockedSlot(key.results, 1, 10, 50));
        this.m_38897_(new LockedSlot(key.results, 2, 10, 68));
        this.m_38897_(new LockedSlot(key.results, 3, 10, 86));
        this.m_38897_(new LockedSlot(key.results, 4, 10, 104));
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 56);
        this.addComponent(new ChargebarComponent(CHARGE_BOX, key, CHARGE_POS, true));
        this.addComponent(new ScannerComponent(key));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    public void m_150399_(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        Slot slot;
        if (slotId >= 0 && slotId < this.f_38839_.size() && (slot = (Slot)this.f_38839_.get(slotId)) instanceof LockedSlot) {
            ((OreScannerTileEntity)this.getHolder()).displayIndexes(player, slot.getSlotIndex());
            return;
        }
        super.m_150399_(slotId, dragType, clickTypeIn, player);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(176, 227);
        screen.setPlayerInventoryOffset(0, -4);
    }

    @Override
    public Vec2i getPreviewButtonOffset() {
        return OFFSET;
    }

    @Override
    public Vec2i getComparatorButtonOffset() {
        return OFFSET_COMPARATOR;
    }
}

