package org.example.utils;

import java.util.concurrent.*;

import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.utils.*;

import java.util.*;

public class ChessGUI extends Application {
    private static final int TILE_SIZE = 80;
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;
    private Board board;
    private Position selectedPosition = null;
    private GridPane gridPane;
    private String currentTurn = "White";
    private Label whiteTimeLabel;
    private Label blackTimeLabel;
    private Button startGameButton;
    private Button surrenderButton;
    private boolean gameStarted = false;
    private Timer whiteTimer;
    private Timer blackTimer;
    private TimerTask whiteTask;
    private TimerTask blackTask;
    private ChoiceBox<String> timerChoiceBox;
    private ChoiceBox<String> gameModeChoiceBox;
    private String playerSide;
    private Label sideLabel;
    private Position lastMoveFrom = null;
    private Position lastMoveTo = null;

    @Override
    public void start(Stage primaryStage) {
        board = new Board();
        board.initializeBoard();
        gridPane = new GridPane();
        drawBoard();
        initializeOverlay();

        VBox root = new VBox();
        root.getChildren().addAll(gridPane, createOverlay());

        Scene scene = new Scene(root, WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE + 100);
        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void resetGame() {
        board = new Board();
        board.initializeBoard();
        currentTurn = "White";
        gameStarted = false;
        lastMoveFrom = null;
        lastMoveTo = null;
        drawBoard();
        whiteTimeLabel.setText("White Time: 00:00");
        blackTimeLabel.setText("Black Time: 00:00");
        startGameButton.setDisable(false);
        surrenderButton.setVisible(false);
    }

    private void drawBoard() {
        if (currentTurn.equals("White")) {
            if (board.checkMate("White") == 2) {
                if (whiteTask != null) {
                    whiteTask.cancel();
                }
                if (blackTask != null) {
                    blackTask.cancel();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(null);
                alert.setContentText("Black wins!");
                alert.showAndWait();
                resetGame();
                return;
            }
            if (board.checkMate("Black") == 1) {
                if (whiteTask != null) {
                    whiteTask.cancel();
                }
                if (blackTask != null) {
                    blackTask.cancel();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(null);
                alert.setContentText("Stalemate!");
                alert.showAndWait();
                resetGame();
                return;
            }
        } else {
            if (board.checkMate("Black") == 2) {
                if (whiteTask != null) {
                    whiteTask.cancel();
                }
                if (blackTask != null) {
                    blackTask.cancel();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(null);
                alert.setContentText("White Wins!");
                alert.showAndWait();
                resetGame();
                return;
            }
            if (board.checkMate("Black") == 1) {
                if (whiteTask != null) {
                    whiteTask.cancel();
                }
                if (blackTask != null) {
                    blackTask.cancel();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(null);
                alert.setContentText("Stalemate!");
                alert.showAndWait();
                resetGame();
                return;
            }
        }
        gridPane.getChildren().clear();
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                Rectangle square = new Rectangle(TILE_SIZE, TILE_SIZE);
                square.setFill((row + col) % 2 == 0 ? Color.BEIGE : Color.BROWN);
                square.setStroke(Color.BLACK);
                final int r = row, c = col;
                square.setOnMouseClicked(e -> handleTileClick(e, r, c));
                gridPane.add(square, col, row);
            }
        }
        if (lastMoveFrom != null && lastMoveTo != null) {
            highlightSelectedTile(lastMoveFrom.x, lastMoveFrom.y, Color.DARKSLATEBLUE);
            highlightSelectedTile(lastMoveTo.x, lastMoveTo.y, Color.DARKSLATEBLUE);
        }
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                addPieceImage(row, col);
            }
        }
    }

    private void initializeOverlay() {
        whiteTimeLabel = new Label("White Time: 00:00");
        blackTimeLabel = new Label("Black Time: 00:00");
        startGameButton = new Button("Start Game");
        startGameButton.setOnAction(e -> startGame());

        surrenderButton = new Button("Surrender");
        surrenderButton.setOnAction(e -> surrenderGame());
        surrenderButton.setVisible(false);

        timerChoiceBox = new ChoiceBox<>();
        timerChoiceBox.getItems().addAll("1 min", "5 mins", "10 mins", "30 mins");
        timerChoiceBox.setValue("5 mins"); // Default value

        gameModeChoiceBox = new ChoiceBox<>();
        gameModeChoiceBox.getItems().addAll("vs Player", "vs Bot");
        gameModeChoiceBox.setValue("vs Player"); // Default value

        sideLabel = new Label("Side: ");
    }

    private HBox createOverlay() {
        HBox overlay = new HBox();
        VBox timeBox = new VBox();
        timeBox.getChildren().addAll(whiteTimeLabel, blackTimeLabel);

        HBox startGameBox = new HBox();
        startGameBox.getChildren().addAll(startGameButton, sideLabel);
        startGameBox.setSpacing(10); // Add some spacing between the button and the label

        VBox configBox = new VBox();
        configBox.getChildren().addAll(timerChoiceBox, gameModeChoiceBox, startGameBox, surrenderButton);
        overlay.getChildren().addAll(timeBox, configBox);
        return overlay;
    }

    private void startGame() {
        // Initialize the game state
        currentTurn = "White";
        gameStarted = true; // Set the flag to true when the game starts
        drawBoard();

        // Disable the start game button and show the surrender button
        startGameButton.setDisable(true);
        surrenderButton.setVisible(true);

        // Get the selected time in seconds
        int selectedTime = 300; // Default to 5 minutes
        switch (timerChoiceBox.getValue()) {
            case "1 min":
                selectedTime = 60;
                break;
            case "5 mins":
                selectedTime = 300;
                break;
            case "10 mins":
                selectedTime = 600;
                break;
            case "30 mins":
                selectedTime = 1800;
                break;
        }

        // Get the selected game mode
        String gameMode = gameModeChoiceBox.getValue();

        // Handle game mode
        if (gameMode.equals("vs Bot")) {
            playerSide = showSideSelectionDialog();
            if (playerSide.equals("Random")) {
                playerSide = Math.random() < 0.5 ? "White" : "Black";
            }
            sideLabel.setText("Side: " + playerSide);
            if (playerSide.equals("Black")) {
                currentTurn = "White";
                Platform.runLater(this::handleBotMove); // Bot makes the first move if player is black
            }
        } else {
            sideLabel.setText("Side: ");
        }

        // Start the timers for each player
        whiteTimer = new Timer();
        blackTimer = new Timer();

        int finalSelectedTime = selectedTime;
        whiteTask = new TimerTask() {
            int whiteTime = finalSelectedTime;
            @Override
            public void run() {
                if (currentTurn.equals("White")) {
                    whiteTime--;
                    int minutes = whiteTime / 60;
                    int seconds = whiteTime % 60;
                    Platform.runLater(() -> whiteTimeLabel.setText(String.format("White Time: %02d:%02d", minutes, seconds)));
                    if (whiteTime <= 0) {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Game Over");
                            alert.setHeaderText(null);
                            alert.setContentText("Black wins by timeout!");
                            alert.showAndWait();
                            resetGame();
                        });
                        whiteTask.cancel();
                        blackTask.cancel();
                    }
                }
            }
        };

