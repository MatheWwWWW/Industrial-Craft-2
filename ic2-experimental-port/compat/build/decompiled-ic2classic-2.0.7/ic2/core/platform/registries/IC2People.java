/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.platform.registries;

import ic2.core.utils.MinecraftPlayer;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import java.util.UUID;

public class IC2People {
    private static final List<MinecraftPlayer> DEV_TEAM = CollectionUtils.createList();
    private static final List<MinecraftPlayer> HELPERS = CollectionUtils.createList();
    public static MinecraftPlayer SPEIGER = IC2People.addTeamMember("721f4109b6a2483789559a4d9d37c9a8", "Speiger");
    public static MinecraftPlayer MEDURIS = IC2People.addTeamMember("5680934c7620476d822f4bcce051fa98", "MedurisDev");
    public static MinecraftPlayer DAENARA = IC2People.addTeamMember("0859a3aff06242ae820cf8c0eb6bd471", "Daenara");
    public static MinecraftPlayer RAZZOKK = IC2People.addTeamMember("2eea59074eaa45158ef68560094891e3", "Razzokk");
    public static MinecraftPlayer QUIZZI = IC2People.addTeamMember("e7c7670c964b47df8ef47b046ae9fe8d", "quizzi");
    public static MinecraftPlayer MINESASHA = IC2People.addTeamMember("fcf721ea2e9848eba3c00a7e88e7a9b8", "MineSasha");
    public static MinecraftPlayer CHOCOHEAD = IC2People.addTeamMember("66580d8e19ad4564a8f1448d021be321", "Chocohead");
    public static MinecraftPlayer DR0N1K = IC2People.addTeamMember("d2b63da992c64352b92603634dbb652a", "dr0n1k");

    static MinecraftPlayer addTeamMember(String UUID2, String name) {
        MinecraftPlayer player = new MinecraftPlayer(UUID2, name);
        DEV_TEAM.add(player);
        return player;
    }

    static MinecraftPlayer addHelper(String UUID2, String name) {
        MinecraftPlayer player = new MinecraftPlayer(UUID2, name);
        HELPERS.add(player);
        return player;
    }

    public static void init() {
        IC2People.addTeamMember("380df991-f603-344c-a090-369bad2a924a", "Dev");
        IC2People.helpers();
        IC2People.testers();
    }

    public static void helpers() {
        IC2People.addHelper("68af6d40a79e4a2998469d4a319ec774", "GregoriusTechneticies");
        IC2People.addHelper("1964e3d1650040e79ff2e6161d41a8c2", "Bear989");
        IC2People.addHelper("cafb70c2d8e240d68dcc5c977530be9c", "MainFreak");
        IC2People.addHelper("8c36e7a2faba4bbe89b56bc6564ec0d5", "Lyra");
        IC2People.addHelper("ad3e5ba2e0214141b567827ede0b806a", "ToAsgaard");
        IC2People.addHelper("b2ac8c03d99448059e0f57fede63c04d", "KitsuneAlexx");
    }

    public static void testers() {
    }

    public static boolean isDevTeam(UUID id) {
        for (MinecraftPlayer player : DEV_TEAM) {
            if (!id.equals(player.getUUID())) continue;
            return true;
        }
        return false;
    }

    public static MinecraftPlayer findHelper(String name) {
        for (MinecraftPlayer player : HELPERS) {
            if (!player.getName().equals(name)) continue;
            return player;
        }
        return null;
    }
}

