/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #4
 * 1 - 5026231012 - Zihni Aryanto Putra Buana
 * 2 - 5026231085 - Firmansyah Adi Prasetyo
 * 3 - 5026231174 - Muhamamd Razan Parisya Putra
 */

package sudoku.src;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SudokuMain extends JFrame {
   private static final long serialVersionUID = 1L;

   private Timer timer;
   private JLabel timerView;
   private int timeSecond = 0;
   private GameBoardPanel board;
   private JButton btnNewGame;
   private JDialog pauseDialog;

   public static int input = 0;

   public SudokuMain() {
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());
      cp.setBackground(new Color(33, 37, 49));

      // Create and set up the timer
      timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               timeSecond++;
               updateTimerLabel();
            }
      });
      timer.start();

      timerView = new JLabel("Timer: 00:00");
      timerView.setHorizontalAlignment(JLabel.RIGHT);
      timerView.setFont(new Font("Figtree", Font.PLAIN, 14));
      timerView.setForeground(Color.WHITE);

      // Create timer panel with pause button
      JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      timerPanel.setBackground(new Color(33, 37, 49));
      
      // Pause button
      JButton btnPause = new JButton("Pause");
      btnPause.setBackground(new Color(33, 37, 49));
      btnPause.setForeground(Color.WHITE);
      btnPause.setFocusPainted(false);
      btnPause.setFont(new Font("Figtree", Font.PLAIN, 12));
      btnPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               showPauseDialog();
            }
      });

      timerPanel.add(timerView);
      timerPanel.add(btnPause);

      // Create the game board
      board = new GameBoardPanel(timer);
      board.setBackground(new Color(33, 37, 49));
      
      // Create the number panel and New Game button container
      JPanel bottomPanel = new JPanel(new BorderLayout());
      
      // Number Panel button
      numberList numberPanel = new numberList();
      bottomPanel.add(numberPanel, BorderLayout.NORTH);

      // New Game button
      btnNewGame = new JButton("New Game");
      btnNewGame.setBackground(new Color(33, 37, 49));
      btnNewGame.setForeground(Color.WHITE);
      btnNewGame.setFocusPainted(false);
      btnNewGame.setFont(new Font("Figtree", Font.PLAIN, 12));
      btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               startNewGame();
            }
      });
      bottomPanel.add(btnNewGame, BorderLayout.CENTER);

      // Setup container
      cp.add(board, BorderLayout.CENTER);
      cp.add(timerPanel, BorderLayout.NORTH);
      cp.add(bottomPanel, BorderLayout.SOUTH);

      // Initialize the game board to start the game
      board.newGame();

      pack(); // Pack the UI components, instead of using setSize()
      setLocationRelativeTo(null); // Center the window
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Handle window-closing
      setTitle("Sudoku");
      setVisible(true);

      // Create pause dialog (but don't show it yet)
      createPauseDialog();
   }

   // Create the pause dialog
   private void createPauseDialog() {
      pauseDialog = new JDialog(this, "Game Paused", true);
      pauseDialog.setUndecorated(true); // Remove window decorations
      pauseDialog.setResizable(false); // Prevent resizing

      // Create a panel with the same size as the game board
      JPanel pausePanel = new JPanel(new BorderLayout());
      pausePanel.setBackground(new Color(33, 37, 49));

      // Pause message
      JLabel pauseLabel = new JLabel("Game is Paused", SwingConstants.CENTER);
      pauseLabel.setForeground(Color.WHITE);
      pauseLabel.setFont(new Font("Figtree", Font.BOLD, 24));
      pauseLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

      // Button panel
      JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 20));
      buttonPanel.setBackground(new Color(33, 37, 49));
      buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 40));

      // Resume button
      JButton btnResume = new JButton("Resume Game");
      btnResume.setBackground(new Color(59, 65, 84));
      btnResume.setForeground(Color.WHITE);
      btnResume.setFont(new Font("Figtree", Font.PLAIN, 18));
      btnResume.setPreferredSize(new Dimension(GameBoardPanel.BOARD_WIDTH - 80, 50));
      btnResume.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            resumeGame();
         }
      });

      // New Game button in pause dialog
      JButton btnNewGamePause = new JButton("New Game");
      btnNewGamePause.setBackground(new Color(59, 65, 84));
      btnNewGamePause.setForeground(Color.WHITE);
      btnNewGamePause.setFont(new Font("Figtree", Font.PLAIN, 18));
      btnNewGamePause.setPreferredSize(new Dimension(GameBoardPanel.BOARD_WIDTH - 80, 50));
      btnNewGamePause.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            startNewGame();
            pauseDialog.dispose();
         }
      });

      buttonPanel.add(btnResume);
      buttonPanel.add(btnNewGamePause);

      // Add components to pause panel
      pausePanel.add(pauseLabel, BorderLayout.NORTH);
      pausePanel.add(buttonPanel, BorderLayout.CENTER);

      // Add pause panel to dialog
      pauseDialog.add(pausePanel);

      // Set dialog size to match game board
      pauseDialog.setSize(GameBoardPanel.BOARD_WIDTH, GameBoardPanel.BOARD_HEIGHT);
      pauseDialog.setLocationRelativeTo(this);

      // Add a way to close the undecorated dialog
      pausePanel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            // Optional: add a specific click area or condition to prevent accidental closing
            if (e.getClickCount() == 2) {
               resumeGame();
            }
         }
      });
   }

   // Show pause dialog and stop timer
   private void showPauseDialog() {
      timer.stop();
      pauseDialog.setVisible(true);
   }

   // Resume the game
   private void resumeGame() {
      timer.start();
      pauseDialog.dispose();
   }

   // Start a new game
   private void startNewGame() {
      board.newGame();
      timeSecond = 0;
      timer.start();
      updateTimerLabel();
   }

   // Update the timer label text in MM:SS format
   private void updateTimerLabel() {
      int minutes = timeSecond / 60;
      int seconds = timeSecond % 60;
      String formattedTime = String.format("%02d:%02d", minutes, seconds);
      timerView.setText("Timer: " + formattedTime);
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new SudokuMain());
   }
}