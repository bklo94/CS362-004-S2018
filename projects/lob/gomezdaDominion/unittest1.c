#include "dominion.h"
#include "dominion_helpers.h"
#include <string.h>
#include <stdio.h>
#include <assert.h>
#include "rngs.h"

// global variable to count the number of failed tests
int countFail = 0;

// function to check if two ints are equal or not
void compareStates(int a, int b){
    if (a == b)
        printf("Test: PASSED\n");
    else{
        printf("Test: FAILED\n");
        countFail++;
    }
}

int main (){
    int i, b, numPlayers = 2, player = 0, seed = 1024, handCount, bonus = 1, coppers[MAX_HAND], silvers[MAX_HAND], golds[MAX_HAND];
    // kingdom cards
    int k[10] = {adventurer, council_room, feast, gardens, mine, remodel, smithy, village, baron, great_hall};
    struct gameState state;
    //fill copper, silver, and gold arrays full of treasures
    for (i = 0; i < MAX_HAND; i++){
        coppers[i] = copper;
        silvers[i] = silver;
        golds[i] = gold;
    }

    printf("TEST: updateCoins()\n");
    // test how updateCoins handles each treasure and a different bonus and hand count
    for (handCount = 0; handCount <= 5; handCount += 5){
        printf("Updated Treasure Cards in hand: %d\n",handCount);
        printf("Updated Bonus: %d\n", bonus);
        //game is initialized
        memset(&state,23,sizeof(struct gameState));
        b = initializeGame(numPlayers, k, seed, &state);
        //Grab the player's hand once the game is initialized
        state.handCount[player] = handCount;
        // fill hand with all coppers
        memcpy(state.hand[player],coppers,sizeof(int)*handCount);
        updateCoins(player,&state,bonus);
        printf("TEST: Copper Coin Hand\n");
        printf("Actual, Expected: %d, %d\n",state.coins, handCount*1+bonus);
        compareStates(state.coins,handCount*1+bonus);
        // fill hand with all silvers
        memcpy(state.hand[player],silvers,sizeof(int)*handCount);
        updateCoins(player,&state,bonus);
        printf("TEST: Silver Coin Hand\n");
        printf("Actual, Expected: %d, %d\n",state.coins, handCount*2+bonus);
        compareStates(state.coins,handCount*2+bonus);
        // fill hand with all golds
        memcpy(state.hand[player],golds,sizeof(int)*handCount);
        updateCoins(player,&state,bonus);
        printf("TEST: Silver Coin Hand\n");
        printf("Actual, Expected: %d, %d\n",state.coins, handCount*3+bonus);
        compareStates(state.coins,handCount*3+bonus);
        bonus = bonus + 2;
    }
    if (countFail != 0){
        printf("CARD TEST FAILED\n");
        printf("NUMBER OF TESTS FAILED: %i\n",countFail);
    }
    else
        printf("ALL CARD TESTS SUCCESSFUL\n");
    return 0;
}
