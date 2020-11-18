package npi.betweenstudents;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

//Extiende de SQLiteOpenHelper para poder administrar bases de datos en Android
public class BaseDatos extends SQLiteOpenHelper {
    private static String nomBD = "BetweenStudents.db";
    private static int version = 10; //Version de la base de datos (Si el valor cambia, se llama a actualizar, UPDGRADE
    private String sentencia; // Para actualizar los datos en el UPDATE

    // Valores de la Tabla FACULTADES
    //##################################################################################################################################
    private String TABLE_FAC_NAME;
    private String KEY_FAC_ID;
    private String KEY_FAC_NOMBRE;
    private String KEY_FAC_UBICACION;
    private String KEY_FAC_IMAGEN;
    private String KEY_FAC_HORARIO;
    private String KEY_FAC_CAMPUS;
    private String KEY__FAC_WEB;
    //##################################################################################################################################


    // Valores de la Tabla CAMPUS
    //##################################################################################################################################
    private String TABLE_CAMP_NAME;
    private String KEY_CAMP_ID;
    private String KEY_CAMP_NOMBRE;
    private String KEY_CAMP_IMAGE;
    //##################################################################################################################################


    //Se definen la sentencia para la creación de cada TABLE
    //##################################################################################################################################
    private String CREATE_TABLE_CAMPUS;
    private String CREATE_TABLE_FACULTADES;
    //##################################################################################################################################


    // Se define la sentencia para cada INSERT en CAMPUS
    //##################################################################################################################################
    private String INSERT_CENTRO_INTO_CAMPUS;
    private String INSERT_CARTUJA_INTO_CAMPUS;
    private String INSERT_FUENTENUEVA_INTO_CAMPUS;
    private String INSERT_AYNADAMAR_INTO_CAMPUS;
    private String INSERT_PTS_INTO_CAMPUS;
    private String INSERT_CEUTAMELILLA_INTO_CAMPUS;
    //##################################################################################################################################


    // Se define la sentencia para cad INSERT en FACULTADES
    //##################################################################################################################################
    private String INSERT_ARQUITECTURA_INTO_FACULTADES;
    private String INSERT_TRABAJO_INTO_FACULTADES;
    private String INSERT_POLITICAS_INTO_FACULTADES;
    private String INSERT_DERECHO_INTO_FACULTADES;
    private String INSERT_TRABAJOSOCIAL_INTO_FACULTADES;
    private String INSERT_TRADUCCION_INTO_FACULTADES;
    private String INSERT_DEPORTE_INTO_FACULTADES;
    private String INSERT_EDUCACION_INTO_FACULTADES;
    private String INSERT_ECONOMIA_INTO_FACULTADES;
    private String INSERT_COMUNICACION_INTO_FACULTADES;
    private String INSERT_FARMACIA_INTO_FACULTADES;
    private String INSERT_FILOSOFIA_INTO_FACULTADES;
    private String INSERT_ODONTOLOGIA_INTO_FACULTADES;
    private String INSERT_PSICOLOGIA_INTO_FACULTADES;
    private String INSERT_CAMINOS_INTO_FACULTADES;
    private String INSERT_EDIFICACION_INTO_FACULTADES;
    private String INSERT_CIENCIAS_INTO_FACULTADES;
    private String INSERT_BELLASARTES_INTO_FACULTADES;
    private String INSERT_INFORMATICA_INTO_FACULTADES;
    private String INSERT_SALUD_INTO_FACULTADES;
    private String INSERT_MEDICINA_INTO_FACULTADES;
    private String INSERT_MELILLADEPORTES_INTO_FACULTADES;
    private String INSERT_MELILLASALUD_INTO_FACULTADES;
    private String INSERT_MELILLAJURIDICAS_INTO_FACULTADES;
    private String INSERT_CEUTASALUD_INTO_FACULTADES;
    private String INSERT_CEUTAEDECTEC_INTO_FACULTADES;
    //##################################################################################################################################


    // DATOS DE LOS CAMPUS
    //##################################################################################################################################

    // CAMPUS CENTRO
    private String VALUE_NOM_CENTRO;
    private int VALUE_IMA_CENTRO;

    // CAMPUS CARTUJA
    private String VALUE_NOM_CARTUJA;
    private int VALUE_IMA_CARTUJA;

    // CAMPUS FUENTENUEVA
    private String VALUE_NOM_FUENTENUEVA;
    private int VALUE_IMA_FUENTENUEVA;

