package abririmagen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
 
public class Operaciones {
    //Imagen actual que se ha cargado
    private BufferedImage imageActual;
    private BufferedImage respaldo;
    private int abrio=0;
    /*******************************************************************************************************/
    /*                              Operaciones basicas de la imagen                                       */
    /*******************************************************************************************************/
    public BufferedImage getImagen(){
        return imageActual;
    }
    public void setImage(BufferedImage s){
        imageActual=s;
    }
    public int getAbrio(){
        return abrio;
    }
    //Método que devuelve una imagen abierta desde archivo
    //Retorna un objeto BufferedImagen
    public BufferedImage abrirImagen(){
        //Creamos la variable que será devuelta (la creamos como null)
        BufferedImage bmp=null;
        //Creamos un nuevo cuadro de diálogo para seleccionar imagen
        JFileChooser selector=new JFileChooser();
        //Le damos un título
        selector.setDialogTitle("Seleccione una imagen");
        //Filtramos los tipos de archivos
        FileNameExtensionFilter tipo_imagen = new FileNameExtensionFilter("JPG & GIF & BMP & TIF", "jpg", "gif", "bmp","tif");
        selector.setFileFilter(tipo_imagen);
        //Abrimos ventana de seleccion
        int flag=selector.showOpenDialog(null);
        //si se pulsa aceptar
        if(flag==JFileChooser.APPROVE_OPTION){
            try {
                //Devuelve el fichero seleccionado
                File imagenSeleccionada=selector.getSelectedFile();
                //Asignamos a la variable bmp la imagen leida
                bmp = ImageIO.read(imagenSeleccionada);
            } catch (Exception e) {
            }
                  
        }
        //Asignamos la imagen cargada a la propiedad imageActual
        if(bmp!=null){
        imageActual=bmp;
        abrio=1;
        }
        else
            abrio=0;
        //Retornamos el valor
        return imageActual;
    }
    //Este metodo permite convertir una imagen a escala de grises
    //devuelve una imagen
    
    public BufferedImage GuardarImagen() throws IOException{  
   ImageIO.write(imageActual, "jpg", new File("foto.jpg"));
        return null;
    }
    
