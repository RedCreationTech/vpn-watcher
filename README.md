# vpn-watcher

## 背景

公司的VPN是通过路由器翻墙实现的, 但是这个VPN不是特别稳定, 经常会掉线, 简单的方案是直接重启路由器.

本程序每10分钟检查一下翻墙状态, 如果没有成功翻墙, 则自动重启路由器.

## Usage

执行编译出来的jar包

`java -jar vpn-watcher-0.1.0-standalone.jar`


## Examples

看起来每40分钟左右需要重启一次

```
vpn-watcher.core> (-main)
22-04-23 08:18:40 pi001 DEBUG [vpn-watcher.core:92] - checking router and vpn...
22-04-23 08:18:41 pi001 WARN [vpn-watcher.core:97] - no need to restart router now....
22-04-23 08:28:41 pi001 WARN [vpn-watcher.core:97] - no need to restart router now....
22-04-23 08:38:42 pi001 DEBUG [vpn-watcher.core:71] - restarting now....
22-04-23 08:50:52 pi001 WARN [vpn-watcher.core:97] - no need to restart router now....
22-04-23 09:00:52 pi001 WARN [vpn-watcher.core:97] - no need to restart router now....
22-04-23 09:10:53 pi001 WARN [vpn-watcher.core:97] - no need to restart router now....
22-04-23 09:20:54 pi001 DEBUG [vpn-watcher.core:71] - restarting now....
22-04-23 09:33:03 pi001 WARN [vpn-watcher.core:97] - no need to restart router now....
22-04-23 09:43:04 pi001 WARN [vpn-watcher.core:97] - no need to restart router now....
22-04-23 09:53:04 pi001 WARN [vpn-watcher.core:97] - no need to restart router now....
22-04-23 10:03:05 pi001 WARN [vpn-watcher.core:97] - no need to restart router now....
```

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2022 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
