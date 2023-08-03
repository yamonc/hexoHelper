package com.yamon.convert;

import com.yamon.convert.dto.FrontMatterEntity;
import com.yamon.convert.dto.PropertiesEntity;
import com.yamon.entity.CategoryEntity;
import com.yamon.entity.TagEntity;
import com.yamon.service.*;
import com.yamon.service.impl.*;
import com.yamon.utils.AlertUtils;
import com.yamon.utils.CMDUtils;
import com.yamon.vo.CategoryTableVO;
import com.yamon.vo.TagTableVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

/**
 * @Author yamon
 * @Date 2023-06-20 20:38
 * @Description
 * @Version 1.0
 */
public class MainController {

    public TableView<TagTableVO> tagTableId;
    public TableColumn<TagTableVO, String> tagId;
    public TableColumn<TagTableVO, String> tagName;
    public TableColumn<TagTableVO, String> tagCreateTime;
    public TableColumn<TagTableVO, String> tagOperate;
    public TableView<CategoryTableVO> categoryTableId;
    public TableColumn<CategoryTableVO, String> categoryId;
    public TableColumn<CategoryTableVO, String> categoryName;
    public TableColumn<CategoryTableVO, String> categoryCreateTime;
    public TableColumn<CategoryTableVO, String> categoryOperate;
    public TextField passwordMessage;
    public TextField passwordWrong;
    public TextField settingPassword;
    public Button btnGenerateByDefaultPassword;
    public TextField passwordText;
    public Label passwordShow;
    Logger logger = Logger.getLogger("mainController");

    private static final String CREATE_TAG_SQL = "CREATE TABLE IF NOT EXISTS tags(id int not null primary key, name varchar(20) not null, create_time varchar(32) not null)";
    private static final String CREATE_CATEGORY_SQL = "CREATE TABLE IF NOT EXISTS categories (id int not null primary key, name varchar(20) not null, create_time varchar(32) not null)";
    private static final String CREATE_LOCATE_SQL = "CREATE TABLE IF NOT EXISTS locates (id int not null primary key, name varchar(20) not null, create_time varchar(32) not null)";
    public static final String CREATE_PROPERTIES_SQL = "CREATE TABLE IF NOT EXISTS properties (item varchar(20) not null, content varchar(255) not null, create_time varchar(32) not null)";

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

    private static String WORK_PATH;

    private Set<String> chooseTags = new HashSet<>();

    private Set<String> chooseCategory = new HashSet<>();

    private Set<String> chooseLocates = new HashSet<>();

    private DaoService daoService = new DaoServiceImpl();

    private MainService mainService = new MainServiceImpl();

    private TagService tagService = new TagServiceImpl();

    private CategoryService categoryService = new CategoryServiceImpl();

    private PropertiesService propertiesService = new PropertiesServiceImpl();

