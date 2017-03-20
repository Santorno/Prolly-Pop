package org.bboed.students.ahmedmoustafa18;

public class Player {
    private String name;
    private int id;
    private int candyCount;

    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;

    private static final int MAX_CANDY = 14;

    public Player(String name, int id) {
        if(name == null || name.length() == 0) {
            name = "Player " + id;
        }

        this.name = name;
        this.candyCount = 0;
        this.id = id;
    }

    public void handleBagContents(Game game, int bagContent) {
        switch(bagContent) {
            case Bag.PLUS_CANDY:
                addCandy(game, 1);
                break;
            case Bag.MINUS_CANDY:
                addCandy(game, -1);
                break;
            case Bag.MINUS_2_CANDY:
                addCandy(game, -2);
                break;
            case Bag.MINUS_3_CANDY:
                addCandy(game, -3);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /*Add candy(+) or subtract candy(-) from player's candy count.
        If subtracting candy, move the removed candy to the loss pile.
     */
    private void addCandy(Game game, int candyToAdd) {
        int initialPlayerCandy = this.candyCount;

        if(this.candyCount + candyToAdd < 0) {
            this.candyCount = 0;
        } else {
            this.candyCount += candyToAdd;
        }

        if(candyToAdd < 0) {
            game.addToLossPile(initialPlayerCandy - this.candyCount);
        }
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getCandyCount() { return candyCount; }

    public String candyDisplayString() {
        return Integer.toString(this.getCandyCount());
    }
}
