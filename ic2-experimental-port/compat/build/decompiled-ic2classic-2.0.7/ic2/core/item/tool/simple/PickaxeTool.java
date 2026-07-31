/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.PickaxeItem
 *  net.minecraft.world.item.Tier
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.item.tool.simple;

import ic2.core.IC2;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.platform.rendering.features.item.IToolModel;
import ic2.core.utils.plugins.IRegistryProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.GameData;

public class PickaxeTool
extends PickaxeItem
implements ISimpleItemModel,
IToolModel,
IRegistryProvider {
    private String textureName;
    private String textureFolder;
    private ResourceLocation id;

    public PickaxeTool(String itemName, Item.Properties properties, String textureFolder, String textureName, Tier tier) {
        super(tier, 1, -2.8f, properties);
        this.id = GameData.checkPrefix((String)itemName, (boolean)false);
        this.textureFolder = textureFolder;
        this.textureName = textureName;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    public PickaxeTool(String itemName, String textureFolder, String textureName, Tier tier) {
        this(itemName, new Item.Properties().m_41491_(IC2.IC2_MAIN_GROUP), textureFolder, textureName, tier);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(this.id.m_135827_(), this.textureFolder).get(this.textureName);
    }
}

