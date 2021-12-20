import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// Miranda Luo & Jared Novack
// Sep 13, 2019
// Description: GUI for the TextTwist game

public class GUI extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    private static final long serialVersionUID = 1L;
    private static final int PREF_W = 1400;
    private static final int PREF_H = 770;
    private RenderingHints hints = new RenderingHints
            (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    private Font font = new Font("Arial", Font.PLAIN, 40);
    private Font font2 = new Font("Arial", Font.PLAIN, 18);
    private TextTwist game;
    private String[] displayWords;
    private ArrayList<LetterBox[]> displayWords2;
    private MyButton shuffleButton = new MyButton(PREF_W / 2 - 300, PREF_H / 2 + 100, 200, 75, "Shuffle");
    private MyButton giveUpButton = new MyButton(PREF_W / 2 - 90, PREF_H / 2 + 100, 200, 75, "Give up");
    private MyButton restartButton = new MyButton(PREF_W / 2 + 110, PREF_H / 2 + 100, 200, 75, "Restart");
    private LetterBox[] masterWordArray;
    private LetterBox[] typedWord2;
    private char[] typedWord;
    private ArrayList<String> availableLetters;
    private boolean endScreen;
    private boolean isCorrect;

    public GUI() {
        this.setBackground(new Color(240, 248, 255));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setFocusable(true);
        this.addKeyListener(this);
        this.setLayout(null);
        reset();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHints(hints);
        g2.setFont(font);
        g2.setColor(Color.BLACK);

        FontMetrics fm = g2.getFontMetrics();

        String message = game.getMasterWordShuffled();

        int spaceBetween = 10;

        int msgWidth = fm.stringWidth(message);
        int msgHeight = fm.getHeight();

        int messageX = PREF_W / 2 - (masterWordArray[0].getWidth() * masterWordArray.length + spaceBetween * (message.length() - 1)) / 2;
        int messageY = 150;


        if (endScreen || game.isGameOver()) {
            for (int i = 0; i < masterWordArray.length; i++) {
                masterWordArray[i].setLetter(game.getMasterWord().substring(i, i + 1));
            }
        }

        for (int i = 0; i < masterWordArray.length; i++) {

            masterWordArray[i].setX(messageX + i * (spaceBetween + masterWordArray[i].getWidth()));
            masterWordArray[i].setY(messageY);

            masterWordArray[i].drawSelf(g2);

        }

        if (endScreen || game.isGameOver()) {
            restartButton.drawPiece(g2, this);
        } else {
            shuffleButton.drawPiece(g2, this);
            giveUpButton.drawPiece(g2, this);
        }

        for (int i = 0; i < typedWord2.length; i++) {

            typedWord2[i].setX(messageX + i * (spaceBetween + typedWord2[i].getWidth()));
            typedWord2[i].setY(messageY + typedWord2[i].getHeight() + 10);
            typedWord2[i].drawSelf(g2);

        }

        g2.setFont(font2);

        int widthRect = displayWords2.get(0)[0].getWidth();
        int heightRect = displayWords2.get(0)[0].getHeight();
        int spaceX = 3;
        int spaceY = 5;

        for (int i = 0; i < game.getWordList().size() / 2; i++) {
            if (i < 23) {
                for (int j = 0; j < displayWords2.get(i).length; j++) {
                    displayWords2.get(i)[j].setX(10 + (widthRect + spaceX) * j);
                    displayWords2.get(i)[j].setY(10 + (i * (spaceY + heightRect)));
                    displayWords2.get(i)[j].drawSelf(g2);
                }
            } else {
                int lastNum = LetterBox.getStringFromLetters(displayWords2.get(22)).length();
                for (int j = 0; j < displayWords2.get(i).length; j++) {
                    displayWords2.get(i)[j].setX(10 + spaceX + 5 + lastNum * (widthRect + spaceX) + (widthRect + spaceX) * j);
                    displayWords2.get(i)[j].setY(10 + ((i - 23) * (spaceY + heightRect)));
                    displayWords2.get(i)[j].drawSelf(g2);
                }
            }
        }

        int counter = 0;

        for (int i = game.getWordList().size() / 2; i < game.getWordList().size(); i++) {
            int xValStart = game.getWordList().size() - game.getWordList().size() / 2 <= 23 ? (PREF_W - 10 - displayWords[i].length() * (spaceX + widthRect)) : (PREF_W - 10 - spaceX - 5 - displayWords[i].length() * (spaceX + widthRect) - game.getMasterWord().length() * (spaceX + widthRect));

            if (counter < 23) {

                for (int j = 0; j < displayWords2.get(i).length; j++) {

                    displayWords2.get(i)[j].setX(xValStart + ((widthRect + spaceX) * j));
                    displayWords2.get(i)[j].setY(10 + (counter * (spaceY + heightRect)));
                    displayWords2.get(i)[j].drawSelf(g2);

                }

            } else {
                xValStart = (PREF_W - 10 - displayWords[i].length() * (spaceX + widthRect));

                for (int j = 0; j < displayWords2.get(i).length; j++) {
                    displayWords2.get(i)[j].setX(xValStart + ((widthRect + spaceX) * j));
                    displayWords2.get(i)[j].setY(10 + ((counter - 23) * (spaceY + heightRect)));
                    displayWords2.get(i)[j].drawSelf(g2);
                }
            }

            counter++;
        }

        fm = g2.getFontMetrics();

        if (game.isGameOver()) {
            g2.setColor(Color.GREEN);
            message = "You win!";
            msgWidth = fm.stringWidth(message);
            msgHeight = fm.getHeight();
            messageX = PREF_W / 2 - msgWidth / 2;
            messageY = PREF_H / 2 - msgHeight / 2;
            g2.drawString(message, PREF_W / 2 - msgWidth / 2, 440);
        }

        message = game.getWordsGuessed().size() + " out of " + game.getWordList().size() + " words found";
        msgWidth = fm.stringWidth(message);
        msgHeight = fm.getHeight();

        g2.setColor(Color.DARK_GRAY);
        g2.drawString(message, (PREF_W / 2 - msgWidth / 2), 380);

        if (isCorrect && game.getGuessed() != null && !game.isGameOver() && !endScreen) {
            g2.setColor(Color.GREEN);
            message = game.getGuessed() + " added";
            msgWidth = fm.stringWidth(message);
            msgHeight = fm.getHeight();
            g2.drawString(message, PREF_W / 2 - fm.stringWidth(message) / 2, 440);
        } else if (game.getWordsGuessed().contains(game.getGuessed()) && game.getGuessed() != null && !game.isGameOver() && !endScreen) {
            g2.setColor(new Color(240, 198, 34));
            message = game.getGuessed() + " was already added";
            msgWidth = fm.stringWidth(message);
            msgHeight = fm.getHeight();
            g2.drawString(message, PREF_W / 2 - fm.stringWidth(message) / 2, 440);
        } else if (!isCorrect && game.getGuessed() != null && !game.isGameOver() && !endScreen) {
            g2.setColor(Color.RED);
            message = game.getGuessed() + " is not valid";
            msgWidth = fm.stringWidth(message);
            msgHeight = fm.getHeight();
            g2.drawString(message, PREF_W / 2 - fm.stringWidth(message) / 2, 440);
        }

        message = "TextTwist";
        g2.setFont(new Font("Arial", Font.PLAIN, 60));
        g2.setColor(Color.DARK_GRAY);
        fm = g2.getFontMetrics();
        msgWidth = fm.stringWidth(message);
        msgHeight = fm.getHeight();
        g2.drawString(message, PREF_W / 2 - msgWidth / 2, 80);

    }

    public int getNextAvailableSpace() {
        for (int i = 0; i < typedWord2.length; i++) {
            if (!typedWord2[i].isLetterShown()) return i;
        }
        return typedWord2.length;
    }

    public void reset() {

        endScreen = false;

        isCorrect = false;

        game = new TextTwist("words.txt");

        typedWord = new char[game.getMasterWord().length()];
        typedWord2 = new LetterBox[game.getMasterWord().length()];

        for (int i = 0; i < typedWord2.length; i++) {
            typedWord2[i] = new LetterBox(" ", 90, 90, 0, 0, new Font("Arial", Font.PLAIN, 50), false);
        }

        ArrayList<String> temp = new ArrayList<>(game.getWordList());
        displayWords = new String[temp.size()];

        displayWords2 = new ArrayList<>();

        masterWordArray = new LetterBox[game.getMasterWord().length()];
        for (int i = 0; i < masterWordArray.length; i++) {
            masterWordArray[i] = new LetterBox(game.getMasterWordShuffled().substring(i, i + 1), 90, 90, 0, 0, new Font("Arial", Font.PLAIN, 50), true);
        }

        Collections.sort(temp, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.length() != o2.length()) {
                    return o1.length() - o2.length();
                }
                return o1.compareTo(o2);
            }
        });

        for (int i = 0; i < temp.size(); i++) {

            LetterBox[] wordy = new LetterBox[temp.get(i).length()];

            for (int j = 0; j < wordy.length; j++) {
                wordy[j] = new LetterBox(temp.get(i).substring(j, j + 1), 28, 28, 0, 0, font2, false);
                wordy[j].setThickness(1);
            }

            displayWords2.add(wordy);


            String thingy = "";
            for (int j = 0; j < temp.get(i).length(); j++) thingy += " ";
            displayWords[i] = thingy;
        }


        availableLetters = new ArrayList<String>();

        for (int i = 0; i < game.getMasterWord().length(); i++) {
            availableLetters.add(game.getMasterWord().substring(i, i + 1));
        }

    }

    public void shuffle() {
        for (int i = masterWordArray.length - 1; i > 0; i--) {
            int rand = (int) (Math.random() * i);
            LetterBox temp = masterWordArray[i];
            masterWordArray[i] = masterWordArray[rand];
            masterWordArray[rand] = temp;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();

        if (!(endScreen || game.isGameOver())) {

            for (LetterBox lb : masterWordArray) {

                if (lb.getBounds().contains(clicked) && availableLetters.contains(lb.getLetter())) {

                    typedWord2[getNextAvailableSpace()].setLetter(lb.getLetter());
                    typedWord2[getNextAvailableSpace()].setLetterShown(true);

                    availableLetters.remove(lb.getLetter());


                    for (LetterBox l : masterWordArray) {
                        if (l.getLetter().equals(lb.getLetter()) && !l.isChanged()) {
                            l.setChanged(true);
                            break;
                        }
                    }
                }
            }

            if (shuffleButton.getSelfBounds().contains(clicked)) {
                shuffle();
//         game.shuffle();
//         for (int i = 0; i < masterWordArray.length; i++) {
//            masterWordArray[i].setLetter(game.getMasterWordShuffled().substring(i, i+1));
//         }
            }

            if (giveUpButton.getSelfBounds().contains(clicked)) {
                endScreen = true;
                for (LetterBox[] lb : displayWords2) {
                    for (LetterBox letterBox : lb) {
                        if (!letterBox.isLetterShown()) {
                            letterBox.setFontColor(Color.RED);
                            letterBox.setLetterShown(true);
                        }
                    }
                }
                for (LetterBox lb : masterWordArray) {
                    lb.setChanged(false);
                }
                for (LetterBox lb : typedWord2) {
                    lb.setLetterShown(false);
                }
            }
        }

        if (restartButton.getSelfBounds().contains(clicked) && (endScreen || game.isGameOver())) {
            reset();
        }

        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private static void createAndShowGUI() {
        GUI gamePanel = new GUI();

        JFrame frame = new JFrame("TextTwist");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void letterUpdate() {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        char keyCode = (char) e.getKeyCode();

        if (!(endScreen || game.isGameOver())) {

            if (game.getMasterWord().indexOf((Character.toLowerCase(keyCode))) != -1 && getNextAvailableSpace() < typedWord2.length && availableLetters.contains(Character.toLowerCase(keyCode) + "")) {
                typedWord2[getNextAvailableSpace()].setLetter(Character.toLowerCase(keyCode) + "");
                typedWord2[getNextAvailableSpace()].setLetterShown(true);

                availableLetters.remove(Character.toLowerCase(keyCode) + "");


                for (LetterBox l : masterWordArray) {
                    if (l.getLetter().equals(Character.toLowerCase(keyCode) + "") && !l.isChanged()) {
                        l.setChanged(true);
                        break;
                    }
                }

            }

            if (keyCode == KeyEvent.VK_DOWN && game.getGuessed() != null && typedWord2[0].getLetter().equals(" ")) {

                availableLetters = new ArrayList<>();

                for (int i = 0; i < game.getMasterWord().length(); i++) {
                    availableLetters.add(game.getMasterWord().substring(i, i + 1));
                    masterWordArray[i].setChanged(false);
                }

                for (int i = 0; i < game.getGuessed().length(); i++) {

                    typedWord2[i].setLetter(game.getGuessed().substring(i, i + 1));
                    typedWord2[i].setLetterShown(true);

                    availableLetters.remove(game.getGuessed().substring(i, i + 1));


                    for (LetterBox l : masterWordArray) {
                        if (l.getLetter().equals(game.getGuessed().substring(i, i + 1)) && !l.isChanged()) {
                            l.setChanged(true);
                            break;
                        }
                    }
                }

            }

            if (keyCode == KeyEvent.VK_BACK_SPACE) {
                if (getNextAvailableSpace() - 1 != -1) {

                    for (int i = masterWordArray.length - 1; i >= 0; i--) {
                        if (masterWordArray[i].getLetter().equals(typedWord2[getNextAvailableSpace() - 1].getLetter()) && masterWordArray[i].isChanged()) {
                            masterWordArray[i].setChanged(false);
                            break;
                        }
                    }
                    availableLetters.add(typedWord2[getNextAvailableSpace() - 1].getLetter());
                    typedWord2[getNextAvailableSpace() - 1].setLetter(" ");
                    typedWord2[getNextAvailableSpace() - 1].setLetterShown(false);
                }
            }

            if (keyCode == KeyEvent.VK_ENTER) {

                if (!typedWord2[0].getLetter().equals(" ")) {

                    availableLetters = new ArrayList<String>();

                    for (int i = 0; i < game.getMasterWord().length(); i++) {
                        availableLetters.add(game.getMasterWord().substring(i, i + 1));
                        masterWordArray[i].setChanged(false);
                    }

                    String newThing = "";

                    for (LetterBox l : typedWord2) {
                        newThing += l.getLetter();
                        l.setLetter(" ");
                        l.setLetterShown(false);
                    }

                    game.setGuessed(newThing.trim());

                    isCorrect = game.getWordsLeft().contains(game.getGuessed());

                }

            }

            if (keyCode == KeyEvent.VK_SPACE) {
                shuffle();
            }

            game.update();

            for (LetterBox[] letterBoxes : displayWords2) {
                if (game.getWordsGuessed().contains(LetterBox.getStringFromLetters(letterBoxes))) {
                    for (LetterBox letterBox : letterBoxes) {
                        letterBox.setLetterShown(true);
                    }
                }
            }

            repaint();

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
