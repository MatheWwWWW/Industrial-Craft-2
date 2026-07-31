/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.platform.recipes.misc;

import ic2.api.recipes.misc.SimpleCanEffect;
import ic2.core.platform.rendering.IC2Textures;
import java.util.Collection;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IC2SimpleCanEffect
extends SimpleCanEffect {
    String key;
    String value;

    public IC2SimpleCanEffect(ResourceLocation id, Collection<MobEffectInstance> effects) {
        super(id, effects);
    }

    public IC2SimpleCanEffect(ResourceLocation id, MobEffectInstance ... effects) {
        super(id, effects);
    }

    public IC2SimpleCanEffect setTexture(String key, String value) {
        this.key = key;
        this.value = value;
        return this;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getOverrideIcon() {
        return IC2Textures.getMappedEntriesItemIC2(this.key).get(this.value);
    }
}

