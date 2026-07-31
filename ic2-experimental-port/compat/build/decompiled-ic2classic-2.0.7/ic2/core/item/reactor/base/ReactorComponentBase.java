/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.item.reactor.base;

import ic2.api.reactor.IReactorPlannerComponent;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import java.util.function.BiPredicate;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;

public abstract class ReactorComponentBase
extends IC2Item
implements IReactorPlannerComponent,
ISimpleItemModel {
    protected String textureFolder;
    protected String textureName;

    public ReactorComponentBase(String itemName, String textureFolder, String textureName, @Nullable PropertiesBuilder builder) {
        super(itemName, (builder == null ? new PropertiesBuilder() : builder).setNoRepair().maxStackSize(1).rarity(Rarity.UNCOMMON));
        this.textureFolder = textureFolder;
        this.textureName = textureName;
    }

    protected String getTextureFolder() {
        return this.textureFolder;
    }

    protected String getTextureName() {
        return this.textureName;
    }

    @Override
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(this.id.m_135827_(), this.getTextureFolder()).get(this.getTextureName());
    }

    @Override
    public void addAffectedSlots(int x, int y, BiPredicate<Integer, Integer> slots) {
        slots.test(x, y);
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (!this.m_220152_(group)) {
            return;
        }
        items.add((Object)new ItemStack((ItemLike)this));
    }

    @Override
    public void provideComponents(NonNullList<ItemStack> list) {
        list.add((Object)new ItemStack((ItemLike)this));
    }

    public boolean m_8120_(ItemStack stack) {
        return false;
    }
}

