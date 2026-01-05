package br.com.dio.ui.custom.screen;

import java.awt.Dimension;
import java.util.Map;

import br.com.dio.service.BoardService;
import br.com.dio.ui.custom.panel.MainPanel;
import br.com.dio.ui.custom.frame.MainFrame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import br.com.dio.ui.custom.button.ResetButton;
import br.com.dio.ui.custom.button.CheckGameStatusButton;
import br.com.dio.ui.custom.button.FinishGameButton;
import br.com.dio.model.GameStatusEnum;


public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;

    private JButton finishGameButton;
    private JButton resetButton;
    private JButton checkGameStatusButton;

    public MainScreen(final Map<String, String> gameConfig) {
        this.boardService = new BoardService(gameConfig);
    }

    public void buildMainScreen() {
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        addresetButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();

    }

    private void addFinishGameButton(JPanel mainPanel) {
        finishGameButton = new FinishGameButton(e -> {
            if (boardService.gameIsFinished()) {
                JOptionPane.showMessageDialog(null, "Parabéns você concluiu o jogo");
                resetButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            }else{
                JOptionPane.showMessageDialog(null, "Seu jogo tem alguma incons");
            }
        });
        mainPanel.add(finishGameButton);

    }

    private void addCheckGameStatusButton(final JPanel mainPanel) {
        checkGameStatusButton = new FinishGameButton(e -> {
            var hasErrors = boardService.hasErrors();
            var gameStatus = boardService.getStatus();
            var message = switch (gameStatus) {
            case NON_STARTED -> "O jogo ainda não foi iniciado";
            case INCOMPLETE -> "O jogo está incompleto";
            case COMPLETE -> "O jogo está completo";

            };

            message += hasErrors ? " e contém erros" : " e não contém erros";
            JOptionPane.showMessageDialog(null, message);

        });
        mainPanel.add(checkGameStatusButton);
    }

    private void addresetButton(JPanel mainPanel) {
        resetButton = new ResetButton(e -> {
            var dialogResult = JOptionPane.showConfirmDialog(null, "Deseja realmente reiniciar o jogo?",
                    "Limpar o  jogo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE

            );

            if (dialogResult == 0) {
                boardService.reset();
            }
        });
        mainPanel.add(resetButton);
    }

}