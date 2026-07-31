/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.item.tool;

import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.inv.inventory.WikiInventory;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.wiki.base.book.BuildWiki;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class WikiItem
extends IC2Item
implements ISimpleItemModel,
IHasHeldGui {
    Supplier<BuildWiki> wiki;

    public WikiItem(Supplier<BuildWiki> wiki) {
        this("wiki_item", null, wiki);
    }

    public WikiItem(String itemName, @Nullable PropertiesBuilder properties, Supplier<BuildWiki> wiki) {
        super(itemName, (properties == null ? new PropertiesBuilder() : properties).maxStackSize(1).group(IC2.IC2_MAIN_GROUP));
        this.wiki = wiki;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (IC2.PLATFORM.isSimulating()) {
            IC2.PLATFORM.launchGui(playerIn, handIn, Direction.NORTH, this.getInventory(playerIn, handIn, playerIn.m_21120_(handIn)));
        }
        return InteractionResultHolder.m_19090_((Object)playerIn.m_21120_(handIn));
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (context.m_7078_() && IC2.PLATFORM.isRendering()) {
            ResourceLocation location = this.getPreviewLocation(ForgeRegistries.ITEMS.getKey((Object)this.getProvider(context)));
            if (!this.getWiki().tryOpenPage(location, 0)) {
                context.m_43723_().m_5661_((Component)this.string("Couldn't find page for block"), false);
                return InteractionResult.FAIL;
            }
            return InteractionResult.PASS;
        }
        return super.onItemUseFirst(stack, context);
    }

    private Item getProvider(UseOnContext context) {
        BlockState state = context.m_43725_().m_8055_(context.m_8083_());
        return state.getCloneItemStack((HitResult)new BlockHitResult(context.m_43720_(), context.m_43719_(), context.m_8083_(), context.m_43721_()), (BlockGetter)context.m_43725_(), context.m_8083_(), context.m_43723_()).m_41720_();
    }

    private ResourceLocation getPreviewLocation(ResourceLocation location) {
        return new ResourceLocation(location.m_135827_(), "preview." + location.m_135815_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("misc").get("wiki_book");
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return new WikiInventory(player, this, stack, null).load(stack);
    }

    public BuildWiki getWiki() {
        return this.wiki.get();
    }
}