    public void initialize() {
        logger.info("执行初始化工作");
        // 先初始化表
        try {
            logger.info("初始化所有表：标签表、分类表、位置表、配置表...");
            daoService.createTable(CREATE_TAG_SQL, CREATE_CATEGORY_SQL, CREATE_LOCATE_SQL, CREATE_PROPERTIES_SQL);
            logger.info("初始化成功...");
            logger.info("开始初始化设置-密码内容");
            if (!propertiesService.isKeyExist("message") && !propertiesService.isKeyExist("wrong_pass_message") && !propertiesService.isKeyExist("password")) {
                propertiesService.initData();
            }
        } catch (ClassNotFoundException | SQLException e) {
            logger.info("初始化失败...");
            throw new RuntimeException(e);
        }
        String uploadPath;
        String wordPath;
        try {
            uploadPath = mainService.getUploadPath();
            wordPath = mainService.getWorkPath();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!Objects.isNull(uploadPath)) {
            this.uploadPath.setText(uploadPath);
        }
        if (!Objects.isNull(wordPath)) {
            this.workPath.setText(wordPath);
            WORK_PATH = wordPath;
        }
        String message = null;
        String wrongMessage = null;
        String passwordd = null;
        try {
            message = propertiesService.getValueByKey("message");
            wrongMessage = propertiesService.getValueByKey("wrong_pass_message");
            passwordd = propertiesService.getValueByKey("password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        passwordMessage.setText(message);
        passwordWrong.setText(wrongMessage);
        settingPassword.setText(passwordd);
        title.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                // 在此处编写失去焦点事件的处理逻辑
                this.titleShow.setText(this.title.getText());
            }
        });
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
        passwordText.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                // 在此处编写失去焦点事件的处理逻辑
                this.passwordShow.setText(this.passwordText.getText());
            }
        });
    }

    @FXML
    protected void getAllTags() {
        List<String> tags = new ArrayList<>();
        // 查出所有的标签内容
        try {
            tags = daoService.query("tags", "1=1");
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
        // 弹出来弹框
        logger.info("正在打开弹框");
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("您正在操作：添加标签");
        dialog.setHeaderText("您正在操作：添加标签");
        dialog.setContentText("请输入标签值:");
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> {
            boolean flag = tagService.saveOneTag(UUID.randomUUID(), name);
            if (flag) {
                // 新增成功
                AlertUtils.successAlert("添加成功");
            } else {
                AlertUtils.errorAlert("添加失败");
            }
        });
    }

    @FXML
    protected void addOneCategory() {
        logger.info("正在打开弹框");
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("您正在操作：添加分类");
        dialog.setHeaderText("您正在操作：添加分类");
        dialog.setContentText("请输入分类名称:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> {
            boolean flag = categoryService.saveOneCategory(UUID.randomUUID().toString(), name);
            if (flag) {
                // 新增成功
                AlertUtils.successAlert("添加成功");
            } else {
                AlertUtils.errorAlert("添加失败");
            }
        });
    }

    @FXML
    protected void addOneLocate() {
        // 弹出来弹框
        logger.info("正在打开弹框");
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("您正在操作：添加位置");
        dialog.setHeaderText("您正在操作：添加位置");
        dialog.setContentText("请输入位置名称:");
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> {
            try {
                int insert = daoService.insert("locates", UUID.randomUUID().toString(), name);
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
        String path;
        try {
            path = mainService.getUploadPath();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!Objects.isNull(path) && !"".equals(path)) {
            fileChooser.setInitialDirectory(new File(path));
        }
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
        frontMatterEntity.setPassword(passwordShow.getText());
        try {
            frontMatterEntity.setPasswordAbstract(propertiesService.getValueByKey("abstract"));
            frontMatterEntity.setPasswordMessage(propertiesService.getValueByKey("message"));
            frontMatterEntity.setWrongPassMessage(propertiesService.getValueByKey("wrong_pass_message"));
            frontMatterEntity.setWrongHashMessage(propertiesService.getValueByKey("wrong_hash_message"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        AlertUtils.infoAlert("执行完成，部署成功。");
    }

    private void executeCMD() {
        System.out.println("开始执行指令...");
        String hexoClean = " hexo clean ";
        String hexoGenerate = "& hexo g ";
        String hexoDeploy = "& hexo d";
        String cdTarget = "cd " + WORK_PATH + "&";
        List<String> commandList = new ArrayList<>();
        commandList.add(hexoClean);
        commandList.add(hexoGenerate);
        commandList.add(hexoDeploy);
        try {
            CMDUtils.executeCommand("cmd.exe", "/c", cdTarget, hexoClean, hexoGenerate, hexoDeploy);
            logger.info("已成功部署。");
        } catch (IOException e) {
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
        sb.append("password: ").append(meta.getPassword()).append("\n");
        sb.append("abstract: ").append(meta.getPasswordAbstract()).append("\n");
        sb.append("message: ").append(meta.getPasswordMessage()).append("\n");
        sb.append("wrong_pass_message: ").append(meta.getWrongPassMessage()).append("\n");
        sb.append("wrong_hash_message: ").append(meta.getWrongHashMessage()).append("\n");
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
            categoryList = daoService.query("categories", "1=1");
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
            LocatesLost = daoService.query("locates", "1=1");
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
            List<String> properties = daoService.query("properties", "item = " + "'" + id + "'");
            if (properties.isEmpty()) {
                // 如果为空，则直接新增
                daoService.insert("properties", id, text);
                AlertUtils.successAlert("新增成功");
                this.workPath.setText(text);
            } else {
                // 如果不为空，需要更新
                daoService.update("properties", "content", text, "item", id);
                AlertUtils.successAlert("更新成功");
                this.workPath.setText(text);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        list.add(workPathProperties);
        String uploadPathText = uploadPath.getText();
        String uploadPathId = uploadPath.getId();
        PropertiesEntity uploadPathProperties = new PropertiesEntity(uploadPathId, uploadPathText);
        try {
            List<String> properties = daoService.query("properties", "item = " + "'" + uploadPathId + "'");
            if (properties.isEmpty()) {
                // 如果为空，则直接新增
                daoService.insert("properties", uploadPathId, uploadPathText);
                AlertUtils.successAlert("新增成功");
                this.uploadPath.setText(uploadPathText);
            } else {
                // 如果不为空，需要更新
                daoService.update("properties", "content", uploadPathText, "item", uploadPathId);
                AlertUtils.successAlert("更新成功");
                this.uploadPath.setText(uploadPathText);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        list.add(uploadPathProperties);
        String settingPasswordText = settingPassword.getText();
        String passwordMessageText = passwordMessage.getText();
        String passwordWrongText = passwordWrong.getText();
        if (Objects.nonNull(passwordMessageText)) {
            try {
                propertiesService.updateValueByKey("message", passwordMessageText);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (Objects.nonNull(passwordWrongText)) {
            try {
                propertiesService.updateValueByKey("wrong_pass_message", passwordWrongText);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (Objects.nonNull(settingPasswordText)) {
            try {
                propertiesService.updateValueByKey("password", settingPasswordText);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void refreshAllTags(ActionEvent actionEvent) {
        try {
            System.out.println("正在手动刷新");
            refreshTags(actionEvent);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshTags(Event event) throws SQLException {
        List<TagTableVO> list = new ArrayList<>();
        List<TagEntity> allTags = tagService.getAllTags();
        // 封装tagvo
        for (TagEntity allTag : allTags) {
            TagTableVO tagTableVO = new TagTableVO();
            tagTableVO.setTagId(allTag.getTagId());
            tagTableVO.setTagName(allTag.getTagName());
            tagTableVO.setCreateTime(allTag.getCreateTime());
            list.add(tagTableVO);
        }
        ObservableList<TagTableVO> data = FXCollections.observableList(list);
        tagId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        tagName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tagCreateTime.setCellValueFactory(cellData -> cellData.getValue().createTimeProperty());
        tagOperate.setCellFactory(createTagButtonCellFactory());
        tagTableId.setItems(data);
    }

    private Callback<TableColumn<TagTableVO, String>, TableCell<TagTableVO, String>> createTagButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<TagTableVO, String> call(TableColumn<TagTableVO, String> param) {
                return new TableCell<>() {
                    private final Button button = new Button("删除");

                    {
                        button.setOnAction(event -> {
                            TagTableVO dataItem = getTableView().getItems().get(getIndex());
                            String id = dataItem.getTagId();
                            System.out.println("Button clicked for id: " + id);
                            // 根据uuid删除这个标签
                            boolean flag = tagService.deleteById(id);
                            if (flag) {
                                AlertUtils.successAlert("删除成功!");
                                try {
                                    refreshTags(new ActionEvent());
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                AlertUtils.errorAlert("删除失败!");
                            }
                        });
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
            }
        };
    }

    public void refreshCategory(Event event) {
        List<CategoryTableVO> list = new ArrayList<>();
        List<CategoryEntity> allTags = null;
        try {
            allTags = categoryService.getAllCateogies();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // 封装tagvo
        for (CategoryEntity allTag : allTags) {
            CategoryTableVO categoryTableVO = new CategoryTableVO();
            categoryTableVO.setCategoryId(allTag.getCategoryId());
            categoryTableVO.setCategoryName(allTag.getCategoryName());
            categoryTableVO.setCategoryCreateTime(allTag.getCategoryCreateTime());
            list.add(categoryTableVO);
        }
        ObservableList<CategoryTableVO> data = FXCollections.observableList(list);
        categoryId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        categoryName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        categoryOperate.setCellFactory(createCategoryButtonCellFactory());
        categoryTableId.setItems(data);
    }

    private Callback<TableColumn<CategoryTableVO, String>, TableCell<CategoryTableVO, String>> createCategoryButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<CategoryTableVO, String> call(TableColumn<CategoryTableVO, String> param) {
                return new TableCell<>() {
                    private final Button button = new Button("删除");

                    {
                        button.setOnAction(event -> {
                            CategoryTableVO dataItem = getTableView().getItems().get(getIndex());
                            String id = dataItem.getCategoryId();
                            System.out.println("Button clicked for id: " + id);
                            // 根据uuid删除这个标签
                            boolean flag = categoryService.deleteById(id);
                            if (flag) {
                                AlertUtils.successAlert("删除成功!");
                                refreshCategory(new ActionEvent());
                            } else {
                                AlertUtils.errorAlert("删除失败!");
                            }
                        });
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
            }
        };
    }

    public void addOneTagFromTable(ActionEvent actionEvent) {
        addOneTag();
        // 刷新列表
        try {
            refreshTags(actionEvent);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addOneCategoryFromTable(ActionEvent actionEvent) {
        addOneCategory();
        refreshCategory(actionEvent);
    }

    public void resetAll(ActionEvent actionEvent) {
        this.file = null;
        this.fileInfo.setText("请重新上传文件");
        this.titleShow.setText("");
        this.coverShow.setText("");
        this.recommendShow.setText("");
        this.commentShow.setText("");
        this.tagShow.setText("");
        this.categoriesShow.setText("");
        this.locateShow.setText("");
        this.keywordShow.setText("");

        this.title.setText("");
        this.coverText.setText("");
        this.isRecommend.setValue(null);
        this.isComment.setValue(null);
        this.tagSelect.setValue(null);
        this.categorySelect.setValue(null);
        this.locateSelect.setValue(null);
        this.keyword.setText(null);

        this.chooseTags = new HashSet<>();
        this.chooseCategory = new HashSet<>();
        this.chooseLocates = new HashSet<>();
    }

    public void useDefaultPassword(ActionEvent actionEvent) {
        try {
            String password = propertiesService.getValueByKey("password");
            passwordText.setText(password);
            passwordShow.setText(password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
