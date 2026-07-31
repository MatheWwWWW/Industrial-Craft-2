/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool.electric;

import ic2.api.items.electric.ElectricItem;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.block.resource.RubberwoodLogBlock;
import ic2.core.item.base.features.IMultiTargetTool;
import ic2.core.item.tool.TreeTapTool;
import ic2.core.item.tool.electric.ElectricTreeTap;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.utils.collection.IterableWrapper;
import ic2.core.utils.helpers.AABBUtil;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AdvancedTreeTap
extends ElectricTreeTap
implements IMultiTargetTool {
    public AdvancedTreeTap() {
        super("advanced_tree_tap");
    }

    @Override
    public boolean canMultiMine(ItemStack stack) {
        return ElectricItem.MANAGER.canUse(stack, this.energyCost * Math.max(1, StackUtil.getNbtData(stack).m_128467_("positions").length));
    }

    @Override
    public boolean isMultiMining(ItemStack stack) {
        return true;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools/treetap").get("advanced");
    }

    @Override
    public InteractionResult m_6225_(UseOnContext context) {
        Level world = context.m_43725_();
        BlockPos pos = context.m_8083_();
        Player player = context.m_43723_();
        ItemStack stack = player.m_21120_(context.m_43724_());
        BlockState state = player.f_19853_.m_8055_(pos);
        Direction side = context.m_43719_();
        if (state.m_60734_() instanceof RubberwoodLogBlock && ((Boolean)state.m_61143_((Property)RubberwoodLogBlock.RESIN)).booleanValue() && state.m_61143_((Property)RubberwoodLogBlock.RESIN_FACING) == side) {
            if (!this.canMultiMine(stack)) {
                return InteractionResult.PASS;
            }
            boolean server = IC2.PLATFORM.isSimulating();
            if (server) {
                int count = 0;
                ObjectArrayList list = new ObjectArrayList();
                for (BlockPos subPos : IterableWrapper.wrap(this.getHitPositions(stack, player, pos, side))) {
                    TreeTapTool.extractRubber(world.m_8055_(subPos), world, subPos, side, stack, (List<ItemStack>)list);
                    list.add(new ItemStack((ItemLike)IC2Items.STICKY_RESIN, TreeTapTool.getResinAmount(stack, 5, 10)));
                    ++count;
                    world.m_7471_(subPos, false);
                }
                IC2.AUDIO.playSound(context.m_43723_(), IC2Sounds.TREE_TAP, AudioManager.SoundType.ITEM);
                ElectricItem.MANAGER.use(stack, this.energyCost * count, (LivingEntity)player);
                for (ItemStack item : list) {
                    StackUtil.addOrDrop(player, item);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public Iterator<BlockPos> getHitPositions(final ItemStack stack, Player player, BlockPos pos, Direction dir) {
        BlockState state = player.f_19853_.m_8055_(pos);
        if (state.m_60734_() instanceof RubberwoodLogBlock && ((Boolean)state.m_61143_((Property)RubberwoodLogBlock.RESIN)).booleanValue() && state.m_61143_((Property)RubberwoodLogBlock.RESIN_FACING) == dir) {
            CompoundTag data = stack.m_41784_();
            if (data.m_128454_("lastPos") != pos.m_121878_()) {
                int limit = ElectricItem.MANAGER.getCharge(stack) / this.energyCost;
                ObjectList<BlockPos> result = AABBUtil.getValidBlocks(player.f_19853_, pos, 20, LogFinder.INSTANCE, 0, DirectionList.ALL, 20).getPositions();
                long[] positions = new long[Math.min(limit, result.size())];
                int m = result.size();
                for (int i = 0; i < m; ++i) {
                    positions[i] = ((BlockPos)result.get(i)).m_121878_();
                }
                data.m_128356_("lastPos", pos.m_121878_());
                data.m_128388_("positions", positions);
            }
            return new Iterator<BlockPos>(){
                long[] positions;
                int index;
                BlockPos.MutableBlockPos position;
                {
                    this.positions = StackUtil.getNbtData(stack).m_128467_("positions");
                    this.index = 0;
                    this.position = new BlockPos.MutableBlockPos();
                }

                @Override
                public boolean hasNext() {
                    return this.index < this.positions.length;
                }

                @Override
                public BlockPos next() {
                    return this.position.m_122188_(this.positions[this.index++]);
                }
            };
        }
        if (stack.m_41782_()) {
            CompoundTag data = StackUtil.getNbtData(stack);
            data.m_128473_("positions");
            data.m_128473_("lastPos");
            if (data.m_128456_()) {
                stack.m_41751_(null);
            }
        }
        return Collections.emptyIterator();
    }

    public static class LogFinder
    implements AABBUtil.IBlockFilter {
        public static final AABBUtil.IBlockFilter INSTANCE = new LogFinder();

        @Override
        public boolean isValid(BlockState state) {
            return state.m_60734_() instanceof RubberwoodLogBlock;
        }
    }
}

