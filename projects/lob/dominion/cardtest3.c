#include "dominion.h"
#include "dominion_helpers.h"
#include <string.h>
#include <stdio.h>
#include <assert.h>
#include "rngs.h"

//FEAST TESTS

// global count of test failures
int failureCount = 0;

// function to check if two ints are equal or not
void compareStates(int a, int b) {
    if (a == b)
        printf("Test: PASSED\n");
    else {
        printf("Test: FAILED\n");
        failureCount++;
    }
}

// runs the tests
int main () {
    int i, numPlayers = 2, p0 = 0, p1 = 1, handpos = 0, choice1 = 0, choice2 = 0, choice3 = 0, bonus = 0, seed = 1024;
    // kingdom cards
    int k[10] = {adventurer, council_room, feast, gardens, mine, remodel, smithy, village, baron, great_hall};
    struct gameState state, stateOriginal;

    //start testing the feastcard()
    //assign the initial starting state and the state that will change
    printf("Testing -> feastCard()\n\n");
    memset(&state,23,sizeof(struct gameState));
    memset(&stateOriginal,23,sizeof(struct gameState));
    //initialize the game state and assign memory to the state
    initializeGame(numPlayers, k, seed, &state);
    memcpy(&stateOriginal, &state, sizeof(struct gameState));
    //call the feast card function
    cardEffect(feast, choice1, choice2, choice3, &state, handpos, &bonus);
    printf("Check Player 0 Gains 4 Cards: ");
    compareStates(state.handCount[p0],stateOriginal.handCount[p0]+4);
    //printf("%i\n", handCard(handpos,&state) != adventurer);
    printf("Check Player 0 Pile if 4 Cards Taken: ");
    compareStates(state.deckCount[p0],stateOriginal.deckCount[p0]-4);
    printf("Check Player 0 if Buy Inceases By 1: ");
    compareStates(state.numBuys,stateOriginal.numBuys+1);

    printf("Check Victory Supply Piles: ");
    compareStates(state.supplyCount[province],stateOriginal.supplyCount[province]);
    compareStates(state.supplyCount[duchy],stateOriginal.supplyCount[duchy]);
    compareStates(state.supplyCount[estate],stateOriginal.supplyCount[estate]);

    printf("Check if Kingdom Supply Piles Remains Same:\n");
    for (i = 0; i < 10; i++) {
        printf("Checking Card %i: ", i);
        compareStates(state.supplyCount[k[i]],stateOriginal.supplyCount[k[i]]);
    }


    printf("Check Player 1 Gains 1 Card: ");
    compareStates(state.handCount[p1],stateOriginal.handCount[p1]+1);
    printf("Check Player 1 Pile if 1 Card Taken: ");
    compareStates(state.deckCount[p1],stateOriginal.deckCount[p1]-1);

    if (failureCount != 0) {
        printf("CARD TEST FAILED\n");
        printf("NUMBER OF TESTS FAILED: %i\n",failureCount);
    }
    else {
        printf("ALL CARD TESTS SUCCESSFUL\n");
    }

    return 0;
}
