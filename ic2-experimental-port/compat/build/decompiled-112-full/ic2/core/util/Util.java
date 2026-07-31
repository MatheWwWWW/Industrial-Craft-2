/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.ChunkPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraft.world.chunk.EmptyChunk
 *  net.minecraft.world.gen.ChunkProviderServer
 *  net.minecraftforge.common.util.FakePlayer
 *  net.minecraftforge.oredict.OreDictionary
 */
package ic2.core.util;

import ic2.core.IC2;
import ic2.core.Ic2Player;
import ic2.core.util.LogCategory;
import ic2.core.util.ReflectionUtil;
import ic2.core.util.StackUtil;
import ic2.core.util.Vector3;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.oredict.OreDictionary;

public final class Util {
    private static final Map<Class<? extends IBlockAccess>, Field> worldFieldCache = new IdentityHashMap<Class<? extends IBlockAccess>, Field>();
    public static Set<EnumFacing> noFacings = Collections.emptySet();
    public static Set<EnumFacing> onlyNorth = Collections.unmodifiableSet(EnumSet.of(EnumFacing.NORTH));
    public static Set<EnumFacing> horizontalFacings = Collections.unmodifiableSet(EnumSet.copyOf(Arrays.asList(EnumFacing.field_176754_o)));
    public static Set<EnumFacing> verticalFacings = Collections.unmodifiableSet(EnumSet.of(EnumFacing.DOWN, EnumFacing.UP));
    public static Set<EnumFacing> downSideFacings = Collections.unmodifiableSet(EnumSet.complementOf(EnumSet.of(EnumFacing.UP)));
    public static Set<EnumFacing> allFacings = Collections.unmodifiableSet(EnumSet.allOf(EnumFacing.class));
    private static final boolean inDev = System.getProperty("INDEV") != null;
    private static final boolean includeWorldHash = System.getProperty("ic2.debug.includeworldhash") != null;
    private static final Map<Class<?>, Boolean> checkedClasses = new IdentityHashMap();

    public static int roundToNegInf(float x) {
        int ret = (int)x;
        if ((float)ret > x) {
            --ret;
        }
        return ret;
    }

    public static int roundToNegInf(double x) {
        int ret = (int)x;
        if ((double)ret > x) {
            --ret;
        }
        return ret;
    }

    public static int saturatedCast(double x) {
        if (x > 2.147483647E9) {
            return Integer.MAX_VALUE;
        }
        if (x < -2.147483648E9) {
            return Integer.MIN_VALUE;
        }
        return (int)x;
    }

    public static int limit(int value, int min, int max) {
        if (value <= min) {
            return min;
        }
        if (value >= max) {
            return max;
        }
        return value;
    }

    public static float limit(float value, float min, float max) {
        if (Float.isNaN(value) || value <= min) {
            return min;
        }
        if (value >= max) {
            return max;
        }
        return value;
    }

    public static double limit(double value, double min, double max) {
        if (Double.isNaN(value) || value <= min) {
            return min;
        }
        if (value >= max) {
            return max;
        }
        return value;
    }

    public static double map(double value, double srcMax, double dstMax) {
        if (value < 0.0 || Double.isNaN(value)) {
            value = 0.0;
        }
        if (value > srcMax) {
            value = srcMax;
        }
        return value / srcMax * dstMax;
    }

    public static double lerp(double start, double end, double fraction) {
        assert (fraction >= 0.0 && fraction <= 1.0);
        return start + (end - start) * fraction;
    }

    public static float lerp(float start, float end, float fraction) {
        assert (fraction >= 0.0f && fraction <= 1.0f);
        return start + (end - start) * fraction;
    }

    public static int square(int x) {
        return x * x;
    }

    public static float square(float x) {
        return x * x;
    }

    public static double square(double x) {
        return x * x;
    }

    public static boolean isSimilar(float a, float b) {
        return Math.abs(a - b) < 1.0E-5f;
    }

    public static boolean isSimilar(double a, double b) {
        return Math.abs(a - b) < 1.0E-5;
    }

    public static int countInArray(Object[] oa, Class<?> ... clsz) {
        int ret = 0;
        for (Object o : oa) {
            for (Class<?> cls : clsz) {
                if (!cls.isAssignableFrom(o.getClass())) continue;
                ++ret;
            }
        }
        return ret;
    }

    public static int countInArray(Object[] oa, Class<?> cls) {
        int ret = 0;
        for (Object o : oa) {
            if (!cls.isAssignableFrom(o.getClass())) continue;
            ++ret;
        }
        return ret;
    }

    public static boolean inDev() {
        return inDev;
    }

    public static boolean hasAssertions() {
        boolean ret = false;
        if (!$assertionsDisabled) {
            ret = true;
            if (!true) {
                throw new AssertionError();
            }
        }
        return ret;
    }

