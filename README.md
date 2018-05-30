# tcpmock

Use Main class to execute following functions:

## RECORDING

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


## PLAYBACK

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

