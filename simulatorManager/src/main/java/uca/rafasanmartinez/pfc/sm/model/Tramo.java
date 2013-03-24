package uca.rafasanmartinez.pfc.sm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
public class Tramo implements Serializable
{

   @Id
   private @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", updatable = false, nullable = false)
   Long id = null;

   @Version
   @Column(name = "version")
   private int version = 0;

   @NotNull(message="Sensor de entrada no puede estar vacío")
   @Column 
   @Size(message = "El nombre del sensor no puede tener más de 20 caracteres", max = 20)
   private String loopEntrada = "Entrar loop entrada";

   @NotNull(message="Sensor de salida no puede estar vacío")
   @Column
   @Size(message = "El nombre del sensor no puede tener más de 20 caracteres", max = 20)
   private String loopSalida = "Entrar loop salida";

   @NotNull(message="Nombre de la vía no puede estar vacío")
   @Column
   @Size(message = "El nombre de la vía no puede tener más de 60 caracteres", max = 60)
   private String nombre = "Entrar nombre";

   @NotNull(message="La longitud no puede estar vacía")
   @Column
   /*@Pattern(message = "Ha de ser un número natural", regexp = "^(?:0|[1-9]\\d*)$")*/
   private
   int longitud = 0;

   @NotNull(message="Umbral de segundos atasco no puede estar vacío")
   @Column
   /* @Pattern(message = "Ha de ser un número natural", regexp = "^(?:0|[1-9]\\d*)$") */
   private int umbralSegundosAtasco = 0;

   @NotNull(message="Umbral de segundos tráfico pesado no puede estar vacío")
   @Column
   /* @Pattern(message = "Ha de ser un número natural", regexp = "^(?:0|[1-9]\\d*)$") */
   private int umbralSegundosPesado = 0;

   @NotNull(message="Sentido de la vía no puede estar vacío")
   @Column
   @Size(message = "El nombre del sentido no puede tener más de 60 caracteres", max = 60)
   private String sentido= "Entrar sentido" ;

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((Tramo) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public String getLoopEntrada()
   {
      return this.loopEntrada;
   }

   public void setLoopEntrada(final String loopEntrada)
   {
      this.loopEntrada = loopEntrada;
   }

   public String getLoopSalida()
   {
      return this.loopSalida;
   }

   public void setLoopSalida(final String loopSalida)
   {
      this.loopSalida = loopSalida;
   }

   public String getNombre()
   {
      return this.nombre;
   }

   public void setNombre(final String nombre)
   {
      this.nombre = nombre;
   }

   public int getLongitud()
   {
      return this.longitud;
   }

   public void setLongitud(final int longitud)
   {
      this.longitud = longitud;
   }

   public int getUmbralSegundosAtasco()
   {
      return this.umbralSegundosAtasco;
   }

   public void setUmbralSegundosAtasco(final int umbralSegundosAtasco)
   {
      this.umbralSegundosAtasco = umbralSegundosAtasco;
   }

   public int getUmbralSegundosPesado()
   {
      return this.umbralSegundosPesado;
   }

   public void setUmbralSegundosPesado(final int umbralSegundosPesado)
   {
      this.umbralSegundosPesado = umbralSegundosPesado;
   }

   public String getSentido()
   {
      return this.sentido;
   }

   public void setSentido(final String sentido)
   {
      this.sentido = sentido;
   }

   public String toString()
   {
      String result = "";
      if (loopEntrada != null && !loopEntrada.trim().isEmpty())
         result += loopEntrada;
      if (loopSalida != null && !loopSalida.trim().isEmpty())
         result += " " + loopSalida;
      if (nombre != null && !nombre.trim().isEmpty())
         result += " " + nombre;
      result += " " + longitud;
      result += " " + umbralSegundosAtasco;
      result += " " + umbralSegundosPesado;
      if (sentido != null && !sentido.trim().isEmpty())
         result += " " + sentido;
      return result;
   }
}