/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.MobEffects
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.fluids.BlockFluidClassic
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block;

import ic2.core.IC2;
import ic2.core.block.BlockBase;
import ic2.core.block.type.ResourceBlock;
import ic2.core.init.BlocksItems;
import ic2.core.item.block.ItemBlockIC2;
import ic2.core.profile.Version;
import ic2.core.ref.BlockName;
import ic2.core.ref.FluidName;
import ic2.core.ref.IBlockModelProvider;
import ic2.core.util.LiquidUtil;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockIC2Fluid
extends BlockFluidClassic
implements IBlockModelProvider {
    protected final Fluid fluid;
    private final int color;

    public BlockIC2Fluid(FluidName name, Fluid fluid, Material material, int color) {
        super(fluid, material);
        this.func_149663_c(name.name());
        this.func_149647_a(IC2.tabIC2);
        this.fluid = fluid;
        this.color = color;
        if (this.density <= FluidRegistry.WATER.getDensity()) {
            this.displacements.put(Blocks.field_150355_j, false);
            this.displacements.put(Blocks.field_150358_i, false);
        }
        if (this.density <= FluidRegistry.LAVA.getDensity()) {
            this.displacements.put(Blocks.field_150353_l, false);
            this.displacements.put(Blocks.field_150356_k, false);
        }
        ResourceLocation regName = IC2.getIdentifier(name.name());
        BlocksItems.registerBlock(this, regName);
        BlocksItems.registerItem(new ItemBlockIC2((Block)this), regName);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerModels(BlockName name) {
        BlockBase.registerDefaultItemModel((Block)this);
    }

    public void func_149666_a(CreativeTabs tab, NonNullList<ItemStack> items) {
        boolean defaultState = Version.shouldEnable(FluidName.class);
        try {
            if (Version.shouldEnable(FluidName.class.getField(super.func_149739_a().substring(5)), defaultState)) {
                items.add((Object)new ItemStack((Block)this));
            }
        }
        catch (NoSuchFieldException e) {
            throw new RuntimeException("Impossible missing enum field!", e);
        }
    }

    public void func_180650_b(World world, BlockPos pos, IBlockState state, Random random) {
        super.func_180650_b(world, pos, state, random);
        if (!world.field_72995_K) {
            if (this.fluid == FluidName.pahoehoe_lava.getInstance()) {
                if (this.isSourceBlock((IBlockAccess)world, pos) && world.func_175671_l(pos) >= world.field_73012_v.nextInt(120)) {
                    world.func_175656_a(pos, BlockName.resource.getBlockState(ResourceBlock.basalt));
                } else if (!this.hardenFromNeighbors(world, pos)) {
                    world.func_175684_a(pos, (Block)this, this.func_149738_a(world));
                }
            } else if (this.fluid == FluidName.hot_water.getInstance()) {
                if (this.isSourceBlock((IBlockAccess)world, pos) && !BlockIC2Fluid.isLavaBlock(world.func_180495_p(pos.func_177979_c(2)).func_177230_c()) && world.func_180495_p(pos.func_177977_b()).func_177230_c() != this && world.field_73012_v.nextInt(60) == 0) {
                    world.func_175656_a(pos, Blocks.field_150358_i.func_176223_P());
                } else {
                    world.func_175684_a(pos, (Block)this, this.func_149738_a(world));
                }
            }
        }
    }

    private static boolean isLavaBlock(Block block) {
        return block == Blocks.field_150353_l || block == Blocks.field_150356_k;
    }

    public void func_189540_a(IBlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos) {
        super.func_189540_a(state, world, pos, block, neighborPos);
        this.hardenFromNeighbors(world, pos);
    }

    public void func_176213_c(World world, BlockPos pos, IBlockState state) {
        super.func_176213_c(world, pos, state);
        this.hardenFromNeighbors(world, pos);
    }

    public void func_180633_a(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (world.field_72995_K) {
            return;
        }
        if (this.fluid == FluidName.biogas.getInstance() || this.fluid == FluidName.air.getInstance()) {
            world.func_175698_g(pos);
            world.func_184133_a(null, pos, SoundEvents.field_187616_bj, SoundCategory.BLOCKS, 1.0f, RANDOM.nextFloat() * 0.4f + 0.8f);
        }
    }

    public void func_180634_a(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        this.func_176199_a(worldIn, pos, entityIn);
    }

    public void func_176199_a(World world, BlockPos pos, Entity entity) {
        if (world.field_72995_K) {
            return;
        }
        if (this.fluid == FluidName.pahoehoe_lava.getInstance()) {
            entity.func_70015_d(10);
        } else if (this.fluid == FluidName.hot_coolant.getInstance()) {
            entity.func_70015_d(30);
        }
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase living = (EntityLivingBase)entity;
            if (this.fluid == FluidName.construction_foam.getInstance()) {
                BlockIC2Fluid.addPotion(living, MobEffects.field_76421_d, 300, 2);
            } else if (this.fluid == FluidName.uu_matter.getInstance()) {
                BlockIC2Fluid.addPotion(living, MobEffects.field_76428_l, 100, 1);
            } else if (this.fluid == FluidName.steam.getInstance() || this.fluid == FluidName.superheated_steam.getInstance()) {
                BlockIC2Fluid.addPotion(living, MobEffects.field_76440_q, 300, 0);
            } else if (this.fluid == FluidName.hot_water.getInstance()) {
                Potion potion = ((EntityLivingBase)entity).func_70662_br() ? MobEffects.field_82731_v : MobEffects.field_76428_l;
                BlockIC2Fluid.addPotion(living, potion, 100, IC2.random.nextInt(2));
            }
        }
    }

    private static void addPotion(EntityLivingBase entity, Potion potion, int duration, int amplifier) {
        if (entity.func_70644_a(potion)) {
            return;
        }
        entity.func_70690_d(new PotionEffect(potion, duration, amplifier, true, true));
    }

    public String func_149739_a() {
        return "ic2." + super.func_149739_a().substring(5);
    }

    public int getColor() {
        return this.color;
    }

    private boolean hardenFromNeighbors(World world, BlockPos pos) {
        if (world.field_72995_K) {
            return false;
        }
        if (this.fluid == FluidName.pahoehoe_lava.getInstance()) {
            for (EnumFacing dir : EnumFacing.field_82609_l) {
                LiquidUtil.LiquidData data = LiquidUtil.getLiquid(world, pos.func_177972_a(dir));
                if (data == null || data.liquid.getTemperature() > this.fluid.getTemperature() / 4) continue;
                if (this.isSourceBlock((IBlockAccess)world, pos)) {
                    world.func_175656_a(pos, BlockName.resource.getBlockState(ResourceBlock.basalt));
                } else {
                    world.func_175698_g(pos);
                }
                return true;
            }
        }
        return false;
    }
}

