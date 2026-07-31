/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.block.Block
 *  net.minecraft.block.SoundType
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.EntityLiving$SpawnPlacementType
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package ic2.core.block;

import ic2.core.block.BlockMultiID;
import ic2.core.block.BlockScaffold;
import ic2.core.block.BlockWall;
import ic2.core.block.state.IIdProvider;
import ic2.core.block.type.ResourceBlock;
import ic2.core.ref.BlockName;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFoam
extends BlockMultiID<FoamType> {
    public static BlockFoam create() {
        return BlockMultiID.create(BlockFoam.class, FoamType.class, new Object[0]);
    }

    private BlockFoam() {
        super(BlockName.foam, Material.field_151580_n);
        this.func_149675_a(true);
        this.func_149711_c(0.01f);
        this.func_149752_b(10.0f);
        this.func_149672_a(SoundType.field_185854_g);
    }

    public boolean func_149662_c(IBlockState state) {
        return false;
    }

    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Nullable
    public AxisAlignedBB func_180646_a(IBlockState blockState, IBlockAccess world, BlockPos pos) {
        return null;
    }

    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    public void func_180645_a(World world, BlockPos pos, IBlockState state, Random random) {
        int tickSpeed = world.func_82736_K().func_180263_c("randomTickSpeed");
        if (tickSpeed <= 0) {
            throw new IllegalStateException("Foam was randomly ticked when world " + world + " isn't ticking?");
        }
        FoamType type = (FoamType)((Object)state.func_177229_b((IProperty)this.typeProperty));
        float chance = BlockFoam.getHardenChance(world, pos, state, type) * 4096.0f / (float)tickSpeed;
        if (random.nextFloat() < chance) {
            world.func_175656_a(pos, ((FoamType)((Object)state.func_177229_b((IProperty)this.typeProperty))).getResult());
        }
    }

    public static float getHardenChance(World world, BlockPos pos, IBlockState state, FoamType type) {
        int light = world.func_175671_l(pos);
        if (!state.func_185916_f() && state.func_177230_c().getLightOpacity(state, (IBlockAccess)world, pos) == 0) {
            for (EnumFacing side : EnumFacing.field_82609_l) {
                light = Math.max(light, world.func_175721_c(pos.func_177972_a(side), false));
            }
        }
        int avgTime = type.hardenTime * (16 - light);
        return 1.0f / (float)(avgTime * 20);
    }

    public boolean func_180639_a(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (StackUtil.consume(player, hand, StackUtil.sameItem((Block)Blocks.field_150354_m), 1)) {
            world.func_175656_a(pos, ((FoamType)((Object)state.func_177229_b((IProperty)this.typeProperty))).getResult());
            return true;
        }
        return false;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return ((FoamType)((Object)state.func_177229_b((IProperty)this.typeProperty))).getDrops();
    }

    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public static enum FoamType implements IIdProvider
    {
        normal(300),
        reinforced(600);

        public final int hardenTime;

        private FoamType(int hardenTime) {
            this.hardenTime = hardenTime;
        }

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public int getId() {
            return this.ordinal();
        }

        public List<ItemStack> getDrops() {
            switch (this) {
                case normal: {
                    return new ArrayList<ItemStack>();
                }
                case reinforced: {
                    ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
                    ret.add(BlockName.scaffold.getItemStack(BlockScaffold.ScaffoldType.iron));
                    return ret;
                }
            }
            throw new UnsupportedOperationException();
        }

        public IBlockState getResult() {
            switch (this) {
                case normal: {
                    return BlockName.wall.getBlockState(BlockWall.defaultColor);
                }
                case reinforced: {
                    return BlockName.resource.getBlockState(ResourceBlock.reinforced_stone);
                }
            }
            throw new UnsupportedOperationException();
        }
    }
}

