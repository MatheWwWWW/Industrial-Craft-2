/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.init.Blocks
 */
package ic2.core.item.tool;

import ic2.core.item.tool.IToolClass;
import ic2.core.ref.IC2Material;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public enum ToolClass implements IToolClass
{
    Axe("axe", Material.field_151575_d, Material.field_151585_k, Material.field_151582_l),
    Pickaxe("pickaxe", Material.field_151573_f, Material.field_151574_g, Material.field_151576_e),
    Shears("shears", Blocks.field_150321_G, Blocks.field_150325_L, Blocks.field_150488_af, Blocks.field_150473_bD, Material.field_151584_j),
    Shovel("shovel", Blocks.field_150431_aC, Blocks.field_150433_aE),
    Sword("sword", Blocks.field_150321_G, Material.field_151585_k, Material.field_151582_l, Material.field_151589_v, Material.field_151584_j, Material.field_151572_C),
    Hoe(null, Blocks.field_150346_d, Blocks.field_150349_c, Blocks.field_150391_bh),
    Wrench("wrench", new Object[]{IC2Material.MACHINE, IC2Material.PIPE}),
    WireCutter("wire_cutter", new Object[]{IC2Material.CABLE}),
    Crowbar("crowbar", Blocks.field_150448_aq, Blocks.field_150408_cc, Blocks.field_150319_E, Blocks.field_150318_D);

    public final String name;
    public final Set<Object> whitelist;
    public final Set<Object> blacklist;

    private ToolClass(String name, Object ... whitelist) {
        this(name, whitelist, new Object[0]);
    }

    private ToolClass(String name, Object[] whitelist, Object[] blacklist) {
        this.name = name;
        this.whitelist = new HashSet<Object>(Arrays.asList(whitelist));
        this.blacklist = new HashSet<Object>(Arrays.asList(blacklist));
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Set<Object> getWhitelist() {
        return this.whitelist;
    }

    @Override
    public Set<Object> getBlacklist() {
        return this.blacklist;
    }
}

