package org.phonestoremanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.phonestoremanager.models.AccountModel;
import org.phonestoremanager.repositories.AccountRepository;
import org.phonestoremanager.repositories.EmployeeRepository;
import org.phonestoremanager.models.EmployeeModel;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class EmployeeViewController implements Initializable {
    @FXML
    TableView<EmployeeModel> tableView;

    @FXML
    TableColumn<EmployeeModel, Integer> columnID;

    @FXML
    TableColumn<EmployeeModel, String> columnFirstname;

    @FXML
    TableColumn<EmployeeModel, String> columnLastname;

    @FXML
    TableColumn<EmployeeModel, String> columnGender;

    @FXML
    TableColumn<EmployeeModel, String> columnEmail;

    @FXML
    TableColumn<EmployeeModel, String> columnPhonenumber;

    @FXML
    TableColumn<EmployeeModel, String> columnAddress;

    @FXML
    TableColumn<EmployeeModel, String> columnPosition;

    @FXML
    TableColumn<EmployeeModel, Double> columnSalary;

    @FXML
    private Button fixedButton;

    private AccountModel accountModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        columnFirstname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnLastname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnGender.setCellValueFactory(new PropertyValueFactory<>("genderText"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnPhonenumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<>("salary")); // Lấy dữ liệu salary

        columnSalary.setCellFactory(column -> new TableCell<EmployeeModel, Double>() {
            private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(item)); // Ví dụ: 15.000.000 ₫
                }
            }
        });


        render(EmployeeRepository.getAll());

        tableView.setRowFactory(tv -> {
            TableRow<EmployeeModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    EmployeeModel clickedEmployee = row.getItem();

                    Alert choiceAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    choiceAlert.setTitle("Chọn thao tác");
                    choiceAlert.setHeaderText("Bạn muốn thực hiện thao tác nào?");
                    choiceAlert.setContentText("Hãy chọn:");

                    ButtonType btnUpdate = new ButtonType("Sửa");
                    ButtonType btnDelete = new ButtonType("Xóa");
                    ButtonType btnCancel = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);

                    choiceAlert.getButtonTypes().setAll(btnUpdate, btnDelete, btnCancel);

                    choiceAlert.showAndWait().ifPresent(response -> {
                        if (response == btnUpdate) {
                            openEmployeeDetailWindow(clickedEmployee); // Hàm mở cửa sổ sửa nhân viên
                        } else if (response == btnDelete) {
                            Alert confirmDelete = new Alert(Alert.AlertType.WARNING);
                            confirmDelete.setTitle("Xác nhận xóa");
                            confirmDelete.setHeaderText("Bạn có chắc muốn xóa nhân viên này?");
                            confirmDelete.setContentText("Hành động này không thể hoàn tác!");

                            ButtonType btnYes = new ButtonType("Xóa", ButtonBar.ButtonData.OK_DONE);
                            ButtonType btnNo = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
                            confirmDelete.getButtonTypes().setAll(btnYes, btnNo);

                            confirmDelete.showAndWait().ifPresent(confirm -> {
                                if (confirm == btnYes) {
                                    String username =
                                            EmployeeRepository.getEmployeeIDByInformation(clickedEmployee.getEmail(),
                                                    clickedEmployee.getPhoneNumber(), clickedEmployee.getGenderText(),
                                                    clickedEmployee.getSalary()).get(0);
                                    int deletedEmp = EmployeeRepository.delete(username);
                                    int deletedAcc =  AccountRepository.delete(username);
                                    if (deletedEmp != 0 && deletedAcc != 0) {
                                        render(EmployeeRepository.getAll());
                                        Alert deleted = new Alert(Alert.AlertType.INFORMATION);
                                        deleted.setTitle("Đã xóa");
                                        deleted.setHeaderText(null);
                                        deleted.setContentText("Nhân viên đã được xóa thành công.");
                                        deleted.showAndWait();
                                    } else {
                                        Alert fail = new Alert(Alert.AlertType.ERROR);
                                        fail.setTitle("Lỗi");
                                        fail.setHeaderText(null);
                                        fail.setContentText("Xóa nhân viên thất bại.");
                                        fail.showAndWait();
                                    }

                                }
                            });
                        }
                        // Nếu chọn Hủy thì không làm gì
                    });
                }
            });
            return row;
        });

        if (fixedButton != null) {
            fixedButton.toFront(); // Đưa nút lên trên cùng giao diện
            fixedButton.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/AddEmployeeForm.fxml"));
                    Parent root = loader.load();

                    // Tạo một Stage mới hoặc sử dụng Stage hiện tại
                    Stage stage = new Stage();
                    stage.setTitle("Thêm Dòng Sản Phẩm Mới");
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                    // Bạn có thể dùng Alert để thông báo lỗi
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Không thể mở giao diện Thêm Sản Phẩm");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });
        } else {
            System.err.println("Nút này nó bị null");
        }
    }

    public void render(List<EmployeeModel> list) {
        ObservableList<EmployeeModel> listObser = FXCollections.observableArrayList();
        if (list != null) {
            listObser.addAll(list);
        }else {
            System.out.println("Khong the lay du lieu nhan vien");
            return;
        }
        tableView.setItems(listObser);
    }

    private void openEmployeeDetailWindow(EmployeeModel employee) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/phonestoremanager/viewsfxml/AddEmployeeForm.fxml"));
            Parent root = loader.load();

            // Truyền dữ liệu sang controller
            AddEmployeeController controller = loader.getController();
            controller.setEmployeeData(employee);

            Stage stage = new Stage();
            stage.setTitle("Chi tiết nhân viên");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/org/phonestoremanager/assets/css/add_employee_style.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); // chặn cửa sổ khác
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAccount(AccountModel accountModel) {
        this.accountModel = accountModel;
    }
}
