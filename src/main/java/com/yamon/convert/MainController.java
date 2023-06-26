package com.yamon.convert;

import com.yamon.convert.dto.FrontMatterEntity;
import com.yamon.convert.dto.PropertiesEntity;
import com.yamon.utils.AlertUtils;
import com.yamon.utils.CMDUtils;
import com.yamon.utils.SQLiteUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Author yamon
 * @Date 2023-06-20 20:38
 * @Description
 * @Version 1.0
 */
public class MainController {

    private static final String CREATE_CATEGORY_SQL = "CREATE TABLE IF NOT EXISTS categories (id int not null primary key, name varchar(20) not null)";
    private static final String CREATE_LOCATE_SQL = "CREATE TABLE IF NOT EXISTS locates (id int not null primary key, name varchar(20) not null)";

    public static final String CREATE_PROPERTIES_SQL = "CREATE TABLE IF NOT EXISTS properties (item varchar(20) not null, content varchar(255) not null)";

    public Button fxUpload;
    public Label fileInfo;
    public ComboBox<String> isRecommend;
    public ComboBox<String> tagSelect;

    public TextField title;
    public Label titleShow;
    public Label coverShow;
    public TextField coverText;
    public Label recommendShow;
    public Label commentShow;
    public ComboBox<String> isComment;
    public Label tagShow;
    public ComboBox<String> categorySelect;
    public Label categoriesShow;
    public Label locateShow;
    public ComboBox<String> locateSelect;
    public TextField keyword;
    public Label keywordShow;
    public TextField workPath;
    public TextField uploadPath;

    private File file;

    private static final String WORK_PATH = "D:\\blog";

    private Set<String> chooseTags = new HashSet<>();

    private Set<String> chooseCategory = new HashSet<>();

    private Set<String> chooseLocates = new HashSet<>();

