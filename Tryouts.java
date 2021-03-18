



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.Random;

import javax.sound.sampled.Line;
import javax.swing.*;

// We are going to create a Game of 15 Puzzle with Java 8 and Swing
// If you have some questions, feel free to ue comments ;)
public class Tryouts extends JPanel { // our grid will be drawn in a dedicated Panel


    private int size;
    private int dimension;
    // Foreground Color
    private Color ELEMENT_COLOR = new Color(45, 239, 19);
    private Color LINE_COLOR = Color.black;
    // elements sadrzi sve elemente koji ce se crtati
    private String[] elements = new String[40];
    // EP pokazuje koliko elementa ima za crtanje
    private int EP = 1;
    // trenutna pozicija pritisnuta mausom
    int[] x = new int [40];
    int[] y = new int [40];
    // polje celog ekrana , ovde ce se znati koji element gde se nalazi
    private int[][] field = new int[1000][700];
    // koliko elemenata su povezani
    private int[] connectedElementsArray = new int[40];
    // matrica koja cova linije gde pocinju i zavrsavaju
    private int[][] linesToDraw = new int[40][10];
    // niz sa indeksima od elementa: tipa element sa id  = 3 je 1 clan u nizu inputa
    private int[] arrayOfLogicElements = new int[40];
    private int[] elementsToComputeForOutput = new int[40];

    // svi inputi i elementi
    private LogicalInput[] inputs = new LogicalInput[20];
    private int inputsCounter = 0;
    private LogicalAND[] ands = new LogicalAND[20];
    private int andsCounter = 0;
    private LogicalOutput[] outputs = new LogicalOutput[20];
    private int outputsCounter = 0;
    private LogicalOR[] ors = new LogicalOR[20];
    private int orsCounter = 0;
    private LogicalNOT[] nots = new LogicalNOT[20];
    private int notsCounter = 0;
    private LogicalNOR[] nors = new LogicalNOR[20];
    private int norsCounter = 0;
    private LogicalNAND[] nands = new LogicalNAND[20];
    private int nandsCounter = 0;