        int finalSelectedTime1 = selectedTime;
        blackTask = new TimerTask() {
            int blackTime = finalSelectedTime1;
            @Override
            public void run() {
                if (currentTurn.equals("Black")) {
                    blackTime--;
                    int minutes = blackTime / 60;
                    int seconds = blackTime % 60;
                    Platform.runLater(() -> blackTimeLabel.setText(String.format("Black Time: %02d:%02d", minutes, seconds)));
                    if (blackTime <= 0) {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Game Over");
                            alert.setHeaderText(null);
                            alert.setContentText("White wins by timeout!");
                            alert.showAndWait();
                            resetGame();
                        });
                        whiteTask.cancel();
                        blackTask.cancel();
                    }
                }
            }
        };

        whiteTimer.scheduleAtFixedRate(whiteTask, 0, 1000);
        blackTimer.scheduleAtFixedRate(blackTask, 0, 1000);
    }

    private String showSideSelectionDialog() {
        List<String> choices = Arrays.asList("White", "Black", "Random");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Random", choices);
        dialog.setTitle("Choose Side");
        dialog.setHeaderText("Select your side");
        dialog.setContentText("Choose your side:");
        Optional<String> result = dialog.showAndWait();
        return result.orElse("Random");
    }

    private void surrenderGame() {
        // End the game and reset the state
        if (whiteTask != null) {
            whiteTask.cancel();
        }
        if (blackTask != null) {
            blackTask.cancel();
        }
        if(currentTurn.equals("White")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Black wins by surrender!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("White wins by surrender!");
            alert.showAndWait();
        }
        resetGame();

    }
    private void addPieceImage(int row, int col) {
        for (Piece piece : board.whitePieces) {
            if (piece.getPosition().x == row && piece.getPosition().y == col) {
                Image image = loadImage(piece);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(TILE_SIZE);
                imageView.setFitHeight(TILE_SIZE);
                imageView.setMouseTransparent(true);
                gridPane.add(imageView, col, row);
                return;
            }
        }
        for (Piece piece : board.blackPieces) {
            if (piece.getPosition().x == row && piece.getPosition().y == col) {
                Image image = loadImage(piece);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(TILE_SIZE);
                imageView.setFitHeight(TILE_SIZE);
                imageView.setMouseTransparent(true);
                gridPane.add(imageView, col, row);
                return;
            }
        }
    }

    private Image loadImage(Piece piece) {
        String color = piece.getColor().toLowerCase();
        String type = piece.getClass().getSimpleName().toLowerCase();
        String imagePath = String.format("/images/%s_%s.png", color, type);
        return new Image(getClass().getResourceAsStream(imagePath));
    }

    private void highlightPossibleMoves(List<Position> possibleMoves) {
        for (Position pos : possibleMoves) {
            Rectangle highlight = new Rectangle(TILE_SIZE, TILE_SIZE);
            highlight.setFill(Color.LIGHTGREEN);
            highlight.setOpacity(0.5);
            highlight.setMouseTransparent(true);
            gridPane.add(highlight, pos.y, pos.x);
        }
    }

    private void handleTileClick(MouseEvent event, int row, int col) {
        if (!gameStarted) {
            return; // Ignore clicks if the game has not started
        }
        Position clickedPosition = new Position(row, col);
        if (selectedPosition == null) {
            if (isPieceAt(clickedPosition) && isCurrentPlayerPiece(clickedPosition)) {
                selectedPosition = clickedPosition;
                highlightSelectedTile(row, col, Color.LIGHTBLUE);
                List<Position> possibleMoves = getPossibleMovesForPieceAt(clickedPosition);
                highlightPossibleMoves(possibleMoves);
            }
        } else {
            if (isValidMove(selectedPosition, clickedPosition)) {
                movePiece(selectedPosition, clickedPosition);
                Platform.runLater(() -> {
                    drawBoard(); // Draw the board immediately after the player's move
                    switchTurn(); // Switch turn after the board is drawn
                    if (gameModeChoiceBox.getValue().equals("vs Bot") && !currentTurn.equals(playerSide)) {
                        new Thread(() -> {
                            handleBotMove();
                            Platform.runLater(this::drawBoard); // Draw the board after the bot's move
                        }).start();
                    }
                });
            }
            selectedPosition = null;
            Platform.runLater(this::drawBoard);
        }
    }

    private List<Position> getPossibleMovesForPieceAt(Position pos) {
        for (Piece piece : board.whitePieces) {
            if (piece.getPosition().equals(pos)) {
                return board.getLegalMoves(piece.getPossibleMoves(), piece);
            }
        }
        for (Piece piece : board.blackPieces) {
            if (piece.getPosition().equals(pos)) {
                return board.getLegalMoves(piece.getPossibleMoves(), piece);
            }
        }
        return new ArrayList<>();
    }

    private boolean isPieceAt(Position pos) {
        return board.whitePieces.stream().anyMatch(p -> p.getPosition().equals(pos)) ||
                board.blackPieces.stream().anyMatch(p -> p.getPosition().equals(pos));
    }

    private boolean isCurrentPlayerPiece(Position pos) {
        if (currentTurn.equals("White")) {
            return board.whitePieces.stream().anyMatch(p -> p.getPosition().equals(pos));
        } else {
            return board.blackPieces.stream().anyMatch(p -> p.getPosition().equals(pos));
        }
    }

    private boolean isValidMove(Position from, Position to) {
        for (Piece piece : board.whitePieces) {
            if (piece.getPosition().equals(from)) {
                List<Position> legalMoves = board.getLegalMoves(piece.getPossibleMoves(), piece);
                return legalMoves.contains(to);
            }
        }
        for (Piece piece : board.blackPieces) {
            if (piece.getPosition().equals(from)) {
                List<Position> legalMoves = board.getLegalMoves(piece.getPossibleMoves(), piece);
                return legalMoves.contains(to);
            }
        }
        return false;
    }

    private void movePiece(Position from, Position to) {
        for (Piece piece : board.whitePieces) {
            if (piece.getPosition().equals(from)) {
                setEnPassant(board);
                if (piece instanceof King) {
                    handleCastle(piece, to);
                }
                piece.move(to);
                board.deleteTakenPieces(piece);
                if(piece instanceof Pawn){
                    if(board.checkPromotion(piece)){
                        promotePawn((Pawn) piece);
                    }
                }
                lastMoveFrom = from;
                lastMoveTo = to;
                drawBoard();
                return;
            }
        }
        for (Piece piece : board.blackPieces) {
            if (piece.getPosition().equals(from)) {
                setEnPassant(board);
                if (piece instanceof King) {
                    handleCastle(piece, to);
                }
                piece.move(to);
                board.deleteTakenPieces(piece);
                if(piece instanceof Pawn){
                    if(board.checkPromotion(piece)){
                        promotePawn((Pawn) piece);
                    }
                }
                lastMoveFrom = from;
                lastMoveTo = to;
                drawBoard();
                return;
            }
        }
    }

    private void setEnPassant(Board board) {
        for (Piece piece : board.blackPieces) {
            if (piece instanceof Pawn) {
                ((Pawn) piece).setEnPassant(false);
            }
        }
        for (Piece piece : board.whitePieces) {
            if (piece instanceof Pawn) {
                ((Pawn) piece).setEnPassant(false);
            }
        }
    }

    private void handleCastle(Piece piece, Position to) {
        if (Math.abs(piece.getPosition().y - to.y) == 2) {
            String side = (to.y > piece.getPosition().y) ? "Right" : "Left";
            board.castle(piece, side);
        }
    }

    private void promotePawn(Pawn pawn) {
        List<String> choices = Arrays.asList("Queen", "Rook", "Bishop", "Knight");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Queen", choices);
        dialog.setTitle("Pawn Promotion");
        dialog.setHeaderText("Promote your pawn");
        dialog.setContentText("Choose a piece:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(choice -> board.promotePawn(pawn, choice));
    }

    private void switchTurn() {
        currentTurn = currentTurn.equals("White") ? "Black" : "White";
        if (gameModeChoiceBox.getValue().equals("vs Bot") && !currentTurn.equals(playerSide)) {
            handleBotMove();
        }
    }

    private void highlightSelectedTile(int row, int col, Color color) {
        Rectangle highlight = new Rectangle(TILE_SIZE, TILE_SIZE);
        highlight.setFill(color);
        highlight.setOpacity(0.5);
        highlight.setMouseTransparent(true); // Make the highlight rectangle mouse transparent
        gridPane.add(highlight, col, row);
    }

    private void handleBotMove() {
        Board copiedBoard = board.copy(); // Create a copy of the board
        int bestValue = currentTurn.equals("White") ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Piece bestPiece = null;
        Position bestMove = null;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        List<Piece> botPieces = currentTurn.equals("White") ? copiedBoard.whitePieces : copiedBoard.blackPieces;

        for (Piece piece : botPieces) {
            List<Position> legalMoves = copiedBoard.getLegalMoves(piece.getPossibleMoves(), piece);

            // Sort legal moves based on capturing, check, and score
            legalMoves.sort((m1, m2) -> {
                Piece target1 = copiedBoard.getPieceAt(m1);
                Piece target2 = copiedBoard.getPieceAt(m2);
                int score1 = (target1 != null ? Board.getPieceValue(target1) : 0) + (copiedBoard.isCheckAfterMove(piece, m1) ? 50 : 0);
                int score2 = (target2 != null ? Board.getPieceValue(target2) : 0) + (copiedBoard.isCheckAfterMove(piece, m2) ? 50 : 0);
                return Integer.compare(score2, score1); // Prioritize higher scores
            });

            for (Position move : legalMoves) {
                Position originalPosition = piece.getPosition();
                boolean firstMovePawn = false;
                boolean enPassant = false;
                boolean movedKing = false;
                boolean movedRook = false;
                piece.move(move);
                if (piece instanceof Pawn) {
                    firstMovePawn = ((Pawn) piece).getFirstMove();
                    enPassant = ((Pawn) piece).getEnPassant();
                }
                if (piece instanceof King) {
                    movedKing = ((King) piece).kingMoved();
                }
                if (piece instanceof Rook) {
                    movedRook = ((Rook) piece).rookMoved();
                }
                Piece deletedPiece = copiedBoard.deleteTakenPieces(piece);
                int moveValue = copiedBoard.iterativeDeepening(2, !currentTurn.equals("White")); // Depth of 2 for example
                piece.move(originalPosition); // Undo move
                if (piece instanceof Pawn) {
                    ((Pawn) piece).setFirstMove(firstMovePawn);
                    ((Pawn) piece).setEnPassant(enPassant);
                }
                if (piece instanceof King) {
                    ((King) piece).setMoved(movedKing);
                }
                if (piece instanceof Rook) {
                    ((Rook) piece).setMoved(movedRook);
                }
                if (deletedPiece != null) {
                    if (deletedPiece.getColor().equals("White")) {
                        copiedBoard.whitePieces.add(deletedPiece);
                    } else {
                        copiedBoard.blackPieces.add(deletedPiece);
                    }
                }

                if (currentTurn.equals("White")) {
                    if (moveValue > bestValue) {
                        bestValue = moveValue;
                        bestPiece = piece;
                        bestMove = move;
                    }
                    alpha = Math.max(alpha, bestValue);
                } else {
                    if (moveValue < bestValue) {
                        bestValue = moveValue;
                        bestPiece = piece;
                        bestMove = move;
                    }
                    beta = Math.min(beta, bestValue);
                }

                if (beta <= alpha) break; // Alpha-beta pruning
            }
        }

        if (bestPiece != null && bestMove != null) {
            movePiece(bestPiece.getPosition(), bestMove);
            switchTurn();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}