    public BufferedImage escalaGrises(){
        //Variables que almacenarán los píxeles
        int promedio,colorSRGB;
        Color colorAux;    
        //Recorremos la imagen píxel a píxel
        System.out.println("Ancho: "+imageActual.getWidth());
        System.out.println("Alto:"+imageActual.getHeight());
        for( int y = 0; y < imageActual.getHeight(); y++ ){
            for( int x = 0; x < imageActual.getWidth(); x++ ){
                //System.out.print("["+y+","+x+"] ");
                //Almacenamos el color del píxel
                colorAux=new Color(this.imageActual.getRGB(x,y));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                promedio=(int)((colorAux.getRed()+colorAux.getGreen()+colorAux.getBlue())/3);
                //Cambiamos a formato sRGB
                colorSRGB=(promedio << 16) | (promedio << 8) | promedio;
                //Asignamos el nuevo valor al BufferedImage
                imageActual.setRGB(x, y,colorSRGB);
            }
            //System.out.println("");
        }
        //Retornamos la imagen
        return imageActual;
    }
    //Este metodo permite convertir una imagen a su negativo
    public BufferedImage negativo(){
        int R,G,B,nR,nG,nB;
        int colorSRGB;
        Color colorAux = null;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
               // System.out.print("["+y+","+x+"] ");
                    colorAux=new Color(imageActual.getRGB(x,y));
                    //obtenemos los valores RGB del pixel
                    R=(int)colorAux.getRed();
                    G=(int)colorAux.getGreen();
                    B=(int)colorAux.getBlue();
                    //obtenemos el negativo para cada RGB
                    nR=255-R;
                    nG=255-G;
                    nB=255-B;
                    colorSRGB=(nR<<16)|(nG<<8)|(nB);
                   imageActual.setRGB(x,y,colorSRGB);
            }
            //System.out.println();
        }
        return imageActual;
    }
    public void respaldo(){
        int R,G,B,nR,nG,nB;
        int colorSRGB;
        Color colorAux = null;
        BufferedImage nueva;
        nueva = new BufferedImage(imageActual.getWidth(),imageActual.getHeight(),imageActual.getType());
        respaldo=nueva;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                colorAux=new Color(imageActual.getRGB(x,y));
                 R=(int)colorAux.getRed();
                 G=(int)colorAux.getGreen();
                 B=(int)colorAux.getBlue();
                 //sumamos el brillo a cada valor
                 colorSRGB=(R<<16)|(G<<8)|B;
                 respaldo.setRGB(x,y,colorSRGB);
            }
        }
    }
    //ESte metodo guarda en la imagen el respaldo
    public void guardar_respaldo(){
        imageActual=respaldo;
    }
    //este metodo nos regresa el respaldo
    public BufferedImage getRespaldo(){
        return respaldo;
    }
    //Metodo que nos sirve para contar la cantidad de RGB presentes
    //en la imagen
    public int[][] histograma(BufferedImage imagen){
        Color colorAuxiliar;
        /*Creamos la variable que contendrá el histograma
        El primer campo [0], almacenará el histograma Rojo
        [1]=verde [2]=azul*/
        int histogramaReturn[][]=new int[3][256];
        //Recorremos la imagen
        for( int i = 0; i < imagen.getWidth(); i++ ){
            for( int j = 0; j < imagen.getHeight(); j++ ){
                //Obtenemos color del píxel actual
                colorAuxiliar=new Color(imagen.getRGB(i, j));
                //Sumamos una unidad en la fila roja [0], 
                //en la columna del color rojo obtenido
                histogramaReturn[0][colorAuxiliar.getRed()]+=1;
                histogramaReturn[1][colorAuxiliar.getGreen()]+=1;
                histogramaReturn[2][colorAuxiliar.getBlue()]+=1;
            }
        }
        return histogramaReturn;
    }
    //este metodo permite binarizar una imagen por medio de un umbral
    //para el brillo y la binarizacion lo que hago es guardar la imagen original en una variable de respaldo
    //las operaciones se realizan sobre la variable respaldo y no sobre imageActual
    public BufferedImage binarizar(int umbral){
        this.escalaGrises();
        int R,G,B;
        int colorSRGB;
        Color colorAux;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                colorAux=new Color(imageActual.getRGB(x, y));
                R=(int)colorAux.getRed();
                
                G=(int)colorAux.getGreen();
                B=(int)colorAux.getBlue();
                if(R>umbral) R=G=B=255;
                else    R=G=B=0;
                
                colorSRGB=(R<<16)|(G<<8)|(B);
                respaldo.setRGB(x, y, colorSRGB);
            }
        }
        return respaldo;
    }
    //este metodo permite binarizar una imagen por medio de un umbral inverso
    public BufferedImage binarizar_inverso(int umbral){
        this.escalaGrises();
        int R,G,B;
        int colorSRGB;
        Color colorAux;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                colorAux=new Color(imageActual.getRGB(x, y));
                R=(int)colorAux.getRed();
                G=(int)colorAux.getGreen();
                B=(int)colorAux.getBlue();
                if(R>umbral) R=G=B=0;
                else    R=G=B=255;
                
                colorSRGB=(R<<16)|(G<<8)|(B);
                respaldo.setRGB(x, y, colorSRGB);
            }
        }
        return respaldo;
    }
    /*******************************************************************************************************/
    /*                              Operaciones puntuales con dos imagenes                                 */
    /*******************************************************************************************************/
    //este metodo permite realizar la suma de dos imagenes
    public BufferedImage suma(BufferedImage imagen2){
        //hacemos respaldo de la imagen original 1
        //this.respaldo();
        int R,G,B;
        int colorSRGB;
        Color colorAux1,colorAux2; 
        //vemos que imagen es mas grande y creamos nueva imagen con las dimensiones de esta
        BufferedImage salida;
        int alto1,alto2,ancho1,ancho2,aux;
        alto1=imageActual.getHeight();
        alto2=imagen2.getHeight();
        ancho1=imageActual.getWidth();
        ancho2=imagen2.getWidth();
        if(alto2>alto1){
            aux=alto1;
            alto1=alto2;
            alto2=aux;
        }
        if(ancho2>ancho1){
            aux=ancho1;
            ancho1=ancho2;
            ancho2=aux;
        }
        salida=new BufferedImage(ancho1,alto1,imageActual.getType());
         System.out.println("Imagen nueva(Ancho: "+ancho1+"x alto:"+alto1+")");
        for(int y=0;y<salida.getHeight();y++){
            for(int x=0;x<salida.getWidth();x++){
                int r1,r2,g1,g2,b1,b2;
                r1=0; g1=0; b1=0;
                r2=0; g2=0; b2=0;
                if(x<imageActual.getWidth() && x<imagen2.getWidth() && y<imageActual.getHeight() && y<imagen2.getHeight()){
                    colorAux1=new Color(imageActual.getRGB(x, y));
                    colorAux2=new Color(imagen2.getRGB(x, y));
                    r1=colorAux1.getRed(); g1=colorAux1.getGreen(); b1=colorAux1.getBlue();
                    r2=colorAux2.getRed(); g2=colorAux2.getGreen(); b2=colorAux2.getBlue();
                }
                else if((x<imageActual.getWidth() &&y<imageActual.getHeight())){
                    colorAux1=new Color(imageActual.getRGB(x, y));
                    r1=colorAux1.getRed(); g1=colorAux1.getGreen(); b1=colorAux1.getBlue();
                }
                else if(x<imagen2.getWidth()&&y<imagen2.getHeight()){
                    colorAux2=new Color(imagen2.getRGB(x, y));
                    r2=colorAux2.getRed(); g2=colorAux2.getGreen(); b2=colorAux2.getBlue();
                }
                
                
                //rectificamos el desbode de color
                 R=(r1+r2)/2;
                 G=(g1+g2)/2;
                 B=(b1+b2)/2;
                 if(R>255)R=255;
                 if(G>255)G=255;
                 if(B>255)B=255;
                 if(R<0)R=0;
                 if(G<0)G=0;
                 if(B<0)B=0;
                colorSRGB=R<<16|G<<8|B;
                salida.setRGB(x, y,colorSRGB);
            }
        }
        return salida;
    }
    //este metodo permite realizar la resta de dos imagenes
    public BufferedImage resta(BufferedImage imagen2){
        //hacemos respaldo de la imagen original 1
        //this.respaldo();
        int R,G,B;
        int colorSRGB;
        Color colorAux1,colorAux2;
        
        //vemos que imagen es mas grande y creamos nueva imagen con las dimensiones de esta
        BufferedImage salida;
        int alto1,alto2,ancho1,ancho2,aux;
        alto1=imageActual.getHeight();
        alto2=imagen2.getHeight();
        ancho1=imageActual.getWidth();
        ancho2=imagen2.getWidth();
        if(alto2>alto1){
            aux=alto1;
            alto1=alto2;
            alto2=aux;
        }
        if(ancho2>ancho1){
            aux=ancho1;
            ancho1=ancho2;
            ancho2=aux;
        }
        salida=new BufferedImage(ancho1,alto1,imageActual.getType());
         System.out.println("Imagen nueva(Ancho: "+ancho1+"x alto:"+alto1+")");
        for(int y=0;y<salida.getHeight();y++){
            for(int x=0;x<salida.getWidth();x++){
                int r1,r2,g1,g2,b1,b2;
                r1=0; g1=0; b1=0;
                r2=0; g2=0; b2=0;
                if(x<imageActual.getWidth() && x<imagen2.getWidth() && y<imageActual.getHeight() && y<imagen2.getHeight()){
                    colorAux1=new Color(imageActual.getRGB(x, y));
                    colorAux2=new Color(imagen2.getRGB(x, y));
                    r1=colorAux1.getRed(); g1=colorAux1.getGreen(); b1=colorAux1.getBlue();
                    r2=colorAux2.getRed(); g2=colorAux2.getGreen(); b2=colorAux2.getBlue();
                }
                else if((x<imageActual.getWidth() &&y<imageActual.getHeight())){
                    colorAux1=new Color(imageActual.getRGB(x, y));
                    r1=colorAux1.getRed(); g1=colorAux1.getGreen(); b1=colorAux1.getBlue();
                }
                else if(x<imagen2.getWidth()&&y<imagen2.getHeight()){
                    colorAux2=new Color(imagen2.getRGB(x, y));
                    r2=colorAux2.getRed(); g2=colorAux2.getGreen(); b2=colorAux2.getBlue();
                }
                
                //rectificamos el desbode de color
                 R=2*(r1-r2);
                 G=2*(g1-g2);
                 B=2*(b1-b2);
                 if(R>255)R=255;
                 if(G>255)G=255;
                 if(B>255)B=255;
                 if(R<0)R=0;
                 if(G<0)G=0;
                 if(B<0)B=0;
                colorSRGB=R<<16|G<<8|B;
                salida.setRGB(x, y,colorSRGB);
            }
        }
        return salida;
    }
    //este metodo permite realizar la multiplicacion de dos imagenes
    public BufferedImage multiplicacion(BufferedImage imagen2){
        //hacemos respaldo de la imagen original 1
        //this.respaldo();
        int R,G,B;
        int colorSRGB;
        Color colorAux1,colorAux2;
        
        //vemos que imagen es mas grande y creamos nueva imagen con las dimensiones de esta
        BufferedImage salida;
        int alto1,alto2,ancho1,ancho2,aux;
        alto1=imageActual.getHeight();
        alto2=imagen2.getHeight();
        ancho1=imageActual.getWidth();
        ancho2=imagen2.getWidth();
        if(alto2>alto1){
            aux=alto1;
            alto1=alto2;
            alto2=aux;
        }
        if(ancho2>ancho1){
            aux=ancho1;
            ancho1=ancho2;
            ancho2=aux;
        }
        salida=new BufferedImage(ancho1,alto1,imageActual.getType());
         System.out.println("Imagen nueva(Ancho: "+ancho1+"x alto:"+alto1+")");
        for(int y=0;y<salida.getHeight();y++){
            for(int x=0;x<salida.getWidth();x++){
                int r1,r2,g1,g2,b1,b2;
                r1=0; g1=0; b1=0;
                r2=0; g2=0; b2=0;
                if(x<imageActual.getWidth() && x<imagen2.getWidth() && y<imageActual.getHeight() && y<imagen2.getHeight()){
                    colorAux1=new Color(imageActual.getRGB(x, y));
                    colorAux2=new Color(imagen2.getRGB(x, y));
                    r1=colorAux1.getRed(); g1=colorAux1.getGreen(); b1=colorAux1.getBlue();
                    r2=colorAux2.getRed(); g2=colorAux2.getGreen(); b2=colorAux2.getBlue();
                }
                else if((x<imageActual.getWidth() &&y<imageActual.getHeight())){
                    colorAux1=new Color(imageActual.getRGB(x, y));
                    r1=colorAux1.getRed(); g1=colorAux1.getGreen(); b1=colorAux1.getBlue();
                }
                else if(x<imagen2.getWidth()&&y<imagen2.getHeight()){
                    colorAux2=new Color(imagen2.getRGB(x, y));
                    r2=colorAux2.getRed(); g2=colorAux2.getGreen(); b2=colorAux2.getBlue();
                }
                
                
                //rectificamos el desbode de color
                 R=r1*r2;
                 G=g1*g2;
                 B=b1*b2;
                 if(R>255)R=255;
                 if(G>255)G=255;
                 if(B>255)B=255;
                 if(R<0)R=0;
                 if(G<0)G=0;
                 if(B<0)B=0;
                colorSRGB=R<<16|G<<8|B;
                salida.setRGB(x, y,colorSRGB);
            }
        }
        return salida;
    }
    //este metodo permite realizar el maximo de dos imagenes
    public BufferedImage maximo(BufferedImage imagen2){
        //hacemos respaldo de la imagen original 1
        //this.respaldo();
        int R,G,B;
        int colorSRGB;
        Color colorAux1,colorAux2;
        
        //vemos que imagen es mas grande y creamos nueva imagen con las dimensiones de esta
        BufferedImage salida;
        int alto1,alto2,ancho1,ancho2,aux;
        alto1=imageActual.getHeight();
        alto2=imagen2.getHeight();
        ancho1=imageActual.getWidth();
        ancho2=imagen2.getWidth();
        if(alto2>alto1){
            aux=alto1;
            alto1=alto2;
            alto2=aux;
        }
        if(ancho2>ancho1){
            aux=ancho1;
            ancho1=ancho2;
            ancho2=aux;
        }
        salida=new BufferedImage(ancho1,alto1,imageActual.getType());
         System.out.println("Imagen nueva(Ancho: "+ancho1+"x alto:"+alto1+")");
        for(int y=0;y<salida.getHeight();y++){
            for(int x=0;x<salida.getWidth();x++){
                int r1,r2,g1,g2,b1,b2;
                r1=0; g1=0; b1=0;
                r2=0; g2=0; b2=0;
                if(x<imageActual.getWidth() && x<imagen2.getWidth() && y<imageActual.getHeight() && y<imagen2.getHeight()){
                    colorAux1=new Color(imageActual.getRGB(x, y));
                    colorAux2=new Color(imagen2.getRGB(x, y));
                    r1=colorAux1.getRed(); g1=colorAux1.getGreen(); b1=colorAux1.getBlue();
                    r2=colorAux2.getRed(); g2=colorAux2.getGreen(); b2=colorAux2.getBlue();
                }
                else if((x<imageActual.getWidth() &&y<imageActual.getHeight())){
                    colorAux1=new Color(imageActual.getRGB(x, y));
                    r1=colorAux1.getRed(); g1=colorAux1.getGreen(); b1=colorAux1.getBlue();
                }
                else if(x<imagen2.getWidth()&&y<imagen2.getHeight()){
                    colorAux2=new Color(imagen2.getRGB(x, y));
                    r2=colorAux2.getRed(); g2=colorAux2.getGreen(); b2=colorAux2.getBlue();
                }
                //asignamos el valor maximo
                 R=r1; if(r1<r2) R=r2;
                 G=g1; if(g1<g2) G=g2;
                 B=b1; if(b1<b2) B=b2;
                //rectificamos el desbode de color
                 if(R>255)R=255;
                 if(G>255)G=255;
                 if(B>255)B=255;
                 if(R<0)R=0;
                 if(G<0)G=0;
                 if(B<0)B=0;
                colorSRGB=R<<16|G<<8|B;
                salida.setRGB(x, y,colorSRGB);
            }
        }
        return salida;
    }
    //este metodo permite realizar el minimo de dos imagenes
    public BufferedImage minimo(BufferedImage imagen2){
        //hacemos respaldo de la imagen original 1
        //this.respaldo();
        int R,G,B;
        int colorSRGB;
        Color colorAux1,colorAux2;
        
        //vemos que imagen es mas grande y creamos nueva imagen con las dimensiones de esta
        BufferedImage salida;
        int alto1,alto2,ancho1,ancho2,aux;
        alto1=imageActual.getHeight();
        alto2=imagen2.getHeight();
        ancho1=imageActual.getWidth();
        ancho2=imagen2.getWidth();
        if(alto2>alto1){
            aux=alto1;
            alto1=alto2;
            alto2=aux;
        }
        if(ancho2>ancho1){
            aux=ancho1;
            ancho1=ancho2;
            ancho2=aux;
        }
        salida=new BufferedImage(ancho1,alto1,imageActual.getType());
         System.out.println("Imagen nueva(Ancho: "+ancho1+"x alto:"+alto1+")");
        for(int y=0;y<salida.getHeight();y++){
            for(int x=0;x<salida.getWidth();x++){
                int r1,r2,g1,g2,b1,b2;
                r1=0; g1=0; b1=0;
                r2=0; g2=0; b2=0;
                if(x<imageActual.getWidth() && x<imagen2.getWidth() && y<imageActual.getHeight() && y<imagen2.getHeight()){
                    colorAux1=new Color(imageActual.getRGB(x, y));
                    colorAux2=new Color(imagen2.getRGB(x, y));
                    r1=colorAux1.getRed(); g1=colorAux1.getGreen(); b1=colorAux1.getBlue();
                    r2=colorAux2.getRed(); g2=colorAux2.getGreen(); b2=colorAux2.getBlue();
                }
                else if((x<imageActual.getWidth() &&y<imageActual.getHeight())){
                    colorAux1=new Color(imageActual.getRGB(x, y));
                    r1=colorAux1.getRed(); g1=colorAux1.getGreen(); b1=colorAux1.getBlue();
                }
                else if(x<imagen2.getWidth()&&y<imagen2.getHeight()){
                    colorAux2=new Color(imagen2.getRGB(x, y));
                    r2=colorAux2.getRed(); g2=colorAux2.getGreen(); b2=colorAux2.getBlue();
                }
                //asignamos el valor minimo
                 R=r1; if(r1>r2) R=r2;
                 G=g1; if(g1>g2) G=g2;
                 B=b1; if(b1>b2) B=b2;
                //rectificamos el desbode de color
                 if(R>255)R=255;
                 if(G>255)G=255;
                 if(B>255)B=255;
                 if(R<0)R=0;
                 if(G<0)G=0;
                 if(B<0)B=0;
                colorSRGB=R<<16|G<<8|B;
                salida.setRGB(x, y,colorSRGB);
            }
        }
        return salida;
    }
    //este metodo permite realizar and entre dos imagenes
    public BufferedImage and(BufferedImage imagen2){
        //hacemos respaldo de la imagen original 1
        //this.respaldo();
        int R,G,B;
        int colorSRGB;
        Color colorAux1,colorAux2;
        
        //vemos que imagen es mas grande y creamos nueva imagen con las dimensiones de esta
        BufferedImage salida;
        int alto1,alto2,ancho1,ancho2,aux;
        alto1=imageActual.getHeight();
        alto2=imagen2.getHeight();
        ancho1=imageActual.getWidth();
        ancho2=imagen2.getWidth();
        if(alto2>alto1){
            aux=alto1;
            alto1=alto2;
            alto2=aux;
        }
        if(ancho2>ancho1){
            aux=ancho1;
            ancho1=ancho2;
            ancho2=aux;
        }
        salida=new BufferedImage(ancho1,alto1,imageActual.getType());
         System.out.println("Imagen nueva(Ancho: "+ancho1+"x alto:"+alto1+")");
        for(int y=0;y<salida.getHeight();y++){
            for(int x=0;x<salida.getWidth();x++){
                int r1,r2,g1,g2,b1,b2;
                r1=0; g1=0; b1=0;
                r2=0; g2=0; b2=0;
                if(x<imageActual.getWidth() && x<imagen2.getWidth() && y<imageActual.getHeight() && y<imagen2.getHeight()){
                    colorAux1=new Color(imageActual.getRGB(x, y));
                    colorAux2=new Color(imagen2.getRGB(x, y));
                    r1=colorAux1.getRed(); g1=colorAux1.getGreen(); b1=colorAux1.getBlue();
                    r2=colorAux2.getRed(); g2=colorAux2.getGreen(); b2=colorAux2.getBlue();
                }
                else if((x<imageActual.getWidth() &&y<imageActual.getHeight())){
                    colorAux1=new Color(imageActual.getRGB(x, y));
                    r1=colorAux1.getRed(); g1=colorAux1.getGreen(); b1=colorAux1.getBlue();
                }
                else if(x<imagen2.getWidth()&&y<imagen2.getHeight()){
                    colorAux2=new Color(imagen2.getRGB(x, y));
                    r2=colorAux2.getRed(); g2=colorAux2.getGreen(); b2=colorAux2.getBlue();
                }
                
                
                //rectificamos el desbode de color
                 R=r1&r2;
                 G=g1&g2;
                 B=b1&b2;
                 if(R>255)R=255;
                 if(G>255)G=255;
                 if(B>255)B=255;
                 if(R<0)R=0;
                 if(G<0)G=0;
                 if(B<0)B=0;
                colorSRGB=R<<16|G<<8|B;
                salida.setRGB(x, y,colorSRGB);
            }
        }
        return salida;
    }
    //este metodo permite realizar or entre dos imagenes
    public BufferedImage or(BufferedImage imagen2){
        //hacemos respaldo de la imagen original 1
        //this.respaldo();
        int R,G,B;
        int colorSRGB;
        Color colorAux1,colorAux2;
        
        //vemos que imagen es mas grande y creamos nueva imagen con las dimensiones de esta
        BufferedImage salida;
        int alto1,alto2,ancho1,ancho2,aux;
        alto1=imageActual.getHeight();
        alto2=imagen2.getHeight();
        ancho1=imageActual.getWidth();
        ancho2=imagen2.getWidth();
        if(alto2>alto1){
            aux=alto1;
            alto1=alto2;
            alto2=aux;
        }
        if(ancho2>ancho1){
            aux=ancho1;
            ancho1=ancho2;
            ancho2=aux;
        }
        salida=new BufferedImage(ancho1,alto1,imageActual.getType());
         System.out.println("Imagen nueva(Ancho: "+ancho1+"x alto:"+alto1+")");
        for(int y=0;y<salida.getHeight();y++){
            for(int x=0;x<salida.getWidth();x++){
                int r1,r2,g1,g2,b1,b2;
                r1=0; g1=0; b1=0;
                r2=0; g2=0; b2=0;
                if(x<imageActual.getWidth() && x<imagen2.getWidth() && y<imageActual.getHeight() && y<imagen2.getHeight()){
                    colorAux1=new Color(imageActual.getRGB(x, y));
                    colorAux2=new Color(imagen2.getRGB(x, y));
                    r1=colorAux1.getRed(); g1=colorAux1.getGreen(); b1=colorAux1.getBlue();
                    r2=colorAux2.getRed(); g2=colorAux2.getGreen(); b2=colorAux2.getBlue();
                }
                else if((x<imageActual.getWidth() &&y<imageActual.getHeight())){
                    colorAux1=new Color(imageActual.getRGB(x, y));
                    r1=colorAux1.getRed(); g1=colorAux1.getGreen(); b1=colorAux1.getBlue();
                }
                else if(x<imagen2.getWidth()&&y<imagen2.getHeight()){
                    colorAux2=new Color(imagen2.getRGB(x, y));
                    r2=colorAux2.getRed(); g2=colorAux2.getGreen(); b2=colorAux2.getBlue();
                }
                
                
                //rectificamos el desbode de color
                 R=r1|r2;
                 G=g1|g2;
                 B=b1|b2;
                 if(R>255)R=255;
                 if(G>255)G=255;
                 if(B>255)B=255;
                 if(R<0)R=0;
                 if(G<0)G=0;
                 if(B<0)B=0;
                colorSRGB=R<<16|G<<8|B;
                salida.setRGB(x, y,colorSRGB);
            }
        }
        return salida;
    }
    //este metodo permite realizar xor entre dos imagenes
    public BufferedImage xor(BufferedImage imagen2){
        //hacemos respaldo de la imagen original 1
        //this.respaldo();
        int R,G,B;
        int colorSRGB;
        Color colorAux1,colorAux2;
        
        //vemos que imagen es mas grande y creamos nueva imagen con las dimensiones de esta
        BufferedImage salida;
        int alto1,alto2,ancho1,ancho2,aux;
        alto1=imageActual.getHeight();
        alto2=imagen2.getHeight();
        ancho1=imageActual.getWidth();
        ancho2=imagen2.getWidth();
        if(alto2>alto1){
            aux=alto1;
            alto1=alto2;
            alto2=aux;
        }
        if(ancho2>ancho1){
            aux=ancho1;
            ancho1=ancho2;
            ancho2=aux;
        }
        salida=new BufferedImage(ancho1,alto1,imageActual.getType());
         System.out.println("Imagen nueva(Ancho: "+ancho1+"x alto:"+alto1+")");
        for(int y=0;y<salida.getHeight();y++){
            for(int x=0;x<salida.getWidth();x++){
                int r1,r2,g1,g2,b1,b2;
                r1=0; g1=0; b1=0;
                r2=0; g2=0; b2=0;
                if(x<imageActual.getWidth() && x<imagen2.getWidth() && y<imageActual.getHeight() && y<imagen2.getHeight()){
                    colorAux1=new Color(imageActual.getRGB(x, y));
                    colorAux2=new Color(imagen2.getRGB(x, y));
                    r1=colorAux1.getRed(); g1=colorAux1.getGreen(); b1=colorAux1.getBlue();
                    r2=colorAux2.getRed(); g2=colorAux2.getGreen(); b2=colorAux2.getBlue();
                }
                else if((x<imageActual.getWidth() &&y<imageActual.getHeight())){
                    colorAux1=new Color(imageActual.getRGB(x, y));
                    r1=colorAux1.getRed(); g1=colorAux1.getGreen(); b1=colorAux1.getBlue();
                }
                else if(x<imagen2.getWidth()&&y<imagen2.getHeight()){
                    colorAux2=new Color(imagen2.getRGB(x, y));
                    r2=colorAux2.getRed(); g2=colorAux2.getGreen(); b2=colorAux2.getBlue();
                }
                
                
                //rectificamos el desbode de color
                 R=((~r1)&r2)|(r1&(~r2));
                 G=((~g1)&g2)|(g1&(~g2));
                 B=((~b1)&b2)|(b1&(~b2));
                 if(R>255)R=255;
                 if(G>255)G=255;
                 if(B>255)B=255;
                 if(R<0)R=0;
                 if(G<0)G=0;
                 if(B<0)B=0;
                colorSRGB=R<<16|G<<8|B;
                salida.setRGB(x, y,colorSRGB);
            }
        }
        return salida;
    }
    //este metodo descompone la imagen en su color rojo
    public BufferedImage getRedImage(){
        int R,G,B,nR,nG,nB;
        int colorSRGB;
        Color colorAux = null;
        BufferedImage nueva;
        nueva = new BufferedImage(imageActual.getWidth(),imageActual.getHeight(),imageActual.getType());
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                colorAux=new Color(imageActual.getRGB(x,y));
                 R=(int)colorAux.getRed();
                 G=0;
                 B=0;
                 //sumamos el brillo a cada valor
                 colorSRGB=(R<<16)|(G<<8)|B;
                 nueva.setRGB(x,y,colorSRGB);
            }
        }
        return nueva;
    }
    //este metodo descompone la imagen en su color verde
    public BufferedImage getGreenImage(){
        int R,G,B,nR,nG,nB;
        int colorSRGB;
        Color colorAux = null;
        BufferedImage nueva;
        nueva = new BufferedImage(imageActual.getWidth(),imageActual.getHeight(),imageActual.getType());
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                colorAux=new Color(imageActual.getRGB(x,y));
                 R=0;
                 G=(int)colorAux.getGreen();
                 B=0;
                 //sumamos el brillo a cada valor
                 colorSRGB=(R<<16)|(G<<8)|B;
                 nueva.setRGB(x,y,colorSRGB);
            }
        }
        return nueva;
    }
    //este metodo descompone la imagen en su color azul
    public BufferedImage getBlueImage(){
        int R,G,B,nR,nG,nB;
        int colorSRGB;
        Color colorAux = null;
        BufferedImage nueva;
        nueva = new BufferedImage(imageActual.getWidth(),imageActual.getHeight(),imageActual.getType());
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                colorAux=new Color(imageActual.getRGB(x,y));
                 R=0;
                 G=0;
                 B=(int)colorAux.getBlue();
                 //sumamos el brillo a cada valor
                 colorSRGB=(R<<16)|(G<<8)|B;
                 nueva.setRGB(x,y,colorSRGB);
            }
        }
        return nueva;
    }
    
    /*******************************************************************************************************/
    /*                              Filtros de imagenes                                                    */
    /*******************************************************************************************************/
    //este metodo nos permite realizar el filtro Promedio estandar a una imagen
    public BufferedImage filtroPromedio(){
        this.respaldo();
       int k1,k2,k3,k4,k5,k6,k7,k8,k9;
                k1=1;k2=1;k3=1;
                k4=1;k5=1;k6=1;
                k7=1;k8=1;k9=1;
        //variable que multiplica el kernel
        int R,G,B;
        float valor,sumaR,sumaG,sumaB;
        //pixeles que van a multiplicarse
        //pixeles rojos
        int[]pr={0,0,0,0,0,0,0,0,0}; 
        //pixeles verdes
        int[]pg={0,0,0,0,0,0,0,0,0}; 
        //pixeles azules
        int[]pb={0,0,0,0,0,0,0,0,0}; 
        int colorSRGB;
        Color colorAux;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                sumaR=sumaG=sumaB=0;
                for(int i=0;i<9;i++){
                    pr[i]=0;pg[i]=0;pb[i]=0;
                }
                //condiciones para hacer la correlacion en la imagen
                if(y==0){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[6]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                    }
                }
                else if(y==imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                    }
                    else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                    }
                }else if(y>0&&y<imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                    }else{
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();;
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }
                }
                for(int i=0;i<pr.length;i++){
                    sumaR+=pr[i];
                    sumaG+=pg[i];
                    sumaB+=pb[i];
                }
                valor=9;
                sumaR=(float)(1.0/valor)*sumaR;
                sumaG=(float)(1.0/valor)*sumaG;
                sumaB=(float)(1.0/valor)*sumaB;
                if(sumaR>255) sumaR=255;
                if(sumaG>255) sumaG=255;
                if(sumaB>255) sumaB=255;
                R=(int)sumaR; G=(int)sumaG; B=(int)sumaB;
                colorSRGB=R<<16|G<<8|B;
                respaldo.setRGB(x, y, colorSRGB);
            }//fin de x    
        }//fin de y
        imageActual=respaldo;
        return imageActual;
    //fin del filtro
    }
    //este metodo nos permite realizar el filtro Gaussiano a una imagen
    public BufferedImage filtroGaussiano(){
        this.respaldo();
        //variables para kernel
        int k1,k2,k3,k4,k5,k6,k7,k8,k9;
        //variable que multiplica el kernel
        int R,G,B;
        float valor,sumaR,sumaG,sumaB;
        //el valor es 16 para obtener el filtro gaussiano
        valor=16;
        //pixeles que van a multiplicarse
        //pixeles rojos
        int pr1,pr2,pr3,pr4,pr5,pr6,pr7,pr8,pr9;
        //pixeles verdes
        int pg1,pg2,pg3,pg4,pg5,pg6,pg7,pg8,pg9;
        //pixeles azules
        int pb1,pb2,pb3,pb4,pb5,pb6,pb7,pb8,pb9;
        int colorSRGB;
        Color colorAux;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                //establecemos kernel
                k1=1; k2=2; k3=1;
                k4=2; k5=4; k6=2;
                k7=1; k8=2; k9=1;
                sumaR=0; sumaG=0; sumaB=0;
                //condiciones para hacer la correlacion en la imagen
                if(y==0){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr5=(int)colorAux.getRed();pg5=(int)colorAux.getGreen();pb5=(int)colorAux.getBlue();
                        sumaR+=k5*pr5; sumaG+=k5*pg5; sumaB+=k5*pb5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr6=(int)colorAux.getRed();pg6=(int)colorAux.getGreen();pb6=(int)colorAux.getBlue();
                        sumaR+=k6*pr6; sumaG+=k6*pg6; sumaB+=k6*pb6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr8=(int)colorAux.getRed();pg8=(int)colorAux.getGreen();pb8=(int)colorAux.getBlue();
                        sumaR+=k8*pr8; sumaG+=k8*pg8; sumaB+=k8*pb8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr9=(int)colorAux.getRed();pg9=(int)colorAux.getGreen();pb9=(int)colorAux.getBlue();
                        sumaR+=k9*pr9; sumaG+=k9*pg9; sumaB+=k9*pb9;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr4=(int)colorAux.getRed();pg4=(int)colorAux.getGreen();pb4=(int)colorAux.getBlue();
                        sumaR+=k4*pr4; sumaG+=k4*pg4; sumaB+=k4*pb4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr5=(int)colorAux.getRed();pg5=(int)colorAux.getGreen();pb5=(int)colorAux.getBlue();
                        sumaR+=k5*pr5; sumaG+=k5*pg5; sumaB+=k5*pb5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr6=(int)colorAux.getRed();pg6=(int)colorAux.getGreen();pb6=(int)colorAux.getBlue();
                        sumaR+=k6*pr6; sumaG+=k6*pg6; sumaB+=k6*pb6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr7=(int)colorAux.getRed();pg7=(int)colorAux.getGreen();pb7=(int)colorAux.getBlue();
                        sumaR+=k7*pr7; sumaG+=k7*pg7; sumaB+=k7*pb7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr8=(int)colorAux.getRed();pg8=(int)colorAux.getGreen();pb8=(int)colorAux.getBlue();
                        sumaR+=k8*pr8; sumaG+=k8*pg8; sumaB+=k8*pb8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr9=(int)colorAux.getRed();pg9=(int)colorAux.getGreen();pb9=(int)colorAux.getBlue();
                        sumaR+=k9*pr9; sumaG+=k9*pg9; sumaB+=k9*pb9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr4=(int)colorAux.getRed();pg4=(int)colorAux.getGreen();pb4=(int)colorAux.getBlue();
                        sumaR+=k4*pr4; sumaG+=k4*pg4; sumaB+=k4*pb4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr5=(int)colorAux.getRed();pg5=(int)colorAux.getGreen();pb5=(int)colorAux.getBlue();
                        sumaR+=k5*pr5; sumaG+=k5*pg5; sumaB+=k5*pb5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr7=(int)colorAux.getRed();pg7=(int)colorAux.getGreen();pb7=(int)colorAux.getBlue();
                        sumaR+=k7*pr7; sumaG+=k7*pg7; sumaB+=k7*pb7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr8=(int)colorAux.getRed();pg8=(int)colorAux.getGreen();pb8=(int)colorAux.getBlue();
                        sumaR+=k8*pr8; sumaG+=k8*pg8; sumaB+=k8*pb8;
                    }
                }
                else if(y==imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr2=(int)colorAux.getRed();pg2=(int)colorAux.getGreen();pb2=(int)colorAux.getBlue();
                        sumaR+=k2*pr2; sumaG+=k2*pg2; sumaB+=k2*pb2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr3=(int)colorAux.getRed();pg3=(int)colorAux.getGreen();pb3=(int)colorAux.getBlue();
                        sumaR+=k3*pr3; sumaG+=k3*pg3; sumaB+=k3*pb3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr5=(int)colorAux.getRed();pg5=(int)colorAux.getGreen();pb5=(int)colorAux.getBlue();
                        sumaR+=k5*pr5; sumaG+=k5*pg5; sumaB+=k5*pb5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr6=(int)colorAux.getRed();pg6=(int)colorAux.getGreen();pb6=(int)colorAux.getBlue();
                        sumaR+=k6*pr6; sumaG+=k6*pg6; sumaB+=k6*pb6;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr1=(int)colorAux.getRed();pg1=(int)colorAux.getGreen();pb1=(int)colorAux.getBlue();
                        sumaR+=k1*pr1; sumaG+=k1*pg1; sumaB+=k1*pb1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr2=(int)colorAux.getRed();pg2=(int)colorAux.getGreen();pb2=(int)colorAux.getBlue();
                        sumaR+=k2*pr2; sumaG+=k2*pg2; sumaB+=k2*pb2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr3=(int)colorAux.getRed();pg3=(int)colorAux.getGreen();pb3=(int)colorAux.getBlue();
                        sumaR+=k3*pr3; sumaG+=k3*pg3; sumaB+=k3*pb3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr4=(int)colorAux.getRed();pg4=(int)colorAux.getGreen();pb4=(int)colorAux.getBlue();
                        sumaR+=k4*pr4; sumaG+=k4*pg4; sumaB+=k4*pb4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr5=(int)colorAux.getRed();pg5=(int)colorAux.getGreen();pb5=(int)colorAux.getBlue();
                        sumaR+=k5*pr5; sumaG+=k5*pg5; sumaB+=k5*pb5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr6=(int)colorAux.getRed();pg6=(int)colorAux.getGreen();pb6=(int)colorAux.getBlue();
                        sumaR+=k6*pr6; sumaG+=k6*pg6; sumaB+=k6*pb6;
                    }
                    else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr1=(int)colorAux.getRed();pg1=(int)colorAux.getGreen();pb1=(int)colorAux.getBlue();
                        sumaR+=k1*pr1; sumaG+=k1*pg1; sumaB+=k1*pb1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr2=(int)colorAux.getRed();pg2=(int)colorAux.getGreen();pb2=(int)colorAux.getBlue();
                        sumaR+=k2*pr2; sumaG+=k2*pg2; sumaB+=k2*pb2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr4=(int)colorAux.getRed();pg4=(int)colorAux.getGreen();pb4=(int)colorAux.getBlue();
                        sumaR+=k4*pr4; sumaG+=k4*pg4; sumaB+=k4*pb4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr5=(int)colorAux.getRed();pg5=(int)colorAux.getGreen();pb5=(int)colorAux.getBlue();
                        sumaR+=k5*pr5; sumaG+=k5*pg5; sumaB+=k5*pb5;
                    }
                }else if(y>0&&y<imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr2=(int)colorAux.getRed();pg2=(int)colorAux.getGreen();pb2=(int)colorAux.getBlue();
                        sumaR+=k2*pr2; sumaG+=k2*pg2; sumaB+=k2*pb2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr3=(int)colorAux.getRed();pg3=(int)colorAux.getGreen();pb3=(int)colorAux.getBlue();
                        sumaR+=k3*pr3; sumaG+=k3*pg3; sumaB+=k3*pb3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr5=(int)colorAux.getRed();pg5=(int)colorAux.getGreen();pb5=(int)colorAux.getBlue();
                        sumaR+=k5*pr5; sumaG+=k5*pg5; sumaB+=k5*pb5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr6=(int)colorAux.getRed();pg6=(int)colorAux.getGreen();pb6=(int)colorAux.getBlue();
                        sumaR+=k6*pr6; sumaG+=k6*pg6; sumaB+=k6*pb6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr8=(int)colorAux.getRed();pg8=(int)colorAux.getGreen();pb8=(int)colorAux.getBlue();
                        sumaR+=k8*pr8; sumaG+=k8*pg8; sumaB+=k8*pb8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr9=(int)colorAux.getRed();pg9=(int)colorAux.getGreen();pb9=(int)colorAux.getBlue();
                        sumaR+=k9*pr9; sumaG+=k9*pg9; sumaB+=k9*pb9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr1=(int)colorAux.getRed();pg1=(int)colorAux.getGreen();pb1=(int)colorAux.getBlue();
                        sumaR+=k1*pr1; sumaG+=k1*pg1; sumaB+=k1*pb1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr2=(int)colorAux.getRed();pg2=(int)colorAux.getGreen();pb2=(int)colorAux.getBlue();
                        sumaR+=k2*pr2; sumaG+=k2*pg2; sumaB+=k2*pb2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr4=(int)colorAux.getRed();pg4=(int)colorAux.getGreen();pb4=(int)colorAux.getBlue();
                        sumaR+=k4*pr4; sumaG+=k4*pg4; sumaB+=k4*pb4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr5=(int)colorAux.getRed();pg5=(int)colorAux.getGreen();pb5=(int)colorAux.getBlue();
                        sumaR+=k5*pr5; sumaG+=k5*pg5; sumaB+=k5*pb5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr7=(int)colorAux.getRed();pg7=(int)colorAux.getGreen();pb7=(int)colorAux.getBlue();
                        sumaR+=k7*pr7; sumaG+=k7*pg7; sumaB+=k7*pb7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr8=(int)colorAux.getRed();pg8=(int)colorAux.getGreen();pb8=(int)colorAux.getBlue();
                        sumaR+=k8*pr8; sumaG+=k8*pg8; sumaB+=k8*pb8;
                    }else{
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr1=(int)colorAux.getRed();pg1=(int)colorAux.getGreen();pb1=(int)colorAux.getBlue();
                        sumaR+=k1*pr1; sumaG+=k1*pg1; sumaB+=k1*pb1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr2=(int)colorAux.getRed();pg2=(int)colorAux.getGreen();pb2=(int)colorAux.getBlue();
                        sumaR+=k2*pr2; sumaG+=k2*pg2; sumaB+=k2*pb2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr3=(int)colorAux.getRed();pg3=(int)colorAux.getGreen();pb3=(int)colorAux.getBlue();
                        sumaR+=k3*pr3; sumaG+=k3*pg3; sumaB+=k3*pb3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr4=(int)colorAux.getRed();pg4=(int)colorAux.getGreen();pb4=(int)colorAux.getBlue();
                        sumaR+=k4*pr4; sumaG+=k4*pg4; sumaB+=k4*pb4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr5=(int)colorAux.getRed();pg5=(int)colorAux.getGreen();pb5=(int)colorAux.getBlue();
                        sumaR+=k5*pr5; sumaG+=k5*pg5; sumaB+=k5*pb5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr6=(int)colorAux.getRed();pg6=(int)colorAux.getGreen();pb6=(int)colorAux.getBlue();
                        sumaR+=k6*pr6; sumaG+=k6*pg6; sumaB+=k6*pb6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr7=(int)colorAux.getRed();pg7=(int)colorAux.getGreen();pb7=(int)colorAux.getBlue();
                        sumaR+=k7*pr7; sumaG+=k7*pg7; sumaB+=k7*pb7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr8=(int)colorAux.getRed();pg8=(int)colorAux.getGreen();pb8=(int)colorAux.getBlue();
                        sumaR+=k8*pr8; sumaG+=k8*pg8; sumaB+=k8*pb8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr9=(int)colorAux.getRed();pg9=(int)colorAux.getGreen();pb9=(int)colorAux.getBlue();
                        sumaR+=k9*pr9; sumaG+=k9*pg9; sumaB+=k9*pb9;
                    }
                }
                sumaR=(float)(1.0/valor)*sumaR;
                sumaG=(float)(1.0/valor)*sumaG;
                sumaB=(float)(1.0/valor)*sumaB;
                if(sumaR>255) sumaR=255;
                if(sumaG>255) sumaG=255;
                if(sumaB>255) sumaB=255;
                R=(int)sumaR; G=(int)sumaG; B=(int)sumaB;
                colorSRGB=R<<16|G<<8|B;
                respaldo.setRGB(x, y, colorSRGB);
            }//fin x
            imageActual=respaldo;
        }//fin y
        return imageActual;
    //fin del filtro
    }
    //este metodo ordena un arreglo bidimencional, se emplea para ordenar los valores de menos a mayor de la vecinadad de p
    public void ordenarArreglo(int []arr){
        for(int i=0;i<arr.length;i++){
                    for(int j=0;j<arr.length;j++){
                        if(arr[i]<arr[j]){
                            int aux=arr[i];
                            arr[i]=arr[j];
                            arr[j]=aux;
                        }   
                    }
                }
    }
    //este metodo nos permite realizar el filtro mediana, hacemos el procedimiento de la correlacion con un 
    //kernel con 1s para obtener los vecinos de p(x) pero no realizamos la suma de los pixeles vecinos
   public BufferedImage filtroMediana(){
       this.respaldo();
        int k1,k2,k3,k4,k5,k6,k7,k8,k9;
                k1=1;k2=1;k3=1;
                k4=1;k5=1;k6=1;
                k7=1;k8=1;k9=1;
        //variable que multiplica el kernel
        int R,G,B;
        float valor,sumaR,sumaG,sumaB;
        //pixeles que van a multiplicarse
        //pixeles rojos
        int[]pr={0,0,0,0,0,0,0,0,0}; 
        //pixeles verdes
        int[]pg={0,0,0,0,0,0,0,0,0}; 
        //pixeles azules
        int[]pb={0,0,0,0,0,0,0,0,0}; 
        int colorSRGB;
        Color colorAux;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                sumaR=sumaG=sumaB=0;
                for(int i=0;i<9;i++){
                    pr[i]=0;pg[i]=0;pb[i]=0;
                }
                //condiciones para hacer la correlacion en la imagen
                if(y==0){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[6]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                    }
                }
                else if(y==imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                    }
                    else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                    }
                }else if(y>0&&y<imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                    }else{
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();;
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }
                }
                //ordenamos arreglo
                ordenarArreglo(pr);ordenarArreglo(pg);ordenarArreglo(pb);
                //valores medios
                sumaR=pr[4]; sumaG=pg[4]; sumaB=pb[4];
                if(sumaR>255) sumaR=255;
                if(sumaG>255) sumaG=255;
                if(sumaB>255) sumaB=255;
                R=(int)sumaR; G=(int)sumaG; B=(int)sumaB;
                colorSRGB=R<<16|G<<8|B;
                respaldo.setRGB(x, y, colorSRGB);
            }//fin de x    
        }//fin de y
        imageActual=respaldo;
        return imageActual;
    }
   //este metodo nos permite realizar el filtro maximo
   public BufferedImage filtroMaximo(){
       this.respaldo();
       int k1,k2,k3,k4,k5,k6,k7,k8,k9;
                k1=1;k2=1;k3=1;
                k4=1;k5=1;k6=1;
                k7=1;k8=1;k9=1;
        //variable que multiplica el kernel
        int R,G,B;
        float valor,sumaR,sumaG,sumaB;
        //pixeles que van a multiplicarse
        //pixeles rojos
        int[]pr={128,128,128,128,128,128,128,128,128}; 
        //pixeles verdes
        int[]pg={128,128,128,128,128,128,128,128,128};
        //pixeles azules
        int[]pb={128,128,128,128,128,128,128,128,128};
        int colorSRGB;
        Color colorAux;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                sumaR=sumaG=sumaB=0;
                for(int i=0;i<9;i++){
                    pr[i]=0;pg[i]=0;pb[i]=0;
                }
                //condiciones para hacer la correlacion en la imagen
                if(y==0){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                    }
                }
                else if(y==imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                    }
                    else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                    }
                }else if(y>0&&y<imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                    }else{
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();;
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }
                }
                //ordenamos arreglo
                ordenarArreglo(pr);ordenarArreglo(pg);ordenarArreglo(pb);
                /*for(int i=0;i<9;i++)
                    System.out.print(pr[i]+",");
                System.out.print("->"+pr[8]+"\n");*/
                //valores medios
                sumaR=pr[8]; sumaG=pg[8]; sumaB=pb[8];
                if(sumaR>255) sumaR=255;
                if(sumaG>255) sumaG=255;
                if(sumaB>255) sumaB=255;
                R=(int)sumaR; G=(int)sumaG; B=(int)sumaB;
                colorSRGB=R<<16|G<<8|B;
                respaldo.setRGB(x, y, colorSRGB);
            }//fin de x    
        }//fin de y
        imageActual=respaldo;
        return imageActual;
   }
   //este metodo nos permite realizar el filtro minimo
   public BufferedImage filtroMinimo(){
       this.respaldo();
       int k1,k2,k3,k4,k5,k6,k7,k8,k9;
                k1=1;k2=1;k3=1;
                k4=1;k5=1;k6=1;
                k7=1;k8=1;k9=1;
        //variable que multiplica el kernel
        int R,G,B;
        float valor,sumaR,sumaG,sumaB;
        //pixeles que van a multiplicarse
        //pixeles rojos
        int[]pr={128,128,128,128,128,128,128,128,128}; 
        //pixeles verdes
        int[]pg={128,128,128,128,128,128,128,128,128};
        //pixeles azules
        int[]pb={128,128,128,128,128,128,128,128,128};
        int colorSRGB;
        Color colorAux;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                sumaR=sumaG=sumaB=0;
                for(int i=0;i<9;i++){
                    pr[i]=255;pg[i]=255;pb[i]=255;
                }
                //condiciones para hacer la correlacion en la imagen
                if(y==0){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                    }
                }
                else if(y==imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                    }
                    else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                    }
                }else if(y>0&&y<imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                    }else{
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();;
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }
                }
                //ordenamos arreglo
                ordenarArreglo(pr);ordenarArreglo(pg);ordenarArreglo(pb);
                /*or(int i=0;i<9;i++)
                    System.out.print(pr[i]+",");
                System.out.print("->"+pr[0]+"\n");*/
                //valores medios
                sumaR=pr[0]; sumaG=pg[0]; sumaB=pb[0];
                if(sumaR>255) sumaR=255;
                if(sumaG>255) sumaG=255;
                if(sumaB>255) sumaB=255;
                R=(int)sumaR; G=(int)sumaG; B=(int)sumaB;
                colorSRGB=R<<16|G<<8|B;
                respaldo.setRGB(x, y, colorSRGB);
            }//fin de x    
        }//fin de y
        imageActual=respaldo;
        return imageActual;
   }
   //este metodo nos permite realizal el filtro moda
   public BufferedImage filtroModa(){
       this.respaldo();
       int k1,k2,k3,k4,k5,k6,k7,k8,k9;
                k1=1;k2=1;k3=1;
                k4=1;k5=1;k6=1;
                k7=1;k8=1;k9=1;
        //variable que multiplica el kernel
        int R,G,B;
        float valor,sumaR,sumaG,sumaB;
        //pixeles que van a multiplicarse
        //pixeles rojos
        int[]pr={0,0,0,0,0,0,0,0,0}; 
        //pixeles verdes
        int[]pg={0,0,0,0,0,0,0,0,0}; 
        //pixeles azules
        int[]pb={0,0,0,0,0,0,0,0,0}; 
        int colorSRGB;
        Color colorAux;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                sumaR=sumaG=sumaB=0;
                for(int i=0;i<9;i++){
                    pr[i]=0;pg[i]=0;pb[i]=0;
                }
                //condiciones para hacer la correlacion en la imagen
                if(y==0){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                    }
                }
                else if(y==imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                    }
                    else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                    }
                }else if(y>0&&y<imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                    }else{
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();;
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }
                }
                //ordenamos arreglo
                ordenarArreglo(pr);ordenarArreglo(pg);ordenarArreglo(pb);
                //obtenemos el valor moda
                int maximoRepetidoR=0,maximoRepetidoG=0,maximoRepetidoB=0;int modar=0,modab=0,modag=0;
                for(int i=0;i<9;i++){
                    int repeticionesR=0,repeticionesG=0,repeticionesB=0;
                    for(int j=0;j<9;j++){
                        if(pr[i]==pr[j])repeticionesR++;
                        if(pg[i]==pg[j])repeticionesG++;
                        if(pb[i]==pb[j])repeticionesB++;
                        
                    }
                    if(maximoRepetidoR<repeticionesR){maximoRepetidoR=repeticionesR;modar=pr[i];}
                    if(maximoRepetidoG<repeticionesG){maximoRepetidoG=repeticionesG;modag=pg[i];}
                    if(maximoRepetidoB<repeticionesB){maximoRepetidoB=repeticionesB;modab=pb[i];}
                   //System.out.print(pr[i]+",");
                }//System.out.println("->moda:"+modar);
                sumaR=modar; sumaG=modag; sumaB=modab;
                if(sumaR>255) sumaR=255;
                if(sumaG>255) sumaG=255;
                if(sumaB>255) sumaB=255;
                R=(int)sumaR; G=(int)sumaG; B=(int)sumaB;
                colorSRGB=R<<16|G<<8|B;
                respaldo.setRGB(x, y, colorSRGB);
            }//fin de x    
        }//fin de y
        imageActual=respaldo;
        return imageActual;
   }
   //este metodo nos permite realizar el filtro pasa altas de Sobel 
   public BufferedImage filtroSobel(){
       this.respaldo();
       int kx1,kx2,kx3,kx4,kx5,kx6,kx7,kx8,kx9;
                kx1=1;kx2=0;kx3=-1;
                kx4=2;kx5=0;kx6=-2;
                kx7=1;kx8=0;kx9=-1;
        int ky1,ky2,ky3,ky4,ky5,ky6,ky7,ky8,ky9;
                ky1=-1;ky2=-2;ky3=-1;
                ky4=0;ky5=0;ky6=0;
                ky7=1;ky8=2;ky9=1;
        //variable que multiplica el kernel
        int R,G,B;
        float valor=4,sumaxR,sumaxG,sumaxB,sumayR,sumayG,sumayB;
       
        //pixeles rojos
        int[]pxr={0,0,0,0,0,0,0,0,0},pyr={0,0,0,0,0,0,0,0,0}; 
        //pixeles verdes
        int[]pxg={0,0,0,0,0,0,0,0,0},pyg={0,0,0,0,0,0,0,0,0}; 
        //pixeles azules
        int[]pxb={0,0,0,0,0,0,0,0,0},pyb={0,0,0,0,0,0,0,0,0}; 
        int colorSRGB;
        Color colorAux;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                sumaxR=sumaxG=sumaxB=sumayR=sumayG=sumayB=0;
                for(int i=0;i<9;i++){
                    pxr[i]=0;pxg[i]=0;pxb[i]=0;pyr[i]=0;pyg[i]=0;pyb[i]=0;
                }
                //condiciones para hacer la correlacion en la imagen
                if(y==0){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pxr[8]=pyr[8]=(int)colorAux.getRed();pxg[8]=pyg[8]=(int)colorAux.getGreen();pxb[8]=pyb[8]=(int)colorAux.getBlue();
                        pxr[8]*=kx9; pxg[8]*=kx9; pxb[8]*=kx9;pyr[8]*=ky9; pyg[8]*=ky9; pyb[8]*=ky9;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pxr[6]=pyr[6]=(int)colorAux.getRed();pxg[6]=pyg[6]=(int)colorAux.getGreen();pxb[6]=pyb[6]=(int)colorAux.getBlue();
                        pxr[6]*=kx7; pxg[6]*=kx7; pxb[6]*=kx7;pyr[6]*=ky7; pyg[6]*=ky7; pyb[6]*=ky7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pxr[8]=pyr[8]=(int)colorAux.getRed();pxg[8]=pyg[8]=(int)colorAux.getGreen();pxb[8]=pyb[8]=(int)colorAux.getBlue();
                        pxr[8]*=kx9; pxg[8]*=kx9; pxb[8]*=kx9;pyr[8]*=ky9; pyg[8]*=ky9; pyb[8]*=ky9;
                        
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pxr[6]=pyr[6]=(int)colorAux.getRed();pxg[6]=pyg[6]=(int)colorAux.getGreen();pxb[6]=pyb[6]=(int)colorAux.getBlue();
                        pxr[6]*=kx7; pxg[6]*=kx7; pxb[6]*=kx7;pyr[6]*=ky7; pyg[6]*=ky7; pyb[6]*=ky7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                    }
                }
                else if(y==imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pxr[2]=pyr[2]=(int)colorAux.getRed();pxg[2]=pyg[2]=(int)colorAux.getGreen();pxb[2]=pyb[2]=(int)colorAux.getBlue();
                        pxr[2]*=kx3; pxg[2]*=kx3; pxb[2]*=kx3;pyr[2]*=ky3; pyg[2]*=ky3; pyb[2]*=ky3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pxr[0]=pyr[0]=(int)colorAux.getRed();pxg[0]=pyg[0]=(int)colorAux.getGreen();pxb[0]=pyb[0]=(int)colorAux.getBlue();
                        pxr[0]*=kx1; pxg[0]*=kx1; pxb[0]*=kx1;pyr[0]*=ky1; pyg[0]*=ky1; pyb[0]*=ky1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pxr[2]=pyr[2]=(int)colorAux.getRed();pxg[2]=pyg[2]=(int)colorAux.getGreen();pxb[2]=pyb[2]=(int)colorAux.getBlue();
                        pxr[2]*=kx3; pxg[2]*=kx3; pxb[2]*=kx3;pyr[2]*=ky3; pyg[2]*=ky3; pyb[2]*=ky3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                    }
                    else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pxr[0]=pyr[0]=(int)colorAux.getRed();pxg[0]=pyg[0]=(int)colorAux.getGreen();pxb[0]=pyb[0]=(int)colorAux.getBlue();
                        pxr[0]*=kx1; pxg[0]*=kx1; pxb[0]*=kx1;pyr[0]*=ky1; pyg[0]*=ky1; pyb[0]*=ky1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pyb[5]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                    }
                }else if(y>0&&y<imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pxr[2]=pyr[2]=(int)colorAux.getRed();pxg[2]=pyg[2]=(int)colorAux.getGreen();pxb[2]=pyb[2]=(int)colorAux.getBlue();
                        pxr[2]*=kx3; pxg[2]*=kx3; pxb[2]*=kx3;pyr[2]*=ky3; pyg[2]*=ky3; pyb[2]*=ky3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pxr[8]=pyr[8]=(int)colorAux.getRed();pxg[8]=pyg[8]=(int)colorAux.getGreen();pxb[8]=pyb[8]=(int)colorAux.getBlue();
                        pxr[8]*=kx9; pxg[8]*=kx9; pxb[8]*=kx9;pyr[8]*=ky9; pyg[8]*=ky9; pyb[8]*=ky9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pxr[0]=pyr[0]=(int)colorAux.getRed();pxg[0]=pyg[0]=(int)colorAux.getGreen();pxb[0]=pyb[0]=(int)colorAux.getBlue();
                        pxr[0]*=kx1; pxg[0]*=kx1; pxb[0]*=kx1;pyr[0]*=ky1; pyg[0]*=ky1; pyb[0]*=ky1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pxr[6]=pyr[6]=(int)colorAux.getRed();pxg[6]=pyg[6]=(int)colorAux.getGreen();pxb[6]=pyb[6]=(int)colorAux.getBlue();
                        pxr[6]*=kx7; pxg[6]*=kx7; pxb[6]*=kx7;pyr[6]*=ky7; pyg[6]*=ky7; pyb[6]*=ky7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                    }else{
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pxr[0]=pyr[0]=(int)colorAux.getRed();pxg[0]=pyg[0]=(int)colorAux.getGreen();pxb[0]=pyb[0]=(int)colorAux.getBlue();
                        pxr[0]*=kx1; pxg[0]*=kx1; pxb[0]*=kx1;pyr[0]*=ky1; pyg[0]*=ky1; pyb[0]*=ky1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pxr[2]=pyr[2]=(int)colorAux.getRed();pxg[2]=pyg[2]=(int)colorAux.getGreen();pxb[2]=pyb[2]=(int)colorAux.getBlue();
                        pxr[2]*=kx3; pxg[2]*=kx3; pxb[2]*=kx3;pyr[2]*=ky3; pyg[2]*=ky3; pyb[2]*=ky3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();;
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pxr[6]=pyr[6]=(int)colorAux.getRed();pxg[6]=pyg[6]=(int)colorAux.getGreen();pxb[6]=pyb[6]=(int)colorAux.getBlue();
                        pxr[6]*=kx7; pxg[6]*=kx7; pxb[6]*=kx7;pyr[6]*=ky7; pyg[6]*=ky7; pyb[6]*=ky7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pxr[8]=pyr[8]=(int)colorAux.getRed();pxg[8]=pyg[8]=(int)colorAux.getGreen();pxb[8]=pyb[8]=(int)colorAux.getBlue();
                        pxr[8]*=kx9; pxg[8]*=kx9; pxb[8]*=kx9;pyr[8]*=ky9; pyg[8]*=ky9; pyb[8]*=ky9;
                    }
                }
                for(int i=0;i<9;i++){
                sumaxR+=pxr[i]; sumaxG+=pxg[i]; sumaxB+=pxb[i];
                sumayR+=pyr[i]; sumayG+=pyg[i]; sumayB+=pyb[i];
                }  
                float sumaR=(float)(1.0/valor)*(Math.abs(sumaxR)+Math.abs(sumayR));
                float sumaG=(float)(1.0/valor)*(Math.abs(sumaxG)+Math.abs(sumayG));
                float sumaB=(float)(1.0/valor)*(Math.abs(sumaxB)+Math.abs(sumayB));
                if(sumaR>255) sumaR=255;
                if(sumaG>255) sumaG=255;
                if(sumaB>255) sumaB=255;

                R=(int)sumaR; G=(int)sumaG; B=(int)sumaB;
                colorSRGB=R<<16|G<<8|B;
                respaldo.setRGB(x, y, colorSRGB);
            }//fin de x    
        }//fin de y
        imageActual=respaldo;
        return imageActual;
   }
   //este metodo nos permite realizar el filtro pasa altas de Prewitt 
   public BufferedImage filtroPrewitt(){
       this.respaldo();
       int kx1,kx2,kx3,kx4,kx5,kx6,kx7,kx8,kx9;
                kx1=1;kx2=0;kx3=-1;
                kx4=1;kx5=0;kx6=-1;
                kx7=1;kx8=0;kx9=-1;
        int ky1,ky2,ky3,ky4,ky5,ky6,ky7,ky8,ky9;
                ky1=-1;ky2=-1;ky3=-1;
                ky4=0;ky5=0;ky6=0;
                ky7=1;ky8=1;ky9=1;
        //variable que multiplica el kernel
        int R,G,B;
        float valor=3,sumaxR,sumaxG,sumaxB,sumayR,sumayG,sumayB;
       
        //pixeles rojos
        int[]pxr={0,0,0,0,0,0,0,0,0},pyr={0,0,0,0,0,0,0,0,0}; 
        //pixeles verdes
        int[]pxg={0,0,0,0,0,0,0,0,0},pyg={0,0,0,0,0,0,0,0,0}; 
        //pixeles azules
        int[]pxb={0,0,0,0,0,0,0,0,0},pyb={0,0,0,0,0,0,0,0,0}; 
        int colorSRGB;
        Color colorAux;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                sumaxR=sumaxG=sumaxB=sumayR=sumayG=sumayB=0;
                for(int i=0;i<9;i++){
                    pxr[i]=0;pxg[i]=0;pxb[i]=0;pyr[i]=0;pyg[i]=0;pyb[i]=0;
                }
                //condiciones para hacer la correlacion en la imagen
                if(y==0){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pxr[8]=pyr[8]=(int)colorAux.getRed();pxg[8]=pyg[8]=(int)colorAux.getGreen();pxb[8]=pyb[8]=(int)colorAux.getBlue();
                        pxr[8]*=kx9; pxg[8]*=kx9; pxb[8]*=kx9;pyr[8]*=ky9; pyg[8]*=ky9; pyb[8]*=ky9;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pxr[6]=pyr[6]=(int)colorAux.getRed();pxg[6]=pyg[6]=(int)colorAux.getGreen();pxb[6]=pyb[6]=(int)colorAux.getBlue();
                        pxr[6]*=kx7; pxg[6]*=kx7; pxb[6]*=kx7;pyr[6]*=ky7; pyg[6]*=ky7; pyb[6]*=ky7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pxr[8]=pyr[8]=(int)colorAux.getRed();pxg[8]=pyg[8]=(int)colorAux.getGreen();pxb[8]=pyb[8]=(int)colorAux.getBlue();
                        pxr[8]*=kx9; pxg[8]*=kx9; pxb[8]*=kx9;pyr[8]*=ky9; pyg[8]*=ky9; pyb[8]*=ky9;
                        
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pxr[6]=pyr[6]=(int)colorAux.getRed();pxg[6]=pyg[6]=(int)colorAux.getGreen();pxb[6]=pyb[6]=(int)colorAux.getBlue();
                        pxr[6]*=kx7; pxg[6]*=kx7; pxb[6]*=kx7;pyr[6]*=ky7; pyg[6]*=ky7; pyb[6]*=ky7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                    }
                }
                else if(y==imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pxr[2]=pyr[2]=(int)colorAux.getRed();pxg[2]=pyg[2]=(int)colorAux.getGreen();pxb[2]=pyb[2]=(int)colorAux.getBlue();
                        pxr[2]*=kx3; pxg[2]*=kx3; pxb[2]*=kx3;pyr[2]*=ky3; pyg[2]*=ky3; pyb[2]*=ky3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pxr[0]=pyr[0]=(int)colorAux.getRed();pxg[0]=pyg[0]=(int)colorAux.getGreen();pxb[0]=pyb[0]=(int)colorAux.getBlue();
                        pxr[0]*=kx1; pxg[0]*=kx1; pxb[0]*=kx1;pyr[0]*=ky1; pyg[0]*=ky1; pyb[0]*=ky1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pxr[2]=pyr[2]=(int)colorAux.getRed();pxg[2]=pyg[2]=(int)colorAux.getGreen();pxb[2]=pyb[2]=(int)colorAux.getBlue();
                        pxr[2]*=kx3; pxg[2]*=kx3; pxb[2]*=kx3;pyr[2]*=ky3; pyg[2]*=ky3; pyb[2]*=ky3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                    }
                    else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pxr[0]=pyr[0]=(int)colorAux.getRed();pxg[0]=pyg[0]=(int)colorAux.getGreen();pxb[0]=pyb[0]=(int)colorAux.getBlue();
                        pxr[0]*=kx1; pxg[0]*=kx1; pxb[0]*=kx1;pyr[0]*=ky1; pyg[0]*=ky1; pyb[0]*=ky1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pyb[5]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                    }
                }else if(y>0&&y<imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pxr[2]=pyr[2]=(int)colorAux.getRed();pxg[2]=pyg[2]=(int)colorAux.getGreen();pxb[2]=pyb[2]=(int)colorAux.getBlue();
                        pxr[2]*=kx3; pxg[2]*=kx3; pxb[2]*=kx3;pyr[2]*=ky3; pyg[2]*=ky3; pyb[2]*=ky3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pxr[8]=pyr[8]=(int)colorAux.getRed();pxg[8]=pyg[8]=(int)colorAux.getGreen();pxb[8]=pyb[8]=(int)colorAux.getBlue();
                        pxr[8]*=kx9; pxg[8]*=kx9; pxb[8]*=kx9;pyr[8]*=ky9; pyg[8]*=ky9; pyb[8]*=ky9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pxr[0]=pyr[0]=(int)colorAux.getRed();pxg[0]=pyg[0]=(int)colorAux.getGreen();pxb[0]=pyb[0]=(int)colorAux.getBlue();
                        pxr[0]*=kx1; pxg[0]*=kx1; pxb[0]*=kx1;pyr[0]*=ky1; pyg[0]*=ky1; pyb[0]*=ky1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pxr[6]=pyr[6]=(int)colorAux.getRed();pxg[6]=pyg[6]=(int)colorAux.getGreen();pxb[6]=pyb[6]=(int)colorAux.getBlue();
                        pxr[6]*=kx7; pxg[6]*=kx7; pxb[6]*=kx7;pyr[6]*=ky7; pyg[6]*=ky7; pyb[6]*=ky7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                    }else{
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pxr[0]=pyr[0]=(int)colorAux.getRed();pxg[0]=pyg[0]=(int)colorAux.getGreen();pxb[0]=pyb[0]=(int)colorAux.getBlue();
                        pxr[0]*=kx1; pxg[0]*=kx1; pxb[0]*=kx1;pyr[0]*=ky1; pyg[0]*=ky1; pyb[0]*=ky1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pxr[2]=pyr[2]=(int)colorAux.getRed();pxg[2]=pyg[2]=(int)colorAux.getGreen();pxb[2]=pyb[2]=(int)colorAux.getBlue();
                        pxr[2]*=kx3; pxg[2]*=kx3; pxb[2]*=kx3;pyr[2]*=ky3; pyg[2]*=ky3; pyb[2]*=ky3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();;
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pxr[6]=pyr[6]=(int)colorAux.getRed();pxg[6]=pyg[6]=(int)colorAux.getGreen();pxb[6]=pyb[6]=(int)colorAux.getBlue();
                        pxr[6]*=kx7; pxg[6]*=kx7; pxb[6]*=kx7;pyr[6]*=ky7; pyg[6]*=ky7; pyb[6]*=ky7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pxr[8]=pyr[8]=(int)colorAux.getRed();pxg[8]=pyg[8]=(int)colorAux.getGreen();pxb[8]=pyb[8]=(int)colorAux.getBlue();
                        pxr[8]*=kx9; pxg[8]*=kx9; pxb[8]*=kx9;pyr[8]*=ky9; pyg[8]*=ky9; pyb[8]*=ky9;
                    }
                }
                for(int i=0;i<9;i++){
                sumaxR+=pxr[i]; sumaxG+=pxg[i]; sumaxB+=pxb[i];
                sumayR+=pyr[i]; sumayG+=pyg[i]; sumayB+=pyb[i];
                }  
                float sumaR=(float)(1.0/valor)*(Math.abs(sumaxR)+Math.abs(sumayR));
                float sumaG=(float)(1.0/valor)*(Math.abs(sumaxG)+Math.abs(sumayG));
                float sumaB=(float)(1.0/valor)*(Math.abs(sumaxB)+Math.abs(sumayB));
                if(sumaR>255) sumaR=255;
                if(sumaG>255) sumaG=255;
                if(sumaB>255) sumaB=255;

                R=(int)sumaR; G=(int)sumaG; B=(int)sumaB;
                colorSRGB=R<<16|G<<8|B;
                respaldo.setRGB(x, y, colorSRGB);
            }//fin de x    
        }//fin de y
        imageActual=respaldo;
        return imageActual;
   }
   //este metodo nos permite realizar el filtro pasa altas de Robert 
   public BufferedImage filtroRobert(){
       this.respaldo();
       int kx1,kx2,kx3,kx4,kx5,kx6,kx7,kx8,kx9;
                kx1=-1;kx2=0;kx3=0;
                kx4=0;kx5=1;kx6=0;
                kx7=0;kx8=0;kx9=0;
        int ky1,ky2,ky3,ky4,ky5,ky6,ky7,ky8,ky9;
                ky1=0;ky2=0;ky3=-1;
                ky4=0;ky5=1;ky6=0;
                ky7=0;ky8=0;ky9=0;
        //variable que multiplica el kernel
        int R,G,B;
        float valor=1,sumaxR,sumaxG,sumaxB,sumayR,sumayG,sumayB;
       
        //pixeles rojos
        int[]pxr={0,0,0,0,0,0,0,0,0},pyr={0,0,0,0,0,0,0,0,0}; 
        //pixeles verdes
        int[]pxg={0,0,0,0,0,0,0,0,0},pyg={0,0,0,0,0,0,0,0,0}; 
        //pixeles azules
        int[]pxb={0,0,0,0,0,0,0,0,0},pyb={0,0,0,0,0,0,0,0,0}; 
        int colorSRGB;
        Color colorAux;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                sumaxR=sumaxG=sumaxB=sumayR=sumayG=sumayB=0;
                for(int i=0;i<9;i++){
                    pxr[i]=0;pxg[i]=0;pxb[i]=0;pyr[i]=0;pyg[i]=0;pyb[i]=0;
                }
                //condiciones para hacer la correlacion en la imagen
                if(y==0){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pxr[8]=pyr[8]=(int)colorAux.getRed();pxg[8]=pyg[8]=(int)colorAux.getGreen();pxb[8]=pyb[8]=(int)colorAux.getBlue();
                        pxr[8]*=kx9; pxg[8]*=kx9; pxb[8]*=kx9;pyr[8]*=ky9; pyg[8]*=ky9; pyb[8]*=ky9;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pxr[6]=pyr[6]=(int)colorAux.getRed();pxg[6]=pyg[6]=(int)colorAux.getGreen();pxb[6]=pyb[6]=(int)colorAux.getBlue();
                        pxr[6]*=kx7; pxg[6]*=kx7; pxb[6]*=kx7;pyr[6]*=ky7; pyg[6]*=ky7; pyb[6]*=ky7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pxr[8]=pyr[8]=(int)colorAux.getRed();pxg[8]=pyg[8]=(int)colorAux.getGreen();pxb[8]=pyb[8]=(int)colorAux.getBlue();
                        pxr[8]*=kx9; pxg[8]*=kx9; pxb[8]*=kx9;pyr[8]*=ky9; pyg[8]*=ky9; pyb[8]*=ky9;
                        
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pxr[6]=pyr[6]=(int)colorAux.getRed();pxg[6]=pyg[6]=(int)colorAux.getGreen();pxb[6]=pyb[6]=(int)colorAux.getBlue();
                        pxr[6]*=kx7; pxg[6]*=kx7; pxb[6]*=kx7;pyr[6]*=ky7; pyg[6]*=ky7; pyb[6]*=ky7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                    }
                }
                else if(y==imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pxr[2]=pyr[2]=(int)colorAux.getRed();pxg[2]=pyg[2]=(int)colorAux.getGreen();pxb[2]=pyb[2]=(int)colorAux.getBlue();
                        pxr[2]*=kx3; pxg[2]*=kx3; pxb[2]*=kx3;pyr[2]*=ky3; pyg[2]*=ky3; pyb[2]*=ky3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pxr[0]=pyr[0]=(int)colorAux.getRed();pxg[0]=pyg[0]=(int)colorAux.getGreen();pxb[0]=pyb[0]=(int)colorAux.getBlue();
                        pxr[0]*=kx1; pxg[0]*=kx1; pxb[0]*=kx1;pyr[0]*=ky1; pyg[0]*=ky1; pyb[0]*=ky1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pxr[2]=pyr[2]=(int)colorAux.getRed();pxg[2]=pyg[2]=(int)colorAux.getGreen();pxb[2]=pyb[2]=(int)colorAux.getBlue();
                        pxr[2]*=kx3; pxg[2]*=kx3; pxb[2]*=kx3;pyr[2]*=ky3; pyg[2]*=ky3; pyb[2]*=ky3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                    }
                    else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pxr[0]=pyr[0]=(int)colorAux.getRed();pxg[0]=pyg[0]=(int)colorAux.getGreen();pxb[0]=pyb[0]=(int)colorAux.getBlue();
                        pxr[0]*=kx1; pxg[0]*=kx1; pxb[0]*=kx1;pyr[0]*=ky1; pyg[0]*=ky1; pyb[0]*=ky1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pyb[5]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                    }
                }else if(y>0&&y<imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pxr[2]=pyr[2]=(int)colorAux.getRed();pxg[2]=pyg[2]=(int)colorAux.getGreen();pxb[2]=pyb[2]=(int)colorAux.getBlue();
                        pxr[2]*=kx3; pxg[2]*=kx3; pxb[2]*=kx3;pyr[2]*=ky3; pyg[2]*=ky3; pyb[2]*=ky3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pxr[8]=pyr[8]=(int)colorAux.getRed();pxg[8]=pyg[8]=(int)colorAux.getGreen();pxb[8]=pyb[8]=(int)colorAux.getBlue();
                        pxr[8]*=kx9; pxg[8]*=kx9; pxb[8]*=kx9;pyr[8]*=ky9; pyg[8]*=ky9; pyb[8]*=ky9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pxr[0]=pyr[0]=(int)colorAux.getRed();pxg[0]=pyg[0]=(int)colorAux.getGreen();pxb[0]=pyb[0]=(int)colorAux.getBlue();
                        pxr[0]*=kx1; pxg[0]*=kx1; pxb[0]*=kx1;pyr[0]*=ky1; pyg[0]*=ky1; pyb[0]*=ky1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pxr[6]=pyr[6]=(int)colorAux.getRed();pxg[6]=pyg[6]=(int)colorAux.getGreen();pxb[6]=pyb[6]=(int)colorAux.getBlue();
                        pxr[6]*=kx7; pxg[6]*=kx7; pxb[6]*=kx7;pyr[6]*=ky7; pyg[6]*=ky7; pyb[6]*=ky7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                    }else{
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pxr[0]=pyr[0]=(int)colorAux.getRed();pxg[0]=pyg[0]=(int)colorAux.getGreen();pxb[0]=pyb[0]=(int)colorAux.getBlue();
                        pxr[0]*=kx1; pxg[0]*=kx1; pxb[0]*=kx1;pyr[0]*=ky1; pyg[0]*=ky1; pyb[0]*=ky1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pxr[1]=pyr[1]=(int)colorAux.getRed();pxg[1]=pyg[1]=(int)colorAux.getGreen();pxb[1]=pyb[1]=(int)colorAux.getBlue();
                        pxr[1]*=kx2; pxg[1]*=kx2; pxb[1]*=kx2;pyr[1]*=ky2; pyg[1]*=ky2; pyb[1]*=ky2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pxr[2]=pyr[2]=(int)colorAux.getRed();pxg[2]=pyg[2]=(int)colorAux.getGreen();pxb[2]=pyb[2]=(int)colorAux.getBlue();
                        pxr[2]*=kx3; pxg[2]*=kx3; pxb[2]*=kx3;pyr[2]*=ky3; pyg[2]*=ky3; pyb[2]*=ky3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pxr[3]=pyr[3]=(int)colorAux.getRed();pxg[3]=pyg[3]=(int)colorAux.getGreen();pxb[3]=pyb[3]=(int)colorAux.getBlue();
                        pxr[3]*=kx4; pxg[3]*=kx4; pxb[3]*=kx4;pyr[3]*=ky4; pyg[3]*=ky4; pyb[3]*=ky4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pxr[4]=pyr[4]=(int)colorAux.getRed();pxg[4]=pyg[4]=(int)colorAux.getGreen();pxb[4]=pyb[4]=(int)colorAux.getBlue();
                        pxr[4]*=kx5; pxg[4]*=kx5; pxb[4]*=kx5;pyr[4]*=ky5; pyg[4]*=ky5; pyb[4]*=ky5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pxr[5]=pyr[5]=(int)colorAux.getRed();pxg[5]=pyg[5]=(int)colorAux.getGreen();pxb[5]=pyb[5]=(int)colorAux.getBlue();;
                        pxr[5]*=kx6; pxg[5]*=kx6; pxb[5]*=kx6;pyr[5]*=ky6; pyg[5]*=ky6; pyb[5]*=ky6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pxr[6]=pyr[6]=(int)colorAux.getRed();pxg[6]=pyg[6]=(int)colorAux.getGreen();pxb[6]=pyb[6]=(int)colorAux.getBlue();
                        pxr[6]*=kx7; pxg[6]*=kx7; pxb[6]*=kx7;pyr[6]*=ky7; pyg[6]*=ky7; pyb[6]*=ky7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pxr[7]=pyr[7]=(int)colorAux.getRed();pxg[7]=pyg[7]=(int)colorAux.getGreen();pxb[7]=pyb[7]=(int)colorAux.getBlue();
                        pxr[7]*=kx8; pxg[7]*=kx8; pxb[7]*=kx8;pyr[7]*=ky8; pyg[7]*=ky8; pyb[7]*=ky8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pxr[8]=pyr[8]=(int)colorAux.getRed();pxg[8]=pyg[8]=(int)colorAux.getGreen();pxb[8]=pyb[8]=(int)colorAux.getBlue();
                        pxr[8]*=kx9; pxg[8]*=kx9; pxb[8]*=kx9;pyr[8]*=ky9; pyg[8]*=ky9; pyb[8]*=ky9;
                    }
                }
                for(int i=0;i<9;i++){
                sumaxR+=pxr[i]; sumaxG+=pxg[i]; sumaxB+=pxb[i];
                sumayR+=pyr[i]; sumayG+=pyg[i]; sumayB+=pyb[i];
                }  
                float sumaR=(float)(1.0/valor)*(Math.abs(sumaxR)+Math.abs(sumayR));
                float sumaG=(float)(1.0/valor)*(Math.abs(sumaxG)+Math.abs(sumayG));
                float sumaB=(float)(1.0/valor)*(Math.abs(sumaxB)+Math.abs(sumayB));
                if(sumaR>255) sumaR=255;
                if(sumaG>255) sumaG=255;
                if(sumaB>255) sumaB=255;

                R=(int)sumaR; G=(int)sumaG; B=(int)sumaB;
                colorSRGB=R<<16|G<<8|B;
                respaldo.setRGB(x, y, colorSRGB);
            }//fin de x    
        }//fin de y
        imageActual=respaldo;
        return imageActual;
   }
   //este metodo nos permite realizar el filtro pasa altas Laplaciano 
   public BufferedImage filtroLaplaciano(){
       this.respaldo();
       int k1,k2,k3,k4,k5,k6,k7,k8,k9;
                k1=0;k2=1;k3=0;
                k4=1;k5=-4;k6=1;
                k7=0;k8=1;k9=0;
        //variable que multiplica el kernel
        int R,G,B;
        float valor=1,sumaR,sumaG,sumaB;
        //pixeles que van a multiplicarse
        //pixeles rojos
        int[]pr={128,128,128,128,128,128,128,128,128}; 
        //pixeles verdes
        int[]pg={128,128,128,128,128,128,128,128,128};
        //pixeles azules
        int[]pb={128,128,128,128,128,128,128,128,128};
        int colorSRGB;
        Color colorAux;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                sumaR=sumaG=sumaB=0;
                for(int i=0;i<9;i++){
                    pr[i]=255;pg[i]=255;pb[i]=255;
                }
                //condiciones para hacer la correlacion en la imagen
                if(y==0){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                    }
                }
                else if(y==imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                    }
                    else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                    }
                }else if(y>0&&y<imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                    }else{
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();;
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }
                }
                for(int i=0;i<9;i++){
                    sumaR+=pr[i]; sumaG+=pg[i]; sumaB+=pb[i];
                }
                sumaR=(float)(1.0/valor)*sumaR;
                sumaG=(float)(1.0/valor)*sumaG;
                sumaB=(float)(1.0/valor)*sumaB;
                if(sumaR>255) sumaR=255;
                if(sumaG>255) sumaG=255;
                if(sumaB>255) sumaB=255;
                R=(int)sumaR; G=(int)sumaG; B=(int)sumaB;
                colorSRGB=R<<16|G<<8|B;
                respaldo.setRGB(x, y, colorSRGB);
            }//fin de x    
        }//fin de y
        imageActual=respaldo;
        return imageActual;
   }
   //este metodo nos permite realizar el filtro promedio pesado 
   public BufferedImage filtroPromedioPesado(){
       this.respaldo();
        float valor=16,sumaR,sumaG,sumaB;
        int R,G,B;
       int k1,k2,k3,k4,k5,k6,k7,k8,k9;
                k1=1;k2=1;k3=1;
                k4=1;k5=8+(int)(valor);k6=1;
                k7=1;k8=1;k9=1;
        //pixeles que van a multiplicarse
        //pixeles rojos
        int[]pr={128,128,128,128,128,128,128,128,128}; 
        //pixeles verdes
        int[]pg={128,128,128,128,128,128,128,128,128};
        //pixeles azules
        int[]pb={128,128,128,128,128,128,128,128,128};
        int colorSRGB;
        Color colorAux;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                sumaR=sumaG=sumaB=0;
                for(int i=0;i<9;i++){
                    pr[i]=255;pg[i]=255;pb[i]=255;
                }
                //condiciones para hacer la correlacion en la imagen
                if(y==0){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                    }
                }
                else if(y==imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                    }else if(x<imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                    }
                    else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                    }
                }else if(y>0&&y<imageActual.getHeight()-1){
                    if(x==0){
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }else if(x==imageActual.getWidth()-1){
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                    }else{
                        colorAux=new Color(this.imageActual.getRGB(x-1, y-1));
                        pr[0]=(int)colorAux.getRed();pg[0]=(int)colorAux.getGreen();pb[0]=(int)colorAux.getBlue();
                        pr[0]*=k1; pg[0]*=k1; pb[0]*=k1;
                        colorAux=new Color(this.imageActual.getRGB(x, y-1));
                        pr[1]=(int)colorAux.getRed();pg[1]=(int)colorAux.getGreen();pb[1]=(int)colorAux.getBlue();
                        pr[1]*=k2; pg[1]*=k2; pb[1]*=k2;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y-1));
                        pr[2]=(int)colorAux.getRed();pg[2]=(int)colorAux.getGreen();pb[2]=(int)colorAux.getBlue();
                        pr[2]*=k3; pg[2]*=k3; pb[2]*=k3;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y));
                        pr[3]=(int)colorAux.getRed();pg[3]=(int)colorAux.getGreen();pb[3]=(int)colorAux.getBlue();
                        pr[3]*=k4; pg[3]*=k4; pb[3]*=k4;
                        colorAux=new Color(this.imageActual.getRGB(x, y));
                        pr[4]=(int)colorAux.getRed();pg[4]=(int)colorAux.getGreen();pb[4]=(int)colorAux.getBlue();
                        pr[4]*=k5; pg[4]*=k5; pb[4]*=k5;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y));
                        pr[5]=(int)colorAux.getRed();pg[5]=(int)colorAux.getGreen();pb[5]=(int)colorAux.getBlue();;
                        pr[5]*=k6; pg[5]*=k6; pb[5]*=k6;
                        colorAux=new Color(this.imageActual.getRGB(x-1, y+1));
                        pr[6]=(int)colorAux.getRed();pg[6]=(int)colorAux.getGreen();pb[6]=(int)colorAux.getBlue();
                        pr[6]*=k7; pg[6]*=k7; pb[6]*=k7;
                        colorAux=new Color(this.imageActual.getRGB(x, y+1));
                        pr[7]=(int)colorAux.getRed();pg[7]=(int)colorAux.getGreen();pb[7]=(int)colorAux.getBlue();
                        pr[7]*=k8; pg[7]*=k8; pb[7]*=k8;
                        colorAux=new Color(this.imageActual.getRGB(x+1, y+1));
                        pr[8]=(int)colorAux.getRed();pg[8]=(int)colorAux.getGreen();pb[8]=(int)colorAux.getBlue();
                        pr[8]*=k9; pg[8]*=k9; pb[8]*=k9;
                    }
                }
                for(int i=0;i<9;i++){
                    sumaR+=pr[i]; sumaG+=pg[i]; sumaB+=pb[i];
                }
                sumaR=(float)(1.0/valor)*sumaR;
                sumaG=(float)(1.0/valor)*sumaG;
                sumaB=(float)(1.0/valor)*sumaB;
                if(sumaR>255) sumaR=255;
                if(sumaG>255) sumaG=255;
                if(sumaB>255) sumaB=255;
                R=(int)sumaR; G=(int)sumaG; B=(int)sumaB;
                colorSRGB=R<<16|G<<8|B;
                respaldo.setRGB(x, y, colorSRGB);
            }//fin de x    
        }//fin de y
        imageActual=respaldo;
        return imageActual;
   }
    /*******************************************************************************************************/
    /*                              Propiedades del histograma                                             */
    /*******************************************************************************************************/
    //Este metodo permite ajustar el brillo de una imagen, como propiedad se define: desplazamiento del histograma
    public BufferedImage brillo(int brillo){
        int R,G,B,nR,nG,nB;
        int colorSRGB;
        Color colorAux = null;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                colorAux=new Color(imageActual.getRGB(x, y));
                 R=(int)colorAux.getRed();
                 G=(int)colorAux.getGreen();
                 B=(int)colorAux.getBlue();
                 //sumamos el brillo a cada valor
                 nR=R+brillo;
                 nG=G+brillo;
                 nB=B+brillo;
                 if(nR>255)nR=255;
                 if(nG>255)nG=255;
                 if(nB>255)nB=255;
                 if(nR<0)nR=0;
                 if(nG<0)nG=0;
                 if(nB<0)nB=0;
                 colorSRGB=(nR<<16)|(nG<<8)|(nB);
                 respaldo.setRGB(x,y,colorSRGB);
            }
        }
        return respaldo;
    }
    //este metodo nos permite realizar la expancion del histograma, se toman como valor maximo 255 y minimo 0
    public BufferedImage expansion(){
        int R,G,B,nR,nG,nB;
        int colorSRGB;
        this.respaldo();
        //maximo y minimo de la imagen de entrada(RGB)
        float fminimoR,fmaximoR,fminimoG,fmaximoG,fminimoB,fmaximoB;
        //maximo y minimo ideal
        int max=255,min=0;
        Color colorAux = null;
        //inicializamos los maximos y minomos RGB
        fminimoR=fminimoG=fminimoB=255;
        fmaximoR=fmaximoG=fmaximoB=0;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                colorAux=new Color(imageActual.getRGB(x, y));
                 R=(int)colorAux.getRed();
                 G=(int)colorAux.getGreen();
                 B=(int)colorAux.getBlue();
                 
                if(R>fmaximoR)fmaximoR=R;
                if(G>fmaximoG)fmaximoG=G;
                if(B>fmaximoB)fmaximoB=B;
                
                if(R<fminimoR)fminimoR=R;
                if(G<fminimoG)fminimoG=G;
                if(B<fminimoB)fminimoB=B;
            }
        }
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                colorAux=new Color(imageActual.getRGB(x, y));
                 R=(int)colorAux.getRed();
                 G=(int)colorAux.getGreen();
                 B=(int)colorAux.getBlue();
                 //sumamos el brillo a cada valor
                 nR=(int)(((R-fminimoR)/(fmaximoR-fminimoR))*(max-min))+min;
                 nG=(int)(((G-fminimoG)/(fmaximoG-fminimoG))*(max-min))+min;
                 nB=(int)(((B-fminimoB)/(fmaximoB-fminimoB))*(max-min))+min;
                 if(nR>255)nR=255;
                 if(nG>255)nG=255;
                 if(nB>255)nB=255;
                 if(nR<0)nR=0;
                 if(nG<0)nG=0;
                 if(nB<0)nB=0;
                 colorSRGB=(nR<<16)|(nG<<8)|(nB);
                 respaldo.setRGB(x,y,colorSRGB);
            }
        }
        imageActual=respaldo;
        return imageActual;
    }
    //este metodo nos permite realizar la contracccion del histograma, se manda como parametro el valor maximo y minimo
    public BufferedImage contraccion(int valmax,int valmin){
        int R,G,B,nR,nG,nB;
        int colorSRGB;
        this.respaldo();
        //maximo y minimo de la imagen de entrada(RGB)
        float fminimoR,fmaximoR,fminimoG,fmaximoG,fminimoB,fmaximoB;
        //maximo y minimo ideal
        int max=valmax,min=valmin;
        Color colorAux = null;
        //inicializamos los maximos y minomos RGB
        fminimoR=fminimoG=fminimoB=255;
        fmaximoR=fmaximoG=fmaximoB=0;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                colorAux=new Color(imageActual.getRGB(x, y));
                 R=(int)colorAux.getRed();
                 G=(int)colorAux.getGreen();
                 B=(int)colorAux.getBlue();
                 
                if(R>fmaximoR)fmaximoR=R;
                if(G>fmaximoG)fmaximoG=G;
                if(B>fmaximoB)fmaximoB=B;
                
                if(R<fminimoR)fminimoR=R;
                if(G<fminimoG)fminimoG=G;
                if(B<fminimoB)fminimoB=B;
            }
        }
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                colorAux=new Color(imageActual.getRGB(x, y));
                 R=(int)colorAux.getRed();
                 G=(int)colorAux.getGreen();
                 B=(int)colorAux.getBlue();
                 //sumamos el brillo a cada valor
                 nR=(int)(((max-min)/(fmaximoR-fminimoR))*(R-fminimoR))+min;
                 nG=(int)(((max-min)/(fmaximoG-fminimoG))*(G-fminimoG))+min;
                 nB=(int)(((max-min)/(fmaximoB-fminimoB))*(B-fminimoB))+min;
                 if(nR>255)nR=255;
                 if(nG>255)nG=255;
                 if(nB>255)nB=255;
                 if(nR<0)nR=0;
                 if(nG<0)nG=0;
                 if(nB<0)nB=0;
                 colorSRGB=(nR<<16)|(nG<<8)|(nB);
                 respaldo.setRGB(x,y,colorSRGB);
            }
        }
        imageActual=respaldo;
        return imageActual;
    }
    //este metodo nos permite realizar la ecualizacion uniforme, toma como valor maximo 255 y minimo 0
    public BufferedImage ecualizacion(){
        int [][] histo=this.histograma(imageActual);
        float [][]pg=new float[3][256],pacumulada=new float[3][256];
        float sumaR=0,sumaG=0,sumaB=0;
        int R,G,B,nR,nG,nB;
        int colorSRGB;
        this.respaldo();
        //maximo y minimo de la imagen de entrada(RGB)
        float fminimoR,fmaximoR,fminimoG,fmaximoG,fminimoB,fmaximoB;
        //maximo y minimo ideal
        int max=255,min=0;
        Color colorAux = null;
        //calculamos las probabilidades de los niveles de gris
        for(int i=0;i<256;i++){
               pg[0][i]=(float)histo[0][i]/(imageActual.getHeight()*imageActual.getWidth());
               pg[1][i]=(float)histo[1][i]/(imageActual.getHeight()*imageActual.getWidth());
               pg[2][i]=(float)histo[2][i]/(imageActual.getHeight()*imageActual.getWidth());
               sumaR+=pg[0][i];pacumulada[0][i]=sumaR;
               sumaG+=pg[1][i];pacumulada[1][i]=sumaG;
               sumaB+=pg[2][i];pacumulada[2][i]=sumaB;
        }
        //obtenemos probabilidades acumuladas
        for(int i=0;i<256;i++)
            System.out.println("p acumulada R"+i+":  "+pacumulada[0][i]);
        //inicializamos los maximos y minomos RGB
        fminimoR=fminimoG=fminimoB=255;
        fmaximoR=fmaximoG=fmaximoB=0;
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                colorAux=new Color(imageActual.getRGB(x, y));
                 R=(int)colorAux.getRed();
                 G=(int)colorAux.getGreen();
                 B=(int)colorAux.getBlue();     
                if(R>fmaximoR)fmaximoR=R;
                if(G>fmaximoG)fmaximoG=G;
                if(B>fmaximoB)fmaximoB=B;
                if(R<fminimoR)fminimoR=R;
                if(G<fminimoG)fminimoG=G;
                if(B<fminimoB)fminimoB=B;
            }
        }
        for(int y=0;y<imageActual.getHeight();y++){
            for(int x=0;x<imageActual.getWidth();x++){
                colorAux=new Color(imageActual.getRGB(x, y));
                 R=(int)colorAux.getRed();
                 G=(int)colorAux.getGreen();
                 B=(int)colorAux.getBlue();
                 //sumamos el brillo a cada valor
                 nR=(int)(((fmaximoR-fminimoR)*pacumulada[0][R])+fminimoR); 
                 nG=(int)((fmaximoG-fminimoG)*pacumulada[1][G]+fminimoG);
                 nB=(int)((fmaximoB-fminimoB)*pacumulada[2][B]+fminimoB);
                 if(nR>255)nR=255;
                 if(nG>255)nG=255;
                 if(nB>255)nB=255;
                 if(nR<0)nR=0;
                 if(nG<0)nG=0;
                 if(nB<0)nB=0;
                 colorSRGB=(nR<<16)|(nG<<8)|(nB);
                 respaldo.setRGB(x,y,colorSRGB);
            }System.out.println("");
        }
        imageActual=respaldo;
        return imageActual;
    }
}
    