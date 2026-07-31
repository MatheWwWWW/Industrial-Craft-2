/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.block.machines.containers.mv;

import ic2.core.block.machines.tiles.mv.CropHarvesterTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.gui.components.simple.AreaOfEffectComponent;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.slot.CallbackSlot;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class CropHarvesterContainer
extends ContainerComponent<CropHarvesterTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/mv/gui_crop_harvester.png");
    public static final Vec2i CHARGE_POS = new Vec2i(176, 0);
    public static final Box2i CHARGE_BOX = new Box2i(20, 36, 14, 14);

    public CropHarvesterContainer(CropHarvesterTileEntity key, Player player, int id) {
        super(key, player, id);
        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 3; ++y) {
                this.m_38897_(FilterSlot.createOutputSlot(key, x + y * 3, 62 + 18 * x, 16 + y * 18));
            }
        }
        for (int i = 0; i < 4; ++i) {
            this.m_38897_(new CallbackSlot(key.upgrades, i, 152, 8 + 18 * i, SpecialFilters.CROP_HARVESTER_FILTER, slot -> key.updateUpgrades()));
        }
        this.m_38897_(new CallbackSlot(key.upgrades, 4, 134, 62, SpecialFilters.CROP_HARVESTER_FILTER, slot -> key.updateUpgrades()));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new ChargebarComponent(CHARGE_BOX, key, CHARGE_POS, true));
        this.addComponent(new AreaOfEffectComponent(key, new Box2i(7, 61, 50, 12)));
    }

    @Override
    public int getInventorySize() {
        return ((CropHarvesterTileEntity)this.getHolder()).inventorySize + ((CropHarvesterTileEntity)this.getHolder()).upgrades.getSlotCount();
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

