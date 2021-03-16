import java.awt.*;

public class DrawingGates {
    public void drawGate(int x, int y, String s, String io, Graphics g, Color ELEMENT_COLOR, Color LINE_COLOR) {
        switch (s) {
            case "AND":
                g.setColor(ELEMENT_COLOR);

                g.fillRect(x, y, 20, 30);

                //okrugla glava
                g.fillOval(x + 10, y, 30, 30);

                // prikljuci
                g.fillRect(x - 5,y + 7,6,5);
                g.fillRect(x - 5,y + 17,6,5);
                break;
            case "NAND":
                g.setColor(ELEMENT_COLOR);

                g.fillRect(x, y, 20, 30);

                //okrugla glava
                g.fillOval(x + 10, y, 30, 30);

                // prikljuci
                g.fillRect(x - 5,y + 7,6,5);
                g.fillRect(x - 5,y + 17,6,5);

                // krug ispred
                g.fillOval(x + 40,y + 12,7,7);
                break;
            case "OR":
                g.setColor(ELEMENT_COLOR);

                g.fillOval(x + 10,y, 40, 30);
                g.setColor(Color.white);
                g.fillOval(x,y, 30, 30);

                // prikljuci
                g.setColor(ELEMENT_COLOR);
                g.fillRect(x + 20,y + 7,10,5);
                g.fillRect(x + 20,y + 17,10,5);
                break;
            case "XOR":
                g.setColor(new Color(45, 239, 19));

                g.fillOval(x + 10,y, 40, 30);
                g.setColor(Color.white);
                g.fillOval(x,y, 30, 30);
                g.setColor(new Color(45, 239, 19));
                // i: x i2: y i3: width i4: lenght i5: agol od kude pocinja  i6:agol kolko dugacko
                g.drawArc(x - 5, y, 30, 30, 90, -180 );

                // prikljuci
                g.fillRect(x + 15,y + 7,10,5);
                g.fillRect(x + 15,y + 17,10,5);
                break;
            case "NOR":
                g.setColor(ELEMENT_COLOR);

                g.fillOval(x + 10,y, 40, 30);
                g.setColor(Color.white);
                g.fillOval(x,y, 30, 30);

                // prikljuci
                g.setColor(ELEMENT_COLOR);
                g.fillRect(x + 20,y + 7,10,5);
                g.fillRect(x + 20,y + 17,10,5);
                // krug ispred
                g.fillOval(x + 50,y + 12,7,7);
                break;
            case "INPUT":
            case "OUTPUT":
                g.setColor(ELEMENT_COLOR);
                g.fillRect(x, y, 20, 20);

                g.setColor(Color.black);
                int fontSize = 10;
                Font f = new Font("Comic Sans MS", Font.BOLD, fontSize);
                g.setFont(f);
                FontMetrics fm = g.getFontMetrics();
                int asc = fm.getAscent();
                int desc = fm.getDescent();
                if (s.equals("INPUT")) {
                    g.drawString(io, x + (45 - fm.stringWidth(s)) / 2,
                            y + (asc + (20 - (asc + desc)) / 2));
                } else {
                    g.drawString(io, x + (55 - fm.stringWidth(s)) / 2,
                            y + (asc + (20 - (asc + desc)) / 2));
                }
                break;
            case "NOT":
                g.setColor(ELEMENT_COLOR);
                g.fillPolygon(new int[] {x, x + 30, x}, new int[] {y + 20, y + 10, y}, 3);
        }
    }
}
