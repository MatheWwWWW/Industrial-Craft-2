/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.core.item.tool;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.item.IBoxable;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.item.IHandHeldInventory;
import ic2.core.item.ItemIC2;
import ic2.core.item.tool.ContainerMeter;
import ic2.core.item.tool.HandHeldMeter;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemToolMeter
extends ItemIC2
implements IBoxable,
IHandHeldInventory {
    public ItemToolMeter() {
        super(ItemName.meter);
        this.field_77777_bU = 1;
        this.func_77656_e(0);
    }

    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (world.field_72995_K) {
            return EnumActionResult.PASS;
        }
        IEnergyTile tile = EnergyNet.instance.getTile(world, pos);
        if (tile instanceof IEnergySource || tile instanceof IEnergyConductor || tile instanceof IEnergySink) {
            if (IC2.platform.launchGui(player, this.getInventory(player, StackUtil.get(player, hand)))) {
                ContainerMeter container = (ContainerMeter)player.field_71070_bA;
                container.setUut(tile);
                return EnumActionResult.SUCCESS;
            }
        } else {
            IC2.platform.messagePlayer(player, "Not an energy net tile", new Object[0]);
        }
        return EnumActionResult.SUCCESS;
    }

    public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player) {
        HandHeldMeter euReader;
        if (!player.func_130014_f_().field_72995_K && !StackUtil.isEmpty(stack) && player.field_71070_bA instanceof ContainerMeter && (euReader = (HandHeldMeter)((ContainerMeter)player.field_71070_bA).base).isThisContainer(stack)) {
            euReader.saveAsThrown(stack);
            player.func_71053_j();
        }
        return true;
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack itemstack) {
        return true;
    }

    @Override
    public IHasGui getInventory(EntityPlayer player, ItemStack stack) {
        return new HandHeldMeter(player, stack);
    }
}

