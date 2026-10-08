import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreditButton extends JButton {
    private JDesktopPane desktopPane;
    
    public CreditButton(JDesktopPane desktopPane) {
        super("Credit");
        this.desktopPane = desktopPane;

        setFont(new Font("SansSerif", Font.BOLD, 14));
        setPreferredSize(new Dimension(100, 30));

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCreditFrame();
            }
        });
    }
    
    private void showCreditFrame() {
        JInternalFrame creditFrame = new JInternalFrame("Credit Information", true, true, true, true);
        creditFrame.setSize(400, 300);
        creditFrame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        
        centerFrame(creditFrame);
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        JTextArea creditText = new JTextArea();
        creditText.setEditable(false);
        creditText.setFont(new Font("SansSerif", Font.PLAIN, 16));
        creditText.setText("Team Members:\n\n" +
                         "1. Chanyut Benjapalakorn\n" +
                         "2. Tongtula Suwan\n" +
                         "3. Tanakit Sridaorueng\n" +
                         "4. Sun Chokanan\n" +
                         "5. Chongkha Saengsarut\n" +
                         "6. Nitiroj Suppatanawat\n" +
                         "7. Wasawat Natphan\n" +
                         "8. Phudit Prakobkit\n" +
                         "9. Siraphop Rungsirijaratthong\n" +
                         "10. Padol Sakulrattanakom\n\n");


        contentPanel.add(new JScrollPane(creditText), BorderLayout.CENTER);
        creditFrame.add(contentPanel);

        creditFrame.setVisible(true);
        desktopPane.add(creditFrame);
        
        try {
            creditFrame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }
    
    private void centerFrame(JInternalFrame frame) {
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = frame.getSize();
        frame.setLocation((desktopSize.width - frameSize.width)/2, 
                        (desktopSize.height - frameSize.height)/2);
    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Credit Button Example");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JDesktopPane desktopPane = new JDesktopPane();
        mainFrame.add(desktopPane, BorderLayout.CENTER);
        
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        CreditButton creditButton = new CreditButton(desktopPane);
        toolbar.add(creditButton);
        
        mainFrame.add(toolbar, BorderLayout.NORTH);
        mainFrame.setVisible(true);
    }
}