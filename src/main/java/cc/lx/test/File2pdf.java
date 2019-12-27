package cc.lx.test;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import java.io.File;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

/*
 * @author: x
 * @version: 1.0
 * @date: 2019/12/26
 * @description: 文档转pdf文档
 */

public class File2pdf extends Thread {

    private static File2pdf oop = new File2pdf();

    private List<String> Office_Formats = new ArrayList<String>();

    private List<String> converterQueue = new CopyOnWriteArrayList<String>();

    private OfficeManager officeManager;

    private static final String[] OFFICE_POSTFIXS = { "doc", "docx", "xls",
            "xlsx", "ppt", "pptx" };

    private static final String PDF_POSTFIX = "pdf";

    // 设置任务执行超时时间， 分钟为单位
    private static final long TASK_EXECUTION_TIMEOUT = 1000 * 60 * 5L;

    // 设置任务队列超时时间，分钟为单位
    private static final long TASK_QUEUE_TIMEOUT = 1000 * 60 * 60 * 24L;

    private byte[] lock = new byte[0];

    private File2pdf() {

    }

    public static File2pdf getInstance() {
        return oop;
    }

/*
     * 使Office2003-2007全部格式的文档(.doc|.docx|.xls|.xlsx|.ppt|.pptx) 转化为pdf文件
     *
     * @param inputFilePath
     *            源文件路径，如："e:/test.docx"
     * @param outputFilePath
     *            如果指定则按照指定方法，如果未指定（null）则按照源文件路径自动生成目标文件路径，如："e:/test_docx.pdf"
     * @return
*/

    private boolean converter(String inputFilePath, String outputFilePath) {
        boolean flag = false;
        long begin_time = new Date().getTime();
        File inputFile = new File(inputFilePath);
        if ((null != inputFilePath) && (inputFile.exists())) {
            Collections.addAll(Office_Formats, OFFICE_POSTFIXS);
            if (Office_Formats.contains(getPostfix(inputFilePath))) {
                try {
                    startService();
                    OfficeDocumentConverter converter = new OfficeDocumentConverter(
                            officeManager);
                    if (null == outputFilePath) {
                        outputFilePath = generateDefaultOutputFilePath(inputFilePath);
                    }
                    File outputFile = new File(outputFilePath);
                    converterFile(inputFile, outputFile, converter);
                    flag = true;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    stopService();
                }
            }
        } else {
            System.out.println("con't find the resource");
        }

        long end_time = new Date().getTime();
        System.out.println("文件转换耗时：[" + (end_time - begin_time) + "]ms");
        return flag;
    }

/*
     * 转换文件服务调用
    */

    private void converterFile(File inputFile, File outputFile,
                               OfficeDocumentConverter converter) {

        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }
        converter.convert(inputFile, outputFile);
    }

/*
     * 添加需要转换的文件
    */

    public void add(String inputFile) {
        synchronized (converterQueue) {
            converterQueue.add(inputFile);
        }
    }

/*
     * 获取文件队列中第一个文件信息
     * @param converterQueue
     * @return
*/

    private String getTaskQueue(List<String> converterQueue) {
        synchronized (lock) {

            if (converterQueue.size() > 0) {
                return converterQueue.remove(0);
            } else {
                return null;
            }
        }
    }

    public void run() {
        while (true) {
            String taskInfo = getTaskQueue(converterQueue);
            if (null != taskInfo && taskInfo.trim().length() > 0) {
                converter(taskInfo, null);
            } else {
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

/*
     * 开启openoffice
    */

    private void startService() {
        DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();
        String officeHome = getOfficeHome();
        config.setOfficeHome(officeHome);
        config.setTaskExecutionTimeout(TASK_EXECUTION_TIMEOUT);
        config.setTaskQueueTimeout(TASK_QUEUE_TIMEOUT);
        officeManager = config.buildOfficeManager();
        officeManager.start();
    }

/*
     * 关闭openoffice
    */

    private void stopService() {
        if (officeManager != null) {
            officeManager.stop();
        }
    }

/*
     * 获取openoffice服务位置
*/

    private String getOfficeHome() {
        String osName = System.getProperty("os.name");
        if (Pattern.matches("Linux.*", osName)) {
            return "/opt/openoffice.org3";
        } else if (Pattern.matches("Windows.*", osName)) {
            return "C:\\Program Files (x86)\\OpenOffice 4";
        }
        return null;
    }

/*
     * 如果未设置输出文件路径则按照源文件路径和文件名生成输出文件地址。例，输入为 D:/fee.xlsx 则输出为D:/fee_xlsx.pdf
*/

    private String generateDefaultOutputFilePath(String inputFilePath) {
        String outputFilePath = (inputFilePath).replaceAll("."
                + getPostfix(inputFilePath), "_" + System.currentTimeMillis()
                + "." + PDF_POSTFIX);
        return outputFilePath;
    }

/*
     * 获取inputFilePath的后缀名，如："e:/test.pptx"的后缀名为："pptx"
*/

    private String getPostfix(String inputFilePath) {
        String[] p = inputFilePath.split("\\.");
        if (p.length > 0) {// 判断文件有无扩展名
            // 比较文件扩展名
            return p[p.length - 1];
        } else {
            return null;
        }
    }


}

