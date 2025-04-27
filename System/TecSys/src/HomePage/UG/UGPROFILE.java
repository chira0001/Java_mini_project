package HomePage.UG;
import DBCONNECTION.DBCONNECTION;
import HomePage.UndergraduateHomePage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.security.PrivateKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class UGPROFILE{
    UndergraduateHomePage UGhomePage;
    PreparedStatement prepStatement;

    Connection conn = new DBCONNECTION().Conn();

    public UGPROFILE(UndergraduateHomePage homePage) {
        this.UGhomePage = homePage;
    }

    public void dbConnection(String tgno){
        try{
            String selectQuery = "select * from undergraduate where tgno = ?";

            prepStatement = conn.prepareStatement(selectQuery);
            prepStatement.setString(1,tgno);
            ResultSet DBresult = prepStatement.executeQuery();

            if(DBresult.next()){
                String UGTgno = DBresult.getString("tgno");
                String UGFname = DBresult.getString("ugfname");
                String UGLname = DBresult.getString("uglname");
                String UGAddress = DBresult.getString("ugaddress");
                String UGEmail = DBresult.getString("ugemail");
                String UGPhno = DBresult.getString("ugphno");
                String UGProfImg = DBresult.getString("ugProfImg");

                UGhomePage.txtTGNO.setText(UGTgno);
                UGhomePage.txtFNAME.setText(UGFname);
                UGhomePage.txtLNAME.setText(UGLname);
                UGhomePage.txtADDRESS.setText(UGAddress);
                UGhomePage.txtEMAIL.setText(UGEmail);
                UGhomePage.txtPHNO.setText(UGPhno);

                UGhomePage.textField7.setText(UGAddress);
                UGhomePage.textField8.setText(UGEmail);
                UGhomePage.textField9.setText(UGPhno);

                loadUGProfImage(tgno);
            }else{
                JOptionPane.showMessageDialog(null,"Internal Error");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadUGProfImage(String tgno){
        try{
            String UGProfImageSearchQuery = "select * from undergraduate where tgno = ?";

            prepStatement = conn.prepareStatement(UGProfImageSearchQuery);
            prepStatement.setString(1,tgno);
            ResultSet result = prepStatement.executeQuery();

            while (result.next()){
                Path UGSaveImagePath = Path.of(result.getString("ugProfImg"));
                ImageIcon icon = new ImageIcon(UGSaveImagePath.toString());
                Image scaled = icon.getImage().getScaledInstance(
                        UGhomePage.HomePageUserProfile.getWidth() - 50,
                        UGhomePage.HomePageUserProfile.getHeight() - 50,
                        Image.SCALE_SMOOTH
                );
                UGhomePage.HomePageUserProfileLable.setIcon(new ImageIcon(scaled));
                UGhomePage.HomePageUserProfileLable.setText("");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void changeBtnState(String btn, String tgno){

        int noOfButtons = UGhomePage.cardButtons.length;
        for (int i = 0; i < noOfButtons; i++){
            if (UGhomePage.cardButtons[i].equals(btn)){
                dbConnection(tgno);
                UGhomePage.cardLayout.show(UGhomePage.UGHomeCard,UGhomePage.cardNames[i]);
                UGhomePage.CardTittleLabel.setText(UGhomePage.cardTitles[i]);
                UGhomePage.btnFieldNames[i].setEnabled(false);
            }else {
                UGhomePage.btnFieldNames[i].setEnabled(true);
            }
        }
    }
}
