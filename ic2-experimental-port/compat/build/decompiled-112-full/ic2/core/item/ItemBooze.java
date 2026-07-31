/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.ItemMeshDefinition
 *  net.minecraft.client.renderer.block.model.ModelBakery
 *  net.minecraft.client.renderer.block.model.ModelResourceLocation
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.MobEffects
 *  net.minecraft.item.EnumAction
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.ActionResult
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 *  net.minecraftforge.client.model.ModelLoader
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item;

import ic2.core.block.state.IIdProvider;
import ic2.core.item.ItemIC2;
import ic2.core.item.ItemMug;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBooze
extends ItemIC2 {
    public String[] solidRatio = new String[]{"Watery ", "Clear ", "Lite ", "", "Strong ", "Thick ", "Stodge ", "X"};
    public String[] hopsRatio = new String[]{"Soup ", "Alcfree ", "White ", "", "Dark ", "Full ", "Black ", "X"};
    public String[] timeRatioNames = new String[]{"Brew", "Youngster", "Beer", "Ale", "Dragonblood", "Black Stuff"};
    public int[] baseDuration = new int[]{300, 600, 900, 1200, 1600, 2000, 2400};
    public float[] baseIntensity = new float[]{0.4f, 0.75f, 1.0f, 1.5f, 2.0f};
    public static float rumStackability = 2.0f;
    public static int rumDuration = 600;

    public ItemBooze() {
        super(ItemName.booze_mug);
        this.func_77625_d(1);
        this.func_77637_a(null);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerModels(final ItemName name) {
        ModelLoader.setCustomMeshDefinition((Item)this, (ItemMeshDefinition)new ItemMeshDefinition(){

            public ModelResourceLocation func_178113_a(ItemStack stack) {
                BoozeMugType mugType;
                int meta = stack.func_77960_j();
                int type = ItemBooze.getTypeOfValue(meta);
                if (type == 1) {
                    int timeRatio = Math.min(ItemBooze.getTimeRatioOfBeerValue(meta), ItemBooze.this.timeRatioNames.length - 1);
                    mugType = BoozeMugType.values[timeRatio];
                } else if (type == 2) {
                    mugType = BoozeMugType.rum;
                } else {
                    return null;
                }
                return ItemIC2.getModelLocation(name, mugType.getName());
            }
        });
        for (BoozeMugType type : BoozeMugType.values) {
            ModelBakery.registerItemVariants((Item)this, (ResourceLocation[])new ResourceLocation[]{ItemBooze.getModelLocation(name, type.getName())});
        }
    }

    @Override
    public String func_77653_i(ItemStack itemstack) {
        int meta = itemstack.func_77952_i();
        int type = ItemBooze.getTypeOfValue(meta);
        if (type == 1) {
            int timeRatio = Math.min(ItemBooze.getTimeRatioOfBeerValue(meta), this.timeRatioNames.length - 1);
            if (timeRatio == this.timeRatioNames.length - 1) {
                return this.timeRatioNames[timeRatio];
            }
            return this.solidRatio[ItemBooze.getSolidRatioOfBeerValue(meta)] + this.hopsRatio[ItemBooze.getHopsRatioOfBeerValue(meta)] + this.timeRatioNames[timeRatio];
        }
        if (type == 2) {
            return "Rum";
        }
        return "Zero";
    }

    public ItemStack func_77654_b(ItemStack stack, World world, EntityLivingBase living) {
        int meta = stack.func_77952_i();
        int type = ItemBooze.getTypeOfValue(meta);
        if (type == 0) {
            return ItemName.mug.getItemStack(ItemMug.MugType.empty);
        }
        if (type == 1) {
            if (ItemBooze.getTimeRatioOfBeerValue(meta) == 5) {
                return this.drinkBlackStuff(living);
            }
            int solidRatio = ItemBooze.getSolidRatioOfBeerValue(meta);
            int alc = ItemBooze.getHopsRatioOfBeerValue(meta);
            int duration = this.baseDuration[solidRatio];
            float intensity = this.baseIntensity[ItemBooze.getTimeRatioOfBeerValue(meta)];
            if (living instanceof EntityPlayer) {
                ((EntityPlayer)living).func_71024_bL().func_75122_a(6 - alc, (float)solidRatio * 0.15f);
            }
            int max = (int)(intensity * ((float)alc * 0.5f));
            PotionEffect slow = living.func_70660_b(MobEffects.field_76419_f);
            int level = -1;
            if (slow != null) {
                level = slow.func_76458_c();
            }
            this.amplifyEffect(living, MobEffects.field_76419_f, max, intensity, duration);
            if (level > -1) {
                this.amplifyEffect(living, MobEffects.field_76420_g, max, intensity, duration);
                if (level > 0) {
                    this.amplifyEffect(living, MobEffects.field_76421_d, max / 2, intensity, duration);
                    if (level > 1) {
                        this.amplifyEffect(living, MobEffects.field_76429_m, max - 1, intensity, duration);
                        if (level > 2) {
                            this.amplifyEffect(living, MobEffects.field_76431_k, 0, intensity, duration);
                            if (level > 3) {
                                living.func_70690_d(new PotionEffect(MobEffects.field_76433_i, 1, living.func_130014_f_().field_73012_v.nextInt(3)));
                            }
                        }
                    }
                }
            }
        }
        if (type == 2) {
            if (ItemBooze.getProgressOfRumValue(meta) < 100) {
                this.drinkBlackStuff(living);
            } else {
                this.amplifyEffect(living, MobEffects.field_76426_n, 0, rumStackability, rumDuration);
                PotionEffect def = living.func_70660_b(MobEffects.field_76429_m);
                int level = -1;
                if (def != null) {
                    level = def.func_76458_c();
                }
                this.amplifyEffect(living, MobEffects.field_76429_m, 2, rumStackability, rumDuration);
                if (level >= 0) {
                    this.amplifyEffect(living, MobEffects.field_76440_q, 0, rumStackability, rumDuration);
                }
                if (level >= 1) {
                    this.amplifyEffect(living, MobEffects.field_76431_k, 0, rumStackability, rumDuration);
                }
            }
        }
        return ItemName.mug.getItemStack(ItemMug.MugType.empty);
    }

    public void amplifyEffect(EntityLivingBase living, Potion potion, int max, float intensity, int duration) {
        PotionEffect eff = living.func_70660_b(potion);
        if (eff == null) {
            living.func_70690_d(new PotionEffect(potion, duration, 0));
        } else {
            int currentDuration = eff.func_76459_b();
            int maxnewdur = (int)((float)duration * (1.0f + intensity * 2.0f) - (float)currentDuration) / 2;
            if (maxnewdur < 0) {
                maxnewdur = 0;
            }
            if (maxnewdur < duration) {
                duration = maxnewdur;
            }
            currentDuration += duration;
            int newamp = eff.func_76458_c();
            if (newamp < max) {
                ++newamp;
            }
            living.func_70690_d(new PotionEffect(potion, currentDuration, newamp));
        }
    }

    public ItemStack drinkBlackStuff(EntityLivingBase living) {
        switch (living.func_130014_f_().field_73012_v.nextInt(6)) {
            case 1: {
                living.func_70690_d(new PotionEffect(MobEffects.field_76431_k, 1200, 0));
                break;
            }
            case 2: {
                living.func_70690_d(new PotionEffect(MobEffects.field_76440_q, 2400, 0));
                break;
            }
            case 3: {
                living.func_70690_d(new PotionEffect(MobEffects.field_76436_u, 2400, 0));
                break;
            }
            case 4: {
                living.func_70690_d(new PotionEffect(MobEffects.field_76436_u, 200, 2));
                break;
            }
            case 5: {
                living.func_70690_d(new PotionEffect(MobEffects.field_76433_i, 1, living.func_130014_f_().field_73012_v.nextInt(4)));
            }
        }
        return ItemName.mug.getItemStack(ItemMug.MugType.empty);
    }

    public int func_77626_a(ItemStack itemstack) {
        return 32;
    }

    public EnumAction func_77661_b(ItemStack itemstack) {
        return EnumAction.DRINK;
    }

    public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
        player.func_184598_c(hand);
        return new ActionResult(EnumActionResult.SUCCESS, (Object)StackUtil.get(player, hand));
    }

    public static int getTypeOfValue(int value) {
        return ItemBooze.unpackValue(value, 0, 2);
    }

    public static int getAmountOfValue(int value) {
        if (ItemBooze.getTypeOfValue(value) == 0) {
            return 0;
        }
        return ItemBooze.unpackValue(value, 2, 5) + 1;
    }

    public static int getSolidRatioOfBeerValue(int value) {
        return ItemBooze.unpackValue(value, 7, 3);
    }

    public static int getHopsRatioOfBeerValue(int value) {
        return ItemBooze.unpackValue(value, 10, 3);
    }

    public static int getTimeRatioOfBeerValue(int value) {
        return ItemBooze.unpackValue(value, 13, 3);
    }

    public static int getProgressOfRumValue(int value) {
        return ItemBooze.unpackValue(value, 7, 7);
    }

    private static int unpackValue(int value, int bitshift, int take) {
        int mask = (1 << take) - 1;
        return (value >>>= bitshift) & mask;
    }

    private static enum BoozeMugType implements IIdProvider
    {
        beer_brew,
        beer_youngster,
        beer_beer,
        beer_ale,
        beer_dragon_blood,
        beer_black_stuff,
        rum;

        public static final BoozeMugType[] values;

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public int getId() {
            throw new UnsupportedOperationException();
        }

        static {
            values = BoozeMugType.values();
        }
    }
}

