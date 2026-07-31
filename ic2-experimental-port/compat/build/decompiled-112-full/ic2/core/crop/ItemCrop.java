/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.SoundType
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package ic2.core.crop;

import ic2.api.item.IBoxable;
import ic2.core.block.TileEntityBlock;
import ic2.core.item.ItemIC2;
import ic2.core.item.block.ItemBlockTileEntity;
import ic2.core.ref.BlockName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import ic2.core.util.StackUtil;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ItemCrop
extends ItemIC2
implements IBoxable {
    public ItemCrop() {
        super(ItemName.crop_stick);
    }

    public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack cropStickStack;
        if (!world.func_180495_p(pos).func_177230_c().func_176200_f((IBlockAccess)world, pos)) {
            pos = pos.func_177972_a(side);
        }
        if (StackUtil.isEmpty(cropStickStack = StackUtil.get(player, hand))) {
            return EnumActionResult.PASS;
        }
        if (world.func_180495_p(pos.func_177977_b()).func_177230_c() != Blocks.field_150458_ak) {
            return EnumActionResult.PASS;
        }
        if (!player.func_175151_a(pos, side, cropStickStack)) {
            return EnumActionResult.PASS;
        }
        if (!world.func_190527_a(BlockName.te.getInstance(), pos, true, side, (Entity)player)) {
            return EnumActionResult.PASS;
        }
        TileEntityBlock tile = TileEntityBlock.instantiate(TeBlock.crop.getTeClass());
        if (ItemBlockTileEntity.placeTeBlock(cropStickStack, (EntityLivingBase)player, world, pos, side, tile)) {
            SoundType stepSound = SoundType.field_185850_c;
            world.func_184148_a(null, (double)pos.func_177958_n() + 0.5, (double)pos.func_177956_o() + 0.5, (double)pos.func_177952_p() + 0.5, stepSound.func_185841_e(), SoundCategory.BLOCKS, (stepSound.func_185843_a() + 1.0f) / 2.0f, stepSound.func_185847_b() * 0.8f);
            StackUtil.consumeOrError(player, hand, 1);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack itemStack) {
        return true;
    }
}

