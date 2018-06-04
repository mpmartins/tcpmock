[![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](https://github.com/mariopmartins/tcpmock/issues) [![Build Status](https://travis-ci.org/mariopmartins/tcpmock.svg?branch=master)](https://travis-ci.org/mariopmartins/tcpmock) [![codecov](https://codecov.io/gh/mariopmartins/tcpmock/branch/master/graph/badge.svg)](https://codecov.io/gh/mariopmartins/tcpmock) [![HitCount](http://hits.dwyl.com/mariopmartins/tcpmock.svg)](http://hits.dwyl.com/mariopmartins/tcpmock)

# tcpmock

## User Manual
Use com.inovarie.tcpmock.Main class to execute following functions:

#### RECORDING

```
NAME
	record - Starts server and record communication.

SYNOPSYS
	record [--server-port] int  [--client-address] string  [--client-port] int  [--file-name] string

OPTIONS
	--server-port  int
		[Mandatory]

	--client-address  string
		[Mandatory]

	--client-port  int
		[Mandatory]

	--file-name  string	
		[Mandatory]
		[must not be empty]
```


#### PLAYBACK

```
NAME
	playback - Starts server and replay specified communication file.

SYNOPSYS
	playback [--server-port] int  [--file-name] string

OPTIONS
	--server-port  int
		[Mandatory]

	--file-name  string
		[Mandatory]
		[must not be empty]
		[File must be in classpath]
```
