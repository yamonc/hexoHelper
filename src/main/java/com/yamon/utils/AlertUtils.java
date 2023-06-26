package com.yamon.utils;

import javafx.scene.control.Alert;

/**
 * @Author yamon
 * @Date 2023-06-15 10:45
 * @Description
 * @Version 1.0
 */
public class AlertUtils {
    public static void errorAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("提示信息");
        alert.setHeaderText("执行错误:");
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void successAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("提示信息");
        alert.setHeaderText("执行成功:");
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void infoAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示信息");
        alert.setHeaderText("执行成功：");
        alert.setContentText(content);
        alert.showAndWait();
    }
}
