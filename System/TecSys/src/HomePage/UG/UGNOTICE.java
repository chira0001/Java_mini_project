package HomePage.UG;

import HomePage.UndergraduateHomePage;

import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class UGNOTICE {
    UndergraduateHomePage UGhomePage;

    private Scanner input;

    public UGNOTICE(UndergraduateHomePage homePage) {
        this.UGhomePage = homePage;
    }
    public void LoadNotices(){
        String notice_Title;
        try{
            String noticeLoadQuery = "select * from notice";

            Statement statement = UGhomePage.conn.createStatement();
            ResultSet result = statement.executeQuery(noticeLoadQuery);

            while(result.next()){
                notice_Title = result.getString("noticeTitle");
                UGhomePage.noticeTitleDropDown.addItem(notice_Title);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void viewNotice(String selected_notice_title){
        String view_notice_Query_details = "Select * from notice where noticeTitle = ?";
        try{
            UGhomePage.prepStatement = UGhomePage.conn.prepareStatement(view_notice_Query_details);
            UGhomePage.prepStatement.setString(1,selected_notice_title);

            ResultSet resultSet = UGhomePage.prepStatement.executeQuery();
            while (resultSet.next()){
                String notice_FilePath = resultSet.getString("noticeFilePath");

                System.out.println(notice_FilePath);

                File notice = new File(notice_FilePath);
                input = new Scanner(notice);

                StringBuilder noticeContent = new StringBuilder();

                while (input.hasNextLine()){
                    noticeContent.append(input.nextLine()).append("\n");
                }
                UGhomePage.noticeDisplayArea.setText(noticeContent.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
