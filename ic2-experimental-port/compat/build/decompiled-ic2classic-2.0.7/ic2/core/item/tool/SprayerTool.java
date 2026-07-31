/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.PriorityQueue
 *  it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.eventbus.api.Event
 */
package ic2.core.item.tool;

import ic2.api.events.FoamEvent;
import ic2.api.items.IRepairable;
import ic2.api.items.armor.IFoamSupplier;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.platform.rendering.features.item.IToolModel;
import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public class SprayerTool
extends IC2Item
implements ISimpleItemModel,
IToolModel,
IRepairable {
    public SprayerTool() {
        this("foam_sprayer", null);
    }

    public SprayerTool(String itemName, @Nullable PropertiesBuilder properties) {
        super(itemName, (properties == null ? new PropertiesBuilder() : properties).maxDamage(1600).group(IC2.IC2_MAIN_GROUP).setNoRepair());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools/cf_sprayer").get("manual");
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (!this.m_220152_(group)) {
            return;
        }
        items.add((Object)new ItemStack((ItemLike)this));
        ItemStack stack = new ItemStack((ItemLike)this);
        stack.m_41721_(1600);
        items.add((Object)stack);
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        return InteractionResultHolder.m_19090_((Object)playerIn.m_21120_(handIn));
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (IC2.PLATFORM.isRendering() || context.m_43723_() == null) {
            return InteractionResult.FAIL;
        }
        int sprayCount = this.getSprayLimit(stack);
        if (sprayCount <= 0) {
            return InteractionResult.FAIL;
        }
        BlockPlaceContext data = new BlockPlaceContext(context);
        Level world = data.m_43725_();
        BlockPos pos = data.m_8083_();
        BlockState state = world.m_8055_(pos);
        FoamEvent.Check check = this.postEvent(new FoamEvent.Check((LevelAccessor)world, pos));
        if (check.isCanceled()) {
            return InteractionResult.FAIL;
        }
        if (!state.m_60629_(data) && check.isCanceled() || !check.isCustomPlacement() && !IC2Blocks.CFOAM_WET.m_7898_(state, (LevelReader)world, pos)) {
            return InteractionResult.FAIL;
        }
        ItemStack pack = SprayerTool.getFoamProvider(context.m_43723_(), sprayCount);
        if (pack.m_41619_() && this.getStoredFoam(stack) < sprayCount) {
            return InteractionResult.FAIL;
        }
        FoamEvent.TargetType result = check.getType();
        DirectionList validDirections = this.calculateDirectionsFromPlayer(context.m_43723_());
        ObjectArrayFIFOQueue queue = new ObjectArrayFIFOQueue();
        ObjectLinkedOpenHashSet toPlace = new ObjectLinkedOpenHashSet();
        toPlace.add(pos);
        this.addNextPositions(pos, (PriorityQueue<BlockPos>)queue, (Set<BlockPos>)toPlace, check.isCustomPlacement() ? DirectionList.ALL : validDirections);
        --sprayCount;
        while (!queue.isEmpty() && sprayCount > 0) {
            BlockPos next = (BlockPos)queue.dequeue();
            check = this.postEvent(new FoamEvent.Check((LevelAccessor)world, next));
            if (check.isCanceled() || result != FoamEvent.TargetType.ANY && result != check.getType() || !check.isCustomPlacement() && !IC2Blocks.CFOAM_WET.m_7898_(world.m_8055_(next), (LevelReader)world, next) || !toPlace.add(next)) continue;
            this.addNextPositions(next, (PriorityQueue<BlockPos>)queue, (Set<BlockPos>)toPlace, check.isCustomPlacement() ? DirectionList.ALL : validDirections);
            --sprayCount;
        }
        for (BlockPos placePos : toPlace) {
            FoamEvent.Place place = this.postEvent(new FoamEvent.Place((LevelAccessor)world, placePos));
            if (place.isCanceled() && !place.shouldPlaceFoam()) continue;
            world.m_46597_(placePos, IC2Blocks.CFOAM_WET.m_49966_());
        }
        if (toPlace.size() > 0) {
            if (!pack.m_41619_()) {
                ((IFoamSupplier)pack.m_41720_()).useFoam(context.m_43723_(), pack, toPlace.size());
            } else {
                this.useFoam(stack, context.m_43723_(), toPlace.size());
            }
            this.onFoamUsed(stack, context.m_43723_(), toPlace.size());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    public void addNextPositions(BlockPos pos, PriorityQueue<BlockPos> queue, Set<BlockPos> alreadyVisited, DirectionList dirs) {
        for (Direction dir : dirs.getRandomIterator()) {
            BlockPos offset = pos.m_121945_(dir);
            if (alreadyVisited.contains(offset)) continue;
            queue.enqueue((Object)offset);
        }
    }

    public DirectionList calculateDirectionsFromPlayer(Player player) {
        DirectionList dirs = DirectionList.ALL;
        float yaw = player.m_146908_() % 360.0f;
        float pitch = player.m_146909_() % 360.0f;
        if (pitch >= -65.0f && pitch <= 65.0f) {
            if (yaw >= 300.0f && yaw <= 360.0f || yaw >= 0.0f && yaw <= 60.0f) {
                dirs = dirs.remove(Direction.NORTH);
            }
            if (yaw >= 30.0f && yaw <= 150.0f) {
                dirs = dirs.remove(Direction.EAST);
            }
            if (yaw >= 120.0f && yaw <= 240.0f) {
                dirs = dirs.remove(Direction.SOUTH);
            }
            if (yaw >= 210.0f && yaw <= 330.0f) {
                dirs = dirs.remove(Direction.WEST);
            }
        }
        if (pitch <= -40.0f) {
            dirs = dirs.remove(Direction.DOWN);
        }
        if (pitch >= 40.0f) {
            dirs = dirs.remove(Direction.UP);
        }
        return dirs;
    }

    private <T extends Event> T postEvent(T event) {
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }

    public int getSprayLimit(ItemStack stack) {
        return 13;
    }

    public int getStoredFoam(ItemStack stack) {
        return stack.m_41776_() - stack.m_41773_();
    }

    @Override
    public boolean repairDamage(ItemStack stack, int amount) {
        if (stack.m_41773_() > 0) {
            stack.m_41721_(Math.max(0, stack.m_41773_() - amount));
            return true;
        }
        return false;
    }

    public void useFoam(ItemStack stack, Player player, int amount) {
        if (player.m_7500_()) {
            return;
        }
        stack.m_41721_(stack.m_41773_() + amount);
    }

    public void onFoamUsed(ItemStack stack, Player player, int amount) {
        player.m_36222_(IC2Stats.FOAM_SPRAYED, amount);
    }

    public static ItemStack getFoamProvider(Player player, int amount) {
        ItemStack stack;
        ItemStack stack2;
        int i;
        Inventory inv = player.m_150109_();
        int m = inv.f_35975_.size();
        for (i = 0; i < m; ++i) {
            stack2 = (ItemStack)inv.f_35975_.get(i);
            if (!(stack2.m_41720_() instanceof IFoamSupplier) || !((IFoamSupplier)stack2.m_41720_()).canProvideFoam(player, stack2, IFoamSupplier.InventoryType.ARMOR, amount)) continue;
            return stack2;
        }
        if (IC2.CURIO_PLUGIN != null && !(stack = IC2.CURIO_PLUGIN.getFoamSupplier(player, amount)).m_41619_()) {
            return stack;
        }
        m = inv.f_35974_.size();
        for (i = 0; i < m; ++i) {
            stack2 = (ItemStack)inv.f_35974_.get(i);
            if (!(stack2.m_41720_() instanceof IFoamSupplier) || !((IFoamSupplier)stack2.m_41720_()).canProvideFoam(player, stack2, IFoamSupplier.InventoryType.HOTBAR, amount)) continue;
            return stack2;
        }
        m = inv.f_35976_.size();
        for (i = 0; i < m; ++i) {
            stack2 = (ItemStack)inv.f_35976_.get(i);
            if (!(stack2.m_41720_() instanceof IFoamSupplier) || !((IFoamSupplier)stack2.m_41720_()).canProvideFoam(player, stack2, IFoamSupplier.InventoryType.OFFHAND, amount)) continue;
            return stack2;
        }
        return ItemStack.f_41583_;
    }
}

