/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 */
package ic2.core.platform.player;

import ic2.core.IC2;
import ic2.core.block.machines.logic.planner.encoder.EncoderRegistry;
import ic2.core.block.machines.logic.planner.encoder.ReactorSetup;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

public class ReactorSubmissions {
    public static final ReactorSubmissions INSTANCE = new ReactorSubmissions();
    Set<String> submissions = CollectionUtils.createLinkedSet();
    List<Map.Entry<String, ReactorSetup>> compiledSubmissions = new ObjectArrayList();
    boolean hasToShuffle = true;

    private ReactorSubmissions() {
    }

    public void init() {
        this.addSetups("PrincessOfSalt", "1bgibpnlau2tqehau1hlus6xruld8vpj4lvt8t3vqq3707vu6e3s1fq2vo3pf2nm", "6hejf7vrhxzq3mlc1nua2c4mtuajpjmk0q36", "2jgffdewoonw8k6i7alfcod810zk9v4lmojb98xoex1n2wsapyc9fnhxk3mqthkm", "rvffr4ulhgrorshz1x0vzslw86iwdde70f2gsw4l4v7vaizd8k41g9wu71l9e", "6pvy7jshuxmfybv9z9w5w45btvqhh7ymm", "2yrh4iugafj7dcsioc7l1qygw7mv68y5fy", "3kgr780zn2hv1qwulxxoqxd1eeh1wabsb942q7wpzejqbr2wle5guazsoug0pmwo706", "5rd9iqdwhla428z7aju5edsonmmh3kojvp57ci62uwa4y", "xbhoswl1jpabaw6duu1uzj11ldr2yky1u5g2", "8qdaqds6cdjz7wwd9m5boqdqdgfg2ae0qz09gjrikny");
        this.addSetups("Dovahkiin0_0", "2fu86x00j401zacfyfqneo2", "39vnmyr40lbw6xjq2mnfhw2j8qohuqxgpsevpcq5ua", "fyxwgdgb0f727ogtrsgroqmxxxc2", "11aw4c1zy2vn3j5u72abk1ic9brntudzfreih2tafm", "1qc3edggsye94vq236wkqleeqcktifyt8p09y1q1i", "3xaqahccmxo7uwgxxwevyzmpxspdu46bee3pe47g72msdjf0433eieuivrcajynq", "g4pwlgz1i61nl8lg676uztjnqgujra890tqu23p9650p7gwk855y");
        this.addSetups("Speiger", "d3ebyev1s7u7aiv5xklbhi08inbhw4pbyter795usi", "ixy7m73q5uls15p3jcvi1bs9tb8ci", "1pqa88rh6bcaimxz6vyvv37qxmyksv4r32rex3ppa48qmu34qnvwcxe78xit9hee7mq");
    }

    public void addSetups(String user, String ... setups) {
        if (!this.hasToShuffle) {
            throw new IllegalStateException("Data was already collected...");
        }
        for (String setup : setups) {
            if (this.submissions.add(setup)) {
                this.compiledSubmissions.add(Map.entry(user, EncoderRegistry.INSTANCE.createDecodedSetup(setup, true)));
                continue;
            }
            IC2.LOGGER.info("Setup: " + setup + ", is a Duplicate");
        }
    }

    public void getSubmissions(Consumer<Map.Entry<String, ReactorSetup>> acceptor) {
        if (this.hasToShuffle) {
            Collections.shuffle(this.compiledSubmissions, new Random(2512102128L));
            this.hasToShuffle = false;
        }
        this.compiledSubmissions.forEach(acceptor);
    }
}

