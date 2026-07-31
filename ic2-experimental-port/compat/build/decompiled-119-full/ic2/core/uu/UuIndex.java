/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.uu;

import ic2.core.IC2;
import ic2.core.init.MainConfig;
import ic2.core.util.Config;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import ic2.core.uu.ILateRecipeResolver;
import ic2.core.uu.IRecipeResolver;
import ic2.core.uu.UuGraph;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.item.ItemStack;

public class UuIndex {
    public static final UuIndex instance = new UuIndex();
    protected final List<IRecipeResolver> resolvers = new ArrayList<IRecipeResolver>();
    protected final List<ILateRecipeResolver> lateResolvers = new ArrayList<ILateRecipeResolver>();

    private UuIndex() {
    }

    public void addResolver(IRecipeResolver iRecipeResolver) {
        this.resolvers.add(iRecipeResolver);
    }

    public void addResolver(ILateRecipeResolver iLateRecipeResolver) {
        this.lateResolvers.add(iLateRecipeResolver);
    }

    public void add(ItemStack itemStack, double d) {
        if (itemStack == null || itemStack.m_41720_() == null) {
            throw new NullPointerException("invalid itemstack to add");
        }
        UuGraph.set(itemStack, d);
    }

    public double get(ItemStack itemStack) {
        return UuGraph.get(itemStack);
    }

    public double getInBuckets(ItemStack itemStack) {
        double d = UuGraph.get(itemStack);
        return d *= 1.0E-5;
    }

    public void init() {
    }

    public void refresh(boolean bl) {
        ItemStack itemStack;
        Config.Value value;
        Config config = MainConfig.get().getSub("balance/uu-values/world scan");
        if (config == null) {
            IC2.log.info(LogCategory.Uu, "Loading predefined UU world scan values, run /ic2 uu-world-scan <small|medium|large> to calibrate them for your world.");
            config = new Config("uu scan values");
            try {
                config.load(IC2.class.getResourceAsStream("/assets/ic2/config/uu_scan_values.ini"));
            }
            catch (Exception exception) {
                throw new RuntimeException("Error loading base config", exception);
            }
        } else {
            IC2.log.debug(LogCategory.Uu, "Loading UU world scan values from the user config.");
        }
        Iterator<Config.Value> iterator = config.valueIterator();
        while (iterator.hasNext()) {
            value = iterator.next();
            try {
                itemStack = ConfigUtil.asStack(value.name);
            }
            catch (ParseException parseException) {
                throw new Config.ParseException("invalid key", value, parseException);
            }
            if (itemStack == null) {
                IC2.log.warn(LogCategory.Uu, "UU world-scan config: Can't find ItemStack for %s, ignoring the entry in line %d.", value.name, value.getLine());
                continue;
            }
            this.add(itemStack, value.getDouble());
        }
        iterator = MainConfig.get().getSub("balance/uu-values/predefined").valueIterator();
        while (iterator.hasNext()) {
            value = iterator.next();
            try {
                itemStack = ConfigUtil.asStack(value.name);
            }
            catch (ParseException parseException) {
                throw new Config.ParseException("invalid key", value, parseException);
            }
            if (itemStack == null) {
                IC2.log.warn(LogCategory.Uu, "UU predefined config: Can't find ItemStack for %s, ignoring the entry in line %d.", value.name, value.getLine());
                continue;
            }
            this.add(itemStack, value.getDouble());
        }
        UuGraph.build(bl);
    }
}

