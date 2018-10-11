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
import java.util.Map;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Michael
 */
public class ExcelRead {

    private final Map<String, ParadeInfo> paradeInfoMap; //시위대명별 행진정보
    private final Map<String, Map<String, Integer>> wholeGraph; //전체 시작노드별 종착노드와의 길이
    private final LocalDate simulationDate;

    //생성자안에 파일 입출력. 프로그램 완성되면 세분화 예정.
    ExcelRead() throws FileNotFoundException, IOException {

        FileInputStream fis = new FileInputStream("Input All Data Template.xlsx");
        // HSSFWorkbook은 엑셀파일 전체 내용을 담고 있는 객체
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        String srcNode, prevKeyName = null;
        String destNode;
        int weight;
        wholeGraph = new HashMap<>();
        paradeInfoMap = new HashMap<>();

        for (int i = 4; i <= 248; i++) { //전체 교차로 정보 입력
            srcNode = workbook.getSheetAt(0).getRow(i).getCell(1).getStringCellValue();
            destNode = workbook.getSheetAt(0).getRow(i).getCell(2).getStringCellValue();
            weight = (int) workbook.getSheetAt(0).getRow(i).getCell(3).getNumericCellValue();

            if (prevKeyName != null && prevKeyName.equals(srcNode)) { //중복되는 이름일때
                wholeGraph.get(srcNode).put(destNode, weight);
            } else { //중복안되는 이름일때
                wholeGraph.put(srcNode, new HashMap<String, Integer>());
                wholeGraph.get(srcNode).put(destNode,weight);
            }
            prevKeyName = srcNode;
        }

        //파일 입력받기. 행진길이(인원수), 시작시간, 끝나는시간, 경로 받아오기.
        int paradeLength;
        LocalTime paradeStartTime, paradeEndTime;
        String paradeRouteText, keyName;

        simulationDate = LocalDate.of(Integer.parseInt(workbook.getSheetAt(1).getRow(3).getCell(1).getStringCellValue().substring(0, 4)),
                Integer.parseInt(workbook.getSheetAt(1).getRow(3).getCell(1).getStringCellValue().substring(5, 7)),
                Integer.parseInt(workbook.getSheetAt(1).getRow(3).getCell(1).getStringCellValue().substring(8, 10)));
        for (int j = 5; j <= 10; j++) {
            keyName = workbook.getSheetAt(1).getRow(j).getCell(1).getStringCellValue();
            paradeLength = (int) workbook.getSheetAt(1).getRow(j).getCell(2).getNumericCellValue();
            paradeStartTime = LocalTime.of(Integer.parseInt(workbook.getSheetAt(1).getRow(j).getCell(3).getStringCellValue().substring(0, 2)),
                    Integer.parseInt(workbook.getSheetAt(1).getRow(j).getCell(3).getStringCellValue().substring(3, 5)));
            paradeEndTime = LocalTime.of(Integer.parseInt(workbook.getSheetAt(1).getRow(j).getCell(3).getStringCellValue().substring(6, 8)),
                    Integer.parseInt(workbook.getSheetAt(1).getRow(j).getCell(3).getStringCellValue().substring(9, 11)));
            paradeRouteText = workbook.getSheetAt(1).getRow(j).getCell(4).getStringCellValue();
            paradeInfoMap.put(keyName, new ParadeInfo(paradeLength, paradeStartTime, paradeEndTime));
            paradeInfoMap.get(keyName).getParadeRoute().addAll(Arrays.asList(paradeRouteText.split("→")));
        }
        
        fis.close();

        //행진 경로 길이 계산
        int accDistance;
        ArrayList temp;
        for (String key : paradeInfoMap.keySet()) { //시위대명별
            accDistance = 0;
            temp = paradeInfoMap.get(key).getParadeRoute(); //행진정보별 행진경로 ArrayList
            for(int i = 0; i < temp.size() - 1; i++){  //행진정보별 행진경로 ArrayList 순환
                accDistance += wholeGraph.get(temp.get(i)).get(temp.get(i+1));
            }
            paradeInfoMap.get(key).setRouteLength(accDistance);//행진 경로 길이 저장
            paradeInfoMap.get(key).setParadeSpeed(             //행진 속도 계산 및 저장
                    (float)(paradeInfoMap.get(key).getRouteLength()+paradeInfoMap.get(key).getParadeLength())/
                            paradeInfoMap.get(key).getTotalTime().toMinutes());
            //(Total route length+ Parade length)meter / Parade time(minute)
        }
    }

    LocalDate getDate() {
        return simulationDate;
    }
    
    Map<String, ParadeInfo> getParadeInfoMap(){
        return paradeInfoMap;
    }
    
    Map<String, Map<String, Integer>> getWholeGraph(){
        return wholeGraph;
    }
}
