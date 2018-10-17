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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Michael
 */
public final class ExcelRead {

    private final Map<String, ParadeInfo> paradeInfoMap;        //시위대명별 행진정보
    private final Map<String, Map<String, Integer>> wholeGraph; //전체 시작노드별 종착노드와의 길이
    FileInputStream fis;         // open하는 파일의 인스턴스
    XSSFWorkbook workbook;       // open하는 파일의 workbook 인스턴스
    LocalDate simulationDate;    // 행진 날짜

    ExcelRead() throws FileNotFoundException, IOException {
        fis = new FileInputStream("Input All Data Template.xlsx");
        workbook = new XSSFWorkbook(fis);
        wholeGraph = new HashMap<>();
        paradeInfoMap = new HashMap<>();
        
        inputWholeGraph();  // 전체 교차로 정보 입력
        inputParadeInfo();  // 전제 행진 정보 입력
        processData();      // 전체 행진 정보 가공
        
        fis.close();
    }   // 생정자

    // getter setter methods
    LocalDate getDate() {
        return simulationDate;
    }   
    Map<String, ParadeInfo> getParadeInfoMap() {
        return paradeInfoMap;
    }
    Map<String, Map<String, Integer>> getWholeGraph() {
        return wholeGraph;
    }

    void inputWholeGraph() {
        String srcNode, destNode;   // 정보 저장을 위한 임시 변수들
        String prevKeyName = null;
        int weight;
        
        for (int i = 4; i <= 248; i++) { 
            srcNode = workbook.getSheetAt(0).getRow(i).getCell(1).getStringCellValue();
            destNode = workbook.getSheetAt(0).getRow(i).getCell(2).getStringCellValue();
            weight = (int) workbook.getSheetAt(0).getRow(i).getCell(3).getNumericCellValue();

            if (prevKeyName != null && prevKeyName.equals(srcNode)) { // 중복되는 이름일때
                wholeGraph.get(srcNode).put(destNode, weight);
            } else {                                                  // 중복안되는 이름일때
                wholeGraph.put(srcNode, new HashMap<>());
                wholeGraph.get(srcNode).put(destNode, weight);
            }
            prevKeyName = srcNode;
        }
    }   // 전체 교차로 정보 입력
    
    void inputParadeInfo() throws IOException{
        int paradeLength;   // 정보 저장을 위한 임시 변수들
        LocalTime paradeStartTime, paradeEndTime;
        String paradeRouteText, keyName;

        // 행진 날짜 저장
        simulationDate = LocalDate.of(Integer.parseInt(workbook.getSheetAt(1).getRow(3).getCell(1).getStringCellValue().substring(0, 4)),
                Integer.parseInt(workbook.getSheetAt(1).getRow(3).getCell(1).getStringCellValue().substring(5, 7)),
                Integer.parseInt(workbook.getSheetAt(1).getRow(3).getCell(1).getStringCellValue().substring(8, 10)));
        
        // 5행부터 10행까지 순환. 행의 수만큼 순환하는 완벽한 입력 처리는 못함.
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

            // 종착 교차로 노드 destNode에 저장.
            paradeInfoMap.get(keyName).setDestNode(paradeInfoMap.get(keyName).getParadeRoute().get(paradeInfoMap.get(keyName).getParadeRoute().size() - 1));
        }
    }   // 전체 행진 정보 입력(인원수, 시작 시간, 끝나는 시간, 경로, 행진 날짜)
    
    void processData(){
        //행진 경로 길이 계산
        int accDistance;
        ArrayList temp;
        for (String key : paradeInfoMap.keySet()) { //시위대명별
            accDistance = 0;
            temp = paradeInfoMap.get(key).getParadeRoute(); //행진정보별 행진경로 ArrayList
            for (int i = 0; i < temp.size() - 1; i++) {  //행진정보별 행진경로 ArrayList 순환
                accDistance += wholeGraph.get(temp.get(i)).get(temp.get(i + 1));
            }
            paradeInfoMap.get(key).setRouteLength(accDistance);//행진 경로 길이 저장
            paradeInfoMap.get(key).setParadeSpeed( //행진 속도 계산 및 저장
                    (float) (paradeInfoMap.get(key).getRouteLength() + paradeInfoMap.get(key).getParadeLength())
                    / paradeInfoMap.get(key).getTotalTime().toMinutes()); // (Total route length+ Parade length)meter / Parade time(minute)
        }
    }   // 입력받은 전체 행진 정보를 이용하여 행진 속도, 경로 길이 등을 계산
}
