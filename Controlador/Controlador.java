/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Operacion;
import Vista.Vista;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedList;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

public class Controlador {

    static Vista ventana;
    /**
     * esta variable se encarga de decir si a la hora de meter un numero es
     * necesario borrar el texto o no (explicado mejor en la documentacion
     * Problemas de desarrollo)
     */
    // la pongo estatica porque la uso en un metodo statico
    static Boolean borrar = false;
    static Boolean borrarDespuesIgual = false;

    public Controlador() {

        ventana = new Vista();

        //Action listener de los numeros
        ventana.btn0.addActionListener((ActionEvent e) -> {
            meterNumero(0);
        });
        ventana.btn1.addActionListener((ActionEvent e) -> {
            meterNumero(1);
        });
        ventana.btn2.addActionListener((ActionEvent e) -> {
            meterNumero(2);
        });
        ventana.btn3.addActionListener((ActionEvent e) -> {
            meterNumero(3);
        });
        ventana.btn4.addActionListener((ActionEvent e) -> {
            meterNumero(4);
        });
        ventana.btn5.addActionListener((ActionEvent e) -> {
            meterNumero(5);
        });
        ventana.btn6.addActionListener((ActionEvent e) -> {
            meterNumero(6);
        });
        ventana.btn7.addActionListener((ActionEvent e) -> {
            meterNumero(7);
        });
        ventana.btn8.addActionListener((ActionEvent e) -> {
            meterNumero(8);
        });
        ventana.btn9.addActionListener((ActionEvent e) -> {
            meterNumero(9);
        });

        //Action listener de operaciones
        ventana.btnSumar.addActionListener((ActionEvent e) -> {
            hacerOperacion("+");
        });
        ventana.btnRestar.addActionListener((ActionEvent e) -> {
            hacerOperacion("-");
        });
        ventana.btnMultiplicar.addActionListener((ActionEvent e) -> {
            hacerOperacion("x");
        });
        ventana.btnDividir.addActionListener((ActionEvent e) -> {
            hacerOperacion("÷");
        });
        ventana.btnIgual.addActionListener((ActionEvent e) -> {
            hacerOperacion(null);
        });
        ventana.btnPorcentaje.addActionListener((ActionEvent e) -> {
            porcentaje();
        });
        ventana.btnMasMenos.addActionListener((ActionEvent e) -> {
            cambiarSigno();
        });

        //coma
        ventana.btncoma.addActionListener((ActionEvent e) -> {
            meterComa();
        });

        // Borrar si esta true el metodo borra todo
        ventana.btnBorrarTodo.addActionListener((ActionEvent e) -> {
            borrar(true);
        });
        ventana.btnBorrarUno.addActionListener((ActionEvent e) -> {
            borrar(false);
        });

        // Borrar Historial
        ventana.btnBorrarHistorial.addActionListener((ActionEvent e) -> {
            Operacion.borrarCeldas();
            anyadirOperacionesTabla();
        });
        //menu
        ventana.JMIDisenyoStandard.addActionListener((ActionEvent e) -> {
            disenyo("standard");
        });
        ventana.JMIDisenyoOscuro.addActionListener((ActionEvent e) -> {
            disenyo("black");
        });
        ventana.JMIDisenyoClaro.addActionListener((ActionEvent e) -> {
            disenyo("white");
        });
        ventana.JMIDisenyoCreem.addActionListener((ActionEvent e) -> {
            disenyo("creem");
        });

        ventana.jMenuSalir.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

    }

