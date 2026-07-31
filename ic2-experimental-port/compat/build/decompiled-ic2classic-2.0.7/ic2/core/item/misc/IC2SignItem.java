/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.SignItem
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.item.misc;

import ic2.core.IC2;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.plugins.IRegistryProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.GameData;

public class IC2SignItem
extends SignItem
implements ISimpleItemModel,
IRegistryProvider {
    private String folder;
    private String texture;
    private ResourceLocation id;

    public IC2SignItem(String name, Block sign, Block wallSign, String folder, String texture) {
        super(new Item.Properties().m_41491_(IC2.IC2_MAIN_GROUP).m_41487_(16), sign, wallSign);
        this.id = GameData.checkPrefix((String)name, (boolean)false);
        this.folder = folder;
        this.texture = texture;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    @Override
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(this.id.m_135827_(), this.folder).get(this.texture);
    }
}

