/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import joker.board.LogMaker;
import joker.util.JoCard;

/**
 * <em>Joker</em> card game starts here.
 * @author OBUMUNEME NWABUDE
 */
public class App extends Application {
        
    /**
     * The {@linkplain javafx.scene.control.MenuBar} of this game
     */
    public GameMenuBar gMB;
    
    /**
     * The root pane
     */
    public BorderPane root;
    
    /**
     * The game pane found at the center of the {@linkplain #root root}
     */
    public BorderPane gamePane;
    
    /**
     * A reference to the Stage used by {@link #start start(Stage)}
     */
    public Stage myStage;
    
    /**
     * The LogMaker used in producing the state of the game
     */
    public LogMaker logMaker;
    
    /*
     * Execution of the game begins here.
     * @param args the command line arguments
     
    public static void main(String[] args) {
        launch(args);
    }*/
    
    @Override
    public void init() {
        root = new BorderPane();
        gamePane = new BorderPane();
    }
    
    @Override 
    public void start(Stage myStage) {
        this.myStage = myStage;
        gMB = new GameMenuBar(myStage);
        root.setTop(gMB);
        root.setCenter(gamePane);
        Scene myScene = new Scene(root, 700, 450);
        myStage.setScene(myScene);
        myStage.setTitle("Joker");
        myStage.show();
    }
    
    /**
     * The {@linkplain javafx.scene.control.MenuBar} of the <em>Joker</em> card
     * game {@linkplain javafx.application.Application}.
     *
     * @author OBUMUNEME NWABUDE
     * @since 1.0
     */
    public class GameMenuBar extends MenuBar {

        /**
         * The App Menu
         */
        public Menu gameMenu;

        /**
         * The New App menu item of the {@linkplain #gameMenu gameMenu}
         */
        public MenuItem newMI;

        /**
         * The Undo App menu item of the {@linkplain #gameMenu}
         */
        public MenuItem undoMI;

        /**
         * The Redo App menu item of the {@linkplain #gameMenu}
         */
        public MenuItem redoMI;

        /**
         * The Load App menu item of the {@linkplain #gameMenu}
         */
        public MenuItem loadMI;

        /**
         * The Save App menu item of the {@linkplain #gameMenu}
         */
        public MenuItem saveMI;

        /**
         * The Save and Exit App menu item of the {@linkplain #gameMenu}
         */
        public MenuItem saveAndExitMI;

        /**
         * The separator before Settings used in {@linkplain #gameMenu}
         */
        public SeparatorMenuItem sepS;

        /**
         * The Settings Menu item of the {@linkplain #gameMenu}
         */
        public MenuItem settingsMI;

        /**
         * The separator before Exit used in {@linkplain #gameMenu}
         */
        public SeparatorMenuItem sepG;

        /**
         * The Exit App menu item of the {@linkplain #gameMenu}
         */
        public MenuItem exitMI;

        /**
         * The Help Menu
         */
        public Menu helpMenu;

        /**
         * The Rules Menu Item of the {@linkplain #helpMenu}
         */
        public MenuItem rulesMI;

        /**
         * The About Menu Item of the {@linkplain #helpMenu}
         */
        public MenuItem aboutMI;

        /**
         * The Stage that owns this {@code GameMenuBar}
         */
        private final Stage myStage;

        /**
         * The Rules Dialog used in when {@linkplain #rulesMI} is clicked
         */
        private RulesDialog rulesD;

        /**
         * Used to monitor if rules Dialog is opened
         */
        private int ruleCount = 0;

        /**
         * Constructs a new <code>GameMenuBar</code>
         *
         * @param stage the Stage that called this GameMenuBar
         */
        public GameMenuBar(Stage stage) {
            super();
            myStage = stage;
            rulesD = new RulesDialog();
            makeMenus();
            addAccelerators();
            addHandlers();
        }

