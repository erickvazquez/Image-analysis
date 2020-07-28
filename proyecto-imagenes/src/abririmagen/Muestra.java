
package abririmagen;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Muestra extends javax.swing.JFrame {
    Operaciones ai=new Operaciones();
    //este arraylist nos permite guardar cada cambio que se le hace a la imagen abierta
    ArrayList<BufferedImage> deshacer= new ArrayList<BufferedImage>();
    
    ventana nueva;
    public Muestra() {
        
        this.setTitle("Aplicacion Analisis de imagenes RMMD");
        initComponents();
        jSlider1.setValue(0);
        jSlider2.setValue(128);
        jButton4.setEnabled(false);
        jButton5.setEnabled(false);
        buttonGroup1.add(jRadioButton1);
        buttonGroup1.add(jRadioButton2);
        jRadioButton1.setSelected(true);
        //jToolBar1.setVisible(false);
        //jToolBar2.setVisible(false);
        //jToolBar3.setVisible(false);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jToolBar1 = new javax.swing.JToolBar();
        jPanel2 = new javax.swing.JPanel();
        promedio = new javax.swing.JButton();
        prompesado = new javax.swing.JButton();
        gaussiano = new javax.swing.JButton();
        mediana = new javax.swing.JButton();
        minimo = new javax.swing.JButton();
        maximo = new javax.swing.JButton();
        moda = new javax.swing.JButton();
        sobel = new javax.swing.JButton();
        prewitt = new javax.swing.JButton();
        robert = new javax.swing.JButton();
        laplaciano = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jSlider2 = new javax.swing.JSlider();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jButton5 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        GuardarImagen = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 999, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 444, Short.MAX_VALUE)
        );

        promedio.setText("Promedio");
        promedio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                promedioActionPerformed(evt);
            }
        });

        prompesado.setText("promedio pesado");
        prompesado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prompesadoActionPerformed(evt);
            }
        });

        gaussiano.setText("Gaussiano");
        gaussiano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gaussianoActionPerformed(evt);
            }
        });

        mediana.setText("Mediana");
        mediana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medianaActionPerformed(evt);
            }
        });

        minimo.setText("Minimo");
        minimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minimoActionPerformed(evt);
            }
        });

        maximo.setText("Maximo");
        maximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maximoActionPerformed(evt);
            }
        });

        moda.setText("Moda");
        moda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modaActionPerformed(evt);
            }
        });

        sobel.setText("Sobel");
        sobel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sobelActionPerformed(evt);
            }
        });

        prewitt.setText("Prewitt");
        prewitt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prewittActionPerformed(evt);
            }
        });

        robert.setText("Robert");
        robert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                robertActionPerformed(evt);
            }
        });

        laplaciano.setText("Laplaciano");
        laplaciano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laplacianoActionPerformed(evt);
            }
        });

        jButton3.setText("Negativo");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setText("Escala Grises");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Histograma");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton6.setText("Descomponer");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel2.setText("Brillo: 0");

        jSlider1.setMinimum(-100);
        jSlider1.setValue(0);
        jSlider1.setMaximumSize(new java.awt.Dimension(100, 26));
        jSlider1.setPreferredSize(new java.awt.Dimension(100, 26));
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jButton4.setText("Seleccionar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel3.setText("Umbra: 120");

        jSlider2.setMaximum(255);
        jSlider2.setValue(128);
        jSlider2.setMaximumSize(new java.awt.Dimension(100, 26));
        jSlider2.setMinimumSize(new java.awt.Dimension(100, 26));
        jSlider2.setPreferredSize(new java.awt.Dimension(100, 26));
        jSlider2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider2StateChanged(evt);
            }
        });

        jRadioButton1.setText("Global");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setText("Global inversa");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jButton5.setText("Binarizar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        GuardarImagen.setText("Archivo");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Abrir Imagen");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        GuardarImagen.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Salir");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        GuardarImagen.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Guardar Imagen");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        GuardarImagen.add(jMenuItem3);

        jMenuBar1.add(GuardarImagen);

        jMenu3.setText("Operaciones Puntuales");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        jMenu5.setText("Rehacer");
        jMenu5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu5MouseClicked(evt);
            }
        });
        jMenu5.addMenuKeyListener(new javax.swing.event.MenuKeyListener() {
            public void menuKeyPressed(javax.swing.event.MenuKeyEvent evt) {
                jMenu5MenuKeyPressed(evt);
            }
            public void menuKeyReleased(javax.swing.event.MenuKeyEvent evt) {
            }
            public void menuKeyTyped(javax.swing.event.MenuKeyEvent evt) {
            }
        });
        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(promedio)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(prompesado)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(gaussiano)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(mediana)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(minimo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(maximo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(moda)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(sobel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(prewitt)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(robert)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(laplaciano))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRadioButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRadioButton2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton5)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jButton2)
                            .addComponent(jButton1))
                        .addComponent(jButton6)
                        .addComponent(jLabel2))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSlider1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton4)
                                .addComponent(jLabel3))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jRadioButton1)
                                .addComponent(jRadioButton2)
                                .addComponent(jButton5)))))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(promedio)
                    .addComponent(prompesado)
                    .addComponent(gaussiano)
                    .addComponent(mediana)
                    .addComponent(minimo)
                    .addComponent(maximo)
                    .addComponent(moda)
                    .addComponent(sobel)
                    .addComponent(prewitt)
                    .addComponent(robert)
                    .addComponent(laplaciano))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //agregamos al arraylist la imagen antes del cambio
        ai.respaldo();
        deshacer.add(ai.getRespaldo());
        //ya tenemos la direeccion a la imagen, por lo tanto podemos emplearla directamente
        BufferedImage imagen=ai.escalaGrises();
        ImageIcon ico=new ImageIcon(imagen);
        //jLabel1.setIcon(ico);
        nueva.setImagen(imagen);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //agregamos al arraylist la imagen antes del cambio
        ai.respaldo();
        deshacer.add(ai.getRespaldo());
        BufferedImage imagen=ai.negativo();
        ImageIcon ico=new ImageIcon(imagen);
        //jLabel1.setIcon(ico);
        nueva.setImagen(imagen);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        int brillo=jSlider1.getValue();
        ai.respaldo();
        System.out.println("Brillo: "+brillo);
        jLabel2.setText("Brillo: "+brillo);
        BufferedImage imagen=ai.brillo(brillo);
        ImageIcon ico=new ImageIcon(imagen);
        //jLabel1.setIcon(ico);
        nueva.setImagen(imagen);
        jButton4.setEnabled(true);
    }//GEN-LAST:event_jSlider1StateChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //agregamos al arraylist la imagen antes del cambio
        //ai.respaldo();
        deshacer.add(ai.getImagen());
        
        //cuando se presiona el boton lo que se encuentra en la variable respaldo se guarda 
        //en la variable imageActual
        ai.guardar_respaldo();
        jSlider1.setValue(0);
        jButton4.setEnabled(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        //cuando se abre una imagen se borran todos los cambios en arraylist deshacer
        deshacer.clear();
        //abrimos la imagen
        BufferedImage imagen=ai.abrirImagen();
        
        ImageIcon ico=new ImageIcon(imagen);
        //jLabel1.setSize(imagen.getWidth(), imagen.getHeight());
        System.out.println("alto:"+imagen.getHeight());
         System.out.println("ancho:"+imagen.getWidth());
         //agregamos imagen a jlabel
        //jLabel1.setIcon(ico);
        nueva=new ventana(imagen);
        jPanel2.add(nueva);
        nueva.show();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Histograma h=new Histograma(ai.getImagen());
        h.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jSlider2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider2StateChanged
        int umbral=jSlider2.getValue();
        //creamos la imagen respaldo
        
        ai.respaldo();
        System.out.println("Umbral: "+umbral);
        jLabel3.setText("Umbral: "+umbral);
        BufferedImage imagen;
        //aplicamos la binarizacion sobre la imagen respaldo
        if(jRadioButton1.isSelected())
            imagen=ai.binarizar(umbral);
        else
            imagen=ai.binarizar_inverso(umbral);
        //mostramos la imagen respaldo en pantalla
        ImageIcon ico=new ImageIcon(imagen);
        //jLabel1.setIcon(ico);
        nueva.setImagen(imagen);
        jButton5.setEnabled(true);
    }//GEN-LAST:event_jSlider2StateChanged

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        //agregamos al arraylist la imagen antes del cambio
        ai.respaldo();
        deshacer.add(ai.getRespaldo());
        
        //cuando se presiona el boton lo que se encuentra en la variable respaldo se guarda 
        //en la variable imageActual
        ai.guardar_respaldo();
        jSlider2.setValue(128);
        jButton5.setEnabled(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
        System.out.println("Operaciones puntuales");
       OperacionPuntual op=new OperacionPuntual();
        op.setVisible(true);
        jToolBar1.setVisible(false);
    }//GEN-LAST:event_jMenu3MouseClicked
