/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.upgrades.speed;

import ic2.api.items.IUpgradeItem;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import ic2.api.tiles.IMachine;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.upgrades.base.BaseUpgradeItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class SawMillUpgrade
extends BaseUpgradeItem.SimpleUpgradeItem {
    protected int effect;
    protected int eu_effect;
    protected int speed_effect;

    public SawMillUpgrade(String itemName, int maxDamage, int effect, int eu_effect, int speed_effect) {
        super(itemName, new PropertiesBuilder().maxStackSize(1).maxDamage(maxDamage));
        this.effect = effect;
        this.eu_effect = eu_effect;
        this.speed_effect = speed_effect;
        this.functions.add(IUpgradeItem.Functions.RECIPE);
    }

    @Override
    public IUpgradeItem.UpgradeType getType(ItemStack stack) {
        return IUpgradeItem.UpgradeType.PROCESSING_MOD;
    }

    @Override
    public void onMachineFinishedRecipePre(ItemStack stack, IMachine machine, IRecipeOutput output, CompoundTag recipeFlags) {
        if (recipeFlags.m_128441_("saw_upgrade")) {
            return;
        }
        int damage = stack.m_41773_();
        if (damage < stack.m_41776_()) {
            recipeFlags.m_128405_("saw_upgrade", this.effect);
            stack.m_41721_(damage + 1);
        }
    }

    @Override
    public int getExtraEnergyDemand(ItemStack stack, IMachine machine) {
        return this.eu_effect;
    }

    @Override
    public int getExtraProcessingSpeed(ItemStack stack, IMachine machine) {
        return this.speed_effect;
    }
}