        /**
         * makes the game menu
         */
        private void makeGameMenu() {
            gameMenu = new Menu("_Game");
            newMI = new MenuItem("_New Game");
            undoMI = new MenuItem("Undo Ga_me");
            redoMI = new MenuItem("Redo Gam_e");
            loadMI = new MenuItem("L_oad Game");
            saveMI = new MenuItem("_Save Game");
            saveAndExitMI = new MenuItem("Save and Ex_it Game");
            sepS = new SeparatorMenuItem();
            settingsMI = new MenuItem("Se_ttings");
            sepG = new SeparatorMenuItem();
            exitMI = new MenuItem("_Exit");
            gameMenu.getItems().addAll(newMI, undoMI, redoMI, loadMI, saveMI,
                    saveAndExitMI, sepS, settingsMI, sepG, exitMI);
        }

        /**
         * Makes help menu
         */
        private void makeHelpMenu() {
            helpMenu = new Menu("_Help");
            rulesMI = new MenuItem("R_ules");
            aboutMI = new MenuItem("A_bout");
            helpMenu.getItems().addAll(rulesMI, aboutMI);
        }

        /**
         * Make all menus and add to menu bar
         */
        private void makeMenus() {
            makeGameMenu();
            makeHelpMenu();
            getMenus().addAll(gameMenu, helpMenu);
        }

        /**
         * Adds accelerators to menu items
         */
        private void addAccelerators() {
            newMI.setAccelerator(KeyCombination.valueOf("shortcut+N"));
            undoMI.setAccelerator(KeyCombination.valueOf("shortcut+X"));
            redoMI.setAccelerator(KeyCombination.valueOf("shortcut+Y"));
            loadMI.setAccelerator(KeyCombination.valueOf("shortcut+O"));
            saveMI.setAccelerator(KeyCombination.valueOf("shortcut+S"));
            settingsMI.setAccelerator(KeyCombination.valueOf("shortcut+T"));
            rulesMI.setAccelerator(KeyCombination.valueOf("shortcut+U"));
            aboutMI.setAccelerator(KeyCombination.valueOf("shortcut+B"));
        }

        /**
         * add handlers
         */
        private void addHandlers() {
            myStage.setOnCloseRequest(u -> {
                if (rulesD.isShowing()) {
                    rulesD.close();
                }
            });
            rulesD.setOnCloseRequest(u -> {
                ruleCount = 0;
                rulesD = new RulesDialog();
            });
            newMI.setOnAction(ae -> {
            });
            loadMI.setOnAction(ae -> {
            });
            saveMI.setOnAction(ae -> {
            });
            undoMI.setOnAction(ae -> {
            });
            redoMI.setOnAction(ae -> {
            });
            saveAndExitMI.setOnAction(ae -> {
            });
            exitMI.setOnAction(u -> Platform.exit());
            settingsMI.setOnAction(u -> {
            });
            rulesMI.setOnAction(u -> {
                if (ruleCount == 0) {
                    rulesD.show();
                    ruleCount++;
                } else {
                    ((Stage) rulesD.getDialogPane().getScene().getWindow()).toFront();
                }
            });
            aboutMI.setOnAction(u -> {
                new Dialog<Void>() {
                    {
                        setTitle("About Joker");
                        getDialogPane().setContentText("Joker is an interesting "
                                + "game to play");
                        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                        initModality(Modality.APPLICATION_MODAL);
                        show();
                        initStyle(StageStyle.UTILITY);
                    }
                };
            });
            undoMI.setDisable(true);
            redoMI.setDisable(true);
            newMI.setDisable(true);
            loadMI.setDisable(true);
            saveMI.setDisable(true);
            saveAndExitMI.setDisable(true);
        }

        /**
         * The {@linkplain javafx.scene.control.Dialog} used for Rules
         */
        private class RulesDialog extends Dialog<Void> {

            private final HBox row1;
            private final HBox row2;
            private final VBox next3;
            private final VBox content;

