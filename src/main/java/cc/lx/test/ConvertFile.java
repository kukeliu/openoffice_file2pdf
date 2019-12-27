package cc.lx.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: x
 * @version: 1.0
 * @date: 2019/12/27
 * @description:
 **/
public class ConvertFile {

    public static void main(String[] args) {
        List<String> inputFilePathList = new ArrayList<String>();
        inputFilePathList.add("E://doc/测试文档.docx");

        File2pdf oop = File2pdf.getInstance();
        for (String inputFilePath : inputFilePathList) {
            oop.add(inputFilePath);
        }
        oop.start();

        //直接调用服务方式
        /*File2pdf1 file2pdf1 = new File2pdf1();
        file2pdf1.convert("E://doc/测试文档.docx","E://doc/测试文档pdf.pdf");*/

    }
}
