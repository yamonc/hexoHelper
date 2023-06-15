package com.yamon.convert;

import com.yamon.convert.dto.FrontMatterEntity;
import com.yamon.utils.AlertUtils;
import com.yamon.utils.CMDUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDateTime;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HelloController {
    public Button fxUpload;
    public Label fileInfo;
    public TextField tag;
    public TextField categories;
    public ComboBox<String> isRecommend;
    public TextField locate;

    private File file;

    private static final String WORK_PATH = "D:\\blog";
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void upload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择要上传的文件");
        //设置文件上传类型
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("excel files (*.md)", "*.md");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectFile = fileChooser.showOpenDialog(new Stage());
        if (Objects.isNull(selectFile)) {
            AlertUtils.errorAlert("原因：选择文件无效");
        } else {
            fileInfo.setText("已上传文件：" + selectFile.getName());
            this.file = selectFile;
        }
    }

    @FXML
    public void convert() {
        if (Objects.isNull(this.file)) {
            AlertUtils.errorAlert("请先上传文件...");
        }
        String filePath = file.getPath();

        String title = getMarkdownTitle(filePath);
        FrontMatterEntity frontMatterEntity = new FrontMatterEntity();
        frontMatterEntity.setTitle(title);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(dateTimeFormatter);
        String tagText = tag.getText();
        if (Objects.isNull(tagText)) {
            AlertUtils.errorAlert("请输入标签值...");
        }
        String categoriesText = categories.getText();
        if (Objects.isNull(categoriesText)) {
            AlertUtils.errorAlert("请输入分类值...");
        }
        String recommendText = isRecommend.getSelectionModel().getSelectedItem();
        if (Objects.isNull(recommendText)) {
            AlertUtils.errorAlert("请选择是否推荐...");
        }
        String locateText = locate.getText();
        if (Objects.isNull(locateText)) {
            AlertUtils.errorAlert("请输入位置...");
        }

        frontMatterEntity.setDate(format);
        frontMatterEntity.setTags(tagText);
        frontMatterEntity.setCategories(categoriesText);
        frontMatterEntity.setRecommend(Objects.equals(recommendText, "是") ? "true" : "false");
        frontMatterEntity.setLocate(locateText);
        addContent(frontMatterEntity, filePath);
        System.out.println("转换完成...");
        AlertUtils.infoAlert("格式转换成功,请查看原文件。");
    }

    @FXML
    public void push(){
        this.executeCMD();
        AlertUtils.infoAlert("指令执行完成,部署成功.");
    }

    @FXML
    public void convertAndPush() {
        this.convert();
        // 开始执行指令
        this.executeCMD();
        AlertUtils.infoAlert("执行执行完成，部署成功。");
    }

    private void executeCMD() {
        System.out.println("开始执行指令...");
        String hexoClean = "hexo clean";
        String hexoGenerate = "hexo g";
        String hexoDeploy = "hexo d";
        List<String> commandList = new ArrayList<>();
        commandList.add(hexoClean);
        commandList.add(hexoGenerate);
        commandList.add(hexoDeploy);
        try {
            CMDUtils.execute(WORK_PATH, commandList);
        } catch (IOException e) {
            AlertUtils.errorAlert("出错了：" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            System.out.println("执行完成...");

        }

    }

    public void addContent(FrontMatterEntity meta, String filePath) {
        String sp = new String("---");
        StringBuilder sb = new StringBuilder();
        sb.append(sp).append("\n");
        sb.append("title: ").append(meta.getTitle()).append("\n");
        sb.append("date: ").append(meta.getDate()).append("\n");
        sb.append("tags: ").append(meta.getTags()).append("\n");
        sb.append("categories: ").append(meta.getCategories()).append("\n");
        sb.append("recommend: ").append(meta.getRecommend()).append("\n");
        sb.append("locate: ").append(meta.getLocate()).append("\n");
        sb.append(sp).append("\n");
        try {
            // 读取原始 Markdown 文件内容
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder originalContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                originalContent.append(line).append("\n");
            }
            reader.close();
            // 构造要添加的新内容
            String combinedContent = sb.toString() + originalContent;
            // 写入合并后的内容到原始文件
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(combinedContent);
            writer.close();
            System.out.println("文件写入完成。");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getMarkdownTitle(String filePath) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String firstLine = bufferedReader.readLine();
            if (Objects.isNull(firstLine)) {
                System.out.println("文件为空");
            }
            StringBuilder result = new StringBuilder();
            char[] chars = firstLine.toCharArray();
            for (char aChar : chars) {
                if (aChar == ' ' || aChar == '#') {
                    continue;
                }
                result.append(aChar);
            }
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}