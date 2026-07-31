/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.item.inv.inventory;

import ic2.api.items.ITagBlock;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.PortableInventory;
import ic2.core.item.inv.container.ScannerContainer;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class ScannerInventory
extends PortableInventory {
    public ScannerInventory(Player owner, IHasHeldGui held, ItemStack stack, Slot slot) {
        super(owner, held, stack, slot);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new ScannerContainer(this, player, this.getID(), windowID);
    }

    @Override
    public int getSlotCount() {
        return 9;
    }

    @Override
    public void load(CompoundTag nbt) {
        for (CompoundTag data : NBTListWrapper.wrap(nbt.m_128437_("blocks", 10), CompoundTag.class)) {
            int slot = data.m_128451_("slot");
            if (slot < 0 || slot >= 9) continue;
            if (data.m_128471_("isTag")) {
                this.inventory.set(slot, (Object)ItemStack.m_41712_((CompoundTag)data));
                continue;
            }
            Block block = (Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation(data.m_128461_("block")));
            if (block == null || block == Blocks.f_50016_) continue;
            this.inventory.set(slot, (Object)new ItemStack((ItemLike)block));
        }
    }

    @Override
    public void save(CompoundTag nbt) {
        ListTag list = new ListTag();
        for (int i = 0; i < 9; ++i) {
            CompoundTag data;
            ItemStack stack = (ItemStack)this.inventory.get(i);
            if (stack.m_41619_()) continue;
            if (stack.m_41720_() instanceof ITagBlock) {
                data = stack.m_41739_(new CompoundTag());
                data.m_128344_("slot", (byte)i);
                data.m_128379_("isTag", true);
                list.add((Object)data);
                continue;
            }
            data = new CompoundTag();
            data.m_128359_("block", ForgeRegistries.BLOCKS.getKey((Object)Block.m_49814_((Item)stack.m_41720_())).toString());
            data.m_128344_("slot", (byte)i);
            list.add((Object)data);
        }
        NBTUtils.put(nbt, "blocks", list);
    }
}

