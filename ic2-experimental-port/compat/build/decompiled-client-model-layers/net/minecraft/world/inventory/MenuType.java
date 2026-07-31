/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.BlastFurnaceMenu;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.CartographyTableMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.inventory.LecternMenu;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.inventory.SmokerMenu;
import net.minecraft.world.inventory.StonecutterMenu;

public class MenuType<T extends AbstractContainerMenu> {
    public static final MenuType<ChestMenu> f_39957_ = MenuType.m_39988_("generic_9x1", ChestMenu::m_39234_);
    public static final MenuType<ChestMenu> f_39958_ = MenuType.m_39988_("generic_9x2", ChestMenu::m_39243_);
    public static final MenuType<ChestMenu> f_39959_ = MenuType.m_39988_("generic_9x3", ChestMenu::m_39255_);
    public static final MenuType<ChestMenu> f_39960_ = MenuType.m_39988_("generic_9x4", ChestMenu::m_39258_);
    public static final MenuType<ChestMenu> f_39961_ = MenuType.m_39988_("generic_9x5", ChestMenu::m_39262_);
    public static final MenuType<ChestMenu> f_39962_ = MenuType.m_39988_("generic_9x6", ChestMenu::m_39266_);
    public static final MenuType<DispenserMenu> f_39963_ = MenuType.m_39988_("generic_3x3", DispenserMenu::new);
    public static final MenuType<AnvilMenu> f_39964_ = MenuType.m_39988_("anvil", AnvilMenu::new);
    public static final MenuType<BeaconMenu> f_39965_ = MenuType.m_39988_("beacon", BeaconMenu::new);
    public static final MenuType<BlastFurnaceMenu> f_39966_ = MenuType.m_39988_("blast_furnace", BlastFurnaceMenu::new);
    public static final MenuType<BrewingStandMenu> f_39967_ = MenuType.m_39988_("brewing_stand", BrewingStandMenu::new);
    public static final MenuType<CraftingMenu> f_39968_ = MenuType.m_39988_("crafting", CraftingMenu::new);
    public static final MenuType<EnchantmentMenu> f_39969_ = MenuType.m_39988_("enchantment", EnchantmentMenu::new);
    public static final MenuType<FurnaceMenu> f_39970_ = MenuType.m_39988_("furnace", FurnaceMenu::new);
    public static final MenuType<GrindstoneMenu> f_39971_ = MenuType.m_39988_("grindstone", GrindstoneMenu::new);
    public static final MenuType<HopperMenu> f_39972_ = MenuType.m_39988_("hopper", HopperMenu::new);
    public static final MenuType<LecternMenu> f_39973_ = MenuType.m_39988_("lectern", (p_39992_, p_39993_) -> new LecternMenu(p_39992_));
    public static final MenuType<LoomMenu> f_39974_ = MenuType.m_39988_("loom", LoomMenu::new);
    public static final MenuType<MerchantMenu> f_39975_ = MenuType.m_39988_("merchant", MerchantMenu::new);
    public static final MenuType<ShulkerBoxMenu> f_39976_ = MenuType.m_39988_("shulker_box", ShulkerBoxMenu::new);
    public static final MenuType<SmithingMenu> f_39977_ = MenuType.m_39988_("smithing", SmithingMenu::new);
    public static final MenuType<SmokerMenu> f_39978_ = MenuType.m_39988_("smoker", SmokerMenu::new);
    public static final MenuType<CartographyTableMenu> f_39979_ = MenuType.m_39988_("cartography_table", CartographyTableMenu::new);
    public static final MenuType<StonecutterMenu> f_39980_ = MenuType.m_39988_("stonecutter", StonecutterMenu::new);
    private final MenuSupplier<T> f_39981_;

    private static <T extends AbstractContainerMenu> MenuType<T> m_39988_(String p_39989_, MenuSupplier<T> p_39990_) {
        return Registry.m_122961_(Registry.f_122863_, p_39989_, new MenuType<T>(p_39990_));
    }

    private MenuType(MenuSupplier<T> p_39984_) {
        this.f_39981_ = p_39984_;
    }

    public T m_39985_(int p_39986_, Inventory p_39987_) {
        return this.f_39981_.m_39994_(p_39986_, p_39987_);
    }

    static interface MenuSupplier<T extends AbstractContainerMenu> {
        public T m_39994_(int var1, Inventory var2);
    }
}

