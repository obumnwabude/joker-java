/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import joker.util.JoCard;

/**
 * An image views of jocard
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public class CardView extends ImageView {
    
    /**
     * The width of this {@code ImageView}
     */
    private int x;
    
    /**
     * The height of this {@code ImageView}
     */
    private int y;
    
    /**
     * a placeholder of blue or red for 1 or 2 when this {@code CardView} is 
     * face down
     */
    private int downColour = 1;
    
    /**
     * The jocard set in view by this {@code CardView}
     */
    private JoCard myCard;

    /**
     * The image of this card view
     */
    private Image img;
    
    /**
     * Holds the names of the letters of suits
     */
    private static final String SUITS = "cdhsbr";
    
    /**
     * The basic image file name for front
     */
    private static final String IMG1 = "img/jocards/";
    
    /**
     * The basic image file name for back
     */
    private static final String IMG2 = "img/jocards/back";
    
    /**
     * Constructs a new {@code CardView} for the provided jocard argument
     * @param card the JoCard that will be set to view in this cardview
     */
    public CardView(JoCard card) {
        super();   
        x = 100;
        y = 100;
        myCard = card;
        String use = IMG1 + String.format("%02d%s.gif", myCard.rank, 
                SUITS.charAt(myCard.suit - 1));
        img = new Image(use, x, y, true, true);
        setImage(img);
    }

    /**
     * Constructs a new {@code CardView} for a face down Card
     */
    public CardView() {
        super();
        x = 100;
        y = 100;
        img = new Image((IMG2 + downColour + ".gif"), x, y, true, true);
        setImage(img);
    }
    
    /**
     * Changes the background of the facedown cards from either blue or red
     */
    public void changeFace() {
        if (downColour == 1) {
            downColour = 2;
            img = new Image((IMG2 + downColour), x, y, true, true);
            setImage(img);
        } else {
            downColour = 1;
            img = new Image((IMG2 + downColour), x, y, true, true);
            setImage(img);
        }
    }
    
    /**
     * Returns a reference to the {@linkplain joker.util.JoCard} set in view 
     * by this {@code CardView}
     * @return a joCard set in view by this CardView
     */
    public JoCard card() {
        return myCard;
    }
}