    // CAMPUS AYNADAMAR
    private String VALUE_NOM_AYNADAMAR;
    private int VALUE_IMA_AYNADAMAR;

    // CAMPUS PTS
    private String VALUE_NOM_PTS;
    private int VALUE_IMA_PTS;

    // CAMPUS CEUTAMELILLA
    private String VALUE_NOM_CEUTAMELILLA;
    private int VALUE_IMA_CEUTAMELILLA;

    //##################################################################################################################################

    private String VALUE_DEFAULT;

    // DATOS DE LAS FACULTADES
    //##################################################################################################################################

    // NOMBRES DE LAS FACULTADES
    private String VALUE_NOM_ARQUITECTURA;
    private String VALUE_NOM_TRABAJO;
    private String VALUE_NOM_POLITICAS;
    private String VALUE_NOM_DERECHO;
    private String VALUE_NOM_TRABAJOSOCIAL;
    private String VALUE_NOM_TRADUCCION;
    private String VALUE_NOM_DEPORTES;
    private String VALUE_NOM_EDUCACION;
    private String VALUE_NOM_ECONOMIA;
    private String VALUE_NOM_COMUNICACION;
    private String VALUE_NOM_FARMACIA;
    private String VALUE_NOM_FILOSOFIA;
    private String VALUE_NOM_ODONTOLOGIA;
    private String VALUE_NOM_PSICOLOGIA;
    private String VALUE_NOM_CAMINOS;
    private String VALUE_NOM_EDIFICACION;
    private String VALUE_NOM_CIENCIAS;
    private String VALUE_NOM_BELLASARTES;
    private String VALUE_NOM_INFORMATICA;
    private String VALUE_NOM_SALUD;
    private String VALUE_NOM_MEDICINA;
    private String VALUE_NOM_MELILLADEPORTES;
    private String VALUE_NOM_MELILLASALUD;
    private String VALUE_NOM_MELILLAJURIDICAS;
    private String VALUE_NOM_CEUTASALUD;
    private String VALUE_NOM_CEUTAEDECTEC;

    // IMAGENES DE LAS FACULTADES
    private int VALUE_IMA_ARQUITECTURA;
    private int VALUE_IMA_TRABAJO;
    private int VALUE_IMA_POLITICAS;
    private int VALUE_IMA_DERECHO;
    private int VALUE_IMA_TRABAJOSOCIAL;
    private int VALUE_IMA_TRADUCCION;
    private int VALUE_IMA_DEPORTES;
    private int VALUE_IMA_EDUCACION;
    private int VALUE_IMA_ECONOMIA;
    private int VALUE_IMA_COMUNICACION;
    private int VALUE_IMA_FARMACIA;
    private int VALUE_IMA_FILOSOFIA;
    private int VALUE_IMA_ODONTOLOGIA;
    private int VALUE_IMA_PSICOLOGIA;
    private int VALUE_IMA_CAMINOS;
    private int VALUE_IMA_EDIFICACION;
    private int VALUE_IMA_CIENCIAS;
    private int VALUE_IMA_BELLASARTES;
    private int VALUE_IMA_INFORMATICA;
    private int VALUE_IMA_SALUD;
    private int VALUE_IMA_MEDICINA;
    private int VALUE_IMA_MELILLADEPORTES;
    private int VALUE_IMA_MELILLASALUD;
    private int VALUE_IMA_MELILLAJURIDICAS;
    private int VALUE_IMA_CEUTASALUD;
    private int VALUE_IMA_CEUTAEDECTEC;

    // UBICACIÓN DE LAS FACULTADES
    private String VALUE_UBI_BELLASARTES;
    private String VALUE_UBI_INFORMATICA;
    private String VALUE_UBI_SALUD;
    private String VALUE_UBI_MEDICINA;

    // HORARIO DE LAS FACULTADES
    private String VALUE_HOR_BELLASARTES;

    // PAGINA WEB DE LAS FACULTADES
    private String VALUE_WEB_BELLASARTES;
    private String VALUE_WEB_INFORMATICA;
    private String VALUE_WEB_SALUD;
    private String VALUE_WEB_MEDICINA;


    //##################################################################################################################################


    private static SQLiteDatabase obBaseDatos;

    //Cuando se crea una base de datos llama a ese metodo
    //Context es desde donde lo estoy llamando
    public BaseDatos(Context context) {
        super(context, nomBD, null, version);
        InicializarKKEYVALUES(context);

    }

