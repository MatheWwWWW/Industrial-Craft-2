/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.ev;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IPumpTile;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.PumpComparator;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.machines.containers.ev.PlasmafierContainer;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PlasmafierTileEntity
extends BaseElectricTileEntity
implements ITickListener,
ITileGui,
IPumpTile {
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    public int plasma;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    public int uuMatter;

    public PlasmafierTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 3, 2048, 5120000);
        this.addNetworkFields("plasma");
        this.addGuiFields("uuMatter");
        this.addComparator(new PumpComparator("plasma", ComparatorNames.PLASMA, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.IMPORT, 0);
        handler.registerSlotAccess(AccessRule.IMPORT, 1);
        handler.registerSlotAccess(AccessRule.EXPORT, 2);
        handler.registerSlotsForSide(DirectionList.DOWN.invert(), 0, 1);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 2);
        handler.registerInputFilter(new SimpleFilter((ItemLike)IC2Items.UUMATTER), 0);
        handler.registerInputFilter(new SimpleFilter((ItemLike)IC2Items.CELL_EMPTY), 1);
        handler.registerInputFilter(SpecialFilters.ALWAYS_FALSE, 2);
        handler.registerNamedSlot(SlotType.INPUT, 0);
        handler.registerNamedSlot(SlotType.CONTAINER, 1);
        handler.registerNamedSlot(SlotType.OUTPUT, 2);
    }

    @Override
    public boolean supportsNotify() {
        return false;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.PLASMAFIER;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new PlasmafierContainer(this, player, windowID);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putShort(compound, "uuMatter", (int)((short)this.uuMatter), 0);
        NBTUtils.putShort(compound, "plasma", (int)((short)this.plasma), 0);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.uuMatter = compound.m_128451_("uuMatter");
        this.plasma = compound.m_128451_("plasma");
    }

    @Override
    public void onTick() {
        if (this.uuMatter < 50 && this.clock(40) && ((ItemStack)this.inventory.get(0)).m_41720_() == IC2Items.UUMATTER) {
            this.uuMatter += 100;
            ((ItemStack)this.inventory.get(0)).m_41774_(1);
            this.updateGuiField("uuMatter");
        }
        if (this.hasEnergy(10240) && this.uuMatter > 0 && this.plasma < 10000) {
            this.useEnergy(10240);
            --this.uuMatter;
            ++this.plasma;
            this.updateGuiField("uuMatter");
            this.updateTileField("plasma");
        }
        if (this.plasma >= 1000 && this.clock(50) && ((ItemStack)this.inventory.get(1)).m_41720_() == IC2Items.CELL_EMPTY) {
            this.plasma -= 1000;
            if (((ItemStack)this.inventory.get(2)).m_41619_()) {
                this.inventory.set(2, (Object)new ItemStack((ItemLike)IC2Items.CELL_PLASMA));
                ((ItemStack)this.inventory.get(1)).m_41774_(1);
            } else if (((ItemStack)this.inventory.get(2)).m_41720_() == IC2Items.CELL_PLASMA && StackUtil.getStackSizeLeft((ItemStack)this.inventory.get(2)) > 0) {
                ((ItemStack)this.inventory.get(2)).m_41769_(1);
                ((ItemStack)this.inventory.get(1)).m_41774_(1);
            }
            this.updateTileField("plasma");
        }
        this.handleComparators();
    }

    @Override
    public int getPumpProgress() {
        return this.plasma;
    }

    @Override
    public int getPumpMaxProgress() {
        return 10000;
    }
}

