/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestLidController;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

public class ChestBlockEntity
extends RandomizableContainerBlockEntity
implements LidBlockEntity {
    private static final int f_155323_ = 1;
    private NonNullList<ItemStack> f_59073_ = NonNullList.m_122780_(27, ItemStack.f_41583_);
    private final ContainerOpenersCounter f_155324_ = new ContainerOpenersCounter(){

        @Override
        protected void m_142292_(Level p_155357_, BlockPos p_155358_, BlockState p_155359_) {
            ChestBlockEntity.m_155338_(p_155357_, p_155358_, p_155359_, SoundEvents.f_11749_);
        }

        @Override
        protected void m_142289_(Level p_155367_, BlockPos p_155368_, BlockState p_155369_) {
            ChestBlockEntity.m_155338_(p_155367_, p_155368_, p_155369_, SoundEvents.f_11747_);
        }

        @Override
        protected void m_142148_(Level p_155361_, BlockPos p_155362_, BlockState p_155363_, int p_155364_, int p_155365_) {
            ChestBlockEntity.this.m_142151_(p_155361_, p_155362_, p_155363_, p_155364_, p_155365_);
        }

        @Override
        protected boolean m_142718_(Player p_155355_) {
            if (p_155355_.f_36096_ instanceof ChestMenu) {
                Container $$1 = ((ChestMenu)p_155355_.f_36096_).m_39261_();
                return $$1 == ChestBlockEntity.this || $$1 instanceof CompoundContainer && ((CompoundContainer)$$1).m_18927_(ChestBlockEntity.this);
            }
            return false;
        }
    };
    private final ChestLidController f_155325_ = new ChestLidController();

    protected ChestBlockEntity(BlockEntityType<?> p_155327_, BlockPos p_155328_, BlockState p_155329_) {
        super(p_155327_, p_155328_, p_155329_);
    }

    public ChestBlockEntity(BlockPos p_155331_, BlockState p_155332_) {
        this(BlockEntityType.f_58918_, p_155331_, p_155332_);
    }

    @Override
    public int m_6643_() {
        return 27;
    }

    @Override
    protected Component m_6820_() {
        return Component.m_237115_("container.chest");
    }

    @Override
    public void m_142466_(CompoundTag p_155349_) {
        super.m_142466_(p_155349_);
        this.f_59073_ = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
        if (!this.m_59631_(p_155349_)) {
            ContainerHelper.m_18980_(p_155349_, this.f_59073_);
        }
    }

    @Override
    protected void m_183515_(CompoundTag p_187489_) {
        super.m_183515_(p_187489_);
        if (!this.m_59634_(p_187489_)) {
            ContainerHelper.m_18973_(p_187489_, this.f_59073_);
        }
    }

    public static void m_155343_(Level p_155344_, BlockPos p_155345_, BlockState p_155346_, ChestBlockEntity p_155347_) {
        p_155347_.f_155325_.m_155374_();
    }

    static void m_155338_(Level p_155339_, BlockPos p_155340_, BlockState p_155341_, SoundEvent p_155342_) {
        ChestType $$4 = p_155341_.m_61143_(ChestBlock.f_51479_);
        if ($$4 == ChestType.LEFT) {
            return;
        }
        double $$5 = (double)p_155340_.m_123341_() + 0.5;
        double $$6 = (double)p_155340_.m_123342_() + 0.5;
        double $$7 = (double)p_155340_.m_123343_() + 0.5;
        if ($$4 == ChestType.RIGHT) {
            Direction $$8 = ChestBlock.m_51584_(p_155341_);
            $$5 += (double)$$8.m_122429_() * 0.5;
            $$7 += (double)$$8.m_122431_() * 0.5;
        }
        p_155339_.m_6263_(null, $$5, $$6, $$7, p_155342_, SoundSource.BLOCKS, 0.5f, p_155339_.f_46441_.m_188501_() * 0.1f + 0.9f);
    }

    @Override
    public boolean m_7531_(int p_59114_, int p_59115_) {
        if (p_59114_ == 1) {
            this.f_155325_.m_155377_(p_59115_ > 0);
            return true;
        }
        return super.m_7531_(p_59114_, p_59115_);
    }

    @Override
    public void m_5856_(Player p_59120_) {
        if (!this.f_58859_ && !p_59120_.m_5833_()) {
            this.f_155324_.m_155452_(p_59120_, this.m_58904_(), this.m_58899_(), this.m_58900_());
        }
    }

    @Override
    public void m_5785_(Player p_59118_) {
        if (!this.f_58859_ && !p_59118_.m_5833_()) {
            this.f_155324_.m_155468_(p_59118_, this.m_58904_(), this.m_58899_(), this.m_58900_());
        }
    }

    @Override
    protected NonNullList<ItemStack> m_7086_() {
        return this.f_59073_;
    }

    @Override
    protected void m_6520_(NonNullList<ItemStack> p_59110_) {
        this.f_59073_ = p_59110_;
    }

    @Override
    public float m_6683_(float p_59080_) {
        return this.f_155325_.m_155375_(p_59080_);
    }

    public static int m_59086_(BlockGetter p_59087_, BlockPos p_59088_) {
        BlockEntity $$3;
        BlockState $$2 = p_59087_.m_8055_(p_59088_);
        if ($$2.m_155947_() && ($$3 = p_59087_.m_7702_(p_59088_)) instanceof ChestBlockEntity) {
            return ((ChestBlockEntity)$$3).f_155324_.m_155450_();
        }
        return 0;
    }

    public static void m_59103_(ChestBlockEntity p_59104_, ChestBlockEntity p_59105_) {
        NonNullList<ItemStack> $$2 = p_59104_.m_7086_();
        p_59104_.m_6520_(p_59105_.m_7086_());
        p_59105_.m_6520_($$2);
    }

    @Override
    protected AbstractContainerMenu m_6555_(int p_59082_, Inventory p_59083_) {
        return ChestMenu.m_39237_(p_59082_, p_59083_, this);
    }

    public void m_155350_() {
        if (!this.f_58859_) {
            this.f_155324_.m_155476_(this.m_58904_(), this.m_58899_(), this.m_58900_());
        }
    }

    protected void m_142151_(Level p_155333_, BlockPos p_155334_, BlockState p_155335_, int p_155336_, int p_155337_) {
        Block $$5 = p_155335_.m_60734_();
        p_155333_.m_7696_(p_155334_, $$5, 1, p_155337_);
    }
}

