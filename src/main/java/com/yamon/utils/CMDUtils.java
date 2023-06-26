package com.yamon.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

/**
 * @Author yamon
 * @Date 2023-06-15 11:28
 * @Description
 * @Version 1.0
 */
public class CMDUtils {

    /**
     * 执行一个cmd命令
     *
     * @param cmdCommand cmd命令
     * @return 命令执行结果字符串，如出现异常返回null
     */
    public static String executeCMDCommand(String cmdCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmdCommand);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void executeCommand(String... command) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true); // 将错误输出合并到标准输出

        Process process = pb.start();

        // 读取命令输出
        java.io.InputStream inputStream = process.getInputStream();
        byte[] bytes = new byte[1024];
        int length;
        while ((length = inputStream.read(bytes)) != -1) {
            System.out.print(new String(bytes, 0, length, StandardCharsets.UTF_8));
        }

        // 等待命令执行结束，并获取退出状态码
        int exitCode;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            throw new IOException("Command execution interrupted.", e);
        }

        if (exitCode != 0) {
            throw new IOException("Command execution failed with exit code: " + exitCode);
        }
    }

    public static void execute(String workPath, List<String> commandList) throws IOException {
        String redirectWorkPath = " cd " + workPath;
        Process process = null;
        String cmdStart = getOsCmd() + redirectWorkPath + " & ";
        StringBuilder sb = new StringBuilder();
        sb.append(cmdStart);
        for (String cmd : commandList) {
            sb.append(cmd).append(" & ");
        }
        String commands = sb.toString();
        commands = commands.substring(0, commands.length() - 2);
        System.out.println("开始执行指令" + commands);
        process = Runtime.getRuntime().exec(commands);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(new String(line.getBytes(), StandardCharsets.UTF_8));
        }
        process.destroy();
        System.out.println("指令执行结束...");
    }

    public static String getOsCmd() {
        //获得系统属性集
        Properties props = System.getProperties();
        //操作系统名称
        String osName = props.getProperty("os.name");
        if (osName.toLowerCase().contains("linux")) {
            return "/bin/sh -c";
        } else if (osName.toLowerCase().contains("windows")) {
            return "cmd /c";
        } else {
            throw new RuntimeException("服务器不是linux|windows操作系统");
        }
    }

}