    public void initialize() {
        System.out.println("init");
        // 先初始化表
        try {
            SQLiteUtils.createTable(CREATE_CATEGORY_SQL);
            SQLiteUtils.createTable(CREATE_LOCATE_SQL);
            SQLiteUtils.createTable(CREATE_PROPERTIES_SQL);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        coverText.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                // 在此处编写失去焦点事件的处理逻辑
                this.coverShow.setText(this.coverText.getText());
            }
        });
        isRecommend.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                this.recommendShow.setText(this.isRecommend.getValue());
            }
        });
        isComment.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                this.commentShow.setText(this.isComment.getValue());
            }
        });
        // 标签label显示的内容
        tagSelect.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (this.tagSelect.getValue() == null) {
                    return;
                }
                String selectValue = this.tagSelect.getValue();
                this.chooseTags.add(selectValue);
                this.tagShow.setText(chooseTags.toString());
            }
        });
        // 分类label中显示的内容
        categorySelect.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (this.categorySelect.getValue() == null) {
                    return;
                }
                String selectValue = this.categorySelect.getValue();
                System.out.println(selectValue);
                this.chooseCategory.add(selectValue);
                this.categoriesShow.setText(chooseCategory.toString());
            }
        });
        locateSelect.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (this.locateSelect.getValue() == null) {
                    return;
                }
                String selectValue = this.locateSelect.getValue();
                System.out.println(selectValue);
                this.chooseLocates.add(selectValue);
                this.locateShow.setText(chooseLocates.toString());
            }
        });
        keyword.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                // 在此处编写失去焦点事件的处理逻辑
                this.keywordShow.setText(this.keyword.getText());
            }
        });

    }

    @FXML
    protected void getAllTags() {
        List<String> tags = new ArrayList<>();
        // 查出所有的标签内容
        try {
            tags = SQLiteUtils.query("tags", "1=1");
            ObservableList<String> items = tagSelect.getItems();
            items.clear();
            items.addAll(tags);
            // 给下拉框添加内容
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void addOneTag() {
        try {
            System.out.println("开始创建表");
            String createSQL = "CREATE TABLE IF NOT EXISTS tags(id varchar(64) not null primary key," +
                    "name varchar(20) not null)";
            SQLiteUtils.createTable(createSQL);
            System.out.println("表创建完成,等到打开弹框...");
            // 创建成功,执行创建标签
            // 弹出来弹框
            System.out.println("正在打开弹框");
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("您正在操作：添加标签");
            dialog.setHeaderText("您正在操作：添加标签");
            dialog.setContentText("请输入标签值:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            // The Java 8 way to get the response value (with lambda expression).
            result.ifPresent(name -> {
                try {
                    int insert = SQLiteUtils.insert("tags", UUID.randomUUID().toString(), name);
                    if (insert == 1) {
                        // 新增成功
                        AlertUtils.successAlert("添加成功");
                    } else {
                        AlertUtils.errorAlert("添加失败");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void addOneCategory() {

        System.out.println("表创建完成,等到打开弹框...");
        // 创建成功,执行创建标签
        // 弹出来弹框
        System.out.println("正在打开弹框");
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("您正在操作：添加分类");
        dialog.setHeaderText("您正在操作：添加分类");
        dialog.setContentText("请输入分类名称:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> {
            try {
                int insert = SQLiteUtils.insert("categories", UUID.randomUUID().toString(), name);
                if (insert == 1) {
                    // 新增成功
                    AlertUtils.successAlert("添加成功");
                } else {
                    AlertUtils.errorAlert("添加失败");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    protected void addOneLocate() {
        // 弹出来弹框
        System.out.println("正在打开弹框");
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("您正在操作：添加位置");
        dialog.setHeaderText("您正在操作：添加位置");
        dialog.setContentText("请输入位置名称:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> {
            try {
                int insert = SQLiteUtils.insert("locates", UUID.randomUUID().toString(), name);
                if (insert == 1) {
                    // 新增成功
                    AlertUtils.successAlert("添加成功");
                } else {
                    AlertUtils.errorAlert("添加失败");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    protected void upload() {
        FileChooser fileChooser = new FileChooser();
        // TODO：设置打开指定目录
        fileChooser.setTitle("选择要上传的文件");
        //设置文件上传类型
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("markdown files (*.md)", "*.md");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectFile = fileChooser.showOpenDialog(new Stage());
        if (Objects.isNull(selectFile)) {
            AlertUtils.errorAlert("原因：选择文件无效");
        } else {
            fileInfo.setText(selectFile.getName());
            this.file = selectFile;
        }
    }

    @FXML
    public void convert() {
        if (Objects.isNull(this.file)) {
            AlertUtils.errorAlert("请先上传文件...");
        }
        String filePath = file.getPath();

        FrontMatterEntity frontMatterEntity = new FrontMatterEntity();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(dateTimeFormatter);
        String tagText = tagShow.getText();
        if (Objects.isNull(tagText)) {
            AlertUtils.errorAlert("请输入标签值...");
        }
        String categoriesText = categoriesShow.getText();
        if (Objects.isNull(categoriesText)) {
            AlertUtils.errorAlert("请输入分类值...");
        }
        String recommendText = isRecommend.getSelectionModel().getSelectedItem();
        if (Objects.isNull(recommendText)) {
            AlertUtils.errorAlert("请选择是否推荐...");
        }
        String locateText = locateShow.getText();
        if (Objects.isNull(locateText)) {
            AlertUtils.errorAlert("请输入位置...");
        }
        frontMatterEntity.setTitle(titleShow.getText());
        frontMatterEntity.setCover(coverShow.getText());
        frontMatterEntity.setRecommend(recommendShow.getText());
        frontMatterEntity.setComment(commentShow.getText());
        frontMatterEntity.setTags(tagText);
        frontMatterEntity.setCategories(categoriesText);
        frontMatterEntity.setLocate(locateText);
        frontMatterEntity.setKeywords(keywordShow.getText());
        frontMatterEntity.setDate(format);
        frontMatterEntity.setRecommend(Objects.equals(recommendText, "是") ? "true" : "false");
        frontMatterEntity.setLocate(locateText);
        addContent(frontMatterEntity, filePath);
        System.out.println("转换完成...");
        AlertUtils.infoAlert("格式转换成功,请查看原文件。");
    }

    @FXML
    public void push() {
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
        String sp = "---";
        StringBuilder sb = new StringBuilder();
        sb.append(sp).append("\n");
        sb.append("title: ").append(meta.getTitle()).append("\n");
        sb.append("date: ").append(meta.getDate()).append("\n");
        sb.append("tags: ").append(meta.getTags()).append("\n");
        sb.append("categories: ").append(meta.getCategories()).append("\n");
        sb.append("recommend: ").append(meta.getRecommend()).append("\n");
        sb.append("locate: ").append(meta.getLocate()).append("\n");
        sb.append("cover: ").append(meta.getCover()).append("\n");
        sb.append("comment: ").append(meta.getComment()).append("\n");
        sb.append("keywords: ").append(meta.getKeywords()).append("\n");
        sb.append(sp).append("\n");
        try {
            // 读取原始 Markdown 文件内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            StringBuilder originalContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                originalContent.append(line).append("\n");
            }
            reader.close();
            // 构造要添加的新内容
            String combinedContent = sb.toString() + originalContent;
            // 写入合并后的内容到原始文件
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8));
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

    /**
     * 生成文章标题
     *
     * @param actionEvent actionEvent
     */
    public void generateTitle(ActionEvent actionEvent) {
        if (Objects.isNull(this.file)) {
            AlertUtils.errorAlert("请上传文件后重试！");
        }
        // 读取文章的第一行
        String title = getMarkdownTitle(this.file.getPath());
        this.title.setText(title);
        this.titleShow.setText(title);

    }

    public void submitTag(ActionEvent actionEvent) {
        // 获取标签填写的内容
        String value = tagSelect.getValue();
        if (Objects.isNull(value)) {
            AlertUtils.errorAlert("请选择任一标签...");
        } else {
            // 给已选里面渲染
            if (!chooseTags.contains(value)) {
                chooseTags.add(value);
            }
        }
        tagSelect.getSelectionModel().clearSelection();
    }

    public void getAllCategories(Event event) {
        List<String> categoryList = new ArrayList<>();
        // 查出所有的标签内容
        try {
            categoryList = SQLiteUtils.query("categories", "1=1");
            ObservableList<String> items = categorySelect.getItems();
            items.clear();
            items.addAll(categoryList);
            // 给下拉框添加内容
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void submitCategory(ActionEvent actionEvent) {
        // 获取标签填写的内容
        String value = categorySelect.getValue();
        if (Objects.isNull(value)) {
            AlertUtils.errorAlert("请选择任一分类...");
        } else {
            // 给已选里面渲染
            chooseCategory.add(value);
        }
        categorySelect.getSelectionModel().clearSelection();
    }

    public void submitLocate(ActionEvent actionEvent) {
        // 获取标签填写的内容
        String value = locateSelect.getValue();
        if (Objects.isNull(value)) {
            AlertUtils.errorAlert("请选择任一位置...");
        } else {
            // 给已选里面渲染
            chooseCategory.add(value);
        }
        locateSelect.getSelectionModel().clearSelection();
    }

    public void getAllLocates(Event event) {
        List<String> LocatesLost = new ArrayList<>();
        // 查出所有的标签内容
        try {
            LocatesLost = SQLiteUtils.query("locates", "1=1");
            ObservableList<String> items = locateSelect.getItems();
            items.clear();
            items.addAll(LocatesLost);
            // 给下拉框添加内容
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveSetting(ActionEvent actionEvent) {
        List<PropertiesEntity> list = new ArrayList<>();
        String id = workPath.getId();
        String text = workPath.getText();
        PropertiesEntity workPathProperties = new PropertiesEntity(id, text);
        try {
            List<String> properties = SQLiteUtils.query("properties", "item = " + "'" + id + "'");
            if (properties.isEmpty()) {
                // 如果为空，则直接新增
                SQLiteUtils.insert1("properties", id, text);
            } else {
                // 如果不为空，需要更新
                SQLiteUtils.update("properties", "item = " + "'" + id + "'", "content = " + "'" + text + "'");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        list.add(workPathProperties);
        String uploadPathText = uploadPath.getText();
        String uploadPathId = uploadPath.getId();
        PropertiesEntity uploadPathProperties = new PropertiesEntity(uploadPathId, uploadPathText);
        try {
            List<String> properties = SQLiteUtils.query("properties", "item = " + "'" + uploadPathId + "'");
            if (properties.isEmpty()) {
                // 如果为空，则直接新增
                SQLiteUtils.insert1("properties", id, uploadPathText);
            } else {
                // 如果不为空，需要更新
                SQLiteUtils.update("properties", "item = " + "'" + uploadPathId + "'", "content = " + "'" + uploadPathText + "'");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        list.add(uploadPathProperties);


    }
}
