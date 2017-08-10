import javax.swing.*;

/**
 * Main client class
 * Created by Dotan Huberman on 26/6/17.
 */

public class Client {

    public static void main(String[] args) {
        boolean play = true;
        String computerName;
        int port;

        computerName = JOptionPane.showInputDialog("Enter computer name:\nClue: localhost");
        port = Integer.parseInt(JOptionPane.showInputDialog("Enter port\nClue: 7777"));

        //as long as the user wants to play will create games with the wanted server
        while (play) {
            ClientController controller = new ClientController(computerName,port);

            //get the startup state of the game
            controller.getMessageToClient();
            controller.getBoardFromServer();
            controller.updateView();

            //display a massage if the user is first or second
            if (controller.isMyTurn())
                JOptionPane.showConfirmDialog(null,"WELCOME\nYOU START THE GAME\nChoose two cards","Memory Game",JOptionPane.DEFAULT_OPTION);
            else
                JOptionPane.showConfirmDialog(null,"WELCOME\nYOU ARE SECOND\nWait for opponent to choose two cards","Memory Game",JOptionPane.DEFAULT_OPTION);

            // until the game is not over, for each turn waits for the user to choose 2 card and update the view accordingly
            do {
                for (int i = 0; i < 3; i++) {
                    controller.getBoardFromServer();
                    controller.updateView();
                }

                //get the new state of the game - who's turn, is game over, scores
                controller.getMessageToClient();
                controller.updateView();
            } while (!controller.isGameOver());

            //asks the user if he wants to play another game
            play = controller.anotherGame();
            controller.closeConnection();
        }
    }
}
