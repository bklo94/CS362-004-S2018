#include "dominion.h"
#include "dominion_helpers.h"
#include <string.h>
#include <stdio.h>
#include <assert.h>
#include "rngs.h"

//SMITHY TESTS

// global variable to count the number of failed tests
int countFail = 0;

// function to check if two ints are equal or not
void compareStates(int a, int b) {
    if (a == b)
        printf("Test: PASSED\n");
    else {
        printf("Test: FAILED\n");
        countFail++;
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
  printf("Testing -> smithyCard()\n\n");
  memset(&state,23,sizeof(struct gameState));
  memset(&stateOriginal,23,sizeof(struct gameState));
  //initialize the game state and assign memory to the state
  initializeGame(numPlayers, k, seed, &state);
  memcpy(&stateOriginal, &state, sizeof(struct gameState));
  //call the smithy card function
  cardEffect(smithy, choice1, choice2, choice3, &state, handpos, &bonus);
  //check if the card effects correctly done
  printf("Check Player 0 Gains 3 Card: ");
  compareStates(state.handCount[p0],stateOriginal.handCount[p0]+3);
  printf("Check Player 0 Pile if 3 Card Taken: ");
  compareStates(state.deckCount[p0],stateOriginal.deckCount[p0]-3);

  //check if the victory pile remains unchanged
  printf("Check if Victory Supply Piles Remains Same:\n");
  compareStates(state.supplyCount[province],stateOriginal.supplyCount[province]);
  compareStates(state.supplyCount[duchy],stateOriginal.supplyCount[duchy]);
  compareStates(state.supplyCount[estate],stateOriginal.supplyCount[estate]);

  //for loop to check if the inital kingdom cards are the same.
  printf("Check if Kingdom Supply Piles Remains Same:\n");
  for (i = 0; i < 10; i++) {
      printf("Checking Card %i: ", i);
      compareStates(state.supplyCount[k[i]],stateOriginal.supplyCount[k[i]]);
  }

  //check if the the other player has no effect per the smithy card
  printf("Check Player 1 Gains 0 Card: ");
  compareStates(state.handCount[p1],stateOriginal.handCount[p1]);
  printf("Check Player 1 Pile if 0 Card Taken: ");
  compareStates(state.deckCount[p1],stateOriginal.deckCount[p1]);

  if (countFail != 0) {
      printf("CARD TEST FAILED\n");
      printf("NUMBER OF TESTS FAILED: %i\n",countFail);
  }
  else
      printf("ALL CARD TESTS SUCCESSFUL\n");
  return 0;
}