    // INICIALIZACIÓN DE LOS VALORES FINALES
    //##################################################################################################################################
    private void InicializarKKEYVALUES(Context context){
        // TABLA CAMPUS
        TABLE_CAMP_NAME = context.getString(R.string.key_table_campus);
        KEY_CAMP_ID = context.getString(R.string.key_camp_id);
        KEY_CAMP_NOMBRE = context.getString(R.string.key_camp_nombre);
        KEY_CAMP_IMAGE = context.getString(R.string.key_camp_imagen);

        // TABLA FACULTADES
        TABLE_FAC_NAME = context.getString(R.string.key_table_facultades);
        KEY_FAC_ID = context.getString(R.string.key_fac_id);
        KEY_FAC_NOMBRE = context.getString(R.string.key_fac_nombre);
        KEY_FAC_UBICACION = context.getString(R.string.key_fac_ubicacion);
        KEY_FAC_IMAGEN = context.getString(R.string.key_fac_imagen); //En realidad guardará el ID del drawable
        KEY_FAC_HORARIO = context.getString(R.string.key_fac_horario);
        KEY_FAC_CAMPUS = context.getString(R.string.key_fac_campus);
        KEY__FAC_WEB = context.getString(R.string.key_fac_web);


        // CENTRO
        VALUE_NOM_CENTRO = context.getString(R.string.c_centro);
        VALUE_IMA_CENTRO = R.drawable.f_derecho;

        // CAMPUS CARTUJA
        VALUE_NOM_CARTUJA = context.getString(R.string.c_cartuja);
        VALUE_IMA_CARTUJA = R.drawable.f_filosofia;

        // CAMPUS FUENTENUEVA
        VALUE_NOM_FUENTENUEVA = context.getString(R.string.c_fuentenueva);
        VALUE_IMA_FUENTENUEVA = R.drawable.f_ciencias;

        // CAMPUS AYNADAMAR
        VALUE_NOM_AYNADAMAR = context.getString(R.string.c_aynadamar);
        VALUE_IMA_AYNADAMAR = R.drawable.f_informatica;

        // CAMPUS PTS
        VALUE_NOM_PTS = context.getString(R.string.c_pts);
        VALUE_IMA_PTS = R.drawable.f_medicina;

        // CAMPUS CEUTAMELILLA
        VALUE_NOM_CEUTAMELILLA = context.getString(R.string.c_ceutamelilla);
        VALUE_IMA_CEUTAMELILLA = R.drawable.ceutamelilla;


        VALUE_DEFAULT = context.getString(R.string.default_values);
     
        // NOMBRE DE LAS FACULTADES
        VALUE_NOM_ARQUITECTURA = context.getString(R.string.f_arquitectura);
        VALUE_NOM_TRABAJO  = context.getString(R.string.f_trabajo);
        VALUE_NOM_POLITICAS  = context.getString(R.string.f_politicas);
        VALUE_NOM_DERECHO = context.getString(R.string.f_derecho);
        VALUE_NOM_TRABAJOSOCIAL = context.getString(R.string.f_trabajosocial);
        VALUE_NOM_TRADUCCION = context.getString(R.string.f_traduccion);
        VALUE_NOM_DEPORTES = context.getString(R.string.f_deporte);
        VALUE_NOM_EDUCACION = context.getString(R.string.f_educacion);
        VALUE_NOM_ECONOMIA = context.getString(R.string.f_economia);
        VALUE_NOM_COMUNICACION = context.getString(R.string.f_comunicacion);
        VALUE_NOM_FARMACIA = context.getString(R.string.f_farmacia);
        VALUE_NOM_FILOSOFIA = context.getString(R.string.f_filosofia);
        VALUE_NOM_ODONTOLOGIA = context.getString(R.string.f_odontologia);
        VALUE_NOM_PSICOLOGIA = context.getString(R.string.f_psicologia);
        VALUE_NOM_CAMINOS = context.getString(R.string.f_caminos);
        VALUE_NOM_EDIFICACION = context.getString(R.string.f_edificacion);
        VALUE_NOM_CIENCIAS = context.getString(R.string.f_ciencias);
        VALUE_NOM_BELLASARTES = context.getString(R.string.f_bellasasrtes);
        VALUE_NOM_INFORMATICA = context.getString(R.string.f_informatica);
        VALUE_NOM_SALUD = context.getString(R.string.f_salud);
        VALUE_NOM_MEDICINA = context.getString(R.string.f_medicina);
        VALUE_NOM_MELILLADEPORTES = context.getString(R.string.f_melilla_deporte);
        VALUE_NOM_MELILLASALUD = context.getString(R.string.f_melilla_salud);
        VALUE_NOM_MELILLAJURIDICAS = context.getString(R.string.f_melilla_socialesjuridicas);
        VALUE_NOM_CEUTASALUD = context.getString(R.string.f_ceuta_salud);
        VALUE_NOM_CEUTAEDECTEC = context.getString(R.string.f_ceuta_educaeconomtecno);

        // IMAGENES DE LAS FACULTADES
        VALUE_IMA_ARQUITECTURA = R.drawable.f_arquitectura;
        VALUE_IMA_TRABAJO = R.drawable.f_trabajo;
        VALUE_IMA_POLITICAS = R.drawable.f_politicassociologia;
        VALUE_IMA_DERECHO = R.drawable.f_derecho;
        VALUE_IMA_TRABAJOSOCIAL = R.drawable.f_trabajosocial;
        VALUE_IMA_TRADUCCION = R.drawable.f_traduccion;
        VALUE_IMA_DEPORTES = R.drawable.f_deporte;
        VALUE_IMA_EDUCACION = R.drawable.f_educacion;
        VALUE_IMA_ECONOMIA = R.drawable.f_empresariales;
        VALUE_IMA_COMUNICACION = R.drawable.f_comunicacion;
        VALUE_IMA_FARMACIA = R.drawable.f_farmacia;
        VALUE_IMA_FILOSOFIA = R.drawable.f_filosofia;
        VALUE_IMA_ODONTOLOGIA = R.drawable.f_odontologia;
        VALUE_IMA_PSICOLOGIA = R.drawable.f_psicologia;
        VALUE_IMA_CAMINOS = R.drawable.f_caminos;
        VALUE_IMA_EDIFICACION = R.drawable.f_edificacion;
        VALUE_IMA_CIENCIAS = R.drawable.f_ciencias;
        VALUE_IMA_BELLASARTES = R.drawable.f_bellasartes;
        VALUE_IMA_INFORMATICA = R.drawable.f_informatica;
        VALUE_IMA_SALUD = R.drawable.f_salud;
        VALUE_IMA_MEDICINA = R.drawable.f_medicina;
        VALUE_IMA_MELILLADEPORTES = R.drawable.f_deportemelilla;
        VALUE_IMA_MELILLASALUD = R.drawable.f_saludmelilla;
        VALUE_IMA_MELILLAJURIDICAS = R.drawable.f_socialesjuridicasmelilla;
        VALUE_IMA_CEUTASALUD = R.drawable.f_saludceuta;
        VALUE_IMA_CEUTAEDECTEC = R.drawable.f_educaecontecnoceuta;

        // UBICACIÓN DE LAS FACULTADES
        VALUE_UBI_BELLASARTES = context.getString(R.string.fu_bellasartes);
        VALUE_UBI_INFORMATICA = context.getString(R.string.fu_informatica);
        VALUE_UBI_SALUD = context.getString(R.string.fu_salud);
        VALUE_UBI_MEDICINA = context.getString(R.string.fu_medicina);

        // HORARIO DE LAS FACULTADES
        VALUE_HOR_BELLASARTES = context.getString(R.string.f_horario);

        // PAGINA WEB DE LAS FACULTADES
        VALUE_WEB_BELLASARTES = context.getString(R.string.fw_bellasartes);
        VALUE_WEB_INFORMATICA = context.getString(R.string.fw_informatica);
        VALUE_WEB_SALUD = context.getString(R.string.fw_salud);
        VALUE_WEB_MEDICINA = context.getString(R.string.fw_medicina);
    }
    //##################################################################################################################################


