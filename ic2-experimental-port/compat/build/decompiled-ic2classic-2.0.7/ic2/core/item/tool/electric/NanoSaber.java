/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.Multimap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.EquipmentSlot$Type
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.enchantment.EnchantmentCategory
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.ToolAction
 *  net.minecraftforge.common.ToolActions
 */
package ic2.core.item.tool.electric;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.item.base.ElectricToolItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IC2ModularElectricArmor;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.IItemModel;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
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
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class NanoSaber
extends ElectricToolItem
implements IItemModel {
    public int soundTicker = 0;
    public static ResourceLocation saberIdle = new ResourceLocation("ic2", "sounds/tools/nanosaber/nanosaber_idle.ogg");
    public static ResourceLocation saberPowerUp = new ResourceLocation("ic2", "sounds/tools/nanosaber/nanosaber_powerup.ogg");
    public static ResourceLocation saberSwingOne = new ResourceLocation("ic2", "sounds/tools/nanosaber/nanosaber_swing_1.ogg");
    public static ResourceLocation saberSwingTwo = new ResourceLocation("ic2", "sounds/tools/nanosaber/nanosaber_swing_2.ogg");
    public static ResourceLocation saberSwingThree = new ResourceLocation("ic2", "sounds/tools/nanosaber/nanosaber_swing_3.ogg");
    public static final RandomSource RANDOM = RandomSource.m_216327_();

    public NanoSaber() {
        super("nano_saber", 0.0f, 1.0f, 2.0f, new PropertiesBuilder().maxDamage(0).maxStackSize(1).setNoRepair());
    }

    @Override
    public List<ItemStack> getModelTypes() {
        ObjectList types = CollectionUtils.createList();
        ItemStack inactive = new ItemStack((ItemLike)this);
        ItemStack active_0 = new ItemStack((ItemLike)this);
        ItemStack active_1 = new ItemStack((ItemLike)this);
        inactive.m_41784_().m_128379_("active", false);
        CompoundTag tag_0 = active_0.m_41784_();
        CompoundTag tag_1 = active_1.m_41784_();
        tag_0.m_128379_("active", true);
        tag_0.m_128379_("0", true);
        tag_1.m_128379_("active", true);
        tag_1.m_128379_("0", false);
        types.add((ItemStack)inactive);
        types.add((ItemStack)active_0);
        types.add((ItemStack)active_1);
        return types;
    }

    public boolean m_6777_(BlockState p_43291_, Level p_43292_, BlockPos p_43293_, Player p_43294_) {
        return !p_43294_.m_7500_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addDataTooltip("tooltip.item.ic2.nano_saber", new Object[0]);
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, "tooltip.item.ic2.nano_saber.enable", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, KeyHelper.ALT_KEY, "tooltip.item.ic2.nano_saber.disable", new Object[0]));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSprite(ItemStack input) {
        CompoundTag nbt = input.m_41784_();
        if (nbt.m_128471_("active")) {
            return IC2Textures.getMappedEntriesItemIC2("tools/nano_saber").get("active_" + (nbt.m_128471_("0") ? 0 : 1));
        }
        return IC2Textures.getMappedEntriesItemIC2("tools/nano_saber").get("off");
    }

    @Override
    public int getModelIndexForStack(ItemStack stack, @Nullable LivingEntity entity) {
        if (stack.m_41784_().m_128471_("active")) {
            return RANDOM.m_188503_(2) + 1;
        }
        return 0;
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return 40000;
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return 128;
    }

    @Override
    public int getTier(ItemStack stack) {
        return 2;
    }

    @Override
    public int getEnergyCost(ItemStack stack) {
        return 10;
    }

    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return toolAction == ToolActions.SWORD_SWEEP && StackUtil.getNbtData(stack).m_128471_("active");
    }

    @Override
    public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!StackUtil.getNbtData(stack).m_128471_("active")) {
            return true;
        }
        if (IC2.PLATFORM.isSimulating() && (IC2.PLATFORM.isPVP() || !(attacker instanceof Player))) {
            if (target instanceof Player) {
                Player player = (Player)target;
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    ItemStack armor;
                    Item item;
                    if (slot.m_20743_() != EquipmentSlot.Type.ARMOR || !((item = (armor = player.m_6844_(slot)).m_41720_()) instanceof IC2ModularElectricArmor)) continue;
                    IC2ModularElectricArmor modular = (IC2ModularElectricArmor)item;
                    int usage = (int)(3000.0 * Math.pow(6.25, modular.getTier(armor) - 1));
                    ElectricItem.MANAGER.discharge(armor, usage, Integer.MAX_VALUE, true, false, false);
                    if (ElectricItem.MANAGER.discharge(armor, 1, Integer.MAX_VALUE, true, false, true) < 1) {
                        player.m_8061_(slot, ItemStack.f_41583_);
                    }
                    NanoSaber.drainSaber(stack, 16, attacker);
                }
            }
            NanoSaber.drainSaber(stack, 40, attacker);
        }
        return true;
    }

    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return IC2Items.SWORD_BRONZE.isCorrectToolForDrops(stack, state);
    }

    public static void drainSaber(ItemStack stack, int needed, LivingEntity entity) {
        if (!IC2.PLATFORM.isSimulating()) {
            return;
        }
        if (!ElectricItem.MANAGER.use(stack, needed, entity)) {
            stack.m_41784_().m_128379_("active", false);
        }
    }

    @Override
    public float m_8102_(ItemStack stack, BlockState state) {
        if (StackUtil.getNbtData(stack).m_128471_("active")) {
            if (IC2.PLATFORM.isRendering()) {
                ++this.soundTicker;
                if (this.soundTicker % 4 == 0) {
                    IC2.AUDIO.playSound(IC2.PLATFORM.getClientPlayerInstance(), this.getRandomSwingSound(), AudioManager.SoundType.ITEM);
                }
            }
            return IC2Items.SWORD_BRONZE.m_8102_(stack, state);
        }
        return 1.0f;
    }

    @Override
    public boolean m_8096_(BlockState blockIn) {
        return blockIn.m_60734_() == Blocks.f_50033_;
    }

    public ResourceLocation getRandomSwingSound() {
        switch (RANDOM.m_188503_(3)) {
            default: {
                return saberSwingOne;
            }
            case 1: {
                return saberSwingTwo;
            }
            case 2: 
        }
        return saberSwingThree;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.m_21120_(handIn);
        CompoundTag nbt = stack.m_41784_();
        if (nbt.m_128471_("active") && PlayerHandler.getHandler((Player)playerIn).altKeyDown) {
            if (IC2.PLATFORM.isSimulating()) {
                nbt.m_128379_("active", false);
            }
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        if (!nbt.m_128471_("active") && ElectricItem.MANAGER.canUse(stack, 16)) {
            if (IC2.PLATFORM.isSimulating()) {
                nbt.m_128379_("active", true);
            }
            IC2.AUDIO.playSound(playerIn, saberPowerUp, AudioManager.SoundType.ITEM, IC2.AUDIO.getVolumeForType(AudioManager.SoundType.ITEM), 1.0f);
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return InteractionResultHolder.m_19098_((Object)stack);
    }

    public void m_6883_(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        boolean drain;
        if (!(entityIn instanceof Player) || !StackUtil.getNbtData(stack).m_128471_("active")) {
            return;
        }
        CompoundTag nbt = stack.m_41784_();
        int delay = nbt.m_128451_("delay");
        boolean bl = isSelected ? delay >= 16 : (drain = ++delay >= 64);
        if (drain) {
            delay = 0;
        }
        nbt.m_128405_("delay", delay);
        if (!drain) {
            return;
        }
        NanoSaber.drainSaber(stack, 16, (LivingEntity)entityIn);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
        HashMultimap map = HashMultimap.create();
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            map.put((Object)Attributes.f_22281_, (Object)new AttributeModifier(f_41374_, "Tool modifier", StackUtil.getNbtData(stack).m_128471_("active") ? 19.0 : 3.0, AttributeModifier.Operation.ADDITION));
        }
        return map;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
    }

    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return StackUtil.getNbtData(oldStack).m_128471_("active") != StackUtil.getNbtData(newStack).m_128471_("active") || slotChanged;
    }

    public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
        return oldStack.m_41720_() != newStack.m_41720_();
    }

    @Override
    public EnchantmentCategory getEnchantmentType(ItemStack stack) {
        return EnchantmentCategory.WEAPON;
    }
}

