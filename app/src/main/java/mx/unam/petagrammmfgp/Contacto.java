package mx.unam.petagrammmfgp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Contacto extends AppCompatActivity {

    private String correo, contraseña;
    private TextInputLayout tilyContacto, tilyEmail, tilyMensaje;
    private TextInputEditText tietContacto, tietEmail, tietMensaje;
    private Button btnEnviar;
    private Session sesion;
    private int tipodeservidor;
    private Toolbar actiontoolbarcontacto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        //Agregamos la barra personalizada a esta activity
        actiontoolbarcontacto = findViewById(R.id.miActionToolBarC);
        setSupportActionBar(actiontoolbarcontacto);
        //le cambiamos el titulo a la barra personalizada para Contacto
        actiontoolbarcontacto.setTitle(R.string.titulo_actiontoolbarc);
        actiontoolbarcontacto.setTitleTextColor(Color.WHITE);
        //validamos que la barra no este NUll
        if(getSupportActionBar()!=null)
        {
            //habilitamos el boton de subir
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //hacemos visible nuestros elementos TextInputLayout de Contacto
        tilyContacto = findViewById(R.id.tilyContacto);
        tilyEmail = findViewById(R.id.tilyEmail);
        tilyMensaje = findViewById(R.id.tilyMensaje);
        //hacemos visible nuestros elementos TextInputEditText de Contacto
        tietContacto = findViewById(R.id.tietContacto);
        tietEmail = findViewById(R.id.tietEmail);
        tietMensaje = findViewById(R.id.tietMensaje);

        btnEnviar = findViewById(R.id.btnEnviar);
        //agregamos los valores(validos) a las variables de correo y contraseña,
        //que seran los del correo que enviara los mensajes, ya sea Gmail o Hotmail
        //Si usara Gmail, debe configurar en su correo el uso de aplicaciones menos seguras, en:
        //Gestionar tu cuenta de google/Seguridad/acceso de aplicaciones poco seguras
        correo = "@hotmail.com";//Ej: correo_que_envia_mensaje@gmail.com
        contraseña = "";//Ej: contraseña12345
        //identificamos que tipo de servidor de correo estaremos agregando en la variable: correo
        tipodeservidor = saberTipoDeServidorCorreo();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //preguntamos si estan validos los campos TextInputEditText de Contacto
                if(validar_campocontacto())
                {
                    //agregamos politicas para enviar los mensajes
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    //agregamos las variables de las propiedades para enviar los correos, desde el proveedor Gmail
                    Properties propiedades = new Properties();
                    //preguntamos con que tipo de servidor estaremos trabajando, 1 = hotmail, 2 = Gmail
                    if(tipodeservidor==1)
                    {
                        //este es nuestro servidor de correo electronico(Hotmail)
                        propiedades.put("mail.smtp.host","smtp.live.com");
                        //para indicar que nos vamos a autenticar, si requiere o no usuario y contraseña
                        propiedades.put("mail.smtp.auth","true");
                        //este es para habilitar TLS si esta disponible
                        propiedades.put("mail.smtp.starttls.enable","true");

                    }else if(tipodeservidor==2)
                    {
                        //este es nuestro servidor de correo electronico(Gmail)
                        propiedades.put("mail.smtp.host","smtp.googlemail.com");
                        //este es el socket para recibir respuesta del servidor de Gmail, con el puerto 465 del protocolo SSL
                        propiedades.put("mail.smtp.socketFactory.port","465");
                        //indicaremos que nuestro socket va a ser SSL, el cual es el protocolo de seguridad
                        // para que se envie de manera segura nuestra informacion
                        propiedades.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
                        //para indicar que nos vamos a autenticar, si requiere o no usuario y contraseña
                        propiedades.put("mail.smtp.auth","true");
                        //este es para el puerto de Gmail, del protocolo SSL
                        propiedades.put("mail.smtp.port","465");
                    }

                    //autenticaremos nuestra sesion e inicializamos
                    try {
                        sesion = Session.getDefaultInstance(propiedades, new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(correo,contraseña);
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.println("Error en inicio de sesion: "+e);
                    }

                    try{
                        //validamos que la sesion no este nula y agregamos contenido a nuestro mensaje
                        if(sesion!=null)
                        {
                            Message message = new MimeMessage(sesion);
                            //correo de quien envia, Ej: correo_que_envia_mensaje@gmail.com
                            message.setFrom(new InternetAddress(correo));
                            //el asunto del mensaje, colocamos el nombre y el correo del que envia el comentario
                            message.setSubject("Comentario de "+tietContacto.getText()+" < "+tietEmail.getText()+" >");
                            //correo al cual le vamos a enviar, ya sea Gmail o Hotmail, Ej: correo_que_recibe_mensaje@gmail.com
                            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("@gmail.com"));
                            //agregamo0s el mensaje y el formato de este
                            message.setContent(tietMensaje.getText().toString(),"text/html; charset=utf-8");
                            //enviamos el correo(mensaje con todos sus parametros)
                            //Transport.send(message);//podemos usar solo este Transport para enviar el correo
                            //o creamos una clase para enviar el correo
                            new EnviarEmail().execute(message);
                        }else if(sesion==null){ System.out.println("Sesion fallida");}
                    }catch (Exception e){

                        e.printStackTrace();
                        System.out.println("Error en los parametros del mensaje: "+e);
                    }
                }

            }
        });
    }
    //funcion sobre escrita donde se captura el evento del boton atras de la ActionBar
    @Override
    public boolean onSupportNavigateUp() {
        //hacemos un intent para indicar que iremos desde esta activity hacia la siguiente(MainActivity)
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();//finalizamos( cerramos ) la activity( AcercaDe ), para liberar memoria
        return super.onSupportNavigateUp();
    }
    //funcion sobre escrita donde se captura el evento del boton atras de la barra inferior
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            //hacemos un intent para indicar que iremos desde esta activity hacia la siguiente(MainActivity)
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();//finalizamos( cerramos ) la activity( AcercaDe ), para liberar memoria
        }
        return super.onKeyDown(keyCode, event);
    }
    //clase creada para manejar el envio del Email
    private class EnviarEmail extends AsyncTask<Message,String,String>
    {
        //inicializamos el dialogo de progreso
        private ProgressDialog progressDialog;
        //metodo sobre escrito para cuando se esta enviando el Email
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //creamos y mostramos el dialogo de progreso
            progressDialog = ProgressDialog.show(Contacto.this,"Porfavor Espere.",
                    "Enviando Email...",true,false);
        }

        @Override
        protected String doInBackground(Message... messages) {
            String estado="";
            try {
                //preguntamos con que tipo de servidor estaremos trabajando, 1 = hotmail, 2 = Gmail
                if (tipodeservidor==1)
                {
                    //cuando esta bien
                    Transport transport = sesion.getTransport("smtp");
                    transport.connect("smtp.live.com",587,correo,contraseña);
                    transport.sendMessage(messages[0],messages[0].getAllRecipients());
                    transport.close();
                    estado="Exito";
                    return estado;

                }else if (tipodeservidor==2)
                {
                    //cuando esta bien
                    Transport.send(messages[0]);
                    estado="Exito";
                    return estado;
                }


            } catch (MessagingException e)
                    {
                        //cuando hay error
                        e.printStackTrace();
                        estado="Error";
                        return estado;
                    }
            return estado;
        }
        //metodo sobre escrito para cuando se ha enviando el Email

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //descartar el dialogo de progreso
            progressDialog.dismiss();
            if (s.equals("Exito"))
            {
                //cuando esta bien
                //inicializamos un dialogo de alerta exitosa
                AlertDialog.Builder builder = new AlertDialog.Builder(Contacto.this);
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<font color='#509324'>Exito</font>"));
                builder.setMessage("Email Enviado Satisfactoriamente");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //limpiamos todos los campos tiet
                        tietContacto.setText("");
                        tietEmail.setText("");
                        tietMensaje.setText("");
                    }
                });
                //mostramos el dialogo de alerta exitosa
                builder.show();
            }else{
                //cuando hay error, enviamos un mensaje indicando que algo salio mal
                Toast.makeText(getApplicationContext(),"Algo salio mal", Toast.LENGTH_SHORT).show();
            }
        }

    }
    //metodo para saber que tipo de servidor de correo se estara usando para enviar el mensaje
    public int saberTipoDeServidorCorreo()
    {
        //variable para el tipo de servidor, en este caso solo dos: Hotmail y Gmail
        String[] servidor = {"@hotmail.com","@gmail.com"};
        int[] posicion= new int[2];
        int tiposervi=0;
        //usamos el metodo indexOf para saber si el correo agregado coinside con Hotmail o Gmail
        posicion[0] = correo.indexOf(servidor[0]);
        posicion[1] = correo.indexOf(servidor[1]);
        //preguntamos si hay coinsidencia, devuelve -1 si no y devuelve la posicion donde comienza la coinsidencia
        if(posicion[0]>-1)
        {
            tiposervi=1;
        }else if(posicion[1]>-1)
        {
            tiposervi=2;
        }
        return tiposervi;
    }
    //metodo para validar que los campos contengan datos( no vacios )
    public boolean validar_campocontacto()
    {
        //validar Nombre
        if(tietContacto.getText().toString().isEmpty())
        {
            tilyContacto.setError("Debe ingresar su Nombre");
            solicitudFocus(tietContacto);
            return false;
        }else if(tietContacto.getText().toString().trim().length()<3)
        {
            tilyContacto.setError("Minimo 3 caracteres");
            solicitudFocus(tietContacto);
            return false;
        }
        //validar Email
        if(tietEmail.getText().toString().isEmpty())
        {
            tilyEmail.setError("Debe ingresar su Correo Electronico( Email )");
            solicitudFocus(tietEmail);
            return false;
        }
        //validar Mensaje
        if(tietMensaje.getText().toString().isEmpty())
        {
            tilyMensaje.setError("Debe ingresar un Mensaje( Comentario )");
            return false;
        }
        return true;
    }
    //metodo para posicionarte en el TextInputEditText donde este el error
    public void solicitudFocus(View v)
    {
        if(v.requestFocus())
        {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}