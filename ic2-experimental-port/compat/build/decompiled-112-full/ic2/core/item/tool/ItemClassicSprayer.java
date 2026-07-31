/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.core.item.tool;

import ic2.core.IC2;
import ic2.core.block.BlockFoam;
import ic2.core.block.BlockScaffold;
import ic2.core.block.wiring.TileEntityCable;
import ic2.core.item.ItemGradualInt;
import ic2.core.item.armor.ItemArmorClassicCFPack;
import ic2.core.ref.BlockName;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import java.util.ArrayDeque;
import java.util.LinkedHashSet;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemClassicSprayer
extends ItemGradualInt {
    public ItemClassicSprayer() {
        super(ItemName.foam_sprayer, 1602);
        this.func_77625_d(1);
    }

    public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        boolean pulledFromCFPack;
        if (!IC2.platform.isSimulating()) {
            return EnumActionResult.SUCCESS;
        }
        ItemStack stack = StackUtil.get(player, hand);
        ItemStack pack = (ItemStack)player.field_71071_by.field_70460_b.get(2);
        boolean bl = pulledFromCFPack = StackUtil.check(pack) && pack.func_77973_b() == ItemName.cf_pack.getInstance() && ((ItemArmorClassicCFPack)pack.func_77973_b()).getCFPellet(player, pack);
        if (!pulledFromCFPack && this.getCustomDamage(stack) < 100) {
            return EnumActionResult.FAIL;
        }
        if (world.func_180495_p(pos).func_177230_c() == BlockName.scaffold.getInstance()) {
            this.sprayFoam(world, pos, ItemClassicSprayer.calculateDirectionsFromPlayer(player), true);
            if (!pulledFromCFPack) {
                this.applyCustomDamage(stack, 100, (EntityLivingBase)player);
            }
            return EnumActionResult.SUCCESS;
        }
        if (this.sprayFoam(world, pos.func_177972_a(facing), ItemClassicSprayer.calculateDirectionsFromPlayer(player), false)) {
            if (!pulledFromCFPack) {
                this.applyCustomDamage(stack, 100, (EntityLivingBase)player);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    private static boolean[] calculateDirectionsFromPlayer(EntityPlayer player) {
        float yaw = player.field_70177_z % 360.0f;
        float pitch = player.field_70125_A;
        boolean[] r = new boolean[]{true, true, true, true, true, true};
        if (pitch >= -65.0f && pitch <= 65.0f) {
            if (yaw >= 300.0f && yaw <= 360.0f || yaw >= 0.0f && yaw <= 60.0f) {
                r[2] = false;
            }
            if (yaw >= 30.0f && yaw <= 150.0f) {
                r[5] = false;
            }
            if (yaw >= 120.0f && yaw <= 240.0f) {
                r[3] = false;
            }
            if (yaw >= 210.0f && yaw <= 330.0f) {
                r[4] = false;
            }
        }
        if (pitch <= -40.0f) {
            r[0] = false;
        }
        if (pitch >= 40.0f) {
            r[1] = false;
        }
        return r;
    }

    public boolean sprayFoam(World world, BlockPos start, boolean[] directions, boolean scaffold) {
        BlockPos set;
        if (!ItemClassicSprayer.canFoam(world, start, scaffold)) {
            return false;
        }
        ArrayDeque<BlockPos> check = new ArrayDeque<BlockPos>();
        LinkedHashSet<BlockPos> place = new LinkedHashSet<BlockPos>();
        int foamcount = ItemClassicSprayer.getSprayMass();
        check.add(start);
        while ((set = (BlockPos)check.poll()) != null && foamcount > 0) {
            if (!ItemClassicSprayer.canFoam(world, set, scaffold) || !place.add(set)) continue;
            for (Object i : (Object)ItemClassicSprayer.generateRngSpread(IC2.random)) {
                if (!scaffold && !directions[i]) continue;
                check.add(set.func_177972_a(EnumFacing.func_82600_a((int)i)));
            }
            --foamcount;
        }
        for (BlockPos pos : place) {
            IBlockState state = world.func_180495_p(pos);
            Block targetBlock = state.func_177230_c();
            if (targetBlock == BlockName.scaffold.getInstance()) {
                BlockScaffold block = (BlockScaffold)targetBlock;
                switch ((BlockScaffold.ScaffoldType)((Object)state.func_177229_b(block.getTypeProperty()))) {
                    case wood: 
                    case reinforced_wood: {
                        block.func_176226_b(world, pos, state, 0);
                        world.func_175656_a(pos, BlockName.foam.getBlockState(BlockFoam.FoamType.normal));
                        break;
                    }
                }
                continue;
            }
            if (targetBlock == BlockName.te.getInstance()) {
                TileEntity te = world.func_175625_s(pos);
                if (!(te instanceof TileEntityCable)) continue;
                ((TileEntityCable)te).foam();
                continue;
            }
            world.func_175656_a(pos, BlockName.foam.getBlockState(BlockFoam.FoamType.normal));
        }
        return true;
    }

    private static boolean canFoam(World world, BlockPos pos, boolean scaffold) {
        if (!scaffold) {
            if (BlockName.foam.getInstance().func_176198_a(world, pos, EnumFacing.DOWN)) {
                return true;
            }
            if (world.func_180495_p(pos).func_177230_c() != BlockName.te.getInstance()) {
                return false;
            }
            TileEntity te = world.func_175625_s(pos);
            return te instanceof TileEntityCable && !((TileEntityCable)te).isFoamed();
        }
        return world.func_180495_p(pos).func_177230_c() == BlockName.scaffold.getInstance();
    }

    private static int[] generateRngSpread(Random random) {
        int[] re = new int[]{0, 1, 2, 3, 4, 5};
        for (int i = 0; i < 16; ++i) {
            int first = random.nextInt(6);
            int second = random.nextInt(6);
            int temp = re[first];
            re[first] = re[second];
            re[second] = temp;
        }
        return re;
    }

    public static int getSprayMass() {
        return 13;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1.0 - super.getDurabilityForDisplay(stack);
    }
}

