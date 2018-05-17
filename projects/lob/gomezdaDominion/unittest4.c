#include "dominion.h"
#include "dominion_helpers.h"
#include <string.h>
#include <stdio.h>
#include <assert.h>
#include "rngs.h"

// global variable to count the number of failed tests
int countFail = 0;

// function to check if two ints are equal or not
void compareStates(int a, int b) {
    if (a == b) {
        printf("Test: PASSED\n");
    }
    else {
        printf("Test: FAILED\n");
        countFail++;
    }
}

// runs the tests
int main () {
   int seed = 1024, deckCount, handCount, discardCount, goldSupply; //i, b, numPlayers = 2, player = 0,  preShuffle, postShuffle; //, handCount, bonus = 1, coppers[MAX_HAND], silvers[MAX_HAND], golds[MAX_HAND]
   // kingdom cards
   int k[10] = {adventurer, council_room, feast, gardens, mine, remodel, smithy, village, baron, great_hall};
   struct gameState state;
   /* Not needed for this unit test
   fill copper, silver, and gold arrays full of treasures
   for (i = 0; i < MAX_HAND; i++){
      coppers[i] = copper;
      silvers[i] = silver;
      golds[i] = gold;
   */

   //initialize the game
   printf("TEST: gainCard()\n");
   memset(&state,23,sizeof(struct gameState));
   initializeGame(2, k, seed, &state);

   //empty gold pile and check if the state is empty
   state.supplyCount[gold] = 0;
   printf("TEST: Gold Supply Empty\n");
   compareStates(gainCard(gold,&state,0,0),-1);

   //refill gold after emptying
   state.supplyCount[gold] = 30;
   deckCount = state.deckCount[0];
   gainCard(gold,&state,1,0);
   printf("TEST: Deck Gains Card\n");
   compareStates(deckCount+1,state.deckCount[0]);

   //test if the gain card function works and adds gold to the handCount
   handCount = state.handCount[0];
   gainCard(gold,&state,2,0);
   printf("TEST: Hand Gains Card\n");
   compareStates(handCount+1,state.handCount[0]);

   //check if the gain card correctly adds to the discard pile
   discardCount = state.discardCount[0];
   gainCard(gold,&state,0,0);
   printf("TEST: Card discarded to pile\n");
   compareStates(discardCount+1,state.discardCount[0]);

   //check if the gold correctly decreases the supply
   goldSupply = state.supplyCount[gold];
   gainCard(gold,&state,0,0);
   printf("TEST: Gold Supply Decreases\n");
   compareStates(goldSupply-1,state.supplyCount[gold]);

   if (countFail != 0){
     printf("CARD TEST FAILED\n");
     printf("NUMBER OF TESTS FAILED: %i\n",countFail);
   }
   else
     printf("ALL CARD TESTS SUCCESSFUL\n");
   return 0;
}
