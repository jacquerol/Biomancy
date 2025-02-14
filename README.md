<p align="center">
<img src="media/v2/text_logo_big.png" alt="Biomancy" height="150px"/>
<h1 align="center">Biomancy</h1>
</p>

<p align="center">
<a aria-label="Build Status" href="https://github.com/Elenterius/Biomancy/actions/workflows/gh_release.yml">
<img alt="" src="https://img.shields.io/github/actions/workflow/status/Elenterius/Biomancy/gh_release.yml?logo=github&style=for-the-badge"></a>

<a aria-label="Latest Release" href="https://github.com/Elenterius/Biomancy/releases/">
<img alt="" src="https://img.shields.io/github/v/release/elenterius/biomancy?include_prereleases&logo=github&style=for-the-badge"></a>

<a aria-label="Project Tracker" href="https://trello.com/b/GUKjOSAl">
<img alt="" src="https://img.shields.io/badge/Trello-0052CC?style=for-the-badge&logo=trello&logoColor=white"></a>

<a aria-label="Downloads on CurseForge" href="https://www.curseforge.com/minecraft/mc-mods/biomancy">
<img alt="" src="https://img.shields.io/endpoint?url=https%3A%2F%2Fdynamic-badge-formatter-ynrxn78r2oye.runkit.sh%2Fjson%3Furl%3Dhttps%253A%252F%252Fapi.curse.tools%252Fv1%252Fcf%252Fmods%252F492939%26query%3D%2524.data.downloadCount%26formatter%3Dmetric%26label%3D%2520%26labelColor%3Dgrey%26color%3DF16436%26logo%3Dcurseforge%26logoWidth%3D16%26style%3Dfor-the-badge%26cacheSeconds%3D86400%26suffix%3D%2520Downloads"></a>

<a aria-label="Biomancy Discord" href="https://discord.gg/424awTDdJJ">
<img alt="" src="https://img.shields.io/discord/920005236645572662?logo=discord&logoColor=white&color=5865F2&label=&style=for-the-badge"></a>

<a aria-label="Downloads on CurseForge" href="https://ko-fi.com/elenterius">
<img alt="" src="https://img.shields.io/badge/Ko--fi-F16061?logo=ko-fi&logoColor=white&style=for-the-badge"></a>
</p>

Biomancy is magi-tech Mod for Minecraft. The mod is inspired by Biopunk and Bio-Manipulation and features a fleshy art
style.

* [Download]
* [Discord]
* [Wiki]
* [Trello]

## Tech Stack

- [MinecraftForge](https://github.com/MinecraftForge/MinecraftForge) (modding API for Minecraft)
- [SpongePowered Mixin](https://github.com/SpongePowered/Mixin) (mixin and bytecode weaving framework)
- [GeckoLib](https://github.com/bernie-g/geckolib) (animation library)

### Integration with other Mods

- [JEI]

## Credits

- **RhinoW** for artwork and game design help
- **Shorepion** for sound desing and music
- **Selea** for general feeback and help with version 1.0

## License

All code is licensed under the [MIT License](https://opensource.org/licenses/MIT).

All artwork (images, textures, models, animations, etc.) is licensed under
the [Creative Commons Attribution-NonCommercial 4.0 International License](http://creativecommons.org/licenses/by-nc/4.0/)
, unless stated otherwise.

## Development

### Installation

- open project with IntelliJ IDEA
- wait for gradle project import to finish, you might have to cancel it if it gets stuck and reload the gradle project
- run gradle task genIntellijRuns (`Tasks > forgegradle runs > genIntellijRuns`)
- run 'Run Configuration' `runData` to generate/update resource pack & data pack stuff (recipes, tags, etc.)
  - see `datagen` package

### Maven

Atm you can use https://www.cursemaven.com/

### Contributing

Pull Request are welcome.

For new features or major changes related to the **gameplay** or **art style** please [join our Discord][Discord] and
request to join the dev team.<br>
This will give you access to the private mod development channels and resources such as the biomancy design document and
concept board.

You can track the development progress via our [Trello Board][Trello].

This project uses **Conventional Commits Messages** (https://www.conventionalcommits.org/en/v1.0.0/) to automatically
genereate
changelogs on release and to determine the semantic version bump.

## Support
If you need help feel free to [join our Discord][Discord].

## User Guide
The mod provides no ingame guide book but uses tooltip descriptions & flavor texts instead. If you need further information you can read the github [Wiki].

Read the [Getting Started Guide](https://github.com/Elenterius/Biomancy/wiki/v2/Getting-Started) section if you don't know what to do at all.

### Recipes
To conveniently look up recipes ingame I recommend the use of the [JEI] mod.


[Download]: https://www.curseforge.com/minecraft/mc-mods/biomancy
[Discord]: https://discord.gg/424awTDdJJ
[Wiki]: https://github.com/Elenterius/Biomancy/wiki/v2
[Trello]: https://trello.com/b/GUKjOSAl
[JitPack]: https://jitpack.io/#Elenterius/Biomancy

[JEI]:https://www.curseforge.com/minecraft/mc-mods/jei
