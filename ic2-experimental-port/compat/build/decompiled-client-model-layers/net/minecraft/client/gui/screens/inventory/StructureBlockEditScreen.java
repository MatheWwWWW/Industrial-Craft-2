/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.properties.StructureMode;

public class StructureBlockEditScreen
extends Screen {
    private static final Component f_99381_ = Component.m_237115_("structure_block.structure_name");
    private static final Component f_99383_ = Component.m_237115_("structure_block.position");
    private static final Component f_99384_ = Component.m_237115_("structure_block.size");
    private static final Component f_99385_ = Component.m_237115_("structure_block.integrity");
    private static final Component f_99386_ = Component.m_237115_("structure_block.custom_data");
    private static final Component f_99387_ = Component.m_237115_("structure_block.include_entities");
    private static final Component f_99388_ = Component.m_237115_("structure_block.detect_size");
    private static final Component f_99389_ = Component.m_237115_("structure_block.show_air");
    private static final Component f_99390_ = Component.m_237115_("structure_block.show_boundingbox");
    private static final ImmutableList<StructureMode> f_169836_ = ImmutableList.copyOf((Object[])StructureMode.values());
    private static final ImmutableList<StructureMode> f_169837_ = (ImmutableList)f_169836_.stream().filter(p_169859_ -> p_169859_ != StructureMode.DATA).collect(ImmutableList.toImmutableList());
    private final StructureBlockEntity f_99391_;
    private Mirror f_99392_ = Mirror.NONE;
    private Rotation f_99393_ = Rotation.NONE;
    private StructureMode f_99394_ = StructureMode.DATA;
    private boolean f_99395_;
    private boolean f_99355_;
    private boolean f_99356_;
    private EditBox f_99357_;
    private EditBox f_99358_;
    private EditBox f_99359_;
    private EditBox f_99360_;
    private EditBox f_99361_;
    private EditBox f_99362_;
    private EditBox f_99363_;
    private EditBox f_99364_;
    private EditBox f_99365_;
    private EditBox f_99366_;
    private Button f_99369_;
    private Button f_99370_;
    private Button f_99371_;
    private Button f_99372_;
    private Button f_99373_;
    private Button f_99374_;
    private Button f_99376_;
    private CycleButton<Boolean> f_169835_;
    private CycleButton<Mirror> f_99378_;
    private CycleButton<Boolean> f_99379_;
    private CycleButton<Boolean> f_99380_;
    private final DecimalFormat f_99382_ = new DecimalFormat("0.0###");

    public StructureBlockEditScreen(StructureBlockEntity p_99398_) {
        super(Component.m_237115_(Blocks.f_50677_.m_7705_()));
        this.f_99391_ = p_99398_;
        this.f_99382_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    }

    @Override
    public void m_86600_() {
        this.f_99357_.m_94120_();
        this.f_99358_.m_94120_();
        this.f_99359_.m_94120_();
        this.f_99360_.m_94120_();
        this.f_99361_.m_94120_();
        this.f_99362_.m_94120_();
        this.f_99363_.m_94120_();
        this.f_99364_.m_94120_();
        this.f_99365_.m_94120_();
        this.f_99366_.m_94120_();
    }

    private void m_99444_() {
        if (this.m_99403_(StructureBlockEntity.UpdateType.UPDATE_DATA)) {
            this.f_96541_.m_91152_(null);
        }
    }

    private void m_99447_() {
        this.f_99391_.m_59881_(this.f_99392_);
        this.f_99391_.m_59883_(this.f_99393_);
        this.f_99391_.m_59860_(this.f_99394_);
        this.f_99391_.m_59876_(this.f_99395_);
        this.f_99391_.m_59896_(this.f_99355_);
        this.f_99391_.m_59898_(this.f_99356_);
        this.f_96541_.m_91152_(null);
    }

    @Override
    protected void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        this.m_142416_(new Button(this.f_96543_ / 2 - 4 - 150, 210, 150, 20, CommonComponents.f_130655_, p_99460_ -> this.m_99444_()));
        this.m_142416_(new Button(this.f_96543_ / 2 + 4, 210, 150, 20, CommonComponents.f_130656_, p_99457_ -> this.m_99447_()));
        this.f_99392_ = this.f_99391_.m_59905_();
        this.f_99393_ = this.f_99391_.m_59906_();
        this.f_99394_ = this.f_99391_.m_59908_();
        this.f_99395_ = this.f_99391_.m_59910_();
        this.f_99355_ = this.f_99391_.m_59834_();
        this.f_99356_ = this.f_99391_.m_59835_();
        this.f_99369_ = this.m_142416_(new Button(this.f_96543_ / 2 + 4 + 100, 185, 50, 20, Component.m_237115_("structure_block.button.save"), p_99454_ -> {
            if (this.f_99391_.m_59908_() == StructureMode.SAVE) {
                this.m_99403_(StructureBlockEntity.UpdateType.SAVE_AREA);
                this.f_96541_.m_91152_(null);
            }
        }));
        this.f_99370_ = this.m_142416_(new Button(this.f_96543_ / 2 + 4 + 100, 185, 50, 20, Component.m_237115_("structure_block.button.load"), p_99451_ -> {
            if (this.f_99391_.m_59908_() == StructureMode.LOAD) {
                this.m_99403_(StructureBlockEntity.UpdateType.LOAD_AREA);
                this.f_96541_.m_91152_(null);
            }
        }));
        this.m_142416_(CycleButton.m_168894_(p_169852_ -> Component.m_237115_("structure_block.mode." + p_169852_.m_7912_())).m_168952_((List<StructureMode>)f_169837_, (List<StructureMode>)f_169836_).m_168929_().m_168948_(this.f_99394_).m_168936_(this.f_96543_ / 2 - 4 - 150, 185, 50, 20, Component.m_237113_("MODE"), (p_169846_, p_169847_) -> {
            this.f_99391_.m_59860_((StructureMode)p_169847_);
            this.m_169838_((StructureMode)p_169847_);
        }));
        this.f_99376_ = this.m_142416_(new Button(this.f_96543_ / 2 + 4 + 100, 120, 50, 20, Component.m_237115_("structure_block.button.detect_size"), p_99443_ -> {
            if (this.f_99391_.m_59908_() == StructureMode.SAVE) {
                this.m_99403_(StructureBlockEntity.UpdateType.SCAN_AREA);
                this.f_96541_.m_91152_(null);
            }
        }));
        this.f_169835_ = this.m_142416_(CycleButton.m_168916_(!this.f_99391_.m_59910_()).m_168929_().m_168936_(this.f_96543_ / 2 + 4 + 100, 160, 50, 20, f_99387_, (p_169861_, p_169862_) -> this.f_99391_.m_59876_(p_169862_ == false)));
        this.f_99378_ = this.m_142416_(CycleButton.m_168894_(Mirror::m_153787_).m_168961_((Mirror[])Mirror.values()).m_168929_().m_168948_(this.f_99392_).m_168936_(this.f_96543_ / 2 - 20, 185, 40, 20, Component.m_237113_("MIRROR"), (p_169843_, p_169844_) -> this.f_99391_.m_59881_((Mirror)p_169844_)));
        this.f_99379_ = this.m_142416_(CycleButton.m_168916_(this.f_99391_.m_59834_()).m_168929_().m_168936_(this.f_96543_ / 2 + 4 + 100, 80, 50, 20, f_99389_, (p_169856_, p_169857_) -> this.f_99391_.m_59896_((boolean)p_169857_)));
        this.f_99380_ = this.m_142416_(CycleButton.m_168916_(this.f_99391_.m_59835_()).m_168929_().m_168936_(this.f_96543_ / 2 + 4 + 100, 80, 50, 20, f_99390_, (p_169849_, p_169850_) -> this.f_99391_.m_59898_((boolean)p_169850_)));
        this.f_99371_ = this.m_142416_(new Button(this.f_96543_ / 2 - 1 - 40 - 1 - 40 - 20, 185, 40, 20, Component.m_237113_("0"), p_99425_ -> {
            this.f_99391_.m_59883_(Rotation.NONE);
            this.m_99464_();
        }));
        this.f_99372_ = this.m_142416_(new Button(this.f_96543_ / 2 - 1 - 40 - 20, 185, 40, 20, Component.m_237113_("90"), p_99415_ -> {
            this.f_99391_.m_59883_(Rotation.CLOCKWISE_90);
            this.m_99464_();
        }));
        this.f_99373_ = this.m_142416_(new Button(this.f_96543_ / 2 + 1 + 20, 185, 40, 20, Component.m_237113_("180"), p_169854_ -> {
            this.f_99391_.m_59883_(Rotation.CLOCKWISE_180);
            this.m_99464_();
        }));
        this.f_99374_ = this.m_142416_(new Button(this.f_96543_ / 2 + 1 + 40 + 1 + 20, 185, 40, 20, Component.m_237113_("270"), p_169841_ -> {
            this.f_99391_.m_59883_(Rotation.COUNTERCLOCKWISE_90);
            this.m_99464_();
        }));
        this.f_99357_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 152, 40, 300, 20, (Component)Component.m_237115_("structure_block.structure_name")){

            @Override
            public boolean m_5534_(char p_99476_, int p_99477_) {
                if (!StructureBlockEditScreen.this.m_96583_(this.m_94155_(), p_99476_, this.m_94207_())) {
                    return false;
                }
                return super.m_5534_(p_99476_, p_99477_);
            }
        };
        this.f_99357_.m_94199_(128);
        this.f_99357_.m_94144_(this.f_99391_.m_59895_());
        this.m_7787_(this.f_99357_);
        BlockPos $$0 = this.f_99391_.m_59902_();
        this.f_99358_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 152, 80, 80, 20, Component.m_237115_("structure_block.position.x"));
        this.f_99358_.m_94199_(15);
        this.f_99358_.m_94144_(Integer.toString($$0.m_123341_()));
        this.m_7787_(this.f_99358_);
        this.f_99359_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 72, 80, 80, 20, Component.m_237115_("structure_block.position.y"));
        this.f_99359_.m_94199_(15);
        this.f_99359_.m_94144_(Integer.toString($$0.m_123342_()));
        this.m_7787_(this.f_99359_);
        this.f_99360_ = new EditBox(this.f_96547_, this.f_96543_ / 2 + 8, 80, 80, 20, Component.m_237115_("structure_block.position.z"));
        this.f_99360_.m_94199_(15);
        this.f_99360_.m_94144_(Integer.toString($$0.m_123343_()));
        this.m_7787_(this.f_99360_);
        Vec3i $$1 = this.f_99391_.m_155805_();
        this.f_99361_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 152, 120, 80, 20, Component.m_237115_("structure_block.size.x"));
        this.f_99361_.m_94199_(15);
        this.f_99361_.m_94144_(Integer.toString($$1.m_123341_()));
        this.m_7787_(this.f_99361_);
        this.f_99362_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 72, 120, 80, 20, Component.m_237115_("structure_block.size.y"));
        this.f_99362_.m_94199_(15);
        this.f_99362_.m_94144_(Integer.toString($$1.m_123342_()));
        this.m_7787_(this.f_99362_);
        this.f_99363_ = new EditBox(this.f_96547_, this.f_96543_ / 2 + 8, 120, 80, 20, Component.m_237115_("structure_block.size.z"));
        this.f_99363_.m_94199_(15);
        this.f_99363_.m_94144_(Integer.toString($$1.m_123343_()));
        this.m_7787_(this.f_99363_);
        this.f_99364_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 152, 120, 80, 20, Component.m_237115_("structure_block.integrity.integrity"));
        this.f_99364_.m_94199_(15);
        this.f_99364_.m_94144_(this.f_99382_.format(this.f_99391_.m_59827_()));
        this.m_7787_(this.f_99364_);
        this.f_99365_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 72, 120, 80, 20, Component.m_237115_("structure_block.integrity.seed"));
        this.f_99365_.m_94199_(31);
        this.f_99365_.m_94144_(Long.toString(this.f_99391_.m_59828_()));
        this.m_7787_(this.f_99365_);
        this.f_99366_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 152, 120, 240, 20, Component.m_237115_("structure_block.custom_data"));
        this.f_99366_.m_94199_(128);
        this.f_99366_.m_94144_(this.f_99391_.m_59907_());
        this.m_7787_(this.f_99366_);
        this.m_99464_();
        this.m_169838_(this.f_99394_);
        this.m_94718_(this.f_99357_);
    }

    @Override
    public void m_6574_(Minecraft p_99411_, int p_99412_, int p_99413_) {
        String $$3 = this.f_99357_.m_94155_();
        String $$4 = this.f_99358_.m_94155_();
        String $$5 = this.f_99359_.m_94155_();
        String $$6 = this.f_99360_.m_94155_();
        String $$7 = this.f_99361_.m_94155_();
        String $$8 = this.f_99362_.m_94155_();
        String $$9 = this.f_99363_.m_94155_();
        String $$10 = this.f_99364_.m_94155_();
        String $$11 = this.f_99365_.m_94155_();
        String $$12 = this.f_99366_.m_94155_();
        this.m_6575_(p_99411_, p_99412_, p_99413_);
        this.f_99357_.m_94144_($$3);
        this.f_99358_.m_94144_($$4);
        this.f_99359_.m_94144_($$5);
        this.f_99360_.m_94144_($$6);
        this.f_99361_.m_94144_($$7);
        this.f_99362_.m_94144_($$8);
        this.f_99363_.m_94144_($$9);
        this.f_99364_.m_94144_($$10);
        this.f_99365_.m_94144_($$11);
        this.f_99366_.m_94144_($$12);
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    private void m_99464_() {
        this.f_99371_.f_93623_ = true;
        this.f_99372_.f_93623_ = true;
        this.f_99373_.f_93623_ = true;
        this.f_99374_.f_93623_ = true;
        switch (this.f_99391_.m_59906_()) {
            case NONE: {
                this.f_99371_.f_93623_ = false;
                break;
            }
            case CLOCKWISE_180: {
                this.f_99373_.f_93623_ = false;
                break;
            }
            case COUNTERCLOCKWISE_90: {
                this.f_99374_.f_93623_ = false;
                break;
            }
            case CLOCKWISE_90: {
                this.f_99372_.f_93623_ = false;
            }
        }
    }

    private void m_169838_(StructureMode p_169839_) {
        this.f_99357_.m_94194_(false);
        this.f_99358_.m_94194_(false);
        this.f_99359_.m_94194_(false);
        this.f_99360_.m_94194_(false);
        this.f_99361_.m_94194_(false);
        this.f_99362_.m_94194_(false);
        this.f_99363_.m_94194_(false);
        this.f_99364_.m_94194_(false);
        this.f_99365_.m_94194_(false);
        this.f_99366_.m_94194_(false);
        this.f_99369_.f_93624_ = false;
        this.f_99370_.f_93624_ = false;
        this.f_99376_.f_93624_ = false;
        this.f_169835_.f_93624_ = false;
        this.f_99378_.f_93624_ = false;
        this.f_99371_.f_93624_ = false;
        this.f_99372_.f_93624_ = false;
        this.f_99373_.f_93624_ = false;
        this.f_99374_.f_93624_ = false;
        this.f_99379_.f_93624_ = false;
        this.f_99380_.f_93624_ = false;
        switch (p_169839_) {
            case SAVE: {
                this.f_99357_.m_94194_(true);
                this.f_99358_.m_94194_(true);
                this.f_99359_.m_94194_(true);
                this.f_99360_.m_94194_(true);
                this.f_99361_.m_94194_(true);
                this.f_99362_.m_94194_(true);
                this.f_99363_.m_94194_(true);
                this.f_99369_.f_93624_ = true;
                this.f_99376_.f_93624_ = true;
                this.f_169835_.f_93624_ = true;
                this.f_99379_.f_93624_ = true;
                break;
            }
            case LOAD: {
                this.f_99357_.m_94194_(true);
                this.f_99358_.m_94194_(true);
                this.f_99359_.m_94194_(true);
                this.f_99360_.m_94194_(true);
                this.f_99364_.m_94194_(true);
                this.f_99365_.m_94194_(true);
                this.f_99370_.f_93624_ = true;
                this.f_169835_.f_93624_ = true;
                this.f_99378_.f_93624_ = true;
                this.f_99371_.f_93624_ = true;
                this.f_99372_.f_93624_ = true;
                this.f_99373_.f_93624_ = true;
                this.f_99374_.f_93624_ = true;
                this.f_99380_.f_93624_ = true;
                this.m_99464_();
                break;
            }
            case CORNER: {
                this.f_99357_.m_94194_(true);
                break;
            }
            case DATA: {
                this.f_99366_.m_94194_(true);
            }
        }
    }

    private boolean m_99403_(StructureBlockEntity.UpdateType p_99404_) {
        BlockPos $$1 = new BlockPos(this.m_99435_(this.f_99358_.m_94155_()), this.m_99435_(this.f_99359_.m_94155_()), this.m_99435_(this.f_99360_.m_94155_()));
        Vec3i $$2 = new Vec3i(this.m_99435_(this.f_99361_.m_94155_()), this.m_99435_(this.f_99362_.m_94155_()), this.m_99435_(this.f_99363_.m_94155_()));
        float $$3 = this.m_99430_(this.f_99364_.m_94155_());
        long $$4 = this.m_99426_(this.f_99365_.m_94155_());
        this.f_96541_.m_91403_().m_104955_(new ServerboundSetStructureBlockPacket(this.f_99391_.m_58899_(), p_99404_, this.f_99391_.m_59908_(), this.f_99357_.m_94155_(), $$1, $$2, this.f_99391_.m_59905_(), this.f_99391_.m_59906_(), this.f_99366_.m_94155_(), this.f_99391_.m_59910_(), this.f_99391_.m_59834_(), this.f_99391_.m_59835_(), $$3, $$4));
        return true;
    }

    private long m_99426_(String p_99427_) {
        try {
            return Long.valueOf(p_99427_);
        }
        catch (NumberFormatException $$1) {
            return 0L;
        }
    }

    private float m_99430_(String p_99431_) {
        try {
            return Float.valueOf(p_99431_).floatValue();
        }
        catch (NumberFormatException $$1) {
            return 1.0f;
        }
    }

    private int m_99435_(String p_99436_) {
        try {
            return Integer.parseInt(p_99436_);
        }
        catch (NumberFormatException $$1) {
            return 0;
        }
    }

    @Override
    public void m_7379_() {
        this.m_99447_();
    }

    @Override
    public boolean m_7933_(int p_99400_, int p_99401_, int p_99402_) {
        if (super.m_7933_(p_99400_, p_99401_, p_99402_)) {
            return true;
        }
        if (p_99400_ == 257 || p_99400_ == 335) {
            this.m_99444_();
            return true;
        }
        return false;
    }

    @Override
    public void m_6305_(PoseStack p_99406_, int p_99407_, int p_99408_, float p_99409_) {
        this.m_7333_(p_99406_);
        StructureMode $$4 = this.f_99391_.m_59908_();
        StructureBlockEditScreen.m_93215_(p_99406_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 10, 0xFFFFFF);
        if ($$4 != StructureMode.DATA) {
            StructureBlockEditScreen.m_93243_(p_99406_, this.f_96547_, f_99381_, this.f_96543_ / 2 - 153, 30, 0xA0A0A0);
            this.f_99357_.m_6305_(p_99406_, p_99407_, p_99408_, p_99409_);
        }
        if ($$4 == StructureMode.LOAD || $$4 == StructureMode.SAVE) {
            StructureBlockEditScreen.m_93243_(p_99406_, this.f_96547_, f_99383_, this.f_96543_ / 2 - 153, 70, 0xA0A0A0);
            this.f_99358_.m_6305_(p_99406_, p_99407_, p_99408_, p_99409_);
            this.f_99359_.m_6305_(p_99406_, p_99407_, p_99408_, p_99409_);
            this.f_99360_.m_6305_(p_99406_, p_99407_, p_99408_, p_99409_);
            StructureBlockEditScreen.m_93243_(p_99406_, this.f_96547_, f_99387_, this.f_96543_ / 2 + 154 - this.f_96547_.m_92852_(f_99387_), 150, 0xA0A0A0);
        }
        if ($$4 == StructureMode.SAVE) {
            StructureBlockEditScreen.m_93243_(p_99406_, this.f_96547_, f_99384_, this.f_96543_ / 2 - 153, 110, 0xA0A0A0);
            this.f_99361_.m_6305_(p_99406_, p_99407_, p_99408_, p_99409_);
            this.f_99362_.m_6305_(p_99406_, p_99407_, p_99408_, p_99409_);
            this.f_99363_.m_6305_(p_99406_, p_99407_, p_99408_, p_99409_);
            StructureBlockEditScreen.m_93243_(p_99406_, this.f_96547_, f_99388_, this.f_96543_ / 2 + 154 - this.f_96547_.m_92852_(f_99388_), 110, 0xA0A0A0);
            StructureBlockEditScreen.m_93243_(p_99406_, this.f_96547_, f_99389_, this.f_96543_ / 2 + 154 - this.f_96547_.m_92852_(f_99389_), 70, 0xA0A0A0);
        }
        if ($$4 == StructureMode.LOAD) {
            StructureBlockEditScreen.m_93243_(p_99406_, this.f_96547_, f_99385_, this.f_96543_ / 2 - 153, 110, 0xA0A0A0);
            this.f_99364_.m_6305_(p_99406_, p_99407_, p_99408_, p_99409_);
            this.f_99365_.m_6305_(p_99406_, p_99407_, p_99408_, p_99409_);
            StructureBlockEditScreen.m_93243_(p_99406_, this.f_96547_, f_99390_, this.f_96543_ / 2 + 154 - this.f_96547_.m_92852_(f_99390_), 70, 0xA0A0A0);
        }
        if ($$4 == StructureMode.DATA) {
            StructureBlockEditScreen.m_93243_(p_99406_, this.f_96547_, f_99386_, this.f_96543_ / 2 - 153, 110, 0xA0A0A0);
            this.f_99366_.m_6305_(p_99406_, p_99407_, p_99408_, p_99409_);
        }
        StructureBlockEditScreen.m_93243_(p_99406_, this.f_96547_, $$4.m_61811_(), this.f_96543_ / 2 - 153, 174, 0xA0A0A0);
        super.m_6305_(p_99406_, p_99407_, p_99408_, p_99409_);
    }

    @Override
    public boolean m_7043_() {
        return false;
    }
}

