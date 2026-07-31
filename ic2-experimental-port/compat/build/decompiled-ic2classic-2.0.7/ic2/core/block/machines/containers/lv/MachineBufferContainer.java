/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.block.machines.containers.lv;

import ic2.core.block.machines.tiles.lv.MachineBufferTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.components.simple.BufferBoxComponent;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.slot.SlotBase;
import ic2.core.inventory.slot.UpgradeSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class MachineBufferContainer
extends ContainerComponent<MachineBufferTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/lv/gui_machine_buffer.png");
    public static final Vec2i CHARGE_POS = new Vec2i(176, 0);
    public static final Box2i CHARGE_BOX = new Box2i(25, 35, 14, 14);

    public MachineBufferContainer(MachineBufferTileEntity key, Player player, int id) {
        super(key, player, id);
        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 3; ++y) {
                this.m_38897_(new SlotBase(key, x + y * 3, 62 + 18 * x, 15 + y * 18));
            }
        }
        for (int i = 0; i < 4; ++i) {
            this.m_38897_(new UpgradeSlot(key, 9 + i, 152, 8 + 18 * i));
        }
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new ChargebarComponent(CHARGE_BOX, key, CHARGE_POS, true));
        this.addComponent(new BufferBoxComponent(key));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

