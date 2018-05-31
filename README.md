[![Build Status](https://travis-ci.org/mariopmartins/tcpmock.svg?branch=master)](https://travis-ci.org/mariopmartins/tcpmock) [![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](https://github.com/dwyl/esta/issues) [![HitCount](http://hits.dwyl.com/mariopmartins/tcpmock.svg)](http://hits.dwyl.com/mariopmartins/tcpmock)

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

## TO-DO

1. Add validations to command line tool.
2. Use a proper logging framework.
3. Change saved files format to Json.
4. Make this project a multi-module project and add examples on how to use it.
5. Create visual interface for recording and playback functions.
6. Possible: create interpreter for different databases protocols to show communication in a more understandable way.

