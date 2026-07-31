/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumHand
 *  net.minecraft.world.World
 */
package ic2.core.block;

import ic2.core.IC2;
import ic2.core.block.EntityIC2Explosive;
import ic2.core.item.tool.ItemToolWrench;
import ic2.core.ref.BlockName;
import ic2.core.ref.TeBlock;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityNuke
extends EntityIC2Explosive {
    public EntityNuke(World world, double x, double y, double z, float power, int radiationRange) {
        super(world, x, y, z, 300, power, 0.05f, 1.5f, BlockName.te.getBlockState(TeBlock.nuke), radiationRange);
    }

    public EntityNuke(World world) {
        this(world, 0.0, 0.0, 0.0, 0.0f, 0);
    }

    public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
        ItemToolWrench wrench;
        ItemStack stack = StackUtil.get(player, hand);
        if (IC2.platform.isSimulating() && !StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof ItemToolWrench && (wrench = (ItemToolWrench)stack.func_77973_b()).canTakeDamage(stack, 1)) {
            wrench.damage(stack, 1, player);
            this.func_70106_y();
        }
        return false;
    }
}

