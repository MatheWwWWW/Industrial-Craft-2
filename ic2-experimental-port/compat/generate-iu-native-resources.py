"""Rebuild source resources for the independent IC2 adaptations from local archives.

Only assets are imported. No IU/addon bytecode enters the companion.
All recipes below are new IC2 recipes, not the IU machine recipe chain.
"""
from pathlib import Path
import json
import zipfile
import hashlib
import re

ROOT = Path(__file__).resolve().parent
RES = ROOT / "src/main/resources"
JAVA = ROOT / "src/main/java/ru/mot/ic2exfidelity/iu"
VERSIONS = {"diamondvein": "1.2", "powerutils": "1.8", "quantumgenerators": "1.8", "reactorplus": "1.1", "simplyquarries": "1.8", "wateringcan": "1.0"}

def write(path, data):
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(data if isinstance(data, str) else json.dumps(data, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")

def shaped(namespace, name, pattern, key, result, count=1):
    write(RES / f"data/{namespace}/recipes/native/{name}.json", {"type": "minecraft:crafting_shaped", "pattern": pattern, "key": {k: {"item": v} for k, v in key.items()}, "result": {"item": result, "count": count}})

def model(namespace, path, texture):
    write(RES / f"assets/{namespace}/models/item/{path}.json", {"parent": "minecraft:item/generated", "textures": {"layer0": texture}})

def loot(namespace, path, result=None):
    write(RES / f"data/{namespace}/loot_tables/blocks/{path}.json", {"type": "minecraft:block", "pools": [{"rolls": 1, "entries": [{"type": "minecraft:item", "name": result or f"{namespace}:{path}"}], "conditions": [{"condition": "minecraft:survives_explosion"}]}]})

def gui(namespace, path, body, height=166):
    write(RES / f"assets/{namespace}/guidef/{path}.xml", f'<?xml version="1.0" encoding="UTF-8"?>\n<gui width="{230 if height > 166 else 176}" height="{height}">\n<text y="6" align="center">%name%</text>\n{body}\n<playerInventory x="7" y="{height - 83}"/>\n</gui>\n')

def main():
    iu = zipfile.ZipFile(ROOT.parent / "upstream/mods/IndustrialUpgrade-1.19.2-3.4.0.9.jar")
    archives = {}
    originals = {}
    provenance = {}
    for namespace, version in VERSIONS.items():
        jar = ROOT.parent / f"upstream/mods/{namespace}-{version}.jar"
        if not jar.exists(): jar = ROOT.parent / f"smoke-instance/mods/{namespace}-{version}.jar"
        archive = zipfile.ZipFile(jar)
        archives[namespace] = archive
        provenance[jar.name] = hashlib.sha256(jar.read_bytes()).hexdigest()
        for name in archive.namelist():
            if name.startswith("assets/") and not name.endswith("/") and ("/textures/" in name or "/models/" in name):
                target = RES / name
                if "/models/" in name and name.endswith(".json"):
                    # The original Diamond Vein model omitted a comma between
                    # its stone and diamond textures; repair that source typo.
                    raw = archive.read(name).decode("utf-8")
                    raw = raw.replace('"industrialupgrade:models/deposits/stone"\n', '"industrialupgrade:models/deposits/stone",\n', 1)
                    value = json.loads(raw)
                    if value.get("parent", "").startswith("industrialupgrade:"):
                        value["parent"] = "minecraft:item/generated"
                    for key, texture in value.get("textures", {}).items():
                        if texture.startswith("industrialupgrade:"):
                            texture_path = texture.split(":", 1)[1]
                            original = f"assets/industrialupgrade/textures/{texture_path}.png"
                            if original not in iu.namelist():
                                raise ValueError(f"Missing author texture: {original}")
                            destination = RES / f"assets/{namespace}/textures/{texture_path}.png"
                            destination.parent.mkdir(parents=True, exist_ok=True)
                            destination.write_bytes(iu.read(original))
                            value["textures"][key] = f"{namespace}:{texture_path}"
                    write(target, value)
                else:
                    target.parent.mkdir(parents=True, exist_ok=True)
                    target.write_bytes(archive.read(name))
        originals[namespace] = {locale: json.loads(archive.read(f"assets/{namespace}/lang/{locale}.json")) for locale in ("en_us", "ru_ru")}
        classname = "Native" + namespace.title().replace("_", "") + "Mod"
        write(JAVA / f"{classname}.java", f'''package ru.mot.ic2exfidelity.iu;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
@Mod("{namespace}")
public final class {classname} {{
    public {classname}() {{ NativeIUContent.register("{namespace}", FMLJavaModLoadingContext.get().getModEventBus()); }}
}}
''')
    provenance["IndustrialUpgrade-1.19.2-3.4.0.9.jar"] = hashlib.sha256((ROOT.parent / "upstream/mods/IndustrialUpgrade-1.19.2-3.4.0.9.jar").read_bytes()).hexdigest()
    write(RES / "META-INF/ic2-native-iu-asset-sources.json", provenance)
    metadata = RES / "META-INF/mods.toml"
    text = metadata.read_text(encoding="utf-8").split("# BEGIN NATIVE IU ADAPTATIONS")[0].rstrip() + "\n\n# BEGIN NATIVE IU ADAPTATIONS\n"
    for namespace, version in VERSIONS.items():
        text += f'''\n[[mods]]
modId="{namespace}"
version="{version}-ic2-native"
displayName="{namespace} (IC2 adaptation)"
authors="Original addon authors; IC2 adaptation by MatheWwWWW"
description="Independent implementation using IC2 Experimental, with native IC2 recipes."
[[dependencies.{namespace}]]
modId="ic2"
mandatory=true
versionRange="[1.0,)"
ordering="AFTER"
side="BOTH"
[[dependencies.{namespace}]]
modId="ic2_experimental_fidelity"
mandatory=true
versionRange="[0.1.0,)"
ordering="AFTER"
side="BOTH"
'''
    write(metadata, text)
    en, ru = {}, {}
    def name(namespace, path, english, russian, block=False):
        key = ("block" if block else "item") + "." + namespace + "." + path.replace("/", ".")
        en.setdefault(namespace, {})[key] = english
        ru.setdefault(namespace, {})[key] = russian
    water = [("simple", "Watering can", "Лейка", "minecraft:iron_ingot"), ("adv", "Advanced watering can", "Продвинутая лейка", "ic2:bronze_ingot"), ("imp", "Improved watering can", "Улучшенная лейка", "ic2:steel_ingot")]
    for i, (prefix, english, russian, metal) in enumerate(water):
        path = prefix + "_watering_can"
        name("wateringcan", path, english, russian)
        shaped("wateringcan", path, [" M ", "MCM", " M "], {"M": metal, "C": "minecraft:bucket" if i == 0 else "wateringcan:" + water[i-1][0] + "_watering_can"}, "wateringcan:" + path)
    for mode in ("ic", "fe", "qe"):
        path = "module_" + mode
        name("powerutils", path, mode.upper() + " conversion module", "Модуль преобразования " + mode.upper())
        ingredient = {"ic": "minecraft:gold_ingot", "fe": "minecraft:quartz", "qe": "minecraft:ender_pearl"}[mode]
        shaped("powerutils", path, ["RRR", "ACA", "RRR"], {"R": "minecraft:redstone", "A": "ic2:circuit", "C": ingredient}, "powerutils:" + path)
    for mode in ("fe", "qe"):
        path = "converter/power_utilities_" + mode
        name("powerutils", path, "EU/" + mode.upper() + " converter", "Конвертер EU/" + mode.upper(), True)
        write(RES / f"assets/powerutils/blockstates/{path}.json", {"variants": {"": {"model": "powerutils:block/power_utilities_" + mode}}})
        loot("powerutils", path)
        shaped("powerutils", "converter_" + mode, ["CMC", "ABA", "CMC"], {"C": "powerutils:module_ic", "M": "powerutils:module_" + mode, "A": "ic2:advanced_circuit", "B": "ic2:advanced_machine"}, "powerutils:" + path)
        gui("powerutils", path, '<text x="7" y="22">{%base.getEnergyText()%}</text>\n<text x="7" y="34">{%base.get' + ("Forge" if mode == "fe" else "Quantum") + 'Text()%}</text>\n<button x="7" y="48" width="125" height="20" event="0">{%base.getConversionText()%}</button>\n<slotgrid name="upgrade" x="136" y="22" rows="2" columns="2"/>')
    path = "quantum_cable"
    name("powerutils", path, "Quantum cable", "Квантовый кабель", True)
    write(RES / "assets/powerutils/models/block/quantum_cable.json", {"parent": "minecraft:block/cube_all", "textures": {"all": "powerutils:items/module_qe"}})
    write(RES / "assets/powerutils/models/item/quantum_cable.json", {"parent": "powerutils:block/quantum_cable"})
    write(RES / "assets/powerutils/blockstates/quantum_cable.json", {"variants": {"": {"model": "powerutils:block/quantum_cable"}}})
    loot("powerutils", path)
    shaped("powerutils", path, ["III", "MCM", "III"], {"I": "ic2:insulated_gold_cable", "M": "powerutils:module_qe", "C": "ic2:advanced_circuit"}, "powerutils:quantum_cable", 8)
    gen_names = ["phsp_gen", "nsp_gen", "bsp_gen", "adsp_gen", "grasp_gen", "kvsp_gen"]
    for i, generator in enumerate(gen_names):
        path = "qg/" + generator
        name("quantumgenerators", path, f"Quantum generator (tier {i + 9})", f"Квантовый генератор (уровень {i + 9})", True)
        name("quantumgenerators", "core_" + generator, f"Quantum core (tier {i + 9})", f"Квантовое ядро (уровень {i + 9})")
        model("quantumgenerators", "core_" + generator, "ic2:items/crafting/advanced_circuit")
        write(RES / f"assets/quantumgenerators/blockstates/{path}.json", {"variants": {"": {"model": "quantumgenerators:block/" + generator}}})
        loot("quantumgenerators", path)
        core = "quantumgenerators:core_" + generator
        shaped("quantumgenerators", "core_" + generator, ["UUU", "UCU", "UUU"], {"U": "ic2:uu_matter", "C": "ic2:iridium" if i == 0 else "quantumgenerators:core_" + gen_names[i-1]}, core)
        shaped("quantumgenerators", generator, ["ICI", "CMC", "ICI"], {"I": "ic2:iridium", "C": core, "M": "ic2:mass_fabricator" if i == 0 else "quantumgenerators:qg/" + gen_names[i-1]}, "quantumgenerators:" + path)
        gui("quantumgenerators", path, '<text x="7" y="22">{%base.getQuantumText()%}</text>\n<text x="7" y="34">{%base.getGenerationText()%}</text>\n<button x="7" y="51" width="100" height="20" event="0">{%base.getModeText()%}</button>\n<button x="111" y="51" event="1">−</button>\n<button x="134" y="51" event="2">+</button>')
    quarry_names = ["simply_quarry", "adv_simply_quarry", "imp_simply_quarry", "per_simply_quarry", "pho_simply_quarry"]
    for i, quarry in enumerate(quarry_names):
        path = "simplyquarries/" + quarry
        name("simplyquarries", path, f"Ore quarry (tier {i+1})", f"Рудный карьер (уровень {i+1})", True)
        write(RES / f"assets/simplyquarries/blockstates/{path}.json", {"variants": {"": {"model": "simplyquarries:block/" + quarry}}})
        loot("simplyquarries", path)
        shaped("simplyquarries", quarry, ["ICI", "DBD", "ICI"], {"I": "ic2:steel_ingot" if i < 2 else "ic2:iridium", "C": "ic2:advanced_circuit", "D": "ic2:diamond_drill", "B": "ic2:miner" if i == 0 else "simplyquarries:simplyquarries/" + quarry_names[i-1]}, "simplyquarries:" + path)
        gui("simplyquarries", path, '<text x="7" y="22">{%base.getEnergyText()%}</text>\n<text x="7" y="34">{%base.getQuantumText()%}</text>\n<text x="7" y="47">{%base.getRangeText()%}</text>\n<button x="7" y="61" width="90" height="20" event="0">{%base.getModeText()%}</button>\n<button x="99" y="61" width="70" height="20" event="1">iu_native.reset</button>\n<button x="174" y="21" event="2">+</button>\n<button x="195" y="21" event="3">−</button>\n<button x="174" y="43" event="4">+</button>\n<button x="195" y="43" event="5">−</button>\n<button x="174" y="68" width="42" height="20" event="6">XP</button>\n<text x="174" y="95">{%base.getExperienceText()%}</text>\n<slotgrid name="modules" x="7" y="86" rows="1" columns="4"/>\n<slotgrid name="output" x="7" y="109" rows="3" columns="8"/>', 251)
    module_names = ["furnace", "speed_i", "speed_ii", "speed_iii", "speed_iv", "speed_v", "lucky_i", "lucky_ii", "lucky_iii", "depth_i", "depth_ii", "depth_iii", "blacklist", "whitelist", "macerator", "combmac"]
    labels = {"furnace": ("Smelting", "Плавка"), "speed": ("Efficiency", "Эффективность"), "lucky": ("Fortune", "Удача"), "depth": ("Range", "Область добычи"), "blacklist": ("Blacklist", "Чёрный список"), "whitelist": ("Whitelist", "Белый список"), "macerator": ("Macerating", "Дробление"), "combmac": ("Macerating and washing", "Дробление и промывка")}
    for module in module_names:
        base = module.split("_")[0]; tier = module.split("_")[1].upper() if "_" in module else ""
        name("simplyquarries", "module_" + module, labels[base][0] + " module " + tier, "Модуль «" + labels[base][1] + "» " + tier)
        model("simplyquarries", "module_" + module, "ic2:items/crafting/advanced_circuit")
        ingredient = {"furnace": "minecraft:furnace", "speed": "ic2:overclocker_upgrade", "lucky": "minecraft:lapis_block", "depth": "minecraft:ender_pearl", "blacklist": "minecraft:coal", "whitelist": "minecraft:paper", "macerator": "ic2:macerator", "combmac": "ic2:ore_washing_plant"}[base]
        if tier and tier != "I": ingredient = "simplyquarries:module_" + module_names[module_names.index(module)-1]
        shaped("simplyquarries", "module_" + module, ["RAR", "BCB", "RAR"], {"R": "minecraft:diamond" if base in ("lucky", "depth") else "ic2:steel_ingot", "A": "ic2:advanced_circuit", "B": ingredient, "C": "ic2:circuit"}, "simplyquarries:module_" + module)
    # The source addons use an IU-specific recipe machine chain. These recipes
    # deliberately expose a separate, explicit IC2 progression instead.
    fuel_names = ["uran233", "toriy", "neptunium", "americium", "curium", "proton", "california", "berkelium", "einsteinium", "fermium", "mendelevium", "nobelium", "lawrencium", "uranium", "mox"]
    fuel_ru = ["Уран-233", "Торий", "Нептуний", "Америций", "Кюрий", "Протонное топливо", "Калифорний", "Берклий", "Эйнштейний", "Фермий", "Менделевий", "Нобелий", "Лоуренсий", "Уран", "MOX"]
    for i, (fuel, russian) in enumerate(zip(fuel_names, fuel_ru)):
        spent = "reactorsplus/depleted_" + fuel + "_fuel_rod"
        name("reactorplus", spent, f"Depleted {fuel} fuel rod", f"Обеднённый стержень: {russian}")
        model("reactorplus", spent, "ic2:items/resource/nuclear/depleted_uranium")
        if i < 13:
            pellet = "reactorsplus/" + fuel + "_pellet"
            name("reactorplus", pellet, f"{fuel} fuel pellet", f"Топливная таблетка: {russian}")
            original_model = json.loads((RES / f"assets/reactorplus/models/item/reactorsplus/reactor{fuel}dual.json").read_text(encoding="utf-8"))
            model("reactorplus", pellet, original_model["textures"]["layer0"])
            shaped("reactorplus", fuel + "_pellet", ["UUU", "UPU", "UUU"], {"U": "ic2:uu_matter", "P": "ic2:uranium_235" if i == 0 else "reactorplus:reactorsplus/" + fuel_names[i-1] + "_pellet"}, "reactorplus:" + pellet)
        for size in ("dual", "quad"):
            path = "reactorsplus/" + (size + "_" + fuel + "_fuel_rod" if fuel in ("uranium", "mox") else "reactor" + fuel + size)
            name("reactorplus", path, f"Enhanced {size} {fuel} fuel rod", f"Усиленный {'сдвоенный' if size == 'dual' else 'счетверённый'} стержень: {russian}")
            base = "ic2:" + ("dual_" + fuel + "_fuel_rod" if fuel in ("uranium", "mox") else "fuel_rod")
            if i < 13: base = "reactorplus:reactorsplus/" + fuel + "_pellet"
            if size == "quad": base = "reactorplus:reactorsplus/" + ("dual_" + fuel + "_fuel_rod" if fuel in ("uranium", "mox") else "reactor" + fuel + "dual")
            shaped("reactorplus", fuel + "_" + size, ["PPP", "BCB", "PPP"], {"P": "ic2:iron_plate", "B": base, "C": "ic2:advanced_circuit"}, "reactorplus:" + path)
    for i, prefix in enumerate(("", "adv_", "imp_", "per_")):
        for component, label, russian, base in (("vent", "Heat vent", "Теплоотвод", "ic2:heat_vent"), ("heat_exchange", "Heat exchanger", "Теплообменник", "ic2:heat_exchanger"), ("component_vent", "Component heat vent", "Компонентный теплоотвод", "ic2:component_heat_vent")):
            path = "reactorsplus/" + prefix + component
            name("reactorplus", path, f"{label} (tier {i+1})", f"{russian} (уровень {i+1})")
            if i: base = "reactorplus:reactorsplus/" + ("", "adv_", "imp_")[i-1] + component
            shaped("reactorplus", prefix + component, ["PCP", "ABA", "PCP"], {"P": "ic2:iron_plate" if i < 2 else "ic2:iridium", "C": "ic2:advanced_circuit", "A": "ic2:steel_ingot", "B": base}, "reactorplus:" + path)
    deposit = "diamond_deposits/deposits_diamond"
    name("diamondvein", deposit, "Diamond deposit", "Алмазное месторождение", True)
    write(RES / f"assets/diamondvein/blockstates/{deposit}.json", {"variants": {"": {"model": "diamondvein:block/" + deposit}}})
    loot("diamondvein", deposit, "minecraft:diamond_ore")
    write(RES / "data/diamondvein/worldgen/configured_feature/diamond_vein.json", {"type": "diamondvein:diamond_vein", "config": {}})
    write(RES / "data/diamondvein/worldgen/placed_feature/diamond_vein.json", {"feature": "diamondvein:diamond_vein", "placement": [{"type": "minecraft:rarity_filter", "chance": 64}, {"type": "minecraft:in_square"}, {"type": "minecraft:biome"}]})
    write(RES / "data/diamondvein/forge/biome_modifier/diamond_vein.json", {"type": "forge:add_features", "biomes": "#minecraft:is_overworld", "features": "diamondvein:diamond_vein", "step": "underground_ores"})
    common_en = {"iu_native.running": "Running", "iu_native.paused": "Paused", "iu_native.finished": "Finished", "iu_native.reset": "Restart", "iu_native.watering.info": "%s / %s mB water; radius %s", "iu_native.filter.info": "Use on blocks to add a filter. Sneak-use resets the list.", "iu_native.fuel.info": "%s EU/t; %s heat per reactor tick"}
    common_ru = {"iu_native.running": "Работает", "iu_native.paused": "Пауза", "iu_native.finished": "Завершено", "iu_native.reset": "Заново", "iu_native.watering.info": "%s / %s мБ воды; радиус %s", "iu_native.filter.info": "Нажмите на блок для добавления в фильтр. Shift сбрасывает список.", "iu_native.fuel.info": "%s EU/т; %s тепла за такт реактора"}
    for namespace in VERSIONS:
        write(RES / f"assets/{namespace}/lang/en_us.json", en[namespace])
        write(RES / f"assets/{namespace}/lang/ru_ru.json", ru[namespace])
    for locale, common in (("en_us", common_en), ("ru_ru", common_ru)):
        path = RES / f"assets/ic2/lang/{locale}.json"
        contents = json.loads(path.read_text(encoding="utf-8")); contents.update(common); write(path, contents)
    pickaxe = RES / "data/minecraft/tags/blocks/mineable/pickaxe.json"
    values = json.loads(pickaxe.read_text(encoding="utf-8"))["values"] if pickaxe.exists() else []
    values += [f"powerutils:converter/power_utilities_{m}" for m in ("fe", "qe")] + [f"quantumgenerators:qg/{n}" for n in gen_names] + [f"simplyquarries:simplyquarries/{n}" for n in quarry_names] + ["diamondvein:" + deposit, "powerutils:quantum_cable"]
    write(pickaxe, {"replace": False, "values": list(dict.fromkeys(values))})
    count = sum(len(list((RES / f"data/{namespace}/recipes/native").glob("*.json"))) for namespace in VERSIONS)
    print(f"Generated source assets, {count} IC2 recipes, six mod entry points and languages.")

if __name__ == "__main__": main()
