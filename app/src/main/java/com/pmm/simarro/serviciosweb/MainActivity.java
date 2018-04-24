package com.pmm.simarro.serviciosweb;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText id;
    private EditText nombre;
    private EditText direccion;

    private Button consultar;
    private Button consultarID;
    private Button borrar;
    private Button insertar;
    private Button actualizar;

    //Ip de mi url
    private String IP = "http://chllopis.ticsimarro.org/alumnos";
    //Rutas de los webServices
    private String GET = IP + "/obtener_alumnos.php";
    private String GET_ET_ID = IP + "/obtener_alumno_por_id.php";
    private String UPDATE = IP + "/actualizar_alumno.php";
    private String DELETE = IP + "/borrar_alumno.php";
    private String INSERT = IP + "/insertar_alumno.php";

    private ObtenerWebService hiloconexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = (EditText) findViewById(R.id.id);
        nombre = (EditText) findViewById(R.id.nombre);
        direccion = (EditText) findViewById(R.id.direccion);

        consultar = (Button) findViewById(R.id.consultar);
        consultarID = (Button) findViewById(R.id.consultarID);
        borrar = (Button) findViewById(R.id.borrar);
        insertar = (Button) findViewById(R.id.insertar);
        actualizar = (Button) findViewById(R.id.actualizar);

        consultar.setOnClickListener(this);
        consultarID.setOnClickListener(this);
        borrar.setOnClickListener(this);
        insertar.setOnClickListener(this);
        actualizar.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.consultar:
                hiloconexion = new ObtenerWebService();
                hiloconexion.execute(GET, "1");
                break;
            case R.id.consultarID:
                hiloconexion = new ObtenerWebService();
                String cadenallamada = GET_ET_ID + "?idalumno=" + id.getText().toString();
                hiloconexion.execute(cadenallamada,"2"); // Parámetros que recibe doInBackground
                break;
            case R.id.borrar:
                break;
            case R.id.insertar:
                break;
            case R.id.actualizar:
                break;
        }
    }

    public class ObtenerWebService extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            String cadena = params[0];
            URL url = null;
            String devuelve = "";

            System.out.println(params[1]);
            System.out.println(cadena);
            if (params[1] == "1") {//Consulta de todos los alumnos
                try {
                    url = new URL(cadena);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión

                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");

                    int respuesta = connection.getResponseCode();

                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {
                        InputStream in = new BufferedInputStream(connection.getInputStream());

                        // preparo la cadena de entrada
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in)); // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;

                        while ((line = reader.readLine()) != null) {
                            result.append(line); // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString()); //Creo un JSONObject a partir del StringBuilder pasado a cadena

                        //Accedemos al vector de resultados
                        String resultJSON = respuestaJSON.getString("estado"); // estado es el nombre del campo en el JSON

                        System.out.println(resultJSON);
                        if (resultJSON.equals("1")) { // hay alumnos a mostrar

                            JSONArray alumnosJSON = respuestaJSON.getJSONArray("alumnos"); // estado es el nombre del campo en el JSON

                            for (int i = 0; i < alumnosJSON.length(); i++) {
                                devuelve += alumnosJSON.getJSONObject(i).getString("idAlumno") + " " + alumnosJSON.getJSONObject(i).getString("nombre") + " " + alumnosJSON.getJSONObject(i).getString("direccion") + "\n";
                            }
                        }else if (resultJSON.equals("2")) {
                            devuelve = "No hay alumnos";
                        }

                        return devuelve;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            } else if (params[1] == "2"){ //consulta por id
                try {
                    url = new URL(cadena);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión

                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");

                    int respuesta = connection.getResponseCode();

                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {
                        InputStream in = new BufferedInputStream(connection.getInputStream());

                        // preparo la cadena de entrada
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in)); // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;

                        while ((line = reader.readLine()) != null) {
                            result.append(line); // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString()); //Creo un JSONObject a partir del StringBuilder pasado a cadena

                        //Accedemos al vector de resultados
                        String resultJSON = respuestaJSON.getString("estado"); // estado es el nombre del campo en el JSON

                        System.out.println(resultJSON);
                        if (resultJSON.equals("1")) { // hay alumnos a mostrar
                            devuelve = devuelve +
                                    respuestaJSON.getJSONObject("alumno").getString("idAlumno") + " " +
                                    respuestaJSON.getJSONObject("alumno").getString("nombre") + " " +
                                    respuestaJSON.getJSONObject("alumno").getString("direccion");
                        }else if (resultJSON.equals("2")) {
                            devuelve = "No hay alumnos";
                        }

                        return devuelve;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if (params[1] == "3"){ //insert

                try {
                    HttpURLConnection urlConn;
                    DataOutputStream printout;
                    DataInputStream input;
                    url = new URL(cadena);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    urlConn.setRequestProperty("Accept", "application/json");
                    urlConn.connect();
                    //Creo el Objeto JSON
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("nombre", params[2]);
                    jsonParam.put("direccion", params[3]);
                    // Envio los parámetros post.
                    OutputStream os = urlConn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();
                    int respuesta = urlConn.getResponseCode();
                    StringBuilder result = new StringBuilder();
                    if (respuesta == HttpURLConnection.HTTP_OK) {
                        String line;
                        BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line=br.readLine()) != null) {
                            result.append(line);
                        }
                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString()); //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados
                        String resultJSON = respuestaJSON.getString("estado"); // estado es el nombre del campo en el JSON
                        if (resultJSON.equals("1")) { // hay un alumno que mostrar
                            devuelve = "Alumno insertado correctamente";
                        } else if (resultJSON.equals("2")) {
                            devuelve = "El alumno no pudo insertarse";
                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return devuelve;

            } else if (params[1 ]== "4"){ //update

                try {
                    HttpURLConnection urlConn;
                    DataOutputStream printout;
                    DataInputStream input;
                    url = new URL(cadena);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    urlConn.setRequestProperty("Accept", "application/json");
                    urlConn.connect();
                    //Creo el Objeto JSON
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("idalumno",params[2]);
                    jsonParam.put("nombre", params[3]);
                    jsonParam.put("direccion", params[4]);
                    // Envio los parámetros post.
                    OutputStream os = urlConn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();
                    int respuesta = urlConn.getResponseCode();
                    StringBuilder result = new StringBuilder();
                    if (respuesta == HttpURLConnection.HTTP_OK) {
                        String line;
                        BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line=br.readLine()) != null) {
                            result.append(line);
                        }
                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString()); //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados
                        String resultJSON = respuestaJSON.getString("estado"); // estado es el nombre del campo en el JSON
                        if (resultJSON.equals("1")) { // hay un alumno que mostrar
                            devuelve = "Alumno actualizado correctamente";
                        } else if (resultJSON.equals("2")) {
                            devuelve = "El alumno no pudo actualizarse";
                        }

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return devuelve;
            } else if (params[1] == "5"){ //delete

                try {
                    HttpURLConnection urlConn;
                    DataOutputStream printout;
                    DataInputStream input;
                    url = new URL(cadena);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true); //abrimos la conexion de entrada
                    urlConn.setDoOutput(true);//abrimos la conexion de salida
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    urlConn.setRequestProperty("Accept", "application/json");
                    urlConn.connect();
                    //Creo el Objeto JSON
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("idalumno", params[2]); //le pasamos un 3r parámetro que es el identificador
                    // Envio los parámetros post.
                    OutputStream os = urlConn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();
                    int respuesta = urlConn.getResponseCode();
                    StringBuilder result = new StringBuilder();
                    if (respuesta == HttpURLConnection.HTTP_OK) {
                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            result.append(line);
                        }
                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString()); //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados
                        String resultJSON = respuestaJSON.getString("estado"); // estado es el nombre del campo en el JSON
                        if (resultJSON == "1") { // hay un alumno que mostrar
                            devuelve = "Alumno borrado correctamente";
                        } else if (resultJSON == "2") {
                            devuelve = "No hay alumnos";
                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return devuelve;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }

    }
}
