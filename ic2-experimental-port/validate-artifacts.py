#!/usr/bin/env python3
"""Validate the installed IC2 fidelity artifacts without launching Forge."""

from __future__ import annotations

import json
import re
from pathlib import Path
from zipfile import ZipFile


ROOT = Path(__file__).resolve().parent.parent
JARS = (
    ROOT / "mods" / "industrialcraft-2-2.9.162+ex119-fidelity-1.19.2-forge.jar",
    ROOT / "mods" / "IC2-Experimental-Fidelity-0.1.0.jar",
)
COMPANION = JARS[1]
RECIPE_SOURCE = (
    ROOT
    / "ic2-experimental-port"
    / "compat"
    / "src"
    / "main"
    / "java"
    / "ru"
    / "mot"
    / "ic2exfidelity"
    / "Ic2ExperimentalFidelity.java"
)
SOURCE_ROOT = RECIPE_SOURCE.parents[3]
EXPECTED_UNIQUE_RECIPE_IDS = 977
OWN_NAMESPACES = {"ic2", "advanced_solars", "gravisuit", "ic2_experimental_fidelity", "diamondvein", "powerutils", "quantumgenerators", "reactorplus", "simplyquarries", "wateringcan"}


def recipe_id(path: str) -> str:
    parts = path.split("/")
    return f"{parts[1]}:{'/'.join(parts[3:])[:-5]}"


def resource_path(kind: str, value: str, extension: str) -> str | None:
    if value.startswith("#"):
        return None
    namespace, separator, path = value.partition(":")
    if not separator:
        namespace, path = "minecraft", namespace
    if namespace not in OWN_NAMESPACES:
        return None
    return f"assets/{namespace}/{kind}/{path}{extension}"


def collect_model_references(value: object, references: list[str]) -> None:
    if not isinstance(value, dict):
        return
    model = value.get("model")
    if isinstance(model, str):
        target = resource_path("models", model, ".json")
        if target is not None:
            references.append(target)
    for nested in value.values():
        if isinstance(nested, dict):
            collect_model_references(nested, references)
        elif isinstance(nested, list):
            for entry in nested:
                collect_model_references(entry, references)


def collect_item_references(value: object, references: set[str]) -> None:
    if isinstance(value, dict):
        item = value.get("item")
        if isinstance(item, str):
            references.add(item)
        for nested in value.values():
            collect_item_references(nested, references)
    elif isinstance(value, list):
        for entry in value:
            collect_item_references(entry, references)