    /**
     * Los botones llaman a este método para meter un numero en el lblnumero
     * Mira si es 0 para no poner un 0 de primer numero Mira si es el número de
     * la operación anterior ya que al pulsar una operación no te borra el
     * número hasta que hagas click en otro número (utilizando la variable
     * booleana borrar)
     *
     * @param num El número que meteremos en lblNumero
     *
     */
    public static void meterNumero(int num) {
        String NumeroCompleto = ventana.lblNumero.getText();

        //si mide mas de 10 no meterá mas números ya que sino sale ...
        if (ventana.lblNumero.getText().length() < 10 || borrar) {

            // Miro si es 0 para no poner un 0 de primer numero
            if (NumeroCompleto.equals("0")) {
                ventana.lblNumero.setText(num + "");
            } // Miro si es el numero de la operacion anterior ya que al pulsar una operacion no te borra el numero hasta que hagas clik en otro numero
            else if ((obtenerNumerosLblCalcular() == obtenerNumerosLblNumero() && borrar == true) || (ventana.lblCalculo.getText().contains("=") && borrar == true)) {
                ventana.lblNumero.setText(num + "");
                if (borrarDespuesIgual) {
                    ventana.lblCalculo.setText("");
                    borrarDespuesIgual = false;
                } //si es true borra el lblcalculo y pasa a false
                borrar = false;
            } else {
                ventana.lblNumero.setText(NumeroCompleto + num);
            }
        }
        // posicionamos el pulsero en el txt para que funcione el keyListener
        ventana.JTKeyListener.requestFocus();
    }

    /**
     * Primero mirará si hay algo en el lblCalculo. Si este está vacío lo meterá
     * ahí con el signo y se acabará . Si ese tiene algo lo cogerá con su signo
     * y llamará al método calcular pasándole los datos pertinentes. Una vez
     * tenga el resultado lo mete en el lblCalculo con el signo de la siguiente
     * operación y en el lbl Número el número. Después de esto llama al método
     * guardar(String operacion, double resultado) que lo guardara en la base de
     * datos
     *
     * @param operacion
     */
    public static void hacerOperacion(String operacion) {

        //Variables:
        // lcalculo tiene el lblcalculo donde esta las operaciones 45 x
        // lnumero tiene el lblnumero dodne esta el numero por el que se va a operar 5
        String lcalculo, lnumero;
        Double numero1, numero2;

        lcalculo = ventana.lblCalculo.getText();
        lnumero = ventana.lblNumero.getText();

        // si esta vacio metera el lblnumero + es signo de operacion en lbl calculo
        if (lcalculo.isEmpty()) {
            ventana.lblCalculo.setText(lnumero + " " + operacion);
            borrar = true;

            // entra si se a hecho una operacion anteriormente
        } else if (borrar) {
            //Entra si se ha pulsado = anteriormente y si se a vuelto a pulsar =
            String op = ventana.lblCalculo.getText();
            if (operacion == null && op.substring(op.length() - 1, op.length()).equals("=")) {

                numero1 = obtenerNumerosLblNumero();
                //quito el " ="
                op = op.substring(0, op.indexOf("=") - 1);
                numero2 = Double.valueOf(op.substring(op.lastIndexOf(" "), op.length()));

                // evitar duplicidad de codigo, hace todo el proceso de meter los numeros = ya que son mas largos y de proratear cuando tenga 15.0
                setTextNumeroIgual(numero1, numero2, operacion);
                borrar = true;

            } else {
                //entra cuando se deja una operacion a medias y se pulsa un igual (se calculará por su mismo número como hace la calculadora de windows)
                if (operacion == null) {
                    setTextNumeroIgual(obtenerNumerosLblCalcular(), obtenerNumerosLblCalcular(), null);
                    borrarDespuesIgual = true; //hace que el siguiente numero que pogamos borre la op 
                } //  entra cuando pulsamos un igual y luego otra operacion 5x5=25 x
                else {
                    ventana.lblCalculo.setText(quitar0(obtenerNumerosLblNumero()) + " " + operacion);
                    borrarDespuesIgual = false; //hace que ya no borre la operacion ya que introducimos un numero al lblcalculo
                }
            }
        } else {
            // si no esta vacio hace el calculo de lvlcalculo y lvlnumero con el signo operacion
            numero1 = obtenerNumerosLblCalcular();
            numero2 = obtenerNumerosLblNumero();

            // evitar duplicidad de codigo, hace todo el proceso de meter los numeros = ya que son mas largos y de proratear cuando tenga 15.0
            setTextNumeroIgual(numero1, numero2, operacion);

            borrar = true;
            if (ventana.lblCalculo.getText().contains("=")) {
                borrarDespuesIgual = true;
            }

        }
        // posicionamos el pulsero en el txt para que funcione el keyListener
        ventana.JTKeyListener.requestFocus();
    }

