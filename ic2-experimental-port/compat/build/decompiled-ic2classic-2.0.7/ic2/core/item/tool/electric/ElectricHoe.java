/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.Multimap
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.NonNullList
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.HoeItem
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.Tiers
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentCategory
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.item.tool.electric;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import ic2.api.crops.ICropModifier;
import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IDamagelessElectricItem;
import ic2.api.items.electric.IElectricEnchantable;
import ic2.core.IC2;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.platform.rendering.features.item.IToolModel;
import ic2.core.utils.plugins.IRegistryProvider;
import ic2.core.utils.tooltips.IAdvancedTooltip;
import ic2.core.utils.tooltips.ILangHelper;
import ic2.core.utils.tooltips.ToolTipHelper;
import java.util.function.Consumer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.GameData;

public class ElectricHoe
extends HoeItem
implements ISimpleItemModel,
IDamagelessElectricItem,
IToolModel,
IElectricEnchantable,
ICropModifier,
IAdvancedTooltip,
ILangHelper,
IRegistryProvider {
    protected int energyCost = 50;
    protected ResourceLocation id = GameData.checkPrefix((String)"electric_hoe", (boolean)false);

    public ElectricHoe() {
        super((Tier)Tiers.IRON, (int)Tiers.IRON.m_6631_(), -3.0f, new Item.Properties().setNoRepair().m_41491_(IC2.IC2_MAIN_GROUP).m_41487_(1));
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.BLOCK_CLICK, "tooltip.item.ic2.hoe.seedmode", new Object[0]));
    }

    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    public InteractionResult m_6225_(UseOnContext context) {
        InteractionResult result = super.m_6225_(context);
        if (result.m_19077_()) {
            ElectricItem.MANAGER.use(context.m_43723_().m_21120_(context.m_43724_()), this.energyCost, (LivingEntity)context.m_43723_());
        }
        return result;
    }

    public boolean m_6813_(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        boolean result = super.m_6813_(stack, world, state, pos, entityLiving);
        if (entityLiving instanceof Player) {
            ElectricItem.MANAGER.use(stack, this.energyCost, entityLiving);
        } else {
            ElectricItem.MANAGER.discharge(stack, this.energyCost, 1, true, false, false);
        }
        return result;
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
        HashMultimap multimap = HashMultimap.create();
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            multimap.put((Object)Attributes.f_22281_, (Object)new AttributeModifier(f_41374_, "Tool modifier", (double)Tiers.IRON.m_6631_(), AttributeModifier.Operation.ADDITION));
            multimap.put((Object)Attributes.f_22283_, (Object)new AttributeModifier(f_41375_, "Tool modifier", -3.0, AttributeModifier.Operation.ADDITION));
        }
        return multimap;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools/hoe").get("base");
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canChangeSeedMode(ItemStack input) {
        return true;
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return 10000;
    }

    @Override
    public int getTier(ItemStack stack) {
        return 1;
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return 100;
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (!this.m_220152_(group)) {
            return;
        }
        IC2ElectricItem.addEmptyAndFullToGroup((ItemLike)this, items);
    }

    public int m_142158_(ItemStack stack) {
        return IC2ElectricItem.getElectricWidth(stack);
    }

    public int m_142159_(ItemStack stack) {
        return IC2ElectricItem.getRGBDurability(stack);
    }

    public boolean m_142522_(ItemStack stack) {
        return true;
    }

    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        ElectricItem.MANAGER.discharge(stack, ElectricItem.applyEnchantmentEffect(stack, this.energyCost * amount), Integer.MAX_VALUE, true, false, false);
        return 0;
    }

    public boolean m_8120_(ItemStack stack) {
        return false;
    }

    @Override
    public InteractionResult getEnchantmentCompatibility(ItemStack stack, Enchantment ench) {
        return ench == Enchantments.f_44987_ || ench == Enchantments.f_44986_ || ench == Enchantments.f_44984_ ? InteractionResult.SUCCESS : (ench == Enchantments.f_44962_ ? InteractionResult.FAIL : InteractionResult.PASS);
    }

    @Override
    public EnchantmentCategory getEnchantmentType(ItemStack stack) {
        return EnchantmentCategory.BREAKABLE;
    }
}

