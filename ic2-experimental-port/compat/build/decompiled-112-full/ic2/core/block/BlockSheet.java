/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package ic2.core.block;

import ic2.core.IC2;
import ic2.core.block.BlockMultiID;
import ic2.core.block.state.IIdProvider;
import ic2.core.block.type.IExtBlockType;
import ic2.core.item.block.ItemBlockSheet;
import ic2.core.item.type.MiscResourceType;
import ic2.core.ref.BlockName;
import ic2.core.ref.ItemName;
import ic2.core.util.Ic2BlockPos;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSheet
extends BlockMultiID<SheetType> {
    private static final AxisAlignedBB aabb = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.125, 1.0);
    private static final EnumFacing[] positiveHorizontalFacings = new EnumFacing[]{EnumFacing.EAST, EnumFacing.SOUTH};

    public static BlockSheet create() {
        return BlockMultiID.create(BlockSheet.class, SheetType.class, new Object[0]);
    }

    public BlockSheet() {
        super(BlockName.sheet, Material.field_151594_q, ItemBlockSheet.class);
    }

    public boolean func_149686_d(IBlockState state) {
        return false;
    }

    public boolean func_149662_c(IBlockState state) {
        return false;
    }

    public AxisAlignedBB func_185496_a(IBlockState state, IBlockAccess source, BlockPos pos) {
        return aabb;
    }

    public void func_185477_a(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if (this.getType(state) == SheetType.wool && entityIn instanceof EntityPlayer && (entityIn.func_70093_af() || entityIn.field_70163_u < (double)pos.func_177956_o() + BlockSheet.aabb.field_72337_e - (double)entityIn.field_70138_W)) {
            return;
        }
        super.func_185477_a(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
    }

    public AxisAlignedBB func_180646_a(IBlockState state, IBlockAccess world, BlockPos pos) {
        AxisAlignedBB aabb = super.func_180646_a(state, world, pos);
        switch ((SheetType)this.getType(state)) {
            case resin: {
                return null;
            }
        }
        return aabb;
    }

    public boolean canReplace(World world, BlockPos pos, EnumFacing side, ItemStack stack) {
        return this.isValidPosition(world, pos, this.func_176203_a(stack.func_77952_i()));
    }

    private boolean isValidPosition(World world, BlockPos pos, IBlockState state) {
        switch ((SheetType)this.getType(state)) {
            case resin: {
                return this.isNormalCubeBelow(world, pos);
            }
            case rubber: {
                for (EnumFacing facing : EnumFacing.field_176754_o) {
                    state = world.func_180495_p(pos.func_177972_a(facing));
                    if (state != BlockName.sheet.getBlockState(SheetType.rubber) && !state.func_177230_c().isNormalCube(state, (IBlockAccess)world, pos)) continue;
                    return true;
                }
                return this.isNormalCubeBelow(world, pos);
            }
            case wool: {
                return true;
            }
        }
        return false;
    }

    private boolean isNormalCubeBelow(World world, BlockPos pos) {
        pos = pos.func_177977_b();
        IBlockState state = world.func_180495_p(pos);
        return state.func_177230_c().isNormalCube(state, (IBlockAccess)world, pos);
    }

    public void func_189540_a(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighborPos) {
        if (!this.isValidPosition(world, pos, state)) {
            world.func_175698_g(pos);
            this.func_176226_b(world, pos, state, 0);
        }
    }

    public void func_180634_a(World world, BlockPos pos, IBlockState state, Entity entity) {
        switch ((SheetType)this.getType(state)) {
            case resin: {
                entity.field_70143_R = (float)((double)entity.field_70143_R * 0.75);
                entity.field_70159_w *= 0.6;
                entity.field_70181_x *= 0.85;
                entity.field_70179_y *= 0.6;
                break;
            }
            case rubber: {
                if (world.func_175677_d(pos.func_177977_b(), false)) {
                    return;
                }
                if (entity instanceof EntityLivingBase && !BlockSheet.canSupportWeight(world, pos)) {
                    world.func_175698_g(pos);
                    return;
                }
                if (!(entity.field_70181_x <= -0.4)) break;
                entity.field_70143_R = 0.0f;
                entity.field_70159_w *= 1.1;
                entity.field_70179_y *= 1.1;
                if (entity instanceof EntityLivingBase) {
                    if (entity instanceof EntityPlayer && IC2.keyboard.isJumpKeyDown((EntityPlayer)entity)) {
                        entity.field_70181_x *= -1.3;
                        break;
                    }
                    if (entity instanceof EntityPlayer && ((EntityPlayer)entity).func_70093_af()) {
                        entity.field_70181_x *= -0.1;
                        break;
                    }
                    entity.field_70181_x *= -0.8;
                    break;
                }
                entity.field_70181_x *= -0.8;
                break;
            }
            case wool: {
                entity.field_70143_R = (float)((double)entity.field_70143_R * 0.95);
            }
        }
    }

    private static boolean canSupportWeight(World world, BlockPos pos) {
        int maxRange = 16;
        Ic2BlockPos cPos = new Ic2BlockPos();
        block0: for (EnumFacing axis : positiveHorizontalFacings) {
            for (int dir = -1; dir <= 1; dir += 2) {
                cPos.set((Vec3i)pos);
                boolean supported = false;
                for (int i = 0; i < 16; ++i) {
                    cPos.move(axis, dir);
                    IBlockState state = cPos.getBlockState((IBlockAccess)world);
                    if (state.func_177230_c().isNormalCube(state, (IBlockAccess)world, (BlockPos)cPos)) {
                        supported = true;
                        break;
                    }
                    if (state != BlockName.sheet.getBlockState(SheetType.rubber)) break;
                    cPos.moveDown();
                    IBlockState baseState = cPos.getBlockState((IBlockAccess)world);
                    if (baseState.func_177230_c().isNormalCube(baseState, (IBlockAccess)world, (BlockPos)cPos)) {
                        supported = true;
                        break;
                    }
                    cPos.moveUp();
                }
                if (!supported) continue block0;
                if (dir != 1) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        switch ((SheetType)this.getType(state)) {
            case resin: {
                if (IC2.random.nextInt(5) != 0) {
                    ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
                    ret.add(ItemName.misc_resource.getItemStack(MiscResourceType.resin));
                    return ret;
                }
                return new ArrayList<ItemStack>();
            }
        }
        return super.getDrops(world, pos, state, fortune);
    }

    public static enum SheetType implements IIdProvider,
    IExtBlockType
    {
        resin(1.6f, 0.5f),
        rubber(0.8f, 2.0f),
        wool(0.8f, 0.8f);

        public static SheetType[] values;
        private final float hardness;
        private final float explosionResistance;

        private SheetType(float hardness, float explosionResistance) {
            this.hardness = hardness;
            this.explosionResistance = explosionResistance;
        }

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public int getId() {
            return this.ordinal();
        }

        @Override
        public float getHardness() {
            return this.hardness;
        }

        @Override
        public float getExplosionResistance() {
            return this.explosionResistance;
        }

        static {
            values = SheetType.values();
        }
    }
}

