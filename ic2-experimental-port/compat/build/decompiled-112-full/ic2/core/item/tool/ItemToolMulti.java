/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.EnumRarity
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
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
package ic2.core.item.tool;

import ic2.api.item.ICustomDamageItem;
import ic2.core.block.state.EnumProperty;
import ic2.core.block.state.IIdProvider;
import ic2.core.item.ItemIC2;
import ic2.core.item.ItemMulti;
import ic2.core.item.ItemToolIC2;
import ic2.core.item.tool.HarvestLevel;
import ic2.core.item.tool.IToolClass;
import ic2.core.ref.IMultiItem;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemToolMulti<T extends Enum<T>>
extends ItemToolIC2
implements IMultiItem<T>,
ICustomDamageItem {
    protected final EnumProperty<T> typeProperty;
    private final Map<T, ItemMulti.IItemRightClickHandler> rightClickHandlers = new IdentityHashMap<T, ItemMulti.IItemRightClickHandler>();
    private final Map<T, ItemMulti.IItemUseHandler> useHandlers = new IdentityHashMap<T, ItemMulti.IItemUseHandler>();
    private final Map<T, ItemMulti.IItemUpdateHandler> updateHandlers = new IdentityHashMap<T, ItemMulti.IItemUpdateHandler>();
    private final Map<T, EnumRarity> rarityFilter = new IdentityHashMap<T, EnumRarity>();

    public static <T extends Enum<T>> ItemToolMulti<T> create(ItemName name, Class<T> typeClass, float damage, float speed, HarvestLevel harvestLevel, Set<? extends IToolClass> toolClasses, Set<Block> mineableBlocks) {
        EnumProperty<T> typeProperty = new EnumProperty<T>("type", typeClass);
        if (typeProperty.getAllowedValues().size() > Short.MAX_VALUE) {
            throw new IllegalArgumentException("Too many values to fit in a short for " + typeClass);
        }
        return new ItemToolMulti<T>(name, typeProperty, damage, speed, harvestLevel, toolClasses, mineableBlocks);
    }

    private ItemToolMulti(ItemName name, EnumProperty<T> typeProperty, float damage, float speed, HarvestLevel harvestLevel, Set<? extends IToolClass> toolClasses, Set<Block> mineableBlocks) {
        super(name, damage, speed, harvestLevel, toolClasses, mineableBlocks);
        this.typeProperty = typeProperty;
        this.func_77627_a(true);
    }

    protected ItemToolMulti(ItemName name, Class<T> typeClass, HarvestLevel harvestLevel, Set<? extends IToolClass> toolClasses) {
        this(name, typeClass, harvestLevel, toolClasses, new HashSet<Block>());
    }

    protected ItemToolMulti(ItemName name, Class<T> typeClass, HarvestLevel harvestLevel, Set<? extends IToolClass> toolClasses, Set<Block> mineableBlocks) {
        this(name, typeClass, 0.0f, 0.0f, harvestLevel, toolClasses, mineableBlocks);
    }

    protected ItemToolMulti(ItemName name, Class<T> typeClass, float damage, float speed, HarvestLevel harvestLevel, Set<? extends IToolClass> toolClasses, Set<Block> mineableBlocks) {
        this(name, new EnumProperty<T>("type", typeClass), damage, speed, harvestLevel, toolClasses, mineableBlocks);
    }

    @Override
    public final String func_77667_c(ItemStack stack) {
        T type = this.getType(stack);
        return type == null ? super.func_77667_c(stack) : super.func_77667_c(stack) + "." + ((IIdProvider)type).getName();
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
    public EnumRarity func_77613_e(ItemStack stack) {
        EnumRarity rarity = this.rarityFilter.get(this.getType(stack));
        return rarity != null ? rarity : super.func_77613_e(stack);
    }

    public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = StackUtil.get(player, hand);
        T type = this.getType(stack);
        if (type == null) {
            return new ActionResult(EnumActionResult.PASS, (Object)stack);
        }
        ItemMulti.IItemRightClickHandler handler = this.rightClickHandlers.get(type);
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
        ItemMulti.IItemUseHandler handler = this.useHandlers.get(type);
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
        ItemMulti.IItemUpdateHandler handler = this.updateHandlers.get(type);
        if (handler == null) {
            return;
        }
        handler.onUpdate(stack, world, entity, slotIndex, isCurrentItem);
    }

    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    public double getDurabilityForDisplay(ItemStack stack) {
        return (double)this.getCustomDamage(stack) / (double)this.getMaxCustomDamage(stack);
    }

    public boolean func_77645_m() {
        return true;
    }

    public boolean isDamaged(ItemStack stack) {
        return this.getCustomDamage(stack) > 0;
    }

    public int getDamage(ItemStack stack) {
        return this.getCustomDamage(stack);
    }

    public int getMaxDamage(ItemStack stack) {
        return this.getMaxCustomDamage(stack);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerModels(ItemName name) {
        for (Enum type : this.typeProperty.getAllowedValues()) {
            ItemIC2.registerModel((Item)this, ((IIdProvider)((Object)type)).getId(), name, ((IIdProvider)((Object)type)).getModelName());
        }
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public final int getItemColor(ItemStack stack, int tintIndex) {
        T type = this.getType(stack);
        return type == null ? super.getItemColor(stack, tintIndex) : ((IIdProvider)type).getColor();
    }

    @Override
    public ItemStack getItemStack(T type) {
        if (!this.typeProperty.getAllowedValues().contains(type)) {
            throw new IllegalArgumentException("Invalid property value " + type + " for property " + this.typeProperty);
        }
        return this.getItemStackUnchecked(type);
    }

    @Override
    public ItemStack getItemStack(String variant) {
        T type = this.typeProperty.getValue(variant);
        if (type == null) {
            throw new IllegalArgumentException("Invalid variant " + variant + " for " + this);
        }
        return this.getItemStackUnchecked(type);
    }

    @Override
    public String getVariant(ItemStack stack) {
        if (stack == null) {
            throw new NullPointerException("The stack cannot be null");
        }
        if (stack.func_77973_b() != this) {
            throw new IllegalArgumentException("The stack " + stack + " does not match " + this);
        }
        T type = this.getType(stack);
        if (type == null) {
            throw new IllegalArgumentException("The stack " + stack + " does not reference any valid subtype");
        }
        return ((IIdProvider)type).getName();
    }

    @Override
    public Set<T> getAllTypes() {
        return EnumSet.allOf(this.typeProperty.func_177699_b());
    }

    @Override
    public int getCustomDamage(ItemStack stack) {
        if (!stack.func_77942_o()) {
            return 0;
        }
        NBTTagCompound data = stack.func_77978_p();
        assert (data != null);
        return data.func_74764_b("durability") ? data.func_74762_e("durability") : 0;
    }

    @Override
    public int getMaxCustomDamage(ItemStack stack) {
        if (!stack.func_77942_o()) {
            return 0;
        }
        NBTTagCompound data = stack.func_77978_p();
        assert (data != null);
        return data.func_74764_b("maxDurability") ? data.func_74762_e("maxDurability") : 0;
    }

    @Override
    public void setCustomDamage(ItemStack stack, int damage) {
        NBTTagCompound data = StackUtil.getOrCreateNbtData(stack);
        data.func_74768_a("durability", damage);
    }

    @Override
    public boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase source) {
        this.setCustomDamage(stack, this.getCustomDamage(stack) + damage);
        return true;
    }

    public final T getType(ItemStack stack) {
        return this.typeProperty.getValue(stack.func_77960_j());
    }

    public void setRightClickHandler(T type, ItemMulti.IItemRightClickHandler handler) {
        if (type == null) {
            for (Enum cType : this.typeProperty.getAllowedValues()) {
                this.setRightClickHandler(cType, handler);
            }
        } else {
            this.rightClickHandlers.put(type, handler);
        }
    }

    public void setUseHandler(T type, ItemMulti.IItemUseHandler handler) {
        if (type == null) {
            for (Enum cType : this.typeProperty.getAllowedValues()) {
                this.setUseHandler(cType, handler);
            }
        } else {
            this.useHandlers.put(type, handler);
        }
    }

    public void setUpdateHandler(T type, ItemMulti.IItemUpdateHandler handler) {
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

    private ItemStack getItemStackUnchecked(T type) {
        return new ItemStack((Item)this, 1, ((IIdProvider)type).getId());
    }
}

