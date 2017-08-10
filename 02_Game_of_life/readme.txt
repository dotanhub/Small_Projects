A variation of the game of life by John Conway

creates a simulation of the game of life on a 10*10 matrix.
the first matrix is random.
each time the program asks the user if he wants to move to the next generation.

the genetic laws of Conway:
-birth - on each site with "no life" which has exactly 3 live neighbours there will be a birth on the next gen.
-death - on each site with "life" and 0 or one live neighbours there will be death on the next gen.
	on each site with "life" and 4 or more live neighbours there will be death on the next gen.
existence - on each site with "life" and 2 or 3 live neighbours the site will live on the next gen.