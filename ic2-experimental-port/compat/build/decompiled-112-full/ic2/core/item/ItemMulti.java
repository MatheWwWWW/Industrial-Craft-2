/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.EnumRarity
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ActionResult
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item;

import ic2.core.block.state.EnumProperty;
import ic2.core.block.state.IIdProvider;
import ic2.core.item.ItemIC2;
import ic2.core.ref.IMultiItem;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMulti<T extends Enum<T>>
extends ItemIC2
implements IMultiItem<T> {
    protected final EnumProperty<T> typeProperty;
    private final Map<T, IItemRightClickHandler> rightClickHandlers = new IdentityHashMap<T, IItemRightClickHandler>();
    private final Map<T, IItemUseHandler> useHandlers = new IdentityHashMap<T, IItemUseHandler>();
    private final Map<T, IItemUpdateHandler> updateHandlers = new IdentityHashMap<T, IItemUpdateHandler>();
    private final Map<T, EnumRarity> rarityFilter = new IdentityHashMap<T, EnumRarity>();

    public static <T extends Enum<T>> ItemMulti<T> create(ItemName name, Class<T> typeClass) {
        EnumProperty<T> typeProperty = new EnumProperty<T>("type", typeClass);
        if (typeProperty.getAllowedValues().size() > Short.MAX_VALUE) {
            throw new IllegalArgumentException("Too many values to fit in a short for " + typeClass);
        }
        return new ItemMulti<T>(name, typeProperty);
    }

    private ItemMulti(ItemName name, EnumProperty<T> typeProperty) {
        super(name);
        this.typeProperty = typeProperty;
        this.func_77627_a(true);
    }

    protected ItemMulti(ItemName name, Class<T> typeClass) {
        this(name, new EnumProperty<T>("type", typeClass));
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerModels(ItemName name) {
        for (Enum type : this.typeProperty.getAllowedValues()) {
            this.registerModel(((IIdProvider)((Object)type)).getId(), name, ((IIdProvider)((Object)type)).getModelName());
        }
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public final int getItemColor(ItemStack stack) {
        T type = this.getType(stack);
        if (type == null) {
            return super.getItemColor(stack);
        }
        return ((IIdProvider)type).getColor();
    }

    @Override
    public final String func_77667_c(ItemStack stack) {
        T type = this.getType(stack);
        if (type == null) {
            return super.func_77667_c(stack);
        }
        return super.func_77667_c(stack) + "." + ((IIdProvider)type).getName();
    }

    @Override
    public ItemStack getItemStack(T type) {
        if (!this.typeProperty.getAllowedValues().contains(type)) {
            throw new IllegalArgumentException("invalid property value " + type + " for property " + this.typeProperty);
        }
        return this.getItemStackUnchecked(type);
    }

    private ItemStack getItemStackUnchecked(T type) {
        return new ItemStack((Item)this, 1, ((IIdProvider)type).getId());
    }

    @Override
    public ItemStack getItemStack(String variant) {
        T type = this.typeProperty.getValue(variant);
        if (type == null) {
            throw new IllegalArgumentException("invalid variant " + variant + " for " + this);
        }
        return this.getItemStackUnchecked(type);
    }

    @Override
    public String getVariant(ItemStack stack) {
        if (stack == null) {
            throw new NullPointerException("null stack");
        }
        if (stack.func_77973_b() != this) {
            throw new IllegalArgumentException("The stack " + stack + " doesn't match " + this);
        }
        T type = this.getType(stack);
        if (type == null) {
            throw new IllegalArgumentException("The stack " + stack + " doesn't reference any valid subtype");
        }
        return ((IIdProvider)type).getName();
    }

    public final void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (!this.func_194125_a(tab)) {
            return;
        }
        for (Enum type : this.typeProperty.getShownValues()) {
            subItems.add((Object)this.getItemStackUnchecked(type));
        }
    }

    @Override
    public Set<T> getAllTypes() {
        return EnumSet.allOf(this.typeProperty.func_177699_b());
    }

    public final T getType(ItemStack stack) {
        return this.typeProperty.getValue(stack.func_77960_j());
    }

    public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = StackUtil.get(player, hand);
        T type = this.getType(stack);
        if (type == null) {
            return new ActionResult(EnumActionResult.PASS, (Object)stack);
        }
        IItemRightClickHandler handler = this.rightClickHandlers.get(type);
        if (handler == null) {
            return new ActionResult(EnumActionResult.PASS, (Object)stack);
        }
        return handler.onRightClick(stack, player, hand);
    }

    public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack stack = StackUtil.get(player, hand);
        T type = this.getType(stack);
        if (type == null) {
            return EnumActionResult.PASS;
        }
        IItemUseHandler handler = this.useHandlers.get(type);
        if (handler == null) {
            return EnumActionResult.PASS;
        }
        return handler.onUse(stack, player, pos, hand, side);
    }

    public void func_77663_a(ItemStack stack, World world, Entity entity, int slotIndex, boolean isCurrentItem) {
        T type = this.getType(stack);
        if (type == null) {
            return;
        }
        IItemUpdateHandler handler = this.updateHandlers.get(type);
        if (handler == null) {
            return;
        }
        handler.onUpdate(stack, world, entity, slotIndex, isCurrentItem);
    }

    @Override
    public EnumRarity func_77613_e(ItemStack stack) {
        EnumRarity rarity = this.rarityFilter.get(this.getType(stack));
        return rarity != null ? rarity : super.func_77613_e(stack);
    }

    public void setRightClickHandler(T type, IItemRightClickHandler handler) {
        if (type == null) {
            for (Enum cType : this.typeProperty.getAllowedValues()) {
                this.setRightClickHandler(cType, handler);
            }
        } else {
            this.rightClickHandlers.put(type, handler);
        }
    }

    public void setUseHandler(T type, IItemUseHandler handler) {
        if (type == null) {
            for (Enum cType : this.typeProperty.getAllowedValues()) {
                this.setUseHandler(cType, handler);
            }
        } else {
            this.useHandlers.put(type, handler);
        }
    }

    public void setUpdateHandler(T type, IItemUpdateHandler handler) {
        if (type == null) {
            for (Enum cType : this.typeProperty.getAllowedValues()) {
                this.setUpdateHandler(cType, handler);
            }
        } else {
            this.updateHandlers.put(type, handler);
        }
    }

    public void setRarity(T type, EnumRarity rarity) {
        if (type == null) {
            this.setRarity(rarity);
        } else {
            this.rarityFilter.put(type, rarity);
        }
    }

    public static interface IItemUpdateHandler {
        public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5);
    }

    public static interface IItemUseHandler {
        public EnumActionResult onUse(ItemStack var1, EntityPlayer var2, BlockPos var3, EnumHand var4, EnumFacing var5);
    }

    public static interface IItemRightClickHandler {
        public ActionResult<ItemStack> onRightClick(ItemStack var1, EntityPlayer var2, EnumHand var3);
    }
}

