/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.block.comp;

import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.tileentity.Ic2TileEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class Redstone
extends TileEntityComponent {
    private int redstoneInput;
    private Set<IRedstoneChangeHandler> changeSubscribers;
    private Set<IRedstoneModifier> modifiers;
    private LinkHandler outboundLink;

    public Redstone(Ic2TileEntity ic2TileEntity) {
        super(ic2TileEntity);
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        this.update();
    }

    @Override
    public void onUnloaded() {
        this.unlinkOutbound();
        this.unlinkInbound();
        super.onUnloaded();
    }

    @Override
    public void onNeighborChange(Block block, BlockPos blockPos) {
        super.onNeighborChange(block, blockPos);
        this.update();
    }

    public void update() {
        Level level = this.parent.m_58904_();
        if (level == null) {
            return;
        }
        int n = level.m_46755_(this.parent.m_58899_());
        if (this.modifiers != null) {
            for (IRedstoneModifier object : this.modifiers) {
                n = object.getRedstoneInput(n);
            }
        }
        if (n != this.redstoneInput) {
            this.redstoneInput = n;
            if (this.changeSubscribers != null) {
                for (IRedstoneChangeHandler iRedstoneChangeHandler : this.changeSubscribers) {
                    iRedstoneChangeHandler.onRedstoneChange(n);
                }
            }
        }
    }

    public int getRedstoneInput() {
        return this.redstoneInput;
    }

    public boolean hasRedstoneInput() {
        return this.redstoneInput > 0;
    }

    public void subscribe(IRedstoneChangeHandler iRedstoneChangeHandler) {
        if (iRedstoneChangeHandler == null) {
            throw new NullPointerException("null handler");
        }
        if (this.changeSubscribers == null) {
            this.changeSubscribers = new HashSet<IRedstoneChangeHandler>();
        }
        this.changeSubscribers.add(iRedstoneChangeHandler);
    }

    public void unsubscribe(IRedstoneChangeHandler iRedstoneChangeHandler) {
        if (iRedstoneChangeHandler == null) {
            throw new NullPointerException("null handler");
        }
        if (this.changeSubscribers == null) {
            return;
        }
        this.changeSubscribers.remove(iRedstoneChangeHandler);
        if (this.changeSubscribers.isEmpty()) {
            this.changeSubscribers = null;
        }
    }

    public void addRedstoneModifier(IRedstoneModifier iRedstoneModifier) {
        if (this.modifiers == null) {
            this.modifiers = new HashSet<IRedstoneModifier>();
        }
        this.modifiers.add(iRedstoneModifier);
    }

    public void addRedstoneModifiers(Collection<IRedstoneModifier> collection) {
        if (this.modifiers == null) {
            this.modifiers = new HashSet<IRedstoneModifier>(collection);
        } else {
            this.modifiers.addAll(collection);
        }
    }

    public void removeRedstoneModifier(IRedstoneModifier iRedstoneModifier) {
        if (this.modifiers == null) {
            return;
        }
        this.modifiers.remove(iRedstoneModifier);
    }

    public void removeRedstoneModifiers(Collection<IRedstoneModifier> collection) {
        if (this.modifiers == null) {
            return;
        }
        this.modifiers.removeAll(collection);
        if (this.modifiers.isEmpty()) {
            this.modifiers = null;
        }
    }

    public boolean isLinked() {
        return this.outboundLink != null;
    }

    public Redstone getLinkReceiver() {
        return this.outboundLink != null ? this.outboundLink.receiver : null;
    }

    public Collection<Redstone> getLinkedOrigins() {
        if (this.modifiers == null) {
            return Collections.emptyList();
        }
        ArrayList<Redstone> arrayList = new ArrayList<Redstone>(this.modifiers.size());
        for (IRedstoneModifier iRedstoneModifier : this.modifiers) {
            if (!(iRedstoneModifier instanceof LinkHandler)) continue;
            arrayList.add(((LinkHandler)iRedstoneModifier).origin);
        }
        return Collections.unmodifiableList(arrayList);
    }

    public void linkTo(Redstone redstone) {
        if (redstone == null) {
            throw new NullPointerException("null receiver");
        }
        if (this.outboundLink != null) {
            if (this.outboundLink.receiver != redstone) {
                throw new IllegalStateException("already linked");
            }
            return;
        }
        this.outboundLink = new LinkHandler(this, redstone);
        this.outboundLink.receiver.addRedstoneModifier(this.outboundLink);
        this.subscribe(this.outboundLink);
        redstone.update();
    }

    public void unlinkOutbound() {
        if (this.outboundLink == null) {
            return;
        }
        this.outboundLink.receiver.removeRedstoneModifier(this.outboundLink);
        this.unsubscribe(this.outboundLink);
        this.outboundLink = null;
    }

    public void unlinkInbound() {
        for (Redstone redstone : this.getLinkedOrigins()) {
            redstone.unlinkOutbound();
        }
    }

    public static interface IRedstoneModifier {
        public int getRedstoneInput(int var1);
    }

    public static interface IRedstoneChangeHandler {
        public void onRedstoneChange(int var1);
    }

    private static class LinkHandler
    implements IRedstoneChangeHandler,
    IRedstoneModifier {
        private final Redstone origin;
        private final Redstone receiver;

        public LinkHandler(Redstone redstone, Redstone redstone2) {
            this.origin = redstone;
            this.receiver = redstone2;
        }

        @Override
        public void onRedstoneChange(int n) {
            this.receiver.update();
        }

        @Override
        public int getRedstoneInput(int n) {
            return Math.max(n, this.origin.redstoneInput);
        }
    }
}

