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
int failedEffects = 0, failedDiscards = 0, failedDraws = 0, failedBuys = 0, failedHands = 0, failedHands2 = 0;

// function to check the council_roomCard
void checkCouncil_RoomCard(int p, struct gameState *endState) {
        int r, a, b, c, d, w, v, i, bonus = 0, preHandCount, postHandCount, preDeckCount, postDeckCount;
        //randGameame state variable to manually act on the functions actions
        struct gameState initialState;
        // copy the passed in game state to initialState
        memcpy(&initialState,endState,sizeof(struct gameState));
        r = cardEffect(council_room, 0, 0, 0, endState, 0, &bonus);
        //check if council_room card failed
        if(r)
                failedEffects++;
        //have the player draw 4 cards
        a = drawCard(p, &initialState);
        b = drawCard(p, &initialState);
        c = drawCard(p, &initialState);
        d = drawCard(p, &initialState);
        //set the initial buy state
        initialState.numBuys++;
        //for loop so each player can draw a card
        for (i = 0; i < initialState.numPlayers; i++) {
                if (i != p) {
                        w = drawCard(i, &initialState);
                        //check draw card for failure
                        if (w == -1 && initialState.deckCount[i] != 0)
                                failedDraws++;
                }
        }
        //discard a card from player
        v = discardCard(0, p, &initialState, 0);
        if(v)
                failedDiscards++;
        //get the hand and deck to compare
        preHandCount = initialState.handCount[p];
        postHandCount = endState->handCount[p];
        preDeckCount = initialState.deckCount[p];
        postDeckCount = endState->deckCount[p];
        //check the numBuys
        if (initialState.numBuys != endState->numBuys)
                failedBuys++;
        //check if any drawcard failed
        if (a == -1 && initialState.deckCount[p] != 0)
                failedDraws++;
        if (b == -1 && initialState.deckCount[p] != 0)
                failedDraws++;
        if (c == -1 && initialState.deckCount[p] != 0)
                failedDraws++;
        if (d == -1 && initialState.deckCount[p] != 0)
                failedDraws++;
        //if the hand counts don't match up increase the faiilure variable
        if (!(postHandCount == preHandCount && postDeckCount == preDeckCount))
                failedHands++;
        //for loop that checks both player's hands
        for (i = 0; i < initialState.numPlayers; i++) {
                if (i != p)
                        if (!(endState->handCount[i] == initialState.handCount[i] && endState->deckCount[i] == initialState.deckCount[i]))
                                failedHands2++;
        }
}
//main function that posts the tests
int main () {
        int iterations = 10000, i, n, player, deckCount, handCount, discardCount, totalFails;
        int numberOfPlayers[] = {2,3,4};
        struct gameState randGame;
        //get the time for the seed
        srand(time(NULL));
        //initialize the randGame state and randomize the inputs
        for (n = 0; n < iterations; n++) {
                for (i = 0; i < sizeof(struct gameState); i++)
                        ((char*)&randGame)[i] = floor(Random() * 256);
                //set the random number of players and set player's initial variables
                randGame.numPlayers = numberOfPlayers[rand() % 3];
                //initialize the variables for the game
                randGame.numBuys = 1;
                randGame.playedCardCount = floor(Random() * (MAX_DECK-1));
                player = randGame.numPlayers - 2;
                deckCount = floor(Random() * MAX_DECK);
                handCount = floor(Random() * MAX_HAND);
                discardCount = floor(Random() * MAX_DECK);
                randGame.whoseTurn = player;
                //set the initial variables for all the players
                for (i = 0; i < randGame.numPlayers; i++) {
                        randGame.deckCount[i] = deckCount;
                        randGame.handCount[i] = handCount;
                        randGame.discardCount[i] = discardCount;
                }
                //after running the interation, check the card
                checkCouncil_RoomCard(player,&randGame);
        }
        //sum up all the failed results after the iterations
        totalFails = failedEffects + failedDiscards + failedDraws + failedHands + failedBuys;
        printf("Some tests failed:\n\n");
        printf("# Passed Tests: %i\n",iterations - totalFails);
        printf("# Failed Tests: %i\n",totalFails);

        //if there are no fails in the random test
        if (totalFails == 0)
                printf ("All Tests Passed\n\n");
        //else print out what had failed
        else {
                printf("Test Failure Results:\n\n");
                printf("drawCard() failed: %i\n",failedDraws);
                printf("cardEffect() failed: %i\n",failedEffects);
                printf("discardCard() failed: %i\n",failedDiscards);
                printf("numBuys Count mismatch: %i\n",failedBuys);
                printf("Other players hand/deck count mismatch: %i\n",failedHands2);
                printf("Selected player hand/deck count mismatch: %i\n",failedHands);
        }
        return 0;
}
