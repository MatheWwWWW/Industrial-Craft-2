$ErrorActionPreference = 'Stop'

$minecraftRoot = Split-Path -Parent $PSScriptRoot
$modsRoot = Join-Path $minecraftRoot 'mods'
$reportPath = Join-Path $PSScriptRoot 'compat\build\active-mod-audit.txt'

Add-Type -AssemblyName System.IO.Compression
Add-Type -AssemblyName System.IO.Compression.FileSystem

function Get-ZipText([string]$archivePath, [string]$entryName) {
    $archive = [IO.Compression.ZipFile]::OpenRead($archivePath)
    try {
        $entry = $archive.GetEntry($entryName)
        if ($null -eq $entry) {
            return $null
        }
        $reader = [IO.StreamReader]::new($entry.Open(), [Text.Encoding]::UTF8)
        try {
            return $reader.ReadToEnd()
        } finally {
            $reader.Dispose()
        }
    } finally {
        $archive.Dispose()
    }
}

$records = [Collections.Generic.List[object]]::new()
$dependencies = [Collections.Generic.List[object]]::new()
$unidentified = [Collections.Generic.List[string]]::new()

foreach ($jar in Get-ChildItem -LiteralPath $modsRoot -File -Filter '*.jar' | Sort-Object Name) {
    $toml = Get-ZipText $jar.FullName 'META-INF/mods.toml'
    if ($null -eq $toml) {
        $fabric = Get-ZipText $jar.FullName 'fabric.mod.json'
        if ($null -eq $fabric) {
            $unidentified.Add($jar.Name)
            continue
        }
        $json = $fabric | ConvertFrom-Json
        $records.Add([pscustomobject]@{
            Id = [string]$json.id
            Version = [string]$json.version
            Jar = $jar.Name
            Loader = 'fabric-metadata'
        })
        continue
    }

    foreach ($match in [regex]::Matches(
            $toml,
            '(?ms)^\s*\[\[mods\]\]\s*(.*?)(?=^\s*\[\[|\z)')) {
        $block = $match.Groups[1].Value
        $id = [regex]::Match($block, '(?m)^\s*modId\s*=\s*"([^"]+)"').Groups[1].Value
        $version = [regex]::Match($block, '(?m)^\s*version\s*=\s*"([^"]+)"').Groups[1].Value
        if (-not [string]::IsNullOrWhiteSpace($id)) {
            $records.Add([pscustomobject]@{
                Id = $id
                Version = $version
                Jar = $jar.Name
                Loader = 'forge'
            })
        }
    }

    foreach ($match in [regex]::Matches(
            $toml,
            '(?ms)^\s*\[\[dependencies\.([^\]]+)\]\]\s*(.*?)(?=^\s*\[\[|\z)')) {
        $owner = $match.Groups[1].Value
        $block = $match.Groups[2].Value
        $id = [regex]::Match($block, '(?m)^\s*modId\s*=\s*"([^"]+)"').Groups[1].Value
        $range = [regex]::Match($block, '(?m)^\s*versionRange\s*=\s*"([^"]+)"').Groups[1].Value
        $mandatory = [regex]::Match($block, '(?m)^\s*mandatory\s*=\s*(true|false)').Groups[1].Value
        $dependencies.Add([pscustomobject]@{
            Owner = $owner
            Dependency = $id
            Range = $range
            Mandatory = $mandatory
            Jar = $jar.Name
        })
    }
}

$duplicates = $records | Group-Object Id | Where-Object Count -gt 1
$ic2Dependencies = $dependencies | Where-Object Dependency -in @(
    'ic2', 'gravisuit', 'advanced_solars', 'ic2_experimental_fidelity')

$lines = [Collections.Generic.List[string]]::new()
$lines.Add("Active JARs: $((Get-ChildItem -LiteralPath $modsRoot -File -Filter '*.jar').Count)")
$lines.Add("Declared mod ids: $($records.Count)")
$lines.Add("Duplicate mod ids: $($duplicates.Count)")
foreach ($duplicate in $duplicates) {
    $sources = ($duplicate.Group | ForEach-Object { "$($_.Jar) [$($_.Version)]" }) -join '; '
    $lines.Add("DUPLICATE $($duplicate.Name): $sources")
}
$lines.Add('')
$lines.Add('IC2-family dependencies:')
foreach ($dependency in $ic2Dependencies | Sort-Object Owner,Dependency,Jar) {
    $lines.Add("$($dependency.Owner) -> $($dependency.Dependency) $($dependency.Range) mandatory=$($dependency.Mandatory) jar=$($dependency.Jar)")
}
$lines.Add('')
$lines.Add('JARs without Forge/Fabric mod metadata:')
foreach ($jar in $unidentified) {
    $lines.Add($jar)
}

[IO.File]::WriteAllLines($reportPath, $lines, [Text.UTF8Encoding]::new($false))
$lines
