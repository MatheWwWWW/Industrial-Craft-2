/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.platform.recipes.misc;

import ic2.api.recipes.misc.ICanEffect;
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChorusFruitEffect
implements ICanEffect {
    String key;
    String value;
    final ResourceLocation id;
    Component component = null;

    public ChorusFruitEffect(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public void onFoodEaten(ItemStack stack, Player player) {
        Level world = player.m_20193_();
        if (!world.f_46443_) {
            double currentPosX = player.m_20185_();
            double currentPosY = player.m_20186_();
            double currentPosZ = player.m_20189_();
            for (int i = 0; i < 16; ++i) {
                double newPosX = currentPosX + (player.m_217043_().m_188500_() - 0.5) * 16.0;
                double newPosY = Mth.m_14008_((double)(currentPosY + (double)(player.m_217043_().m_188503_(16) - 8)), (double)0.0, (double)(world.m_151558_() - 1));
                double newPosZ = currentPosZ + (player.m_217043_().m_188500_() - 0.5) * 16.0;
                if (player.m_20160_()) {
                    player.m_20153_();
                }
                if (!player.m_20984_(newPosX, newPosY, newPosZ, true)) continue;
                world.m_6263_(null, currentPosX, currentPosY, currentPosZ, SoundEvents.f_11757_, SoundSource.PLAYERS, 1.0f, 1.0f);
                player.m_5496_(SoundEvents.f_11757_, 1.0f, 1.0f);
                break;
            }
        }
    }

    public ICanEffect setTexture(String key, String value) {
        this.key = key;
        this.value = value;
        return this;
    }

    public ICanEffect setTooltip(Component component) {
        this.component = component;
        return this;
    }

    @Override
    public Component getTooltip() {
        return this.component;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getOverrideIcon() {
        return IC2Textures.getMappedEntriesItemIC2(this.key).get(this.value);
    }

    @Override
    public ResourceLocation getID() {
        return this.id;
    }
}

