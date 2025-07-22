# ChatChannels
This is a plugin that adds channels to chat.
Every player can only see messages in channels they joined.

This version is made for Minecraft 1.21.8, Spigot

### Configuration
- **config.yml**: [General configuration documentation](CONFIG.md)

### Usage

When a player writes in chat, only the players that have joined the sender's active channel can see the message.
As a player you also have to set your active channel based on which channel you want to send messages on.

### Commands
- **/chatchannelsreload**: Reload the configuration files
- **/createchannel \<channel\>**: [cchannel] Creates a new chat channel
- **/deletechannel \<channel\>**: [dchannel] Deletes a chat channel
- **/joinchannel \<channel\>**: [jchannel, jc] Join a chat channel
- **/leavechannel \<channel\>**: [lchannel, lc] Leave a chat channel
- **/activechannel \<channel\>**: [achannel, ac] Set a chat channel as the active