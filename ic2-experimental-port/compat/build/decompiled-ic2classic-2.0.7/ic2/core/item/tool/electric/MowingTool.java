/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Tiers
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BushBlock
 *  net.minecraft.world.level.block.CropBlock
 *  net.minecraft.world.level.block.DoublePlantBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.DoubleBlockHalf
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraftforge.common.ToolActions
 */
package ic2.core.item.tool.electric;

import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.base.ElectricToolItem;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.IItemModel;
import ic2.core.platform.rendering.features.item.IToolModel;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.ToolActions;

public class MowingTool
extends ElectricToolItem
implements IItemModel,
IToolModel {
    public MowingTool() {
        super("mowing_tool", -1.0f, Tiers.IRON.m_6631_(), -3.0f, new PropertiesBuilder().group(IC2.IC2_MAIN_GROUP));
        this.provider = false;
        this.capacity = 10000;
        this.tier = 1;
        this.transferLimit = 100;
        this.provider = false;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (IC2.PLATFORM.isSimulating()) {
            CompoundTag nbt;
            nbt.m_128379_("active", !(nbt = stack.m_41784_()).m_128471_("active"));
            player.m_5661_((Component)this.translate("info.item.ic2.mowing_tool.state", new Object[]{MowingTool.isActive(stack) ? "enabled" : "disabled"}), false);
        }
        return InteractionResultHolder.m_19090_((Object)stack);
    }

    public void m_6883_(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
        ItemStack offHand;
        if (!MowingTool.isActive(stack) || !(entity instanceof Player) || world.f_46443_) {
            return;
        }
        Player player = (Player)entity;
        if (!isSelected && (offHand = player.m_21206_()).m_41720_() != this) {
            return;
        }
        if (!ElectricItem.MANAGER.canUse(stack, this.getEnergyCost(stack))) {
            return;
        }
        if (world.m_46467_() % 5L == 0L) {
            BlockHitResult result = MowingTool.m_41435_((Level)world, (Player)player, (ClipContext.Fluid)ClipContext.Fluid.NONE);
            if (result.m_6662_() == HitResult.Type.MISS && ((result = IC2Item.rayTraceEntities(world, (LivingEntity)player, false, 5.0)) == null || result.m_6662_() == HitResult.Type.MISS)) {
                return;
            }
            if (result.m_6662_() == HitResult.Type.ENTITY) {
                EntityHitResult entityRayTraceResult = (EntityHitResult)result;
                entityRayTraceResult.m_82443_().m_6469_(DamageSource.f_19318_, 1.0f);
            } else if (result.m_6662_() == HitResult.Type.BLOCK) {
                BlockPos pos = result.m_82425_();
                ObjectList list = CollectionUtils.createList();
                UseOnContext context = new UseOnContext(player, isSelected ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND, result);
                for (int x = -1; x <= 1; ++x) {
                    for (int y = -1; y <= 1; ++y) {
                        for (int z = -1; z <= 1; ++z) {
                            BlockPos target = pos.m_7918_(x, y, z);
                            BlockState state = world.m_8055_(target);
                            BlockState newState = state.getToolModifiedState(context, ToolActions.AXE_SCRAPE, isSelected);
                            if (newState != null) {
                                world.m_46597_(target.m_7949_(), newState);
                                continue;
                            }
                            list.addAll(this.getDrops(state, (ServerLevel)world, target, null, (LivingEntity)player, stack));
                        }
                    }
                }
                this.addItemsToPlayer((List<ItemStack>)list, player);
            }
        }
    }

    public List<ItemStack> getDrops(BlockState state, ServerLevel world, BlockPos pos, BlockEntity tileEntity, LivingEntity entity, ItemStack stack) {
        List drops;
        LinkedList<ItemStack> list = new LinkedList<ItemStack>();
        Block block = state.m_60734_();
        if (!(block instanceof CropBlock)) {
            if (block instanceof DoublePlantBlock) {
                DoubleBlockHalf half = (DoubleBlockHalf)state.m_61143_((Property)DoublePlantBlock.f_52858_);
                if (half == DoubleBlockHalf.UPPER && !((block = (state = world.m_8055_(pos = pos.m_7495_())).m_60734_()) instanceof DoublePlantBlock)) {
                    return list;
                }
                List drops2 = Block.m_49874_((BlockState)state, (ServerLevel)world, (BlockPos)pos, (BlockEntity)tileEntity, (Entity)entity, (ItemStack)stack);
                if (drops2.size() > 0) {
                    list.addAll(drops2);
                }
                world.m_5594_(null, pos, state.m_60827_().m_56775_(), SoundSource.BLOCKS, 1.0f, 1.0f);
                world.m_7731_(pos, Blocks.f_50016_.m_49966_(), 2);
                world.m_46597_(pos.m_7494_(), Blocks.f_50016_.m_49966_());
                ElectricItem.MANAGER.use(stack, 5, entity);
            } else if (block instanceof BushBlock) {
                drops = Block.m_49874_((BlockState)state, (ServerLevel)world, (BlockPos)pos, (BlockEntity)tileEntity, (Entity)entity, (ItemStack)stack);
                if (drops.size() > 0) {
                    list.addAll(drops);
                }
                world.m_5594_(null, pos, state.m_60827_().m_56775_(), SoundSource.BLOCKS, 1.0f, 1.0f);
                world.m_46597_(pos, Blocks.f_50016_.m_49966_());
                ElectricItem.MANAGER.use(stack, 5, entity);
            }
        }
        if (state.m_204336_(BlockTags.f_13035_)) {
            drops = Block.m_49874_((BlockState)state, (ServerLevel)world, (BlockPos)pos, (BlockEntity)tileEntity, (Entity)entity, (ItemStack)stack);
            if (drops.size() > 0) {
                list.addAll(drops);
            }
            world.m_46597_(pos, Blocks.f_50016_.m_49966_());
            world.m_5594_(null, pos, state.m_60827_().m_56775_(), SoundSource.BLOCKS, 1.0f, 1.0f);
            ElectricItem.MANAGER.use(stack, 5, entity);
        }
        return list;
    }

    public void addItemsToPlayer(List<ItemStack> list, Player player) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (ItemStack stack : list) {
            StackUtil.addOrDrop(player, stack);
        }
    }

    @Override
    public int getEnergyCost(ItemStack stack) {
        return 1;
    }

    @Override
    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (!this.m_220152_(group)) {
            return;
        }
        IC2ElectricItem.addEmptyAndFullToGroup((ItemLike)this, items);
    }

    @Override
    public List<ItemStack> getModelTypes() {
        ObjectList types = CollectionUtils.createList();
        ItemStack inactiveState = new ItemStack((ItemLike)this);
        inactiveState.m_41784_().m_128379_("active", false);
        types.add((ItemStack)inactiveState);
        ItemStack activeState = new ItemStack((ItemLike)this);
        activeState.m_41784_().m_128379_("active", true);
        types.add((ItemStack)activeState);
        return types;
    }

    @Override
    public TextureAtlasSprite getSprite(ItemStack input) {
        return IC2Textures.getMappedEntriesItemIC2("tools/mowing").get("base_" + (MowingTool.isActive(input) ? "on" : "off"));
    }

    @Override
    public int getModelIndexForStack(ItemStack stack, @Nullable LivingEntity entity) {
        return MowingTool.isActive(stack) ? 1 : 0;
    }

    public static boolean isActive(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128471_("active");
    }

    @Override
    public InteractionResult getEnchantmentCompatibility(ItemStack stack, Enchantment ench) {
        return ench == Enchantments.f_44984_ ? InteractionResult.SUCCESS : super.getEnchantmentCompatibility(stack, ench);
    }
}

