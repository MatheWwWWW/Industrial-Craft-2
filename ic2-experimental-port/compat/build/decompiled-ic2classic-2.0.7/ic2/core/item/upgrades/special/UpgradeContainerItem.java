/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.NonNullList
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
package ic2.core.item.upgrades.special;

import ic2.api.items.IUpgradeItem;
import ic2.api.recipes.ingridients.queue.IStackOutput;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.tiles.IMachine;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.inv.inventory.UpgradeContainerInventory;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.NonNullList;
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

public class UpgradeContainerItem
extends IC2Item
implements IHasHeldSlotInventory,
IUpgradeItem,
ISimpleItemModel {
    public UpgradeContainerItem() {
        super("upgrade_container", new PropertiesBuilder().maxStackSize(1));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("upgrades/machines").get("container");
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return new UpgradeContainerInventory(player, this, stack, null).load(stack);
    }

    @Override
    public IPortableInventory getInventory(Player player, ItemStack stack, Slot slot) {
        return new UpgradeContainerInventory(player, this, stack, slot).load(stack);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        IntOpenHashSet valid = new IntOpenHashSet(StackUtil.getNbtData(stack).m_128465_("valid"));
        List<ItemStack> items = this.getItems(stack);
        int m = items.size();
        for (int i = 0; i < m; ++i) {
            ItemStack entry = items.get(i);
            if (entry.m_41619_()) continue;
            helper.addSimpleToolTip("tooltip.item.ic2.upgrade_container." + (valid.contains(i) ? "valid" : "invalid"), entry.m_41613_(), entry.m_41786_());
        }
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (IC2.KEYBOARD.isSideInventoryKeyDown(playerIn)) {
            ItemStack stack = playerIn.m_21120_(handIn);
            if (IC2.PLATFORM.isSimulating()) {
                IC2.PLATFORM.launchGui(playerIn, handIn, null, this.getInventory(playerIn, handIn, stack));
            }
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(worldIn, playerIn, handIn);
    }

    @Override
    public IUpgradeItem.UpgradeType getType(ItemStack stack) {
        return IUpgradeItem.UpgradeType.CUSTOM_MOD;
    }

    @Override
    public EnumSet<IUpgradeItem.Functions> getFunctions(ItemStack stack) {
        EnumSet<IUpgradeItem.Functions> functions = EnumSet.noneOf(IUpgradeItem.Functions.class);
        List<ItemStack> list = this.getValidItems(stack);
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack entry = list.get(i);
            if (!(stack.m_41720_() instanceof IUpgradeItem)) continue;
            functions.addAll(((IUpgradeItem)entry.m_41720_()).getFunctions(entry));
        }
        return functions;
    }

    @Override
    public void onInstall(ItemStack stack, IMachine machine) {
        IntArrayList upgrades = new IntArrayList();
        List<ItemStack> items = this.getItems(stack);
        EnumSet<IUpgradeItem.UpgradeType> types = machine.getSupportedUpgradeTypes();
        int m = items.size();
        for (int i = 0; i < m; ++i) {
            IUpgradeItem item;
            ItemStack entry = items.get(i);
            if (!(entry.m_41720_() instanceof IUpgradeItem) || !types.contains((Object)(item = (IUpgradeItem)entry.m_41720_()).getType(entry))) continue;
            item.onInstall(entry, machine);
            upgrades.add(i);
        }
        this.setValidItems(stack, upgrades.toIntArray());
    }

    @Override
    public double getProcessingSpeedMultiplier(ItemStack stack, IMachine machine) {
        double multiplier = 1.0;
        List<ItemStack> list = this.getValidItems(stack);
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack entry = list.get(i);
            if (!(entry.m_41720_() instanceof IUpgradeItem)) continue;
            multiplier *= Math.pow(((IUpgradeItem)entry.m_41720_()).getProcessingSpeedMultiplier(entry, machine), entry.m_41613_());
        }
        return multiplier;
    }

    @Override
    public int getExtraProcessingSpeed(ItemStack stack, IMachine machine) {
        int extra = 0;
        List<ItemStack> list = this.getValidItems(stack);
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack entry = list.get(i);
            if (!(entry.m_41720_() instanceof IUpgradeItem)) continue;
            extra += ((IUpgradeItem)entry.m_41720_()).getExtraProcessingSpeed(entry, machine) * entry.m_41613_();
        }
        return extra;
    }

    @Override
    public double getProcessingTimeMultiplier(ItemStack stack, IMachine machine) {
        double multiplier = 1.0;
        List<ItemStack> list = this.getValidItems(stack);
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack entry = list.get(i);
            if (!(entry.m_41720_() instanceof IUpgradeItem)) continue;
            multiplier *= Math.pow(((IUpgradeItem)entry.m_41720_()).getProcessingTimeMultiplier(entry, machine), entry.m_41613_());
        }
        return multiplier;
    }

    @Override
    public int getExtraProcessingTime(ItemStack stack, IMachine machine) {
        int extra = 0;
        List<ItemStack> list = this.getValidItems(stack);
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack entry = list.get(i);
            if (!(entry.m_41720_() instanceof IUpgradeItem)) continue;
            extra += ((IUpgradeItem)entry.m_41720_()).getExtraProcessingTime(entry, machine) * entry.m_41613_();
        }
        return extra;
    }

    @Override
    public double getEnergyDemandMultiplier(ItemStack stack, IMachine machine) {
        double multiplier = 1.0;
        List<ItemStack> list = this.getValidItems(stack);
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack entry = list.get(i);
            if (!(entry.m_41720_() instanceof IUpgradeItem)) continue;
            multiplier *= Math.pow(((IUpgradeItem)entry.m_41720_()).getEnergyDemandMultiplier(entry, machine), entry.m_41613_());
        }
        return multiplier;
    }

    @Override
    public int getExtraEnergyDemand(ItemStack stack, IMachine machine) {
        int extra = 0;
        List<ItemStack> list = this.getValidItems(stack);
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack entry = list.get(i);
            if (!(entry.m_41720_() instanceof IUpgradeItem)) continue;
            extra += ((IUpgradeItem)entry.m_41720_()).getExtraEnergyDemand(entry, machine) * entry.m_41613_();
        }
        return extra;
    }

    @Override
    public double getEnergyStorageMultiplier(ItemStack stack, IMachine machine) {
        double multiplier = 1.0;
        List<ItemStack> list = this.getValidItems(stack);
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack entry = list.get(i);
            if (!(entry.m_41720_() instanceof IUpgradeItem)) continue;
            multiplier *= Math.pow(((IUpgradeItem)entry.m_41720_()).getEnergyStorageMultiplier(entry, machine), entry.m_41613_());
        }
        return multiplier;
    }

    @Override
    public int getExtraEnergyStorage(ItemStack stack, IMachine machine) {
        int extra = 0;
        List<ItemStack> list = this.getValidItems(stack);
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack entry = list.get(i);
            if (!(entry.m_41720_() instanceof IUpgradeItem)) continue;
            extra += ((IUpgradeItem)entry.m_41720_()).getExtraEnergyStorage(entry, machine) * entry.m_41613_();
        }
        return extra;
    }

    @Override
    public int getExtraTier(ItemStack stack, IMachine machine) {
        int extra = 0;
        List<ItemStack> list = this.getValidItems(stack);
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack entry = list.get(i);
            if (!(entry.m_41720_() instanceof IUpgradeItem)) continue;
            extra += ((IUpgradeItem)entry.m_41720_()).getExtraTier(entry, machine) * entry.m_41613_();
        }
        return extra;
    }

    @Override
    public float getSoundMultiplier(ItemStack stack, IMachine machine) {
        float multiplier = 1.0f;
        List<ItemStack> list = this.getValidItems(stack);
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack entry = list.get(i);
            if (!(entry.m_41720_() instanceof IUpgradeItem)) continue;
            multiplier = (float)((double)multiplier * Math.pow(((IUpgradeItem)entry.m_41720_()).getSoundMultiplier(entry, machine), entry.m_41613_()));
        }
        return multiplier;
    }

    @Override
    public boolean useRedstoneInvertion(ItemStack stack, IMachine machine) {
        List<ItemStack> list = this.getValidItems(stack);
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack entry = list.get(i);
            if (!(entry.m_41720_() instanceof IUpgradeItem) || !((IUpgradeItem)entry.m_41720_()).useRedstoneInvertion(entry, machine)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void onTick(ItemStack stack, IMachine machine) {
        List<ItemStack> list = this.getValidItems(stack);
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack entry = list.get(i);
            if (!(entry.m_41720_() instanceof IUpgradeItem)) continue;
            ((IUpgradeItem)entry.m_41720_()).onTick(entry, machine);
        }
    }

    @Override
    public void onMachineFinishedRecipePre(ItemStack stack, IMachine machine, IRecipeOutput output, CompoundTag recipeFlags) {
        ValidContainer container = this.getValidItemContainer(stack);
        if (container == null) {
            return;
        }
        for (ItemStack entry : container) {
            if (!(entry.m_41720_() instanceof IUpgradeItem)) continue;
            ((IUpgradeItem)entry.m_41720_()).onMachineFinishedRecipePre(entry, machine, output, recipeFlags);
        }
        container.close();
    }

    @Override
    public void onMachineFinishedRecipePost(ItemStack stack, IMachine machine, IMachineRecipeList.RecipeEntry recipe, List<IStackOutput> drops) {
        ValidContainer container = this.getValidItemContainer(stack);
        if (container == null) {
            return;
        }
        for (ItemStack entry : container) {
            if (!(entry.m_41720_() instanceof IUpgradeItem)) continue;
            ((IUpgradeItem)entry.m_41720_()).onMachineFinishedRecipePost(entry, machine, recipe, drops);
        }
        container.close();
    }

    @Override
    public void onMachineProcessed(ItemStack stack, IMachine machine) {
        ValidContainer container = this.getValidItemContainer(stack);
        if (container == null) {
            return;
        }
        for (ItemStack entry : container) {
            if (!(entry.m_41720_() instanceof IUpgradeItem)) continue;
            ((IUpgradeItem)entry.m_41720_()).onMachineProcessed(entry, machine);
        }
        container.close();
    }

    public void setValidItems(ItemStack stack, int[] slots) {
        if (slots.length <= 0) {
            stack.m_41784_().m_128473_("valid");
            return;
        }
        stack.m_41784_().m_128385_("valid", slots);
    }

    public List<ItemStack> getValidItems(ItemStack stack) {
        CompoundTag nbt = StackUtil.getNbtData(stack);
        int[] validSlots = nbt.m_128465_("valid");
        if (validSlots.length <= 0) {
            return ObjectLists.emptyList();
        }
        NonNullList inv = NonNullList.m_122780_((int)3, (Object)ItemStack.f_41583_);
        NBTUtils.loadItems(nbt, (NonNullList<ItemStack>)inv);
        NonNullList result = NonNullList.m_122779_();
        int m = validSlots.length;
        for (int i = 0; i < m; ++i) {
            ItemStack entry = (ItemStack)inv.get(validSlots[i]);
            if (entry.m_41619_()) continue;
            result.add((Object)entry);
        }
        return result;
    }

    public ValidContainer getValidItemContainer(ItemStack stack) {
        CompoundTag nbt = StackUtil.getNbtData(stack);
        int[] validSlots = nbt.m_128465_("valid");
        return validSlots.length <= 0 ? null : new ValidContainer(validSlots, nbt);
    }

    public List<ItemStack> getItems(ItemStack stack) {
        return NBTUtils.getItems(stack);
    }

    private static class ValidContainer
    implements Iterable<ItemStack> {
        NonNullList<ItemStack> inv = NonNullList.m_122780_((int)3, (Object)ItemStack.f_41583_);
        List<ItemStack> valids = new ObjectArrayList();
        CompoundTag owner;

        public ValidContainer(int[] valid, CompoundTag owner) {
            this.owner = owner;
            NBTUtils.loadItems(owner, this.inv);
            int m = valid.length;
            for (int i = 0; i < m; ++i) {
                ItemStack entry = (ItemStack)this.inv.get(valid[i]);
                if (entry.m_41619_()) continue;
                this.valids.add(entry);
            }
        }

        @Override
        public Iterator<ItemStack> iterator() {
            return this.valids.iterator();
        }

        public void close() {
            NBTUtils.saveItems(this.owner, this.inv);
        }
    }
}

