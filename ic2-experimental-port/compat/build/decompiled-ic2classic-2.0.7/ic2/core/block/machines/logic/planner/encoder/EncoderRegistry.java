/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.Tuple
 */
package ic2.core.block.machines.logic.planner.encoder;

import com.google.common.base.Strings;
import ic2.core.block.machines.logic.planner.encoder.IEncoder;
import ic2.core.block.machines.logic.planner.encoder.ReactorSetup;
import ic2.core.block.machines.logic.planner.encoder.impl.OldNBTEncoder;
import ic2.core.block.machines.logic.planner.encoder.impl.TalonEncoder;
import ic2.core.block.machines.logic.planner.encoder.impl.V1Encoder;
import ic2.core.block.machines.logic.planner.encoder.impl.V2Encoder;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tuple;

public class EncoderRegistry {
    public static final EncoderRegistry INSTANCE = new EncoderRegistry();
    protected Map<String, IEncoder> encoderMap = new LinkedHashMap<String, IEncoder>();
    protected Map<String, String> idToNameMap = new LinkedHashMap<String, String>();
    protected String defaultEncoder;

    public void init() {
        this.registerEncoder("OldNBT", new OldNBTEncoder());
        this.registerEncoder("Talon", new TalonEncoder());
        this.registerEncoder("V1", new V1Encoder());
        this.registerEncoder("V2", new V2Encoder());
        this.setDefaultEncoder("V2");
    }

    public void setDefaultEncoder(String id) {
        this.defaultEncoder = id;
    }

    public String getDefaultEncoderID() {
        return this.defaultEncoder;
    }

    public IEncoder getDefaultEncoder() {
        return this.encoderMap.get(this.getDefaultEncoderID());
    }

    public void registerEncoder(String id, IEncoder encoder) {
        if (encoder == null || id == null) {
            return;
        }
        this.encoderMap.put(id, encoder);
        this.idToNameMap.put(id, encoder.getName());
    }

    public IEncoder getEncoderFromID(String id) {
        return this.encoderMap.get(id);
    }

    public Tuple<String, CompoundTag> createDecodedMessage(String text) {
        if (Strings.isNullOrEmpty((String)text)) {
            return null;
        }
        for (Map.Entry<String, IEncoder> entry : this.encoderMap.entrySet()) {
            IEncoder encoder = entry.getValue();
            CompoundTag nbt = encoder.createDecodedData(text);
            if (nbt == null) continue;
            return new Tuple((Object)entry.getKey(), (Object)nbt);
        }
        return null;
    }

    public ReactorSetup createDecodedSetup(String text, boolean simulate) {
        if (Strings.isNullOrEmpty((String)text)) {
            return null;
        }
        for (IEncoder encoder : this.encoderMap.values()) {
            ReactorSetup data = encoder.createSetup(text, simulate);
            if (data == null) continue;
            return data;
        }
        return null;
    }

    public Iterable<Tuple<String, String>> getChoiceList() {
        ObjectList list = CollectionUtils.createList();
        for (Map.Entry<String, String> entry : this.idToNameMap.entrySet()) {
            list.add((Tuple)new Tuple((Object)entry.getKey(), (Object)entry.getValue()));
        }
        return list;
    }
}

