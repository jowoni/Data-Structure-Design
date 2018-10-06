/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parade.route.dynamic.graph;

import java.time.*;
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
    
    private final Map<String, ParadeInfo> returnParadeList;
    private final Map<String, ArrayList<Edge>> returnMap;
    private final LocalDate returnDate;
    private final LocalTime returnTime;

    ExcelRead(Map<String, ArrayList<Edge>> wholeMap, Map<String, ParadeInfo> routeMap, LocalDate simulationDate, LocalTime initialTime) throws FileNotFoundException, IOException {

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
                wholeMap.get(keyName).add(new Edge(keyName, destNode, weight));
            } else {
                wholeMap.put(keyName, new ArrayList<>());
                wholeMap.get(keyName).add(new Edge(keyName, destNode, weight));
            }
            prevKeyName = keyName;
        }
        fis.close();

        FileInputStream fis2 = new FileInputStream("Input Route Data Template.xlsx");

        // 탐색에 사용할 Sheet, Row, Cell 객체
        XSSFWorkbook workbook1 = new XSSFWorkbook(fis2);

        int paradeLength;
        LocalTime paradeStartTime, paradeEndTime;
        String paradeRoute;

        simulationDate = LocalDate.of(Integer.parseInt(workbook1.getSheetAt(0).getRow(3).getCell(1).getStringCellValue().substring(0, 4)),
                Integer.parseInt(workbook1.getSheetAt(0).getRow(3).getCell(1).getStringCellValue().substring(5, 7)),
                Integer.parseInt(workbook1.getSheetAt(0).getRow(3).getCell(1).getStringCellValue().substring(8, 10)));
        for (int j = 5; j <= 10; j++) {
            keyName = workbook1.getSheetAt(0).getRow(j).getCell(1).getStringCellValue();
            paradeLength = (int) workbook1.getSheetAt(0).getRow(j).getCell(2).getNumericCellValue();
            paradeStartTime = LocalTime.of(Integer.parseInt(workbook1.getSheetAt(0).getRow(j).getCell(3).getStringCellValue().substring(0, 2)),
                    Integer.parseInt(workbook1.getSheetAt(0).getRow(j).getCell(3).getStringCellValue().substring(3, 5)));
            paradeEndTime = LocalTime.of(Integer.parseInt(workbook1.getSheetAt(0).getRow(j).getCell(3).getStringCellValue().substring(6, 8)),
                    Integer.parseInt(workbook1.getSheetAt(0).getRow(j).getCell(3).getStringCellValue().substring(9, 11)));
            paradeRoute = workbook1.getSheetAt(0).getRow(j).getCell(4).getStringCellValue();
            routeMap.put(keyName, new ParadeInfo(paradeLength, paradeStartTime, paradeEndTime));
            routeMap.get(keyName).getParadeRoute().addAll(Arrays.asList(paradeRoute.split("→")));
        }
        
        LocalTime tempTime = null;
        for( String key : routeMap.keySet() ){
            if(tempTime == null){
                tempTime = routeMap.get(key).startTime;
            }
            else if(tempTime.isAfter(routeMap.get(key).startTime)){
                tempTime = routeMap.get(key).startTime;
            }
        }
        initialTime = tempTime;
        
        returnParadeList = routeMap;
        returnMap = wholeMap;
        returnDate = simulationDate;
        returnTime = initialTime;
    }
    
    Map<String, ParadeInfo> getParadeList(){
        return returnParadeList;
    }
    
    Map<String, ArrayList<Edge>> getMap(){
        return returnMap;
    }
    
    LocalDate getDate(){
        return returnDate;
    }
    
    LocalTime getTime(){
        return returnTime;
    }
}
