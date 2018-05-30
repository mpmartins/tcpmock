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
3. Create interface for recording and playback.
4. Possible: create interpreter for different databases protocols to show communication in a more understandable way.

