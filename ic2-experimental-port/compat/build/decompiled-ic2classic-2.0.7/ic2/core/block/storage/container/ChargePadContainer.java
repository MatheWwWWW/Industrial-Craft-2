/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.storage.container;

import ic2.core.block.base.tiles.impls.BaseChargePadTileEntity;
import ic2.core.block.storage.components.PadComponent;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.slot.ChargePadSlot;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.UpgradeSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChargePadContainer
extends ContainerComponent<BaseChargePadTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/storage/gui_charge_pad.png");
    public static final Vec2i OFFSET = new Vec2i(0, -11);

    public ChargePadContainer(BaseChargePadTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(this.createDischargeSlot(key));
        int slot = 1;
        if (key.data.getBaseTier() > 1) {
            this.m_38897_(new ChargePadSlot(key, slot++, 152, 58));
            this.m_38897_(new ChargePadSlot(key, slot++, 152, 29));
        }
        int left = key.inventory.getSlotCount() - slot;
        this.m_38897_(new UpgradeSlot(key, slot++, 98, 38));
        if (left > 1) {
            this.m_38897_(new UpgradeSlot(key, slot++, 88, 58));
            if (left > 2) {
                this.m_38897_(new UpgradeSlot(key, slot++, 108, 58));
            }
        }
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new PadComponent(key));
        this.addComponent(new ChargebarComponent(new Box2i(31, 19, 12, 51), key, new Vec2i(176, 0), true));
    }

    protected Slot createDischargeSlot(BaseChargePadTileEntity key) {
        return FilterSlot.createDischargeSlot(key, key.tier, 0, 8, 56);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.f_96546_ = false;
        screen.setMaxSize(176, 169);
        screen.setPlayerInventoryOffset(0, -3);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public Vec2i getPreviewButtonOffset() {
        return OFFSET;
    }
}

