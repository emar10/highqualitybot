# High Quality Bot

High Quality Bot is a Java-based bot for the VoIP and chat platform [Discord](https://discordapp.com) built on [JDA](https://github.com/DV8FromTheWorld/JDA) and [LavaPlayer](https://github.com/sedmelluq/lavaplayer). Currently features commands for music playback, but future versions are planned to include other features such as administrative commands and chat tools/games.

## Building

### Prerequisites

High Quality Bot requires Java Development Kit 8. Building is done with Gradle, so all other dependencies are managed automagically. Just clone the repository and run the `shadowJar` task using the wrapper.

### On MacOS, Linux, etc.

1. Navigate to the location of the cloned repository in your terminal emulator of choice
2. Run `./gradlew shadowJar`

### On Windows

1. Open a Command Prompt and move to the cloned repository
2. Run `gradlew shadowJar`

On all platforms the built executable JAR will be placed in `build/libs/highqualitybot-<version>.jar`

## Setting Up the Bot

Before you can run the bot yourself, you'll need to set up an application on Discord's [developer](https://discordapp.com/developers) page and add the bot account to your server. If you've already done this and have your token ready, then skip to Configuring.

### Creating an Application on Discord

1. Make sure that you're logged in, then go to "My Apps."
2. Create a new app.
2. Give your app a sweet name, and optionally give it a description and icon.
3. After creating the app, go to "Create a Bot User."
4. You should now have the option to display your bot's token. You'll need this for the config file later.
5. Add the bot to your server as discussed [here](https://discordapp.com/developers/docs/topics/oauth2#bots).

### Configuring

High Quality Bot expects a file called `config.txt` to be in the same directory as the .jar file, and won't run without it. At present, there should only be two lines in the config:
* Bot account's token
* Desired command prefix

Example `config.txt`:

```
MjY4MjExODI2MzU1ODYzNTUz.DByjTQ.1ycw5crEGVZGo-ckyGakydsno0o (this isn't a valid token don't even try it)
.
```

From here High Quality Bot should be ready to run. Place the .jar and config wherever you like, and run it with `java -jar highqualitybot-<version>.jar`

## Using the Bot

Once connected, you can invoke commands through any text channel using the prefix set in the config file followed by the name of the command. Any arguments follow the command name separated by spaces. To list all available commands (with a prefix of `.`):
```
.help
```
