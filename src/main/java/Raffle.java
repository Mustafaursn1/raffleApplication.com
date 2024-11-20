import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Raffle extends JDialog {
    private JPanel contentPane;
    private JTextField searchBox;
    private JButton search;
    private JList winnerList;
    private JLabel winnerLabel;
    private JButton startTheRaffleButton;
    private JButton buttonOK;
    private JButton buttonCancel;
    private String pathOfFile;
    private ArrayList<String> participants = new ArrayList<String>();
    private Set<String> winners = new TreeSet<String>();
    private DefaultListModel model = new DefaultListModel();

    public Raffle() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        winnerList.setModel(model);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();

            }

            ;
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        },
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        search.addActionListener(new ActionListener() {
            private String pathOfFile;

            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                int i = fileChooser.showOpenDialog(Raffle.this);
                if (i == JFileChooser.APPROVE_OPTION) {
                    this.pathOfFile = fileChooser.getSelectedFile().getPath();
                    searchBox.setText(pathOfFile);
                }


            }
        });
        startTheRaffleButton.addActionListener(new ActionListener() {


            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                if (pathOfFile.equals("")) {
                    JOptionPane.showMessageDialog(Raffle.this, "Please choose a list of raffle");
                } else {
                    makeRaffle();//Cekilis yapar
                    for(String w:winners ){
                        model.addElement(w);

                    }

                }

            }
        });
    }

    private void makeRaffle() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(pathOfFile)))) {
            String person;
            while ((person= reader.readLine())!=null){
                participants.add(person);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (winners.size()!=10){
            Random random=new Random();
            int indexOfWinner=random.nextInt(winners.size());
            winners.add(participants.get(indexOfWinner));
        }

    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Raffle dialog = new Raffle();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
