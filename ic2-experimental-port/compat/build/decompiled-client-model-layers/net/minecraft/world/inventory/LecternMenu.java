/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class LecternMenu
extends AbstractContainerMenu {
    private static final int f_150610_ = 1;
    private static final int f_150611_ = 1;
    public static final int f_150606_ = 1;
    public static final int f_150607_ = 2;
    public static final int f_150608_ = 3;
    public static final int f_150609_ = 100;
    private final Container f_39819_;
    private final ContainerData f_39820_;

    public LecternMenu(int p_39822_) {
        this(p_39822_, new SimpleContainer(1), new SimpleContainerData(1));
    }

    public LecternMenu(int p_39824_, Container p_39825_, ContainerData p_39826_) {
        super(MenuType.f_39973_, p_39824_);
        LecternMenu.m_38869_(p_39825_, 1);
        LecternMenu.m_38886_(p_39826_, 1);
        this.f_39819_ = p_39825_;
        this.f_39820_ = p_39826_;
        this.m_38897_(new Slot(p_39825_, 0, 0, 0){

            @Override
            public void m_6654_() {
                super.m_6654_();
                LecternMenu.this.m_6199_(this.f_40218_);
            }
        });
        this.m_38884_(p_39826_);
    }

    @Override
    public boolean m_6366_(Player p_39833_, int p_39834_) {
        if (p_39834_ >= 100) {
            int $$2 = p_39834_ - 100;
            this.m_7511_(0, $$2);
            return true;
        }
        switch (p_39834_) {
            case 2: {
                int $$3 = this.f_39820_.m_6413_(0);
                this.m_7511_(0, $$3 + 1);
                return true;
            }
            case 1: {
                int $$4 = this.f_39820_.m_6413_(0);
                this.m_7511_(0, $$4 - 1);
                return true;
            }
            case 3: {
                if (!p_39833_.m_36326_()) {
                    return false;
                }
                ItemStack $$5 = this.f_39819_.m_8016_(0);
                this.f_39819_.m_6596_();
                if (!p_39833_.m_150109_().m_36054_($$5)) {
                    p_39833_.m_36176_($$5, false);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack m_7648_(Player p_219987_, int p_219988_) {
        return ItemStack.f_41583_;
    }

    @Override
    public void m_7511_(int p_39828_, int p_39829_) {
        super.m_7511_(p_39828_, p_39829_);
        this.m_38946_();
    }

    @Override
    public boolean m_6875_(Player p_39831_) {
        return this.f_39819_.m_6542_(p_39831_);
    }

    public ItemStack m_39835_() {
        return this.f_39819_.m_8020_(0);
    }

    public int m_39836_() {
        return this.f_39820_.m_6413_(0);
    }
}

