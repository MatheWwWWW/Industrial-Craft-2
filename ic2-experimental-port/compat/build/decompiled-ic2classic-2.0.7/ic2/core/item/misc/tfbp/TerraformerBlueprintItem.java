/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.misc.tfbp;

import ic2.api.items.ITerraformerBP;
import ic2.api.network.item.INetworkItemEvent;
import ic2.api.tiles.ITerraformer;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.base.IC2Item;
import ic2.core.item.inv.container.TFBPContainer;
import ic2.core.item.inv.inventory.TFBPInventory;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TerraformerBlueprintItem
extends IC2Item
implements ISimpleItemModel,
ITerraformerBP,
IHasHeldSlotInventory,
INetworkItemEvent {
    ITerraformerBP bluePrint;
    String texture;
    String path;

    public TerraformerBlueprintItem(String itemName, ITerraformerBP bluePrint, String path, String texture) {
        super(itemName);
        this.path = path;
        this.texture = texture;
        this.bluePrint = bluePrint;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, KeyHelper.SIDE_INV_KEY, "tooltip.ic2.open_item_inventory", new Object[0]));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(this.id.m_135827_(), this.path).get(this.texture);
    }

    @Override
    public boolean canInsert(ItemStack stack, Player player, Level world, BlockPos pos) {
        return this.bluePrint.canInsert(stack, player, world, pos);
    }

    @Override
    public boolean isRandomized(ItemStack stack) {
        return this.bluePrint.isRandomized(stack);
    }

    @Override
    public int getEnergyUsage(ItemStack stack) {
        return this.bluePrint.getEnergyUsage(stack);
    }

    @Override
    public int getRadius(ItemStack stack) {
        int radius = StackUtil.getNbtData(stack).m_128451_("radius");
        return radius > 0 ? radius : this.bluePrint.getRadius(stack);
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return new TFBPInventory(player, this, stack, null).load(stack);
    }

    @Override
    public IPortableInventory getInventory(Player player, ItemStack stack, Slot slot) {
        return new TFBPInventory(player, this, stack, slot).load(stack);
    }

    @Override
    public void onEventReceived(ItemStack stack, Player player, int key, int value, Dist target) {
        if (target.isDedicatedServer()) {
            TFBPContainer container = (TFBPContainer)this.getContainer(player, TFBPContainer.class);
            if (container == null) {
                return;
            }
            ((TFBPInventory)container.getHolder()).onDataReceived(key, value);
        }
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        if (this instanceof IHasHeldGui && IC2.KEYBOARD.isSideInventoryKeyDown(player)) {
            ItemStack stack = player.m_21120_(hand);
            IC2.PLATFORM.launchGui(player, hand, Direction.NORTH, this.getInventory(player, hand, stack));
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(world, player, hand);
    }

    @Override
    public boolean terraform(ItemStack stack, Level world, BlockPos position, ITerraformer terraformer) {
        return this.bluePrint.terraform(stack, world, position, terraformer);
    }

    @Override
    public void onInsert(ItemStack stack, Player player, Level world, BlockPos pos) {
        this.bluePrint.onInsert(stack, player, world, pos);
    }

    public static void setRadius(ItemStack stack, int radius) {
        if (radius <= 0) {
            CompoundTag nbt = StackUtil.getNbtData(stack);
            nbt.m_128473_("radius");
            if (nbt.m_128456_()) {
                stack.m_41751_(null);
            }
            return;
        }
        stack.m_41784_().m_128405_("radius", radius);
    }
}

