/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.SoundType
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.BlockRenderLayer
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumFacing$Axis
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.block.BlockIC2Fence;
import ic2.core.block.BlockMultiID;
import ic2.core.block.state.IIdProvider;
import ic2.core.block.type.IBlockSound;
import ic2.core.block.type.IExtBlockType;
import ic2.core.ref.BlockName;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockScaffold
extends BlockMultiID<ScaffoldType> {
    private static final IRecipeInput stickInput = Recipes.inputFactory.forOreDict("stickWood");
    private static final EnumFacing[] supportedFacings = new EnumFacing[]{EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST};
    private static final double border = 0.03125;
    private static final AxisAlignedBB aabb = new AxisAlignedBB(0.03125, 0.0, 0.03125, 0.96875, 1.0, 0.96875);

    public static BlockScaffold create() {
        return BlockMultiID.create(BlockScaffold.class, ScaffoldType.class, new Object[0]);
    }

    private BlockScaffold() {
        super(BlockName.scaffold, Material.field_151575_d);
        this.func_149675_a(true);
    }

    @SideOnly(value=Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.CUTOUT;
    }

    public Material func_149688_o(IBlockState state) {
        ScaffoldType type = (ScaffoldType)this.getType(state);
        if (type == null) {
            return super.func_149688_o(state);
        }
        switch (type) {
            case wood: 
            case reinforced_wood: {
                return Material.field_151575_d;
            }
            case iron: 
            case reinforced_iron: {
                return Material.field_151573_f;
            }
        }
        throw new IllegalStateException("Invalid scaffold type: " + type);
    }

    public boolean func_149662_c(IBlockState state) {
        return false;
    }

    public boolean func_149721_r(IBlockState state) {
        return false;
    }

    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
        return true;
    }

    public void func_180634_a(World world, BlockPos pos, IBlockState state, Entity rawEntity) {
        if (rawEntity instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase)rawEntity;
            entity.field_70143_R = 0.0f;
            double limit = 0.15;
            entity.field_70159_w = Util.limit(entity.field_70159_w, -limit, limit);
            entity.field_70179_y = Util.limit(entity.field_70179_y, -limit, limit);
            entity.field_70181_x = entity.func_70093_af() && entity instanceof EntityPlayer ? (entity.func_70090_H() ? 0.02 : 0.08) : (entity.field_70123_F ? 0.2 : Math.max(entity.field_70181_x, -0.07));
        }
    }

    public AxisAlignedBB func_185496_a(IBlockState state, IBlockAccess world, BlockPos pos) {
        return aabb;
    }

    public AxisAlignedBB func_180640_a(IBlockState state, World worldIn, BlockPos pos) {
        return field_185505_j.func_186670_a(pos);
    }

    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return side.func_176740_k() == EnumFacing.Axis.Y;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (state.func_177230_c() != this) {
            return Collections.emptyList();
        }
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ScaffoldType type = (ScaffoldType)((Object)state.func_177229_b((IProperty)this.typeProperty));
        switch (type) {
            case wood: 
            case iron: {
                ret.add(this.getItemStack(type));
                break;
            }
            case reinforced_wood: {
                ret.add(this.getItemStack(ScaffoldType.wood));
                ret.add(new ItemStack(Items.field_151055_y, 2));
                break;
            }
            case reinforced_iron: {
                ret.add(this.getItemStack(ScaffoldType.iron));
                ret.add(BlockName.fence.getItemStack(BlockIC2Fence.IC2FenceType.iron));
                break;
            }
            default: {
                throw new IllegalStateException();
            }
        }
        return ret;
    }

    public boolean func_180639_a(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (player.func_70093_af()) {
            return false;
        }
        ItemStack stack = player.func_184586_b(hand);
        if (StackUtil.isEmpty(stack)) {
            return false;
        }
        ScaffoldType type = (ScaffoldType)this.getType(state);
        if (type == null) {
            return false;
        }
        int stickCount = 2;
        boolean fenceCount = true;
        switch (type) {
            case wood: {
                if (stickInput.matches(stack) && StackUtil.getSize(stack) >= 2) break;
                return false;
            }
            case iron: {
                if (StackUtil.checkItemEquality(stack, BlockName.fence.getItemStack(BlockIC2Fence.IC2FenceType.iron)) && StackUtil.getSize(stack) >= 1) break;
                return false;
            }
            case reinforced_wood: 
            case reinforced_iron: {
                return false;
            }
            default: {
                throw new IllegalStateException();
            }
        }
        if (!this.isPillar(world, pos)) {
            return false;
        }
        switch (type) {
            case wood: {
                StackUtil.consumeOrError(player, hand, StackUtil.recipeInput(stickInput), 2);
                type = ScaffoldType.reinforced_wood;
                break;
            }
            case iron: {
                StackUtil.consumeOrError(player, hand, StackUtil.sameStack(BlockName.fence.getItemStack(BlockIC2Fence.IC2FenceType.iron)), 1);
                type = ScaffoldType.reinforced_iron;
                break;
            }
            default: {
                throw new IllegalStateException();
            }
        }
        world.func_175656_a(pos, state.func_177226_a((IProperty)this.typeProperty, (Comparable)((Object)type)));
        return true;
    }

    public void func_180649_a(World world, BlockPos pos, EntityPlayer player) {
        EnumHand hand = EnumHand.MAIN_HAND;
        ItemStack stack = player.func_184586_b(hand);
        if (StackUtil.isEmpty(stack)) {
            return;
        }
        if (StackUtil.checkItemEquality(stack, Item.func_150898_a((Block)this))) {
            while (world.func_180495_p(pos).func_177230_c() == this) {
                pos = pos.func_177984_a();
            }
            if (this.func_176196_c(world, pos) && pos.func_177956_o() < IC2.getWorldHeight(world)) {
                boolean isCreative = player.field_71075_bZ.field_75098_d;
                ItemStack prev = isCreative ? StackUtil.copy(stack) : null;
                stack.func_179546_a(player, world, pos.func_177977_b(), hand, EnumFacing.UP, 0.5f, 1.0f, 0.5f);
                if (!isCreative) {
                    StackUtil.clearEmpty(player, hand);
                } else {
                    StackUtil.set(player, hand, prev);
                }
            }
        }
    }

    public boolean func_176196_c(World world, BlockPos pos) {
        return super.func_176196_c(world, pos) && this.hasSupport((IBlockAccess)world, pos, ScaffoldType.wood);
    }

    public void func_189540_a(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighborPos) {
        this.checkSupport(world, pos);
    }

    public void func_180645_a(World world, BlockPos pos, IBlockState state, Random random) {
        if (random.nextInt(8) == 0) {
            this.checkSupport(world, pos);
        }
    }

    private boolean isPillar(World world, BlockPos pos) {
        while (world.func_180495_p(pos).func_177230_c() == this) {
            pos = pos.func_177977_b();
        }
        return world.func_175677_d(pos, false);
    }

    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        ScaffoldType type = (ScaffoldType)this.getType(world, pos);
        if (type == null) {
            return 0;
        }
        switch (type) {
            case wood: 
            case reinforced_wood: {
                return 8;
            }
            case iron: 
            case reinforced_iron: {
                return 0;
            }
        }
        throw new IllegalStateException();
    }

    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        ScaffoldType type = (ScaffoldType)this.getType(world, pos);
        if (type == null) {
            return 0;
        }
        switch (type) {
            case wood: 
            case reinforced_wood: {
                return 20;
            }
            case iron: 
            case reinforced_iron: {
                return 0;
            }
        }
        throw new IllegalStateException();
    }

    private boolean hasSupport(IBlockAccess world, BlockPos start, ScaffoldType type) {
        return this.calculateSupport((IBlockAccess)world, (BlockPos)start, (ScaffoldType)type).get((Object)start).strength >= 0;
    }

    private void checkSupport(World world, BlockPos start) {
        IBlockState state = world.func_180495_p(start);
        if (state.func_177230_c() != this) {
            return;
        }
        Map<BlockPos, Support> results = this.calculateSupport((IBlockAccess)world, start, (ScaffoldType)((Object)state.func_177229_b((IProperty)this.typeProperty)));
        boolean droppedAny = false;
        for (Support support : results.values()) {
            if (support.strength >= 0) continue;
            world.func_180501_a(support.pos, Blocks.field_150350_a.func_176223_P(), 2);
            this.func_176226_b(world, support.pos, this.func_176223_P().func_177226_a((IProperty)this.typeProperty, (Comparable)((Object)support.type)), 0);
            droppedAny = true;
        }
        if (droppedAny) {
            for (Support support : results.values()) {
                if (support.strength >= 0) continue;
                world.func_175722_b(support.pos, (Block)this, true);
            }
        }
    }

    private Map<BlockPos, Support> calculateSupport(IBlockAccess world, BlockPos start, ScaffoldType type) {
        HashMap<BlockPos, Support> results = new HashMap<BlockPos, Support>();
        ArrayDeque<Support> queue = new ArrayDeque<Support>();
        HashSet<BlockPos> groundSupports = new HashSet<BlockPos>();
        Support support = new Support(start, type, -1);
        results.put(start, support);
        queue.add(support);
        while ((support = (Support)queue.poll()) != null) {
            for (EnumFacing dir : EnumFacing.field_82609_l) {
                BlockPos pos = support.pos.func_177972_a(dir);
                if (results.containsKey(pos)) continue;
                EnumFacing[] state = world.func_180495_p(pos);
                Block block = state.func_177230_c();
                if (block == this) {
                    type = (ScaffoldType)((Object)state.func_177229_b((IProperty)this.typeProperty));
                    Support cSupport = new Support(pos, type, -1);
                    results.put(pos, cSupport);
                    queue.add(cSupport);
                    continue;
                }
                if (!block.isNormalCube((IBlockState)state, world, pos)) continue;
                groundSupports.add(pos);
            }
        }
        for (BlockPos groundPos : groundSupports) {
            BlockPos pos = groundPos.func_177984_a();
            int propagatedStrength = 0;
            while ((support = (Support)results.get(pos)) != null) {
                int strength;
                if (support.type.strength >= propagatedStrength) {
                    strength = support.type.strength;
                    propagatedStrength = strength - 1;
                } else {
                    strength = propagatedStrength--;
                }
                if (support.strength < strength) {
                    support.strength = strength;
                    for (EnumFacing dir : EnumFacing.field_176754_o) {
                        BlockPos nPos = pos.func_177972_a(dir);
                        Support nSupport = (Support)results.get(nPos);
                        if (nSupport == null || nSupport.strength >= strength) continue;
                        nSupport.strength = strength - 1;
                        queue.add(nSupport);
                    }
                }
                pos = pos.func_177984_a();
            }
        }
        while ((support = (Support)queue.poll()) != null) {
            for (EnumFacing dir : supportedFacings) {
                BlockPos pos = support.pos.func_177972_a(dir);
                Support nSupport = (Support)results.get(pos);
                if (nSupport == null || nSupport.strength >= support.strength) continue;
                nSupport.strength = support.strength - 1;
                if (nSupport.strength <= 0) continue;
                queue.add(nSupport);
            }
        }
        return results;
    }

    public static enum ScaffoldType implements IIdProvider,
    IExtBlockType,
    IBlockSound
    {
        wood(2, 0.5f, 0.12f, SoundType.field_185848_a),
        reinforced_wood(5, 0.6f, 0.24f, SoundType.field_185848_a),
        iron(5, 0.8f, 6.0f, SoundType.field_185852_e),
        reinforced_iron(12, 1.0f, 8.0f, SoundType.field_185852_e);

        public final int strength;
        private final float hardness;
        private final float explosionResistance;
        private final SoundType sound;

        private ScaffoldType(int strength, float hardness, float explosionResistance, SoundType sound) {
            if (strength < 1) {
                throw new IllegalArgumentException();
            }
            this.strength = strength;
            this.hardness = hardness;
            this.explosionResistance = explosionResistance;
            this.sound = sound;
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

        @Override
        public SoundType getSound() {
            return this.sound;
        }
    }

    private static class Support {
        final BlockPos pos;
        final ScaffoldType type;
        int strength;

        Support(BlockPos pos, ScaffoldType type, int strength) {
            this.pos = pos;
            this.type = type;
            this.strength = strength;
        }
    }
}

