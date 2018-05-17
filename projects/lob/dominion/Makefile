CFLAGS = -Wall -fpic -coverage -lm

rngs.o: rngs.h rngs.c
	gcc -c rngs.c -g  $(CFLAGS)

dominion.o: dominion.h dominion.c rngs.o
	gcc -c dominion.c -g  $(CFLAGS)

playdom: dominion.o playdom.c
	gcc -o playdom playdom.c -g dominion.o rngs.o $(CFLAGS)
#To run playdom you need to entere: ./playdom <any integer number> like ./playdom 10*/
testDrawCard: testDrawCard.c dominion.o rngs.o
	gcc  -o testDrawCard -g  testDrawCard.c dominion.o rngs.o $(CFLAGS)

badTestDrawCard: badTestDrawCard.c dominion.o rngs.o
	gcc -o badTestDrawCard -g  badTestDrawCard.c dominion.o rngs.o $(CFLAGS)

testBuyCard: testDrawCard.c dominion.o rngs.o
	gcc -o testDrawCard -g  testDrawCard.c dominion.o rngs.o $(CFLAGS)

testAll: dominion.o testSuite.c
	gcc -o testSuite testSuite.c -g  dominion.o rngs.o $(CFLAGS)

interface.o: interface.h interface.c
	gcc -c interface.c -g  $(CFLAGS)

runtests: testDrawCard
	./testDrawCard &> unittestresult.out
	gcov dominion.c >> unittestresult.out
	cat dominion.c.gcov >> unittestresult.out

rununits: unittest1.c unittest2.c unittest3.c unittest4.c dominion.c rngs.c
	echo "unittest1.c:" >> unittestresults.out
	gcc -o unittest1 -g  unittest1.c dominion.c rngs.c $(CFLAGS)
	./unittest1 >> unittestresults.out
	gcov unittest1.c >> unittestresults.out
	gcov dominion.c >> unittestresults.out
	cat dominion.c.gcov >> unittestresults.out
	echo "unittest2.c:" >> unittestresults.out
	gcc -o unittest2 -g  unittest2.c dominion.c rngs.c $(CFLAGS)
	./unittest2 >> unittestresults.out
	gcov unittest2.c >> unittestresults.out
	gcov dominion.c >> unittestresults.out
	cat dominion.c.gcov >> unittestresults.out
	echo "unittest3.c:" >> unittestresults.out
	gcc -o unittest3 -g  unittest3.c dominion.c rngs.c $(CFLAGS)
	./unittest3 >> unittestresults.out
	gcov unittest3.c >> unittestresults.out
	gcov dominion.c >> unittestresults.out
	cat dominion.c.gcov >> unittestresults.out
	echo "unittest4.c:" >> unittestresults.out
	gcc -o unittest4 -g  unittest4.c dominion.c rngs.c $(CFLAGS)
	./unittest4 >> unittestresults.out
	gcov unittest4.c >> unittestresults.out
	gcov dominion.c >> unittestresults.out
	cat dominion.c.gcov >> unittestresults.out

runcards: cardtest1.c cardtest2.c cardtest3.c cardtest4.c dominion.c rngs.c
	echo "cardtest1.c:" >> unittestresults.out
	gcc -o cardtest1 -g  cardtest1.c dominion.c rngs.c $(CFLAGS)
	./cardtest1 >> unittestresults.out
	gcov cardtest1.c >> unittestresults.out
	gcov dominion.c >> unittestresults.out
	cat dominion.c.gcov >> unittestresults.out
	echo "cardtest2.c:" >> unittestresults.out
	gcc -o cardtest2 -g  cardtest2.c dominion.c rngs.c $(CFLAGS)
	./cardtest2 >> unittestresults.out
	gcov cardtest2.c >> unittestresults.out
	gcov dominion.c >> unittestresults.out
	cat dominion.c.gcov >> unittestresults.out
	echo "cardtest3.c:" >> unittestresults.out
	gcc -o cardtest3 -g  cardtest3.c dominion.c rngs.c $(CFLAGS)
	./cardtest3 >> unittestresults.out
	gcov cardtest3.c >> unittestresults.out
	gcov dominion.c >> unittestresults.out
	cat dominion.c.gcov >> unittestresults.out
	echo "cardtest4.c:" >> unittestresults.out
	gcc -o cardtest4 -g  cardtest4.c dominion.c rngs.c $(CFLAGS)
	./cardtest4 >> unittestresults.out
	gcov cardtest4.c >> unittestresults.out
	gcov dominion.c >> unittestresults.out
	cat dominion.c.gcov >> unittestresults.out

randomtestcard1: randomtestcard1.c dominion.c rngs.c
	gcc -o randomtestcard1 -g randomtestcard1.c dominion.c rngs.c $(CFLAGS)
	echo "RANDOM TEST: randomtestcard1.c:" >> randomtestcard1.out
	./randomtestcard1 > randomtestcard1.out
	echo "COVERAGE: randomtestcard1.c:" >> randomtestcard1.out
	gcov randomtestcard1.c >> randomtestcard1.out
	gcov dominion.c >> randomtestcard1.out
	cat dominion.c.gcov >> randomtestcard1.out

randomtestcard2: randomtestcard2.c dominion.c rngs.c
	gcc -o randomtestcard2 -g randomtestcard2.c dominion.c rngs.c $(CFLAGS)
	echo "RANDOM TEST: randomtestcard2.c:" >> randomtestcard2.out
	./randomtestcard2 > randomtestcard2.out
	echo "COVERAGE: randomtestcard2.c:" >> randomtestcard2.out
	gcov randomtestcard2.c >> randomtestcard2.out
	gcov dominion.c >> randomtestcard2.out
	cat dominion.c.gcov >> randomtestcard2.out

randomadventurer: randomadventurer.c dominion.c rngs.c
	gcc -o randomadventurer -g randomadventurer.c dominion.c rngs.c $(CFLAGS)
	echo "RANDOM TEST: randomadventurer.c:" >> randomadventurer.out
	./randomadventurer > randomadventurer.out
	echo "COVERAGE: randomadventurer.c:" >> randomadventurer.out
	gcov randomadventurer.c >> randomadventurer.out
	gcov dominion.c >> randomadventurer.out
	cat dominion.c.gcov >> randomadventurer.out

randomtests: randomadventurer randomtestcard1 randomtestcard2

player: player.c interface.o
	gcc -o player player.c -g  dominion.o rngs.o interface.o $(CFLAGS)

unittestresults.out: rununits runcards

all: playdom player testDrawCard testBuyCard badTestDrawCard testAll runtests unittestresults.out

clean:
	rm -f *.o randomtestcard1 randomtestcard2 randomadventurer badTestDrawCard playdom.exe playdom player player.exe *.gcov *.gcda *.gcno *.so *.out testDrawCard testDrawCard.exe unittest1 unittest2 unittest3 unittest4 cardtest1 cardtest2 cardtest3 cardtest4
