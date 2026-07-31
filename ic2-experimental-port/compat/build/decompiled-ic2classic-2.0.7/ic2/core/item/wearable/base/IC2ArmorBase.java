/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  it.unimi.dsi.fastutil.ints.Int2DoubleLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayPriorityQueue
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.damagesource.CombatRules
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.EquipmentSlot$Type
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.ArmorMaterial
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.item.wearable.base;

import com.google.common.base.Strings;
import ic2.api.items.armor.ICustomArmor;
import ic2.core.IC2;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.plugins.IRegistryProvider;
import ic2.core.utils.tooltips.IAdvancedTooltip;
import ic2.core.utils.tooltips.ILangHelper;
import ic2.core.utils.tooltips.ToolTipHelper;
import it.unimi.dsi.fastutil.ints.Int2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayPriorityQueue;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.GameData;

public abstract class IC2ArmorBase
extends ArmorItem
implements ISimpleItemModel,
ILangHelper,
IAdvancedTooltip,
IRegistryProvider {
    private ResourceLocation id;

    public IC2ArmorBase(String itemName, ArmorMaterial material, EquipmentSlot slot, @Nullable PropertiesBuilder props) {
        super(material, slot, (props == null ? new PropertiesBuilder() : props).group(IC2.IC2_MAIN_GROUP).build());
        this.id = GameData.checkPrefix((String)itemName.toLowerCase(), (boolean)false);
    }

    public IC2ArmorBase(String itemName, ArmorMaterial material, EquipmentSlot slot) {
        this(itemName, material, slot, null);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
    }

    public void setDamage(ItemStack stack, int damage) {
        if (damage <= 0) {
            stack.m_41749_("Damage");
            return;
        }
        stack.m_41784_().m_128405_("Damage", damage);
    }

    public abstract String getTextureFolder();

    public abstract String getTextureName();

    public abstract String getArmorTexture();

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(this.id.m_135827_(), this.getTextureFolder()).get(this.getTextureName());
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return this.getArmorTexture() + "_" + (slot == EquipmentSlot.LEGS ? "2" : "1") + (String)(!Strings.isNullOrEmpty((String)type) ? "_" + type : "") + ".png";
    }

    public static float applyArmor(LivingEntity entity, DamageSource source, double damage) {
        ObjectArrayPriorityQueue properties = new ObjectArrayPriorityQueue(4);
        Int2DoubleLinkedOpenHashMap map = new Int2DoubleLinkedOpenHashMap(4);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ICustomArmor armor;
            ItemStack stack;
            if (slot.m_20743_() != EquipmentSlot.Type.ARMOR || !((stack = entity.m_6844_(slot)).m_41720_() instanceof ICustomArmor) || !(armor = (ICustomArmor)stack.m_41720_()).canBlockDamageSource(entity, stack, source, slot)) continue;
            ICustomArmor.AbsorptionProperties prop = armor.getProperties(entity, stack, source, damage, slot).copy();
            prop.slot = slot;
            if (!(prop.absorbRatio > 0.0) || prop.absorbMax <= 0) continue;
            properties.enqueue((Object)prop);
            map.addTo(prop.priority, prop.absorbRatio);
        }
        if (properties.size() > 0) {
            double startDamage = damage;
            while (!properties.isEmpty()) {
                ICustomArmor.AbsorptionProperties entry = (ICustomArmor.AbsorptionProperties)properties.dequeue();
                double percent = map.get(entry.priority);
                if (percent > 1.0) {
                    entry.absorbRatio /= percent;
                }
                double damageAbsorbed = Math.min(damage, Math.min(startDamage * entry.absorbRatio, (double)entry.absorbMax));
                damage -= damageAbsorbed;
                if (damageAbsorbed > 0.0) {
                    ItemStack stack = entity.m_6844_(entry.slot);
                    int itemDamage = (int)Math.max(1.0, damageAbsorbed);
                    if (stack.m_41720_() instanceof ICustomArmor) {
                        ((ICustomArmor)stack.m_41720_()).damageArmor(entity, stack, source, itemDamage, entry.slot, ICustomArmor.DamageType.MODDED);
                    }
                }
                if (!(damage <= 0.0)) continue;
                return 0.0f;
            }
        }
        double totalArmor = entity.m_21230_();
        double totalToughness = entity.m_21051_(Attributes.f_22285_).m_22135_();
        if (damage > 0.0 && (totalArmor > 0.0 || totalToughness > 0.0) && !source.m_19376_()) {
            double armorDamage = Math.max(1.0, damage / 4.0);
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.m_20743_() != EquipmentSlot.Type.ARMOR) continue;
                ItemStack stack = entity.m_6844_(slot);
                if (stack.m_41720_() instanceof ICustomArmor) {
                    ((ICustomArmor)stack.m_41720_()).damageArmor(entity, stack, source, (int)armorDamage, slot, ICustomArmor.DamageType.VANILLA);
                    continue;
                }
                if (!(stack.m_41720_() instanceof ArmorItem)) continue;
                stack.m_41622_((int)armorDamage, entity, IC2Item.get(slot));
            }
            damage = CombatRules.m_19272_((float)((float)damage), (float)((float)totalArmor), (float)((float)totalToughness));
        }
        return (float)damage;
    }
}

