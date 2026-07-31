/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.NonNullList
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.UseAnim
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.food_and_drink;

import ic2.api.items.IDrinkContainer;
import ic2.api.items.IDrinkableFluid;
import ic2.core.IC2;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.rendering.features.item.IItemModel;
import ic2.core.utils.helpers.StackUtil;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class IC2Drink
extends IC2Item
implements IDrinkContainer,
IItemModel {
    protected IDrinkableFluid fluid;

    public IC2Drink(String itemName) {
        super(itemName, new PropertiesBuilder().group(IC2.FOOD_AND_DRINK_GROUP));
    }

    public IC2Drink(String itemName, IDrinkableFluid fluid) {
        super(itemName, new PropertiesBuilder().group(IC2.FOOD_AND_DRINK_GROUP));
        this.fluid = fluid;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (player.m_36391_(true) && this.fluid != null) {
            player.m_6672_(hand);
            return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)stack);
        }
        return new InteractionResultHolder(InteractionResult.FAIL, (Object)stack);
    }

    public ItemStack m_5922_(ItemStack stack, Level world, LivingEntity livingEntity) {
        if (livingEntity instanceof Player) {
            Player player = (Player)livingEntity;
            if (!world.m_5776_()) {
                this.fluid.drink(stack, world, player);
                StackUtil.addOrDrop(player, this.getEmptyContainer());
                stack.m_41774_(1);
            }
        }
        return stack;
    }

    public abstract String getBasePath(ItemStack var1);

    @Override
    public IDrinkableFluid getContent(ItemStack stack) {
        return this.fluid;
    }

    @Override
    public boolean hasContent(ItemStack stack) {
        return this.fluid != null;
    }

    @Override
    public int getCapacity() {
        return 1000;
    }

    public int m_8105_(ItemStack stack) {
        return 32;
    }

    public UseAnim m_6164_(ItemStack stack) {
        return UseAnim.DRINK;
    }

    public Component m_7626_(ItemStack stack) {
        if (this.fluid != null && this.fluid.hasSpecialName()) {
            return this.fluid.getSpecialName(stack);
        }
        return super.m_7626_(stack);
    }

    @Override
    public int getModelIndexForStack(ItemStack stack, @Nullable LivingEntity entity) {
        return this.fluid != null ? this.fluid.getTextureIndex(stack) : 0;
    }

    @Override
    public List<ItemStack> getModelTypes() {
        return this.fluid != null ? this.fluid.generateSubStates(new ItemStack((ItemLike)this), true) : Collections.singletonList(new ItemStack((ItemLike)this));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSprite(ItemStack input) {
        return (TextureAtlasSprite)Minecraft.m_91087_().m_91258_(InventoryMenu.f_39692_).apply(this.fluid == null ? new ResourceLocation(this.getRegistryName().m_135827_(), this.getBasePath(input) + "/empty") : this.fluid.getTexture(input, this.getRegistryName().m_135827_() + ":" + this.getBasePath(input)));
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (!this.m_220152_(group)) {
            return;
        }
        if (this.fluid == null) {
            items.add((Object)new ItemStack((ItemLike)this));
        } else {
            items.addAll(this.fluid.generateSubStates(new ItemStack((ItemLike)this), false));
        }
    }

    public abstract Map<IDrinkableFluid, Item> getFilledContainers();
}

