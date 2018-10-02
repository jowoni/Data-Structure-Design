/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demonstration.parade.route;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
/**
 *
 * @author Michael
 */
public class Dynamic_Graph 
{
    private static Map<String, ArrayList<Edge>> all_intersection = new HashMap<String, ArrayList<Edge>>();
    private static Map<String, ParadeInfo> paradeInfoList = new HashMap<String, ParadeInfo>();
    
    public static void main(String[] args) throws Exception
    {
        View v = new View();
        Controller c = new Controller(v);
        ExcelRead input_all = new ExcelRead(all_intersection, paradeInfoList);
    }
}
