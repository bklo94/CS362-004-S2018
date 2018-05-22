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
   int i, b, numPlayers = 2, player = 0, seed = 1024, preShuffle, postShuffle; //, handCount, bonus = 1, coppers[MAX_HAND], silvers[MAX_HAND], golds[MAX_HAND]
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

    printf("TEST: shuffle()\n");
    memset(&state,23,sizeof(struct gameState));
    initializeGame(numPlayers, k, seed, &state);

    //initialize the deck and see if the player deck is the same as the game state deck
    state.deckCount[player] = 0;
    printf("TEST: Player 0 deck count is 0\n");
    compareStates(shuffle(player,&state),-1);

    //check if the cards in the players deck is still 10 before and after the shuffle
    printf("TEST: Player 0 deck count is correct before/after shuffle\n");
    state.deckCount[player] = 10;
    shuffle(player,&state);
    compareStates(state.deckCount[player],10);

    //test if the cards are successfully shuffled
    printf("TEST: Cards shuffled\n");
    preShuffle = state.deck[player][0];
    shuffle(player,&state);
    postShuffle = state.deck[player][0];

    //if the cards are the same, then they are not shuffled, therefore they are the same and fail the test
    if (preShuffle != postShuffle)
        compareStates(1,1);
    else
        compareStates(0,1);

    if (countFail != 0){
        printf("CARD TEST FAILED\n");
        printf("NUMBER OF TESTS FAILED: %i\n",countFail);
    }
    else
        printf("ALL CARD TESTS SUCCESSFUL\n");
    return 0;
}
