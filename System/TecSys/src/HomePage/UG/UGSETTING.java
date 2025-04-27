package HomePage.UG;

import DBCONNECTION.DBCONNECTION;
import HomePage.UndergraduateHomePage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;

public class UGSETTING {
    UndergraduateHomePage homePage;
    DBCONNECTION db = new DBCONNECTION();
    Connection conn = db.Conn();
    public UGSETTING(UndergraduateHomePage homePage){
        this.homePage = homePage;
    }

    public void UGUpdateCredentials(String tgno){
        try{
            String UGaddress = homePage.textField7.getText();
            String UGemail = homePage.textField8.getText();
            String UGphno = homePage.textField9.getText();

            if(UGaddress.isEmpty() || UGemail.isEmpty() || UGphno.isEmpty()){
                JOptionPane.showMessageDialog(null,"Please fill all the fields");
                return;
            }
            if(!UGphno.matches("[0-9]+")){
                JOptionPane.showMessageDialog(null,"Please enter numbers only for Phone Number");
                return;
            }

            String extension = (String) homePage.filePathValues[3];

            String UGProfileImagePath = "Resources/ProfileImages/" + tgno + "." + extension;
            String UGCredentialupdateQuery;
            if (extension == null){
                UGCredentialupdateQuery = "Update undergraduate set ugaddress = '" + UGaddress + "', ugemail = '"+ UGemail +"',ugphno = '"+ UGphno+"' where tgno = '" + tgno + "'";
            }else {
                UGCredentialupdateQuery = "Update undergraduate set ugaddress = '" + UGaddress + "', ugemail = '"+ UGemail +"',ugphno = '"+ UGphno+"',ugProfImg ='" + UGProfileImagePath + "' where tgno = '" + tgno + "'";
            }

            Statement statement = conn.createStatement();
            int resultSet = statement.executeUpdate(UGCredentialupdateQuery);

            if(resultSet > 0){
                UGSaveProfileImage(tgno);
                JOptionPane.showMessageDialog(null,"Credentials updated successfully");
            }else {
                JOptionPane.showMessageDialog(null,"Error in credential updation");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void UGSaveProfileImage(String tgno){
        try{
            Path fromFile = (Path) homePage.filePathValues[0];
            Path toFile = (Path) homePage.filePathValues[1];
            File UGDestinationFile = (File) homePage.filePathValues[2];

            if (UGDestinationFile.exists()){
                UGDestinationFile.delete();
                Files.copy(fromFile,toFile);
            }else{
                Files.copy(fromFile,toFile);
            }
        }catch(Exception exc){
        }
    }

    public void UGUploadToPreviewProfileImage(String tgno) {
        try {
            JFileChooser UGFileChooser = new JFileChooser();
            UGFileChooser.setDialogTitle("Select Profile Picture");
            UGFileChooser.setAcceptAllFileFilterUsed(false);
            UGFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg"));

            if (UGFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                ImageIcon icon = new ImageIcon(UGFileChooser.getSelectedFile().getPath());
                Image scaled = icon.getImage().getScaledInstance(
                        homePage.UGProfImgPanel.getWidth() - 50,
                        homePage.UGProfImgPanel.getHeight() - 50,
                        Image.SCALE_SMOOTH
                );
                homePage.UGProfileImage.setIcon(new ImageIcon(scaled));
                homePage.UGProfileImage.setText("");

                String filename = UGFileChooser.getSelectedFile().getAbsolutePath();

                String UGSaveImagePath = "Resources/ProfileImages/";
                File UGSaveImageDirectory = new File(UGSaveImagePath);
                if (!UGSaveImageDirectory.exists()) {
                    UGSaveImageDirectory.mkdirs();
                }

                File UGSourceFile = null;

                String extension = filename.substring(filename.lastIndexOf('.') + 1);

                UGSourceFile = new File(tgno + "." + extension);

                File UGDestinationFile = new File(UGSaveImagePath + UGSourceFile);

                Path fromFile = UGFileChooser.getSelectedFile().toPath();
                Path toFile = UGDestinationFile.toPath();

                homePage.filePathValues[0] = fromFile;
                homePage.filePathValues[1] = toFile;
                homePage.filePathValues[2] = UGDestinationFile;
                homePage.filePathValues[3] = extension;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
