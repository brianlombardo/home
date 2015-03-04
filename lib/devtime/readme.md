# devtime

## setup
+ install packages `bash gawk git grep tmux vim watch coreutils gnu-sed`
+ add `devtime` script to PATH
+ optional: init repo `cd && git clone my_repo_url .devtime`
+ optional: change devtime dir `export DEVTIME="$HOME/devtimes"`

## run
+ start session `devtime`
+ start a new task `> + new task text` or `> ... another task`
+ start from time `> + 01:23 task` or `> ... 12:34 task`
+ enter a note `> - note text`
  - indent note `> --- 3 tabs before note text`
  * add a quest `> ? todo: solve me`
+ take a break `> ... afk` or `> ... 23:45 afk`
+ edit current log with vim `> -`
+ open workspace in vscode `> #`
+ remove last line `> pop`, or n lines `> pop pop pop`
+ tag words using `> #these @will +be !highlighted`
  - create a tag `> tag epic`
  - edit tags file `> tag`
+ finish the day `> gg`
+ run commands (carefully) with /
  - such as `> /ls -phat` or `> /git status`
  - don't run `> /sudo rm -fr /*`
+ leave session `> q` or `ctrl+c`

## info
+ validate logs `devtime validate`
+ print hours by week `devtime weeks`
+ print total hours `devtime forever`
+ print logs and stats `devtime report`

## questions
+ should devtime be used in production?
  + no
+ are the calculations in devtime accurate?
  + probably, use the validator
+ is the validator accurate?
  + probably
+ does devtime work past midnight?
  + untested. there may be consequences regardless