    // SENTENCIAS CREATE
    //##################################################################################################################################

    // Se inicializan los String necesarios para para la creación de Tablas
    private void ValuesCreate(){

        CREATE_TABLE_CAMPUS = "CREATE TABLE "+TABLE_CAMP_NAME+"("
                +KEY_CAMP_ID+" integer primary key AUTOINCREMENT, "+KEY_CAMP_NOMBRE+" TEXT, "+KEY_CAMP_IMAGE+ " integer);";

        CREATE_TABLE_FACULTADES = "CREATE TABLE "+TABLE_FAC_NAME+"("
                +KEY_FAC_ID+" integer primary key AUTOINCREMENT, "+KEY_FAC_NOMBRE+" TEXT, "+KEY_FAC_UBICACION+ " TEXT,"
                +KEY_FAC_IMAGEN+" integer, "+KEY_FAC_HORARIO+" TEXT, "+KEY_FAC_CAMPUS+" TEXT, "+KEY__FAC_WEB+" TEXT );";
    }
    //##################################################################################################################################

    // SENTENCIAS INSERT
    //##################################################################################################################################

    // Se inicializan los String necesarios para INSERTAR en las tablas
    private void ValuesInsert(){

        // INSERT DE LA TABLA CAMPUS
        //##################################################################################################################################

        // CENTRO
        INSERT_CENTRO_INTO_CAMPUS = "INSERT INTO "+ TABLE_CAMP_NAME+" ("+KEY_CAMP_ID+", "+KEY_CAMP_NOMBRE+", "+KEY_CAMP_IMAGE+") " +
                "VALUES (null, '"+VALUE_NOM_CENTRO+"', " +
                "'"+VALUE_IMA_CENTRO+"');";

        // CARTUJA
        INSERT_CARTUJA_INTO_CAMPUS = "INSERT INTO "+ TABLE_CAMP_NAME+" ("+KEY_CAMP_ID+", "+KEY_CAMP_NOMBRE+", "+KEY_CAMP_IMAGE+") " +
                "VALUES (null, '"+VALUE_NOM_CARTUJA+"', " +
                "'"+VALUE_IMA_CARTUJA+"');";

        // FUENTENUEVA
        INSERT_FUENTENUEVA_INTO_CAMPUS = "INSERT INTO "+ TABLE_CAMP_NAME+" ("+KEY_CAMP_ID+", "+KEY_CAMP_NOMBRE+", "+KEY_CAMP_IMAGE+") " +
                "VALUES (null, '"+VALUE_NOM_FUENTENUEVA+"', " +
                "'"+VALUE_IMA_FUENTENUEVA+"');";

        // AYNADAMAR
        INSERT_AYNADAMAR_INTO_CAMPUS = "INSERT INTO "+ TABLE_CAMP_NAME+" ("+KEY_CAMP_ID+", "+KEY_CAMP_NOMBRE+", "+KEY_CAMP_IMAGE+") " +
                "VALUES (null, '"+VALUE_NOM_AYNADAMAR+"', " +
                "'"+VALUE_IMA_AYNADAMAR+"');";

        // PTS
        INSERT_PTS_INTO_CAMPUS = "INSERT INTO "+ TABLE_CAMP_NAME+" ("+KEY_CAMP_ID+", "+KEY_CAMP_NOMBRE+", "+KEY_CAMP_IMAGE+") " +
                "VALUES (null, '"+VALUE_NOM_PTS+"', " +
                "'"+VALUE_IMA_PTS+"');";

        // CEUTAMELILLA
        INSERT_CEUTAMELILLA_INTO_CAMPUS = "INSERT INTO "+ TABLE_CAMP_NAME+" ("+KEY_CAMP_ID+", "+KEY_CAMP_NOMBRE+", "+KEY_CAMP_IMAGE+") " +
                "VALUES (null, '"+VALUE_NOM_CEUTAMELILLA+"', " +
                "'"+VALUE_IMA_CEUTAMELILLA+"');";

        //##################################################################################################################################


        INSERT_ARQUITECTURA_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_ARQUITECTURA+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_ARQUITECTURA+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CENTRO+"', "+
                "'"+VALUE_DEFAULT+"');";
        
        INSERT_TRABAJO_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_TRABAJO+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_TRABAJO+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CENTRO+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_POLITICAS_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_POLITICAS+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_POLITICAS+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CENTRO+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_DERECHO_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_DERECHO+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_DERECHO+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CENTRO+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_TRABAJOSOCIAL_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_TRABAJOSOCIAL+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_TRABAJOSOCIAL+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CENTRO+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_TRADUCCION_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_TRADUCCION+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_TRADUCCION+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CENTRO+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_DEPORTE_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_DEPORTES+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_DEPORTES+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CARTUJA+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_EDUCACION_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_EDUCACION+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_EDUCACION+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CARTUJA+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_ECONOMIA_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_ECONOMIA+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_ECONOMIA+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CARTUJA+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_COMUNICACION_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_COMUNICACION+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_COMUNICACION+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CARTUJA+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_FARMACIA_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_FARMACIA+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_FARMACIA+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CARTUJA+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_FILOSOFIA_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_FILOSOFIA+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_FILOSOFIA+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CARTUJA+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_ODONTOLOGIA_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_ODONTOLOGIA+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_ODONTOLOGIA+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CARTUJA+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_PSICOLOGIA_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_PSICOLOGIA+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_PSICOLOGIA+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CARTUJA+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_CAMINOS_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_CAMINOS+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_CAMINOS+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_FUENTENUEVA+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_EDIFICACION_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_EDIFICACION+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_EDIFICACION+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_FUENTENUEVA+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_CIENCIAS_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_CIENCIAS+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_CIENCIAS+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_FUENTENUEVA+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_BELLASARTES_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_BELLASARTES+"', " +
                "'"+VALUE_UBI_BELLASARTES+"', '"+VALUE_IMA_BELLASARTES+"', " +
                "'"+VALUE_HOR_BELLASARTES+"', '"+VALUE_NOM_AYNADAMAR+"', "+
                "'"+VALUE_WEB_BELLASARTES+"');";

        INSERT_INFORMATICA_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_INFORMATICA+"', " +
                "'"+VALUE_UBI_INFORMATICA+"', '"+VALUE_IMA_INFORMATICA+"', " +
                "'"+VALUE_HOR_BELLASARTES+"', '"+VALUE_NOM_AYNADAMAR+"', "+
                "'"+VALUE_WEB_INFORMATICA+"');";
        
        INSERT_SALUD_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_SALUD+"', " +
                "'"+VALUE_UBI_SALUD+"', '"+VALUE_IMA_SALUD+"', " +
                "'"+VALUE_HOR_BELLASARTES+"', '"+VALUE_NOM_PTS+"', "+
                "'"+VALUE_WEB_SALUD+"');";

        INSERT_MEDICINA_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_MEDICINA+"', " +
                "'"+VALUE_UBI_MEDICINA+"', '"+VALUE_IMA_MEDICINA+"', " +
                "'"+VALUE_HOR_BELLASARTES+"', '"+VALUE_NOM_PTS+"', "+
                "'"+VALUE_WEB_MEDICINA+"');";

        INSERT_MELILLADEPORTES_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_MELILLADEPORTES+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_MELILLADEPORTES+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CEUTAMELILLA+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_MELILLASALUD_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_MELILLASALUD+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_MELILLASALUD+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CEUTAMELILLA+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_MELILLAJURIDICAS_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_MELILLAJURIDICAS+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_MELILLAJURIDICAS+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CEUTAMELILLA+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_CEUTASALUD_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_CEUTASALUD+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_CEUTASALUD+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CEUTAMELILLA+"', "+
                "'"+VALUE_DEFAULT+"');";

        INSERT_CEUTAEDECTEC_INTO_FACULTADES = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                "VALUES (null, '"+VALUE_NOM_CEUTAEDECTEC+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_IMA_CEUTAEDECTEC+"', " +
                "'"+VALUE_DEFAULT+"', '"+VALUE_NOM_CEUTAMELILLA+"', "+
                "'"+VALUE_DEFAULT+"');";

    }
    //##################################################################################################################################

