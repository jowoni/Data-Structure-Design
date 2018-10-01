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

    ExcelRead(Map<String, ArrayList<Edge>> inputMap) throws FileNotFoundException, IOException {
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
        //System.out.println(inputMap);
        /*
        for (String keys : inputMap.keySet()) {
            System.out.println(keys);
        }
        */
        /*
        for (int i = 4; i <= 245; i++) {
            keyName = workbook.getSheetAt(0).getRow(i).getCell(1).getStringCellValue();
            destNode = workbook.getSheetAt(0).getRow(i).getCell(2).getStringCellValue();
            weight = (int) workbook.getSheetAt(0).getRow(i).getCell(3).getNumericCellValue();
            //weight = Float.parseFloat(workbook.getSheetAt(0).getRow(i).getCell(3).getStringCellValue());

            inputMap.put(keyName, new ArrayList<Edge>());
            inputMap.get(keyName).add(new Edge(keyName, destNode, weight));

            if (workbook.getSheetAt(0).getRow(i + 1).getCell(1).getStringCellValue() != null) {
                while (workbook.getSheetAt(0).getRow(i).getCell(1).getStringCellValue().equals(workbook.getSheetAt(0).getRow(i + j).getCell(1).getStringCellValue())) {
                    destNode = workbook.getSheetAt(0).getRow(i + j).getCell(2).getStringCellValue();
                    weight = (int) workbook.getSheetAt(0).getRow(i + j).getCell(3).getNumericCellValue();
                    //weight = Float.parseFloat(workbook.getSheetAt(0).getRow(i+j).getCell(3).getStringCellValue());

                    inputMap.get(keyName).add(new Edge(keyName, destNode, weight));
                    j++;
                }
            }
            i += j;
            j = 1;
        }

        System.out.println();*/
    }
}
