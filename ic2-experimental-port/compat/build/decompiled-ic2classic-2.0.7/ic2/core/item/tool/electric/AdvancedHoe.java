/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Tiers
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.ToolAction
 *  net.minecraftforge.common.ToolActions
 */
package ic2.core.item.tool.electric;

import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.base.ElectricToolItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.base.features.IMultiTargetTool;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.collection.IterableWrapper;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.math.geometry.Box;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import java.util.Iterator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class AdvancedHoe
extends ElectricToolItem
implements ISimpleItemModel,
IMultiTargetTool {
    public AdvancedHoe() {
        this("advanced_hoe");
    }

    public AdvancedHoe(String itemName) {
        super(itemName, Tiers.NETHERITE.m_6624_(), Tiers.NETHERITE.m_6631_(), -3.0f, new PropertiesBuilder());
        this.registerTools(Tool.HOE.withLevel(Tiers.NETHERITE.m_6604_()));
        this.capacity = 100000;
        this.tier = 3;
        this.transferLimit = 100;
    }

    @Override
    public int getEnergyCost(ItemStack stack) {
        return 100;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)) {
            ItemStack stack = playerIn.m_21120_(handIn);
            CompoundTag nbt = stack.m_41784_();
            int newValue = Mth.m_14045_((int)(nbt.m_128451_("radius") + (playerIn.m_6144_() ? -1 : 1)), (int)0, (int)5);
            nbt.m_128405_("radius", newValue);
            if (IC2.PLATFORM.isSimulating()) {
                playerIn.m_5661_((Component)this.translate("tooltip.item.ic2.adv_hoe.radius", new Object[]{newValue}), false);
            }
            nbt.m_128473_("lastPos");
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(worldIn, playerIn, handIn);
    }

    public InteractionResult m_6225_(UseOnContext context) {
        Player player = context.m_43723_();
        ItemStack stack = context.m_43722_();
        Level world = context.m_43725_();
        int used = 0;
        for (BlockPos pos : IterableWrapper.wrap(this.getHitPositions(stack, player, context.m_8083_(), context.m_43719_()))) {
            BlockState state = world.m_8055_(pos).getToolModifiedState(context, ToolActions.HOE_TILL, false);
            if (state == null) continue;
            world.m_5594_(player, pos, SoundEvents.f_11955_, SoundSource.BLOCKS, 1.0f, 1.0f);
            ++used;
            if (world.f_46443_) continue;
            world.m_7731_(pos, state, 11);
        }
        if (used > 0) {
            stack.m_41784_().m_128473_("lastPos");
            ElectricItem.MANAGER.use(stack, this.getEnergyCost(stack) * used, (LivingEntity)player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools/hoe").get("advanced");
    }

    @Override
    public InteractionResult getEnchantmentCompatibility(ItemStack stack, Enchantment ench) {
        return ench == Enchantments.f_44987_ || ench == Enchantments.f_44984_ ? InteractionResult.SUCCESS : super.getEnchantmentCompatibility(stack, ench);
    }

    @Override
    public boolean isMultiMining(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canMultiMine(ItemStack stack) {
        return true;
    }

    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolActions.DEFAULT_HOE_ACTIONS.contains(toolAction);
    }

    @Override
    public Iterator<BlockPos> getHitPositions(ItemStack stack, Player player, BlockPos pos, Direction dir) {
        final CompoundTag nbt = stack.m_41784_();
        if (pos.m_121878_() != nbt.m_128454_("lastPos")) {
            boolean remove = true;
            int limit = ElectricItem.MANAGER.getCharge(stack) / this.getEnergyCost(stack);
            if (limit > 0) {
                UseOnContext context = new UseOnContext(player, player.m_21205_() == stack ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND, BlockHitResult.m_82426_((Vec3)Vec3.f_82478_, (Direction)dir, (BlockPos)pos));
                BlockState state = player.f_19853_.m_8055_(pos).getToolModifiedState(context, ToolActions.HOE_TILL, true);
                if (state != null && player.f_19853_.m_46859_(pos.m_7494_())) {
                    LongArrayList list = new LongArrayList();
                    list.add(pos.m_121878_());
                    int radius = StackUtil.getNbtData(stack).m_128451_("radius");
                    if (radius > 0) {
                        for (BlockPos subPos : Box.fromPos(pos, true).expandSide(Direction.Axis.Y, radius)) {
                            if (subPos.equals((Object)pos) || (state = player.f_19853_.m_8055_(subPos).getToolModifiedState(context, ToolActions.HOE_TILL, true)) == null || !player.f_19853_.m_46859_(subPos.m_7494_())) continue;
                            list.add(subPos.m_121878_());
                            if (list.size() < limit) continue;
                            break;
                        }
                    }
                    nbt.m_128388_("positions", list.toLongArray());
                    remove = false;
                }
            }
            if (remove) {
                nbt.m_128473_("positions");
            }
            nbt.m_128356_("lastPos", pos.m_121878_());
        }
        return new Iterator<BlockPos>(){
            long[] positions;
            int index;
            BlockPos.MutableBlockPos position;
            {
                this.positions = nbt.m_128467_("positions");
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
}

