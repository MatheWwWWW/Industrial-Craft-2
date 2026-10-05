#!/usr/bin/env python3
"""Check Russian localization in the built or installed fidelity jars."""

from __future__ import annotations

import argparse
import json
import re
from collections import Counter
from pathlib import Path
from zipfile import ZipFile

PORT = Path(__file__).resolve().parent
SOURCE = PORT / "compat" / "src" / "main"
JAR_NAMES = (
    "industrialcraft-2-2.9.162+ex119-fidelity-1.19.2-forge.jar",
    "IC2-Experimental-Fidelity-0.1.0.jar",
)
UNCHANGED_NAMES = {"itemGroup.ic2.general", "item.ic2.pesd"}
EMPTY_FRAGMENTS = {
    "item.ic2.booze_mug.solid.plain",
    "item.ic2.booze_mug.hops.plain",
}
FORMAT = re.compile(r"%(?:(\d+)\$)?([sdif])|%%")
KEY = re.compile(
    r'"((?:tooltip\.|tile_info\.|item_info\.|message\.|key\.|gui\.|'
    r'container\.|translation\.|iu_native\.|ic2\.tooltip\.|ic2\.crop\.|ic2\.jetpackAttached)'
    r'[^"\s]*)"'
)


def unique_object(pairs: list[tuple[str, str]]) -> dict[str, str]:
    result = {}
    for key, value in pairs:
        if key in result:
            raise ValueError(f"Duplicate translation key: {key}")
        result[key] = value
    return result


def load(raw: str | bytes) -> dict[str, str]:
    data = json.loads(raw, object_pairs_hook=unique_object)
    if not isinstance(data, dict) or any(
        not isinstance(value, str) for value in data.values()
    ):
        raise ValueError("Language files must contain string values")
    return data


def placeholders(value: str) -> Counter[tuple[int, str]]:
    result: Counter[tuple[int, str]] = Counter()
    implicit_index = 0
    for match in FORMAT.finditer(value):
        if match.group() == "%%":
            continue
        position, kind = match.groups()
        if position is None:
            implicit_index += 1
            position = str(implicit_index)
        result[(int(position), kind)] += 1
    return result


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--jar-dir", type=Path, default=PORT / "compat" / "build")
    args = parser.parse_args()
    errors: list[str] = []
    effective: dict[str, dict[str, dict[str, str]]] = {}
    packaged: list[dict[str, dict[str, str]]] = []
    for name in JAR_NAMES:
        languages = {}
        with ZipFile(args.jar_dir / name) as archive:
            for path in archive.namelist():
                if not path.endswith(("/lang/en_us.json", "/lang/ru_ru.json")):
                    continue
                data = load(archive.read(path))
                languages[path] = data
                namespace = path.split("/")[1]
                locale = Path(path).stem
                merged = effective.setdefault(namespace, {}).setdefault(locale, {})
                for key in data.keys() & merged.keys():
                    if merged[key] != data[key]:
                        errors.append(f"Conflicting jar translations: {path}: {key}")
                merged.update(data)
        packaged.append(languages)

    for namespace, languages in sorted(effective.items()):
        english = languages.get("en_us", {})
        russian = languages.get("ru_ru", {})
        for key, value in english.items():
            if key not in russian:
                errors.append(f"Missing Russian translation: {key}")
                continue
            translation = russian[key]
            if not translation.strip() and key not in EMPTY_FRAGMENTS:
                errors.append(f"Empty Russian translation: {key}")
            if placeholders(value) != placeholders(translation):
                errors.append(f"Mismatched format arguments: {key}")
            if value == translation and re.search(r"[A-Za-z]{3}", value):
                if key not in UNCHANGED_NAMES:
                    errors.append(f"Untranslated English text: {key}")
        print(f"{namespace}: {len(english)} English keys, {len(russian)} Russian keys")

    for path in (SOURCE / "resources" / "assets").glob("*/lang/*.json"):
        if path.stem not in {"en_us", "ru_ru"}:
            continue
        relative = path.relative_to(SOURCE / "resources").as_posix()
        expected = load(path.read_text(encoding="utf-8"))
        # IC2 overlays must also be present in the base jar for either load order.
        targets = packaged if path.parts[-3] == "ic2" else [packaged[1]]
        for target in targets:
            actual = target.get(relative, {})
            for key, value in expected.items():
                if actual.get(key) != value:
                    errors.append(f"Source translation absent from jar: {relative}: {key}")

    english_keys = set().union(
        *(languages.get("en_us", {}).keys() for languages in effective.values())
    )
    for source in (SOURCE / "java").rglob("*.java"):
        for key in KEY.findall(source.read_text(encoding="utf-8")):
            if not key.endswith(".") and key not in english_keys:
                errors.append(f"Missing translation key in {source.name}: {key}")
    for mode in ("enabled", "not_in_hand", "disabled"):
        key = f"ic2.tooltip.mode.{mode}"
        if key not in english_keys:
            errors.append(f"Missing charging battery mode: {key}")

    if errors:
        raise SystemExit("\n".join(sorted(set(errors))))
    print("Translation coverage, format arguments, source overlays and jar consistency: OK")


if __name__ == "__main__":
    main()
