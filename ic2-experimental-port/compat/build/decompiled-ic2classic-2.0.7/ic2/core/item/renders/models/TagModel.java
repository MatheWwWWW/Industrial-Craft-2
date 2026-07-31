/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.renderer.block.model.ItemOverrides
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.tags.ITag
 */
package ic2.core.item.renders.models;

import ic2.core.item.misc.TagItem;
import ic2.core.platform.rendering.QuadBaker;
import ic2.core.platform.rendering.models.items.BaseItemModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

public class TagModel
extends BaseItemModel<TagModel> {
    @Override
    public void init() {
        this.setParticleTexture((TextureAtlasSprite)Minecraft.m_91087_().m_91258_(InventoryMenu.f_39692_).apply(new ResourceLocation("item/writable_book")));
        this.quads.addAll(QuadBaker.createQuadsFromTexture(-1, this.m_6160_(), this.getTransformMap().f_111792_));
        this.initOther(new TagModel());
    }

    @Override
    public ItemOverrides m_7343_() {
        return TagOverride.INSTANCE;
    }

    public static class TagOverride
    extends ItemOverrides {
        public static final TagOverride INSTANCE = new TagOverride();

        public BakedModel m_173464_(BakedModel model, ItemStack stack, ClientLevel worldIn, LivingEntity entityIn, int entityId) {
            ITag tag;
            ResourceLocation location;
            if (Screen.m_96638_() && (location = TagItem.getTag(stack)) != null && (tag = ForgeRegistries.ITEMS.tags().getTag(ItemTags.create((ResourceLocation)location))) != null) {
                long time = Minecraft.m_91087_().f_91073_.m_46467_() / 20L;
                return Minecraft.m_91087_().m_91291_().m_174264_(tag.getRandomElement(RandomSource.m_216335_((long)time)).orElse(Items.f_41852_).m_7968_(), (Level)worldIn, entityIn, entityId);
            }
            return model;
        }
    }
}

