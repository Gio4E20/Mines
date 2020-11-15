<!-- PROJECT SHIELDS -->

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]


<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/giovalgas/Mines">
    <img src="https://static.wikia.nocookie.net/minecraft/images/4/49/DiamondOreNew.png/revision/latest?cb=20190907223256" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">Mines Minigame</h3>

  <p align="center">
    Custom made Minecraft Minigame!
    <br />
    <br />
    <a href="https://youtu.be/jtKMocVHUZ0">View Demo</a>
    ·
    <a href="https://github.com/giovalgas/Mines/issues">Report Bug</a>
    ·
    <a href="https://github.com/giovalgas/Mines/issues">Request Feature</a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
  * [Configuration](#configuration)
* [Usage](#usage)
* [Contributing](#contributing)
* [Contact](#contact)

<!-- ABOUT THE PROJECT -->
## About The Project
<table>
  <tr>
    <td><img src="https://i.gyazo.com/c0e7ac75d1aa75e9fd20bf070abc958c.gif"></td>
    <td><img src="https://i.gyazo.com/608f626fdd470ab7f8ea5e7207e1acba.gif"></td>
  </tr>
 </table>

This minigame is a quick gamemode made with the intent of putting it in my portfolio.

Features:
* Randomly generated ores.
* Configurable.
* No lag.

### Built With
This project was built with:
* [Spigot](https://www.spigotmc.org/)
* [Java](https://java.com/pt-BR/)
* [Vault](https://dev.bukkit.org/projects/vault)


<!-- GETTING STARTED -->
## Getting Started

To get this minigame up and running you will only need to follow the following steps.

### Prerequisites

You will need these installed in your server before proceeding.

* [Vault](https://dev.bukkit.org/projects/vault)
* [Spigot](https://www.spigotmc.org/)


### Installation

1. Download the plugin at [https://github.com/giovalgas/Mines/releases](https://github.com/giovalgas/Mines/releases)
2. Put the downloaded jar into the plugins folder (yourServerFolder/plugins)

### Configuration

```yaml
#Config.yml - 'Mines'

#Worlds
numberOfLobbies: 1 # Requires a restart. It will crash otherwise
numberOfMines: 1 # Requires a restart. It will crash otherwise
numberOfSpawns: 6

spawnsX: [-259, -259, -259, -259, -259, -259] # SpawnsX: [AX,BX,CX] , SpawnsY: [AY,BY,CY], SpawnsZ: [AZ,BZ,CZ]
spawnsY: [7, 7, 7, 7, 7, 7] # SpawnsX: [AX,BX,CX] , SpawnsY: [AY,BY,CY], SpawnsZ: [AZ,BZ,CZ]
spawnsZ: [32, 26, 20, 14, 8, 2] # SpawnsX: [AX,BX,CX] , SpawnsY: [AY,BY,CY], SpawnsZ: [AZ,BZ,CZ]
yaw: 92
pitch: 4


#Players
maxNumberOfPlayers: 6
minNumberOfPlayers: 2
moneyOnWin: 6
moneyOnLoss: 3
diamondsToWin: 6
nightVision: true
#

#lobby
lobbySpawnX: [-240, -246] #Max, min
lobbySpawnY: 7
lobbySpawnZ: [16, 9] #Max, min
countdownTimer: 5 #seconds
#

#Ore generating
x: [-331, -271] #min, max
y: [1, 11] #min, max
z: [0, 35] #min, max

#A random number is generated from 1-100.
#To change those values you have to put in brackets: [bigger than, smaller than]
#Eg: diamond_ore: [0, 2], the number that is bigger than 0, and smaller than 2 is = 1,
#if the random number is = 1 a diamond ore will spawn.

diamond_ore: [0, 2]
gold_ore: [1, 12]
nether_quartz_ore: [12, 23]
coal_ore: [23, 34]
lapis_ore: [34, 45]
stone: [45, 56]
dirt: [56, 67]
redstone_ore: [67, 78]
emerald_ore: [78, 89]
iron_ore: [89, 100]
#
```

<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<!-- CONTACT -->
## Contact

Your Name - [@giovalgas](https://twitter.com/giovalgas) - giovalgascom@gmail.com

Project Link: [https://github.com/giovalgas/Mines](https://github.com/giovalgas/Mines)

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/giovalgas/Mines.svg?style=flat-square
[contributors-url]: https://github.com/giovalgas/Mines/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/giovalgas/Mines.svg?style=flat-square
[forks-url]: https://github.com/giovalgas/Mines/network/members
[stars-shield]: https://img.shields.io/github/stars/giovalgas/Mines.svg?style=flat-square
[stars-url]: https://github.com/giovalgas/Mines/stargazers
[issues-shield]: https://img.shields.io/github/issues/giovalgas/Mines.svg?style=flat-square
[issues-url]: https://github.com/giovalgas/Mines/issues


