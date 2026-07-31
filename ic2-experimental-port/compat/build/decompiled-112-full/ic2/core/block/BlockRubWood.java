/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.SoundType
 *  net.minecraft.block.material.EnumPushReaction
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.properties.PropertyEnum
 *  net.minecraft.block.state.BlockStateContainer
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumFacing$Axis
 *  net.minecraft.util.IStringSerializable
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos$MutableBlockPos
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package ic2.core.block;

import ic2.core.block.BlockBase;
import ic2.core.item.type.MiscResourceType;
import ic2.core.ref.BlockName;
import ic2.core.ref.ItemName;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRubWood
extends BlockBase {
    public static final PropertyEnum<RubberWoodState> stateProperty = PropertyEnum.func_177709_a((String)"state", RubberWoodState.class);

    public BlockRubWood() {
        super(BlockName.rubber_wood, Material.field_151575_d);
        this.func_149675_a(true);
        this.func_149711_c(1.0f);
        this.func_149672_a(SoundType.field_185848_a);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(stateProperty, (Comparable)((Object)RubberWoodState.plain_y)));
    }

    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[]{stateProperty});
    }

    public IBlockState func_176203_a(int meta) {
        if (meta >= 0 && meta < RubberWoodState.values.length) {
            return this.func_176223_P().func_177226_a(stateProperty, (Comparable)((Object)RubberWoodState.values[meta]));
        }
        return this.func_176223_P();
    }

    public int func_176201_c(IBlockState state) {
        return ((RubberWoodState)((Object)state.func_177229_b(stateProperty))).ordinal();
    }

    public IBlockState func_180642_a(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState state = super.func_180642_a(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        return state.func_177226_a(stateProperty, (Comparable)((Object)BlockRubWood.getPlainAxisState(facing.func_176740_k())));
    }

    private static RubberWoodState getPlainAxisState(EnumFacing.Axis axis) {
        switch (axis) {
            case X: {
                return RubberWoodState.plain_x;
            }
            case Y: {
                return RubberWoodState.plain_y;
            }
            case Z: {
                return RubberWoodState.plain_z;
            }
        }
        throw new IllegalArgumentException("invalid axis: " + axis);
    }

    public void func_180653_a(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
        if (world.field_72995_K) {
            return;
        }
        int count = this.func_149745_a(world.field_73012_v);
        for (int j1 = 0; j1 < count; ++j1) {
            if (world.field_73012_v.nextFloat() > chance) continue;
            Item item = this.func_180660_a(state, world.field_73012_v, fortune);
            if (item != null) {
                BlockRubWood.func_180635_a((World)world, (BlockPos)pos, (ItemStack)new ItemStack(item, 1, 0));
            }
            if (((RubberWoodState)((Object)state.func_177229_b(stateProperty))).isPlain() || world.field_73012_v.nextInt(6) != 0) continue;
            BlockRubWood.func_180635_a((World)world, (BlockPos)pos, (ItemStack)ItemName.misc_resource.getItemStack(MiscResourceType.resin));
        }
    }

    public void func_180663_b(World world, BlockPos pos, IBlockState state) {
        int range = 4;
        BlockPos.MutableBlockPos cPos = new BlockPos.MutableBlockPos();
        for (int y = -range; y <= range; ++y) {
            for (int z = -range; z <= range; ++z) {
                for (int x = -range; x <= range; ++x) {
                    cPos.func_181079_c(pos.func_177958_n() + x, pos.func_177956_o() + y, pos.func_177952_p() + z);
                    IBlockState cState = world.func_180495_p((BlockPos)cPos);
                    Block cBlock = cState.func_177230_c();
                    if (!cBlock.isLeaves(cState, (IBlockAccess)world, (BlockPos)cPos)) continue;
                    cBlock.beginLeavesDecay(cState, world, new BlockPos((Vec3i)cPos));
                }
            }
        }
    }

    public void func_180645_a(World world, BlockPos pos, IBlockState state, Random random) {
        if (random.nextInt(7) == 0) {
            RubberWoodState rwState = (RubberWoodState)((Object)state.func_177229_b(stateProperty));
            if (!rwState.canRegenerate()) {
                return;
            }
            world.func_175656_a(pos, state.func_177226_a(stateProperty, (Comparable)((Object)rwState.getWet())));
        }
    }

    public EnumPushReaction func_149656_h(IBlockState state) {
        RubberWoodState rstate = (RubberWoodState)((Object)state.func_177229_b(stateProperty));
        if (rstate == RubberWoodState.plain_x || rstate == RubberWoodState.plain_y || rstate == RubberWoodState.plain_z) {
            return EnumPushReaction.NORMAL;
        }
        return EnumPushReaction.BLOCK;
    }

    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    public boolean isWood(IBlockAccess world, BlockPos pos) {
        return true;
    }

    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 4;
    }

    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 20;
    }

    public static enum RubberWoodState implements IStringSerializable
    {
        plain_y(EnumFacing.Axis.Y, null, false),
        plain_x(EnumFacing.Axis.X, null, false),
        plain_z(EnumFacing.Axis.Z, null, false),
        dry_north(EnumFacing.Axis.Y, EnumFacing.NORTH, false),
        dry_south(EnumFacing.Axis.Y, EnumFacing.SOUTH, false),
        dry_west(EnumFacing.Axis.Y, EnumFacing.WEST, false),
        dry_east(EnumFacing.Axis.Y, EnumFacing.EAST, false),
        wet_north(EnumFacing.Axis.Y, EnumFacing.NORTH, true),
        wet_south(EnumFacing.Axis.Y, EnumFacing.SOUTH, true),
        wet_west(EnumFacing.Axis.Y, EnumFacing.WEST, true),
        wet_east(EnumFacing.Axis.Y, EnumFacing.EAST, true);

        public final EnumFacing.Axis axis;
        public final EnumFacing facing;
        public final boolean wet;
        private static final RubberWoodState[] values;

        private RubberWoodState(EnumFacing.Axis axis, EnumFacing facing, boolean wet) {
            this.axis = axis;
            this.facing = facing;
            this.wet = wet;
        }

        public String func_176610_l() {
            return this.name();
        }

        public boolean isPlain() {
            return this.facing == null;
        }

        public boolean canRegenerate() {
            return !this.isPlain() && !this.wet;
        }

        public RubberWoodState getWet() {
            if (this.isPlain()) {
                return null;
            }
            if (this.wet) {
                return this;
            }
            return values[this.ordinal() + 4];
        }

        public RubberWoodState getDry() {
            if (this.isPlain() || !this.wet) {
                return this;
            }
            return values[this.ordinal() - 4];
        }

        public static RubberWoodState getWet(EnumFacing facing) {
            switch (facing) {
                case NORTH: {
                    return wet_north;
                }
                case SOUTH: {
                    return wet_south;
                }
                case WEST: {
                    return wet_west;
                }
                case EAST: {
                    return wet_east;
                }
            }
            throw new IllegalArgumentException("incompatible facing: " + facing);
        }

        static {
            values = RubberWoodState.values();
        }
    }
}

