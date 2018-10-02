/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demonstration.parade.route;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.hssf.usermodel.XSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Michael
 */
public class ExcelRead {

    ExcelRead(Map<String, ArrayList<Edge>> inputMap, Map<String, ParadeInfo> inputParadeList) throws FileNotFoundException, IOException {

        FileInputStream fis = new FileInputStream("Input All Data Template.xlsx");
        // HSSFWorkbook은 엑셀파일 전체 내용을 담고 있는 객체
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        // 탐색에 사용할 Sheet, Row, Cell 객체
        XSSFSheet curSheet;
        XSSFRow curRow;
        XSSFCell curCell;

        String keyName, prevKeyName = null;
        String destNode;
        int weight;
        //int sameName = 1, j = 1;

        for (int i = 4; i <= 245; i++) {
            keyName = workbook.getSheetAt(0).getRow(i).getCell(1).getStringCellValue();
            destNode = workbook.getSheetAt(0).getRow(i).getCell(2).getStringCellValue();
            weight = (int) workbook.getSheetAt(0).getRow(i).getCell(3).getNumericCellValue();

            if (prevKeyName != null && prevKeyName.equals(keyName)) {
                inputMap.get(keyName).add(new Edge(keyName, destNode, weight));
            } else {
                inputMap.put(keyName, new ArrayList<Edge>());
                inputMap.get(keyName).add(new Edge(keyName, destNode, weight));
            }
            prevKeyName = keyName;
        }
        fis.close();

        FileInputStream fis2 = new FileInputStream("Input Route Data Template.xlsx");

        // 탐색에 사용할 Sheet, Row, Cell 객체
        XSSFWorkbook workbook1 = new XSSFWorkbook(fis2);

        int numPeople, paradeTime;
        String paradeRoute;

        for (int j = 5; j <= 10; j++) {
            keyName = workbook1.getSheetAt(0).getRow(j).getCell(1).getStringCellValue();
            numPeople = (int) workbook1.getSheetAt(0).getRow(j).getCell(2).getNumericCellValue();
            paradeTime = (Integer.parseInt(workbook1.getSheetAt(0).getRow(j).getCell(3).getStringCellValue().substring(0, 2))
                    - Integer.parseInt(workbook1.getSheetAt(0).getRow(j).getCell(3).getStringCellValue().substring(6, 8))) * 60
                    + Integer.parseInt(workbook1.getSheetAt(0).getRow(j).getCell(3).getStringCellValue().substring(3, 5))
                    - Integer.parseInt(workbook1.getSheetAt(0).getRow(j).getCell(3).getStringCellValue().substring(9, 11));
            paradeRoute = workbook1.getSheetAt(0).getRow(j).getCell(4).getStringCellValue();
            inputParadeList.put(keyName, new ParadeInfo(numPeople, paradeTime));
            inputParadeList.get(keyName).getParadeRoute().addAll(Arrays.asList(paradeRoute.split("→")));
        }
    }
}
