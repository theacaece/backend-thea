package edu.caece.app.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FuncionesUtiles {

  public static final String FORMATO_FECHA = "dd/MM/yyyy HH:mm:ss";

  public static Date obtenerFechaHoy() {
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_FECHA);
      String date = simpleDateFormat.format(new Date());
      Date fechaHoy = simpleDateFormat.parse(date);
      return fechaHoy;
    } catch (ParseException e) {
      return null;
    }
  }

  public static Date obtenerFecha(String fecha) {
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_FECHA);
      Date fechaHoy = simpleDateFormat.parse(fecha);
      return fechaHoy;
    } catch (ParseException e) {
      return null;
    }
  }

}
