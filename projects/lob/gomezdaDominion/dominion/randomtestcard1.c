#include "dominion.h"
#include "dominion_helpers.h"
#include "rngs.h"
#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include <stdio.h>
#include <time.h>
#include <math.h>

//Failure global variable counts
int failedEffects = 0, failedDiscards = 0, failedDraws = 0, failedHands = 0;

//function to check the smithyCard
void checkSmithyCard(int players, struct gameState *endState){
        int r, a, b, c, v, bonus = 0, preHandCount, postHandCount, preDecCount, postDecCount;
        //randGameame state variable to manually act on the functions actions
        struct gameState initialState;
        //copy the passed in randGameame state to initialState
        memcpy(&initialState, endState, sizeof(struct gameState));
        //call the card effect function with the smithy card
        r = cardEffect(smithy, 0, 0, 0, endState, 0, &bonus);
        //check if smithy card failed
        if(r)
                failedEffects++;
        //have the player draw 3 cards
        a = drawCard(players, &initialState);
        b = drawCard(players, &initialState);
        c = drawCard(players, &initialState);
        //call discardCard
        v = discardCard(0, players, &initialState, 0);
        //check if discard card failed
        if(v)
                failedDiscards++;
        //get the hand and deck to compare
        preHandCount = initialState.handCount[players];
        postHandCount = endState->handCount[players];
        preDecCount = initialState.deckCount[players];
        postDecCount = endState->deckCount[players];
        //check if any of the draws failed
        if (a == -1 && initialState.deckCount[players] != 0)
                failedDraws++;
        if (b == -1 && initialState.deckCount[players] != 0)
                failedDraws++;
        if (c == -1 && initialState.deckCount[players] != 0)
                failedDraws++;
        //if the hand counts don't match up increase the faiilure variable
        if (!(postHandCount == preHandCount && postDecCount == preDecCount))
                failedHands++;
}

//main function that posts the tests
int main (){
        int iterations = 10000, i, n, player, totalFails;
        struct gameState randGame;
        //get the time for the seed
        srand(time(NULL));
        //initialize the randGame state and randomize the inputs
        for (n = 0; n < iterations; n++) {
                for (i = 0; i < sizeof(struct gameState); i++)
                        ((char*)&randGame)[i] = floor(Random() * 256);
                //set the random number of players
                player = floor(Random() * MAX_PLAYERS);
                //initialize the variables for the game
                randGame.deckCount[player] = floor(Random() * MAX_DECK);
                randGame.discardCount[player] = floor(Random() * MAX_DECK);
                randGame.handCount[player] = floor(Random() * MAX_HAND);
                randGame.playedCardCount = floor(Random() * (MAX_DECK-1));
                randGame.whoseTurn = player;
                //after running the interation, check the card
                checkSmithyCard(player,&randGame);
        }
        //sum up all the failed results after the iterations
        totalFails = failedEffects + failedDiscards + failedDraws + failedHands;
        printf("Some tests failed:\n\n");
        printf("# Passed Tests: %i\n",iterations - totalFails);
        printf("# Failed Tests: %i\n",totalFails);

        //if there are no fails in the random test
        if (totalFails == 0)
                printf ("All Tests Passed\n\n");
        //else print out what had failed
        else{
                printf("Test Failure Results:\n\n");
                printf("discardCard() failed: %i\n", failedDiscards);
                printf("drawCard() failed: %i\n", failedDraws);
                printf("cardEffect() failed: %i\n", failedEffects);
                printf("Hand/Deck Count mismatch: %i\n", failedHands);
        }
        return 0;
}
