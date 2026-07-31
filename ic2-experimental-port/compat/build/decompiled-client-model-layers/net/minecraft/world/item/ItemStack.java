/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Multimap
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.AdventureModeCheck;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.DigDurabilityEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.slf4j.Logger;

public final class ItemStack {
    public static final Codec<ItemStack> f_41582_ = RecordCodecBuilder.create(p_41697_ -> p_41697_.group((App)Registry.f_122827_.m_194605_().fieldOf("id").forGetter(p_150946_ -> p_150946_.f_41589_), (App)Codec.INT.fieldOf("Count").forGetter(p_150941_ -> p_150941_.f_41587_), (App)CompoundTag.f_128325_.optionalFieldOf("tag").forGetter(p_150939_ -> Optional.ofNullable(p_150939_.f_41590_))).apply((Applicative)p_41697_, ItemStack::new));
    private static final Logger f_41585_ = LogUtils.getLogger();
    public static final ItemStack f_41583_ = new ItemStack((ItemLike)null);
    public static final DecimalFormat f_41584_ = Util.m_137469_(new DecimalFormat("#.##"), p_41704_ -> p_41704_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT)));
    public static final String f_150906_ = "Enchantments";
    public static final String f_150909_ = "display";
    public static final String f_150910_ = "Name";
    public static final String f_150911_ = "Lore";
    public static final String f_150912_ = "Damage";
    public static final String f_150913_ = "color";
    private static final String f_150914_ = "Unbreakable";
    private static final String f_150915_ = "RepairCost";
    private static final String f_150916_ = "CanDestroy";
    private static final String f_150917_ = "CanPlaceOn";
    private static final String f_150918_ = "HideFlags";
    private static final int f_150919_ = 0;
    private static final Style f_41586_ = Style.f_131099_.m_131140_(ChatFormatting.DARK_PURPLE).m_131155_(true);
    private int f_41587_;
    private int f_41588_;
    @Deprecated
    private final Item f_41589_;
    @Nullable
    private CompoundTag f_41590_;
    private boolean f_41591_;
    @Nullable
    private Entity f_41592_;
    @Nullable
    private AdventureModeCheck f_186360_;
    @Nullable
    private AdventureModeCheck f_186361_;

    public Optional<TooltipComponent> m_150921_() {
        return this.m_41720_().m_142422_(this);
    }

    public ItemStack(ItemLike p_41599_) {
        this(p_41599_, 1);
    }

    public ItemStack(Holder<Item> p_204116_) {
        this(p_204116_.m_203334_(), 1);
    }

    private ItemStack(ItemLike p_41604_, int p_41605_, Optional<CompoundTag> p_41606_) {
        this(p_41604_, p_41605_);
        p_41606_.ifPresent(this::m_41751_);
    }

    public ItemStack(Holder<Item> p_220155_, int p_220156_) {
        this(p_220155_.m_203334_(), p_220156_);
    }

    public ItemStack(ItemLike p_41601_, int p_41602_) {
        this.f_41589_ = p_41601_ == null ? null : p_41601_.m_5456_();
        this.f_41587_ = p_41602_;
        if (this.f_41589_ != null && this.f_41589_.m_41465_()) {
            this.m_41721_(this.m_41773_());
        }
        this.m_41617_();
    }

    private void m_41617_() {
        this.f_41591_ = false;
        this.f_41591_ = this.m_41619_();
    }

    private ItemStack(CompoundTag p_41608_) {
        this.f_41589_ = Registry.f_122827_.m_7745_(new ResourceLocation(p_41608_.m_128461_("id")));
        this.f_41587_ = p_41608_.m_128445_("Count");
        if (p_41608_.m_128425_("tag", 10)) {
            this.f_41590_ = p_41608_.m_128469_("tag");
            this.m_41720_().m_142312_(this.f_41590_);
        }
        if (this.m_41720_().m_41465_()) {
            this.m_41721_(this.m_41773_());
        }
        this.m_41617_();
    }

    public static ItemStack m_41712_(CompoundTag p_41713_) {
        try {
            return new ItemStack(p_41713_);
        }
        catch (RuntimeException $$1) {
            f_41585_.debug("Tried to load invalid item: {}", (Object)p_41713_, (Object)$$1);
            return f_41583_;
        }
    }

    public boolean m_41619_() {
        if (this == f_41583_) {
            return true;
        }
        if (this.m_41720_() == null || this.m_150930_(Items.f_41852_)) {
            return true;
        }
        return this.f_41587_ <= 0;
    }

    public ItemStack m_41620_(int p_41621_) {
        int $$1 = Math.min(p_41621_, this.f_41587_);
        ItemStack $$2 = this.m_41777_();
        $$2.m_41764_($$1);
        this.m_41774_($$1);
        return $$2;
    }

    public Item m_41720_() {
        return this.f_41591_ ? Items.f_41852_ : this.f_41589_;
    }

    public Holder<Item> m_220173_() {
        return this.m_41720_().m_204114_();
    }

    public boolean m_204117_(TagKey<Item> p_204118_) {
        return this.m_41720_().m_204114_().m_203656_(p_204118_);
    }

    public boolean m_150930_(Item p_150931_) {
        return this.m_41720_() == p_150931_;
    }

    public boolean m_220167_(Predicate<Holder<Item>> p_220168_) {
        return p_220168_.test(this.m_41720_().m_204114_());
    }

    public boolean m_220165_(Holder<Item> p_220166_) {
        return this.m_41720_().m_204114_() == p_220166_;
    }

    public Stream<TagKey<Item>> m_204131_() {
        return this.m_41720_().m_204114_().m_203616_();
    }

    public InteractionResult m_41661_(UseOnContext p_41662_) {
        Player $$1 = p_41662_.m_43723_();
        BlockPos $$2 = p_41662_.m_8083_();
        BlockInWorld $$3 = new BlockInWorld(p_41662_.m_43725_(), $$2, false);
        if ($$1 != null && !$$1.m_150110_().f_35938_ && !this.m_204121_(p_41662_.m_43725_().m_5962_().m_175515_(Registry.f_122901_), $$3)) {
            return InteractionResult.PASS;
        }
        Item $$4 = this.m_41720_();
        InteractionResult $$5 = $$4.m_6225_(p_41662_);
        if ($$1 != null && $$5.m_146666_()) {
            $$1.m_36246_(Stats.f_12982_.m_12902_($$4));
        }
        return $$5;
    }

    public float m_41691_(BlockState p_41692_) {
        return this.m_41720_().m_8102_(this, p_41692_);
    }

    public InteractionResultHolder<ItemStack> m_41682_(Level p_41683_, Player p_41684_, InteractionHand p_41685_) {
        return this.m_41720_().m_7203_(p_41683_, p_41684_, p_41685_);
    }

    public ItemStack m_41671_(Level p_41672_, LivingEntity p_41673_) {
        return this.m_41720_().m_5922_(this, p_41672_, p_41673_);
    }

    public CompoundTag m_41739_(CompoundTag p_41740_) {
        ResourceLocation $$1 = Registry.f_122827_.m_7981_(this.m_41720_());
        p_41740_.m_128359_("id", $$1 == null ? "minecraft:air" : $$1.toString());
        p_41740_.m_128344_("Count", (byte)this.f_41587_);
        if (this.f_41590_ != null) {
            p_41740_.m_128365_("tag", this.f_41590_.m_6426_());
        }
        return p_41740_;
    }

    public int m_41741_() {
        return this.m_41720_().m_41459_();
    }

    public boolean m_41753_() {
        return this.m_41741_() > 1 && (!this.m_41763_() || !this.m_41768_());
    }

    public boolean m_41763_() {
        if (this.f_41591_ || this.m_41720_().m_41462_() <= 0) {
            return false;
        }
        CompoundTag $$0 = this.m_41783_();
        return $$0 == null || !$$0.m_128471_(f_150914_);
    }

    public boolean m_41768_() {
        return this.m_41763_() && this.m_41773_() > 0;
    }

    public int m_41773_() {
        return this.f_41590_ == null ? 0 : this.f_41590_.m_128451_(f_150912_);
    }

    public void m_41721_(int p_41722_) {
        this.m_41784_().m_128405_(f_150912_, Math.max(0, p_41722_));
    }

    public int m_41776_() {
        return this.m_41720_().m_41462_();
    }

    public boolean m_220157_(int p_220158_, RandomSource p_220159_, @Nullable ServerPlayer p_220160_) {
        if (!this.m_41763_()) {
            return false;
        }
        if (p_220158_ > 0) {
            int $$3 = EnchantmentHelper.m_44843_(Enchantments.f_44986_, this);
            int $$4 = 0;
            for (int $$5 = 0; $$3 > 0 && $$5 < p_220158_; ++$$5) {
                if (!DigDurabilityEnchantment.m_220282_(this, $$3, p_220159_)) continue;
                ++$$4;
            }
            if ((p_220158_ -= $$4) <= 0) {
                return false;
            }
        }
        if (p_220160_ != null && p_220158_ != 0) {
            CriteriaTriggers.f_10586_.m_43669_(p_220160_, this, this.m_41773_() + p_220158_);
        }
        int $$6 = this.m_41773_() + p_220158_;
        this.m_41721_($$6);
        return $$6 >= this.m_41776_();
    }

    public <T extends LivingEntity> void m_41622_(int p_41623_, T p_41624_, Consumer<T> p_41625_) {
        if (p_41624_.f_19853_.f_46443_ || p_41624_ instanceof Player && ((Player)p_41624_).m_150110_().f_35937_) {
            return;
        }
        if (!this.m_41763_()) {
            return;
        }
        if (this.m_220157_(p_41623_, p_41624_.m_217043_(), p_41624_ instanceof ServerPlayer ? (ServerPlayer)p_41624_ : null)) {
            p_41625_.accept(p_41624_);
            Item $$3 = this.m_41720_();
            this.m_41774_(1);
            if (p_41624_ instanceof Player) {
                ((Player)p_41624_).m_36246_(Stats.f_12983_.m_12902_($$3));
            }
            this.m_41721_(0);
        }
    }

    public boolean m_150947_() {
        return this.f_41589_.m_142522_(this);
    }

    public int m_150948_() {
        return this.f_41589_.m_142158_(this);
    }

    public int m_150949_() {
        return this.f_41589_.m_142159_(this);
    }

    public boolean m_150926_(Slot p_150927_, ClickAction p_150928_, Player p_150929_) {
        return this.m_41720_().m_142207_(this, p_150927_, p_150928_, p_150929_);
    }

    public boolean m_150932_(ItemStack p_150933_, Slot p_150934_, ClickAction p_150935_, Player p_150936_, SlotAccess p_150937_) {
        return this.m_41720_().m_142305_(this, p_150933_, p_150934_, p_150935_, p_150936_, p_150937_);
    }

    public void m_41640_(LivingEntity p_41641_, Player p_41642_) {
        Item $$2 = this.m_41720_();
        if ($$2.m_7579_(this, p_41641_, p_41642_)) {
            p_41642_.m_36246_(Stats.f_12982_.m_12902_($$2));
        }
    }

    public void m_41686_(Level p_41687_, BlockState p_41688_, BlockPos p_41689_, Player p_41690_) {
        Item $$4 = this.m_41720_();
        if ($$4.m_6813_(this, p_41687_, p_41688_, p_41689_, p_41690_)) {
            p_41690_.m_36246_(Stats.f_12982_.m_12902_($$4));
        }
    }

    public boolean m_41735_(BlockState p_41736_) {
        return this.m_41720_().m_8096_(p_41736_);
    }

    public InteractionResult m_41647_(Player p_41648_, LivingEntity p_41649_, InteractionHand p_41650_) {
        return this.m_41720_().m_6880_(this, p_41648_, p_41649_, p_41650_);
    }

    public ItemStack m_41777_() {
        if (this.m_41619_()) {
            return f_41583_;
        }
        ItemStack $$0 = new ItemStack(this.m_41720_(), this.f_41587_);
        $$0.m_41754_(this.m_41612_());
        if (this.f_41590_ != null) {
            $$0.f_41590_ = this.f_41590_.m_6426_();
        }
        return $$0;
    }

    public static boolean m_41658_(ItemStack p_41659_, ItemStack p_41660_) {
        if (p_41659_.m_41619_() && p_41660_.m_41619_()) {
            return true;
        }
        if (p_41659_.m_41619_() || p_41660_.m_41619_()) {
            return false;
        }
        if (p_41659_.f_41590_ == null && p_41660_.f_41590_ != null) {
            return false;
        }
        return p_41659_.f_41590_ == null || p_41659_.f_41590_.equals(p_41660_.f_41590_);
    }

    public static boolean m_41728_(ItemStack p_41729_, ItemStack p_41730_) {
        if (p_41729_.m_41619_() && p_41730_.m_41619_()) {
            return true;
        }
        if (p_41729_.m_41619_() || p_41730_.m_41619_()) {
            return false;
        }
        return p_41729_.m_41744_(p_41730_);
    }

    private boolean m_41744_(ItemStack p_41745_) {
        if (this.f_41587_ != p_41745_.f_41587_) {
            return false;
        }
        if (!this.m_150930_(p_41745_.m_41720_())) {
            return false;
        }
        if (this.f_41590_ == null && p_41745_.f_41590_ != null) {
            return false;
        }
        return this.f_41590_ == null || this.f_41590_.equals(p_41745_.f_41590_);
    }

    public static boolean m_41746_(ItemStack p_41747_, ItemStack p_41748_) {
        if (p_41747_ == p_41748_) {
            return true;
        }
        if (!p_41747_.m_41619_() && !p_41748_.m_41619_()) {
            return p_41747_.m_41656_(p_41748_);
        }
        return false;
    }

    public static boolean m_41758_(ItemStack p_41759_, ItemStack p_41760_) {
        if (p_41759_ == p_41760_) {
            return true;
        }
        if (!p_41759_.m_41619_() && !p_41760_.m_41619_()) {
            return p_41759_.m_41726_(p_41760_);
        }
        return false;
    }

    public boolean m_41656_(ItemStack p_41657_) {
        return !p_41657_.m_41619_() && this.m_150930_(p_41657_.m_41720_());
    }

    public boolean m_41726_(ItemStack p_41727_) {
        if (this.m_41763_()) {
            return !p_41727_.m_41619_() && this.m_150930_(p_41727_.m_41720_());
        }
        return this.m_41656_(p_41727_);
    }

    public static boolean m_150942_(ItemStack p_150943_, ItemStack p_150944_) {
        return p_150943_.m_150930_(p_150944_.m_41720_()) && ItemStack.m_41658_(p_150943_, p_150944_);
    }

    public String m_41778_() {
        return this.m_41720_().m_5671_(this);
    }

    public String toString() {
        return this.f_41587_ + " " + this.m_41720_();
    }

    public void m_41666_(Level p_41667_, Entity p_41668_, int p_41669_, boolean p_41670_) {
        if (this.f_41588_ > 0) {
            --this.f_41588_;
        }
        if (this.m_41720_() != null) {
            this.m_41720_().m_6883_(this, p_41667_, p_41668_, p_41669_, p_41670_);
        }
    }

    public void m_41678_(Level p_41679_, Player p_41680_, int p_41681_) {
        p_41680_.m_6278_(Stats.f_12981_.m_12902_(this.m_41720_()), p_41681_);
        this.m_41720_().m_7836_(this, p_41679_, p_41680_);
    }

    public int m_41779_() {
        return this.m_41720_().m_8105_(this);
    }

    public UseAnim m_41780_() {
        return this.m_41720_().m_6164_(this);
    }

    public void m_41674_(Level p_41675_, LivingEntity p_41676_, int p_41677_) {
        this.m_41720_().m_5551_(this, p_41675_, p_41676_, p_41677_);
    }

    public boolean m_41781_() {
        return this.m_41720_().m_41463_(this);
    }

    public boolean m_41782_() {
        return !this.f_41591_ && this.f_41590_ != null && !this.f_41590_.m_128456_();
    }

    @Nullable
    public CompoundTag m_41783_() {
        return this.f_41590_;
    }

    public CompoundTag m_41784_() {
        if (this.f_41590_ == null) {
            this.m_41751_(new CompoundTag());
        }
        return this.f_41590_;
    }

    public CompoundTag m_41698_(String p_41699_) {
        if (this.f_41590_ == null || !this.f_41590_.m_128425_(p_41699_, 10)) {
            CompoundTag $$1 = new CompoundTag();
            this.m_41700_(p_41699_, $$1);
            return $$1;
        }
        return this.f_41590_.m_128469_(p_41699_);
    }

    @Nullable
    public CompoundTag m_41737_(String p_41738_) {
        if (this.f_41590_ == null || !this.f_41590_.m_128425_(p_41738_, 10)) {
            return null;
        }
        return this.f_41590_.m_128469_(p_41738_);
    }

    public void m_41749_(String p_41750_) {
        if (this.f_41590_ != null && this.f_41590_.m_128441_(p_41750_)) {
            this.f_41590_.m_128473_(p_41750_);
            if (this.f_41590_.m_128456_()) {
                this.f_41590_ = null;
            }
        }
    }

    public ListTag m_41785_() {
        if (this.f_41590_ != null) {
            return this.f_41590_.m_128437_(f_150906_, 10);
        }
        return new ListTag();
    }

    public void m_41751_(@Nullable CompoundTag p_41752_) {
        this.f_41590_ = p_41752_;
        if (this.m_41720_().m_41465_()) {
            this.m_41721_(this.m_41773_());
        }
        if (p_41752_ != null) {
            this.m_41720_().m_142312_(p_41752_);
        }
    }

    public Component m_41786_() {
        CompoundTag $$0 = this.m_41737_(f_150909_);
        if ($$0 != null && $$0.m_128425_(f_150910_, 8)) {
            try {
                MutableComponent $$1 = Component.Serializer.m_130701_($$0.m_128461_(f_150910_));
                if ($$1 != null) {
                    return $$1;
                }
                $$0.m_128473_(f_150910_);
            }
            catch (Exception $$2) {
                $$0.m_128473_(f_150910_);
            }
        }
        return this.m_41720_().m_7626_(this);
    }

    public ItemStack m_41714_(@Nullable Component p_41715_) {
        CompoundTag $$1 = this.m_41698_(f_150909_);
        if (p_41715_ != null) {
            $$1.m_128359_(f_150910_, Component.Serializer.m_130703_(p_41715_));
        } else {
            $$1.m_128473_(f_150910_);
        }
        return this;
    }

    public void m_41787_() {
        CompoundTag $$0 = this.m_41737_(f_150909_);
        if ($$0 != null) {
            $$0.m_128473_(f_150910_);
            if ($$0.m_128456_()) {
                this.m_41749_(f_150909_);
            }
        }
        if (this.f_41590_ != null && this.f_41590_.m_128456_()) {
            this.f_41590_ = null;
        }
    }

    public boolean m_41788_() {
        CompoundTag $$0 = this.m_41737_(f_150909_);
        return $$0 != null && $$0.m_128425_(f_150910_, 8);
    }

    public List<Component> m_41651_(@Nullable Player p_41652_, TooltipFlag p_41653_) {
        int $$5;
        Integer $$4;
        ArrayList $$2 = Lists.newArrayList();
        MutableComponent $$3 = Component.m_237119_().m_7220_(this.m_41786_()).m_130940_(this.m_41791_().f_43022_);
        if (this.m_41788_()) {
            $$3.m_130940_(ChatFormatting.ITALIC);
        }
        $$2.add($$3);
        if (!p_41653_.m_7050_() && !this.m_41788_() && this.m_150930_(Items.f_42573_) && ($$4 = MapItem.m_151131_(this)) != null) {
            $$2.add(Component.m_237113_("#" + $$4).m_130940_(ChatFormatting.GRAY));
        }
        if (ItemStack.m_41626_($$5 = this.m_41618_(), TooltipPart.ADDITIONAL)) {
            this.m_41720_().m_7373_(this, p_41652_ == null ? null : p_41652_.f_19853_, $$2, p_41653_);
        }
        if (this.m_41782_()) {
            if (ItemStack.m_41626_($$5, TooltipPart.ENCHANTMENTS)) {
                ItemStack.m_41709_($$2, this.m_41785_());
            }
            if (this.f_41590_.m_128425_(f_150909_, 10)) {
                CompoundTag $$6 = this.f_41590_.m_128469_(f_150909_);
                if (ItemStack.m_41626_($$5, TooltipPart.DYE) && $$6.m_128425_(f_150913_, 99)) {
                    if (p_41653_.m_7050_()) {
                        $$2.add(Component.m_237110_("item.color", String.format(Locale.ROOT, "#%06X", $$6.m_128451_(f_150913_))).m_130940_(ChatFormatting.GRAY));
                    } else {
                        $$2.add(Component.m_237115_("item.dyed").m_130944_(ChatFormatting.GRAY, ChatFormatting.ITALIC));
                    }
                }
                if ($$6.m_128435_(f_150911_) == 9) {
                    ListTag $$7 = $$6.m_128437_(f_150911_, 8);
                    for (int $$8 = 0; $$8 < $$7.size(); ++$$8) {
                        String $$9 = $$7.m_128778_($$8);
                        try {
                            MutableComponent $$10 = Component.Serializer.m_130701_($$9);
                            if ($$10 == null) continue;
                            $$2.add(ComponentUtils.m_130750_($$10, f_41586_));
                            continue;
                        }
                        catch (Exception $$11) {
                            $$6.m_128473_(f_150911_);
                        }
                    }
                }
            }
        }
        if (ItemStack.m_41626_($$5, TooltipPart.MODIFIERS)) {
            for (EquipmentSlot $$12 : EquipmentSlot.values()) {
                Multimap<Attribute, AttributeModifier> $$13 = this.m_41638_($$12);
                if ($$13.isEmpty()) continue;
                $$2.add(CommonComponents.f_237098_);
                $$2.add(Component.m_237115_("item.modifiers." + $$12.m_20751_()).m_130940_(ChatFormatting.GRAY));
                for (Map.Entry $$14 : $$13.entries()) {
                    double $$20;
                    AttributeModifier $$15 = (AttributeModifier)$$14.getValue();
                    double $$16 = $$15.m_22218_();
                    boolean $$17 = false;
                    if (p_41652_ != null) {
                        if ($$15.m_22209_() == Item.f_41374_) {
                            $$16 += p_41652_.m_21172_(Attributes.f_22281_);
                            $$16 += (double)EnchantmentHelper.m_44833_(this, MobType.f_21640_);
                            $$17 = true;
                        } else if ($$15.m_22209_() == Item.f_41375_) {
                            $$16 += p_41652_.m_21172_(Attributes.f_22283_);
                            $$17 = true;
                        }
                    }
                    if ($$15.m_22217_() == AttributeModifier.Operation.MULTIPLY_BASE || $$15.m_22217_() == AttributeModifier.Operation.MULTIPLY_TOTAL) {
                        double $$18 = $$16 * 100.0;
                    } else if (((Attribute)$$14.getKey()).equals(Attributes.f_22278_)) {
                        double $$19 = $$16 * 10.0;
                    } else {
                        $$20 = $$16;
                    }
                    if ($$17) {
                        $$2.add(Component.m_237113_(" ").m_7220_(Component.m_237110_("attribute.modifier.equals." + $$15.m_22217_().m_22235_(), f_41584_.format($$20), Component.m_237115_(((Attribute)$$14.getKey()).m_22087_()))).m_130940_(ChatFormatting.DARK_GREEN));
                        continue;
                    }
                    if ($$16 > 0.0) {
                        $$2.add(Component.m_237110_("attribute.modifier.plus." + $$15.m_22217_().m_22235_(), f_41584_.format($$20), Component.m_237115_(((Attribute)$$14.getKey()).m_22087_())).m_130940_(ChatFormatting.BLUE));
                        continue;
                    }
                    if (!($$16 < 0.0)) continue;
                    $$2.add(Component.m_237110_("attribute.modifier.take." + $$15.m_22217_().m_22235_(), f_41584_.format($$20 *= -1.0), Component.m_237115_(((Attribute)$$14.getKey()).m_22087_())).m_130940_(ChatFormatting.RED));
                }
            }
        }
        if (this.m_41782_()) {
            ListTag $$23;
            ListTag $$21;
            if (ItemStack.m_41626_($$5, TooltipPart.UNBREAKABLE) && this.f_41590_.m_128471_(f_150914_)) {
                $$2.add(Component.m_237115_("item.unbreakable").m_130940_(ChatFormatting.BLUE));
            }
            if (ItemStack.m_41626_($$5, TooltipPart.CAN_DESTROY) && this.f_41590_.m_128425_(f_150916_, 9) && !($$21 = this.f_41590_.m_128437_(f_150916_, 8)).isEmpty()) {
                $$2.add(CommonComponents.f_237098_);
                $$2.add(Component.m_237115_("item.canBreak").m_130940_(ChatFormatting.GRAY));
                for (int $$22 = 0; $$22 < $$21.size(); ++$$22) {
                    $$2.addAll(ItemStack.m_41761_($$21.m_128778_($$22)));
                }
            }
            if (ItemStack.m_41626_($$5, TooltipPart.CAN_PLACE) && this.f_41590_.m_128425_(f_150917_, 9) && !($$23 = this.f_41590_.m_128437_(f_150917_, 8)).isEmpty()) {
                $$2.add(CommonComponents.f_237098_);
                $$2.add(Component.m_237115_("item.canPlace").m_130940_(ChatFormatting.GRAY));
                for (int $$24 = 0; $$24 < $$23.size(); ++$$24) {
                    $$2.addAll(ItemStack.m_41761_($$23.m_128778_($$24)));
                }
            }
        }
        if (p_41653_.m_7050_()) {
            if (this.m_41768_()) {
                $$2.add(Component.m_237110_("item.durability", this.m_41776_() - this.m_41773_(), this.m_41776_()));
            }
            $$2.add(Component.m_237113_(Registry.f_122827_.m_7981_(this.m_41720_()).toString()).m_130940_(ChatFormatting.DARK_GRAY));
            if (this.m_41782_()) {
                $$2.add(Component.m_237110_("item.nbt_tags", this.f_41590_.m_128431_().size()).m_130940_(ChatFormatting.DARK_GRAY));
            }
        }
        return $$2;
    }

    private static boolean m_41626_(int p_41627_, TooltipPart p_41628_) {
        return (p_41627_ & p_41628_.m_41809_()) == 0;
    }

    private int m_41618_() {
        if (this.m_41782_() && this.f_41590_.m_128425_(f_150918_, 99)) {
            return this.f_41590_.m_128451_(f_150918_);
        }
        return 0;
    }

    public void m_41654_(TooltipPart p_41655_) {
        CompoundTag $$1 = this.m_41784_();
        $$1.m_128405_(f_150918_, $$1.m_128451_(f_150918_) | p_41655_.m_41809_());
    }

    public static void m_41709_(List<Component> p_41710_, ListTag p_41711_) {
        for (int $$2 = 0; $$2 < p_41711_.size(); ++$$2) {
            CompoundTag $$3 = p_41711_.m_128728_($$2);
            Registry.f_122825_.m_6612_(EnchantmentHelper.m_182446_($$3)).ifPresent(p_41708_ -> p_41710_.add(p_41708_.m_44700_(EnchantmentHelper.m_182438_($$3))));
        }
    }

    private static Collection<Component> m_41761_(String p_41762_) {
        try {
            return (Collection)BlockStateParser.m_234724_(Registry.f_122824_, p_41762_, true).map(p_220162_ -> Lists.newArrayList((Object[])new Component[]{p_220162_.f_234748_().m_60734_().m_49954_().m_130940_(ChatFormatting.DARK_GRAY)}), p_220164_ -> p_220164_.f_234762_().m_203614_().map(p_220172_ -> ((Block)p_220172_.m_203334_()).m_49954_().m_130940_(ChatFormatting.DARK_GRAY)).collect(Collectors.toList()));
        }
        catch (CommandSyntaxException commandSyntaxException) {
            return Lists.newArrayList((Object[])new Component[]{Component.m_237113_("missingno").m_130940_(ChatFormatting.DARK_GRAY)});
        }
    }

    public boolean m_41790_() {
        return this.m_41720_().m_5812_(this);
    }

    public Rarity m_41791_() {
        return this.m_41720_().m_41460_(this);
    }

    public boolean m_41792_() {
        if (!this.m_41720_().m_8120_(this)) {
            return false;
        }
        return !this.m_41793_();
    }

    public void m_41663_(Enchantment p_41664_, int p_41665_) {
        this.m_41784_();
        if (!this.f_41590_.m_128425_(f_150906_, 9)) {
            this.f_41590_.m_128365_(f_150906_, new ListTag());
        }
        ListTag $$2 = this.f_41590_.m_128437_(f_150906_, 10);
        $$2.add(EnchantmentHelper.m_182443_(EnchantmentHelper.m_182432_(p_41664_), (byte)p_41665_));
    }

    public boolean m_41793_() {
        if (this.f_41590_ != null && this.f_41590_.m_128425_(f_150906_, 9)) {
            return !this.f_41590_.m_128437_(f_150906_, 10).isEmpty();
        }
        return false;
    }

    public void m_41700_(String p_41701_, Tag p_41702_) {
        this.m_41784_().m_128365_(p_41701_, p_41702_);
    }

    public boolean m_41794_() {
        return this.f_41592_ instanceof ItemFrame;
    }

    public void m_41636_(@Nullable Entity p_41637_) {
        this.f_41592_ = p_41637_;
    }

    @Nullable
    public ItemFrame m_41795_() {
        return this.f_41592_ instanceof ItemFrame ? (ItemFrame)this.m_41609_() : null;
    }

    @Nullable
    public Entity m_41609_() {
        return !this.f_41591_ ? this.f_41592_ : null;
    }

    public int m_41610_() {
        if (this.m_41782_() && this.f_41590_.m_128425_(f_150915_, 3)) {
            return this.f_41590_.m_128451_(f_150915_);
        }
        return 0;
    }

    public void m_41742_(int p_41743_) {
        this.m_41784_().m_128405_(f_150915_, p_41743_);
    }

    public Multimap<Attribute, AttributeModifier> m_41638_(EquipmentSlot p_41639_) {
        Multimap<Attribute, AttributeModifier> $$7;
        if (this.m_41782_() && this.f_41590_.m_128425_("AttributeModifiers", 9)) {
            HashMultimap $$1 = HashMultimap.create();
            ListTag $$2 = this.f_41590_.m_128437_("AttributeModifiers", 10);
            for (int $$3 = 0; $$3 < $$2.size(); ++$$3) {
                AttributeModifier $$6;
                Optional<Attribute> $$5;
                CompoundTag $$4 = $$2.m_128728_($$3);
                if ($$4.m_128425_("Slot", 8) && !$$4.m_128461_("Slot").equals(p_41639_.m_20751_()) || !($$5 = Registry.f_122866_.m_6612_(ResourceLocation.m_135820_($$4.m_128461_("AttributeName")))).isPresent() || ($$6 = AttributeModifier.m_22212_($$4)) == null || $$6.m_22209_().getLeastSignificantBits() == 0L || $$6.m_22209_().getMostSignificantBits() == 0L) continue;
                $$1.put((Object)$$5.get(), (Object)$$6);
            }
        } else {
            $$7 = this.m_41720_().m_7167_(p_41639_);
        }
        return $$7;
    }

    public void m_41643_(Attribute p_41644_, AttributeModifier p_41645_, @Nullable EquipmentSlot p_41646_) {
        this.m_41784_();
        if (!this.f_41590_.m_128425_("AttributeModifiers", 9)) {
            this.f_41590_.m_128365_("AttributeModifiers", new ListTag());
        }
        ListTag $$3 = this.f_41590_.m_128437_("AttributeModifiers", 10);
        CompoundTag $$4 = p_41645_.m_22219_();
        $$4.m_128359_("AttributeName", Registry.f_122866_.m_7981_(p_41644_).toString());
        if (p_41646_ != null) {
            $$4.m_128359_("Slot", p_41646_.m_20751_());
        }
        $$3.add($$4);
    }

    public Component m_41611_() {
        MutableComponent $$0 = Component.m_237119_().m_7220_(this.m_41786_());
        if (this.m_41788_()) {
            $$0.m_130940_(ChatFormatting.ITALIC);
        }
        MutableComponent $$1 = ComponentUtils.m_130748_($$0);
        if (!this.f_41591_) {
            $$1.m_130940_(this.m_41791_().f_43022_).m_130938_(p_220170_ -> p_220170_.m_131144_(new HoverEvent(HoverEvent.Action.f_130832_, new HoverEvent.ItemStackInfo(this))));
        }
        return $$1;
    }

    public boolean m_204121_(Registry<Block> p_204122_, BlockInWorld p_204123_) {
        if (this.f_186361_ == null) {
            this.f_186361_ = new AdventureModeCheck(f_150917_);
        }
        return this.f_186361_.m_204085_(this, p_204122_, p_204123_);
    }

    public boolean m_204128_(Registry<Block> p_204129_, BlockInWorld p_204130_) {
        if (this.f_186360_ == null) {
            this.f_186360_ = new AdventureModeCheck(f_150916_);
        }
        return this.f_186360_.m_204085_(this, p_204129_, p_204130_);
    }

    public int m_41612_() {
        return this.f_41588_;
    }

    public void m_41754_(int p_41755_) {
        this.f_41588_ = p_41755_;
    }

    public int m_41613_() {
        return this.f_41591_ ? 0 : this.f_41587_;
    }

    public void m_41764_(int p_41765_) {
        this.f_41587_ = p_41765_;
        this.m_41617_();
    }

    public void m_41769_(int p_41770_) {
        this.m_41764_(this.f_41587_ + p_41770_);
    }

    public void m_41774_(int p_41775_) {
        this.m_41769_(-p_41775_);
    }

    public void m_41731_(Level p_41732_, LivingEntity p_41733_, int p_41734_) {
        this.m_41720_().m_5929_(p_41732_, p_41733_, this, p_41734_);
    }

    public void m_150924_(ItemEntity p_150925_) {
        this.m_41720_().m_142023_(p_150925_);
    }

    public boolean m_41614_() {
        return this.m_41720_().m_41472_();
    }

    public SoundEvent m_41615_() {
        return this.m_41720_().m_6023_();
    }

    public SoundEvent m_41616_() {
        return this.m_41720_().m_6061_();
    }

    @Nullable
    public SoundEvent m_150920_() {
        return this.m_41720_().m_142602_();
    }

    public static final class TooltipPart
    extends Enum<TooltipPart> {
        public static final /* enum */ TooltipPart ENCHANTMENTS = new TooltipPart();
        public static final /* enum */ TooltipPart MODIFIERS = new TooltipPart();
        public static final /* enum */ TooltipPart UNBREAKABLE = new TooltipPart();
        public static final /* enum */ TooltipPart CAN_DESTROY = new TooltipPart();
        public static final /* enum */ TooltipPart CAN_PLACE = new TooltipPart();
        public static final /* enum */ TooltipPart ADDITIONAL = new TooltipPart();
        public static final /* enum */ TooltipPart DYE = new TooltipPart();
        private final int f_41803_ = 1 << this.ordinal();
        private static final /* synthetic */ TooltipPart[] $VALUES;

        public static TooltipPart[] values() {
            return (TooltipPart[])$VALUES.clone();
        }

        public static TooltipPart valueOf(String p_41811_) {
            return Enum.valueOf(TooltipPart.class, p_41811_);
        }

        public int m_41809_() {
            return this.f_41803_;
        }

        private static /* synthetic */ TooltipPart[] m_150950_() {
            return new TooltipPart[]{ENCHANTMENTS, MODIFIERS, UNBREAKABLE, CAN_DESTROY, CAN_PLACE, ADDITIONAL, DYE};
        }

        static {
            $VALUES = TooltipPart.m_150950_();
        }
    }
}

