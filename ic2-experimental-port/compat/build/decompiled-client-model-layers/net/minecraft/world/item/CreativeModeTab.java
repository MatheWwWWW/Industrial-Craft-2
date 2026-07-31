/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.Blocks;

public abstract class CreativeModeTab {
    public static final CreativeModeTab[] f_40748_ = new CreativeModeTab[12];
    public static final CreativeModeTab f_40749_ = new CreativeModeTab(0, "buildingBlocks"){

        @Override
        public ItemStack m_6976_() {
            return new ItemStack(Blocks.f_50076_);
        }
    }.m_40784_("building_blocks");
    public static final CreativeModeTab f_40750_ = new CreativeModeTab(1, "decorations"){

        @Override
        public ItemStack m_6976_() {
            return new ItemStack(Blocks.f_50358_);
        }
    };
    public static final CreativeModeTab f_40751_ = new CreativeModeTab(2, "redstone"){

        @Override
        public ItemStack m_6976_() {
            return new ItemStack(Items.f_42451_);
        }
    };
    public static final CreativeModeTab f_40752_ = new CreativeModeTab(3, "transportation"){

        @Override
        public ItemStack m_6976_() {
            return new ItemStack(Blocks.f_50030_);
        }
    };
    public static final CreativeModeTab f_40753_ = new CreativeModeTab(6, "misc"){

        @Override
        public ItemStack m_6976_() {
            return new ItemStack(Items.f_42448_);
        }
    };
    public static final CreativeModeTab f_40754_ = new CreativeModeTab(5, "search"){

        @Override
        public ItemStack m_6976_() {
            return new ItemStack(Items.f_42522_);
        }
    }.m_40779_("item_search.png");
    public static final CreativeModeTab f_40755_ = new CreativeModeTab(7, "food"){

        @Override
        public ItemStack m_6976_() {
            return new ItemStack(Items.f_42410_);
        }
    };
    public static final CreativeModeTab f_40756_ = new CreativeModeTab(8, "tools"){

        @Override
        public ItemStack m_6976_() {
            return new ItemStack(Items.f_42386_);
        }
    }.m_40781_(EnchantmentCategory.VANISHABLE, EnchantmentCategory.DIGGER, EnchantmentCategory.FISHING_ROD, EnchantmentCategory.BREAKABLE);
    public static final CreativeModeTab f_40757_ = new CreativeModeTab(9, "combat"){

        @Override
        public ItemStack m_6976_() {
            return new ItemStack(Items.f_42430_);
        }
    }.m_40781_(EnchantmentCategory.VANISHABLE, EnchantmentCategory.ARMOR, EnchantmentCategory.ARMOR_FEET, EnchantmentCategory.ARMOR_HEAD, EnchantmentCategory.ARMOR_LEGS, EnchantmentCategory.ARMOR_CHEST, EnchantmentCategory.BOW, EnchantmentCategory.WEAPON, EnchantmentCategory.WEARABLE, EnchantmentCategory.BREAKABLE, EnchantmentCategory.TRIDENT, EnchantmentCategory.CROSSBOW);
    public static final CreativeModeTab f_40758_ = new CreativeModeTab(10, "brewing"){

        @Override
        public ItemStack m_6976_() {
            return PotionUtils.m_43549_(new ItemStack(Items.f_42589_), Potions.f_43599_);
        }
    };
    public static final CreativeModeTab f_40759_ = f_40753_;
    public static final CreativeModeTab f_40760_ = new CreativeModeTab(4, "hotbar"){

        @Override
        public ItemStack m_6976_() {
            return new ItemStack(Blocks.f_50078_);
        }

        @Override
        public void m_6151_(NonNullList<ItemStack> p_40820_) {
            throw new RuntimeException("Implement exception client-side.");
        }

        @Override
        public boolean m_6563_() {
            return true;
        }
    };
    public static final CreativeModeTab f_40761_ = new CreativeModeTab(11, "inventory"){

        @Override
        public ItemStack m_6976_() {
            return new ItemStack(Blocks.f_50087_);
        }
    }.m_40779_("inventory.png").m_40792_().m_40790_();
    private final int f_40762_;
    private final String f_40763_;
    private final Component f_40764_;
    private String f_40765_;
    private String f_40766_ = "items.png";
    private boolean f_40767_ = true;
    private boolean f_40768_ = true;
    private EnchantmentCategory[] f_40769_ = new EnchantmentCategory[0];
    private ItemStack f_40770_;

    public CreativeModeTab(int p_40773_, String p_40774_) {
        this.f_40762_ = p_40773_;
        this.f_40763_ = p_40774_;
        this.f_40764_ = Component.m_237115_("itemGroup." + p_40774_);
        this.f_40770_ = ItemStack.f_41583_;
        CreativeModeTab.f_40748_[p_40773_] = this;
    }

    public int m_40775_() {
        return this.f_40762_;
    }

    public String m_40783_() {
        return this.f_40765_ == null ? this.f_40763_ : this.f_40765_;
    }

    public Component m_40786_() {
        return this.f_40764_;
    }

    public ItemStack m_40787_() {
        if (this.f_40770_.m_41619_()) {
            this.f_40770_ = this.m_6976_();
        }
        return this.f_40770_;
    }

    public abstract ItemStack m_6976_();

    public String m_40788_() {
        return this.f_40766_;
    }

    public CreativeModeTab m_40779_(String p_40780_) {
        this.f_40766_ = p_40780_;
        return this;
    }

    public CreativeModeTab m_40784_(String p_40785_) {
        this.f_40765_ = p_40785_;
        return this;
    }

    public boolean m_40789_() {
        return this.f_40768_;
    }

    public CreativeModeTab m_40790_() {
        this.f_40768_ = false;
        return this;
    }

    public boolean m_40791_() {
        return this.f_40767_;
    }

    public CreativeModeTab m_40792_() {
        this.f_40767_ = false;
        return this;
    }

    public int m_40793_() {
        return this.f_40762_ % 6;
    }

    public boolean m_40794_() {
        return this.f_40762_ < 6;
    }

    public boolean m_6563_() {
        return this.m_40793_() == 5;
    }

    public EnchantmentCategory[] m_40795_() {
        return this.f_40769_;
    }

    public CreativeModeTab m_40781_(EnchantmentCategory ... p_40782_) {
        this.f_40769_ = p_40782_;
        return this;
    }

    public boolean m_40776_(@Nullable EnchantmentCategory p_40777_) {
        if (p_40777_ != null) {
            for (EnchantmentCategory $$1 : this.f_40769_) {
                if ($$1 != p_40777_) continue;
                return true;
            }
        }
        return false;
    }

    public void m_6151_(NonNullList<ItemStack> p_40778_) {
        for (Item $$1 : Registry.f_122827_) {
            $$1.m_6787_(this, p_40778_);
        }
    }
}