    public static boolean matchesOD(ItemStack stack, Object match) {
        if (match instanceof ItemStack) {
            return !StackUtil.isEmpty(stack) && stack.func_77969_a((ItemStack)match);
        }
        if (match instanceof String) {
            if (StackUtil.isEmpty(stack)) {
                return false;
            }
            for (int oreId : OreDictionary.getOreIDs((ItemStack)stack)) {
                if (!OreDictionary.getOreName((int)oreId).equals(match)) continue;
                return true;
            }
            return false;
        }
        return stack == match;
    }

    public static String toString(TileEntity te) {
        if (te == null) {
            return "null";
        }
        return Util.toString(te, (IBlockAccess)te.func_145831_w(), te.func_174877_v());
    }

    public static String toString(Object o, IBlockAccess world, BlockPos pos) {
        return Util.toString(o, world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
    }

    public static String toString(Object o, IBlockAccess world, int x, int y, int z) {
        StringBuilder ret = new StringBuilder(64);
        if (o == null) {
            ret.append("null");
        } else {
            ret.append(o.getClass().getName());
            ret.append('@');
            ret.append(Integer.toHexString(System.identityHashCode(o)));
        }
        ret.append(" (");
        ret.append(Util.formatPosition(world, x, y, z));
        ret.append(")");
        return ret.toString();
    }

    public static String formatPosition(TileEntity te) {
        return Util.formatPosition((IBlockAccess)te.func_145831_w(), te.func_174877_v());
    }

    public static String formatPosition(IBlockAccess world, BlockPos pos) {
        return Util.formatPosition(world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
    }

    public static String formatPosition(IBlockAccess world, int x, int y, int z) {
        int dimId = world instanceof World && ((World)world).field_73011_w != null ? ((World)world).field_73011_w.getDimension() : Integer.MIN_VALUE;
        if (!includeWorldHash) {
            return Util.formatPosition(dimId, x, y, z);
        }
        return String.format("dim %d (@%x): %d/%d/%d", dimId, System.identityHashCode(world), x, y, z);
    }

    public static String formatPosition(int dimId, int x, int y, int z) {
        return "dim " + dimId + ": " + x + "/" + y + "/" + z;
    }

    public static String formatPosition(BlockPos pos) {
        return Util.formatPosition(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
    }

    public static String formatPosition(int x, int y, int z) {
        return x + "/" + y + "/" + z;
    }

    public static String toSiString(double value, int digits) {
        int dVal;
        String si;
        double mul;
        if (value == 0.0) {
            return "0 ";
        }
        if (Double.isNaN(value)) {
            return "NaN ";
        }
        String ret = "";
        if (value < 0.0) {
            ret = "-";
            value = -value;
        }
        if (Double.isInfinite(value)) {
            return ret + "\u221e ";
        }
        double log = Math.log10(value);
        if (log >= 0.0) {
            int reduce = (int)Math.floor(log / 3.0);
            mul = 1.0 / Math.pow(10.0, reduce * 3);
            switch (reduce) {
                case 0: {
                    si = "";
                    break;
                }
                case 1: {
                    si = "k";
                    break;
                }
                case 2: {
                    si = "M";
                    break;
                }
                case 3: {
                    si = "G";
                    break;
                }
                case 4: {
                    si = "T";
                    break;
                }
                case 5: {
                    si = "P";
                    break;
                }
                case 6: {
                    si = "E";
                    break;
                }
                case 7: {
                    si = "Z";
                    break;
                }
                case 8: {
                    si = "Y";
                    break;
                }
                default: {
                    si = "E" + reduce * 3;
                    break;
                }
            }
        } else {
            int expand = (int)Math.ceil(-log / 3.0);
            mul = Math.pow(10.0, expand * 3);
            switch (expand) {
                case 0: {
                    si = "";
                    break;
                }
                case 1: {
                    si = "m";
                    break;
                }
                case 2: {
                    si = "\u00b5";
                    break;
                }
                case 3: {
                    si = "n";
                    break;
                }
                case 4: {
                    si = "p";
                    break;
                }
                case 5: {
                    si = "f";
                    break;
                }
                case 6: {
                    si = "a";
                    break;
                }
                case 7: {
                    si = "z";
                    break;
                }
                case 8: {
                    si = "y";
                    break;
                }
                default: {
                    si = "E-" + expand * 3;
                }
            }
        }
        int iVal = (int)Math.floor(value *= mul);
        value -= (double)iVal;
        int iDigits = 1;
        if (iVal > 0) {
            iDigits = (int)((double)iDigits + Math.floor(Math.log10(iVal)));
        }
        if ((double)(dVal = (int)Math.round(value * (mul = Math.pow(10.0, digits - iDigits)))) >= mul) {
            dVal = (int)((double)dVal - mul);
            iDigits = 1;
            if (++iVal > 0) {
                iDigits = (int)((double)iDigits + Math.floor(Math.log10(iVal)));
            }
        }
        ret = ret + Integer.toString(iVal);
        if (digits > iDigits && dVal != 0) {
            ret = ret + String.format(".%0" + (digits - iDigits) + "d", dVal);
        }
        ret = ret.replaceFirst("(\\.\\d*?)0+$", "$1");
        return ret + " " + si;
    }

    public static void exit(int status) {
        Method exit = null;
        try {
            exit = Class.forName("java.lang.Shutdown").getDeclaredMethod("exit", Integer.TYPE);
            exit.setAccessible(true);
        }
        catch (Exception e) {
            IC2.log.warn(LogCategory.General, e, "Method lookup failed.");
            try {
                Field security = System.class.getDeclaredField("security");
                security.setAccessible(true);
                security.set(null, null);
                exit = System.class.getMethod("exit", Integer.TYPE);
            }
            catch (Exception f) {
                throw new Error(f);
            }
        }
        try {
            exit.invoke(null, status);
        }
        catch (Exception e) {
            throw new Error(e);
        }
    }

    public static Vector3 getEyePosition(Entity entity) {
        return new Vector3(entity.field_70165_t, entity.field_70163_u + (double)entity.func_70047_e(), entity.field_70161_v);
    }

    public static Vector3 getLook(Entity entity) {
        return new Vector3(entity.func_70040_Z());
    }

    public static Vector3 getLookScaled(Entity entity) {
        return Util.getLook(entity).scale(Util.getReachDistance(entity));
    }

    public static double getReachDistance(Entity entity) {
        if (entity instanceof EntityPlayerMP) {
            return ((EntityPlayerMP)entity).field_71134_c.getBlockReachDistance();
        }
        return 5.0;
    }

    public static RayTraceResult traceBlocks(EntityPlayer player, boolean liquid) {
        return Util.traceBlocks(player, liquid, !liquid, false);
    }

    public static RayTraceResult traceBlocks(EntityPlayer player, boolean liquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
        Vector3 start = Util.getEyePosition((Entity)player);
        Vector3 end = Util.getLookScaled((Entity)player).add(start);
        return player.func_130014_f_().func_147447_a(start.toVec3(), end.toVec3(), liquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
    }

    public static RayTraceResult traceEntities(EntityPlayer player, boolean alwaysCollide) {
        Vector3 start = Util.getEyePosition((Entity)player);
        return Util.traceEntities(player.func_130014_f_(), start.toVec3(), Util.getLookScaled((Entity)player).add(start).toVec3(), (Entity)player, alwaysCollide);
    }

    public static RayTraceResult traceEntities(EntityPlayer player, Vec3d end, boolean alwaysCollide) {
        return Util.traceEntities(player.func_130014_f_(), Util.getEyePosition((Entity)player).toVec3(), end, (Entity)player, alwaysCollide);
    }

    public static RayTraceResult traceEntities(World world, Vec3d start, Vec3d end, Entity exclude, boolean alwaysCollide) {
        AxisAlignedBB aabb = new AxisAlignedBB(Math.min(start.field_72450_a, end.field_72450_a), Math.min(start.field_72448_b, end.field_72448_b), Math.min(start.field_72449_c, end.field_72449_c), Math.max(start.field_72450_a, end.field_72450_a), Math.max(start.field_72448_b, end.field_72448_b), Math.max(start.field_72449_c, end.field_72449_c));
        List entities = world.func_72839_b(exclude, aabb);
        RayTraceResult closest = null;
        double minDist = Double.POSITIVE_INFINITY;
        for (Entity entity : entities) {
            double distance;
            RayTraceResult pos;
            if (!alwaysCollide && !entity.func_70067_L() || (pos = entity.func_174813_aQ().func_72327_a(start, end)) == null || !((distance = start.func_72436_e(pos.field_72307_f)) < minDist)) continue;
            pos.field_72308_g = entity;
            pos.field_72313_a = RayTraceResult.Type.ENTITY;
            minDist = distance;
            closest = pos;
        }
        return closest;
    }

    public static boolean isFakePlayer(EntityPlayer entity, boolean fuzzy) {
        if (entity == null) {
            return false;
        }
        if (!(entity instanceof EntityPlayerMP)) {
            return true;
        }
        if (fuzzy) {
            return entity instanceof FakePlayer;
        }
        return entity.getClass() != EntityPlayerMP.class;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static World getWorld(IBlockAccess world) {
        Field field;
        if (world == null) {
            return null;
        }
        if (world instanceof World) {
            return (World)world;
        }
        Class<?> cls = world.getClass();
        Map<Class<? extends IBlockAccess>, Field> map = worldFieldCache;
        synchronized (map) {
            field = worldFieldCache.get(cls);
            if (field == null && !worldFieldCache.containsKey(cls)) {
                field = ReflectionUtil.getFieldRecursive(world.getClass(), World.class, false);
                worldFieldCache.put(cls, field);
            }
        }
        if (field != null) {
            try {
                return (World)field.get(world);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static Chunk getLoadedChunk(World world, int chunkX, int chunkZ) {
        Chunk chunk = null;
        if (world.func_72863_F() instanceof ChunkProviderServer) {
            ChunkProviderServer cps = (ChunkProviderServer)world.func_72863_F();
            try {
                chunk = (Chunk)cps.field_73244_f.get(ChunkPos.func_77272_a((int)chunkX, (int)chunkZ));
            }
            catch (NoSuchFieldError e) {
                if (cps.func_73149_a(chunkX, chunkZ)) {
                    chunk = cps.func_186025_d(chunkX, chunkZ);
                }
            }
        } else {
            chunk = world.func_72964_e(chunkX, chunkZ);
        }
        if (chunk instanceof EmptyChunk) {
            return null;
        }
        return chunk;
    }

    public static boolean checkMcCoordBounds(int x, int y, int z) {
        return Util.checkMcCoordBounds(x, z) && y >= 0 && y < 256;
    }

    public static boolean checkMcCoordBounds(int x, int z) {
        return x >= -30000000 && z >= -30000000 && x < 30000000 && z < 30000000;
    }

    public static boolean checkInterfaces(Class<?> cls) {
        Boolean cached = checkedClasses.get(cls);
        if (cached != null) {
            return cached;
        }
        Set<Class> interfaces = Collections.newSetFromMap(new IdentityHashMap());
        Class<?> c = cls;
        do {
            for (Class<?> i : c.getInterfaces()) {
                interfaces.add(i);
            }
        } while ((c = c.getSuperclass()) != null);
        boolean result = true;
        for (Class iface : interfaces) {
            for (Method method : iface.getMethods()) {
                boolean found = false;
                c = cls;
                do {
                    try {
                        Method match = c.getDeclaredMethod(method.getName(), method.getParameterTypes());
                        if (!method.getReturnType().isAssignableFrom(match.getReturnType())) continue;
                        found = true;
                        break;
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        // empty catch block
                    }
                } while ((c = c.getSuperclass()) != null);
                if (found) continue;
                IC2.log.info(LogCategory.General, "Can't find method %s.%s in %s.", method.getDeclaringClass().getName(), method.getName(), cls.getName());
                result = false;
            }
        }
        checkedClasses.put(cls, result);
        return result;
    }

    public static IBlockState getBlockState(IBlockAccess world, BlockPos pos) {
        IBlockState state = world.func_180495_p(pos);
        return state.func_185899_b(world, pos);
    }

    public static Block getBlock(String name) {
        if (name == null) {
            throw new NullPointerException("null name");
        }
        return Util.getBlock(new ResourceLocation(name));
    }

    public static Block getBlock(ResourceLocation loc) {
        Block ret = (Block)Block.field_149771_c.func_82594_a((Object)loc);
        if (ret != Blocks.field_150350_a) {
            return ret;
        }
        if (loc.func_110624_b().equals("minecraft") && loc.func_110623_a().equals("air")) {
            return ret;
        }
        return null;
    }

    public static ResourceLocation getName(Block block) {
        return (ResourceLocation)Block.field_149771_c.func_177774_c((Object)block);
    }

    public static Item getItem(String name) {
        if (name == null) {
            throw new NullPointerException("null name");
        }
        return Util.getItem(new ResourceLocation(name));
    }

    public static Item getItem(ResourceLocation loc) {
        return (Item)Item.field_150901_e.func_82594_a((Object)loc);
    }

    public static ResourceLocation getName(Item item) {
        return (ResourceLocation)Item.field_150901_e.func_177774_c((Object)item);
    }

    public static boolean harvestBlock(World world, BlockPos pos) {
        if (world.field_72995_K) {
            return false;
        }
        IBlockState state = world.func_180495_p(pos);
        Block block = state.func_177230_c();
        TileEntity te = world.func_175625_s(pos);
        EntityPlayer player = Ic2Player.get(world);
        boolean canHarvest = block.canHarvestBlock((IBlockAccess)world, pos, player);
        block.func_176208_a(world, pos, state, player);
        boolean removed = block.removedByPlayer(state, world, pos, player, canHarvest);
        if (canHarvest && removed) {
            block.func_180657_a(world, player, pos, state, te, new ItemStack(Items.field_151046_w));
        }
        return removed;
    }
}