//accion de boton para descomponer en RGB una imagen
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       BufferedImage rojo,verde,azul;
       if(ai!=null){
       rojo=ai.getRedImage();
       verde=ai.getGreenImage();
       azul=ai.getBlueImage();
       ventana vrojo=new ventana(rojo);
       ventana vazul=new ventana(azul);
       ventana vverde=new ventana(verde);
       jPanel2.add(vrojo);
       jPanel2.add(vverde);
       jPanel2.add(vazul);
       vrojo.setLocation(10, 10);
       vrojo.show();
       vverde.setLocation(50, 50);
       vverde.show();
       vazul.setLocation(90, 90);
       vazul.show();
       }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu5MouseClicked
        System.out.println("Tamaño de pila: "+deshacer.size());
        if(deshacer.size()>0){
        ai.setImage(deshacer.get(deshacer.size()-1));
        nueva.setImagen(ai.getImagen());
        deshacer.remove(deshacer.size()-1);
        }else
            JOptionPane.showMessageDialog(null, "No hay cambios recientes");
    }//GEN-LAST:event_jMenu5MouseClicked

    private void jMenu5MenuKeyPressed(javax.swing.event.MenuKeyEvent evt) {//GEN-FIRST:event_jMenu5MenuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu5MenuKeyPressed

    private void promedioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_promedioActionPerformed
        // TODO add your handling code here:
         //agregamos al arraylist la imagen antes del cambio
        ai.respaldo();
        deshacer.add(ai.getRespaldo());
        BufferedImage imagen=ai.filtroPromedio();
        ImageIcon ico=new ImageIcon(imagen);
        //jLabel1.setIcon(ico);
        nueva.setImagen(imagen);
    }//GEN-LAST:event_promedioActionPerformed

    private void maximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maximoActionPerformed
        // TODO add your handling code here:
        ai.respaldo();
        deshacer.add(ai.getRespaldo());
        BufferedImage imagen=ai.filtroMaximo();
        ImageIcon ico=new ImageIcon(imagen);
        //jLabel1.setIcon(ico);
        nueva.setImagen(imagen);
    }//GEN-LAST:event_maximoActionPerformed

    private void prompesadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prompesadoActionPerformed
        // TODO add your handling code here:
        ai.respaldo();
        deshacer.add(ai.getRespaldo());
        BufferedImage imagen=ai.filtroPromedioPesado();
        ImageIcon ico=new ImageIcon(imagen);
        //jLabel1.setIcon(ico);
        nueva.setImagen(imagen);
    }//GEN-LAST:event_prompesadoActionPerformed

    private void gaussianoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gaussianoActionPerformed
        // TODO add your handling code here:
        ai.respaldo();
        deshacer.add(ai.getRespaldo());
        BufferedImage imagen=ai.filtroGaussiano();
        ImageIcon ico=new ImageIcon(imagen);
        //jLabel1.setIcon(ico);
        nueva.setImagen(imagen);
    }//GEN-LAST:event_gaussianoActionPerformed

    private void medianaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medianaActionPerformed
        // TODO add your handling code here:
        ai.respaldo();
        deshacer.add(ai.getRespaldo());
        BufferedImage imagen=ai.filtroMediana();
        ImageIcon ico=new ImageIcon(imagen);
        //jLabel1.setIcon(ico);
        nueva.setImagen(imagen);
    }//GEN-LAST:event_medianaActionPerformed

    private void minimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minimoActionPerformed
        // TODO add your handling code here:
        ai.respaldo();
        deshacer.add(ai.getRespaldo());
        BufferedImage imagen=ai.filtroMinimo();
        ImageIcon ico=new ImageIcon(imagen);
        //jLabel1.setIcon(ico);
        nueva.setImagen(imagen);
    }//GEN-LAST:event_minimoActionPerformed

    private void modaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modaActionPerformed
        // TODO add your handling code here:
        ai.respaldo();
        deshacer.add(ai.getRespaldo());
        BufferedImage imagen=ai.filtroModa();
        ImageIcon ico=new ImageIcon(imagen);
        //jLabel1.setIcon(ico);
        nueva.setImagen(imagen);
    }//GEN-LAST:event_modaActionPerformed

    private void sobelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sobelActionPerformed
        // TODO add your handling code here:
        ai.respaldo();
        deshacer.add(ai.getRespaldo());
        BufferedImage imagen=ai.filtroSobel();
        ImageIcon ico=new ImageIcon(imagen);
        //jLabel1.setIcon(ico);
        nueva.setImagen(imagen);
    }//GEN-LAST:event_sobelActionPerformed

    private void prewittActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prewittActionPerformed
        // TODO add your handling code here:
        ai.respaldo();
        deshacer.add(ai.getRespaldo());
        BufferedImage imagen=ai.filtroPrewitt();
        ImageIcon ico=new ImageIcon(imagen);
        //jLabel1.setIcon(ico);
        nueva.setImagen(imagen);
    }//GEN-LAST:event_prewittActionPerformed

    private void robertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_robertActionPerformed
        // TODO add your handling code here:
        ai.respaldo();
        deshacer.add(ai.getRespaldo());
        BufferedImage imagen=ai.filtroRobert();
        ImageIcon ico=new ImageIcon(imagen);
        //jLabel1.setIcon(ico);
        nueva.setImagen(imagen);
    }//GEN-LAST:event_robertActionPerformed

    private void laplacianoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laplacianoActionPerformed
        // TODO add your handling code here:
        ai.respaldo();
        deshacer.add(ai.getRespaldo());
        BufferedImage imagen=ai.filtroLaplaciano();
        ImageIcon ico=new ImageIcon(imagen);
        //jLabel1.setIcon(ico);
        nueva.setImagen(imagen);
    }//GEN-LAST:event_laplacianoActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        try {
            // TODO add your handling code here:
            ai.GuardarImagen();
                    } catch (IOException ex) {
            Logger.getLogger(Muestra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Muestra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Muestra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Muestra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Muestra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Muestra().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu GuardarImagen;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton gaussiano;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSlider jSlider2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton laplaciano;
    private javax.swing.JButton maximo;
    private javax.swing.JButton mediana;
    private javax.swing.JButton minimo;
    private javax.swing.JButton moda;
    private javax.swing.JButton prewitt;
    private javax.swing.JButton promedio;
    private javax.swing.JButton prompesado;
    private javax.swing.JButton robert;
    private javax.swing.JButton sobel;
    // End of variables declaration//GEN-END:variables
}
