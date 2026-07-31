/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.block.machines.containers.lv;

import ic2.api.items.electric.IMiningDrill;
import ic2.api.items.electric.IScanner;
import ic2.core.block.machines.components.lv.MinerComponent;
import ic2.core.block.machines.containers.lv.BasicMachineContainer;
import ic2.core.block.machines.tiles.lv.MinerTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.ClassFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Box2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class MinerContainer
extends ContainerComponent<MinerTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/lv/gui_miner.png");
    public static final Box2i CHARGE_BOX = new Box2i(80, 41, 14, 14);

    public MinerContainer(MinerTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createDischargeSlot(key, key.tier, 0, 81, 59));
        this.m_38897_(new FilterSlot(key, 1, 117, 22, new ClassFilter(IScanner.class)));
        this.m_38897_(new FilterSlot(key, 2, 81, 22, SpecialFilters.BLOCK_FILTER));
        this.m_38897_(new FilterSlot(key, 3, 45, 22, new ClassFilter(IMiningDrill.class)));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new ChargebarComponent(CHARGE_BOX, key, BasicMachineContainer.CHARGE_POS, true));
        this.addComponent(new MinerComponent(key));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

