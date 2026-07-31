/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.List$ListType
 *  com.mojang.datafixers.types.templates.TaggedChoice$TaggedChoiceType
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.util.datafix.fixes.AddNewChoices;
import net.minecraft.util.datafix.fixes.LeavesFix;
import net.minecraft.util.datafix.fixes.References;
import org.slf4j.Logger;

public class TrappedChestBlockEntityFix
extends DataFix {
    private static final Logger f_17015_ = LogUtils.getLogger();
    private static final int f_145734_ = 4096;
    private static final short f_145735_ = 12;

    public TrappedChestBlockEntityFix(Schema p_17018_, boolean p_17019_) {
        super(p_17018_, p_17019_);
    }

    public TypeRewriteRule makeRule() {
        Type $$0 = this.getOutputSchema().getType(References.f_16773_);
        Type $$1 = $$0.findFieldType("Level");
        Type $$2 = $$1.findFieldType("TileEntities");
        if (!($$2 instanceof List.ListType)) {
            throw new IllegalStateException("Tile entity type is not a list type.");
        }
        List.ListType $$3 = (List.ListType)$$2;
        OpticFinder $$4 = DSL.fieldFinder((String)"TileEntities", (Type)$$3);
        Type $$5 = this.getInputSchema().getType(References.f_16773_);
        OpticFinder $$6 = $$5.findField("Level");
        OpticFinder $$7 = $$6.type().findField("Sections");
        Type $$8 = $$7.type();
        if (!($$8 instanceof List.ListType)) {
            throw new IllegalStateException("Expecting sections to be a list.");
        }
        Type $$9 = ((List.ListType)$$8).getElement();
        OpticFinder $$10 = DSL.typeFinder((Type)$$9);
        return TypeRewriteRule.seq((TypeRewriteRule)new AddNewChoices(this.getOutputSchema(), "AddTrappedChestFix", References.f_16781_).makeRule(), (TypeRewriteRule)this.fixTypeEverywhereTyped("Trapped Chest fix", $$5, p_17031_ -> p_17031_.updateTyped($$6, p_145746_ -> {
            Optional $$4 = p_145746_.getOptionalTyped($$7);
            if (!$$4.isPresent()) {
                return p_145746_;
            }
            List $$5 = ((Typed)$$4.get()).getAllTyped($$10);
            IntOpenHashSet $$6 = new IntOpenHashSet();
            for (Typed $$7 : $$5) {
                TrappedChestSection $$8 = new TrappedChestSection($$7, this.getInputSchema());
                if ($$8.m_16298_()) continue;
                for (int $$9 = 0; $$9 < 4096; ++$$9) {
                    int $$10 = $$8.m_16302_($$9);
                    if (!$$8.m_17053_($$10)) continue;
                    $$6.add($$8.m_16301_() << 12 | $$9);
                }
            }
            Dynamic $$11 = (Dynamic)p_145746_.get(DSL.remainderFinder());
            int $$12 = $$11.get("xPos").asInt(0);
            int $$13 = $$11.get("zPos").asInt(0);
            TaggedChoice.TaggedChoiceType $$14 = this.getInputSchema().findChoiceType(References.f_16781_);
            return p_145746_.updateTyped($$4, arg_0 -> TrappedChestBlockEntityFix.m_145747_($$14, $$12, $$13, (IntSet)$$6, arg_0));
        })));
    }

    private static /* synthetic */ Typed m_145747_(TaggedChoice.TaggedChoiceType p_145748_, int p_145749_, int p_145750_, IntSet p_145751_, Typed p_145752_) {
        return p_145752_.updateTyped(p_145748_.finder(), p_145741_ -> {
            int $$8;
            int $$7;
            Dynamic $$5 = (Dynamic)p_145741_.getOrCreate(DSL.remainderFinder());
            int $$6 = $$5.get("x").asInt(0) - (p_145749_ << 4);
            if (p_145751_.contains(LeavesFix.m_16210_($$6, $$7 = $$5.get("y").asInt(0), $$8 = $$5.get("z").asInt(0) - (p_145750_ << 4)))) {
                return p_145741_.update(p_145748_.finder(), p_145754_ -> p_145754_.mapFirst(p_145756_ -> {
                    if (!Objects.equals(p_145756_, "minecraft:chest")) {
                        f_17015_.warn("Block Entity was expected to be a chest");
                    }
                    return "minecraft:trapped_chest";
                }));
            }
            return p_145741_;
        });
    }

    public static final class TrappedChestSection
    extends LeavesFix.Section {
        @Nullable
        private IntSet f_17048_;

        public TrappedChestSection(Typed<?> p_17050_, Schema p_17051_) {
            super(p_17050_, p_17051_);
        }

        @Override
        protected boolean m_7969_() {
            this.f_17048_ = new IntOpenHashSet();
            for (int $$0 = 0; $$0 < this.f_16281_.size(); ++$$0) {
                Dynamic $$1 = (Dynamic)this.f_16281_.get($$0);
                String $$2 = $$1.get("Name").asString("");
                if (!Objects.equals($$2, "minecraft:trapped_chest")) continue;
                this.f_17048_.add($$0);
            }
            return this.f_17048_.isEmpty();
        }

        public boolean m_17053_(int p_17054_) {
            return this.f_17048_.contains(p_17054_);
        }
    }
}

