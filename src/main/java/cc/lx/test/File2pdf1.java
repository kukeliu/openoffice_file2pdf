//package cc.lx.test;
//
//import com.artofsolving.jodconverter.DocumentConverter;
//import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
//import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
//import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
//
//import java.io.File;
//import java.net.ConnectException;
//
///**
// * @author: x
// * @version: 1.0
// * @date: 2019/12/27
// * @description: 提前启动openoffice服务，直接调用该服务来实现文件转换
// *  启动服务看 File2pdf1/readme.txt
// **/
//public class File2pdf1 {
//    private int port = 8100;
//
//    public void convert(String inputFilePath,String outputFilePath){
//
//        //直接调用openoffice服务，需要事先手动启动相应服务
//        OpenOfficeConnection con = new SocketOpenOfficeConnection(port);
//        DocumentConverter conver = null;
//        try {
//            con.connect();
//            conver = new StreamOpenOfficeDocumentConverter(con);//OpenOfficeDocumentConverter
//            conver.convert(new File(inputFilePath), new File(outputFilePath));// 执行转换成pdf
//        } catch (ConnectException e) {
//            e.printStackTrace();
//        } finally {
//            con.disconnect();
//        }
//
//    }
//}
