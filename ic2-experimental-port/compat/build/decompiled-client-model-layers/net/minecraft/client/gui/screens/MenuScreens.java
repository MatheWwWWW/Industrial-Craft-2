/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.screens;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.BeaconScreen;
import net.minecraft.client.gui.screens.inventory.BlastFurnaceScreen;
import net.minecraft.client.gui.screens.inventory.BrewingStandScreen;
import net.minecraft.client.gui.screens.inventory.CartographyTableScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.inventory.DispenserScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.client.gui.screens.inventory.FurnaceScreen;
import net.minecraft.client.gui.screens.inventory.GrindstoneScreen;
import net.minecraft.client.gui.screens.inventory.HopperScreen;
import net.minecraft.client.gui.screens.inventory.LecternScreen;
import net.minecraft.client.gui.screens.inventory.LoomScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.client.gui.screens.inventory.ShulkerBoxScreen;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.client.gui.screens.inventory.SmokerScreen;
import net.minecraft.client.gui.screens.inventory.StonecutterScreen;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.slf4j.Logger;

public class MenuScreens {
    private static final Logger f_96195_ = LogUtils.getLogger();
    private static final Map<MenuType<?>, ScreenConstructor<?, ?>> f_96196_ = Maps.newHashMap();

    public static <T extends AbstractContainerMenu> void m_96201_(@Nullable MenuType<T> p_96202_, Minecraft p_96203_, int p_96204_, Component p_96205_) {
        if (p_96202_ == null) {
            f_96195_.warn("Trying to open invalid screen with name: {}", (Object)p_96205_.getString());
            return;
        }
        ScreenConstructor<T, ?> $$4 = MenuScreens.m_96199_(p_96202_);
        if ($$4 == null) {
            f_96195_.warn("Failed to create screen for menu type: {}", (Object)Registry.f_122863_.m_7981_(p_96202_));
            return;
        }
        $$4.m_96209_(p_96205_, p_96202_, p_96203_, p_96204_);
    }

    @Nullable
    private static <T extends AbstractContainerMenu> ScreenConstructor<T, ?> m_96199_(MenuType<T> p_96200_) {
        return f_96196_.get(p_96200_);
    }

    private static <M extends AbstractContainerMenu, U extends Screen> void m_96206_(MenuType<? extends M> p_96207_, ScreenConstructor<M, U> p_96208_) {
        ScreenConstructor<M, U> $$2 = f_96196_.put(p_96207_, p_96208_);
        if ($$2 != null) {
            throw new IllegalStateException("Duplicate registration for " + Registry.f_122863_.m_7981_(p_96207_));
        }
    }

    public static boolean m_96198_() {
        boolean $$0 = false;
        for (MenuType menuType : Registry.f_122863_) {
            if (f_96196_.containsKey(menuType)) continue;
            f_96195_.debug("Menu {} has no matching screen", (Object)Registry.f_122863_.m_7981_(menuType));
            $$0 = true;
        }
        return $$0;
    }

    static {
        MenuScreens.m_96206_(MenuType.f_39957_, ContainerScreen::new);
        MenuScreens.m_96206_(MenuType.f_39958_, ContainerScreen::new);
        MenuScreens.m_96206_(MenuType.f_39959_, ContainerScreen::new);
        MenuScreens.m_96206_(MenuType.f_39960_, ContainerScreen::new);
        MenuScreens.m_96206_(MenuType.f_39961_, ContainerScreen::new);
        MenuScreens.m_96206_(MenuType.f_39962_, ContainerScreen::new);
        MenuScreens.m_96206_(MenuType.f_39963_, DispenserScreen::new);
        MenuScreens.m_96206_(MenuType.f_39964_, AnvilScreen::new);
        MenuScreens.m_96206_(MenuType.f_39965_, BeaconScreen::new);
        MenuScreens.m_96206_(MenuType.f_39966_, BlastFurnaceScreen::new);
        MenuScreens.m_96206_(MenuType.f_39967_, BrewingStandScreen::new);
        MenuScreens.m_96206_(MenuType.f_39968_, CraftingScreen::new);
        MenuScreens.m_96206_(MenuType.f_39969_, EnchantmentScreen::new);
        MenuScreens.m_96206_(MenuType.f_39970_, FurnaceScreen::new);
        MenuScreens.m_96206_(MenuType.f_39971_, GrindstoneScreen::new);
        MenuScreens.m_96206_(MenuType.f_39972_, HopperScreen::new);
        MenuScreens.m_96206_(MenuType.f_39973_, LecternScreen::new);
        MenuScreens.m_96206_(MenuType.f_39974_, LoomScreen::new);
        MenuScreens.m_96206_(MenuType.f_39975_, MerchantScreen::new);
        MenuScreens.m_96206_(MenuType.f_39976_, ShulkerBoxScreen::new);
        MenuScreens.m_96206_(MenuType.f_39977_, SmithingScreen::new);
        MenuScreens.m_96206_(MenuType.f_39978_, SmokerScreen::new);
        MenuScreens.m_96206_(MenuType.f_39979_, CartographyTableScreen::new);
        MenuScreens.m_96206_(MenuType.f_39980_, StonecutterScreen::new);
    }

    static interface ScreenConstructor<T extends AbstractContainerMenu, U extends Screen> {
        default public void m_96209_(Component p_96210_, MenuType<T> p_96211_, Minecraft p_96212_, int p_96213_) {
            U $$4 = this.m_96214_(p_96211_.m_39985_(p_96213_, p_96212_.f_91074_.m_150109_()), p_96212_.f_91074_.m_150109_(), p_96210_);
            p_96212_.f_91074_.f_36096_ = ((MenuAccess)$$4).m_6262_();
            p_96212_.m_91152_((Screen)$$4);
        }

        public U m_96214_(T var1, Inventory var2, Component var3);
    }
}

