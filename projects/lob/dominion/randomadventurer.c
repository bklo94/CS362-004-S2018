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
int failedEffects = 0, failedShuffles = 0, failedDraws = 0, failedHands = 0, failedTreasures = 0;

//function to check the adventurerCard
void checkAdventurerCard(int players, struct gameState *endState){
        int PostTreasureCount = 0, PreTreasureCount = 0, drawTreasure = 0, drawnCard, postHandCount, postDecCount, postDiscCount, preHandCount, preDecCount, preDiscCount, card, bonus = 0, r, s, t, i, z = 0;
        int tempHand[MAX_HAND];
        struct gameState initialState;
        //copy the passed in game state to initialState
        //call the card effect function with the adventurerCard
        memcpy(&initialState,endState,sizeof(struct gameState));
        r = cardEffect(adventurer, 0, 0, 0, endState, 0, &bonus);
        //check if adventure card failed
        if(r)
                failedEffects++;
        //else you can execute adventure card actions
        while(drawTreasure < 2) {
                //if the deck is empty we need to shuffle discard and add to deck
                // check if shuffle failed
                if (initialState.deckCount[players] < 1) {
                        s = shuffle(players, &initialState);
                        if (s == -1 && initialState.deckCount[players] >= 1)
                                failedShuffles++;
                }
                t = drawCard(players, &initialState);
                //check the return and the number of cards in the deck if drawcard failed
                if (t == -1 && initialState.deckCount[players] != 0)
                        failedDraws++;
                //grab the top card/most recently drawn card
                drawnCard = initialState.hand[players][initialState.handCount[players] - 1];
                //if the drawn card is a treausre increament the variable
                if (drawnCard == copper || drawnCard == silver || drawnCard == gold)
                        drawTreasure++;
                //else it is not a treasure and you put into a temp hand to discard
                else{
                        tempHand[z] = drawnCard;
                        initialState.handCount[players]--;
                        z++;
                }
        }
        // discard all cards in play that have been drawn
        while(z - 1 >= 0) {
                initialState.discard[players][initialState.discardCount[players]++] = tempHand[z - 1];
                z = z - 1;
        }
        //iterate through the changed game state and count the treasures
        for (i = 0; i < endState->handCount[players]; i++) {
                card = endState->hand[players][i];
                if (card == copper || card == silver || card == gold)
                        PostTreasureCount++;
        }
        //iterate through the changed game state and count the pretreasures
        for (i = 0; i < initialState.handCount[players]; i++) {
                card = initialState.hand[players][i];
                if (card == copper || card == silver || card == gold)
                        PreTreasureCount++;
        }
        //compare the changed and original game states
        if (PostTreasureCount != PreTreasureCount)
                failedTreasures++;
        //get the hand, deck, and discardcounts to compare
        preHandCount = initialState.handCount[players];
        postHandCount = endState->handCount[players];
        preDecCount = initialState.deckCount[players];
        postDecCount = endState->deckCount[players];
        preDiscCount = initialState.discardCount[players];
        postDiscCount = endState->discardCount[players];
        //if the hand counts don't match up increase the faiilure variable
        if (!(postHandCount == preHandCount && postDecCount == preDecCount && postDiscCount == preDiscCount))
                failedHands++;
}

//main function that posts the tests
int main (){
        int iterations = 10000, treasures[] = {copper, silver, gold}, numTreasures, i, n, player, min = 3, totalFails;
        struct gameState randGame;
        //get the time for the seed
        srand(time(NULL));
        //initialize the game state and randomize the inputs
        for (n = 0; n < iterations; n++) {
                for (i = 0; i < sizeof(struct gameState); i++)
                        ((char*)&randGame)[i] = floor(Random() * 256);
                //randomly initialize the game with random amount of players
                player = floor(Random() * MAX_PLAYERS);
                //initialize the game deck
                randGame.deckCount[player] = floor(Random() * ((MAX_DECK - min) + 1) + min);
                numTreasures = floor(Random() * ((randGame.deckCount[player] - min) + 1) + min);
                // put a min of 3 treasure cards in deck
                for (i = 0; i < numTreasures; i++)
                        randGame.deck[player][i] = treasures[rand() % 3];
                //set the discard count to 0
                randGame.discardCount[player] = 0;
                randGame.handCount[player] = floor(Random() * ((MAX_HAND - min) + 1) + min);
                //set the player turn
                randGame.whoseTurn = player;
                //after running the interation, check the card
                checkAdventurerCard(player, &randGame);
        }
        //sum up all the failed results after the iterations
        totalFails = failedEffects + failedDraws + failedShuffles + failedHands + failedTreasures;

        //edge case if all the iterations failed
        if (iterations - totalFails <= 0) {
                printf("All tests failed:\n\n");
                printf("# Passed Tests: %i\n",0);
                printf("# Failed Tests: %i\n",iterations);
        }
        //else print out how many passed and failed tests
        else{
                printf("Some tests failed:\n\n");
                printf("# Passed Tests: %i\n",iterations - totalFails);
                printf("# Failed Tests: %i\n",totalFails);
        }
        //if there are no fails in the random test
        if (totalFails == 0)
                printf ("All Tests Passed\n\n");
        //else print out what had failed
        else{
                printf("Test Failure Results:\n\n");
                printf("shuffle() failed: %i\n", failedShuffles);
                printf("drawCard() failed: %i\n", failedDraws);
                printf("cardEffect() failed: %i\n", failedEffects);
                printf("Treasure Count mismatch: %i\n", failedTreasures);
                printf("Hand/Deck Count mismatch: %i\n", failedHands);
        }
        return 0;
}
