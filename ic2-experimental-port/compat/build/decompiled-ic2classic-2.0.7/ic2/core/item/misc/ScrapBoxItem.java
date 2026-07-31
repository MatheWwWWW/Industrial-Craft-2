/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockSource
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Position
 *  net.minecraft.core.dispenser.DefaultDispenseItemBehavior
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.DispenserBlock
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.eventbus.api.Event
 */
package ic2.core.item.misc;

import ic2.api.events.ScrapBoxEvent;
import ic2.api.recipes.registries.IScrapBoxRegistry;
import ic2.core.IC2;
import ic2.core.item.base.IC2Item;
import ic2.core.platform.recipes.misc.FoodCanRegistry;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public class ScrapBoxItem
extends IC2Item
implements ISimpleItemModel {
    public ScrapBoxItem() {
        super("scrapbox");
    }

    public static void loadBoxes(IScrapBoxRegistry registry) {
        LocalDate date = LocalDate.now();
        LocalDate start = LocalDate.of(date.getYear(), Month.APRIL, 1);
        LocalDate end = LocalDate.of(date.getYear(), Month.APRIL, 4);
        if (date.isAfter(start) && date.isBefore(end)) {
            registry.addDrop((ItemLike)Items.f_42424_, 9001.0f);
        } else {
            registry.addDrop((ItemLike)Items.f_42424_, 5.01f);
        }
        registry.addDrop((ItemLike)Blocks.f_50493_, 5.0f);
        registry.addDrop((ItemLike)Blocks.f_50034_, 3.0f);
        registry.addDrop((ItemLike)Blocks.f_49994_, 3.0f);
        registry.addDrop((ItemLike)Blocks.f_50134_, 2.0f);
        registry.addDrop((ItemLike)Blocks.f_50135_, 1.0f);
        registry.addDrop((ItemLike)Blocks.f_50133_, 0.9f);
        registry.addDrop((ItemLike)Blocks.f_49996_, 0.5f);
        registry.addDrop((ItemLike)Blocks.f_49995_, 0.5f);
        registry.addDrop((ItemLike)Blocks.f_152505_, 0.7f);
        registry.addDrop((ItemLike)IC2Blocks.TIN_ORE, 0.7f);
        registry.addDrop((ItemLike)IC2Blocks.SILVER_ORE, 0.6f);
        registry.addDrop((ItemLike)Items.f_42451_, 0.9f);
        registry.addDrop((ItemLike)IC2Items.DUST_COAL, 0.8f);
        registry.addDrop((ItemLike)IC2Items.DUST_CHARCOAL, 0.8f);
        registry.addDrop((ItemLike)IC2Items.DUST_COPPER, 0.8f);
        registry.addDrop((ItemLike)IC2Items.DUST_TIN, 0.8f);
        registry.addDrop((ItemLike)IC2Items.DUST_SILVER, 0.7f);
        registry.addDrop((ItemLike)IC2Items.DUST_ALUMINIUM, 0.8f);
        registry.addDrop((ItemLike)IC2Items.DUST_DIAMOND, 0.5f);
        registry.addDrop((ItemLike)IC2Items.PLANT_BALL, 0.7f);
        registry.addDrop((ItemLike)IC2Items.DUST_IRON, 0.7f);
        registry.addDrop((ItemLike)IC2Items.DUST_GOLD, 0.7f);
        registry.addDrop((ItemLike)Items.f_42415_, 0.1f);
        registry.addDrop((ItemLike)Items.f_42616_, 0.05f);
        registry.addDrop((ItemLike)Items.f_42525_, 0.8f);
        registry.addDrop((ItemLike)IC2Items.RUBBER, 0.8f);
        registry.addDrop((ItemLike)Items.f_42420_, 1.0f);
        registry.addDrop((ItemLike)Items.f_42421_, 1.0f);
        registry.addDrop((ItemLike)Items.f_42422_, 1.0f);
        registry.addDrop((ItemLike)Items.f_42398_, 4.0f);
        registry.addDrop((ItemLike)Items.f_42442_, 0.2f);
        registry.addDrop((ItemLike)Items.f_42440_, 0.2f);
        registry.addDrop((ItemLike)Items.f_42443_, 0.2f);
        registry.addDrop((ItemLike)Items.f_42441_, 0.2f);
        registry.addDrop((ItemLike)Items.f_42438_, 0.2f);
        registry.addDrop((ItemLike)Items.f_42439_, 0.2f);
        registry.addDrop((ItemLike)IC2Items.RUBBERWOOD_SIGN, 0.2f);
        registry.addDrop((ItemLike)Items.f_42583_, 2.0f);
        registry.addDrop((ItemLike)Items.f_42410_, 1.5f);
        registry.addDrop((ItemLike)Items.f_42406_, 1.5f);
        registry.addDrop((ItemLike)IC2Items.TIN_CAN_FILLED, 1.5f);
        registry.addDrop((ItemLike)FoodCanRegistry.INSTANCE.getItemForFood(new ItemStack((ItemLike)Items.f_42436_)), 0.05f);
        registry.addDrop((ItemLike)FoodCanRegistry.INSTANCE.getItemForFood(new ItemStack((ItemLike)Items.f_42730_)), 0.05f);
        registry.addDrop((ItemLike)FoodCanRegistry.INSTANCE.getItemForFood(new ItemStack((ItemLike)Items.f_42583_)), 0.25f);
        registry.addDrop((ItemLike)Items.f_42502_, 0.5f);
        registry.addDrop((ItemLike)Items.f_42486_, 0.9f);
        registry.addDrop((ItemLike)Items.f_42580_, 0.9f);
        registry.addDrop((ItemLike)Items.f_42582_, 0.9f);
        registry.addDrop((ItemLike)Items.f_42659_, 0.9f);
        registry.addDrop((ItemLike)Items.f_42698_, 0.9f);
        registry.addDrop((ItemLike)Items.f_42454_, 1.0f);
        registry.addDrop((ItemLike)Items.f_42402_, 1.0f);
        registry.addDrop((ItemLike)Items.f_42500_, 1.0f);
        registry.addDrop((ItemLike)Items.f_42518_, 0.6f);
        registry.addDrop((ItemLike)Items.f_42476_, 0.05f);
        registry.addDrop((ItemLike)Items.f_42586_, 0.08f);
        registry.addDrop((ItemLike)Items.f_42419_, 0.01f);
        registry.addDrop((ItemLike)Items.f_42686_, 0.001f);
        registry.addDrop((ItemLike)Items.f_42449_, 0.09f);
        registry.addDrop((ItemLike)IC2Items.SU_BATTERY, 0.7f);
    }

    @Override
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("matter").get("scrapbox");
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (IC2.PLATFORM.isSimulating()) {
            IScrapBoxRegistry.IDrop drop = IC2.RECIPES.get((boolean)true).scrapBoxes.getRandomDrop(stack, false);
            if (drop != null) {
                if (drop.getChance() <= 0.1f) {
                    player.m_36220_(IC2Stats.LUCKY_PERSON);
                }
                ObjectList results = CollectionUtils.createList();
                results.add((ItemStack)drop.getDrop().m_41777_());
                MinecraftForge.EVENT_BUS.post((Event)new ScrapBoxEvent.ScrapBoxPlayerUseEvent((List<ItemStack>)results, stack, player, hand));
                for (ItemStack result : results) {
                    player.m_36176_(result, false);
                }
            }
            if (!player.m_7500_()) {
                stack.m_41774_(1);
            }
        }
        return InteractionResultHolder.m_19090_((Object)stack);
    }

    public static class DispenserScrapBoxBehavior
    extends DefaultDispenseItemBehavior {
        protected ItemStack m_7498_(BlockSource source, ItemStack stack) {
            IScrapBoxRegistry.IDrop drop = IC2.RECIPES.get().scrapBoxes.getRandomDrop(stack, false);
            if (drop != null) {
                ObjectList results = CollectionUtils.createList();
                results.add((ItemStack)drop.getDrop().m_41777_());
                MinecraftForge.EVENT_BUS.post((Event)new ScrapBoxEvent.ScrapBoxDispenseEvent((List<ItemStack>)results, stack, source));
                if (results.size() > 0) {
                    Direction direction = (Direction)source.m_6414_().m_61143_((Property)DispenserBlock.f_52659_);
                    Position pos = DispenserBlock.m_52720_((BlockSource)source);
                    ServerLevel world = source.m_7727_();
                    for (ItemStack dropItem : results) {
                        DispenserScrapBoxBehavior.m_123378_((Level)world, (ItemStack)dropItem, (int)6, (Direction)direction, (Position)pos);
                    }
                    this.m_123387_(source, (Direction)source.m_6414_().m_61143_((Property)DispenserBlock.f_52659_));
                }
            }
            stack.m_41774_(1);
            return stack;
        }
    }
}