    //METODOS OBLIGATORIOS
    @Override
    //Este metodo solo se ejecuta si la Base de Datos no existe
    public void onCreate(SQLiteDatabase db) {
        ValuesCreate(); // Se inicializan los CREATE
        ValuesInsert(); // Se inicializan los INSERT
        //Ejecuta el string que se le pasa por parametro

        db.execSQL(CREATE_TABLE_CAMPUS);

        db.execSQL(INSERT_CENTRO_INTO_CAMPUS);
        db.execSQL(INSERT_CARTUJA_INTO_CAMPUS);
        db.execSQL(INSERT_FUENTENUEVA_INTO_CAMPUS);
        db.execSQL(INSERT_AYNADAMAR_INTO_CAMPUS);
        db.execSQL(INSERT_PTS_INTO_CAMPUS);
        db.execSQL(INSERT_CEUTAMELILLA_INTO_CAMPUS);


        db.execSQL(CREATE_TABLE_FACULTADES);

        db.execSQL(INSERT_ARQUITECTURA_INTO_FACULTADES);
        db.execSQL(INSERT_TRABAJO_INTO_FACULTADES);
        db.execSQL(INSERT_POLITICAS_INTO_FACULTADES);
        db.execSQL(INSERT_DERECHO_INTO_FACULTADES);
        db.execSQL(INSERT_TRABAJOSOCIAL_INTO_FACULTADES);
        db.execSQL(INSERT_TRADUCCION_INTO_FACULTADES);
        db.execSQL(INSERT_DEPORTE_INTO_FACULTADES);
        db.execSQL(INSERT_EDUCACION_INTO_FACULTADES);
        db.execSQL(INSERT_ECONOMIA_INTO_FACULTADES);
        db.execSQL(INSERT_COMUNICACION_INTO_FACULTADES);
        db.execSQL(INSERT_FARMACIA_INTO_FACULTADES);
        db.execSQL(INSERT_FILOSOFIA_INTO_FACULTADES);
        db.execSQL(INSERT_ODONTOLOGIA_INTO_FACULTADES);
        db.execSQL(INSERT_PSICOLOGIA_INTO_FACULTADES);
        db.execSQL(INSERT_CAMINOS_INTO_FACULTADES);
        db.execSQL(INSERT_EDIFICACION_INTO_FACULTADES);
        db.execSQL(INSERT_CIENCIAS_INTO_FACULTADES);
        db.execSQL(INSERT_BELLASARTES_INTO_FACULTADES);
        db.execSQL(INSERT_INFORMATICA_INTO_FACULTADES);
        db.execSQL(INSERT_SALUD_INTO_FACULTADES);
        db.execSQL(INSERT_MEDICINA_INTO_FACULTADES);
        db.execSQL(INSERT_MELILLADEPORTES_INTO_FACULTADES);
        db.execSQL(INSERT_MELILLASALUD_INTO_FACULTADES);
        db.execSQL(INSERT_MELILLAJURIDICAS_INTO_FACULTADES);
        db.execSQL(INSERT_CEUTASALUD_INTO_FACULTADES);
        db.execSQL(INSERT_CEUTAEDECTEC_INTO_FACULTADES);

    }

