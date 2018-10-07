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
import java.util.HashMap;
import java.util.Iterator;
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

    private final Map<String, ParadeInfo> returnParadeList; //시위대명별 행진정보
    private final Map<String, Map<String, Integer>> returnMap; //전체 시작노드별 종착노드와의 길이
    private final LocalDate returnDate;
    private final LocalTime returnTime;

    ExcelRead(Map<String, Map<String,Integer>> wholeMap, Map<String, ParadeInfo> routeMap, LocalDate simulationDate, LocalTime initialTime) throws FileNotFoundException, IOException {

        FileInputStream fis = new FileInputStream("Input All Data Template.xlsx");
        // HSSFWorkbook은 엑셀파일 전체 내용을 담고 있는 객체
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        // 탐색에 사용할 Sheet, Row, Cell 객체
        XSSFSheet curSheet;
        XSSFRow curRow;
        XSSFCell curCell;

        String srcNode, prevKeyName = null;
        String destNode;
        int weight;
        //int sameName = 1, j = 1;

        for (int i = 4; i <= 250; i++) {
            srcNode = workbook.getSheetAt(0).getRow(i).getCell(1).getStringCellValue();
            destNode = workbook.getSheetAt(0).getRow(i).getCell(2).getStringCellValue();
            weight = (int) workbook.getSheetAt(0).getRow(i).getCell(3).getNumericCellValue();

            if (prevKeyName != null && prevKeyName.equals(srcNode)) { //중복되는 이름일때
                //wholeMap.get(keyName).add(new Edge(keyName, destNode, weight));
                wholeMap.get(srcNode).put(destNode, weight);
            } else { //중복안되는 이름일때
                //wholeMap.put(keyName, new ArrayList<>());
                //wholeMap.get(keyName).add(new Edge(keyName, destNode, weight));
                wholeMap.put(srcNode, new HashMap<String, Integer>());
                wholeMap.get(srcNode).put(destNode,weight);
            }
            prevKeyName = srcNode;
        }
        fis.close();

        FileInputStream fis2 = new FileInputStream("Input Route Data Template.xlsx");

        // 탐색에 사용할 Sheet, Row, Cell 객체
        XSSFWorkbook workbook1 = new XSSFWorkbook(fis2);

        int paradeLength;
        LocalTime paradeStartTime, paradeEndTime;
        String paradeRouteText, keyName;

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
            paradeRouteText = workbook1.getSheetAt(0).getRow(j).getCell(4).getStringCellValue();
            routeMap.put(keyName, new ParadeInfo(paradeLength, paradeStartTime, paradeEndTime));
            routeMap.get(keyName).getParadeRoute().addAll(Arrays.asList(paradeRouteText.split("→")));
        }

        // Initial Time 세팅. 최초 시위 시작 - 10 분.
        LocalTime tempTime = null;
        for (String key : routeMap.keySet()) {
            if (tempTime == null) {
                tempTime = routeMap.get(key).getStartTime();
            } else if (tempTime.isAfter(routeMap.get(key).getStartTime())) {
                tempTime = routeMap.get(key).getStartTime();
            }
        }
        initialTime = tempTime.minusMinutes(10);

        //행진 경로 길이 계산
        int accDistance;
        ArrayList temp;
        for (String key : routeMap.keySet()) { //시위대명별
            accDistance = 0;
            temp = routeMap.get(key).getParadeRoute(); //행진정보별 행진경로 ArrayList
            for(int i = 0; i < temp.size() - 1; i++){  //행진정보별 행진경로 ArrayList 순환
                accDistance += wholeMap.get(temp.get(i)).get(temp.get(i+1));
            }
            routeMap.get(key).setRouteLength(accDistance);//행진 경로 길이 저장
            routeMap.get(key).setParadeSpeed(
                    (routeMap.get(key).getRouteLength()+routeMap.get(key).getParadeLength())/
                            routeMap.get(key).getTotalTime().toMinutes());
            //(Total route length+ Parade length)meter / Parade time(minute)
        }
        returnParadeList = routeMap;
        returnMap = wholeMap;
        returnDate = simulationDate;
        returnTime = initialTime;
    }

    Map<String, ParadeInfo> getParadeList() {
        return returnParadeList;
    }

    Map<String, Map<String,Integer>> getMap() {
        return returnMap;
    }

    LocalDate getDate() {
        return returnDate;
    }

    LocalTime getTime() {
        return returnTime;
    }
}
