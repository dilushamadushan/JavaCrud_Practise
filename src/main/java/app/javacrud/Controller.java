package app.javacrud;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private TextField author;

    @FXML
    private TableView<Book> book_table;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField id;

    @FXML
    private TextField pages;

    @FXML
    private TableColumn<Book, String> t_author;

    @FXML
    private TableColumn<Book, Integer> t_id;

    @FXML
    private TableColumn<Book, Integer> t_pages;

    @FXML
    private TableColumn<Book, String> t_tittle;

    @FXML
    private TableColumn<Book, Integer> t_year;

    @FXML
    private TextField tittle;

    @FXML
    private Button updateBtn;

    @FXML
    private TextField year;

    @FXML
    private Label errorMessages;

    @FXML
    private Label sucessMessage;

    @FXML
    void addBtnClick(ActionEvent event) {
            insertBook();
    }

    @FXML
    void deleteBtnClick(ActionEvent event) {
        deleteBook();
    }

    @FXML
    void updateBtnClcik(ActionEvent event) {
        updateBook();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showBook();
    }
    @FXML
    void handleRowAction(MouseEvent event) {
        Book book = book_table.getSelectionModel().getSelectedItem();
        id.setText("" + book.getId());
        tittle.setText(book.getTitle());
        author.setText(book.getAuthor());
        year.setText("" + book.getYear());
        pages.setText("" + book.getPages());
    }
    public ObservableList<Book> getBooks() {
        ObservableList<Book> bookList = FXCollections.observableArrayList();

        Connection con = GetConnection.getConnection();

        String sql = "SELECT * FROM book";
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            rs = st.executeQuery(sql);
            Book book;
            while(rs.next()){
                book = new Book(rs.getInt("id"),rs.getString("tittle"),rs.getString("author"),rs.getInt("year"),rs.getInt("pages"));
                bookList.add(book);
            }
        }catch (Exception e){
            System.out.println("error" + e.getMessage());
        }
        return bookList;
    }
    public void clearTextField(){
        id.setText("");
        tittle.setText("");
        author.setText("");
        year.setText("");
        pages.setText("");
        errorMessages.setText("");
    }
    public void showBook() {
        ObservableList<Book> list = getBooks();
        t_id.setCellValueFactory(new PropertyValueFactory<Book,Integer>("id"));
        t_tittle.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
        t_author.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
        t_year.setCellValueFactory(new PropertyValueFactory<Book,Integer>("year"));
        t_pages.setCellValueFactory(new PropertyValueFactory<Book,Integer>("pages"));

        book_table.setItems(list);
    }

    private void insertBook() {
        Connection con = GetConnection.getConnection();
        String bookId,bookTitle,bookAuthor,bookYear,bookPages;
        bookId = id.getText();
        bookTitle = tittle.getText();
        bookAuthor = author.getText();
        bookYear = year.getText();
        bookPages = pages.getText();

        if(bookId.isEmpty() || bookTitle.isEmpty() || bookAuthor.isEmpty() || bookYear.isEmpty() || bookPages.isEmpty()){
            errorMessages.setText("Please fill all the fields");
            return;
        }

        String insertQuery = "INSERT INTO book (id, tittle, author, year, pages) VALUES (?, ?, ?, ?, ?)";

        try{
            PreparedStatement ps = con.prepareStatement(insertQuery);
            ps.setString(1,bookId);
            ps.setString(2,bookTitle);
            ps.setString(3,bookAuthor);
            ps.setString(4,bookYear);
            ps.setString(5,bookPages);
            ps.executeUpdate();
            sucessMessage.setText("Book Inserted Successfully");
            clearTextField();
        }catch (Exception e){
            System.out.println("error" + e.getMessage());
        }
        showBook();
        clearTextField();
    }

    private void updateBook() {
        Connection con = GetConnection.getConnection();
        String bookId,bookTitle,bookAuthor,bookYear,bookPages;
        bookId = id.getText();
        bookTitle = tittle.getText();
        bookAuthor = author.getText();
        bookYear = year.getText();
        bookPages = pages.getText();

        if(bookId.isEmpty()){
            errorMessages.setText("Please Enter Book ID");
            return;
        }

        String updateQuery = "UPDATE book SET tittle = ?, author = ?, year = ?, pages = ? WHERE id = ?";

        try{
            PreparedStatement ps = con.prepareStatement(updateQuery);
            ps.setString(1,bookTitle);
            ps.setString(2,bookAuthor);
            ps.setString(3,bookYear);
            ps.setString(4,bookPages);
            ps.setString(5,bookId);
            int rowAffected = ps.executeUpdate();
            if(rowAffected == 1){
                sucessMessage.setText("Book Updated Successfully");
                clearTextField();
            }else{
                errorMessages.setText("No book find with the given id");
            }
        }catch (Exception e){
            System.out.println("error" + e.getMessage());
        }
        showBook();
        clearTextField();
    }

    private void deleteBook() {
        Connection con = GetConnection.getConnection();
        String deleteId = id.getText();
        if(deleteId.isEmpty()){
            errorMessages.setText("Please Enter Book ID");
            return;
        }
       // String deleteId = JOptionPane.showInputDialog(null,"Enter Delete Id","Delete Book",JOptionPane.PLAIN_MESSAGE);
        String deleteQuery = "DELETE FROM book WHERE id = ?";
        try{
            PreparedStatement ps = con.prepareStatement(deleteQuery);
            ps.setString(1,deleteId);
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0){
                sucessMessage.setText("Book Deleted Successfully");
                clearTextField();
            }else{
                errorMessages.setText("No book found given id");
            }
        } catch (Exception e){
            System.out.println("error" + e.getMessage());
        }
        showBook();
        clearTextField();
    }
}
