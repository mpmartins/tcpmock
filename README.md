# tcpmock

Main
        playback: Starts server and replay specified communication file.
        record: Starts server and record communication.


shell:>help record


NAME
	record - Starts server and record communication.

SYNOPSYS
	record [--server-port] int  [[--client-address] string]  [--client-port] int  [--file-name] string  [--detached]  

OPTIONS
	--server-port  int
		
		[Mandatory]

	--client-address  string
		
		[Optional, default = localhost]

	--client-port  int
		
		[Mandatory]

	--file-name  string
		
		[Mandatory]
		[must not be empty]

	--detached	
		[Optional, default = false]



shell:>help playback


NAME
	playback - Starts server and replay specified communication file.

SYNOPSYS
	playback [--server-port] int  [--file-name] string  [--detached]  

OPTIONS
	--server-port  int
		
		[Mandatory]

	--file-name  string
		
		[Mandatory]
		[must not be empty]

	--detached	
		[Optional, default = false]


