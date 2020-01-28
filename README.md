# Prevalent colors analyzer

## HOWTO
1. Compile `javac src/cz/krtinec/colors/*.java -d out/production/PrevalentColors/`
1. Run `java -cp out/production/PrevalentColors/ cz.krtinec.colors.Main`

The program takes input file from `resources/input.txt` and puts the output to `resources/output.txt`.
Urls that cannot be downloaded are written to `resources/errors.txt`.
Diagnostic information is occasionally written to standard output.

## Random thoughts
### Color counter strategy
I implemented `FastColorCounter` class to count colors in image. `FastColorCounter` randomly selects 1/4 of image pixels
and counts colors only for those pixels.
It looks promising the results are consistent with `StrictColorCounter` which counts all pixels (and is twice as slow).
But to use it in production it would need more tests and discussion with product owner.

### Image queue size
I try to keep less than 30 images in memory. This number works well on my machine - it might be changed for target environment.
For same reasons I try to download 20 images at a time. Again - works well with my bandwidth but with faster line this number can be increased.

### Single core
I found very hard to test the code on single CPU core. There is literally no way how to assure that command will run
on single CPU core on Mac OS X.
See https://apple.stackexchange.com/questions/166870/processor-affinity-on-mac

### Unit tests
There are no unit tests. That's a shame :/

### CLI
In the real world I would probably to configure the input/output files through command line. Here I wanted to keep `Main.java` as simple as possible so I omitted the CLI interface.
