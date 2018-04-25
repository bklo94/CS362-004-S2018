#include "dominion.h"
#include "dominion_helpers.h"
#include <string.h>
#include <stdio.h>
#include <assert.h>
#include "rngs.h"

//ADVENTURER TESTS

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

int countTreasures(int player, struct gameState state){
   int currentCard, count = 0;
   for (int i = 0; i < state.handCount[player]; i++) {
      currentCard = state.hand[player][i];
      if (currentCard == copper || currentCard == silver || currentCard == gold) {
           count++;
      }
   }
   return count;
}

// runs the tests
int main () {
    int i, numPlayers = 2, p0 = 0, p1 = 1, handpos = 0, choice1 = 0, choice2 = 0, choice3 = 0, bonus = 0, seed = 1024;
    int treasureCount = 0;
    int treasureCountOriginal = 0;
    // kingdom cards
    int k[10] = {adventurer, council_room, feast, gardens, mine, remodel, smithy, village, baron, great_hall};
    struct gameState state, stateOriginal;

    //start testing the adventurerCard()
    //assign the initial starting state and the state that will change
    printf("Testing -> adventurerCard()\n\n");
    memset(&state,23,sizeof(struct gameState));
    memset(&stateOriginal,23,sizeof(struct gameState));
    //initialize the game state and assign memory to the state
    initializeGame(numPlayers, k, seed, &state);
    memcpy(&stateOriginal, &state, sizeof(struct gameState));
    //call the feast card function
    cardEffect(adventurer, choice1, choice2, choice3, &state, handpos, &bonus);
    printf("Check Player 0 Gains 2 Cards: ");
    compareStates(state.handCount[p0],stateOriginal.handCount[p0]+2);

    treasureCount = countTreasures(p0, state);
    treasureCountOriginal = countTreasures(p0, stateOriginal);
    printf("Check Player 0 if 2 Treasure Cards Taken: ");
    compareStates(treasureCountOriginal+2,treasureCount);

    printf("Check if Victory Supply Piles Remains Same:\n");
    compareStates(state.supplyCount[province],stateOriginal.supplyCount[province]);
    compareStates(state.supplyCount[duchy],stateOriginal.supplyCount[duchy]);
    compareStates(state.supplyCount[estate],stateOriginal.supplyCount[estate]);

    printf("Check if Kingdom Supply Piles Remains Same:\n");
    for (i = 0; i < 10; i++) {
        printf("Checking Card %i: ", i);
        compareStates(state.supplyCount[k[i]],stateOriginal.supplyCount[k[i]]);
    }

    printf("Check Player 1 Gains 0 Card: ");
    compareStates(state.handCount[p1],stateOriginal.handCount[p1]);
    printf("Check Player 1 Pile if 0 Card Taken: ");
    compareStates(state.deckCount[p1],stateOriginal.deckCount[p1]);

    if (failureCount != 0) {
        printf("CARD TEST FAILED\n");
        printf("NUMBER OF TESTS FAILED: %i\n",failureCount);
    }
    else {
        printf("ALL CARD TESTS SUCCESSFUL\n");
    }

    return 0;
}
