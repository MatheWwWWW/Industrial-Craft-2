/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.Multimap
 *  ic2.api.items.electric.ElectricItem
 *  ic2.core.item.tool.electric.DrillTool
 *  ic2.core.platform.player.KeyHelper
 *  ic2.core.platform.rendering.IC2Textures
 *  ic2.core.utils.helpers.Tool
 *  ic2.core.utils.tooltips.ToolTipHelper
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.BaseFireBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.gameevent.GameEvent$Context
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Material
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.level.BlockEvent$BreakEvent
 *  net.minecraftforge.eventbus.api.Event
 */
package trinsdar.gravisuit.items.tools;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.tool.electric.DrillTool;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.tooltips.ToolTipHelper;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.GravisuitLang;
import trinsdar.gravisuit.util.Registry;

public class ItemToolVajra
extends DrillTool {
    public ItemToolVajra() {
        super("vajra", 4, 3333, 16384.0f);
        Registry.REGISTRY.put(new ResourceLocation("gravisuit", "vajra"), (Item)this);
        this.registerTools(new Tool[]{Tool.AXE.withLevel(4), Tool.HOE.withLevel(4)});
        this.capacity = 3000000;
        this.transferLimit = 1000;
        this.tier = 3;
    }

    public int getCapacity(ItemStack stack) {
        return GravisuitConfig.POWER_VALUES.VAJRA_STORAGE;
    }

    public int getTransferLimit(ItemStack stack) {
        return GravisuitConfig.POWER_VALUES.VAJRA_TRANSFER;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)) {
            ItemStack stack = playerIn.m_21120_(handIn);
            CompoundTag nbt = stack.m_41784_();
            boolean silkTouch = nbt.m_128471_("silkTouch");
            if (IC2.PLATFORM.isSimulating()) {
                if (silkTouch) {
                    nbt.m_128379_("silkTouch", false);
                    playerIn.m_5661_((Component)this.translate(GravisuitLang.silkTouchOff), false);
                } else {
                    nbt.m_128379_("silkTouch", true);
                    playerIn.m_5661_((Component)this.translate(GravisuitLang.silkTouchOn), false);
                }
                return InteractionResultHolder.m_19090_((Object)stack);
            }
        }
        return super.m_7203_(worldIn, playerIn, handIn);
    }

    private boolean shouldBreak(Player playerIn, Level worldIn, BlockPos pos) {
        BlockState blockState = worldIn.m_8055_(pos);
        if (blockState.m_60767_() == Material.f_76296_) {
            return false;
        }
        if (blockState.m_60767_().m_76332_()) {
            return false;
        }
        float blockHardness = blockState.m_60800_((BlockGetter)worldIn, pos);
        if (blockHardness < 0.0f) {
            return false;
        }
        return blockState.m_60734_().canHarvestBlock(blockState, (BlockGetter)worldIn, pos, playerIn);
    }

    public InteractionResult m_6225_(UseOnContext context) {
        ItemStack stack = context.m_43722_();
        Level world = context.m_43725_();
        BlockPos pos = context.m_8083_();
        Player player = context.m_43723_();
        if (ElectricItem.MANAGER.getCharge(stack) >= this.getEnergyCost(stack) && this.shouldBreak(player, world, pos) && IC2.PLATFORM.isSimulating()) {
            ElectricItem.MANAGER.use(stack, this.getEnergyCost(stack), (LivingEntity)player);
            this.destroyBlock(world, pos, true, (Entity)player, stack);
            return InteractionResult.SUCCESS;
        }
        return super.m_6225_(context);
    }

    public boolean destroyBlock(Level level, BlockPos pos, boolean dropBlock, @Nullable Entity entity, ItemStack item) {
        boolean flag;
        Player player;
        BlockState blockstate = level.m_8055_(pos);
        if (blockstate.m_60795_()) {
            return false;
        }
        FluidState fluidstate = level.m_6425_(pos);
        if (!(blockstate.m_60734_() instanceof BaseFireBlock)) {
            level.m_46796_(2001, pos, Block.m_49956_((BlockState)blockstate));
        }
        CompoundTag tag = item.m_41783_();
        BlockEntity blockentity = blockstate.m_155947_() ? level.m_7702_(pos) : null;
        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(level, pos, blockstate, entity instanceof Player ? (player = (Player)entity) : null);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            return false;
        }
        if (dropBlock) {
            Map enchantments = EnchantmentHelper.m_44831_((ItemStack)item);
            if (tag != null && tag.m_128471_("silkTouch")) {
                enchantments.put(Enchantments.f_44985_, 1);
                EnchantmentHelper.m_44865_((Map)enchantments, (ItemStack)item);
            }
            Block.m_49881_((BlockState)blockstate, (Level)level, (BlockPos)pos, (BlockEntity)blockentity, (Entity)entity, (ItemStack)item);
            if (tag != null && tag.m_128471_("silkTouch")) {
                enchantments.remove(Enchantments.f_44985_);
                EnchantmentHelper.m_44865_((Map)enchantments, (ItemStack)item);
            }
        }
        if (flag = level.m_6933_(pos, fluidstate.m_76188_(), 3, 512)) {
            level.m_220407_(GameEvent.f_157794_, pos, GameEvent.Context.m_223719_((Entity)entity, (BlockState)blockstate));
        }
        return flag;
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        HashMultimap multimap = HashMultimap.create();
        if (slot == EquipmentSlot.MAINHAND) {
            if (ElectricItem.MANAGER.getCharge(stack) >= this.getEnergyCost(stack) * 2) {
                multimap.put((Object)Attributes.f_22281_, (Object)new AttributeModifier(f_41374_, "Vajra Powered Damage", 25.0, AttributeModifier.Operation.ADDITION));
            } else {
                multimap.put((Object)Attributes.f_22281_, (Object)new AttributeModifier(f_41374_, "Vajra Unpowered Damage", 3.0, AttributeModifier.Operation.ADDITION));
            }
        }
        return multimap;
    }

    public InteractionResult getEnchantmentCompatibility(ItemStack stack, Enchantment ench) {
        return ench == Enchantments.f_44984_ ? InteractionResult.FAIL : super.getEnchantmentCompatibility(stack, ench);
    }

    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return (TextureAtlasSprite)IC2Textures.getMappedEntriesItem((String)"gravisuit", (String)"tools").get("vajra");
    }

    public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ElectricItem.MANAGER.use(stack, this.getEnergyCost(stack) * 2, attacker);
        return super.m_7579_(stack, target, attacker);
    }

    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        CompoundTag nbt = stack.m_41784_();
        boolean silkTouch = nbt.m_128471_("silkTouch");
        if (silkTouch) {
            helper.addSimpleToolTip(GravisuitLang.silkMode, new Object[]{this.translate(GravisuitLang.vajraSilktouchOn)});
        } else {
            helper.addSimpleToolTip(GravisuitLang.silkMode, new Object[]{this.translate(GravisuitLang.vajraSilktouchOff)});
        }
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, GravisuitLang.vajraSilktouchToggle, new Object[0]));
    }
}

