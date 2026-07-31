/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.Multimap
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentCategory
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.item.base;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IElectricEnchantable;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.rendering.features.item.IToolModel;
import ic2.core.utils.helpers.Tool;
import java.util.EnumMap;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ElectricToolItem
extends IC2ElectricItem
implements IToolModel,
IElectricEnchantable {
    protected float miningSpeed;
    protected float hitDamage;
    protected float hitSpeed;
    protected EnumMap<Tool.ToolType, Tool> tools = new EnumMap(Tool.ToolType.class);

    public ElectricToolItem(String itemName, float miningSpeed, float hitDamage, float hitSpeed, @Nullable PropertiesBuilder properties) {
        super(itemName, properties);
        this.miningSpeed = miningSpeed;
        this.hitDamage = hitDamage;
        this.hitSpeed = hitSpeed;
    }

    protected void registerTools(Tool ... tools) {
        for (Tool tool : tools) {
            this.tools.put(tool.getType(), tool);
        }
    }

    public float m_8102_(ItemStack stack, BlockState state) {
        if (!ElectricItem.MANAGER.canUse(stack, this.getEnergyCost(stack))) {
            return 1.0f;
        }
        if (this.isCorrectToolForDrops(stack, state)) {
            return this.miningSpeed;
        }
        return super.m_8102_(stack, state);
    }

    public boolean m_8096_(BlockState p_41450_) {
        if (this.tools.isEmpty()) {
            return false;
        }
        for (Tool tool : this.tools.values()) {
            if (!tool.isCorrectTool(p_41450_)) continue;
            return true;
        }
        return false;
    }

    public int m_6473_() {
        return 14;
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
        HashMultimap multimap = HashMultimap.create();
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            multimap.put((Object)Attributes.f_22281_, (Object)new AttributeModifier(f_41374_, "Tool modifier", (double)this.hitDamage, AttributeModifier.Operation.ADDITION));
            multimap.put((Object)Attributes.f_22283_, (Object)new AttributeModifier(f_41375_, "Tool modifier", (double)this.hitSpeed, AttributeModifier.Operation.ADDITION));
        }
        return multimap;
    }

    public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    public boolean m_6813_(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.m_60800_((BlockGetter)worldIn, pos) != 0.0f) {
            ElectricItem.MANAGER.use(stack, this.getEnergyCost(stack), entityLiving);
        }
        return true;
    }

    public boolean m_8120_(ItemStack stack) {
        return false;
    }

    @Override
    public InteractionResult getEnchantmentCompatibility(ItemStack stack, Enchantment ench) {
        return ench == Enchantments.f_44962_ ? InteractionResult.FAIL : (ench == Enchantments.f_44986_ ? InteractionResult.SUCCESS : InteractionResult.PASS);
    }

    @Override
    public EnchantmentCategory getEnchantmentType(ItemStack stack) {
        return EnchantmentCategory.DIGGER;
    }
}

