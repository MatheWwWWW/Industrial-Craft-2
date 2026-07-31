/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.crops.crops;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICrop;
import ic2.api.crops.ICropTile;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.block.crops.crops.BaseCrop;
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class InkbergineCrop
extends BaseCrop {
    public InkbergineCrop() {
        super("inkbergine", new CropProperties(6, 5, 0, 3, 1, 0), "Green", "Ink", "Leaves", "Black");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(int stage) {
        return IC2Textures.getMappedEntriesBlockIC2("crops/inkbergine").get("growing_" + stage);
    }

    @Override
    public Component discoveredBy() {
        return BaseCrop.NANO_HEART;
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack((ItemLike)Items.f_42532_);
    }

    @Override
    public int getGrowthSteps() {
        return 7;
    }

    @Override
    public void onEntityCollision(ICropTile cropTile, Entity entity) {
        if (cropTile.getGrowthStage() == this.getGrowthSteps()) {
            int cooldown = cropTile.getCustomData().m_128451_("cooldown");
            Level world = cropTile.getWorldObj();
            if (cooldown == 0) {
                IC2.AUDIO.playSound(entity, SoundEvents.f_12441_.m_11660_(), AudioManager.SoundType.STATIC, 1.0f, 1.0f);
                for (int i = 0; i < 10; ++i) {
                    ((ServerLevel)world).m_8767_((ParticleOptions)ParticleTypes.f_123765_, (double)cropTile.getPosition().m_123341_() + 0.5, (double)cropTile.getPosition().m_123342_() + 0.5, (double)cropTile.getPosition().m_123343_() + 0.5, 0, (double)world.f_46441_.m_188503_(20) / 10.0, (double)world.f_46441_.m_188503_(20) / 10.0, (double)world.f_46441_.m_188503_(20) / 10.0, (double)0.1f);
                }
                cropTile.getCustomData().m_128405_("cooldown", world.f_46441_.m_188503_(100));
            } else {
                cropTile.getCustomData().m_128405_("cooldown", Math.max(cooldown - 1, 0));
            }
        }
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        return new ItemStack[]{new ItemStack((ItemLike)Items.f_42532_)};
    }

    @Override
    public ICrop.CropType getCropType() {
        return ICrop.CropType.WATER;
    }
}

