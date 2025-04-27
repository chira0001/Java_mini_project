package HomePage.AD;

import DBCONNECTION.DBCONNECTION;
import HomePage.AdminHomePage;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ADNOTICES {
    AdminHomePage homePage;

    PreparedStatement prepStatement;
    DBCONNECTION db = new DBCONNECTION();
    Connection conn = db.Conn();
    BufferedWriter writer;

    public ADNOTICES(AdminHomePage homePage){
        this.homePage = homePage;
    }

    public void addNotices(){
        try{
            String noticeTitle = homePage.textField14.getText();
            String text = homePage.noticeGetArea.getText();

            String filePath = "Resources/Notices/"+noticeTitle+".txt";

            String addNoticeQuery = "Insert into notice(noticeTitle,noticeFilePath) values(?,?)";

            prepStatement = conn.prepareStatement(addNoticeQuery);
            prepStatement.setString(1,noticeTitle);
            prepStatement.setString(2,filePath);

            int resultAddNotice = prepStatement.executeUpdate();
            if(resultAddNotice > 0){
                writer = new BufferedWriter(new FileWriter(filePath));
                writer.write(text);
                writer.close();
                JOptionPane.showMessageDialog(null,"Notice Successfully added");
            }else{
                JOptionPane.showMessageDialog(null,"Notice addeing failed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
