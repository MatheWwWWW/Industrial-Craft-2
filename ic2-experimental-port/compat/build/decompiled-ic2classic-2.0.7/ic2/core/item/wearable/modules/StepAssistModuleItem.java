/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeInstance
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.common.ForgeMod
 */
package ic2.core.item.wearable.modules;

import ic2.api.items.armor.IArmorModule;
import ic2.core.item.wearable.modules.BaseModuleItem;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

public class StepAssistModuleItem
extends BaseModuleItem {
    private static final UUID ID = UUID.fromString("0aef79b0-07f9-41b4-87d0-9010da8ca5ab");

    public StepAssistModuleItem(String textureFolder, String textureName) {
        super("step_assist_module", null, textureFolder, textureName, IArmorModule.ModuleType.MOVEMENT);
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.LEGS || type == EquipmentSlot.FEET;
    }

    @Override
    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        if (world.m_46467_() % 20L == 0L) {
            CompoundTag data = armor.m_41784_();
            double stepY = data.m_128459_("step_last_y");
            if (stepY < player.m_20186_() && player.m_20096_()) {
                this.useEnergy(armor, 250, (LivingEntity)player);
            }
            data.m_128347_("step_last_y", player.m_20186_());
        }
    }

    @Override
    public void onEquipped(ItemStack stack, ItemStack armor, Player entity) {
        AttributeInstance instance = entity.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get());
        if (instance == null || instance.m_22111_(ID) != null) {
            return;
        }
        instance.m_22125_(new AttributeModifier(ID, "IC2StepAssist", (double)0.6f, AttributeModifier.Operation.ADDITION));
    }

    @Override
    public void onUnequipped(ItemStack stack, ItemStack armor, Player entity) {
        entity.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22120_(ID);
    }
}

