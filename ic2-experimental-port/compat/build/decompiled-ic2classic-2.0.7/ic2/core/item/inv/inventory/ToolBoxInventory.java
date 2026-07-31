/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.inv.inventory;

import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.inv.PortableInventory;
import ic2.core.item.inv.container.ToolBoxContainer;
import ic2.core.item.tool.ToolBoxTool;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public abstract class ToolBoxInventory
extends PortableInventory {
    ToolBoxTool.ToolBoxData data;

    public ToolBoxInventory(Player owner, IHasHeldGui held, ItemStack stack, Slot slot, ToolBoxTool.ToolBoxData data) {
        super(owner, held, stack, slot);
        this.data = data;
    }

    public static ToolBoxInventory create(Player owner, IHasHeldGui held, ItemStack stack, Slot slot, final ToolBoxTool.ToolBoxData newData) {
        return new ToolBoxInventory(owner, held, stack, slot, newData){

            @Override
            public int getSlotCount() {
                return newData.getSlotCount();
            }
        };
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new ToolBoxContainer(this, player, this.getID(), windowID);
    }

    @Override
    public void save(CompoundTag nbt) {
        super.save(nbt);
        NBTUtils.putInt(nbt, "flags", StackUtil.hasItems((NonNullList<ItemStack>)this.inventory, SpecialFilters.EU_READER, SpecialFilters.THERMOMETER, SpecialFilters.CROP_SCANNER, SpecialFilters.AUTO_EATABLE), 0);
    }

    public ResourceLocation getTexture() {
        return this.data.getGuiTexture();
    }

    public int getStartX() {
        return this.data.getSlotXStart();
    }

    public int getColumns() {
        return this.data.getColumns();
    }

    public IFilter getFilter() {
        return this.data.getFilter();
    }

    public Vec2i getOffset() {
        return this.data.getInventoryOffset();
    }
}