def main() -> None:
    ids: set[str] = set()
    invalid: list[tuple[str, str, str]] = []
    resource_counts: dict[str, int] = {}
    ids_by_jar: dict[str, set[str]] = {}
    resources: dict[str, bytes] = {}

    translated: set[str]
    with ZipFile(COMPANION) as companion:
        translated = set(
            companion.read("META-INF/ic2-fidelity-legacy-recipes.txt")
            .decode("utf-8")
            .splitlines()
        )

    source = RECIPE_SOURCE.read_text(encoding="utf-8")
    restored_block = source.split(
        "RESTORED_RECIPE_IDS = List.of(", 1
    )[1].split(");", 1)[0]
    restored = set(re.findall(r'"([^"]+)"', restored_block))
    expected = translated | restored

    for jar in JARS:
        with ZipFile(jar) as archive:
            for name in archive.namelist():
                if (
                    name.startswith(("assets/", "data/"))
                    or name == "pack.mcmeta"
                ) and not name.endswith("/"):
                    resources[name] = archive.read(name)
            recipes = [
                name
                for name in archive.namelist()
                if "/recipes/" in name and name.endswith(".json")
            ]
            resource_counts[jar.name] = len(recipes)
            jar_ids = ids_by_jar.setdefault(jar.name, set())
            for name in recipes:
                try:
                    json.loads(archive.read(name))
                except (UnicodeDecodeError, json.JSONDecodeError) as error:
                    invalid.append((jar.name, name, str(error)))
                identifier = recipe_id(name)
                ids.add(identifier)
                jar_ids.add(identifier)

    overlaps = set.intersection(*ids_by_jar.values())
    unexpected_overlaps = sorted(overlaps - expected)

    bad_resource_json: list[tuple[str, str]] = []
    parsed_json: dict[str, object] = {}
    for name, raw in resources.items():
        if not name.endswith((".json", ".mcmeta")):
            continue
        try:
            parsed_json[name] = json.loads(raw)
        except (UnicodeDecodeError, json.JSONDecodeError) as error:
            bad_resource_json.append((name, str(error)))

    model_dependencies: dict[str, set[str]] = {}
    for name, data in parsed_json.items():
        if "/models/" not in name or not name.endswith(".json"):
            continue
        dependencies = model_dependencies.setdefault(name, set())
        if not isinstance(data, dict):
            continue
        parent = data.get("parent")
        if isinstance(parent, str):
            target = resource_path("models", parent, ".json")
            if target is not None:
                dependencies.add(target)
        if data.get("loader") == "ic2:be":
            model_id = data.get("id")
            if isinstance(model_id, str):
                dynamic_model = resource_path("models", model_id, ".json")
                if dynamic_model is not None:
                    backing_model = dynamic_model.replace(
                        "/models/block/be/", "/models/block/", 1
                    )
                    dependencies.add(backing_model)

    recursive_models: list[list[str]] = []
    visit_state: dict[str, int] = {}
    visit_stack: list[str] = []

    def visit_model(name: str) -> None:
        state = visit_state.get(name, 0)
        if state == 1:
            cycle_start = visit_stack.index(name)
            recursive_models.append(visit_stack[cycle_start:] + [name])
            return
        if state == 2:
            return
        visit_state[name] = 1
        visit_stack.append(name)
        for dependency in model_dependencies.get(name, ()):
            if dependency in model_dependencies:
                visit_model(dependency)
        visit_stack.pop()
        visit_state[name] = 2

    for model_name in model_dependencies:
        visit_model(model_name)

    missing_assets: list[tuple[str, str]] = []
    for name, raw in resources.items():
        is_model = "/models/" in name and name.endswith(".json")
        is_blockstate = "/blockstates/" in name and name.endswith(".json")
        if not (is_model or is_blockstate):
            continue
        data = parsed_json.get(name)
        if not isinstance(data, dict):
            continue
        references: list[str] = []
        if is_model:
            parent = data.get("parent")
            if isinstance(parent, str):
                target = resource_path("models", parent, ".json")
                if target is not None:
                    references.append(target)
            textures = data.get("textures", {})
            if isinstance(textures, dict):
                for texture in textures.values():
                    if isinstance(texture, str):
                        target = resource_path("textures", texture, ".png")
                        if target is not None:
                            references.append(target)
        else:
            collect_model_references(data, references)
        for target in references:
            if target not in resources:
                missing_assets.append((name, target))

    missing_sounds: list[tuple[str, str]] = []
    for name, data in parsed_json.items():
        if not name.endswith("/sounds.json") or not isinstance(data, dict):
            continue
        for event in data.values():
            if not isinstance(event, dict):
                continue
            sounds = event.get("sounds", [])
            if not isinstance(sounds, list):
                continue
            for sound in sounds:
                sound_name = sound if isinstance(sound, str) else sound.get("name")
                if not isinstance(sound_name, str):
                    continue
                target = resource_path("sounds", sound_name, ".ogg")
                if target is not None and target not in resources:
                    missing_sounds.append((name, target))

    item_references: set[str] = set()
    for name, data in parsed_json.items():
        if "/recipes/" in name:
            collect_item_references(data, item_references)
    missing_item_models: list[str] = []
    for identifier in sorted(item_references):
        namespace, separator, path = identifier.partition(":")
        if not separator or namespace not in OWN_NAMESPACES:
            continue
        item_model = f"assets/{namespace}/models/item/{path}.json"
        blockstate = f"assets/{namespace}/blockstates/{path}.json"
        if item_model not in resources and blockstate not in resources:
            missing_item_models.append(identifier)

    source_text = "\n".join(
        path.read_text(encoding="utf-8") for path in SOURCE_ROOT.rglob("*.java")
    )
    code_texture_references = {
        f"assets/{namespace}/{path}"
        for namespace, path in re.findall(
            r'"(ic2|advanced_solars|gravisuit|ic2cuumatter):(textures/[^"]+\.png)"',
            source_text,
        )
    }
    code_texture_references.update(
        f"assets/{namespace}/{path}"
        for namespace, path in re.findall(
            r'new ResourceLocation\(\s*"(ic2|advanced_solars|gravisuit|ic2cuumatter)"\s*,\s*"(textures/[^"]+\.png)"\s*\)',
            source_text,
        )
    )
    missing_code_textures = sorted(code_texture_references - resources.keys())

    print(f"recipe_resources={resource_counts}")
    print(f"unique_recipe_ids={len(ids)}")
    print(f"translated_manifest_ids={len(translated)}")
    print(f"restored_source_ids={len(restored)}")
    print(f"expected_union_ids={len(expected)}")
    print(f"intentional_override_ids={len(overlaps)}")
    print(f"unexpected_override_ids={len(unexpected_overlaps)}")
    print(f"invalid_json={len(invalid)}")
    print(f"invalid_resource_json={len(bad_resource_json)}")
    print(f"recursive_model_dependencies={len(recursive_models)}")
    print(f"missing_internal_assets={len(missing_assets)}")
    print(f"missing_internal_sounds={len(missing_sounds)}")
    print(f"recipe_items_without_model_or_blockstate={len(missing_item_models)}")
    print(f"missing_literal_code_textures={len(missing_code_textures)}")
    for jar, name, error in invalid:
        print(f"INVALID {jar}!/{name}: {error}")
    for identifier in unexpected_overlaps:
        print(f"UNEXPECTED OVERRIDE {identifier}")
    for name, error in bad_resource_json:
        print(f"INVALID RESOURCE JSON {name}: {error}")
    for cycle in recursive_models:
        print(f"RECURSIVE MODEL {' -> '.join(cycle)}")
    for name, target in missing_assets:
        print(f"MISSING ASSET {name} -> {target}")
    for name, target in missing_sounds:
        print(f"MISSING SOUND {name} -> {target}")
    for identifier in missing_item_models:
        print(f"MISSING RECIPE ITEM MODEL {identifier}")
    for target in missing_code_textures:
        print(f"MISSING CODE TEXTURE {target}")

    missing = sorted(expected - ids)
    print(f"missing_expected_ids={len(missing)}")
    for identifier in missing:
        print(f"MISSING {identifier}")

    counts_match = (
        len(ids) == EXPECTED_UNIQUE_RECIPE_IDS
        and len(translated) == 149
        and len(restored) == 185
        and len(expected) == 334
    )
    if not counts_match:
        print("COUNT MISMATCH: update only after auditing the changed recipe set")

    if (
        invalid
        or bad_resource_json
        or recursive_models
        or missing_assets
        or missing_sounds
        or missing_item_models
        or missing_code_textures
        or missing
        or unexpected_overlaps
        or not counts_match
    ):
        raise SystemExit(1)


if __name__ == "__main__":
    main()
