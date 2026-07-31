/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.IC2
 *  ic2.core.platform.registries.IC2Items
 *  ic2.core.platform.rendering.IC2Textures
 *  ic2.core.platform.rendering.features.item.ISimpleItemModel
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 */
package trinsdar.advancedsolars.items;

import ic2.core.IC2;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemMisc
extends Item
implements ISimpleItemModel {
    final ResourceLocation id;

    public ItemMisc(String name) {
        super(new Item.Properties().m_41491_(IC2.IC2_MAIN_GROUP));
        this.id = new ResourceLocation("advanced_solars", name);
        IC2Items.registerItem((Item)this, (ResourceLocation)this.id);
    }

    public TextureAtlasSprite getTexture() {
        return (TextureAtlasSprite)IC2Textures.getMappedEntriesItem((String)"advanced_solars", (String)"materials").get(this.id.m_135815_());
    }

    public ResourceLocation getRegistryName() {
        return this.id;
    }
}

