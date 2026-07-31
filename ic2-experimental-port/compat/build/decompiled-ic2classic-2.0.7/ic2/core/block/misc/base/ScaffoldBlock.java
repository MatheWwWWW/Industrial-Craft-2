/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.stats.Stats
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SupportType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc.base;

import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.IC2Block;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.item.tool.SprayerTool;
import ic2.core.platform.rendering.features.IRenderType;
import ic2.core.utils.helpers.StackUtil;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class ScaffoldBlock
extends IC2Block
implements IRenderType {
    public static final VoxelShape COLLISION = Block.m_49796_((double)0.0625, (double)0.0, (double)0.0625, (double)15.9375, (double)16.0, (double)15.9375);
    protected IntegerProperty prop;
    protected int defaultStrength;
    protected int maxStrength;

    public ScaffoldBlock(String blockName, int min, int max, BlockBehaviour.Properties properties) {
        super(blockName, properties.m_60971_((T, Y, V) -> false).m_60924_((K, V, Y) -> false).m_60955_());
        this.defaultStrength = min;
        this.maxStrength = max;
        this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)this.prop, (Comparable)Integer.valueOf(min)));
    }

    public abstract IntegerProperty createProps();

    public abstract ItemStack getUpgradeItem();

    public abstract double getMaxSpeed(boolean var1);

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        this.prop = this.createProps();
        builder.m_61104_(new Property[]{this.prop});
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem(this);
    }

    public void setStrength(BlockState state, Level world, BlockPos pos, int strength) {
        world.m_46597_(pos, (BlockState)state.m_61124_((Property)this.prop, (Comparable)Integer.valueOf(Mth.m_14045_((int)strength, (int)0, (int)this.maxStrength))));
    }

    public int getStrength(BlockState state) {
        return (Integer)state.m_61143_((Property)this.prop);
    }

    public boolean m_6864_(BlockState state, BlockPlaceContext useContext) {
        return useContext.m_43722_().m_41720_() instanceof SprayerTool;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public RenderType getType() {
        return RenderType.m_110463_();
    }

    public VoxelShape m_5939_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return COLLISION;
    }

    public boolean m_7898_(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn instanceof Level && worldIn.m_8055_(pos).m_60767_().m_76336_() && this.getStrengthFrom((Level)worldIn, pos) >= 0;
    }

    public void m_7892_(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof Player) {
            Player player = (Player)entityIn;
            player.f_19789_ = 0.0f;
            Vec3 motion = player.m_20184_();
            double max = player.m_6144_() ? 0.08 : -0.15;
            double y = this.getMaxSpeed(IC2.KEYBOARD.isBoostKeyDown(player));
            if (IC2.KEYBOARD.isForwardKeyDown(player) && motion.f_82480_ < y) {
                max = y;
                if (IC2.KEYBOARD.isBoostKeyDown(player)) {
                    player.m_36399_((float)max * 0.01f);
                }
            }
            player.m_20334_(Mth.m_14008_((double)motion.f_82479_, (double)-0.15, (double)0.15), Math.max(max, motion.f_82480_), Mth.m_14008_((double)motion.f_82481_, (double)-0.15, (double)0.15));
            player.m_36222_(Stats.f_12999_, (int)Math.round(player.m_20184_().f_82480_ * 100.0));
        }
    }

    public boolean isPillar(Level world, BlockPos pos) {
        BlockPos realPos = pos;
        while (world.m_8055_(realPos).m_60734_() == this) {
            realPos = realPos.m_7495_();
        }
        return world.m_8055_(realPos).m_60659_((BlockGetter)world, realPos, Direction.UP, SupportType.FULL);
    }

    public int getConnectedStrength(Level world, BlockPos pos) {
        if (!world.m_46749_(pos)) {
            return 0;
        }
        BlockState state = world.m_8055_(pos);
        return state.m_60734_() == this ? this.getStrength(state) : 0;
    }

    public int getStrengthFrom(Level world, BlockPos pos) {
        int strength = this.isPillar(world, pos.m_7495_()) ? this.defaultStrength + 1 : 0;
        for (Direction dir : DirectionList.HORIZONTAL) {
            strength = Math.max(strength, this.getConnectedStrength(world, pos.m_121945_(dir)));
        }
        return strength - 1;
    }

    public void m_6861_(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        worldIn.m_186460_(pos, blockIn, 1);
    }

    public void m_213897_(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (state.m_60734_() != this) {
            return;
        }
        int ownStrength = this.getStrength(state);
        if (ownStrength >= this.maxStrength) {
            if (!this.isPillar((Level)worldIn, pos)) {
                ownStrength = this.getStrengthFrom((Level)worldIn, pos);
                ItemStack drop = this.getUpgradeItem();
                if (!drop.m_41619_()) {
                    Block.m_49840_((Level)worldIn, (BlockPos)pos, (ItemStack)drop);
                }
            }
        } else {
            ownStrength = this.getStrengthFrom((Level)worldIn, pos);
        }
        if (ownStrength < 0) {
            Block.m_49840_((Level)worldIn, (BlockPos)pos, (ItemStack)new ItemStack((ItemLike)this));
            worldIn.m_7471_(pos, false);
            worldIn.m_5594_(null, pos, state.m_60827_().m_56775_(), SoundSource.BLOCKS, 1.0f, 1.0f);
        } else if (ownStrength != this.getStrength(state)) {
            this.setStrength(state, (Level)worldIn, pos, ownStrength);
        }
    }

    public void m_6402_(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (worldIn instanceof ServerLevel) {
            this.m_213897_(state, (ServerLevel)worldIn, pos, worldIn.f_46441_);
        }
    }

    public void m_6256_(BlockState state, Level worldIn, BlockPos pos, Player player) {
        if (!(worldIn instanceof ServerLevel)) {
            return;
        }
        ServerLevel world = (ServerLevel)worldIn;
        if (this.handleSideScaffold(world, pos, player)) {
            return;
        }
        this.handleStandardScaffold(world, pos, player);
    }

    public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack upgrade;
        if (player.m_6144_()) {
            return InteractionResult.PASS;
        }
        ItemStack current = player.m_21120_(handIn);
        if (!StackUtil.isStackEqual(current, upgrade = this.getUpgradeItem()) || current.m_41613_() < upgrade.m_41613_()) {
            return InteractionResult.PASS;
        }
        if (this.getStrength(state) == this.maxStrength || !this.isPillar(worldIn, pos)) {
            return InteractionResult.PASS;
        }
        if (!player.m_7500_()) {
            current.m_41774_(upgrade.m_41613_());
        }
        this.setStrength(state, worldIn, pos, this.maxStrength);
        return InteractionResult.SUCCESS;
    }

    public boolean handleSideScaffold(ServerLevel world, BlockPos pos, Player player) {
        BlockPos cuPos;
        if (player.m_20186_() <= (double)pos.m_123342_()) {
            return false;
        }
        Direction facing = Direction.m_122364_((double)player.m_146908_());
        ItemStack stack = player.m_21205_();
        if (stack.m_41720_() == this.m_5456_()) {
            cuPos = pos;
            while (world.m_8055_(cuPos).m_60734_() == this) {
                cuPos = cuPos.m_121945_(facing);
            }
            if (this.m_7898_(this.m_49966_(), (LevelReader)world, cuPos)) {
                world.m_46597_(cuPos, this.m_49966_());
                this.m_213897_(this.m_49966_(), world, cuPos, world.m_213780_());
                this.onScaffoldPlaced(player, (Level)world, pos, cuPos, false);
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                return true;
            }
        }
        if ((stack = player.m_21206_()).m_41720_() == this.m_5456_()) {
            cuPos = pos;
            while (world.m_8055_(cuPos).m_60734_() == this) {
                cuPos = cuPos.m_121945_(facing);
            }
            if (this.m_7898_(this.m_49966_(), (LevelReader)world, cuPos)) {
                world.m_46597_(cuPos, this.m_49966_());
                this.m_213897_(this.m_49966_(), world, cuPos, world.m_213780_());
                this.onScaffoldPlaced(player, (Level)world, pos, cuPos, false);
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                return true;
            }
        }
        return true;
    }

    public void handleStandardScaffold(ServerLevel world, BlockPos pos, Player player) {
        BlockPos cuPos;
        ItemStack stack = player.m_21205_();
        if (stack.m_41720_() == this.m_5456_()) {
            cuPos = pos;
            while (world.m_8055_(cuPos).m_60734_() == this) {
                cuPos = cuPos.m_7494_();
            }
            if (this.m_7898_(this.m_49966_(), (LevelReader)world, cuPos) && cuPos.m_123342_() < world.m_151558_()) {
                world.m_46597_(cuPos, this.m_49966_());
                this.m_213897_(this.m_49966_(), world, cuPos, world.m_213780_());
                this.onScaffoldPlaced(player, (Level)world, pos, cuPos, true);
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                return;
            }
        }
        if ((stack = player.m_21206_()).m_41720_() == this.m_5456_()) {
            cuPos = pos;
            while (world.m_8055_(cuPos).m_60734_() == this) {
                cuPos = cuPos.m_7494_();
            }
            if (this.m_7898_(this.m_49966_(), (LevelReader)world, cuPos) && cuPos.m_123342_() < world.m_151558_()) {
                world.m_46597_(cuPos, this.m_49966_());
                this.m_213897_(this.m_49966_(), world, cuPos, world.m_213780_());
                this.onScaffoldPlaced(player, (Level)world, pos, cuPos, true);
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
            }
        }
    }

    protected void onScaffoldPlaced(Player player, Level level, BlockPos clickPos, BlockPos placePos, boolean vertical) {
    }

    @Override
    public void addExtraDrops(List<ItemStack> list, BlockState state, ItemStack tool, RandomSource rand) {
        if (this.getStrength(state) == this.maxStrength) {
            list.add(this.getUpgradeItem());
        }
    }
}

