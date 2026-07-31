/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiComponent
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.EnchantedBookItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.alchemy.PotionUtils
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentInstance
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.utils.config.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.config.gui.api.ISuggestionRenderer;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.Collection;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.registries.ForgeRegistries;

public class SuggestionRenderers {

    public static class ColorEntry
    implements ISuggestionRenderer {
        @Override
        public Component renderSuggestion(PoseStack stack, String value, int x, int y) {
            try {
                GuiComponent.m_93172_((PoseStack)stack, (int)(x + 1), (int)(y + 1), (int)(x + 18), (int)(y + 19), (int)-6250336);
                GuiComponent.m_93172_((PoseStack)stack, (int)(x + 2), (int)(y + 2), (int)(x + 17), (int)(y + 18), (int)(Integer.decode(value) | 0xFF000000));
            }
            catch (Exception exception) {
                // empty catch block
            }
            return null;
        }
    }

    public static class PotionEntry
    implements ISuggestionRenderer {
        @Override
        public Component renderSuggestion(PoseStack stack, String value, int x, int y) {
            ResourceLocation id = ResourceLocation.m_135820_((String)value);
            if (id == null) {
                return null;
            }
            MobEffect potion = (MobEffect)ForgeRegistries.MOB_EFFECTS.getValue(id);
            if (potion == null) {
                return null;
            }
            ItemStack item = new ItemStack((ItemLike)Items.f_42589_);
            PotionUtils.m_43552_((ItemStack)item, (Collection)ObjectLists.singleton((Object)new MobEffectInstance(potion)));
            item.m_41700_("CustomPotionColor", (Tag)IntTag.m_128679_((int)potion.m_19484_()));
            Minecraft.m_91087_().m_91291_().m_115203_(item, x, y);
            return potion.m_19482_().m_6881_().m_130940_(ChatFormatting.YELLOW);
        }
    }

    public static class EnchantmentEntry
    implements ISuggestionRenderer {
        @Override
        public Component renderSuggestion(PoseStack stack, String value, int x, int y) {
            ResourceLocation id = ResourceLocation.m_135820_((String)value);
            if (id == null) {
                return null;
            }
            Enchantment ench = (Enchantment)ForgeRegistries.ENCHANTMENTS.getValue(id);
            if (ench == null) {
                return null;
            }
            Minecraft.m_91087_().m_91291_().m_115203_(EnchantedBookItem.m_41161_((EnchantmentInstance)new EnchantmentInstance(ench, ench.m_44702_())), x, y);
            return ench.m_44700_(ench.m_44702_()).m_6881_().m_130940_(ChatFormatting.YELLOW);
        }
    }

    public static class FluidEntry
    implements ISuggestionRenderer {
        @Override
        public Component renderSuggestion(PoseStack stack, String value, int x, int y) {
            ResourceLocation id = ResourceLocation.m_135820_((String)value);
            if (id == null) {
                return null;
            }
            Fluid fluid = (Fluid)ForgeRegistries.FLUIDS.getValue(id);
            if (fluid == Fluids.f_76191_ || fluid == null) {
                return null;
            }
            TextureAtlasSprite sprite = this.getSprite(fluid);
            if (sprite == null) {
                return null;
            }
            RenderSystem.m_157456_((int)0, (ResourceLocation)InventoryMenu.f_39692_);
            int color = IClientFluidTypeExtensions.of((Fluid)fluid).getTintColor();
            RenderSystem.m_157429_((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)1.0f);
            GuiComponent.m_93200_((PoseStack)stack, (int)x, (int)y, (int)0, (int)18, (int)18, (TextureAtlasSprite)sprite);
            RenderSystem.m_157429_((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            return fluid.getFluidType().getDescription().m_6881_().m_130940_(ChatFormatting.YELLOW);
        }

        private TextureAtlasSprite getSprite(Fluid fluid) {
            return (TextureAtlasSprite)Minecraft.m_91087_().m_91258_(InventoryMenu.f_39692_).apply(IClientFluidTypeExtensions.of((Fluid)fluid).getStillTexture());
        }
    }

    public static class ItemEntry
    implements ISuggestionRenderer {
        @Override
        public Component renderSuggestion(PoseStack stack, String value, int x, int y) {
            ResourceLocation id = ResourceLocation.m_135820_((String)value);
            if (id == null) {
                return null;
            }
            Item item = (Item)ForgeRegistries.ITEMS.getValue(id);
            if (item == Items.f_41852_ || item == null) {
                return null;
            }
            ItemStack itemStack = new ItemStack((ItemLike)item);
            Minecraft.m_91087_().m_91291_().m_115203_(itemStack, x, y);
            return itemStack.m_41786_().m_6881_().m_130940_(ChatFormatting.YELLOW);
        }
    }
}