    /**
     * Se encarga de calcular los dos valores que le metas con la operación
     * pertinente "+ - x ÷" Transforma los double en BigDecimal para operar y
     * devuelve un double
     *
     * @param num1
     * @param num2
     * @param operacion
     * @return Devuelve el resultado de la operación
     */
    public static Double calcular(double num1, double num2, String operacion) {
        BigDecimal numero1 = new BigDecimal(num1);
        BigDecimal numero2 = new BigDecimal(num2);
        BigDecimal resultado = new BigDecimal(0);

        // se hace esto ya que no detecta el simbolo ÷
        if (operacion.equals("÷")) {
            operacion = "/";
        }

        switch (operacion) {
            case "+" ->
                resultado = numero1.add(numero2);
            case "-" ->
                resultado = numero1.subtract(numero2);
            case "x" ->
                resultado = numero1.multiply(numero2);
            case "/" ->
                resultado = numero1.divide(numero2, 5, RoundingMode.HALF_UP);
        }

        if (operacion.equals("/")) {
            operacion = "÷";
        }

        //miramos si da 1.54000000000658789454 ya que al poner 0.88 se buguea el big decimal 
        if (numero2.toString().length() > 15) {
            numero2 = numero2.setScale(2, BigDecimal.ROUND_FLOOR);
        }
        if (numero1.toString().length() > 15) {
            numero1 = numero1.setScale(2, BigDecimal.ROUND_FLOOR);
        }
        if (resultado.toString().length() > 15) {
            resultado = resultado.setScale(2, BigDecimal.ROUND_FLOOR);
        }

        //hacemos el insert
        Modelo.Operacion.insertarOperacion(numero1 + " " + operacion + " " + numero2 + " =", Double.parseDouble(resultado.toString()));

        return Double.valueOf(resultado.toString());
    }

    //Metodos para obtener los numeros de la operacion 
    /**
     * este metodo coje número que tiene el lblCalculo y lo devuelve en double
     *
     * @return double si esta vacio devolverá 999999999
     */
    public static double obtenerNumerosLblCalcular() {
        String calculo;
        int espacio;

        calculo = ventana.lblCalculo.getText();
        if (!calculo.isEmpty()) {
            espacio = calculo.indexOf(" ");

            return Double.parseDouble(calculo.substring(0, espacio));
        } else {
            return 999999999;
        }
    }

    /**
     * este metodo cogerá número que tiene el lblnumero
     *
     * @return Double
     */
    public static double obtenerNumerosLblNumero() {

        return Double.parseDouble(ventana.lblNumero.getText());
    }

    /**
     * este metodo cogera la operacion que tiene el lblCalculo
     *
     * @return String con la operación
     */
    public static String obtenerOperacionLblCalcular() {
        String calculo;
        int espacio;

        calculo = ventana.lblCalculo.getText();
        if (!calculo.isEmpty()) {
            espacio = calculo.indexOf(" ");

            return calculo.substring(espacio + 1, espacio + 2);
        } else {
            return null;
        }
    }

    /**
     * Se le pasa un numero y le devuelve un string con el mismo numero si tiene
     * 15.0 pasa a 15 y si tiene 15.5 se queda igual
     *
     * @param num
     * @return String
     */
    public static String quitar0(double num) {

        //cogemos la parte entera del nuemro
        int entero = (int) num; // parte entera 12345
        //cogemos los decimales 
        double decimal = num - entero;// parte decimal 0.6789

        //si tiene decimales devolvemos el numero con decimales y si no lo devolvemos entero
        if (decimal > 0) {
            return num + "";
        } else {
            return entero + "";
        }
    }

