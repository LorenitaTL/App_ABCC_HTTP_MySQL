package controlador;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class Analizador_JSON {
    InputStream is = null;
    JSONObject jsonObject = null;
    String json = null;
    OutputStream os = null;
    //metodo para ALTAS, BAJAS y CAMBIOS
    public JSONObject peticionHTTP(String url,String metodo, Map datos) throws UnsupportedEncodingException { //Debe recibir los datos del alumno
        HttpURLConnection conexion = null;
        URL mUrl = null;
        //magia
        String cadenaJSON = "{\"nc\":\""+URLEncoder.encode(String.valueOf(datos.get("nc")),"UTF-8")+
                "\",\"n\":\""+URLEncoder.encode(String.valueOf(datos.get("n")),"UTF-8")+
                "\",\"pa\":\""+URLEncoder.encode(String.valueOf(datos.get("pa")),"UTF-8")+
                "\",\"sa\":\""+URLEncoder.encode(String.valueOf(datos.get("sa")),"UTF-8")+
                "\",\"e\":\""+URLEncoder.encode(String.valueOf(datos.get("e")),"UTF-8")+
                "\",\"s\":\""+URLEncoder.encode(String.valueOf(datos.get("s")),"UTF-8")+
                "\",\"c\":\""+URLEncoder.encode(String.valueOf(datos.get("c")),"UTF-8")+"\"}";
        try {
            mUrl = new URL(url);
            conexion = (HttpURLConnection) mUrl.openConnection();
            conexion.setDoOutput(true);
            conexion.setRequestMethod(metodo);

            conexion.setFixedLengthStreamingMode(cadenaJSON.getBytes().length);

            conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            os = new BufferedOutputStream(conexion.getOutputStream());

            os.write(cadenaJSON.getBytes());
            os.flush();
            os.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            is = new BufferedInputStream(conexion.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder cadena = new StringBuilder();

            String linea;
            while ((linea=br.readLine())!=null){
                cadena.append(linea+"\n");
            }
            is.close();
            json = cadena.toString();
            Log.i("Mensaje 1 >>>>", "Respuesta JSON: "+json);
            jsonObject = new JSONObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }//metodo PeticionHTTP

    public JSONObject peticionHTTP(String url,String metodo) throws UnsupportedEncodingException { //Debe recibir los datos del alumno
        HttpURLConnection conexion = null;
        URL mUrl = null;
        //magia

        try {
            mUrl = new URL(url);
            conexion = (HttpURLConnection) mUrl.openConnection();
            conexion.setDoOutput(true);
            conexion.setRequestMethod(metodo);

            //conexion.setFixedLengthStreamingMode(cadenaJSON.getBytes().length);

            conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            os = new BufferedOutputStream(conexion.getOutputStream());

            //os.write(cadenaJSON.getBytes());
            os.flush();
            os.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            is = new BufferedInputStream(conexion.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder cadena = new StringBuilder();

            String linea;
            while ((linea=br.readLine())!=null){
                cadena.append(linea+"\n");
            }
            is.close();
            json = cadena.toString();
            Log.i("Mensaje 1 >>>>", "Respuesta JSON: "+json);
            jsonObject = new JSONObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }//metodo PeticionHTTP




}