    //Version comprueba si no es superior se ejecuta el onCreate y si es superior el onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAC_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CAMP_NAME);
        onCreate(db);
    }

    //GESTION DE LA TABLA FACULTADES
    //##################################################################################################################################

    //Devuelve un array de objetos FACULTAD que se encuentren en la base de datos guardados
    public ArrayList<Facultad> ReadFACULTADES() {
        /* Abrimos la BD de Lectura */
        obBaseDatos = getReadableDatabase();
        /* Creamos una Lista de Facultades que será la que devolvamos en este metodo */
        ArrayList<Facultad> listaFacultades = new ArrayList<>();

        if (obBaseDatos != null) {
            sentencia = "SELECT * FROM "+TABLE_FAC_NAME+" ORDER BY "+ KEY_FAC_NOMBRE+" ASC;";
            Cursor fac_Datos = obBaseDatos.rawQuery(sentencia, null);
            if (fac_Datos.moveToFirst()) {
                do {
                    //Posicion 0 = id
                    //Posicion 1 = nombre
                    //Posicion 2 = ubicacion
                    //Posicion 3 = imagen
                    //Posicion 4 = horario
                    //Posicion 5 = campus
                    //Posicion 6 = url
                    Facultad facul = new Facultad(fac_Datos.getInt(0), fac_Datos.getString(1), fac_Datos.getString(2), fac_Datos.getInt(3), fac_Datos.getString(4), fac_Datos.getString(5), fac_Datos.getString(6));
                    listaFacultades.add(facul);
                } while (fac_Datos.moveToNext());
            }
            fac_Datos.close();
            obBaseDatos.close();
        }
        return listaFacultades;
    }

    public ArrayList<Facultad> ReadFACULTADESWHERE(String campus_selec) {
        /* Abrimos la BD de Lectura */
        obBaseDatos = getReadableDatabase();
        /* Creamos una Lista de Facultades que será la que devolvamos en este metodo */
        ArrayList<Facultad> listaFacultades = new ArrayList<>();

        if (obBaseDatos != null) {
            sentencia = "SELECT * FROM "+TABLE_FAC_NAME+" WHERE "+KEY_FAC_CAMPUS+" = '"+ campus_selec + "' ORDER BY "+ KEY_FAC_NOMBRE+" ASC;";
            Cursor fac_Datos = obBaseDatos.rawQuery(sentencia, null);
            if (fac_Datos.moveToFirst()) {
                do {
                    //Posicion 0 = id
                    //Posicion 1 = nombre
                    //Posicion 2 = ubicacion
                    //Posicion 3 = imagen
                    //Posicion 4 = horario
                    //Posicion 5 = campus
                    //Posicion 6 = url
                    Facultad facul = new Facultad(fac_Datos.getInt(0), fac_Datos.getString(1), fac_Datos.getString(2), fac_Datos.getInt(3), fac_Datos.getString(4), fac_Datos.getString(5), fac_Datos.getString(6));
                    listaFacultades.add(facul);
                } while (fac_Datos.moveToNext());
            }
            fac_Datos.close();
            obBaseDatos.close();
        }
        return listaFacultades;
    }

    //Si recibe un array con datos, borra la tabla FACULTADES, la crea, y le inserta los nuevos datos
    public void UpdateFACULTADES(ArrayList<Facultad> datos){

        if(datos != null && datos.size()>0){
            // Se abre la base de datos en modo escritura
            obBaseDatos = getWritableDatabase();
            if(obBaseDatos != null){
                obBaseDatos.execSQL("DROP TABLE IF EXISTS "+TABLE_FAC_NAME);

                obBaseDatos.execSQL(CREATE_TABLE_FACULTADES);

                for(Facultad f : datos){
                    sentencia = "INSERT INTO "+ TABLE_FAC_NAME+" ("+KEY_FAC_ID+", "+KEY_FAC_NOMBRE+", "+KEY_FAC_UBICACION+", "+KEY_FAC_IMAGEN+", "+KEY_FAC_HORARIO+", "+KEY_FAC_CAMPUS+", "+KEY__FAC_WEB+") " +
                            "VALUES (null, '"+f.getNombre()+"', " +
                            "'"+f.getUbicacion()+"', '"+f.getImagen()+"', " +
                            "'"+f.getHorario()+"', '"+f.getCampus()+"', "+
                            "'"+f.getPagina_web()+"');";

                    obBaseDatos.execSQL(sentencia);
                }
                obBaseDatos.close();
            }
        }
    }

    //Si recibe un array con datos, borra la tabla FACULTADES, la crea, y le inserta los nuevos datos
    public void UpdateCAMPUS(ArrayList<Campus> datos){

        if(datos != null && datos.size()>0){
            // Se abre la base de datos en modo escritura
            obBaseDatos = getWritableDatabase();
            if(obBaseDatos != null){
                obBaseDatos.execSQL("DROP TABLE IF EXISTS "+TABLE_FAC_NAME);

                obBaseDatos.execSQL(CREATE_TABLE_FACULTADES);

                for(Campus c : datos){
                    sentencia = "INSERT INTO "+ TABLE_CAMP_NAME+" ("+KEY_CAMP_ID+", "+KEY_CAMP_NOMBRE+", "+KEY_CAMP_IMAGE+") " +
                            "VALUES (null, '"+c.getNombre()+"', " +
                            "'"+c.getImage()+"');";

                    obBaseDatos.execSQL(sentencia);
                }
                obBaseDatos.close();
            }
        }
    }
    //##################################################################################################################################

    // GESTION DE LA TABLA CAMPUS
    //##################################################################################################################################
    //Devuelve un array de objetos FACULTAD que se encuentren en la base de datos guardados
    public ArrayList<Campus> ReadCAMPUS() {
        // Abrimos la BD de Lectura
        obBaseDatos = getReadableDatabase();
        // Creamos una Lista de Campus que será la que devolvamos en este metodo
        ArrayList<Campus> listaCampus = new ArrayList<>();

        if (obBaseDatos != null) {
            String camp_Consulta = "SELECT * FROM "+TABLE_CAMP_NAME+" ORDER BY "+KEY_CAMP_ID+";";
            Cursor camp_Datos = obBaseDatos.rawQuery(camp_Consulta, null);
            if (camp_Datos.moveToFirst()) {
                do {
                    //Posicion 0 = id // No es necesario
                    //Posicion 1 = nombre
                    //Posicion 2 = imagen
                    Campus cam = new Campus(camp_Datos.getInt(0), camp_Datos.getString(1), camp_Datos.getInt(2));
                    listaCampus.add(cam);
                } while (camp_Datos.moveToNext());
            }
            camp_Datos.close();
            obBaseDatos.close();
        }
        return listaCampus;
    }


}