    /**
     * Se le pasan los valores al pulsar = y se encarga de hacer la operacion,
     * prorratear los doubles y poner el resultado en los labels
     *
     * @param numero1
     * @param numero2
     * @param op Si va null dice que pone el resultado en el lblcalculo con el =
     * EJ true "4 x 5 =" EJ false "4 x "
     */
    public static void setTextNumeroIgual(double numero1, double numero2, String op) {
        String stringNumero1Prorateado, stringNumero2Prorateado, stringResultadoProrateado;
        Double resultado;

        resultado = calcular(numero1, numero2, obtenerOperacionLblCalcular());

        //pasa de por ej 15.0 ya que es double a 15
        stringNumero1Prorateado = quitar0(numero1);
        stringNumero2Prorateado = quitar0(numero2);
        stringResultadoProrateado = quitar0(resultado);

        // mira si es un = la variable operacion ya que actuara de forma diferente
        if (op == null) {
            ventana.lblCalculo.setText(stringNumero1Prorateado + " " + obtenerOperacionLblCalcular() + " " + stringNumero2Prorateado + " =");
            ventana.lblNumero.setText(stringResultadoProrateado + "");
        } else {
            ventana.lblCalculo.setText(stringResultadoProrateado + " " + op);
            ventana.lblNumero.setText(stringResultadoProrateado + "");
        }
    }

    /**
     * mete coma en el lblNumero si ya tiene coma no meterá la coma
     * mira si es = o si e ha hecho una operacionpara poner directamente 0.
     */
    public static void meterComa() {
        // mira si es = o si e ha hecho una operacionpara poner directamente 0.
        String num = ventana.lblNumero.getText();
        if ((obtenerNumerosLblCalcular() == obtenerNumerosLblNumero() && borrar == true) || (ventana.lblCalculo.getText().contains("=") && borrar == true)) {
            ventana.lblNumero.setText("0.");
            ventana.lblCalculo.setText("");
            borrar = false;
            borrarDespuesIgual = false;
        } else if (num.contains(".")) {
        } else {
            ventana.lblNumero.setText(num + ".");
        }

        // posicionamos el pulsero en el txt para que funcione el keyListener
        ventana.JTKeyListener.requestFocus();
    }

    /**
     * Borra texto. Si la variable borrarTodo es true borrará todo el texto de
     * lblCalculo y de lblNumero Si la variable todo es false borrará de
     * lblCalculo el último numero Si le damos a borrar despues de un igual
     * borrara todo 
     *
     * @param borrarTodo
     */
    public static void borrar(boolean borrarTodo) {
        if (borrarTodo) {
            ventana.lblCalculo.setText("");
            ventana.lblNumero.setText("0");
            borrar=false;
            borrarDespuesIgual=false;
        } else {
            String num = ventana.lblNumero.getText();

            if (num.length() == 2 && num.contains("-")) {
                ventana.lblNumero.setText("0");
            } else {
                ventana.lblNumero.setText(num.substring(0, num.length() - 1));
            }
            //Si despues de borrar es el String es "" pondrá un 0
            if (ventana.lblNumero.getText().equals("")) {
                ventana.lblNumero.setText("0");
            }

            //despues de un igual borramos borrara todo
            if ((obtenerNumerosLblCalcular() == obtenerNumerosLblNumero() && borrar == true) || (ventana.lblCalculo.getText().contains("=") && borrar == true)) {
                ventana.lblNumero.setText("0");
                ventana.lblCalculo.setText("");
            }
        }
        // posicionamos el pulsero en el txt para que funcione el keyListener
        ventana.JTKeyListener.requestFocus();
    }

    /**
     * Saca el porcentaje del número del lblnumero y lo pone por pantalla Si en 
     * el lblcalcular no hay nada se pone un 0 como en el funcionamiento de la 
     * calculadora de windows Se tiene en cuenta el número de decimales para  
     * poner la escala de la división del bigDecimal correctamente porque sino 
     * saldría 0.00250000000000016541654165
     */
    public void porcentaje() {
        Double num = obtenerNumerosLblNumero();
        BigDecimal numb = new BigDecimal(num);
        String lblcalculo = ventana.lblCalculo.getText();

        // mira si el lblcalculo esta vacio o si contiene un = ya que si pasa esto tengo que poner 0 en cada uno como el funcionamineto de la calculadora de windows
        if (lblcalculo.isEmpty() || lblcalculo.contains("=")) {
            ventana.lblNumero.setText("0");
        } else {
            //miramos el numero de decimales para poner la escala de la division del bigDecimal correctamente porque sino saldria 0.00250000000000016541654165
            String decimales;
            String numero = ventana.lblNumero.getText();

            if (numero.contains(".")) {
                decimales = numero.substring(numero.indexOf(".") + 1, numero.length());
            } else {
                decimales = "";
            }

            numb = numb.divide(new BigDecimal(100), decimales.length() + 2, RoundingMode.HALF_UP);

            ventana.lblNumero.setText(numb + "");
        }
        // posicionamos el pulsero en el txt para que funcione el keyListener
        ventana.JTKeyListener.requestFocus();
        borrar = false;
    }

