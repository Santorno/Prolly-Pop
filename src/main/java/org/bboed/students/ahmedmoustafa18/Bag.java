package org.bboed.students.ahmedmoustafa18;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import java.util.*;

public class Bag {
    private int id;

    private Vector2f position;

    private Image renderImage;
    private Image idleImage;
    private Image hoverImage;

    private Rectangle hitbox;

    private Card card;

    private static Random rand = new Random();

    private int bagContent;
    private boolean bagSelected = false;

    private static Map<Integer, Integer> cardMapping = new HashMap<>();
    private static List<Integer> possibleItems = new ArrayList<>();

    private static final int HOVER_MARGIN = 5;

    public static final int PLUS_CANDY = 0;
    public static final int MINUS_CANDY = 1;
    public static final int MINUS_2_CANDY = 2;
    public static final int MINUS_3_CANDY = 3;

    static {
        populatePossibleItems();

        for(int i = 1; i <= Game.BAG_COUNT; i++) {
            int randNum = rand.nextInt(possibleItems.size());

            cardMapping.put(i, possibleItems.get(randNum));
            possibleItems.remove(randNum);
        }
    }

    public Bag(int id) throws SlickException {
        this.id = id;

        this.idleImage = new Image("resources/images/game/bags/" + id + ".png");
        this.hoverImage = new Image("resources/images/game/bags-hover/" + id + ".png");

        this.position = getPositionFromId(id);
        this.hitbox = new Rectangle(position.getX(), position.getY(), hoverImage.getWidth(), hoverImage.getHeight());

        this.renderImage = idleImage;

        this.bagContent = cardMapping.get(id);
        this.card = new Card(bagContent);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
        renderImage.draw(position.getX(), position.getY());
    }

    public void update(GameContainer gc, StateBasedGame sbg, Player currentPlayer, Game game, int delta) throws SlickException {
        if(Card.cardDisplayed) {
            return;
        }

        boolean mouseInHitbox = hitbox.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY());

        if(mouseInHitbox && renderImage != hoverImage) {
            renderImage = hoverImage;
            position.x -= HOVER_MARGIN;
            position.y -= HOVER_MARGIN;
        } else if(!mouseInHitbox && renderImage != idleImage) {
            renderImage = idleImage;
            position.x += HOVER_MARGIN;
            position.y += HOVER_MARGIN;
        }

        if(mouseInHitbox && gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            handleBagSelection(game, game.getCurrentPlayer());
            System.out.println(bagContent);
            bagSelected = true;
            card.makeVisible();
        }
    }

    public Vector2f getPositionFromId(int id) {
        int x = 0;
        int y = 0;
        int columnsMoved = 0;

//        boolean column1 = false;
//        boolean row1 = id <= 5;

        while(id > 5) {
            id -= 5;
            columnsMoved++;
        }

//        if(id == 1) {
//            column1 = true;
//        }

        x = Game.BAG_MARGIN_X + ((id - 1) * idleImage.getWidth()) + ((id - 1) * Game.BETWEEN_BAG_MARGIN_X) /* + (column1 ? 0 : Game.BETWEEN_BAG_MARGIN_X)*/;
        y = Game.BAG_MARGIN_Y + ((columnsMoved) * idleImage.getHeight()) + (columnsMoved * Game.BETWEEN_BAG_MARGIN_Y) /* (row1 ? 0 : Game.BETWEEN_BAG_MARGIN_Y*/;

        return new Vector2f(x, y);
    }

    public void handleBagSelection(Game game, Player currentPlayer) {
        currentPlayer.handleBagContents(game, bagContent);
        card.makeVisible();
    }

    private static void populatePossibleItems() {
        for(int i = 0; i < 1; i++) {
            possibleItems.add(MINUS_3_CANDY);
        }

        for(int i = 0; i < 2; i++) {
            possibleItems.add(MINUS_2_CANDY);
        }

        for(int i = 0; i < 3; i++) {
            possibleItems.add(MINUS_CANDY);
        }

        for(int i = 0; i < 14; i++) {
            possibleItems.add(PLUS_CANDY);
        }
    }

    public Vector2f getPosition() {
        return position;
    }

    public Image getRenderImage() {
        return renderImage;
    }

    public Card getCard() { return card; }

    public boolean getBagSelected() { return bagSelected; }
}
