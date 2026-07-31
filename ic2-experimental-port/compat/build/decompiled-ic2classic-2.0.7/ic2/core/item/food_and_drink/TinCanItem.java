/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.stats.Stats
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.UseAnim
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.food_and_drink;

import ic2.api.items.IAutoEatable;
import ic2.api.recipes.misc.ICanEffect;
import ic2.core.IC2;
import ic2.core.inventory.filter.IFilter;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.helpers.StackUtil;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TinCanItem
extends IC2Item
implements ISimpleItemModel,
IAutoEatable {
    public static final IFilter FOOD_CAN_FILTER = new FoodCanFilter();
    private final int value;
    private final float saturation;
    private final int eatTime;
    private final boolean canEatWhenFull;
    private ICanEffect effect;

    public TinCanItem() {
        super("filled_tin_can", new PropertiesBuilder().group(IC2.FOOD_AND_DRINK_GROUP));
        this.value = 2;
        this.saturation = 0.95f;
        this.eatTime = 20;
        this.canEatWhenFull = true;
    }

    public TinCanItem(ICanEffect effect) {
        super("filled_tin_can_" + effect.getID().m_135815_(), new PropertiesBuilder().group(IC2.FOOD_AND_DRINK_GROUP));
        this.value = 2;
        this.saturation = 0.95f;
        this.eatTime = 20;
        this.canEatWhenFull = true;
        this.effect = effect;
    }

    protected String m_41467_() {
        return "item.ic2.filled_tin_can";
    }

    public ICanEffect getEffect() {
        return this.effect;
    }

    @Override
    public int getFoodValue(ItemStack stack) {
        return this.value;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (player.m_36391_(this.canEatWhenFull)) {
            player.m_6672_(hand);
            return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)stack);
        }
        return new InteractionResultHolder(InteractionResult.FAIL, (Object)stack);
    }

    public ItemStack m_5922_(ItemStack stack, Level world, LivingEntity livingEntity) {
        if (livingEntity instanceof Player) {
            Player player = (Player)livingEntity;
            if (!world.m_5776_()) {
                StackUtil.addOrDrop(player, this.onEaten(stack, world, player));
                stack.m_41774_(1);
            }
        }
        return stack;
    }

    public UseAnim m_6164_(ItemStack stack) {
        return UseAnim.EAT;
    }

    public int m_8105_(ItemStack stack) {
        return this.eatTime;
    }

    public float getSaturation() {
        return this.saturation;
    }

    public int getHealing() {
        return this.value;
    }

    @Override
    public boolean canAutoEat(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack onEaten(ItemStack stack, Level level, Player player) {
        player.m_36220_(IC2Stats.FOOD_CANS_EATEN);
        player.m_5634_(2.0f);
        if (this.effect != null) {
            this.effect.onFoodEaten(stack, player);
        }
        player.m_36324_().m_38707_(this.value, this.saturation);
        level.m_6263_(player, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_12321_, SoundSource.PLAYERS, 0.5f, level.f_46441_.m_188501_() * 0.1f + 0.9f);
        player.m_36246_(Stats.f_12982_.m_12902_((Object)this));
        return new ItemStack((ItemLike)IC2Items.TIN_CAN);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void m_7373_(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        if (this.effect == null) {
            tooltip.add((Component)this.translate("tooltip.item.ic2.filled_tin_can"));
            return;
        }
        Component comp = this.effect.getTooltip();
        if (comp != null) {
            tooltip.add(comp);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        TextureAtlasSprite override;
        if (this.effect != null && (override = this.effect.getOverrideIcon()) != null) {
            return override;
        }
        return IC2Textures.getMappedEntriesItemIC2("food_and_drink/tincan").get("filled_tin_can");
    }

    public static class FoodCanFilter
    implements IFilter {
        @Override
        public boolean matches(ItemStack stack) {
            return stack.m_41720_() instanceof TinCanItem;
        }
    }
}

