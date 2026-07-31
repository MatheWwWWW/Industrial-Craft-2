/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.containers.hv;

import ic2.api.items.electric.IMiningDrill;
import ic2.api.items.electric.IScanner;
import ic2.core.block.machines.containers.hv.RocketMinerComponent;
import ic2.core.block.machines.containers.lv.BasicMachineContainer;
import ic2.core.block.machines.tiles.hv.RocketMinerTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.ClassFilter;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.SlotBase;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RocketMinerContainer
extends ContainerComponent<RocketMinerTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/hv/gui_rocket_miner.png");
    public static final Box2i CHARGE_BOX = new Box2i(154, 4, 14, 14);
    public static final Vec2i INV_OFFSET = new Vec2i(75, 36);
    public static final Vec2i PREVIEW_OFFSET = new Vec2i(86, 25);
    public static final Vec2i COMPARATOR_OFFSET = new Vec2i(86, 36);

    public RocketMinerContainer(RocketMinerTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createDischargeSlot(key, key.tier, 0, 8, 16));
        this.m_38897_(new FilterSlot(key, 1, 44, 16, new ClassFilter(IScanner.class)));
        this.m_38897_(new FilterSlot(key, 2, 62, 16, new ClassFilter(IMiningDrill.class)));
        this.m_38897_(new FilterSlot(key, 5, 80, 16, new SimpleFilter((ItemLike)IC2Blocks.RECYCLER)));
        this.m_38897_(new FilterSlot(key, 9, 44, 34, new SimpleFilter((ItemLike)IC2Items.MINER_REMOTE)));
        this.m_38897_(new FilterSlot(key, 6, 62, 34, new SimpleFilter((ItemLike)IC2Items.COBBLEGEN_UPGRADE)));
        this.m_38897_(new FilterSlot(key, 10, 98, 16, new SimpleFilter((ItemLike)IC2Blocks.CHUNKLOADER)));
        this.m_38897_(new FilterSlot(key, 3, 134, 16, new SimpleFilter((ItemLike)IC2Blocks.MINING_PIPE_SHAFT)));
        this.m_38897_(new FilterSlot(key, 8, 134, 34, new SimpleFilter((ItemLike)IC2Items.CELL_EMPTY)).setBackground(new ResourceLocation("ic2", "misc/gui/fluid_drain")));
        for (int i = 0; i < 36; ++i) {
            this.m_38897_(new SlotBase(key, 11 + i, 8 + i % 9 * 18, 58 + i / 9 * 18));
        }
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 58);
        this.addComponent(new ChargebarComponent(new Box2i(9, 35, 14, 14), key, BasicMachineContainer.CHARGE_POS, true));
        this.addComponent(new RocketMinerComponent(key));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.modifySize(0, 58);
    }

    @Override
    public int getInventorySize() {
        return super.getInventorySize() - 2;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public Vec2i getInvButtonOffset() {
        return INV_OFFSET;
    }

    @Override
    public Vec2i getPreviewButtonOffset() {
        return PREVIEW_OFFSET;
    }

    @Override
    public Vec2i getComparatorButtonOffset() {
        return COMPARATOR_OFFSET;
    }
}

