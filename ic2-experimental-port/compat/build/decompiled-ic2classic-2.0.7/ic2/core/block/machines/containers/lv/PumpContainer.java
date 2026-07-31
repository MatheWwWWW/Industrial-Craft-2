/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.block.machines.containers.lv;

import ic2.core.block.machines.tiles.lv.PumpTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.gui.components.simple.PumpComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class PumpContainer
extends ContainerComponent<PumpTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/lv/gui_pump.png");
    public static Box2i PROGRESS_BOX = new Box2i(99, 26, 12, 41);
    public static Vec2i PROGRESS_POS = new Vec2i(176, 55);
    public static Vec2i PROGRESS_FLUID = new Vec2i(176, 14);
    public static Vec2i PROGRESS_GLASS = new Vec2i(188, 14);
    public static final Box2i CHARGE_BOX = new Box2i(62, 36, 14, 14);
    public static final Vec2i CHARGE_POS = new Vec2i(176, 0);

    public PumpContainer(PumpTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createDischargeSlot(key, key.tier, 0, 62, 53));
        this.m_38897_(FilterSlot.createFluidFillSlot((IHasInventory)key, 1, 122, 17, (IFluidHandler)key.tank));
        this.m_38897_(FilterSlot.createFluidOutputSlot(key, 2, 122, 53));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new ChargebarComponent(CHARGE_BOX, key, CHARGE_POS, true));
        this.addComponent(new PumpComponent(PROGRESS_BOX, key, PROGRESS_FLUID, PROGRESS_POS, PROGRESS_GLASS, "gui.ic2.pump_charge"));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