    public Tryouts(int size, int dim) {
        this.size = size;
        this.dimension = dim;
        System.out.println(field[0][0]);

        setPreferredSize(new Dimension(dimension + 300, dimension));
        setBackground(Color.WHITE);
       // setForeground(FOREGROUND_COLOR);
        setFont(new Font("SansSerif", Font.BOLD, 60));

        final JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setSize(400,100);
        // label.setLocation(300, 159);

        JMenuBar mb=new JMenuBar();
        JMenu menu1 =new JMenu("File");
        JMenuItem  newfile = new JMenuItem("New");
        JMenu menu2 =new JMenu("Edit");
        JMenuItem  cngColor1 = new JMenuItem("Change color of elemets");
        JMenuItem  cngColor2 = new JMenuItem("Change color of lines");
        JMenuItem menu3 =new JMenuItem("Compute");
        JMenuItem menu4 =new JMenuItem("Help");
        menu1.add(newfile); menu2.add(cngColor1); menu2.add(cngColor2);
        mb.add(menu1); mb.add(menu2); mb.add(menu3); mb.add(menu4);
        add(mb);

        final JPopupMenu popupmenu = new JPopupMenu("Edit");
        JPopupMenu addComponent = new JPopupMenu("add component");
        JMenuItem add = new JMenuItem("add element >");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem remove = new JMenuItem("Remove");
        JMenuItem and = new JMenuItem("and");
        JMenuItem or = new JMenuItem("or");
        JMenuItem input = new JMenuItem("input");
        JMenuItem output = new JMenuItem("output");
        JMenuItem not = new JMenuItem("not");
        JMenuItem nor = new JMenuItem("nor");
        JMenuItem nand = new JMenuItem("nand");
        JMenuItem connect = new JMenuItem("connect");
        addComponent.add(input); addComponent.add(output); addComponent.add(and); addComponent.add(or); addComponent.add(not);
        addComponent.add(nor); addComponent.add(nand);

        popupmenu.add(add);
        // popupmenu.add(copy); popupmenu.add(remove);
        popupmenu.add(connect);
        add(popupmenu);
        add(label);

        final boolean[] open = {true, true};
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 1) {
                   // System.out.println(e.getX() + 190 + " , " + e.getY() + 60);
                   // int gz = e.getY() + 60;
                   // if (gz > 600) System.out.println("su k: " + gz);
                    if (elements[field[e.getX()][e.getY()]] == "INPUT") {
                        boolean state = inputs[arrayOfLogicElements[field[e.getX()][e.getY()]]].GetState();
                        inputs[arrayOfLogicElements[field[e.getX()][e.getY()]]].SetState(!state);
                        repaint();
                    } else {
                        if (open[0]) {
                            if (e.getY() + 60 < 710 && e.getX() + 190 < 1130) {
                                popupmenu.setLocation(e.getX() + 185, e.getY() + 30);
                                x[EP] = e.getX();
                                y[EP] = e.getY();
                                popupmenu.setVisible(true);
                                open[0] = !open[0];
                            }
                        } else {
                            popupmenu.setVisible(false);
                            addComponent.setVisible(false);
                            open[0] = !open[0];
                        }
                       // open[0] = !open[0];
                        label.setText(String.valueOf(field[e.getX()][e.getY()]));
                    }
                }
            }
        });

        newfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // sa sledecom komandom se brise sve iz programa
                EP = 1;
                x = new int[40];
                y = new int[40];
                field = new int[1000][700];
                linesToDraw = new int[40][10];
                connectedElementsArray = new int[40];
                elementsToComputeForOutput = new int[40];

                inputs = new LogicalInput[20];
                inputsCounter = 0;
                ands = new LogicalAND[20];
                andsCounter = 0;
                outputs = new LogicalOutput[20];
                outputsCounter = 0;
                ors = new LogicalOR[20];
                orsCounter = 0;
                nors = new LogicalNOR[20];
                norsCounter = 0;
                nands = new LogicalNAND[20];
                nandsCounter = 0;

                repaint();
                // --------------
            }
        });

        cngColor1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ELEMENT_COLOR = JColorChooser.showDialog(null, "Choose a color", ELEMENT_COLOR);
                popupmenu.setVisible(false);
                addComponent.setVisible(false);
                open[0] = true;
                repaint();
            }
        });
        cngColor2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LINE_COLOR = JColorChooser.showDialog(null, "Chooose a color", LINE_COLOR);
                popupmenu.setVisible(false);
                addComponent.setVisible(false);
                open[0] = true;
                repaint();
            }
        });

        menu3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // pokretanje resenja
                for (int i = 0; i < outputsCounter; i++) {
                    int localId = elementsToComputeForOutput[i];
                    System.out.println("local id: " +  localId);
                    if (localId / 100 == 0) {
                        boolean result = ands[arrayOfLogicElements[localId % 100]].ComputeOutput();
                        if (result)  outputs[i].SetState(1);
                        else    outputs[i].SetState(0);
                    } else if (localId / 100 == 1) {
                        boolean result = ors[arrayOfLogicElements[localId % 100]].ComputeOutput();
                        if (result) outputs[i].SetState(1);
                        else outputs[i].SetState(0);
                    } else if (localId / 100 == 2) {
                        boolean result = nots[arrayOfLogicElements[localId % 100]].ComputeOutput();
                        if (result) outputs[i].SetState(1);
                        else outputs[i].SetState(0);
                    } else if (localId / 100 == 3) {
                        boolean result = nands[arrayOfLogicElements[localId % 100]].ComputeOutput();
                        if (result) outputs[i].SetState(1);
                        else outputs[i].SetState(0);
                    } else if (localId / 100 == 4) {
                        boolean result = nors[arrayOfLogicElements[localId % 100]].ComputeOutput();
                        if (result) outputs[i].SetState(1);
                        else outputs[i].SetState(0);
                    }
                }
                repaint();
            }
        });

        and.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (field[x[EP]][y[EP]] == 0) {
                    arrayOfLogicElements[EP] = andsCounter;
                    ands[andsCounter++] = new LogicalAND();

                    elements[EP++] = "AND";
                    for (int i = 0; i < 60; i++)
                        for (int j = 0; j < 50; j++) {
                            field[x[EP - 1] + i][y[EP - 1] + j] = EP - 1;
                        }

                    popupmenu.setVisible(false);
                    addComponent.setVisible(false);
                    open[0] = true;
                    repaint();
                }
            }
        });

        input.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //  label.setText("cut MenuItem clicked.");
                if (field[x[EP]][y[EP]] == 0) {
                    arrayOfLogicElements[EP] = inputsCounter;
                    inputs[inputsCounter++] = new LogicalInput();
                    inputs[inputsCounter - 1].SetState(false);
                    // System.out.println(inputs[inputsCounter - 1].GetState());

                    elements[EP++] = "INPUT";
                    for (int i = 0; i < 60; i++)
                        for (int j = 0; j < 50; j++) {
                            field[x[EP - 1] + i][y[EP - 1] + j] = EP - 1;
                            // System.out.println(x[EP]+","+y[EP]+" :"+field[x[EP] + i][y[EP] + j]);
                        }
                    popupmenu.setVisible(false);
                    addComponent.setVisible(false);
                    open[0] = true;
                    repaint();
                }
            }
        });

        output.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //  label.setText("cut MenuItem clicked.");
                if (field[x[EP]][y[EP]] == 0) {
                    arrayOfLogicElements[EP] = outputsCounter;
                    outputs[outputsCounter++] = new LogicalOutput();
                    outputs[outputsCounter - 1].SetState(2);
                    // System.out.println(inputs[inputsCounter - 1].GetState());

                    elements[EP++] = "OUTPUT";
                    for (int i = 0; i < 60; i++)
                        for (int j = 0; j < 50; j++) {
                            field[x[EP - 1] + i][y[EP - 1] + j] = EP - 1;
                            // System.out.println(x[EP]+","+y[EP]+" :"+field[x[EP] + i][y[EP] + j]);
                        }
                    popupmenu.setVisible(false);
                    addComponent.setVisible(false);
                    open[0] = true;
                    repaint();
                }
            }
        });
        or.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //  label.setText("cut MenuItem clicked.");
                if (field[x[EP]][y[EP]] == 0) {
                    arrayOfLogicElements[EP] = orsCounter;
                    ors[orsCounter++] = new LogicalOR();

                    elements[EP++] = "OR";
                    for (int i = 0; i < 60; i++)
                        for (int j = 0; j < 50; j++) {
                            field[x[EP - 1] + i][y[EP - 1] + j] = EP - 1;
                            // System.out.println(x[EP]+","+y[EP]+" :"+field[x[EP] + i][y[EP] + j]);
                        }
                    popupmenu.setVisible(false);
                    addComponent.setVisible(false);
                    open[0] = true;
                    repaint();
                }
            }
        });
        not.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (field[x[EP]][y[EP]] == 0) {
                    arrayOfLogicElements[EP] = notsCounter;
                    nots[notsCounter++] = new LogicalNOT();

                    elements[EP++] = "NOT";
                    for (int i = 0; i < 60; i++)
                        for (int j = 0; j < 50; j++) {
                            field[x[EP - 1] + i][y[EP - 1] + j] = EP - 1;
                        }
                    popupmenu.setVisible(false);
                    addComponent.setVisible(false);
                    open[0] = true;
                    repaint();
                }
            }
        });

        nand.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (field[x[EP]][y[EP]] == 0) {
                    arrayOfLogicElements[EP] = nandsCounter;
                    nands[nandsCounter++] = new LogicalNAND();

                    elements[EP++] = "NAND";
                    for (int i = 0; i < 60; i++)
                        for (int j = 0; j < 50; j++) {
                            field[x[EP - 1] + i][y[EP - 1] + j] = EP - 1;
                        }
                    popupmenu.setVisible(false);
                    addComponent.setVisible(false);
                    open[0] = true;
                    repaint();
                }
            }
        });

        nor.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (field[x[EP]][y[EP]] == 0) {
                    arrayOfLogicElements[EP] = norsCounter;
                    nors[norsCounter++] = new LogicalNOR();

                    elements[EP++] = "NOR";
                    for (int i = 0; i < 60; i++)
                        for (int j = 0; j < 50; j++) {
                            field[x[EP - 1] + i][y[EP - 1] + j] = EP - 1;
                        }
                    popupmenu.setVisible(false);
                    addComponent.setVisible(false);
                    open[0] = true;
                    repaint();
                }
            }
        });

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addComponent.setLocation(x[EP] + 255, y[EP] + 60);
                addComponent.setVisible(true);
            }
        });

        copy.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                label.setText("copy MenuItem clicked. " + field[x[EP]+ 10][y[EP] + 10]);
                /* new file
                     na klik na new file za da se otvoriprazan list treba EP da se setuje na 1;
                */
                // sa sledecom komandom se brise sve iz programa
         /*       EP = 1;
                repaint();
                inputs = new LogicalInput[10];
                inputsCounter = 0;
                ands = new LogicalAND[10];
                andsCounter = 0;
                outputs = new LogicalOutput[10];
                outputsCounter = 0;
                elementsToComputeForOutput = new int[10];
                ors = new LogicalOR[10];
                orsCounter = 0; */
                // --------------
             /*   for (int i = 0; i < 80; i++)
                    for (int j = 0; j < 50; j++) {
                        // field[x[EP] + i][y[EP] + j] = EP;
                       // System.out.println(x[EP]+","+y[EP]+" :"+field[x[EP] + i][y[EP] + j]);
                    }*/

                /*
                boolean result = ands[arrayOfLogicElements[EP - 1]].ComputeOutput();
                System.out.println("resultat: " + result);
                arrayOfLogicElements[EP] = inputsCounter;
                inputs[inputsCounter++] = new LogicalInput();
                inputs[inputsCounter - 1].SetState(result);

                elements[EP++] = "OUTPUT";
                repaint();
                */

                // upisivanje resenje u outpute
                for (int i = 0; i < outputsCounter; i++) {
                    int localId = elementsToComputeForOutput[i];
                    System.out.println("local id: " +  localId);
                    if (localId / 100 == 0) {
                        boolean result = ands[arrayOfLogicElements[localId % 100]].ComputeOutput();
                        // System.out.println("result: " + result);
                        if (result)  outputs[i].SetState(1);
                        else    outputs[i].SetState(0);
                    } else if (localId / 100 == 1) {
                        boolean result = ors[arrayOfLogicElements[localId % 100]].ComputeOutput();
                        // System.out.println("result: " + result);
                        if (result) outputs[i].SetState(1);
                        else outputs[i].SetState(0);
                    }
                }
                repaint();
            }
        });

        remove.addActionListener(new ActionListener(){


            public void actionPerformed(ActionEvent e) {
                // label.setText("paste MenuItem clicked.");
                /*if (field[x[EP]][y[EP]] != 0) {
                    // ovde se u nizove oznacavaju tako da se ne crtaju  na ekranu
                    elements[field[x[EP]][y[EP]]] = "-";
                    linesToDraw[field[x[EP]][y[EP]]][4] = 0;
                    linesToDraw[field[x[EP]][y[EP]]][9] = 0;
                    // ovde se obelezava da na datom prostoru vise nema elemenat
                    for (int i = -80; i < 80; i++)
                        for (int j = -50; j < 50; j++) {
                            if (x[EP] + i < 0 || y[EP] + j < 0) {
                                continue;
                            }
                            else
                                field[x[EP] + i][y[EP] + j] = 0;
                            // System.out.println(x[EP]+","+y[EP]+" :"+field[x[EP] + i][y[EP] + j]);        neka provera
                        }
                    popupmenu.setVisible(false);
                    addComponent.setVisible(false);
                    open[0] = true;
                    repaint();
                }*/

            }
        });

        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (field[x[EP]][y[EP]] != 0) {
                    int elementId = field[x[EP]][y[EP]];
                    if (connectedElementsArray[elementId] == 2) {
                        //do something ,svi ulazi povezani
                        popupmenu.setVisible(false);
                        addComponent.setVisible(false);
                        open[0] = true;
                        repaint();
                        return;
                    } else if (connectedElementsArray[elementId] == 0) {
                        // povezi prvi ulaz
                        int i = x[EP];
                        int j = y[EP];
                        while (field[i][j] != 0) i--;
                        i++;
                        while (field[i][j] != 0) j--;
                        j++;
                        // pocetak linije
                        switch (elements[elementId]) {
                            case "AND":
                            case "NAND":
                                linesToDraw[elementId][0] = i - 5;
                                linesToDraw[elementId][1] = j + 7;
                                break;
                            case "OR":
                            case "NOR":
                                linesToDraw[elementId][0] = i + 40;
                                linesToDraw[elementId][1] = j + 7;
                                break;
                            case "OUTPUT":
                            case "NOT":
                                linesToDraw[elementId][0] = i - 2;
                                linesToDraw[elementId][1] = j + 7;
                                break;
                        }

                        final boolean[] traziNovuTacku = {true};
                        addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if (e.getButton() == MouseEvent.BUTTON1 &&
                                        e.getClickCount() == 1 &&
                                        traziNovuTacku[0]) {
                                    int i = e.getX();
                                    int j = e.getY();
                                    int elementID2 = field[i][j];

                                    if (elementID2 != 0) {
                                        switch (elements[elementId]) {
                                            case "AND":
                                                switch (elements[elementID2]) {
                                                    case "AND":
                                                        ands[arrayOfLogicElements[elementId]].AddInput(ands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "INPUT":
                                                        ands[arrayOfLogicElements[elementId]].AddInput(inputs[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "OR":
                                                        ands[arrayOfLogicElements[elementId]].AddInput(ors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOT":
                                                        ands[arrayOfLogicElements[elementId]].AddInput(nots[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NAND":
                                                        ands[arrayOfLogicElements[elementId]].AddInput(nands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOR":
                                                        ands[arrayOfLogicElements[elementId]].AddInput(nors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                }
                                                break;
                                            case "OR":
                                                switch (elements[elementID2]) {
                                                    case "AND":
                                                        ors[arrayOfLogicElements[elementId]].AddInput(ands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "INPUT":
                                                        ors[arrayOfLogicElements[elementId]].AddInput(inputs[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "OR":
                                                        ors[arrayOfLogicElements[elementId]].AddInput(ors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOT":
                                                        ors[arrayOfLogicElements[elementId]].AddInput(nots[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NAND":
                                                        ors[arrayOfLogicElements[elementId]].AddInput(nands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOR":
                                                        ors[arrayOfLogicElements[elementId]].AddInput(nors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                }
                                                break;
                                            case "OUTPUT":
                                                int localId = arrayOfLogicElements[elementId];
                                                switch (elements[elementID2]) {
                                                    case "AND":
                                                        elementsToComputeForOutput[localId] = elementID2;
                                                        break;
                                                    case "OR":
                                                        elementsToComputeForOutput[localId] = 100 + elementID2;
                                                        break;
                                                    case "NOT":
                                                        elementsToComputeForOutput[localId] = 200 + elementID2;
                                                        break;
                                                    case "NAND":
                                                        elementsToComputeForOutput[localId] = 300 + elementID2;
                                                        break;
                                                    case "NOR":
                                                        elementsToComputeForOutput[localId] = 400 + elementID2;
                                                        break;
                                                }
                                                break;
                                            case "NOT":
                                                switch (elements[elementID2]) {
                                                    case "AND":
                                                        nots[arrayOfLogicElements[elementId]].AddInput(ands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "INPUT":
                                                        nots[arrayOfLogicElements[elementId]].AddInput(inputs[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "OR":
                                                        nots[arrayOfLogicElements[elementId]].AddInput(ors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOT":
                                                        nots[arrayOfLogicElements[elementId]].AddInput(nots[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NAND":
                                                        nots[arrayOfLogicElements[elementId]].AddInput(nands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOR":
                                                        nots[arrayOfLogicElements[elementId]].AddInput(nors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                }
                                                break;
                                            case "NAND":
                                                switch (elements[elementID2]) {
                                                    case "AND":
                                                        nands[arrayOfLogicElements[elementId]].AddInput(ands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "INPUT":
                                                        nands[arrayOfLogicElements[elementId]].AddInput(inputs[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "OR":
                                                        nands[arrayOfLogicElements[elementId]].AddInput(ors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOT":
                                                        nands[arrayOfLogicElements[elementId]].AddInput(nots[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NAND":
                                                        nands[arrayOfLogicElements[elementId]].AddInput(nands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOR":
                                                        nands[arrayOfLogicElements[elementId]].AddInput(nors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                }
                                                break;
                                            case "NOR":
                                                switch (elements[elementID2]) {
                                                    case "AND":
                                                        nors[arrayOfLogicElements[elementId]].AddInput(ands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "INPUT":
                                                        nors[arrayOfLogicElements[elementId]].AddInput(inputs[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "OR":
                                                        nors[arrayOfLogicElements[elementId]].AddInput(ors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOT":
                                                        nors[arrayOfLogicElements[elementId]].AddInput(nots[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NAND":
                                                        nors[arrayOfLogicElements[elementId]].AddInput(nands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOR":
                                                        nors[arrayOfLogicElements[elementId]].AddInput(nors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                }
                                                break;
                                        }


                                        while (field[i][j] != 0) i++;
                                        i--;
                                        while (field[i][j] != 0) j--;
                                        j++;
                                        //   System.out.println(j);
                                        //  kraj linije
                                        switch (elements[elementID2]) {
                                            case "AND":
                                            case "NAND":
                                            case "OR":
                                            case "NOR":
                                                linesToDraw[elementId][2] = i - 15;
                                                linesToDraw[elementId][3] = j + 15;
                                                break;
                                            case "INPUT":
                                                linesToDraw[elementId][2] = i - 35;
                                                linesToDraw[elementId][3] = j + 15;
                                            case "NOT":
                                                linesToDraw[elementId][2] = i - 28;
                                                linesToDraw[elementId][3] = j + 10;
                                        }

                                        linesToDraw[elementId][4] = 1;
                                        connectedElementsArray[elementId]++;
                                        repaint();

                                        //  label.setText("ajde" + pointOutput[0].getX() +  " , " + pointOutput[0].getY());
                                        traziNovuTacku[0] = !traziNovuTacku[0];
                                    }
                                }
                            }
                        });

                    } else if (connectedElementsArray[elementId] == 1) {
                        // povezi prvi ulaz
                        int i = x[EP];
                        int j = y[EP];
                        while (field[i][j] != 0) i--;
                        i++;
                        while (field[i][j] != 0) j--;
                        j++;

                        // pocetak linije
                        switch (elements[elementId]) {
                            case "AND":
                            case "NAND":
                                linesToDraw[elementId][5] = i - 5;
                                linesToDraw[elementId][6] = j + 17;
                                break;
                            case "OR":
                            case "NOR":
                                linesToDraw[elementId][5] = i + 40;
                                linesToDraw[elementId][6] = j + 17;
                                break;
                        }

                        final boolean[] traziNovuTacku = {true};
                        addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1 && traziNovuTacku[0]) {
                                    int i = e.getX();
                                    int j = e.getY();

                                    int elementID2 = field[i][j];
                                    if (elementID2 != 0) {
                                        switch (elements[elementId]) {
                                            case "AND":
                                                switch (elements[elementID2]) {
                                                    case "AND":
                                                        ands[arrayOfLogicElements[elementId]].AddInput(ands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "INPUT":
                                                        ands[arrayOfLogicElements[elementId]].AddInput(inputs[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "OR":
                                                        ands[arrayOfLogicElements[elementId]].AddInput(ors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOT":
                                                        ands[arrayOfLogicElements[elementId]].AddInput(nots[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NAND":
                                                        ands[arrayOfLogicElements[elementId]].AddInput(nands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOR":
                                                        ands[arrayOfLogicElements[elementId]].AddInput(nors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                }
                                                break;
                                            case "OR":
                                                switch (elements[elementID2]) {
                                                    case "AND":
                                                        ors[arrayOfLogicElements[elementId]].AddInput(ands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "INPUT":
                                                        ors[arrayOfLogicElements[elementId]].AddInput(inputs[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "OR":
                                                        ors[arrayOfLogicElements[elementId]].AddInput(ors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOT":
                                                        ors[arrayOfLogicElements[elementId]].AddInput(nots[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NAND":
                                                        ors[arrayOfLogicElements[elementId]].AddInput(nands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOR":
                                                        ors[arrayOfLogicElements[elementId]].AddInput(nors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                }
                                                break;
                                            case "NAND":
                                                switch (elements[elementID2]) {
                                                    case "AND":
                                                        nands[arrayOfLogicElements[elementId]].AddInput(ands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "INPUT":
                                                        nands[arrayOfLogicElements[elementId]].AddInput(inputs[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "OR":
                                                        nands[arrayOfLogicElements[elementId]].AddInput(ors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOT":
                                                        nands[arrayOfLogicElements[elementId]].AddInput(nots[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NAND":
                                                        nands[arrayOfLogicElements[elementId]].AddInput(nands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOR":
                                                        nands[arrayOfLogicElements[elementId]].AddInput(nors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                }
                                                break;
                                            case "NOR":
                                                switch (elements[elementID2]) {
                                                    case "AND":
                                                        nors[arrayOfLogicElements[elementId]].AddInput(ands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "INPUT":
                                                        nors[arrayOfLogicElements[elementId]].AddInput(inputs[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "OR":
                                                        nors[arrayOfLogicElements[elementId]].AddInput(ors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOT":
                                                        nors[arrayOfLogicElements[elementId]].AddInput(nots[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NAND":
                                                        nors[arrayOfLogicElements[elementId]].AddInput(nands[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                    case "NOR":
                                                        nors[arrayOfLogicElements[elementId]].AddInput(nors[arrayOfLogicElements[elementID2]]);
                                                        break;
                                                }
                                                break;
                                        }

                                        while (field[i][j] != 0) i++;
                                        i--;
                                        while (field[i][j] != 0) j--;
                                        j++;
                                        //  kraj linije
                                        switch (elements[elementID2]) {
                                            case "AND":
                                            case "NAND":
                                            case "OR":
                                            case "NOR":
                                                linesToDraw[elementId][7] = i - 15;
                                                linesToDraw[elementId][8] = j + 15;
                                                break;
                                            case "INPUT":
                                                linesToDraw[elementId][7] = i - 35;
                                                linesToDraw[elementId][8] = j + 15;
                                                break;
                                            case "NOT":
                                                linesToDraw[elementId][7] = i - 28;
                                                linesToDraw[elementId][8] = j + 10;
                                                break;
                                        }
                                        if (elements[elementId].equals("OUTPUT") || elements[elementId].equals("NOT"))
                                            linesToDraw[elementId][9] = 0;
                                        else
                                            linesToDraw[elementId][9] = 1;
                                        repaint();

                                        connectedElementsArray[elementId]++;
                                        //  label.setText("ajde" + pointOutput[0].getX() +  " , " + pointOutput[0].getY());
                                        traziNovuTacku[0] = !traziNovuTacku[0];
                                    }
                                }
                            }
                        });
                    }
                } // else izbaci exception  ili neki popup sa tekstom na dato mesto se ne nalazi elemenat
            }
        });

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        DrawingGates draw = new DrawingGates();
        for (int i = 1; i < EP; i++) {
            g.setColor(LINE_COLOR);
            if (linesToDraw[i][4] == 1) {
                g.drawLine(linesToDraw[i][0], linesToDraw[i][1], linesToDraw[i][2], linesToDraw[i][3]);
            }
            if (linesToDraw[i][9] == 1) {
                g.drawLine(linesToDraw[i][5], linesToDraw[i][6], linesToDraw[i][7], linesToDraw[i][8]);
            }
            if (elements[i].equals("-")) continue;
            else {
                String value = "";
                if (inputs[arrayOfLogicElements[i]] != null && elements[i].equals("INPUT")) {
                    if (inputs[arrayOfLogicElements[i]].GetState()) value += 1;
                    else value += 0;
                }
                if (outputs[arrayOfLogicElements[i]] != null && elements[i].equals("OUTPUT")) {
                    int getState = outputs[arrayOfLogicElements[i]].getState();
                    if (getState == 0) {
                        value = "0";
                    } else if (getState == 1) {
                        value = "1";
                    } else if (getState == 2) {
                        value = "x";
                    } }
                draw.drawGate(x[i], y[i], elements[i], value, g, ELEMENT_COLOR, LINE_COLOR);
            }
        }
    }





    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Logic Gates Simulator");
            frame.setResizable(false);
            frame.add(new Tryouts(3, 700), BorderLayout.CENTER);

            frame.pack();
            // center on the screen
            frame.setLocationRelativeTo(null);

            // enable the window's close button
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setVisible(true);
        });
    }
}