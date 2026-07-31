/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.util.ITooltipFlag
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item.tool;

import ic2.api.item.IEnhancedOverlayProvider;
import ic2.core.IC2;
import ic2.core.audio.PositionSpec;
import ic2.core.block.transport.cover.ICoverHolder;
import ic2.core.init.Localization;
import ic2.core.item.ItemToolIC2;
import ic2.core.item.tool.HarvestLevel;
import ic2.core.item.tool.ToolClass;
import ic2.core.ref.ItemName;
import ic2.core.util.RotationUtil;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemToolCrowbar
extends ItemToolIC2
implements IEnhancedOverlayProvider {
    public ItemToolCrowbar() {
        super(ItemName.crowbar, HarvestLevel.Iron, EnumSet.of(ToolClass.Crowbar));
        this.func_77656_e(250);
    }

    public boolean canTakeDamage(ItemStack stack, int amount) {
        return true;
    }

    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack stack = StackUtil.get(player, hand);
        if (!this.canTakeDamage(stack, 1)) {
            return EnumActionResult.FAIL;
        }
        IBlockState state = Util.getBlockState((IBlockAccess)world, pos);
        Block block = state.func_177230_c();
        if (block.isAir(state, (IBlockAccess)world, pos)) {
            return EnumActionResult.FAIL;
        }
        if (world.func_175625_s(pos) instanceof ICoverHolder) {
            EnumFacing selectedFacing;
            ICoverHolder target = (ICoverHolder)world.func_175625_s(pos);
            if (target.canRemoveCover(world, pos, selectedFacing = RotationUtil.rotateByHit(side, hitX, hitY, hitZ))) {
                if (!world.field_72995_K) {
                    target.removeCover(world, pos, selectedFacing);
                    stack.func_77972_a(1, (EntityLivingBase)player);
                } else {
                    IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/Crowbar.ogg", true, IC2.audioManager.getDefaultVolume());
                    IC2.platform.messagePlayer(player, Localization.translate("Attachment removed"), new Object[0]);
                }
            }
            return world.field_72995_K ? EnumActionResult.PASS : EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

    public boolean func_82789_a(ItemStack toRepair, ItemStack repair) {
        return repair != null && Util.matchesOD(repair, "ingotBronze");
    }

    public boolean func_77616_k(ItemStack stack) {
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> info, ITooltipFlag flagIn) {
        info.add(Minecraft.func_71410_x().field_71474_y.field_74313_G.getDisplayName() + ":");
        info.add(" Remove attachments from blocks");
    }

    @Override
    public boolean providesEnhancedOverlay(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
        TileEntity tileEntity = world.func_175625_s(pos);
        return tileEntity instanceof ICoverHolder;
    }
}

