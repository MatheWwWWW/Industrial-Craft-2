/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Component$Serializer
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.AreaEffectCloud
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.alchemy.PotionUtils
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.common.Tags$Items
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.templates.FluidTank
 *  net.minecraftforge.fml.loading.FMLEnvironment
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.misc.tiles;

import ic2.api.items.IDrinkContainer;
import ic2.api.items.readers.IThermometer;
import ic2.api.recipes.RecipeRegistry;
import ic2.api.recipes.registries.IPotionBrewRegistry;
import ic2.core.IC2;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.features.IDropProvider;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.misc.comparator.BaseComparator;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.special.BarrelComparator;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.inventory.filter.StackFilter;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.item.food_and_drink.IC2FoodsAndDrinks;
import ic2.core.item.food_and_drink.drinks.Beer;
import ic2.core.item.food_and_drink.drinks.Rum;
import ic2.core.item.food_and_drink.drinks.Whisky;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.Collection;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;

public class BarrelTileEntity
extends BaseTileEntity
implements ITickListener,
IClickable,
IDropProvider {
    public static final int[] POTION_DURATIONS = new int[]{1800, 1500, 1200, 960, 720, 600, 300, 150, 90, 30, 15};
    public static final int MAX_POTION_AMPLIFIERS = 20;
    public static final int FLUID_CAPACITY = 32000;
    public static final int POTION_FLUID_CAPACITY = 3000;
    private boolean hasTreeTap = false;
    public boolean opened = false;
    public int brewType = 0;
    public int fluidAmount = 0;
    public int wheatAmount = 0;
    public int hopsAmount = 0;
    public int age = 0;
    public int beerQuality = 0;
    public int alcoholLevel = 0;
    public int solidRatio = 0;
    public MobEffect potionType;

    public BarrelTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addComparator(new BarrelComparator("stage", ComparatorNames.BREW_STAGE, this, true));
        this.addComparator(new BarrelComparator("progress", ComparatorNames.BREW_STAGE_PROGRESS, this, false));
    }

    public int getStageProgress() {
        switch (this.brewType) {
            case 1: {
                return BaseComparator.value(this.age, (float)(24000.0 * Math.pow(3.0, this.beerQuality == 4 ? 6.0 : (double)this.beerQuality)), 15);
            }
            case 10: {
                return BaseComparator.value(this.age, (float)(5000.0 * Math.pow(3.0, this.beerQuality)), 15);
            }
            case 2: {
                return BaseComparator.value(this.age, this.timeNeededForRum(), 15);
            }
            case 5: {
                return BaseComparator.value(this.age, 1728000.0f, 15);
            }
        }
        return 0;
    }

    public int getStage() {
        return this.beerQuality;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.BARREL;
    }

    @Override
    public void onTick() {
        if (!(this.isEmpty() || this.hasTreeTap || this.opened)) {
            ++this.age;
            if (this.brewType == 1 && this.beerQuality < 5) {
                double d = this.age;
                double d2 = this.beerQuality == 4 ? 6.0 : (double)this.beerQuality;
                if (d >= 24000.0 * Math.pow(3.0, d2)) {
                    this.age = 0;
                    ++this.beerQuality;
                }
            } else if (this.brewType == 10 && this.beerQuality < 6) {
                if ((double)this.age >= 5000.0 * Math.pow(3.0, this.beerQuality)) {
                    this.age = 0;
                    ++this.beerQuality;
                }
            } else if (this.brewType == 5 && this.age >= this.getWhiskBrewTime() && this.beerQuality < 50) {
                this.age = 0;
                ++this.beerQuality;
            }
        }
        this.handleComparators();
    }

    public ItemStack getBarrelStack() {
        if (this.fluidAmount <= 0) {
            return new ItemStack((ItemLike)IC2Blocks.BARREL);
        }
        ItemStack stack = new ItemStack((ItemLike)IC2Blocks.BARREL);
        if (this.m_8077_()) {
            stack.m_41714_(this.m_7770_());
        }
        CompoundTag data = stack.m_41784_();
        data.m_128405_("type", this.brewType);
        switch (this.brewType) {
            case 0: {
                return new ItemStack((ItemLike)IC2Blocks.BARREL);
            }
            case 1: {
                data.m_128405_("data", Beer.createData(this.beerQuality, this.getSolidRatio(), this.getAlcoholLevel()));
                break;
            }
            case 2: {
                data.m_128405_("data", Mth.m_14045_((int)(this.age * 100 / this.timeNeededForRum()), (int)0, (int)100));
                break;
            }
            case 5: {
                data.m_128405_("data", this.opened ? this.alcoholLevel : this.calculateWhiskyYears());
                break;
            }
            case 10: {
                data.m_128359_("potion", ForgeRegistries.MOB_EFFECTS.getKey((Object)this.potionType).toString());
                data.m_128405_("data", this.calculatePotionData());
            }
        }
        data.m_128405_("fluid", this.fluidAmount);
        return stack;
    }

    protected int calculatePotionData() {
        if (this.opened) {
            return (this.solidRatio & 0xF) << 4 | this.alcoholLevel & 0xF;
        }
        int solidRatio = this.beerQuality > 0 && this.wheatAmount > 0 ? (int)Mth.m_14036_((float)((float)this.beerQuality * ((float)this.wheatAmount / 10.0f)), (float)0.0f, (float)(POTION_DURATIONS.length - 1)) : 0;
        int alcoholLevel = this.beerQuality > 0 && this.hopsAmount > 0 ? (int)Mth.m_14036_((float)((float)this.beerQuality * 0.5f * ((float)this.hopsAmount / 10.0f)), (float)0.0f, (float)2.0f) : 0;
        switch (alcoholLevel) {
            case 1: {
                solidRatio = (int)((float)solidRatio * 0.5f);
                break;
            }
            case 2: {
                solidRatio = (int)((float)solidRatio * 0.25f);
            }
        }
        return (solidRatio & 0xF) << 4 | alcoholLevel & 0xF;
    }

    public void loadItemData(ItemStack stack) {
        CompoundTag data;
        int amount;
        if (stack.m_41788_()) {
            this.setCustomName(stack.m_41786_());
        }
        if ((amount = (data = StackUtil.getNbtData(stack)).m_128451_("fluid")) <= 0) {
            return;
        }
        this.opened = true;
        this.fluidAmount = amount;
        this.brewType = data.m_128451_("type");
        switch (this.brewType) {
            case 1: {
                this.beerQuality = Beer.getTime(data.m_128451_("data"));
                this.solidRatio = Beer.getSolid(data.m_128451_("data"));
                this.alcoholLevel = Beer.getHops(data.m_128451_("data"));
                break;
            }
            case 2: {
                this.opened = false;
                this.age = (int)((double)this.timeNeededForRum() * ((double)data.m_128451_("data") / 100.0));
                break;
            }
            case 5: {
                this.alcoholLevel = data.m_128451_("data");
                break;
            }
            case 10: {
                this.potionType = (MobEffect)ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(data.m_128461_("potion")));
                this.solidRatio = data.m_128451_("data") >> 4 & 0xF;
                this.alcoholLevel = data.m_128451_("data") & 0xF;
            }
        }
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        drops.add(new ItemStack((ItemLike)IC2Blocks.SCAFFOLD_WOOD));
    }

    @Override
    public IClickable.ClickAction getRequiredActions() {
        return IClickable.ClickAction.BOTH;
    }

    public Component getBrewType() {
        return this.translate(switch (this.brewType) {
            case 0 -> "ic2.probe.barrel.status.empty.name";
            case 1 -> "ic2.probe.barrel.status.beer.name";
            case 2 -> "ic2.probe.barrel.status.rum.name";
            case 5 -> "ic2.probe.barrel.status.whisky.name";
            case 10 -> "ic2.probe.barrel.status.potion.name";
            default -> "I AM ERROR";
        });
    }

    @Override
    public boolean onLeftClick(Player player, BlockPos pos) {
        if (this.hasTreeTap) {
            this.setHasTreeTap(false);
            this.reduceFluidAmount(1000);
            StackUtil.addOrDrop(player, new ItemStack((ItemLike)IC2Items.TREETAP));
            return true;
        }
        if (!this.opened) {
            this.openBarrel();
        }
        Block.m_49840_((Level)this.m_58904_(), (BlockPos)this.m_58899_(), (ItemStack)this.getBarrelStack());
        this.f_58857_.m_46597_(this.f_58858_, IC2Blocks.SCAFFOLD_WOOD.m_49966_());
        return true;
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        ItemStack currentItem = player.m_21120_(hand);
        if (currentItem.m_41619_()) {
            if (player.m_6144_()) {
                this.setFacing(side);
                return true;
            }
            return false;
        }
        if (IThermometer.isThermometerImpl(currentItem)) {
            if (this.isSimulating()) {
                if (!player.m_7500_()) {
                    currentItem.m_220157_(1, player.m_217043_(), (ServerPlayer)player);
                }
                player.m_5661_(this.getBrewType(), false);
                switch (this.brewType) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        player.m_5661_((Component)this.translate("ic2.probe.barrel.beer.wheat.name", ChatFormatting.YELLOW, new Object[]{this.wheatAmount}), false);
                        player.m_5661_((Component)this.translate("ic2.probe.barrel.beer.hops.name", ChatFormatting.GREEN, new Object[]{this.hopsAmount}), false);
                        player.m_5661_((Component)((MutableComponent)new FluidStack((Fluid)Fluids.f_76193_, 1).getDisplayName()).m_130946_(": ").m_130946_(Formatters.EU_FORMAT.format(this.fluidAmount)).m_130946_("/").m_130946_(Formatters.EU_FORMAT.format(32000L)).m_130946_(" mB").m_130940_(ChatFormatting.AQUA), false);
                        player.m_5661_((Component)this.translate("ic2.probe.barrel.beer.quality." + this.beerQuality + ".name", new ChatFormatting[]{ChatFormatting.BLUE}), false);
                        player.m_5661_((Component)this.translate("ic2.probe.barrel.beer.alc." + this.getAlcoholLevel() + ".name", new ChatFormatting[]{ChatFormatting.GREEN}), false);
                        player.m_5661_((Component)this.translate("ic2.probe.barrel.beer.solid." + this.getSolidRatio() + ".name", new ChatFormatting[]{ChatFormatting.YELLOW}), false);
                        double max = 24000.0 * Math.pow(3.0, this.beerQuality == 4 ? 6.0 : (double)this.beerQuality);
                        player.m_5661_((Component)this.translate("ic2.probe.barrel.status.progress.name", ChatFormatting.BLUE, new Object[]{Formatters.BREWING.format((double)this.age / max * 100.0)}), false);
                        break;
                    }
                    case 2: {
                        player.m_5661_((Component)this.translate("ic2.probe.barrel.beer.sugar_cane.name", ChatFormatting.GREEN, new Object[]{this.fluidAmount / 1000}), false);
                        int max = this.timeNeededForRum();
                        player.m_5661_((Component)this.translate("ic2.probe.barrel.status.progress.name", ChatFormatting.BLUE, new Object[]{Formatters.BREWING.format((double)Math.min(this.age, max) * 100.0 / (double)max)}), false);
                        break;
                    }
                    case 5: {
                        player.m_5661_((Component)this.translate("ic2.probe.barrel.whisky.grist.name", ChatFormatting.GREEN, new Object[]{this.hopsAmount}), false);
                        player.m_5661_((Component)((MutableComponent)new FluidStack((Fluid)Fluids.f_76193_, 1).getDisplayName()).m_130946_(": ").m_130946_(Formatters.EU_FORMAT.format(this.fluidAmount)).m_130946_("/").m_130946_(Formatters.EU_FORMAT.format(32000L)).m_130946_(" mB").m_130940_(ChatFormatting.AQUA), false);
                        player.m_5661_((Component)this.translate("ic2.probe.barrel.whisky.years.name", ChatFormatting.BLUE, new Object[]{this.beerQuality}), false);
                        player.m_5661_((Component)this.translate("ic2.probe.barrel.status.progress.name", ChatFormatting.BLUE, new Object[]{Formatters.BREWING.format((double)this.age / ((double)this.getWhiskBrewTime() / 100.0))}), false);
                        break;
                    }
                    case 10: {
                        player.m_5661_((Component)this.translate("ic2.probe.barrel.beer.redstone.name", ChatFormatting.RED, new Object[]{this.wheatAmount}), false);
                        player.m_5661_((Component)this.translate("ic2.probe.barrel.beer.glowstone.name", ChatFormatting.YELLOW, new Object[]{this.hopsAmount}), false);
                        player.m_5661_((Component)((MutableComponent)new FluidStack((Fluid)Fluids.f_76193_, 1).getDisplayName()).m_130946_(": ").m_130946_(Formatters.EU_FORMAT.format(this.fluidAmount)).m_130946_("/").m_130946_(Formatters.EU_FORMAT.format(3000L)).m_130946_(" mB").m_130940_(ChatFormatting.AQUA), false);
                        player.m_5661_((Component)this.translate("ic2.probe.barrel.status.output.name", new Object[]{this.getBrewedPotion()}), false);
                        player.m_5661_((Component)this.translate("ic2.probe.barrel.potion.quality." + this.beerQuality + ".name", new ChatFormatting[]{ChatFormatting.BLUE}), false);
                        double max = 5000.0 * Math.pow(3.0, this.beerQuality);
                        player.m_5661_((Component)this.translate("ic2.probe.barrel.status.progress.name", ChatFormatting.BLUE, new Object[]{Formatters.BREWING.format((double)this.age / max * 100.0)}), false);
                        break;
                    }
                }
            }
            return true;
        }
        if (currentItem.m_150930_(Items.f_42656_)) {
            Component customName = this.m_7770_();
            if (currentItem.m_41788_() && !currentItem.m_41786_().equals(customName)) {
                customName = this.setCustomName(currentItem.m_41786_());
                return true;
            }
            if (!currentItem.m_41788_() && this.m_8077_()) {
                customName = this.setCustomName(null);
                return true;
            }
            return false;
        }
        if (currentItem.m_204117_(ItemTags.f_13167_)) {
            int useAmount = this.calculateAmount(currentItem, player, this.fluidAmount / 1000, 0);
            this.reduceFluidAmount(useAmount * 1000);
            currentItem.m_41774_(useAmount);
            return true;
        }
        if (currentItem.m_41720_() == Items.f_41902_) {
            this.reduceFluidAmount(this.getFluidCapacity());
            currentItem.m_41774_(1);
            StackUtil.addOrDrop(player, new ItemStack((ItemLike)Items.f_41903_, 1));
            return true;
        }
        if (!this.opened && !this.hasTreeTap) {
            if (currentItem.m_41720_() == Items.f_42447_ && (this.canApplyType(1) || this.canApplyType(5) || this.canApplyType(10) && this.potionType != null)) {
                if (this.fluidAmount >= this.getFluidCapacity() || this.age > 600) {
                    return false;
                }
                if (this.brewType == 0) {
                    this.brewType = 1;
                }
                this.fluidAmount += 1000;
                player.m_21008_(hand, new ItemStack((ItemLike)Items.f_42446_));
                return true;
            }
            if (currentItem.m_41720_() == IC2Items.CELL_WATER && (this.canApplyType(1) || this.canApplyType(5) || this.canApplyType(10) && this.potionType != null)) {
                ItemStack empty;
                if (this.fluidAmount >= this.getFluidCapacity() || this.age > 600) {
                    return false;
                }
                if (this.brewType == 0) {
                    this.brewType = 1;
                }
                if ((empty = new ItemStack((ItemLike)IC2Items.CELL_EMPTY, this.calculateAmount(currentItem, player, this.getFluidCapacity() / 1000, this.fluidAmount / 1000))).m_41613_() <= 0) {
                    return false;
                }
                this.fluidAmount += empty.m_41613_() * 1000;
                StackUtil.addOrDrop(player, empty);
                return true;
            }
            if (currentItem.m_41720_() == Items.f_42405_ && this.canApplyType(1)) {
                this.brewType = 1;
                this.wheatAmount += this.calculateAmount(currentItem, player, 64, this.wheatAmount);
                this.alterComposition();
                return true;
            }
            if (currentItem.m_41720_() == IC2Items.HOPS && this.canApplyType(1)) {
                this.brewType = 1;
                this.hopsAmount += this.calculateAmount(currentItem, player, 64, this.hopsAmount);
                this.alterComposition();
                return true;
            }
            if (currentItem.m_41720_() == Items.f_41909_ && this.canApplyType(2)) {
                if (this.age > 600 || this.brewType > 0) {
                    return false;
                }
                this.brewType = 2;
                this.fluidAmount += this.calculateAmount(currentItem, player, 32, this.fluidAmount / 1000) * 1000;
                return true;
            }
            if (currentItem.m_41720_() == IC2Items.WHISKY_GRIST && this.canApplyType(5)) {
                if (this.age > 600 || this.beerQuality > 0) {
                    return false;
                }
                this.brewType = 5;
                this.hopsAmount += this.calculateAmount(currentItem, player, 16, this.hopsAmount);
                return true;
            }
            if (currentItem.m_41720_() == Items.f_42588_ && this.canApplyType(10)) {
                if (this.brewType > 0) {
                    return false;
                }
                this.brewType = 10;
                if (!player.m_7500_()) {
                    currentItem.m_41774_(1);
                }
                return true;
            }
            if (this.canApplyType(10) && this.age <= 0 && RecipeRegistry.POTION_BREWING.get(this.isSimulating()).getEffect(currentItem.m_41720_(), this.potionType) != null) {
                this.potionType = RecipeRegistry.POTION_BREWING.get(this.isSimulating()).getEffect(currentItem.m_41720_(), this.potionType);
                if (!player.m_7500_()) {
                    currentItem.m_41774_(1);
                }
                return true;
            }
            if (this.canApplyType(10) && this.potionType != null && this.age <= 0 && (currentItem.m_204117_(Tags.Items.DUSTS_REDSTONE) || currentItem.m_204117_(Tags.Items.DUSTS_GLOWSTONE))) {
                if (currentItem.m_204117_(Tags.Items.DUSTS_REDSTONE)) {
                    this.wheatAmount += this.calculateAmount(currentItem, player, 20, this.wheatAmount);
                } else {
                    this.hopsAmount += this.calculateAmount(currentItem, player, 20, this.hopsAmount);
                }
                return true;
            }
            if (currentItem.m_41720_() == Items.f_42614_ && this.brewType != 0 && (this.brewType != 10 || this.potionType != null)) {
                MutableComponent text = this.string("Type: " + this.getName(this.brewType)).m_130946_("\n");
                CompoundTag info = new CompoundTag();
                info.m_128405_("type", this.brewType);
                switch (this.brewType) {
                    case 1: {
                        text.m_130946_("Wheat: " + this.wheatAmount).m_130946_("\n");
                        text.m_130946_("Hops: " + this.hopsAmount).m_130946_("\n");
                        info.m_128405_("hops", this.hopsAmount);
                        info.m_128405_("wheat", this.wheatAmount);
                        break;
                    }
                    case 5: {
                        text.m_130946_("Grist: " + this.hopsAmount).m_130946_("\n");
                        info.m_128405_("grist", this.hopsAmount);
                        break;
                    }
                    case 10: {
                        text.m_130946_("Effect: ").m_7220_(this.potionType.m_19482_()).m_130946_("\n");
                        text.m_130946_("Redstone: " + this.wheatAmount).m_130946_("\n");
                        text.m_130946_("Glowstone: " + this.hopsAmount).m_130946_("\n");
                        info.m_128359_("effect", ForgeRegistries.MOB_EFFECTS.getKey((Object)this.potionType).toString());
                        info.m_128405_("red", this.wheatAmount);
                        info.m_128405_("glow", this.hopsAmount);
                    }
                }
                text.m_130946_("Water: " + this.fluidAmount / 1000);
                info.m_128405_("water", this.fluidAmount / 1000);
                ItemStack stack = new ItemStack((ItemLike)Items.f_42615_);
                CompoundTag data = new CompoundTag();
                ListTag list = new ListTag();
                list.add((Object)StringTag.m_129297_((String)Component.Serializer.m_130703_((Component)text)));
                data.m_128365_("pages", (Tag)list);
                data.m_128365_("info", (Tag)info);
                data.m_128359_("author", player.m_7755_().getString());
                data.m_128359_("title", "Brew Recipe");
                stack.m_41751_(data);
                if (!player.m_7500_()) {
                    currentItem.m_41774_(1);
                }
                player.m_36356_(stack);
                return true;
            }
            if (currentItem.m_41720_() == Items.f_42615_) {
                CompoundTag data = StackUtil.getNbtData(currentItem);
                if (!data.m_128441_("info")) {
                    return false;
                }
                int type = (data = data.m_128469_("info")).m_128451_("type");
                if (type == 0 || !this.canApplyType(type)) {
                    return false;
                }
                List<ItemStack> list = this.getItems(type, data);
                if (list.isEmpty()) {
                    return false;
                }
                IItemTransporter transporter = TransporterManager.getTransporter(player);
                if (transporter == null) {
                    return false;
                }
                List<ItemStack> leftOver = this.consumeItems(list, transporter, true);
                if (!leftOver.isEmpty()) {
                    player.m_5661_((Component)this.string("Missing Items:"), false);
                    for (ItemStack stack : leftOver) {
                        player.m_5661_((Component)stack.m_41611_().m_6881_().m_130946_(stack.m_41613_() + "x"), false);
                    }
                    return true;
                }
                this.consumeItems(list, transporter, player.m_7500_());
                this.brewType = type;
                switch (this.brewType) {
                    case 1: {
                        this.hopsAmount = data.m_128451_("hops");
                        this.wheatAmount = data.m_128451_("wheat");
                        this.fluidAmount = data.m_128451_("water") * 1000;
                        break;
                    }
                    case 5: {
                        this.hopsAmount = data.m_128451_("grist");
                        this.fluidAmount = data.m_128451_("water") * 1000;
                        break;
                    }
                    case 2: {
                        this.fluidAmount = data.m_128451_("water") * 1000;
                        break;
                    }
                    case 10: {
                        this.potionType = (MobEffect)ForgeRegistries.MOB_EFFECTS.getValue(ResourceLocation.m_135820_((String)data.m_128461_("effect")));
                        this.hopsAmount = data.m_128451_("glow");
                        this.wheatAmount = data.m_128451_("red");
                        this.fluidAmount = data.m_128451_("water") * 1000;
                    }
                }
                if (data.m_128451_("water") > 0) {
                    transporter.addItem(new ItemStack((ItemLike)IC2Items.CELL_EMPTY, data.m_128451_("water")), Direction.DOWN, false);
                }
                return true;
            }
        } else if (this.hasTreeTap() && (this.isDrinkContainer(currentItem) || this.brewType == 10 && RecipeRegistry.POTION_BREWING.get(this.isSimulating()).getPotionContainer(currentItem.m_41720_()) != null)) {
            ItemStack result = this.drainAmount(this.brewType != 10 && PlayerHandler.getHandler((Player)player).altKeyDown ? currentItem.m_41613_() : 1, currentItem);
            if (!result.m_41619_()) {
                player.m_150109_().m_36054_(result);
                if (!player.m_7500_()) {
                    currentItem.m_41774_(1);
                }
            }
            return true;
        }
        return false;
    }

    private List<ItemStack> getItems(int type, CompoundTag data) {
        ObjectList list = CollectionUtils.createList();
        switch (type) {
            case 1: {
                this.addIfEnough(data.m_128451_("hops"), IC2Items.HOPS, (List<ItemStack>)list);
                this.addIfEnough(data.m_128451_("wheat"), Items.f_42405_, (List<ItemStack>)list);
                this.addIfEnough(data.m_128451_("water"), IC2Items.CELL_WATER, (List<ItemStack>)list);
                break;
            }
            case 5: {
                this.addIfEnough(data.m_128451_("grist"), IC2Items.WHISKY_GRIST, (List<ItemStack>)list);
                this.addIfEnough(data.m_128451_("water"), IC2Items.CELL_WATER, (List<ItemStack>)list);
                break;
            }
            case 2: {
                this.addIfEnough(data.m_128451_("water"), Items.f_41909_, (List<ItemStack>)list);
                break;
            }
            case 10: {
                List<Item> items = RecipeRegistry.POTION_BREWING.get(this.isSimulating()).getRequiredItems((MobEffect)ForgeRegistries.MOB_EFFECTS.getValue(ResourceLocation.m_135820_((String)data.m_128461_("effect"))));
                if (items.isEmpty()) {
                    return ObjectLists.emptyList();
                }
                for (Item item : items) {
                    list.add((ItemStack)new ItemStack((ItemLike)item));
                }
                list.add((ItemStack)new ItemStack((ItemLike)Items.f_42588_));
                this.addIfEnough(data.m_128451_("red"), Items.f_42451_, (List<ItemStack>)list);
                this.addIfEnough(data.m_128451_("glow"), Items.f_42525_, (List<ItemStack>)list);
                this.addIfEnough(data.m_128451_("water"), IC2Items.CELL_WATER, (List<ItemStack>)list);
            }
        }
        return list;
    }

    private void addIfEnough(int amount, Item item, List<ItemStack> result) {
        if (amount > 0) {
            result.add(new ItemStack((ItemLike)item, amount));
        }
    }

    private List<ItemStack> consumeItems(List<ItemStack> results, IItemTransporter transporter, boolean simulate) {
        ObjectList leftOver = CollectionUtils.createList();
        for (ItemStack stack : results) {
            ItemStack found = transporter.removeItem(StackFilter.defaultCompare(stack), Direction.DOWN, stack.m_41613_(), simulate);
            if (stack.m_41613_() == found.m_41613_()) continue;
            leftOver.add((ItemStack)StackUtil.copyWithSize(stack, stack.m_41613_() - found.m_41613_()));
        }
        return leftOver;
    }

    private String getName(int type) {
        switch (this.brewType) {
            case 1: {
                return "Beer";
            }
            case 5: {
                return "Wisky";
            }
            case 10: {
                return "Potion";
            }
            case 2: {
                return "Rum";
            }
        }
        return "Unknown";
    }

    private int getFluidCapacity() {
        return this.brewType == 10 ? 3000 : 32000;
    }

    private int calculateAmount(ItemStack currentItem, Player player, int capacity, int amount) {
        int useAmount = Math.min(player.m_6144_() ? 1 : currentItem.m_41613_(), capacity - amount);
        if (!player.m_7500_()) {
            currentItem.m_41774_(useAmount);
        }
        return useAmount;
    }

    private void alterComposition() {
        switch (this.beerQuality) {
            case 0: {
                this.age = 0;
                break;
            }
            case 1: {
                if (this.f_58857_.f_46441_.m_188499_()) {
                    this.beerQuality = 0;
                    break;
                }
                if (!this.f_58857_.f_46441_.m_188499_()) break;
                this.beerQuality = 5;
                break;
            }
            case 2: {
                if (!this.f_58857_.f_46441_.m_188499_()) break;
                this.beerQuality = 5;
                break;
            }
            default: {
                this.beerQuality = 5;
            }
        }
    }

    public void openBarrel() {
        if (!this.isEmpty()) {
            this.opened = true;
            if (this.brewType == 1) {
                this.calculateAlcoholLevel();
                this.calculateSolidRatio();
            } else if (this.brewType == 10) {
                if (this.beerQuality == 6) {
                    AreaEffectCloud cloud = new AreaEffectCloud(this.f_58857_, (double)((float)this.m_58899_().m_123341_() + 0.5f), (double)((float)this.m_58899_().m_123342_() + 0.5f), (double)((float)this.m_58899_().m_123343_() + 0.5f));
                    cloud.m_19712_(5.0f);
                    cloud.m_19732_(0.0f);
                    cloud.m_19740_(10);
                    cloud.m_19734_(1200);
                    cloud.m_19738_(0.0f);
                    cloud.m_19716_(new MobEffectInstance(MobEffects.f_19602_, 20, 3));
                    this.f_58857_.m_7967_((Entity)cloud);
                    this.reduceFluidAmount(this.fluidAmount);
                    return;
                }
                this.calculatePotionTime();
            } else if (this.brewType == 5) {
                this.fluidAmount = (int)((double)this.fluidAmount * (1.0 - (double)this.beerQuality / 100.0));
                this.fluidAmount = (int)((double)this.fluidAmount * ((double)this.hopsAmount / 16.0));
                this.alcoholLevel = this.calculateWhiskyYears();
            }
        }
    }

    public int calculateWhiskyYears() {
        int yearId = 0;
        if (this.beerQuality >= 50) {
            yearId = 5;
        } else if (this.beerQuality >= 25) {
            yearId = 4;
        } else if (this.beerQuality >= 15) {
            yearId = 3;
        } else if (this.beerQuality >= 12) {
            yearId = 2;
        } else if (this.beerQuality >= 10) {
            yearId = 1;
        }
        return yearId;
    }

    private void calculatePotionTime() {
        this.solidRatio = this.beerQuality > 0 && this.wheatAmount > 0 ? (int)Mth.m_14036_((float)((float)this.beerQuality * ((float)this.wheatAmount / 10.0f)), (float)0.0f, (float)(POTION_DURATIONS.length - 1)) : 0;
        this.alcoholLevel = this.beerQuality > 0 && this.hopsAmount > 0 ? (int)Mth.m_14036_((float)((float)this.beerQuality * 0.5f * ((float)this.hopsAmount / 10.0f)), (float)0.0f, (float)2.0f) : 0;
        switch (this.alcoholLevel) {
            case 1: {
                this.solidRatio = (int)((float)this.solidRatio * 0.5f);
                break;
            }
            case 2: {
                this.solidRatio = (int)((float)this.solidRatio * 0.25f);
            }
        }
    }

    private void calculateAlcoholLevel() {
        float hopsToWheat = this.wheatAmount > 0 ? (float)this.hopsAmount / (float)this.wheatAmount : 10.0f;
        this.alcoholLevel = 0;
        if (this.hopsAmount <= 0 && this.wheatAmount <= 0) {
            hopsToWheat = 0.0f;
        }
        if (hopsToWheat >= 5.0f) {
            this.beerQuality = 5;
        } else if (hopsToWheat >= 4.0f) {
            this.alcoholLevel = 6;
        } else if (hopsToWheat >= 3.0f) {
            this.alcoholLevel = 5;
        } else if (hopsToWheat >= 2.0f) {
            this.alcoholLevel = 4;
        } else if (hopsToWheat > 0.5f) {
            this.alcoholLevel = 3;
        } else if (hopsToWheat > 0.33333334f) {
            this.alcoholLevel = 2;
        } else if (hopsToWheat > 0.25f) {
            this.alcoholLevel = 1;
        }
    }

    private ItemStack drainAmount(int amount, ItemStack stack) {
        if (!this.opened) {
            this.openBarrel();
        }
        switch (this.brewType) {
            case 1: {
                return this.drainBeer(amount, stack);
            }
            case 2: {
                return this.drainRum(amount, stack);
            }
            case 5: {
                return this.drainWhisky(amount, stack);
            }
            case 10: {
                return this.drainPotion(amount, stack);
            }
        }
        return ItemStack.f_41583_;
    }

    private ItemStack drainBeer(int amount, ItemStack stack) {
        if (this.isDrinkContainer(stack)) {
            IDrinkContainer container = (IDrinkContainer)stack.m_41720_();
            if ((amount = Math.min(amount, this.fluidAmount / container.getCapacity())) <= 0) {
                return ItemStack.f_41583_;
            }
            ItemStack result = container.fillWith(IC2FoodsAndDrinks.BEER, amount);
            if (result.m_41619_()) {
                return ItemStack.f_41583_;
            }
            Beer.createData(result, this.beerQuality, this.solidRatio, this.alcoholLevel);
            if (this.m_8077_()) {
                StackUtil.addTooltip(result, this.m_7770_().getString());
            }
            this.reduceFluidAmount(amount * container.getCapacity());
            return result;
        }
        return ItemStack.f_41583_;
    }

    private ItemStack drainWhisky(int amount, ItemStack stack) {
        if (this.isDrinkContainer(stack)) {
            IDrinkContainer container = (IDrinkContainer)stack.m_41720_();
            if ((amount = Math.min(amount, Math.max(1, this.fluidAmount / container.getCapacity()))) <= 0 || this.fluidAmount <= 0) {
                return ItemStack.f_41583_;
            }
            ItemStack result = container.fillWith(IC2FoodsAndDrinks.WHISKY, amount);
            if (result.m_41619_()) {
                return ItemStack.f_41583_;
            }
            Whisky.createYear(result, this.alcoholLevel);
            if (this.m_8077_()) {
                StackUtil.addTooltip(result, this.m_7770_().getString());
            }
            this.reduceFluidAmount(amount * container.getCapacity());
            return result;
        }
        return ItemStack.f_41583_;
    }

    private ItemStack drainRum(int amount, ItemStack stack) {
        if (this.isDrinkContainer(stack)) {
            IDrinkContainer container = (IDrinkContainer)stack.m_41720_();
            if ((amount = Math.min(amount, this.fluidAmount / container.getCapacity())) <= 0) {
                return ItemStack.f_41583_;
            }
            ItemStack result = container.fillWith(IC2FoodsAndDrinks.RUM, amount);
            if (result.m_41619_()) {
                return ItemStack.f_41583_;
            }
            if (this.m_8077_()) {
                StackUtil.addTooltip(result, this.m_7770_().getString());
            }
            int progress = this.age * 100 / this.timeNeededForRum();
            this.reduceFluidAmount(amount * container.getCapacity());
            this.age = (int)((float)progress / 100.0f * (float)this.timeNeededForRum());
            Rum.createData(result, progress);
            return result;
        }
        return ItemStack.f_41583_;
    }

    private ItemStack drainPotion(int amount, ItemStack stack) {
        if (this.potionType != null) {
            IPotionBrewRegistry.PotionContainer output = RecipeRegistry.POTION_BREWING.get(this.isSimulating()).getPotionContainer(stack.m_41720_());
            if (output == null) {
                return ItemStack.f_41583_;
            }
            if ((amount = Math.min(amount, this.fluidAmount / output.getFluidUsage())) <= 0) {
                return ItemStack.f_41583_;
            }
            ObjectList effects = ObjectLists.singleton((Object)new MobEffectInstance(this.potionType, (int)Math.max(20.0f, (float)POTION_DURATIONS[10 - this.solidRatio] * 20.0f * output.getDurationEffectiveness()), this.alcoholLevel));
            ItemStack result = PotionUtils.m_43552_((ItemStack)new ItemStack((ItemLike)output.getOutput()), (Collection)effects);
            result.m_41784_().m_128405_("CustomPotionColor", PotionUtils.m_43564_((Collection)effects));
            MutableComponent name = this.translate(output.getOutput().m_5524_() + ".effect." + RecipeRegistry.POTION_BREWING.get(this.isSimulating()).getName(this.potionType));
            name.m_7383_().m_131155_(Boolean.valueOf(false));
            result.m_41714_((Component)name);
            this.reduceFluidAmount(amount * output.getFluidUsage());
            if (this.m_8077_()) {
                StackUtil.addTooltip(result, this.m_7770_().getString());
            }
            return result;
        }
        return ItemStack.f_41583_;
    }

    public static Component getDescription(MobEffectInstance effect, float durationFactor) {
        MutableComponent text = Component.m_237115_((String)effect.m_19576_());
        if (effect.m_19564_() > 0) {
            text.m_130946_(" ").m_7220_((Component)Component.m_237115_((String)("potion.potency." + effect.m_19564_())));
        }
        if (effect.m_19557_() > 20) {
            text.m_130946_(" (").m_130946_(effect.m_19577_() ? "**:**" : BarrelTileEntity.ticksToElapsedTime(Mth.m_14143_((float)((float)effect.m_19557_() * durationFactor)))).m_130946_(")");
        }
        text.m_7383_().m_131155_(Boolean.valueOf(false));
        return Component.m_237110_((String)"tooltip.item.ic2.tipped_arrow.name", (Object[])new Object[]{text.m_130940_(effect.m_19544_().m_19483_().m_19497_())});
    }

    public static String ticksToElapsedTime(int ticks) {
        int i = ticks / 20;
        int j = i / 60;
        return (i %= 60) < 10 ? j + ":0" + i : j + ":" + i;
    }

    public int getWhiskBrewTime() {
        return FMLEnvironment.dist.isClient() && this.isSimulating() && IC2.CONFIG.reducedWhiskyBrewTime.get() ? 120960 : 1209600;
    }

    private boolean isDrinkContainer(ItemStack item) {
        return item.m_41720_() instanceof IDrinkContainer && !((IDrinkContainer)item.m_41720_()).hasContent(item);
    }

    private boolean canApplyType(int type) {
        return this.brewType == 0 || this.brewType == type;
    }

    private void reduceFluidAmount(int amount) {
        this.fluidAmount -= amount;
        if (this.fluidAmount <= 0) {
            if (this.brewType == 1) {
                this.hopsAmount = 0;
                this.wheatAmount = 0;
                this.alcoholLevel = 0;
                this.beerQuality = 0;
                this.solidRatio = 0;
            } else if (this.brewType == 10) {
                this.hopsAmount = 0;
                this.wheatAmount = 0;
                this.alcoholLevel = 0;
                this.beerQuality = 0;
                this.solidRatio = 0;
                this.potionType = null;
            }
            this.age = 0;
            this.opened = false;
            this.fluidAmount = 0;
            this.brewType = 0;
        }
    }

    public int timeNeededForRum() {
        return (int)(1200.0 * (double)Math.max(1, this.fluidAmount / 1000) * Math.pow(0.95, Math.max(0, this.fluidAmount / 1000 - 1)));
    }

    private void calculateSolidRatio() {
        float solid = this.fluidAmount > 0 ? (float)(this.hopsAmount + this.wheatAmount) / ((float)this.fluidAmount / 1000.0f) : 10.0f;
        this.solidRatio = 0;
        if (solid >= 4.0f) {
            this.beerQuality = 5;
        } else if (solid >= 2.4f) {
            this.solidRatio = 6;
        } else if (solid >= 2.0f) {
            this.solidRatio = 5;
        } else if (solid > 1.0f) {
            this.solidRatio = 4;
        } else if (solid == 1.0f) {
            this.solidRatio = 3;
        } else if (solid > 0.5f) {
            this.solidRatio = 2;
        } else if (solid > 0.4166667f) {
            this.solidRatio = 1;
        }
    }

    public int getSolidRatio() {
        float solid;
        if (this.opened) {
            return this.solidRatio;
        }
        float f = solid = this.fluidAmount > 0 ? (float)(this.hopsAmount + this.wheatAmount) / ((float)this.fluidAmount / 1000.0f) : 10.0f;
        if (solid >= 2.4f) {
            return 6;
        }
        if (solid >= 2.0f) {
            return 5;
        }
        if (solid > 1.0f) {
            return 4;
        }
        if (solid == 1.0f) {
            return 3;
        }
        if (solid > 0.5f) {
            return 2;
        }
        if (solid > 0.4166667f) {
            return 1;
        }
        return 0;
    }

    public int getAlcoholLevel() {
        float hopsToWheat;
        if (this.opened) {
            return this.alcoholLevel;
        }
        float f = hopsToWheat = this.wheatAmount > 0 ? (float)this.hopsAmount / (float)this.wheatAmount : 10.0f;
        if (this.hopsAmount <= 0 && this.wheatAmount <= 0) {
            hopsToWheat = 0.0f;
        } else {
            if (hopsToWheat >= 4.0f) {
                return 6;
            }
            if (hopsToWheat >= 3.0f) {
                return 5;
            }
            if (hopsToWheat >= 2.0f) {
                return 4;
            }
            if (hopsToWheat > 0.5f) {
                return 3;
            }
            if (hopsToWheat > 0.33333334f) {
                return 2;
            }
            if (hopsToWheat > 0.25f) {
                return 1;
            }
        }
        return 0;
    }

    public IFluidHandler getBeerStoredFluid() {
        FluidTank tank = new FluidTank(this.getFluidCapacity());
        tank.setFluid(new FluidStack((Fluid)Fluids.f_76193_, this.fluidAmount));
        return tank;
    }

    public Component getBrewedPotion() {
        return this.potionType == null ? this.translate("tooltip.block.ic2.barrel.unknown") : this.potionType.m_19482_();
    }

    public boolean hasTreeTap() {
        return this.hasTreeTap;
    }

    public void setHasTreeTap(boolean hasTreeTap) {
        if (this.hasTreeTap != hasTreeTap) {
            this.hasTreeTap = hasTreeTap;
            this.onStateChanged();
        }
    }

    public boolean isEmpty() {
        return this.brewType == 0 || this.fluidAmount <= 0;
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.hasTreeTap = compound.m_128471_("tab");
        this.opened = compound.m_128471_("opened");
        this.fluidAmount = compound.m_128451_("fluid");
        this.brewType = compound.m_128451_("type");
        this.age = compound.m_128451_("age");
        if (this.brewType == 1 || this.brewType == 10) {
            this.beerQuality = compound.m_128451_("quality");
            this.wheatAmount = compound.m_128451_("wheat");
            this.hopsAmount = compound.m_128451_("hops");
            if (this.opened) {
                this.alcoholLevel = compound.m_128451_("level");
                this.solidRatio = compound.m_128451_("solid");
            }
            if (this.brewType == 10) {
                String id = compound.m_128461_("potion");
                this.potionType = id.isEmpty() || id.equals("empty") ? null : (MobEffect)ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(id));
            }
        } else if (this.brewType == 5) {
            this.beerQuality = compound.m_128451_("quality");
            this.hopsAmount = compound.m_128451_("hops");
            if (this.opened) {
                this.alcoholLevel = compound.m_128451_("level");
            }
        }
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putBoolean(compound, "tab", this.hasTreeTap, false);
        NBTUtils.putBoolean(compound, "opened", this.opened, false);
        compound.m_128405_("fluid", this.fluidAmount);
        NBTUtils.putByte(compound, "type", this.brewType, 0);
        compound.m_128405_("age", this.age);
        if (this.brewType == 1 || this.brewType == 10) {
            NBTUtils.putByte(compound, "quality", this.beerQuality, 0);
            NBTUtils.putByte(compound, "wheat", this.wheatAmount, 0);
            NBTUtils.putByte(compound, "hops", this.hopsAmount, 0);
            if (this.opened) {
                NBTUtils.putByte(compound, "level", this.alcoholLevel, 0);
                NBTUtils.putByte(compound, "solid", this.solidRatio, 0);
            }
            if (this.brewType == 10) {
                compound.m_128359_("potion", this.potionType == null ? "empty" : ForgeRegistries.MOB_EFFECTS.getKey((Object)this.potionType).toString());
            }
        } else if (this.brewType == 5) {
            NBTUtils.putByte(compound, "quality", this.beerQuality, 0);
            NBTUtils.putByte(compound, "hops", this.hopsAmount, 0);
            if (this.opened) {
                NBTUtils.putByte(compound, "level", this.alcoholLevel, 0);
            }
        }
    }
}

