package test.lib;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Arrays;

/**
 * Created by garth.combrinck on 2017/06/09.
 */
public class ExcelUtils {
    private static Sheet sheet;
    private static Workbook workbook;
    private static Cell cell;
    private static Row row;
    public static String FILE_NAME = "C:\\_automation\\restassured\\src\\test\\data\\login.xlsx";

    public static void main(String[] args) throws Exception {
        Object[][] testObjArray = getTableArray(FILE_NAME, "Sheet1");
        System.out.println(Arrays.deepToString(testObjArray));
    }

    /**
     * @param FilePath
     * @param SheetName
     * @return
     * @throws Exception
     */
    public static Object[][] getTableArray(String FilePath, String SheetName) throws Exception {
        String[][] tabArray = null;
        try {
            FileInputStream ExcelFile = new FileInputStream(FilePath);
            // Access the required test data sheet
            workbook = new XSSFWorkbook(ExcelFile);
            sheet = workbook.getSheet(SheetName);
            int startCol = 0;
            int totalRows = sheet.getLastRowNum();
            int totalCols = sheet.getRow(0).getLastCellNum();
            tabArray = new String[totalRows][totalCols];
            int ci = 0, cj = 0;
            for (int i = 1; i <= totalRows; i++, ci++) {
                for (int j = startCol; j <= totalCols - 1; j++, cj++) {
                    tabArray[ci][cj] = getCellData(i, j);
                    System.out.println(tabArray[ci][cj]);
                }
                if (cj > totalCols - 1){
                    cj = 0;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();
        }
        return (tabArray);
    }

    /**
     * This method set the Excel file and the sheet within that will be used to get and set data.
     *
     * @param Path       Path to Excel Spreadsheet
     * @param SheetIndex Index of sheet to be used
     * @throws Exception
     */
    public static void setExcelFile(String Path, int SheetIndex) throws Exception {
        try {
            FileInputStream excelFile = new FileInputStream(new File(Path));
            workbook = new XSSFWorkbook(excelFile);
            sheet = workbook.getSheetAt(SheetIndex);
        } catch (Exception e) {
            throw (e);
        }
    }

    /**
     * This method gets data from a specific cell
     *
     * @param RowNum Row Number
     * @param ColNum Cell Number
     * @return Returns value of cell
     * @throws Exception
     */
    public static String getCellData(int RowNum, int ColNum) throws Exception {
        try {
            cell = sheet.getRow(RowNum).getCell(ColNum);
            return String.valueOf(cell);

        } catch (Exception e) {
            return "";
        }
    }

    /**
     * This method sets data from a specific cell
     *
     * @param filePath Path to Excel Spreadsheet
     * @param Result   Value to be inserted into cell
     * @param RowNum   Row Number
     * @param ColNum   Cell Number
     * @throws Exception
     */
    public static void setCellData(String filePath, String Result, int RowNum, int ColNum) throws Exception {
        try {
            row = sheet.getRow(RowNum);
            cell = row.getCell(ColNum);
            if (cell == null) {
                cell = row.createCell(ColNum);
                cell.setCellValue(Result);
            } else {
                cell.setCellValue(Result);
            }
            FileOutputStream fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
            throw (e);
        }
    }
}


