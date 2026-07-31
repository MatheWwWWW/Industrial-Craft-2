/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.SoundType
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.BlockStateContainer
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block;

import ic2.core.block.BlockBase;
import ic2.core.block.UnstartingThreadLocal;
import ic2.core.block.state.EnumProperty;
import ic2.core.block.state.IIdProvider;
import ic2.core.block.type.IBlockSound;
import ic2.core.block.type.IExtBlockType;
import ic2.core.item.block.ItemBlockMulti;
import ic2.core.ref.BlockName;
import ic2.core.ref.IMultiBlock;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMultiID<T extends Enum<T>>
extends BlockBase
implements IMultiBlock<T> {
    private static final ThreadLocal<EnumProperty<? extends Enum<?>>> currentTypeProperty = new UnstartingThreadLocal();
    protected final EnumProperty<T> typeProperty = this.getTypeProperty();

    public static <T extends Enum<T>> BlockMultiID<T> create(BlockName name, Material material, Class<T> typeClass) {
        EnumProperty<T> typeProperty = BlockMultiID.createTypeProperty(typeClass);
        currentTypeProperty.set(typeProperty);
        BlockMultiID<T> ret = new BlockMultiID<T>(name, material);
        currentTypeProperty.remove();
        return ret;
    }

    private static <T extends Enum<T>> EnumProperty<T> createTypeProperty(Class<T> typeClass) {
        EnumProperty<T> ret = new EnumProperty<T>("type", typeClass);
        if (ret.getAllowedValues().size() > 16) {
            throw new IllegalArgumentException("Too many values to fit in 16 meta values for " + typeClass);
        }
        return ret;
    }

    protected static <T extends Enum<T>, U extends BlockMultiID<T>> U create(Class<U> blockClass, Class<T> typeClass, Object ... ctorArgs) {
        BlockMultiID ret;
        EnumProperty<T> typeProperty = BlockMultiID.createTypeProperty(typeClass);
        Constructor<?> ctor = null;
        block5: for (Constructor<?> cCtor : blockClass.getDeclaredConstructors()) {
            Class<?>[] parameterTypes = cCtor.getParameterTypes();
            if (parameterTypes.length != ctorArgs.length) continue;
            for (int i = 0; i < parameterTypes.length; ++i) {
                Class<?> type = parameterTypes[i];
                Object arg = ctorArgs[i];
                if (arg == null && type.isPrimitive() || arg != null && !parameterTypes[i].isInstance(arg)) continue block5;
            }
            if (ctor != null) {
                throw new IllegalArgumentException("ambiguous constructor");
            }
            ctor = cCtor;
        }
        if (ctor == null) {
            throw new IllegalArgumentException("no matching constructor");
        }
        currentTypeProperty.set(typeProperty);
        try {
            ctor.setAccessible(true);
            ret = (BlockMultiID)ctor.newInstance(ctorArgs);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            currentTypeProperty.remove();
        }
        return (U)ret;
    }

    protected BlockMultiID(BlockName name, Material material) {
        this(name, material, ItemBlockMulti.class);
    }

    protected BlockMultiID(BlockName name, Material material, Class<? extends ItemBlock> itemClass) {
        super(name, material, itemClass);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(this.typeProperty, this.typeProperty.getDefault()));
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerModels(BlockName name) {
        BlockMultiID.registerItemModels(this, this.getTypeStates());
    }

    protected final List<IBlockState> getTypeStates() {
        ArrayList<IBlockState> ret = new ArrayList<IBlockState>(this.typeProperty.getAllowedValues().size());
        for (Enum type : this.typeProperty.getAllowedValues()) {
            ret.add(this.func_176223_P().func_177226_a(this.typeProperty, (Comparable)((Object)type)));
        }
        return ret;
    }

    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[]{this.getTypeProperty()});
    }

    public IBlockState func_176203_a(int meta) {
        EnumProperty<T> typeProperty = this.getTypeProperty();
        return this.func_176223_P().func_177226_a(typeProperty, typeProperty.getValueOrDefault(meta));
    }

    public int func_176201_c(IBlockState state) {
        return ((IIdProvider)((Object)((Enum)((Object)state.func_177229_b(this.getTypeProperty()))))).getId();
    }

    public T getType(IBlockAccess world, BlockPos pos) {
        return this.getType(world.func_180495_p(pos));
    }

    public final T getType(IBlockState state) {
        if (state.func_177230_c() != this) {
            return null;
        }
        return (T)((Enum)((Object)state.func_177229_b(this.typeProperty)));
    }

    @Override
    public IBlockState getState(T type) {
        if (type == null) {
            throw new IllegalArgumentException("invalid type: " + type);
        }
        return this.func_176223_P().func_177226_a(this.typeProperty, type);
    }

    @Override
    public IBlockState getState(String variant) {
        if (variant == null) {
            return this.func_176223_P();
        }
        for (Enum type : this.typeProperty.getAllowedValues()) {
            if (!type.name().equals(variant)) continue;
            return this.getState(type);
        }
        throw new IllegalArgumentException("Invalid type " + variant + " for " + this);
    }

    @Override
    public ItemStack getItemStack(T type) {
        return this.getItemStack(this.getState(type));
    }

    @Override
    public ItemStack getItemStack(String variant) {
        if (variant == null) {
            throw new IllegalArgumentException("invalid type: " + variant);
        }
        T type = this.typeProperty.getValue(variant);
        if (type == null) {
            throw new IllegalArgumentException("invalid variant " + variant + " for " + this);
        }
        return this.getItemStack(type);
    }

    @Override
    public String getVariant(ItemStack stack) {
        if (stack == null) {
            throw new NullPointerException("null stack");
        }
        Item item = Item.func_150898_a((Block)this);
        if (stack.func_77973_b() != item) {
            throw new IllegalArgumentException("The stack " + stack + " doesn't match " + item + " (" + this + ")");
        }
        IBlockState state = this.func_176203_a(stack.func_77960_j());
        T type = this.getType(state);
        return ((IIdProvider)type).getName();
    }

    @Override
    public ItemStack getItemStack(IBlockState state) {
        if (state.func_177230_c() != this) {
            return null;
        }
        Item item = Item.func_150898_a((Block)this);
        if (item == null || item == Items.field_190931_a) {
            throw new RuntimeException("no matching item for " + this);
        }
        int meta = this.func_176201_c(state);
        return new ItemStack(item, 1, meta);
    }

    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        ItemStack stack = this.getItemStack(state);
        if (stack == null) {
            return new ArrayList<ItemStack>();
        }
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(stack);
        return ret;
    }

    public void func_149666_a(CreativeTabs tabs, NonNullList<ItemStack> itemList) {
        for (Enum type : this.typeProperty.getShownValues()) {
            itemList.add((Object)this.getItemStack(type));
        }
    }

    @Override
    public Set<T> getAllTypes() {
        return EnumSet.allOf(this.typeProperty.func_177699_b());
    }

    public ItemStack func_185473_a(World world, BlockPos pos, IBlockState state) {
        return this.getItemStack(state);
    }

    public final EnumProperty<T> getTypeProperty() {
        EnumProperty<Object> ret;
        if (this.typeProperty != null) {
            ret = this.typeProperty;
        } else {
            ret = currentTypeProperty.get();
            if (ret == null) {
                throw new IllegalStateException("The type property can't be obtained.");
            }
        }
        return ret;
    }

    public float func_176195_g(IBlockState state, World world, BlockPos pos) {
        T type;
        if (IExtBlockType.class.isAssignableFrom(this.typeProperty.func_177699_b()) && (type = this.getType(state)) != null) {
            return ((IExtBlockType)type).getHardness();
        }
        return super.func_176195_g(state, world, pos);
    }

    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
        T type;
        if (IExtBlockType.class.isAssignableFrom(this.typeProperty.func_177699_b()) && (type = this.getType((IBlockAccess)world, pos)) != null) {
            return ((IExtBlockType)type).getExplosionResistance();
        }
        return super.getExplosionResistance(world, pos, exploder, explosion);
    }

    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, Entity entity) {
        T type;
        if (IBlockSound.class.isAssignableFrom(this.typeProperty.func_177699_b()) && (type = this.getType(state)) != null) {
            return ((IBlockSound)type).getSound();
        }
        return super.getSoundType(state, world, pos, entity);
    }
}

