/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.level.BlockEvent$BreakEvent
 *  net.minecraftforge.eventbus.api.Event
 */
package ic2.core.item.tool.electric;

import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.base.features.IMultiTargetTool;
import ic2.core.item.tool.electric.DrillTool;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.utils.collection.IterableWrapper;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Box;
import java.util.Iterator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;

public class AdvancedDrill
extends DrillTool
implements IMultiTargetTool {
    public AdvancedDrill() {
        super("advanced_drill", 3, 80, 30.0f);
        this.tier = 3;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools/drill").get("advanced");
    }

    @Override
    public float m_8102_(ItemStack stack, BlockState state) {
        if (!ElectricItem.MANAGER.canUse(stack, this.getEnergyCost(stack))) {
            return 1.0f;
        }
        if (this.isCorrectToolForDrops(stack, state)) {
            return this.isMultiMining(stack) ? 270.0f : 30.0f;
        }
        return 1.0f;
    }

    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        if (this.isMultiMining(stack) && this.canMultiMine(stack)) {
            Level world = player.f_19853_;
            BlockHitResult ray = AdvancedDrill.m_41435_((Level)world, (Player)player, (ClipContext.Fluid)ClipContext.Fluid.NONE);
            int removed = 0;
            for (BlockPos offset : IterableWrapper.wrap(this.getHitPositions(stack, player, pos, ray.m_82434_()))) {
                BlockState state = world.m_8055_(offset);
                if (state.m_60795_() && !(state.m_60800_((BlockGetter)world, pos) >= 0.0f)) continue;
                BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, offset, state, player);
                MinecraftForge.EVENT_BUS.post((Event)event);
                if (event.isCanceled()) continue;
                BlockEntity tile = world.m_7702_(offset);
                if (!state.onDestroyedByPlayer(world, offset, player, true, state.m_60819_())) continue;
                Block block = state.m_60734_();
                block.m_6786_((LevelAccessor)world, offset, state);
                block.m_6240_(world, player, offset, state, tile, stack);
                if (event.getExpToDrop() != -1 && world instanceof ServerLevel) {
                    ServerLevel server = (ServerLevel)world;
                    block.m_49805_(server, pos, event.getExpToDrop());
                }
                ++removed;
            }
            if (removed > 0) {
                player.m_36220_(IC2Stats.BLOCKS_DRILLED);
                ElectricItem.MANAGER.use(stack, this.getEnergyCost(stack) * removed, (LivingEntity)player);
                return true;
            }
        }
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)) {
            ItemStack stack = playerIn.m_21120_(handIn);
            CompoundTag data = stack.m_41784_();
            boolean multi = !data.m_128471_("multi");
            data.m_128379_("multi", multi);
            if (IC2.PLATFORM.isSimulating()) {
                playerIn.m_5661_((Component)this.translate(multi ? "tooltip.item.ic2.multi_mine.enable" : "tooltip.item.ic2.multi_mine.disable"), false);
            }
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(worldIn, playerIn, handIn);
    }

    @Override
    public boolean canMultiMine(ItemStack stack) {
        return ElectricItem.MANAGER.canUse(stack, this.getEnergyCost(stack) * 9);
    }

    @Override
    public boolean isMultiMining(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128471_("multi");
    }

    @Override
    public Iterator<BlockPos> getHitPositions(ItemStack stack, Player player, BlockPos pos, Direction dir) {
        return Box.fromPos(pos, true).expandSide(dir.m_122434_(), 1).iterator();
    }

    @Override
    public int getMiningBoost(ItemStack stack, BlockState state) {
        int boost = EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44984_, (ItemStack)stack);
        return 10 + (boost == 0 ? 0 : boost * boost + 1);
    }

    @Override
    public int getExtraEnergyCost(ItemStack stack) {
        int eff = EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44984_, (ItemStack)stack);
        int unb = EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44986_, (ItemStack)stack);
        return 25 + (eff == 0 ? 0 : eff * eff + 1) - unb * (unb + unb);
    }
}

