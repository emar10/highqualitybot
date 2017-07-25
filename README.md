# High Quality Bot [![CircleCI](https://circleci.com/gh/sasquench/highqualitybot/tree/master.svg?style=svg)](https://circleci.com/gh/sasquench/highqualitybot/tree/master)

High Quality Bot is a Java-based bot for the VoIP and chat platform [Discord](https://discordapp.com) built on
[JDA](https://github.com/DV8FromTheWorld/JDA) and [LavaPlayer](https://github.com/sedmelluq/lavaplayer). Currently
features commands for music playback, but future versions are planned to include other features such as administrative
commands and chat tools/games.

## Building

### Prerequisites

High Quality Bot requires Java Development Kit 8. Building is done with Gradle, so all other dependencies are managed
automagically. Just clone the repository and run the `shadowJar` task using the wrapper.

### On MacOS, Linux, etc.

1. Navigate to the location of the cloned repository in your terminal emulator of choice
2. Run `./gradlew shadowJar`

### On Windows

1. Open a Command Prompt and move to the cloned repository
2. Run `gradlew shadowJar`

On all platforms the built JAR will be placed in `build/libs/highqualitybot-<version>.jar`

## Setting Up the Bot

Before you can run the bot yourself, you'll need to set up an application on Discord's
[developer](https://discordapp.com/developers) page and add the bot account to your server. If you've already done this
and have your token ready, then skip to Configuring.

### Creating an Application on Discord

1. Make sure that you're logged in, then go to "My Apps."
2. Create a new app.
2. Give your app a sweet name, and optionally give it a description and icon.
3. After creating the app, go to "Create a Bot User."
4. You should now have the option to display your bot's token. You'll need this for the config file later.
5. Add the bot to your server as discussed [here](https://discordapp.com/developers/docs/topics/oauth2#bots).

### Configuring

High Quality Bot expects a file called `config.cfg` to be in the same directory as the .jar file, and won't run without
it. The format is simply one `key=value` declaration per line. `exampleconfig.cfg` provides a sample configuration.
Currently available options to set are:
* `token` - Your Bot account's OAuth token
* `prefix` - The desired character (or string of characters if you're a crazy person) used to invoke commands.
* `ownerid` - The User attached to this ID is automatically given permission to use all commands.
* `allowed` - This is a list of command names separated by commas that users are allowed to use by default.
* `disallowed` - Users are by default explicitly disallowed to use these commands.
* `allowedHasPrecedence` - If true, any commands in both `allowed` and `disallowed` are considered allowed.
* `permissiongroups` - A list of filenames separated by commas from which HQBot will read permission groups.
At the bare minimum you should set the token and ownerid. *If the owner ID is not set, you will not be able to run any
commands.* For more information about the Permissions system, see below.

Example `config.cfg`:

```
# Parser will ignore lines starting with '#'
# Options are set with:
# key=value

# Token (this one isn't valid don't even try it)
token=MjY4MjExODI2MzU1ODYzNTUz.DByjTQ.1ycw5crEGVZGo-ckyGakydsno0o

# Prefix
prefix=.

# OwnerID
ownerid=123456789

# Allowed & Disallowed
allowed=*
disallowed=shutdown

allowedHasPrecedence=false

```

## Permissions

High Quality Bot makes use of a permissions system that integrates with Discord's Roles. When checking if a user is
allowed to run a command, the bot starts with the permissions set in the `config.cfg`. From there, another .cfg file is
added for each Discord Role you would like the bot to make use of. This file includes lists of commands that users in
the role will be granted or denied permission to use, as well as a priority in the event of collision between two roles.
Once the file is created, simply add its path to `permissiongroups` in the main config. Several preset configs are
located in the `examplegroups` folder.

Example Permission Group `DJ.cfg`

```
# Lines starting with '#' are ignored

# RoleID
role=123456789

# Allowed
allowed=play,pause,skip,queue

# No Disallowed commands required

# Priority (should be set higher than any conflicting groups you don't want to override this one)
priority=9

```

## Using the Bot

To run High Quality Bot, place the JAR and config in the same directory wherever you like,
and start it up with `java -jar highqualitybot-<version>.jar`. Once connected, you can invoke commands through any text
channel using the prefix set in the config file followed by the name of the command. Any arguments follow the command
name separated by spaces. To list all available commands (with a prefix of `.`):
```
.help
```

## Attribution

Development of High Quality Bot is made possible through the use of the following libraries:

* [JDA](https://github.com/DV8FromTheWorld/JDA) by *DV8FromTheWorld* (Licensed under [Apache-2.0](https://apache.org/licenses/LICENSE-2.0))
* [LavaPlayer](https://github.com/sedmelluq/LavaPlayer) by *sedmelluq* (Licensed under [Apache-2.0](https://apache.org/licenses/LICENSE-2.0))

