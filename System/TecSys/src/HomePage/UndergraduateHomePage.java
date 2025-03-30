package HomePage;

import javax.swing.*;

public class UndergraduateHomePage extends JFrame {
    private JPanel UndergraduateHomePage;
    private JButton myProfileButton;
    private JButton attendanceButton;
    private JButton medicalButton;
    private JButton coursesButton;
    private JButton gradesButton;
    private JButton viewTimeTableButton;
    private JButton viewNoticesButton;

    public UndergraduateHomePage(){
        setContentPane(UndergraduateHomePage);
        setTitle("Undergraduate User Profile");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(824, 524);
        setLocationRelativeTo(this);
        setVisible(true);
    }
}
