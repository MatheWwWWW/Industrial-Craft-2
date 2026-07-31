/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.Nameable
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.BaseEntityBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.block.base;

import ic2.api.blocks.IAdvancedComparable;
import ic2.api.blocks.IWrenchable;
import ic2.core.IC2;
import ic2.core.block.base.IAutoCreator;
import ic2.core.block.base.IToolProvider;
import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.base.drops.IRarityProvider;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.features.ICollideable;
import ic2.core.block.base.features.IDropProvider;
import ic2.core.block.base.features.IParticleSpawner;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.features.personal.IPersonalWrenchable;
import ic2.core.block.base.features.redstone.IComparatorProvider;
import ic2.core.block.base.features.redstone.IRedstoneListener;
import ic2.core.block.base.features.redstone.IRedstoneProvider;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.inventory.base.IHasGui;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.plugins.IRegistryProvider;
import ic2.core.utils.tooltips.ILangHelper;
import ic2.core.utils.tooltips.helper.ITooltipProvider;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.GameData;

public abstract class IC2ContainerBlock
extends BaseEntityBlock
implements IAdvancedComparable,
IAutoCreator,
IWrenchable,
IRarityProvider,
IRegistryProvider,
ILangHelper,
IToolProvider {
    protected boolean animations;
    protected Rarity rarityOverride = null;
    List<ITooltipProvider> tooltips = null;
    IBlockDropProvider provider = IBlockDropProvider.SELF;
    EnumMap<Tool.ToolType, Tool> harvestMap = new EnumMap(Tool.ToolType.class);
    protected ResourceLocation id;

    public IC2ContainerBlock(String blockName, BlockBehaviour.Properties properties) {
        super(properties);
        this.id = GameData.checkPrefix((String)blockName, (boolean)false);
    }

    @Override
    public void registerTools() {
        for (Tool tool : this.harvestMap.values()) {
            tool.register((Block)this);
        }
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    public IC2ContainerBlock enableAnimations() {
        this.animations = true;
        return this;
    }

    public IC2ContainerBlock setDropProvider(IBlockDropProvider provider) {
        this.provider = provider;
        return this;
    }

    public IC2ContainerBlock setOverrideRarity(Rarity rarity) {
        this.rarityOverride = rarity;
        return this;
    }

    public IC2ContainerBlock setHarvestTool(Tool tier) {
        this.harvestMap.put(tier.getType(), tier);
        return this;
    }

    public IC2ContainerBlock setTooltips(ITooltipProvider tooltips) {
        this.tooltips = CollectionUtils.asList(tooltips);
        return this;
    }

    public IC2ContainerBlock addTooltip(ITooltipProvider tooltips) {
        if (this.tooltips == null) {
            this.tooltips = CollectionUtils.createList();
        }
        this.tooltips.add(tooltips);
        return this;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return this.rarityOverride;
    }

    public RenderShape m_7514_(BlockState state) {
        return RenderShape.MODEL;
    }

    public abstract BlockEntity m_142194_(BlockPos var1, BlockState var2);

    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level world, BlockState state, BlockEntityType<T> type) {
        return ITickListener::update;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void m_5871_(ItemStack stack, BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (this.tooltips != null) {
            int m = this.tooltips.size();
            for (int i = 0; i < m; ++i) {
                this.tooltips.get(i).addInformation(stack, worldIn, tooltip, flagIn);
            }
        }
    }

    public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        IClickable click;
        BlockEntity te = worldIn.m_7702_(pos);
        if (te instanceof IClickable && (click = (IClickable)te).getRequiredActions().canDoRightClick() && click.onRightClick(player, handIn, hit.m_82434_(), hit)) {
            return InteractionResult.SUCCESS;
        }
        if (player.m_6144_()) {
            return InteractionResult.PASS;
        }
        return te instanceof IHasGui && (IC2.PLATFORM.isRendering() && ((IHasGui)te).hasGui(player, handIn, hit.m_82434_()) || IC2.PLATFORM.launchGui(player, handIn, hit.m_82434_(), (IHasGui)te)) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    public void m_6256_(BlockState state, Level worldIn, BlockPos pos, Player player) {
        BlockEntity tile = worldIn.m_7702_(pos);
        if (!(tile instanceof IClickable)) {
            return;
        }
        IClickable click = (IClickable)tile;
        if (click.getRequiredActions().canDoLeftClick()) {
            click.onLeftClick(player, pos);
        }
    }

    public boolean m_7278_(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState state, Level world, BlockPos pos, Direction side) {
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof IComparatorProvider) {
            return ((IComparatorProvider)tile).getSignalStrength(side);
        }
        return 0;
    }

    public int m_6782_(BlockState blockState, Level worldIn, BlockPos pos) {
        return this.getComparatorInputOverride(blockState, worldIn, pos, null);
    }

    public int m_6376_(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        BlockEntity tile = blockAccess.m_7702_(pos);
        if (tile instanceof IRedstoneProvider) {
            return ((IRedstoneProvider)tile).getStrongSignalStrength(side);
        }
        return 0;
    }

    public int m_6378_(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        BlockEntity tile = blockAccess.m_7702_(pos);
        if (tile instanceof IRedstoneProvider) {
            return ((IRedstoneProvider)tile).getWeakSignalStrength(side);
        }
        return 0;
    }

    public boolean shouldCheckWeakPower(BlockState state, LevelReader world, BlockPos pos, Direction side) {
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof IRedstoneListener) {
            return ((IRedstoneListener)tile).allowWeakSignal(side);
        }
        return super.shouldCheckWeakPower(state, world, pos, side);
    }

    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof IRedstoneListener) {
            return ((IRedstoneListener)tile).canConnectToRedstone(side);
        }
        return super.canConnectRedstone(state, world, pos, side);
    }

    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        BlockEntity tile = level.m_7702_(pos);
        if (tile instanceof BaseTileEntity) {
            ((BaseTileEntity)tile).onComparatorUpdate(neighbor);
        }
    }

    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        ItemStack stack = super.getCloneItemStack(state, target, world, pos, player);
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof Nameable && ((Nameable)tile).m_8077_()) {
            stack.m_41714_(((Nameable)tile).m_7770_());
        }
        return stack;
    }

    public List<ItemStack> m_7381_(BlockState state, LootContext.Builder builder) {
        LootTable loottable = builder.m_78962_().m_7654_().m_129898_().m_79217_(this.m_60589_());
        if (loottable != LootTable.f_79105_) {
            return loottable.m_230922_(builder.m_78972_(LootContextParams.f_81461_, (Object)state).m_78975_(LootContextParamSets.f_81421_));
        }
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        BlockEntity tile = (BlockEntity)builder.m_78982_(LootContextParams.f_81462_);
        ItemStack stack = this.createDrop(state, (ItemStack)builder.m_78982_(LootContextParams.f_81463_), builder.m_78962_().f_46441_, tile, false);
        if (!stack.m_41619_()) {
            if (tile instanceof Nameable && ((Nameable)tile).m_8077_()) {
                stack.m_41714_(((Nameable)tile).m_7770_());
            }
            drops.add(stack);
        }
        this.addExtraDrops(drops, state, stack, builder.m_78962_().m_213780_());
        if (tile instanceof IDropProvider) {
            ((IDropProvider)tile).addDrops(drops);
        }
        return drops;
    }

    public ItemStack createDrop(BlockState state, ItemStack stack, RandomSource rand, @Nullable BlockEntity tile, boolean wrench) {
        return this.provider.createDrop(state, stack, rand, tile, wrench);
    }

    public void addExtraDrops(List<ItemStack> list, BlockState state, ItemStack tool, RandomSource rand) {
    }

    public void m_6861_(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        BlockEntity tile = worldIn.m_7702_(pos);
        if (tile instanceof BaseTileEntity) {
            ((BaseTileEntity)tile).onBlockUpdate(blockIn, fromPos);
        }
    }

    public void m_7892_(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        BlockEntity tile = worldIn.m_7702_(pos);
        if (tile instanceof ICollideable) {
            ((ICollideable)tile).onEntityCollided(entityIn);
        }
    }

    @Override
    public Direction getFacing(BlockState state, Level world, BlockPos pos) {
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof BaseTileEntity) {
            return ((BaseTileEntity)tile).getFacing();
        }
        return Direction.NORTH;
    }

    @Override
    public boolean canSetFacing(BlockState state, Level world, BlockPos pos, Player player, Direction side) {
        BlockEntity tile = world.m_7702_(pos);
        return tile instanceof IPersonalWrenchable && ((IPersonalWrenchable)tile).canSetFacing(side, player) || tile instanceof IWrenchableTile && ((IWrenchableTile)tile).canSetFacing(side);
    }

    @Override
    public boolean setFacing(BlockState state, Level world, BlockPos pos, Player player, Direction side) {
        IWrenchableTile base;
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof IPersonalWrenchable) {
            IPersonalWrenchable base2 = (IPersonalWrenchable)tile;
            if (base2.canSetFacing(side, player)) {
                base2.setFacing(side);
                return true;
            }
        } else if (tile instanceof IWrenchableTile && (base = (IWrenchableTile)tile).canSetFacing(side)) {
            base.setFacing(side);
            return true;
        }
        return false;
    }

    @Override
    public AABB hasSpecialAction(BlockState state, Level world, BlockPos pos, Direction dir, Player player, Vec3 hit) {
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof IWrenchableTile) {
            return ((IWrenchableTile)tile).hasSpecialAction(dir, hit, player);
        }
        return null;
    }

    @Override
    public boolean doSpecialAction(BlockState state, Level world, BlockPos pos, Direction dir, Player player, Vec3 hit) {
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof IWrenchableTile) {
            return ((IWrenchableTile)tile).doSpecialAction(dir, hit, player);
        }
        return false;
    }

    @Override
    public boolean canRemoveBlock(BlockState state, Level world, BlockPos pos, Player player) {
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof IWrenchableTile) {
            return ((IWrenchableTile)tile).canRemoveBlock(player);
        }
        return false;
    }

    @Override
    public double getDropRate(BlockState state, Level world, BlockPos pos, Player player) {
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof IWrenchableTile) {
            return ((IWrenchableTile)tile).getDropRate(player);
        }
        return 0.0;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, Level world, BlockPos pos, Player player) {
        BlockEntity tile = world.m_7702_(pos);
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        ItemStack stack = this.createDrop(state, ItemStack.f_41583_, world.f_46441_, tile, true);
        if (!stack.m_41619_()) {
            if (tile instanceof Nameable && ((Nameable)tile).m_8077_()) {
                stack.m_41714_(((Nameable)tile).m_7770_());
            }
            drops.add(stack);
        }
        if (tile instanceof IDropProvider) {
            ((IDropProvider)tile).addDrops(drops);
        }
        return drops;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void m_214162_(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        BlockEntity tile;
        if (this.animations && (tile = worldIn.m_7702_(pos)) instanceof IParticleSpawner) {
            ((IParticleSpawner)tile).animationTick(rand);
        }
    }
}

