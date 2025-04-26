package HomePage.UG;

import HomePage.UndergraduateHomePage;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class TableSetMeth {
    UndergraduateHomePage homePage;

    public TableSetMeth(UndergraduateHomePage homePage){
        this.homePage = homePage;
    }

    public void TimeTableSetModelMethod(){
        homePage.tableTimeTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Day", "Course Code", "Course Module","Time"}
        ));

        TableColumnModel timeTableColumns = homePage.tableTimeTable.getColumnModel();
        timeTableColumns.getColumn(2).setMinWidth(100);
        timeTableColumns.getColumn(3).setMinWidth(20);

        DefaultTableCellRenderer timeTableCells = new DefaultTableCellRenderer();
        timeTableCells.setHorizontalAlignment(JLabel.CENTER);

        timeTableColumns.getColumn(0).setCellRenderer(timeTableCells);
        timeTableColumns.getColumn(1).setCellRenderer(timeTableCells);
        timeTableColumns.getColumn(3).setCellRenderer(timeTableCells);
    }

    public void MarksTableSetModelMethod(){
        homePage.UGGradeTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Course Code","Course Name","CA Marks","Eligibility for Final Exam","Final Marks","Attendance Eligibility","Grade"}
        ));
    }

    public void MedicalTableSetModelMethod(){
        homePage.UGMedicalTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Medical No","Course code","Course Name","Course Status","Week No","Medical Reason"}
        ));
    }

    public void AttendanceTableSetMethod(){
        homePage.AttendanceTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Week No","Course No","Course Name","Attendance Status","Medical No","Medical Reason"}
        ));
    }

}
