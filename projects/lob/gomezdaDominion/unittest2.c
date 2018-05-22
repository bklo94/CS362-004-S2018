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
   int i, b, numPlayers = 2, player = 0, seed = 1024; //, handCount, bonus = 1, coppers[MAX_HAND], silvers[MAX_HAND], golds[MAX_HAND]
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

    printf("TEST: isGameOver()\n");
    //initialize the game
    memset(&state,23,sizeof(struct gameState));
    initializeGame(numPlayers, k, seed, &state);
    state.supplyCount[province] = 0;
    printf("TEST: Province Cards Empty\n");
    compareStates(isGameOver(&state),1);
    state.supplyCount[province] = 1;

    //empty the supply piles and check if they are truly empty
    state.supplyCount[0] = 0;
    state.supplyCount[1] = 0;
    state.supplyCount[2] = 0;
    printf("TEST: Supply Piles Empty\n");
    compareStates(isGameOver(&state),1);

    //fill the supply piles and check if they are truly filled
    printf("TEST: Filling piles\n");
    state.supplyCount[0] = 1;
    state.supplyCount[1] = 1;
    state.supplyCount[2] = 1;
    printf("TEST: Province and Supply Piles not empty\n");
    compareStates(isGameOver(&state),0);

    if (countFail != 0){
        printf("CARD TEST FAILED\n");
        printf("NUMBER OF TESTS FAILED: %i\n",countFail);
    }
    else
        printf("ALL CARD TESTS SUCCESSFUL\n");
    return 0;
}
