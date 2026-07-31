/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.api.recipes.misc;

import ic2.api.recipes.misc.ICanEffect;
import java.util.Collection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SimpleCanEffect
implements ICanEffect {
    final ResourceLocation id;
    final MobEffectInstance[] effects;
    Component component = null;
    ResourceLocation texture;
    boolean isRandom = false;
    float chance;

    public SimpleCanEffect(ResourceLocation id, Collection<MobEffectInstance> effects) {
        this(id, effects.toArray(new MobEffectInstance[effects.size()]));
    }

    public SimpleCanEffect(ResourceLocation id, MobEffectInstance ... effects) {
        this.id = id;
        this.effects = effects;
    }

    public SimpleCanEffect setRandom(float chance) {
        this.chance = chance;
        this.isRandom = true;
        return this;
    }

    public SimpleCanEffect setTexture(ResourceLocation texture) {
        this.texture = texture;
        return this;
    }

    public SimpleCanEffect setTooltip(Component component) {
        this.component = component;
        return this;
    }

    @Override
    public void onFoodEaten(ItemStack stack, Player player) {
        if (player.f_19853_.f_46443_) {
            return;
        }
        if (this.isRandom && player.f_19853_.f_46441_.m_188501_() >= this.chance || this.effects == null || this.effects.length == 0) {
            return;
        }
        for (int i = 0; i < this.effects.length; ++i) {
            if (this.effects[i] == null) continue;
            player.m_7292_(new MobEffectInstance(this.effects[i]));
        }
    }

    @Override
    public Component getTooltip() {
        return this.component;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getOverrideIcon() {
        return this.texture == null ? null : (TextureAtlasSprite)Minecraft.m_91087_().m_91258_(InventoryMenu.f_39692_).apply(this.texture);
    }

    @Override
    public ResourceLocation getID() {
        return this.id;
    }
}

