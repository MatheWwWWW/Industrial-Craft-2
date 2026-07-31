/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.util.ITooltipFlag
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.ActionResult
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item.tool;

import ic2.core.IC2;
import ic2.core.block.machine.tileentity.TileEntityTeleporter;
import ic2.core.init.Localization;
import ic2.core.item.ItemIC2;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFrequencyTransmitter
extends ItemIC2 {
    private static final String targetSetNbt = "targetSet";
    private static final String targetJustSetNbt = "targetJustSet";
    private static final String targetXNbt = "targetX";
    private static final String targetYNbt = "targetY";
    private static final String targetZNbt = "targetZ";

    public ItemFrequencyTransmitter() {
        super(ItemName.frequency_transmitter);
        this.field_77777_bU = 1;
    }

    public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = StackUtil.get(player, hand);
        if (IC2.platform.isSimulating()) {
            NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
            boolean hadJustSet = nbtData.func_74767_n(targetJustSetNbt);
            if (nbtData.func_74767_n(targetSetNbt) && !hadJustSet) {
                nbtData.func_74757_a(targetSetNbt, false);
                IC2.platform.messagePlayer(player, "Frequency Transmitter unlinked", new Object[0]);
            }
            if (hadJustSet) {
                nbtData.func_74757_a(targetJustSetNbt, false);
            }
        }
        return new ActionResult(EnumActionResult.SUCCESS, (Object)stack);
    }

    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (world.field_72995_K) {
            return EnumActionResult.PASS;
        }
        TileEntity te = world.func_175625_s(pos);
        if (!(te instanceof TileEntityTeleporter)) {
            return EnumActionResult.PASS;
        }
        TileEntityTeleporter tp = (TileEntityTeleporter)te;
        NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(StackUtil.get(player, hand));
        boolean targetSet = nbtData.func_74767_n(targetSetNbt);
        boolean justSetTarget = true;
        BlockPos target = new BlockPos(nbtData.func_74762_e(targetXNbt), nbtData.func_74762_e(targetYNbt), nbtData.func_74762_e(targetZNbt));
        if (!targetSet) {
            targetSet = true;
            target = tp.func_174877_v();
            IC2.platform.messagePlayer(player, "Frequency Transmitter linked to Teleporter.", new Object[0]);
        } else if (tp.func_174877_v().equals((Object)target)) {
            IC2.platform.messagePlayer(player, "Can't link Teleporter to itself.", new Object[0]);
        } else if (tp.hasTarget() && tp.getTarget().equals((Object)target)) {
            IC2.platform.messagePlayer(player, "Teleportation link unchanged.", new Object[0]);
        } else {
            TileEntity targetTe = world.func_175625_s(target);
            if (targetTe instanceof TileEntityTeleporter) {
                tp.setTarget(target);
                ((TileEntityTeleporter)targetTe).setTarget(pos);
                IC2.platform.messagePlayer(player, "Teleportation link established.", new Object[0]);
            } else {
                justSetTarget = false;
                targetSet = false;
            }
        }
        nbtData.func_74757_a(targetSetNbt, targetSet);
        nbtData.func_74757_a(targetJustSetNbt, justSetTarget);
        nbtData.func_74768_a(targetXNbt, target.func_177958_n());
        nbtData.func_74768_a(targetYNbt, target.func_177956_o());
        nbtData.func_74768_a(targetZNbt, target.func_177952_p());
        return EnumActionResult.SUCCESS;
    }

    @SideOnly(value=Side.CLIENT)
    public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
        NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
        if (nbtData.func_74767_n(targetSetNbt)) {
            tooltip.add(Localization.translate("ic2.frequency_transmitter.tooltip.target", nbtData.func_74762_e(targetXNbt), nbtData.func_74762_e(targetYNbt), nbtData.func_74762_e(targetZNbt)));
        } else {
            tooltip.add(Localization.translate("ic2.frequency_transmitter.tooltip.blank"));
        }
    }
}

