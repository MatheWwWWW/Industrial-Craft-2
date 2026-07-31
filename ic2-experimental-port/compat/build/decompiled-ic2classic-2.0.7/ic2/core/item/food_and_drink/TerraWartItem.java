/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.food.FoodProperties
 *  net.minecraft.world.food.FoodProperties$Builder
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.food_and_drink;

import ic2.core.IC2;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TerraWartItem
extends IC2Item
implements ISimpleItemModel {
    public static final FoodProperties TERRAWART_FOOD = new FoodProperties.Builder().m_38766_().m_38758_(1.0f).m_38765_().m_38767_();

    public TerraWartItem() {
        super("terrawart", new PropertiesBuilder().group(IC2.FOOD_AND_DRINK_GROUP).food(TERRAWART_FOOD).rarity(Rarity.RARE));
    }

    public ItemStack m_5922_(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        for (MobEffectInstance instance : new ObjectArrayList(entityLiving.m_21220_())) {
            if (instance.m_19544_().m_19483_() != MobEffectCategory.HARMFUL) continue;
            entityLiving.m_21195_(instance.m_19544_());
        }
        return super.m_5922_(stack, worldIn, entityLiving);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("crops").get("terrawart");
    }
}

