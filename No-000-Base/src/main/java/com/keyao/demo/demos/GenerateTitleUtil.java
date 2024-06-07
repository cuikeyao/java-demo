package com.keyao.demo.demos;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

/**
 * 生成readme文件
 */
public class GenerateTitleUtil {
    public static void main(String[] args) throws IOException {
        String rootPath = System.getProperty("user.dir");
        File rootFile = FileUtils.getFile(rootPath);
        List<File> files = Arrays.stream(rootFile.listFiles()).filter(file -> file.getName().startsWith("No-")).sorted().toList();

        File rootMD = FileUtils.getFile(rootFile, "README.md");
        Files.write(rootMD.toPath(), new byte[0]); // 使用空byte数组清空文件

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rootMD, true))) {
            writer.write("# Java最佳实践\n\n");
            for (int i = 1; i < files.size(); i++) {
                File file = FileUtils.getFile(files.get(i));
                File mdFile = FileUtils.getFile(file, "HELP.md");
                try (BufferedReader reader = new BufferedReader(new FileReader(mdFile))) {
                    String title = reader.readLine();
                    int index = title.indexOf(" ");
                    StringBuilder sb = new StringBuilder();
                    sb.append(title.substring(0, index) + " ")
                        .append(file.getName().substring(0, 6))
                        .append(title.substring(index));
                    writer.write(sb + "\n\n");
                }
            }
        }
        System.out.println("README.md文件已生成！" + rootMD);
    }
}
