package se.ya.videobutik.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class StaffController {
    @FXML private Button btn_add_employee;
    @FXML private Button btn_delete_employee;
    @FXML private Button btn_edit_employee;

    @FXML private ComboBox<?> cb_add_city;
    @FXML private ComboBox<?> cb_edit_city;

    @FXML private CheckBox checkb_add_active;
    @FXML private CheckBox checkb_edit_active;

    @FXML private TextField tf_add_address;
    @FXML private TextField tf_add_district;
    @FXML private TextField tf_add_email;
    @FXML private TextField tf_add_first_name;
    @FXML private TextField tf_add_last_name;
    @FXML private TextField tf_add_password;
    @FXML private TextField tf_add_postal_code;
    @FXML private TextField tf_add_user_name;
    @FXML private TextField tf_edit_address;
    @FXML private TextField tf_edit_district;
    @FXML private TextField tf_edit_email;
    @FXML private TextField tf_edit_first_name;
    @FXML private TextField tf_edit_last_name;
    @FXML private TextField tf_edit_password;
    @FXML private TextField tf_edit_postal_code;
    @FXML private TextField tf_edit_user_name;
    @FXML private TextField tf_find_last_name;

    @FXML private TableView<?> tv_employees;
}