    /**
     * Cambia el signo del lblNumero
     */
    public void cambiarSigno() {
        Double num = obtenerNumerosLblNumero();
        if (num == 0) {
            ventana.lblNumero.setText("0");
        } else if (num > 0) {
            ventana.lblNumero.setText("-" + quitar0(num));
        } else {
            ventana.lblNumero.setText(ventana.lblNumero.getText().substring(1));
        }
        // posicionamos el pulsero en el txt para que funcione el keyListener
        ventana.JTKeyListener.requestFocus();
    }

    /**
     * Carga los datos la base de datos en la tabla
     */
    public static void anyadirOperacionesTabla() {
        LinkedList<Operacion> lista = Modelo.Operacion.selectOperaciones();
        Collections.reverse(lista);
        String[] titulos = {"Operacion", "Resultado"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        for (Operacion op : lista) {
            String[] opj = {op.getOperacion(), quitar0(op.getResultado())};
            modelo.addRow(opj);
        }
        ventana.jTableHistorial.setModel(modelo);

        // posicionamos el pulsero en el txt para que funcione el keyListener
        ventana.JTKeyListener.requestFocus();
    }
    
    /**
     * Pone los datos de la línea seleccionada en el lblCalculo y el lblNumero
     */
    public static void cogerDatoDelHistorial() {
        DefaultTableModel modelo = (DefaultTableModel) ventana.jTableHistorial.getModel();

        //Cojo la primera columna
        String colum1 = String.valueOf(modelo.getValueAt(ventana.jTableHistorial.getSelectedRow(), 0));
        ventana.lblCalculo.setText(colum1);

        String colum2 = String.valueOf(modelo.getValueAt(ventana.jTableHistorial.getSelectedRow(), 1));
        ventana.lblNumero.setText(colum2);

        borrar = true;
    }

    /**
     * Recibe un parámetro, según este parámetro establecerá un diseño cambiando
     * el UIManager.setLookAndFeel y aplicando unos cambios manualmente
     *
     * @param nombre es un string donde le dice el nombre del diseño que quieres
     * y lo pone en todo el Frame
     */
    public static void disenyo(String nombre) {

        String look = "";
        // select the Look and Feel
        try {
            switch (nombre) {
                case "standard" -> {
                    look = "com.jtattoo.plaf.graphite.GraphiteLookAndFeel";
                    colorFondo(new Color(250, 250, 250), Color.black, new Color(102, 102, 102), new Color(51, 102, 255));

                }
                case "black" -> {
                    look = "com.jtattoo.plaf.hifi.HiFiLookAndFeel";
                    colorFondo(new Color(66, 73, 73), Color.white, new Color(236, 240, 241), new Color(211, 84, 0));

                }
                case "white" -> {
                    look = "com.jtattoo.plaf.fast.FastLookAndFeel";
                    colorFondo(new Color(250, 250, 250), Color.black, new Color(102, 102, 102), new Color(174, 214, 241));

                }
                case "creem" -> {
                    look = "com.jtattoo.plaf.smart.SmartLookAndFeel";
                    colorFondo(new Color(254, 249, 231), Color.black, new Color(102, 102, 102), new Color(174, 214, 241));
                }

            }

            UIManager.setLookAndFeel(look);

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        ventana.revalidate();
        ventana.repaint();

        // posicionamos el pulsero en el txt para que funcione el keyListener
        ventana.JTKeyListener.requestFocus();
    }

    public static void colorFondo(Color colorFondo, Color colorlblNumero, Color colorlblCalculo, Color colorBtnigual) {
        ventana.getContentPane().setBackground(colorFondo);
        ventana.panelCalculo.setBackground(colorFondo);
        ventana.panelHistorial.setBackground(colorFondo);
        ventana.lblNumero.setForeground(colorlblNumero);
        ventana.lblCalculo.setForeground(colorlblCalculo);
        ventana.btnIgual.setBackground(colorBtnigual);
    }
}
