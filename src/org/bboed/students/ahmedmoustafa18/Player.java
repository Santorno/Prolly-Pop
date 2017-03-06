package org.bboed.students.ahmedmoustafa18;

public class Player {
    private String name;
    private int id;

    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;

    public Player(String name, int id) {
        if(name == null || name.length() == 0) {
            name = "Player " + id;
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
