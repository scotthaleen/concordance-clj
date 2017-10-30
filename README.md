
# Concordance

Creates an alphabetic list of all word occurrences labeled with the frequency and sentences the word occurred.

Uses [instaparse](https://github.com/Engelberg/instaparse) to describe tokenization in EBNF

[![Build Status](https://travis-ci.org/scotthaleen/concordance-clj.svg?branch=master)](https://travis-ci.org/scotthaleen/concordance-clj)


## Installation

Download from the latest [release](https://github.com/scotthaleen/concordance-clj/releases/latest)


## Usage

```sh
$ java -jar concordance-*-standalone.jar [TEXT_FILE]
```

## Build

To build an executable jar, run:

```sh
$ lein uberjar
```

This will produce an executable jar in the `target` directory. <br />
Example: `target/concordance-0.1.0-d0fa75f-standalone.jar`


### Test

```sh
$ lein test
```


## Examples

```sh

$ cat examples/helloworld.txt
Hello World! The world is round.

$ java -jar concordance-*-standalone.jar examples/helloworld.txt
a.	hello	{1:1}
b.	is	{1:2}
c.	round	{1:2}
d.	the	{1:2}
e.	world	{2:1,2}


$ java -jar concordance-*-standalone.jar examples/alice-in-wonderland.txt

...
www.    accept  {1:1656}
xxx.    acceptance      {1:263}
yyy.    accepted        {2:1737,1740}
zzz.    accepting       {1:1736}
aaaa.   access  {10:1658,1662,1667,1673,1677,1680,1682,1685,1685,1690}
bbbb.   accessed        {1:1673}
cccc.   accessible      {1:1730}
dddd.   accident        {2:1529,1533}
eeee.   accidentally    {1:1527}
ffff.   accordance      {2:1691,1709}
gggg.   account {1:1041}
...
```



## License

Copyright © 2017 ☕

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
