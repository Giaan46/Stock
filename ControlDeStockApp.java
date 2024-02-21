package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ControlDeStockApp extends Application {
    private TableView<Product> tablaProductos;
    private ObservableList<Product> productos;
    private TextField campoIdentificador;
    private TextField campoNombreProducto;
    private TextField campoStock;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Crear la tabla de productos
        tablaProductos = new TableView<>();
        TableColumn<Product, Integer> columnaIdentificador = new TableColumn<>("Identificador");
        columnaIdentificador.setCellValueFactory(new PropertyValueFactory<>("identificador"));
        TableColumn<Product, String> columnaNombreProducto = new TableColumn<>("Nombre Producto");
        columnaNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<Product, Integer> columnaStock = new TableColumn<>("Stock");
        columnaStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tablaProductos.getColumns().addAll(columnaIdentificador, columnaNombreProducto, columnaStock);

        // Crear los campos de entrada y los botones
        campoIdentificador = new TextField();
        campoIdentificador.setPromptText("Identificador");
        campoNombreProducto = new TextField();
        campoNombreProducto.setPromptText("Nombre Producto");
        campoStock = new TextField();
        campoStock.setPromptText("Stock");
        Button botonAgregar = new Button("Agregar");
        botonAgregar.setOnAction(e -> agregarProducto());
        Button botonModificar = new Button("Modificar");
        botonModificar.setOnAction(e -> modificarProducto());

        // Crear el contenedor para los campos de entrada y los botones
        GridPane contenedorCampos = new GridPane();
        contenedorCampos.setHgap(10);
        contenedorCampos.setVgap(50);
        contenedorCampos.addRow(0, new Label("Identificador:"), campoIdentificador);
        contenedorCampos.addRow(1, new Label("Nombre Producto:"), campoNombreProducto);
        contenedorCampos.addRow(2, new Label("Stock:"), campoStock);
        contenedorCampos.addRow(3, botonAgregar, botonModificar);
        contenedorCampos.setAlignment(Pos.CENTER);
        contenedorCampos.setPadding(new Insets(10));

        Insets margen = new Insets(50);
        GridPane.setMargin(contenedorCampos,margen);
        // Crear el contenedor principal
        BorderPane contenedorPrincipal = new BorderPane();
        contenedorPrincipal.setPadding(new Insets(20));
        contenedorPrincipal.setLeft(contenedorCampos);
        contenedorPrincipal.setRight(tablaProductos);
        BorderPane.setAlignment(contenedorCampos, Pos.CENTER);
        BorderPane.setAlignment(tablaProductos, Pos.CENTER);

        // Crear la escena
        Scene scene = new Scene(contenedorPrincipal, 600, 400);

        // Configurar la ventana principal
        primaryStage.setTitle("Control de Stock");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Inicializar la lista de productos
        productos = FXCollections.observableArrayList(
                new Product(1, "yerba mate", 10),
                new Product(2, "taza de cafe", 5),
                new Product(3, "pendrive ", 2)
        );
        tablaProductos.setItems(productos);
    }

    private void agregarProducto() {
        int identificador = Integer.parseInt(campoIdentificador.getText());
        String nombre = campoNombreProducto.getText();
        int stock = Integer.parseInt(campoStock.getText());
        Product producto = new Product(identificador, nombre, stock);
        productos.add(producto);
        limpiarCampos();
    }

    private void modificarProducto() {
        Product productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            int identificador = Integer.parseInt(campoIdentificador.getText());
            String nombre = campoNombreProducto.getText();
            int stock = Integer.parseInt(campoStock.getText());
            productoSeleccionado.setIdentificador(identificador);
            productoSeleccionado.setNombre(nombre);
            productoSeleccionado.setStock(stock);
            tablaProductos.refresh();
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        campoIdentificador.clear();
        campoNombreProducto.clear();
        campoStock.clear();
    }
}