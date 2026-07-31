/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.resources.model.ModelResourceLocation
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.ShieldItem
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.extensions.common.IClientItemExtensions
 *  net.minecraftforge.common.ToolAction
 *  net.minecraftforge.common.ToolActions
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.item.wearable.base;

import ic2.core.IC2;
import ic2.core.item.renders.models.ShieldModel;
import ic2.core.platform.rendering.features.item.ICustomItemModel;
import ic2.core.platform.rendering.features.item.IToolModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.plugins.IRegistryProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.GameData;

public abstract class IC2ShieldBase
extends ShieldItem
implements ICustomItemModel,
IToolModel,
IRegistryProvider {
    ResourceLocation id;

    public IC2ShieldBase(String name, int maxDamage) {
        this(name, new Item.Properties().m_41491_(IC2.IC2_MAIN_GROUP).m_41503_(maxDamage));
    }

    public IC2ShieldBase(String name, Item.Properties properties) {
        super(properties);
        this.id = GameData.checkPrefix((String)name, (boolean)false);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    public void setDamage(ItemStack stack, int damage) {
        if (damage <= 0) {
            stack.m_41749_("Damage");
            return;
        }
        stack.m_41784_().m_128405_("Damage", damage);
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(IC2.PLATFORM.getTileRenderer());
    }

    public abstract void damageShield(ItemStack var1, int var2, LivingEntity var3);

    public abstract ResourceLocation getTexture(ItemStack var1);

    public boolean canBlock(ItemStack item, LivingEntity entity) {
        return true;
    }

    public boolean isBlocking(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128471_("Blocking");
    }

    public ItemStack setBlocking(ItemStack stack, boolean blocking) {
        stack.m_41784_().m_128379_("Blocking", blocking);
        return stack;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.m_21120_(handIn);
        if (this.canBlock(stack, (LivingEntity)playerIn)) {
            playerIn.m_6672_(handIn);
            return new InteractionResultHolder(InteractionResult.CONSUME, (Object)stack);
        }
        return new InteractionResultHolder(InteractionResult.FAIL, (Object)stack);
    }

    @Override
    public List<ItemStack> getCustomTypes() {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        list.add(new ItemStack((ItemLike)this));
        list.add(this.setBlocking(new ItemStack((ItemLike)this), true));
        return list;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getModel(ItemStack stack) {
        boolean blocking = this.isBlocking(stack);
        return new ShieldModel(blocking).setOther(this.createResourceLocationForStack(this.setBlocking(stack.m_41777_(), !blocking)));
    }

    private ModelResourceLocation createResourceLocationForStack(ItemStack stack) {
        boolean blocking = this.isBlocking(stack);
        String name = stack.m_41778_();
        name = name.substring(name.lastIndexOf(".") + 1);
        ResourceLocation resource = ForgeRegistries.ITEMS.getKey((Object)stack.m_41720_());
        return new ModelResourceLocation(resource.m_135827_() + name + (blocking ? "_blocking" : ""), "inventory");
    }

    @Override
    public int getModelIndexForStack(ItemStack stack, @Nullable LivingEntity entity) {
        return entity != null && entity.m_6117_() && entity.m_21211_() == stack ? 1 : 0;
    }

    @Override
    public ResourceLocation getCustomTransform(ItemStack stack) {
        return new ResourceLocation("minecraft:models/item/shield" + (this.isBlocking(stack) ? "_blocking" : ""));
    }

    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return toolAction == ToolActions.SHIELD_BLOCK;
    }
}

