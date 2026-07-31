/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.upgrades.base;

import ic2.api.items.IUpgradeItem;
import ic2.api.recipes.ingridients.queue.IStackOutput;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.tiles.IMachine;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class BaseUpgradeItem
extends IC2Item
implements IUpgradeItem {
    public final EnumSet<IUpgradeItem.Functions> functions = EnumSet.noneOf(IUpgradeItem.Functions.class);

    public BaseUpgradeItem(String itemName) {
        super(itemName);
    }

    public BaseUpgradeItem(String itemName, @Nullable PropertiesBuilder properties) {
        super(itemName, properties);
    }

    @Override
    public EnumSet<IUpgradeItem.Functions> getFunctions(ItemStack stack) {
        return this.functions;
    }

    @Override
    public void onInstall(ItemStack stack, IMachine machine) {
    }

    @Override
    public double getProcessingSpeedMultiplier(ItemStack stack, IMachine machine) {
        return 1.0;
    }

    @Override
    public int getExtraProcessingSpeed(ItemStack stack, IMachine machine) {
        return 0;
    }

    @Override
    public double getProcessingTimeMultiplier(ItemStack stack, IMachine machine) {
        return 1.0;
    }

    @Override
    public int getExtraProcessingTime(ItemStack stack, IMachine machine) {
        return 0;
    }

    @Override
    public double getEnergyDemandMultiplier(ItemStack stack, IMachine machine) {
        return 1.0;
    }

    @Override
    public int getExtraEnergyDemand(ItemStack stack, IMachine machine) {
        return 0;
    }

    @Override
    public double getEnergyStorageMultiplier(ItemStack stack, IMachine machine) {
        return 1.0;
    }

    @Override
    public int getExtraEnergyStorage(ItemStack stack, IMachine machine) {
        return 0;
    }

    @Override
    public int getExtraTier(ItemStack stack, IMachine machine) {
        return 0;
    }

    @Override
    public float getSoundMultiplier(ItemStack stack, IMachine machine) {
        return 1.0f;
    }

    @Override
    public boolean useRedstoneInvertion(ItemStack stack, IMachine machine) {
        return false;
    }

    @Override
    public void onTick(ItemStack stack, IMachine machine) {
    }

    @Override
    public void onMachineFinishedRecipePre(ItemStack stack, IMachine machine, IRecipeOutput output, CompoundTag recipeFlags) {
    }

    @Override
    public void onMachineFinishedRecipePost(ItemStack stack, IMachine machine, IMachineRecipeList.RecipeEntry entry, List<IStackOutput> drops) {
    }

    @Override
    public void onMachineProcessed(ItemStack stack, IMachine machine) {
    }

    public static abstract class SimpleUpgradeItem
    extends BaseUpgradeItem
    implements ISimpleItemModel {
        private String textureName;

        public SimpleUpgradeItem(String itemName) {
            super("upgrade_" + itemName);
            this.textureName = itemName;
        }

        public SimpleUpgradeItem(String itemName, @Nullable PropertiesBuilder properties) {
            super("upgrade_" + itemName, properties);
            this.textureName = itemName;
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public TextureAtlasSprite getTexture() {
            return IC2Textures.getMappedEntriesItem(this.id.m_135827_(), "upgrades/machines").get(this.textureName);
        }
    }
}

