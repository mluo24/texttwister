import java.awt.*;

// Miranda Luo
// Sep 20, 2019
// Description: Defines a LetterBox to be displayed on the GUI

public class LetterBox {

   private String letter;
   private int width, height;
   private int x, y;
   private Font font;
   private Color backgroundColor;
   private Color borderColor;
   private Color fontColor;
   private int thickness;
   private boolean letterShown;
   private boolean isChanged;

   public LetterBox(String letter, int width, int height, int x, int y, Font font, boolean letterShown) {
      this.letter = letter;
      this.width = width;
      this.height = height;
      this.x = x;
      this.y = y;
      this.font = font;
      this.letterShown = letterShown;
      thickness = 2;
      backgroundColor = Color.WHITE;
      borderColor = Color.LIGHT_GRAY;
      fontColor = Color.DARK_GRAY;
      isChanged = false;
   }

   public void drawSelf(Graphics2D g2) {
      g2.setFont(font);
      FontMetrics fm = g2.getFontMetrics(font);
      int msgY = y + (((height - fm.getHeight()) / 2) + fm.getAscent());
      int msgX = fm.stringWidth(letter) >= width ? x : x + width / 2 - fm.stringWidth(letter.toUpperCase()) / 2;

      g2.setColor(borderColor);
      g2.fillRect(x, y, width, height);
      if (!isChanged)
         g2.setColor(backgroundColor);
      else
         g2.setColor(Color.BLUE);
      g2.fillRect(x + thickness, y + thickness, width - thickness * 2, height - thickness * 2);

      if (letterShown) {
         if (!isChanged)
            g2.setColor(fontColor);
         else
            g2.setColor(Color.WHITE);
         g2.drawString(letter.toUpperCase(), msgX, msgY);
      }
   }

   public Rectangle getBounds() {
      return new Rectangle(x, y, width, height);
   }

   public String getLetter() {
      return letter;
   }

   public void setLetter(String letter) {
      this.letter = letter;
   }

   public int getWidth() {
      return width;
   }

   public void setWidth(int width) {
      this.width = width;
   }

   public int getHeight() {
      return height;
   }

   public void setHeight(int height) {
      this.height = height;
   }

   public int getX() {
      return x;
   }

   public void setX(int x) {
      this.x = x;
   }

   public int getY() {
      return y;
   }

   public void setY(int y) {
      this.y = y;
   }

   public boolean isLetterShown() {
      return letterShown;
   }

   public void setLetterShown(boolean letterShown) {
      this.letterShown = letterShown;
   }

   public Font getFont() {
      return font;
   }

   public void setFont(Font font) {
      this.font = font;
   }

   public Color getBackgroundColor() {
      return backgroundColor;
   }

   public void setBackgroundColor(Color backgroundColor) {
      this.backgroundColor = backgroundColor;
   }

   public Color getBorderColor() {
      return borderColor;
   }

   public void setBorderColor(Color borderColor) {
      this.borderColor = borderColor;
   }

   public Color getFontColor() {
      return fontColor;
   }

   public void setFontColor(Color fontColor) {
      this.fontColor = fontColor;
   }

   public int getThickness() {
      return thickness;
   }

   public void setThickness(int thickness) {
      this.thickness = thickness;
   }

   public boolean isChanged() {
      return isChanged;
   }

   public void setChanged(boolean isChanged) {
      this.isChanged = isChanged;
   }

   public static String getStringFromLetters(LetterBox[] lb) {
      StringBuilder s = new StringBuilder();
      for (LetterBox letterBox : lb)
         s.append(letterBox.getLetter());
      return s.toString();
   }

}
