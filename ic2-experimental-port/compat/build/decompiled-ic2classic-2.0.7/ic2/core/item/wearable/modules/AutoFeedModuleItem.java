/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.wearable.modules;

import ic2.api.items.IAutoEatable;
import ic2.api.items.armor.IArmorModule;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.item.wearable.modules.BaseModuleItem;
import ic2.core.platform.registries.IC2Advancements;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import java.util.List;
import java.util.function.BiFunction;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AutoFeedModuleItem
extends BaseModuleItem {
    static final List<BiFunction<Player, ItemStack, IItemTransporter>> TRANSPORTERS = CollectionUtils.createList();

    public static synchronized void addFoodSource(BiFunction<Player, ItemStack, IItemTransporter> provider) {
        TRANSPORTERS.add(provider);
    }

    public AutoFeedModuleItem(String textureFolder, String textureName) {
        super("auto_feeder_module", null, textureFolder, textureName, IArmorModule.ModuleType.GENERIC);
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.HEAD;
    }

    @Override
    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        if (player.m_36324_().m_38721_() && this.canUseEnergy(armor, 1000)) {
            ItemStack food;
            int m = TRANSPORTERS.size();
            for (int i = 0; i < m; ++i) {
                ItemStack food2;
                IItemTransporter transporter = TRANSPORTERS.get(i).apply(player, armor);
                if (transporter == null || (food2 = transporter.removeItem(SpecialFilters.AUTO_EATABLE, null, 1, false)).m_41619_() || (food2 = ((IAutoEatable)food2.m_41720_()).onEaten(food2, world, player)).m_41619_()) continue;
                food2.m_41774_(transporter.addItem(food2, null, false));
                StackUtil.addOrDrop(player, food2);
                this.useEnergy(armor, 1000, (LivingEntity)player);
                return;
            }
            IItemTransporter transporter = TransporterManager.getTransporter(player);
            if (transporter != null && !(food = transporter.removeItem(SpecialFilters.AUTO_EATABLE, Direction.DOWN, 1, false)).m_41619_()) {
                food = ((IAutoEatable)food.m_41720_()).onEaten(food, world, player);
                StackUtil.addOrDrop(player, food);
                this.useEnergy(armor, 1000, (LivingEntity)player);
            }
        } else if (player.m_36324_().m_38702_() <= 0) {
            IC2Advancements.ID_TRIGGER.onTrigger(player, new ResourceLocation("ic2:starve"));
        }
    }
}

