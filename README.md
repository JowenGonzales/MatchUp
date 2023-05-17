# MatchUp Game

MatchUp is a fun and challenging memory matching game app where players are tasked with finding pairs of matching cards. The objective is to clear the game board by matching all the cards in the shortest possible time.

## Features

- Multiple difficulty levels: Easy, Medium, Hard, and Custom.
- Customizable game board with adjustable rows and columns.
- Interactive card flipping and matching gameplay.
- Score tracking to challenge players to achieve higher scores.
- Completion screen displaying game statistics.

## Getting Started

1. Install the MatchUp app on your device.
2. Launch the app to start playing the game.
3. From the main menu, tap the "Play" button.
4. Select a difficulty level or customize the game settings.
5. The game board will be displayed with face-down cards.
6. Tap on cards to flip them and find matching pairs.
7. Continue matching pairs until all cards are cleared.
8. After completing the game, view your score and statistics.
9. You can restart the game or return to the main menu.

## Algorithms

### Card Shuffling (Fisher-Yates Shuffle)

To shuffle the cards before populating the game board, the Fisher-Yates shuffle algorithm is used. This algorithm ensures that the cards are randomly distributed, providing a fair and unpredictable game.

### Matching Algorithm

The matching algorithm checks if two flipped cards are a match based on their values. It returns true if the cards match, and false otherwise.

### Game Completion

The game completion algorithm checks if all the cards on the game board have been matched, indicating the completion of the game.

## Code Samples

```java
// Card Shuffling (Fisher-Yates Shuffle)
public void shuffleCards(Card[] cards) {
    Random random = new Random();
    for (int i = cards.length - 1; i > 0; i--) {
        int j = random.nextInt(i + 1);
        Card temp = cards[i];
        cards[i] = cards[j];
        cards[j] = temp;
    }
}

// Matching Algorithm
public boolean checkMatch(Card card1, Card card2) {
    return card1.getValue().equals(card2.getValue());
}

// Game Completion
public boolean isGameComplete(Card[] cards) {
    int matchedPairs = 0;
    for (Card card : cards) {
        if (card.isMatched()) {
            matchedPairs++;
        }
    }
    return matchedPairs == cards.length;
}
