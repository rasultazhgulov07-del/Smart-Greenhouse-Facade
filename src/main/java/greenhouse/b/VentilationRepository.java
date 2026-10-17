package greenhouse.b;
import java.nio.file.*;import java.io.*;import java.util.*;
public class VentilationRepository {
 private final Path dir;public VentilationRepository(Path root){dir=root.resolve("b");}
 public void save(Ventilation e){try{Files.createDirectories(dir);Properties p=new Properties();p.setProperty("status",e.status().name());try(var out=Files.newOutputStream(dir.resolve(e.id()+".properties"))){p.store(out,"status");}}catch(IOException x){throw new IllegalStateException(x);}}
 public Ventilation find(String id){Path f=dir.resolve(id+".properties");if(!Files.exists(f))return null;try(var in=Files.newInputStream(f)){Properties p=new Properties();p.load(in);return new Ventilation(id,Ventilation.Status.valueOf(p.getProperty("status")));}catch(IOException x){throw new IllegalStateException(x);}}
}
