/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.entity;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;

public class HopperBlockEntity
extends RandomizableContainerBlockEntity
implements Hopper {
    public static final int f_155547_ = 8;
    public static final int f_155548_ = 5;
    private NonNullList<ItemStack> f_59301_ = NonNullList.m_122780_(5, ItemStack.f_41583_);
    private int f_59302_ = -1;
    private long f_59303_;

    public HopperBlockEntity(BlockPos p_155550_, BlockState p_155551_) {
        super(BlockEntityType.f_58933_, p_155550_, p_155551_);
    }

    @Override
    public void m_142466_(CompoundTag p_155588_) {
        super.m_142466_(p_155588_);
        this.f_59301_ = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
        if (!this.m_59631_(p_155588_)) {
            ContainerHelper.m_18980_(p_155588_, this.f_59301_);
        }
        this.f_59302_ = p_155588_.m_128451_("TransferCooldown");
    }

    @Override
    protected void m_183515_(CompoundTag p_187502_) {
        super.m_183515_(p_187502_);
        if (!this.m_59634_(p_187502_)) {
            ContainerHelper.m_18973_(p_187502_, this.f_59301_);
        }
        p_187502_.m_128405_("TransferCooldown", this.f_59302_);
    }

    @Override
    public int m_6643_() {
        return this.f_59301_.size();
    }

    @Override
    public ItemStack m_7407_(int p_59309_, int p_59310_) {
        this.m_59640_(null);
        return ContainerHelper.m_18969_(this.m_7086_(), p_59309_, p_59310_);
    }

    @Override
    public void m_6836_(int p_59315_, ItemStack p_59316_) {
        this.m_59640_(null);
        this.m_7086_().set(p_59315_, p_59316_);
        if (p_59316_.m_41613_() > this.m_6893_()) {
            p_59316_.m_41764_(this.m_6893_());
        }
    }

    @Override
    protected Component m_6820_() {
        return Component.m_237115_("container.hopper");
    }

    public static void m_155573_(Level p_155574_, BlockPos p_155575_, BlockState p_155576_, HopperBlockEntity p_155577_) {
        --p_155577_.f_59302_;
        p_155577_.f_59303_ = p_155574_.m_46467_();
        if (!p_155577_.m_59407_()) {
            p_155577_.m_59395_(0);
            HopperBlockEntity.m_155578_(p_155574_, p_155575_, p_155576_, p_155577_, () -> HopperBlockEntity.m_155552_(p_155574_, p_155577_));
        }
    }

    private static boolean m_155578_(Level p_155579_, BlockPos p_155580_, BlockState p_155581_, HopperBlockEntity p_155582_, BooleanSupplier p_155583_) {
        if (p_155579_.f_46443_) {
            return false;
        }
        if (!p_155582_.m_59407_() && p_155581_.m_61143_(HopperBlock.f_54022_).booleanValue()) {
            boolean $$5 = false;
            if (!p_155582_.m_7983_()) {
                $$5 = HopperBlockEntity.m_155562_(p_155579_, p_155580_, p_155581_, p_155582_);
            }
            if (!p_155582_.m_59404_()) {
                $$5 |= p_155583_.getAsBoolean();
            }
            if ($$5) {
                p_155582_.m_59395_(8);
                HopperBlockEntity.m_155232_(p_155579_, p_155580_, p_155581_);
                return true;
            }
        }
        return false;
    }

    private boolean m_59404_() {
        for (ItemStack $$0 : this.f_59301_) {
            if (!$$0.m_41619_() && $$0.m_41613_() == $$0.m_41741_()) continue;
            return false;
        }
        return true;
    }

    private static boolean m_155562_(Level p_155563_, BlockPos p_155564_, BlockState p_155565_, Container p_155566_) {
        Container $$4 = HopperBlockEntity.m_155592_(p_155563_, p_155564_, p_155565_);
        if ($$4 == null) {
            return false;
        }
        Direction $$5 = p_155565_.m_61143_(HopperBlock.f_54021_).m_122424_();
        if (HopperBlockEntity.m_59385_($$4, $$5)) {
            return false;
        }
        for (int $$6 = 0; $$6 < p_155566_.m_6643_(); ++$$6) {
            if (p_155566_.m_8020_($$6).m_41619_()) continue;
            ItemStack $$7 = p_155566_.m_8020_($$6).m_41777_();
            ItemStack $$8 = HopperBlockEntity.m_59326_(p_155566_, $$4, p_155566_.m_7407_($$6, 1), $$5);
            if ($$8.m_41619_()) {
                $$4.m_6596_();
                return true;
            }
            p_155566_.m_6836_($$6, $$7);
        }
        return false;
    }

    private static IntStream m_59339_(Container p_59340_, Direction p_59341_) {
        if (p_59340_ instanceof WorldlyContainer) {
            return IntStream.of(((WorldlyContainer)p_59340_).m_7071_(p_59341_));
        }
        return IntStream.range(0, p_59340_.m_6643_());
    }

    private static boolean m_59385_(Container p_59386_, Direction p_59387_) {
        return HopperBlockEntity.m_59339_(p_59386_, p_59387_).allMatch(p_59379_ -> {
            ItemStack $$2 = p_59386_.m_8020_(p_59379_);
            return $$2.m_41613_() >= $$2.m_41741_();
        });
    }

    private static boolean m_59397_(Container p_59398_, Direction p_59399_) {
        return HopperBlockEntity.m_59339_(p_59398_, p_59399_).allMatch(p_59319_ -> p_59398_.m_8020_(p_59319_).m_41619_());
    }

    public static boolean m_155552_(Level p_155553_, Hopper p_155554_) {
        Container $$2 = HopperBlockEntity.m_155596_(p_155553_, p_155554_);
        if ($$2 != null) {
            Direction $$3 = Direction.DOWN;
            if (HopperBlockEntity.m_59397_($$2, $$3)) {
                return false;
            }
            return HopperBlockEntity.m_59339_($$2, $$3).anyMatch(p_59363_ -> HopperBlockEntity.m_59354_(p_155554_, $$2, p_59363_, $$3));
        }
        for (ItemEntity $$4 : HopperBlockEntity.m_155589_(p_155553_, p_155554_)) {
            if (!HopperBlockEntity.m_59331_(p_155554_, $$4)) continue;
            return true;
        }
        return false;
    }

    private static boolean m_59354_(Hopper p_59355_, Container p_59356_, int p_59357_, Direction p_59358_) {
        ItemStack $$4 = p_59356_.m_8020_(p_59357_);
        if (!$$4.m_41619_() && HopperBlockEntity.m_59380_(p_59356_, $$4, p_59357_, p_59358_)) {
            ItemStack $$5 = $$4.m_41777_();
            ItemStack $$6 = HopperBlockEntity.m_59326_(p_59356_, p_59355_, p_59356_.m_7407_(p_59357_, 1), null);
            if ($$6.m_41619_()) {
                p_59356_.m_6596_();
                return true;
            }
            p_59356_.m_6836_(p_59357_, $$5);
        }
        return false;
    }

    public static boolean m_59331_(Container p_59332_, ItemEntity p_59333_) {
        boolean $$2 = false;
        ItemStack $$3 = p_59333_.m_32055_().m_41777_();
        ItemStack $$4 = HopperBlockEntity.m_59326_(null, p_59332_, $$3, null);
        if ($$4.m_41619_()) {
            $$2 = true;
            p_59333_.m_146870_();
        } else {
            p_59333_.m_32045_($$4);
        }
        return $$2;
    }

    public static ItemStack m_59326_(@Nullable Container p_59327_, Container p_59328_, ItemStack p_59329_, @Nullable Direction p_59330_) {
        if (p_59328_ instanceof WorldlyContainer && p_59330_ != null) {
            WorldlyContainer $$4 = (WorldlyContainer)p_59328_;
            int[] $$5 = $$4.m_7071_(p_59330_);
            for (int $$6 = 0; $$6 < $$5.length && !p_59329_.m_41619_(); ++$$6) {
                p_59329_ = HopperBlockEntity.m_59320_(p_59327_, p_59328_, p_59329_, $$5[$$6], p_59330_);
            }
        } else {
            int $$7 = p_59328_.m_6643_();
            for (int $$8 = 0; $$8 < $$7 && !p_59329_.m_41619_(); ++$$8) {
                p_59329_ = HopperBlockEntity.m_59320_(p_59327_, p_59328_, p_59329_, $$8, p_59330_);
            }
        }
        return p_59329_;
    }

    private static boolean m_59334_(Container p_59335_, ItemStack p_59336_, int p_59337_, @Nullable Direction p_59338_) {
        if (!p_59335_.m_7013_(p_59337_, p_59336_)) {
            return false;
        }
        return !(p_59335_ instanceof WorldlyContainer) || ((WorldlyContainer)p_59335_).m_7155_(p_59337_, p_59336_, p_59338_);
    }

    private static boolean m_59380_(Container p_59381_, ItemStack p_59382_, int p_59383_, Direction p_59384_) {
        return !(p_59381_ instanceof WorldlyContainer) || ((WorldlyContainer)p_59381_).m_7157_(p_59383_, p_59382_, p_59384_);
    }

    private static ItemStack m_59320_(@Nullable Container p_59321_, Container p_59322_, ItemStack p_59323_, int p_59324_, @Nullable Direction p_59325_) {
        ItemStack $$5 = p_59322_.m_8020_(p_59324_);
        if (HopperBlockEntity.m_59334_(p_59322_, p_59323_, p_59324_, p_59325_)) {
            boolean $$6 = false;
            boolean $$7 = p_59322_.m_7983_();
            if ($$5.m_41619_()) {
                p_59322_.m_6836_(p_59324_, p_59323_);
                p_59323_ = ItemStack.f_41583_;
                $$6 = true;
            } else if (HopperBlockEntity.m_59344_($$5, p_59323_)) {
                int $$8 = p_59323_.m_41741_() - $$5.m_41613_();
                int $$9 = Math.min(p_59323_.m_41613_(), $$8);
                p_59323_.m_41774_($$9);
                $$5.m_41769_($$9);
                boolean bl = $$6 = $$9 > 0;
            }
            if ($$6) {
                HopperBlockEntity $$10;
                if ($$7 && p_59322_ instanceof HopperBlockEntity && !($$10 = (HopperBlockEntity)p_59322_).m_59409_()) {
                    int $$11 = 0;
                    if (p_59321_ instanceof HopperBlockEntity) {
                        HopperBlockEntity $$12 = (HopperBlockEntity)p_59321_;
                        if ($$10.f_59303_ >= $$12.f_59303_) {
                            $$11 = 1;
                        }
                    }
                    $$10.m_59395_(8 - $$11);
                }
                p_59322_.m_6596_();
            }
        }
        return p_59323_;
    }

    @Nullable
    private static Container m_155592_(Level p_155593_, BlockPos p_155594_, BlockState p_155595_) {
        Direction $$3 = p_155595_.m_61143_(HopperBlock.f_54021_);
        return HopperBlockEntity.m_59390_(p_155593_, p_155594_.m_121945_($$3));
    }

    @Nullable
    private static Container m_155596_(Level p_155597_, Hopper p_155598_) {
        return HopperBlockEntity.m_59347_(p_155597_, p_155598_.m_6343_(), p_155598_.m_6358_() + 1.0, p_155598_.m_6446_());
    }

    public static List<ItemEntity> m_155589_(Level p_155590_, Hopper p_155591_) {
        return p_155591_.m_59300_().m_83299_().stream().flatMap(p_155558_ -> p_155590_.m_6443_(ItemEntity.class, p_155558_.m_82386_(p_155591_.m_6343_() - 0.5, p_155591_.m_6358_() - 0.5, p_155591_.m_6446_() - 0.5), EntitySelector.f_20402_).stream()).collect(Collectors.toList());
    }

    @Nullable
    public static Container m_59390_(Level p_59391_, BlockPos p_59392_) {
        return HopperBlockEntity.m_59347_(p_59391_, (double)p_59392_.m_123341_() + 0.5, (double)p_59392_.m_123342_() + 0.5, (double)p_59392_.m_123343_() + 0.5);
    }

    @Nullable
    private static Container m_59347_(Level p_59348_, double p_59349_, double p_59350_, double p_59351_) {
        List<Entity> $$9;
        BlockEntity $$8;
        Container $$4 = null;
        BlockPos $$5 = new BlockPos(p_59349_, p_59350_, p_59351_);
        BlockState $$6 = p_59348_.m_8055_($$5);
        Block $$7 = $$6.m_60734_();
        if ($$7 instanceof WorldlyContainerHolder) {
            $$4 = ((WorldlyContainerHolder)((Object)$$7)).m_5840_($$6, p_59348_, $$5);
        } else if ($$6.m_155947_() && ($$8 = p_59348_.m_7702_($$5)) instanceof Container && ($$4 = (Container)((Object)$$8)) instanceof ChestBlockEntity && $$7 instanceof ChestBlock) {
            $$4 = ChestBlock.m_51511_((ChestBlock)$$7, $$6, p_59348_, $$5, true);
        }
        if ($$4 == null && !($$9 = p_59348_.m_6249_(null, new AABB(p_59349_ - 0.5, p_59350_ - 0.5, p_59351_ - 0.5, p_59349_ + 0.5, p_59350_ + 0.5, p_59351_ + 0.5), EntitySelector.f_20405_)).isEmpty()) {
            $$4 = (Container)((Object)$$9.get(p_59348_.f_46441_.m_188503_($$9.size())));
        }
        return $$4;
    }

    private static boolean m_59344_(ItemStack p_59345_, ItemStack p_59346_) {
        if (!p_59345_.m_150930_(p_59346_.m_41720_())) {
            return false;
        }
        if (p_59345_.m_41773_() != p_59346_.m_41773_()) {
            return false;
        }
        if (p_59345_.m_41613_() > p_59345_.m_41741_()) {
            return false;
        }
        return ItemStack.m_41658_(p_59345_, p_59346_);
    }

    @Override
    public double m_6343_() {
        return (double)this.f_58858_.m_123341_() + 0.5;
    }

    @Override
    public double m_6358_() {
        return (double)this.f_58858_.m_123342_() + 0.5;
    }

    @Override
    public double m_6446_() {
        return (double)this.f_58858_.m_123343_() + 0.5;
    }

    private void m_59395_(int p_59396_) {
        this.f_59302_ = p_59396_;
    }

    private boolean m_59407_() {
        return this.f_59302_ > 0;
    }

    private boolean m_59409_() {
        return this.f_59302_ > 8;
    }

    @Override
    protected NonNullList<ItemStack> m_7086_() {
        return this.f_59301_;
    }

    @Override
    protected void m_6520_(NonNullList<ItemStack> p_59371_) {
        this.f_59301_ = p_59371_;
    }

    public static void m_155567_(Level p_155568_, BlockPos p_155569_, BlockState p_155570_, Entity p_155571_, HopperBlockEntity p_155572_) {
        if (p_155571_ instanceof ItemEntity && Shapes.m_83157_(Shapes.m_83064_(p_155571_.m_20191_().m_82386_(-p_155569_.m_123341_(), -p_155569_.m_123342_(), -p_155569_.m_123343_())), p_155572_.m_59300_(), BooleanOp.f_82689_)) {
            HopperBlockEntity.m_155578_(p_155568_, p_155569_, p_155570_, p_155572_, () -> HopperBlockEntity.m_59331_(p_155572_, (ItemEntity)p_155571_));
        }
    }

    @Override
    protected AbstractContainerMenu m_6555_(int p_59312_, Inventory p_59313_) {
        return new HopperMenu(p_59312_, p_59313_, this);
    }
}

