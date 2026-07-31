/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockDispenser
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.dispenser.BehaviorDefaultDispenseItem
 *  net.minecraft.dispenser.IBlockSource
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.core.item.block;

import ic2.core.block.EntityItnt;
import ic2.core.ref.BlockName;
import ic2.core.ref.TeBlock;
import ic2.core.util.StackUtil;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.properties.IProperty;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BehaviorTeBlockDispense
extends BehaviorDefaultDispenseItem {
    protected ItemStack func_82487_b(IBlockSource source, ItemStack stack) {
        if (StackUtil.checkItemEquality(stack, BlockName.te.getItemStack(TeBlock.itnt))) {
            World world = source.func_82618_k();
            BlockPos pos = source.func_180699_d().func_177972_a((EnumFacing)source.func_189992_e().func_177229_b((IProperty)BlockDispenser.field_176441_a));
            assert (!world.field_72995_K);
            EntityItnt entity = new EntityItnt(world, (double)pos.func_177958_n() + 0.5, pos.func_177956_o(), (double)pos.func_177952_p() + 0.5);
            world.func_175698_g(pos);
            world.func_72838_d((Entity)entity);
            world.func_184148_a(null, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, SoundEvents.field_187904_gd, SoundCategory.BLOCKS, 1.0f, 1.0f);
            return StackUtil.decSize(stack);
        }
        return stack;
    }
}