            /**
             * Constructs a new {@code RulesDialog}
             */
            public RulesDialog() {
                super();
                setTitle("Joker Rules");
                content = new VBox(15);
                row1 = new HBox(10);
                row2 = new HBox(10);
                next3 = new VBox(10);
                fillRow1();
                fillRow2();
                fillNext3();
                content.setMaxSize(400, 400);
                getDialogPane().setContent(content);
                getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                initModality(Modality.NONE);
                initStyle(StageStyle.UTILITY);
            }

            /**
             * Fills the first row
             */
            private void fillRow1() {
                CardView groundnuts = new CardView(new JoCard(10, 1));
                Label groundnutsDef = new Label("Groundnuts");
                VBox col1 = new VBox(3);
                col1.getChildren().addAll(groundnuts, groundnutsDef);
                CardView square = new CardView(new JoCard(9, 2));
                Label squareDef = new Label("Square");
                VBox col2 = new VBox(3);
                col2.getChildren().addAll(square, squareDef);
                CardView red = new CardView(new JoCard(7, 3));
                Label redDef = new Label("Red");
                VBox col3 = new VBox(3);
                col3.getChildren().addAll(red, redDef);
                CardView cassava = new CardView(new JoCard(6, 4));
                Label cassavaDef = new Label("Cassava");
                VBox col4 = new VBox(3);
                col4.getChildren().addAll(cassava, cassavaDef);
                row1.getChildren().addAll(col1, col2, col3, col4);
                content.getChildren().add(row1);
            }

            /**
             * Fills the second row
             */
            private void fillRow2() {
                CardView pick2 = new CardView(new JoCard(7, 2));
                Label pick2Def = new Label("Pick 2");
                VBox col1 = new VBox(3);
                col1.getChildren().addAll(pick2, pick2Def);
                CardView pick4 = new CardView(new JoCard(14, 5));
                Label pick4Def = new Label("Pick 4");
                VBox col2 = new VBox(3);
                col2.getChildren().addAll(pick4, pick4Def);
                CardView skip = new CardView(new JoCard(1, 3));
                Label skipDef = new Label("Skip you");
                VBox col3 = new VBox(3);
                col3.getChildren().addAll(skip, skipDef);
                CardView jCommand = new CardView(new JoCard(11, 4));
                Label jCommandDef = new Label("J Command");
                VBox col4 = new VBox(4);
                col4.getChildren().addAll(jCommand, jCommandDef);
                row2.getChildren().addAll(col1, col2, col3, col4);
                content.getChildren().add(row2);
            }

            /**
             * Fills the third row
             */
            private void fillNext3() {
                String norm = "You can play any card if it has the same colour or"
                        + " the same number as the card on the board";
                String pick = "When you play Pick 2 or Pick 4, the next player has"
                        + " to pick 2 or 4 cards. If adding and cooling is enabled,"
                        + " then the next player can add the number of cards to be "
                        + "picked by playing another Pick 2 or Pick 4 (this will "
                        + "make the next player to pick more cards) or they can"
                        + " cool the picking by playing Skip you or J Command.";
                String skip = "When Skip you is played, the next player is "
                        + "automatically skipped.";
                String command = "J Command can be played at any time, except if "
                        + "the option is disabled in settings. When J Command is "
                        + "played, the person who played it has to command a "
                        + "colour to be played by the next player, if the next "
                        + "player does not have a card of that colour, they go board."
                        + "Any card of the demanded colour must be played or "
                        + "another J Command can be played.";
                Text normText = new Text(norm);
                normText.setWrappingWidth(550);
                Text pickText = new Text(pick);
                pickText.setWrappingWidth(550);
                Text skipText = new Text(skip);
                skipText.setWrappingWidth(550);
                Text commandText = new Text(command);
                commandText.setWrappingWidth(550);
                next3.getChildren().addAll(normText, pickText, skipText, 
                        commandText);
                content.getChildren().add(next3);
            }

        }
    }
    
}