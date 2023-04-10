![Total Downloads](https://img.shields.io/github/downloads/DaveJarvis/kmcaster/total?color=blue&label=Total%20Downloads&style=flat) ![Release Downloads](https://img.shields.io/github/downloads/DaveJarvis/kmcaster/latest/total?color=purple&label=Release%20Downloads&style=flat) ![Released](https://img.shields.io/github/release-date/DaveJarvis/kmcaster?color=red&style=flat&label=Released) ![Version](https://img.shields.io/github/v/release/DaveJarvis/kmcaster?style=flat&label=Release)

# Introduction

Java-based on-screen display (OSD) for keyboard and mouse events.

This program displays keyboard and mouse events for the purpose of screencasting. While such software already exists, none meet all the following criteria:

* custom display size;
* easily positioned;
* show single events;
* show all mouse clicks;
* show scrolling;
* configurable translucent background colour;
* configurable gaps between keys;
* accurate modifier key states; and
* works with emulation software (e.g., [Sikuli](http://sikulix.com/)).

## Alternatives

* [QKeysOnScreen](https://github.com/ctrlcctrlv/QKeysOnScreen)
* [screenkey](https://www.thregr.org/~wavexx/software/screenkey)

# Comparison

The following video compares KmCaster to [key-mon](https://github.com/critiqjo/key-mon):

![KmCaster Demo](images/kmcaster-01.gif "Comparison Video")

The UI has been modernized:

![KmCaster UI](images/kmcaster-02.png)

# Requirements

[OpenJDK](https://bell-sw.com/pages/downloads/#/java-19-current) version 19.0.1 or newer.

## Linux Java Version

Depending on the Linux distribution, Java 19+ can be installed by issuing one of the following commands in a terminal:

```bash
sudo apt install openjdk-19-jdk
sudo pacman -S jdk-openjdk
```

Switching from earlier versions of Java can be accomplished by issuing one of the following commands in a terminal:

```
sudo update-alternatives --config java
sudo archlinux-java set java-19-openjdk
```

Note: on some Linux operating systems you may need to add a repository.
On Ubuntu 18.04 run the following commands before trying to install Java 19, for example ([source](http://ubuntuhandbook.org/index.php/2020/03/install-oracle-java-14-ubuntu-18-04-20-04/)):

```
sudo add-apt-repository ppa:linuxuprising/java
sudo apt install openjdk-19-jdk
```

# Download

Download the latest Java Archive file:

[Download](https://github.com/DaveJarvis/kmcaster/releases/latest/download/kmcaster.jar)

# Running

After installing Java, run the program as follows:

``` bash
java -jar kmcaster.jar
```

To see the configuration options, run the program as follows:

``` bash
java -jar kmcaster.jar -h
```

To quit the application:

1. Click the application to give it focus.
1. Press `Alt+F4` to exit.

# All in one on Ubuntu 22.04

You can run the following commands to install `kmcaster` on Ubuntu 22.04:

```bash
sudo apt install openjdk-19-jdk
mkdir ~/programs
wget https://github.com/DaveJarvis/kmcaster/releases/latest/download/kmcaster.jar -O ~/programs/kmcaster.jar
java -jar ~/programs/kmcaster.jar -h    # show usage instructions
java -jar ~/programs/kmcaster.jar -d 70 # run at 70% of default size
```

## Error Messages

Earlier versions of Java will display the following message:

> Error: A JNI error has occurred, please check your installation and try again.

