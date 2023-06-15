package com.yamon.utils;

import java.io.*;
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
        process = Runtime.getRuntime().exec(commands);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(new String(line.getBytes(), StandardCharsets.UTF_8));
        }
        process.destroy();
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
